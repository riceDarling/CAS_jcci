package com.jcci.service.part;

import static unicorn.common.charset.StringUtils.isEmpty;

import com.jcci.pojo.Administrator;
import com.jcci.repository.AdministratorMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorServiceImpl implements AdministratorService {

  

  @Override
  public Administrator readAdministratorByCode(String adminCode) {
    logger.debug("S> 使用登录账号读取管理员信息。Param:adminCode-{}", adminCode);
    if (isEmpty(adminCode)) {
      logger.debug("E> 参数验证失败。Return:null");
      return null;
    } else {
      logger.debug("T> 调用Mapper#readAdministratorByCode(String)方法，读取数据库中对应的记录。");
      Administrator administratorDb = administratorM.readAdministratorByCode(adminCode);
      logger.debug("E> Return:{}", administratorDb);
      return administratorDb;
    }
  }

  
  /*
   * 管理员的mapper对象
   */
  @Autowired
  private AdministratorMapper administratorM;
  
  /*
   * 日志记录对象
   */
  private static Logger logger = LoggerFactory.getLogger(AdministratorService.class);

}
