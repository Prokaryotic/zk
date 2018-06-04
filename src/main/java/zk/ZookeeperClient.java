package zk;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @author lijunxue
 * @create 2018-04-15 15:08
 **/
public class ZookeeperClient {
    public static void main(String[] args) throws Exception {

        String CONN = "192.168.56.100,192.168.56.101,192.168.56.102";

        // 第一个参数是 假如第一台连不上依次连接后面的  zookeeper 第二个参数 是几毫秒之后 说明连接断了 第三个是监听器看有没有连接上
        // 这里可以用门闩 CountDownLatch
        ZooKeeper zk = new ZooKeeper(CONN, 5000, null);

        Thread.sleep(3000);

        zk.create("/yyy","yyy".getBytes("utf8"), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        Thread.sleep(20000);
        zk.close();


    }
}
