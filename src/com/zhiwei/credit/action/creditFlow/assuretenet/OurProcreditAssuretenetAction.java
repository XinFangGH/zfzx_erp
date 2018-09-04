package com.zhiwei.credit.action.creditFlow.assuretenet;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.log.LogResource;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.permission.CatalogModel;
import com.zhiwei.credit.model.system.GlobalType;
import com.zhiwei.credit.service.creditFlow.assuretenet.OurProcreditAssuretenetService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.util.TreeBeanUtil;
/**
 * 
 * @author 
 *
 */
public class OurProcreditAssuretenetAction extends BaseAction{
	@Resource
	private OurProcreditAssuretenetService ourProcreditAssuretenetService;
	@Resource
	private GlobalTypeService globalTypeService;
	
	private OurProcreditAssuretenet ourProcreditAssuretenet;
	private Long assuretenetId;
	public Long getAssuretenetId() {
		return assuretenetId;
	}
	public void setAssuretenetId(Long assuretenetId) {
		this.assuretenetId = assuretenetId;
	}

	public OurProcreditAssuretenet getOurProcreditAssuretenet() {
		return ourProcreditAssuretenet;
	}

	public void setOurProcreditAssuretenet(OurProcreditAssuretenet ourProcreditAssuretenet) {
		this.ourProcreditAssuretenet = ourProcreditAssuretenet;
	}
	
	/**
	 * 贷款必备条件树
	 */
	public void getAssuretenetTree(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String customerType = request.getParameter("customerType");
		String productId=request.getParameter("productId");
		List<OurProcreditAssuretenet> list = null;
		List<OurProcreditAssuretenet> newList=null;
		List<OurProcreditAssuretenet> temp=new ArrayList<OurProcreditAssuretenet>();
		if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
			newList=ourProcreditAssuretenetService.getByProductId(Long.valueOf(productId));
		}
		list = ourProcreditAssuretenetService.getAssuretenetTree(customerType);
		if(null!=newList && newList.size()>0){
			for(int i=0;i<list.size();i++){
				OurProcreditAssuretenet o=list.get(i);
				boolean flag=true;
				for(int j=0;j<newList.size();j++){
					OurProcreditAssuretenet l=newList.get(j);
					if(o.getAssuretenet().equals(l.getAssuretenet())){
						flag=false;
					}
				}
				if(flag){
					temp.add(o);
				}
			}
		}else{
			temp=list;
		}
		List<CatalogModel> catalogList = new ArrayList<CatalogModel>();
		for (OurProcreditAssuretenet o : temp) {
			CatalogModel cm = new CatalogModel();
			cm.setId(o.getAssuretenetId() + "");
			cm.setText(o.getAssuretenet());
			cm.setLeaf(true);
			catalogList.add(cm);
		}
		JSONArray jsonObject = JSONArray.fromObject(catalogList);
		String json = jsonObject.toString();
		JsonUtil.responseJsonString(json);
	}

	/**
	 * 显示列表
	 */
	public String list(){

		String businessType = this.getRequest().getParameter("businessType");
		PagingBean pb=new PagingBean(start,limit);
		List<OurProcreditAssuretenet> list = ourProcreditAssuretenetService.getListByBussinessType(businessType, pb);
		// List<OurProcreditAssuretenet> list=
		// ourProcreditAssuretenetService.getAll();
		/*List<OurProcreditAssuretenet> returnList = new ArrayList<OurProcreditAssuretenet>();
		for (OurProcreditAssuretenet oA : list) {
			oA.setBusinessTypeName(this.globalTypeService.getByNodeKey(
					oA.getBusinessTypeKey()).get(0).getTypeName());
			oA.setOperationTypeName(this.globalTypeService.getByNodeKey(
					oA.getOperationTypeKey()).get(0).getTypeName());
			returnList.add(oA);
		}*/
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String listByProjectId(){
		List<OurProcreditAssuretenet> list=null;
		String projectId=this.getRequest().getParameter("projectId");
		String productId=this.getRequest().getParameter("productId");
		if(null!=projectId && !"".equals(projectId) && !"null".equals(projectId)){
			list= ourProcreditAssuretenetService.getByProjectId(Long.valueOf(projectId));
		}
		if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
			list= ourProcreditAssuretenetService.getByProductId(Long.valueOf(productId));
		}
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(null!=list){
			buff.append(list.size()).append(",result:");
		}else{
			buff.append(0).append(",result:");
		}
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	@LogResource(description = "删除贷款准入原则")
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				ourProcreditAssuretenetService.remove(Long.valueOf(id));
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
		OurProcreditAssuretenet ourProcreditAssuretenet=ourProcreditAssuretenetService.get(assuretenetId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(ourProcreditAssuretenet));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	@LogResource(description = "添加或更新贷款准入原则")
	public String save(){
		if(ourProcreditAssuretenet.getBusinessTypeGlobalId()!=null){
			GlobalType globalType = globalTypeService.get(Long.valueOf(ourProcreditAssuretenet.getBusinessTypeGlobalId()));
			String businessTypeKey = "";
			if(globalType!=null){
				if(globalType.getNodeKey()!=null&&globalType.getNodeKey().indexOf("_")!=-1){
					String[] proArrs = globalType.getNodeKey().split("_");
					businessTypeKey = proArrs[0];
				}else{
					businessTypeKey = globalType.getNodeKey();
				}
				
				ourProcreditAssuretenet.setBusinessTypeName(globalType.getTypeName());
				ourProcreditAssuretenet.setBusinessTypeKey(businessTypeKey);
			}
			
			if(ourProcreditAssuretenet.getOperationTypeGlobalId()!=null){
				GlobalType global = globalTypeService.get(Long.valueOf(ourProcreditAssuretenet.getOperationTypeGlobalId()));
				if(global!=null){
					ourProcreditAssuretenet.setOperationTypeKey(global.getNodeKey());
				}
			}
		}
	    this.ourProcreditAssuretenetService.save(ourProcreditAssuretenet);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String save2(){
		try {
			if(null!=ourProcreditAssuretenet){
				if(ourProcreditAssuretenet.getAssuretenetId()!=null){
					OurProcreditAssuretenet orgOurProcreditAssuretenet=ourProcreditAssuretenetService.get(ourProcreditAssuretenet.getAssuretenetId());
					/*if(null!=ourProcreditAssuretenet.getOutletopinion() &&!"".equals(ourProcreditAssuretenet.getOutletopinion())){
						orgOurProcreditAssuretenet.setOutletopinion(ourProcreditAssuretenet.getOutletopinion());
					}*/
					BeanUtil.copyNotNullProperties(orgOurProcreditAssuretenet, ourProcreditAssuretenet);
					ourProcreditAssuretenetService.merge(orgOurProcreditAssuretenet);
					setJsonString("{success:true}");
				}else{
					OurProcreditAssuretenet op=new OurProcreditAssuretenet();
					BeanUtil.copyNotNullProperties(op, ourProcreditAssuretenet);
					ourProcreditAssuretenetService.save(op);
					setJsonString("{success:true}");
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			setJsonString("{success:false}");
		}
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
		List<OurProcreditAssuretenet> listtemp1 = ourProcreditAssuretenetService
				.getListByBussinessType(businessType, null);
		int i = 0;
		if (null != listtemp1 && listtemp1.size() > 0) {
			for (OurProcreditAssuretenet m : listtemp1) {
				List<OurProcreditAssuretenet> children = ourProcreditAssuretenetService
						.checkIsExit(productId, m.getAssuretenetId(),
								businessType);
				if (children == null || children.size() == 0) {
					TreeBeanUtil c = new TreeBeanUtil();
					c.setId(m.getAssuretenetId());
					c.setText(m.getAssuretenet());
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
			List<OurProcreditAssuretenet> mlist = ourProcreditAssuretenetService
					.getByPProductIdAndOperationType(productId, businessType);
			for (OurProcreditAssuretenet s : mlist) {
				TreeBeanUtil c = new TreeBeanUtil();
				c.setId(s.getAssuretenetId());
				c.setText(s.getAssuretenet());
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
			logger.error("OurProcreditAssuretenetAction:" + e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//新增产品贷款材料
	public String updateAssurent(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String productId= this.getRequest().getParameter("productId");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					OurProcreditAssuretenet o = ourProcreditAssuretenetService.get(Long.valueOf(proArrs[i]));
					OurProcreditAssuretenet add =new OurProcreditAssuretenet();
					add.setProductId(Long.valueOf(productId));
					add.setProjectId(o.getAssuretenetId());
					add.setAssuretenet(o.getAssuretenet());
					add.setBusinessTypeGlobalId(o.getBusinessTypeGlobalId());
					add.setBusinessTypeKey(o.getBusinessTypeKey());
					add.setOperationTypeGlobalId(o.getOperationTypeGlobalId());
					add.setOperationTypeKey(o.getOperationTypeKey());
					add.setCustomerType(o.getCustomerType());
					add.setCompanyId(o.getCompanyId());
					ourProcreditAssuretenetService.save(add);
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
 
 public String deleteAssurent(){
	 try{
		 String materialIds = this.getRequest().getParameter("materialsIds");
		if(null!=materialIds && !"".equals(materialIds)){
			String[] proArrs = materialIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				ourProcreditAssuretenetService.remove(Long.valueOf(proArrs[i]));
			}
		}
		jsonString="{success:true}";
	 }catch(Exception e){
		 jsonString="{success:false}";
		 logger.error("删除产品贷款条件报错:"+e.getMessage());
		 e.printStackTrace();
	 }
	 return SUCCESS;
 }
 
 public String saveProduct(){
	    this.ourProcreditAssuretenetService.save(ourProcreditAssuretenet);
		setJsonString("{success:true}");
		return SUCCESS;
	
	
 }
}
