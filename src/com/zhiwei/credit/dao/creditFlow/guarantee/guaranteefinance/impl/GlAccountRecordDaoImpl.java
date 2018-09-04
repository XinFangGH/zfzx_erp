package com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.guarantee.guaranteefinance.GlAccountRecordDao;
import com.zhiwei.credit.model.creditFlow.guarantee.guaranteefinance.GlAccountRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlAccountRecordDaoImpl extends BaseDaoImpl<GlAccountRecord> implements GlAccountRecordDao{

	public GlAccountRecordDaoImpl() {
		super(GlAccountRecord.class);
	}

	@Override
	public List<GlAccountRecord> getallbycautionAccountId(Long cautionAccountId,int start,int limit) {
		String hql = "from GlAccountRecord s where s.idDelete is null and  s.cautionAccountId="+cautionAccountId;
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public List<GlAccountRecord> getallbyglAccountBankId(Long glAccountBankId,int start,int limit) {
		String hql = "from GlAccountRecord s where s.idDelete is null and s.glAccountBankId="+glAccountBankId;
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallbycautionAccountIdsize(Long cautionAccountId) {
		String hql = "from GlAccountRecord s where s.idDelete is null and s.cautionAccountId="+cautionAccountId;
		return findByHql(hql).size();
	}

	@Override
	public int getallbyglAccountBankIdsize(Long glAccountBankId) {
		String hql = "from GlAccountRecord s where  s.idDelete is null and  s.glAccountBankId="+glAccountBankId;
		return findByHql(hql).size();
	}

	@Override
	public List<GlAccountRecord> getallreleatebycautionAccountId(
			Long cautionAccountId, int start, int limit, String flag) {
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=1 or s.capitalType=2 ) and s.cautionAccountId="+cautionAccountId;
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=3 or s.capitalType=4 ) and s.cautionAccountId="+cautionAccountId;
		}
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallreleatebycautionAccountIdsize(Long cautionAccountId,
			String flag) {
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=1 or s.capitalType=2 ) and s.cautionAccountId="+cautionAccountId;
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=3 or s.capitalType=4 ) and s.cautionAccountId="+cautionAccountId;
		}
		return findByHql(hql).size();
	}

	@Override
	public List<GlAccountRecord> getallreleatebyglAccountBankId(
			Long glAccountBankId, int start, int limit, String flag) {
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=1 or s.capitalType=2 ) and s.glAccountBankId="+glAccountBankId;
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=3 or s.capitalType=4 ) and s.glAccountBankId="+glAccountBankId;
		}
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallreleatebyglAccountBankIdsize(Long glAccountBankId,
			String flag) {
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=1 or s.capitalType=2 ) and s.glAccountBankId="+glAccountBankId;
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=3 or s.capitalType=4 ) and s.glAccountBankId="+glAccountBankId;
		}
		return findByHql(hql).size();
	}

	@Override
	public List<GlAccountRecord> getallreleate(int start, int limit, String flag) {
		// TODO Auto-generated method stub
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=1 or s.capitalType=2 )";
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where  s.idDelete is null and  (s.capitalType=3 or s.capitalType=4 ) ";
		}
		Query query = getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit);
		 return  query.list();
	}

	@Override
	public int getallreleate(String flag) {
		String hql="";
		if(flag.equals("incomepaycheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=1 or s.capitalType=2 )";
		}
		if(flag.equals("freezecheckbox")){
			 hql = "from GlAccountRecord s where s.idDelete is null and (s.capitalType=3 or s.capitalType=4 ) ";
		}
		return findByHql(hql).size();
	}

	@Override
	public List<GlAccountRecord> getbyprojectId(Long projectId) {
		String hql = "from GlAccountRecord s where s.projectId="+projectId;
		Query query = getSession().createQuery(hql);
		 return  query.list();
	}

	@Override
	public List<GlAccountRecord> getbyprojectIdcapitalType(Long projectId,
			int capitalType) {
		String hql = "from GlAccountRecord s where s.projectId="+projectId+" and s.capitalType="+capitalType;
		Query query = getSession().createQuery(hql);
		 return  query.list();
	}

	@Override
	public List<GlAccountRecord> getalllist() {
		String hql = "from GlAccountRecord s where s.idDelete is null";
		return findByHql(hql);
	}
   
}