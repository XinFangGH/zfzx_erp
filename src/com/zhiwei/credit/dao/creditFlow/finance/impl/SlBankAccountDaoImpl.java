package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.finance.SlBankAccountDao;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;


@SuppressWarnings("unchecked")
public class SlBankAccountDaoImpl extends BaseDaoImpl<SlBankAccount> implements SlBankAccountDao{

	public SlBankAccountDaoImpl() {
		super(SlBankAccount.class);
	}

	@Override
	public List<SlBankAccount> getbyaccount(String accont) {
		 String hql="from SlBankAccount q where 1=1";
			String strs=ContextUtil.getBranchIdsStr();//39,40
			if(null!=strs && !"".equals(strs)){
				hql+=" and q.companyId in ("+strs+")"; //
			}
			hql+=" and  q.account='"+accont+"'";
		 Query query = getSession().createQuery(hql);
		 return  query.list();
	}
	@Override
	public List<SlBankAccount> webgetbyaccount(String accont, Long companyId) {
		 String hql="from SlBankAccount q where q.companyId ="+companyId+" and  q.account='"+accont+"'";
		 Query query = getSession().createQuery(hql);
		 return  query.list();
	}
	@Override
	public List<SlBankAccount> getall(Map<String, String> map) {
		 String hql="from SlBankAccount q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
	
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String  sort=map.get("sort");
		String dir=map.get("dir");
		String companyId=map.get("companyId");
		if(null!=companyId && !companyId.equals("")){
			hql=hql+" and  q.companyId ="+companyId +" ";
		}
		if(map.size()!=5){
			String bankBranchName=map.get("Q_bankBranchName_S_LK");
			String account=map.get("Q_account_S_LK");
			String  accountType=map.get("Q_accountType_SN_EQ");
			
			/*if(null!=bankBranchName && !bankBranchName.equals("")){
				hql=hql+" and q.csDicAreaDynam.remarks like '%"+bankBranchName+"%' ";
			}*/
			if(null!=account && !account.equals("")){
				hql=hql+" and q.account like '%"+account+"%'";
			}
			if(null!=accountType && !accountType.equals("")){
				hql=hql+" and q.accountType  = "+accountType;
			}
	
			}else{
				
				
			}
		//hql=hql+" order by q."+sort.trim()+"  "+dir;
	    Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
	     return  query.list();
	}

	@Override
	public int getallsize(Map<String, String> map) {
		 String hql="from SlBankAccount q where 1=1";
			String strs=ContextUtil.getBranchIdsStr();//39,40
			if(null!=strs && !"".equals(strs)){
				hql+=" and q.companyId in ("+strs+")"; //
			}
		
			Integer startpage=Integer.parseInt(map.get("start"));
			Integer pagesize=Integer.parseInt(map.get("limit"));
			String  sort=map.get("sort");
			String dir=map.get("dir");
			String companyId=map.get("companyId");
			if(null!=companyId && !companyId.equals("")){
			hql=hql+" and  q.companyId ="+companyId ;
			}
		
			if(map.size()!=5){
				String bankBranchName=map.get("Q_bankBranchName_S_LK");
				String account=map.get("Q_account_S_LK");
				String  accountType=map.get("Q_accountType_SN_EQ");
				
				if(null!=bankBranchName && !bankBranchName.equals("")){
					hql=hql+" and q.csDicAreaDynam.remarks like '%"+bankBranchName+"%' ";
				}
				if(null!=account && !account.equals("")){
					hql=hql+" and q.account like '%"+account+"%'";
				}
				if(null!=accountType && !accountType.equals("")){
					hql=hql+" and q.accountType  = "+accountType;
				}
		
				}else{
					
					
				}
		 Query query = getSession().createQuery(hql);
		 return  query.list().size();
	}

	@Override
	public List<SlBankAccount> getallbycompanyId( PagingBean pb) {
		 String hql="from SlBankAccount q where 1=1";
			String strs=ContextUtil.getBranchIdsStr();//39,40
			if(null!=strs && !"".equals(strs)){
				hql+=" and q.companyId in ("+strs+")"; //
			}
			hql+=" order by q.remarks desc";
			if(pb == null) {
				return findByHql(hql.toString());
			} else {
				return findByHql(hql.toString(), null, pb);
			}
	}

	@Override
	public List<SlBankAccount> getallbycompanyId(PagingBean pb,
			Map<String, String> map) {
		String hql="from SlBankAccount q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
	
		if(map.size()!=2){
			String openType=map.get("Q_openType_SN_EQ");
			String account=map.get("Q_account_S_LK");
			String accountType=map.get("Q_accountType_SN_EQ");
			String name=map.get("Q_name_S_LK");
			String rawMoneyg=map.get("Q_rawMoney_BD_GE");
			String rawMoneyl=map.get("Q_rawMoney_BD_LE");
			String recordTimeg=map.get("Q_recordTime_D_GE");
			String recordTimel=map.get("Q_recordTime_D_LE");
			String companyId=map.get("companyId");
			
			if(null !=companyId  &&!companyId.equals("")){
				hql=hql+" and q.companyId ="+companyId ;
			}
			if(!openType.equals("")){
				hql=hql+" and q.openType  = "+openType+"";
			}
			if(!account.equals("")){
				hql=hql+" and q.account like '%"+account+"%'   ";
			}
			
			if(!accountType.equals("")){
				hql=hql+" and q.accountType = "+accountType+"";
			}
			if(!rawMoneyg.equals("")){
				hql=hql+" and  q.rawMoney >= '"+rawMoneyg+"'";
			}
			if(!rawMoneyl.equals("")){
				hql=hql+" and  q.rawMoney <= '"+rawMoneyl+"'";
			}
			if(!name.equals("")){
				hql=hql+" and q.name like '%"+name+"%'   ";
			}
			if(!recordTimeg.equals("")){
				hql=hql+" and  q.recordTime>= '"+recordTimeg+"'";
			}
			
			if(!recordTimel.equals("")){
				hql=hql+" and  q.recordTime <= '"+recordTimel+"'";
			}
			
		}else{
			//hql=hql+" and q.payMoney is null";
		}
		hql=hql+" order by q.remarks desc";
		
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}

	@Override
	public List<SlBankAccount> selectbycompanyId(PagingBean pb,
			Map<String, String> map) {
		 String hql="from SlBankAccount q where 1=1";
			String strs=ContextUtil.getBranchIdsStr();//39,40
			if(null!=strs && !"".equals(strs)){
				hql+=" and q.companyId in ("+strs+")"; //
			}
		
			String  sort=map.get("sort");
			String dir=map.get("dir");
			
			if(map.size()!=4){
				String bankBranchName=map.get("Q_bankBranchName_S_LK");
				String account=map.get("Q_account_S_LK");
				String  accountType=map.get("Q_accountType_SN_EQ");
				String companyId=map.get("companyId");
				if(null !=companyId  &&!companyId.equals("")){
					hql=hql+" and q.companyId ="+companyId;
				}
				if(!bankBranchName.equals("")){
					hql=hql+" and q.csDicAreaDynam.remarks like '%"+bankBranchName+"%' ";
				}
				if(!account.equals("")){
					hql=hql+" and q.account like '%"+account+"%'";
				}
				if(!accountType.equals("")){
					hql=hql+" and q.accountType  = "+accountType;
				}
		
				}else{
					
					
				}
			hql=hql+"order by q."+sort.trim()+"  "+dir;
			
			
			if(pb == null) {
				return findByHql(hql.toString());
			} else {
				return findByHql(hql.toString(), null, pb);
			}
	}

	

}