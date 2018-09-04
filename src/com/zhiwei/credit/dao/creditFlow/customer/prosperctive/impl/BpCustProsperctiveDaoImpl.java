package com.zhiwei.credit.dao.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProsperctiveDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.AppUserService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpCustProsperctiveDaoImpl extends BaseDaoImpl<BpCustProsperctive> implements BpCustProsperctiveDao{
	@Resource
	private AppUserService appUserService;
	@Resource
	private CreditBaseDao creditBaseDao;
	
	public BpCustProsperctiveDaoImpl() {
		super(BpCustProsperctive.class);
	}

	@Override
	public List<BpCustProsperctive> getList(HttpServletRequest request,Integer start, Integer limit, String[] userIds,String departmentId) {
		// TODO Auto-generated method stub
		String prosperctiveType =request.getParameter("prosperctiveType");//表示客户类型，1表示正式客户，2表示潜在客户，3表示已排除客户
		String otherType =request.getParameter("otherType");
		String isAll =request.getParameter("isAll");
		if(prosperctiveType!=null &&!"".equals(prosperctiveType) &&!"null".equals(prosperctiveType)){
			String hql = " from BpCustProsperctive as p where p.prosperctiveType in ("+prosperctiveType+")";
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
			if (userIdsStr != "") {
				hql=hql+"  and (fn_check_repeat(p.creatorId,'"+userIdsStr+"') = 1 or fn_check_repeat(p.belongId,'"+userIdsStr+"') = 1) ";
			}
			String strs=ContextUtil.getBranchIdsStr();//39,40
			AppUser appuser = ContextUtil.getCurrentUser();
			if(appuser.getUserId().intValue()!=1){
				if(null!=strs && !"".equals(strs)){
					if(!"1".equals(strs)){
						hql=hql+"   and p.companyId in ("+strs+")" ;
					}
				}
			}
			
			//按门店分离
			if(null != departmentId && !"".equals(departmentId)){
				hql=hql+" and p.departmentId="+departmentId;
			}
			
			if(otherType!=null &&!"".equals(otherType) &&!"null".equals(otherType)){
				if(otherType.equals("1")){
					hql =hql+" and size(p.bpCustProspectiveFollowups)>0";
				}else if(otherType.equals("2")){
					hql =hql+" and size(p.bpCustProspectiveFollowups)<=0";
				}else if(otherType.equals("3")){
					SimpleDateFormat sd =new SimpleDateFormat("yyyy-MM-dd");
					hql =hql+" and p.nextFollowDate <= '"+sd.format(new Date())+"'";
				}
			}
			String customerName =request.getParameter("customerName");//查询条件  客户名称
			if(customerName!=null &&!"".equals(customerName) &&!"null".equals(customerName)){
				hql=hql+" and p.customerName like '%"+customerName+"%'";
			}
			
			String telephoneNumber =request.getParameter("telephoneNumber");//查询条件  联系电话
			if(telephoneNumber!=null &&!"".equals(telephoneNumber) &&!"null".equals(telephoneNumber)){
				hql=hql+" and p.telephoneNumber like '%"+telephoneNumber+"%'";
			}
			
			String customerType =request.getParameter("customerType");//查询条件  客户类型
			if(customerType!=null &&!"".equals(customerType) &&!"null".equals(customerType)){
				hql=hql+" and p.customerType = "+Short.valueOf(customerType);
			}
			//中金亿信新增
			String personType=request.getParameter("personType");
			if(personType!=null &&!"".equals(personType) &&!"null".equals(personType)){
				hql=hql+" and p.personType = "+Integer.valueOf(personType);
			}
			
			String followUpType =request.getParameter("followUpType");//查询条件 跟进状态
			if(followUpType!=null &&!"".equals(followUpType) &&!"null".equals(followUpType)){
				hql=hql+" and p.followUpType = "+Long.valueOf(followUpType);
			}
			
			
			//hql =hql =" order by p."
			if(start==null ||limit ==null){
				return this.getSession().createQuery(hql).list();
			}else{
				return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
			}
			
		}else{
			return null;
		}
		
	}

	@Override
	public List<BpCustProsperctive> getByCreatorId(String creatorId) {
		// TODO Auto-generated method stub
		String hql =" from BpCustProsperctive as bp where bp.prosperctiveType=2 and (fn_check_repeat(bp.creatorId,'"+creatorId+"') = 1)";
		return   this.getSession().createQuery(hql).list();
	}

	@Override
	public List<BpCustProsperctive> getByBelongId(String belongId) {
		String hql =" from BpCustProsperctive as bp where bp.prosperctiveType=2 and (fn_check_repeat(bp.belongId,'"+belongId+"') = 1)";
		return   this.getSession().createQuery(hql).list();
	}

	@Override
	public List<BpCustProsperctive> getByAreaId(String areaId) {
		String hql =" from BpCustProsperctive as bp where bp.prosperctiveType=2 and bp.areaId='"+areaId+"'";
		return   this.getSession().createQuery(hql).list();
	}

	@Override
	public BpCustProsperctive queryTelNumber(String telephoneNumber,String companyId) {
		String hql = "from BpCustProsperctive AS p where p.telephoneNumber=? and companyId=?" ;
		try{
			List list = creditBaseDao.queryHql(hql, new Object[]{telephoneNumber,Long.valueOf(companyId)}) ;
			return (BpCustProsperctive) (list.isEmpty()?null:list.get(0));
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

}