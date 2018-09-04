package com.zhiwei.credit.action.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import antlr.RecognitionException;
import antlr.TokenStreamException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.mapper.MapperException;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.supplior.FlObjectSupplior;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseFinanceInsuranceInfoService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfoService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfoService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.supplior.FlObjectSuppliorService;
import com.zhiwei.credit.service.system.AppUserService;
/**
 * 
 * @author 
 *
 */
public class FlLeaseobjectInfoAction extends BaseAction{
	@Resource
	private FlLeaseobjectInfoService flLeaseobjectInfoService;
	@Resource
	private FlObjectSuppliorService flObjectSuppliorService;
	@Resource
	private VLeaseFinanceObjectInfoService vLeaseFinanceObjectInfoService;
	@Resource
	private FlLeaseFinanceInsuranceInfoService flLeaseFinanceInsuranceInfoService;
	@Resource
	private AppUserService appUserService;
	
	
	private FlLeaseobjectInfo flLeaseobjectInfo;
	private FlObjectSupplior flObjectSupplior;
	
	private Long id;
	private Long projectId;
	
	
	
	
	

	public FlObjectSupplior getFlObjectSupplior() {
		return flObjectSupplior;
	}

	public void setFlObjectSupplior(FlObjectSupplior flObjectSupplior) {
		this.flObjectSupplior = flObjectSupplior;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FlLeaseobjectInfo getFlLeaseobjectInfo() {
		return flLeaseobjectInfo;
	}

	public void setFlLeaseobjectInfo(FlLeaseobjectInfo flLeaseobjectInfo) {
		this.flLeaseobjectInfo = flLeaseobjectInfo;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FlLeaseobjectInfo> list= flLeaseobjectInfoService.getAll(filter);
		
		Type type=new TypeToken<List<FlLeaseobjectInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	public String listView(){
		
/*		QueryFilter filter=new QueryFilter(getRequest());
		List<VLeaseFinanceObjectInfo> list= vLeaseFinanceObjectInfoService.getAll(filter);
		
		Type type=new TypeToken<List<VLeaseFinanceObjectInfo>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();*/
		List<VLeaseFinanceObjectInfo> list=vLeaseFinanceObjectInfoService.getListByProjectId(projectId);
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	public String listAllView(){
		String projectName="";
		String projectNumber="";
		String standardSize = "";
		String owner = "";
		String companyId = "";
		boolean isManaged = false;//是否办理    默认为未处理
		boolean isHandled = false;//是否处置    默认为未处置
		boolean isBuyBack = false;//是否回购   默认为未回购
		QueryFilter filter=new QueryFilter(getRequest());
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		if(null!=filter.getRequest().getParameter("isManaged")){
			isManaged=Boolean.valueOf(filter.getRequest().getParameter("isManaged"));
		}
		if(null!=filter.getRequest().getParameter("isHandled")){
			isHandled=Boolean.valueOf(filter.getRequest().getParameter("isHandled"));
		}
		if(null!=filter.getRequest().getParameter("isBuyBack")){
			isBuyBack=Boolean.valueOf(filter.getRequest().getParameter("isBuyBack"));
		}
		if(null!=filter.getRequest().getParameter("projectName")){
			projectName=filter.getRequest().getParameter("projectName");
		}
		if(null!=filter.getRequest().getParameter("projectNumber")){
			projectNumber = filter.getRequest().getParameter("projectNumber");
		}
		if(null!=filter.getRequest().getParameter("projectNumber")){
			projectNumber = filter.getRequest().getParameter("projectNumber");
		}
		if(null!=filter.getRequest().getParameter("standardSize")){
			standardSize = filter.getRequest().getParameter("standardSize");
		}
		if(null!=filter.getRequest().getParameter("owner")){
			owner = filter.getRequest().getParameter("owner");
		}
		if(null!=filter.getRequest().getParameter("companyId")){
			companyId = filter.getRequest().getParameter("companyId");
		}
//        List list = mortgageService.getMortgageList(businessType,mortgageName,projectName,projectNum,mortgageType,danbaoType,minMoney,maxMoney,start,limit,0,mortgageStatus,companyId);
//
//        List totalList = mortgageService.getMortgageList(businessType,mortgageName,projectName,projectNum,mortgageType,danbaoType,minMoney,maxMoney,start,limit,1,mortgageStatus,companyId);
        
		List<VLeaseFinanceObjectInfo> list=vLeaseFinanceObjectInfoService.getVLeaseFinanceObjectInfoList(start,limit,projectName,projectNumber,standardSize,owner,companyId,isManaged,isHandled,isBuyBack);
		StringBuffer buff = new StringBuffer("{success:true,result:");
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list));
		buff.append(",totalCounts:"+list.size());
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[] ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				flLeaseobjectInfoService.remove(new Long(id));
				flLeaseFinanceInsuranceInfoService.deleteByLeaseObjectId(new Long(id));
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
		FlLeaseobjectInfo flLeaseobjectInfo = flLeaseobjectInfoService.get(id);
		FlObjectSupplior flObjectSupplior = flObjectSuppliorService.get(flLeaseobjectInfo.getSuppliorId());
//		Map map = new HashMap();
//		map.put("flLeaseobjectInfo", flLeaseobjectInfo);
//		map.put("flObjectSupplior", flObjectSupplior);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		StringBuffer sb = new StringBuffer("{success:true,data:{\"flLeaseobjectInfo\":");//flLeaseobjectInfo
		sb.append(gson.toJson(flLeaseobjectInfo));
		sb.append(",\"flObjectSupplior\":");//flObjectSupplior:
		sb.append(gson.toJson(flObjectSupplior));
//		sb.append(gson.toJson(map));
		sb.append("}}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String flLeaseFinanceInsuranceInfo = this.getRequest().getParameter("flLeaseFinanceInsuranceInfo");
		FlLeaseobjectInfo flLeaseobjectInfo1 = flLeaseobjectInfoService.updateFlLeaseFinanceObjectInfo(flLeaseobjectInfo, flObjectSupplior, projectId);
		flLeaseobjectInfoService.merge(flLeaseobjectInfo1);//save 不可以   
		try {
			if (flLeaseFinanceInsuranceInfo!=null&&!"".equals(flLeaseFinanceInsuranceInfo)) {
				String[] insuranceyArr = flLeaseFinanceInsuranceInfo.split("@");
				for(int i=0; i<insuranceyArr.length;i++) {
					String str = insuranceyArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					FlLeaseFinanceInsuranceInfo flLeaseFinanceInsuranceInfo1 = (FlLeaseFinanceInsuranceInfo) JSONMapper.toJava(parser.nextValue(),FlLeaseFinanceInsuranceInfo.class);
					flLeaseFinanceInsuranceInfo1.setLeaseObjectId(flLeaseobjectInfo1.getId());
					flLeaseFinanceInsuranceInfoService.save(flLeaseFinanceInsuranceInfo1);
				}
			}
		} catch (TokenStreamException e) {
			e.printStackTrace();
		} catch (RecognitionException e) {
			e.printStackTrace();
		} catch (MapperException e) {
			e.printStackTrace();
		}
		
/*		if(flLeaseobjectInfo.getId()==null){
			flLeaseobjectInfoService.save(flLeaseobjectInfo);
		}else{
			FlLeaseobjectInfo orgFlLeaseobjectInfo=flLeaseobjectInfoService.get(flLeaseobjectInfo.getId());
			try{
				BeanUtil.copyNotNullProperties(orgFlLeaseobjectInfo, flLeaseobjectInfo);
				flLeaseobjectInfoService.save(orgFlLeaseobjectInfo);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}*/
		setJsonString("{success:true,objectId:"+flLeaseobjectInfo1.getId()+"}");
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String update(){
		FlLeaseobjectInfo persistent = flLeaseobjectInfoService.get(flLeaseobjectInfo.getId());
		try {
			//已处置isHandled，或者已办理isBuyBack    则必须为已处理isManaged
			/*if(null!=flLeaseobjectInfo.getIsHandled()?flLeaseobjectInfo.getIsHandled():false|| 
					null!=flLeaseobjectInfo.getIsBuyBack()?flLeaseobjectInfo.getIsBuyBack():false){
				flLeaseobjectInfo.setIsManaged(true);
			}*/
			BeanUtil.copyNotNullProperties(persistent, flLeaseobjectInfo);
			flLeaseobjectInfoService.save(persistent);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String seeObjectInfo(){
		try {
			Map<String,Object> map=new HashMap<String,Object>();
			VLeaseFinanceObjectInfo vLeaseFinanceObjectInfo= vLeaseFinanceObjectInfoService.get(id);
			map.put("vLeaseFinanceObjectInfo", vLeaseFinanceObjectInfo);
			
			Long suppliorId = vLeaseFinanceObjectInfo.getSuppliorId();
			FlObjectSupplior flObjectSupplior = flObjectSuppliorService.get(suppliorId);
			map.put("flObjectSupplior", flObjectSupplior);
			/*if(null!=typeid && typeid==1){
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
			}*/
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
	
}
