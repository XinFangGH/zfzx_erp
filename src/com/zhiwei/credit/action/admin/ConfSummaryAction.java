package com.zhiwei.credit.action.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.ConfSummary;
import com.zhiwei.credit.model.admin.Conference;
import com.zhiwei.credit.service.admin.ConfSummaryService;

/**
 * @description ConfSummaryAction
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConfSummaryAction extends BaseAction {
	@Resource
	private ConfSummaryService confSummaryService;

	private ConfSummary confSummary;
	private Long sumId;
	private java.util.Date endtime;// 結束日期
	private String fileIds;

	public String getFileIds() {
		return this.fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
	}

	public java.util.Date getEndtime() {
		return endtime;
	}

	public void setEndtime(java.util.Date endtime) {
		this.endtime = endtime;
	}

	public Long getSumId() {
		return sumId;
	}

	public void setSumId(Long sumId) {
		this.sumId = sumId;
	}

	public ConfSummary getConfSummary() {
		return confSummary;
	}

	public void setConfSummary(ConfSummary confSummary) {
		this.confSummary = confSummary;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		QueryFilter filter = new QueryFilter(getRequest());
		if (endtime != null)
			filter.addFilter("Q_createtime_D_LE", sdf.format(endtime));
		List<ConfSummary> list = confSummaryService.getAll(filter);
		Type type = new TypeToken<List<ConfSummary>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				confSummaryService.remove(new Long(id));
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * @description 单条数据删除
	 */
	public String del() {
		String id = getRequest().getParameter("sumId");
		confSummaryService.remove(new Long(id));
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * @description 显示详细信息
	 */
	public String get() {
		ConfSummary confSummary = confSummaryService.get(sumId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
				.create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(confSummary));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * @description 发送
	 */
	public String send() {
		String content = confSummary.getSumContent();
		if (content == null || content.isEmpty() || content.equals(" ")) {
			setJsonString("{failure:true,msg:'读不起，会议纪要内容不能为空，请输入！'}");
		} else {
			confSummary.setCreatetime(new java.util.Date());
			confSummary.setCreator(ContextUtil.getCurrentUser().getUsername());
			confSummary.setStatus((short) 1);
			confSummaryService.send(confSummary, fileIds);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}

	/**
	 * 添加
	 */
	public String save() {
		String content = confSummary.getSumContent();
		if (content == null || content.isEmpty() || content.equals(" ")) {
			setJsonString("{failure:true,msg:'对不起，会议纪要内容不能为空，请重新输入！'}");
		} else {
			confSummary.setCreatetime(new java.util.Date());
			confSummary.setCreator(ContextUtil.getCurrentUser().getUsername());
			confSummary.setStatus((short) 0);
			confSummaryService.save(confSummary, fileIds);
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}

	/**
	 * @description 编辑保存
	 */
	public String edit() {
		confSummaryService.save(confSummary, fileIds);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * @description 当前用户的纪要信息
	 */
	public String display() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_status_SN_EQ", "1");
		filter.addSorted("createtime", "DESC");
		List<ConfSummary> list = confSummaryService.getAll(filter);
		for (int i = 0; i < list.size(); i++) {
			ConfSummary cm = list.get(i);
			Conference cf = cm.getConfId();
			if (cm.getStatus() != 1
					&& (myConfSummary(cf.getCompere())
							&& myConfSummary(cf.getRecorder()) && myConfSummary(cf
							.getAttendUsers()))) {
				list.remove(i);
			}
			if (i > 7) { //保证8条数据显示
				for (int j = 8; j < list.size(); j++)
					list.remove(j);
			}
		}
		getRequest().setAttribute("confSummaryList", list);
		return "display";
	}

	/**
	 * @description 判断是否为我的会议纪要,true不是的
	 */
	private boolean myConfSummary(String str) {
		boolean bo = true; // 默认不是的
		Long userId = ContextUtil.getCurrentUserId();
		int index = str.indexOf(userId.toString());
		if (index > 1 && str.substring(index - 1, index).equals(","))
			bo = false;
		else if (index == 0)
			bo = false;
		return bo;
	}

}
