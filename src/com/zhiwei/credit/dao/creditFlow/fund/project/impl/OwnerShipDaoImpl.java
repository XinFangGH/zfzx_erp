package com.zhiwei.credit.dao.creditFlow.fund.project.impl;

import com.credit.proj.settlecenter.entity.OwnerShip;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.fund.project.OwnerShipDao;

public class OwnerShipDaoImpl extends BaseDaoImpl<OwnerShip> implements OwnerShipDao{
	public OwnerShipDaoImpl(){
		super(OwnerShip.class); 
	}
}
