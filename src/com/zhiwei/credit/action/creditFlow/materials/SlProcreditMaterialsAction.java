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
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.util.TreeBeanUtil;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class SlProcreditMaterialsAction extends BaseAction{
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	@Resource
	private OurProcreditMaterialsEnterpriseService ourProcreditMaterialsEnterpriseService;

	private SlProcreditMaterials slProcreditMaterials;
	
	private Long proMaterialsId;
	private String projId;//项目ID
	private boolean show;//是否显示
	private String proMaterialIds;//Id拼接字符串
	private String businessType;
	private String operationType;
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	
	
	public Long getProMaterialsId() {
		return proMaterialsId;
	}

	public void setProMaterialsId(Long proMaterialsId) {
		this.proMaterialsId = proMaterialsId;
	}

	public SlProcreditMaterials getSlProcreditMaterials() {
		return slProcreditMaterials;
	}

	public void setSlProcreditMaterials(SlProcreditMaterials slProcreditMaterials) {
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

	/**
	 * 显示列表
	 */
	public String list(){
		
		String businessType="";
		businessType=this.getRequest().getParameter("businessType");
		List<SlProcreditMaterials> list= slProcreditMaterialsService.getByProjIdAndShow(projId,businessType,show);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		JSONSerializer json=new JSONSerializer();
    	json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"recieveTime","confirmTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public String listEnterprise(){
		
		
		List<SlProcreditMaterials> returnList=new ArrayList<SlProcreditMaterials>();
		
		List<OurProcreditMaterialsEnterprise> listtemp=ourProcreditMaterialsEnterpriseService.getListByParentId(0,null);
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		StringBuffer buff = new StringBuffer("{\"success\":true").append(",\"result\":");
		//for(OurProcreditMaterialsEnterprise areatemp:listtemp){
			       
			    // List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(),businessTypeKey, show);
			     List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjId(projId, businessTypeKey, show);
			     if(null!=listtemp1 && listtemp1.size()>0){   
			    	 for(SlProcreditMaterials  mt:listtemp1){
			    		 OurProcreditMaterialsEnterprise areatemp=ourProcreditMaterialsEnterpriseService.get(mt.getMaterialsId());
			    		 if(null!=areatemp){
			    			 OurProcreditMaterialsEnterprise o=ourProcreditMaterialsEnterpriseService.get(areatemp.getParentId().longValue());
			    			 mt.setParentName(o!=null?o.getMaterialsName():"");
			    		 }
			    		
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
		List<OurProcreditMaterialsEnterprise> listtemp=ourProcreditMaterialsEnterpriseService.getListByParentId(0,null);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		for(OurProcreditMaterialsEnterprise areatemp:listtemp){
			
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
			 if(null!=listtemp1 && listtemp1.size()>0)
			 {
				 treeBean.setId(Long.valueOf(areatemp.getMaterialsId()));
				 treeBean.setText(areatemp.getMaterialsName());
				 treeBean.setChecked(false);
				 treeBean.setCls("folder");
				 treeBean.setLeaf(false);
				 List<SlProcreditMaterials> children= slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
				 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
				 for(SlProcreditMaterials  t: children){
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
	
	public  String listTree(){
		String operationTypeKey=this.getRequest().getParameter("operationTypeKey");
		List<OurProcreditMaterialsEnterprise> listtemp=ourProcreditMaterialsEnterpriseService.getListByParentIdAndType(0, operationTypeKey);
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		String businessTypeKey="";
		businessTypeKey=this.getRequest().getParameter("businessType");
		for(OurProcreditMaterialsEnterprise areatemp:listtemp){
			
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 List<SlProcreditMaterials> listtemp1 = slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
			 if(null!=listtemp1 && listtemp1.size()>0)
			 {
				 treeBean.setId(Long.valueOf(areatemp.getMaterialsId()));
				 treeBean.setText(areatemp.getMaterialsName());
				 treeBean.setChecked(false);
				 treeBean.setCls("folder");
				 treeBean.setLeaf(false);
				 List<SlProcreditMaterials> children= slProcreditMaterialsService.getByProjIdAndParentId(projId, areatemp.getMaterialsId().intValue(), businessTypeKey, show);
				 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
				 for(SlProcreditMaterials  t: children){
					   
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
	
	
	public  String listTree1(){
		//String businessTypeKey=this.getRequest().getParameter("businessType");
		String businessType=this.getRequest().getParameter("businessType");
		String operationType=this.getRequest().getParameter("operationType");
		
		List<OurProcreditMaterialsEnterprise> listtemp=ourProcreditMaterialsEnterpriseService.getListByParentIdAndType(0, businessType+"_"+operationType);
		List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
		for(OurProcreditMaterialsEnterprise areatemp:listtemp){
			TreeBeanUtil treeBean=new TreeBeanUtil();
			 treeBean.setId(areatemp.getMaterialsId());
			 treeBean.setText(areatemp.getMaterialsName());
			 treeBean.setChecked(false);
			 treeBean.setCls("folder");
			 treeBean.setLeaf(false);
			 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
			 List<OurProcreditMaterialsEnterprise> listtemp1 = ourProcreditMaterialsEnterpriseService.getListByParentIdAndType( areatemp.getMaterialsId().intValue(), businessType+"_"+operationType);
			 int i=0;
			 if(null!=listtemp1 && listtemp1.size()>0){
				 for(OurProcreditMaterialsEnterprise m:listtemp1){
					 List<SlProcreditMaterials> children= slProcreditMaterialsService.getByProjIdAndOperationType(projId,m.getMaterialsId(), businessType,operationType);
					 if(children==null||children.size()==0){
						 TreeBeanUtil c= new TreeBeanUtil();
						 c.setId(m.getMaterialsId());
						 c.setText(m.getMaterialsName());
						 c.setChecked(false);
						 c.setCls("file");
						 c.setLeaf(true);
						 set.add(c);
						 i++;
					 }
				 }
				 if(i!=0){
					 treeBean.setChildren(set);
					 blist.add(treeBean);
				 }
				
			 } 
		}
		 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		return SUCCESS;
	}
	
	public  String listExitTree(){
		try{
			String businessType=this.getRequest().getParameter("businessType");
			String operationType=this.getRequest().getParameter("operationType");
			 List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
			 List<SlProcreditMaterials> list=slProcreditMaterialsService.listByMaterialsIdGroupById(projId, businessType,operationType);
			 for(SlProcreditMaterials m:list){
				 	 OurProcreditMaterialsEnterprise setting=ourProcreditMaterialsEnterpriseService.get(Long.valueOf(m.getParentId()));
					 TreeBeanUtil treeBean=new TreeBeanUtil();
					 treeBean.setId(setting.getMaterialsId());
					 treeBean.setText(setting.getMaterialsName());
					 treeBean.setChecked(false);
					 treeBean.setCls("folder");
					 treeBean.setLeaf(false);
					 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
					 List<SlProcreditMaterials> mlist=slProcreditMaterialsService.listByMaterialsIdAndOperationTypeKey(projId, businessType,operationType, Long.valueOf(m.getParentId()));
					 for(SlProcreditMaterials s:mlist){
							 TreeBeanUtil c= new TreeBeanUtil();
							 c.setId(s.getMaterialsId());
							 c.setText(s.getMaterialsName());
							 c.setChecked(false);
							 c.setCls("file");
							 c.setLeaf(true);
							 set.add(c);
						 
					 }
					 treeBean.setChildren(set);
					 blist.add(treeBean); 
				 }
			
			 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		 }catch(Exception e){
			 logger.error("PlArchivesMaterialsAction:"+e.getMessage());
			 e.printStackTrace();
		 }
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
				slProcreditMaterialsService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	public String updateDataNum()
	{        
		    SlProcreditMaterials materials=this.slProcreditMaterialsService.get(slProcreditMaterials.getProMaterialsId());
		    materials.setDatumNums(slProcreditMaterials.getDatumNums());
		    this.slProcreditMaterialsService.merge(materials);
		    jsonString="{success:true}";
		    setJsonString(jsonString);
			return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlProcreditMaterials slProcreditMaterials=slProcreditMaterialsService.get(proMaterialsId);
		
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
			slProcreditMaterialsService.save(slProcreditMaterials);
		}else{
			SlProcreditMaterials orgSlProcreditMaterials=slProcreditMaterialsService.get(slProcreditMaterials.getProMaterialsId());
			try{
				BeanUtil.copyNotNullProperties(orgSlProcreditMaterials, slProcreditMaterials);
				slProcreditMaterialsService.save(orgSlProcreditMaterials);
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
					SlProcreditMaterials s = null;
					if("Guarantee".equals(businessType)){
						s = slProcreditMaterialsService.get(Long.valueOf(proArrs[i]));
					}else if("LeaseFinance".equals(businessType)){
						s = slProcreditMaterialsService.get(Long.valueOf(proArrs[i]));
					}else if("SmallLoan".equals(businessType)){
						s = slProcreditMaterialsService.get(Long.valueOf(proArrs[i]));
					}
					else{
						s = slProcreditMaterialsService.getSLMaterials(projId, businessType, Long.valueOf(proArrs[i]));
					}
					if(null!=s){
						s.setIsShow(Boolean.valueOf(show));
						slProcreditMaterialsService.merge(s);
					}else{
			/*			SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
						slProcreditMaterials.setProjId(projId);
						slProcreditMaterials.setMaterialsId(o.getMaterialsId());
						slProcreditMaterials.setMaterialsName(o.getMaterialsName());
						slProcreditMaterials.setIsShow(show);
						slProcreditMaterials.setDatumNums(0);
						slProcreditMaterials.setParentId(null);
						slProcreditMaterials.setBusinessTypeKey(businessType);
						slProcreditMaterials.setOperationTypeKey(o.getOperationTypeKey());
						slProcreditMaterialsService.save(slProcreditMaterials);*/
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
				SlProcreditMaterials orgSlProcreditMaterials=slProcreditMaterialsService.get(java.lang.Long.valueOf(proArrs[i]));
				orgSlProcreditMaterials.setIsShow(show);
				slProcreditMaterialsService.merge(orgSlProcreditMaterials);
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
			SlProcreditMaterials materials=this.slProcreditMaterialsService.get(Long.valueOf(sid));
			materials.setRemark(remark);
			this.slProcreditMaterialsService.merge(materials);
		}catch (Exception e) {
		    e.printStackTrace();
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
	public String updateAfter(){
		 
		SlProcreditMaterials materials=this.slProcreditMaterialsService.get(slProcreditMaterials.getProMaterialsId());
		materials.setDatumNums(slProcreditMaterials.getDatumNums());
		materials.setRemark(slProcreditMaterials.getRemark());
		materials.setIsReceive(slProcreditMaterials.getIsReceive());
		materials.setIsPigeonhole(slProcreditMaterials.getIsPigeonhole());//add by gao
		materials.setConfirmTime(slProcreditMaterials.getConfirmTime());//add by gao
		materials.setIsArchiveConfirm(slProcreditMaterials.getIsArchiveConfirm());
		materials.setDatumNumsOfLine(slProcreditMaterials.getDatumNumsOfLine());
		this.slProcreditMaterialsService.merge(materials);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String updateMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					OurProcreditMaterialsEnterprise o = ourProcreditMaterialsEnterpriseService.get(Long.valueOf(proArrs[i]));
					SlProcreditMaterials add =new SlProcreditMaterials();
					add.setProjId(projId);
					add.setMaterialsId(o.getMaterialsId());
					add.setMaterialsName(o.getMaterialsName());
					add.setIsShow(true);
					add.setDatumNums(0);
					add.setXxnums(0);
					add.setParentId(o.getParentId());
					add.setBusinessTypeKey(businessType);
					add.setOperationTypeKey(operationType);
					/*add.setMaterialsType(o.getIsPublic());*/
					slProcreditMaterialsService.save(add);
					
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
 
 public String deleteMaterials(){
	 try{
		 String materialIds = this.getRequest().getParameter("materialsIds");
		if(null!=materialIds && !"".equals(materialIds)){
			String[] proArrs = materialIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				List<SlProcreditMaterials> list=slProcreditMaterialsService.getByProjIdAndOperationType(projId, Long.valueOf(proArrs[i]), businessType,operationType);
				for(SlProcreditMaterials m:list){
					slProcreditMaterialsService.remove(m);
				}
			}
		}
		jsonString="{success:true}";
	 }catch(Exception e){
		 jsonString="{success:false}";
		 logger.error("BpMortgageMaterials:"+e.getMessage());
		 e.printStackTrace();
	 }
	 return SUCCESS;
 }
}
