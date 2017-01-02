package code.point2point;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Knigh on 2017/1/2.
 * 当生产者产生消息时，自动接收，监听的方式接收
 */
public class JMSConsumer2 {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;  //默认的额连接地址(localhost:61616)
    private static final int SENDNUM = 10;  //发送的消息数量
    public static void main(String[] args){
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;  //接收或者发送消息的线程
        Destination destination;  //目的地址
        MessageConsumer messageConsumer;  //消息的消费者
        TextMessage receivedMessage = null;
        try {
            //实例化
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
            connection = connectionFactory.createConnection();
            connection.start();
            //第一个参数表示是否启用事务，第二个参数是消息确认方式：
            //Session.AUTO_ACKNOWLEDGE:当客户成功从receive方法返回的时候，
            //或者从MessageListener.onMessage方法成功返回时，回话自动确认客户收到的消息
            //Session.CLIENT_ACKNOWLEDGE:客户通过消息的acknowledge方式确认（会话层的确认）消息
            //例如：消费者消费了10个消息，然后确认第5个消息，那么这10个消息都被确认
            //Session.DUPS_OK_ACKNOWLEDGE:JMS provider失败时，重新发送
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);  //这里不加事务
            destination = session.createQueue("Queue1");
            messageConsumer = session.createConsumer(destination);
            messageConsumer.setMessageListener(new Listener());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
