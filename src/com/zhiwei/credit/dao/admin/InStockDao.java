package com.zhiwei.credit.dao.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.math.BigDecimal;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.admin.InStock;

/**
 * 
 * @author 
 *
 */
public interface InStockDao extends BaseDao<InStock>{
	public Integer findInCountByBuyId(Long buyId);
}