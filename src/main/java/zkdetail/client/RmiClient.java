package zkdetail.client;

import zkdetail.common.HelloService;

import java.rmi.Naming;


/**
 * @author lijunxue
 * @create 2018-05-07 23:28
 **/
public class RmiClient {
    public static void main(String[] args) throws Exception {
        String url = "rmi://localhost:1098/zkdetail.server.HelloServiceImp";
        HelloService helloService = (HelloService) Naming.lookup(url);
        String result = helloService.sayhello("666");
        System.out.println(result);
    }
}
