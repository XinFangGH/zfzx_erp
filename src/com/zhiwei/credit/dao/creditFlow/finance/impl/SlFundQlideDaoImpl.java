package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.SlFundQlideDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlFundQlideDaoImpl extends BaseDaoImpl<SlFundQlide> implements SlFundQlideDao{

	public SlFundQlideDaoImpl() {
		super(SlFundQlide.class);
	}

	@Override
	public List<SlFundQlide> search(Map<String,String> map) {
		String hql="from SlFundQlide q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		String isProject=map.get("isProject");
		if(isProject.equals("是")){
		hql=hql+" and  q.fundQlideId > 0 and q.isCash =null and q.isProject='是'";
		}else{
		hql=hql+" and q.fundQlideId > 0 and q.isCash =null ";
			
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String accounts=map.get("Q_myAccount_S_EQ");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
		       isProject=map.get("Q_isProject_BD_LE");
		String transactionType=map.get("Q_transactionType_D_LE");
		String companyId=map.get("companyId");
		
		if(null !=companyId && !companyId.equals("")){
			hql=hql+" and q.companyId ="+companyId ;
		}
		if(null !=transactionType && !transactionType.equals("")){
			hql=hql+" and  q.transactionType like '%"+transactionType+"%'";
		}
		if(null !=isProject && !isProject.equals("")){
			hql=hql+" and  q.isProject = '"+isProject+"'";
		}
		if(null!=factDateg && !factDateg.equals("")){
			factDateg=factDateg.split("T")[0]+"T00:00:00";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(null!=factDatel && !factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(null!=accounts && !accounts.equals("")){
			hql=hql+" and  q.myAccount = '"+accounts+"'";
		}
		if(null!=notMoneyle && !notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(null!=notMoneyle && !notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(null!=flag && !flag.equals("")){
			if(null!=diaMoneyg && !diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(null!=diaMoneyg && !diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(null!=diaMoneyl && !diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+"and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(null!=diaMoneyl && !diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(null!=diaMoneyg && diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '0'";
			}
            if(null!=diaMoneyg && diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+"and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(null!=diaMoneyg && !diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(null!=diaMoneyl && !diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.factDate desc";
	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		
		 return  query.list();
	}

	@Override
	public int searchsize(Map<String, String> map) {
		String hql="select count(*) from SlFundQlide q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		String isProject=map.get("isProject");
		if(isProject.equals("是")){
		hql=hql+" and  q.fundQlideId > 0 and q.isCash =null and q.isProject='是'";
		}else{
		hql=hql+" and q.fundQlideId > 0 and q.isCash =null ";
			
		}

		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String accounts=map.get("Q_myAccount_S_EQ");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
	       isProject=map.get("Q_isProject_BD_LE");
	    String transactionType=map.get("Q_transactionType_D_LE");
	    String companyId=map.get("companyId");
		
	    if(null !=companyId && !companyId.equals("")){
			hql=hql+" and q.companyId ="+companyId ;
		}

		if(null !=transactionType && !transactionType.equals("")){
			hql=hql+" and  q.transactionType like '%"+transactionType+"%'";
		}
		if(null !=isProject && !isProject.equals("")){
			hql=hql+" and  q.isProject = '"+isProject+"'";
		}
		if(null!=factDateg && !factDateg.equals("")){
	//		factDateg=factDateg.split("T")[0]+"T00:00:00";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(null!=factDatel && !factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(null!=accounts && !accounts.equals("")){
			hql=hql+" and  q.myAccount = '"+accounts+"'";
		}
		if(null!=notMoneyle && !notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(null!=notMoneyle && !notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(null!=flag && !flag.equals("")){
			if(null!=diaMoneyg && !diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(null!=diaMoneyg && !diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(null!=diaMoneyl && !diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+"and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(null!=diaMoneyl && !diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '0'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+"and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(!diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(!diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.factDate desc";
		Long count=Long.valueOf("0");
	    Query query = getSession().createQuery(hql);
	    List list=query.list();
	    if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		 return  count.intValue();
	}

	@Override
	public List<SlFundQlide> getsortAll(Map<String,String> map) {
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String payin=map.get("fundType");
		String companyId=map.get("companyId");
		String hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.isCash IS NULL order by q.factDate asc";
		
		if(payin.equals("principalLending") || payin.equals("1")){ 
			hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.payMoney IS NOT NULL and q.isCash IS NULL order by q.factDate asc";
			}
		if(payin.equals("principalRepayment") || payin.equals("loanInterest") || payin.equals("serviceMoney")|| payin.equals("consultationMoney") ||  payin.equals("0")){
			hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.incomeMoney IS NOT NULL and q.isCash IS NULL order by q.factDate asc";
		}
		
		 Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		 return  query.list();
	}

	@Override
	public int getsortcount(Map<String, String> map) {
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String payin=map.get("fundType");
		String companyId=map.get("companyId");
		String hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.isCash =null order by q.factDate asc";
		
		if(payin.equals("principalLending") || payin.equals("1")){ 
			hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.payMoney !=null and q.isCash =null order by q.factDate asc";
			}
		if(payin.equals("principalRepayment") || payin.equals("loanInterest") || payin.equals("serviceMoney")|| payin.equals("consultationMoney") ||  payin.equals("0")){	hql="from SlFundQlide q  where q.companyId="+companyId+" and q.isProject='是' and q.notMoney != '0.00' and q.incomeMoney !=null and q.isCash =null order by q.factDate asc";}
		
		 Query query = getSession().createQuery(hql);
		 return  query.list().size();
	}

	@Override//此方法应该是没用了的
	public List<SlFundQlide> getcashQlide(String iscash) {
		String hql="from SlFundQlide q  where q.isCash = '"+iscash+"' order by q.factDate asc";
		Query query = getSession().createQuery(hql);
		 return  query.list();
	}

	@Override
	public List<SlFundQlide> searchcash(Map<String, String> map) {
	
		String hql="from SlFundQlide q   where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql=hql+" and q.isCash ='cash' and q.myAccount='cahsqlideAccount'";
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=2){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
		String transactionType=map.get("Q_transactionType_D_LE");
		String isProject=map.get("Q_isProject_BD_LE");
		String companyId=map.get("companyId");
		
		if(null !=companyId && !companyId.equals("")){
			hql=hql+" and q.companyId ="+companyId ;
		}
		if(null !=isProject && !isProject.equals("")){
			hql=hql+" and  q.isProject = '"+isProject+"'";
		}
		if(null !=transactionType && !transactionType.equals("")){
			hql=hql+" and  q.transactionType like '%"+transactionType+"%'";
		}
		if(!factDateg.equals("")){
			factDateg=factDateg.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(!factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(!flag.equals("")){
			if(!diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+"and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '0'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+"and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(!diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(!diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.factDate desc";
	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		
		 return  query.list();
	}

	@Override
	public int searchcashsize(Map<String, String> map) {
		String hql="select count(*)  from SlFundQlide q   where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql=hql+" and q.isCash ='cash' and q.myAccount='cahsqlideAccount'";
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=2){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String notMoneyle=map.get("Q_notMoney_BD_LE");
	    String transactionType=map.get("Q_transactionType_D_LE");
	    String isProject=map.get("Q_isProject_BD_LE");
	    String companyId=map.get("companyId");
		
	    if(null !=companyId && !companyId.equals("")){
			hql=hql+" and q.companyId ="+companyId ;
		}
	    if(null !=isProject && !isProject.equals("")){
			hql=hql+" and  q.isProject = '"+isProject+"'";
		}
		if(null !=transactionType && !transactionType.equals("")){
			hql=hql+" and  q.transactionType like '%"+transactionType+"%'";
		}
		if(!factDateg.equals("")){
			factDateg=factDateg.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(!factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
			hql=hql+" and  q.notMoney = 0";
		}
		
		if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
			hql=hql+" and  q.notMoney !=0 ";
		}
		if(!flag.equals("")){
			if(!diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+"and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+"and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+"and q.payMoney >= '0'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+"and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(!diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(!diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.factDate desc";
		Long count=Long.valueOf("0");
	    Query query = getSession().createQuery(hql);
	    List list=query.list();
	    if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		 return  count.intValue();
	}

	@Override
	public List<SlFundQlide> listbyaccount(Map<String,String> map) {
		String hql="from SlFundQlide q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		String myaccount=map.get("myaccount");
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
	//	String  companyId=map.get("companyId");
		
		if(factDateg !=null&&!factDateg.equals("")){
			factDateg=factDateg.split("T")[0]+"T00:00:00";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		if(factDatel !=null&&!factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		String sort=map.get("sort");
		String dir=map.get("dir");
		if(dir !=null&&!dir.equals("")){
			hql+="  and  q.isCash = null and q.myAccount= '"+myaccount+"' order by q.factDate "+dir+",q.fundQlideId "+dir;
			
		}else{
		    hql+="  and  q.isCash = null and q.myAccount= '"+myaccount+"' order by q.factDate asc,q.fundQlideId asc";
		
		}
//	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		return findByHql(hql);
	}

	@Override
	public int listbyaccountsize(Map<String,String> map) {
		String hql="select count(*) from SlFundQlide q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		String myaccount=map.get("myaccount");
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
	//	String  companyId=map.get("companyId");
		if(factDateg !=null&&!factDateg.equals("")){
			factDateg=factDateg.split("T")[0]+"T00:00:00";
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		if(factDatel !=null&&!factDatel.equals("")){
			factDatel=factDatel.split("T")[0]+"T23:59:59";
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		hql+="  and  q.isCash = null  and q.myAccount= '"+myaccount+"'";
		Long count=Long.valueOf("0");
	    Query query = getSession().createQuery(hql);
	    List list=query.list();
	    if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		 return  count.intValue();
	}

	@Override
	public List<SlFundQlide>  listbyaccountall(Map<String, String> map) {
		String hql="from SlFundQlide q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
	//	hql+=" and  q.isProject='是' and  q.notMoney !=0 ";
		
		String myaccount=map.get("myaccount");
		hql+=" and  q.isCash = null and q.myAccount= '"+myaccount+"' order by q.factDate asc ,q.fundQlideId asc";
		return findByHql(hql);
	}
	@Override
	public List<SlFundQlide>  listcashall(Map<String, String> map) {
		String  hql="from SlFundQlide q  where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql+=" and q.isCash ='cash' and q.myAccount='cahsqlideAccount' order by q.factDate asc";
		return findByHql(hql);
	}

	@Override
	public List<SlFundQlide> searchnotcheck(Map<String, String> map) {
		
		String hql="from SlFundQlide q where  q.isProject='是' and  q.notMoney !=0 ";
	
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String accounts=map.get("Q_myAccount_S_EQ");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String qlidetype=map.get("qlidetype");
		String companyId=map.get("companyId");
		String oOpenName=map.get("Q_OopenName_D_GE");
		
		if(null !=companyId && !companyId.equals("")){
			hql=hql+" and  q.companyId = "+companyId;
		}
		if(null !=oOpenName && !oOpenName.equals("")){
			hql=hql+" and  q.opOpenName  like '%"+oOpenName+"%'";
		}
		if(!"".equals(qlidetype)){
			if("bankqlide".equals(qlidetype)){
			hql=hql+" and  q.isCash is null  ";
			}else{
				hql=hql+" and  q.isCash ='cash' and q.myAccount='cahsqlideAccount'";
			}
		}
		if(!factDateg.equals("")){
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(!factDatel.equals("")){
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(!accounts.equals("")){
			hql=hql+" and  q.myAccount = '"+accounts+"'";
		}
	
		if(!flag.equals("")){
			if(!diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+" and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+" and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+" and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+" and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+" and q.payMoney >= '0'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+" and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(!diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(!diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}
		hql=hql+" order by q.factDate asc";
	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		
		 return  query.list();
	}

	@Override
	public int searchnotchecksize(Map<String, String> map) {
		String hql="select count(*) from SlFundQlide q where  q.isProject='是' and  q.notMoney !=0 ";
	
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
		String factDateg=map.get("Q_factDate_D_GE");
		String factDatel=map.get("Q_factDate_D_LE");
		String accounts=map.get("Q_myAccount_S_EQ");
		String flag=map.get("Q_fundType_SN_EQ");
		String diaMoneyg=map.get("Q_dialMoney_BD_GE");
		String diaMoneyl=map.get("Q_dialMoney_BD_LE");
		String qlidetype=map.get("qlidetype");
	   String companyId=map.get("companyId");
		
		if(!companyId.equals("")){
			hql=hql+" and  q.companyId = "+companyId;
		}
		if(!qlidetype.equals("")){
			if(qlidetype.equals("bankqlide")){
			hql=hql+" and  q.isCash =null  ";
			}else{
				hql=hql+" and  q.isCash ='cash' and q.myAccount='cahsqlideAccount'";
			}
		}
		if(!factDateg.equals("")){
			hql=hql+" and  q.factDate >= '"+factDateg+"'";
		}
		
		if(!factDatel.equals("")){
			hql=hql+" and  q.factDate <= '"+factDatel+"'";
		}
		if(!accounts.equals("")){
			hql=hql+" and  q.myAccount = '"+accounts+"'";
		}
	
		if(!flag.equals("")){
			if(!diaMoneyg.equals("") &&flag.equals("0")){
				hql=hql+" and q.payMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyg.equals("") &&flag.equals("1")){
            	hql=hql+" and q.incomeMoney >= '"+diaMoneyg+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("0")){
            	hql=hql+" and q.payMoney <= '"+diaMoneyl+"'";
			}
            if(!diaMoneyl.equals("") &&flag.equals("1")){
            	hql=hql+" and q.incomeMoney <= '"+diaMoneyl+"'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("0")){
				hql=hql+" and q.payMoney >= '0'";
			}
            if(diaMoneyg.equals("") && diaMoneyl.equals("") &&flag.equals("1")){
				hql=hql+" and q.incomeMoney >= '0'";
			}
		}
		if(flag.equals("")){
			
			if(!diaMoneyg.equals("")){
				hql=hql+" and  (q.incomeMoney >=" +diaMoneyg+ " or q.payMoney >= "+diaMoneyg+")";
				
			}
			if(!diaMoneyl.equals("")){
				hql=hql+" and  (q.incomeMoney <=" +diaMoneyl+ " or q.payMoney <= "+diaMoneyl+")";
			}
		}
		}
		hql=hql+" order by q.factDate desc";
		Long count=Long.valueOf("0");
	    Query query = getSession().createQuery(hql);
	    List list=query.list();
	    if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		 return  count.intValue();
	}

	@Override
	public List<SlFundQlide> getallbycompanyId() {
		 String hql="from SlFundQlide q where 1=1";
			String strs=ContextUtil.getBranchIdsStr();//39,40
			if(null!=strs && !"".equals(strs)){
				hql+=" and q.companyId in ("+strs+")"; //
			}
		return findByHql(hql.toString());
	}

	@Override
	public List<SlFundQlide> getLastByMaxDate(Long comId) {
		 String hql="from SlFundQlide q where 1=1 and q.companyId='"+comId+"'and  q.webgetTime is not NULL order by q.webgetTime desc limit 1";
		return findByHql(hql.toString());
	}


}