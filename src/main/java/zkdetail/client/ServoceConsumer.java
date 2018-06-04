package zkdetail.client;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import zkdetail.common.Constant;

import java.rmi.Naming;
import java.rmi.Remote;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lijunxue
 * @create 2018-05-08 0:44
 **/
public class ServoceConsumer {
    private CountDownLatch latch = new CountDownLatch(1);
    private List<String> urlList = new ArrayList<>();

    public ServoceConsumer() {
        ZooKeeper zooKeeper = connectServer();
        if (zooKeeper != null){
            try {
                watchNode(zooKeeper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void watchNode(final ZooKeeper zooKeeper) throws Exception {
        List<String> nodeList = zooKeeper.getChildren(Constant.ZK_REGISTRY_PATH,event ->{
           if (event.getType() == Watcher.Event.EventType.NodeChildrenChanged){
               try {
                   watchNode(zooKeeper);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }
        });
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < nodeList.size(); i++) {
            byte[] data = zooKeeper.getData(Constant.ZK_REGISTRY_PATH+"/"+nodeList.get(i),false,null);
            dataList.add(new String(data,"utf8"));
        }
        System.out.println(dataList);
        urlList = dataList;
    }

    // 查找RMI 服务号
    public <T extends Remote> T lookup(){
        T service = null;
        int size = urlList.size();
        if (size >0){
            String url ;
            if (size == 1){
                url = urlList.get(0);
                System.out.println(String.format("using only url %s", url));

            } else {
                url = urlList.get(ThreadLocalRandom.current().nextInt(size));
                System.out.println(String.format("using random url %s", url));
            }

            try {
                service = looupService(url); // 从 JNDI 中查找 RMI 服务
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return service;
    }

    private <T extends Remote> T looupService(String url) throws Exception {
        return (T) Naming.lookup(url);
    }


    //连接zookeeper
    private ZooKeeper connectServer(){
        ZooKeeper zooKeeper = null;

        try {
            zooKeeper = new ZooKeeper(Constant.ZK_CONNECTION_STRING,Constant.ZK_SESSION_TIMEOUT, e->{
                if (e.getState() == Watcher.Event.KeeperState.SyncConnected){
                    latch.countDown();
                }
            });
            latch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return zooKeeper;
    }
}
