package unicorn.project.answer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnswerBuilder {
  public AnswerBuilder setStatus(Status status) {
    this.status = status;
    return this;
  }

  public AnswerBuilder setVersion(String version) {
    this.headerVersion = version;
    return this;
  }

  public AnswerBuilder setDate(String date) {
    this.headerDate = date;
    return this;
  }

  public AnswerBuilder setForward(String forward) {
    this.headerForward = forward;
    return this;
  }

  public AnswerBuilder setBackward(String backward) {
    this.headerBackward = backward;
    return this;
  }

  public AnswerBuilder putNotice(String notice) {
    this.messageNotice.add(notice);
    return this;
  }

  public AnswerBuilder putAllNotices(List<String> notices) {
    this.messageNotice.addAll(notices);
    return this;
  }

  public AnswerBuilder putCause(String cause) {
    this.messageCause.add(cause);
    return this;
  }

  public AnswerBuilder putAllCauses(List<String> causes) {
    this.messageCause.addAll(causes);
    return this;
  }

  public AnswerBuilder putContent(String key, Object value) {
    this.contentMap.put(key, value);
    return this;
  }

  public AnswerBuilder putAllContents(Map<String, Object> contents) {
    this.contentMap.putAll(contents);
    return this;
  }

  /**
   * 创建Answer对象.
   * 
   * @return 新创建的answer对象
   */
  public Answer build() {
    Answer answer = buildStatus(null);
    answer = buildHeader(answer);
    answer = buildMessage(answer);
    answer = buildContent(answer);
    return answer;
  }

  private Answer buildStatus(Answer answer) {
    if (answer == null) {
      answer = new Answer();
    }
    answer.setStatus(this.status);
    return answer;
  }

  private Answer buildHeader(Answer answer) {
    if (this.headerVersion == null && this.headerDate == null && this.headerForward == null
        && this.headerBackward == null) {
      return answer;
    } else {
      Header header = answer.getHeader();
      if (header == null) {
        header = new Header();
      }
      header.setVersion(this.headerVersion);
      header.setDate(this.headerDate);
      header.setForward(this.headerForward);
      header.setBackward(this.headerBackward);
      answer.setHeader(header);
      return answer;
    }
  }

  private Answer buildMessage(Answer answer) {
    if ((this.messageCause == null || this.messageCause.isEmpty())
        && (this.messageNotice == null || this.messageNotice.isEmpty())) {
      return answer;
    } else {
      Message message = answer.getMessage();
      if (message == null) {
        message = new Message();
      }
      if (messageNotice.size() == 0) {
        message.setNotice(null);
      }
      if (messageNotice.size() == 1) {
        message.setNotice(messageNotice.get(0));
      } else {
        StringBuilder sb = new StringBuilder();
        for (String str : this.messageNotice) {
          sb.append(str).append(" ");
        }
        if (sb.length() > 0) {
          sb.deleteCharAt(sb.length() - 1);
        }
        message.setNotice(sb.toString());
      }
      if (messageCause.size() == 0) {
        message.setCause(null);
      } else if (messageCause.size() == 1) {
        message.setCause(messageCause.get(0));
      } else {
        StringBuilder sb = new StringBuilder();
        for (String str : this.messageCause) {
          sb.append(str).append(" ");
        }
        if (sb.length() > 0) {
          sb.deleteCharAt(sb.length() - 1);
        }
        message.setCause(sb.toString());
      }
      answer.setMessage(message);
      return answer;
    }
  }

  private Answer buildContent(Answer answer) {
    if (this.contentMap.isEmpty()) {
      return answer;
    } else {
      answer.setContent(this.contentMap);
      return answer;
    }
  }

  private Status status;
  private String headerVersion;
  private String headerDate;
  private String headerForward;
  private String headerBackward;
  private List<String> messageNotice = new ArrayList<String>();
  private List<String> messageCause = new ArrayList<String>();
  private Map<String, Object> contentMap = new HashMap<String, Object>();
}
