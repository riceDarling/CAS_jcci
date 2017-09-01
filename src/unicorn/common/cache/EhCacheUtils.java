package unicorn.common.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * ehcache的操作工具类.用来使用ehcache，可以对缓存增删改查操作。
 * 
 * @author 周峰
 * @time 2016年10月11日 上午9:27:55
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
public class EhCacheUtils {
  
  
  /**
   * 创建缓存.
   * 
   * @param cacheName 缓存名
   * @return 创建结果
   */
  public static EhCache cache(String cacheName) {
    
    CacheManager manager = CacheManager.create();
    Cache cache = manager.getCache(cacheName);
    EhCache result = new EhCache(cache);
    return result;
  }

  public static class EhCache {
    private Cache cache;

    public EhCache(Cache cache) {
      this.cache = cache;
    }

    /**
     * 存入键值对.
     * 
     * @param key 键
     * @param value 值
     */
    public void put(String key, String value) {
      Element element = new Element(key, value);
      cache.put(element);
    }

    /**
     * 存入键值对.
     * 
     * @param key 键
     * @param value 值
     */
    public void put(String key, Object value) {
      Element element = new Element(key, value);
      cache.put(element);
    }

    /**
     * 读取缓存中key对应的值.
     * 
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
      Element element = cache.get(key);
      if (element == null) {
        return null;
      } else {
        return element.getObjectValue();
      }
    }

    /**
     * 删除某键值对.
     * 
     * @param key 键
     * @return 删除结果，true删除成功，false删除失败
     */
    public boolean remove(String key) {
      boolean removeFlag = cache.remove(key);
      return removeFlag;
    }
  }
}
