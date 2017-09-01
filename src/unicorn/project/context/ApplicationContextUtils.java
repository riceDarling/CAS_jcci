package unicorn.project.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ApplicationContextUtils implements ApplicationContextAware {
  private static ApplicationContext applicationContext;

  public void setApplicationContext(ApplicationContext arg0) throws BeansException {
    applicationContext = arg0;
  }

  /**
   * 获取applicationContext对象.
   * 
   * @return ApplicationContext对象
   */
  public static ApplicationContext getApplicationContext() {
    return applicationContext;
  }

  /**
   * 根据bean的id来查找对象.
   * 
   * @param id bean的id
   * @return 托管在spring中的对象
   */
  public static Object getBeanById(String id) {
    return applicationContext.getBean(id);
  }

  /**
   * 根据bean的class来查找对象.
   * 
   * @param cls bean的class
   * @return 托管在spring中的对象
   */
  public static Object getBeanByClass(Class<?> cls) {
    return applicationContext.getBean(cls);
  }

  /**
   * 根据bean的class来查找所有的对象(包括子类).
   * 
   * @param cls bean的class
   * @return 托管在spring中的对象
   */
  public static Map<String, ?> getBeansByClass(Class<?> cls) {
    return applicationContext.getBeansOfType(cls);
  }
}
