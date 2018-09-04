package com.zhiwei.credit.service.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.admin.ConfPrivilege;

/**
 * @description ConfPrivilegeService
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public interface ConfPrivilegeService extends BaseService<ConfPrivilege> {

	/**
	 * @description 获取该数据的权限
	 * @param confId
	 *            confId
	 * @param s
	 *            1=查看,2=修改,3=建立纪要
	 * @return 0.没有权限,1.查看，2.修改，3.创建
	 */
	Short getPrivilege(Long confId, Short s);
}
