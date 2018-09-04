package com.zhiwei.credit.service.info.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.info.InMessageDao;
import com.zhiwei.credit.dao.info.ShortMessageDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.model.info.InMessage;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.info.ShortMessageService;

public class ShortMessageServiceImpl extends BaseServiceImpl<ShortMessage>
		implements ShortMessageService {
	private ShortMessageDao messageDao;
	@Resource
	private InMessageDao inMessageDao;
	@Resource
	private AppUserDao appUserDao;

	public ShortMessageServiceImpl(ShortMessageDao dao) {
		super(dao);
		this.messageDao = dao;
	}

	@Override
	public List<ShortMessage> findAll(Long userId, PagingBean pb) {
		return messageDao.findAll(userId, pb);
	}

	@Override
	public List<ShortMessage> findByUser(Long userId) {
		return messageDao.findByUser(userId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ShortMessage> searchShortMessage(Long userId, ShortMessage shortMessage,
			Date from, Date to, PagingBean pb, Short readFlag) {
		return messageDao.searchShortMessage(userId, shortMessage, from, to,
				pb, readFlag);
	}

	public ShortMessage save(Long senderId, String receiveIds, String content,Short msgType) {

		ShortMessage shortMessage = new ShortMessage();
		shortMessage.setContent(content);
		shortMessage.setMsgType(msgType);
		AppUser curUser = appUserDao.get(senderId);
		shortMessage.setSender(curUser.getFullname());
		shortMessage.setSenderId(curUser.getUserId());
		shortMessage.setSendTime(new Date());

		messageDao.save(shortMessage);

		String[] reIds = receiveIds.split("[,]");
		if (reIds != null) {

			for (String userId : reIds) {
				InMessage inMsg = new InMessage();
				inMsg.setDelFlag(Constants.FLAG_UNDELETED);
				inMsg.setReadFlag(InMessage.FLAG_UNREAD);
				inMsg.setShortMessage(shortMessage);
				AppUser receiveUser = appUserDao.get(new Long(userId));

				inMsg.setUserId(receiveUser.getUserId());
				inMsg.setUserFullname(receiveUser.getFullname());
				inMessageDao.save(inMsg);
			}
		}

		return shortMessage;
	}

}
