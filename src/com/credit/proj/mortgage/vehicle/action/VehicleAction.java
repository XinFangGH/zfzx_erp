package com.credit.proj.mortgage.vehicle.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageCar;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortCar;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.morservice.service.pageconfig;
import com.credit.proj.mortgage.vehicle.service.VehicleService;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author weipengfei
 * 
 */
@SuppressWarnings("all")
public class VehicleAction extends CreditBaseAction {
	private static final long serialVersionUID = 1L;
	private VehicleService vehicleService;
	private MortgageService mortgageService;
	private ProcreditMortgage procreditMortgage;
	private ProcreditMortgageCar procreditMortgageCar;
	private VProcreditDictionary vProcreditDictionary;
	private VProjMortCar vProjMortCar;
	private Long projectId;
	private Integer mortgageid;
	//private Integer personType;
	//private Integer assuretypeid;
	
	private Integer customerEnterpriseName;//客户名称-企业id
	private Integer customerPersonName;//客户名称-个人id
	private Integer manufacturer;//制造商id====cs_process_car表的id
	private String query;
	private int start;
	private int limit;
	
	//private int processType;//1：企业贷款保前；2：二次放款；3：个人经营性贷款。
	
	private String businessType;//业务种类
	private String mfinancingId;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private FileFormService fileFormService;
	//制造商查询
	public void ajaxQueryCarFactoryForCombo(){
		vehicleService.ajaxQueryCarFactoryForCombo(query, start, limit);
	}

	/**
	 * 
	 * 添加车贷信息
	 */
	public void addVehicle() {
		try {
			//final String projId = JbpmUtil.piKeyToProjectId(projectId);
			procreditMortgage.setMortgagenametypeid(pageconfig.VehicleID);
			procreditMortgage.setProjid(projectId);
			if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("SmallLoan")){
			    procreditMortgage.setMortgageStatus("smallcblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Guarantee")){
			    procreditMortgage.setMortgageStatus("guaranteecblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Financing")){
			    procreditMortgage.setMortgageStatus("financingwbl");
			}
			if(null !=customerEnterpriseName){
				procreditMortgage.setAssureofname(customerEnterpriseName);
			}else{
				procreditMortgage.setAssureofname(customerPersonName);
			}
			procreditMortgage.setContractid(0);
			if(manufacturer==null||"".equals(manufacturer)||"null".equals(manufacturer)){
				procreditMortgageCar.setManufacturer(0);
			}else{
				procreditMortgageCar.setManufacturer(manufacturer);
			}
			procreditMortgage.setMortgagepersontypeforvalue(pageconfig.VehicleValue);
			vehicleService.addVehicle(procreditMortgageCar, procreditMortgage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从反担保主表,车辆表查询数据显示在更新页面
	 */
	
	public void seeVehicleForUpdate() throws Exception{
		Map<String, Object> mapObject = new HashMap<String, Object>();
		int rid=0;
		
		VProcreditDictionaryGuarantee vpg = null;
		VProcreditDictionaryFinance vpf = null;
		VProcreditMortgageLeaseFinance vmlf = null;
		
		int manufacturer = 0;//制造商id
		
		if(getRequest().getParameter("id")!=null){
			rid=Integer.parseInt(getRequest().getParameter("id"));
		}
		ProcreditMortgage pro = mortgageService.seeMortgage(ProcreditMortgage.class, rid);
		if("SmallLoan".equals(businessType)){
			vProcreditDictionary=mortgageService.seeMortgage(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vProcreditDictionary.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vProcreditDictionary.getId(),null,null,null);//获取 解除文件
			vProcreditDictionary.setHavingTransactFile(null!=list1&&list1.size()>0);
			vProcreditDictionary.setHavingUnchainFile(null!=list2&&list2.size()>0);
		}else if("Guarantee".equals(businessType)){
			vpg = mortgageService.seeMortgageGuarantee(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpg.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpg.getId(),null,null,null);//获取 解除文件
			vpg.setHavingTransactFile(null!=list1&&list1.size()>0);
			vpg.setHavingUnchainFile(null!=list2&&list2.size()>0);
		}else if("Financing".equals(businessType)){
				vpf = mortgageService.seeMortgageFinance(rid);
				List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpf.getId(),null,null,null);//获取办理文件
				List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpf.getId(),null,null,null);//获取 解除文件
				vpf.setHavingTransactFile(null!=list1&&list1.size()>0);
				vpf.setHavingUnchainFile(null!=list2&&list2.size()>0);
		}else if("LeaseFinance".equals(businessType)){
			vmlf = mortgageService.seeMortgageLeaseFinance(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vmlf.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vmlf.getId(),null,null,null);//获取 解除文件
			vmlf.setHavingTransactFile(null!=list1&&list1.size()>0);
			vmlf.setHavingUnchainFile(null!=list2&&list2.size()>0);
		}

		//获取唯一的值
		List list=vehicleService.seeVehicle(rid);
		if(null!=list && list.size()!=0){
			vProjMortCar=(VProjMortCar)list.get(0);
			if(vProjMortCar.getDisplacement()!=null)
			vProjMortCar.setDisplacementValue(dictionaryService.get(vProjMortCar.getDisplacement().longValue()).getItemValue());
			if(vProjMortCar.getSeating()!=null)
			vProjMortCar.setSeatingValue(dictionaryService.get(vProjMortCar.getSeating().longValue()).getItemValue());
			if("SmallLoan".equals(businessType)){
				mapObject.put("vProcreditDictionary", vProcreditDictionary);
			}else if("Guarantee".equals(businessType)){
				mapObject.put("vProcreditDictionary", vpg);
			}else if("Financing".equals(businessType)){
					mapObject.put("vProcreditDictionary", vpf);
			}else if("LeaseFinance".equals(businessType)){//★抵押★
				mapObject.put("vProcreditDictionary", vmlf);
			}
			mapObject.put("vProjMortCar", vProjMortCar);
			JsonUtil.jsonFromObject(mapObject, true);
		}
	}
	/**
	 * 
	 * 更新记录
	 */
	public void updateVehicle(){
		
		if(null !=customerEnterpriseName){
			procreditMortgage.setAssureofname(customerEnterpriseName);
		}else{
			procreditMortgage.setAssureofname(customerPersonName);
		}
		procreditMortgageCar.setManufacturer(manufacturer);
		try {
			vehicleService.updateVehicle(mortgageid, procreditMortgageCar, procreditMortgage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//根据制造商id查询相应信息
	/*public void ajaxGetProcessCarData(){
		vehicleService.ajaxGetProcessCarData(manufacturer);
	}*/
	
	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public ProcreditMortgage getProcreditMortgage() {
		return procreditMortgage;
	}

	public void setProcreditMortgage(ProcreditMortgage procreditMortgage) {
		this.procreditMortgage = procreditMortgage;
	}

	public ProcreditMortgageCar getProcreditMortgageCar() {
		return procreditMortgageCar;
	}

	public void setProcreditMortgageCar(
			ProcreditMortgageCar procreditMortgageCar) {
		this.procreditMortgageCar = procreditMortgageCar;
	}



	public MortgageService getMortgageService() {
		return mortgageService;
	}

	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
	}
	
	public VProjMortCar getvProjMortCar() {
		return vProjMortCar;
	}

	public void setvProjMortCar(VProjMortCar vProjMortCar) {
		this.vProjMortCar = vProjMortCar;
	}

	public Integer getMortgageid() {
		return mortgageid;
	}

	public void setMortgageid(Integer mortgageid) {
		this.mortgageid = mortgageid;
	}

	/**
	 * ---------------方法-----------------
	 */

	public VProcreditDictionary getvProcreditDictionary() {
		return vProcreditDictionary;
	}

	public void setvProcreditDictionary(VProcreditDictionary vProcreditDictionary) {
		this.vProcreditDictionary = vProcreditDictionary;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getCustomerEnterpriseName() {
		return customerEnterpriseName;
	}

	public void setCustomerEnterpriseName(Integer customerEnterpriseName) {
		this.customerEnterpriseName = customerEnterpriseName;
	}

	public Integer getCustomerPersonName() {
		return customerPersonName;
	}

	public void setCustomerPersonName(Integer customerPersonName) {
		this.customerPersonName = customerPersonName;
	}

	
	
	public Integer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getMfinancingId() {
		return mfinancingId;
	}

	public void setMfinancingId(String mfinancingId) {
		this.mfinancingId = mfinancingId;
	}

}
