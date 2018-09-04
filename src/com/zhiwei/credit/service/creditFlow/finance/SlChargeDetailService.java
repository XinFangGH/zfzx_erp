package com.zhiwei.credit.service.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;

/**
 * 
 * @author 
 *
 */
public interface SlChargeDetailService extends BaseService<SlChargeDetail>{
	public  List<SlChargeDetail> getallbycompanyId();
	public List<SlChargeDetail> getlistbyActualChargeId(Long actualChargeId);
	
}


