package com.credit.proj.entity;

import com.google.gson.annotations.Expose;

/**
 * VProcreditDictionaryFinance entity. @author MyEclipse Persistence Tools
 */

public class VProcreditDictionaryFinance extends BaseVProcreditDictionaryExceptFinancing implements java.io.Serializable {

	// Fields

	// Fields

	@Expose
	private String assuremodeidValue;//担保类型数据字典值
	//private String mortgagepersontypeidValue;//反担保人类型数据字典值
	@Expose
	private String mortgagenametypeid;//反担保物类型数据字典值
	@Expose
	private String mortgagename;//反担保物名称
	@Expose
	private Integer assureofname;//反担保人/企业名称=所有权人
	@Expose
	private Double finalprice;//最终估价(万元:)
	@Expose
	private Double assuremoney;//可担保额度(万元:)
	//private Boolean censor;//审保会审定
	@Expose
	private String pledgenumber;//抵质押登记号
	@Expose
	private String statusidValue;//状态数据字典值
	@Expose
	private java.util.Date enregisterdate;//登记日期
	@Expose
	private String enregisterperson;//登记人对应人员表name
	@Expose
	private java.util.Date unchaindate;//解除日期
	@Expose
	private String unchainofperson;//解除人对应人员表name
	@Expose
	private String remarks;//归档备注
	@Expose
	private Double assureamount;//担保金额
	@Expose
	private String assuretypeidValue;//担保类型数据字典值
	@Expose
	private String bargainNum;//反担保合同编号
	//private String enregisterDatumList;//反担保登记材料清单
	//private Integer isAuditingPass;//反担保手续是否审验通过(下拉列表,对应财务审核的唯一标识:shStates)
	@Expose
	private java.util.Date planCompleteDate;//计划登记完成日期
	@Expose
	private String isAuditingPassValue;//反担保手续是否审验通过数据字典值
	@Expose
	private Integer id;//编号
	@Expose
	private Integer typeid;//类型id
	
	//private Integer mortgagepersontypeid;//反担保人类型
	//private Integer mortgagenametypeid;//反担保物类型数据字典值
	@Expose
	private Integer statusid;//状态id
	@Expose
	private Integer personTypeId;//所有人类型id
	@Expose
	private String personTypeValue;//所有人类型数据字典值
	@Expose
	private Integer assuretypeid;//担保类型数据字典值
	@Expose
	private Integer assuremodeid;//保证方式数据字典值
	@Expose
	private Integer isAuditingPass;//反担保手续是否审验通过id
	@Expose
	private String mortgagepersontypeforvalue;//反担保类型值
	@Expose
	private Long projid;//项目id
	@Expose
	private Object Others;//存放子表的实体
	@Expose
	private Integer contractid ;
	
	//12.14增加字段 by lu
	@Expose
	private String assureofnameEnterOrPerson;//企业名称或个人名字(所有权人)
	@Expose
	private String relation;//所有权人与借款人的关系
	@Expose
	private String needDatumList;//要求材料清单：有预置内容，可继续编辑
	@Expose
	private String receiveDatumList;//收到材料清单
	@Expose
	private String lessDatumRecord;//缺少材料过程记录
	@Expose
	private Boolean businessPromise;//业务经理承诺函：选项字段（有无）
	@Expose
	private java.util.Date replenishTimeLimit;//承诺补齐期限
	@Expose
	private Boolean isReplenish;//是否补齐：选项字段（是否）
	@Expose
	private java.util.Date replenishDate;//补齐日期
	
	//2011.01.20
	@Expose
	private Integer enregisterpersonId;//登记人id
	@Expose
	private Integer unchainofpersonId;//解除人id
	
	//2011.03.04
	@Expose
	private String projnum;//项目编号
	@Expose
	private String projname;//项目名称
	@Expose
	private String enterprisename;//企业全称
	@Expose
	private Boolean isArchiveConfirm;//是否归档
	@Expose
	private Boolean isTransact;//是否办理
	@Expose
	private java.util.Date transactDate;//办理时间
	@Expose
	private String enregisterDepartment;//反担保登记机关
	
	//2011.12.22
	@Expose
	private String businessType;//  业务类别
	@Expose
	private String businessTypeValue;//  业务类别对应多级数据字典的值
	
	@Expose
	private Boolean isunchain;//是否解除
	
	@Expose
	private String unchainremark;// 解除remark
	
	@Expose
	private Integer fileCounts;//抵质押物附件总数
	
	//2012.1.10 chencc
	@Expose
	private String contractCategoryText;//具体合同类型名称
	@Expose
	private String contractCategoryTypeText;//合同分类名称
	@Expose
	private Boolean isLegalCheck;//是否法务确认
	@Expose
	private Integer contractId;//合同id
	@Expose
	private Integer categoryId;//合同类型id
	@Expose
	private Integer temptId;//合同模板id
	@Expose
	private Boolean issign;//是否签署
	@Expose
	private java.util.Date signDate;//签署日期
	@Expose
	private String fileCount;//合同附件个数
	//private String isfanassurematerial;        //反担保材料是否完整
	//private String isdirectorpromise;          //业务经理承诺函
	
	private String contractContent;//反担保合同内容
	private Integer contractCount;//合同个数
	@Expose
	private Short isPledged;//是否已抵押。0:否；1：是
	@Expose
	private Short isDel;//是否已删除的标志。0:否；1：是
	private Integer dywId;
	private String mortgageRemarks;//备注

	public VProcreditDictionaryFinance() {
		super();
	}

	
	
	public VProcreditDictionaryFinance(String assuremodeidValue,
			String mortgagenametypeid,
			String mortgagename, Integer assureofname, Double finalprice,
			Double assuremoney, String pledgenumber,
			String statusidValue, java.util.Date enregisterdate,
			String enregisterperson, java.util.Date unchaindate,
			String unchainofperson, String remarks, Double assureamount,
			String assuretypeidValue, String bargainNum,
			java.util.Date planCompleteDate,String isAuditingPassValue,
			Integer id, Integer typeid,
			Integer statusid,Integer personTypeId,String personTypeValue,Integer assuretypeid,
			Integer assuremodeid,Integer isAuditingPass,String mortgagepersontypeforvalue,
			Long projid,Object Others ,Integer contractid,String assureofnameEnterOrPerson,
			String relation,String needDatumList,String receiveDatumList,String lessDatumRecord,
			Boolean businessPromise,java.util.Date replenishTimeLimit,Boolean isReplenish,java.util.Date replenishDate,
			Integer enregisterpersonId,Integer unchainofpersonId,String projnum,String projname,
			String enterprisename,Boolean isArchiveConfirm,Boolean isTransact,java.util.Date transactDate,
			String businessType,String businessTypeValue,Boolean isunchain,String unchainremark,
			String enregisterDepartment,Integer fileCounts,Short isPledged,Short isDel,Integer dywId,String mortgageRemarks) {

		super();
		this.assuremodeidValue = assuremodeidValue;
		this.mortgagenametypeid = mortgagenametypeid;
		this.mortgagename = mortgagename;
		this.assureofname = assureofname;
		this.finalprice = finalprice;
		this.assuremoney = assuremoney;
		//this.censor = censor;
		this.pledgenumber = pledgenumber;
		this.statusidValue = statusidValue;
		this.enregisterdate = enregisterdate;
		this.enregisterperson = enregisterperson;
		this.unchaindate = unchaindate;
		this.unchainofperson = unchainofperson;
		this.remarks = remarks;
		this.assureamount = assureamount;
		this.assuretypeidValue = assuretypeidValue;
		this.bargainNum = bargainNum;
		//this.isAuditingPass = isAuditingPass;
		this.planCompleteDate = planCompleteDate;
		this.isAuditingPassValue = isAuditingPassValue; 
		this.id = id;
		this.typeid = typeid;
		//this.mortgagenametypeid = mortgagenametypeid;
		this.statusid = statusid;
		this.personTypeId = personTypeId;
		this.personTypeValue = personTypeValue;
		this.assuretypeid = assuretypeid;
		this.assuremodeid = assuremodeid;
		this.isAuditingPass = isAuditingPass;
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
		this.projid = projid;
		this.Others = Others;
		this.contractid =contractid ;
		this.assureofnameEnterOrPerson = assureofnameEnterOrPerson;
		this.relation = relation;
		this.needDatumList = needDatumList;
		this.receiveDatumList = receiveDatumList;
		this.lessDatumRecord = lessDatumRecord;
		this.businessPromise = businessPromise;
		this.replenishTimeLimit = replenishTimeLimit;
		this.isReplenish = isReplenish;
		this.replenishDate = replenishDate;
		this.enregisterpersonId = enregisterpersonId;
		this.unchainofpersonId = unchainofpersonId;
		this.projnum = projnum;
		this.projname = projname;
		this.enterprisename = enterprisename;
		this.isArchiveConfirm = isArchiveConfirm;
		this.isTransact = isTransact;
		this.transactDate = transactDate;
		this.businessType = businessType;
		this.businessTypeValue = businessTypeValue;

		this.isunchain = isunchain;
		this.unchainremark = unchainremark;

		this.enregisterDepartment = enregisterDepartment;

		this.fileCounts = fileCounts;
		this.isPledged = isPledged;
		this.isDel = isDel;
		this.dywId=dywId;
		this.mortgageRemarks=mortgageRemarks;
	}



	public String getMortgageRemarks() {
		return mortgageRemarks;
	}



	public void setMortgageRemarks(String mortgageRemarks) {
		this.mortgageRemarks = mortgageRemarks;
	}



	public Integer getTypeid() {
		return typeid;
	}
	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}
	
	public String getMortgagename() {
		return mortgagename;
	}
	public void setMortgagename(String mortgagename) {
		this.mortgagename = mortgagename;
	}
	public Integer getAssureofname() {
		return assureofname;
	}
	public void setAssureofname(Integer assureofname) {
		this.assureofname = assureofname;
	}
	public Double getFinalprice() {
		return finalprice;
	}
	public void setFinalprice(Double finalprice) {
		this.finalprice = finalprice;
	}
	public Double getAssuremoney() {
		return assuremoney;
	}
	public void setAssuremoney(Double assuremoney) {
		this.assuremoney = assuremoney;
	}
	public String getPledgenumber() {
		return pledgenumber;
	}
	public void setPledgenumber(String pledgenumber) {
		this.pledgenumber = pledgenumber;
	}
	public java.util.Date getEnregisterdate() {
		return enregisterdate;
	}
	public void setEnregisterdate(java.util.Date enregisterdate) {
		this.enregisterdate = enregisterdate;
	}
	public String getEnregisterperson() {
		return enregisterperson;
	}
	public void setEnregisterperson(String enregisterperson) {
		this.enregisterperson = enregisterperson;
	}
	public java.util.Date getUnchaindate() {
		return unchaindate;
	}
	public void setUnchaindate(java.util.Date unchaindate) {
		this.unchaindate = unchaindate;
	}
	public String getUnchainofperson() {
		return unchainofperson;
	}
	public void setUnchainofperson(String unchainofperson) {
		this.unchainofperson = unchainofperson;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Double getAssureamount() {
		return assureamount;
	}
	public void setAssureamount(Double assureamount) {
		this.assureamount = assureamount;
	}
	public String getBargainNum() {
		return bargainNum;
	}
	public void setBargainNum(String bargainNum) {
		this.bargainNum = bargainNum;
	}
	public java.util.Date getPlanCompleteDate() {
		return planCompleteDate;
	}
	public void setPlanCompleteDate(java.util.Date planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}
	public String getIsAuditingPassValue() {
		return isAuditingPassValue;
	}
	public void setIsAuditingPassValue(String isAuditingPassValue) {
		this.isAuditingPassValue = isAuditingPassValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getAssuremodeidValue() {
		return assuremodeidValue;
	}
	public void setAssuremodeidValue(String assuremodeidValue) {
		this.assuremodeidValue = assuremodeidValue;
	}
	public String getStatusidValue() {
		return statusidValue;
	}
	public String getMortgagenametypeid() {
		return mortgagenametypeid;
	}
	public void setMortgagenametypeid(String mortgagenametypeid) {
		this.mortgagenametypeid = mortgagenametypeid;
	}
	public void setStatusidValue(String statusidValue) {
		this.statusidValue = statusidValue;
	}
	public String getAssuretypeidValue() {
		return assuretypeidValue;
	}
	public void setAssuretypeidValue(String assuretypeidValue) {
		this.assuretypeidValue = assuretypeidValue;
	}
	public Integer getStatusid() {
		return statusid;
	}
	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}
	public Integer getPersonTypeId() {
		return personTypeId;
	}
	public void setPersonTypeId(Integer personTypeId) {
		this.personTypeId = personTypeId;
	}
	public String getPersonTypeValue() {
		return personTypeValue;
	}
	public void setPersonTypeValue(String personTypeValue) {
		this.personTypeValue = personTypeValue;
	}
	public Integer getAssuretypeid() {
		return assuretypeid;
	}
	public void setAssuretypeid(Integer assuretypeid) {
		this.assuretypeid = assuretypeid;
	}
	public Integer getAssuremodeid() {
		return assuremodeid;
	}
	public void setAssuremodeid(Integer assuremodeid) {
		this.assuremodeid = assuremodeid;
	}
	public Integer getIsAuditingPass() {
		return isAuditingPass;
	}
	public void setIsAuditingPass(Integer isAuditingPass) {
		this.isAuditingPass = isAuditingPass;
	}
	public String getMortgagepersontypeforvalue() {
		return mortgagepersontypeforvalue;
	}
	public void setMortgagepersontypeforvalue(String mortgagepersontypeforvalue) {
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
	}
	public Long getProjid() {
		return projid;
	}
	public void setProjid(Long projid) {
		this.projid = projid;
	}
	public Object getOthers() {
		return Others;
	}
	public void setOthers(Object others) {
		Others = others;
	}
	public Integer getContractid() {
		return contractid;
	}
	public void setContractid(Integer contractid) {
		this.contractid = contractid;
	}
	public String getAssureofnameEnterOrPerson() {
		return assureofnameEnterOrPerson;
	}
	public void setAssureofnameEnterOrPerson(String assureofnameEnterOrPerson) {
		this.assureofnameEnterOrPerson = assureofnameEnterOrPerson;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getNeedDatumList() {
		return needDatumList;
	}
	public void setNeedDatumList(String needDatumList) {
		this.needDatumList = needDatumList;
	}
	public String getReceiveDatumList() {
		return receiveDatumList;
	}
	public void setReceiveDatumList(String receiveDatumList) {
		this.receiveDatumList = receiveDatumList;
	}
	public String getLessDatumRecord() {
		return lessDatumRecord;
	}
	public void setLessDatumRecord(String lessDatumRecord) {
		this.lessDatumRecord = lessDatumRecord;
	}
	public Boolean getBusinessPromise() {
		return businessPromise;
	}
	public void setBusinessPromise(Boolean businessPromise) {
		this.businessPromise = businessPromise;
	}
	public java.util.Date getReplenishTimeLimit() {
		return replenishTimeLimit;
	}
	public void setReplenishTimeLimit(java.util.Date replenishTimeLimit) {
		this.replenishTimeLimit = replenishTimeLimit;
	}
	public Boolean getIsReplenish() {
		return isReplenish;
	}
	public void setIsReplenish(Boolean isReplenish) {
		this.isReplenish = isReplenish;
	}
	public java.util.Date getReplenishDate() {
		return replenishDate;
	}
	public void setReplenishDate(java.util.Date replenishDate) {
		this.replenishDate = replenishDate;
	}
	public Integer getEnregisterpersonId() {
		return enregisterpersonId;
	}
	public void setEnregisterpersonId(Integer enregisterpersonId) {
		this.enregisterpersonId = enregisterpersonId;
	}
	public Integer getUnchainofpersonId() {
		return unchainofpersonId;
	}
	public void setUnchainofpersonId(Integer unchainofpersonId) {
		this.unchainofpersonId = unchainofpersonId;
	}
	public String getProjnum() {
		return projnum;
	}
	public void setProjnum(String projnum) {
		this.projnum = projnum;
	}
	public String getProjname() {
		return projname;
	}
	public void setProjname(String projname) {
		this.projname = projname;
	}
	public String getEnterprisename() {
		return enterprisename;
	}
	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}
	public Boolean getIsArchiveConfirm() {
		return isArchiveConfirm;
	}
	public void setIsArchiveConfirm(Boolean isArchiveConfirm) {
		this.isArchiveConfirm = isArchiveConfirm;
	}
	public Boolean getIsTransact() {
		return isTransact;
	}
	public void setIsTransact(Boolean isTransact) {
		this.isTransact = isTransact;
	}
	public java.util.Date getTransactDate() {
		return transactDate;
	}
	public void setTransactDate(java.util.Date transactDate) {
		this.transactDate = transactDate;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getBusinessTypeValue() {
		return businessTypeValue;
	}
	public void setBusinessTypeValue(String businessTypeValue) {
		this.businessTypeValue = businessTypeValue;
	}
	public Boolean getIsunchain() {
		return isunchain;
	}
	public void setIsunchain(Boolean isunchain) {
		this.isunchain = isunchain;
	}
	public String getUnchainremark() {
		return unchainremark;
	}
	public void setUnchainremark(String unchainremark) {
		this.unchainremark = unchainremark;
	}
	public String getEnregisterDepartment() {
		return enregisterDepartment;
	}
	public void setEnregisterDepartment(String enregisterDepartment) {
		this.enregisterDepartment = enregisterDepartment;
	}
	public Integer getFileCounts() {
		return fileCounts;
	}
	public void setFileCounts(Integer fileCounts) {
		this.fileCounts = fileCounts;
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



	public Boolean getIsLegalCheck() {
		return isLegalCheck;
	}



	public void setIsLegalCheck(Boolean isLegalCheck) {
		this.isLegalCheck = isLegalCheck;
	}



	public Integer getContractId() {
		return contractId;
	}



	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}



	public Integer getCategoryId() {
		return categoryId;
	}



	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}



	public Integer getTemptId() {
		return temptId;
	}



	public void setTemptId(Integer temptId) {
		this.temptId = temptId;
	}



	public Boolean getIssign() {
		return issign;
	}



	public void setIssign(Boolean issign) {
		this.issign = issign;
	}



	public java.util.Date getSignDate() {
		return signDate;
	}



	public void setSignDate(java.util.Date signDate) {
		this.signDate = signDate;
	}



	public String getFileCount() {
		return fileCount;
	}



	public void setFileCount(String fileCount) {
		this.fileCount = fileCount;
	}



	public String getContractContent() {
		return contractContent;
	}



	public void setContractContent(String contractContent) {
		this.contractContent = contractContent;
	}



	public Integer getContractCount() {
		return contractCount;
	}



	public void setContractCount(Integer contractCount) {
		this.contractCount = contractCount;
	}



	public Short getIsPledged() {
		return isPledged;
	}



	public void setIsPledged(Short isPledged) {
		this.isPledged = isPledged;
	}



	public Short getIsDel() {
		return isDel;
	}



	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}



	public Integer getDywId() {
		return dywId;
	}



	public void setDywId(Integer dywId) {
		this.dywId = dywId;
	}
	
}