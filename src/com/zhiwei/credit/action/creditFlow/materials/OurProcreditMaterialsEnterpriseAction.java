package com.zhiwei.credit.action.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.creditFlow.permission.CatalogModel;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;
import com.zhiwei.credit.service.system.GlobalTypeService;
import com.zhiwei.credit.util.TreeBeanUtil;
/**
 * 
 * @author 
 *
 */
public class OurProcreditMaterialsEnterpriseAction extends BaseAction{
	@Resource
	private OurProcreditMaterialsEnterpriseService ourProcreditMaterialsEnterpriseService;
	@Resource
	private GlobalTypeService globalTypeService;
	
	private OurProcreditMaterialsEnterprise ourProcreditMaterialsEnterprise;
	
	private Long materialsId;

	public Long getMaterialsId() {
		return materialsId;
	}

	public void setMaterialsId(Long materialsId) {
		this.materialsId = materialsId;
	}

	public OurProcreditMaterialsEnterprise getOurProcreditMaterialsEnterprise() {
		return ourProcreditMaterialsEnterprise;
	}

	public void setOurProcreditMaterialsEnterprise(OurProcreditMaterialsEnterprise ourProcreditMaterialsEnterprise) {
		this.ourProcreditMaterialsEnterprise = ourProcreditMaterialsEnterprise;
	}
	
	/**
	 * 贷款材料清单树
	 */
	public void getMaterialsTree(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String node = request.getParameter("node");
		String operationTypeKey = request.getParameter("operationTypeKey");
		String productId=request.getParameter("productId");
		List<OurProcreditMaterialsEnterprise> list = null;
		List<OurProcreditMaterialsEnterprise> newList=null;
		List<OurProcreditMaterialsEnterprise> temp=new ArrayList<OurProcreditMaterialsEnterprise>();
		if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
			newList=ourProcreditMaterialsEnterpriseService.getByProductId(Long.valueOf(productId));
		}
		list = ourProcreditMaterialsEnterpriseService.getListByIdsNotNull(node,operationTypeKey);
		if(null!=newList && newList.size()>0){
			for(int i=0;i<list.size();i++){
				OurProcreditMaterialsEnterprise o=list.get(i);
				boolean flag=true;
				for(int j=0;j<newList.size();j++){
					OurProcreditMaterialsEnterprise l=newList.get(j);
					if(o.getMaterialsName().equals(l.getMaterialsName())){
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
		for (OurProcreditMaterialsEnterprise o : temp) {
			CatalogModel cm = new CatalogModel();
			cm.setId(o.getMaterialsId() + "");
			cm.setText(o.getMaterialsName());
			cm.setLeaf(0==o.getLeaf()?false:true);
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

		try {
			// List<OurProcreditMaterialsEnterprise> list = new
			// ArrayList<OurProcreditMaterialsEnterprise>();
			String node = this.getRequest().getParameter("node");
			if (null == node)
				node = "0";
			List<OurProcreditMaterialsEnterprise> listTemp = this.ourProcreditMaterialsEnterpriseService
					.getListByParentId(Integer.valueOf(node),null);
			/*for (OurProcreditMaterialsEnterprise om : listTemp) {
				String operationTypeName = "";
				if (null != om.getOperationTypeKey()
						&& !om.getOperationTypeKey().equals("")) {
					operationTypeName = this.globalTypeService.getByNodeKey(
							om.getOperationTypeKey()).get(0).getTypeName();
					// operationTypeName =
					// globalTypeService.getByNodeKeyCatKey(om.getOperationTypeKey(),
					// "FLOW");
				}
				om.setOperationTypeName(operationTypeName);
				// list.add(om);
			}*/
			StringBuffer buff = new StringBuffer("{success:true")
					.append(",result:");
			Gson gson = new Gson();
			buff.append(gson.toJson(listTemp));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	/**
	 * 显示列表
	 */
	public String listByProductId(){
		List<OurProcreditMaterialsEnterprise> list = null;
		String productId = this.getRequest().getParameter("productId");
		String projectId = this.getRequest().getParameter("projectId");
		/*
		 * if(null!=productId && !"".equals(productId) &&
		 * !"null".equals(productId)){ QueryFilter filter=new
		 * QueryFilter(getRequest()); filter.addFilter("Q_productId_L_EQ",
		 * productId); list=
		 * ourProcreditMaterialsEnterpriseService.getAll(filter); }
		 * if(null!=projectId && !"".equals(projectId)){ list=
		 * ourProcreditMaterialsEnterpriseService
		 * .getByProjectId(Long.valueOf(projectId)); }
		 */

		if (null != projectId && !"".equals(projectId)
				&& !"null".equals(projectId)) {
			list = ourProcreditMaterialsEnterpriseService.getByProjectId(Long
					.valueOf(projectId));
		}
		if (null != productId && !"".equals(productId)
				&& !"null".equals(productId)) {
			list = ourProcreditMaterialsEnterpriseService.getByProductId(Long
					.valueOf(productId));
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if (null != list) {
			buff.append(list.size()).append(",result:");
			for (OurProcreditMaterialsEnterprise temp : list) {
				OurProcreditMaterialsEnterprise parentObject = ourProcreditMaterialsEnterpriseService
						.get(temp.getParentId().longValue());
				if (parentObject != null) {
					temp.setParentName(parentObject.getMaterialsName());
				}
			}
		} else {
			buff.append(0).append(",result:");
		}
		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String listByOperationTypeKey(){

		try {
			// List<OurProcreditMaterialsEnterprise> list = new
			// ArrayList<OurProcreditMaterialsEnterprise>();
			String node = this.getRequest().getParameter("node");
			String operationTypeKey = this.getRequest().getParameter(
					"operationTypeKey");
			if (null == node)
				node = "0";
			// List<OurProcreditMaterialsEnterprise> listTemp =
			// this.ourProcreditMaterialsEnterpriseService.getListByParentId(Integer.valueOf(node));
			PagingBean pb=new PagingBean(start,limit);
			Map<String,Object> map  = new HashMap<String,Object>();
			map.put("operationTypeKey", operationTypeKey);
			pb.setMap(map);
			List<OurProcreditMaterialsEnterprise> listTemp = this.ourProcreditMaterialsEnterpriseService.getListByParentId(Integer.valueOf(node),pb);
			/*for (OurProcreditMaterialsEnterprise om : listTemp) {
				String operationTypeName = "";
				if (null != om.getOperationTypeKey()
						&& !om.getOperationTypeKey().equals("")) {
					operationTypeName = this.globalTypeService.getByNodeKey(
							om.getOperationTypeKey()).get(0).getTypeName();
					// operationTypeName =
					// globalTypeService.getByNodeKeyCatKey(om.getOperationTypeKey(),
					// "FLOW");
				}
				om.setOperationTypeName(operationTypeName);
				// list.add(om);
			}*/
			StringBuffer buff = new StringBuffer("{success:true,'totalCount':").append(pb.getTotalItems())
					.append(",result:");
			Gson gson = new Gson();
			buff.append(gson.toJson(listTemp));
			buff.append("}");
			jsonString = buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return SUCCESS;
	}
	public String listChildren(){

		String node = this.getRequest().getParameter("node");
		PagingBean pb=new PagingBean(start,limit);
		List<OurProcreditMaterialsEnterprise> listTemp = this.ourProcreditMaterialsEnterpriseService.getListByParentId(Integer.valueOf(node),pb);
		/*List<OurProcreditMaterialsEnterprise> list = new ArrayList<OurProcreditMaterialsEnterprise>();
		for (OurProcreditMaterialsEnterprise cme : listTemp) {

			OurProcreditMaterialsEnterprise pcme = this.ourProcreditMaterialsEnterpriseService
					.get(Long.valueOf(cme.getParentId()));
			cme.setParentText(pcme.getMaterialsName());
			list.add(cme);
		}*/
		StringBuffer buff = new StringBuffer("{success:true,'totalCount':").append(pb.getTotalItems())
				.append(",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(listTemp));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String listTreeByOperationTypeKey(){
		String operationTypeKey=this.getRequest().getParameter("operationTypeKey");
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		List<TreeBeanUtil> list= new ArrayList<TreeBeanUtil>();
		List<OurProcreditMaterialsEnterprise> listTemp = this.ourProcreditMaterialsEnterpriseService.getListByParentIdAndType(0, operationTypeKey);
		for(OurProcreditMaterialsEnterprise p :listTemp){
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 treeBean.setId(Long.valueOf(p.getMaterialsId()));
			 treeBean.setText(p.getMaterialsName());
			 treeBean.setChecked(false);
			 treeBean.setCls("folder");
			 treeBean.setLeaf(false);
			 List<OurProcreditMaterialsEnterprise> list1 = this.ourProcreditMaterialsEnterpriseService.getListByParentIdAndType(p.getMaterialsId().intValue(), operationTypeKey);
			 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
			 for(OurProcreditMaterialsEnterprise temp : list1){
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
				ourProcreditMaterialsEnterpriseService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	public String delete(){

		OurProcreditMaterialsEnterprise ome = this.ourProcreditMaterialsEnterpriseService
				.get(materialsId);
		if (null != ome) {
			if (ome.getLeaf() == 0) { // 父节点

				List<OurProcreditMaterialsEnterprise> list = this.ourProcreditMaterialsEnterpriseService
						.getListByParentId(ome.getMaterialsId().intValue(),null);
				for (OurProcreditMaterialsEnterprise temp : list) {

					this.ourProcreditMaterialsEnterpriseService.remove(temp);
				}
				this.ourProcreditMaterialsEnterpriseService.remove(ome);
			} else if (ome.getLeaf() == 1) {
				this.ourProcreditMaterialsEnterpriseService.remove(ome);
			}
		}
		return SUCCESS;
	}
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		OurProcreditMaterialsEnterprise ourProcreditMaterialsEnterprise=ourProcreditMaterialsEnterpriseService.get(materialsId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{success:true,data:");
		String parentText="";
		if(null!=ourProcreditMaterialsEnterprise){
			OurProcreditMaterialsEnterprise temp=this.ourProcreditMaterialsEnterpriseService.get(Long.valueOf(ourProcreditMaterialsEnterprise.getParentId()));
			if(null!=temp){
				parentText=temp.getMaterialsName();
			}
			ourProcreditMaterialsEnterprise.setParentText(parentText);
			sb.append(gson.toJson(ourProcreditMaterialsEnterprise));
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
		if(ourProcreditMaterialsEnterprise.getMaterialsId()!=null){
			
			OurProcreditMaterialsEnterprise  _ourProcreditMaterialsEnterprise = ourProcreditMaterialsEnterpriseService.get(ourProcreditMaterialsEnterprise.getMaterialsId());
			try {
				BeanUtil.copyNotNullProperties(_ourProcreditMaterialsEnterprise, ourProcreditMaterialsEnterprise);
			} catch (Exception e) {
			}
			this.ourProcreditMaterialsEnterpriseService.save(_ourProcreditMaterialsEnterprise);
		}else{
			this.ourProcreditMaterialsEnterpriseService.save(ourProcreditMaterialsEnterprise);
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 添加及保存操作
	 */
	public String save2(){
		try {
			if(null==ourProcreditMaterialsEnterprise.getMaterialsId() || "".equals(ourProcreditMaterialsEnterprise.getMaterialsId())){
				this.ourProcreditMaterialsEnterpriseService.save(ourProcreditMaterialsEnterprise);
			}else{
				OurProcreditMaterialsEnterprise oldMaterials=this.ourProcreditMaterialsEnterpriseService.get(ourProcreditMaterialsEnterprise.getMaterialsId());
				BeanUtil.copyNotNullProperties(oldMaterials, ourProcreditMaterialsEnterprise);
				this.ourProcreditMaterialsEnterpriseService.merge(oldMaterials);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 查询出来小额贷款下面的所有父类贷款材料
	 * add by linyan  2014-8-13
	 * @return
	 */
	public String selectParentId(){
		String nodeKey = this.getRequest().getParameter("nodeKey");
		List<OurProcreditMaterialsEnterprise> list = this.ourProcreditMaterialsEnterpriseService
				.getListByParentIdAndType(0, nodeKey);
		if (null != list && list.size() > 0) {
			StringBuffer buff = new StringBuffer("[");
			for (OurProcreditMaterialsEnterprise temp : list) {
				buff.append("['").append(temp.getMaterialsId()).append("','")
						.append(temp.getMaterialsName()).append("'],");
			}
			if (list.size() > 0) {
				buff.deleteCharAt(buff.length() - 1);
			}
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
	
	
	//查询出来没有产品没有初始化的基础贷款材料
	public  String listTree1(){
		//String businessType = this.getRequest().getParameter("businessType");
		//String operationType = this.getRequest().getParameter("operationType");
		String productId = this.getRequest().getParameter("productId");
		List<OurProcreditMaterialsEnterprise> listtemp = ourProcreditMaterialsEnterpriseService
				.getListByParentId(0,null);
		List<TreeBeanUtil> blist = new ArrayList<TreeBeanUtil>();
		for (OurProcreditMaterialsEnterprise areatemp : listtemp) {
			TreeBeanUtil treeBean = new TreeBeanUtil();
			treeBean.setId(areatemp.getMaterialsId());
			treeBean.setText(areatemp.getMaterialsName());
			treeBean.setChecked(false);
			treeBean.setCls("folder");
			treeBean.setLeaf(false);
			Set<TreeBeanUtil> set = new HashSet<TreeBeanUtil>();
			List<OurProcreditMaterialsEnterprise> listtemp1 = ourProcreditMaterialsEnterpriseService
					.getListByParentId(areatemp.getMaterialsId()
							.intValue(), null);
			int i = 0;
			if (null != listtemp1 && listtemp1.size() > 0) {
				for (OurProcreditMaterialsEnterprise m : listtemp1) {
					List<OurProcreditMaterialsEnterprise> children = ourProcreditMaterialsEnterpriseService
							.getByPProductIdAndOperationType(productId, m
									.getMaterialsId());
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
				if (i != 0) {
					treeBean.setChildren(set);
					blist.add(treeBean);
				}

			}
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
			List<OurProcreditMaterialsEnterprise> list = ourProcreditMaterialsEnterpriseService
					.listByMaterialsIdGroupById(productId);
			for (OurProcreditMaterialsEnterprise m : list) {
				OurProcreditMaterialsEnterprise setting = ourProcreditMaterialsEnterpriseService
						.get(Long.valueOf(m.getParentId()));
				TreeBeanUtil treeBean = new TreeBeanUtil();
				treeBean.setId(setting.getMaterialsId());
				treeBean.setText(setting.getMaterialsName());
				treeBean.setChecked(false);
				treeBean.setCls("folder");
				treeBean.setLeaf(false);
				Set<TreeBeanUtil> set = new HashSet<TreeBeanUtil>();
				List<OurProcreditMaterialsEnterprise> mlist = ourProcreditMaterialsEnterpriseService
						.listByProductIdAndParentId(Long.valueOf(productId), setting
								.getMaterialsId().intValue());
				for (OurProcreditMaterialsEnterprise s : mlist) {
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
			}

			Gson gson = new Gson();
			jsonString = gson.toJson(blist);
		} catch (Exception e) {
			logger.error("PlArchivesMaterialsAction:" + e.getMessage());
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	//新增产品贷款材料
	public String updateMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String productId= this.getRequest().getParameter("productId");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					OurProcreditMaterialsEnterprise o = ourProcreditMaterialsEnterpriseService.get(Long.valueOf(proArrs[i]));
					OurProcreditMaterialsEnterprise add =new OurProcreditMaterialsEnterprise();
					add.setProductId(Long.valueOf(productId));
					add.setProjectId(o.getMaterialsId());
					add.setMaterialsName(o.getMaterialsName());
					add.setParentId(o.getParentId());
					add.setOperationTypeKey(o.getOperationTypeKey());
					add.setRemarks(o.getRemarks());
					add.setRuleExplain(o.getRuleExplain());
					add.setDatumNums(o.getDatumNums());
					add.setXxnums(o.getXxnums());
					add.setIsfile(o.getIsfile());
					add.setGdremark(o.getGdremark());
					add.setLeaf(o.getLeaf());
					add.setCompanyId(o.getCompanyId());
					ourProcreditMaterialsEnterpriseService.save(add);
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
				ourProcreditMaterialsEnterpriseService.remove(Long.valueOf(proArrs[i]));
			}
		}
		jsonString="{success:true}";
	 }catch(Exception e){
		 jsonString="{success:false}";
		 logger.error("删除产品贷款材料报错:"+e.getMessage());
		 e.printStackTrace();
	 }
	 return SUCCESS;
 }
 
 public String addProduct(){
	 try{
		 String parentId = this.getRequest().getParameter("parentId");
		 String productId = this.getRequest().getParameter("productId");
		 String materialsName = this.getRequest().getParameter("materialsName");
		 OurProcreditMaterialsEnterprise o = ourProcreditMaterialsEnterpriseService.get(Long.valueOf(parentId));
			OurProcreditMaterialsEnterprise add =new OurProcreditMaterialsEnterprise();
			add.setProductId(Long.valueOf(productId));
			add.setMaterialsName(materialsName);
			add.setParentId(o.getMaterialsId().intValue());
			add.setOperationTypeKey(o.getOperationTypeKey());
			add.setRemarks(o.getRemarks());
			add.setRuleExplain(o.getRuleExplain());
			add.setDatumNums(o.getDatumNums());
			add.setXxnums(o.getXxnums());
			add.setIsfile(o.getIsfile());
			add.setGdremark(o.getGdremark());
			add.setLeaf(o.getLeaf());
			add.setCompanyId(o.getCompanyId());
			ourProcreditMaterialsEnterpriseService.save(add);
		jsonString="{success:true}";
	 }catch(Exception e){
		 jsonString="{success:false}";
		 logger.error("删除产品贷款材料报错:"+e.getMessage());
		 e.printStackTrace();
	 }
	 return SUCCESS;
 }
 
 /**
	 * 理财产品材料清单查询
	 * @return
	 */
	public String listByProjectAndBusinessType(){
		List<OurProcreditMaterialsEnterprise> list = null;
		String projectId = this.getRequest().getParameter("projectId");
		String businessType = this.getRequest().getParameter("businessType");

		if (null != projectId && !"".equals(projectId)&& !"null".equals(projectId)) {
			list = ourProcreditMaterialsEnterpriseService.listByProjectAndBusinessType(Long.valueOf(projectId),businessType);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if (null != list) {
			buff.append(list.size()).append(",result:");
			for (OurProcreditMaterialsEnterprise temp : list) {
				OurProcreditMaterialsEnterprise parentObject = ourProcreditMaterialsEnterpriseService.get(temp.getParentId().longValue());
				if (parentObject != null) {
					temp.setParentName(parentObject.getMaterialsName());
				}
			}
		} else {
			buff.append(0).append(",result:");
		}
		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
 
 
 
 
 
 
}
