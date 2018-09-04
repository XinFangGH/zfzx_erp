package com.zhiwei.credit.service.creditFlow.finance.compensatory;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.compensatory.PlBidCompensatoryFlow;

/**
 * 
 * @author 
 *
 */
public interface PlBidCompensatoryFlowService extends BaseService<PlBidCompensatoryFlow>{

	/**
	 * @param plBidCompensatoryFlow
	 * @return
	 */
	public Boolean saveCompensatoryFlow(PlBidCompensatoryFlow plBidCompensatoryFlow);

	/**
	 * @param plBidCompensatoryFlow
	 * @return
	 */
	public String[] check(PlBidCompensatoryFlow plBidCompensatoryFlow);
	
}


