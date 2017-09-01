package unicorn.project.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

public interface EmptyCheckRule {
  /**
   * 判断对象内的属性是空.所有属性全是空，则返回true。
   * 
   * @return 判断结果
   */
  @JsonIgnore
  public boolean isEmpty();

  /**
   * 新增时检查.除ID外所有的字段都为空，则返回true。
   * 
   * @return 判断结果
   */
  @JsonIgnore
  public boolean isMeetC();

  /**
   * 修改时检查.ID为空或ID不为空但是其他字段全空，则返回true。
   * 
   * @return 判断结果
   */
  @JsonIgnore
  public boolean isMeetU();

  /**
   * 删除时检查.ID为空则返回true。
   * 
   * @return 判断结果
   */
  @JsonIgnore
  public boolean isMeetD();

  /**
   * 判断除id以外的字段全是空.
   * 
   * @return 判断结果
   */
  @JsonIgnore
  public boolean isEmptyWithOutId();
}
