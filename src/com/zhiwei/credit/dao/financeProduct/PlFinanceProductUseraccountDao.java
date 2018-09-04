package com.zhiwei.credit.dao.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import java.util.Map;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductUseraccountDao extends BaseDao<PlFinanceProductUseraccount>{

	public List<PlFinanceProductUseraccount> getUserAccountList(Map<String,String> request, PagingBean pb);
	
}