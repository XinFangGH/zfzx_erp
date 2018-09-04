package com.zhiwei.credit.dao.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.admin.Conference;

/**
 * @description ConferenceDao
 * @author YHZ
 * @data 2010-10-8 PM
 * 
 */
public interface ConferenceDao extends BaseDao<Conference> {

	/**
	 * @description 根据会议标题模糊分页查询
	 */
	List<Conference> getConfTopic(String confTopic, PagingBean pb);

	/**
	 * @description 会议发送
	 */
	Conference send(Conference conference, String view, String updater,
			String summary, String fileIds);

	/**
	 * @description 会议暂存
	 */
	Conference temp(Conference conference, String view, String updater,
			String summary, String filePath);

	/**
	 * @description 根据userId组成的字符串，查询对应的fullname,返回fullName组成的字符串
	 */
	String baseUserIdSearchFullName(String userIds);

	/**
	 * @description 根据会议室编号判断在输入的时间段内是否会议室可用,可用返回success,不可用返回不可用的时间段
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param roomId
	 *            会议室编号
	 * @return 可用返回success,不可用返回不可用的时间段
	 */
	String judgeBoardRoomNotUse(java.util.Date startTime,
			java.util.Date endTime, Long roomId);

	/**
	 * @description 会议审批，只是修改会议状态
	 * @param confId
	 *            会议编号
	 * @param checkReason
	 *            审核原因
	 * @param bo
	 *            true:审核通过,false:审核未通过
	 * @return 成功:success,失败：failure
	 */
	String apply(Long confId, String checkReason, boolean bo);

	/**
	 * @description 与我相关的会议信息
	 * @param conference
	 *            Conference包含查询的条件
	 * @param bo
	 *            是否过期
	 * @param pb
	 *            PageingBean 分页
	 * @return List<Conference>
	 */
	List<Conference> myJoin(Conference conference, Boolean bo, PagingBean pb);

}