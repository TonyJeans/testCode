package com.taotao.pagehelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;


public class SpringActivemq {
//使用jsmTemplate发送消息
    @Test
    public void testJmsTemple(){
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //获得jsmTemplate
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从容器中获得Destination对象
        // applicationContext.getBean(Destination.class)
        Destination destination = (Destination) applicationContext.getBean("test-queue-mylistener");
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage message = session.createTextMessage("spring activemq send queue");
                return message;
            }
        });
    }
}
