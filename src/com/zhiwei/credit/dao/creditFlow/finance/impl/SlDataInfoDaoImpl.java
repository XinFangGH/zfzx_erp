package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.finance.SlDataInfoDao;
import com.zhiwei.credit.model.creditFlow.finance.SlDataInfo;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlDataInfoDaoImpl extends BaseDaoImpl<SlDataInfo> implements SlDataInfoDao{

	public SlDataInfoDaoImpl() {
		super(SlDataInfo.class);
	}

	@Override
	public List<SlDataInfo> getListByDataId(Long dataId) {
		String hql="from SlDataInfo as s where s.dataId=? order by s.dataTypeStatus  asc";
		return getSession().createQuery(hql).setParameter(0, dataId).list();
		
	}

}