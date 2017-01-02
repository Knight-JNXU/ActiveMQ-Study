package code.point2point;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Knigh on 2017/1/2.
 * 消息监听
 */
public class Listener implements MessageListener {
    public void onMessage(Message message) {
        try {
            System.out.println("Listener1:"+((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
