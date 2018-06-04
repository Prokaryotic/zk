package zkdetail.client;

import zkdetail.common.HelloService;

import static java.lang.System.out;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @author lijunxue
 * @create 2018-05-08 1:05
 **/
public class RmiClientWithZk {
    public static void main(String[] args) throws Exception{
        ServoceConsumer consumer = new ServoceConsumer();
        while (true) {
            HelloService helloService = consumer.lookup();
            String result = helloService.sayhello("坎坎坷坷");
            out.println(result);
            SECONDS.sleep(3);

        }
    }
}
