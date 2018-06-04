package zkdetail.server;

import zkdetail.common.HelloService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author lijunxue
 * @create 2018-05-07 23:27
 **/
public class HelloServiceImp extends UnicastRemoteObject implements HelloService {

    protected HelloServiceImp() throws RemoteException{

    }
    public String sayhello(String s) throws RemoteException {
        return String.format("Hello %s" ,s);
    }
}
