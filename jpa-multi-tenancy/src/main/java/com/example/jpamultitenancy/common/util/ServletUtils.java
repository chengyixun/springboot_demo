package com.example.jpamultitenancy.common.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: ServletUtils @Author: amy @Description: ServletUtils @Date: 2021/7/6 @Version: 1.0
 */
public class ServletUtils {

  /**
   * 将字符串渲染到客户端
   *
   * @param response
   * @param string
   * @return
   */
  public static String renderString(HttpServletResponse response, String string) {
    try {
      response.setStatus(200);
      response.setContentType("application/json");
      response.setCharacterEncoding("utf-8");
      response.getWriter().print(string);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
