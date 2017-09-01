package unicorn.project.security;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Empower {
  /**
   * 将ID赋值给某个属性
   * 
   * @return 属性名
   */
  public String setIdTo() default "adminId";

  /**
   * 将Type复制给某个属性
   * 
   * @return 属性名
   */
  public String setTypeTo() default "adminType";
}
