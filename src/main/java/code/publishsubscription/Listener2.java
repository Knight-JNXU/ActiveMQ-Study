package code.publishsubscription;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Knigh on 2017/1/2.
 * 消息监听
 */
public class Listener2 implements MessageListener {
    public void onMessage(Message message) {
        try {
            System.out.println("subscription2:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
