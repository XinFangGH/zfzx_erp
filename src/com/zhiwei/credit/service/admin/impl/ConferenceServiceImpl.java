package com.zhiwei.credit.service.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.ConferenceDao;
import com.zhiwei.credit.model.admin.Conference;
import com.zhiwei.credit.service.admin.ConferenceService;

/**
 * @description ConferenceServiceImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConferenceServiceImpl extends BaseServiceImpl<Conference>
		implements ConferenceService {
	private ConferenceDao dao;

	public ConferenceServiceImpl(ConferenceDao dao) {
		super(dao);
		this.dao = dao;
	}

	/**
	 * @description 根据会议标题分页模糊查询
	 */
	@Override
	public List<Conference> getConfTopic(String confTopic, PagingBean pb) {
		return dao.getConfTopic(confTopic, pb);
	}

	/**
	 * @description 根据userId查询对应的fullName,返回fullName组成的字符串
	 * @param userIds
	 *            userId组成的字符串
	 * @return fullName组成的字符串
	 */
	public String baseUserIdSearchFullName(String userIds) {
		return dao.baseUserIdSearchFullName(userIds);
	}

	/**
	 * @description 会议发送,并发送
	 */
	public Conference send(Conference conference, String view, String updater,
			String summary, String fileIds) {
		conference.setCreatetime(new Date());
		conference.setSendtime(new Date());
		return dao.send(conference, view, updater, summary, fileIds);
	}

	/**
	 * @description 会议暂存
	 */
	public Conference temp(Conference conference, String view, String updater,
			String summary, String fileIds) {
		// 修改状态0
		conference.setStatus((short) 0);
		conference.setCreatetime(new Date());// 获取当前时间
		return dao.temp(conference, view, updater, summary, fileIds);
	}

	/**
	 * @description 根据会议室编号判断在输入的时间段内是否会议室可用,可用返回success,不可用返回不可用的时间段
	 */
	@Override
	public String judgeBoardRoomNotUse(Date startTime, Date endTime, Long roomId) {
		return dao.judgeBoardRoomNotUse(startTime, endTime, roomId);
	}

	/**
	 * @description 会议审批操作
	 */
	@Override
	public String apply(Long confId, String checkReason, boolean bo) {
		return dao.apply(confId, checkReason, bo);
	}
	
	/**
	 * @description 与我相关的会议信息
	 */
	@Override
	public List<Conference> myJoin(Conference conference, Boolean bo, PagingBean pb) {
		return dao.myJoin(conference, bo, pb);
	}
}