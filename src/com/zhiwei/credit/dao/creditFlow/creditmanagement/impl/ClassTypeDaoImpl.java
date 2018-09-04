package com.zhiwei.credit.dao.creditFlow.creditmanagement.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.log.LogServicesResource;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ClassTypeDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ClassType;



public class ClassTypeDaoImpl extends BaseDaoImpl<ClassType> implements ClassTypeDao{
	public ClassTypeDaoImpl() {
		super(ClassType.class);
		
	}

	@Resource
	private CreditBaseDao creditBaseDao;
	@LogServicesResource(description = "添加或修改信用评级标准")
	@Override
	public Boolean addClassType(ClassType classType) {
		boolean b=false;
		try {
			b=creditBaseDao.saveDatas(classType);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}
	@LogServicesResource(description = "删除信用评级标准")
	@Override
	public Boolean deleteClassType(ClassType classType) {
		boolean b=false;
		try {
			b=creditBaseDao.deleteDatas(classType);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public ClassType getClassType(Long classId) {
		ClassType classType=null;
		try {
			classType=(ClassType) creditBaseDao.getById(ClassType.class, classId);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return classType;
	}

	@Override
	public Boolean updateClassType(ClassType classType) {
		boolean b=false;
		try {
			b=creditBaseDao.updateDatas(classType);
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return b;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClassType> getClassTypeList(String className,
			String createPersonId, Date startTime, Date endTime,int start,int limit) {
	
		String hql="from ClassType c where c.className like ?";
		if(null!=createPersonId && !"".equals(createPersonId)){
			hql=hql+" and c.createPersonId="+Long.parseLong(createPersonId);
		}
		if(null!=startTime && null!=endTime){
			hql=hql+" and c.createTime between ? and ?";
			return getSession().createQuery(hql).setParameter(0, "%"+className+"%").setParameter(1, startTime).setParameter(2, endTime).setFirstResult(start).setMaxResults(limit).list();
		}else{
		    return getSession().createQuery(hql).setParameter(0, "%"+className+"%").setFirstResult(start).setMaxResults(limit).list();
		}
	}

	@Override
	public int getListNum(String className, String createPersonId,
			Date startTime, Date endTime) {

		String hql="from ClassType c where c.className like ?";
		if(null!=createPersonId && !"".equals(createPersonId)){
			hql=hql+" and c.createPersonId="+Long.parseLong(createPersonId);
		}
		List list=null;
		if(null!=startTime && null!=endTime){
			hql=hql+" and c.createTime between ? and ?";
			list=getSession().createQuery(hql).setParameter(0, "%"+className+"%").setParameter(1, startTime).setParameter(2, endTime).list();
		}else{
		    list=getSession().createQuery(hql).setParameter(0, "%"+className+"%").list();
		}
		int count=0;
		if(null!=list && list.size()>0){
			count=list.size();
		}
		return count;
	}

	@Override
	public List<ClassType> getAllClassType() {
		String hql="from ClassType";
		return getSession().createQuery(hql).list();
	}

	@Override
	public ClassType getClassTypeByName(String className) {
		String hql="from ClassType c where c.className=?";
		return (ClassType) getSession().createQuery(hql).setParameter(0, className).uniqueResult();
	}

	@Override
	public ClassType getClassTypeByIdAndName(Long classId, String className) {
		String hql="from ClassType c where c.classId!=? and c.className=?";
		return (ClassType) getSession().createQuery(hql).setParameter(0, classId).setParameter(1, className).uniqueResult();
	}
}
