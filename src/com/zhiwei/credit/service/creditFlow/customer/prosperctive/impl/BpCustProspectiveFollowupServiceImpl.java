package com.zhiwei.credit.service.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProspectiveFollowupDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveFollowup;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveFollowupService;

/**
 * 
 * @author 
 *
 */
public class BpCustProspectiveFollowupServiceImpl extends BaseServiceImpl<BpCustProspectiveFollowup> implements BpCustProspectiveFollowupService{
	@SuppressWarnings("unused")
	private BpCustProspectiveFollowupDao dao;
	
	public BpCustProspectiveFollowupServiceImpl(BpCustProspectiveFollowupDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustProspectiveFollowup> getList(HttpServletRequest request,
			Integer start, Integer limit, String[] userIds,String departmentId) {
		// TODO Auto-generated method stub
		return dao.getList(request,start,limit,userIds,departmentId);
	}

	@Override
	public List<BpCustProspectiveFollowup> getListByPerId(String perId,
			HttpServletRequest request, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return this.dao.getListByPerId(perId,request,start,limit);
	}

}