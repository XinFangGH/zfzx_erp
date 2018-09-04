package com.zhiwei.credit.action.admin;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.ConfAttend;
import com.zhiwei.credit.service.admin.ConfAttendService;

/**
 * @description ConfAttendAction
 * @author YHZ
 * @date 2010-10-8 PM
 * 
 */
public class ConfAttendAction extends BaseAction {
	@Resource
	private ConfAttendService confAttendService;
	private ConfAttend confAttend;

	private Long attendId;

	public Long getAttendId() {
		return attendId;
	}

	public void setAttendId(Long attendId) {
		this.attendId = attendId;
	}

	public ConfAttend getConfAttend() {
		return confAttend;
	}

	public void setConfAttend(ConfAttend confAttend) {
		this.confAttend = confAttend;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<ConfAttend> list = confAttendService.getAll(filter);

		Type type = new TypeToken<List<ConfAttend>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				confAttendService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		ConfAttend confAttend = confAttendService.get(attendId);

		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(confAttend));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		confAttendService.save(confAttend);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
