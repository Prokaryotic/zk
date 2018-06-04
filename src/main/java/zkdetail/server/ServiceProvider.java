package zkdetail.server;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import zkdetail.common.Constant;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.concurrent.CountDownLatch;

/**
 * @author lijunxue
 * @create 2018-05-08 0:04
 **/
public class ServiceProvider {

    private CountDownLatch latch = new CountDownLatch(1);

    // 发布RMI 服务并注册   Rmi 地址到zookeeper
    public void publish(Remote remote,String host,int port) throws Exception {
        String url = publishService(remote,host,port);
        if (url != null){
            ZooKeeper zooKeeper =  connectServer();
            if (zooKeeper != null){
                createNode(zooKeeper,url);
            }
        }
    }

    private void createNode(ZooKeeper zooKeeper, String url) throws  Exception {
        byte[] data = url.getBytes("utf8");
        String path = zooKeeper.create(Constant.ZK_PROVIDER_PATH,data,ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL); // 创建一个零时的有序列号的
        System.out.println(String.format("create zookeeper node (%s => %s)",path,url));
    }

    // 发布到RMI 服务
    private String publishService(Remote remote,String host,int port){
        String url = null;

        try {
            url = String.format("rmi://%s:%d/%s",host,port,remote.getClass().getName());
            LocateRegistry.createRegistry(port);
            Naming.rebind(url,remote);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    //连接zookeeper

    private ZooKeeper connectServer(){
        ZooKeeper zooKeeper = null;

        try {
            zooKeeper = new ZooKeeper(Constant.ZK_CONNECTION_STRING,Constant.ZK_SESSION_TIMEOUT,e->{
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
