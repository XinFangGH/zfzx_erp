package com.zhiwei.credit.action.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsExt;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsDetailService;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsExtService;
import com.zhiwei.credit.util.TreeBeanUtil;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * GlobalGuaranteeMaterialsExtService
 * @author 
 *
 */
public class GlobalGuaranteeMaterialsDetailAction extends BaseAction{
	@Resource
	private GlobalGuaranteeMaterialsDetailService globalGuaranteeMaterialsDetailService;
	@Resource
	private GlobalGuaranteeMaterialsExtService globalGuaranteeMaterialsExtService;

	private GlobalGuaranteeMaterialsDetail slProcreditMaterials;
	
	private Long proMaterialsId;
	private String projId;//项目ID
	private boolean show;//是否显示
	private String proMaterialIds;//Id拼接字符串

	public Long getProMaterialsId() {
		return proMaterialsId;
	}

	public void setProMaterialsId(Long proMaterialsId) {
		this.proMaterialsId = proMaterialsId;
	}

	public GlobalGuaranteeMaterialsDetail getGlobalGuaranteeMaterialsDetail() {
		return slProcreditMaterials;
	}

	public void setGlobalGuaranteeMaterialsDetail(GlobalGuaranteeMaterialsDetail slProcreditMaterials) {
		this.slProcreditMaterials = slProcreditMaterials;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public String getProMaterialIds() {
		return proMaterialIds;
	}

	public void setProMaterialIds(String proMaterialIds) {
		this.proMaterialIds = proMaterialIds;
	}
	
	

	public GlobalGuaranteeMaterialsDetail getSlProcreditMaterials() {
		return slProcreditMaterials;
	}

	public void setSlProcreditMaterials(
			GlobalGuaranteeMaterialsDetail slProcreditMaterials) {
		this.slProcreditMaterials = slProcreditMaterials;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String businessType="";
		businessType=this.getRequest().getParameter("businessType");
		List<GlobalGuaranteeMaterialsDetail> list= globalGuaranteeMaterialsDetailService.getByProjIdAndShow(projId,businessType,show);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		JSONSerializer json=new JSONSerializer();
    	json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"recieveTime","confirmTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String listEnterprise(){
		
		
		List<GlobalGuaranteeMaterialsDetail> returnList=new ArrayList<GlobalGuaranteeMaterialsDetail>();
		
		List<GlobalGuaranteeMaterialsExt> listtemp=globalGuaranteeMaterialsExtService.getListByParentId(0);
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		//for(GlobalGuaranteeMaterialsExt areatemp:listtemp){
			       
			    // List<GlobalGuaranteeMaterialsDetail> listtemp1 = globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(),businessTypeKey, show);
			     List<GlobalGuaranteeMaterialsDetail> listtemp1 = globalGuaranteeMaterialsDetailService.getByProjId(projId, businessTypeKey, show);
			     if(null!=listtemp1 && listtemp1.size()>0)
			     {   
			    	 for(GlobalGuaranteeMaterialsDetail  mt:listtemp1){
			    		 GlobalGuaranteeMaterialsExt areatemp=globalGuaranteeMaterialsExtService.get(mt.getMaterialsId());
			    		 GlobalGuaranteeMaterialsExt o=globalGuaranteeMaterialsExtService.get(areatemp.getParentId().longValue());
			    		 mt.setParentName(o.getMaterialsName());
			    		 returnList.add(mt);
			    	 }
			     }
		//}
		Gson gson=new Gson();
		buff.append(gson.toJson(returnList));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}              
	public  String listTreeEnterprise(){
		List<GlobalGuaranteeMaterialsExt> listtemp=globalGuaranteeMaterialsExtService.getListByParentId(0);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		for(GlobalGuaranteeMaterialsExt areatemp:listtemp){
			
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 List<GlobalGuaranteeMaterialsDetail> listtemp1 = globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
			 if(null!=listtemp1 && listtemp1.size()>0)
			 {
				 treeBean.setId(Long.valueOf(areatemp.getMaterialsId()));
				 treeBean.setText(areatemp.getMaterialsName());
				 treeBean.setChecked(false);
				 treeBean.setCls("folder");
				 treeBean.setLeaf(false);
				 List<GlobalGuaranteeMaterialsDetail> children= globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
				 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
				 for(GlobalGuaranteeMaterialsDetail  t: children){
					 TreeBeanUtil c= new TreeBeanUtil();
					 c.setId(Long.valueOf(t.getProMaterialsId()));
					 c.setText(t.getMaterialsName());
					 c.setChecked(false);
					 c.setCls("file");
					 c.setLeaf(true);
					 set.add(c);
				 }
				 treeBean.setChildren(set);
				 list.add(treeBean);
			 } 
		}
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=gson.toJson(list).toString();
		return SUCCESS;
	}
	/*public  String listTreeGlobalGuarantee(){
		List<GlobalGuaranteeMaterials> listtemp=globalGuaranteeMaterialsService.getListByParentId(0);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		for(GlobalGuaranteeMaterials areatemp:listtemp){
			
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 List<GlobalGuaranteeMaterialsDetail> listtemp1 = globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
			 if(null!=listtemp1 && listtemp1.size()>0)
			 {
				 treeBean.setId(Long.valueOf(areatemp.getMaterialsId()));
				 treeBean.setText(areatemp.getMaterialsName());
				 treeBean.setChecked(false);
				 treeBean.setCls("folder");
				 treeBean.setLeaf(false);
				 List<GlobalGuaranteeMaterialsDetail> children= globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
				 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
				 for(GlobalGuaranteeMaterialsDetail  t: children){
					   
					 TreeBeanUtil c= new TreeBeanUtil();
					 c.setId(Long.valueOf(t.getProMaterialsId()));
					 c.setText(t.getMaterialsName());
					 c.setChecked(false);
					 c.setCls("file");
					 c.setLeaf(true);
					 set.add(c);
				 }
				 treeBean.setChildren(set);
				 list.add(treeBean);
			 } 
		}
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=gson.toJson(list).toString();
		return SUCCESS;
	}*/
	
	public  String listTree(){
		String operationTypeKey=this.getRequest().getParameter("operationTypeKey");
		List<GlobalGuaranteeMaterialsExt> listtemp=globalGuaranteeMaterialsExtService.getListByParentIdAndType(0, operationTypeKey);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		for(GlobalGuaranteeMaterialsExt areatemp:listtemp){
			
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 List<GlobalGuaranteeMaterialsDetail> listtemp1 = globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
			 if(null!=listtemp1 && listtemp1.size()>0)
			 {
				 treeBean.setId(Long.valueOf(areatemp.getMaterialsId()));
				 treeBean.setText(areatemp.getMaterialsName());
				 treeBean.setChecked(false);
				 treeBean.setCls("folder");
				 treeBean.setLeaf(false);
				 List<GlobalGuaranteeMaterialsDetail> children= globalGuaranteeMaterialsDetailService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
				 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
				 for(GlobalGuaranteeMaterialsDetail  t: children){
					   
					 TreeBeanUtil c= new TreeBeanUtil();
					 c.setId(Long.valueOf(t.getProMaterialsId()));
					 c.setText(t.getMaterialsName());
					 c.setChecked(false);
					 c.setCls("file");
					 c.setLeaf(true);
					 set.add(c);
				 }
				 treeBean.setChildren(set);
				 list.add(treeBean);
			 } 
		}
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=gson.toJson(list).toString();
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				globalGuaranteeMaterialsDetailService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String updateDataNum()
	{        
		    GlobalGuaranteeMaterialsDetail materials=this.globalGuaranteeMaterialsDetailService.get(slProcreditMaterials.getProMaterialsId());
		    materials.setDatumNums(slProcreditMaterials.getDatumNums());
		    this.globalGuaranteeMaterialsDetailService.merge(materials);
		    jsonString="{success:true}";
		    setJsonString(jsonString);
			return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlobalGuaranteeMaterialsDetail slProcreditMaterials=globalGuaranteeMaterialsDetailService.get(proMaterialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slProcreditMaterials));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slProcreditMaterials.getProMaterialsId()==null){
			globalGuaranteeMaterialsDetailService.save(slProcreditMaterials);
		}else{
			GlobalGuaranteeMaterialsDetail orgGlobalGuaranteeMaterialsDetail=globalGuaranteeMaterialsDetailService.get(slProcreditMaterials.getProMaterialsId());
			try{
				BeanUtil.copyNotNullProperties(orgGlobalGuaranteeMaterialsDetail, slProcreditMaterials);
				globalGuaranteeMaterialsDetailService.save(orgGlobalGuaranteeMaterialsDetail);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String update(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String businessType = this.getRequest().getParameter("businessType");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					GlobalGuaranteeMaterialsDetail o = globalGuaranteeMaterialsDetailService.get(Long.valueOf(proArrs[i]));
					GlobalGuaranteeMaterialsDetail s = null;
					if("Guarantee".equals(businessType)){
						s = globalGuaranteeMaterialsDetailService.get(Long.valueOf(proArrs[i]));
					}else if("LeaseFinance".equals(businessType)){
						s = globalGuaranteeMaterialsDetailService.get(Long.valueOf(proArrs[i]));
					}/*else if("SmallLoan".equals(businessType)){
						
					}*/
					else{
						s = globalGuaranteeMaterialsDetailService.getSLMaterials(projId, businessType, Long.valueOf(proArrs[i]));
					}
					if(null!=s){
						s.setIsShow(Boolean.valueOf(show));
						globalGuaranteeMaterialsDetailService.merge(s);
					}else{
						GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
						slProcreditMaterials.setProjId(projId);
						slProcreditMaterials.setMaterialsId(o.getMaterialsId());
						slProcreditMaterials.setMaterialsName(o.getMaterialsName());
						slProcreditMaterials.setIsShow(show);
						slProcreditMaterials.setDatumNums(0);
						slProcreditMaterials.setParentId(null);
						slProcreditMaterials.setBusinessTypeKey(businessType);
						slProcreditMaterials.setOperationTypeKey(o.getOperationTypeKey());
						globalGuaranteeMaterialsDetailService.save(slProcreditMaterials);
					}
				}
			}
			setJsonString("{success:true}");
		}catch(Exception e){
			setJsonString("{success:false}");
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String updateShow(){
		if(null!=proMaterialIds&&!"".equals(proMaterialIds)){
			String[] proArrs = proMaterialIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				GlobalGuaranteeMaterialsDetail orgGlobalGuaranteeMaterialsDetail=globalGuaranteeMaterialsDetailService.get(java.lang.Long.valueOf(proArrs[i]));
				orgGlobalGuaranteeMaterialsDetail.setIsShow(show);
				globalGuaranteeMaterialsDetailService.merge(orgGlobalGuaranteeMaterialsDetail);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String  updateR()
	{
		String  sid=this.getRequest().getParameter("sid");
		String remark=this.getRequest().getParameter("remark");
		
		try
		{
			GlobalGuaranteeMaterialsDetail materials=this.globalGuaranteeMaterialsDetailService.get(Long.valueOf(sid));
			materials.setRemark(remark);
			this.globalGuaranteeMaterialsDetailService.merge(materials);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String updateAfter(){
		 
		GlobalGuaranteeMaterialsDetail materials=this.globalGuaranteeMaterialsDetailService.get(slProcreditMaterials.getProMaterialsId());
		materials.setDatumNums(slProcreditMaterials.getDatumNums());
		materials.setRemark(slProcreditMaterials.getRemark());
		materials.setIsReceive(slProcreditMaterials.getIsReceive());
		materials.setIsArchiveConfirm(slProcreditMaterials.getIsArchiveConfirm());
		materials.setDatumNumsOfLine(slProcreditMaterials.getDatumNumsOfLine());
		this.globalGuaranteeMaterialsDetailService.merge(materials);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
