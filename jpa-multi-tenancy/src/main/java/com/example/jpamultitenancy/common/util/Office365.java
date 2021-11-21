package com.example.jpamultitenancy.common.util;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Date;
import java.util.Properties;

/** @ClassName: Office365 @Author: amy @Description: Office365 @Date: 2021/6/23 @Version: 1.0 */
public class Office365 {

  public static void main(String[] args) {
    String sender = "Amy.Wang@gaiaworks.cn"; // Etin.Wang@gaiaworks.cn
    String password = "Gaia@2021"; // wmjWMJ12!@

    // 收件人邮箱地址
    String receiver = "Amy.Wang@gaiaworks.cn";

    // office365 邮箱服务器地址及端口号
    String host = "smtp.partner.outlook.cn"; // smtp.partner.outlook.cn
    String port = "587";

    try {

      Properties props = new Properties();
      // 开启debug调试
      props.setProperty("mail.debug", "true"); // false
      // 发送服务器需要身份验证
      props.setProperty("mail.smtp.auth", "true");
      // 设置邮件服务器主机名
      props.setProperty("mail.host", host);
      // 发送邮件协议名称
      props.setProperty("mail.transport.protocol", "smtp");
      // 发送邮件协议port
      props.setProperty("mail.smtp.port", port);
      // 开启ttl
      props.put("mail.smtp.starttls.enable", "true");

      // 设置环境信息
      Session session = Session.getInstance(props);
      // 创建邮件对象
      MimeMessage msg = new MimeMessage(session);
      // 设置发件人
      msg.setFrom(new InternetAddress(sender));

      // 设置收件人
      msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiver));

      // 设置邮件主题
      msg.setSubject("TEST1");
      msg.setSentDate(new Date());

      StringBuffer buffer = new StringBuffer();
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setDataHandler(
          new DataHandler(
              new ByteArrayDataSource(
                  buffer.toString(), "text/calendar;method=REQUEST;charset=\"UTF-8\"")));

      // 设置邮件内容
      Multipart multipart = new MimeMultipart();

      MimeBodyPart textPart = new MimeBodyPart();
      textPart.setText("hello world");
      multipart.addBodyPart(textPart);

      multipart.addBodyPart(messageBodyPart);

      // 添加附件
      /*    MimeBodyPart attachPart = new MimeBodyPart();
      DataSource source = new FileDataSource("/Users/amy/gac/logs/gac-schedule/app.log");
      attachPart.setDataHandler(new DataHandler(source));
      attachPart.setFileName("app.log");
      multipart.addBodyPart(attachPart); */

      msg.setContent(multipart);

      Transport transport = session.getTransport();
      // 连接邮件服务器
      transport.connect(sender, password);
      // 发送邮件
      transport.sendMessage(msg, new Address[] {new InternetAddress(receiver)});
      // 关闭连接
      transport.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
