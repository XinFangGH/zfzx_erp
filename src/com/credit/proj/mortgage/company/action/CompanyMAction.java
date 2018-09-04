package com.credit.proj.mortgage.company.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgageEnterprise;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortCompany;
import com.credit.proj.mortgage.company.service.CompanyMService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.morservice.service.pageconfig;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;

/**
 * 
 * @author weipengfei
 * 
 */
@SuppressWarnings("all")
public class CompanyMAction extends CreditBaseAction {
	private static final long serialVersionUID = 1L;
	private CompanyMService companyMService;
	private MortgageService mortgageService;
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private FileFormService fileFormService;
	private ProcreditMortgage procreditMortgage;
	private ProcreditMortgageEnterprise procreditMortgageEnterprise;
	private VProcreditDictionary vProcreditDictionary;
	private VProjMortCompany vProjMortCompany;
	private Long projectId;
	private Integer mortgageid;
	//private Integer personType;
	//private Integer assuretypeid;
	private Integer customerEnterpriseName;//企业id
	private Integer legalpersonid;//企业表中的法人id===个人表id
	//private Integer customerPersonName;
	private Enterprise enterprise;
	private Person person;
	
	//private int processType;//1：企业贷款保前；2：二次放款；3：个人经营性贷款。
	
	private String businessType;//业务种类
	private String mfinancingId;
	

	/**
	 * 
	 * 添加信息
	 */
	public void addCompany() {
		try {
			//final String projId = JbpmUtil.piKeyToProjectId(projectId);
			procreditMortgage.setMortgagenametypeid(pageconfig.CompanyID);
			procreditMortgage.setProjid(projectId);
			if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("SmallLoan")){
			    procreditMortgage.setMortgageStatus("smallcblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Guarantee")){
			    procreditMortgage.setMortgageStatus("guaranteecblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Financing")){
			    procreditMortgage.setMortgageStatus("financingwbl");
			}
			procreditMortgage.setContractid(0);
			procreditMortgage.setAssureofname(customerEnterpriseName);
			procreditMortgage.setMortgagepersontypeforvalue(pageconfig.CompanyValue);
			//enterprise.setId(customerEnterpriseName);//把选择的企业的id放到实体
			//enterprise.setLegalpersonid(legalpersonid);//更新法人id
			//person.setId(legalpersonid);//把选择得到的个人id放到实体
			companyMService.addCompany(procreditMortgageEnterprise,procreditMortgage,enterprise,person,customerEnterpriseName,legalpersonid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 从反担保主表,责任公司表查询数据显示在更新页面
	 */
	
	public void seeCompanyForUpdate() throws Exception{
		Map<String, Object> mapObject = new HashMap<String, Object>();
		int rid=0;
		VProcreditDictionaryGuarantee vpg = null;
		VProcreditDictionaryFinance vpf = null;
		VProcreditMortgageLeaseFinance vmlf = null;
		
		int enterpriseId = 0;//企业id
		int legalpersonid = 0;//法人代表id
		EnterpriseView enterpriseView = null;
		
		
		
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
			enterpriseId = vProcreditDictionary.getAssureofname();//获取企业的id
		}else if("Guarantee".equals(businessType)){
			vpg = mortgageService.seeMortgageGuarantee(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpg.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpg.getId(),null,null,null);//获取 解除文件
			vpg.setHavingTransactFile(null!=list1&&list1.size()>0);
			vpg.setHavingUnchainFile(null!=list2&&list2.size()>0);
			enterpriseId = vpg.getAssureofname();//获取企业的id
		}else if("Financing".equals(businessType)){
			//vpf = mortgageService.seeMortgageFinance(rid);
			/*if(mfinancingId!=null&&!"".equals(mfinancingId)&&!"null".equals(mfinancingId)){
				vmf = mortgageService.getFinance(Integer.parseInt(mfinancingId));
				List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vmf.getId(),null,null,null);//获取办理文件
				List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vmf.getId(),null,null,null);//获取 解除文件
				vmf.setHavingTransactFile(null!=list1&&list1.size()>0);
				vmf.setHavingUnchainFile(null!=list2&&list2.size()>0);
				enterpriseId = vmf.getAssureofname();
			}else{*/
				vpf = mortgageService.seeMortgageFinance(rid);
				List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpf.getId(),null,null,null);//获取办理文件
				List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpf.getId(),null,null,null);//获取 解除文件
				vpf.setHavingTransactFile(null!=list1&&list1.size()>0);
				vpf.setHavingUnchainFile(null!=list2&&list2.size()>0);
				enterpriseId = vpf.getAssureofname();
			//}
		}else if("LeaseFinance".equals(businessType)){
			vmlf = mortgageService.seeMortgageLeaseFinance(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vmlf.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vmlf.getId(),null,null,null);//获取 解除文件
			vmlf.setHavingTransactFile(null!=list1&&list1.size()>0);
			vmlf.setHavingUnchainFile(null!=list2&&list2.size()>0);
		}
		
		List list=companyMService.seeCompany(rid);
		if(null!=list && list.size()!=0){
			//enterpriseId = vpg.getAssureofname();//获取企业的id
			vProjMortCompany=(VProjMortCompany)list.get(0);
			if("Financing".equals(businessType)){
				SlCompanyMain comMain=slCompanyMainService.get(new Long(enterpriseId));
				if(comMain!=null){
					Long personMainId = comMain.getPersonMainId();
					legalpersonid = Integer.parseInt(personMainId.toString());
					SlPersonMain perMain=slPersonMainService.get(personMainId);
					vProjMortCompany.setRegisteraddress(comMain.getSjjyAddress());
					vProjMortCompany.setLicensenumber(comMain.getBusinessCode());
					vProjMortCompany.setCorporate(comMain.getLawName());
					vProjMortCompany.setTelephone(comMain.getTel());
					if(perMain!=null){
						vProjMortCompany.setCorporatetel(perMain.getLinktel());
					}
				}
			}else{
				enterpriseView = companyMService.getEnterpriseObj(enterpriseId);
				if(enterpriseView != null){
					legalpersonid = enterpriseView.getLegalpersonid();
				}
			}
			
			vProjMortCompany.setLegalpersonid(legalpersonid);
			
			if("SmallLoan".equals(businessType)){
				mapObject.put("vProcreditDictionary", vProcreditDictionary);
			}else if("Guarantee".equals(businessType)){
				mapObject.put("vProcreditDictionary", vpg);
			}else if("Financing".equals(businessType)){
				//mapObject.put("vProcreditDictionary", vpf);
				/*if(mfinancingId!=null&&!"".equals(mfinancingId)&&!"null".equals(mfinancingId)){
					mapObject.put("vProcreditDictionary", vmf);
				}else{*/
					mapObject.put("vProcreditDictionary", vpf);
				//}
			}else if("LeaseFinance".equals(businessType)){
				mapObject.put("vProcreditDictionary", vmlf);
			}
			mapObject.put("vProjMortCompany", vProjMortCompany);
			JsonUtil.jsonFromObject(mapObject, true);
		}
	}
	
	/**
	 * 
	 * 更新记录
	 */
	public void updateCompany(){
		
		procreditMortgage.setAssureofname(customerEnterpriseName);
		enterprise.setId(customerEnterpriseName);//把选择的企业的id放到实体
		enterprise.setLegalpersonid(legalpersonid);//更新法人id
		//person.setId(legalpersonid);//把选择得到的个人id放到实体
		
		try {
			companyMService.updateCompany(mortgageid,procreditMortgageEnterprise,procreditMortgage,enterprise,person,legalpersonid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public CompanyMService getCompanyMService() {
		return companyMService;
	}

	public void setCompanyMService(CompanyMService companyMService) {
		this.companyMService = companyMService;
	}

	public ProcreditMortgage getProcreditMortgage() {
		return procreditMortgage;
	}

	public void setProcreditMortgage(ProcreditMortgage procreditMortgage) {
		this.procreditMortgage = procreditMortgage;
	}

	public ProcreditMortgageEnterprise getProcreditMortgageEnterprise() {
		return procreditMortgageEnterprise;
	}

	public void setProcreditMortgageEnterprise(
			ProcreditMortgageEnterprise procreditMortgageEnterprise) {
		this.procreditMortgageEnterprise = procreditMortgageEnterprise;
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

	public VProjMortCompany getvProjMortCompany() {
		return vProjMortCompany;
	}

	public void setvProjMortCompany(VProjMortCompany vProjMortCompany) {
		this.vProjMortCompany = vProjMortCompany;
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


	public Integer getLegalpersonid() {
		return legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
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
