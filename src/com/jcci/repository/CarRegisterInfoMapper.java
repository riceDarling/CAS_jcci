package com.jcci.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jcci.pojo.CarRegisterInfo;

import unicorn.project.page.Paging;


public interface CarRegisterInfoMapper {

	/**
	 * 查询
	 * */
	public List<CarRegisterInfo> readCarRegisterInfo(@Param("paging") Paging paging);
	
	/**
	 * 查询条数
	 * */
	public Long readCarRegisterInfoCount();
	
	/**
	 * 增加
	 * */
	public int createCarRegisterInfo( @Param("carinfo") CarRegisterInfo carinfo );
	
	/**
	 * 修改
	 * */
	public int updateCarRegisterInfo( @Param("carinfo") CarRegisterInfo carinfo );
	
	/**
	 * 删除
	 * */
	public int deleteCarRegisterInfo( @Param("carId") Long carId );
	
}
