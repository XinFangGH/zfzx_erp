package com.zhiwei.credit.service.hrm;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.hrm.SalaryItem;

public interface SalaryItemService extends BaseService<SalaryItem>{

	public List<SalaryItem> getAllExcludeId(String excludeIds,PagingBean pb);
	
}


