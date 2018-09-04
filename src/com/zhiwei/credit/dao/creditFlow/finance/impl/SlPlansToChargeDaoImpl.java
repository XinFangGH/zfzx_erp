package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlPlansToChargeDaoImpl extends BaseDaoImpl<SlPlansToCharge> implements SlPlansToChargeDao{

	public SlPlansToChargeDaoImpl() {
		super(SlPlansToCharge.class);
	}

	@Override
	public List<SlPlansToCharge> getall() {
		String hql="from SlPlansToCharge q where 1=1 and q.productId is null and q.settingId is null";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		hql+=" and  where q.isValid="+0;
		 Query query = getSession().createQuery(hql);
		 return  query.list();
	}

	@Override
	public List<SlPlansToCharge> getbytype(int type) {
		 String hql="from SlPlansToCharge q  where q.isType="+type+" and q.isValid=0 and q.productId is null and q.settingId is null";
		 Query query = getSession().createQuery(hql);
		 return  query.list();
	}
	@Override
	public List<SlPlansToCharge> getbyOperationType(int type,String businessType) {
		String hql="";
		if(businessType.equals("all")){
		  hql="from SlPlansToCharge q  where  q.isValid=0 and q.productId is null and q.settingId is null";
		}else{
			 hql="from SlPlansToCharge q  where  q.isValid=0 and q.businessType='"+businessType+"' and q.productId is null and q.settingId is null";
		}
		 if(type !=3){
			 hql=hql+"and q.isType="+type;
		 }else if(type==3){
			 hql+=" and (q.chargeKey not in('premiumMoney','earnestMoney') or q.chargeKey is null)";
		 }
		 hql=hql+" order by q.sort asc";
		 Query query = getSession().createQuery(hql);
		 return  query.list();
	}
	@Override
	public int setIsValid(int isValid,SlPlansToCharge s){
		return getSession().createQuery("UPDATE SlPlansToCharge f SET f.isValid='"+isValid+"' where f.plansTochargeId='"+s.getPlansTochargeId()+"'").executeUpdate();
	}

	@Override
	public List<SlPlansToCharge> getallbycompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlPlansToCharge> getListByIdsNotNull(String businessType,String isValid) {
		String hql = "from SlPlansToCharge AS a where 1=1 and a.productId is null and a.settingId is null";
		if(null!=businessType && !"".equals(businessType)){
			hql+=" and a.businessType='"+businessType+"'";
		}
		if(null!=isValid && !"".equals(isValid)){
			hql+=" and a.isValid="+isValid;
		}
		return getSession().createQuery(hql).list();
	}

	@Override
	public List<SlPlansToCharge> checkIsExit(String productId,
			Long plansTochargeId, String businessType) {
		String  hql="from SlPlansToCharge q  where  q.isValid=0 and q.productId=? and q.settingId =? and q.businessType=?";
		return getSession().createQuery(hql).setParameter(0, Long.valueOf(productId)).setParameter(1, plansTochargeId).setParameter(2, businessType).list();
	}

	@Override
	public List<SlPlansToCharge> getByPProductIdAndOperationType(
			String productId, String businessType) {
		String  hql="from SlPlansToCharge q  where  q.isValid=0 and q.productId=?  and q.businessType=?";
		return getSession().createQuery(hql).setParameter(0, Long.valueOf(productId)).setParameter(1, businessType).list();
	}
	
	@Override
	public List<SlPlansToCharge> getbyProductId(String productId,String businessType) {
		if(productId.equals("undefined")){
			productId=null;
		}
		String hql="";
		if(productId !=null && !"".equals(productId)){
			hql="from SlPlansToCharge q  where  q.isValid=0 and q.businessType='"+businessType+"' and (q.productId="+productId+")";
		}else{
			hql="from SlPlansToCharge q  where  q.isValid=0 and q.businessType='"+businessType+"' and q.productId is null ";
		}
		hql+=" and (q.chargeKey not in('premiumMoney','earnestMoney') or q.chargeKey is null)";
		hql=hql+" order by q.sort asc";
		Query query = getSession().createQuery(hql);
		return  query.list();
	}

}