package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.dao.creditFlow.finance.VPunishDetailDao;
import com.zhiwei.credit.model.creditFlow.finance.VPunishDetail;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class VPunishDetailDaoImpl extends BaseDaoImpl<VPunishDetail> implements VPunishDetailDao{

	public VPunishDetailDaoImpl() {
		super(VPunishDetail.class);
	}

	@Override
	public List<VPunishDetail> wslistbyPunish(String businessType,
			Long projectId, String factDate) {
		String hql="from VPunishDetail s  where 1=1";
		String strs=ContextUtil.getLoginCompanyId().toString();
		if(null!=strs && !"".equals(strs)){
			hql+=" and s.companyId in ("+strs+")"; //
		}
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String factDatel=sdf.format(DateUtil.addDaysToDate(DateUtil.parseDate(factDate, "yyyy-MM-dd"), +1));
		hql+=" and  s.iscancel = null  and s.businessType='"+businessType+"' and s.projectId="+projectId +
				"and s.factDate >= '"+factDate+"' and s.factDate <='"+factDatel+"'";
		return findByHql(hql);
	
	}

	@Override
	public List<VPunishDetail> search(Map<String, String> map) {
		String hql="from VPunishDetail q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")";
		}
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		String businessType=map.get("businessType");
		if(null!=businessType && !businessType.equals("")){
			hql=hql+" and q.businessType='"+businessType+"'";
		}
		if(map.size()!=3){
			String projNum=map.get("Q_projNum_N_EQ");	
			String projName=map.get("Q_proj_Name_N_EQ");
			String fundType=map.get("Q_fundType_S_EQ");
			String intentDatege1=map.get("Q_intentDate_D_GE");
			String intentDatege =intentDatege1+ " 00:00:00";
			String intentDatele1=map.get("Q_intentDate_D_LE");
			String intentDatele =intentDatele1 +" 23:59:59";
			String transactionType=map.get("Q_transactionType_S_LK");
			String companyId=map.get("companyId");
			String projectProperties=map.get("properties");
			
			if(null !=companyId&&!companyId.equals("")){
				hql=hql+" and q.companyId="+companyId;
			}
			if(null !=projNum && !projNum.equals("")){
				hql=hql+" and q.projectNumber like '%/"+projNum.toString()+"%'  escape '/' ";
			}
			if(null !=projName && !projName.equals("")){
				hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
			}
			if(null != transactionType && !transactionType.equals("")){
				hql=hql+" and (q.transactionType  like '%"+transactionType+"%' or q.qlidetransactionType like '%" +transactionType+"%')";
			}
			if(null != fundType && !fundType.equals("")){
				hql=hql+" and q.funType = '"+fundType+"'";
			}
			if(null != intentDatege1 && !intentDatege1.equals("")){
				hql=hql+" and  q.operTime >= '"+intentDatege+"'";
			}
			if(null != intentDatele1 && !intentDatele1.equals("")){
				hql=hql+" and  q.operTime <= '"+intentDatele+"'";
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
				}
			}
		}
		hql=hql+" order by q.operTime desc";
		Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
		return  query.list();
	}

	@Override
	public int searchsize(Map<String, String> map) {
		String hql="select count(*) from VPunishDetail q where 1=1";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")";
		}
		String businessType=map.get("businessType");
		if(null!=businessType && !businessType.equals("")){
			hql=hql+" and q.businessType='"+businessType+"'";
		}
		if(map.size()!=3){
			String projNum=map.get("Q_projNum_N_EQ");
			String projName=map.get("Q_proj_Name_N_EQ");
			String fundType=map.get("Q_fundType_S_EQ");
			String intentDatege1=map.get("Q_intentDate_D_GE");
			String intentDatege =intentDatege1+ " 00:00:00";
			String intentDatele1=map.get("Q_intentDate_D_LE");
			String intentDatele =intentDatele1 +" 23:59:59";
			String transactionType=map.get("Q_transactionType_S_LK");
			String projectProperties=map.get("properties");
		    String companyId=map.get("companyId");
			
		    if(null !=companyId && !companyId.equals("")){
				hql=hql+" and q.companyId="+companyId;
			}
		    if(null != projNum && !projNum.equals("")){
				hql=hql+" and q.projectNumber like '%/"+projNum.toString()+"%'  escape '/' ";
			}
			if(null != projName && !projName.equals("")){
				hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
			}
			if(null != transactionType && !transactionType.equals("")){
				hql=hql+" and (q.transactionType  like '%"+transactionType+"%' or q.qlidetransactionType like '%" +transactionType+"%')";
			}
			if(null != fundType && !fundType.equals("")){
				hql=hql+" and q.funType = '"+fundType+"'";
			}
			if(null != intentDatege1 && !intentDatege1.equals("")){
				hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
			}
			if(null != intentDatele1 && !intentDatele1.equals("")){
				hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
				}
			}
		}
		Long count=Long.valueOf("0");
		Query query = getSession().createQuery(hql);
	    List list=query.list();
	    if(null!=list && list.size()>0){
			count=(Long)list.get(0);
		}
		return  count.intValue();
	}
}