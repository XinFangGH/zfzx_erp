package com.thirdPayInterface.YeePay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.YeePay.YeePayInterfaceUtil;
import com.thirdPayInterface.CommonResponse;
import com.thirdPayInterface.ThirdPayConstants;
import com.thirdPayInterface.ThirdPayTypeInterface;
import com.thirdPayInterface.CommonRequst;
import com.thirdPayInterface.ThirdPayUtil;

public class YeepayConfigImpl implements ThirdPayTypeInterface {

	/**
	 * 易宝
	 */
	@Override
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse=new CommonResponse();
	    try{
	    	
	    	if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREGISTER)){//个人开通第三方
	    		commonResponse=YeePayInterfaceUtil.enterRegister(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_EREGISTER)){//企业开通第三方
	    		commonResponse=YeePayInterfaceUtil.enterRegister(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_EREGISTER)){//充值
	    		commonResponse=YeePayInterfaceUtil.recharge(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_WITHDRAW)){//取现
	    		commonResponse=YeePayInterfaceUtil.toWithdraw(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BINDCARD)){//绑定银行卡
	    		commonResponse=YeePayInterfaceUtil.toBindBankCard(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELCARD)){//取消绑定银行卡
	    		commonResponse=YeePayInterfaceUtil.toBindBankCard(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEPHONE)){//修改手机号
	    		commonResponse=YeePayInterfaceUtil.toResetMobile(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BID)){//散标投标
	    		commonResponse=YeePayInterfaceUtil.fiancialTransfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_LOAN)){//散标放款
	    		 commonRequst.setRequsetNo(commonRequst.getSubOrdId());
	    		 commonRequst.setBidType(CommonRequst.HRY_BID);
	    		//commonResponse=YeePayInterfaceUtil.loan(commonRequst);
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_REPAYMENT)){//散标自助还款
	    		commonResponse=YeePayInterfaceUtil.toRepaymentByLoaner(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HELPPAY)){//平台帮助借款人还款
	    		 commonRequst.setBidType(CommonRequst.HRY_BID);
//	    		commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner(commonRequst);//1.0还款
	    		commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner2(commonRequst);//2.0还款
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BEFOREPAY)){//散标提前还款
	    		commonResponse=YeePayInterfaceUtil.toRepaymentByLoaner(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_AUTOBID)){//散标自动投标
	    		 commonRequst.setBidType(CommonRequst.HRY_BID);
//	    		commonResponse=YeePayInterfaceUtil.autoTransfer(commonRequst);//1.0接口
	    		 commonResponse=YeePayInterfaceUtil.autoTransfer2(commonRequst);//2.0接口
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_OPENBIDAUTH)){//自动投标授权
	    		commonResponse=YeePayInterfaceUtil.autoTransferAuthorization(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_OPENPAYAUTH)){//自动还款授权
	    		commonResponse=YeePayInterfaceUtil.autoRepaymentAuthorization(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CLOSEBIDAUTH)){//关闭自动投标授权
	    		commonResponse=YeePayInterfaceUtil.cancelTransferAuthorization(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CLOSEPAYAUTH)){//关闭自动还款授权
	    		commonResponse=YeePayInterfaceUtil.cancelRepaymentAuthorization(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELBID)){//取消投标
	    		commonRequst.setBidType(CommonRequst.HRY_BID);
	    		commonResponse=YeePayInterfaceUtil.revocationTransfer(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_REPLACEMONEY)){//代偿还款 申请 
	    		commonResponse=YeePayInterfaceUtil.ommontransferIntrface(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CHECKREPLACE)){//代偿还款审核 
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMUSER)){//理财计划购买(收款账户为用户)
	    		commonResponse=YeePayInterfaceUtil.fiancialTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMPLATFORM)){//理财计划购买(收款账户为平台)
	    		commonResponse=YeePayInterfaceUtil.ommontransferIntrface(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANUSER)){//理财计划起息(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		 commonRequst.setRequsetNo(commonRequst.getSubOrdId());
//	    		commonResponse=YeePayInterfaceUtil.loan(commonRequst);//1.0接口 
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);//2.0接口
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMLOANPLATF)){//理财计划起息(收款账户为平台)
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEUSER)){//理财计划派息(收款账户为用户)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		//commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner(commonRequst); //1.0派息  
	    		commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner2(commonRequst);//2.0派息
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMGIVEPLATF)){//理财计划派息(收款账户为平台)
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=YeePayInterfaceUtil.platformTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELUSER)){//理财计划派息(收款账户为用户)
	    		commonResponse=YeePayInterfaceUtil.revocationTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMCANCELPLATF)){//理财计划派息(收款账户为平台)
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
	    		commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=YeePayInterfaceUtil.autoRepaymentAuthorization(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HANGDEAL)){//挂牌交易
	    		commonResponse=YeePayInterfaceUtil.ommontransferIntrface(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BUYDEAL)){//购买债权
	    		commonResponse=YeePayInterfaceUtil.obligationDeal(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CANCELDEAL)){//取消挂牌
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_TRUESERVICE)){//挂牌交易服务费将预收转为实收
	    		commonRequst.setRequsetNo(commonRequst.getQueryRequsetNo());
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_FALSESERVICE)){//挂牌交易服务费将预收转实收后退费
	    		commonRequst.setBidType(CommonRequst.HRY_QUIT);
	    		commonResponse=YeePayInterfaceUtil.platformTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HRBIN)){//互融宝转入申请
	    		commonResponse=YeePayInterfaceUtil.ommontransferIntrface(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CHECKHRBIN)){//互融宝转入审核
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_HRBOUT)){//互融宝转出
	    		commonResponse=YeePayInterfaceUtil.ommontransferIntrface(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_SEDRED)){//派发红包
	    		commonResponse=YeePayInterfaceUtil.platformTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_COUPONAWARD)){//派发优惠券奖励
	    		commonResponse=YeePayInterfaceUtil.platformTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PREPAREPAY)){//平台准备金代偿还款
	    		commonResponse=YeePayInterfaceUtil.toRepaymentByReserve(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYPLATF)){//平台会员交易查询(充值、取现、还款、放款、转账)
	    		commonResponse=YeePayInterfaceUtil.singleQuery(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYUSER)||commonRequst.getBussinessType().equals(ThirdPayConstants.BT_BALANCEQUERY)){//注册用户查询
	    		commonResponse=YeePayInterfaceUtil.registerQuery(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_QUERYACCOUNT)){//业务对账
	    		commonResponse=YeePayInterfaceUtil.reconciliation(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTPLATF)){//理财计划提前退出(收款账户为平台)
	    		 commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
	    		commonResponse=YeePayInterfaceUtil.platformTransfer(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_MMSIGNOUTUSER)){//理财计划提前退出(收款账户为用户)
	    		 commonRequst.setBidType(CommonRequst.HRY_PLANBUY);
//	    		commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner(commonRequst);  //1.0 接口
	    		 commonResponse=YeePayInterfaceUtil.toRepaymentDirectlyByLoaner2(commonRequst);  //2.0接口 
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_PLATEFORMRECHAGE)){//平台收费
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("处理成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_GETPLATFORM)){//平台商户余额查询
	    		commonResponse=YeePayInterfaceUtil.registerQuery(commonRequst);   
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_UPDATEBID)){
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("处理成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_CREATEBID)){
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
	    		commonResponse.setResponseMsg("处理成功");
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BT_USEALLAUDIT)){//通用转账授权审核
	    		commonRequst.setRequsetNo(commonRequst.getLoanNoList());
	    		commonResponse=YeePayInterfaceUtil.checkCommonTransfer(commonRequst);
	    	}else{
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

	@Override
	public CommonResponse businessReturn(Map maps,HttpServletRequest request) {
		CommonResponse commonResponse = new CommonResponse();
		
		String req=maps.get("resp").toString();
		String sign=maps.get("sign").toString();
		YeePayReponse response=null;
		try {
			response = ThirdPayUtil.JAXBunmarshal(req,YeePayReponse.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Boolean isSign=ThirdPayUtil.verifySign(req, sign);
		if(isSign){
			if(response.getCode().equals("1")){
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_SUCCESS);
			}else{
				commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
			}
			commonResponse.setResponseMsg(response.getDescription());//返回说明
		}else{
			commonResponse.setResponsecode(CommonResponse.RESPONSECODE_ISNOTPASSSIGN);
			commonResponse.setResponseMsg("签名验证失败");//返回说明
		}
		commonResponse.setRequestNo(response.getRequestNo());//流水号
		commonResponse.setBussinessType(response.getService());//业务类型
		commonResponse.setRequestInfo(req+""+sign);//返回数据包
		return commonResponse;
	}

	/**
	 * 判断不同的第三方操作模式
	 */
	@Override
	public Object[] checkThirdType(String businessType,String type) {
		Object[] thirdType = new Object[2];
		if(businessType.equals(ThirdPayConstants.BT_MMLOANUSER)||businessType.equals(ThirdPayConstants.BT_LOAN)){//理财计划注册用户起息
			//one表示 单个起息。all表示批量起息
			thirdType[0]="one";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;	
    	}else if(businessType.equals(ThirdPayConstants.BT_MMAUTH)){//理财计划授权平台派息
			//1表示 打开页面。0表示不打开页面
			thirdType[0]="1";
			thirdType[1]=CommonResponse.RESPONSECODE_SUCCESS;
    	}else{
    		thirdType[0]="error";
    		thirdType[1]=CommonResponse.RESPONSECODE_NOTBUSINESS;
    	}
		return thirdType;
	}

}
