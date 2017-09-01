package unicorn.project.log;

import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

public class BoldCompositeConverter<E> extends ForegroundCompositeConverterBase<E> {

  private static final String BOLD_ONLY = "1";

  @Override
  protected String getForegroundColorCode(E event) {
    return BOLD_ONLY;
  }
}
