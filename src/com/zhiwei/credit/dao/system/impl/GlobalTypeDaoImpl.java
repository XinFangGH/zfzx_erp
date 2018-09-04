package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司企业管理平台   -- http://www.hurongtime.com
 *  Copyright (C) 2008-20010 JinZhi WanWei Software Limited company.
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jfree.util.Log;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.GlobalTypeDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;

@SuppressWarnings("unchecked")
public class GlobalTypeDaoImpl extends BaseDaoImpl<GlobalType> implements GlobalTypeDao{

	public GlobalTypeDaoImpl() {
		super(GlobalType.class);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.ent.dao.system.GlobalTypeDao#getByParentIdCatKey(java.lang.Long, java.lang.String)
	 */
	public List<GlobalType> getByParentIdCatKey(Long parentId,String catKey){
		String hql=" from GlobalType gt where gt.parentId = ? and gt.catKey = ? and gt.status=0 order by gt.sn asc";
		return findByHql(hql, new Object[]{parentId,catKey});
	}
	
	public Integer getCountsByParentId(Long parentId){
		ArrayList<Object> param=new ArrayList<Object>();
		String hql= " select count(proTypeId) from GlobalType gt ";
		if(parentId!=null && parentId!=0){
			hql+=" where gt.parentId=?";
			param.add(parentId);
		}else{
			hql+=" where gt.parentId is null";
		}
		
		Object obj=findUnique(hql, param.toArray());
		return new Integer(obj.toString());
		
	}
	
	public List<GlobalType> getByParentId(Long parentId){
		ArrayList<Object> param=new ArrayList<Object>();
		String hql= " from GlobalType gt ";
		if(parentId!=null && parentId!=0){
			hql+=" where gt.parentId=? and gt.status=0  order by gt.sn asc";
			param.add(parentId);
		}else{
			hql+=" where gt.parentId is null";
		}
		return findByHql(hql, param.toArray());
	}
	/**
	 * 
	 * @param path
	 * @return
	 */
	public List<GlobalType> getByPath(String path){
		String hql=" from GlobalType gt where gt.path like ?";
		return findByHql(hql,new Object[]{path+"%"});
	}
	@Override
	public GlobalType findByTypeName(String typeName) {
		String hql=" from GlobalType gt where gt.typeName = ?";
		List<GlobalType> list = findByHql(hql,new Object[]{typeName});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	@Override
	public List<GlobalType> getByParentIdCatKeyUserId(Long parentId,
			String catKey, Long userId) {
		String hql=" from GlobalType gt where gt.parentId = ? and gt.catKey = ? and gt.userId=?";
		return findByHql(hql, new Object[]{parentId,catKey,userId});
	}
	@Override
	public List<GlobalType> getByRightsCatKey(AppUser curUser, String catKey) {
		String uIds = "%,"+curUser.getUserId()+",%";
		String dIds = "%,"+curUser.getDepartment().getDepId()+",%";
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql =new StringBuffer("select gt from ProDefRights pr right join pr.globalType gt  where gt.catKey = ? and (pr.userIds like ?  or pr.depIds like ? ");
		params.add(catKey);
		params.add(uIds);
		params.add(dIds);
		
		Set<AppRole> roles = curUser.getRoles();
		for(Iterator<AppRole> it = roles.iterator();it.hasNext();){
			AppRole role = it.next();
			hql.append("or pr.roleIds like ? ");
			String rIds = "%,"+role.getRoleId()+",%";
			params.add(rIds);
		}
		
		hql.append(")");
		return findByHql(hql.toString(),params.toArray());
	}
	
	@Override
	public List<GlobalType> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey) {
		String hql=" from GlobalType gt where gt.parentId = ? and gt.catKey = ? order by gt.sn asc";
		Log.debug("GlobalType : " + hql);
		return findByHql(hql, new Object[]{parentId,catKey});
	}
	
	/**
	 * 根据proTypeId删除下面所有节点的信息
	 */
	@Override
	public void delChildrens(Long proTypeId) {
		GlobalType type = get(proTypeId);
		String hql = "delete from GlobalType vo where vo.path like ? or vo.proTypeId = ?";
		Object [] params = {type.getPath() + "%",proTypeId};
		Long count = update(hql, params);
		logger.debug("删除数据操作成功，受影响的行数：" + count);
	}
	
	
	@Override
	public GlobalType findByFileType(String fileType) {
		String hql=" from GlobalType gt where gt.nodeKey = ? and gt.catKey = ? order by gt.sn asc";
		Log.debug("GlobalType : " + hql);
		List<GlobalType> list = findByHql(hql, new Object[]{fileType, "ATTACHFILE_TYPE"});
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	@Override
	public List<GlobalType> getByNodeKey(String nodeKey) {
		
		String hql=" from GlobalType gt where gt.nodeKey = ? and gt.status=0 order by gt.sn asc";
		
		return findByHql(hql, new Object[]{nodeKey});
	}
	@Override
	public List<GlobalType> getByNodeKeyState(String nodeKey) {
        String hql=" from GlobalType gt where gt.nodeKey = ? order by gt.sn asc";
		
		return findByHql(hql, new Object[]{nodeKey});
	}
	@Override
	public List<GlobalType> getByNodeKeyCatKey(String nodeKey,String catKey) {
		
		String hql=" from GlobalType gt where gt.nodeKey like '%"+nodeKey+"%' and gt.catKey = ? order by gt.sn asc";
		
		return findByHql(hql, new Object[]{catKey});
	}
	
	/**
	 * 根据业务品种的名称,如：业务品种查询子项
	 * @param typeName
	 * @return
	 */
	@Override
	public GlobalType getByTypeName(String typeName){
		String hql="from GlobalType gt where gt.typeName=? and gt.status=0 order by gt.sn asc";
		List<GlobalType> list = findByHql(hql, new Object[]{typeName});
		if(list!=null&&list.size()!=0){
			GlobalType globalType = list.get(0);
			return globalType;
		}
		return null;
	}
}