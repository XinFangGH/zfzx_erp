package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jfree.util.Log;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.system.GlobalTypeIndependentDao;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.model.system.GlobalTypeIndependent;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class GlobalTypeIndependentDaoImpl extends BaseDaoImpl<GlobalTypeIndependent> implements GlobalTypeIndependentDao{

	public GlobalTypeIndependentDaoImpl() {
		super(GlobalTypeIndependent.class);
	}

	@Override
	public GlobalTypeIndependent getByNodeKey(String nodeKey) {
		String hql="from GlobalTypeIndependent where nodeKey=?";
		return (GlobalTypeIndependent) getSession().createQuery(hql).setParameter(0, nodeKey).uniqueResult();
	}
	public List<GlobalTypeIndependent> getByParentIdCatKey(Long parentId,String catKey){
		String hql=" from GlobalTypeIndependent gt where gt.parentId = ? and gt.catKey = ? and gt.status=0 order by gt.sn asc";
		return findByHql(hql, new Object[]{parentId,catKey});
	}
	
	public Integer getCountsByParentId(Long parentId){
		ArrayList<Object> param=new ArrayList<Object>();
		String hql= " select count(proTypeId) from GlobalTypeIndependent gt ";
		if(parentId!=null && parentId!=0){
			hql+=" where gt.parentId=?";
			param.add(parentId);
		}else{
			hql+=" where gt.parentId is null";
		}
		
		Object obj=findUnique(hql, param.toArray());
		return new Integer(obj.toString());
		
	}
	
	public List<GlobalTypeIndependent> getByParentId(Long parentId){
		ArrayList<Object> param=new ArrayList<Object>();
		String hql= " from GlobalTypeIndependent gt ";
		if(parentId!=null && parentId!=0){
			hql+=" where gt.parentId=? order by gt.sn asc";
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
	public List<GlobalTypeIndependent> getByPath(String path){
		String hql=" from GlobalTypeIndependent gt where gt.path like ?";
		return findByHql(hql,new Object[]{path+"%"});
	}
	@Override
	public GlobalTypeIndependent findByTypeName(String typeName) {
		String hql=" from GlobalTypeIndependent gt where gt.typeName = ?";
		List<GlobalTypeIndependent> list = findByHql(hql,new Object[]{typeName});
		if(list.size()>0){
			return list.get(0);
		}else{
			return null;
		}
		
	}
	@Override
	public List<GlobalTypeIndependent> getByParentIdCatKeyUserId(Long parentId,
			String catKey, Long userId) {
		String hql=" from GlobalTypeIndependent gt where gt.parentId = ? and gt.catKey = ? and gt.userId=?";
		return findByHql(hql, new Object[]{parentId,catKey,userId});
	}
	@Override
	public List<GlobalTypeIndependent> getByRightsCatKey(AppUser curUser, String catKey) {
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
	public List<GlobalTypeIndependent> getByParentIdCatKeyAndNodeKey(Long parentId, String catKey) {
		String hql=" from GlobalTypeIndependent gt where gt.parentId = ? and gt.catKey = ? order by gt.sn asc";
		Log.debug("GlobalTypeIndependent : " + hql);
		return findByHql(hql, new Object[]{parentId,catKey});
	}
	
	/**
	 * 根据proTypeId删除下面所有节点的信息
	 */
	@Override
	public void delChildrens(Long proTypeId) {
		GlobalTypeIndependent type = get(proTypeId);
		String hql = "delete from GlobalTypeIndependent vo where vo.path like ? or vo.proTypeId = ?";
		Object [] params = {type.getPath() + "%",proTypeId};
		Long count = update(hql, params);
		logger.debug("删除数据操作成功，受影响的行数：" + count);
	}
}