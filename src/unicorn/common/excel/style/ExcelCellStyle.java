package unicorn.common.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 装饰着模式所有类的基类.所有类的基类，用于声明接口。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @since 1.0.0
 */
public abstract class ExcelCellStyle {
  protected CellStyle cs;

  public abstract CellStyle getStyle();
}
