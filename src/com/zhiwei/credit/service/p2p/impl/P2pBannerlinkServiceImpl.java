package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.P2pBannerlinkDao;
import com.zhiwei.credit.model.p2p.P2pBannerlink;
import com.zhiwei.credit.service.p2p.P2pBannerlinkService;

/**
 * 
 * @author 
 *
 */
public class P2pBannerlinkServiceImpl extends BaseServiceImpl<P2pBannerlink> implements P2pBannerlinkService{
	@SuppressWarnings("unused")
	private P2pBannerlinkDao dao;
	
	public P2pBannerlinkServiceImpl(P2pBannerlinkDao dao) {
		super(dao);
		this.dao=dao;
	}

}