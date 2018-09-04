package com.highchart.dao;

import java.math.BigDecimal;

import com.highchart.model.Highchart;
import com.highchart.model.HighchartModel;
import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;

public interface HighchartDao extends BaseDao<Highchart>{
	public void findList(PageBean<Highchart> pageBean);
	public void findUserTrend(PageBean<Highchart> pageBean);
	public void findUserInvest(PageBean<Highchart> pageBean);
	public void findOverdue(PageBean<Highchart> pageBean);
	/**
	 * 查询本月订单交易信息
	 * @param pageBean
	 */
	public void findSomeInfoWealth(PageBean<Highchart> pageBean);
	/**
	 * 查询本月新增正式客户数量
	 * @param pageBean
	 */
	public void findSomeFormalPerson(PageBean<Highchart> pageBean);
	/**
	 * 查询本月新增意向客户数量
	 * @param pageBean
	 */
	public void findSomeIntentionPerson(PageBean<Highchart> pageBean);
	/**
	 * 业务排行榜查询
	 * @param pageBean
	 */
	public void findSomeRanking(PageBean<Highchart> pageBean);
	/**
	 * 往期销售冠军查询
	 * @param pageBean
	 */
	public void findSomeToChampion(PageBean<Highchart> pageBean);
	/**
	 * 提前赎回项目
	 * @param pageBean
	 */
	public void findSomeEarlyRedemption(PageBean<Highchart> pageBean);
	/**
	 * 年度交易额统计
	 * @param pageBean 
	 */
	public void findSomeYearStatistics(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面(月度成交统计)
	 * @param pageBean
	 */
	public void findSomeMonthTypeStatistics(PageBean<Highchart> pageBean);

	
	/**
	 * 散标桌面(线上用户的充值取现的图表数据)
	 * @param pageBean
	 */
	public void onlineRechargeWithDrawChart(PageBean<HighchartModel> pageBean);

	/**
	 * 理财计划桌面(新增注册人数、实名认证人数)
	 * @param pageBean
	 */
	public void getRegUser(PageBean<Highchart> pageBean);
	/**
	 * 理财计划桌面(新增投资、充值、提现)
	 * @param pageBean
	 */
	public void getObAccountDeal(PageBean<Highchart> pageBean);
	/**
	 * 理财计划桌面(提前赎回笔数)
	 * @param pageBean
	 */
	public void getPlearlyRedemptionNum(PageBean<Highchart> pageBean);
	/**
	 * 理财计划桌面(提前赎回记录)
	 * @param pageBean
	 */
	public void getPlearlyRedemptionList(PageBean<Highchart> pageBean);
	/**
	 * 理财计划个人桌面-->批量债权转让状态统计图
	 * @param pageBean
	 */
	public void getPlanManageState(PageBean<Highchart> pageBean);
	/**
	 * 理财计划个人桌面-->批量债权转让销售统计图
	 * @param pageBean
	 */
	public void getPlanManageMarket(PageBean<Highchart> pageBean);
	/**
	 * 理财计划个人桌面-->资金债权分析图
	 * @param pageBean
	 */
	public void getPlanFundAnalyze(PageBean<Highchart> pageBean);
	public BigDecimal getPlanChildren(String date);
	
	/**
	 * 理财计划个人桌面-->批量债权类型占比图
	 * @param pageBean
	 */
	public void getPlanProportion(PageBean<Highchart> pageBean);
	public Long getPlanType(Long typeId);
	/**
	 * 统计本月和上月新增意向客户数量按创建者
	 * @param pageBean
	 * @param creatorId
	 */
	public void findSomeIntentionPerson(PageBean<Highchart> pageBean,Long creatorId);
	/**
	 * 统计本月和上月新增借款客户按创建者（企业+个人）
	 * @param pageBean
	 * @param creatorId
	 */
	public void findLoanCustomer(PageBean<Highchart> pageBean,Long creatorId);
	/**
	 * 统计本月和上月新增贷款金额及笔数
	 * @param pageBean
	 * @param creatorId
	 * @param projectStatus
	 */
	public void findLoanMoneyAmount(PageBean<Highchart> pageBean,Long creatorId,Short projectStatus);
	/**
	 * 统计到现在为止的应收、未收等款项
	 * @param pageBean
	 * @param userId
	 */
	public void findOverdueMoney(PageBean<Highchart> pageBean,Long userId);
	/**
	 * 逾期款项查询
	 * @param pageBean
	 * @param userId
	 */
	public void findOverdueRemind(PageBean<Highchart> pageBean,Long userId);
	/**
	 * 放款项目查询
	 * @param pageBean
	 * @param creatorId
	 * @param projectStatus
	 */
	public void findLoneRemind(PageBean<Highchart> pageBean,Long userId,Short projectStatus);
	/**
	 * 月度业务总排名
	 * @param pageBean
	 */
	public void findLoneRanking(PageBean<Highchart> pageBean);
	/**
	 * 线下业务放款统计
	 * @param pageBean
	 * @param userId
	 * @param projectStatus
	 */
	public void findLoanYearStatistics(PageBean<Highchart> pageBean,Long userId,Short projectStatus);
	/**
	 * *线下业务风险等级统计
	 * @param pageBean
	 */
	public void findRiskControl(PageBean<Highchart> pageBean);
	/**
	 * 线下业务客户逾期统计
	 * @param pageBean
	 */
	public void findLoanOverdueStatistics(PageBean<Highchart> pageBean);
	
	/**
	 * 线下贷款业务个人业绩排名名次
	 * @param pageBean
	 */
	public void findOwnRanking(PageBean<Highchart> pageBean,Long userId,String type);
	/**
	 * 线下贷款业务本月及上月应收金额查询
	 * @param pageBean
	 */
	public void findFinancialLoanMoney(PageBean<Highchart> pageBean);
	/**
	 * 线下贷款业务本月及上月实收金额查询
	 * @param pageBean
	 * @param string
	 * @param sdate
	 */
	public void findFinancialAfterMoney(PageBean<Highchart> pageBean,String startDate, String sdate);
	
	/**
	 * 线下贷款业务未收金额查询
	 * @param pageBean
	 * @param startDate
	 * @param endDate
	 */
	public void findFinancialNotMoney(PageBean<Highchart> pageBean,String startDate,String endDate);
	/**
	 * 查询银行and现金的未对账的流水
	 * @param pageBean
	 */
	public void findFundQlide(PageBean<Highchart> pageBean);
	/**
	 * 款项对账情况总览
	 * @param pageBean
	 */
	public void findIncomePayStatistics(PageBean<Highchart> pageBean);
	
	/**
	 * 散标违约情况统计表
	 * @param pageBean
	 */
	public void bidExceptionMap(PageBean<HighchartModel> pageBean);
	
	
	/**
	 * 散标放款后类型所占总放款金额比列
	 * @param pageBean
	 */
	public void bidTypeProportion(PageBean<HighchartModel> pageBean);
	
	/**
	 * 散标销售统计图
	 * @param pageBean
	 */
	
	public void bidSaleStatistics(PageBean<HighchartModel> pageBean);
	
	public void businessRankByMonth(PageBean<Highchart> pageBean);
	public void myRank(PageBean<Highchart> pageBean);
	public void busenessStatistics(PageBean<Highchart> pageBean);
	

}
