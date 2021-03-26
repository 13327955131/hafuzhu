package com.hoostec.hfz.service;

import com.hoostec.hfz.dto.LoginUser;
import com.hoostec.hfz.dto.Token;
import com.hoostec.hfz.entity.CmsLogLogin;
import com.hoostec.hfz.utils.DataUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Token管理器<br>
 * 可存储到redis
 * <p>
 * 2018年10月14日
 */
@Service
public class TokenService {

	/**
	 * token过期秒数
	 */
	private Integer expireSeconds = 7200;
	@Autowired
	private RedisTemplate<String, LoginUser> redisTemplate;
	@Autowired
	private CmsLogLoginService cmsLogLoginService;

	/**
	 * 私钥
	 */
	private String jwtSecret = "(HA:)_$^11244^%$_(FU:)_@@++--(ZHU:)";

	private static Key KEY = null;
	private static final String LOGIN_USER_KEY = "LOGIN_USER_KEY";

	public Token saveToken(LoginUser loginUser) {
		loginUser.setToken(UUID.randomUUID().toString());
		cacheLoginUser(loginUser);

		// 登陆日志
		CmsLogLogin plcLogLogin = new CmsLogLogin();
		plcLogLogin.setIp(DataUtils.getIp());
		plcLogLogin.setUserId(loginUser.getId());
		plcLogLogin.setUsername(loginUser.getNickName());
		plcLogLogin.setType("登陆");
		cmsLogLoginService.insert(plcLogLogin);

		String jwtToken = createJWTToken(loginUser);

		return new Token(jwtToken, loginUser.getLoginTime());
	}

	/**
	 * 生成jwt
	 *
	 * @param loginUser
	 * @return
	 */
	private String createJWTToken(LoginUser loginUser) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(LOGIN_USER_KEY, loginUser.getToken());// 放入一个随机字符串，通过该串可找到登陆用户

		String jwtToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, getKeyInstance())
				.compact();

		return jwtToken;
	}

	private void cacheLoginUser(LoginUser loginUser) {
		loginUser.setLoginTime(System.currentTimeMillis());
		loginUser.setExpireTime(loginUser.getLoginTime() + expireSeconds * 1000);
		// 根据uuid将loginUser缓存
		redisTemplate.boundValueOps(getTokenKey(loginUser.getToken())).set(loginUser, expireSeconds, TimeUnit.SECONDS);
	}

	/**
	 * 更新缓存的用户信息
	 */
	public void refresh(LoginUser loginUser) {
		cacheLoginUser(loginUser);
	}

	public LoginUser getLoginUser(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			return redisTemplate.boundValueOps(getTokenKey(uuid)).get();
		}

		return null;
	}

	public boolean deleteToken(String jwtToken) {
		String uuid = getUUIDFromJWT(jwtToken);
		if (uuid != null) {
			String key = getTokenKey(uuid);
			LoginUser loginUser = redisTemplate.opsForValue().get(key);
			if (loginUser != null) {
				redisTemplate.delete(key);
				return true;
			}
		}

		return false;
	}

	private String getTokenKey(String uuid) {
		return "tokens:" + uuid;
	}

	private Key getKeyInstance() {
		if (KEY == null) {
			synchronized (TokenService.class) {
				if (KEY == null) {// 双重锁
					byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(jwtSecret);
					KEY = new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS256.getJcaName());
				}
			}
		}

		return KEY;
	}

	private String getUUIDFromJWT(String jwtToken) {
		if ("null".equals(jwtToken) || StringUtils.isBlank(jwtToken)) {
			return null;
		}

		try {
			Map<String, Object> jwtClaims = Jwts.parser().setSigningKey(getKeyInstance()).parseClaimsJws(jwtToken)
					.getBody();
			return MapUtils.getString(jwtClaims, LOGIN_USER_KEY);
		} catch (ExpiredJwtException e) {
		} catch (Exception e) {
		}

		return null;
	}
}
