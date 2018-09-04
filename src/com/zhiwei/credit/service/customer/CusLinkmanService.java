package com.zhiwei.credit.service.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.customer.CusLinkman;

public interface CusLinkmanService extends BaseService<CusLinkman>{

	public boolean checkMainCusLinkman(Long customerId, Long linkmanId);
	
}


