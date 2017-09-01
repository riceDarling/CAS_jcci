package unicorn.project.answer;

/**
 * 响应结果判断枚举值.“响应结果”是controller根据业务操作的情况对本次请求作出的判断，该枚举共有四种类型：<br>
 * <ul>
 * <li><em><code>SUCCESS</code></em>&nbsp;&nbsp;枚举值SUCCESS的内容为“SUCCESS”，标识本次请求结果是正常。</li>
 * <li><em><code>ERROR</code></em>
 * &nbsp;&nbsp;枚举值ERROR的内容为“ERROR”，标识本次请求结果是错误。注：错误情况是指业务流程错误，并不包含程序异常的情况
 * 。拿登录验证做例，如果传入的登录用户登录名或密码错误，则属于ERROR范畴，如果程序在登录过程中发生了异常，则属于EXCEPTION范畴。</li>
 * <li><em><code>INVALID</code></em> &nbsp;&nbsp;枚举值INVALID的内容为“INVALID”，标识本次请求传入的参数是无效的。</li>
 * <li><em><code>NOTACCESSIBLE</code></em>
 * &nbsp;&nbsp;枚举值NOTACCESSIBLE的内容为“NOTACCESSIBLE”，标识本次请求被禁止访问
 * 。注：在0.0.1版本中，被禁止访问只有一种情况是session中没有存入manager对象。</li>
 * <li><em><code>NORESOURCES</code></em> &nbsp;&nbsp;枚举值NORESOURCES的内容为“NORESOURCES”，标识本次请求的资源不存在。
 * </li>
 * <li><em><code>EXCEPTION</code></em>
 * &nbsp;&nbsp;枚举值EXCEPTION的内容为“EXCEPTION”，标识本次请求结果是异常。本次请求程序中发生了不可恢复异常。</li>
 * </ul>
 * 
 * @author 周峰
 * @since v1.0.0
 * @version 0.0.1
 */
public enum Status {
  /*
   * 请求成功
   */
  SUCCESS,
  /*
   * 请求失败，逻辑错误
   */
  ERROR,
  /*
   * 请求无效，参数不合法
   */
  INVALID,
  /*
   * 请求无效，禁止访问
   */
  NOTACCESSIBLE,
  /*
   * 无资源，请求的资源不存在。
   */
  NORESOURCES,
  /*
   * 请求失败，程序发生异常
   */
  EXCEPTION
}
