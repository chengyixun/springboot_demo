package com.example.jpacrud.commons.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

/**
 * @ClassName: MessageUtils @Author: amy @Description: MessageUtils @Date:
 *             2021/9/23 @Version: 1.0
 */
@Component
public class MessageUtils {

	private static MessageSource messageSource;

	public MessageUtils(MessageSource messageSource) {
		MessageUtils.messageSource = messageSource;
	}

	public static String get(String msgKey) {
		try {
			return messageSource.getMessage(msgKey, null, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			return msgKey;
		}
	}
}
