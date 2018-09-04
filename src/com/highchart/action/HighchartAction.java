package com.highchart.action;

import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.highchart.model.Highchart;
import com.highchart.model.HighchartModel;
import com.highchart.service.HighchartService;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.system.AppUser;

public class HighchartAction extends BaseAction{
	@Resource
	private HighchartService highchartService;
	/**
	 * 投资客户充值、取现统计
	 * @return
	 */
	public String findOffLineChart(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.findList(pageBean);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 用户数量趋势分析统计
	 * @return
	 */
	public String findUserTrend(){
		PageBean<Highchart> pageBean1 = new PageBean<Highchart>(start,limit,getRequest());
		PageBean<Highchart> pageBean2 = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.findUserTrend(pageBean1);
		highchartService.findUserInvest(pageBean2);
		for(int i=0;i<pageBean1.getResult().size();i++){
			pageBean1.getResult().get(i).setSumD(pageBean2.getResult().get(i).getSumD());
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean1.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 逾期借款统计
	 * @return
	 */
	public String findOverdue(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.findOverdue(pageBean);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(财富系统)——查询单一统计类数据(订单类)
	 * @return
	 */
	public String findSomeInfoWealth(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		Highchart highchart = highchartService.findSomeInfoWealth(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 个人桌面(财富系统)——查询单一统计类数据(客户类)
	 * @return
	 */
	public String findSomePerson(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		Highchart highchart = highchartService.findSomePerson(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务排行榜)
	 * @return
	 */
	public String findSomeRanking(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findSomeRanking(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(往期销售冠军)
	 * @return
	 */
	public String findSomeToChampion(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findSomeToChampion(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(提前赎回项目)
	 * @return
	 */
	public String findSomeEarlyRedemption(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findSomeEarlyRedemption(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(年度交易额统计)
	 * @return
	 */
	public String findSomeYearStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findSomeYearStatistics(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(月度成交统计)
	 * @return
	 */
	public String findSomeMonthTypeStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findSomeMonthTypeStatistics(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}


	/**
	 * 理财计划个人桌面-->新增注册人数，实名认证人数
	 * @return
	 */
	public String getRegUser(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getRegUser(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->投资、充值、提现
	 * @return
	 */
	public String getObAccountDeal(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getObAccountDeal(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->提前赎回 笔数
	 * @return
	 */
	public String getPlearlyRedemptionNum(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlearlyRedemptionNum(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->提前赎回记录
	 * @return
	 */
	public String getPlearlyRedemptionList(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlearlyRedemptionList(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->批量债权转让状态统计图
	 * @return
	 */
	public String getPlanManageState(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlanManageState(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->批量债权转让销售统计图
	 * @return
	 */
	public String getPlanManageMarket(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlanManageMarket(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->资金债权分析图
	 * @return
	 */
	public String getPlanFundAnalyze(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlanFundAnalyze(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 理财计划个人桌面-->批量债权转让类型占比统计图
	 * @return
	 */
	public String getPlanProportion(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.getPlanProportion(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务员桌面)——查询新增客户及贷款数据
	 * @return
	 */
	public String findLoanData(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		Highchart highchart = highchartService.findLoanData(pageBean, user.getUserId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务员桌面)——客户逾期统计
	 * @return
	 */
	public String findOverdueMoney(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		highchartService.findOverdueMoney(pageBean, user.getUserId());
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务员桌面)——放款提醒
	 * @return
	 */
	public String findLoneRemind(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		highchartService.findLoneRemind(pageBean, user.getUserId(), Short.valueOf("1"));
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 个人桌面(业务员桌面)——逾期款项提醒
	 * @return
	 */
	public String findOverdueRemind(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		highchartService.findOverdueRemind(pageBean, user.getUserId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务员桌面)——月度总业务排名
	 * @return
	 */
	public String findLoneRanking(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findLoneRanking(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 个人桌面(业务员桌面)——年度业务统计
	 * @return
	 */
	public String findLoanYearStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		String flag=getRequest().getParameter("flag");
		if(null!=flag && "1".equals(flag)){
			highchartService.findLoanYearStatistics(pageBean,user.getUserId(), Short.valueOf("1"));
		}else{
			highchartService.findLoanYearStatistics(pageBean,null, Short.valueOf("1"));
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 通用桌面——查询新增客户及贷款数据
	 * @return
	 */
	public String findCommonLoanData(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		Highchart highchart = highchartService.findLoanData(pageBean, null);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 通用桌面——逾期款项提醒
	 * @return
	 */
	public String findCommonOverdueRemind(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findOverdueRemind(pageBean,null);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 通用桌面-风险等级统计
	 * @return
	 */
	public String findRiskControl(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findRiskControl(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 通用桌面——客户逾期统计
	 * @return
	 */
	public String findLoanOverdueStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findLoanOverdueStatistics(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * 业务员桌面——线下贷款业务个人业绩排名
	 * @return
	 */
	public String findOwnRanking(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		AppUser user=ContextUtil.getCurrentUser();
		Highchart highchart = highchartService.findOwnRanking(pageBean, user.getUserId());
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 个人桌面(财务桌面)——查询线下贷款等数据
	 * @return
	 */
	public String findFinancialData(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		Highchart highchart = highchartService.findFinancialData(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(highchart));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 个人桌面(财务桌面)——未对账流水
	 * @return
	 */
	public String findFundQlide(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findFundQlide(pageBean);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{\"success\":true,\"result\":");
		sb.append(gson.toJson(pageBean.getResult()));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 个人桌面(财务桌面)——公司收支总览
	 * @return
	 */
	public String findIncomePayStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start, limit,getRequest());
		highchartService.findIncomePayStatistics(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 散标桌面（线上散标异常情况统计）
	 * @return
	 */
	public String bidExceptionMap(){
		PageBean<HighchartModel> pageBean = new PageBean<HighchartModel>(start, limit,getRequest());
		highchartService.bidExceptionMap(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getModel());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		System.out.println(sb.toString());
		return SUCCESS;
		
	}
	
	/**
	 * 散标桌面（线上散标销售情况统计）
	 * @return
	 */
	public String bidSaleStatistics(){
		PageBean<HighchartModel> pageBean = new PageBean<HighchartModel>(start, limit,getRequest());
		highchartService.bidSaleStatistics(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getModel());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		System.out.println(sb.toString());
		return SUCCESS;
		
	}
	
	/**
	 * 散标桌面（线上散标放款后类型占比情况统计）
	 * @return
	 */
	public String bidTypeProportion(){
		PageBean<HighchartModel> pageBean = new PageBean<HighchartModel>(start, limit,getRequest());
		highchartService.bidTypeProportion(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getModel());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		System.out.println(sb.toString());
		return SUCCESS;
		
	}

	/**
	 * 散标桌面(线上用户的充值取现的图表数据)
	 * @return
	 */
	public String onlineRechargeWithDrawChart(){
		PageBean<HighchartModel> pageBean = new PageBean<HighchartModel>(start, limit,getRequest());
		highchartService.onlineRechargeWithDrawChart(pageBean);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getModel());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
	System.out.println("========"+sb.toString());
		return SUCCESS;
	}
	
	
	/**
	 * mobile 业务排行按月
	 * @return
	 */
	public String businessRankByMonth(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.businessRankByMonth(pageBean);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		List<Highchart> liststr = pageBean.getResult();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * mobile我的业绩
	 * @return
	 */
	public String myRank(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.myRank(pageBean);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		List<Highchart> liststr = pageBean.getResult();
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	/**
	 * mobile 业务统计信息获取
	 * @return
	 */
	public String busenessStatistics(){
		PageBean<Highchart> pageBean = new PageBean<Highchart>(start,limit,getRequest());
		highchartService.busenessStatistics(pageBean);
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer();
		String liststr = gson.toJson(pageBean.getResult());
		sb.append("{\"success\":true,\"result\":");
		sb.append(gson.toJson(liststr));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	
	
}
