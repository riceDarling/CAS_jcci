package unicorn.common.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 属性的参数注解.该注解包含着order和title两个属性，order是该属性在生成的excel中的位置。title是该属性在生成的excel中的title。bgColor是列的背景颜色默认44。
 * 
 * @author 周峰
 * @version v0.0.1
 */
@Target({java.lang.annotation.ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public abstract @interface ExcelInstruction {
  /**
   * excel中的列顺序.
   * 
   * @return 顺序
   */
  public String order();

  /**
   * excel中的列头.
   * 
   * @return 列头名
   */
  public String title();
  /**
   * excel中的列的背景颜色.
   * 
   * @return 背景颜色
   */
  public int bgColor() default 44;
}
