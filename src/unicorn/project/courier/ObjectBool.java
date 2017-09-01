package unicorn.project.courier;

public class ObjectBool<T> extends MsgBool {
  private T content;

  /**
   * 构造方法.
   * 
   * @param success 是否成功
   * @param cause 原因
   * @param notice 提示信息
   * @param obj 存入的对象
   */
  public ObjectBool(boolean success, String cause, String notice, T obj) {
    super.setSuccess(success);
    super.setCause(cause);
    super.setNotice(notice);
    this.content = obj;
  }

  /**
   * 构造方法.
   * 
   * @param success 是否成功
   * @param obj 存入的对象
   */
  public ObjectBool(boolean success, T obj) {
    super.setSuccess(success);
    this.content = obj;
  }

  /**
   * 构造方法.
   * 
   * @param success 是否成功
   * @param cause 原因
   * @param notice 提示信息
   */
  public ObjectBool(boolean success, String cause, String notice) {
    super.setSuccess(success);
    super.setCause(cause);
    super.setNotice(notice);
  }


  public T getContent() {
    return content;
  }

  public void setContent(T content) {
    this.content = content;
  }
}
