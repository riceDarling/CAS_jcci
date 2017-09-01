package unicorn.project.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.Map;

/**
 * 为api系统设计的应答类.该类可作为api系统的应答设计的一部分，主要用来按照某种协议存储应答结果。该类以及配套类使用构造模式设计开发，实例化时需要使用专用的构造器。<br>
 * 参照协议的范文如下：
 * <pre>
 * {
 *   "status":"SUCCESS",
 *   "header":{
 *     "version":"v1.0.0",
 *     "date":"2012-12-05",
 *     "forward":"http://www.xxx.com/1",
 *     "backward":"http://www.xxx.com/2"
 *   },
 *   "message":{
 *     "notice":"登录失败",
 *     "cause":"用户名错误"
 *   },
 *   "content":{
 *     "apple":{
 *       "total":123,"index","list":[
 *         {
 *           "appleId":1,
 *           "appleName":"xxxx"
 *         }
 *       ]
 *     }
 *   }
 * }
 * </pre>
 * status:应答的状态，共包含"SUCCESS"、"ERROR"、"INVALID"、"NOTACCESSIBLE"、"NORESOURCES"、"EXCEPTION"六种，详见
 * {@linkplain unicorn.project.answer.Status Status}。<br>
 * header:应答头，用来存储和系统相关的信息，如系统版本号，数据库当前时间等。详见{@linkplain unicorn.project.answer.Header Header}。<br>
 * message:应答的交互提示，用来存储用于人机交互的提示信息。 详见{@linkplain unicorn.project.answer.Message Message}。<br>
 * content:使用map存储返回的内容，调用者根据实际情况使用。
 * 
 * 
 * @author 周峰
 * @since v1.0.0
 * @version v0.0.1
 */
@JsonInclude(Include.NON_NULL)
public class Answer {
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Answer [");
    sb.append("status=").append(status).append(",");
    if (header != null) {
      sb.append("header [");
      sb.append("version=").append(header.getVersion()).append(",");
      sb.append("date=").append(header.getDate()).append(",");
      sb.append("forward=").append(header.getForward()).append(",");
      sb.append("backward=").append(header.getBackward()).append(",");
      sb.append("]").append(",");
    }
    if (message != null) {
      sb.append("message [");
      // sb.append("code=").append(message.getCode()).append(",");
      sb.append("notice=").append(message.getNotice()).append(",");
      sb.append("cause=").append(message.getCause()).append(",");
      sb.append("]").append(",");
    }
    if (content != null) {
      sb.append("content=").append(content.toString());
    }
    sb.append("]");
    return sb.toString();
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public Header getHeader() {
    return header;
  }

  public void setHeader(Header header) {
    this.header = header;
  }

  public Message getMessage() {
    return message;
  }

  public void setMessage(Message message) {
    this.message = message;
  }

  public Map<String, Object> getContent() {
    return content;
  }

  public void setContent(Map<String, Object> content) {
    this.content = content;
  }

  public static AnswerBuilder builder() {
    return new AnswerBuilder();
  }

  /**
   * 设置数据库当前时间.
   * 
   * @param date 数据库的当前时间
   */
  public void setDate(String date) {
    if (header == null) {
      Header header = new Header();
      header.setDate(date);
    } else {
      header.setDate(date);
    }
  }

  /**
   * 设置软件版本号.
   * 
   * @param version 软件版本号
   */
  public void setVersion(String version) {
    if (header == null) {
      Header header = new Header();
      header.setVersion(version);
    } else {
      header.setVersion(version);
    }
  }

  private Status status;
  private Header header;
  private Message message;
  private Map<String, Object> content;
}
