package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.P2pFriendlink;

/**
 * 
 * @author 
 *
 */
public interface P2pFriendlinkService extends BaseService<P2pFriendlink>{
	public P2pFriendlink save(P2pFriendlink link,String path);
	
	/**
	 * 排序查询友情链接所有值
	 * @return
	 */
	public List<P2pFriendlink>  listOrderBy();
	
	public P2pFriendlink save(P2pFriendlink link);
	
}


