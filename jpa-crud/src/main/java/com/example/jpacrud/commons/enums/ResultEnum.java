package com.example.jpacrud.commons.enums;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

/**
 * {@link ResultEnum}
 *
 * @author Liyaohui
 * @date 10/7/21
 */
public enum ResultEnum {
  SUCCESS("success"),
  ERROR("error"),
  OTHER("other");

  /** 返回值详情 */
  private String message;

  private MessageSource messageSource;

  ResultEnum(String message) {
    this.message = message;
  }

  public ResultEnum setMessageSource(MessageSource messageSource) {
    this.messageSource = messageSource;
    return this;
  }

  @Component
  public static class ReportTypeServiceInjector {

    @Autowired private MessageSource messageSource;

    @PostConstruct
    public void init() {
      for (ResultEnum re : EnumSet.allOf(ResultEnum.class)) {
        re.setMessageSource(messageSource);
      }
    }
  }

  public String getMessage() {
    return messageSource.getMessage(message, null, LocaleContextHolder.getLocale());
  }
}
