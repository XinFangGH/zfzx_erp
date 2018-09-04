package com.zhiwei.credit.service.pay;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.pay.BpBidLoan;

/**
 * 
 * @author 
 *
 */
public interface BpBidLoanService extends BaseService<BpBidLoan>{
     /**
      * 获取第三方流水号 通过 系统订单号
      * @param orderNo
      * @return
      */
	BpBidLoan getByOrderNo(String orderNo);
	/**
     * 获取第三方流水号 通过 第三方流水号
     * @param loanNo
     * @return
     */
	BpBidLoan getByLoanNo(String loanNo);
	
}


