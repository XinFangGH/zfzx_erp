package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.IndexDisplay;

public interface IndexDisplayService extends BaseService<IndexDisplay>{
	public List<IndexDisplay> findByUser(Long userId);
}


