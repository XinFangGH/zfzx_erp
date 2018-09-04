package com.zhiwei.credit.dao.admin.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.admin.ConfPrivilegeDao;
import com.zhiwei.credit.dao.admin.ConferenceDao;
import com.zhiwei.credit.dao.system.AppUserDao;
import com.zhiwei.credit.dao.system.FileAttachDao;
import com.zhiwei.credit.model.admin.ConfPrivilege;
import com.zhiwei.credit.model.admin.Conference;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.info.ShortMessageService;

/**
 * @description ConferenceDaoImpl
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
@SuppressWarnings("unchecked")
public class ConferenceDaoImpl extends BaseDaoImpl<Conference> implements
		ConferenceDao {
	// 中文的
	private static SimpleDateFormat sdfc = new SimpleDateFormat(
			"yyyy年MM月dd日 HH时mm分");
	@Resource
	private FileAttachDao fileAttachDao;
	@Resource
	private AppUserDao appUserDao;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private ConfPrivilegeDao confPrivilegeDao;
//	@Resource
//	private MailEngine mailEngine;

	public ConferenceDaoImpl() {
		super(Conference.class);
	}

	/**
	 * @description 根据会议标题模糊分页查询，参数：confTopic会议标题,pb即PagingBean对象
	 */
	@Override
	public List<Conference> getConfTopic(String confTopic, PagingBean pb) {
		Long userId = ContextUtil.getCurrentUserId();
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuffer bf = new StringBuffer(
				"select c from Conference c,ConfPrivilege p where c.endTime < ? and c.confId=p.confId ");
		bf.append("and p.rights=3 and p.userId=" + userId);
		paramList.add(new Date());
		if (confTopic != null && !confTopic.isEmpty()) {
			bf.append(" and c.confTopic like ? ");
			paramList.add("%" + confTopic + "%");
		}
		logger.debug("可创建会议纪要的HQL：" + bf.toString());
		return findByHql(bf.toString(), paramList.toArray(), pb);
	}

	/**
	 * @description 根据userId查询fullName,返回fullName组成的字符串
	 */
	public String baseUserIdSearchFullName(String userIds) {
		String fullNames = "";
		for (String userId : userIds.split(",")) {
			fullNames += appUserDao.get(new Long(userId)).getFullname() + ",";
		}
		return fullNames.substring(0, fullNames.length() - 1);
	}

	/**
	 * @description 会议发送[保存Conference,Mail]
	 */
	public Conference send(Conference conference, String view, String updater,
			String summary, String fileIds) { // 只发送【审核人】信息
		// 内部消息[审核人]
		String sMsg = "请审核主题为【" + conference.getConfTopic() + "】的会议信息！";
		shortMessageService.save(AppUser.SYSTEM_USER, conference
				.getCheckUserId().toString(), sMsg, ShortMessage.MSG_TYPE_SYS);
		return temp(conference, view, updater, summary, fileIds);
	}

	/**
	 * @description 会议暂存
	 */
	public Conference temp(Conference conference, String view, String updater,
			String summary, String fileIds) {
		// 获取附件信息
		if (fileIds != null && !fileIds.isEmpty()) {
			Set<FileAttach> set = new HashSet<FileAttach>();
			for (String f : fileIds.split(",")) {
				FileAttach fa = fileAttachDao.get(new Long(f));
				set.add(fa);
			}
			conference.setAttachFiles(set);
		}
		Conference cf = super.save(conference);

		Set<ConfPrivilege> sett = new HashSet<ConfPrivilege>();
		// 查看人
		setConfPrivilege(cf.getConfId(), view, 1, sett);
		// 修改人
		setConfPrivilege(cf.getConfId(), updater, 2, sett);
		// 创建纪要人
		setConfPrivilege(cf.getConfId(), summary, 3, sett);
		// 删除原来的权限
		confPrivilegeDao.delete(cf.getConfId());
		// 保存
		cf.setConfPrivilege(sett);
		return super.save(cf);
	}

	/**
	 * @description 根据会议室编号判断在输入的时间段内是否会议室可用,可用返回success,不可用返回不可用的时间段
	 */
	@Override
	public String judgeBoardRoomNotUse(Date startTime, Date endTime, Long roomId) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		String msg = "success";
		String hql = "select t from Conference t where t.roomId = ? and t.status=1 and ";
	    hql += "((t.startTime < ? and t.endTime > ?) "; //查询不可用的
	    hql +="or (t.startTime < ? and t.endTime > ?) ";
	    hql += "or (t.startTime > ? and t.endTime <?))";
		paramList.add(roomId);
		paramList.add(startTime);
		paramList.add(startTime);
		paramList.add(endTime);
		paramList.add(endTime);
		paramList.add(startTime);
		paramList.add(endTime);
		List<Conference> list = findByHql(hql, paramList.toArray());
		if (list != null && list.size() > 0) {
			Conference conference = list.get(0);
			msg = "会议室【" + conference.getRoomName() + "】，在【"
					+ sdfc.format(conference.getStartTime()) + " 至 "
					+ sdfc.format(conference.getEndTime())
					+ "】这段时间不可使用，请选择其他时间段或者会议室！";
		} else {
			msg = "success";
		}
		logger.debug("Conference中判断会议室是否可用：" + hql);
		return msg;
	}

	/**
	 * @description 会议审批
	 */
	@Override
	public String apply(Long confId, String checkReason, boolean bo) {
		int status = bo ? 1 : 3; // 1.通过
		Conference conference = get(confId);
		if (status == 1) { // 审核通过发送信息
			// 内部消息[主持人]
			String cMsg = "请于【" + sdfc.format(conference.getStartTime()) + "-"
					+ sdfc.format(conference.getEndTime()) + "】到【"
					+ conference.getRoomName() + "】去主持主题为【"
					+ conference.getConfTopic() + "】的会议！\n\t地址："
					+ conference.getRoomLocation();
			shortMessageService.save(AppUser.SYSTEM_USER,
					conference.getCompere(), cMsg, ShortMessage.MSG_TYPE_SYS);
			// 内部消息[记录人]
			String rMsg = "请于【" + sdfc.format(conference.getStartTime()) + "-"
					+ sdfc.format(conference.getEndTime()) + "】到【"
					+ conference.getRoomName() + "】去记录主题为【"
					+ conference.getConfTopic() + "】的会议内容！\n\t地址："
					+ conference.getRoomLocation();
			shortMessageService.save(AppUser.SYSTEM_USER,
					conference.getRecorder(), rMsg, ShortMessage.MSG_TYPE_SYS);
			// 内部消息[参入人]
			String aMsg = "请于【" + sdfc.format(conference.getStartTime()) + "-"
					+ sdfc.format(conference.getEndTime()) + "】到【"
					+ conference.getRoomName() + "】去参加主题为【"
					+ conference.getConfTopic() + "】的会议！\n\t地址："
					+ conference.getRoomLocation();
			shortMessageService.save(AppUser.SYSTEM_USER,
					conference.getAttendUsers(), aMsg,
					ShortMessage.MSG_TYPE_SYS);

			// 发送邮件
			if (conference.getIsEmail() != null && conference.getIsEmail() == 1) {
//				String tempPath = "mail/flowMail.vm"; // 模板
//				Map<String, Object> hashMap = new HashMap<String, Object>();
//				String subject = "来自未央区城管办公系统的待办任务提醒";
//				mailEngine.sendTemplateMail(tempPath, hashMap, subject, AppUser.SYSTEM_USER,
//						new String[] { "" }, null, null,
//						null, null, true);
			}

			// 短信提示
			if (conference.getIsMobile() != null
					&& conference.getIsMobile() == 1) {

			}
		}
		conference.setStatus((short) status);
		conference.setCheckReason(checkReason);
		return "success";
	}

	/**
	 * @description 与我相关的会议内容
	 */
	@Override
	public List<Conference> myJoin(Conference conference, Boolean bo, PagingBean pb) {
		ArrayList<Object> paramList = new ArrayList<Object>();
		StringBuffer bf = new StringBuffer("select c from Conference c where 1=1");
		bf.append(" and (c.compereName like ? or c.recorderName like ? or c.attendUsersName like ?)");
		String fullName = ContextUtil.getCurrentUser().getFullname(); // 当前用户fullName
		paramList.add("%" + fullName + "%"); //主持人
		paramList.add("%" + fullName + "%"); //记录人
		paramList.add("%" + fullName + "%"); //参加人
		//判断是否过期
		if(bo != null){
			if(bo == false){ //将要参加
				bf.append(" and c.startTime >= ?");
				paramList.add(new java.util.Date());
			} else { //过期
				bf.append(" and c.endTime < ? ");
				paramList.add(new java.util.Date());
			}
		}
		//标题,会议类型,会议内容,会议室名称,会议室类型,开始和结束时间与我相关的会议,状态
		if(conference != null ){ //条件查询开始
			//1.标题
			if(conference.getConfTopic()!= null && !conference.getConfTopic().trim().equals("")){
				bf.append(" and c.confTopic like ?");
				paramList.add("%" + conference.getConfTopic() + "%");
			}
			//2.会议类型confProperty
			if(conference.getConfProperty() != null && !conference.getConfProperty().trim().equals("")){
				bf.append(" and c.confProperty = ?");
				paramList.add(conference.getConfProperty());
			}
			//3.会议内容
			if(conference.getConfContent() != null && !conference.getConfContent().trim().equals("")){
				bf.append(" and c.confContent like ?");
				paramList.add("%" + conference.getConfContent() + "%");
			}
			//4.会议室名称
			if(conference.getRoomName() != null && !conference.getRoomName().trim().equals("")){
				bf.append(" and c.roomName like ?");
				paramList.add("%" + conference.getRoomName() + "%");
			}
			//5.会议室类型
			if(conference.getRoomId() != null ){
				bf.append(" and c.roomId = ?");
				paramList.add(conference.getRoomId());
			}
			//6.开始时间
			if(conference.getStartTime() != null ){
				bf.append(" and c.startTime = ?");
				paramList.add(conference.getStartTime());
			}
			//7.结束时间
			if(conference.getEndTime() != null){
				bf.append(" and c.endTime = ?");
				paramList.add(conference.getEndTime());
			}
			//8.状态
			if(conference.getStatus() != null){
				bf.append(" and c.status = ?");
				paramList.add(conference.getStatus());
			}
		}
		bf.append(" order by confId DESC");
		logger.debug("与我相关会议查询：" + bf.toString());
		return findByHql(bf.toString(), paramList.toArray(), pb);
	}

	// ########私用方法#######//

	/**
	 * @description 添加会议权限
	 */
	private void setConfPrivilege(Long confId, String ids, int type,
			Set<ConfPrivilege> set) {
		ConfPrivilege cp;
		for (String id : ids.split(",")) {
			AppUser appUser = appUserDao.get(new Long(id));
			cp = new ConfPrivilege();
			cp.setConfId(confId);
			cp.setUserId(appUser.getUserId());
			cp.setFullname(appUser.getFullname());
			cp.setRights((short) type);
			set.add(cp);
		}
	}

}