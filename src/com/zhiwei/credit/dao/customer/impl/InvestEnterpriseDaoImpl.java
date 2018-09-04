package com.zhiwei.credit.dao.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.customer.InvestEnterpriseDao;
import com.zhiwei.credit.model.customer.InvestEnterprise;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class InvestEnterpriseDaoImpl extends BaseDaoImpl<InvestEnterprise> implements InvestEnterpriseDao{

	public InvestEnterpriseDaoImpl() {
		super(InvestEnterprise.class);
	}

	@Override
	public List<InvestEnterprise> getList(HttpServletRequest request,
			PagingBean pb,String userIdStr) {
		
		StringBuffer hql=new StringBuffer("from InvestEnterprise as e where 1=1");
			String str=ContextUtil.getBranchIdsStr();
			String companyId=request.getParameter("companyId");
			if(null!=companyId && !"".equals(companyId)){
				str=companyId;
			}
			if(null!=str && !"".equals(str)){
				hql.append(" and e.companyId in ("+str+")");
			}
			//add by zcb 2014-03-19
			String businessType=request.getParameter("businessType");
			String isAll=request.getParameter("isAll");
			if(null!=businessType && !businessType.equals("") && !"true".equals(isAll)&&!"undefined".equals(businessType)){
				hql.append(" and e.businessType='"+businessType+"'");
			}else if("undefined".equals(businessType)){
				hql.append(" and e.businessType = ''");
			}
			if(null!=userIdStr && !userIdStr.equals("")){
				hql.append(" and fn_check_repeat(e.belongedId,'"+userIdStr+"') = 1");
			}
			String	enterprisename = request.getParameter("enterprisename");
			
			if(null!=enterprisename && !"".equals(enterprisename)){
				hql.append(" and e.enterprisename like '%"+enterprisename+"%'");
			}
			String organizecode=request.getParameter("organizecode");
			if(null!=organizecode && !"".equals(organizecode)){
				hql.append(" and e.organizecode like '%"+organizecode+"%'");
			}
			String hangye=request.getParameter("hangye");
			if(null!=hangye && !"".equals(hangye)){
				hql.append(" and e.hangyetype like '%"+hangye+"%'");
			}
			String cciaa=request.getParameter("cciaa");
			if(null!=cciaa && !cciaa.equals("")){
				hql.append(" and e.cciaa like '%"+cciaa+"%'");
			}
		if(null==pb){
			return this.findByHql(hql.toString());
		}else{
			return this.findByHql(hql.toString(), null, pb);
		}
		
	}
	@Override
	public List<InvestEnterprise> getExcelList(HttpServletRequest request,
			PagingBean pb) {
		
		StringBuffer hql=new StringBuffer("from InvestEnterprise as e where 1=1");
		try {
			String str=ContextUtil.getBranchIdsStr();
			String companyId=request.getParameter("companyId");
			if(null!=companyId && !"".equals(companyId)){
				str=companyId;
			}
			if(null!=str && !"".equals(str)){
				hql.append(" and e.companyId in ("+str+")");
			}
			
			
			String	enterprisename = java.net.URLDecoder.decode(request.getParameter("enterprisename"),"UTF-8");
			if(null!=enterprisename && !"".equals(enterprisename)){
				hql.append(" and e.enterprisename like '%"+enterprisename+"%'");
			}
			
			String businessType = java.net.URLDecoder.decode(request.getParameter("businessType"),"UTF-8");
			if(null!=businessType && !"".equals(businessType)){
				hql.append(" and e.businessType = '"+businessType+"'");
			}
			
			String organizecode=java.net.URLDecoder.decode(request.getParameter("organizecode"),"UTF-8");
			if(null!=organizecode && !"".equals(organizecode)){
				hql.append(" and e.organizecode like '%"+organizecode+"%'");
			}
			String hangye=request.getParameter("hangye");
			if(null!=hangye && !"".equals(hangye)){
				hql.append(" and e.hangyetype like '%"+hangye+"%'");
			}
			String cciaa=java.net.URLDecoder.decode(request.getParameter("cciaa"),"UTF-8");
			if(null!=cciaa && !cciaa.equals("")){
				hql.append(" and e.cciaa like '%"+cciaa+"%'");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		if(null==pb){
			return this.findByHql(hql.toString());
		}else{
			return this.findByHql(hql.toString(), null, pb);
		}
		
	}

	@Override
	public InvestEnterprise getByOrganizecode(String organizecode) {
		String hql="from InvestEnterprise as e where e.organizecode=?";
		return (InvestEnterprise) getSession().createQuery(hql).setParameter(0, organizecode).uniqueResult();
	}

	@Override
	public List<InvestEnterprise> getList(String businessType, String userIdStr) {
			StringBuffer hql=new StringBuffer("from InvestEnterprise as e where 1=1");
			String str=ContextUtil.getBranchIdsStr();

			if(null!=str && !"".equals(str)){
				hql.append(" and e.companyId in ("+str+")");
			}
			//add by zcb 2014-03-19
			if(null!=businessType && !businessType.equals("")){
				hql.append(" and e.businessType='"+businessType+"'");
			}
			if(null!=userIdStr && !userIdStr.equals("")){
				hql.append(" and fn_check_repeat(e.belongedId,'"+userIdStr+"') = 1");
			}
			return super.findByHql(hql.toString());
	}
}