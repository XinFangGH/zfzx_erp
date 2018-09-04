package com.thirdPayInteface.YeePay;

import com.thirdPayInteface.CommonResponse;
import com.thirdPayInteface.ThirdPayConstants;
import com.thirdPayInteface.ThirdPayTypeInterface;
import com.thirdPayInteface.CommonRequst;

public class YeepayConfigImpl implements ThirdPayTypeInterface {

	/**
	 * 易宝
	 */
	@Override
	public CommonResponse businessHandle(CommonRequst commonRequst) {
		CommonResponse commonResponse=new CommonResponse();
	    try{
	    	if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_REGISTER)){//注册
	    		if(commonRequst.getCustMemberType().equals(ThirdPayConstants.CUSTERTYPE_ENTERPRISE)){//企业客户注册
	    			commonResponse=YeePayInterfaceUtil.enterRegister(commonRequst);
	    		}else{//个人客户注册
	    			commonResponse=YeePayInterfaceUtil.register(commonRequst);
	    		}
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_RECHAGE)){//充值
	    		commonResponse=YeePayInterfaceUtil.recharge(commonRequst);
	    	}else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_LOAN)){//投标放款
	    		commonResponse=YeePayInterfaceUtil.loan(commonRequst);
	    	}
	    	else if(commonRequst.getBussinessType().equals(ThirdPayConstants.BUSSINESSTYPE_BINDBANKCAR)){//绑定银行卡
	    		
	    	}else{
	    		commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
		    	commonResponse.setResponseMsg("没有该业务类型");
	    	}
	    }catch(Exception e){
	    	e.printStackTrace();
	    	commonResponse.setResponsecode(CommonResponse.RESPONSECODE_FAILD);
	    	commonResponse.setResponseMsg("系统报错");
	    }
		
		return commonResponse;
	}

}
