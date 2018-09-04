package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalTypeIndependent;

/**
 * 
 * @author 
 *
 */
public interface GlobalTypeIndependentService extends BaseService<GlobalTypeIndependent>{
	public GlobalTypeIndependent getByNodeKey(String nodeKey);
	public List<GlobalTypeIndependent> getByParentIdCatKey(Long parentId,String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	List<GlobalTypeIndependent> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	public List<GlobalTypeIndependent> getByParentIdCatKeyUserId(Long parentId,String catKey,Long userId);
	/**
	 * 取得该分类下的数目
	 * @param parentId
	 * @return
	 */
	public Integer getCountsByParentId(Long parentId);
	
	/**
	 * 取得该分类下的所有子分类
	 * @param parentId
	 * @return
	 */
	public List<GlobalTypeIndependent> getByParentId(Long parentId);
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public List<GlobalTypeIndependent> getByPath(String path);

	public GlobalTypeIndependent findByTypeName(String typeName);
	/**
	 * 根据当前用户权限产生流程分类树
	 * @param curUser
	 * @param catKey
	 * @return
	 */
	public List<GlobalTypeIndependent> getByRightsCatKey(AppUser curUser, String catKey);
	
	/**
	 * 根据proTypeId删除，下面所有的子节点信息
	 * @param proTypeId
	 */
	void delChildrens(Long proTypeId);
}


