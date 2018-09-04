package com.zhiwei.credit.action.creditFlow.contract;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.contract.ProcreditContract;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.service.creditFlow.contract.ElementHandleService;
import com.zhiwei.credit.service.creditFlow.contract.ProcreditContractService;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;

public class ProcreditContractAction extends BaseAction {
	@Resource
	private ElementHandleService elementHandleService;
	@Resource
	private ProcreditContractService procreditContractService;
	@Resource
	private VProcreditContractService vProcreditContractService;
	private String taskId ;
	private String projId ;
	private int typeId ;
	private String onlymark ;
	private String contractExplain;
	private List arrayValue ;
	private String templateId;
	private String contractType;
	private String categoryId;
	private String contractId;
	private String mortgageId;
	private String businessType;
	private String htType;
	private int isApply;
	private String type;
	private ProcreditContract procreditContract;
	
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ProcreditContract getProcreditContract() {
		return procreditContract;
	}
	public void setProcreditContract(ProcreditContract procreditContract) {
		this.procreditContract = procreditContract;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public String getOnlymark() {
		return onlymark;
	}
	public void setOnlymark(String onlymark) {
		this.onlymark = onlymark;
	}
	public String getContractExplain() {
		return contractExplain;
	}
	public void setContractExplain(String contractExplain) {
		this.contractExplain = contractExplain;
	}
	public List getArrayValue() {
		return arrayValue;
	}
	public void setArrayValue(List arrayValue) {
		this.arrayValue = arrayValue;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getContractId() {
		return contractId;
	}
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}
	public String getMortgageId() {
		return mortgageId;
	}
	public void setMortgageId(String mortgageId) {
		this.mortgageId = mortgageId;
	}
	public int getIsApply() {
		return isApply;
	}
	public void setIsApply(int isApply) {
		this.isApply = isApply;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getHtType() {
		return htType;
	}
	public void setHtType(String htType) {
		this.htType = htType;
	}
	public String getContractTree(){
		try{
			
			List<VProcreditContract> list=vProcreditContractService.listByHtType(businessType, projId, htType,type);
			JsonUtil.jsonFromList(list);
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public void deleteContractCategoryRecord(){
		String url = super.getRequest().getRealPath("/");
		try {
			vProcreditContractService.deleteContractRecord(url,contractId);
		} catch (Exception e) {
			logger.error("删除合同出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void deleteRecordFor(){
		String url = super.getRequest().getRealPath("/");
		try {
			vProcreditContractService.deleteContractRecordFor(url,projId,businessType,htType);
		} catch (Exception e) {
			logger.error("删除合同出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	public void deleteRecordByMortgageId(){
		String url = super.getRequest().getRealPath("/");
		try {
			vProcreditContractService.deleteContractRecordByMortgageId(url,mortgageId,businessType,contractType,projId);
		} catch (Exception e) {
			logger.error("删除合同出错:"+e.getMessage());
			e.printStackTrace();
		}
	}
	public void deleteByProject(){
		vProcreditContractService.deleteContractByProject(projId,businessType,htType,false);
	}

	public void ajaxIsExistValida(){
		try {
			boolean flag =  elementHandleService.ajaxValida(typeId);
			if(flag){
				JsonUtil.responseJsonString("{success:true,exsit:true}");
				return ;
			}else
				JsonUtil.responseJsonString("{success:true,exsit:false,msg :'加载错误，未上传文件'}");
				return ;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("验证出错:"+e.getMessage());
			JsonUtil.responseJsonString("{success:true,exsit:false,msg :'加载错误'}");
			return ;
		}
	}
	//获得所有反担保合同列表
	public void getThirdContractCategoryTree(){
		try{
			List<VProcreditContract> list=vProcreditContractService.getThirdContractCategoryTree(businessType,projId,htType,mortgageId);
			JsonUtil.jsonFromList(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//获得客户承诺函或担保意向书列表
	public void getLetterAndBookTree(){
		try{
			List<VProcreditContract> list=vProcreditContractService.getListByHtType(businessType, projId, htType);
			List arrayList = new ArrayList();
			VProcreditContract vpcc = null;
		
			if (list != null && list.size() > 0) {
				if (list.size() > 1) {
					vpcc = (VProcreditContract) list
							.get(list.size() - 1);
				} else {
					vpcc = (VProcreditContract) list.get(0);
				}
				arrayList.add(vpcc);
				JsonUtil.jsonFromList(arrayList, 1);
			} else {
				JsonUtil.responseJsonString("{\"topics\":[],\"totalProperty\":0}");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	
	}
	
	public void getSlauseContractCategoryTree(){
		try{
			HttpServletRequest requrst = getRequest();
			String bisApply =requrst.getParameter("isApplyy");
			String bisUpdate = requrst.getParameter("isUpdate");
			String clauseId = requrst.getParameter("clauseId");
			boolean iisApply = "true".equals(bisApply)|| bisApply=="true"?true:false;
			boolean iisUpdate = "true".equals(bisUpdate)|| bisUpdate=="true"?true:false;
			List<VProcreditContract> list=vProcreditContractService.getSlauseContractCategoryTree(businessType,projId,htType,iisApply,iisUpdate,clauseId);
			JsonUtil.jsonFromList(list);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void applySlauseContractCategory(){
		Object[] obj = {projId ,businessType,htType} ;
		try {
			elementHandleService.updateCon("update ProcreditContract set isApply=1 where projId =? and businessType = ? and htType = ?",obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("保存展期合同出错:"+e.getMessage());
			e.printStackTrace();
		}
	}


	//添加一条合同类型
	public void ajaxAddContractCategory(){
	
		procreditContractService.add(procreditContract,projId,businessType);
	}
	
	
	//查询一条合同类型信息
	public void seeContractCategory(){
		vProcreditContractService.seeContract(contractId);
	}
	
	//批量签署合同
	public void batchQSContract(){
		String categoryIds = super.getRequest().getParameter("categoryIds");
		procreditContractService.batchQSContract(categoryIds);
	}
	
	//批量签署反担保合同
	public void getAllThirdContractByProjectId(){
		vProcreditContractService.getAllThirdContractByProjectId(projId,businessType,htType);
	}
}