package com.zhiwei.credit.service.archive.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.archive.ArchFlowConfDao;
import com.zhiwei.credit.model.archive.ArchFlowConf;
import com.zhiwei.credit.service.archive.ArchFlowConfService;

public class ArchFlowConfServiceImpl extends BaseServiceImpl<ArchFlowConf> implements ArchFlowConfService{
	private ArchFlowConfDao dao;
	
	public ArchFlowConfServiceImpl(ArchFlowConfDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public ArchFlowConf getByFlowType(Short archType) {
		return dao.getByFlowType(archType);
	}

	@Override
	public Long getDefId(Short archType) {
		ArchFlowConf ac=  getByFlowType(archType);
		if(ac !=null){
			return ac.getDefId();
		}else{
			return null;
		}
		
	}

}