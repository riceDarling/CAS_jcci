package unicorn.common.excel.style;

import org.apache.poi.ss.usermodel.CellStyle;

/**
 * 单元格样式生成器.所有装饰模式单元格类的基类。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-12-12
 * @see agate.excel.style.ExcelCellStyle
 * @since 1.0.0
 */
public abstract class CellStyleMaker extends ExcelCellStyle {
  public abstract CellStyle getStyle();
}
