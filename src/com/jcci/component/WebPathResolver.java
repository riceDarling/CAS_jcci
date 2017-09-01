package com.jcci.component;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * 读取项目绝对路径的解析器.该解析器是自定义的spring解析器，该解析器在api方法被调用前执行。<br>
 * 解析器执行时会将从HttpRequest中读取的项目绝对路径赋值给标有该注解的参数。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
public class WebPathResolver implements HandlerMethodArgumentResolver {

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer model,
      NativeWebRequest nativeRequest, WebDataBinderFactory dataFactory) throws Exception {
    if (parameter.getParameterAnnotation(WebPath.class) != null
        || parameter.getMethodAnnotation(WebPath.class) != null) {
      HttpServletRequest request = nativeRequest.getNativeRequest(HttpServletRequest.class);
      return request.getSession().getServletContext().getRealPath("");
    } else {
      return null;
    }
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (parameter.hasParameterAnnotation(WebPath.class)) {
      return true;
    } else if (parameter.getMethodAnnotation(WebPath.class) != null) {
      return true;
    }
    return false;
  }

}
