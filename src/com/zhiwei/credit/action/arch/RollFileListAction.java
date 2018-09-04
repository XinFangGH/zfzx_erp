package com.zhiwei.credit.action.arch;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
 */
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.arch.RollFileList;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.arch.RollFileListService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author
 * 
 */
public class RollFileListAction extends BaseAction {
	
	@Resource
	private RollFileListService rollFileListService;
	private RollFileList rollFileList;
	@Resource
	private FileAttachService fileAttachService;

	private Long listId;

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public RollFileList getRollFileList() {
		return rollFileList;
	}

	public void setRollFileList(RollFileList rollFileList) {
		this.rollFileList = rollFileList;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<RollFileList> list = rollFileListService.getAll(filter);

		Type type = new TypeToken<List<RollFileList>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		// Gson gson=new Gson();
		// buff.append(gson.toJson(list, type));
		JSONSerializer serializer = new JSONSerializer();
		serializer
				.exclude(new String[] { "rollFile", "fileAttach.createTime" })
				.transform(new DateTransformer("yyyy-MM-dd"),
						new String[] { "fileAttach.createTime", "createTime" });
		buff.append(serializer.serialize(list));

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

		String[] listIds = getRequest().getParameterValues("listIds");
		if (listIds != null && listIds.length > 0) {
			for (String id : listIds) {
				if (id != null && !id.equals("")) {
					rollFileList = rollFileListService.get(new Long(id));
					FileAttach fileAttach = rollFileList.getFileAttach();
					
					rollFileListService.remove(rollFileList);
					fileAttachService.removeByPath(fileAttach.getFilePath());
				}
			}
		}
		String[] fileIds = getRequest().getParameterValues("fileIds");
		if (fileIds != null && fileIds.length > 0) {
			for (String id : fileIds) {
				if (id != null && !id.equals("")) {
					FileAttach fileAttach = fileAttachService.get(new Long(id));
					
					fileAttachService.removeByPath(fileAttach.getFilePath());
				}
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
		RollFileList rollFileList = rollFileListService.get(listId);

		// Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		// sb.append(gson.toJson(rollFileList));
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "rollFile" }).transform(
				new DateTransformer("yyyy-MM-dd"),
				new String[] { "fileAttach.createtime" });
		sb.append(serializer.serialize(rollFileList));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (rollFileList.getListId() == null) {
			rollFileListService.save(rollFileList);
		} else {
			RollFileList orgRollFileList = rollFileListService.get(rollFileList
					.getListId());
			try {
				BeanUtil.copyNotNullProperties(orgRollFileList, rollFileList);
				rollFileListService.save(orgRollFileList);
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;

	}


}
