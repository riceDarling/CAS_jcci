package com.jcci.portal;

import static unicorn.project.answer.Status.ERROR;
import static unicorn.project.answer.Status.NORESOURCES;
import static unicorn.project.answer.Status.SUCCESS;

import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jcci.component.WebPath;
import com.jcci.pojo.CarRegisterInfo;
import com.jcci.service.kit.CarRegisterKitService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import unicorn.project.answer.Answer;
import unicorn.project.courier.MsgBool;
import unicorn.project.dict.FileDict;
import unicorn.project.page.Paging;
import unicorn.project.page.RowSet;
import unicorn.project.security.Uid;

/**
 * 车型登记 查询、编辑、删除 功能。
 * 
 * @author 周峰
 * @time 2017年8月8日 上午9:42:13
 * @since v3.0.0
 * @version v0.0.1
 * @Reviews
 * @ReviewsTime
 */
@RestController
@RequestMapping("carregister")
public class CarRegisterPortel {
	
	 /**
	   * 车辆信息查询。<br>
	   * 
	   * @param adminId管理员id用来判断是否登录。
	   * @return 。
	   * @Uri GET carregister/batch/read.do
	   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
	   * 
	   */
	@RequestMapping(value = "batch/read.do", method = RequestMethod.GET)
	public Answer readCarRegisterInfo( @Uid Long adminId,Paging paging) {
		
		logger.debug("S> 使用管理员id判断token是否存在。Param:{}", adminId);
		RowSet<CarRegisterInfo> result = carregService.readCarRegisterInfo(paging);
		if (result.isEmpty()) {
		      Answer answer = Answer.builder().setStatus(NORESOURCES).putNotice("无数据").build();
		      logger.debug("E> Return:{}", answer);
		      return answer;
		 } else {
		      Answer answer = Answer.builder().setStatus(SUCCESS).putContent("carinfos", result).build();
		      logger.debug("E> Return:{}", answer);
		      return answer;
		 }
		
	}
	
	
	/**
	   * 车辆信息新增。<br>
	   * 
	   * @param adminId管理员id用来判断是否登录。
	   * @return 。
	   * @Uri GET carregister/create.do
	   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
	   * 
	   */
	@RequestMapping(value = "create.do", method = RequestMethod.GET)
	public Answer createCarRegisterInfo( @Uid Long adminId, CarRegisterInfo carinfo) {
		
		logger.debug("S> 存入参数。carinfo:{}", carinfo);
		MsgBool result = carregService.createCarRegisterInfo(carinfo);
		if (result.isSuccess()) {
		      Answer answer = Answer.builder().setStatus(SUCCESS).putNotice(result.getNotice())
		          .putCause(result.getCause()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		} else {
		      Answer answer = Answer.builder().setStatus(ERROR).putCause(result.getCause()).putNotice(result.getNotice()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		}
		
	}
	
	
	/**
	   * 车辆信息更新。<br>
	   * 
	   * @param adminId管理员id用来判断是否登录。
	   * @return 。
	   * @Uri GET carregister/update.do
	   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
	   * 
	   */
	@RequestMapping(value = "update.do", method = RequestMethod.GET)
	public Answer updateCarRegisterInfo( @Uid Long adminId, CarRegisterInfo carinfo) {
		
		logger.debug("S> 存入参数。carinfo:{}", carinfo);
		MsgBool result = carregService.editCarRegisterInfo(carinfo);
		if (result.isSuccess()) {
		      Answer answer = Answer.builder().setStatus(SUCCESS).putNotice(result.getNotice())
		          .putCause(result.getCause()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		} else {
		      Answer answer = Answer.builder().setStatus(ERROR).putCause(result.getCause()).putNotice(result.getNotice()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		}
		
	}
	
	
	/**
	   * 车辆信息删除。<br>
	   * 
	   * @param adminId管理员id用来判断是否登录。
	   * @return 。
	   * @Uri GET carregister/delete.do
	   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
	   * 
	   */
	@RequestMapping(value = "delete.do", method = RequestMethod.GET)
	public Answer deleteCarRegisterInfo( @Uid Long adminId, Long carId) {
		
		logger.debug("S> 存入参数。carinfo:{}", carId);
		MsgBool result = carregService.deleteCarRegisterInfo(carId);
		if (result.isSuccess()) {
		      Answer answer = Answer.builder().setStatus(SUCCESS).putNotice(result.getNotice())
		          .putCause(result.getCause()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		} else {
		      Answer answer = Answer.builder().setStatus(ERROR).putCause(result.getCause())
		          .putNotice(result.getNotice()).build();
		      logger.debug("E> Return:{}", result);
		      return answer;
		}
		
	}
	
	
	
	/**
	   * 导入文件.
	   * 
	   * @param file 导入的文件
	   * @return 执行结果
	   * @Uri POST carregister/import.do
	   * @RequestParams 
	   *                file:导入的文件<br>
	   * @Answer 详见 {@linkplain unicorn.project.answer.Answer Answer}。<br>
	   */
	  @RequestMapping(value = "import.do", method = RequestMethod.POST)
	  public Answer importCarRegisterInfo(@Uid Long adminId, String carFileName, Long carId, @RequestParam("file") MultipartFile file, @WebPath String path) throws IOException {
	    logger.debug("S> 导入文件信息。");
	    path += FileDict.uploadPath();
	    String fileName = carFileName;
	    File localFile = new File(path + "车辆ID=" + carId + fileName);
	    file.transferTo(localFile);
	    Answer answer = Answer.builder().setStatus(SUCCESS).putContent("carfilepath", path + "车辆ID=" + carId + fileName).build();
	    logger.debug("E> Return:{}", answer);
	    return answer;
	   
	  }
	
	@Autowired
	private CarRegisterKitService carregService;
	
	/*
	  * 车辆登记portal的日志记录器
	  */
	private static Logger logger = LoggerFactory.getLogger(CarRegisterPortel.class);
	
}
