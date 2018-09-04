package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.OaNewsMessageDao;
import com.zhiwei.credit.model.p2p.OaNewsMessage;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class OaNewsMessageDaoImpl extends BaseDaoImpl<OaNewsMessage> implements OaNewsMessageDao{

	public OaNewsMessageDaoImpl() {
		super(OaNewsMessage.class);
	}

	/**
	 * 获取单个用户的所有站内信
	 */
	@Override
	public List<OaNewsMessage> getUserAll(Long userId) {
		List<OaNewsMessage> list=null;
		StringBuffer hql = new StringBuffer(" from OaNewsMessage ");
		hql.append(" where (isDelete!=1 or isDelete is null)  and recipient= ").append(userId);
		 list = findByHql(hql.toString());
		
		return list;
	}
}