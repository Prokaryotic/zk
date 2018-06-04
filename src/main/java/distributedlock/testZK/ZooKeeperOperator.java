package distributedlock.testZK;
import java.util.Arrays;  
import java.util.List;  
import org.apache.zookeeper.CreateMode;  
import org.apache.zookeeper.KeeperException;  
import org.apache.zookeeper.ZooDefs.Ids;  
  
public class ZooKeeperOperator extends AbstractZooKeeper {  
public void create(String path,byte[] data) throws KeeperException, InterruptedException{
        this.zooKeeper.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }  
      
      

    public void getChild(String path) throws KeeperException, InterruptedException{  
        try {  
            List<String> children = this.zooKeeper.getChildren(path, false);  
            if (children.isEmpty()) {  

                return;  
            }else{  

                for(String child: children){  
                    System.out.println(child);  
                }  
            }  
        } catch (KeeperException.NoNodeException e) {  

            throw e;  
        }  
    }  
  
    public byte[] getData(String path) throws KeeperException, InterruptedException {  
        return  this.zooKeeper.getData(path, false,null);  
    }  
    
    
    public static void main(String[] args) {  
        try {  
            ZooKeeperOperator zkoperator             = new ZooKeeperOperator();  
            zkoperator.connect("192.168.1.201");  
            byte[] data = new byte[]{'d','a','t','a'};  
              
            zkoperator.create("/root",null);  
            System.out.println(Arrays.toString(zkoperator.getData("/root")));  
              
            zkoperator.create("/root/child1",data);  
            System.out.println(Arrays.toString(zkoperator.getData("/root/child1")));  
              
            zkoperator.create("/root/child2",data);  
            System.out.println(Arrays.toString(zkoperator.getData("/root/child2")));  
              
            System.out.println("�ڵ㺢����Ϣ:");  
            zkoperator.getChild("/root");  
              
            zkoperator.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    } 
}  