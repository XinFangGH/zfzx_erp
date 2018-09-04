package com.zhiwei.credit.service.creditFlow.smallLoan.project; import java.util.List;
import java.util.Map;

import com.zhiwei.credit.dao.creditFlow.smallLoan.project.MySelfDao;
/*
 * @author刘程源
 * */
public class MySelfService {

	private MySelfDao mySelfDao;  MySelfService() { 
	 
 }
 
 MySelfService(MySelfDao mySelfDao) {
  this.mySelfDao = mySelfDao;
 }
 public Object saveOrUpdate(Object o) {
  return mySelfDao.saveOrUpdate(o);
 } 
 public Object get(String name, String propertyName, Object value) {
  return mySelfDao.get(name, propertyName, value);
 }  
 public List getForList(String pcalss) {
  return mySelfDao.getList(pcalss);
 }
 public Object[] getForList(String pcalss, Integer start, Integer limit) {
  return mySelfDao.getList(pcalss, start, limit);
 }
 public Object[] getForList(String pcalss, String propertyName, Object value,Integer start, Integer limit) {
	 return mySelfDao.getList(pcalss,propertyName,value, start, limit);
 }
 public List getForList(String pcalss, Map m) {
  return mySelfDao.getList(pcalss,m);
 }
 public void delete(Object object){
	 mySelfDao.delete(object);
 }
 
 }
