package zkdetail.common;

/**
 * @author lijunxue
 * @create 2018-05-08 0:16
 **/
public interface Constant {
    String ZK_CONNECTION_STRING = "192.168.56.100:2181,192.168.56.101:2181,192.168.56.102:2181";
    int ZK_SESSION_TIMEOUT = 5000;
    String ZK_REGISTRY_PATH = "registry";
    String ZK_PROVIDER_PATH = ZK_REGISTRY_PATH + "/provider";
}
