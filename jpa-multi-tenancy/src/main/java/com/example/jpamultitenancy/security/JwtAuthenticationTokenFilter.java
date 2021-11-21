package com.example.jpamultitenancy.security;

import com.example.jpamultitenancy.common.constant.JWTConstants;
import com.example.jpamultitenancy.common.interceptor.TenantContextHolder;
import com.example.jpamultitenancy.common.util.JwtTokenUtils;
import com.example.jpamultitenancy.common.util.SecurityUtils;
import com.example.jpamultitenancy.master.entity.MasterTenant;
import com.example.jpamultitenancy.master.service.MasterTenantService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.example.jpamultitenancy.common.constant.JWTConstants.HEADER_STRING;

/**
 * @ClassName: JwtAuthenticationTokenFilter @Author: amy @Description: token过滤器 验证token有效性 @Date:
 * 2021/7/6 @Version: 1.0
 */
@Slf4j
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

  @Autowired private TokenService tokenService;

  @Autowired private MasterTenantService masterTenantService;

  @Autowired private JwtTokenUtils jwtTokenUtils;

  @Autowired private UserDetailServiceImpl jwtUserDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // 1. getHeader
    String header = request.getHeader(HEADER_STRING);
    String username = null;
    String audience = null; // tenantOrClientId
    String authToken = null;
    if (header != null && header.startsWith(JWTConstants.TOKEN_PREFIX)) {
      authToken = header.replace(JWTConstants.TOKEN_PREFIX, "");
      try {
        // 1. getUsername tenant
        username = jwtTokenUtils.getUsernameFromToken(authToken);
        audience = jwtTokenUtils.getAudienceFromToken(authToken);
        // 根据 登陆用户获取 tenant信息
        MasterTenant masterTenant = masterTenantService.findByTenant(audience);
        if (null == masterTenant) {
          logger.error("An error during getting tenant name");
          throw new BadCredentialsException("Invalid tenant and user.");
        }
        TenantContextHolder.setTenant(masterTenant.getTenant());

      } catch (IllegalArgumentException ex) {
        logger.error("An error during getting username from token", ex);
      } catch (ExpiredJwtException ex) {
        logger.warn("The token is expired and not valid anymore", ex);
      } catch (SignatureException ex) {
        logger.error("Authentication Failed. Username or Password not valid.", ex);
      }
    } else {
      logger.warn("Couldn't find bearer string, will ignore the header");
    }

    if (Objects.nonNull(username) && Objects.isNull(SecurityUtils.getAuthentication())) {
      // 验证 token
      // tokenService.verifyToken(loginUser);
      UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);

      // check token
      jwtTokenUtils.validateToken(authToken, userDetails);
      UsernamePasswordAuthenticationToken authenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }
}
