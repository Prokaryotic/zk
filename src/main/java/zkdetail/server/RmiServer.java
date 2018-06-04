package zkdetail.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * @author lijunxue
 * @create 2018-05-07 23:24
 **/
public class RmiServer {

    public static void main(String[] args) throws Exception {
        int port = 1098;
        String url = "rmi://localhost:1098/zkdetail.server.HelloServiceImp";
        LocateRegistry.createRegistry(port);
        Naming.rebind(url,new HelloServiceImp());
    }
}
