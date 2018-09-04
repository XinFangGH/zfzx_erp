package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyTypeDao extends BaseDao<PlManageMoneyType>{
	public List<PlManageMoneyType> getListbykeystr(String keystr);
	public PlManageMoneyPlan yesOrNo(Long manageMoneyTypeId);
}