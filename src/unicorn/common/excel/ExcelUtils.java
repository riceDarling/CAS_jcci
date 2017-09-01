package unicorn.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class ExcelUtils {
  /**
   * 删除空行.
   * 
   * @param sheet sheet对象
   * @return 返回删除空行后的sheet对象
   */
  public static Sheet removeEmptyRows(Sheet sheet) {
    for (int rowIndex = 0; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
      Row row = sheet.getRow(rowIndex);
      boolean flag = false;
      if (row != null) {
        short rowSize = row.getLastCellNum();
        for (int i = 0; i < rowSize; i++) {
          Object value = null;
          Cell cell = row.getCell(i);
          if (cell != null) {
            int cellType = cell.getCellType();
            switch (cellType) {
              case Cell.CELL_TYPE_NUMERIC:
                value = cell.getNumericCellValue();
                break;
              case Cell.CELL_TYPE_STRING:
                value = cell.getStringCellValue();
                break;
              case Cell.CELL_TYPE_FORMULA:
                value = cell.getStringCellValue();
                break;
              case Cell.CELL_TYPE_BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
              default:
                value = null;
                break;
            }
            if (value != null) {
              flag = true;
              break;
            }
          } else {
            break;
          }
        }
      }
      if (!flag) {
        sheet.removeRow(row);
      }
    }
    return sheet;
  }
}
