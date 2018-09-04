package com.highchart.service.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.highchart.dao.HighchartDao;
import com.highchart.model.Highchart;
import com.highchart.model.HighchartModel;
import com.highchart.service.HighchartService;
import com.util.NumberFormat.NumberFormat;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.util.Common;

public class HighchartServiceImpl extends BaseServiceImpl<Highchart> implements HighchartService {
	@SuppressWarnings("unused")
	private HighchartDao dao;
	public HighchartServiceImpl(HighchartDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public void findList(PageBean<Highchart> pageBean) {
		dao.findList(pageBean);
	}
	@Override
	public void findUserTrend(PageBean<Highchart> pageBean) {
		dao.findUserTrend(pageBean);
	}

	@Override
	public void findUserInvest(PageBean<Highchart> pageBean) {
		dao.findUserInvest(pageBean);
		
	}
	@Override
	public void findOverdue(PageBean<Highchart> pageBean) {
		dao.findOverdue(pageBean);
	}
	@Override
	public Highchart findSomeInfoWealth(PageBean<Highchart> pageBean) {
		Highchart highchart = new Highchart();
		//查询本月订单交易信息
		dao.findSomeInfoWealth(pageBean);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			Highchart highchart1 = pageBean.getResult().get(0);
			Highchart highchart2 = pageBean.getResult().get(1);
			//环比增长率=（本期数－上期数）/上期数×100%
			
			//本月交易环比增长率
			//除数为不能为零
			if(highchart1.getMoneyA().compareTo(new BigDecimal("0")) == 1){
				highchart.setPercentA((highchart2.getMoneyA().subtract(highchart1.getMoneyA()))
						.divide(highchart1.getMoneyA(),2,BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentA(new BigDecimal("1000"));
			}
			//本月交易金额NumberFormat.numberFormat(higchartList2.getMoneyB(), "元")
			highchart.setNameA(NumberFormat.numberFormat(highchart2.getMoneyA(),"元"));
			
			//本月成单环比增长率
			//除数为不能为零
			if(highchart1.getSumA()>0){
				highchart.setPercentB(new BigDecimal((highchart2.getSumA()-highchart1.getSumA())/highchart1.getSumA())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentB(new BigDecimal("1000"));
			}
			//本月成单笔数
			highchart.setSumA(highchart2.getSumA());
		}
		return highchart;
	}
	
	@Override
	public Highchart findSomePerson(PageBean<Highchart> pageBean) {
		Highchart highchart = new Highchart();
		//环比增长率=（本期数－上期数）/上期数×100%
		
		//上月正式客户数据
		Highchart highchart1 = new Highchart();
		//本月正式客户数据
		Highchart highchart2 = new Highchart();
		//上月意向客户数据与上月转化客户数据
		Highchart highchart3 = new Highchart();
		//本月意向客户数据与本月转化客户数据
		Highchart highchart4 = new Highchart();
		
		//查询本月新增正式客户数量
		dao.findSomeFormalPerson(pageBean);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1 = pageBean.getResult().get(0);
			highchart2 = pageBean.getResult().get(1);
			//本月新增正式客户环比增长率
			//除数为不能为零
			if(highchart1.getSumA() > 0){
				highchart.setPercentA(new BigDecimal((highchart2.getSumA()-highchart1.getSumA())/highchart1.getSumA())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentA(new BigDecimal("1000"));
			}
			//本月新增正式客户数量
			highchart.setSumA(highchart2.getSumA());
		}
		
		//查询本月新增意向客户数量
		dao.findSomeIntentionPerson(pageBean);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart3 = pageBean.getResult().get(0);
			highchart4 = pageBean.getResult().get(1);
			//本月新增意向客户环比增长率
			//除数为不能为零
			if(highchart3.getSumA() > 0){
				highchart.setPercentB(new BigDecimal((highchart4.getSumA()-highchart3.getSumA())/highchart3.getSumA())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentB(new BigDecimal("1000"));
			}
			//本月新增意向客户数量
			highchart.setSumB(highchart4.getSumA());
		    
			//本月本月转化客户数量
			//本月本月转化客户环比增长率
			if(highchart3.getSumB() > 0){
				highchart.setPercentC(new BigDecimal((highchart4.getSumB()-highchart3.getSumB())/highchart3.getSumB())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentC(new BigDecimal("1000"));
			}
			highchart.setSumC(highchart4.getSumB());
		}
		return highchart;
	}
	
	@Override
	public void findSomeRanking(PageBean<Highchart> pageBean) {
		dao.findSomeRanking(pageBean);
	}
	@Override
	public void findSomeToChampion(PageBean<Highchart> pageBean) {
		dao.findSomeToChampion(pageBean);
	}
	@Override
	public void findSomeEarlyRedemption(PageBean<Highchart> pageBean) {
		dao.findSomeEarlyRedemption(pageBean);
	}
	@Override
	public void findSomeYearStatistics(PageBean<Highchart> pageBean) {
		dao.findSomeYearStatistics(pageBean);
	}
	@Override
	public void findSomeMonthTypeStatistics(PageBean<Highchart> pageBean) {
		dao.findSomeMonthTypeStatistics(pageBean);
	}



	@Override
	public void getRegUser(PageBean<Highchart> pageBean) {
		dao.getRegUser(pageBean);
		List<Highchart> highchartList = new ArrayList<Highchart>();
		if(pageBean.getResult().size()>0){
			//新增注册
			Highchart highchart1 = new Highchart();
			//实名认证
			Highchart highchart2 = new Highchart();
			//上一个月数据
			Highchart higchartList1 = pageBean.getResult().get(0);
			//本月数据
			Highchart higchartList2 = pageBean.getResult().get(1);
			//计算注册人数环比比例
			if(higchartList1.getSumA() > 0){
				highchart1.setPercentA(new BigDecimal((higchartList2.getSumA()-higchartList1.getSumA())/higchartList1.getSumA())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart1.setPercentA(new BigDecimal("0"));
			}
			highchart1.setSumA(higchartList2.getSumA());
			highchartList.add(highchart1);
			//计算实名认证人数环比比例
			if(higchartList1.getSumB() > 0){
				highchart2.setPercentB(new BigDecimal((higchartList2.getSumB()-higchartList1.getSumB())/higchartList1.getSumB())
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart2.setPercentB(new BigDecimal("0"));
			}
			highchart2.setSumB(higchartList2.getSumB());
			highchartList.add(highchart2);
		}
		pageBean.setResult(highchartList);
	}
	@Override
	public void getObAccountDeal(PageBean<Highchart> pageBean) {
		dao.getObAccountDeal(pageBean);
		List<Highchart> highchartList = new ArrayList<Highchart>();
		if(pageBean.getResult().size()>0){
			//新增充值金额
			Highchart highchart1 = new Highchart();
			//新增提现金额
			Highchart highchart2 = new Highchart();
			//新增投资金额
			Highchart highchart3 = new Highchart();
			//上一个月数据
			Highchart higchartList1 = pageBean.getResult().get(0);
			//本月数据
			Highchart higchartList2 = pageBean.getResult().get(1);
			//计算充值金额环比比例
			if(higchartList1.getMoneyA().compareTo(new BigDecimal("0"))>0){
				highchart1.setPercentA((higchartList2.getMoneyA().subtract(higchartList1.getMoneyA()).multiply(higchartList1.getMoneyA()))
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart1.setPercentA(new BigDecimal("0"));
			}
			highchart1.setNameA(NumberFormat.numberFormat(higchartList2.getMoneyA(), "元"));
			System.out.println("highchart1.setNameA="+highchart1.getNameA());
			highchartList.add(highchart1);
			//计算提现金额环比比例
			if(higchartList1.getMoneyB().compareTo(new BigDecimal("0"))>0){
				highchart2.setPercentB((higchartList2.getMoneyB().subtract(higchartList1.getMoneyB()).multiply(higchartList1.getMoneyB()))
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart2.setPercentB(new BigDecimal("0"));
			}
			highchart2.setNameB(NumberFormat.numberFormat(higchartList2.getMoneyB(), "元"));
			highchartList.add(highchart2);
			//计算投资金额环比比例
			if(higchartList1.getMoneyC().compareTo(new BigDecimal("0"))>0){
				highchart3.setPercentC((higchartList2.getMoneyC().subtract(higchartList1.getMoneyC()).multiply(higchartList1.getMoneyC()))
				.setScale(2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart3.setPercentC(new BigDecimal("0"));
			}
			highchart3.setNameC(NumberFormat.numberFormat(higchartList2.getMoneyC(), "元"));
			highchartList.add(highchart3);
		}
		pageBean.setResult(highchartList);
	}
	@Override
	public void getPlearlyRedemptionNum(PageBean<Highchart> pageBean) {
		dao.getPlearlyRedemptionNum(pageBean);
		
	}
	@Override
	public void getPlearlyRedemptionList(PageBean<Highchart> pageBean) {
		dao.getPlearlyRedemptionList(pageBean);
	}
	
	@Override
	public void getPlanManageState(PageBean<Highchart> pageBean) {
		dao.getPlanManageState(pageBean);
		
	}
	
	@Override
	public void getPlanManageMarket(PageBean<Highchart> pageBean) {
		dao.getPlanManageMarket(pageBean);
		
	}
	
	@Override
	public void getPlanFundAnalyze(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.getPlanFundAnalyze(pageBean);
		//计算当月债权金额
		List<Highchart> highchart = pageBean.getResult();
		for(Highchart hc:highchart){
			BigDecimal money = dao.getPlanChildren(hc.getSearchDate().toString());
			//计算差额
			BigDecimal ceMoney = money.subtract(hc.getMoneyA());
			hc.setMoneyA(hc.getMoneyA().divide(new BigDecimal("10000")));
			hc.setMoneyB(ceMoney.divide(new BigDecimal("10000")));
			hc.setMoneyC(money.divide(new BigDecimal("10000")));
			
		}
	}
	
	@Override
	public void getPlanProportion(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.getPlanProportion(pageBean);
		//查询每个类型的记录
		List<Highchart> highchart = pageBean.getResult();
		for(Highchart hc:highchart){
			Long count = dao.getPlanType(Long.valueOf(hc.getRemarkA()));
			//计算占比
			BigDecimal zb = new BigDecimal(hc.getSumA()).multiply(new BigDecimal(100)).divide(new BigDecimal(count),2,BigDecimal.ROUND_HALF_UP);
			hc.setPercentA(zb);
		}
	}
	/**
	 *查询线下借款业务统计数据 
	 * @param pageBean
	 * @return
	 */
	@Override
	public Highchart findLoanData(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		Highchart highchart = new Highchart();//
		//环比增长率=（本期数－上期数）/上期数×100%
		//上月统计数据
		Highchart highchart1 = new Highchart();
		//本月统计数据
		Highchart highchart2 = new Highchart();
		//上月数据转化类型
		BigDecimal b1= new BigDecimal("0");
		//本月数据转化类型
		BigDecimal b2= new BigDecimal("0");
	
		//查询本月意向客户数量
		dao.findSomeIntentionPerson(pageBean, userId);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1 = pageBean.getResult().get(0);
			highchart2 = pageBean.getResult().get(1);
			//本月新增意向客户环比增长率
			//除数为不能为零
			if(highchart1.getSumA() > 0){
				b1=BigDecimal.valueOf(highchart1.getSumA());
				b2=BigDecimal.valueOf(highchart2.getSumA());
				   highchart.setPercentA(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentA(new BigDecimal("0"));
			}
			//本月新增正式客户数量
			highchart.setSumA(highchart2.getSumA());
		}
		
		//查询本月新增正式客户数量
		dao.findLoanCustomer(pageBean, userId);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1 = pageBean.getResult().get(0);
			highchart2 = pageBean.getResult().get(1);
			//本月新增正式客户环比增长率
			//除数为不能为零   金额
			if(highchart1.getSumA() > 0){
				b1=BigDecimal.valueOf(highchart1.getSumA());
				b2=BigDecimal.valueOf(highchart2.getSumA());
					   highchart.setPercentB(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			 }else{
				highchart.setPercentB(new BigDecimal("0"));
			}
			//本月新增正式客户数量
			highchart.setSumB(highchart2.getSumA());
		}
		//查询本月新增贷款数据
		dao.findLoanMoneyAmount(pageBean, userId, Short.valueOf("1"));
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1 = pageBean.getResult().get(0);
			highchart2 = pageBean.getResult().get(1);
			//本月新增贷款环比增长率
			//除数为不能为零
			if(highchart1.getMoneyA().compareTo(new BigDecimal("0"))>0){
				b1=highchart1.getMoneyA();
				b2=highchart2.getMoneyA();
					highchart.setPercentC(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentC(new BigDecimal("0"));
			}
			//本月新增贷款金额
		//	highchart.setMoneyA(highchart2.getMoneyA().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
			highchart.setNameA(NumberFormat.numberFormat(highchart2.getMoneyA(), "元"));
			
			//除数为不能为零  笔数
			if(highchart1.getSumA() > 0){
				b1=BigDecimal.valueOf(highchart1.getSumA());
				b2=BigDecimal.valueOf(highchart2.getSumA());
			    	highchart.setPercentE(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentE(new BigDecimal("0"));
			}
			//本月新增贷款数量
			highchart.setSumE(highchart2.getSumA());
		}
		
		//查询本月驳回贷款数据
		dao.findLoanMoneyAmount(pageBean, userId, Short.valueOf("3"));
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1 = pageBean.getResult().get(0);
			highchart2 = pageBean.getResult().get(1);
			//本月新增贷款环比增长率
			//除数为不能为零
			if(highchart1.getMoneyA().compareTo(new BigDecimal("0"))>0){
				b1=highchart1.getMoneyA();
				b2=highchart2.getMoneyA();
					highchart.setPercentD(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentD(new BigDecimal("0"));
			}
			//本月驳回贷款金额
			//highchart.setMoneyB(highchart2.getMoneyA().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
			highchart.setNameB(NumberFormat.numberFormat(highchart2.getMoneyA(), "元"));
			
			//除数为不能为零  笔数
			if(highchart1.getSumA() > 0){
				b1=BigDecimal.valueOf(highchart1.getSumA());
				b2=BigDecimal.valueOf(highchart2.getSumA());
					highchart.setPercentF(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
			}else{
				highchart.setPercentF(new BigDecimal("0"));
			}
			//本月新增正式客户数量
			highchart.setSumF(highchart2.getSumA());
		}
		return highchart;
	}
	@Override
	public void findOverdueMoney(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		Highchart highchart = new Highchart();//
		//逾期款项金额
		Highchart highchart1 = new Highchart();
		//已收款项金额
		Highchart highchart2 = new Highchart();
        List<Highchart> list=new ArrayList<Highchart>();
		//查询本月意向客户数量
		dao.findOverdueMoney(pageBean, userId);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart = pageBean.getResult().get(0);
			highchart1.setNameA("逾期还款比例");
			highchart2.setNameA("正常还款比例");
				highchart1.setMoneyA(highchart.getMoneyA().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart2.setMoneyA(highchart.getMoneyB().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
		
	
		}
	    list.add(highchart1);
	    list.add(highchart2);
	    pageBean.setResult(list);
		
	}
	@Override
	public void findLoneRemind(PageBean<Highchart> pageBean, Long userId,
			Short projectStatus) {
		// TODO Auto-generated method stub
		dao.findLoneRemind(pageBean, userId, projectStatus);
		
	}
	@Override
	public void findOverdueRemind(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		dao.findOverdueRemind(pageBean, userId);
	}
	@Override
	public void findLoneRanking(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.findLoneRanking(pageBean);
		}
	@Override
	public void findLoanYearStatistics(PageBean<Highchart> pageBean,
			Long userId, Short projectStatus) {
		// TODO Auto-generated method stub
		dao.findLoanYearStatistics(pageBean, userId, projectStatus);
		
	}
	@Override
	public void findRiskControl(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		Highchart highchart = new Highchart();//
		//关注贷款金额
		Highchart highchart1 = new Highchart();
		//次级贷款金额
		Highchart highchart2 = new Highchart();
		//可疑贷款金额
		Highchart highchart3 = new Highchart();
		//损失贷款金额
		Highchart highchart4 = new Highchart();
		//正常贷款金额
		Highchart highchart5 = new Highchart();
		
        List<Highchart> list=new ArrayList<Highchart>();
		
		dao.findRiskControl(pageBean);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart = pageBean.getResult().get(0);
			highchart1.setNameA("关注贷款");
			highchart2.setNameA("次级贷款");
			highchart3.setNameA("可疑贷款");
			highchart4.setNameA("损失贷款");
			highchart5.setNameA("正常贷款");
				highchart1.setMoneyA(highchart.getMoneyA().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart2.setMoneyA(highchart.getMoneyB().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart3.setMoneyA(highchart.getMoneyC().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart4.setMoneyA(highchart.getMoneyD().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart5.setMoneyA(highchart.getMoneyE().divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
		}
	    list.add(highchart1);
	    list.add(highchart2);
	    list.add(highchart3);
	    list.add(highchart4);
	    list.add(highchart5);
	    pageBean.setResult(list);
		
	}
	@Override
	public void findLoanOverdueStatistics(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.findLoanOverdueStatistics(pageBean);
	}
	@Override
	public Highchart findOwnRanking(PageBean<Highchart> pageBean, Long userId) {
		// TODO Auto-generated method stub
		Highchart highchart = new Highchart();//
		//业务排名
		Highchart highchart1 = new Highchart();
		dao.findOwnRanking(pageBean, userId, "%Y-%m");
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart1 = pageBean.getResult().get(0);
			highchart.setRankingA(highchart1.getRankingA());
		}
		dao.findOwnRanking(pageBean, userId, "%Y");
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart1 = pageBean.getResult().get(0);
			highchart.setRankingB(highchart1.getRankingA());
		}
		return highchart;
	}
	@Override
	public Highchart findFinancialData(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		Highchart highchart = new Highchart();//
		//环比增长率=（本期数－上期数）/上期数×100%
		//上月统计数据
		Highchart highchart1 = new Highchart();
		//本月统计数据
		Highchart highchart2 = new Highchart();
		//上月数据转化类型
		BigDecimal b1= new BigDecimal("0");
		//本月数据转化类型
		BigDecimal b2= new BigDecimal("0");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date=new Date();
		String sdate=format.format(date);
		String smonth="";
		String [] array=sdate.split("-");
		String day=array[2];
		String year=array[0];
		String month=array[1];
		Integer	sm=Integer.valueOf(array[1])-1;
		if(sm<10){
			smonth="0"+sm.toString();
		}else{
			smonth=sm.toString();
		}
		//本月应收
		dao.findFinancialLoanMoney(pageBean);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 1){
			highchart1=pageBean.getResult().get(0);
			highchart2=pageBean.getResult().get(1);
			//除数为不能为零
			if(highchart1.getMoneyA().compareTo(new BigDecimal("0"))>0){
				b1=highchart1.getMoneyA();
				b2=highchart2.getMoneyA();
					highchart.setPercentA(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				
			}else{
				highchart.setPercentA(new BigDecimal("0"));
			}
			highchart.setNameA(NumberFormat.numberFormat(highchart2.getMoneyA(), "元"));
			
		}
		
		//本月实收金额
		dao.findFinancialAfterMoney(pageBean, year+"-"+month+"-01", sdate);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart2 = pageBean.getResult().get(0);
			//截止今天上月同日实收金额
			dao.findFinancialAfterMoney(pageBean, year+"-"+smonth+"-01", year+"-"+smonth+"-"+day);
			if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
				highchart1 = pageBean.getResult().get(0);
				b2=(highchart2.getMoneyA()!=null)?highchart2.getMoneyA():new BigDecimal(0);
				b1=(highchart1.getMoneyA()!=null)?highchart1.getMoneyA():new BigDecimal(0);
				if(b1.compareTo(new BigDecimal("0"))>0){
						   highchart.setPercentB(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}else{
					
					highchart.setPercentB(new BigDecimal("999"));
				}
				highchart.setNameB(NumberFormat.numberFormat(b2, "元"));
			}
		}
		
		//截止今天本月今日欠收金额
    	dao.findFinancialNotMoney(pageBean, null, sdate);
		if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
			highchart2 = pageBean.getResult().get(0);
			//截止今天上月同日欠收金额
			dao.findFinancialNotMoney(pageBean, null, year+"-"+smonth+"-"+day);
			if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
				highchart1 = pageBean.getResult().get(0);
				b2=highchart2.getMoneyA();
				b1=highchart1.getMoneyA();
				if(b1.compareTo(new BigDecimal("0"))>0){
						   highchart.setPercentC(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
				}else{
					
					highchart.setPercentC(new BigDecimal("0"));
				}
			//	highchart.setMoneyC(b2.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
				highchart.setNameC(NumberFormat.numberFormat(b2, "元"));
			}
		}
			//截止今天本月今日实收金额
	    	dao.findFinancialNotMoney(pageBean, year+"-"+month+"-01", sdate);
			if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
				highchart2 = pageBean.getResult().get(0);
				//截止今天上月同日欠收金额
				dao.findFinancialNotMoney(pageBean, year+"-"+smonth+"-01", year+"-"+smonth+"-"+day);
				if(pageBean.getResult() != null && pageBean.getResult().size() > 0){
					highchart1 = pageBean.getResult().get(0);
					b2=highchart2.getMoneyA();
					b1=highchart1.getMoneyA();
					if(b1.compareTo(new BigDecimal("0"))>0){
							   highchart.setPercentD(b2.subtract(b1).divide(b1, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")));
					}else{
						
						highchart.setPercentD(new BigDecimal("0"));
					}
					//highchart.setMoneyD(b2.divide(new BigDecimal("10000"),2,BigDecimal.ROUND_HALF_UP));
					highchart.setNameD(NumberFormat.numberFormat(b2, "元"));
					
				}
			}
		return highchart;
	}
	@Override
	public void findFundQlide(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.findFundQlide(pageBean);
	}
	@Override
	public void findIncomePayStatistics(PageBean<Highchart> pageBean) {
		// TODO Auto-generated method stub
		dao.findIncomePayStatistics(pageBean);
	}
	
	/**
	 * 散标桌面（线上散标异常情况统计）
	 */
	@Override
	public void bidExceptionMap(PageBean<HighchartModel> pageBean) {
		// TODO Auto-generated method stub
		dao.bidExceptionMap(pageBean);
	}
	
	/**
	 * 散标桌面（线上散标销售情况统计）
	 */
	@Override
	public void bidSaleStatistics(PageBean<HighchartModel> pageBean) {
		// TODO Auto-generated method stub
		dao.bidSaleStatistics(pageBean);
	}
	/**
	 * 散标桌面（线上散标放款后类型占比情况统计）
	 */
	@Override
	public void bidTypeProportion(PageBean<HighchartModel> pageBean) {
		// TODO Auto-generated method stub
		dao.bidTypeProportion(pageBean);
	}

	/**
	 * 散标桌面(线上用户的充值取现的图表数据)
	 */
	@Override
	public void onlineRechargeWithDrawChart(PageBean<HighchartModel> pageBean) {
		// TODO Auto-generated method stub
		dao.onlineRechargeWithDrawChart(pageBean);
		
	}
	
	@Override
	public void businessRankByMonth(PageBean<Highchart> pageBean) {
		dao.businessRankByMonth(pageBean);
	}
	
	@Override
	public void myRank(PageBean<Highchart> pageBean) {
		dao.myRank(pageBean);
	}
	
	@Override
	public void busenessStatistics(PageBean<Highchart> pageBean) {
		dao.busenessStatistics(pageBean);
	}

}
