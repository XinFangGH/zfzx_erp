package com.zhiwei.credit.dao.creditFlow.customer.common.impl;

import java.math.BigInteger;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;


@SuppressWarnings("unchecked")
@Repository
public class EnterpriseBankDaoImpl extends BaseDaoImpl<EnterpriseBank> implements EnterpriseBankDao{
	public EnterpriseBankDaoImpl() {
		super(EnterpriseBank.class);
	}

	@Override
	public List<EnterpriseBank> getBankList(Integer customerId,Short isEnterprise,Short iscredit,Short isInvest){
		String hql = "from EnterpriseBank as e where e.enterpriseid=? and e.isEnterprise=? and e.iscredit=? and e.isInvest = ?";
		return findByHql(hql,new Object[]{customerId,isEnterprise,iscredit,isInvest});
	}

	@Override
	public EnterpriseBank getById(Integer id) {
		String hql="from EnterpriseBank as e where id=?";
		return (EnterpriseBank) getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
	@Override
	public List<EnterpriseBank> getList(Integer customerId, Short isEnterprise,
			Short isInvest, Integer start, Integer limit) {
		String hql="from EnterpriseBank as e where e.enterpriseid="+customerId+" and e.isEnterprise="+isEnterprise;
		if(isInvest != null ){
			if(isInvest==1){
				hql=hql+" and e.isInvest ="+isInvest;
			}else{
				hql=hql+" and (e.isInvest ="+isInvest+" or e.isInvest is null)";
			}
		}
		
		List<EnterpriseBank> list=null;
		try {
			if(null==start && null==limit){
				list=this.findByHql(hql);
			}else{
				list=this.findByHql(hql,new Object[]{}, start, limit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public EnterpriseBank queryIscredit(Integer customerId, Short isEnterprise,
			Short isInvest) {
		String hql="from EnterpriseBank as e where e.iscredit=0 and e.enterpriseid="+customerId+" and e.isEnterprise="+isEnterprise;
		if(isInvest==1){
			hql=hql+" and e.isInvest ="+isInvest;
		}else{
			hql=hql+" and (e.isInvest ="+isInvest+" or e.isInvest is null)";
		}
		List<EnterpriseBank> list=this.findByHql(hql);
		EnterpriseBank e=null;
		if(null!=list && list.size()>0){
			e=list.get(0);
		}
		return e;
	}
	
	@Override
	public List<EnterpriseBank> queryAlreadyAccount(Integer id,String accountnum) {
		
		StringBuffer hql= new StringBuffer("from EnterpriseBank as e where e.accountnum ='"+accountnum+"'");
		if(id!=null){
			hql.append(" and e.id !="+id);
		}
		
		return  this.findByHql(hql.toString());
	}
	
	@Override
	public void querySomeList(PageBean<EnterpriseBank> pageBean){
		HttpServletRequest request=pageBean.getRequest();
		String name=request.getParameter("name");
		String isEnterprise=request.getParameter("isEnterprise");
		String accountnum=request.getParameter("accountnum");
		String bankname=request.getParameter("bankname");
		
		List list=null;
		
		StringBuffer tempCount=new StringBuffer("SELECT count(cb.id) FROM cs_enterprise_bank AS cb where 1=1");
		
		StringBuffer temp=new StringBuffer("select" +
				" cb.bankname," +
				" cb.openCurrency," +
				" cb.iscredit," +
				" cb.name," +
				" cb.accountnum," +
				" cb.isEnterprise" +
				" FROM cs_enterprise_bank AS cb where 1=1");
		
		if(null!=accountnum && !"".equals(accountnum)){
			temp.append(" and cb.accountnum like '%"+accountnum+"%'");
			tempCount.append(" and cb.accountnum like '%"+accountnum+"%'");
		}
		if(null!=name && !"".equals(name)){
			temp.append(" and cb.name like '%"+name+"%'");
			tempCount.append(" and cb.name like '%"+name+"%'");
		}
		if(null!=bankname && !"".equals(bankname)){
			temp.append(" and cb.bankname like '%"+bankname+"%'");
			tempCount.append(" and cb.bankname like '%"+bankname+"%'");
		}
		if(null!=isEnterprise && !"".equals(isEnterprise)){
			temp.append(" and cb.isEnterprise = "+isEnterprise);
			tempCount.append(" and cb.isEnterprise = "+isEnterprise);
		}
		temp.append(" LIMIT "+pageBean.getStart()+","+pageBean.getLimit());
		
		list = this.getSession().createSQLQuery(temp.toString())
			.addScalar("name", Hibernate.STRING)
			.addScalar("accountnum", Hibernate.STRING)
			.addScalar("bankname", Hibernate.STRING)
			.addScalar("openCurrency", Hibernate.SHORT)
			.addScalar("iscredit", Hibernate.SHORT)
			.addScalar("isEnterprise", Hibernate.SHORT)
			.setResultTransformer(Transformers.aliasToBean(EnterpriseBank.class)).list();
		
		pageBean.setResult(list);
		BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(tempCount.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}

}
