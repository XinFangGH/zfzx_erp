package com.zhiwei.credit.service.flow.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.model.DynaModel;
import com.zhiwei.core.service.DynamicService;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.FunctionsUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.model.flow.FormDef;
import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.model.flow.FormTable;
import com.zhiwei.credit.service.flow.FlowFormService;
import com.zhiwei.credit.service.flow.FormDefService;
import com.zhiwei.credit.service.flow.FormFieldService;
import com.zhiwei.credit.service.flow.FormTableService;
import com.zhiwei.credit.util.FlowUtil;
/**
 * 流程表单处理类
 * <B><P>EST-BPM -- http://www.hurongtime.com</P></B>
 * <B><P>Copyright (C)  JinZhi WanWei Software Company (北京互融时代软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.zhiwei.credit.service.flow.impl.FlowFormServiceImpl
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-12-23下午05:38:37
 */
public class FlowFormServiceImpl implements FlowFormService{
	private Log logger=LogFactory.getLog(FlowFormServiceImpl.class);
	@Resource
	private FormDefService formDefService;
	@Resource
	private FormFieldService formFieldService;
	@Resource
	private FormTableService formTableService;
	
	/**
	 * 保存业务数据至实体表
	 * @param flowRunInfo
	 * @return 返回实体对象
	 */
	public DynaModel doSaveData(FlowRunInfo flowRunInfo){
		if(flowRunInfo.getFormDefId()==null) return null;
		FormDef formDef=formDefService.get(new Long(flowRunInfo.getFormDefId()));
		//若存在表单定义
		if(formDef!=null){
			//取得主表
			FormTable mainTable=formDef.getMainTable();
			/**
			**
			*OLD CODE 
			//取得从表
			FormTable subTable=formDef.getSubTable();
			* 
			 */
			//取多个子表
			List<FormTable> subTables=formDef.getSubTables();
			//存在主表
			if(mainTable!=null){
				try{
					//主体映射Model
					DynaModel mainDynaModel=new DynaModel(mainTable); //FlowUtil.getInitDynModel(mainTable.getEntityName());
					
					DynamicService mainDynamicService=BeanUtil.getDynamicServiceBean(mainTable.getEntityName());
					
					//通过页面取值至实体
					Map mainData=BeanUtil.populateEntity(flowRunInfo.getRequest(),mainDynaModel);
				    String pkFieldName=mainDynaModel.getPrimaryFieldName();
				    Serializable entityPKValue=(Serializable)mainData.get(pkFieldName);
 
				    if(entityPKValue!=null){//通过检查主键是否为空来决定是更新还是保存
				    	Map orgEntity=(Map)mainDynamicService.get(entityPKValue);
				    	//盖上修改的值
				    	orgEntity.putAll(mainData);
				    	
				    	mainDynamicService.save(orgEntity);
				    	//设置回其值
				    	mainDynaModel.getDatas().putAll(orgEntity);
				    	
				    }else{
				    	//保存该实体的方法
				    	mainDynamicService.save(mainData);
					    //设置其新主键值至流程中
					    entityPKValue=(Serializable)mainData.get(pkFieldName);
				    	
				    	mainDynaModel.set(FlowRunInfo.ENTITY_NAME,mainTable.getEntityName());
				    	
				    	mainDynaModel.setDatas(mainData);
				    	//设置回主键值
					    flowRunInfo.setEntityPK(entityPKValue);
				    }
				    if(subTables!=null&&(subTables.size()>0)){
				    	for(FormTable subTable:subTables){
				    		String details=flowRunInfo.getRequest().getParameter(subTable.getTableKey()+"details");
				    		if(StringUtils.isNotEmpty(details)){//转化为数组对象
				    			DynaModel subDynaModel=new DynaModel(subTable);
				    			DynamicService subDynamicService=BeanUtil.getDynamicServiceBean(subTable.getEntityName());
					    		String subPk=subDynaModel.getPrimaryFieldName();
					    		Gson gson=new Gson();
					    		String[] detailArr=gson.fromJson(details, String[].class);
					    		for(String rowArr:detailArr){
					    			HashMap rowMap=gson.fromJson(rowArr, new TypeToken<HashMap<String,String>>(){}.getType());
					    			//转化为Map
					    			Map<String,Object> datas=BeanUtil.populateEntity(rowMap, subDynaModel);
					    			
					    			Object subPkVal=datas.get(subPk);
					    			if(subPkVal!=null){//更新实体
					    				Map orgEntity=(Map)subDynamicService.get((Serializable)subPkVal);
					    				orgEntity.putAll(datas);
								    	subDynamicService.save(orgEntity);
					    			}else{//新增加
					    				datas.put(mainTable.getEntityName(),mainDynaModel.getDatas());
						    			subDynamicService.save(datas);
					    			}
					    		}
				    		}else{
				    			//子体映射Model
								DynaModel subDynaModel=new DynaModel(subTable); //FlowUtil.getInitDynModel(mainTable.getEntityName());
								DynamicService subDynamicService=BeanUtil.getDynamicServiceBean(subTable.getEntityName());
								//通过页面取值至实体
								Map subData=BeanUtil.populateEntity(flowRunInfo.getRequest(),subDynaModel);
							    String subpkFieldName=subDynaModel.getPrimaryFieldName();
//							    Serializable subEntityPKValue=(Serializable)subData.get(subTable.getTableKey()+"_"+subpkFieldName);
							    String strsubEntityPKValue=flowRunInfo.getRequest().getParameter(subTable.getTableKey()+"_"+subpkFieldName);
							    Object subEntityPKValue=null;
							    if(StringUtils.isNotEmpty(strsubEntityPKValue)){
							    	subEntityPKValue=BeanUtil.convertValue(new ConvertUtilsBean(), strsubEntityPKValue, subDynaModel.getType(subpkFieldName));
							    }
							    if(subEntityPKValue!=null){//通过检查主键是否为空来决定是更新还是保存
							    	Map orgSubEntity=(Map)subDynamicService.get((Serializable)subEntityPKValue);
							    	//盖上修改的值
							    	orgSubEntity.putAll(subData);
							    	
							    	subDynamicService.save(orgSubEntity);
							    	//设置回其值
							    	subDynaModel.getDatas().putAll(orgSubEntity);
							    	
							    }else{
							    	subData.put(mainTable.getEntityName(),mainDynaModel.getDatas());
							    	//保存该实体的方法
							    	subDynamicService.save(subData);
							    }
				    		}
				    	}
				    }
				    /**   OLD CODE
				    if(subTable!=null){//进行从表的保存
				    	DynaModel subDynaModel=new DynaModel(subTable); 
				    	
				    	String details=flowRunInfo.getRequest().getParameter("details");
				    	
				    	//取得从表
				    	if(StringUtils.isNotEmpty(details)){//转化为数组对象
				    		DynamicService subDynamicService=BeanUtil.getDynamicServiceBean(subTable.getEntityName());
				    		
				    		String subPk=subDynaModel.getPrimaryFieldName();
				    		Gson gson=new Gson();
				    		String[] detailArr=gson.fromJson(details, String[].class);
				    		
				    		for(String rowArr:detailArr){
				    			HashMap rowMap=gson.fromJson(rowArr, new TypeToken<HashMap<String,String>>(){}.getType());
				    			//转化为Map
				    			Map<String,Object> datas=BeanUtil.populateEntity(rowMap, subDynaModel);
				    			
				    			Object subPkVal=datas.get(subPk);
				    			if(subPkVal!=null){//更新实体
				    				Map orgEntity=(Map)subDynamicService.get((Serializable)subPkVal);
				    				orgEntity.putAll(datas);
							    	subDynamicService.save(orgEntity);
				    			}else{//新增加
				    				datas.put(mainTable.getEntityName(),mainDynaModel.getDatas());
					    			subDynamicService.save(datas);
				    			}
				    		}
				    	}
				    }
					**/
					//取到其对应的流程标题
					FormField titleField=formFieldService.find(mainTable.getTableId(), FormField.FLOW_TITLE);
					if(titleField!=null){
						try{
							//取得标题字段值
							String flowSubject=(String)mainDynaModel.get(titleField.getFieldName());
							flowRunInfo.setFlowSubject(flowSubject);
						}catch(Exception ex){
							logger.error(ex.getMessage());
						}
					}
					//为流程变量加入实体名，方便更新
				    flowRunInfo.setEntityName(mainTable.getEntityName());
				    
				    //把该表对应的所有数据加至流程变量中
				    flowRunInfo.getVariables().putAll(mainDynaModel.getDatas());
				    
				    return mainDynaModel;
				    
				}catch(Exception ex){
					ex.printStackTrace();
					logger.error("error:"+ex.getMessage());
				}
			}else{
				logger.debug("main table is not exist");
			}
		}else{
			logger.debug("formdef is not exist " + flowRunInfo.getFormDefId());
		}
		return null;
		
	}
	
	public boolean deleteItems(String strIds,Long tableId){
		FormTable formTable=formTableService.get(tableId);
		DynaModel subDynaModel=new DynaModel(formTable);
		DynamicService subDynamicService=BeanUtil.getDynamicServiceBean(subDynaModel.getEntityName());
		String [] ids=strIds.split(",");
		String subpkFieldName=subDynaModel.getPrimaryFieldName();
		try{
			for(String id:ids){
				Object subEntityPKValue=null;
			    if(StringUtils.isNotEmpty(id)){
			    	subEntityPKValue=BeanUtil.convertValue(new ConvertUtilsBean(), id, subDynaModel.getType(subpkFieldName));
			    	if(subEntityPKValue!=null){
			    		subDynamicService.remove((Serializable)subEntityPKValue);
			    	}
			    }			
			}
		}catch(Exception e){
			return false;
		}
		return true;
		
	}
	
}
