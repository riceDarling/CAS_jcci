package unicorn.common.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 基本样式. 一个空的excel样式对象。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public class BaseStyle extends CellStyleMaker {
  private CellStyle cs = null;

  /**
   * 构造方法.
   * 
   * @param wb 文本
   */
  public BaseStyle(Workbook wb) {
    cs = wb.createCellStyle();
    if (cs == null) {
      throw new InstantiationError("无法创建对象，传入数据不合法");
    }
  }

  @Override
  public CellStyle getStyle() {
    if (cs != null) {
      return cs;
    } else {
      throw new InstantiationError("没有创建CellStyle对象。");
    }
  }

}
