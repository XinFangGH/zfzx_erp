package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.DictionaryIndependent;
import com.zhiwei.credit.model.system.GlobalTypeIndependent;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
import com.zhiwei.credit.service.system.GlobalTypeIndependentService;
/**
 * 
 * @author 
 *
 */
public class GlobalTypeIndependentAction extends BaseAction{
	@Resource
	private GlobalTypeIndependentService globalTypeIndependentService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService;
	private GlobalTypeIndependent globalTypeIndependent;
	
	private Long proTypeId;

	
	private String catKey;
    
	private String nodeKey;
	
	
	public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public Long getProTypeId() {
		return proTypeId;
	}

	public void setProTypeId(Long proTypeId) {
		this.proTypeId = proTypeId;
	}

	public GlobalTypeIndependent getGlobalTypeIndependent() {
		return globalTypeIndependent;
	}

	public void setGlobalTypeIndependent(GlobalTypeIndependent globalTypeIndependent) {
		this.globalTypeIndependent = globalTypeIndependent;
	}

	/**
	 * 取得其子类节点列表
	 * @return
	 */
	public String sub(){
		Long parentId=null;
		String sParentId=getRequest().getParameter("parentId");
		if(StringUtils.isNotEmpty(sParentId)){
			parentId=new Long(sParentId);
		}
		List<GlobalTypeIndependent> typeList=globalTypeIndependentService.getByParentIdCatKey(parentId,catKey);
		
		Type type=new TypeToken<List<GlobalTypeIndependent>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(typeList, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	public String mulSave(){
		String data =getRequest().getParameter("data");
		
		logger.info("data:" + data);
		
		if(StringUtils.isNotEmpty(data)){
			Gson gson=new Gson();
			GlobalTypeIndependent[]types=gson.fromJson(data, GlobalTypeIndependent[].class);
			
			for(int i=0;i<types.length;i++){
				GlobalTypeIndependent newType=globalTypeIndependentService.get(types[i].getProTypeId());
				try{
					BeanUtil.copyNotNullProperties(newType, types[i]);
					
					//newType.setSn((i+1));
					globalTypeIndependentService.save(newType);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				};
			}
		}
		
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	/**
	 * 产生树
	 * @return
	 */
	public String tree(){
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'总分类',expanded:true,children:[");
		List<GlobalTypeIndependent> typeList = globalTypeIndependentService.getByParentIdCatKey(new Long(0l),catKey);
		for(GlobalTypeIndependent type:typeList){
			buff.append("{id:'"+type.getProTypeId()).append("',text:'"+type.getTypeName()).append("',");
			buff.append("nodeKey:'" + type.getNodeKey() + "',");
			if(type.getUserId() != null && type.getUserId() > 0 ){
				buff.append("isPublic:'false',"); // 似有
			} else {
				buff.append("isPublic:'true',"); // 公共
			}
		    buff.append(getChildType(type.getProTypeId(), catKey));
		}
		
		if (!typeList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");

		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());		
		return SUCCESS;
	}
	/*
	 * 根据传值产生字典tree
	 */
	public String treeOfType(){
		StringBuffer buff = new StringBuffer("[");
		GlobalTypeIndependent type =globalTypeIndependentService.getByNodeKey(nodeKey);
		
			buff.append("{id:'"+type.getProTypeId()).append("',text:'"+type.getTypeName()).append("',");
			buff.append("nodeKey:'" + type.getNodeKey() + "',");
			if(type.getUserId() != null && type.getUserId() > 0 ){
				buff.append("isPublic:'false',"); // 私有
			} else {
				buff.append("isPublic:'true',"); // 公共
			}
		    buff.append(getChildType(type.getProTypeId(), catKey));
		
		
			buff.deleteCharAt(buff.length() - 1);

		buff.append("]");
		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());
		return SUCCESS;
	}
	
	
	
	public String getChildType(Long parentId, String catKey){
		StringBuffer buff = new StringBuffer();
		List<GlobalTypeIndependent> typeList = globalTypeIndependentService.getByParentIdCatKey(parentId,catKey);
		if(typeList.size() == 0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(GlobalTypeIndependent type:typeList){
				buff.append("{id:'"+type.getProTypeId()).append("',text:'"+type.getTypeName()).append("',");
				buff.append("nodeKey:'" + type.getNodeKey() + "',");
				if(type.getUserId() != null && type.getUserId() > 0){ // 似有不可修改
					buff.append("isPublic:'false',");
				} else {
					buff.append("isPublic:'true',");
				}
			    buff.append(getChildType(type.getProTypeId(), catKey));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<GlobalTypeIndependent> list= globalTypeIndependentService.getAll(filter);
		
		Type type=new TypeToken<List<GlobalTypeIndependent>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}

	
	/**
	 * 根据proTypeId删除下面对应的所有节点信息，包含本身
	 * @return
	 */
	public String delChildrens(){
		if(proTypeId != null && proTypeId > 0){			
			globalTypeIndependentService.delChildrens(proTypeId);
			jsonString = "{success:true}";
		}
		jsonString = "{failure:true}";
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlobalTypeIndependent globalType=globalTypeIndependentService.get(proTypeId);
		
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(globalType));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(globalTypeIndependent!=null && globalTypeIndependent.getNodeKey()!=null){
			GlobalTypeIndependent globalTypeIndependent1=null;
			if(globalTypeIndependent.getProTypeId()==null){
				globalTypeIndependent1=globalTypeIndependentService.getByNodeKey(globalTypeIndependent.getNodeKey());
			}else{
				GlobalTypeIndependent orgGlobalTypeIndependent=globalTypeIndependentService.get(globalTypeIndependent.getProTypeId());
				if(!orgGlobalTypeIndependent.getNodeKey().equals(globalTypeIndependent.getNodeKey())){
					globalTypeIndependent1=globalTypeIndependentService.getByNodeKey(globalTypeIndependent.getNodeKey());
				}
			}
			if(null!=globalTypeIndependent1 ){
				setJsonString("{success:true,mag:false}");
			}else{
				if(globalTypeIndependent!=null && globalTypeIndependent.getProTypeId()!=null){
					GlobalTypeIndependent orgGlobalType=globalTypeIndependentService.get(globalTypeIndependent.getProTypeId());
					try{
						BeanUtil.copyNotNullProperties(orgGlobalType, globalTypeIndependent);
						String isPublic = getRequest().getParameter("isPublic");
						if(StringUtils.isEmpty(isPublic) || isPublic.equalsIgnoreCase("true")){ // 公共
							orgGlobalType.setUserId(null);
						}
						globalTypeIndependentService.save(orgGlobalType);
					}catch(Exception ex){
						logger.error(ex.getMessage());
					}
				}else{
					String isPublic = getRequest().getParameter("isPublic"); // 是否公共
					//缺省0代表父节点
					String parentPath="0.";
					long level=1;
					if(globalTypeIndependent!=null && globalTypeIndependent.getParentId()!=null && globalTypeIndependent.getParentId()!=0){
						GlobalTypeIndependent parentType=globalTypeIndependentService.get(globalTypeIndependent.getParentId());
						if(parentType!=null){
							parentPath=parentType.getPath();
							level=parentType.getDepth()+1;
						}
					}
					globalTypeIndependent.setDepth(level);
					
					/*//set sn
					Integer sn=globalTypeIndependentService.getCountsByParentId(globalTypeIndependent.getParentId());
					long sn1=(sn+1).longValue();
					globalTypeIndependent.setSn((sn+1).longValue());*/
					if(StringUtils.isNotEmpty(isPublic) && isPublic.equalsIgnoreCase("false")){	// 似有 			
						AppUser user = ContextUtil.getCurrentUser();
						if(user != null){
							globalTypeIndependent.setUserId(user.getUserId());
						}
					} else { // 公共
						globalTypeIndependent.setUserId(null);
					}
					globalTypeIndependentService.save(globalTypeIndependent);
					
					globalTypeIndependent.setPath(parentPath+ globalTypeIndependent.getProTypeId()+"." ) ;
					if(StringUtils.isNotEmpty(isPublic) && isPublic.equalsIgnoreCase("false")){	// 似有 			
						AppUser user = ContextUtil.getCurrentUser();
						if(user != null){
							globalTypeIndependent.setUserId(user.getUserId());
						}
					} else { // 公共
						globalTypeIndependent.setUserId(null);
					}
					globalTypeIndependentService.save(globalTypeIndependent);
				}
				
				setJsonString("{success:true}");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据当前用户权限产生流程分类树
	 * @return
	 */
	public String flowTree(){
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'总分类',expanded:true,children:[");
		AppUser curUser = ContextUtil.getCurrentUser();
		List<GlobalTypeIndependent> typeList = null;
		if(curUser.isSupperManage()){//假如是超级管理员,则有全部权限
			typeList=globalTypeIndependentService.getByParentIdCatKey(new Long(0l),catKey);
		}else{
			typeList=globalTypeIndependentService.getByRightsCatKey(curUser,catKey);
		}
		Set<GlobalTypeIndependent> record = new HashSet<GlobalTypeIndependent>();
		for(GlobalTypeIndependent type:typeList){
			if(!record.contains(type)){
					record.add(type);
					buff.append("{id:'"+type.getProTypeId()).append("',text:'"+type.getTypeName()).append("',");
				    buff.append(getTypeByRights(type.getProTypeId(),catKey,record));
			}
		}
		if (!typeList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");
		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());		
		return SUCCESS;
	}
	public String getTypeJsonByNodeKey(){
		String nodeKey=this.getRequest().getParameter("nodeKey");
		GlobalTypeIndependent globalTypeIndependent=this.globalTypeIndependentService.getByNodeKey(nodeKey);
		
			
			 Long parentId=globalTypeIndependent.getProTypeId();
			 List<GlobalTypeIndependent> jsonList =this.globalTypeIndependentService.getByParentId(parentId);
			 
			StringBuffer buff = new StringBuffer("[");
			for (GlobalTypeIndependent glType : jsonList) {
				buff.append("['").append(glType.getNodeKey()).append("','").append(glType.getTypeName()).append("'],");
			}
		
				buff.deleteCharAt(buff.length() - 1);
		
			buff.append("]");
			setJsonString(buff.toString());
	
		
		return SUCCESS;
	}
	private String getTypeByRights(Long parentId,String catKey,Set<GlobalTypeIndependent> record){
		StringBuffer buff=new StringBuffer();
		List<GlobalTypeIndependent> typeList=globalTypeIndependentService.getByParentIdCatKey(parentId,catKey);
		
		if(typeList.size()==0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(GlobalTypeIndependent type:typeList){
				if(!record.contains(type)){
					record.add(type);
					buff.append("{id:'"+type.getProTypeId()).append("',text:'"+type.getTypeName()).append("',");
				    buff.append(getTypeByRights(type.getProTypeId(),catKey,record));
				}else{
				}
				
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
//删除分类
	public String delete(){
		if(proTypeId!=null){
			List<DictionaryIndependent> list=dictionaryIndependentService.getListByProTypeId(proTypeId);
			if(null!=list && list.size()>0){
				setJsonString("{success:false}");
			}else{
				GlobalTypeIndependent globalTypeIndependent=globalTypeIndependentService.get(proTypeId);
				globalTypeIndependent.setStatus("1");
				globalTypeIndependentService.save(globalTypeIndependent);
				setJsonString("{success:true}");
			}
		}
		return SUCCESS;
	}
}
