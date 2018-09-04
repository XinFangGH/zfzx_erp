package com.zhiwei.credit.service.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;

/**
 * 
 * @author 
 *
 */
public interface PlFinanceProductService extends BaseService<PlFinanceProduct>{

	public List<PlFinanceProduct> getAllProduct();
	
}


