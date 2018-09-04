package com.zhiwei.credit.dao.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.p2p.P2pFriendlink;

/**
 * 
 * @author 
 *
 */
public interface P2pFriendlinkDao extends BaseDao<P2pFriendlink>{
	public List<P2pFriendlink> getLinks(boolean isLogo);
	public List<P2pFriendlink> getOrderby() ;
}