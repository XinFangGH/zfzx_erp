package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;

/**
 * 
 * @author 
 *
 */
public interface SlPlansToChargeService extends BaseService<SlPlansToCharge>{
	public  List<SlPlansToCharge> getallbycompanyId();
	public List<SlPlansToCharge> getall();
	public List<SlPlansToCharge> getbytype(int type);
	public List<SlPlansToCharge> getbyOperationType(int type,String chargekey);
	public int setIsValid(int isValid,SlPlansToCharge f);
	public List<SlPlansToCharge> getListByIdsNotNull(String businessType,String isValid);
	public List<SlPlansToCharge> checkIsExit(String productId,
			Long plansTochargeId, String businessType);
	public List<SlPlansToCharge> getByPProductIdAndOperationType(
			String productId, String businessType);
	public List<SlPlansToCharge> getbyProductId(String productId, String businessType);
}


