package com.zhiwei.credit.dao.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProspectiveFollowupDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveFollowup;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppUserService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustProspectiveFollowupDaoImpl extends BaseDaoImpl<BpCustProspectiveFollowup> implements BpCustProspectiveFollowupDao{
	@Resource
	private AppUserService appUserService;
	public BpCustProspectiveFollowupDaoImpl() {
		super(BpCustProspectiveFollowup.class);
	}

	@Override
	public List<BpCustProspectiveFollowup> getList(HttpServletRequest request,Integer start, Integer limit, String[] userIds,String departmentId) {
		String customerName =request.getParameter("customerName");
		String followPersonId =request.getParameter("followPersonId");
		String  followUpStatus =request.getParameter("followUpStatus");
		String personType=request.getParameter("personType");
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
		
		String hql =" from BpCustProspectiveFollowup as bp where 1=1 " ;
		if (userIdsStr != ""){
			hql=hql+" and (fn_check_repeat(bp.followPersonId,'"+userIdsStr+"') = 1) ";
		}
		
		String strs=ContextUtil.getBranchIdsStr();//39,40
		AppUser appuser = ContextUtil.getCurrentUser();
		if(appuser.getUserId().intValue()!=1){
			if(null!=strs && !"".equals(strs)){
				if(!"1".equals(strs)){
					hql=hql+"   and bp.bpCustProsperctive.companyId in ("+strs+")" ;
				}
			}
		}
		//按门店分离数据
		if(null !=departmentId && !"".equals(departmentId)){
			hql = hql + " and bp.bpCustProsperctive.departmentId"+departmentId;
		}
		
		if(customerName!=null && !"".equals(customerName)&& !"null".equals(customerName)){
			hql =hql +" and bp.bpCustProsperctive.customerName like '%"+customerName+"%'";
		}
		if(followUpStatus!=null && !"".equals(followUpStatus)&& !"null".equals(followUpStatus)){
			hql =hql +" and bp.followUpStatus ="+Long.valueOf(followUpStatus);
		}
		if(followPersonId!=null && !"".equals(followPersonId)&& !"null".equals(followPersonId)){
			hql=hql+" and (fn_check_repeat(bp.followPersonId,'"+followPersonId+"') = 1) ";
		}
		if(personType!=null && !"".equals(personType)&& !"null".equals(personType)){
			hql =hql +" and bp.personType ="+Integer.valueOf(personType);
		}
		hql=hql +" order by bp.bpCustProsperctive.perId asc";
		if(start!=null && limit !=null){
			return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
		}else{
			return this.getSession().createQuery(hql).list();
		}
	}

	@Override
	public List<BpCustProspectiveFollowup> getListByPerId(String perId,
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		String hql =" from BpCustProspectiveFollowup as bp where bp.bpCustProsperctive.perId=?  order by bp.followDate desc";
		if(start==null ||limit ==null){
			return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(perId)).list();
		}else{
			return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(perId)).setFirstResult(start).setMaxResults(limit).list();
		}
	}

}