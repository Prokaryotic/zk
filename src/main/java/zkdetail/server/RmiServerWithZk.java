package zkdetail.server;

import zkdetail.common.HelloService;

/**
 * @author lijunxue
 * @create 2018-05-07 23:59
 **/
public class RmiServerWithZk {

    public static void main(String[] args) throws Exception {
//        int port = 1098;
//        String url = "rmi://localhost:1098/zkdetail.server.HelloServiceImp";
//        LocateRegistry.createRegistry(port);
//        Naming.rebind(url,new HelloServiceImp());


        String host = "localhost";
        int port = Integer.parseInt("6666");
        ServiceProvider provider = new ServiceProvider();
        HelloService helloService  = new HelloServiceImp();
        provider.publish(helloService,host,port);
        Thread.sleep(Long.MAX_VALUE);

    }
}
