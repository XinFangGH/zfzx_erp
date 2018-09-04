package com.zhiwei.credit.service.info;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.info.InMessage;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;

public interface InMessageService extends BaseService<InMessage> {

	/**
	 * 读出最新的一条未读信息
	 */
	public InMessage findByRead(Long userId);
	public Integer findByReadFlag(Long userId);
	public List<InMessage> findAll(Long userId,PagingBean pb);
	public List findByUser(Long userId,PagingBean pb);
	public List searchInMessage(Long userId,InMessage inMessage,ShortMessage shortMessage,Date from,Date to,PagingBean pb);
	/**
	 * 查找最新的一条信息
	 * @param userId
	 * @return
	 */
	public InMessage findLatest(Long userId);
}
