package com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormBidIncomeDetail;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateRedFinanceDetail;

public interface PlateFormFinanceDao {
   /**
    * 获取平台应收的随息款项
    * @param request
    * @param start
    * @param limit
    * @return
    */
	public List<PlateFormBidIncomeDetail> getBidPlateFormReciveMoney(HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 根据类型获得风险保证金或其他一次性收费项目
	 * @param request
	 * @param fundType
	 * @param start
	 * @param limit
	 * @return
	 */
    public List<PlateFormBidIncomeDetail> getOneTimeReciveMoneyList(HttpServletRequest request, String fundType, Integer start,Integer limit);
	
    /**
	 * 根据交易类型获得平台账户的资金明细
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
    public List<ObAccountDealInfo> getAccountDealInfoByTransferType(HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 获取平台资金账户发放的红包
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
    public List<PlateRedFinanceDetail> getPlateFormRedFianceDetail(HttpServletRequest request, Integer start, Integer limit);

}
