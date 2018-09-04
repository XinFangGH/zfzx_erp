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
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsDetail;
import com.zhiwei.credit.model.creditFlow.materials.GlobalGuaranteeMaterialsExt;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsDetailService;
import com.zhiwei.credit.service.creditFlow.materials.GlobalGuaranteeMaterialsExtService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.util.TreeBeanUtil;
/**
 * 
 * @author 
 *
 */
public class GlobalGuaranteeMaterialsExtAction extends BaseAction{
	@Resource
	private GlobalGuaranteeMaterialsExtService globalGuaranteeMaterialsExtService;
	@Resource
	private GlobalGuaranteeMaterialsDetailService globalGuaranteeMaterialsDetailService;
	@Resource
	private GlobalTypeService globalTypeService;
	
	private GlobalGuaranteeMaterialsExt ourProcreditMaterialsEnterprise;
	
	private Long materialsId;
	private Long projectId;
	private String businessType;
	
	
	

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

	public Long getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(Long materialsId) {
		this.materialsId = materialsId;
	}

	public GlobalGuaranteeMaterialsExt getOurProcreditMaterialsEnterprise() {
		return ourProcreditMaterialsEnterprise;
	}

	public void setOurProcreditMaterialsEnterprise(
			GlobalGuaranteeMaterialsExt ourProcreditMaterialsEnterprise) {
		this.ourProcreditMaterialsEnterprise = ourProcreditMaterialsEnterprise;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		try
		{    
			//List<GlobalGuaranteeMaterialsExt>  list = new ArrayList<GlobalGuaranteeMaterialsExt>();
			String node = this.getRequest().getParameter("node");
			if(null==node)
				node="0";
			List<GlobalGuaranteeMaterialsExt> listTemp = this.globalGuaranteeMaterialsExtService.getListByParentId(Integer.valueOf(node));
			for(GlobalGuaranteeMaterialsExt om:listTemp){
				String operationTypeName = "";
				if(null!=om.getOperationTypeKey() && !om.getOperationTypeKey().equals("")){
				  operationTypeName = this.globalTypeService.getByNodeKey(om.getOperationTypeKey()).get(0).getTypeName();
					//operationTypeName = globalTypeService.getByNodeKeyCatKey(om.getOperationTypeKey(), "FLOW");
				}
				om.setOperationTypeName(operationTypeName);
				//list.add(om);
			}
			StringBuffer buff = new StringBuffer("{success:true").append(",result:");
			Gson gson = new Gson();
			buff.append(gson.toJson(listTemp));
			buff.append("}");
			jsonString=buff.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String listByOperationTypeKey(){
		
		try
		{    
			//List<GlobalGuaranteeMaterialsExt>  list = new ArrayList<GlobalGuaranteeMaterialsExt>();
			String node = this.getRequest().getParameter("node");
			String operationTypeKey=this.getRequest().getParameter("operationTypeKey");
			if(null==node)
				node="0";
			//List<OurProcreditMaterialsEnterprise> listTemp = this.globalGuaranteeMaterialsExtService.getListByParentId(Integer.valueOf(node));
			List<GlobalGuaranteeMaterialsExt> listTemp = this.globalGuaranteeMaterialsExtService.getListByParentIdAndType(Integer.valueOf(node), operationTypeKey);
			for(GlobalGuaranteeMaterialsExt om:listTemp){
				String operationTypeName = "";
				if(null!=om.getOperationTypeKey() && !om.getOperationTypeKey().equals("")){
				  operationTypeName = this.globalTypeService.getByNodeKey(om.getOperationTypeKey()).get(0).getTypeName();
					//operationTypeName = globalTypeService.getByNodeKeyCatKey(om.getOperationTypeKey(), "FLOW");
				}
				om.setOperationTypeName(operationTypeName);
				//list.add(om);
			}
			StringBuffer buff = new StringBuffer("{success:true").append(",result:");
			Gson gson = new Gson();
			buff.append(gson.toJson(listTemp));
			buff.append("}");
			jsonString=buff.toString();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	public String listChildren(){
		
		String node=this.getRequest().getParameter("node");
		List<GlobalGuaranteeMaterialsExt> listTemp=this.globalGuaranteeMaterialsExtService.getListByParentId(Integer.valueOf(node));
		List<GlobalGuaranteeMaterialsExt> list =new ArrayList<GlobalGuaranteeMaterialsExt>();
		for(GlobalGuaranteeMaterialsExt cme:listTemp){
			       
			GlobalGuaranteeMaterialsExt pcme=this.globalGuaranteeMaterialsExtService.get(Long.valueOf(cme.getParentId()));
			cme.setParentText(pcme.getMaterialsName());
			list.add(cme);
		}
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	public String listTreeByOperationTypeKey(){
		String operationTypeKey=this.getRequest().getParameter("operationTypeKey");
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		List<GlobalGuaranteeMaterialsExt> listTemp = this.globalGuaranteeMaterialsExtService.getListByParentIdAndType(0, operationTypeKey);
		for(GlobalGuaranteeMaterialsExt p :listTemp){
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 treeBean.setId(Long.valueOf(p.getMaterialsId()));
			 treeBean.setText(p.getMaterialsName());
			 treeBean.setChecked(false);
			 treeBean.setCls("folder");
			 treeBean.setLeaf(false);
			 List<GlobalGuaranteeMaterialsExt> list1 = this.globalGuaranteeMaterialsExtService.getListByParentIdAndType(p.getMaterialsId().intValue(), operationTypeKey);
			 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
			 for(GlobalGuaranteeMaterialsExt temp : list1){
				 TreeBeanUtil c= new TreeBeanUtil();
				 c.setId(Long.valueOf(temp.getMaterialsId()));
				 c.setText(temp.getMaterialsName());
				 c.setChecked(false);
				 c.setCls("file");
				 c.setLeaf(true);
				 set.add(c);
			 }
			 treeBean.setChildren(set);
			 list.add(treeBean);
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
				globalGuaranteeMaterialsExtService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	public String delete(){
		   
		  GlobalGuaranteeMaterialsExt ome= this.globalGuaranteeMaterialsExtService.get(materialsId);
		  if(null!=ome){
			  if(ome.getLeaf()==0){ //父节点
				  
				    List<GlobalGuaranteeMaterialsExt>  list=this.globalGuaranteeMaterialsExtService.getListByParentId(ome.getMaterialsId().intValue());
				    for(GlobalGuaranteeMaterialsExt temp:list){
				    	
				    	this.globalGuaranteeMaterialsExtService.remove(temp);
				    }
				    this.globalGuaranteeMaterialsExtService.remove(ome);
			  }
			  else if(ome.getLeaf()==1){
				  this.globalGuaranteeMaterialsExtService.remove(ome);
			  }
		  }
		  return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		GlobalGuaranteeMaterialsExt globalGuaranteeMaterialsExt=globalGuaranteeMaterialsExtService.get(materialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{success:true,data:");
		String parentText="";
		if(null!=globalGuaranteeMaterialsExt){
			GlobalGuaranteeMaterialsExt temp=this.globalGuaranteeMaterialsExtService.get(Long.valueOf(globalGuaranteeMaterialsExt.getParentId()));
			if(null!=temp){
				parentText=temp.getMaterialsName();
			}
			globalGuaranteeMaterialsExt.setParentText(parentText);
			sb.append(gson.toJson(globalGuaranteeMaterialsExt));
		}
		else{
			
			sb.append("null");
		}
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		/*String op = globalGuaranteeMaterialsExt.getOperationTypeKey();
		if(op!=null&&!"".equals(op)){
			if(op.indexOf("_")!=-1){
				String[] proArrs = op.split("_");
				if(proArrs.length>1){
					globalGuaranteeMaterialsExt.setOperationTypeKey(proArrs[proArrs.length-1]);
					System.out.println(globalGuaranteeMaterialsExt.getOperationTypeKey());
				}
			}
		}*/
	    this.globalGuaranteeMaterialsExtService.save(ourProcreditMaterialsEnterprise);
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	public String updateMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String show=this.getRequest().getParameter("show");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					GlobalGuaranteeMaterialsExt o = globalGuaranteeMaterialsExtService.get(Long.valueOf(proArrs[i]));
					GlobalGuaranteeMaterialsDetail s=null;
					s = globalGuaranteeMaterialsDetailService.get(Long.valueOf(proArrs[i]));
					
					if(null!=s){
						s.setIsShow(Boolean.valueOf(show));
						globalGuaranteeMaterialsDetailService.merge(s);
					}else{
						GlobalGuaranteeMaterialsDetail slProcreditMaterials = new GlobalGuaranteeMaterialsDetail();
						slProcreditMaterials.setProjId(String.valueOf(projectId));
						slProcreditMaterials.setMaterialsId(o.getMaterialsId());
						slProcreditMaterials.setMaterialsName(o.getMaterialsName());
						slProcreditMaterials.setIsShow(true);
						slProcreditMaterials.setDatumNums(0);
						slProcreditMaterials.setParentId(o.getParentId());
						slProcreditMaterials.setBusinessTypeKey(businessType);
						slProcreditMaterials.setOperationTypeKey(o.getOperationTypeKey());
						globalGuaranteeMaterialsDetailService.save(slProcreditMaterials);
					}
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
