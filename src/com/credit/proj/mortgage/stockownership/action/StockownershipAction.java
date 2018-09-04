package com.credit.proj.mortgage.stockownership.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageStockownership;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortStockFinance;
import com.credit.proj.entity.VProjMortStockOwnerShip;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.morservice.service.pageconfig;
import com.credit.proj.mortgage.stockownership.service.StockownershipService;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;

/**
 * 
 * @author weipengfei
 * 
 */
@SuppressWarnings("all")
public class StockownershipAction extends CreditBaseAction {
	private static final long serialVersionUID = 1L;
	private StockownershipService stockownershipService;
	private MortgageService mortgageService;
	private ProcreditMortgage procreditMortgage;
	private ProcreditMortgageStockownership procreditMortgageStockownership;
	private VProcreditDictionary vProcreditDictionary;
	private VProjMortStockOwnerShip vProjMortStockOwnerShip;
	private Long projectId;
	private Integer mortgageid;
	//private Integer personType;
	//private Integer assuretypeid;
	private Integer customerEnterpriseName;
	private Integer customerPersonName;
	private Enterprise enterprise;
	private Integer targetEnterpriseName;
	
	//private int processType;//1：企业贷款保前；2：二次放款；3：个人经营性贷款。
	private String businessType;//业务种类
	private String mfinancingId;
	@Resource
	private FileFormService fileFormService;

	/**
	 * 
	 * 添加股贷信息
	 */
	public void addStockownership() {
		try {
			//final String projId = JbpmUtil.piKeyToProjectId(projectId);
			procreditMortgage.setMortgagenametypeid(pageconfig.StockownershipID);
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
			enterprise.setId(targetEnterpriseName);
			procreditMortgageStockownership.setCorporationname(targetEnterpriseName);
			procreditMortgageStockownership.setBusinessType(procreditMortgage.getBusinessType());
			procreditMortgage.setMortgagepersontypeforvalue(pageconfig.StockownershipValue);
			stockownershipService.addStockownership(procreditMortgageStockownership,procreditMortgage,enterprise);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从反担保主表,股权表查询数据显示在更新页面
	 */
	
	public void seeStockownershipForUpdate() throws Exception{
		Map<String, Object> mapObject = new HashMap<String, Object>();
		VProjMortStockOwnerShip vProjMortStockOwnerShip = null;//小额、担保抵质押物视图-股权
		VProjMortStockFinance vProjMortStockFinance = null;//融资抵质押物视图-股权
		VProcreditDictionaryFinance vpf = null;
		VProcreditMortgageLeaseFinance vmlf = null;
		
		int rid=0;
		VProcreditDictionaryGuarantee vpg = null;
		if(getRequest().getParameter("id")!=null){
			rid=Integer.parseInt(getRequest().getParameter("id"));
		}
		ProcreditMortgage pro = mortgageService.seeMortgage(ProcreditMortgage.class, rid);
		/*if(pro!=null){
			short isPledged = pro.getIsPledged();
			if(isPledged==1){
				SlMortgageFinancing sl = slMortgageFinancingService.getMortgageByMortId(rid);
				if(sl!=null){
					mfinancingId = sl.getMfinancingId().toString();
				}
			}
		}*/
		if("SmallLoan".equals(businessType)){
			vProcreditDictionary=mortgageService.seeMortgage(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vProcreditDictionary.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vProcreditDictionary.getId(),null,null,null);//获取 解除文件
			vProcreditDictionary.setHavingTransactFile(null!=list1&&list1.size()>0);
			vProcreditDictionary.setHavingUnchainFile(null!=list2&&list2.size()>0);
			vProjMortStockOwnerShip = stockownershipService.seeStockownershipFromView(rid);
			
			mapObject.put("vProcreditDictionary", vProcreditDictionary);
			mapObject.put("vProjMortStockOwnerShip", vProjMortStockOwnerShip);
			
		}else if("Guarantee".equals(businessType)){
			vpg = mortgageService.seeMortgageGuarantee(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpg.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpg.getId(),null,null,null);//获取 解除文件
			vpg.setHavingTransactFile(null!=list1&&list1.size()>0);
			vpg.setHavingUnchainFile(null!=list2&&list2.size()>0);
			vProjMortStockOwnerShip = stockownershipService.seeStockownershipFromView(rid);
			
			mapObject.put("vProcreditDictionary", vpg);
			mapObject.put("vProjMortStockOwnerShip", vProjMortStockOwnerShip);
			
		}else if("Financing".equals(businessType)){
			
			/*if(mfinancingId!=null&&!"".equals(mfinancingId)&&!"null".equals(mfinancingId)){
				vmf = mortgageService.getFinance(Integer.parseInt(mfinancingId));
				List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vmf.getId(),null,null,null);//获取办理文件
				List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vmf.getId(),null,null,null);//获取 解除文件
				vmf.setHavingTransactFile(null!=list1&&list1.size()>0);
				vmf.setHavingUnchainFile(null!=list2&&list2.size()>0);
				mapObject.put("vProcreditDictionary", vmf);
			}else{*/
				vpf = mortgageService.seeMortgageFinance(rid);
				List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpf.getId(),null,null,null);//获取办理文件
				List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpf.getId(),null,null,null);//获取 解除文件
				vpf.setHavingTransactFile(null!=list1&&list1.size()>0);
				vpf.setHavingUnchainFile(null!=list2&&list2.size()>0);
				mapObject.put("vProcreditDictionary", vpf);
			//}
			
			vProjMortStockFinance = stockownershipService.getStockownershipFinance(rid);
			mapObject.put("vProjMortStockOwnerShip", vProjMortStockFinance);
			/*vpf = mortgageService.seeMortgageFinance(rid);
			vProjMortStockFinance = stockownershipService.getStockownershipFinance(rid);
			
			mapObject.put("vProcreditDictionary", vpf);
			mapObject.put("vProjMortStockOwnerShip", vProjMortStockFinance);*/
		}else if("LeaseFinance".equals(businessType)){//★抵押★
			vmlf = mortgageService.seeMortgageLeaseFinance(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vmlf.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vmlf.getId(),null,null,null);//获取 解除文件
			vmlf.setHavingTransactFile(null!=list1&&list1.size()>0);
			vmlf.setHavingUnchainFile(null!=list2&&list2.size()>0);
			vProjMortStockOwnerShip = stockownershipService.seeStockownershipFromView(rid);
			mapObject.put("vProcreditDictionary", vmlf);
			mapObject.put("vProjMortStockOwnerShip", vProjMortStockOwnerShip);
		}
		
		JsonUtil.jsonFromObject(mapObject, true);
	}
	
	/**
	 * 
	 * 更新记录
	 */
	public void updateStockownership(){
		enterprise.setId(targetEnterpriseName);
		procreditMortgageStockownership.setCorporationname(targetEnterpriseName);
		if(null !=customerEnterpriseName){
			procreditMortgage.setAssureofname(customerEnterpriseName);
		}else{
			procreditMortgage.setAssureofname(customerPersonName);
		}
		try {
			stockownershipService.updateStockownership(mortgageid,procreditMortgageStockownership,procreditMortgage,enterprise);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public StockownershipService getStockownershipService() {
		return stockownershipService;
	}

	public void setStockownershipService(StockownershipService stockownershipService) {
		this.stockownershipService = stockownershipService;
	}

	public ProcreditMortgage getProcreditMortgage() {
		return procreditMortgage;
	}

	public void setProcreditMortgage(ProcreditMortgage procreditMortgage) {
		this.procreditMortgage = procreditMortgage;
	}

	public ProcreditMortgageStockownership getProcreditMortgageStockownership() {
		return procreditMortgageStockownership;
	}

	public void setProcreditMortgageStockownership(
			ProcreditMortgageStockownership procreditMortgageStockownership) {
		this.procreditMortgageStockownership = procreditMortgageStockownership;
	}



	public MortgageService getMortgageService() {
		return mortgageService;
	}

	public void setMortgageService(MortgageService mortgageService) {
		this.mortgageService = mortgageService;
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

	public VProjMortStockOwnerShip getvProjMortStockOwnerShip() {
		return vProjMortStockOwnerShip;
	}

	public void setvProjMortStockOwnerShip(
			VProjMortStockOwnerShip vProjMortStockOwnerShip) {
		this.vProjMortStockOwnerShip = vProjMortStockOwnerShip;
	}


	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Integer getMortgageid() {
		return mortgageid;
	}

	public void setMortgageid(Integer mortgageid) {
		this.mortgageid = mortgageid;
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

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Integer getTargetEnterpriseName() {
		return targetEnterpriseName;
	}

	public void setTargetEnterpriseName(Integer targetEnterpriseName) {
		this.targetEnterpriseName = targetEnterpriseName;
	}

	public String getMfinancingId() {
		return mfinancingId;
	}

	public void setMfinancingId(String mfinancingId) {
		this.mfinancingId = mfinancingId;
	}
	
}
