package com.jcci.service.kit;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jcci.pojo.CarRegisterInfo;
import com.jcci.repository.CarRegisterInfoMapper;

import unicorn.project.courier.MsgBool;
import unicorn.project.exception.DataInconsistencyException;
import unicorn.project.page.Paging;
import unicorn.project.page.RowSet;

@Service
public class CarRegisterKitServiceImpl implements  CarRegisterKitService{
	

	@Override
	public RowSet<CarRegisterInfo> readCarRegisterInfo(Paging paging) {
		
		logger.debug("S> 分页读车辆登记信息。Param:Page-{}", paging);
	   
	    List<CarRegisterInfo> carList = carRegMapper.readCarRegisterInfo(paging);
	    
	    Long total = carRegMapper.readCarRegisterInfoCount();
	    
	    RowSet<CarRegisterInfo> carRowSet = new  RowSet<CarRegisterInfo>( total , carList  );
	    logger.debug("E> Return:{}", carRowSet);
	    
	    return carRowSet;
	
	}
	
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout = 10,
    isolation = Isolation.REPEATABLE_READ, rollbackFor = RuntimeException.class)
	public MsgBool createCarRegisterInfo(CarRegisterInfo carinfo) {
		
		if (carinfo == null) {
		      MsgBool result = new MsgBool(false, "参数验证失败", "无法为您添加车辆信息，请再次核实输入信息。");
		      logger.debug("E> 参数验证失败。 Return:{}", result);
		      return result;
		}
		int sqlFlag = carRegMapper.createCarRegisterInfo(carinfo);
		if (sqlFlag == 1) {
	        MsgBool result = new MsgBool(true, "添加成功", "已成功为您添加一个新的车辆信息。");
	        logger.debug("E> 新增车辆信息成功。Return:{}", result);
	        return result;
	      } else if (sqlFlag == 0) {
	        MsgBool result = new MsgBool(false, "添加失败", "无法为您添加车辆信息，请再次核实信息。");
	        logger.debug("E> 新增车辆失败。 Return:{}", result);
	        return result;
	      } else {
	        logger.error("@> 在调用Mapper#createAdministrator(Administrator)方法时，本应新增一条记录，但新增了{}条记录。传入的参数为{}",sqlFlag,carinfo);
	        logger.debug("E> 新增了错误数据，抛出异常，回滚数据。 Throw:DataInconsistencyException");
	        throw new DataInconsistencyException("The result of create administrator:" + sqlFlag);
	      }
		
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, timeout = 10,
    isolation = Isolation.REPEATABLE_READ, rollbackFor = RuntimeException.class)
	public MsgBool editCarRegisterInfo( CarRegisterInfo carinfo ) {
		
		if (carinfo == null) {
		      MsgBool result = new MsgBool(false, "参数验证失败", "无法为您添加车辆信息，请再次核实输入信息。");
		      logger.debug("E> 参数验证失败。 Return:{}", result);
		      return result;
		}
		int sqlFlag = carRegMapper.updateCarRegisterInfo(carinfo);
		if (sqlFlag == 1) {
	        MsgBool result = new MsgBool(true, "修改成功", "已成功为您修改一个车辆信息。");
	        logger.debug("E> 修改车辆信息成功。Return:{}", result);
	        return result;
	      } else if (sqlFlag == 0) {
	        MsgBool result = new MsgBool(false, "修改失败", "无法为您修改车辆信息，请再次核实信息。");
	        logger.debug("E> 修改车辆失败。 Return:{}", result);
	        return result;
	      } else {
	        logger.error("@> 在调用Mapper#createAdministrator(Administrator)方法时，本应修改一条记录，但修改了{}条记录。传入的参数为{}",sqlFlag,carinfo);
	        logger.debug("E> 修改了错误数据，抛出异常，回滚数据。 Throw:DataInconsistencyException");
	        throw new DataInconsistencyException("The result of create administrator:" + sqlFlag);
	      }
		
	}

	@Override
	public MsgBool deleteCarRegisterInfo( Long carId ) {
		
	    int ref = carRegMapper.deleteCarRegisterInfo(carId);
	    
	    MsgBool mb = null;
	    if ( ref > 0 ) {
	    	mb = new MsgBool(true, "删除成功");
	    }
	    
	    return mb;
	    
	}
	
	
	/*
	  * 管理员的mapper对象
	  */
	  @Autowired
	  private CarRegisterInfoMapper carRegMapper;

	  
	  /*
	    * 日志记录对象
	    */
	  private static Logger logger = LoggerFactory.getLogger(CarRegisterKitService.class);


	
}
