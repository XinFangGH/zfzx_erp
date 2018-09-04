package com.zhiwei.credit.service.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.BpFinanceApply;

/**
 * 
 * @author 
 *
 */
@WebService
public interface BpFinanceApplyService extends BaseService<BpFinanceApply>{
	/**
	 * 提交融资申请
	 * @param productId
	 * @param linkPersion
	 * @param phone
	 * @param loanMoney
	 * @param isOnline
	 * @param loanTimeLen
	 * @param area
	 * @param remark
	 * @param productName
	 * @return
	 */
	@WebMethod
	public String addFinanceApply(String productId,String linkPersion,String phone,String loanMoney,String isOnline,String loanTimeLen,String area,String remark,String productName);
}


