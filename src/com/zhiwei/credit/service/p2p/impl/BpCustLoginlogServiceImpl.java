package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.BpCustLoginlogDao;
import com.zhiwei.credit.model.p2p.BpCustLoginlog;
import com.zhiwei.credit.service.p2p.BpCustLoginlogService;

/**
 * 
 * @author 
 *
 */
public class BpCustLoginlogServiceImpl extends BaseServiceImpl<BpCustLoginlog> implements BpCustLoginlogService{
	@SuppressWarnings("unused")
	private BpCustLoginlogDao dao;
	
	public BpCustLoginlogServiceImpl(BpCustLoginlogDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustLoginlog> getAllList(HttpServletRequest request,
			Integer start, Integer limit) {
		// TODO Auto-generated method stub
		return dao.getAllList(request,start,limit);
	}

}