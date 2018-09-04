package com.zhiwei.credit.dao.creditFlow.fund.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.fund.project.SettlementInfoDao;
import com.zhiwei.credit.model.creditFlow.fund.project.SettlementInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SettlementInfoDaoImpl extends BaseDaoImpl<SettlementInfo> implements SettlementInfoDao{

	public SettlementInfoDaoImpl() {
		super(SettlementInfo.class);
	}
	@Override
	public List<SettlementInfo> listByOrgId(HttpServletRequest request){
		String orgId = request.getParameter("orgId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String sql = "from SettlementInfo ";
		if(startDate!=null&&!"".equals(startDate)&&endDate!=null&&!"".equals(endDate)){
			sql = sql+"where 1=1 and createDate>='"+startDate+"' and createDate<= '"+endDate+"' ";
			
		}else{
			sql = sql+" where 1=2 ";
		}
		if(orgId!=null&&!"".equals(orgId)){
			sql = sql+" and orgId="+orgId;
		}
		return this.findByHql(sql);
	}
	@Override
	public List<SettlementInfo> listByOrgId(String orgId,String startDate,String endDate){
		String sql = "from SettlementInfo ";
		if(startDate!=null&&!"".equals(startDate)&&endDate!=null&&!"".equals(endDate)){
			sql = sql+"where 1=1 and createDate>='"+startDate+"' and createDate<= '"+endDate+"' ";
			
		}else{
			sql = sql+" where 1=2 ";
		}
		if(orgId!=null&&!"".equals(orgId)){
			sql = sql+" and orgId="+orgId;
		}
		return this.findByHql(sql);
	}
}