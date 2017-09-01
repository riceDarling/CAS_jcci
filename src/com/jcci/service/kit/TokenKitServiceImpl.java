package com.jcci.service.kit;

import static unicorn.project.security.AccessSecurity.KEY;
import com.jcci.pojo.Administrator;
import com.jcci.service.part.AdministratorService;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import unicorn.common.cache.EhCacheUtils;
import unicorn.project.courier.StrBool;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

@Service
public class TokenKitServiceImpl implements TokenKitService {

	@Override
	public StrBool createToken(Administrator administrator) {
		logger.debug("S> 验证用户登录，创建并返回令牌。Param:{}", administrator);
		Administrator administratorDb = adminS.readAdministratorByCode(administrator.getAdminCode());
		if (administratorDb == null) {
			StrBool result = new StrBool();
		    result.setSuccess(false);
		    result.setCause("登录的用户不存在。");
		    result.setNotice("您的身份有误，无法登录系统。");
		    logger.debug("E> 登录的用户不存在。Return:{}", result);
		    return result;
		}
		
		if (administratorDb.getAdminPwd().equals(administrator.getAdminPwd())) {
			String token = compactToken(administratorDb.getAdminId().toString(), administratorDb.getAdminType().toString());
			      
			StrBool result = new StrBool();
		    result.setSuccess(true);
		    result.setContent(token);
		    // 将token存入缓存
		    EhCacheUtils.cache("tokenCache").put(String.valueOf(administratorDb.getAdminId()), token);
		    logger.debug("E> 登录成功。Return:{}", result);
		    return result;
		    
		} else {
			
			StrBool result = new StrBool();
			result.setSuccess(false);
			result.setCause("用户名或密码错误。");
			result.setNotice("您输入的用户名或密码错误。");
			logger.debug("E> 用户名或密码错误。Return:{}", result);
			return result;
			
	    }
    
  }
  
  

  @Override
  public boolean deleteToken(String token) {
    return true;
  }

  /**
   * 使用JWT生成Token.
   * 
   * @param adminId 系统用户的主键
   * @param adminType 系统用户的类型
   * @return 令牌的字符串
   */
  private String compactToken(String adminId, String adminType) {
    // 初始化用于存入claims的map。
    Map<String, Object> claimParams = new HashMap<String, Object>();
    claimParams.put("uid", adminId);
    claimParams.put("utype", adminType);
    // 用于存入header的map
    Map<String, Object> header = new HashMap<String, Object>();
    header.put("typ", "JWT");
    header.put("alg", "HS256");
    // 将字符串转换成一个字节数组。
    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(KEY);
    // 判断加密算法
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    // 使用指定的加密算法生成一个密钥
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
    // 获取服务器当前的时间。
    long currentTime = System.currentTimeMillis();
    Date now = new Date(currentTime);
    JwtBuilder builder = Jwts.builder().setHeader(header).setIssuedAt(now).setIssuer("jcci")
        .setClaims(claimParams).signWith(signatureAlgorithm, signingKey);
    long expMillis = currentTime + (1440L * 60L * 1000L);// 24小时后失效
    Date exp = new Date(expMillis);
    builder.setExpiration(exp);
    return builder.compact();
  }
  
  

  
  /*
   * 绠＄悊鍛樼殑partService瀵硅薄
   */
  @Autowired
  private AdministratorService adminS;
  

  /*
   * 数据库记录对象
   */
  private static Logger logger = LoggerFactory.getLogger(TokenKitService.class);
}
