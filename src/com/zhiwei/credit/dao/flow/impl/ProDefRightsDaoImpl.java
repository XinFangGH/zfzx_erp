package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.flow.ProDefRightsDao;
import com.zhiwei.credit.model.flow.ProDefRights;

@SuppressWarnings("unchecked")
public class ProDefRightsDaoImpl extends BaseDaoImpl<ProDefRights> implements ProDefRightsDao{

	public ProDefRightsDaoImpl() {
		super(ProDefRights.class);
	}

	@Override
	public ProDefRights findByDefId(Long defId) {
		String hql = "from ProDefRights pd where pd.proDefinition.defId = ?";
		List<ProDefRights> list = findByHql(hql,new Object[]{defId});
		if(list.size()>0){
			return list.get(0);
		}else{
			return new ProDefRights();
		}
	}

	@Override
	public ProDefRights findByTypeId(Long proTypeId) {
		String hql = "from ProDefRights pd where pd.globalType.proTypeId = ?";
		List<ProDefRights> list = findByHql(hql,new Object[]{proTypeId});
		if(list.size()>0){
			return list.get(0);
		}else{
			return new ProDefRights();
		}
	}

}