package unicorn.common.arithmetic;

import java.math.BigDecimal;

/**
 * 高精度的取余运算
 * 
 * @version v0.0.1
 */
public class Remainder implements IOperation {
  /**
   * 传入double类型的被余数和余数，进行取余运算.数据先被转换成{@link BigDecimal}类型后，执行取余运算，取得 {@link BigDecimal}
   * 类型的结果，四舍五入截取四位小数后返回String型的结果。
   * 
   * @param target 被余数
   * @param operand 余数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(double target, double operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.remainder(bdOperand).toPlainString();
  }

  /**
   * 传入String类型的被余数和余数，进行取余运算.数据先被转换成{@link BigDecimal}类型后，执行取余运算，取得 {@link BigDecimal}
   * 类型的结果，四舍五入截取四位小数后返回String型的结果。
   * 
   * @param target 被余数
   * @param operand 余数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(String target, String operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.remainder(bdOperand).toPlainString();
  }

  /**
   * 传入String类型的被余数和余数，进行取余运算.数据先被转换成{@link BigDecimal}类型后，执行取余运算，取得 {@link BigDecimal}
   * 类型的结果，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被余数
   * @param operand 余数
   * @param scale 精度控制参数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(String target, String operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.remainder(bdOperand).setScale(scale).toPlainString();
  }

  /**
   * 传入double类型的被余数和余数，进行取余运算.数据先被转换成{@link BigDecimal}类型后，执行取余运算，取得 {@link BigDecimal}
   * 类型的结果，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被余数
   * @param operand 余数
   * @param scale 精度控制参数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(double target, double operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.remainder(bdOperand).setScale(scale).toPlainString();
  }

}
