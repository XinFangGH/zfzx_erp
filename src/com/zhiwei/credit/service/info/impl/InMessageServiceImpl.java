package com.zhiwei.credit.service.info.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.info.InMessageDao;
import com.zhiwei.credit.model.info.InMessage;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.service.info.InMessageService;

public class InMessageServiceImpl extends BaseServiceImpl<InMessage> implements
		InMessageService {

	private InMessageDao dao;
	public InMessageServiceImpl(InMessageDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public InMessage findByRead(Long userId) {
		return dao.findByRead(userId);
	}
	@Override
	public Integer findByReadFlag(Long userId) {
		return dao.findByReadFlag(userId);
	}
	@Override
	public List<InMessage> findAll(Long userId, PagingBean pb) {
		return dao.findAll(userId, pb);
	}
	@Override
	public List findByUser(Long userId, PagingBean pb) {
		return dao.findByUser(userId, pb);
	}
	@Override
	public List searchInMessage(Long userId, InMessage inMessage,
			ShortMessage shortMessage, Date from, Date to, PagingBean pb) {
		return dao.searchInMessage(userId, inMessage, shortMessage, from, to, pb);
	}
	@Override
	public InMessage findLatest(Long userId) {
		return dao.findLatest(userId);
	}

}
