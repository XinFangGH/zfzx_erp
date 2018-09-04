package com.zhiwei.credit.service.pay;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.pay.BpMoneyManager;

/**
 * 
 * @author 
 *
 */
public interface BpMoneyManagerService extends BaseService<BpMoneyManager>{
	public BpMoneyManager getByOrderAndType(String OrderNo,String LoanNo,String Type);
}


