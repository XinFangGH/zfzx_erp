package com.zhiwei.credit.service.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProsperctiveDao;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProsperctiveService;

/**
 * 
 * @author 
 *
 */
public class BpCustProsperctiveServiceImpl extends BaseServiceImpl<BpCustProsperctive> implements BpCustProsperctiveService{
	@SuppressWarnings("unused")
	private BpCustProsperctiveDao dao;
	
	public BpCustProsperctiveServiceImpl(BpCustProsperctiveDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustProsperctive> getList(HttpServletRequest request,
			Integer start, Integer limit, String[] userIds,String departmentId) {
		// TODO Auto-generated method stub
		return this.dao.getList(request,start,limit,userIds,departmentId);
	}

	@Override
	public List<BpCustProsperctive> getByCreatorId(String creatorId) {
		// TODO Auto-generated method stub
		return this.dao.getByCreatorId(creatorId);
	}

	@Override
	public List<BpCustProsperctive> getByBelongId(String belongId) {
		// TODO Auto-generated method stub
		return this.dao.getByBelongId(belongId);
	}

	@Override
	public List<BpCustProsperctive> getByAreaId(String areaId) {
		// TODO Auto-generated method stub
		return this.dao.getByAreaId(areaId);
	}

	@Override
	public BpCustProsperctive queryTelNumber(String telephoneNumber,String companyId) {
		return dao.queryTelNumber(telephoneNumber,companyId);
	}

}