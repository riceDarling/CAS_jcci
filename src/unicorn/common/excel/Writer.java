package unicorn.common.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import unicorn.common.excel.annotation.ExcelDisguiser;
import unicorn.common.excel.annotation.ExcelFormat;
import unicorn.common.excel.annotation.ExcelInstruction;
import unicorn.common.excel.pojo.Title;
import unicorn.common.excel.style.AlignStyle;
import unicorn.common.excel.style.BackGroundColorStyle2Body;
import unicorn.common.excel.style.BackGroundColorStyle2Title;
import unicorn.common.excel.style.BaseStyle;
import unicorn.common.excel.style.BorderStyle;
import unicorn.common.excel.style.ExcelCellStyle;
import unicorn.common.excel.style.FontStyle2Body;
import unicorn.common.excel.style.FontStyle2Title;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生成excel的基础方法，是其他生成excel方法的基类
 * 
 * @author 周峰
 * @since v0.0.1
 */
class Writer {
  /**
   * 生成excel.只能生成相同数据的excel。
   * 
   * @param paramsList 数据对象集合
   * @param sheetName sheet名称
   * @param pageSize 每页的列数
   * @return poi的workbook对象
   */
  Workbook newExcel(List<?> paramsList, String sheetName, int pageSize) {
    return null;
  }

  /**
   * 生成excel.可以讲不同的数据生成到不同的sheet中。
   * 
   * @param paramsMap 数据对象集合的集合，key为sheet名称，value为数据对象集合
   * @param pageSize 每页的列数
   * @return poi的workbook对象
   */
  Workbook newExcel(Map<String, List<?>> paramsMap, int pageSize) {
    return null;
  }

  Workbook newExcel(String fileName,List<?> paramsList, String sheetName, int pageSize){
    return null;
  }
  /**
   * 生成Title集合.
   * 
   * @param fields属性集合
   * @return Title的集合
   */
  List<Title> newTitle(List<Field> fields) {
    List<Title> rsList = new ArrayList<Title>();
    for (Field field : fields) {
      ExcelInstruction ep = field.getAnnotation(ExcelInstruction.class);
      if(ep.bgColor()>0){
        rsList.add(new Title(ep.order(), ep.title(),ep.bgColor()));
      }else{
        rsList.add(new Title(ep.order(), ep.title()));
      }
        
      
    }
    return rsList;
  }

  /**
   * 获取总共的sheet页数.
   * 
   * @param paramsList 参数集合
   * @param pageSize 每页显示记录数
   * @return 页数
   */
  int sheetCount(List<?> paramsList, int pageSize) {
    if (pageSize != 0) {
      int sheetCount = paramsList.size() % pageSize == 0 ? paramsList.size() / pageSize
          : paramsList.size() / pageSize + 1;
      return sheetCount;
    } else {
      return 0;
    }
  }

  /**
   * 生成sheet.
   * 
   * @param wb Workbook对象
   * @param sheetName sheet命名
   * @param sign 当前页数
   * @return poi的sheet对象
   */
  Sheet newSheet(Workbook wb, String sheetName, int sign) {
    Sheet sheet = (sheetName != null && !"".equals(sheetName)
        ? wb.createSheet(sheetName + String.valueOf(sign)) : wb.createSheet());
    return sheet;
  }

  /**
   * 写入title.
   * 
   * @param wb Workbook对象
   * @param sheet Sheet对象
   * @param 列头集合
   */
  void writeTitle(Workbook wb, Sheet sheet, List<Title> titleList) {
    Row row = sheet.createRow(0);
    
    
    for (int m = 0; m < titleList.size(); m++) {
      Cell cell = row.createCell(m);
      
      ExcelCellStyle ecs = new BaseStyle(wb);
      BackGroundColorStyle2Title bgcs = new BackGroundColorStyle2Title(ecs);
      bgcs.setBgColor(titleList.get(m).getBgColor());
      AlignStyle as = new AlignStyle(bgcs);
      FontStyle2Title fst = new FontStyle2Title(wb, as);
      BorderStyle bs = new BorderStyle(fst);
      
      cell.setCellStyle(bs.getStyle());
      cell.setCellValue(titleList.get(m).getTitle());
      sheet.setColumnWidth(m, 5000); // 设置每列的宽度
    }
  }

  /**
   * 写入body.
   * 
   * @param wb Workbook对象
   * @param sheet Sheet对象
   * @param paramsList 数据对象的集合
   * @param fields 数据对象的类型
   * @param index 起始的标志位
   * @param pageSize 页数
   * @throws IllegalAccessException 反射异常，抛出此异常时无法创建行或列的值
   * @throws IllegalArgumentException 反射异常，抛出此异常时无法创建行或列的值
   * @throws InstantiationException 反射异常，抛出此异常时无法创建行或列的值
   * @throws SecurityException 反射异常，抛出此异常时无法创建行或列的值
   * @throws NoSuchMethodException 反射异常，抛出此异常时无法创建行或列的值
   * @throws InvocationTargetException 反射异常，抛出此异常时无法创建行或列的值
   */
  void writeBody(Workbook wb, Sheet sheet, List<?> paramsList, List<Field> fields, int index,
      int pageSize) throws IllegalArgumentException, IllegalAccessException, InstantiationException,
      NoSuchMethodException, SecurityException, InvocationTargetException {
    ExcelCellStyle ecs = new BaseStyle(wb);
    AlignStyle as = new AlignStyle(ecs);
    BackGroundColorStyle2Body bgcs = new BackGroundColorStyle2Body(as);
    FontStyle2Body fst = new FontStyle2Body(wb, bgcs);
    BorderStyle bs = new BorderStyle(fst);
    int begin = (index - 1) * pageSize;
    int end = (begin + pageSize) > paramsList.size() ? paramsList.size() : (begin + pageSize);
    int rowCount = 1;
    for (int n = begin; n < end; n++) {
      Object param = paramsList.get(n);
      Row row = sheet.createRow(rowCount);
      rowCount++;
      for (int x = 0; x < fields.size(); x++) {
        Field field = fields.get(x);
        PropertyDescriptor property = null;
        Object paramValue = null;
        try {
          property = new PropertyDescriptor(field.getName(), param.getClass());
          Method readParam = property.getReadMethod();
          paramValue = readParam.invoke(param);
        } catch (IllegalArgumentException | InvocationTargetException | IntrospectionException
            | IllegalAccessException ex) {
          // 在调用get方法获取时，如果发生了任何异常，则直接取属性的值。
          field.setAccessible(true);
          paramValue = field.get(param);
        }
        Cell cell = row.createCell(x);
        cell.setCellStyle(bs.getStyle());
        if (paramValue == null) {
          cell.setCellValue("");
        } else {
          ExcelDisguiser dataDisg = field.getAnnotation(ExcelDisguiser.class);
          if (dataDisg != null) {
            Class<?> dataDisgClass = dataDisg.using();
            if (dataDisgClass != Void.class) {
              if (dataDisg.using().getName().indexOf("$") == -1) {
                Constructor<?> constructor = dataDisgClass.getDeclaredConstructor();
                constructor.setAccessible(true);
                Method method = dataDisgClass.getDeclaredMethod("mask", Object.class);
                method.setAccessible(true);
                paramValue = method.invoke(constructor.newInstance(param), paramValue);
              } else {
                Constructor<?> constructor = dataDisgClass.getDeclaredConstructor(param.getClass());
                constructor.setAccessible(true);
                Method method = dataDisgClass.getDeclaredMethod("mask", Object.class);
                method.setAccessible(true);
                paramValue = method.invoke(constructor.newInstance(param), paramValue);
              }
            }
          }
          ExcelFormat dataFormat = field.getAnnotation(ExcelFormat.class);
          if (dataFormat != null && dataFormat.value() != "") {
            Date value = (Date) paramValue;
            SimpleDateFormat dateFormater = new SimpleDateFormat(dataFormat.value());
            paramValue = dateFormater.format(value);
          }
          if (paramValue instanceof Date) {
            Date value = (Date) paramValue;
            cell.setCellValue(value);
          } else if (paramValue instanceof Integer) {
            String value = String.valueOf(paramValue);
            cell.setCellValue(value);
          } else if (paramValue instanceof Float) {
            String value = String.valueOf(paramValue);
            cell.setCellValue(value);
          } else if (paramValue instanceof Double) {
            String value = String.valueOf(paramValue);
            cell.setCellValue(value);
          } else if (paramValue instanceof Boolean) {
            String value = String.valueOf(paramValue);
            cell.setCellValue(value);
          } else if (paramValue instanceof Long) {
            String value = String.valueOf(paramValue);
            cell.setCellValue(value);
          } else {
            cell.setCellValue((String) paramValue);
          }
        }
      }
    }
  }

  List<Field> eraseField(List<?> paramsList) {
    Class<?> cls = paramsList.get(0).getClass();
    Field[] allFields = cls.getDeclaredFields();
    List<Field> fields = new ArrayList<Field>();
    for (Field field : allFields) {
      if (field.getAnnotation(ExcelInstruction.class) != null) {
        fields.add(field);
      }
    }
    ComparatorField cf = new ComparatorField();
    Collections.sort(fields, cf);
    return fields;
  }

  public class ComparatorField implements Comparator<Field> {
    @Override
    public int compare(Field o1, Field o2) {
      String order1 = o1.getAnnotation(ExcelInstruction.class).order();
      String order2 = o2.getAnnotation(ExcelInstruction.class).order();
      return order1.compareTo(order2);
    }
  }
  
}
