package com.zhiwei.credit.service.system.impl;

/*
 *  北京互融时代软件有限公司企业管理平台   -- http://www.hurongtime.com
 *  Copyright (C) 2008-20010 JinZhi WanWei Software Limited company.
 */
import java.util.ArrayList;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.system.GlobalTypeDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.system.GlobalTypeService;

public class GlobalTypeServiceImpl extends BaseServiceImpl<GlobalType>
		implements GlobalTypeService {
	private GlobalTypeDao dao;

	public GlobalTypeServiceImpl(GlobalTypeDao dao) {
		super(dao);
		this.dao = dao;
	}
	public List<GlobalType> getByParentIdCatKey(Long parentId, String catKey) {
		return dao.getByParentIdCatKey(parentId, catKey);
	}

	@Override
	public Integer getCountsByParentId(Long parentId) {
		return dao.getCountsByParentId(parentId);
	}

	/**
	 * 删除分类，同时删除其下所有子子分类
	 * 
	 * @param parentId
	 */
	public void mulDel(Long proTypeId) {
		GlobalType globalType = get(proTypeId);
		dao.evict(globalType);

		List<GlobalType> subList = dao.getByPath(globalType.getPath());

		for (GlobalType gt : subList) {
			dao.remove(gt);
		}

	}

	@Override
	public List<GlobalType> getByParentIdCatKeyUserId(Long parentId,
			String catKey, Long userId) {
		return dao.getByParentIdCatKeyUserId(parentId, catKey, userId);
	}

	@Override
	public List<GlobalType> getByRightsCatKey(AppUser curUser, String catKey) {
		return dao.getByRightsCatKey(curUser, catKey);
	}

	@Override
	public List<GlobalType> getByCatKeyUserId(AppUser curUser,String catKey) {
		List<GlobalType> typeList = null;
		if (curUser.isSupperManage()) // 假如是超级管理员,则有全部权限
			typeList = getByParentIdCatKey(new Long(0l), catKey);
		else
			typeList = getByRightsCatKey(curUser, catKey);
		List<GlobalType> record = new ArrayList<GlobalType>();
		for (GlobalType type : typeList) {
			if (!record.contains(type)) {
				//以下代码注释：修改流程分类或发布流程新版本在名字前累加"-"
				//update by lu 2012.02.17
				/*String str = "";
				for (int i = 0; i < type.getDepth(); i++) {
					str += "—";
				}
				type.setTypeName(str + type.getTypeName());*/
				type.setTypeName(type.getTypeName());
				record.add(type);
				getTypeByRights(type.getProTypeId(), catKey, record);
			}
		}
		return record;
	}

	private void getTypeByRights(Long parentId, String catKey,
			List<GlobalType> record) {
		List<GlobalType> typeList = getByParentIdCatKey(parentId, catKey);
		for (GlobalType type : typeList) {
			if (!record.contains(type)) { 
				//以下代码注释：修改流程分类或发布流程新版本在名字前累加"-"
				//update by lu 2012.02.17
				/*String str = "";
				for (int i = 0; i < type.getDepth(); i++) {
					str += "—";
				}
				type.setTypeName(str + type.getTypeName());*/
				type.setTypeName(type.getTypeName());
				record.add(type);
				getTypeByRights(type.getProTypeId(), catKey, record);
			} else
				System.out.print("已经存在" + type.getTypeName());
		}
	}

	@Override
	public List<GlobalType> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey) {
		return dao.getByParentIdCatKeyAndNodeKey(parentId, catKey);
	}

	/**
	 * 根据proTypeId删除下面对应所有节点的信息
	 */
	@Override
	public void delChildrens(Long proTypeId) {
		 dao.delChildrens(proTypeId);
	}

	@Override
	public GlobalType findByFileType(String fileType) {
		return dao.findByFileType(fileType);
	}

	@Override
	public List<GlobalType> getByNodeKey(String nodeKey) {
		
		return dao.getByNodeKey(nodeKey);
	}
	@Override
	public List<GlobalType> getByParentId(Long parentId) {
		
		return dao.getByParentId(parentId);
	}
	@Override
	public List<GlobalType> getByNodeKeyState(String nodeKey) {
		
		return dao.getByNodeKeyState(nodeKey);
	}
	/**
	 * 根据业务品种的key(CompanyBusiness,SmallLoanBusiness)查询当前的业务品种
	 * 各子表中存储的值为：  SmallLoanBusiness,CompanyBusiness,
	 * GlobalType中则为如：SmallLoan_SmallLoanBusiness,Guarantee_definitionType_CompanyBusiness
	 * @param nodeKey
	 * @param catKey
	 * @return
	 */
	public String getByNodeKeyCatKey(String nodeKey,String catKey){
		String typeName = "";
		try{
			List<GlobalType> listGlobalType = dao.getByNodeKeyCatKey(nodeKey, catKey);
			if(listGlobalType!=null&&listGlobalType.size()!=0){
				GlobalType global = listGlobalType.get(0);
				String nodeKye = global.getNodeKey();
				if(nodeKye.indexOf("_")!=-1){
					String[] proArrs = nodeKye.split("_");
					if(proArrs.length>2){
						GlobalType glo = dao.get(global.getParentId());
						if(proArrs.length==3){
							if(glo!=null){
								typeName = glo.getTypeName();
							}
						}else{
							GlobalType g = dao.get(glo.getParentId());
							if(g!=null){
								typeName = g.getTypeName();
							}
						}
					}else{
						typeName = global.getTypeName();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return typeName;
	}
	
	/**
	 * 根据业务品种的名称,如：业务品种查询子项
	 * @param typeName
	 * @return
	 */
	public GlobalType getByTypeName(String typeName){
		return dao.getByTypeName(typeName);
	}
}