package com.jcci.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 读取项目路径注解.该注解用于portal层方法的参数上。在调用接口方法之前由自定义的springResolver捕获该注解。<br>
 * Resolver会将读取到的项目的绝对路径，并将其赋值给标注有该注解的参数。
 * 
 * @author 周峰
 * @time 2016年7月22日 上午11:02:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebPath {
}
