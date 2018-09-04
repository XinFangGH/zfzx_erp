package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.ContractConfigDao;
import com.zhiwei.credit.model.customer.ContractConfig;
import com.zhiwei.credit.service.customer.ContractConfigService;

public class ContractConfigServiceImpl extends BaseServiceImpl<ContractConfig> implements ContractConfigService{
	private ContractConfigDao dao;
	
	public ContractConfigServiceImpl(ContractConfigDao dao) {
		super(dao);
		this.dao=dao;
	}

}