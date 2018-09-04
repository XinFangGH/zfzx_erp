package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.UploadLogDao;
import com.zhiwei.credit.model.system.UploadLog;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class UploadLogDaoImpl extends BaseDaoImpl<UploadLog> implements UploadLogDao{

	public UploadLogDaoImpl() {
		super(UploadLog.class);
	}

	@Override
	public List<UploadLog> listBybBidId(String bidId) {
		String hql="from UploadLog as u where u.bidId=?";
		return this.findByHql(hql, new Object[]{bidId});
	}

}