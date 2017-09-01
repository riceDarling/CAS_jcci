package unicorn.project.page;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页结果集.用来存储分页的数据对象。
 * 
 * @author 周峰
 * @since v1.0.0
 * @version v0.0.1
 * 
 * @param <T> 泛型参数
 */
public class RowSet<T> {
  /*
   * 总记录数
   */
  private Long total;
  /*
   * 分页结果
   */
  private List<T> rows;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("RowSet [");
    sb.append("total=").append(total).append(",");
    sb.append("rows.size=").append(rows.size());
    sb.append("]");
    return sb.toString();
  }

  public RowSet() {
    this.total = null;
    this.rows = new ArrayList<T>();
  }

  public RowSet(Long total, List<T> rows) {
    this.total = total;
    this.rows = rows;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  public List<T> getRows() {
    return rows;
  }

  public void setRows(List<T> rows) {
    this.rows = rows;
  }

  /**
   * 可以判断该对象是否为空.如果total是空或者0，并且rows集合也是空的话，返回true。其余情况返回false。
   * 
   * @return 空是true，非空是false
   */
  @JsonIgnore
  public boolean isEmpty() {
    if ((this.getRows() == null || this.getRows().isEmpty()) && (getTotal() == null || getTotal().intValue() == 0)) {
      return true;
    } else {
      return false;
    }
  }
}
