package unicorn.common.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import unicorn.common.excel.pojo.Title;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 生成xlsx格式的excel文档（ 2007及以后的格式）。
 * 
 * @author 周峰
 * @version 1.0.0, 2014-5-23
 * @since 1.0.0
 */
public class XlsxWriter extends Writer {
  /**
   * 生成excel2007及其以后格式的Workbook类.
   * 
   * @param paramsList 数据对象集合
   * @param sheetName sheet名字
   * @param pageSize 每页的记录数
   * @return poi的workbook对象
   * @see Writer.#newExcel(java.util.List, java.lang.String, int)
   */
  @Override
  public Workbook newExcel(List<?> paramsList, String sheetName, int pageSize) {
    List<Field> fields = eraseField(paramsList);
    XSSFWorkbook hwRs = new XSSFWorkbook();
    List<Title> listTitle = newTitle(fields);
    int sheetCount = sheetCount(paramsList, pageSize);
    for (int sign = 1; sign <= sheetCount; sign++) {
      XSSFSheet hs = (XSSFSheet) newSheet(hwRs, sheetName, sign);
      writeTitle(hwRs, hs, listTitle);
      try {
        writeBody(hwRs, hs, paramsList, fields, sign, pageSize);
      } catch (IllegalArgumentException | IllegalAccessException | InstantiationException
          | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
        continue;
      }
    }
    return hwRs;
  }

  /**
   * 生成excel2007及其以后格式的Workbook类.可以生成多个sheet在同一个workbook里。
   * 
   * @param paramsMap 数据对象集合，key为sheet的名称value为list集合
   * @param pageSize 每页生成数据的数量
   * @return poi的workbook对象
   * @see Writer#newExcel(java.util.Map, int)
   */
  @Override
  Workbook newExcel(Map<String, List<?>> paramsMap, int pageSize) {
    XSSFWorkbook hwRs = new XSSFWorkbook();
    for (Entry<String, List<?>> entry : paramsMap.entrySet()) {
      List<?> paramsList = entry.getValue();
      String sheetName = entry.getKey();
      List<Field> fields = eraseField(paramsList);
      List<Title> listTitle = newTitle(fields);
      int sheetCount = sheetCount(paramsList, pageSize);
      for (int sign = 1; sign <= sheetCount; sign++) {
        XSSFSheet hs = (XSSFSheet) newSheet(hwRs, sheetName, sign);
        writeTitle(hwRs, hs, listTitle);
        try {
          writeBody(hwRs, hs, paramsList, fields, sign, pageSize);
        } catch (IllegalArgumentException | IllegalAccessException | InstantiationException
            | NoSuchMethodException | SecurityException | InvocationTargetException ex) {
          continue;
        }
      }
    }
    return hwRs;
  }
}
