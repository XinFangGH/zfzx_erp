package com.zhiwei.credit.model.creditFlow.contract;

import com.zhiwei.core.model.BaseModel;

/**
 * VProcreditContractId entity. @author MyEclipse Persistence Tools
 */

public class VProcreditContract extends BaseModel {

	// Fields

	private Integer id;
	private String projId;   //项目id 
	private String bidPlanId;   
	private String contractNumber;    //合同编号
	private String contractName;  //合同名称
	private Integer contractType;   //合同类型
	private Integer templateId;//合同模板id
	private Integer mortgageId; //担保物ID
	
	private Boolean isDraftp ;    //是否已起草  1
	private Integer draftpId;    //起草人id 1
	private java.util.Date draftDate ; //起草时间
	
	private Boolean isLegalCheck;    //是否已确认修改以及法务审核确认   2
	private Integer legalCheckpId;    //确认修改 人 2
	private java.util.Date legalCheckDate ; //确认修改时间
	
	private Boolean issign;   //是否已客户签署   3
	private Integer issignId;  //合同签署人 3
	private java.util.Date signDate;    //签署日期  3
	
	private Boolean isAgreeLetter;        //是否已出具同意签订函 4  审签
	private Integer agreeLetterId;      //出具同意签订函操作人 4
	private java.util.Date agreeLetterDate ;  // 审签日期
	
	private Boolean isSeal;             //是否已公司盖章 5
	private Integer sealId;             //公司盖章操作人 5
	private java.util.Date sealDate ;       //盖章日期
	
	private String supplyClause	; //补充条款内容
	private Boolean isSupplyClause ;   //是否有补充条款
	private String url;
	private Boolean isUpload ;
	
	private String text ;
	private String legalCheckName;
	private String signName;
	private String sealName;
	private String agreeLetterName;
	private String draftName;
	/**以下为显示字段，不对应数据库字段*/
	private String assureText;      //反担保物名称
	private String assureTypeText;  //担保类型
	private String reverseAssureText; //反担保物类型
	private String ownershipPersonType ; //所有权人类型
	private String ownershipPerson;     //所有权人
	private String needmateriallistof ;     //要求材料清单
	private String receivemateriallistof;   //收到材料清单
	private String lackmaterialrecord;     //缺少材料过程记录
	private String isfanassurematerial ;   //反担保材料是否完整 
	private String isdirectorpromise ;     //业务经理承诺函
	private java.util.Date promisebpdeadline;       //承诺补齐期限
	
	private Integer sealSubmitPersonId ;      //盖章提交人
	private Integer sealJinbanPersonId ;      //盖章经办人
	private java.util.Date sealDate2 ;           //盖章日期
	
	private String name6;
	private String name7;
	
	private Integer categoryId;//合同分类表id
	
	private Boolean isRecord;//是否归档
	private Integer recordId;//归档人Id
	private java.util.Date recordDate;//归档日期
	private String recordRemark;//归档备注
	
	private String fileCount;
	
	 private Integer parentId;//父id
     private Integer number;//编号-排序或显示顺序
   
     private Integer isOld;//表示是否已废用
     private String remark;//备注
     
     private Integer localParentId;
     
     private String contractCategoryText;//具体合同类型名称
     private String contractCategoryTypeText;//合同分类名称
     private Integer orderNum;
     private Long leaseObjectId; //租赁物标的Id
     private Long dwId; //当物的Id
     
 	
 	private Integer temptId;//模板id
 	
 	private String businessType;//业务类型
 	
 	private String htType;//合同类型，用来区分是否第三方保证合同
 	
 	private Boolean isApply; //是否申请展期
 	
 	private Long clauseId;//展期记录id
 	
 	private String mortgagename;
 	private String mortgageTypeValue;
 	
 	private String projectName;
 	private String projectNumber;
 	private String companyName;
 	private String pawnItemName;
	// Constructors

	/** default constructor */
	public VProcreditContract() {
	}
	
	
	public String getMortgagename() {
		return mortgagename;
	}


	public void setMortgagename(String mortgagename) {
		this.mortgagename = mortgagename;
	}


	public String getMortgageTypeValue() {
		return mortgageTypeValue;
	}


	public void setMortgageTypeValue(String mortgageTypeValue) {
		this.mortgageTypeValue = mortgageTypeValue;
	}


	public String getProjectName() {
		return projectName;
	}


	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}


	public String getProjectNumber() {
		return projectNumber;
	}


	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}


	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getPawnItemName() {
		return pawnItemName;
	}


	public void setPawnItemName(String pawnItemName) {
		this.pawnItemName = pawnItemName;
	}


	public Integer getParentId() {
		return parentId;
	}


	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public Integer getIsOld() {
		return isOld;
	}


	public void setIsOld(Integer isOld) {
		this.isOld = isOld;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getLocalParentId() {
		return localParentId;
	}


	public void setLocalParentId(Integer localParentId) {
		this.localParentId = localParentId;
	}


	public String getContractCategoryText() {
		return contractCategoryText;
	}


	public void setContractCategoryText(String contractCategoryText) {
		this.contractCategoryText = contractCategoryText;
	}


	public String getContractCategoryTypeText() {
		return contractCategoryTypeText;
	}


	public void setContractCategoryTypeText(String contractCategoryTypeText) {
		this.contractCategoryTypeText = contractCategoryTypeText;
	}


	public Integer getOrderNum() {
		return orderNum;
	}


	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}


	public Long getLeaseObjectId() {
		return leaseObjectId;
	}


	public void setLeaseObjectId(Long leaseObjectId) {
		this.leaseObjectId = leaseObjectId;
	}


	public Long getDwId() {
		return dwId;
	}


	public void setDwId(Long dwId) {
		this.dwId = dwId;
	}


	public Integer getTemptId() {
		return temptId;
	}


	public void setTemptId(Integer temptId) {
		this.temptId = temptId;
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


	public Boolean getIsApply() {
		return isApply;
	}


	public void setIsApply(Boolean isApply) {
		this.isApply = isApply;
	}


	public Long getClauseId() {
		return clauseId;
	}


	public void setClauseId(Long clauseId) {
		this.clauseId = clauseId;
	}


	public String getFileCount() {
		return fileCount;
	}

	public void setFileCount(String fileCount) {
		this.fileCount = fileCount;
	}

	public Boolean getIsRecord() {
		return isRecord;
	}
	public void setIsRecord(Boolean isRecord) {
		this.isRecord = isRecord;
	}
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}
	public java.util.Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(java.util.Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getRecordRemark() {
		return recordRemark;
	}
	public void setRecordRemark(String recordRemark) {
		this.recordRemark = recordRemark;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public Integer getContractType() {
		return contractType;
	}

	public void setContractType(Integer contractType) {
		this.contractType = contractType;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public Integer getMortgageId() {
		return mortgageId;
	}

	public void setMortgageId(Integer mortgageId) {
		this.mortgageId = mortgageId;
	}

	public Boolean getIsDraftp() {
		return isDraftp;
	}

	public void setIsDraftp(Boolean isDraftp) {
		this.isDraftp = isDraftp;
	}

	public Integer getDraftpId() {
		return draftpId;
	}

	public void setDraftpId(Integer draftpId) {
		this.draftpId = draftpId;
	}

	public java.util.Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(java.util.Date draftDate) {
		this.draftDate = draftDate;
	}

	public Boolean getIsLegalCheck() {
		return isLegalCheck;
	}

	public void setIsLegalCheck(Boolean isLegalCheck) {
		this.isLegalCheck = isLegalCheck;
	}

	public Integer getLegalCheckpId() {
		return legalCheckpId;
	}

	public void setLegalCheckpId(Integer legalCheckpId) {
		this.legalCheckpId = legalCheckpId;
	}

	public java.util.Date getLegalCheckDate() {
		return legalCheckDate;
	}

	public void setLegalCheckDate(java.util.Date legalCheckDate) {
		this.legalCheckDate = legalCheckDate;
	}

	public Boolean getIssign() {
		return issign;
	}

	public void setIssign(Boolean issign) {
		this.issign = issign;
	}

	public Integer getIssignId() {
		return issignId;
	}

	public void setIssignId(Integer issignId) {
		this.issignId = issignId;
	}
	public java.util.Date getSignDate() {
		return signDate;
	}

	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}

	public Boolean getIsAgreeLetter() {
		return isAgreeLetter;
	}

	public void setIsAgreeLetter(Boolean isAgreeLetter) {
		this.isAgreeLetter = isAgreeLetter;
	}

	public Integer getAgreeLetterId() {
		return agreeLetterId;
	}

	public void setAgreeLetterId(Integer agreeLetterId) {
		this.agreeLetterId = agreeLetterId;
	}

	public java.util.Date getAgreeLetterDate() {
		return agreeLetterDate;
	}

	public void setAgreeLetterDate(java.util.Date agreeLetterDate) {
		this.agreeLetterDate = agreeLetterDate;
	}

	public Boolean getIsSeal() {
		return isSeal;
	}

	public void setIsSeal(Boolean isSeal) {
		this.isSeal = isSeal;
	}

	public Integer getSealId() {
		return sealId;
	}

	public void setSealId(Integer sealId) {
		this.sealId = sealId;
	}

	public java.util.Date getSealDate() {
		return sealDate;
	}

	public void setSealDate(java.util.Date sealDate) {
		this.sealDate = sealDate;
	}

	public String getSupplyClause() {
		return supplyClause;
	}

	public void setSupplyClause(String supplyClause) {
		this.supplyClause = supplyClause;
	}

	public Boolean getIsSupplyClause() {
		return isSupplyClause;
	}

	public void setIsSupplyClause(Boolean isSupplyClause) {
		this.isSupplyClause = isSupplyClause;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Boolean getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Boolean isUpload) {
		this.isUpload = isUpload;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLegalCheckName() {
		return legalCheckName;
	}

	public void setLegalCheckName(String legalCheckName) {
		this.legalCheckName = legalCheckName;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getSealName() {
		return sealName;
	}

	public void setSealName(String sealName) {
		this.sealName = sealName;
	}

	public String getAgreeLetterName() {
		return agreeLetterName;
	}

	public void setAgreeLetterName(String agreeLetterName) {
		this.agreeLetterName = agreeLetterName;
	}

	public String getDraftName() {
		return draftName;
	}

	public void setDraftName(String draftName) {
		this.draftName = draftName;
	}

	public String getAssureText() {
		return assureText;
	}

	public void setAssureText(String assureText) {
		this.assureText = assureText;
	}

	public String getAssureTypeText() {
		return assureTypeText;
	}

	public void setAssureTypeText(String assureTypeText) {
		this.assureTypeText = assureTypeText;
	}

	public String getReverseAssureText() {
		return reverseAssureText;
	}

	public void setReverseAssureText(String reverseAssureText) {
		this.reverseAssureText = reverseAssureText;
	}

	public String getOwnershipPersonType() {
		return ownershipPersonType;
	}

	public void setOwnershipPersonType(String ownershipPersonType) {
		this.ownershipPersonType = ownershipPersonType;
	}

	public String getOwnershipPerson() {
		return ownershipPerson;
	}

	public void setOwnershipPerson(String ownershipPerson) {
		this.ownershipPerson = ownershipPerson;
	}
	public String getNeedmateriallistof() {
		return needmateriallistof;
	}
	public void setNeedmateriallistof(String needmateriallistof) {
		this.needmateriallistof = needmateriallistof;
	}
	public String getReceivemateriallistof() {
		return receivemateriallistof;
	}
	public void setReceivemateriallistof(String receivemateriallistof) {
		this.receivemateriallistof = receivemateriallistof;
	}
	public String getLackmaterialrecord() {
		return lackmaterialrecord;
	}
	public void setLackmaterialrecord(String lackmaterialrecord) {
		this.lackmaterialrecord = lackmaterialrecord;
	}
	public String getIsfanassurematerial() {
		return isfanassurematerial;
	}
	public void setIsfanassurematerial(String isfanassurematerial) {
		this.isfanassurematerial = isfanassurematerial;
	}
	public String getIsdirectorpromise() {
		return isdirectorpromise;
	}
	public void setIsdirectorpromise(String isdirectorpromise) {
		this.isdirectorpromise = isdirectorpromise;
	}
	public java.util.Date getPromisebpdeadline() {
		return promisebpdeadline;
	}
	public void setPromisebpdeadline(java.util.Date promisebpdeadline) {
		this.promisebpdeadline = promisebpdeadline;
	}
	public Integer getSealSubmitPersonId() {
		return sealSubmitPersonId;
	}
	public void setSealSubmitPersonId(Integer sealSubmitPersonId) {
		this.sealSubmitPersonId = sealSubmitPersonId;
	}
	public Integer getSealJinbanPersonId() {
		return sealJinbanPersonId;
	}
	public void setSealJinbanPersonId(Integer sealJinbanPersonId) {
		this.sealJinbanPersonId = sealJinbanPersonId;
	}
	public java.util.Date getSealDate2() {
		return sealDate2;
	}
	public void setSealDate2(java.util.Date sealDate2) {
		this.sealDate2 = sealDate2;
	}
	public String getName6() {
		return name6;
	}
	public void setName6(String name6) {
		this.name6 = name6;
	}
	public String getName7() {
		return name7;
	}
	public void setName7(String name7) {
		this.name7 = name7;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}


	public String getBidPlanId() {
		return bidPlanId;
	}


	public void setBidPlanId(String bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

}