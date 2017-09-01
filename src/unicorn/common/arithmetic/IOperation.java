package unicorn.common.arithmetic;

/**
 * 运算接口
 * 
 * @version v0.0.1
 */
interface IOperation {
  /**
   * 传入两个Double类型的运算参数，直接获取运算结果.
   * 
   * @param target 被操作数
   * @param operand 参数
   * @return String类型的运算结果
   */
  String getResult(double target, double operand);

  /**
   * 传入两个Double类型的运算参数，和一个精度参数，算法使用传入的精度参数对运算结果进行舍入运算.
   * 
   * @param target 被操作数
   * @param operand 参数
   * @param scale 精度
   * @return String类型的运算结果
   */
  String getResult(double target, double operand, int scale);

  /**
   * 传入String类型的运算参数，直接获取运算结果，不限制运算结果的精度.
   * 
   * @param target 被操作数
   * @param operand 参数
   * @return String类型的运算结果
   */
  String getResult(String target, String operand);

  /**
   * 传入String类型的运算参数，和一个精度参数，算法使用传入的精度参数对运算结果进行舍入运算.
   * 
   * @param target 被操作数
   * @param operand 参数
   * @param scale 精度
   * @return String类型的运算结果
   */
  String getResult(String target, String operand, int scale);
}
