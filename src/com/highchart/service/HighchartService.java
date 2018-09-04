package com.highchart.service;

import com.highchart.model.Highchart;
import com.highchart.model.HighchartModel;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;

public interface HighchartService extends BaseService<Highchart>{
	/**
	 * 投资客户充值、取现统计
	 * @param pageBean
	 */
	public void findList(PageBean<Highchart> pageBean);
	/**
	 * 统计注册用户信息
	 * @param pageBean
	 */
	public void findUserTrend(PageBean<Highchart> pageBean);
	/**
	 * 统计注册投资次数
	 * @param pageBean
	 */
	public void findUserInvest(PageBean<Highchart> pageBean);
	/**
	 * 统计逾期借款
	 * @param pageBean
	 */
	public void findOverdue(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面查询单一统计类数据(订单类)
	 * @param pageBean
	 */
	public Highchart findSomeInfoWealth(PageBean<Highchart> pageBean); 
	/**
	 * 财富系统个人桌面查询单一统计类数据（客户类）
	 * @param pageBean
	 */
	public Highchart findSomePerson(PageBean<Highchart> pageBean); 
	/**
	 * 财富系统个人桌面(业务排行榜)
	 * @param pageBean
	 */
	public void findSomeRanking(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面(往期销售冠军)
	 * @param pageBean
	 */
	public void findSomeToChampion(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面(提前赎回项目)
	 * @param pageBean
	 */
	public void findSomeEarlyRedemption(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面(年度交易额统计)
	 * @param pageBean
	 */
	public void findSomeYearStatistics(PageBean<Highchart> pageBean);
	/**
	 * 财富系统个人桌面(月度成交统计)
	 * @param pageBean
	 */
	public void findSomeMonthTypeStatistics(PageBean<Highchart> pageBean);

	
	/**
	 * 
	 * @param pageBean
	 */
	//public void bidExceptionMap(PageBean<HighchartModel> pageBean);

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
	/**
	 * 理财计划个人桌面-->批量债权转让类型占比图
	 * @param pageBean
	 */
	public void getPlanProportion(PageBean<Highchart> pageBean);
	/**
	 *查询线下借款业务统计数据 
	 * @param pageBean
	 * @return
	 */
	public Highchart findLoanData(PageBean<Highchart> pageBean,Long userId);
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
	public Highchart findOwnRanking(PageBean<Highchart> pageBean,Long userId);
	/**
	 * 财务桌面线下贷款数据查询
	 * @param pageBean
	 * @return
	 */
	public Highchart findFinancialData(PageBean<Highchart> pageBean);
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
	 * 散标桌面（线上散标异常情况统计）
	 */
	public void bidExceptionMap(PageBean<HighchartModel> pageBean);
	
	/**
	 * 散标桌面(线上用户的充值取现的图表数据)
	 */
	public void onlineRechargeWithDrawChart(PageBean<HighchartModel> pageBean);
	/**
	 * 散标桌面（线上散标放款后类型占比情况统计）
	 * @return
	 */
	public void bidTypeProportion(PageBean<HighchartModel> pageBean);
	
	public void bidSaleStatistics(PageBean<HighchartModel> pageBean);
	
	public void businessRankByMonth(PageBean<Highchart> pageBean);
	/**
	 * mobile我的业绩
	 * @param pageBean
	 */
	public void myRank(PageBean<Highchart> pageBean);
	
	/**
	 * mobile-业务统计
	 * @param pageBean
	 */
	public void busenessStatistics(PageBean<Highchart> pageBean);
	
}
