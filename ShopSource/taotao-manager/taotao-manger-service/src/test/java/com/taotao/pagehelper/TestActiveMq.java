package com.taotao.pagehelper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

public class TestActiveMq {

    @Test
    public void testQueryProducer() throws JMSException {
        //1创建连接工厂对象,服务ip和端口号
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.123.150:61616");
        //2使用连接工厂创建连接
        Connection connection = connectionFactory.createConnection();
        //3开启连接
        connection.start();
        //4使用连接创建session,
        // p1:是否开启事务MQ(一般不使用保证最终一致就行),
        // true自动忽略,p2:消息的应答模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5使用session对象创建一个Destination对象,queue,topic
        Queue queue = session.createQueue("test-queue");
        //6使用session创建Producer生产者
        MessageProducer producer = session.createProducer(queue);
        //7.创建TextMessage对象
        //TextMessage textMessage = new ActiveMQTextMessage();
       // textMessage.setText("hello mq");
        TextMessage message = session.createTextMessage("hello mq");
        //8发送消息
        producer.send(message);
        //9关闭资源
        session.close();
        connection.close();
    }
    @Test
    public void testQueueConsumer() throws JMSException, IOException {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.123.150:61616");
        //使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个连接session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session创建一个Destination,与消息发送端一致
        Queue queue = session.createQueue("test-queue");
        //session创建消费者Consumer对象
        MessageConsumer consumer = session.createConsumer(queue);
        //Consumer对象设置MessageListener对象,用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //取消息内容
                if (message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                     //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //系统等待接收消息
//        while(true){
//            Thread.sleep(100);
//        }
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
@Test  //默认不持久化,消费者没有启动就接收不到
    public void testTopicProducer() throws JMSException {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.123.150:61616");
        //创建连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //创建session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination,使用topic
        Topic topic = session.createTopic("test-topic");
        //创建Producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建TextMessage对象
        TextMessage textMessage = session.createTextMessage("hello topic");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicConsumer() throws Exception{
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://192.168.123.150:61616");
        //使用连接工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个连接session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session创建一个Destination,与消息发送端一致
        Topic topic = session.createTopic("test-topic");
        //session创建消费者Consumer对象
        MessageConsumer consumer = session.createConsumer(topic);
        //Consumer对象设置MessageListener对象,用来接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //取消息内容
                if (message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        String text = textMessage.getText();
                        //打印消息内容
                        System.out.println(text);
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //系统等待接收消息
//        while(true){
//            Thread.sleep(100);
//        }
        System.out.println("消费者3....");
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();

    }
}


