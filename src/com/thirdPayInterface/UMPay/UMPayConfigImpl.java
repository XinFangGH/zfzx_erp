package com.thirdPayInterface.UMPay;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.thirdPayInteface.UMPay.UMPay;
import com.thirdPayInterface.CommonRecord;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayTypeInterface;
import com.thirdPayInterface.MoneyMorePay.MoneyMorePayInterfaceUtil;
import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.thirdPayInterface.ThirdPayLog.service.ThirdPayRecordService;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;

public class UMPayConfigImpl implements ThirdPayTypeInterface {
	protected static Log logger=LogFactory.getLog(UMPayInterfaceUtil.class);
	public static volatile Map<String,String> requestValue=new ConcurrentHashMap<String,String>();
	static  ThirdPayRecordService thirdPayRecordService = (ThirdPayRecordService) AppUtil.getBean("thirdPayRecordService");
	/**
	 * 联动优势
	 */
	@Override
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse=new CommonResponse();
	    try{
	    	if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREGISTER)){//个人开通第三方
	    		//commonResponse=UMPayInterfaceUtil.regiest(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_EREGISTER)){//企业开通第三方
	    		//commonResponse=UMPayInterfaceUtil.register(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RECHAGE)){//充值
	    	    //commonResponse=UMPayInterfaceUtil.rechargeMoney(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAW)){//取现
	    		//commonResponse=UMPayInterfaceUtil.withdrawsMoney(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)){//流标放款
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BINDCARD)){//绑定银行卡
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELCARD)){//取消绑定银行卡
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEPHONE)){//修改手机号
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BID)){//散标投标
	    		//commonResponse=UMPayInterfaceUtil.transfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)){//散标放款
	    		commonRequst.setBidType(CommonRequst.HRY_BID);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE)){//平台收费
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_REPAYMENT)){//散标自助还款
	    		//commonResponse=UMPayInterfaceUtil.repayment(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY)){//平台帮助借款人还款
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BEFOREPAY)){//散标提前还款
	    		//commonResponse=UMPayInterfaceUtil.repayment(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//散标自动投标
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_OPENBIDAUTH)){//自动投标授权
	    		//commonResponse=UMPayInterfaceUtil.authorize(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_OPENPAYAUTH)){//自动还款授权
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RETURNMONEY)){//返款接口
	    		commonRequst.setBidType(CommonRequst.HRY_BID);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CLOSEBIDAUTH)){//关闭自动投标授权
	    		//commonResponse=UMPayInterfaceUtil.authorize(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CLOSEPAYAUTH)){//关闭自动还款授权
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_REPLACEMONEY)){//代偿还款申请
	    		//commonResponse=UMPayInterfaceUtil.transfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CHECKREPLACE)){//代偿还款审核
	    		//commonResponse=UMPayInterfaceUtil.transferaudit(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMUSER)){//理财计划购买(收款账户为用户)
	    		//commonResponse=UMPayInterfaceUtil.transferPlan(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMPLATFORM)){//理财计划购买(收款账户为平台)
	    		//commonResponse=UMPayInterfaceUtil.transferPlan(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)){//理财计划起息(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANPLATF)){//理财计划起息(收款账户为平台)
	    		//commonResponse=UMPayInterfaceUtil.transferaudit(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEUSER)){//理财计划派息(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEPLATF)){//理财计划派息(收款账户为平台)
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELUSER)){//理财计划取消购买(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELPLATF)){//理财计划取消购买(收款账户为平台)
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
	    		commonResponse=UMPayInterfaceUtil.autoAuthorization(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)){//理财计划提前退出(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HANGDEAL)){//挂牌交易
	    		commonResponse=UMPayInterfaceUtil.transferUserTo(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BUYDEAL)){//购买债权
	    		//commonResponse=UMPayInterfaceUtil.buySaleTransfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELDEAL)){//取消挂牌
	    		commonResponse=UMPayInterfaceUtil.normalNOPassWordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRUESERVICE)){//挂牌交易服务费将预收转为实收
	    		
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_FALSESERVICE)){//挂牌交易服务费将预收转实收后退费
	    		commonResponse=UMPayInterfaceUtil.normalNOPassWordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HRBIN)){//互融宝转入申请 
	    		//commonResponse=UMPayInterfaceUtil.transfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CHECKHRBIN)){//互融宝转入审核
	    		//commonResponse=UMPayInterfaceUtil.transferaudit(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HRBOUT)){//互融宝转出
	    		//commonResponse=UMPayInterfaceUtil.giveRed(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_SEDRED)){//派发红包
	    		commonResponse=UMPayInterfaceUtil.normalNOPassWordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_COUPONAWARD)){//优惠券派发奖励
	    		commonResponse=UMPayInterfaceUtil.normalNOPassWordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREPAREPAY)){//平台准备金代偿还款
	    		commonResponse=UMPayInterfaceUtil.transferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)){//平台会员交易查询(充值、取现、还款、放款、转账)
	    		commonRequst.setBidType(commonRequst.HRY_BID);
	    		commonResponse=UMPayInterfaceUtil.accountQulideQuery(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_GETPLATFORM)){//平台商户查询   
	    		commonResponse=UMPayInterfaceUtil.queryPlatformInfo(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QPLATFORM)){//平台商户流水查询   
	    		commonResponse=UMPayInterfaceUtil.platformQulideQuery(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BALANCEQUERY)){//注册用户查询
	    		commonResponse=UMPayInterfaceUtil.queryPlatformInfo(commonRequst);
	    	}/*else if(){
	    		commonResponse=UMPayInterfaceUtil.accountQulideQuery(commonRequst);
	    	}*/else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYACCOUNT)){//业务对账
	    		QueryFilter filter  = new QueryFilter();
	    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    		Long diff= commonRequst.getTransactionTime().getTime();//前一天
	    		Long diff1 = commonRequst.getTransactionTime().getTime()+86400*1000;//后一天
	    		Date requestDate = new Date();
	    		requestDate.setTime(diff);
	    		sdf.format(requestDate);
	    		Date requestDate1 = new Date();
	    		requestDate1.setTime(diff1);
	    		sdf.format(requestDate1);
	    		filter.addFilter("Q_requestTime_D_GE", sdf.format(requestDate));
	    		filter.addFilter("Q_requestTime_D_LT", sdf.format(requestDate1));
	    		filter.addFilter("Q_code_S_EQ", "responsecode_success");
	    		filter.addFilter("Q_thirdPayFlagId_NOTNULL", null);
	    		List<ThirdPayRecord> record = thirdPayRecordService.getAll(filter);
	    		List<CommonRecord> list = new ArrayList<CommonRecord>();
	    		if(record.size()>0){
	    			int i = 1;
	    			for(ThirdPayRecord record1 : record){
	    				if(record1.getRecordNumber()!=null&&!"".equals(record1.getRecordNumber())&&record1.getThirdPayFlagId()!=null&&!"".equals(record1.getThirdPayFlagId())){
	    					commonRequst.setBidType(commonRequst.HRY_BID);
	    					if(record1.getInterfaceType().equals(ThirdPayConstants.BT_RECHAGE)){//充值
	    						commonRequst.setBussinessType("RECHARGE_RECORD");
	    						commonRequst.setRemark1("01");
	    					}else if(record1.getInterfaceType().equals(ThirdPayConstants.BT_WITHDRAW)){//取现
	    						commonRequst.setBussinessType("WITHDRAW_RECORD");
	    						commonRequst.setRemark1("02");
	    						//标的类查询接口
	    					}else if(record1.getInterfaceType().equals(ThirdPayConstants.BT_BID)||record1.getInterfaceType().equals(ThirdPayConstants.BT_LOAN)||
	    							record1.getInterfaceType().equals(ThirdPayConstants.BT_REPAYMENT)||record1.getInterfaceType().equals(ThirdPayConstants.BT_BEFOREPAY)//BT_BEFOREPAY	
	    							|| record1.getInterfaceType().equals(ThirdPayConstants.BT_RETURNMONEY) //BT_RETURNMONEY
	    					){
	    						commonRequst.setBussinessType("REPAYMENT_RECORD");
	    						commonRequst.setRemark1("03");
	    						//理财计划查询类接口 
	    					}else if(record1.getInterfaceType().equals(ThirdPayConstants.BT_MMUSER)||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMLOANUSER)
	    							||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMGIVEUSER)||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMCANCELUSER)
	    							||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMAUTH)||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)
	    							||record1.getInterfaceType().equals(ThirdPayConstants.BT_MMBACKMONEY)){
	    						commonRequst.setBidType(commonRequst.HRY_PLANBUY);
	    						commonRequst.setBussinessType("REPAYMENT_RECORD");
	    						commonRequst.setRemark1("03");
	    					}/*else{//普通转账查询
	    						commonRequst.setBussinessType(ThirdPayConstants.BT_TRANSFER_QUERY);
	    						commonRequst.setRemark1("04");
	    					}*/
	    					commonRequst.setQueryRequsetNo(record1.getRecordNumber().toString());
	    					if(record1.getThirdPayFlagId()!=null){
	    						commonRequst.setThirdPayConfigId(record1.getThirdPayFlagId());
	    					}
	    					commonResponse= UMPayInterfaceUtil.accountQulideQuery(commonRequst);
	    					if(commonResponse.getResponsecode().equals(CommonResponse.RESPONSECODE_SUCCESS)&&commonResponse.getRecordList()!=null){
	    						for(CommonRecord record2 :commonResponse.getRecordList()){
	    							record2.setRequestNo(record1.getRecordNumber());
	    							record2.setPlatformNo(record1.getLoginName());
	    							record2.setBizType(record1.getInterfaceName());
	    							record2.setFee("0.00");
	    							record2.setBalance("0.00");
	    							System.out.println("进入方法的次数"+i);
	    							i++;
	    							list.add(record2);
	    						}
	    					}
	    				}
	    			}
 	    		}
	    		commonResponse.setRecordList(list);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDFLOWQUERY)){
	    		commonRequst.setBidType(commonRequst.HRY_BID);
	    		commonResponse=UMPayInterfaceUtil.accountQulideQuery(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CREATEBID)){//建立标的信息
	    		commonResponse=UMPayInterfaceUtil.createBidAccount(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEBID)){//更改标的信息
	    		commonResponse=UMPayInterfaceUtil.updateBidAccount(commonRequst);
	    	}/*else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RETURNMONEY)){//散标返款
	    		commonResponse=UMPayInterfaceUtil.updateBidAccount(commonRequst);
	    	}*/else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMBACKMONEY)){//理财计划返款
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BACKDEAL)){//债权交易返款  
	    		commonRequst.setBidType(CommonRequst.HRY_BID);
	    		commonResponse=UMPayInterfaceUtil.NoPasswordTransferInterface(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATFORMPAY)){//平台商户充值
	    		commonResponse=UMPayInterfaceUtil.platformRecharge(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_RECHARGEFILE)){//充值对账文件
	    		commonResponse=UMPayInterfaceUtil.downAccountFile_recharge(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAWFILE)){//提现对账文件
	    		commonResponse=UMPayInterfaceUtil.downAccountFile_withdraw(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDBALANCEFILE)){//标的对账文件
	    		commonResponse=UMPayInterfaceUtil.downAccountFile(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BIDTRANSFERFILE)){//标的转账对账文件
	    		commonResponse=UMPayInterfaceUtil.downAccountFile_bidTransfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRANSFERFILE)){//转账对账文件
	    		commonResponse=UMPayInterfaceUtil.downAccountFile_Transfer(commonRequst);
	    	}else {
	    		System.out.println("业务类型="+commonRequst.getBussinessType());
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTBUSINESS); 
		    	commonResponse.setResponseMsg("没有该业务类型");
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	commonResponse.setResponseMsg("系统报错");
	    }
		 
		return commonResponse;
	}
	
	/**
	 * 解析回调
	 */
	@Override
	public CommonResponse businessReturn(Map map,HttpServletRequest request) {
		ThirdPayRecordService thirdPayRecordService = (ThirdPayRecordService) AppUtil.getBean("thirdPayRecordService");
		CommonResponse commonResponse = new CommonResponse();
    	if(!map.isEmpty()){
    		commonResponse.setRequestInfo(map.toString());//保存datapackage
    		Boolean isSign=false;
    		//联动优势验证签名方法
    		logger.info("联动优势页面回调函数通知签名验证结果isSign="+isSign);
    		if(true){
    			String service=request.getParameter("service");
    			if(service!=null&&!"".equals(service)){//其他业务处理方法
    				if(service.equals(UMPay.NOTIFY_RECHARGE)){//充值交易回调通知
    					logger.info("联动优势服务器端回调函数通知调用充值业务操作方法开始");
    					if(!requestValue.containsKey(map.get("order_id"))){
    						requestValue.put(map.get("order_id").toString(),map.get("order_id").toString());
    					}
    					synchronized(requestValue.get(map.get("order_id"))){
    			          //TODO 添加处理业务方法
    						commonResponse.setRequestNo(map.get("order_id").toString());
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("充值成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("充值失败");
    						}
    						requestValue.remove(map.get("order_id"));
    					}
    				}else if(service.equals(UMPay.NOTIFY_WITHDRAW)){//取现回调通知（第一次申请结果通知）
    					logger.info("联动优势服务器端回调函数通知调用取现业务操作方法开始");
    					// TODO Auto-generated method stub
    					if(!requestValue.containsKey(map.get("order_id"))){
    						requestValue.put(map.get("order_id").toString(),map.get("order_id").toString());
    					}
    					synchronized(requestValue.get(map.get("order_id"))){
    			          //TODO 添加处理业务方法
    						commonResponse.setRequestNo(map.get("order_id").toString());
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("取现申请成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("取现失败");
    						}
    						requestValue.remove(map.get("order_id"));
    					}
    				}else if(service.equals(UMPay.NOTIFY_BINDCARD)){//绑卡回调通知||更换银行卡
    					logger.info("联动优势服务器端回调函数通知调用绑卡业务操作方法开始");
    					if(!requestValue.containsKey(map.get("order_id"))){
    						requestValue.put(map.get("order_id").toString(),map.get("order_id").toString());
    					}
    					synchronized(requestValue.get(map.get("order_id"))){
    			          //TODO 添加处理业务方法
    						commonResponse.setRequestNo(map.get("order_id").toString());
    						commonResponse.setResponseMsg(map.get("ret_msg").toString());
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
								commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
								commonResponse.setResponseMsg("绑卡成功");
    							
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("绑卡失败");
    						}
    						requestValue.remove(map.get("order_id"));
    					}
    				}else if(service.equals(UMPay.NOTIFY_BINDAGERRMENT)){//开通无密交易
    					//无密交易授权回调通知
    					logger.info("联动优势服务器端回调函数通知页面回调函数通知调用无密交易授权业务操作方法开始");
    					if(!requestValue.containsKey(map.get("user_bind_agreement_list"))){
    						requestValue.put(map.get("user_bind_agreement_list")+map.get("user_id").toString(),map.get("user_bind_agreement_list")+map.get("user_id").toString());
    					}
    					commonResponse.setThirdPayConfigId(map.get("user_id").toString());
    					List<ThirdPayRecord> recordList = thirdPayRecordService.getByIdAndType(map.get("user_id").toString(), ThirdPayConstants.BT_MMAUTH);
    					if(recordList != null && recordList.size()>0){
    						ThirdPayRecord tr = recordList.get(0);
    						commonResponse.setRequestNo(tr.getRecordNumber());
    					}
    					synchronized(requestValue.get(map.get("user_bind_agreement_list")+map.get("user_id").toString())){
    						//String bind_agreement=map.get("user_bind_agreement_list").toString().trim();
    						//授权用户的第三方账号
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("无密交易授权成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("无密交易授权失败，失败原因："+map.get("ret_msg"));
    						}
    						requestValue.remove(map.get("user_bind_agreement_list")+map.get("user_id").toString());
    					}
    				
    				}else if(service.equals(UMPay.NOTIFY_CANCELAGERRMENT)){//关闭无密交易
    					//关闭无密交易授权回调通知
    					logger.info("联动优势服务器端回调函数通知页面回调函数通知调用关闭无密交易授权业务操作方法开始");
    					if(!requestValue.containsKey(map.get("user_unbind_agreement_list"))){
    						requestValue.put(map.get("user_unbind_agreement_list")+map.get("user_id").toString(),map.get("user_unbind_agreement_list")+map.get("user_id").toString());
    					}
    					commonResponse.setThirdPayConfigId(map.get("user_id").toString());
    					List<ThirdPayRecord> recordList = thirdPayRecordService.getByIdAndType(map.get("user_id").toString(), ThirdPayConstants.BT_CLOSEBIDAUTH);
    					if(recordList != null && recordList.size()>0){
    						ThirdPayRecord tr = recordList.get(0);
    						commonResponse.setRequestNo(tr.getRecordNumber());
    					}
    					synchronized(requestValue.get(map.get("user_unbind_agreement_list")+map.get("user_id").toString())){
    						//String bind_agreement=map.get("user_bind_agreement_list").toString().trim();
    						//授权用户的第三方账号
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("关闭无密交易授权成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("关闭无密交易授权失败，失败原因："+map.get("ret_msg"));
    						}
    						requestValue.remove(map.get("user_unbind_agreement_list")+map.get("user_id").toString());
    					}
    				
    				}else if(service.equals(UMPay.NOTIFY_BIDTRANSFER)){//标类的转账回调通知
    					logger.info("联动优势服务器端回调函数通知调用标类转账操作方法开始");
    					if(!requestValue.containsKey(map.get("order_id"))){
    						requestValue.put(map.get("order_id").toString(),map.get("order_id").toString());
    					}
    					synchronized(requestValue.get(map.get("order_id"))){
    			          //TODO 添加处理业务方法
    						commonResponse.setRequestNo(map.get("order_id").toString());
    						//commonResponse.setBussinessType(service);
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("标的账户转账成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("标的账户转账失败");
    						}
    						requestValue.remove(map.get("order_id"));
    						logger.debug("order_id="+map.get("order_id")+","+commonResponse.getResponseMsg());
    					}
    				}else if(service.equals(UMPay.NOTIFY_NOMALTRANSFER)){//普通有密码的标类转账
    					logger.info("联动优势服务器端回调函数通知调用普通转账操作方法开始");
    					if(!requestValue.containsKey(map.get("order_id"))){
    						requestValue.put(map.get("order_id").toString(),map.get("order_id").toString());
    					}
    					synchronized(requestValue.get(map.get("order_id"))){
    			          //TODO 添加处理业务方法
    						commonResponse.setRequestNo(map.get("order_id").toString());
    						//commonResponse.setBussinessType(service);
    						if(map.get("ret_code").toString().equals(UMPay.CODE_SUCESS)){
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
    							commonResponse.setResponseMsg("普通转账成功");
    						}else{
    							commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
    							commonResponse.setResponseMsg("普通转账失败");
    						}
    						requestValue.remove(map.get("order_id"));
    					}
    				}
    			}
    		}
    	}else{
    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_NOTRECIVEPARAMETER);
			commonResponse.setResponseMsg("没有收到任何回调通知");
    		logger.info("联动优势页面回调函数通知出错，原因：没有收到任何交易参数，请等待一段时间后查询个人中心资金交易明细，或者联系管理员");
    	}
		return commonResponse;
	}
	public void gettest(){}

	/**
	 * 判断不同的第三方操作模式
	 */
	@Override
	public Object[] checkThirdType(String businessType,String type) {
		Object[] thirdType = new Object[2];
		if(businessType.equals(ThirdPayConstants.BT_MMLOANUSER)||businessType.equals(ThirdPayConstants.BT_LOAN)){//理财计划注册用户起息
			//one表示 单个起息。all表示批量起息
			thirdType[0]="all";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else if(businessType.equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
			//1表示 打开页面。0表示不打开页面
			thirdType[0]="0";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else{
    		thirdType[0]="error";
    		thirdType[1]=CommonResponse.RESPONSECODE_NOTBUSINESS;
    	}
		return thirdType;
	}
}
