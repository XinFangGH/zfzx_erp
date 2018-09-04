package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司企业管理平台   -- http://www.hurongtime.com
 *  Copyright (C) 2008-20010 JinZhi WanWei Software Limited company.
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;

/**
 * 
 * @author 
 *
 */
public interface GlobalTypeDao extends BaseDao<GlobalType>{
	/**
	 * 根据node key 获取节点列表
	 */
	public List<GlobalType> getByNodeKey(String nodeKey);
	public List<GlobalType> getByNodeKeyState(String nodeKey);
	
	/**
	 * 取得某种分类下的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByParentIdCatKey(Long parentId,String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	List<GlobalType> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey);
	
	/**
	 * 取得某种分类下某个用户的子结点列表
	 * @param parentId
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByParentIdCatKeyUserId(Long parentId,String catKey,Long userId);
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
	public List<GlobalType> getByParentId(Long parentId);
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public List<GlobalType> getByPath(String path);

	public GlobalType findByTypeName(String typeName);
	/**
	 * 根据当前用户权限产生流程分类树
	 * @param curUser
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByRightsCatKey(AppUser curUser, String catKey);
	
	/**
	 * 根据proTypeId删除，下面所有的子节点信息
	 * @param proTypeId
	 */
	void delChildrens(Long proTypeId);
	
	/**
	 * 根据fileType查询对应的一个GlobalType对象
	 * @param fileType fileType
	 * @return GlobalType
	 */
	GlobalType findByFileType(String fileType);
	
	/**
	 * 根据业务品种的key(CompanyBusiness,SmallLoanBusiness)查询当前的业务品种
	 * 各子表中存储的值为：  SmallLoanBusiness,CompanyBusiness,
	 * GlobalType中则为如：SmallLoan_SmallLoanBusiness,Guarantee_definitionType_CompanyBusiness
	 * @param nodeKey
	 * @param catKey
	 * @return
	 */
	public List<GlobalType> getByNodeKeyCatKey(String nodeKey,String catKey);
	
	/**
	 * 根据业务品种的名称,如：业务品种查询子项
	 * @param typeName
	 * @return
	 */
	public GlobalType getByTypeName(String typeName);
}