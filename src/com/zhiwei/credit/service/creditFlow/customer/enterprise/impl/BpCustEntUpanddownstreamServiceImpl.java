package com.zhiwei.credit.service.creditFlow.customer.enterprise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.enterprise.BpCustEntUpanddownstreamDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntDownstreamCustom;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpanddownstream;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.BpCustEntUpstreamCustom;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.BpCustEntUpanddownstreamService;

/**
 * 
 * @author 
 *
 */
public class BpCustEntUpanddownstreamServiceImpl extends BaseServiceImpl<BpCustEntUpanddownstream> implements BpCustEntUpanddownstreamService{
	@SuppressWarnings("unused")
	private BpCustEntUpanddownstreamDao dao;
	
	public BpCustEntUpanddownstreamServiceImpl(BpCustEntUpanddownstreamDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustEntUpanddownstream> getByentpriseid(Integer entpriseid) {
		// TODO Auto-generated method stub
		return dao.getByentpriseid(entpriseid);
	}

	@Override
	public List<BpCustEntUpstreamCustom> getByupAndDownCustomId(
			Integer upAndDownCustomId) {
		// TODO Auto-generated method stub
		return dao.getByupAndDownCustomId(upAndDownCustomId);
	}

	@Override
	public List<BpCustEntDownstreamCustom> getByupAndDownCustomId1(
			Integer upAndDownCustomId) {
		// TODO Auto-generated method stub
		return dao.getByupAndDownCustomId1(upAndDownCustomId);
	}

	@Override
	public BpCustEntUpanddownstream getByeid(Integer upAndDownCustomId) {
		// TODO Auto-generated method stub
		return dao.getByeid(upAndDownCustomId);
	}

	@Override
	public BpCustEntDownstreamCustom getByedownid(Integer downCustomId) {
		// TODO Auto-generated method stub
		return dao.getByedownid(downCustomId);
	}

	@Override
	public BpCustEntUpstreamCustom getByeupid(Integer upCustomId) {
		// TODO Auto-generated method stub
		return dao.getByeupid(upCustomId);
	}

}