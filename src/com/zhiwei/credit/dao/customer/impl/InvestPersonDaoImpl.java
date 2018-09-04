package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.customer.InvestPersonDao;
import com.zhiwei.credit.model.customer.InvestPerson;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class InvestPersonDaoImpl extends BaseDaoImpl<InvestPerson> implements InvestPersonDao{

	public InvestPersonDaoImpl() {
		super(InvestPerson.class);
	}

	/**
	 * 获取投资客户列表
	 * @param userIdsStr
	 * @param pb
	 * @param request
	 * @return
	 */

	public List<InvestPerson> getList(String companyId,String userIdsStr,PagingBean pb,HttpServletRequest request,Map map){
		StringBuffer hql = new StringBuffer("from InvestPerson p where 1=1 ");
		
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql.append(" and p.companyId in ("+strs+")"); //
		}
		if (userIdsStr != "") {
			hql.append("and fn_check_repeat(p.createrId,'"+userIdsStr+"') = 1 ");
		}
	/*	if(null != companyId && !"".equals(companyId)){
			hql.append("and  p.companyId in ("+companyId+") ");
		}*/
		String comId = request.getParameter("companyId");
		if(!"".equals(comId) && null != comId) {
			hql.append("and p.companyId ='"+comId+"' ");
		}
		
		String name = request.getParameter("Q_perName_S_LK");
		if(!"".equals(name) && null != name) {
			hql.append("and p.perName like '%"+name+"%' ");
		}
		
		String sex = request.getParameter("Q_perSex_S_EQ");
		if(!"".equals(sex) && null != sex) {
			hql.append("and p.perSex ='"+sex+"' ");
		}
		
		String cardType = request.getParameter("Q_cardType_S_EQ");
		if(!"".equals(cardType) && null != cardType) {
			hql.append("and p.cardType ='"+cardType+"' ");
		}
		
		String cardNumber = request.getParameter("Q_cardNumber_S_LK");
		if(!"".equals(cardNumber) && null != cardNumber) {
			hql.append("and p.cardNumber like'%"+cardNumber+"%' ");
		}
		String level = request.getParameter("customerLevel");
		if(!"".equals(level)&& null != level){
			hql.append("and p.customerLevel ='"+level+"'");
		}
		String userIds = request.getParameter("useIds");
		if(!"".equals(userIds)&& null != userIds){
			userIds = userIds.substring(0, userIds.length()-1);
			userIds.replace("[", "");
			userIds.replace("]", "");
			hql.append("and p.perId in ("+userIds+")");
		}
		if(map != null && map.size() != 0){
			String area = map.get("areaId").toString();
			if(null != area && !area.equals("")){
				hql.append(" and p.areaId like '%"+area+"%'");
			}
		}
		String starDate = request.getParameter("Q_intentDate_D_GE");
		String endDate = request.getParameter("Q_intentDate_D_LE");
		if((null !=starDate && !"".equals(starDate)) && (null !=endDate && !"".equals(endDate))){
			String[] sd = starDate.split("-");
			String[] ed = endDate.split("-");
			if(Integer.valueOf(sd[0]) <Integer.valueOf(ed[0])){
				hql.append(" and (Date_FORMAT(p.perBirthday,'%m-%d')>=Date_FORMAT('"+starDate+"','%m-%d') or Date_FORMAT(p.perBirthday,'%m-%d')<=Date_FORMAT('"+endDate+"','%m-%d'))");
			}else{
				hql.append(" and (Date_FORMAT(p.perBirthday,'%m-%d')>=Date_FORMAT('"+starDate+"','%m-%d') and Date_FORMAT(p.perBirthday,'%m-%d')<=Date_FORMAT('"+endDate+"','%m-%d'))");
			}
		}else{
			if(null !=starDate && !"".equals(starDate)){
				hql.append(" and Date_FORMAT(p.perBirthday,'%m-%d')>=Date_FORMAT('"+starDate+"','%m-%d')");
			}
			if(null !=endDate && !"".equals(endDate)){
				hql.append(" and Date_FORMAT(p.perBirthday,'%m-%d')<=Date_FORMAT('"+endDate+"','%m-%d')");
			}
		}
		String belonger = request.getParameter("Q_belongedId_S_LK");
		if(null !=belonger && !belonger.equals("")){
			hql.append(" and p.belongedId ='"+belonger+"'");
		}
		String dir =request.getParameter("dir");
		if(null !=dir){
			hql.append(" order by p.perBirthday "+dir);
		}else{
			hql.append(" order by p.perId desc");
		}
		if(pb == null) {
			return findByHql(hql.toString());
		} else {
			return findByHql(hql.toString(), null, pb);
		}
	}
	
	public InvestPerson getByCardNumber(String cardNumber) {
		String hql = "from InvestPerson where cardNumber = '"+cardNumber+"'";
		if( findByHql(hql) != null && findByHql(hql).size()>0) {
			return findByHql(hql).get(0);
		}else {
			return null;
		}
	}
}