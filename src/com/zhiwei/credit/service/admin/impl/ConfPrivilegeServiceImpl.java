package com.zhiwei.credit.service.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.admin.ConfPrivilegeDao;
import com.zhiwei.credit.model.admin.ConfPrivilege;
import com.zhiwei.credit.service.admin.ConfPrivilegeService;

/**
 * @description ConfPrivilegeServiceImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConfPrivilegeServiceImpl extends BaseServiceImpl<ConfPrivilege>
		implements ConfPrivilegeService {
	private ConfPrivilegeDao dao;

	public ConfPrivilegeServiceImpl(ConfPrivilegeDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * @description 获取该数据的权限
	 * @param confId
	 *            confId
	 * @param s
	 *            1=查看,2=修改,3=建立纪要
	 * @return 0.没有权限,1.查看，2.修改，3.创建
	 */
	@Override
	public Short getPrivilege(Long confId, Short s) {
		return dao.getPrivilege(ContextUtil.getCurrentUserId(), confId, s);
	}

}