package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemaccountSettingDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemaccountSetting;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObSystemaccountSettingDaoImpl extends BaseDaoImpl<ObSystemaccountSetting> implements ObSystemaccountSettingDao{

	public ObSystemaccountSettingDaoImpl() {
		super(ObSystemaccountSetting.class);
	}

	@Override
	public List<ObSystemaccountSetting> findObSystemaccountSetting() {
		// TODO Auto-generated method stub
	  String hql=" from ObSystemaccountSetting  as o where o.usedRemark like '%供系统账户交易明细类型%' ";
	  return   this.findByHql(hql);
	}

	@Override
	public List<ObSystemaccountSetting> findThirdObSystemaccountSetting() {
		String hql=" from ObSystemaccountSetting  as o where o.usedRemark like '%三方业务类型%' ";
		return   this.findByHql(hql);
	}

}