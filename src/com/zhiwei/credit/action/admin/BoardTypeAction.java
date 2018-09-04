package com.zhiwei.credit.action.admin;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.BoardType;
import com.zhiwei.credit.service.admin.BoardTypeService;

/**
 * @description BoardTypeAction
 * @author YHZ
 * @date 2010-5-25 PM
 * 
 */
public class BoardTypeAction extends BaseAction {

	@Resource
	private BoardTypeService boardTypeService;

	private BoardType boardType;
	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public BoardType getBoardType() {
		return boardType;
	}

	public void setBoardType(BoardType boardType) {
		this.boardType = boardType;
	}

	/**
	 * @description 分页查询
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("typeId", "DESC");
		List<BoardType> list = boardTypeService.getAll(filter);
		Type type = new TypeToken<List<BoardType>>() {
		}.getType();
		StringBuffer bf = new StringBuffer("{success:true,'totalCounts':");
		bf.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson = new Gson();
		bf.append(gson.toJson(list, type)).append("}");
		setJsonString(bf.toString());
		return SUCCESS;
	}

	/**
	 * @description 批量删除
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("typeIds");
		for (String id : ids) {
			boardTypeService.remove(new Long(id));
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * @description 单条数据删除
	 */
	public String del() {
		boardTypeService.remove(typeId);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * @description 保存
	 */
	public String save() {
		boardTypeService.save(boardType);
		setJsonString("{success:true}");
		return SUCCESS;
	}

	/**
	 * @description 详细信息
	 */
	public String get() {
		BoardType boardType = boardTypeService.get(typeId);
		Gson gson = new Gson();
		StringBuffer bf = new StringBuffer("{success:true,data:");
		bf.append(gson.toJson(boardType)).append("}");
		setJsonString(bf.toString());
		return SUCCESS;
	}

}
