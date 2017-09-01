package unicorn.common.arithmetic;

import java.math.BigDecimal;

/**
 * 高精度加法运算，推荐Double和Long类型的带小数数据使用该类中的方法计算结果
 * 
 * @version v0.0.1
 */
public class Addition implements IOperation {

  /**
   * 传入两个double类型的运算数据，返回String类型的运算结果.使用该方法做加法运算， 被加数和加数先被转换成 {@link BigDecimal}类型后再执行加法运算
   * ，运算结束后将结果转换成String类型并返回。理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被加数
   * @param operand 加数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(double target, double operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.add(bdOperand).toPlainString();
  }

  /**
   * 传入两个String类型的运算数据，返回String类型的运算结果.使用该方法做加法运算， 被加数和加数先被转换成 {@link BigDecimal}
   * 类型后再执行加法运算，运算结束后将结果转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被加数
   * @param operand 加数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(String target, String operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.add(bdOperand).toPlainString();
  }

  /**
   * 传入两个String类型的运算数据，和一个int型的精度控制参数，返回String类型的运算结果.使用该方法做加法运算， 被加数和加数先被转换成 {@link BigDecimal}
   * 类型后再执行加法运算，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被加数
   * @param operand 加数
   * @param scale 精度控制参数
   */
  @Override
  public String getResult(String target, String operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.add(bdOperand).setScale(scale, BigDecimal.ROUND_HALF_UP)
        .toPlainString();
  }

  /**
   * 传入两个double类型的运算数据，和一个int型的精度控制参数，返回String类型的运算结果.使用该方法做加法运算， 被加数和加数先被转换成 {@link BigDecimal}
   * 类型后再执行加法运算，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被加数
   * @param operand 加数
   * @param scale 精度控制参数
   */
  @Override
  public String getResult(double target, double operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.add(bdOperand).setScale(scale, BigDecimal.ROUND_HALF_UP)
        .toPlainString();
  }
}
