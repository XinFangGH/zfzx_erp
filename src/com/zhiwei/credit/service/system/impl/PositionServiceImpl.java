package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.system.PositionDao;
import com.zhiwei.credit.model.system.Position;
import com.zhiwei.credit.service.system.PositionService;

public class PositionServiceImpl extends BaseServiceImpl<Position> implements PositionService{

	private PositionDao dao;
	
	public PositionServiceImpl(PositionDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Position> getByParent(Long parentId){
		return dao.getByParent(parentId);
	}
	
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Position> getByPath(String path){
		return dao.getByPath(path);
	}
	
	/**
	 * 删除某个岗位及其下属岗位
	 * @param posId
	 */
	public void delPositionCascade(Long posId){
		Position position=get(posId);
		evict(position);
		List<Position> listPos=getByPath(position.getPath());
		for(Position pos:listPos){
			remove(pos);
		}
	}
	
	/**
	 * 根据某岗位取得相应岗位
	 * @param 
	 * posId 当前岗位
	 * @return
	 */
	public List<Position> getRelativeUnderling(Long posId, PagingBean pb) {
		return dao.getRelativeUnderling(posId, pb);
	}

	@Override
	public List<Position> getUnderling(Long posId, PagingBean pb) {
		return dao.getUnderLing(posId, pb);
	}

	/**
	 * 取得同级岗位的主岗位
	 * @param posId 岗位id
	 * @return
	 */
	public List<Position> getUnderByParent(Long posId) {
		return dao.getUnderByParent(posId);
	}

	/**
	 * 取得同级岗位
	 * @param 
	 * posId 岗位id
	 * posName 岗位名称
	 * @return
	 */
	public List<Position> getSameLevel(Long posId, PagingBean pb, String posName) {
		return dao.getSameLevel(posId, pb, posName);
	}
	                                    
}