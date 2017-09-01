package unicorn.common.arithmetic;

import java.math.BigDecimal;

/**
 * 高精度除法运算，推荐除数和被除数小数位较多，或者对结果精度要求较高的情况下优先使用该方法做除法运算。
 * 
 * @version v0.0.1
 */
public class Division implements IOperation {

  /**
   * 传入double类型的除数和被除数，进行除法运算.数据先被转换成{@link BigDecimal}类型后，执行除法运算，取得 {@link BigDecimal}
   * 类型的结果，四舍五入截取四位小数后返回String型的结果。
   * 
   * @param target 被除数
   * @param operand 除数
   * @return String类型的运算结果，默认保留四位小数，整除的情况下返回带".0000"的结果。
   */
  @Override
  public String getResult(double target, double operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.divide(bdOperand, Operation.FOUR, BigDecimal.ROUND_HALF_UP)
        .toPlainString();
  }

  /**
   * 传入String类型的除数和被除数，进行除法运算.数据先被转换成{@link BigDecimal}类型后，执行除法运算，取得 {@link BigDecimal}
   * 类型的结果，四舍五入截取四位小数后返回String型的结果。
   * 
   * @param target 被除数
   * @param operand 除数
   * @return String类型的运算结果，默认保留四位小数，整除的情况下返回带".0000"的结果。
   */
  @Override
  public String getResult(String target, String operand) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.divide(bdOperand, Operation.FOUR, BigDecimal.ROUND_HALF_UP)
        .toPlainString();
  }

  /**
   * 传入String类型的除数，被除数，和int型的精度控制参数，进行除法运算.数据先被转换成{@link BigDecimal} 类型的数据后，执行除法运算，取得
   * {@link BigDecimal} 类型的结果，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被除数
   * @param operand 除数
   * @param scale 精度控制参数
   * @return String类型的运算结果
   */
  @Override
  public String getResult(String target, String operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.divide(bdOperand, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
  }

  /**
   * 传入double类型的除数，被除数，和int型的精度控制参数，进行除法运算.数据先被转换成{@link BigDecimal} 类型的数据后，执行除法运算，取得
   * {@link BigDecimal} 类型的结果，运算结束后将结果根据精度控制参数向上截取后转换成String类型并返回。 理论上无长度限制。数据可占满计算机内存。
   * 
   * @param target 被除数
   * @param operand 除数
   * @param scale 精度控制参数
   * @return String类型的运算结果，
   */
  @Override
  public String getResult(double target, double operand, int scale) {
    BigDecimal bdTarget = new BigDecimal(target);
    BigDecimal bdOperand = new BigDecimal(operand);
    return bdTarget.divide(bdOperand, scale, BigDecimal.ROUND_HALF_UP).toPlainString();
  }
}
