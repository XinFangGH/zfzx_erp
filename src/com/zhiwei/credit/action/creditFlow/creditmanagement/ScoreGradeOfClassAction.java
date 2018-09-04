package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ClassTypeDao;
import com.zhiwei.credit.dao.creditFlow.creditmanagement.ScoreGradeOfClassDao;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ClassType;
import com.zhiwei.credit.model.creditFlow.creditmanagement.ScoreGradeOfClass;
import com.zhiwei.credit.model.system.AppUser;



public class ScoreGradeOfClassAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
	private String classId;
	private String classname;
	private String remarks;
	private String scoreGrade;
	private Long grandId;
	private String createPersonId;
	private Date startTime;
	private Date endTime;
	@Resource
	private ClassTypeDao classTypeDao;
	@Resource
	private ScoreGradeOfClassDao scoreGradeOfClassDao;
	public void saveClassAndGrand(){
		String msg="";
		try{
			ClassType classType=null;
			
			
			if(null==classId || "".equals(classId)){
				ClassType ct=classTypeDao.getClassTypeByName(classname);
				if(ct==null){
					classType=new ClassType();
					classType.setClassName(classname);
					classType.setRemarks(remarks);
					AppUser userInfo = ContextUtil.getCurrentUser();
					classType.setCreatePersonId(userInfo.getUserId());
					classType.setCretePerson(userInfo.getFullname());
					Date date=new Date();
					classType.setCreateTime(date);
					classType.setUpdatePersonId(userInfo.getUserId());
					classType.setUpdatePerson(userInfo.getFullname());
					classType.setUpdateTime(date);
				    classTypeDao.addClassType(classType);
				  
				}else{
					 msg="{success:true,msg:'unquine'}";
				}
			}else if(null!=classId && !"".equals(classId)){
				ClassType ct=classTypeDao.getClassTypeByIdAndName(Long.parseLong(classId), classname);
				if(ct==null){
					classType=classTypeDao.getClassType(Long.parseLong(classId));
					classType.setClassName(classname);
					classType.setRemarks(remarks);
					AppUser userInfo = ContextUtil.getCurrentUser();
					classType.setUpdatePersonId(userInfo.getUserId());
					classType.setUpdatePerson(userInfo.getFullname());
					Date date=new Date();
					classType.setUpdateTime(date);
					classTypeDao.updateClassType(classType);
				}else{
					 msg="{success:true,msg:'unquine'}";
				}
			}
			if("".equals(msg)){
				if(null != scoreGrade && !"".equals(scoreGrade)) {
		
					String[] scoreGradeArr = scoreGrade.split("@");
					
					for(int i=0; i<scoreGradeArr.length; i++) {
						String str = scoreGradeArr[i];
						JSONParser parser = new JSONParser(new StringReader(str));
						
							ScoreGradeOfClass scoreGradeOfClass = (ScoreGradeOfClass)JSONMapper.toJava(parser.nextValue(),ScoreGradeOfClass.class);
							scoreGradeOfClass.setClassId(classType.getClassId());
							scoreGradeOfClass.setClassName(classType.getClassName());
							
							if(null==scoreGradeOfClass.getGrandId()){
								scoreGradeOfClassDao.saveScoreGradeOfClass(scoreGradeOfClass);
							}else{
								scoreGradeOfClassDao.updateScoreGradeOfClass(scoreGradeOfClass);
							}
						
						
						
					}
				 }
			
				  msg="{success:true}";
			}
		} catch(Exception e) {
			msg="{success:false}";
			e.printStackTrace();
            
		}
		
        JsonUtil.responseJsonString(msg);
		
	}
	
	public void deleteSG(){
		String msg="";
		try{
			ScoreGradeOfClass scoreGradeOfClass=scoreGradeOfClassDao.getScoreGradeOfClass(grandId);
			scoreGradeOfClassDao.deleteScoreGradeOfClass(scoreGradeOfClass);
		    msg="{success:true}";
		}catch(Exception e){
			msg="{success:false}";
			e.printStackTrace();
			
		}
		JsonUtil.responseJsonString(msg);
	}
	
	public void getClassType(){
		ClassType classType=null;
		if(null!=classId && !"null".equals(classId)){
			classType=classTypeDao.getClassType(Long.parseLong(classId));
			
		}
		JsonUtil.jsonFromObject(classType, true);
	}
	
	public void classTypeList(){
		if(null==classname){
			classname="";
		}
	
		
		List<ClassType> list=classTypeDao.getClassTypeList(classname, createPersonId, startTime, endTime,start,limit);
		int totalCount=classTypeDao.getListNum(classname, createPersonId, startTime, endTime);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(totalCount).append(",result:");
		Type type = new TypeToken<List<ClassType>>() {}.getType();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		JsonUtil.responseJsonString(buff.toString());
	}
	
	public void getScoreGradeList(){
		List<ScoreGradeOfClass> list=new ArrayList<ScoreGradeOfClass>();
		if(null!=classId && !"null".equals(classId)){
			list=scoreGradeOfClassDao.getScoreGradeList(Long.parseLong(classId));
			
		}
		JsonUtil.jsonFromList(list);
	}
	public void deleteClassType(){
		String msg="";
		try{
			String[]ids=getRequest().getParameterValues("ids");
			if(ids!=null){
				for(String id:ids){
					List<ScoreGradeOfClass> list=scoreGradeOfClassDao.getScoreGradeList(Long.parseLong(id));
					scoreGradeOfClassDao.deleteScoreList(list);
					ClassType classType=classTypeDao.getClassType(Long.parseLong(id));
					classTypeDao.deleteClassType(classType);
				}
			}
			msg="{success:true}";
		}catch(Exception e){
			msg="{success:false}";
			e.printStackTrace();
		}
	}
	public void getAllCLassType(){
		List<ClassType> list=classTypeDao.getAllClassType();
		StringBuffer buff = new StringBuffer("[");
		for (ClassType ClassType : list) {
			buff.append("['").append(ClassType.getClassName()).append("','")
					.append(ClassType.getClassId()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		JsonUtil.responseJsonString(buff.toString());
	}
	

	public String getClassId() {
		return classId;
	}
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getScoreGrade() {
		return scoreGrade;
	}
	public void setScoreGrade(String scoreGrade) {
		this.scoreGrade = scoreGrade;
	}
	public ClassTypeDao getClassTypeDao() {
		return classTypeDao;
	}
	public void setClassTypeDao(ClassTypeDao classTypeDao) {
		this.classTypeDao = classTypeDao;
	}
	public ScoreGradeOfClassDao getScoreGradeOfClassDao() {
		return scoreGradeOfClassDao;
	}
	public void setScoreGradeOfClassDao(ScoreGradeOfClassDao scoreGradeOfClassDao) {
		this.scoreGradeOfClassDao = scoreGradeOfClassDao;
	}

	public Long getGrandId() {
		return grandId;
	}

	public void setGrandId(Long grandId) {
		this.grandId = grandId;
	}

	public String getCreatePersonId() {
		return createPersonId;
	}

	public void setCreatePersonId(String createPersonId) {
		this.createPersonId = createPersonId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	
}
