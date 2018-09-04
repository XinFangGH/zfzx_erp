package com.zhiwei.credit.dao.admin.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import org.hibernate.Query;


import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.DepreRecordDao;
import com.zhiwei.credit.model.admin.DepreRecord;

public class DepreRecordDaoImpl extends BaseDaoImpl<DepreRecord> implements DepreRecordDao{

	public DepreRecordDaoImpl() {
		super(DepreRecord.class);
	}

	@Override
	public Date findMaxDate(Long assetsId) {
		String hql="select max(vo.calTime) from DepreRecord vo where vo.fixedAssets.assetsId=?";//where vo.fixedAssets.assetsId=?
		Query query =getSession().createQuery(hql);
		query.setLong(0, assetsId);
		Date date=(Date)query.list().get(0);
		return date;
	}

}