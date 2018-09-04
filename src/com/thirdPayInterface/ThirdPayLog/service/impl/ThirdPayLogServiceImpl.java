package com.thirdPayInterface.ThirdPayLog.service.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayLog.dao.ThirdPayLogDao;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayLog;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayLogService;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;

/**
 * 
 * @author 
 *
 */
public class ThirdPayLogServiceImpl extends BaseServiceImpl<ThirdPayLog> implements ThirdPayLogService{
	@SuppressWarnings("unused")
	private ThirdPayLogDao dao;
	@Resource
	private BpCustMemberDao custDao;
	
	private static Map configMap = AppUtil.getConfigMap();
	public ThirdPayLogServiceImpl(ThirdPayLogDao dao) {
		super(dao);
		this.dao=dao;
	}

	
	
	
	
	
	/**
	 *在回调方法中根据不同的业务类型进行业务处理 
	 * 
	 */
	/*public void handleByBussinessType(CommonResponse response){
		try{
				if(response.getRequestNo()!=null){
					ThirdPayRecord 	record = dao.getByOrderNo(response.getRequestNo());//通过流水号找到对应的日志信息
					if(record!=null){
						//1=已发起请求，2=交易成功，3=交易失败，4=签名验证失败,5=没有接收到回调参数,6=系统报错
						if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
							record.setStatus(2);
						}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
							record.setStatus(3);
						}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_ISNOTPASSSIGN)){
							record.setStatus(4);
						}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER)){
							record.setStatus(5);
						}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SYSTEMERROR)){
							record.setStatus(6);
						}
						 record.setReturnNum(record.getReturnNum()+1);//回调次数：在原有的次数上加一
						 record.setReturnTime(new Date());//回调系统时间 ：new Date()
						 record.setDataPackage(response.getRequestInfo());//返回数据包
						 record.setCode(response.getResponsecode());//第三方返回状态
						 record.setCodeMsg(response.getResponseMsg());//返回说明
						 if(response.getLoanNo()!=null&&!response.getLoanNo().equals("")){
							 record.setThirdRecordNumber(response.getLoanNo());//第三方流水号
						 }
						 if(response.getThirdPayConfigId()!=null&&!response.getThirdPayConfigId().equals("")){
							 record.setThirdPayFlagId(response.getThirdPayConfigId());//更新第三方账号信息
						 }
						 //最后进行修改 
						 dao.merge(record);
					}
				}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}*/

	@Override
	public ThirdPayLog getByOrderNo(String recordNum) {
		// TODO Auto-generated method stub
		return dao.getByOrderNo(recordNum);
	}

	@Override
	public ThirdPayLog getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return dao.getByUniqueId(uniqueId);
	}
	
}
