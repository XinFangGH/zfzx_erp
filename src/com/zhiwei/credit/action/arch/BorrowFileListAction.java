package com.zhiwei.credit.action.arch;

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
import com.zhiwei.credit.model.arch.BorrowFileList;
import com.zhiwei.credit.service.arch.BorrowFileListService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;

/**
 * 
 * @author
 * 
 */
public class BorrowFileListAction extends BaseAction {
	@Resource
	private BorrowFileListService borrowFileListService;
	private BorrowFileList borrowFileList;

	private Long listId;

	public Long getListId() {
		return listId;
	}

	public void setListId(Long listId) {
		this.listId = listId;
	}

	public BorrowFileList getBorrowFileList() {
		return borrowFileList;
	}

	public void setBorrowFileList(BorrowFileList borrowFileList) {
		this.borrowFileList = borrowFileList;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<BorrowFileList> list = borrowFileListService.getAll(filter);

		Type type = new TypeToken<List<BorrowFileList>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(",result:");

		// Gson gson=new
		// GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "*.class" }).transform(
				new DateTransformer("yyyy-MM-dd"),
				new String[] { "createtime"

				, "archFond.createTime", "archFond.updateTime"

				, "archRoll.archFond.createTime",
						"archRoll.archFond.updateTime"

						, "rollFile.archRoll.archFond.createTime",
						"rollFile.archRoll.archFond.updateTime"

						, "archRoll.startTime", "archRoll.endTime",
						"archRoll.setupTime", "archRoll.createTime"

						, "rollFile.archRoll.startTime",
						"rollFile.archRoll.endTime",
						"rollFile.archRoll.setupTime",
						"rollFile.archRoll.createTime"

						, "rollFile.createTime", "rollFile.fileTime"

						, "borrowRecord.borrowDate", "borrowRecord.checkDate",
						"borrowRecord.returnDate",
						"borrowRecord.appUser.accessionTime"

				});

		buff.append(serializer.serialize(list));
		buff.append("}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	public String listCheck() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<BorrowFileList> list = borrowFileListService.getAll(filter);

		Type type = new TypeToken<List<BorrowFileList>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		JSONSerializer serializer = new JSONSerializer();
		serializer.exclude(new String[] { "*.class" }).transform(
				new DateTransformer("yyyy-MM-dd"),
				new String[] { "createtime"

				, "archFond.createTime", "archFond.updateTime"

				, "archRoll.archFond.createTime",
						"archRoll.archFond.updateTime"

						, "rollFile.archRoll.archFond.createTime",
						"rollFile.archRoll.archFond.updateTime"

						, "archRoll.startTime", "archRoll.endTime",
						"archRoll.setupTime", "archRoll.createTime"

						, "rollFile.archRoll.startTime",
						"rollFile.archRoll.endTime",
						"rollFile.archRoll.setupTime",
						"rollFile.archRoll.createTime"

						, "rollFile.createTime", "rollFile.fileTime"

						, "borrowRecord.borrowDate", "borrowRecord.checkDate",
						"borrowRecord.returnDate",
						"borrowRecord.appUser.accessionTime"

				});

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

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				borrowFileListService.remove(new Long(id));
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
		BorrowFileList borrowFileList = borrowFileListService.get(listId);

		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(borrowFileList));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		borrowFileListService.save(borrowFileList);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
