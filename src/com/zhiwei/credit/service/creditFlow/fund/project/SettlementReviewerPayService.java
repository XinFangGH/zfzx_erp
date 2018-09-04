package com.zhiwei.credit.service.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;

/**
 * 
 * @author 
 *
 */
public interface SettlementReviewerPayService extends BaseService<SettlementReviewerPay>{
	public List<SettlementReviewerPay> platInvestCore(HttpServletRequest request);
	public List<SettlementReviewerPay> listSettle(Long id,String startDate,String endDate);
	public List<SettlementReviewerPay> listInformation(String date);
	List<SettlementReviewerPay> getByOrgId(String orgId);
	void createSettleInfo();
	Integer updateInfoNextStep(FlowRunInfo flowRunInfo);
	void createSettleInfoAll(Date startDate, Date endDate);
}


