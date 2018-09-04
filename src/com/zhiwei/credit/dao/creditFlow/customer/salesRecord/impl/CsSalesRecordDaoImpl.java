package com.zhiwei.credit.dao.creditFlow.customer.salesRecord.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.customer.salesRecord.CsSalesRecordDao;
import com.zhiwei.credit.model.creditFlow.customer.salesRecord.CsSalesRecord;
import com.zhiwei.credit.model.system.AppUser;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsSalesRecordDaoImpl extends BaseDaoImpl<CsSalesRecord> implements CsSalesRecordDao{

	public CsSalesRecordDaoImpl() {
		super(CsSalesRecord.class);
	}

	@Override
	public List<CsSalesRecord> getListByRequest(HttpServletRequest request,
			Integer start, Integer limit, String[] userIds) {
		SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String hql =" from CsSalesRecord as cs where 1=1 ";
		
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
	    String  roleType=ContextUtil.getRoleTypeSession();
		
		String userIdsStr = "";
		if (userIds != null) {
			StringBuffer sb1 = new StringBuffer();
			for(String userid : userIds) {
				sb1.append(userid);
				sb1.append(",");
			}
			userIdsStr = sb1.toString().substring(0,sb1.length()-1);
		}
		if(flag && roleType.equals("control")){ //如果是集团版本  并且当前roleType为管控角色
			userIdsStr="";
		}
		
		if (userIdsStr != ""){
			hql=hql+" and (fn_check_repeat(cs.userId,'"+userIdsStr+"') = 1) ";
		}
		
		String strs=ContextUtil.getBranchIdsStr();//39,40
		AppUser appuser = ContextUtil.getCurrentUser();
		if(appuser.getUserId().intValue()!=1){
			if(null!=strs && !"".equals(strs)){
				if(!"1".equals(strs)){
					hql=hql+"   and cs.companyId in ("+strs+")" ;
				}
			}
		}
		
		if(request.getParameter("userName")!=null&&!"".equals(request.getParameter("userName"))){
			hql=hql +" and cs.userName like '%"+request.getParameter("userName")+"%'";
		}
		if(request.getParameter("userNumber")!=null&&!"".equals(request.getParameter("userNumber"))){
			hql=hql +" and cs.userNumber like '%"+request.getParameter("userNumber")+"%'";
		}
		if(request.getParameter("createDate")!=null&&!"".equals(request.getParameter("createDate"))){
			hql=hql +" and cs.createDate = "+sd.format(request.getParameter("createDate"));
		}
		if(request.getParameter("personType")!=null&&!"".equals(request.getParameter("personType"))){
			hql=hql +" and cs.personType = "+Integer.valueOf(request.getParameter("personType"));
		}
		hql=hql+"  order by cs.createDate asc";
		List<CsSalesRecord>  list =null;
		if(start!=null&&limit!=null){
			list=this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
		}else{
			list=this.getSession().createQuery(hql).list();
		}
		return list;
	}

}