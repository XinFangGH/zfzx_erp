package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.Position;

public interface PositionService extends BaseService<Position>{
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId);
	
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path);
	/**
	 * 
	 * @param posId
	 */
	public void delPositionCascade(Long posId);
	
	/**
	 * 根据某岗位取得相应岗位
	 * @param 
	 * posId 当前岗位
	 * posName 岗位名称
	 * @return
	 */
	public List<Position> getRelativeUnderling(Long posId, PagingBean pb);

	List<Position> getUnderling(Long posId, PagingBean pb);
	
	/**
	 * 取得同级岗位的主岗位
	 * @param posId 岗位id
	 * @return
	 */
	public List<Position> getUnderByParent(Long posId);
	
	/**
	 * 取得同级岗位
	 * @param 
	 * posId 岗位id
	 * posName 岗位名称
	 * @return
	 */
	public List<Position> getSameLevel(Long posId, PagingBean pb, String posName);
}


