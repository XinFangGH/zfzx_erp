package com.zhiwei.credit.service.communicate;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.communicate.OutMailUserSeting;

/**
 * @description 外部邮箱管理
 * @class OutMailUserSetingService
 * 
 */
public interface OutMailUserSetingService extends BaseService<OutMailUserSeting> {

	OutMailUserSeting getByLoginId(Long loginid);
	
    public List findByUserAll();
	
    public List<OutMailUserSeting> findByUserAll(String userName,PagingBean pb);
}
