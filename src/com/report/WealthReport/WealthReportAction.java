package com.report.WealthReport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.hurong.core.util.DateUtil;
import com.report.ReportUtil;
import com.zhiwei.core.web.action.BaseAction;

public class WealthReportAction extends BaseAction{
	
	/**
	 * 理财产品购买明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmInfo(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String startinInterestTimeGE=this.getRequest().getParameter("startinInterestTime_GE");
		String startinInterestTimeLE=this.getRequest().getParameter("startinInterestTime_LE");
		String buyMoneyGE=this.getRequest().getParameter("buyMoney_GE");
		String buyMoneyLE=this.getRequest().getParameter("buyMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String investPersonNameLK=this.getRequest().getParameter("investPersonName_LK");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			sqlCondition=sqlCondition+" and plmb.startinInterestTime>=\""+startinInterestTimeGE+"\"";
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			sqlCondition=sqlCondition+" and plmb.startinInterestTime<=\""+startinInterestTimeLE+"\"";
		}
		
		if(null!=buyMoneyGE && !"".equals(buyMoneyGE)){
			sqlCondition=sqlCondition+" and plmb.buyMoney>=\""+buyMoneyGE+"\"";
		}
		if(null!=buyMoneyLE && !"".equals(buyMoneyLE)){
			sqlCondition=sqlCondition+" and plmb.buyMoney<=\""+buyMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and plmb.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=investPersonNameLK && !"".equals(investPersonNameLK)){
			sqlCondition=sqlCondition+" and plmb.investPersonName like \"%"+investPersonNameLK+"%\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and plmoi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and plmoi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			map.put("Date1",startinInterestTimeGE);
			//param.append(" &Date1=").append(startinInterestTimeGE).append("");
		}else{
			map.put("Date1", "");
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			map.put("Date2",startinInterestTimeLE);
			//param.append(" &Date2=").append(startinInterestTimeLE).append("");
		}else{
			map.put("Date2", "");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 理财产品派息预算汇总月度表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmMonth(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String startinInterestTimeGE=this.getRequest().getParameter("intentDate_GE");
		String startinInterestTimeLE=this.getRequest().getParameter("intentDate_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			sqlCondition=sqlCondition+" and plmor.intentDate>=\""+startinInterestTimeGE+"\"";
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			sqlCondition=sqlCondition+" and plmor.intentDate<=\""+startinInterestTimeLE+"\"";
		}
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and plmor.mmName like \"%"+mmNameLK+"%\"";
		}
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		//页面参数
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			map.put("Date1", startinInterestTimeGE);
			//param.append(" &Date1=").append(startinInterestTimeGE).append("");
		}else{
			map.put("Date1","");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			map.put("Date2", startinInterestTimeLE);
			//param.append(" &Date2=").append(startinInterestTimeLE).append("");
		}else{
			map.put("Date2","");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 理财产品派息预算明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmDividend(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String incomeMoneyGE=this.getRequest().getParameter("incomeMoney_GE");
		String incomeMoneyLE=this.getRequest().getParameter("incomeMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String investPersonNameLK=this.getRequest().getParameter("investPersonName_LK");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and pma.intentDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and pma.intentDate<=\""+intentDateLE+"\"";
		}
		
		if(null!=incomeMoneyGE && !"".equals(incomeMoneyGE)){
			sqlCondition=sqlCondition+" and pma.incomeMoney>=\""+incomeMoneyGE+"\"";
		}
		if(null!=incomeMoneyLE && !"".equals(incomeMoneyLE)){
			sqlCondition=sqlCondition+" and pma.incomeMoney<=\""+incomeMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and pma.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=investPersonNameLK && !"".equals(investPersonNameLK)){
			sqlCondition=sqlCondition+" and pma.investPersonName like \"%"+investPersonNameLK+"%\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and pmi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and pmi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			map.put("Date1",intentDateGE);
			//param.append(" &Date1=").append(intentDateGE).append("");
		}else{
			map.put("Date1","");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			map.put("Date2",intentDateLE);
			//param.append(" &Date2=").append(intentDateLE).append("");
		}else{
			map.put("Date2","");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 理财产品派息明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmDividendAlready(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("factDate_GE");
		String intentDateLE=this.getRequest().getParameter("factDate_LE");
		String incomeMoneyGE=this.getRequest().getParameter("afterMoney_GE");
		String incomeMoneyLE=this.getRequest().getParameter("afterMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String investPersonNameLK=this.getRequest().getParameter("investPersonName_LK");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and pma.factDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and pma.factDate<=\""+intentDateLE+"\"";
		}
		
		if(null!=incomeMoneyGE && !"".equals(incomeMoneyGE)){
			sqlCondition=sqlCondition+" and pma.afterMoney>=\""+incomeMoneyGE+"\"";
		}
		if(null!=incomeMoneyLE && !"".equals(incomeMoneyLE)){
			sqlCondition=sqlCondition+" and pma.afterMoney<=\""+incomeMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and pma.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=investPersonNameLK && !"".equals(investPersonNameLK)){
			sqlCondition=sqlCondition+" and pma.investPersonName like \"%"+investPersonNameLK+"%\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and pmi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and pmi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			map.put("Date1", intentDateGE);
			//param.append(" &Date1=").append(intentDateGE).append("");
		}else{
			map.put("Date1","");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			map.put("Date2", intentDateLE);
			//param.append(" &Date2=").append(intentDateLE).append("");
		}else{
			map.put("Date2", "");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 门店理财产品购买明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmInfoShop(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String startinInterestTimeGE=this.getRequest().getParameter("startinInterestTime_GE");
		String startinInterestTimeLE=this.getRequest().getParameter("startinInterestTime_LE");
		String buyMoneyGE=this.getRequest().getParameter("buyMoney_GE");
		String buyMoneyLE=this.getRequest().getParameter("buyMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String departMentNameEQ=this.getRequest().getParameter("departMentName_EQ");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			sqlCondition=sqlCondition+" and plmb.startinInterestTime>=\""+startinInterestTimeGE+"\"";
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			sqlCondition=sqlCondition+" and plmb.startinInterestTime<=\""+startinInterestTimeLE+"\"";
		}
		
		if(null!=buyMoneyGE && !"".equals(buyMoneyGE)){
			sqlCondition=sqlCondition+" and plmb.buyMoney>=\""+buyMoneyGE+"\"";
		}
		if(null!=buyMoneyLE && !"".equals(buyMoneyLE)){
			sqlCondition=sqlCondition+" and plmb.buyMoney<=\""+buyMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and plmb.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=departMentNameEQ && !"".equals(departMentNameEQ)){
			sqlCondition=sqlCondition+" and plmoi.departMentName=\""+departMentNameEQ+"\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and plmoi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and plmoi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=startinInterestTimeGE && !"".equals(startinInterestTimeGE)){
			map.put("Date1", startinInterestTimeGE);
			//param.append(" &Date1=").append(startinInterestTimeGE).append("");
		}else{
			map.put("Date1", "");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=startinInterestTimeLE && !"".equals(startinInterestTimeLE)){
			map.put("Date2", startinInterestTimeLE);
			//param.append(" &Date2=").append(startinInterestTimeLE).append("");
		}else{
			map.put("Date2", "");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 门店理财产品派息预算明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmInfoDividendShop(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String incomeMoneyGE=this.getRequest().getParameter("incomeMoney_GE");
		String incomeMoneyLE=this.getRequest().getParameter("incomeMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String departMentNameEQ=this.getRequest().getParameter("departMentName_EQ");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and pma.intentDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and pma.intentDate<=\""+intentDateLE+"\"";
		}
		
		if(null!=incomeMoneyGE && !"".equals(incomeMoneyGE)){
			sqlCondition=sqlCondition+" and pma.incomeMoney>=\""+incomeMoneyGE+"\"";
		}
		if(null!=incomeMoneyLE && !"".equals(incomeMoneyLE)){
			sqlCondition=sqlCondition+" and pma.incomeMoney<=\""+incomeMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and pma.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=departMentNameEQ && !"".equals(departMentNameEQ)){
			sqlCondition=sqlCondition+" and pmi.departMentName=\""+departMentNameEQ+"\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and pmi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and pmi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			map.put("Date1", intentDateGE);
			//param.append(" &Date1=").append(intentDateGE).append("");
		}else{
			map.put("Date1", "");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			map.put("Date2", intentDateLE);
			//param.append(" &Date2=").append(intentDateLE).append("");
		}else{
			map.put("Date2", "");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());

	}
	
	/**
	 * 门店理财产品派息明细表
	 * 后台传参方法
	 * @return
	 */
	public void reportPlmmInfoAlreadyShop(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String factDateGE=this.getRequest().getParameter("factDate_GE");
		String factDateLE=this.getRequest().getParameter("factDate_LE");
		String afterMoneyGE=this.getRequest().getParameter("afterMoney_GE");
		String afterMoneyLE=this.getRequest().getParameter("afterMoney_LE");
		String mmNameLK=this.getRequest().getParameter("mmName_LK");
		String departMentNameEQ=this.getRequest().getParameter("departMentName_EQ");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		String teamManageNameEQ=this.getRequest().getParameter("teamManageName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=factDateGE && !"".equals(factDateGE)){
			sqlCondition=sqlCondition+" and pma.factDate>=\""+factDateGE+"\"";
		}
		if(null!=factDateLE && !"".equals(factDateLE)){
			sqlCondition=sqlCondition+" and pma.factDate<=\""+factDateLE+"\"";
		}
		
		if(null!=afterMoneyGE && !"".equals(afterMoneyGE)){
			sqlCondition=sqlCondition+" and pma.afterMoney>=\""+afterMoneyGE+"\"";
		}
		if(null!=afterMoneyLE && !"".equals(afterMoneyLE)){
			sqlCondition=sqlCondition+" and pma.afterMoney<=\""+afterMoneyLE+"\"";
		}
		
		if(null!=mmNameLK && !"".equals(mmNameLK)){
			sqlCondition=sqlCondition+" and pma.mmName like \"%"+mmNameLK+"%\"";
		}
		
		if(null!=departMentNameEQ && !"".equals(departMentNameEQ)){
			sqlCondition=sqlCondition+" and pmi.departMentName=\""+departMentNameEQ+"\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and pmi.customerManagerName=\""+customerManagerNameEQ+"\"";
		}
		
		if(null!=teamManageNameEQ && !"".equals(teamManageNameEQ)){
			sqlCondition=sqlCondition+" and pmi.teamManageName=\""+teamManageNameEQ+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=factDateGE && !"".equals(factDateGE)){
			map.put("Date1", factDateGE);
			//param.append(" &Date1=").append(factDateGE).append("");
		}else{
			map.put("Date1", "");
			//param.append(" &Date1=").append("").append("");
		}
		if(null!=factDateLE && !"".equals(factDateLE)){
			map.put("Date2", factDateLE);
			//param.append(" &Date2=").append(factDateLE).append("");
		}else{
			map.put("Date2", "");
			//param.append(" &Date2=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 投资大客户累计投资统计表
	 * 后台传参方法
	 * @return
	 */
	public void getCsInvestmentpersonCount(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String factDateGE=this.getRequest().getParameter("factDate_GE");
		String factDateLE=this.getRequest().getParameter("factDate_LE");
		String buyMoney=this.getRequest().getParameter("buyMoney");
		String departMentName=this.getRequest().getParameter("departMentName");
		String customerManagerName=this.getRequest().getParameter("customerManagerName");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=buyMoney && !"".equals(buyMoney)){
			sqlCondition=sqlCondition+" and aaa.累计投资金额 >=\""+buyMoney+"\"";
		}
		if(null!=departMentName && !"".equals(departMentName)){
			sqlCondition=sqlCondition+" and aaa.所属门店 like \"%"+departMentName+"%\"";
		}
		if(null!=customerManagerName && !"".equals(customerManagerName)){
			sqlCondition=sqlCondition+" and aaa.客户经理 like \"%"+customerManagerName+"%\"";
		}
		SimpleDateFormat sdf  = new SimpleDateFormat("yyyy-MM-dd");
		map.put("Date1",sdf.format(new Date()));
		//sqlCondition=sqlCondition+" &Date1="+sdf.format(new Date());
		//页面参数
		if(null!=factDateGE && !"".equals(factDateGE)){
			//sqlCondition=sqlCondition+" &Date1="+factDateGE+"";
			map.put("Date1", factDateGE);
		}else{
			//sqlCondition=sqlCondition+" &Date1="+""+"";
			map.put("Date1","");
		}
		if(null!=factDateLE && !"".equals(factDateLE)){
			map.put("Date2", factDateLE);
			//sqlCondition=sqlCondition+" &Date2="+factDateLE+"";
		}else{
			//sqlCondition=sqlCondition+" &Date2="+""+"";
			map.put("Date2","");
		}
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 新增投资客户月度统计表 
	 * 后台传参方法
	 * @return
	 */
	public void reportCsInvestNewlyAdded(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String searchDateGE=this.getRequest().getParameter("searchDate_GE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=searchDateGE && !"".equals(searchDateGE)){
			sqlCondition=sqlCondition+" AND DATE_FORMAT(pb.buyDatetime,\"%Y-%m\")=\""+searchDateGE+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		//页面参数
		if(null!=searchDateGE && !"".equals(searchDateGE)){
			map.put("Date1", searchDateGE);
			//sqlCondition=sqlCondition+" &Date1="+searchDateGE+"";
		}else{
			map.put("Date1", "");
			//sqlCondition=sqlCondition+" &Date1="+""+"";
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	/**
	 * 投资客户即将生日表
	 * 后台传参方法
	 * @return
	 */
	public void reportBeGoingBirthday(){
		String reportType=this.getRequest().getParameter("reportType");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String DayGE=this.getRequest().getParameter("Day_GE");
		String DayLE=this.getRequest().getParameter("Day_LE");
		String departMentNameEQ=this.getRequest().getParameter("departMentName_EQ");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=DayGE && !"".equals(DayGE)){
			sqlCondition=sqlCondition+"  and DATEDIFF(date(concat(year(CURDATE()),\"-\",month(p.birthday),\"-\",day(p.birthday))),CURDATE()) >="+DayGE;
		}
		if(null!=DayLE && !"".equals(DayLE)){
			sqlCondition=sqlCondition+"  and DATEDIFF(date(concat(year(CURDATE()),\"-\",month(p.birthday),\"-\",day(p.birthday))),CURDATE()) <="+DayLE;
		}
		
		if(null!=departMentNameEQ && !"".equals(departMentNameEQ)){
			sqlCondition=sqlCondition+" and ci.shopName=\""+departMentNameEQ+"\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+"&sqlCondition1= and ci.creater=\""+customerManagerNameEQ+"\"";
		}
		
		//页面参数
		map.put("Date1",sdf.format(new Date()));
		//sqlCondition=sqlCondition+"&Date1="+sdf.format(new Date())+"";
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 投资客户明细表  
	 * 后台传参方法
	 * 查询条件是登记日期起止  所属门店  客户经理
	 * @return
	 */
	public void personDetailInfo(){
		try{
			String reportType=this.getRequest().getParameter("reportType");
			String reportKey=this.getRequest().getParameter("reportKey");//报表Key值
			//ci.createdate
			String searchDateS=this.getRequest().getParameter("searchDate_S");//登记开始日期
			String searchDateE=this.getRequest().getParameter("searchDate_E");//登记结束日期
			//ci.creater
			String managerName=this.getRequest().getParameter("managerName");//客户经理名称
			//ci.shopName
			String shopName=this.getRequest().getParameter("shopName");//所属门店名称
			Map<String,String> map=new HashMap<String,String>();
			//拼接sql参数
			String sqlCondition="";
			if(null!=searchDateS && !"".equals(searchDateS)){//登记开始日期
				sqlCondition=sqlCondition+" AND ci.createdate>=\""+searchDateS+"\"";
			}
			if(null!=searchDateE && !"".equals(searchDateE)){//登记结束日期
				sqlCondition=sqlCondition+" AND ci.createdate<=\""+searchDateE+"\"";
			}
			if(null!=managerName && !"".equals(managerName)){//客户经理名称
				sqlCondition=sqlCondition+" AND ci.creater  like \"%"+managerName+"%\"";
			}
			if(null!=shopName && !"".equals(shopName)){//所属门店名称
				sqlCondition=sqlCondition+" AND ci.shopName  like \"%"+shopName+"%\"";
			}
			String reportDate="";//报告日期
			//页面参数
			SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 "); 
			SimpleDateFormat sft = new SimpleDateFormat("yyyy-MM-dd"); 
			if(null!=searchDateS && !"".equals(searchDateS)){//报告开始日期
				reportDate=reportDate+sf.format(sft.parse(searchDateS))+" 至   ";
			}else{
				reportDate=reportDate+" 至    ";
			}
			if(null!=searchDateE && !"".equals(searchDateE)){//报告开始日期
				reportDate=reportDate+sf.format(sft.parse(searchDateE));
			}
			map.put("reportDate",reportDate);
			//sqlCondition=sqlCondition+" &reportDate="+reportDate+"";
			if(!"".equals(sqlCondition)){
				map.put("sqlCondition", sqlCondition);
			}
			ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 投资客户累计投资统计表
	 * 查询条件:累计投资起    至    、所属门店、客户经理
	 * @return
	 */
	public void reportCsInvestSum(){
		String reportType=this.getRequest().getParameter("reportType");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String reportKey=this.getRequest().getParameter("reportKey");
		String SumBuyMoneyGE=this.getRequest().getParameter("SumBuyMoney_GE");
		String SumBuyMoneyLE=this.getRequest().getParameter("SumBuyMoney_LE");
		String departMentNameEQ=this.getRequest().getParameter("departMentName_EQ");
		String customerManagerNameEQ=this.getRequest().getParameter("customerManagerName_EQ");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=SumBuyMoneyGE && !"".equals(SumBuyMoneyGE)){
			sqlCondition=sqlCondition+" and ccc.累计投资金额>=\""+SumBuyMoneyGE+"\"";
		}
		if(null!=SumBuyMoneyLE && !"".equals(SumBuyMoneyLE)){
			sqlCondition=sqlCondition+" and ccc.累计投资金额<=\""+SumBuyMoneyLE+"\"";
		}
		if(null!=departMentNameEQ && !"".equals(departMentNameEQ)){
			sqlCondition=sqlCondition+" and ccc.所属门店=\""+departMentNameEQ+"\"";
		}
		
		if(null!=customerManagerNameEQ && !"".equals(customerManagerNameEQ)){
			sqlCondition=sqlCondition+" and ccc.客户经理=\""+customerManagerNameEQ+"\"";
		}
		
		//页面参数
		map.put("searchDate_GE",sdf.format(new Date()));
		//sqlCondition=sqlCondition+" &searchDate_GE="+sdf.format(new Date())+"";
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
}