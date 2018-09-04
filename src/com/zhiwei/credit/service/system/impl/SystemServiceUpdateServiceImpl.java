package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.update.SystemServiceUpdateDao;
import com.zhiwei.credit.model.system.update.SystemServiceUpdate;
import com.zhiwei.credit.service.system.update.SystemServiceUpdateService;

/**
 * 
 * @author 
 *
 */
public class SystemServiceUpdateServiceImpl extends BaseServiceImpl<SystemServiceUpdate> implements SystemServiceUpdateService{
	@SuppressWarnings("unused")
	private SystemServiceUpdateDao dao;
	
	public SystemServiceUpdateServiceImpl(SystemServiceUpdateDao dao) {
		super(dao);
		this.dao=dao;
	}

}