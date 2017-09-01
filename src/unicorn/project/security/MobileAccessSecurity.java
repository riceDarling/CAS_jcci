package unicorn.project.security;

import static unicorn.common.charset.StringUtils.isEmpty;
import static unicorn.project.answer.Status.NOTACCESSIBLE;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import unicorn.common.cache.EhCacheUtils;
import unicorn.project.answer.Answer;

/**
 * 访问验证切面.在需要验证访问用户身份的时候验证手机用户的Token。
 * 
 * @author 周峰
 * @time 2017年2月24日 上午10:13:50
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
@Component
@Aspect
@Order(1)
public class MobileAccessSecurity {

	/**
	   * 访问验证的执行方法.在portal层的方法被调用的前后执行，在方法被调用前，验证用户携带的token是否合法，如果不合法则不允许方法被调用。
	   * 方法中产生的任何异常均直接抛出，被抛出的异常会被统一异常处理捕获并处理。
	   * 
	   * @param pjp 切入点
	   * @return 验证结果或方法执行结果
	   * @throws Throwable 所有的异常均抛出
	   */
	  @Around("execution(* com.jcci.portal.customer.*Portal.*(..)) && !@annotation(unicorn.project.security.Exposed)")
	  public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		  // 取的header中的值
		  String jwtStr = request.getHeader("Access-Token");
		  if (jwtStr == null || jwtStr.length() == 0) {
			  jwtStr = request.getParameter("token");
		  }
		  if(jwtStr == null){
			  Answer result =
					  Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("token不存在").build();
			  return result;
		  }
		  String uid = null;
		  try {
			  // 将token解析成数据对象
			  Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(CustomerKEY))
					  .parseClaimsJws(jwtStr).getBody();
			  // 取的token中的内容
			  uid = String.valueOf(claims.get("uid"));
		  } catch (SignatureException ex) {
			  Answer result =
					  Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("无效token").build();
			  return result;
		  } catch (ExpiredJwtException ex) {
			  Answer result =
					  Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("token失效").build();
			  return result;
		  }
		  // 判断是否取到了uid和utype两个值。
		  if (isEmpty(uid) ) {
			  Answer result =
					  Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误").putCause("无效token").build();
			  return result;
		  } else {
			  // 判断token是否过期。
			  String token = (String) EhCacheUtils.cache("tokenCustomerCache").get(uid);
			  if (isEmpty(token)) {
				  Answer result = Answer.builder().setStatus(NOTACCESSIBLE).putNotice("访问错误")
						  .putCause("token已过期").build();
				  return result;
			  } else {
				  // 取的调用方法的方法签名。
				  MethodSignature methodSign = (MethodSignature) pjp.getSignature();
				  Method method = methodSign.getMethod();
				  Annotation[][] paramAnns = method.getParameterAnnotations();
				  Class<?>[] paramsClass = method.getParameterTypes();
				  Object[] args = pjp.getArgs();
				  for (int i = 0; i < paramAnns.length; i++) {
					  Annotation[] annotations = paramAnns[i];
			          Class<?> paramClass = paramsClass[i];
			          for (Annotation annotation : annotations) {
			        	  if (annotation instanceof Uid) {
			        		  if (args[i] == null) {
			        			  if (paramClass == String.class) {
			        				  args[i] = uid;
			                          continue;
			        			  } else if (paramClass == Integer.class) {
			                          args[i] = new Integer(uid);
			                          continue;
			        			  } else if (paramClass == Long.class) {
			                          args[i] = new Long(uid);
			                          continue;
			        			  } else {
			                          continue;
			        			  }
			        		  }
			        	  }
			        	  else {
			        		  continue;
			        	  }
			          }
				  }
				  Object result = pjp.proceed(args);
				  return result;
			  }
		  }
	  }

	  @Autowired
	  private HttpServletRequest request;
	  public static final String CustomerKEY = "F!qabihsSJU34jshmobile";
}
