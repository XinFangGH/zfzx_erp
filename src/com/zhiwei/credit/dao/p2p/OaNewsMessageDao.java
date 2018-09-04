package com.zhiwei.credit.dao.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.p2p.OaNewsMessage;

/**
 * 
 * @author 
 *
 */
public interface OaNewsMessageDao extends BaseDao<OaNewsMessage>{
	/**
	 * 获取单个用户所有未删除的站内信
	 */
	public List<OaNewsMessage> getUserAll(Long userId);
}