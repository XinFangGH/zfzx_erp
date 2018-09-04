package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.BpFinanceApplyUserDao;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.p2p.BpFinanceApplyUser;



/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpFinanceApplyUserDaoImpl extends BaseDaoImpl<BpFinanceApplyUser> implements BpFinanceApplyUserDao{

	public BpFinanceApplyUserDaoImpl() {
		super(BpFinanceApplyUser.class);
	}

	@Override
	public List<BpFinanceApplyUser> personList(Integer start, Integer limit,Integer state,HttpServletRequest request) {
		String sql="select"+
			" m.loginname as appName,"+
			" u.userID,"+
			" u.productName as proName,"+
			" u.userID,"+
			" u.state,"+
			" u.loanId ,"+
			" u.loanTitle,"+
			" u.loanMoney, "+
			" u.loanTimeLen,"+
			" u.loanUse,"+
			" m.truename," +
			" d.itemValue as loanUseStr, " +
			" u.createTime, "+
			" u.productId "+
			" from bp_cust_member m inner join bp_finance_apply_user u on m.id=u.userID " +
			" left join dictionary as d on d.dicId=u.loanUse "+
			" where 1=1 " ;
		if(4==state){//查询审批通过,应该将(5立项中,6已发标)都查询出来
			sql=sql+" and u.state in (4,5,6) ";
		}else{
			sql=sql+" and u.state='"+state+"'";
		}
		String loanMoney=request.getParameter("Q_loanMoney_S_EQ");
		if(null!=loanMoney && !loanMoney.equals("")){
			sql=sql+" and u.loanMoney="+new BigDecimal(loanMoney);
		}
		String loanTimeLen=request.getParameter("Q_loanTimeLen_S_EQ");
		if(null!=loanTimeLen && !loanTimeLen.equals("")){
			sql=sql+" and u.loanTimeLen="+Integer.valueOf(loanTimeLen);
		}
		String createTime=request.getParameter("Q_createTime_S_EQ");
		if(null!=createTime && !createTime.equals("")){
			sql=sql+" and u.createTime='"+createTime+"'";
		}
		String loginname=request.getParameter("loginname");
		if(null!=loginname && !loginname.equals("")){
			sql=sql+" and m.loginname like '%"+loginname+"%'";
		}
		String truename=request.getParameter("truename");
		if(null!=truename && !truename.equals("")){
			sql=sql+" and m.truename like '%"+truename+"%'";
		}
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("appName",Hibernate.STRING).
			 addScalar("userID",Hibernate.LONG).
			 addScalar("proName",Hibernate.STRING).
			 addScalar("state",Hibernate.STRING).
			 addScalar("loanId",Hibernate.LONG).
			 addScalar("productId",Hibernate.LONG).
			 addScalar("loanTitle",Hibernate.STRING).
			 addScalar("loanMoney",Hibernate.BIG_DECIMAL).
			 addScalar("loanTimeLen",Hibernate.INTEGER).
			 addScalar("loanUse",Hibernate.LONG).
			 addScalar("loanUseStr",Hibernate.STRING).
			 addScalar("createTime",Hibernate.DATE).
			 addScalar("truename",Hibernate.STRING).
			 setResultTransformer(Transformers.aliasToBean(BpFinanceApplyUser.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else{
				list=this.getSession().createSQLQuery(sql).
				addScalar("appName",Hibernate.STRING).
				 addScalar("userID",Hibernate.LONG).
				 addScalar("proName",Hibernate.STRING).
				 addScalar("state",Hibernate.STRING).
				 addScalar("loanId",Hibernate.LONG).
				 addScalar("productId",Hibernate.LONG).
				 addScalar("loanTitle",Hibernate.STRING).
				 addScalar("loanMoney",Hibernate.BIG_DECIMAL).
				 addScalar("loanTimeLen",Hibernate.INTEGER).
				 addScalar("loanUse",Hibernate.LONG).
				 addScalar("loanUseStr",Hibernate.STRING).
				 addScalar("createTime",Hibernate.DATE).
				 addScalar("truename",Hibernate.STRING).
				 setResultTransformer(Transformers.aliasToBean(BpFinanceApplyUser.class)).
				 list();
		}
		return list;
	}

	@Override
	public BpFinanceApplyUser getDetailed(String loanId) {

		String sql="select"+
			" m.loginname as appName,"+
			" u.userID,"+
			" u.productName as proName,"+
			" u.userID,"+
			" u.state,"+
			" u.loanId ,"+
			" u.loanTitle,"+
			" u.loanMoney, "+
			" u.loanTimeLen,"+
			" u.loanUse,"+
			" m.truename," +
			" d.itemValue as loanUseStr, " +
			" u.createTime, "+
			" m.telphone, "+
			" u.productId, "+
			" r.yearAccrualRate, "+
			" r.yearFinanceServiceOfRate, "+
			" r.yearManagementConsultingOfRate "+
			" from bp_finance_apply_user  as u left join bp_cust_member as m on m.id=u.userID " +
			" left join dictionary as d on d.dicId=u.loanUse "+
			" LEFT JOIN p2p_loan_rate r ON (r.productId = u.productId and r.loanTime=u.loanTimeLen) "+
			" where u.state=2 and u.loanId="+loanId;
		System.out.println(sql);
		BpFinanceApplyUser bpFinanceApplyUser=(BpFinanceApplyUser) this.getSession().createSQLQuery(sql).
		addScalar("appName",Hibernate.STRING).
		addScalar("userID",Hibernate.LONG).
		addScalar("proName",Hibernate.STRING).
		addScalar("state",Hibernate.STRING).
		addScalar("loanId",Hibernate.LONG).
		addScalar("productId",Hibernate.LONG).
		addScalar("loanTitle",Hibernate.STRING).
		addScalar("loanMoney",Hibernate.BIG_DECIMAL).
		addScalar("loanTimeLen",Hibernate.INTEGER).
		addScalar("loanUse",Hibernate.LONG).
		addScalar("loanUseStr",Hibernate.STRING).
		addScalar("createTime",Hibernate.DATE).
		addScalar("truename",Hibernate.STRING).
		addScalar("telphone",Hibernate.STRING).
		addScalar("yearAccrualRate",Hibernate.BIG_DECIMAL).
		addScalar("yearFinanceServiceOfRate",Hibernate.BIG_DECIMAL).
		addScalar("yearManagementConsultingOfRate",Hibernate.BIG_DECIMAL).
		setResultTransformer(Transformers.aliasToBean(BpFinanceApplyUser.class)).uniqueResult();
		
		return bpFinanceApplyUser;
	}
	

}