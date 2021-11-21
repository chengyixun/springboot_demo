package com.example.jpamultitenancy.tenant.service.impl;

import cn.hutool.core.util.ArrayUtil;
import com.example.jpamultitenancy.tenant.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * @ClassName: MailServiceImpl @Author: amy @Description: MailServiceImpl @Date: 2021/6/24 @Version:
 * 1.0
 */
@Slf4j
@Service
public class MailServiceImpl implements MailService {

  @Autowired private JavaMailSender mailSender;

  @Value("${mail.from}")
  private String from;

  /**
   * 发送简单邮件
   *
   * @param to
   * @param subject
   * @param content
   * @param cc
   */
  @Override
  public void sendSimpleMail(String to, String subject, String content, String... cc) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();
    mailMessage.setFrom(from);
    mailMessage.setTo(to);
    mailMessage.setSubject(subject);
    mailMessage.setText(content);
    if (ArrayUtil.isNotEmpty(cc)) {
      mailMessage.setCc(cc);
    }
    mailSender.send(mailMessage);
    log.info("sendSimpleMail 已发送！");
  }

  @Override
  public void sendHtmlMail(String to, String subject, String content, String... cc) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;
    try {
      helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      if (ArrayUtil.isNotEmpty(cc)) {
        helper.setCc(cc);
      }
    } catch (MessagingException e) {
      log.error("send html email error:", e.getMessage());
    }
    mailSender.send(mimeMessage);
  }

  @Override
  public void sendAttachmentsMail(
      String to, String subject, String content, String filePath, String... cc) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    MimeMessageHelper helper = null;
    try {
      helper = new MimeMessageHelper(mimeMessage, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      if (ArrayUtil.isNotEmpty(cc)) {
        helper.setCc(cc);
      }
      FileSystemResource file = new FileSystemResource(new File(filePath));
      String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
      helper.addAttachment(fileName, file);

    } catch (MessagingException e) {
      log.error("send html email error:", e.getMessage());
    }
    mailSender.send(mimeMessage);
  }

  @Override
  public void sendResourceMail(
      String to, String subject, String content, String rscPath, String rscId, String... cc) {
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = null;
    try {
      helper = new MimeMessageHelper(message, true);
      helper.setFrom(from);
      helper.setTo(to);
      helper.setSubject(subject);
      helper.setText(content, true);
      if (ArrayUtil.isNotEmpty(cc)) {
        helper.setCc(cc);
      }
      FileSystemResource res = new FileSystemResource(new File(rscPath));
      helper.addInline(rscId, res);

    } catch (MessagingException e) {
      e.printStackTrace();
    }

    mailSender.send(message);
  }
}
