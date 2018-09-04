package com.zhiwei.credit.action.creditFlow.pawn.pawnItems;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgageBusiness;
import com.credit.proj.entity.ProcreditMortgageBusinessandlive;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.credit.proj.entity.ProcreditMortgageDroit;
import com.credit.proj.entity.ProcreditMortgageEducation;
import com.credit.proj.entity.ProcreditMortgageHouse;
import com.credit.proj.entity.ProcreditMortgageHouseground;
import com.credit.proj.entity.ProcreditMortgageIndustry;
import com.credit.proj.entity.ProcreditMortgageMachineinfo;
import com.credit.proj.entity.ProcreditMortgageOfficebuilding;
import com.credit.proj.entity.ProcreditMortgageProduct;
import com.credit.proj.entity.ProcreditMortgageStockownership;
import com.credit.proj.entity.VProjMortBusAndLive;
import com.credit.proj.entity.VProjMortBusiness;
import com.credit.proj.entity.VProjMortCar;
import com.credit.proj.entity.VProjMortDroit;
import com.credit.proj.entity.VProjMortEducation;
import com.credit.proj.entity.VProjMortHouse;
import com.credit.proj.entity.VProjMortHouseGround;
import com.credit.proj.entity.VProjMortIndustry;
import com.credit.proj.entity.VProjMortMachineInfo;
import com.credit.proj.entity.VProjMortOfficeBuilding;
import com.credit.proj.entity.VProjMortProduct;
import com.credit.proj.entity.VProjMortStockOwnerShip;
import com.credit.proj.mortgage.business.service.BusinessServMort;
import com.credit.proj.mortgage.businessandlive.service.BusinessandliveService;
import com.credit.proj.mortgage.droit.service.DroitService;
import com.credit.proj.mortgage.education.service.EducationService;
import com.credit.proj.mortgage.house.service.HouseService;
import com.credit.proj.mortgage.houseground.service.HousegroundService;
import com.credit.proj.mortgage.industry.service.IndustryService;
import com.credit.proj.mortgage.machineinfo.service.MachineinfoService;
import com.credit.proj.mortgage.officebuilding.service.OfficebuildingService;
import com.credit.proj.mortgage.product.service.ProductService;
import com.credit.proj.mortgage.stockownership.service.StockownershipService;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.creditFlow.materials.SlProcreditMaterials;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnCrkRecord;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.PawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.pawnItems.VPawnItemsList;
import com.zhiwei.credit.model.creditFlow.pawn.project.PlPawnProject;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;
import com.zhiwei.credit.service.creditFlow.materials.SlProcreditMaterialsService;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnCrkRecordService;
import com.zhiwei.credit.service.creditFlow.pawn.pawnItems.PawnItemsListService;
import com.zhiwei.credit.service.creditFlow.pawn.project.PlPawnProjectService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class PawnItemsListAction extends BaseAction{
	@Resource
	private PawnItemsListService pawnItemsListService;
	@Resource
	private VehicleService vehicleService;
	@Resource
	private StockownershipService stockownershipService;
	@Resource
	private MachineinfoService machineinfoService;
	@Resource
	private ProductService productService;
	@Resource
	private HouseService houseService;
	@Resource
	private OfficebuildingService officebuildingService;
	@Resource
	private HousegroundService housegroundService;
	@Resource
	private BusinessServMort businessServMort;
	@Resource
	private BusinessandliveService businessandliveService;
	@Resource
	private EducationService educationService;
	@Resource
	private IndustryService industryService;
	@Resource
	private DroitService droitService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private OurProcreditMaterialsEnterpriseService ourProcreditMaterialsEnterpriseService;
	@Resource
	private SlProcreditMaterialsService slProcreditMaterialsService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private PlPawnProjectService plPawnProjectService;
	@Resource
	private OrganizationService organizationService;
	@Resource
	private PawnCrkRecordService pawnCrkRecordService;
	private PawnItemsList pawnItemsList;
	
	private Long pawnItemId;
	private ProcreditMortgageCar procreditMortgageCar;
	private ProcreditMortgageStockownership procreditMortgageStockownership;
	private Enterprise enterprise;
	private ProcreditMortgageMachineinfo procreditMortgageMachineinfo;
	private ProcreditMortgageProduct procreditMortgageProduct;
	private ProcreditMortgageHouse procreditMortgageHouse;
	private ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding;
	private ProcreditMortgageHouseground procreditMortgageHouseground;
	private ProcreditMortgageBusiness procreditMortgageBusiness;
	private ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive;
	private ProcreditMortgageEducation procreditMortgageEducation;
	private ProcreditMortgageIndustry procreditMortgageIndustry;
	private ProcreditMortgageDroit procreditMortgageDroit;
	private Integer typeid;
	private Long mortgageid;
	private Long projectId;
	private String businessType;
	private Integer targetEnterpriseName;
	
	
	public Integer getTargetEnterpriseName() {
		return targetEnterpriseName;
	}

	public void setTargetEnterpriseName(Integer targetEnterpriseName) {
		this.targetEnterpriseName = targetEnterpriseName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public Long getMortgageid() {
		return mortgageid;
	}

	public void setMortgageid(Long mortgageid) {
		this.mortgageid = mortgageid;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public ProcreditMortgageDroit getProcreditMortgageDroit() {
		return procreditMortgageDroit;
	}

	public void setProcreditMortgageDroit(
			ProcreditMortgageDroit procreditMortgageDroit) {
		this.procreditMortgageDroit = procreditMortgageDroit;
	}

	public ProcreditMortgageIndustry getProcreditMortgageIndustry() {
		return procreditMortgageIndustry;
	}

	public void setProcreditMortgageIndustry(
			ProcreditMortgageIndustry procreditMortgageIndustry) {
		this.procreditMortgageIndustry = procreditMortgageIndustry;
	}

	public ProcreditMortgageEducation getProcreditMortgageEducation() {
		return procreditMortgageEducation;
	}

	public void setProcreditMortgageEducation(
			ProcreditMortgageEducation procreditMortgageEducation) {
		this.procreditMortgageEducation = procreditMortgageEducation;
	}

	public ProcreditMortgageBusinessandlive getProcreditMortgageBusinessandlive() {
		return procreditMortgageBusinessandlive;
	}

	public void setProcreditMortgageBusinessandlive(
			ProcreditMortgageBusinessandlive procreditMortgageBusinessandlive) {
		this.procreditMortgageBusinessandlive = procreditMortgageBusinessandlive;
	}

	public ProcreditMortgageHouse getProcreditMortgageHouse() {
		return procreditMortgageHouse;
	}

	public void setProcreditMortgageHouse(
			ProcreditMortgageHouse procreditMortgageHouse) {
		this.procreditMortgageHouse = procreditMortgageHouse;
	}

	public ProcreditMortgageBusiness getProcreditMortgageBusiness() {
		return procreditMortgageBusiness;
	}

	public void setProcreditMortgageBusiness(
			ProcreditMortgageBusiness procreditMortgageBusiness) {
		this.procreditMortgageBusiness = procreditMortgageBusiness;
	}

	public ProcreditMortgageHouseground getProcreditMortgageHouseground() {
		return procreditMortgageHouseground;
	}

	public void setProcreditMortgageHouseground(
			ProcreditMortgageHouseground procreditMortgageHouseground) {
		this.procreditMortgageHouseground = procreditMortgageHouseground;
	}

	public ProcreditMortgageOfficebuilding getProcreditMortgageOfficebuilding() {
		return procreditMortgageOfficebuilding;
	}

	public void setProcreditMortgageOfficebuilding(
			ProcreditMortgageOfficebuilding procreditMortgageOfficebuilding) {
		this.procreditMortgageOfficebuilding = procreditMortgageOfficebuilding;
	}

	public ProcreditMortgageProduct getProcreditMortgageProduct() {
		return procreditMortgageProduct;
	}

	public void setProcreditMortgageProduct(
			ProcreditMortgageProduct procreditMortgageProduct) {
		this.procreditMortgageProduct = procreditMortgageProduct;
	}

	public ProcreditMortgageMachineinfo getProcreditMortgageMachineinfo() {
		return procreditMortgageMachineinfo;
	}

	public void setProcreditMortgageMachineinfo(
			ProcreditMortgageMachineinfo procreditMortgageMachineinfo) {
		this.procreditMortgageMachineinfo = procreditMortgageMachineinfo;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public ProcreditMortgageStockownership getProcreditMortgageStockownership() {
		return procreditMortgageStockownership;
	}

	public void setProcreditMortgageStockownership(
			ProcreditMortgageStockownership procreditMortgageStockownership) {
		this.procreditMortgageStockownership = procreditMortgageStockownership;
	}

	public ProcreditMortgageCar getProcreditMortgageCar() {
		return procreditMortgageCar;
	}

	public void setProcreditMortgageCar(ProcreditMortgageCar procreditMortgageCar) {
		this.procreditMortgageCar = procreditMortgageCar;
	}

	public Long getPawnItemId() {
		return pawnItemId;
	}

	public void setPawnItemId(Long pawnItemId) {
		this.pawnItemId = pawnItemId;
	}

	public PawnItemsList getPawnItemsList() {
		return pawnItemsList;
	}

	public void setPawnItemsList(PawnItemsList pawnItemsList) {
		this.pawnItemsList = pawnItemsList;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		/*QueryFilter filter=new QueryFilter(getRequest());
		List<PawnItemsList> list= pawnItemsListService.getAll(filter);
		
		Type type=new TypeToken<List<PawnItemsList>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");*/
		List<PawnItemsList> list=pawnItemsListService.getListByProjectId(projectId, businessType);
		for(PawnItemsList pawn:list){
			List<SlProcreditMaterials> slist=slProcreditMaterialsService.getByProjId(pawn.getPawnItemId().toString(), "Pawn", true);
			if(null!=slist && slist.size()>0){
				pawn.setFileCount(slist.size());
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String ids=getRequest().getParameter("ids");
		try {
			pawnItemsListService.deleteAllObjectDatas(PawnItemsList.class, ids);
			jsonString="{success:true}";
		} catch (Exception e) {
			jsonString="{success:false}";
			e.printStackTrace();
		}
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		PawnItemsList pawnItemsList=pawnItemsListService.get(pawnItemId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(pawnItemsList));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(pawnItemsList.getPawnItemId()==null){
			pawnItemsListService.save(pawnItemsList);
		}else{
			PawnItemsList orgPawnItemsList=pawnItemsListService.get(pawnItemsList.getPawnItemId());
			try{
				BeanUtil.copyNotNullProperties(orgPawnItemsList, pawnItemsList);
				pawnItemsListService.save(orgPawnItemsList);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String addItems(){
    	
    	try {
    		if(null!=pawnItemsList){
    			if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==1){
    				vehicleService.addPawnVehicle(procreditMortgageCar, pawnItemsList);
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==2){
    				enterprise.setId(targetEnterpriseName);
					procreditMortgageStockownership.setCorporationname(targetEnterpriseName);
					procreditMortgageStockownership.setBusinessType(pawnItemsList.getBusinessType());
    				stockownershipService.addPawnStockownership(procreditMortgageStockownership, pawnItemsList, enterprise);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==5){
    				
    				machineinfoService.addPawnMachineinfo(procreditMortgageMachineinfo, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==6){
    				
    				productService.addPawnProduct(procreditMortgageProduct, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && (pawnItemsList.getPawnItemType()==7 || pawnItemsList.getPawnItemType()==15 || pawnItemsList.getPawnItemType()==16 || pawnItemsList.getPawnItemType()==17)){
    				
    				houseService.addPawnHouse(procreditMortgageHouse, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==8){
    				
    				officebuildingService.addPawnOfficebuilding(procreditMortgageOfficebuilding, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==9){
    				
    				housegroundService.addPawnHouseground(procreditMortgageHouseground, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==10){
    				
    				businessServMort.addPawnBusiness(procreditMortgageBusiness, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==11){
    				
    				businessandliveService.addPawnBusinessandlive(procreditMortgageBusinessandlive, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==12){
    				
    				educationService.addPawnEducation(procreditMortgageEducation, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==13){
    				
    				industryService.addPawnIndustry(procreditMortgageIndustry, pawnItemsList);
    				
    			}else if(null!=pawnItemsList.getPawnItemType() && pawnItemsList.getPawnItemType()==14){
    				
    				droitService.addPawnDroit(procreditMortgageDroit, pawnItemsList);
    			}
    		}
    	} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
    }
	
	public String getPawnItemType(){

    	try {
    		String objectType=this.getRequest().getParameter("objectType");
    		
			pawnItemsList=this.pawnItemsListService.get(mortgageid);
		
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("pawnItemsList", pawnItemsList);
			//VCarDic vCarDic=null;
			if(null!=typeid && typeid==1 ){
				List list=vehicleService.getVehicleByObjectType(mortgageid.intValue(), objectType);
				if(null!=list && list.size()>0){
					procreditMortgageCar=(ProcreditMortgageCar)list.get(0);
					/*if(null!=procreditMortgageCar){
						if(null!=procreditMortgageCar.getManufacturer()){
							vCarDic=(VCarDic) creditBaseDao.getById(VCarDic.class, procreditMortgageCar.getManufacturer());
						}
					}*/
				}
				map.put("procreditMortgageCar", procreditMortgageCar);
				//map.put("vCarDic", vCarDic);
	    	}else if(null!=typeid && typeid==2){
	    		List list=stockownershipService.getStockownershipByObjectType(mortgageid.intValue(), objectType);
	    		EnterpriseView enterpriseView=null;
	    		if(null!=list && list.size()>0){
	    			procreditMortgageStockownership=(ProcreditMortgageStockownership) list.get(0);
	    			if(null!=procreditMortgageStockownership){
	    				if(null!=procreditMortgageStockownership.getCorporationname()){
	    					enterpriseView=(EnterpriseView) creditBaseDao.getById(EnterpriseView.class, procreditMortgageStockownership.getCorporationname());
	    				}
	    			}
	    		}
	    		map.put("procreditMortgageStockownership", procreditMortgageStockownership);
	    		map.put("enterprise", enterpriseView);
	    	}else if(null!=typeid && typeid==5){
	    		List list=machineinfoService.getMachineinfoByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageMachineinfo=(ProcreditMortgageMachineinfo) list.get(0);
	    		}
	    		map.put("procreditMortgageMachineinfo", procreditMortgageMachineinfo);
	    	}else if(null!=typeid && typeid==6){
	    		List list=productService.getProductByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageProduct=(ProcreditMortgageProduct) list.get(0);
	    		}
	    		map.put("procreditMortgageProduct", procreditMortgageProduct);
	    	}else if(null!=typeid && (typeid==7 || typeid==15 || typeid==16 || typeid==17)){
	    		List list=houseService.seeHouseByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageHouse=(ProcreditMortgageHouse) list.get(0);
	    		}
	    		map.put("procreditMortgageHouse", procreditMortgageHouse);
	    	}else if(null!=typeid && typeid==8){
	    		List list=officebuildingService.seeOfficebuildingByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageOfficebuilding=(ProcreditMortgageOfficebuilding) list.get(0);
	    		}
	    		map.put("procreditMortgageOfficebuilding", procreditMortgageOfficebuilding);
	    	}else if(null!=typeid && typeid==9){
	    		List list=housegroundService.seeHousegroundByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageHouseground=(ProcreditMortgageHouseground) list.get(0);
	    		}
	    		map.put("procreditMortgageHouseground", procreditMortgageHouseground);
	    	}else if(null!=typeid && typeid==10){
	    		List list=businessServMort.seeBusinessByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageBusiness=(ProcreditMortgageBusiness) list.get(0);
	    		}
	    		map.put("procreditMortgageBusiness", procreditMortgageBusiness);
	    	}else if(null!=typeid && typeid==11){
	    		List list=businessandliveService.seeBusinessandliveByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageBusinessandlive=(ProcreditMortgageBusinessandlive) list.get(0);
	    		}
	    		map.put("procreditMortgageBusinessandlive", procreditMortgageBusinessandlive);
	    	}else if(null!=typeid && null!=typeid && typeid==12){
	    		List list=educationService.seeEducationByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageEducation=(ProcreditMortgageEducation) list.get(0);
	    		}
	    		map.put("procreditMortgageEducation", procreditMortgageEducation);
	    	}else if(null!=typeid && typeid==13){
	    		List list=industryService.seeIndustryByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageIndustry=(ProcreditMortgageIndustry) list.get(0);
	    		}
	    		map.put("procreditMortgageIndustry", procreditMortgageIndustry);
	    	}else if(null!=typeid && typeid==14){
	    		List list=droitService.seeDroitByObjectType(mortgageid.intValue(), objectType);
	    		if(null!=list && list.size()>0){
	    			procreditMortgageDroit=(ProcreditMortgageDroit) list.get(0);
	    		}
	    		map.put("procreditMortgageDroit", procreditMortgageDroit);
	    	}
			StringBuffer sb=new StringBuffer("{success:true,data:");
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			sb.append(gson.toJson(map));
			sb.append("}");
			jsonString=sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    	return SUCCESS;
    
	}
	public String updateMaterials(){
		try{
			String materialIds = this.getRequest().getParameter("materialsIds");
			String show=this.getRequest().getParameter("show");
			if(null!=materialIds && !"".equals(materialIds)){
				String[] proArrs = materialIds.split(",");
				for(int i = 0;i<proArrs.length;i++){
					OurProcreditMaterialsEnterprise o = ourProcreditMaterialsEnterpriseService.get(Long.valueOf(proArrs[i]));
					SlProcreditMaterials s=null;
					s = slProcreditMaterialsService.get(Long.valueOf(proArrs[i]));
					
					if(null!=s){
						s.setIsShow(Boolean.valueOf(show));
						slProcreditMaterialsService.merge(s);
					}else{
						SlProcreditMaterials slProcreditMaterials = new SlProcreditMaterials();
						slProcreditMaterials.setProjId(String.valueOf(projectId));
						slProcreditMaterials.setMaterialsId(o.getMaterialsId());
						slProcreditMaterials.setMaterialsName(o.getMaterialsName());
						slProcreditMaterials.setIsShow(true);
						slProcreditMaterials.setDatumNums(0);
						slProcreditMaterials.setParentId(o.getParentId());
						slProcreditMaterials.setBusinessTypeKey(businessType);
						slProcreditMaterials.setOperationTypeKey(o.getOperationTypeKey());
						slProcreditMaterialsService.save(slProcreditMaterials);
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
	public String deleteMaterials(){
		try{
			List<SlProcreditMaterials> slist=slProcreditMaterialsService.getByProjIdBusinessTypeKey("0", "Pawn");
			for(SlProcreditMaterials s:slist){
				slProcreditMaterialsService.remove(s);
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getInfo(){
		try {
			String pawnItemId=this.getRequest().getParameter("id");
			Map<String,Object> map=new HashMap<String,Object>();
			VPawnItemsList vPawnItemsList=(VPawnItemsList) creditBaseDao.getById(VPawnItemsList.class, Long.valueOf(pawnItemId));
			map.put("vPawnItemsList", vPawnItemsList);
			if(null!=typeid && typeid==1){
				List list=vehicleService.seeVehicleByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortCar vProjMortCar=(VProjMortCar) list.get(0);
					vProjMortCar.setDisplacementValue(dictionaryService.get(vProjMortCar.getDisplacement().longValue()).getItemValue());
					vProjMortCar.setSeatingValue(dictionaryService.get(vProjMortCar.getSeating().longValue()).getItemValue());
					map.put("vProjMortCar", vProjMortCar);
				}
			}else if(null!=typeid && typeid==2){
				List list=stockownershipService.seeStockownershipByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortStockOwnerShip vProjMortStockOwnerShip=(VProjMortStockOwnerShip) list.get(0);
					map.put("vProjMortStockOwnerShip", vProjMortStockOwnerShip);
				}
			}else if(null!=typeid && typeid==5){
				List list=machineinfoService.seeMachineinfoByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortMachineInfo vProjMortMachineInfo=(VProjMortMachineInfo) list.get(0);
					map.put("vProjMortMachineInfo", vProjMortMachineInfo);
				}
			}else if(null!=typeid && typeid==6){
				List list=productService.seeProductByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortProduct vProjMortProduct=(VProjMortProduct) list.get(0);
					map.put("vProjMortProduct", vProjMortProduct);
				}
			}else if(null!=typeid && (typeid==7 || typeid==15 || typeid==16 || typeid==17)){
				List list=houseService.getHouseByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortHouse vProjMortHouse=(VProjMortHouse) list.get(0);
					map.put("vProjMortHouse", vProjMortHouse);
				}
			}else if(null!=typeid && typeid==8){
				List list=officebuildingService.getOfficebuildingByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortOfficeBuilding vProjMortOfficeBuilding=(VProjMortOfficeBuilding) list.get(0);
					map.put("vProjMortOfficeBuilding", vProjMortOfficeBuilding);
				}
			}else if(null!=typeid && typeid==9){
				List list=housegroundService.getHousegroundByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortHouseGround vProjMortHouseGround=(VProjMortHouseGround) list.get(0);
					map.put("vProjMortHouseGround", vProjMortHouseGround);
				}
			}else if(null!=typeid && typeid==10){
				List list=businessServMort.getBusinessByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortBusiness vProjMortBusiness=(VProjMortBusiness) list.get(0);
					map.put("vProjMortBusiness", vProjMortBusiness);
				}
				
			}else if(null!=typeid && typeid==11){
				List list=businessandliveService.getBusinessandliveByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortBusAndLive vProjMortBusAndLive=(VProjMortBusAndLive) list.get(0);
					map.put("vProjMortBusAndLive", vProjMortBusAndLive);
				}
			}else if(null!=typeid && typeid==12){
				List list=educationService.getEducationByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortEducation vProjMortEducation=(VProjMortEducation) list.get(0);
					map.put("vProjMortEducation", vProjMortEducation);
				}
			}else if(null!=typeid && typeid==13){
				List list=industryService.getIndustryByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortIndustry vProjMortIndustry=(VProjMortIndustry) list.get(0);
					map.put("vProjMortIndustry", vProjMortIndustry);
				}
			}else if(null!=typeid && typeid==14){
				List list=droitService.getDroitByObjectType(Integer.valueOf(pawnItemId), "pawn");
				if(null!=list && list.size()>0){
					VProjMortDroit vProjMortDroit=(VProjMortDroit) list.get(0);
					map.put("vProjMortDroit", vProjMortDroit);
				}
			}
			StringBuffer buff=new StringBuffer("{success:true,data:");
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(map));
			buff.append("}");
			jsonString=buff.toString();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getPawnList(){
		List<PawnItemsList> list=pawnItemsListService.getListByProjectId(projectId, businessType);
		StringBuffer buff = new StringBuffer("[");
		for(PawnItemsList pawn:list){
			buff.append("["+pawn.getPawnItemId()+",'"+pawn.getPawnItemName()+"'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		jsonString=buff.toString();
		return SUCCESS;
	}
	public String getAllList(){
		try{
			String pawnItemStatus=this.getRequest().getParameter("pawnItemStatus");
			PagingBean pb=new PagingBean(start,limit);
			List<VPawnItemsList> vlist=pawnItemsListService.listByProjectId(pawnItemStatus, this.getRequest(), null);
			List<VPawnItemsList> list=pawnItemsListService.listByProjectId(pawnItemStatus, this.getRequest(), pb);
			StringBuffer buff=new StringBuffer("{success:true,totalCounts:");
			buff.append(vlist.size());
			buff.append(",result:");
			for(VPawnItemsList vp :list){
				List<SlProcreditMaterials> slist=slProcreditMaterialsService.getByProjId(vp.getPawnItemId().toString(), "Pawn", true);
				if(null!=slist && slist.size()>0){
					vp.setFileCount(slist.size());
				}
				PlPawnProject project=plPawnProjectService.get(vp.getProjectId());
				if(null!=project && null!=project.getCompanyId()){
					Organization org=organizationService.get(project.getCompanyId());
					if(null!=org){
						vp.setCompanyName(org.getOrgName());
					}
				}
				List<PawnCrkRecord> clist=pawnCrkRecordService.getListByPawnItemId(vp.getProjectId(), vp.getBusinessType(), vp.getPawnItemId());
				if(null!=clist && clist.size()>0){
					PawnCrkRecord record=clist.get(clist.size()-1);
					if(null!=record){
						vp.setCrkstatus(record.getOperateType());
						vp.setStorageLocation(record.getStorageLocation());
					}
				}
			}
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
			buff.append("}");
			jsonString=buff.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
}
