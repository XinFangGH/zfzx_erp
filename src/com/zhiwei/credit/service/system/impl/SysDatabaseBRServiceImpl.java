package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.SysDatabaseBRDao;
import com.zhiwei.credit.model.system.SysDatabaseBR;
import com.zhiwei.credit.service.system.SysDatabaseBRService;

/**
 * 
 * @author 
 *
 */
public class SysDatabaseBRServiceImpl extends BaseServiceImpl<SysDatabaseBR> implements SysDatabaseBRService{
	@SuppressWarnings("unused")
	private SysDatabaseBRDao dao;
	
	public SysDatabaseBRServiceImpl(SysDatabaseBRDao dao) {
		super(dao);
		this.dao=dao;
	}
}