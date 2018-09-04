package com.zhiwei.credit.dao.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.archive.ArchFlowConfDao;
import com.zhiwei.credit.model.archive.ArchFlowConf;

public class ArchFlowConfDaoImpl extends BaseDaoImpl<ArchFlowConf> implements ArchFlowConfDao{

	public ArchFlowConfDaoImpl() {
		super(ArchFlowConf.class);
	}

	@Override
	public ArchFlowConf getByFlowType(Short archType) {
		String hql="from ArchFlowConf vo where vo.archType=?";
		Object[] objs={archType};
		List<ArchFlowConf> list=findByHql(hql, objs);
		if(list.size()==1){
			return list.get(0);
		}else{
		    return null;
		}
	}

}