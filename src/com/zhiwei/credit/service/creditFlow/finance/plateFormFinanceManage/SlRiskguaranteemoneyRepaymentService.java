package com.zhiwei.credit.service.creditFlow.finance.plateFormFinanceManage;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepayment;

/**
 * 
 * @author 
 *
 */
public interface SlRiskguaranteemoneyRepaymentService extends BaseService<SlRiskguaranteemoneyRepayment>{

	public void savePlateFormRepayment(String cardNo, String planId,
			BigDecimal allMoney,String peridId);
	
}


