package jack28_web;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaMail02Test {
    @Test
    public void test() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");
        SimpleMailMessage message = ac.getBean(SimpleMailMessage.class);
        message.setSubject("spring集成javamail");
        message.setText("测试内容");
        message.setTo("isunyl@sina.com");

        JavaMailSender sender = ac.getBean(JavaMailSenderImpl.class);
        sender.send(message);
    }
}
