package com.zhiwei.credit.action.system;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Diary;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.DiaryService;

import flexjson.transformer.DateTransformer;
import flexjson.JSONSerializer;

/**
 * @description 我的下属日志搜索展示
 * @class DiaryAction
 * @author csx,YHZ
 * @company www.credit-software.com
 * @data 2010-12-23PM
 */
public class DiaryAction extends BaseAction {
	@Resource
	private DiaryService diaryService;
	@Resource
	private AppUserService appUserService;
	private Diary diary;
	private Date from;
	private Date to;

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	private Long diaryId;

	public Long getDiaryId() {
		return diaryId;
	}

	public void setDiaryId(Long diaryId) {
		this.diaryId = diaryId;
	}

	public Diary getDiary() {
		return diary;
	}

	public void setDiary(Diary diary) {
		this.diary = diary;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		AppUser user = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_appUser.userId_L_EQ", (user.getId()).toString());
		List<Diary> list = diaryService.getAll(filter);
		Type type = new TypeToken<List<Diary>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
				.excludeFieldsWithoutExposeAnnotation().create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 查找我的下属的工作日志
	 */
	public String subUser() {
		PagingBean pb = getInitPagingBean();
		String usrIds = getRequest().getParameter("userId");
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(usrIds)) {
			sb.append(usrIds);
		}
		// 查询我的下属
//		List<AppUser> list = appUserService.findRelativeUsersByUserId();
		List<AppUser> list = appUserService.findRelativeUsersByUserId(ContextUtil.getCurrentUserId());
		if (list != null) {
			for (AppUser appUser : list) {
				sb.append(appUser.getUserId()).append(",");
			}
			if (list.size() > 0)
				sb.deleteCharAt(sb.length() - 1);
		}
		
		List<Diary> diaryList = new ArrayList<Diary>();

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if (sb.length() > 0) {
			diaryList = diaryService.getSubDiary(sb.toString(), pb);
			buff.append(pb.getTotalItems());
		} else {
			buff.append("0");// 记录数为0
		}
		
		buff.append(",result:");
		JSONSerializer serializer = new JSONSerializer();
		serializer.transform(new DateTransformer("yyyy-MM-dd"), "dayTime");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(diaryList));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	
	/*
	 * 查询列表
	 */
	public String search() {

		AppUser user = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(getRequest());
		// 按用户查询
		filter.addFilter("Q_appUser.userId_L_EQ", (user.getId()).toString());
		// 按起始时间查询
		if (getRequest().getParameter("from") != "") {
			filter.addFilter("Q_dayTime_D_GE", getRequest()
					.getParameter("from"));
		}
		// 按最终时间查询
		if (getRequest().getParameter("to") != "") {
			filter.addFilter("Q_dayTime_D_LE", getRequest().getParameter("to"));
		}
		// 按内容查询
		filter.addFilter("Q_content_S_LK", diary.getContent());
		// 按类型查询
		if (diary.getDiaryType() != null) {
			filter.addFilter("Q_diaryType_SN_EQ", (diary.getDiaryType())
					.toString());
		}

		List<Diary> list = diaryService.getAll(filter);
		Type type = new TypeToken<List<Diary>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
				diaryService.remove(new Long(id));
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
		Diary diary = diaryService.get(diaryId);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(diary));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		AppUser user = ContextUtil.getCurrentUser();
		diary.setAppUser(user);
		diaryService.save(diary);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	public String check() {
		String strId = getRequest().getParameter("diaryId");
		if (StringUtils.isNotEmpty(strId)) {
			diary = diaryService.get(new Long(strId));
		}
		return "check";
	}

	public String display() {
		AppUser user = ContextUtil.getCurrentUser();
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_appUser.userId_L_EQ", (user.getId()).toString());
		filter.addSorted("diaryId", "desc");
		List<Diary> list = diaryService.getAll(filter);
		getRequest().setAttribute("diaryList", list);
		return "display";
	}

}
