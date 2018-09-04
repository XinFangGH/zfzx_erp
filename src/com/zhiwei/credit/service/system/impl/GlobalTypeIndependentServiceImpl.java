package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.GlobalTypeIndependentDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalTypeIndependent;
import com.zhiwei.credit.service.system.GlobalTypeIndependentService;

/**
 * 
 * @author 
 *
 */
public class GlobalTypeIndependentServiceImpl extends BaseServiceImpl<GlobalTypeIndependent> implements GlobalTypeIndependentService{
	@SuppressWarnings("unused")
	private GlobalTypeIndependentDao dao;
	
	public GlobalTypeIndependentServiceImpl(GlobalTypeIndependentDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public GlobalTypeIndependent getByNodeKey(String nodeKey) {
		
		return dao.getByNodeKey(nodeKey);
	}

	@Override
	public void delChildrens(Long proTypeId) {
		dao.delChildrens(proTypeId);
		
	}

	@Override
	public GlobalTypeIndependent findByTypeName(String typeName) {
		
		return dao.findByTypeName(typeName);
	}

	@Override
	public List<GlobalTypeIndependent> getByParentId(Long parentId) {
		
		return dao.getByParentId(parentId);
	}

	@Override
	public List<GlobalTypeIndependent> getByParentIdCatKey(Long parentId,
			String catKey) {
		
		return dao.getByParentIdCatKey(parentId, catKey);
	}

	@Override
	public List<GlobalTypeIndependent> getByParentIdCatKeyAndNodeKey(
			Long parentId, String catKey) {
		
		return dao.getByParentIdCatKeyAndNodeKey(parentId, catKey);
	}

	@Override
	public List<GlobalTypeIndependent> getByParentIdCatKeyUserId(Long parentId,
			String catKey, Long userId) {
		
		return dao.getByParentIdCatKeyUserId(parentId, catKey, userId);
	}

	@Override
	public List<GlobalTypeIndependent> getByPath(String path) {
		
		return dao.getByPath(path);
	}

	@Override
	public List<GlobalTypeIndependent> getByRightsCatKey(AppUser curUser,
			String catKey) {
		
		return dao.getByRightsCatKey(curUser, catKey);
	}

	@Override
	public Integer getCountsByParentId(Long parentId) {
		
		return dao.getCountsByParentId(parentId);
	}

}