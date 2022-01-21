package com.example.jpamultitenancy.tenant.service;

/**
 * @ClassName: MailService @Author: amy @Description: MailService @Date:
 *             2021/6/24 @Version: 1.0
 */
public interface MailService {

	void sendSimpleMail(String to, String subject, String content, String... cc);

	void sendHtmlMail(String to, String subject, String content, String... cc);

	void sendAttachmentsMail(String to, String subject, String content, String filePath, String... cc);

	void sendResourceMail(String to, String subject, String content, String rscPath, String rscId, String... cc);
}
