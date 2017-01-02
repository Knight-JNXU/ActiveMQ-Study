package code.point2point;


import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by Knigh on 2017/1/2.
 * 消息的生产者
 */
public class JMSProducer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKEURL = ActiveMQConnection.DEFAULT_BROKER_URL;  //默认的额连接地址(localhost:61616)
    private static final int SENDNUM = 10;  //发送的消息数量

    public static void main(String[] args){
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session = null;  //接收或者发送消息的线程
        Destination destination;  //目的地址
        MessageProducer messageProducer;  //消息生产者

        try{
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
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Queue1");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();  //前面开启了事务，所以需要提交事务
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
                session.close();  //开启的事务必须关闭
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception{
        for(int i=0; i<SENDNUM; i++){
            TextMessage textMessage = session.createTextMessage("No:"+i+" activemq");
            System.out.println("send "+"No:"+i+" activemq");
            messageProducer.send(textMessage);
        }
    }
}
