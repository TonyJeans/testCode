package cn.syl.utils;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtils {
    public static void sendMessage(String toAddr,String subject,String content)throws Exception{
        Properties props= new  Properties();
        props.put("mail.smtp.host","smtp.sina.com");//发送服务器
        props.put("mail.smtp.auth",true);//是否验证身份
        Session session = Session.getInstance(props);
        session.setDebug(true);//debug模式,输出smtp应答过程

        //
        MimeMessage message = new MimeMessage(session);
        //发送者
        Address fromAddress = new InternetAddress("isunyl@sina.com");
        message.setFrom(fromAddress);

        //接受者
        Address toAddress = new InternetAddress(toAddr);
        message.setRecipient(Message.RecipientType.TO,toAddress);//设置接收

        //设置邮件主题
        message.setSubject(subject);
        message.setText(content);

        //保存邮件
        message.saveChanges();

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.sina.com","isunyl@sina.com","s15927109076");
        transport.sendMessage(message,message.getAllRecipients());
        transport.close();
    }


}
