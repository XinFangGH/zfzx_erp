package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.update.SystemServiceUpdateDao;
import com.zhiwei.credit.model.system.update.SystemServiceUpdate;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SystemServiceUpdateDaoImpl extends BaseDaoImpl<SystemServiceUpdate> implements SystemServiceUpdateDao{

	public SystemServiceUpdateDaoImpl() {
		super(SystemServiceUpdate.class);
	}

}