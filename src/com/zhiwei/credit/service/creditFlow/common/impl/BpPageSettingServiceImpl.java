package com.zhiwei.credit.service.creditFlow.common.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.common.BpPageSettingDao;
import com.zhiwei.credit.model.creditFlow.common.BpPageSetting;
import com.zhiwei.credit.service.creditFlow.common.BpPageSettingService;

/**
 * 
 * @author 
 *
 */
public class BpPageSettingServiceImpl extends BaseServiceImpl<BpPageSetting> implements BpPageSettingService{
	@SuppressWarnings("unused")
	private BpPageSettingDao dao;
	
	public BpPageSettingServiceImpl(BpPageSettingDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpPageSetting> listByParentId(Long parentId) {
		
		return dao.listByParentId(parentId);
	}

	@Override
	public List<BpPageSetting> listByPageKey(String pageKey) {
		
		return dao.listByPageKey(pageKey);
	}

}