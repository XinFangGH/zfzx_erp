package com.zhiwei.credit.dao.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.admin.ConfSummaryDao;
import com.zhiwei.credit.dao.admin.ConferenceDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.admin.ConfSummary;
import com.zhiwei.credit.model.admin.Conference;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.info.ShortMessageService;

/**
 * @description confSummaryDaoImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
@SuppressWarnings("unchecked")
public class ConfSummaryDaoImpl extends BaseDaoImpl<ConfSummary> implements
		ConfSummaryDao {
	@Resource
	private ConferenceDao confDao;
	@Resource
	private FileAttachDao fileAttachDao;
	@Resource
	private ShortMessageService shortMessageService;

	public ConfSummaryDaoImpl() {
		super(ConfSummary.class);
	}

	/**
	 * @description 发送
	 */
	public ConfSummary send(ConfSummary cm, String fileIds) {
		// 内部邮件
		Conference conf = confDao.get(cm.getConfId().getConfId());
		String ids = conf.getCompere() + "," + conf.getRecorder() + ","
				+ conf.getAttendUsers();
		String msg = "请查看主题为【" + conf.getConfTopic() + "】的会议纪要信息！";
		shortMessageService.save(AppUser.SYSTEM_USER, ids, msg,
				ShortMessage.MSG_TYPE_SYS);
		return save(cm, fileIds);
	}

	/**
	 * @description 保存
	 */
	public ConfSummary save(ConfSummary cm, String fileIds) {
		if (fileIds != null && !fileIds.isEmpty()) {
			Set<FileAttach> set = new HashSet<FileAttach>();
			for (String s : fileIds.split(",")) {
				set.add(fileAttachDao.get(new Long(s)));
			}
			cm.setAttachFiles(set);
		}
		Conference cf = confDao.get(cm.getConfId().getConfId());
		cm.setConfId(cf);
		return super.save(cm);
	}

}