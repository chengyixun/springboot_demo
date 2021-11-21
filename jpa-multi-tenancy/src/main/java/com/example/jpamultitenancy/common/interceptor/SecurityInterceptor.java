package com.example.jpamultitenancy.common.interceptor;

import com.example.jpamultitenancy.common.exception.Exceptions;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link SecurityInterceptor}
 *
 * @author Liyaohui
 * @date 6/20/21
 */
public class SecurityInterceptor implements HandlerInterceptor {

  private static final String SESSION_KEY = "sessionId";

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    boolean isLogin = loginHandler(request);
    if (!isLogin) {
      throw Exceptions.NOT_FOUND();
    }
    return true;
  }

  @Override
  public void postHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      ModelAndView modelAndView)
      throws Exception {}

  /**
   * 从请求头中获取 token或者session 正常流程是：先登陆，登陆之后缓存redis， key：token/sessionId value：CurrentUserBean 接下来
   * 拦截器拦截各种请求，都走preHandle的loginHandler，
   *
   * @param request
   * @return
   */
  private boolean loginHandler(HttpServletRequest request) {
    // 解析 request 获取key，先从redis中获取 无 报错，
    String sessionId = request.getHeader(SESSION_KEY);
    CurrentLoginUser currentLoginUser = new CurrentLoginUser();

    // 有存 threadLocal
    CurrentUser.set(currentLoginUser);
    return true;
  }
}
