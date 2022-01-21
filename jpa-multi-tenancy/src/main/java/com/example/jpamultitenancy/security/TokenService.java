package com.example.jpamultitenancy.security;

import com.example.jpamultitenancy.common.constant.Constants;
import com.example.jpamultitenancy.dto.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: TokenService @Author: amy @Description: TokenService @Date:
 *             2021/7/6 @Version: 1.0
 */
@Component
public class TokenService {

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

	private static final Long MILLIS_MINUTE_TEN = 20 * 60 * 1000L;

	@Autowired
	public RedisTemplate redisTemplate;

	/**
	 * 获取用户身份
	 *
	 * @param request
	 * @return
	 */
	public LoginUser getLoginUser(HttpServletRequest request) {
		String token = getToken(request);
		if (StringUtils.isNotEmpty(token)) {
			Claims claims = parseToken(token);
			// 解析对应的权限以及用户信息
			String uuid = (String) claims.get(Constants.LOGIN_USER_KEY);
			String userKey = getTokenKey(uuid);

			return (LoginUser) redisTemplate.opsForValue().get(userKey);
		}
		return null;
	}

	/**
	 * 验证令牌有效期，相差不足20分钟，自动刷新缓存 token 续期
	 *
	 * @param loginUser
	 */
	/*
	 * public void verifyToken(LoginUser loginUser) { Long expireTime =
	 * loginUser.getExpireTime(); long currentTime = System.currentTimeMillis(); if
	 * (expireTime - currentTime <= MILLIS_MINUTE_TEN) { refreshToken(loginUser); }
	 * }
	 */

	/**
	 * token 续期
	 *
	 * @param loginUser
	 */
	/*
	 * public void refreshToken(LoginUser loginUser) {
	 * loginUser.setLoginTime(System.currentTimeMillis());
	 * loginUser.setExpireTime(loginUser.getLoginTime() + expireTime *
	 * MILLIS_MINUTE); String userKey = getTokenKey(loginUser.getToken());
	 * redisTemplate.opsForValue().set(userKey, loginUser, expireTime,
	 * TimeUnit.MINUTES); }
	 */

	/**
	 * 从令牌中获取数据申明
	 *
	 * @param token
	 * @return
	 */
	private Claims parseToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	/**
	 * 获取请求token
	 *
	 * @param request
	 * @return
	 */
	public String getToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER_KEY);
		if (StringUtils.isNotEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
			token.replace(TOKEN_PREFIX, "");
		}
		return token;
	}

	private String getTokenKey(String uuid) {
		return Constants.LOGIN_TOKEN_KEY + uuid;
	}

	/**
	 * 创建token 规则 要修改 暂不考虑token续期
	 *
	 * @param
	 * @return
	 */
	/*
	 * public String createToken(LoginUser loginUser) { // uuid String token =
	 * UUID.randomUUID().toString(); loginUser.setToken(token); // token 设置过期时间
	 * refreshToken(loginUser);
	 * 
	 * // 封装 claim Map<String, Object> claims = new HashMap<>();
	 * claims.put(Constants.LOGIN_USER_KEY, token); // 加密生成token return
	 * createToken(claims); }
	 */

	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.ES512, secret).compact();
	}

	/**
	 * 构建token
	 *
	 * @param loginUser
	 * @return
	 */
	private String doGenerateToken(LoginUser loginUser) {

		Claims claims = Jwts.claims().setSubject(loginUser.getUsername()).setAudience(loginUser.getTenant());
		claims.put("scopes", Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")));

		return Jwts.builder().setClaims(claims).setIssuer("system").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expireTime * MILLIS_MINUTE))
				.signWith(SignatureAlgorithm.HS256, secret).compact();
	}
}
