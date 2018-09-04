package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmOrderInfo;

/**
 * 
 * @author 
 *
 */
public interface PlMmOrderInfoService extends BaseService<PlMmOrderInfo>{
	
	/**
	 * 启动投资流程
	 * @param request
	 * @return
	 */
	boolean startInvestFlow(HttpServletRequest request);
	
	
	/**
	 * 投资流程提交下一步
	 */
	public Integer investFlowNext(FlowRunInfo flowRunInfo);


	PlMmOrderInfo getByOrderId(Long id);
	
	
}


