package com.neo.discovery.util.mail;

import com.sun.mail.util.MailSSLSocketFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by cuizhixiang on 2017/5/31.
 * 资源文件方式
 **/
@Component
public class MailUtil {
    private static final String HOST = MailConfig.host;
    private static final Integer PORT = MailConfig.port;
    private static final String USERNAME = MailConfig.userName;
    private static final String PASSWORD = MailConfig.passWord;
    private static final String emailForm = MailConfig.emailForm;
    private static final String timeout = MailConfig.timeout;
    private static final String personal = MailConfig.personal;
    private static JavaMailSenderImpl mailSender = createMailSender();

    private static final String HOST_QQ = "smtp.qq.com";
    private static final Integer PORT_QQ = 465;
    private static final String USERNAME_QQ = "710927120@qq.com";
    private static final String PASSWORD_QQ = "iygiobnopvyobbch";
    private static final String emailForm_QQ = "710927120@qq.com";
    private static final String timeout_QQ = MailConfig.timeout;
    private static final String personal_QQ = MailConfig.personal;
    private static JavaMailSenderImpl mailSender_QQ = createQQMailSender();

    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST);
        sender.setPort(PORT);
        sender.setUsername(USERNAME);
        sender.setPassword(PASSWORD);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.timeout", timeout);
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
    }
    /**
     * 邮件发送器
     *
     * @return 配置好的工具
     */
    private static JavaMailSenderImpl createQQMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(HOST_QQ);
        sender.setPort(PORT_QQ);
        sender.setUsername(USERNAME_QQ);
        sender.setPassword(PASSWORD_QQ);
        sender.setDefaultEncoding("Utf-8");
        try {

            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            Properties p = new Properties();
            p.setProperty("mail.smtp.timeout", timeout);
            p.setProperty("mail.smtp.auth", "true");
            p.put("mail.smtp.ssl.enable", "true");
            p.put("mail.smtp.ssl.socketFactory", sf);
            sender.setJavaMailProperties(p);
            return sender;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发送邮件
     *
     * @param to 接受人
     * @param subject 主题
     * @param html 发送内容
     * @throws javax.mail.MessagingException 异常
     * @throws java.io.UnsupportedEncodingException 异常
     */
    public static void sendMail(String to, String subject, String html) throws MessagingException,UnsupportedEncodingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(emailForm, personal);
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);
        messageHelper.setText(html, true);
        mailSender.send(mimeMessage);
    }


    public static void sendMail(String subject,String content){

        try{

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            // 设置utf-8或GBK编码，否则邮件会有乱码
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(emailForm, personal);
            messageHelper.setTo("13691177451@163.com");
            messageHelper.setSubject(subject);
            messageHelper.setText(content, true);
            mailSender.send(mimeMessage);
        }catch (Exception e){
            e.printStackTrace();
            try{

                MimeMessage mimeMessage = mailSender_QQ.createMimeMessage();
                // 设置utf-8或GBK编码，否则邮件会有乱码
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                messageHelper.setFrom(emailForm_QQ, personal);
                messageHelper.setTo("13691177451@163.com");
                messageHelper.setSubject(subject);
                messageHelper.setText(content, true);
                mailSender_QQ.send(mimeMessage);
            }catch (Exception e1){
                e1.printStackTrace();
            }
        }


    }


    public static  void main(String[] args){
        try{

            MailUtil.sendMail("13691177451@163.com","test","aaaaaaa");
            System.out.print(" finish");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
