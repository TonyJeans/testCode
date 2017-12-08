package jack28_web;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

public class JavaMail03Test {
    @Test
    public void test() throws Exception {
        ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext-mail.xml");



        JavaMailSender sender = ac.getBean(JavaMailSenderImpl.class);
        //发送一个允许带图片,带附件的邮件
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setFrom("isunyl@sina.com");
        helper.setTo("510806100@qq.com");
        helper.setSubject("来自sina");
        helper.setText("<html><head></head><body><h1>hello!!spring image html mail</h1>\"\n" +
                "<a href=http://www.baidu.com>baidu</a>\t\"<img src=cid:image/></body></html>\n",true);
        FileSystemResource resource = new FileSystemResource(new File("D:\\自定义壁纸\\enterdesk.jpg"));
        helper.addInline("image",resource);
        //发送时带附件
        FileSystemResource zipResource = new FileSystemResource(new File("D:\\stdio.7z"));
        helper.addAttachment("stdio.7z",zipResource);
        sender.send(mimeMessage);
    }
}
