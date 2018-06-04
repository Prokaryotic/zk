package zkdetail.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author lijunxue
 * @create 2018-05-07 23:29
 **/
public interface HelloService extends Remote {
    String sayhello(String s) throws RemoteException;
}
