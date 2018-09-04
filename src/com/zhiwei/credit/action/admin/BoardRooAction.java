package com.zhiwei.credit.action.admin;

import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.BoardRoo;
import com.zhiwei.credit.service.admin.BoardRooService;

/**
 * @description BoardRooAction
 * @author 智维软件
 * @data 2010-9-20 PM
 * 
 */
public class BoardRooAction extends BaseAction {

	@Resource
	private BoardRooService boardRooService;

	private BoardRoo boardRoo;
	private Long roomId;

	public Long getRoomId() {
		return roomId;
	}

	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public BoardRoo getBoardRoo() {
		return boardRoo;
	}

	public void setBoardRoo(BoardRoo boardRoo) {
		this.boardRoo = boardRoo;
	}

	/**
	 * @description 分页查询所有会议室信息
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addSorted("roomId", "DESC");
		List<BoardRoo> list = boardRooService.getAll(filter);
		Type type = new TypeToken<List<BoardRoo>>() {
		}.getType();
		StringBuffer bf = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		Gson gson = new Gson();
		bf.append(gson.toJson(list, type)).append("}");
		setJsonString(bf.toString());
		return SUCCESS;
	}

	/**
	 * @description 批量删除数据操作
	 */
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				boardRooService.remove(Long.valueOf(id));
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * @description 单条数据参数
	 */
	public String del() {
		boardRooService.remove(roomId);
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * @description 新增操作
	 */
	public String save() {
		boardRooService.save(boardRoo);
		jsonString = "{success:true,msg:'保存成功'}";
		return SUCCESS;
	}

	/**
	 * @description 根据RoomId获取对象的详细信息
	 */
	public String get() {
		BoardRoo boardRoo = boardRooService.get(roomId);
		Gson gson = new Gson();
		StringBuffer bf = new StringBuffer("{success:true,data:");
		bf.append(gson.toJson(boardRoo)).append("}");
		setJsonString(bf.toString());
		return SUCCESS;
	}
}
