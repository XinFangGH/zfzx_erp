package com.zhiwei.credit.service.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlan;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyType;

/**
 * 
 * @author 
 *
 */
public interface PlManageMoneyTypeService extends BaseService<PlManageMoneyType>{
	public List<PlManageMoneyType> getListbykeystr(String keystr);
	/**
	 * 查询此类型是否已经使用
	 * @param manageMoneyTypeId
	 * @return
	 */
	public PlManageMoneyPlan yesOrNo(Long manageMoneyTypeId);
}


