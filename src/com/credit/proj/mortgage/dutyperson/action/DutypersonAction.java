package com.credit.proj.mortgage.dutyperson.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.credit.proj.entity.ProcreditMortgage;
import com.credit.proj.entity.ProcreditMortgagePerson;
import com.credit.proj.entity.VProcreditDictionary;
import com.credit.proj.entity.VProcreditDictionaryFinance;
import com.credit.proj.entity.VProcreditDictionaryGuarantee;
import com.credit.proj.entity.VProcreditMortgageLeaseFinance;
import com.credit.proj.entity.VProjMortDutyPerson;
import com.credit.proj.mortgage.dutyperson.service.DutypersonService;
import com.credit.proj.mortgage.morservice.service.MortgageService;
import com.credit.proj.mortgage.morservice.service.pageconfig;
import com.zhiwei.credit.core.commons.CreditBaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author weipengfei
 * 
 */
@SuppressWarnings("all")
public class DutypersonAction extends CreditBaseAction {
	private static final long serialVersionUID = 1L;
	private DutypersonService dutypersonService;
	private MortgageService mortgageService;
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private FileFormService fileFormService;
	private ProcreditMortgage procreditMortgage;
	private ProcreditMortgagePerson procreditMortgagePerson;
	private VProcreditDictionary vProcreditDictionary;
	private VProjMortDutyPerson vProjMortDutyPerson;
	private Long projectId;
	private Integer mortgageid;
	private Integer personType;
	//private Integer assuretypeid;
	//private Integer customerEnterpriseName;
	private Integer customerPersonName;
	private Person person;
	private Integer cardtype;
	
	//private int processType;//1：企业贷款保前；2：二次放款；3：个人经营性贷款。
	
	private String businessType;//业务种类
	private String mfinancingId;
	/**
	 * 
	 * 添加信息
	 */
	public void addDutyperson() {
		try {
			//final String projId = JbpmUtil.piKeyToProjectId(projectId);
			procreditMortgage.setMortgagenametypeid(pageconfig.PersonID);
			procreditMortgage.setProjid(projectId);
			if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("SmallLoan")){
			    procreditMortgage.setMortgageStatus("smallcblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Guarantee")){
			    procreditMortgage.setMortgageStatus("guaranteecblr");
			}else if(null!=procreditMortgage.getBusinessType() &&  procreditMortgage.getBusinessType().equals("Financing")){
			    procreditMortgage.setMortgageStatus("financingwbl");
			}
			procreditMortgage.setContractid(0);
			procreditMortgage.setAssureofname(customerPersonName);
			//procreditMortgage.setPersonType(personType);
			//person.setCardtype(cardtype);
			//person.setId(customerPersonName);
			procreditMortgage.setMortgagepersontypeforvalue(pageconfig.PersonValue);
			dutypersonService.addDutyperson(procreditMortgagePerson,procreditMortgage,person,customerPersonName,cardtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 从反担保主表,车辆表查询数据显示在更新页面
	 */
	
	public void seeDutypersonForUpdate() throws Exception{
		Map<String, Object> mapObject = new HashMap<String, Object>();
		int rid=0;
		int enterpriseId = 0;
		VProcreditDictionaryGuarantee vpg = null;
		VProcreditDictionaryFinance vpf = null;
		VProcreditMortgageLeaseFinance vmlf = null;
		
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
			enterpriseId = vProcreditDictionary.getAssureofname();
		}else if("Guarantee".equals(businessType)){
			vpg = mortgageService.seeMortgageGuarantee(rid);
			List<FileForm> list1 = fileFormService.getFileList("cs_procredit_mortgage."+vpg.getId(),null,null,null);//获取办理文件
			List<FileForm> list2 = fileFormService.getFileList("cs_procredit_mortgage_jc."+vpg.getId(),null,null,null);//获取 解除文件
			vpg.setHavingTransactFile(null!=list1&&list1.size()>0);
			vpg.setHavingUnchainFile(null!=list2&&list2.size()>0);
			enterpriseId = vpg.getAssureofname();
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
		
		List list=dutypersonService.seeDutyperson(rid);
		if(null!=list && list.size()!=0){
			vProjMortDutyPerson=(VProjMortDutyPerson)list.get(0);
			
			if("Financing".equals(businessType)){
				SlPersonMain slPerson = slPersonMainService.get(new Long(enterpriseId));
				if(slPerson!=null){
					if(slPerson.getCardtype()!=null&&!"".equals(slPerson.getCardtype())){
						Dictionary dicc=dictionaryService.get(slPerson.getCardtype().longValue());
						vProjMortDutyPerson.setCardtypevalue(dicc.getItemValue());
						vProjMortDutyPerson.setCardtype(slPerson.getCardtype().intValue());
						
					}
					vProjMortDutyPerson.setZhusuo(slPerson.getHome());
					vProjMortDutyPerson.setIdcard(slPerson.getCardnum());
					vProjMortDutyPerson.setPhone(slPerson.getLinktel());
				}
			}
			
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
			}else if("LeaseFinance".equals(businessType)){//★抵押★
				mapObject.put("vProcreditDictionary", vmlf);
			}
			mapObject.put("vProjMortDutyPerson", vProjMortDutyPerson);
			JsonUtil.jsonFromObject(mapObject, true);
		}
	}
	
	/**
	 * 
	 * 更新记录
	 */
	public void updateDutyperson(){
		procreditMortgage.setAssureofname(customerPersonName);
		//procreditMortgage.setPersonType(personType);
		//person.setCardtype(cardtype);
		//person.setId(customerPersonName);
		try {
			dutypersonService.updateDutyperson(mortgageid,procreditMortgagePerson,procreditMortgage,person,customerPersonName,cardtype);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public DutypersonService getDutypersonService() {
		return dutypersonService;
	}

	public void setDutypersonService(DutypersonService dutypersonService) {
		this.dutypersonService = dutypersonService;
	}

	public ProcreditMortgage getProcreditMortgage() {
		return procreditMortgage;
	}

	public void setProcreditMortgage(ProcreditMortgage procreditMortgage) {
		this.procreditMortgage = procreditMortgage;
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

	public ProcreditMortgagePerson getProcreditMortgagePerson() {
		return procreditMortgagePerson;
	}

	public void setProcreditMortgagePerson(
			ProcreditMortgagePerson procreditMortgagePerson) {
		this.procreditMortgagePerson = procreditMortgagePerson;
	}

	public void setvProcreditDictionary(VProcreditDictionary vProcreditDictionary) {
		this.vProcreditDictionary = vProcreditDictionary;
	}

	public VProjMortDutyPerson getvProjMortDutyPerson() {
		return vProjMortDutyPerson;
	}

	public void setvProjMortDutyPerson(VProjMortDutyPerson vProjMortDutyPerson) {
		this.vProjMortDutyPerson = vProjMortDutyPerson;
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


	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public Integer getCustomerPersonName() {
		return customerPersonName;
	}

	public void setCustomerPersonName(Integer customerPersonName) {
		this.customerPersonName = customerPersonName;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
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
