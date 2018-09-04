package com.zhiwei.credit.dao.creditFlow.finance.plateFormFinanceManage;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.plateFormFinanceManage.SlRiskguaranteemoneyRepayment;

/**
 * 
 * @author 
 *
 */
public interface SlRiskguaranteemoneyRepaymentDao extends BaseDao<SlRiskguaranteemoneyRepayment>{

	public List<SlRiskguaranteemoneyRepayment> getpunishmentRecord(HttpServletRequest request, Integer start, Integer limit);
	
}