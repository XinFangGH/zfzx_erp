package com.zhiwei.credit.dao.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductDao extends BaseDao<PlFinanceProduct>{

	public List<PlFinanceProduct> getAllProduct();
	
}