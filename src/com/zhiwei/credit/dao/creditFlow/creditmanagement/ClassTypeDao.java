package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ClassType;

public interface ClassTypeDao extends BaseDao<ClassType>{
   public Boolean addClassType(ClassType classType);
   
   public Boolean updateClassType(ClassType classType);
   
   public Boolean deleteClassType(ClassType classType);
   
   public ClassType getClassType(Long classId);
   
   public List<ClassType> getClassTypeList(String className,String createPersonId,Date startTime,Date endTime,int start,int limit);
   
   public int getListNum(String className,String createPersonId,Date startTime,Date endTime);
   
   public List<ClassType> getAllClassType();
   
   public ClassType getClassTypeByName(String className);
   
   public ClassType getClassTypeByIdAndName(Long classId,String className);
}
