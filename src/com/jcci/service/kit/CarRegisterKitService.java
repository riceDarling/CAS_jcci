package com.jcci.service.kit;

import com.jcci.pojo.CarRegisterInfo;

import unicorn.project.courier.MsgBool;
import unicorn.project.page.Paging;
import unicorn.project.page.RowSet;

/**
 * 车辆登记业务调用接口。
 * 
 * @author 周峰
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 *
 */
public interface CarRegisterKitService {

	/**
	  * 查询车辆信息.
	  * 
	  * @param 
	  * @return 结果
	  */
	public RowSet<CarRegisterInfo> readCarRegisterInfo( Paging paging);
	
	/**
	  * 编辑车辆信息.
	  * 
	  * @param 
	  * @return 结果
	  */
	public MsgBool editCarRegisterInfo( CarRegisterInfo carinfo );
	
	/**
	  * 编辑车辆信息.
	  * 
	  * @param 
	  * @return 结果
	  */
	public MsgBool createCarRegisterInfo( CarRegisterInfo carinfo );
	
	/**
	  * 删除车辆信息.
	  * 
	  * @param 
	  * @return 结果
	  */
	public MsgBool deleteCarRegisterInfo(Long carId);
	
	
	
}
