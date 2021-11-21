package com.example.jpamultitenancy.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/** @author Md. Amran Hossain */
@Component
public class JwtTokenUtils {
  // 令牌秘钥
  @Value("${token.secret}")
  private String secret;

  // 令牌有效期（默认30分钟）
  @Value("${token.expireTime}")
  private int expireTime;

  private static final String TOKEN_HEADER_KEY = "Authorization";

  private static final String TOKEN_PREFIX = "Bearer ";

  protected static final long MILLIS_SECOND = 1000;

  protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

  public String getUsernameFromToken(String token) {
    return getClaimFromToken(token, Claims::getSubject);
  }

  public String getAudienceFromToken(String token) {
    return getClaimFromToken(token, Claims::getAudience);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(String userName, String tenantOrClientId) {
    return doGenerateToken(userName, tenantOrClientId);
  }

  private String doGenerateToken(String subject, String tenantOrClientId) {

    Claims claims = Jwts.claims().setSubject(subject).setAudience(tenantOrClientId);
    //   claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

    return Jwts.builder()
        .setClaims(claims)
        .setIssuer("system")
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
