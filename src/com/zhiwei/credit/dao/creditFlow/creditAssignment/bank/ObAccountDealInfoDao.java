package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.thirdPayInterface.ThirdPayLog.model.ThirdPayRecord;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.PlateFormStatisticsDetail;

/**
 * 
 * @author 
 *
 */
public interface ObAccountDealInfoDao extends BaseDao<ObAccountDealInfo>{
	//查询取现交易信息
	public List<ObAccountDealInfo> getDealList(String investPersonName,String transferDate,String flag);
//查询充值确认列表信息
	public List<ObAccountDealInfo> getRechargeDealList(String investPersonName,
			String seniorValidationRechargeStatus,
			String rechargeConfirmStatus, String flag,String rechargeLevel);
	/*查询投资人账户记录，总收入，总支出*/
	public String getCreateNameByCreateId(Long createId);
	//根据投资人id  投资人债权id  以及账户交易类型
	public ObAccountDealInfo getDealInfo(Long investMentPersonId, Long id,String flag);
	public List<ObAccountDealInfo> queyAccountInfoRecord(Long accountId,
			String transferType, Short dealRecordStatus,
			Short rechargeConfirmStatus, HttpServletRequest request,Integer start, Integer limit);
	/**
	 * 查询系统账户预冻结金额
	 * @param accountId
	 * @param direction
	 * @return
	 */
	public BigDecimal prefreezMoney(Long accountId, String direction);
	/**
	 * 查询系统账户中各种生效交易的总额
	 * @param accountId
	 * @param direction 
	 * @return
	 */
	public BigDecimal typeTotalMoney(Long accountId, String direction);
	/**
	 * 根据系统账户信息以及查询条件来查询系统账户成功和没有冻结的交易信息
	 * @param accountId
	 * @param request
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<ObAccountDealInfo> getaccountListQuery(String accountId,
			HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 查询线上提现审核
	 * @return
	 */
	public List<ObAccountDealInfo> getWithdrawCheck(HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 查询交易记录
	 * @param accountId
	 * @param investPersonId
	 * @param recordNumber
	 * @param accountInfoId
	 * @return
	 */
	public ObAccountDealInfo getDealinfo(String transferType,Long accountId, Long investPersonId,Short investPersonType,
			String recordNumber, Long accountInfoId);
	
	/**
	 * 查询系统账户中各种生效交易的总额
	 * @param accountId
	 * @param direction 
	 * @return
	 */
	public BigDecimal typeTotalMoney(Long userId,Long accountId, String direction);
	public BigDecimal sumPersonMoney(String transferType,Long investPersonId,String startTime,String endTime);
	
	public ObAccountDealInfo getByOrderNumber(String orderNo, String totalFee,String transferType,
			String type0);
	public ObAccountDealInfo getBythirdNumber(String thirdNumber) ;
	/**
	 * 查出来平台每个账户的自己明细
	 */
	public List<PlateFormStatisticsDetail> getOneTypePlateFormStaticsDatail(HttpServletRequest request, Integer start, Integer limit);
	/**
	 * 合作机构账户明细查询
	 * @param map
	 * @return
	 */
	public List<ObAccountDealInfo> cooperationAccountInfoList(Map<String, String> map);
	/**
	 * 合作机构账户明细查询--总记录数
	 * @param map
	 * @return
	 */
	public Long cooperationAccountInfoListCount(Map<String, String> map);
	public BigDecimal getAccountIdRechargeMoney(Long accoutId);
	/**
	 * 查询系统所有发生交易的用户
	 * @param day
	 */
	public List<ObAccountDealInfo> getAllThirdDealInfo(String day);
	
	public BigDecimal sumMoney(String transferType,Long[] investPersonIds,String startTime,String endTime);
	
	/**
	 * 根据交易类型，交易状态查询记录
	 * @param transferType
	 * @param dealRecordStatus
	 * @param searchHours
	 * @return
	 */
	public List<ObAccountDealInfo> getListByTransFerTypeAndState(
			String transferType, String dealRecordStatus,String searchHours);

    List<ThirdPayRecord> getThirdPayRecord(String bpName, String startDate, String transferType);
}