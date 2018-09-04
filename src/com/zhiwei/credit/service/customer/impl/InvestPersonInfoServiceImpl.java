package com.zhiwei.credit.service.customer.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.InvestPersonInfoDao;
import com.zhiwei.credit.model.customer.InvestPersonInfo;
import com.zhiwei.credit.service.customer.InvestPersonInfoService;

/**
 * 
 * @author 
 *
 */
public class InvestPersonInfoServiceImpl extends BaseServiceImpl<InvestPersonInfo> implements InvestPersonInfoService{
	@SuppressWarnings("unused")
	private InvestPersonInfoDao dao;
	
	public InvestPersonInfoServiceImpl(InvestPersonInfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	/**
	 * 获取投资人Id列表
	 * @return
	 */
	public List<InvestPersonInfo> getByPersonId(Long personId){
		return dao.getByPersonId(personId);
	}

	@Override
	public List<InvestPersonInfo> getByMoneyPlanId(Long moneyPlanId) {
		// TODO Auto-generated method stub
		return dao.getByMoneyPlanId(moneyPlanId);
	}

	@Override
	public List<InvestPersonInfo> getByBidPlanId(Long bidPlanId) {
		// TODO Auto-generated method stub
		return dao.getByBidPlanId(bidPlanId);
	}
	@Override
	public void saveInvestPerson(Long investPersonId,String investPersonName,
			BigDecimal investMoney,BigDecimal investPercent,String remark,
			String businessType,Long projectId,String orderNo,Long bidPlanId,Long moneyPlanId,Short fundResource){
		InvestPersonInfo personInfo=new InvestPersonInfo();
		
		personInfo.setInvestPersonId(investPersonId);
		personInfo.setInvestPersonName(investPersonName);
		personInfo.setInvestMoney(investMoney);
		personInfo.setInvestPercent(investPercent);
		personInfo.setRemark(remark);
		personInfo.setBusinessType(businessType);
		personInfo.setProjectId(projectId);
		personInfo.setOrderNo(orderNo);// 订单号

		personInfo.setBidPlanId(bidPlanId);
		personInfo.setMoneyPlanId(moneyPlanId); // 资金方案表的 id
		personInfo.setFundResource(fundResource); // 资金来源 add by zcb 2014--2-12
		personInfo.setPersionType(InvestPersonInfo.P_TYPE0);
		personInfo.setBidtype(1);
		
		save(personInfo);
	}
	
	@Override
	public List<InvestPersonInfo> getByRequestNumber(String requestNo) {
		// TODO Auto-generated method stub
		return dao.getByRequestNumber(requestNo);
	}
	@Override
	public List<CommonRequestInvestRecord> getRepaymentList(String planId,String peridId) {
		return dao.getRepaymentList(planId,peridId);
	}

	@Override
	public List<InvestPersonInfo> getByPlanId(Long bidId) {
		// TODO Auto-generated method stub
		return dao.getByPlanId(bidId);
	}

	@Override
	public BigDecimal getInvestTotalMoney(Long bidId) {
		return dao.getInvestTotalMoney(bidId);
	}
	
}