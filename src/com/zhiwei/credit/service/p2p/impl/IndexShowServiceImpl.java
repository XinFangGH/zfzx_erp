package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.IndexShowDao;
import com.zhiwei.credit.model.p2p.IndexShow;
import com.zhiwei.credit.service.p2p.IndexShowService;

/**
 * 
 * @author 
 *
 */
public class IndexShowServiceImpl extends BaseServiceImpl<IndexShow> implements IndexShowService{
	@SuppressWarnings("unused")
	private IndexShowDao dao;
	
	public IndexShowServiceImpl(IndexShowDao dao) {
		super(dao);
		this.dao=dao;
	}

}