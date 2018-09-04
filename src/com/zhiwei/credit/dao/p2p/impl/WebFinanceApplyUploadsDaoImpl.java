package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.WebFinanceApplyUploadsDao;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class WebFinanceApplyUploadsDaoImpl extends BaseDaoImpl<WebFinanceApplyUploads> implements WebFinanceApplyUploadsDao{

	public WebFinanceApplyUploadsDaoImpl() {
		super(WebFinanceApplyUploads.class);
	}

	@Override
	public List<WebFinanceApplyUploads> upLoadList(Integer start,Integer limit, String state,HttpServletRequest request) {
		
		String sql="select"+
		" m.loginname as loginName,"+
		"u.id,"+
		"u.userID,"+
		"u.materialstype,"+
		"u.files,"+
		"u.`status`,"+
		"u.lastuploadtime,"+
		"m.truename,"+
		"u.rejectReason," +
		"(SELECT COUNT(*) from web_finance_apply_uploads as web where web.userID=u.userID and web.materialstype=u.materialstype) as materialCount"+
		" from web_finance_apply_uploads u inner join bp_cust_member m on u.userID=m.id  where 1=1 ";
		if(state!=null&&!state.equals("")){
			sql=sql+"and  u.STATUS="+state;
		}
		String loginname=request.getParameter("loginname");
		if(null!=loginname && !loginname.equals("")){
			sql=sql+" and m.loginname like '%"+loginname+"%'";
		}
		String userId=request.getParameter("userId");
		if(null!=userId && !userId.equals("")){
			sql=sql+" and u.userID="+userId;
		}
		String truename=request.getParameter("truename");
		if(null!=truename && !truename.equals("")){
			sql=sql+" and m.truename like '%"+truename+"%'";
		}
		sql=sql+" GROUP BY u.userID,u.materialstype";
		sql=sql+" order by u.lastuploadtime";
	//	System.out.println("-->"+sql.toString());
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.LONG).
			 addScalar("loginName",Hibernate.STRING).
			 addScalar("userID",Hibernate.LONG).
			 addScalar("materialstype",Hibernate.STRING).
			 addScalar("files",Hibernate.STRING).
			 addScalar("status",Hibernate.INTEGER).
			 addScalar("lastuploadtime",Hibernate.TIMESTAMP).
			 addScalar("truename",Hibernate.STRING).
			 addScalar("rejectReason",Hibernate.STRING).
			 addScalar("materialCount",Hibernate.LONG).
			 setResultTransformer(Transformers.aliasToBean(WebFinanceApplyUploads.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else {
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.LONG).
			 addScalar("loginName",Hibernate.STRING).
			 addScalar("userID",Hibernate.LONG).
			 addScalar("materialstype",Hibernate.STRING).
			 addScalar("files",Hibernate.STRING).
			 addScalar("status",Hibernate.INTEGER).
			 addScalar("lastuploadtime",Hibernate.TIMESTAMP).
			 addScalar("truename",Hibernate.STRING).
			 addScalar("rejectReason",Hibernate.STRING).
			 addScalar("materialCount",Hibernate.LONG).
			 setResultTransformer(Transformers.aliasToBean(WebFinanceApplyUploads.class)).
			 list();
		}
		return list;
	}
	
	@Override
	public List <WebFinanceApplyUploads> getlistByUserIDAndType(Integer userId,String materialstype ){
		String hql = "from WebFinanceApplyUploads where userID="+userId+" and materialstype='"+materialstype+"'";
		return super.findByHql(hql);
	}

	@Override
	public List<WebFinanceApplyUploads> upLoadOnLineList(Integer start,
			Integer limit, String state, HttpServletRequest request) {
		// TODO Auto-generated method stub
		String projectId=request.getParameter("projectId");
		String sql="select"+
		" m.loginname as loginName,"+
		"u.id,"+
		"u.userID,"+
		"u.materialstype,"+
		"u.files,"+
		"u.`status`,"+
		"u.lastuploadtime,"+
		"m.truename,"+
		"u.rejectReason," +
		"(SELECT COUNT(*) from web_finance_apply_uploads as web where web.userID=u.userID and web.materialstype=u.materialstype) as materialCount"+
		" from web_finance_apply_uploads u inner join bp_cust_member m on u.userID=m.id"+
		" where u.id not in("+
		" select p.materialsId from pl_web_show_materials p where p.projId="+projectId+
		" and p.isOnline=1"+
		" )";
		if(state!=null&&!state.equals("")){
			sql=sql+" and  u.STATUS="+state;
		}
		String userId=request.getParameter("userId");
		if(null!=userId && !userId.equals("")){
			sql=sql+" and u.userID="+userId;
		}
		String loginname=request.getParameter("loginname");
		if(null!=loginname && !loginname.equals("")){
			sql=sql+" and m.loginname like '%"+loginname+"%'";
		}
		String truename=request.getParameter("truename");
		if(null!=truename && !truename.equals("")){
			sql=sql+" and m.truename like '%"+truename+"%'";
		}
		sql=sql+" GROUP BY u.userID,u.materialstype";
		sql=sql+" order by u.lastuploadtime";
	//	System.out.println("-->"+sql.toString());
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.LONG).
			 addScalar("loginName",Hibernate.STRING).
			 addScalar("userID",Hibernate.LONG).
			 addScalar("materialstype",Hibernate.STRING).
			 addScalar("files",Hibernate.STRING).
			 addScalar("status",Hibernate.INTEGER).
			 addScalar("lastuploadtime",Hibernate.TIMESTAMP).
			 addScalar("truename",Hibernate.STRING).
			 addScalar("rejectReason",Hibernate.STRING).
			 addScalar("materialCount",Hibernate.LONG).
			 setResultTransformer(Transformers.aliasToBean(WebFinanceApplyUploads.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else {
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.LONG).
			 addScalar("loginName",Hibernate.STRING).
			 addScalar("userID",Hibernate.LONG).
			 addScalar("materialstype",Hibernate.STRING).
			 addScalar("files",Hibernate.STRING).
			 addScalar("status",Hibernate.INTEGER).
			 addScalar("lastuploadtime",Hibernate.TIMESTAMP).
			 addScalar("truename",Hibernate.STRING).
			 addScalar("rejectReason",Hibernate.STRING).
			 addScalar("materialCount",Hibernate.LONG).
			 setResultTransformer(Transformers.aliasToBean(WebFinanceApplyUploads.class)).
			 list();
		}
		return list;
	}
 
}