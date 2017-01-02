package code.publishsubscription;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Knigh on 2017/1/2.
 * 消息发布者
 */
public class JMSPublish {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENDNUM = 10;
    public static void main(String[] args){
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;
        Destination destination;
        MessageProducer messageProducer;
        try {
            connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("Topic1");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static void sendMessage(Session session, MessageProducer messageProducer){
        for (int i=0; i<SENDNUM; i++){
            try {
                TextMessage textMessage = session.createTextMessage("publish:No:"+i);
                System.out.println("publish:No:"+i);
                messageProducer.send(textMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
