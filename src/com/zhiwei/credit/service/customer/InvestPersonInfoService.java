package com.zhiwei.credit.service.customer;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import com.thirdPayInterface.CommonRequestInvestRecord;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

/**
 * 
 * @author 
 *
 */
public interface InvestPersonInfoService extends BaseService<InvestPersonInfo>{
	/**
	 * 获取投资人Id列表
	 * @return
	 */
	public List<InvestPersonInfo> getByPersonId(Long personId);
	
	List<InvestPersonInfo>  getByMoneyPlanId(Long moneyPlanId);
	
	List<InvestPersonInfo>  getByBidPlanId(Long bidPlanId);
	// 保存投资人 信息
	public void saveInvestPerson(Long investPersonId,String investPersonName,
			BigDecimal investMoney,BigDecimal investPercent,String remark,
			String businessType,Long projectId,String orderNo,Long bidPlanId,Long moneyPlanId,Short fundResource);
	
	/**
	 * 获取交易流水号获得的交易记录
	 * @param requestNo
	 * @return
	 */
	public List<InvestPersonInfo> getByRequestNumber(String requestNo);
	/**
	 * 根据标的id和期数获得相应款项记录信息
	 * @param planId   标id
	 * @param peridId  期数
	 * @return
	 */
	public List<CommonRequestInvestRecord> getRepaymentList(String planId,String peridId);
	/**
	 * 通过标id 获取
	 * @param bidId
	 * @return
	 */
	public List<InvestPersonInfo> getByPlanId(Long bidId);
	
	/**
	 * 根据标的Id查询已投资成功总金额
	 */
	public BigDecimal getInvestTotalMoney(Long bidId); 

}


