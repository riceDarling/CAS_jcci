package unicorn.common.arithmetic;

import java.math.BigDecimal;

/**
 * 为程序提供高精度的运算.<br>
 * 该类是工具包中为调用者提供高精度运算的策略类，工具包中使用策略模式设计运算处理。
 * 使用的时候先传入一个标签Sign同时根据Sign创建该类的对象，并返还给调用者，调用者调用对象中的getRS方法获得运算的返回值，如果程序中需要不同的计算 则需要创建不同的对象。
 * 
 * @version v0.0.1
 */
public class Operation implements IOperation {
  private IOperation opr = null;

  /**
   * 生成实际运算对象.
   * 
   * @param arithmetic 算数算法
   */
  public Operation(Arithmetic arithmetic) {
    switch (arithmetic) {
      case DIVIDE:
        opr = new Division();
        break;
      case ADD:
        opr = new Addition();
        break;
      case SUBTRACT:
        opr = new Subtraction();
        break;
      case MULTIPY:
        opr = new Multiplication();
        break;
      case REMAINDER:
        opr = new Remainder();
        break;
      default:
        throw new InstantiationError("无法根据sign值判断要实例化的对象。");
    }
  }

  /**
   * 按精度四舍五入截取小数.
   * 
   * @param target 目标数
   * @param scale 精度
   * @return String型的返回结果
   */
  public static String round(double target, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("精度不符合要求。");
    }
    BigDecimal bdTarget = new BigDecimal(target);
    return bdTarget.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
  }

  /**
   * 按精度四舍五入截取小数.
   * 
   * @param target 目标数
   * @param scale 精度
   * @return String型的返回结果
   */
  public static String round(String target, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException("精度不符合要求");
    }
    BigDecimal bdTarget = new BigDecimal(target);
    return bdTarget.setScale(scale, BigDecimal.ROUND_HALF_UP).toPlainString();
  }

  /*
   * (non-Javadoc) <p>Title: getRS</p> <p>Description: </p>
   * 
   * @param target
   * 
   * @param operand
   * 
   * @return
   * 
   * @see com.hzyb.arithmetic.IOperate#getRS(double, double)
   */
  @Override
  public String getResult(double target, double operand) {
    return this.opr.getResult(target, operand);
  }

  /*
   * (non-Javadoc) <p>Title: getRs</p> <p>Description: </p>
   * 
   * @param target
   * 
   * @param operand
   * 
   * @param scale
   * 
   * @return
   * 
   * @see com.hzyb.arithmetic.IOperate#getRs(double, double, int)
   */
  @Override
  public String getResult(double target, double operand, int scale) {
    return this.opr.getResult(target, operand, scale);
  }

  /*
   * (non-Javadoc) <p>Title: getRS</p> <p>Description: </p>
   * 
   * @param target
   * 
   * @param operand
   * 
   * @return
   * 
   * @see com.hzyb.arithmetic.IOperate#getRS(java.lang.String, java.lang.String)
   */
  @Override
  public String getResult(String target, String operand) {
    return this.opr.getResult(target, operand);
  }

  /*
   * (non-Javadoc) <p>Title: getRS</p> <p>Description: </p>
   * 
   * @param target
   * 
   * @param operand
   * 
   * @param scale
   * 
   * @return
   * 
   * @see com.hzyb.arithmetic.IOperate#getRS(java.lang.String, java.lang.String, int)
   */
  @Override
  public String getResult(String target, String operand, int scale) {
    return this.opr.getResult(target, operand, scale);
  }

  /**
   * 运算标识.<br>
   * 该类通过传入的标识判断执行的操作。ADD-加法运算，MULTIPY-乘法运算，DIVIDE-除法运算 ，SUBTRACT-减法运算，REMAINDER -取余运算
   * 
   * @author 周峰
   * @version v0.0.1
   */
  public enum Arithmetic {
    /*
     * 加
     */
    ADD,
    /*
     * 乘
     */
    MULTIPY,
    /*
     * 除
     */
    DIVIDE,
    /*
     * 减
     */
    SUBTRACT,
    /*
     * 取余
     */
    REMAINDER
  }

  /*
   * 保留十位小数，其值为{@value #TEN}
   */
  public static final int TEN = 10;
  /*
   * 保留两位小数，其值为{@value #TWO}
   */
  public static final int TWO = 2;
  /*
   * 默认保留四位小数，其值为{@value #FOUR}
   */
  public static final int FOUR = 4;
}
