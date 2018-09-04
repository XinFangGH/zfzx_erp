package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.Type;
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
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.creditFlow.permission.CatalogModel;
import com.zhiwei.credit.service.creditFlow.finance.SlActualToChargeService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.util.TreeBeanUtil;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class SlPlansToChargeAction extends BaseAction{
	@Resource
	private SlPlansToChargeService slPlansToChargeService;
	@Resource
	private SlActualToChargeService slActualToChargeService;
	private SlPlansToCharge slPlansToCharge;
	
	private Long plansTochargeId;
	private Long projId;
	private String chargejson;
    private Integer isOperationType;
    private String chargekey;
    private String businessType;
	public Integer getIsOperationType() {
		return isOperationType;
	}

	public void setIsOperationType(Integer isOperationType) {
		this.isOperationType = isOperationType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getChargekey() {
		return chargekey;
	}

	public void setChargekey(String chargekey) {
		this.chargekey = chargekey;
	}

	public Long getPlansTochargeId() {
		return plansTochargeId;
	}

	public void setPlansTochargeId(Long plansTochargeId) {
		this.plansTochargeId = plansTochargeId;
	}

	public SlPlansToCharge getSlPlansToCharge() {
		return slPlansToCharge;
	}

	public void setSlPlansToCharge(SlPlansToCharge slPlansToCharge) {
		this.slPlansToCharge = slPlansToCharge;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public String getChargejson() {
		return chargejson;
	}

	public void setChargejson(String chargejson) {
		this.chargejson = chargejson;
	}

	
	/**
	 * 手续费用清单树
	 */
	public void getActualToChargeTree(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String businessType = request.getParameter("businessType");
		String isValid = request.getParameter("isValid");
		String productId=request.getParameter("productId");
		List<SlPlansToCharge> list = null;
		list = slPlansToChargeService.getListByIdsNotNull(businessType,isValid);
		List<SlActualToCharge> newList=null;
		List<SlPlansToCharge> temp=new ArrayList<SlPlansToCharge>();
		if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
			newList=slActualToChargeService.getByProductId(productId);
		}
		if(null!=newList && newList.size()>0){
			for(int i=0;i<list.size();i++){
				SlPlansToCharge o=list.get(i);
				boolean flag=true;
				for(int j=0;j<newList.size();j++){
					SlActualToCharge l=newList.get(j);
					if(o.getName().equals(l.getCostType())){
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
		for (SlPlansToCharge o : temp) {
			CatalogModel cm = new CatalogModel();
			cm.setId(o.getPlansTochargeId() + "");
			cm.setText(o.getName());
			cm.setLeaf(true);
			cm.setModel(o.getChargeStandard());//临时存放其他值
			catalogList.add(cm);
		}
		JSONArray jsonObject = JSONArray.fromObject(catalogList);
		String json = jsonObject.toString();
		com.zhiwei.credit.core.creditUtils.JsonUtil.responseJsonString(json);
	}
	
	/**
	 * 显示列表
	 */
	public String list(){
		QueryFilter filter=new QueryFilter(getRequest());
		List<SlPlansToCharge> list1= slPlansToChargeService.getbyOperationType(3,businessType);
		Type type=new TypeToken<List<SlPlansToCharge>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list1.size()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list1, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
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
				slPlansToChargeService.remove(new Long(id));
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
		SlPlansToCharge slPlansToCharge=slPlansToChargeService.get(plansTochargeId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slPlansToCharge));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(slPlansToCharge.getPlansTochargeId()==null){
			slPlansToChargeService.save(slPlansToCharge);
		}else{
			SlPlansToCharge orgSlPlansToCharge=slPlansToChargeService.get(slPlansToCharge.getPlansTochargeId());
			try{
				BeanUtil.copyNotNullProperties(orgSlPlansToCharge, slPlansToCharge);
				slPlansToChargeService.save(orgSlPlansToCharge);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String savejson() {


		if (null != chargejson && !"".equals(chargejson)) {
			String[] shareequityArr = chargejson.split("@");
			for (int i = 0; i < shareequityArr.length; i++) {
				String str = shareequityArr[i];
				String substr = str.substring(19,21);
				String str1 = "\"\"";
				if (substr.equals("\"\"")) {
					str = str.substring(22);
					str = "{" + str;
				}
				JSONParser parser = new JSONParser(new StringReader(str));
				try {
					SlPlansToCharge slPlansToCharge = (SlPlansToCharge) JSONMapper.toJava(parser.nextValue(), SlPlansToCharge.class);
					if (null == slPlansToCharge.getPlansTochargeId()) {
						slPlansToCharge.setIsValid(0);
						slPlansToChargeService.save(slPlansToCharge);
					} else {
						SlPlansToCharge orggLPlansTocharge=slPlansToChargeService.get(slPlansToCharge.getPlansTochargeId());
						BeanUtil.copyNotNullProperties(orggLPlansTocharge,slPlansToCharge);
						slPlansToChargeService.save(orggLPlansTocharge);
					}

					setJsonString("{success:true}");

				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());

				}

			}
		}

		return SUCCESS;
	}
	public String getall(){
		List<SlPlansToCharge> list = slPlansToChargeService.getbyOperationType(3,businessType);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",root:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public String getall1(){
		List<SlPlansToCharge> list = slPlansToChargeService.getbyOperationType(3,businessType);
		StringBuffer buff = new StringBuffer("[");
		for (SlPlansToCharge dic : list) {
			buff.append("[").append(dic.getPlansTochargeId()).append(",'")
					.append(dic.getName()).append("'],");
	
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
		
	}
	public String getallbytype(){
		List<SlPlansToCharge> list = slPlansToChargeService.getbyOperationType(0,businessType);
		StringBuffer buff = new StringBuffer("[");
		for (SlPlansToCharge dic : list) {
			buff.append("[").append(dic.getPlansTochargeId()).append(",'")
					.append(dic.getName()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
		}
	
	public String delete() {
		try {
			SlPlansToCharge slPlansToCharge = slPlansToChargeService.get(plansTochargeId);
			slPlansToChargeService.setIsValid(1,slPlansToCharge);
			setJsonString("{success:true}");
		} catch (Exception e) {
			setJsonString("{success:false}");
			logger.error(e.getMessage());
		}

		return SUCCESS;
	}
	
	
	public String listByProjectId(){
		String productId=this.getRequest().getParameter("productId");
		List<SlPlansToCharge> list1= slPlansToChargeService.getByPProductIdAndOperationType(productId,businessType);
		Type type=new TypeToken<List<SlPlansToCharge>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(list1.size()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list1, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	} 
	//查询出来没有产品没有初始化的基础贷款材料
	public  String listTree1(){
		String businessType=this.getRequest().getParameter("businessType");
		String productId =this.getRequest().getParameter("productId");
		List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
		 TreeBeanUtil treeBean=new TreeBeanUtil();
		 treeBean.setId(Long.valueOf("0"));
		 treeBean.setText("费用清单");
		 treeBean.setChecked(false);
		 treeBean.setCls("folder");
		 treeBean.setLeaf(false);
		 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
		 List<SlPlansToCharge> listtemp1 = slPlansToChargeService.getbyOperationType(3,businessType);
		 int i=0;
		 if(null!=listtemp1 && listtemp1.size()>0){
			 for(SlPlansToCharge m:listtemp1){
				 List<SlPlansToCharge> children= slPlansToChargeService.checkIsExit(productId,m.getPlansTochargeId(),businessType);
				 if(children==null||children.size()==0){
					 TreeBeanUtil c= new TreeBeanUtil();
					 c.setId(m.getPlansTochargeId());
					 c.setText(m.getName());
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
	
		 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		return SUCCESS;
	}
	//查到产品已经有的贷款材料树
	public  String listExitTree(){
		try{
			String businessType=this.getRequest().getParameter("businessType");
			String productId=this.getRequest().getParameter("productId");
			 List<TreeBeanUtil> blist= new ArrayList<TreeBeanUtil>();
			 TreeBeanUtil treeBean=new TreeBeanUtil();
			 treeBean.setId(Long.valueOf("0"));
			 treeBean.setText("费用清单");
			 treeBean.setChecked(false);
			 treeBean.setCls("folder");
			 treeBean.setLeaf(false);
			 Set<TreeBeanUtil> set=new HashSet<TreeBeanUtil>();
			 List<SlPlansToCharge> mlist=slPlansToChargeService.getByPProductIdAndOperationType(productId,businessType);
			 for(SlPlansToCharge s:mlist){
					 TreeBeanUtil c= new TreeBeanUtil();
					 c.setId(s.getPlansTochargeId());
					 c.setText(s.getName());
					 c.setChecked(false);
					 c.setCls("file");
					 c.setLeaf(true);
					 set.add(c);
			 }
			 treeBean.setChildren(set);
			 blist.add(treeBean); 
			 Gson gson=new Gson();
			jsonString=gson.toJson(blist);
		 }catch(Exception e){
			 logger.error("OurProcreditAssuretenetAction:"+e.getMessage());
			 e.printStackTrace();
		 }
		 return SUCCESS;
	}
	
	//新增产品贷款材料
	public String updatePlan(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String productId= this.getRequest().getParameter("productId");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					SlPlansToCharge o = slPlansToChargeService.get(Long.valueOf(proArrs[i]));
					SlPlansToCharge add =new SlPlansToCharge();
					add.setProductId(Long.valueOf(productId));
					add.setSettingId(o.getPlansTochargeId());
					add.setName(o.getName());
					add.setChargeKey(o.getChargeKey());
					add.setChargeStandard(o.getChargeStandard());
					add.setIsOperationType(o.getIsOperationType());
					add.setIsValid(o.getIsValid());
					add.setOperationType(o.getOperationType());
					add.setIsType(o.getIsType());
					add.setBusinessType(o.getBusinessType());
					add.setPlanCharges(o.getPlanCharges());
					add.setCompanyId(o.getCompanyId());
					add.setSort(o.getSort());
					slPlansToChargeService.save(add);
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
 
 public String deletePlan(){
	 try{
		 String materialIds = this.getRequest().getParameter("materialsIds");
		if(null!=materialIds && !"".equals(materialIds)){
			String[] proArrs = materialIds.split(",");
			for(int i = 0;i<proArrs.length;i++){
				slPlansToChargeService.remove(Long.valueOf(proArrs[i]));
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
	    this.slPlansToChargeService.save(slPlansToCharge);
		setJsonString("{success:true}");
		return SUCCESS;
	
	
 }
 
 	public String getProductId(){
 		String productId=this.getRequest().getParameter("productId");
		List<SlPlansToCharge> list = slPlansToChargeService.getbyProductId(productId,businessType);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",root:");
		JSONSerializer json = JsonUtil.getJSONSerializer("intentDate","factDate");
		json.transform(new DateTransformer("yyyy-MM-dd"),new String[]{"intentDate"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
}
