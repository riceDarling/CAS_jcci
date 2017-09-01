package com.jcci.component;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * 读取session解析器.该解析器是自定义的spring解析器，该解析器在api方法被调用前执行。<br>
 * 解析器执行时会将session中的某个字段读取出来并赋值给标有ReadSession注解的参数。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class ReadSessionResolver implements HandlerMethodArgumentResolver {

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer model,
      NativeWebRequest request, WebDataBinderFactory dataFactory) throws Exception {
    if (parameter.getParameterAnnotation(ReadSession.class) != null) {
      String annoVal = parameter.getParameterAnnotation(ReadSession.class).value();
      return request.getAttribute(annoVal, RequestAttributes.SCOPE_SESSION);
    } else {
      return null;
    }
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    if (parameter.hasParameterAnnotation(ReadSession.class)) {
      return true;
    } else {
      return false;
    }
  }

}
