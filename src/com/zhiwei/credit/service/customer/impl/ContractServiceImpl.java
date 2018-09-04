package com.zhiwei.credit.service.customer.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.customer.ContractDao;
import com.zhiwei.credit.model.customer.Contract;
import com.zhiwei.credit.service.customer.ContractService;

public class ContractServiceImpl extends BaseServiceImpl<Contract> implements ContractService{
	private ContractDao dao;
	
	public ContractServiceImpl(ContractDao dao) {
		super(dao);
		this.dao=dao;
	}

}