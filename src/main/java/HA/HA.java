package HA;

import org.apache.zookeeper.*;
import org.apache.zookeeper.ZooDefs.Ids;

import java.util.Random;





/**
 * @author lijunxue
 * @create 2018-04-15 15:23
 **/
public class HA {
    static String status = "ACTIVE";

    public static void main(String[] args) throws Exception {
        String CONN = "192.168.56.100:2181,192.168.56.101:2181,192.168.56.102:2181";

        // 第一个参数是 假如第一台连不上依次连接后面的  zookeeper 第二个参数 是几毫秒之后 说明连接断了 第三个是监听器看有没有连接上
        // 这里可以用停拴 CountDownLatch
        ZooKeeper zk = new ZooKeeper(CONN, 5000, null);

        String ip = "192.168.56.101";
        String port = new Random().nextInt(500)+"";

        System.out.println(port);

        if (zk.exists("/tank/master",new MasterWatcher()) != null){
            status = "STANDBY";
        } else {
            zk.create("/tank/master",(ip+":"+port).getBytes("utf8"), Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL);
            status = "ACTIVE";
            // 启动server
            System.out.println("启动就是active");
        }

        Thread.sleep(Integer.MAX_VALUE);

    }

    static class MasterWatcher implements Watcher{

        public void process(WatchedEvent event) {
           if (event.getType() == Event.EventType.NodeDeleted){
               status = "ACTIVE";
               System.out.println("切换为active");
           }
        }
    }
}
