package com.zhiwei.credit.dao.creditFlow.common.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.common.BpPageSettingDao;
import com.zhiwei.credit.model.creditFlow.common.BpPageSetting;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpPageSettingDaoImpl extends BaseDaoImpl<BpPageSetting> implements BpPageSettingDao{

	public BpPageSettingDaoImpl() {
		super(BpPageSetting.class);
	}

	@Override
	public List<BpPageSetting> listByParentId(Long parentId) {
		String hql="from BpPageSetting as s where s.parentId=?";
		return this.findByHql(hql, new Object[]{parentId});
	}

	@Override
	public List<BpPageSetting> listByPageKey(String pageKey) {
		String hql="from BpPageSetting as s where s.pageKey=?";
		return this.findByHql(hql, new Object[]{pageKey});
	}

}