package com.zhiwei.credit.model.creditFlow.contract;

import com.zhiwei.core.model.BaseModel;

public class ProcreditContract extends BaseModel {

	private Integer id;
	private String projId; // 项目id
	private String contractNumber; // 合同编号
	private String contractName; // 合同名称
	private Integer contractType; // 合同类型
	private Integer templateId;// 合同模板id
	private Integer mortgageId; // 担保物ID

	private Boolean isDraftp; // 是否已起草 1
	private Integer draftpId; // 起草人id 1
	private java.util.Date draftpDate; // 起草时间

	private Boolean isLegalCheck; // 是否已确认修改以及法务审核确认 2
	private Integer legalCheckpId; // 确认修改 人 2
	private java.util.Date legalCheckDate; // 确认修改时间

	private Boolean issign; // 是否已客户签署 3
	private Integer issignId; // 合同签署人 3
	private java.util.Date signDate; // 签署日期 3

	private Boolean isAgreeLetter; // 是否已出具同意签订函 4 审签
	private Integer agreeLetterId; // 出具同意签订函操作人 4
	private java.util.Date agreeLetterDate; // 审签日期

	private Boolean isSeal; // 是否已公司盖章 5
	private Integer sealId; // 公司盖章操作人 5
	private java.util.Date sealDate; // 盖章日期

	private String supplyClause; // 补充条款内容
	private Boolean isSupplyClause; // 是否有补充条款
	private String url;
	private Boolean isUpload;

	private Integer sealSubmitPersonId; // 盖章提交人
	private Integer sealJinbanPersonId; // 盖章经办人
	private java.util.Date sealDate2; // 盖章日期

	// private Integer categoryId;//合同分类表id

	private Boolean isRecord;// 是否归档
	private Integer recordId;// 归档人Id
	private java.util.Date recordDate;// 归档日期
	private String recordRemark;// 归档备注
	private String fileCount;// 上传的附件个数
	private String searchRemark;// 查询用备注
	private Integer parentId;// 父id
	private Integer number;// 编号-排序或显示顺序

	private Integer isOld;// 表示是否已废用
	private String remark;// 备注

	private Integer localParentId;

	private String contractCategoryText;// 具体合同类型名称
	private String contractCategoryTypeText;// 合同分类名称
	private Integer orderNum;
	private Long leaseObjectId; // 租赁物标的Id
	private Long dwId; // 当物的Id

	private Integer temptId;// 合同模板的ID

	private String businessType;// 业务品种

	public String htType;

	public Boolean isApply;

	public Long bidPlanId;// 资金方案

	public Long investId;// 投资人

	public Long clauseId;

	public ProcreditContract() {
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

	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	public Long getInvestId() {
		return investId;
	}

	public void setInvestId(Long investId) {
		this.investId = investId;
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

	public Integer getLegalCheckpId() {
		return legalCheckpId;
	}

	public void setLegalCheckpId(Integer legalCheckpId) {
		this.legalCheckpId = legalCheckpId;
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

	public String getSupplyClause() {
		return supplyClause;
	}

	public void setSupplyClause(String supplyClause) {
		this.supplyClause = supplyClause;
	}

	public Boolean getIsLegalCheck() {
		return isLegalCheck;
	}

	public void setIsLegalCheck(Boolean isLegalCheck) {
		this.isLegalCheck = isLegalCheck;
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

	public java.util.Date getDraftpDate() {
		return draftpDate;
	}

	public void setDraftpDate(java.util.Date draftpDate) {
		this.draftpDate = draftpDate;
	}

	public java.util.Date getLegalCheckDate() {
		return legalCheckDate;
	}

	public void setLegalCheckDate(java.util.Date legalCheckDate) {
		this.legalCheckDate = legalCheckDate;
	}

	public java.util.Date getAgreeLetterDate() {
		return agreeLetterDate;
	}

	public void setAgreeLetterDate(java.util.Date agreeLetterDate) {
		this.agreeLetterDate = agreeLetterDate;
	}

	public java.util.Date getSealDate() {
		return sealDate;
	}

	public void setSealDate(java.util.Date sealDate) {
		this.sealDate = sealDate;
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

	/*
	 * public Integer getCategoryId() { return categoryId; } public void
	 * setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
	 */

	public String getSearchRemark() {
		return searchRemark;
	}

	public void setSearchRemark(String searchRemark) {
		this.searchRemark = searchRemark;
	}

}