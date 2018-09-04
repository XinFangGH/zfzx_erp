package com.zhiwei.credit.dao.creditFlow.fund.project;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementReviewerPay;

/**
 * 
 * @author 
 *
 */
public interface SettlementReviewerPayDao extends BaseDao<SettlementReviewerPay>{
	public List<SettlementReviewerPay> platInvestCore(HttpServletRequest request);
	public List<SettlementReviewerPay> listSettle(Long id,String startDate,String endDate);
	public List<SettlementReviewerPay> listInformation(String date);
	List<SettlementReviewerPay> getByOrgId(String orgId);
}