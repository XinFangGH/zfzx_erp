package com.report.InternetReport;
import java.util.HashMap;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.util.JSONUtils;

import org.junit.Test;

import com.hurong.core.util.DateUtil;
import com.hurong.core.util.JsonUtil;
import com.report.ReportUtil;
import com.sun.org.apache.commons.collections.ListUtils;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.util.JsonUtils;

public class InternetReportAction extends BaseAction{
	
	/**
	 * 新增会员明细表
	 * 后台传参方法
	 * @return
	 */
	public void financeEarning(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String recommandPerson=this.getRequest().getParameter("recommandPerson");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and member.registrationDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and member.registrationDate<=\""+intentDateLE+"\"";
		}
		if(null!=recommandPerson && !"".equals(recommandPerson)){
			sqlCondition=sqlCondition+" and cust.loginname  like\"%"+recommandPerson+"%\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 推荐会员明细表
	 * @return
	 */
	public void memberrecommend(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String recommandPerson=this.getRequest().getParameter("recommandPerson");
		String recommandNum=this.getRequest().getParameter("recommandNum");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and member.registrationDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and member.registrationDate<=\""+intentDateLE+"\"";
		}
		if(null!=recommandPerson && !"".equals(recommandPerson)){
			sqlCondition=sqlCondition+" and cust.loginname  like\"%"+recommandPerson+"%\"";
		}
		if(null!=recommandNum && !"".equals(recommandNum)){
			sqlCondition=sqlCondition+" and cust.plainpassword  like\"%"+recommandNum+"%\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 会员收益明细表
	 * @return
	 */
	public void financeComplex(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String loginname=this.getRequest().getParameter("loginname");
		String truename=this.getRequest().getParameter("truename");
		String recommandPerson=this.getRequest().getParameter("recommandPerson");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and bp.registrationDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and bp.registrationDate<=\""+intentDateLE+"\"";
		}
		if(null!=loginname && !"".equals(loginname)){
			sqlCondition=sqlCondition+" and bp.loginname  like\"%"+loginname+"%\"";
		}
		if(null!=truename && !"".equals(truename)){
			sqlCondition=sqlCondition+" and bp.truename  like\"%"+truename+"%\"";
		}
		if(null!=recommandPerson && !"".equals(recommandPerson)){
			sqlCondition=sqlCondition+" and cust.loginname  like\"%"+recommandPerson+"%\"";
		}
		sqlCondition=sqlCondition+" group by bp.id";
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}

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
	 * 推荐投资明细表
	 * @return
	 */
	public void getReportMemberRecommendInvest(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String loginname=this.getRequest().getParameter("loginName");
		String recommandNum=this.getRequest().getParameter("recommandNum");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and  b.registrationDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and  b.registrationDate<=\""+intentDateLE+"\"";
		}
		if(null!=loginname && !"".equals(loginname)){
			sqlCondition=sqlCondition+" and p.loginname  like\"%"+loginname+"%\"";
		}
		if(null!=recommandNum && !"".equals(recommandNum)){
			sqlCondition=sqlCondition+" and b.recommandPerson  like\"%"+recommandNum+"%\"";
		}
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
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
	 * 直投标,转让标 放款明细表
	 * @return
	 */
	public void getReportPlBidPlanDir(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidName=this.getRequest().getParameter("bidName");
		String bidNum=this.getRequest().getParameter("bidNum");
		String recommandPerson=this.getRequest().getParameter("recommandPerson");
		String bidMoney_GE=this.getRequest().getParameter("bidMoney_GE");
		String bidMoney_LE=this.getRequest().getParameter("bidMoney_LE");
		String proKeepType=this.getRequest().getParameter("proKeepType");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and plan.startIntentDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and plan.startIntentDate<=\""+intentDateLE+"\"";
		}
		
		if(null!=bidMoney_GE && !"".equals(bidMoney_GE)){
			sqlCondition=sqlCondition+" and plan.bidMoney>=\""+bidMoney_GE+"\"";
		}
		if(null!=bidMoney_LE && !"".equals(bidMoney_LE)){
			sqlCondition=sqlCondition+" and plan.bidMoney<=\""+bidMoney_LE+"\"";
		}
		if(null!=bidName && !"".equals(bidName)){
			sqlCondition=sqlCondition+" and plan.bidProName  like\"%"+bidName+"%\"";
		}
		if(null!=bidNum && !"".equals(bidNum)){
			sqlCondition=sqlCondition+" and plan.bidProNumber  like\"%"+bidNum+"%\"";
		}
		if(null!=recommandPerson && !"".equals(recommandPerson)){
			if(reportKey!=null&&!reportKey.equals("")&&reportKey.equals("ReportPlBidPlanOr")){
				sqlCondition=sqlCondition+" and (persion.receiverName like   \"%"+recommandPerson+"%\"";
				sqlCondition=sqlCondition+" or business.receiverName like \"%"+recommandPerson+"%\"";
				sqlCondition=sqlCondition+")";
			}else{
				sqlCondition=sqlCondition+" and plan.receiverName=\""+recommandPerson+"\"";
			}
		}
		if(null!=proKeepType && !"".equals(proKeepType)){
			sqlCondition=sqlCondition+" and plan.proKeepType=\""+proKeepType+"\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 满标时间分析表
	 * @return
	 */
	public void getReportPlBidFullDate(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidName=this.getRequest().getParameter("bidName");
		String bidNum=this.getRequest().getParameter("bidNum");
		String bidMoney_GE=this.getRequest().getParameter("bidMoney_GE");
		String bidMoney_LE=this.getRequest().getParameter("bidMoney_LE");
		String investNum_GE=this.getRequest().getParameter("investNum_GE");
		String investNum_LE=this.getRequest().getParameter("investNum_LE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and plan.fullTime>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and plan.fullTime<=\""+intentDateLE+"\"";
		}
		
		if(null!=bidMoney_GE && !"".equals(bidMoney_GE)){
			sqlCondition=sqlCondition+" and plan.bidMoney>=\""+bidMoney_GE+"\"";
		}
		if(null!=bidMoney_LE && !"".equals(bidMoney_LE)){
			sqlCondition=sqlCondition+" and plan.bidMoney<=\""+bidMoney_LE+"\"";
		}
		
		if(null!=investNum_GE && !"".equals(investNum_GE)){
			sqlCondition=sqlCondition+" and info.userId>=\""+investNum_GE+"\"";
		}
		if(null!=investNum_LE && !"".equals(investNum_LE)){
			sqlCondition=sqlCondition+" and info.userId<=\""+investNum_LE+"\"";
		}
		if(null!=bidName && !"".equals(bidName)){
			sqlCondition=sqlCondition+" and plan.bidProName  like\"%"+bidName+"%\"";
		}
		if(null!=bidNum && !"".equals(bidNum)){
			sqlCondition=sqlCondition+" and plan.bidProNumber  like\"%"+bidNum+"%\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 债权交易明细表
	 * @return
	 */
	public void getReportCreditorDeal(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidProName=this.getRequest().getParameter("bidProName");
		String outCustName=this.getRequest().getParameter("outCustName");
		String inCustName=this.getRequest().getParameter("inCustName");
		String saleMoney_GE=this.getRequest().getParameter("saleMoney_GE");
		String saleMoney_LE=this.getRequest().getParameter("saleMoney_LE");
		String changeMoneyType=this.getRequest().getParameter("changeMoneyType");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and sale.saleSuccessTime>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and sale.saleSuccessTime<=\""+intentDateLE+"\"";
		}
		
		if(null!=saleMoney_GE && !"".equals(saleMoney_GE)){
			sqlCondition=sqlCondition+" and sale.saleMoney>=\""+saleMoney_GE+"\"";
		}
		if(null!=saleMoney_LE && !"".equals(saleMoney_LE)){
			sqlCondition=sqlCondition+" and sale.saleMoney<=\""+saleMoney_LE+"\"";
		}
		if(null!=bidProName && !"".equals(bidProName)){
			sqlCondition=sqlCondition+" and plan.bidProName  like\"%"+bidProName+"%\"";
		}
		if(null!=outCustName && !"".equals(outCustName)){
			sqlCondition=sqlCondition+" and sale.outCustName  like\"%"+outCustName+"%\"";
		}
		if(null!=inCustName && !"".equals(inCustName)){
			sqlCondition=sqlCondition+" and sale.inCustName  like\"%"+inCustName+"%\"";
		}
		if(null!=changeMoneyType && !"".equals(changeMoneyType)){
			if(changeMoneyType.equals("折价")){
				sqlCondition=sqlCondition+" and sale.changeMoneyType=\""+"1"+"\"";
			}else{
				sqlCondition=sqlCondition+" and sale.changeMoneyType=\""+"0"+"\"";
			}
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 散标到期，逾期，款项明细表
	 * @return
	 */
	public void getReportPlBidExpire(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidProName=this.getRequest().getParameter("bidProName");
		String receiverName=this.getRequest().getParameter("receiverName");
		String bidProNumber=this.getRequest().getParameter("bidProNumber");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and p.intentDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and p.intentDate<=\""+intentDateLE+"\"";
		}
		if(null!=bidProName && !"".equals(bidProName)){
			sqlCondition=sqlCondition+" and plan.bidProName  like\"%"+bidProName+"%\"";
		}
		if(null!=receiverName && !"".equals(receiverName)){
			sqlCondition=sqlCondition+" and plan.receiverName  like\"%"+receiverName+"%\"";
		}
		if(null!=bidProNumber && !"".equals(bidProNumber)){
			sqlCondition=sqlCondition+" and plan.bidProNumber  like\"%"+bidProNumber+"%\"";
		}
		sqlCondition=sqlCondition+" GROUP BY	p.bidPlanId,p.payintentPeriod";
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 平台收益明细表
	 * @return
	 */
	public void getReportTerraceIncome(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidProName=this.getRequest().getParameter("bidProName");
		String receiverName=this.getRequest().getParameter("receiverName");
		String bidProNumber=this.getRequest().getParameter("bidProNumber");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and plan.endIntentDate>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and plan.endIntentDate<=\""+intentDateLE+"\"";
		}
		if(null!=bidProName && !"".equals(bidProName)){
			sqlCondition=sqlCondition+" and plan.bidProName  like\"%"+bidProName+"%\"";
		}
		if(null!=receiverName && !"".equals(receiverName)){
			sqlCondition=sqlCondition+" and plan.receiverName  like\"%"+receiverName+"%\"";
		}
		if(null!=bidProNumber && !"".equals(bidProNumber)){
			sqlCondition=sqlCondition+" and plan.bidProNumber  like\"%"+bidProNumber+"%\"";
		}
		sqlCondition=sqlCondition+" GROUP BY	p.bidPlanId,p.payintentPeriod";
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 理财计划明细表
	 * @return
	 */
	public void getReportManagePlan(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String buyMoney_GE=this.getRequest().getParameter("buyMoney_GE");
		String buyMoney_LE=this.getRequest().getParameter("buyMoney_LE");
		String investPersonName=this.getRequest().getParameter("investPersonName");
		String mmName=this.getRequest().getParameter("mmName");
		String mmNumber=this.getRequest().getParameter("mmNumber");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and buy.startinInterestTime>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and buy.startinInterestTime<=\""+intentDateLE+"\"";
		}
		if(null!=buyMoney_GE && !"".equals(buyMoney_GE)){
			sqlCondition=sqlCondition+" and buy.buyMoney>=\""+buyMoney_GE+"\"";
		}
		if(null!=buyMoney_LE && !"".equals(buyMoney_LE)){
			sqlCondition=sqlCondition+" and buy.buyMoney<=\""+buyMoney_LE+"\"";
		}
		
		
		if(null!=mmName && !"".equals(mmName)){
			sqlCondition=sqlCondition+" and buy.mmName  like\"%"+mmName+"%\"";
		}
		if(null!=mmNumber && !"".equals(mmNumber)){
			sqlCondition=sqlCondition+" and plan.mmNumber  like\"%"+mmNumber+"%\"";
		}
		if(null!=investPersonName && !"".equals(investPersonName)){
			sqlCondition=sqlCondition+" and buy.investPersonName  like\"%"+investPersonName+"%\"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 平台交易明细
	 * @return
	 */
	public void getReportplatformDeail(){
		String reportType=this.getRequest().getParameter("reportType");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		String reportKey=this.getRequest().getParameter("reportKey");
		String year=this.getRequest().getParameter("year");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(year!=null&&!year.equals("")){
			sqlCondition=sqlCondition+"="+year;
		}else{
			sqlCondition=sqlCondition+"="+sdf1.format(new Date());
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		map.put("Date1", sdf.format(new Date()));
		//param.append(" &Date1=").append(sdf.format(new Date())).append("");
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 理财计划到期款项汇总表
	 * 后台传参方法
	 * @return
	 */
	public void assigninterestExpire(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and p.intentDate>=date_sub(\""+intentDateGE+"\",INTERVAL DAY(\""+intentDateGE+"\")-1 DAY)"+"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and p.intentDate<=LAST_DAY(\""+intentDateLE+"\")";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 平台随息费汇总表
	 * 后台传参方法
	 * @return
	 */
	public void platformWithInterest(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+"\""+intentDateGE+"\"";
		}else{
			sqlCondition=sqlCondition+"CURDATE()"+"";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			map.put("Date1", intentDateGE);
			//param.append(" &Date1=").append(intentDateGE).append("");
		}else{
			map.put("Date1", "");
			//param.append(" &Date1=").append("").append("");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	
	/**
	 * 散标到期款项汇总表
	 * 后台传参方法
	 * @return
	 */
	public void intentExpire(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and p.intentDate>=date_sub(\""+intentDateGE+"\",INTERVAL DAY(\""+intentDateGE+"\")-1 DAY)"+"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and p.intentDate<=LAST_DAY(\""+intentDateLE+"\")";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 债权标放款汇总表
	 * 后台传参方法
	 * @return
	 */
	public void orIntentExpire(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and p.bidStartTime>=date_sub(\""+intentDateGE+"\",INTERVAL DAY(\""+intentDateGE+"\")-1 DAY)"+"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and p.bidStartTime<=LAST_DAY(\""+intentDateLE+"\")";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 直投标放款汇总表
	 * 后台传参方法
	 * @return
	 */
	public void dirIntentExpire(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and p.bidStartTime>=date_sub(\""+intentDateGE+"\",INTERVAL DAY(\""+intentDateGE+"\")-1 DAY)"+"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and p.bidStartTime<=LAST_DAY(\""+intentDateLE+"\")";
		}
		
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
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
	 * 借款标统计表
	 * @return
	 */
	public void getReportLoanBid(){
		String reportType=this.getRequest().getParameter("reportType");
		String reportKey=this.getRequest().getParameter("reportKey");
		String intentDateGE=this.getRequest().getParameter("intentDate_GE");
		String intentDateLE=this.getRequest().getParameter("intentDate_LE");
		String bidProName=this.getRequest().getParameter("bidProName");//标的名称
		String bidProNumber=this.getRequest().getParameter("bidProNumber");//项目编号
		Map<String,String> map=new HashMap<String,String>();
		//拼接sql参数
		String sqlCondition="";
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			sqlCondition=sqlCondition+" and DATE(plan.endIntentDate)>=\""+intentDateGE+"\"";
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			sqlCondition=sqlCondition+" and DATE(plan.endIntentDate)<=\""+intentDateLE+"\"";
		}
		if(null!=bidProName && !"".equals(bidProName)){
			sqlCondition=sqlCondition+" and plan.bidProName LIKE \"%"+bidProName+"%\"";
		}
		if(null!=bidProNumber && !"".equals(bidProNumber)){
			sqlCondition=sqlCondition+" and plan.bidProNumber LIKE \"%"+bidProNumber+"%\"";
		}
		if(!"".equals(sqlCondition)){
			map.put("sqlCondition", sqlCondition);
		}
		
		if(null!=intentDateGE && !"".equals(intentDateGE)){
			map.put("Date1", intentDateGE);
		}else{
			map.put("Date1", "");
		}
		if(null!=intentDateLE && !"".equals(intentDateLE)){
			map.put("Date2", intentDateLE);
		}else{
			map.put("Date2", "");
		}
		ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
	}
	/**
	 * 投资、融资部保有量报表
	 */
    public void getReportReMainMoney(){
        String reportType=this.getRequest().getParameter("reportType");
        String reportKey=this.getRequest().getParameter("reportKey");
        String intentDateGE=this.getRequest().getParameter("intentDate_GE");
        String intentDateLE=this.getRequest().getParameter("intentDate_LE");
        String bidProName=this.getRequest().getParameter("bidProName");//标的名称
        String bidProNumber=this.getRequest().getParameter("bidProNumber");//项目编号
        Map<String,String> map=new HashMap<String,String>();
        //拼接sql参数
        String sqlCondition="";
        if(null!=intentDateGE && !"".equals(intentDateGE)){
            sqlCondition=sqlCondition+" and DATE(os.transferDate)>=\""+intentDateGE+"\"";
        }
        if(null!=intentDateLE && !"".equals(intentDateLE)){
            sqlCondition=sqlCondition+" and DATE(os.transferDate)<=\""+intentDateLE+"\"";
        }
        if(null!=bidProName && !"".equals(bidProName)){
            sqlCondition=sqlCondition+" and plan.bidProName LIKE \"%"+bidProName+"%\"";
        }
        if(null!=bidProNumber && !"".equals(bidProNumber)){
            sqlCondition=sqlCondition+" and plan.bidProNumber LIKE \"%"+bidProNumber+"%\"";
        }
        if(!"".equals(sqlCondition)){
            map.put("sqlCondition", sqlCondition);
        }
        
        if(null!=intentDateGE && !"".equals(intentDateGE)){
            map.put("Date1", intentDateGE);
        }else{
            map.put("Date1", "");
        }
        if(null!=intentDateLE && !"".equals(intentDateLE)){
            map.put("Date2", intentDateLE);
        }else{
            map.put("Date2", "");
        }
        ReportUtil.createReportHtml(map,reportKey,reportType,  this.getRequest());
    }
}