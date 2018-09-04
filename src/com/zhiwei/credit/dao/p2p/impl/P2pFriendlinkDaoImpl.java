package com.zhiwei.credit.dao.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.p2p.P2pFriendlinkDao;
import com.zhiwei.credit.model.p2p.P2pFriendlink;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class P2pFriendlinkDaoImpl extends BaseDaoImpl<P2pFriendlink> implements P2pFriendlinkDao{

	public P2pFriendlinkDaoImpl() {
		super(P2pFriendlink.class);
	}
	
	public List<P2pFriendlink> getOrderby() {
		String hql = "from P2pFriendlink as  fl where 1=1  order by fl.orderList asc";
//		System.out.println("****************进入查询方法中*****************888888");
		 List<P2pFriendlink> list =super.findByHql(hql);
		return super.findByHql(hql);
	 
//		return this.getSession().createQuery(hql).list();
	}

	@Override
	public List<P2pFriendlink> getLinks(boolean isLogo) {
		String hql = "from P2pFriendlink as link where link.linkType = ";
		if(isLogo==true){
			hql +=" 1 ";
		}else {
			hql +=" 2 ";
		}
		return super.findByHql(hql);
	}

}