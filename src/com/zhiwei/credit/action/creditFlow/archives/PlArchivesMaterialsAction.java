package com.zhiwei.credit.action.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;
import com.zhiwei.credit.service.creditFlow.archives.OurArchivesMaterialsService;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesMaterialsService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class PlArchivesMaterialsAction extends BaseAction{
	@Resource
	private PlArchivesMaterialsService plArchivesMaterialsService;
	@Resource
	private OurArchivesMaterialsService ourArchivesMaterialsService;
	private PlArchivesMaterials plArchivesMaterials;
	
	private Long proMaterialsId;
	private String materialsIds;//Id拼接字符串
	private String businessType;
	private String operationType;
	private Long projectId;

	public Long getProMaterialsId() {
		return proMaterialsId;
	}

	public void setProMaterialsId(Long proMaterialsId) {
		this.proMaterialsId = proMaterialsId;
	}


	public String getMaterialsIds() {
		return materialsIds;
	}

	public void setMaterialsIds(String materialsIds) {
		this.materialsIds = materialsIds;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public PlArchivesMaterials getPlArchivesMaterials() {
		return plArchivesMaterials;
	}

	public void setPlArchivesMaterials(PlArchivesMaterials plArchivesMaterials) {
		this.plArchivesMaterials = plArchivesMaterials;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		String bidPlanId = getRequest().getParameter("bidPlanId");
		List<PlArchivesMaterials> list = new ArrayList<PlArchivesMaterials>();
		if(null!=bidPlanId&&!"".equals(bidPlanId)&&!"undefined".equals(bidPlanId)){
			list = plArchivesMaterialsService.listByBidPlanId(Long.parseLong(bidPlanId));
		}else{
			list= plArchivesMaterialsService.listbyProjectId(projectId, businessType);
		}
		Type type=new TypeToken<List<PlArchivesMaterials>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list !=null?list.size():0).append(",result:");
		
		/*Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");*/
		JSONSerializer json=new JSONSerializer();
    	json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"recieveTime","pigeonholeTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		if(null!=materialsIds&&!"".equals(materialsIds)){
			String[] proArrs = materialsIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				plArchivesMaterialsService.remove(Long.valueOf(proArrs[i]));
			}
		}
	
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		PlArchivesMaterials plArchivesMaterials=plArchivesMaterialsService.get(proMaterialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(plArchivesMaterials));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(plArchivesMaterials.getProMaterialsId()==null){
			plArchivesMaterialsService.save(plArchivesMaterials);
		}else{
			PlArchivesMaterials orgPlArchivesMaterials=plArchivesMaterialsService.get(plArchivesMaterials.getProMaterialsId());
			try{
				BeanUtil.copyNotNullProperties(orgPlArchivesMaterials, plArchivesMaterials);
				plArchivesMaterialsService.save(orgPlArchivesMaterials);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String update(){
		String bidPlanId = getRequest().getParameter("bidPlanId");
		if(null!=materialsIds&&!"".equals(materialsIds)){
			String[] proArrs = materialsIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				OurArchivesMaterials ourArchivesMaterials=ourArchivesMaterialsService.get(java.lang.Long.valueOf(proArrs[i]));
				PlArchivesMaterials plArchivesMaterials = new PlArchivesMaterials();
				plArchivesMaterials.setProjId(projectId);
				plArchivesMaterials.setMaterialsId(ourArchivesMaterials.getMaterialsId());
				plArchivesMaterials.setMaterialsName(ourArchivesMaterials.getMaterialsName());
				plArchivesMaterials.setIsShow(false);
				plArchivesMaterials.setDatumNums(0);
				plArchivesMaterials.setXxnums(0);
				if(null==bidPlanId||"".equals(bidPlanId)||"undefined".equals(bidPlanId)){
					plArchivesMaterials.setBidPlanId(null);
				}else{
					plArchivesMaterials.setBidPlanId(Long.parseLong(bidPlanId));
				}
				
				plArchivesMaterials.setBusinessType(businessType);
				plArchivesMaterials.setOperationType(operationType);
				plArchivesMaterials.setMaterialsType(ourArchivesMaterials.getIsPublic());
				plArchivesMaterialsService.save(plArchivesMaterials);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
