package com.example.jpamultitenancy;

import lombok.Data;

/** @ClassName: LoginResult @Author: amy @Description: LoginResult @Date: 2021/6/22 @Version: 1.0 */
@Data
public class LoginResult {

  private Boolean result;

  private String errorCode;

  private String errorMsg;

  private TokenInfo data;

  @Data
  class TokenInfo {

    private String access_token;

    private Long expires_in;

    private Long refresh_expires_in;

    private String refresh_token;

    private String token_type;

    private String session_state;

    private String scope;
  }
}
