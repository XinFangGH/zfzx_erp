package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.SysConfigDao;
import com.zhiwei.credit.model.system.SysConfig;

public class SysConfigDaoImpl extends BaseDaoImpl<SysConfig> implements SysConfigDao{

	public SysConfigDaoImpl() {
		super(SysConfig.class);
	}

	@Override
	public SysConfig findByKey(String key) {
		String hql="from SysConfig vo where vo.configKey=?";
		Object[] objs={key};
		List<SysConfig> list=findByHql(hql, objs);
		if(list.size()>0)
		return (SysConfig)list.get(0);
		else return null;
	}

	@Override
	public List<SysConfig> findConfigByTypeKey(String typeKey) {
		String hql="from SysConfig vo where vo.typeKey=?";
		Object[] objs={typeKey};
		return findByHql(hql, objs);
	}

	@Override
	public List findTypeKeys() {
		String sql="select vo.typeKey from SysConfig vo group by vo.typeKey";
		Query query=getSession().createQuery(sql);
		return query.list();
	}
}