package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.BpCustLoginlogDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.p2p.BpCustLoginlog;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustLoginlogDaoImpl extends BaseDaoImpl<BpCustLoginlog> implements BpCustLoginlogDao{

	public BpCustLoginlogDaoImpl() {
		super(BpCustLoginlog.class);
	}

	@Override
	public List<BpCustLoginlog> getAllList(HttpServletRequest request,
			Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String sql=" select " +
				   " bp.id, " +
				   "bp.type," +
				   "bp.loginIp," +
				   "bp.loginTime," +
				   "bp.memberId," +
				   "bp.exitTime," +
				   "cust.loginname as loginMemberName " +
				   "from bp_cust_loginlog as bp ,bp_cust_member as cust where bp.memberId=cust.id ";
		
		if(request!=null){
			String memberName =request.getParameter("memberName");
			if(memberName!=null &&!"".equals(memberName)){
				sql=sql+" and cust.loginname like '%"+memberName+"%'";
			}
			String loginTime =request.getParameter("loginTime");
			if(loginTime!=null &&!"".equals(loginTime)){
				sql=sql+" and bp.loginTime  >='"+loginTime+" 00:00:00' ";
				sql=sql+" and bp.loginTime <='"+loginTime+" 23:59:59' ";
			}
			String loginIp =request.getParameter("loginIp");
			if(loginIp!=null &&!"".equals(loginIp)){
				sql=sql+" and bp.loginIp like '%"+loginIp+"%'";
			}
			String exitTime =request.getParameter("exitTime");
			if(exitTime!=null &&!"".equals(exitTime)){
				sql=sql+" and bp.exitTime  >='"+exitTime+" 00:00:00' ";
				sql=sql+" and bp.exitTime <='"+exitTime+" 23:59:59' ";
			}
		}
		sql=sql+"  order by bp.loginTime desc ";
	//	System.out.println("查询的sql是"+sql);
		List list=null;
		if(start ==null || limit==null){
			list=this.getSession().createSQLQuery(sql).
				addScalar("id", Hibernate.LONG).
				addScalar("type", Hibernate.INTEGER).
				addScalar("loginIp", Hibernate.STRING).
				addScalar("loginTime", Hibernate.TIMESTAMP).
				addScalar("memberId", Hibernate.LONG).
				addScalar("exitTime", Hibernate.DATE).
				addScalar("loginMemberName", Hibernate.STRING).
				setResultTransformer(Transformers.aliasToBean(BpCustLoginlog.class)).
				list();
		}else{
			list=this.getSession().createSQLQuery(sql).
				  addScalar("id", Hibernate.LONG).
				  addScalar("type", Hibernate.INTEGER).
				  addScalar("loginIp", Hibernate.STRING).
				  addScalar("loginTime", Hibernate.TIMESTAMP).
				  addScalar("memberId", Hibernate.LONG).
				  addScalar("exitTime", Hibernate.DATE).
				  addScalar("loginMemberName", Hibernate.STRING).
				  setResultTransformer(Transformers.aliasToBean(BpCustLoginlog.class)).
				  setFirstResult(start).setMaxResults(limit).
				  list();
		}
		return list;
	}

}