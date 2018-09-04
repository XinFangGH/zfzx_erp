package com.zhiwei.credit.action.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;


import com.zhiwei.credit.model.creditFlow.archives.OurArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.archives.PlArchivesMaterials;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;

import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.creditFlow.archives.OurArchivesMaterialsService;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesMaterialsService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.util.TreeBeanUtil;
/**
 * 
 * @author 
 *
 */
public class OurArchivesMaterialsAction extends BaseAction{
	@Resource
	private OurArchivesMaterialsService ourArchivesMaterialsService;
	@Resource
	private PlArchivesMaterialsService plArchivesMaterialsService;
	private OurArchivesMaterials ourArchivesMaterials;

	@Resource
	private GlobalTypeService globalTypeService;
	
	private String operationTypeKey;
	private String businessType;
	private Long projectId;
	private Long materialsId;

	public Long getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(Long materialsId) {
		this.materialsId = materialsId;
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

	public OurArchivesMaterials getOurArchivesMaterials() {
		return ourArchivesMaterials;
	}

	public void setOurArchivesMaterials(OurArchivesMaterials ourArchivesMaterials) {
		this.ourArchivesMaterials = ourArchivesMaterials;
	}

	public String getOperationTypeKey() {
		return operationTypeKey;
	}

	public void setOperationTypeKey(String operationTypeKey) {
		this.operationTypeKey = operationTypeKey;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		// List<OurArchivesMaterials> list=
		// ourArchivesMaterialsService.getAll(filter);
		if (businessType != null && !"".equals(businessType)) {
			PagingBean pb=new PagingBean(start,limit);
			List<OurArchivesMaterials> list = ourArchivesMaterialsService.getByBusinessType(businessType,pb);
			/*for (OurArchivesMaterials om : list) {
				om.setOperationTypeName(this.globalTypeService.getByNodeKey(
						om.getOperationTypeKey()).get(0).getTypeName());
			}*/
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(pb.getTotalItems()).append(",result:");
			Gson gson = new Gson();
			buff.append(gson.toJson(list));
			buff.append("}");
			jsonString = buff.toString();
		}
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "删除归档材料")
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				ourArchivesMaterialsService.remove(new Long(id));
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
		OurArchivesMaterials ourArchivesMaterials=ourArchivesMaterialsService.get(materialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(ourArchivesMaterials));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	@LogResource(description = "添加或修改归档材料清单")
	public String save(){
		if(ourArchivesMaterials.getBusinessTypeGlobalId()!=null){
			GlobalType globalType = globalTypeService.get(Long.valueOf(ourArchivesMaterials.getBusinessTypeGlobalId()));
			String businessTypeKey = "";
			if(globalType!=null){
				if(globalType.getNodeKey()!=null&&globalType.getNodeKey().indexOf("_")!=-1){
					String[] proArrs = globalType.getNodeKey().split("_");
					businessTypeKey = proArrs[0];
				}else{
					businessTypeKey = globalType.getNodeKey();
				}
				ourArchivesMaterials.setBusinessTypeName(globalType.getTypeName());
				ourArchivesMaterials.setBusinessTypeKey(businessTypeKey);
				
				if(ourArchivesMaterials.getOperationTypeGlobalId()!=null){
					GlobalType global = globalTypeService.get(Long.valueOf(ourArchivesMaterials.getOperationTypeGlobalId()));
					if(global!=null){
						ourArchivesMaterials.setOperationTypeKey(global.getNodeKey());
					}
				}
			}
		}
		ourArchivesMaterialsService.save(ourArchivesMaterials);
		setJsonString("{success:true}");
		return SUCCESS;
		/*if(ourArchivesMaterials.getMaterialsId()==null){
			ourArchivesMaterialsService.save(ourArchivesMaterials);
		}else{
			OurArchivesMaterials orgOurArchivesMaterials=ourArchivesMaterialsService.get(ourArchivesMaterials.getMaterialsId());
			try{
				BeanUtil.copyNotNullProperties(orgOurArchivesMaterials, ourArchivesMaterials);
				ourArchivesMaterialsService.save(orgOurArchivesMaterials);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;*/
	}
	
	public String getbyoperationTypeKey(){
		if(operationTypeKey!=null&&!"".equals(operationTypeKey)){
			List<OurArchivesMaterials> list= ourArchivesMaterialsService.getbyoperationTypeKey(operationTypeKey);
			List<OurArchivesMaterials> retrunList= new ArrayList<OurArchivesMaterials>();
			List<PlArchivesMaterials> listti=plArchivesMaterialsService.listbyProjectId(projectId, businessType);
			for(OurArchivesMaterials om:list){
				int i=0;
				for(PlArchivesMaterials a:listti){
					if(a.getMaterialsId().equals(om.getMaterialsId())){
						i++;
					}
				}
				if(i==0){
					om.setOperationTypeName(this.globalTypeService.getByNodeKey(om.getOperationTypeKey()).get(0).getTypeName());
					retrunList.add(om);
				}
			}
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(retrunList.size()).append(",result:");
			Gson gson=new Gson();
			buff.append(gson.toJson(retrunList));
			buff.append("}");
			jsonString=buff.toString();
		}
		return SUCCESS;
	}
	
	public String listByProjectId(){
		List<OurArchivesMaterials> list = null;
		String projectId = this.getRequest().getParameter("projectId");
		String productId = this.getRequest().getParameter("productId");
		if (null != productId && !"".equals(productId)
				&& !"null".equals(productId)) {
			list = ourArchivesMaterialsService.getByProductId(Long
					.valueOf(productId));
		}

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if (null != list) {
			buff.append(list.size()).append(",result:");
		} else {
			buff.append(0).append(",result:");
		}
		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	

	//查询出来没有产品没有初始化的基础贷款材料
	public  String listTree1(){
		String businessType = this.getRequest().getParameter("businessType");
		String operationType = this.getRequest().getParameter("operationType");
		String productId = this.getRequest().getParameter("productId");
		List<TreeBeanUtil> blist = new ArrayList<TreeBeanUtil>();
		TreeBeanUtil treeBean = new TreeBeanUtil();
		treeBean.setId(Long.valueOf("0"));
		treeBean.setText("基础贷款条件");
		treeBean.setChecked(false);
		treeBean.setCls("folder");
		treeBean.setLeaf(false);
		Set<TreeBeanUtil> set = new HashSet<TreeBeanUtil>();
		List<OurArchivesMaterials> listtemp1 = ourArchivesMaterialsService
				.getByBusinessType(businessType, null);
		int i = 0;
		if (null != listtemp1 && listtemp1.size() > 0) {
			for (OurArchivesMaterials m : listtemp1) {
				List<OurArchivesMaterials> children = ourArchivesMaterialsService
						.checkIsExit(productId, m.getMaterialsId(),
								businessType);
				if (children == null || children.size() == 0) {
					TreeBeanUtil c = new TreeBeanUtil();
					c.setId(m.getMaterialsId());
					c.setText(m.getMaterialsName());
					c.setChecked(false);
					c.setCls("file");
					c.setLeaf(true);
					set.add(c);
					i++;
				}
			}
			treeBean.setChildren(set);
			blist.add(treeBean);

		}

		Gson gson = new Gson();
		jsonString = gson.toJson(blist);
		return SUCCESS;
	}
	//查到产品已经有的贷款材料树
	public  String listExitTree(){
		try {
			String businessType = this.getRequest()
					.getParameter("businessType");
			String operationType = this.getRequest().getParameter(
					"operationType");
			String productId = this.getRequest().getParameter("productId");
			List<TreeBeanUtil> blist = new ArrayList<TreeBeanUtil>();
			TreeBeanUtil treeBean = new TreeBeanUtil();
			treeBean.setId(Long.valueOf("0"));
			treeBean.setText("基础贷款条件");
			treeBean.setChecked(false);
			treeBean.setCls("folder");
			treeBean.setLeaf(false);
			Set<TreeBeanUtil> set = new HashSet<TreeBeanUtil>();
			List<OurArchivesMaterials> mlist = ourArchivesMaterialsService
					.getByPProductIdAndOperationType(productId, businessType);
			for (OurArchivesMaterials s : mlist) {
				TreeBeanUtil c = new TreeBeanUtil();
				c.setId(s.getMaterialsId());
				c.setText(s.getMaterialsName());
				c.setChecked(false);
				c.setCls("file");
				c.setLeaf(true);
				set.add(c);
			}
			treeBean.setChildren(set);
			blist.add(treeBean);
			Gson gson = new Gson();
			jsonString = gson.toJson(blist);
		} catch (Exception e) {
			logger.error("OurArchivesMaterialsAction:" + e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//新增产品贷款材料
	public String updateArchives(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String productId= this.getRequest().getParameter("productId");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					OurArchivesMaterials o = ourArchivesMaterialsService.get(Long.valueOf(proArrs[i]));
					OurArchivesMaterials add =new OurArchivesMaterials();
					add.setProductId(Long.valueOf(productId));
					add.setSettingId(o.getMaterialsId());
					add.setMaterialsName(o.getMaterialsName());
					add.setBusinessTypeGlobalId(o.getBusinessTypeGlobalId());
					add.setBusinessTypeKey(o.getBusinessTypeKey());
					add.setOperationTypeGlobalId(o.getOperationTypeGlobalId());
					add.setOperationTypeKey(o.getOperationTypeKey());
					add.setIsPublic(o.getIsPublic());
					add.setCompanyId(o.getCompanyId());
					ourArchivesMaterialsService.save(add);
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
 
 public String deleteArchives(){
	 try{
		 String materialIds = this.getRequest().getParameter("materialsIds");
		if(null!=materialIds && !"".equals(materialIds)){
			String[] proArrs = materialIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				ourArchivesMaterialsService.remove(Long.valueOf(proArrs[i]));
			}
		}
		jsonString="{success:true}";
	 }catch(Exception e){
		 jsonString="{success:false}";
		 logger.error("删除产品归档材料报错:"+e.getMessage());
		 e.printStackTrace();
	 }
	 return SUCCESS;
 }
 
 public String saveProduct(){
	    this.ourArchivesMaterialsService.save(ourArchivesMaterials);
		setJsonString("{success:true}");
		return SUCCESS;
	
	
 }
	
}
