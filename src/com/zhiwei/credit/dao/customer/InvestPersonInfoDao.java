package com.zhiwei.credit.dao.customer;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

/**
 * 
 * @author 
 *
 */
public interface InvestPersonInfoDao extends BaseDao<InvestPersonInfo>{
	/**
	 * 获取投资人Id列表
	 * @return
	 */
	public List<InvestPersonInfo> getByPersonId(Long personId);
	
	List<InvestPersonInfo> getByMoneyPlanId(Long moneyPlanId);
	
	List<InvestPersonInfo> getByBidPlanId(Long bidPlanId);
	
	public List<InvestPersonInfo> getByRequestNumber(String requestNo);
	public List<CommonRequestInvestRecord> getRepaymentList(String planId,String peridId);
	public List<InvestPersonInfo> getByPlanId(Long bidId);
	/**
	 * 根据标的Id查询已投资成功总金额
	 */
	public BigDecimal getInvestTotalMoney(Long bidId); 
}