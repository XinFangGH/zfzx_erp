package com.thirdPayInterface.ThirdPayLog.service.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;

import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayLog.dao.ThirdPayRecordDao;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.dao.GenericDao;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;

/**
 * 
 * @author 
 *
 */
public class ThirdPayRecordServiceImpl extends BaseServiceImpl<ThirdPayRecord> implements ThirdPayRecordService{
	@SuppressWarnings("unused")
	private ThirdPayRecordDao dao;
	private static Map configMap = AppUtil.getConfigMap();
	public ThirdPayRecordServiceImpl(ThirdPayRecordDao dao) {
		super((GenericDao) dao);
		this.dao=dao;
	}

	/**
	 * 进行日志插入操作
	 * 
	 */
	public void insertOrUpdateLog(CommonRequst request, CommonResponse response) {
		//第一次发送请求的处理方法
		if(request!=null&&response!=null){
			 //根据唯一业务主键判断数据库里是否有该条日志   如果有更新 没有则新建  
			 //如果有 则判断该条记录的状态 如果成功则不处理，失败则可以再次调用
	         //如果数据库中没有这条记录则进行新增保存
				insertInfo(request,response);
		}else{//进入回调方法处理数据
			    handleByBussinessType(response);
		}
	}
	
	
	
	
	
	/**
	 *在回调方法中根据不同的业务类型进行业务处理 
	 * 
	 */
	public void handleByBussinessType(CommonResponse response){
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
						 if(response.getLoanNo()!=null){
							 record.setThirdRecordNumber(response.getLoanNo());
						 }
						 dao.flush();
						 //最后进行修改 
						 dao.merge(record);
					}
				}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("222222222222222222");
		}
		
	}
		
	/**
	 *新增加数据 
	 * 
	 */
	public void insertInfo(CommonRequst request, CommonResponse response){
		//得到第三方标识
		String thirdPayConfig = configMap.get("thirdPayConfig").toString();
		ThirdPayRecord	record = new ThirdPayRecord();
		//通过第三方标识得到当前用户，为空默认是平台商户
		List list = dao.getBpCustmember(request.getThirdPayConfigId());
		//BpCustMember member = custDao.getMemberByFlagId(request.getThirdPayConfigId());
		//唯一主键（必填）
		if(request.getUniqueId()!=null&&!request.getUniqueId().equals("")){
			record.setUniqueId(request.getUniqueId());
		}else{
			record.setUniqueId(request.getBussinessType()+request.getRequsetNo());
		}
		record.setReturnNum(0); //回调次数 默认为 0 
		//判断如果是充值的业务则更改状态为1=已发起请求，2=交易成功，3=交易失败，4=签名验证失败,5=没有接收到回调参数,6=系统报错
		if((response.getResponsecode().equals(CommonResponse.RESPONSECODE_APPLAY))){
			record.setStatus(1);
		}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)){
			record.setStatus(2);
			record.setReturnNum(1); //直连接口返回成功默认
			record.setReturnTime(new Date());//直连接口返回成功回调时间
		}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_FAILD)){
			record.setStatus(3);
		}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_ISNOTPASSSIGN)){
			record.setStatus(4);
		}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER)){
			record.setStatus(5);
		}else if(response.getResponsecode().equals(CommonResponse.RESPONSECODE_SYSTEMERROR)){
			record.setStatus(6);
		}
		if(list.size()>0){
			Object[] obj = (Object[]) list.get(0);
			//用户Id
			record.setUserId(Long.valueOf(obj[0].toString()));
			//用户登录名
			record.setLoginName(obj[1].toString());
		}else{
			if(request.getCustMemberId()!=null&&!request.getCustMemberId().equals("")){
				record.setUserId(Long.valueOf(request.getCustMemberId()));
			}
			if(request.getLoginname()!=null&&!request.getLoginname().equals("")){
				record.setLoginName(request.getLoginname());
			}
		}
		//第三方标识
		if(thirdPayConfig!=null&&!thirdPayConfig.equals("")){
			record.setThirdPayConfig(thirdPayConfig);
		}else{
			record.setThirdPayConfig("notThirdPay");
		}
		//第三方账号。如果是平台，就保存平台标识
		if (request.getThirdPayConfigId() != null&& !"".equals(request.getThirdPayConfigId())) {
			record.setThirdPayFlagId(request.getThirdPayConfigId());
		}else if(response.getThirdPayConfigId() != null&& !"".equals(response.getThirdPayConfigId())) {
			record.setThirdPayFlagId(response.getThirdPayConfigId());
		}
 
		if (request.getTransferName() != null&& !"".equals(request.getTransferName())) {// interfaceType 接口类型 对应接口用途
			record.setInterfaceName(request.getTransferName());
		}else{
			record.setInterfaceName(request.getBussinessType());//null 就和 业务类型保持一致
		}
		
		if (request.getBussinessType() != null
				&& !"".equals(request.getBussinessType())) {// interfaceName
															// 接口名称 对应业务类型
			record.setInterfaceType(request.getBussinessType());

		}
		if (request.getAmount() != null && !"".equals(request.getAmount())) {// dealMoney// //交易金额// request.getAmount() +request.getFee()
			if (request.getFee() != null && !"".equals(request.getFee())
					&& !request.getBussinessType().equals(ThirdPayConstants.BT_MMPLATFORM)
					&& !request.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)) {// 有手续费
				record.setDealMoney(request.getAmount().add(request.getFee()));
			} else {// 没有手续费
				record.setDealMoney(request.getAmount());
			}
		}
		record.setRequestTime(new Date());// //requestTime //请求接口时间
		record.setRequestNum(1);//请求接口次数
		

		if (request.getRequsetNo() != null
				&& !"".equals(request.getRequsetNo())) {// recordNumber 平台流水号
			record.setRecordNumber(request.getRequsetNo());
		}

		if (response.getResponsecode() != null
				&& !"".equals(response.getResponsecode())) {// code //第三方返回码
			record.setCode(response.getResponsecode());
		}

		if (response.getResponseMsg() != null
				&& !"".equals(response.getResponseMsg())) {// codeMsg//第三方返回状态说明
			record.setCodeMsg(response.getResponseMsg());
		}
		
		//如果是查询类的不用保存此字段
		if(!request.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)
			&&!request.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)
			&&!request.getBussinessType().equals(ThirdPayConstants.BT_QUERYACCOUNT)){
			if(response.getRequestInfo()!=null&&!"".equals(response.getRequestInfo())){////第三方返回数据包
				 record.setDataPackage(response.getRequestInfo());
			}
		}
		if (request.getBidId() != null && !"".equals(request.getBidId())) {// bidId标的Id
			record.setBidId(Long.valueOf(request.getBidId()));//Long.valueOf(request.getBidId())
		}

		if (request.getBidType() != null && !"".equals(request.getBidType())) {// bidType标的类型
			record.setBidType(request.getBidType());
		}
		
		if (request.getIntentDate() != null
				&& !"".equals(request.getIntentDate())) {// intentDate //计划还款日
			record.setIntentDate(request.getIntentDate());
		}
		if (request.getOtherThirdpayFlagId() != null
				&& !"".equals(request.getOtherThirdpayFlagId())) {// otherThirdpayFlagId被交易人第三方账号
			record.setOtherThirdpayFlagId(request.getOtherThirdpayFlagId());
		}

		if (request.getOtherUserId() != null
				&& !"".equals(request.getOtherUserId())) {// otherUserId被交易人平台用户Id，来源于bp_cust_member主键
			record.setOtherUserId(request.getOtherUserId());
		}

		if (request.getOtherLoginName() != null
				&& !"".equals(request.getOtherLoginName())) {// otherLoginName被交易人平台用户登录名，来源于bp_cust_member loginname字段
			record.setOtherLoginName(request.getOtherLoginName());
		}
		//提前还款对应的id
		if(request.getSlEarlyRepaymentId()!=null&&!"".equals(request.getSlEarlyRepaymentId())){
			record.setSlEarlyRepaymentId(request.getSlEarlyRepaymentId());
		}
		if (request.getRemark1() != null && !"".equals(request.getRemark1())) {// remark
			record.setRemark1(request.getRemark1());
		}
		if (request.getRemark2() != null && !"".equals(request.getRemark2())) {// remark1
			record.setRemark2(request.getRemark2());
		}
		if (request.getRemark3() != null && !"".equals(request.getRemark3())) {// remark2
			record.setRemark3(request.getRemark3());
		}
		 if(response.getLoanNo()!=null&&!response.getLoanNo().equals("")){
			 record.setThirdRecordNumber(response.getLoanNo());
		 }
		dao.save(record);
	}
	
	
	public void test(){
		
		
	}

	@Override
	public ThirdPayRecord getByOrderNo(String recordNum) {
		// TODO Auto-generated method stub
		return dao.getByOrderNo(recordNum);
	}

	@Override
	public ThirdPayRecord getByUniqueId(String uniqueId) {
		// TODO Auto-generated method stub
		return dao.getByUniqueId(uniqueId);
	}

	@Override
	public List getBpCustmember(String thirdPayConfigId) {
		// TODO Auto-generated method stub
		return dao.getBpCustmember(thirdPayConfigId);
	}
	
	@Override
	public List<ThirdPayRecord> getByIdAndType(String thirdPayConfigId, String interfaceType) {
		return dao.getByIdAndType(thirdPayConfigId,interfaceType);
	}
	
}
