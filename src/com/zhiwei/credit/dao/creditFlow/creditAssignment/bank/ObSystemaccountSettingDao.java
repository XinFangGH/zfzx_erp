package com.zhiwei.credit.dao.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemaccountSetting;

/**
 * 
 * @author 
 *
 */
public interface ObSystemaccountSettingDao extends BaseDao<ObSystemaccountSetting>{
	public List<ObSystemaccountSetting> findObSystemaccountSetting();

    List<ObSystemaccountSetting> findThirdObSystemaccountSetting();
}