package unicorn.common.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface ExcelDisguiser {
  /**
   * 字段的处理类.
   * 
   * @return 类的声明
   */
  public Class<?> using() default Void.class;
}
