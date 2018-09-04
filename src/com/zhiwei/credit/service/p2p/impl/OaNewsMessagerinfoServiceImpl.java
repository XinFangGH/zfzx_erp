package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.p2p.OaNewsMessagerinfoDao;
import com.zhiwei.credit.model.p2p.OaNewsMessagerinfo;
import com.zhiwei.credit.service.p2p.OaNewsMessagerinfoService;

/**
 * 
 * @author 
 *
 */
public class OaNewsMessagerinfoServiceImpl extends BaseServiceImpl<OaNewsMessagerinfo> implements OaNewsMessagerinfoService{
	@SuppressWarnings("unused")
	private OaNewsMessagerinfoDao dao;
	
	public OaNewsMessagerinfoServiceImpl(OaNewsMessagerinfoDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<OaNewsMessagerinfo> getAllInfo(PagingBean pb,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.getAllInfo(pb,request);
	}

}