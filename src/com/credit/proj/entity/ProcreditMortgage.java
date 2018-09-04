package com.credit.proj.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CsProcreditMortgage entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgage implements java.io.Serializable {

	// Fields

	private Integer id;//编号
	private Integer assuretypeid;//担保类型
	private Integer assuremodeid;//保证方式
	//private Integer mortgagepersontypeid;//反担保人类型
	private Integer mortgagenametypeid;//反担保物类型
	private String mortgagename;//反担保物名称
	private Integer assureofname;//反担保人(企业)名称=所有权人
	private Double finalprice;//最终估价(万元)
	private Double assuremoney;//可担保额度(万元)
	//private Boolean censor;
	private String pledgenumber;//抵质押登记号
	private Integer statusid;//状态
	private Integer personType;//所有人类型-法人-自然人
	private java.util.Date enregisterdate;//登记日期
	private Integer enregisterperson;//登记人--由原来的String类型修改为Integer类型
	private java.util.Date unchaindate;//解除日期
	private Boolean isunchain;   //是否解除
	private String unchainremark;//解除remark
	private Integer unchainofperson;//解除人--由原来的String类型修改为Integer类型
	private String remarks;//归档备注
	private String mortgageRemarks;//备注
	private String bargainNum;//反担保合同编号
	//private String enregisterDatumList;//反担保登记材料清单
	private Integer isAuditingPass;//反担保材料是否完整
	private java.util.Date planCompleteDate;//计划登记完成日期
	private Double assureamount;//待定
	private Long projid;//项目id
	private String mortgagepersontypeforvalue;//反担保人类型值
	private Integer contractid;//合同id
	
	//12.13增加字段 by lu
	private String relation;//所有权人与借款人的关系
	private String needDatumList;//要求材料清单
	private String receiveDatumList;//收到材料清单
	private String lessDatumRecord;//缺少材料过程记录
	private Boolean businessPromise;//业务经理承诺函：选项字段（有无）
	private java.util.Date replenishTimeLimit;//承诺补齐期限
	private Boolean isReplenish;//是否补齐：选项字段（是否）
	private java.util.Date replenishDate;//补齐日期
	
	private Integer areaId;//多级数据字典id--声明的14种类型的id，用于插入cs_procredit_contract_category表的字段。属于反担保合同分类。
	
	private Boolean isArchiveConfirm;//是否归档
	
	private Boolean isTransact;//是否办理
	private java.util.Date transactDate;//办理时间
	
	private String businessType;//  业务类别
	private String enregisterDepartment;//反担保登记机关
	
	private Integer categoryId;//合同类型id
	
	private Short isPledged;//是否已抵押。0:否；1：是
	private Short isDel;//是否已删除的标志。0:否；1：是
	private String mortgageStatus;//抵质押物的状态
	private String transactRemarks;//办理备注
	private String transactPerson;//办理经办人
	private String transactPersonId;//办理经办人Id
	private String unchainPerson;//解除经办人
	private String unchainPersonId;//解除经办人Id
	
	private Integer dywId;
	private String valuationMechanism;//评估机构
	private Date valuationTime;//评估时间
	//private String isfanassurematerial;        //反担保材料是否完整 
	//private String isdirectorpromise;          //业务经理承诺函
	// Constructors
	//add by lisl 2012-09-26
	private Boolean isHandle;//是否处理
	private Boolean isRecieve;//是否收到
	private Date recieveDate;//收到时间
	private String recieveRemarks;//收到备注
	private Date createTime;
	private Date tractCreateTime;
	private Date unchainCreateTime;
	private BigDecimal finalCertificationPrice;//最终认证价格
	
	private Long productId;//产品Id
	private String guaranteeType;//担保类型
	//end
	/** default constructor */
	public ProcreditMortgage() {
	}

	/** full constructor */
	public ProcreditMortgage(Integer assuretypeid, Integer assuremodeid,
			Integer mortgagenametypeid,
			String mortgagename, Integer assureofname, Double finalprice,
			Double assuremoney, String pledgenumber,
			Integer statusid, Integer personType,java.util.Date enregisterdate,
			Integer enregisterperson, java.util.Date unchaindate,
			Integer unchainofperson, String remarks, String bargainNum,
			Integer isAuditingPass,java.util.Date planCompleteDate,
			Double assureamount,Long projid,String mortgagepersontypeforvalue,Integer contractid,
			String relation,String needDatumList,String receiveDatumList,String lessDatumRecord,
			Boolean businessPromise,java.util.Date replenishTimeLimit,Boolean isReplenish,
			java.util.Date replenishDate,Integer areaId,Boolean isArchiveConfirm,Boolean isTransact,
			java.util.Date transactDate,String businessType,Boolean isunchain,String unchainremark,
			String enregisterDepartment,Short isPledged,Short isDel,String mortgageStatus,String transactRemarks,
			String transactPerson,String unchainPerson,String transactPersonId,String unchainPersonId,String valuationMechanism,
			Date valuationTime,String mortgageRemarks,Boolean isRecieve,Date recieveDate,String recieveRemarks,Date createTime,Date unchainCreateTime,Date tractCreateTime,BigDecimal finalCertificationPrice) {

		this.assuretypeid = assuretypeid;
		this.assuremodeid = assuremodeid;
		this.mortgagenametypeid = mortgagenametypeid;
		this.mortgagename = mortgagename;
		this.assureofname = assureofname;
		this.finalprice = finalprice;
		this.assuremoney = assuremoney;
		this.pledgenumber = pledgenumber;
		this.statusid = statusid;
		this.personType = personType;
		this.enregisterdate = enregisterdate;
		this.enregisterperson = enregisterperson;
		this.unchaindate = unchaindate;
		this.unchainofperson = unchainofperson;
		this.remarks = remarks;
		this.bargainNum = bargainNum;
		this.isAuditingPass = isAuditingPass;
		this.planCompleteDate = planCompleteDate;
		this.assureamount = assureamount;
		this.projid = projid;
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
		this.contractid = contractid;
		this.relation = relation;
		this.needDatumList = needDatumList;
		this.receiveDatumList = receiveDatumList;
		this.lessDatumRecord = lessDatumRecord;
		this.businessPromise = businessPromise;
		this.replenishTimeLimit = replenishTimeLimit;
		this.isReplenish = isReplenish;
		this.replenishDate = replenishDate;
		this.areaId = areaId;
		this.isArchiveConfirm = isArchiveConfirm;
		this.isTransact = isTransact;
		this.transactDate = transactDate;
		this.businessType = businessType;

		this.isunchain = isunchain;
		this.unchainremark = unchainremark;

		this.enregisterDepartment = enregisterDepartment;
		this.isPledged = isPledged;
		this.isDel = isDel;
		this.mortgageStatus=mortgageStatus;
		this.transactRemarks=transactRemarks;
		this.transactPerson=transactPerson;
		this.unchainPerson=unchainPerson;
		this.transactPersonId=transactPersonId;
		this.unchainPersonId=unchainPersonId;
		this.valuationMechanism=valuationMechanism;
		this.valuationTime=valuationTime;
		this.mortgageRemarks=mortgageRemarks;
		this.isRecieve=isRecieve;
		this.recieveDate=recieveDate;
		this.recieveRemarks=recieveRemarks;
		this.createTime=createTime;
		this.unchainCreateTime=unchainCreateTime;
		this.tractCreateTime=tractCreateTime;
		this.finalCertificationPrice=finalCertificationPrice;
	}

	// Property accessors

	
	public String getMortgageRemarks() {
		return mortgageRemarks;
	}

	public Boolean getIsRecieve() {
		return isRecieve;
	}

	public void setIsRecieve(Boolean isRecieve) {
		this.isRecieve = isRecieve;
	}

	public Date getRecieveDate() {
		return recieveDate;
	}

	public void setRecieveDate(Date recieveDate) {
		this.recieveDate = recieveDate;
	}

	public String getRecieveRemarks() {
		return recieveRemarks;
	}

	public void setRecieveRemarks(String recieveRemarks) {
		this.recieveRemarks = recieveRemarks;
	}

	public void setMortgageRemarks(String mortgageRemarks) {
		this.mortgageRemarks = mortgageRemarks;
	}

	public String getValuationMechanism() {
		return valuationMechanism;
	}

	public void setValuationMechanism(String valuationMechanism) {
		this.valuationMechanism = valuationMechanism;
	}

	public Date getValuationTime() {
		return valuationTime;
	}

	public void setValuationTime(Date valuationTime) {
		this.valuationTime = valuationTime;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getAssuretypeid() {
		return this.assuretypeid;
	}

	public void setAssuretypeid(Integer assuretypeid) {
		this.assuretypeid = assuretypeid;
	}

	public Integer getAssuremodeid() {
		return this.assuremodeid;
	}

	public void setAssuremodeid(Integer assuremodeid) {
		this.assuremodeid = assuremodeid;
	}

	public Integer getMortgagenametypeid() {
		return this.mortgagenametypeid;
	}

	public void setMortgagenametypeid(Integer mortgagenametypeid) {
		this.mortgagenametypeid = mortgagenametypeid;
	}

	public String getMortgagename() {
		return this.mortgagename;
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
		return this.finalprice;
	}

	public void setFinalprice(Double finalprice) {
		this.finalprice = finalprice;
	}

	public Double getAssuremoney() {
		return this.assuremoney;
	}

	public void setAssuremoney(Double assuremoney) {
		this.assuremoney = assuremoney;
	}

	/*public Boolean getCensor() {
		return this.censor;
	}

	public void setCensor(Boolean censor) {
		this.censor = censor;
	}*/

	public String getPledgenumber() {
		return this.pledgenumber;
	}

	public void setPledgenumber(String pledgenumber) {
		this.pledgenumber = pledgenumber;
	}

	public Integer getStatusid() {
		return this.statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public java.util.Date getEnregisterdate() {
		return this.enregisterdate;
	}

	public void setEnregisterdate(java.util.Date enregisterdate) {
		this.enregisterdate = enregisterdate;
	}

	public Integer getEnregisterperson() {
		return enregisterperson;
	}

	public void setEnregisterperson(Integer enregisterperson) {
		this.enregisterperson = enregisterperson;
	}

	public java.util.Date getUnchaindate() {
		return this.unchaindate;
	}

	public void setUnchaindate(java.util.Date unchaindate) {
		this.unchaindate = unchaindate;
	}


	public Integer getUnchainofperson() {
		return unchainofperson;
	}

	public void setUnchainofperson(Integer unchainofperson) {
		this.unchainofperson = unchainofperson;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

	public String getBargainNum() {
		return bargainNum;
	}

	public void setBargainNum(String bargainNum) {
		this.bargainNum = bargainNum;
	}

	public Integer getIsAuditingPass() {
		return isAuditingPass;
	}

	public void setIsAuditingPass(Integer isAuditingPass) {
		this.isAuditingPass = isAuditingPass;
	}

	public java.util.Date getPlanCompleteDate() {
		return planCompleteDate;
	}

	public void setPlanCompleteDate(java.util.Date planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}

	public Double getAssureamount() {
		return this.assureamount;
	}

	public void setAssureamount(Double assureamount) {
		this.assureamount = assureamount;
	}

	public Long getProjid() {
		return projid;
	}

	public void setProjid(Long projid) {
		this.projid = projid;
	}

	public String getMortgagepersontypeforvalue() {
		return mortgagepersontypeforvalue;
	}

	public void setMortgagepersontypeforvalue(String mortgagepersontypeforvalue) {
		this.mortgagepersontypeforvalue = mortgagepersontypeforvalue;
	}

	public Integer getContractid() {
		return contractid;
	}

	public void setContractid(Integer contractid) {
		this.contractid = contractid;
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

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
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

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
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

	public String getMortgageStatus() {
		return mortgageStatus;
	}

	public void setMortgageStatus(String mortgageStatus) {
		this.mortgageStatus = mortgageStatus;
	}

	public String getTransactRemarks() {
		return transactRemarks;
	}

	public void setTransactRemarks(String transactRemarks) {
		this.transactRemarks = transactRemarks;
	}

	public String getTransactPerson() {
		return transactPerson;
	}

	public void setTransactPerson(String transactPerson) {
		this.transactPerson = transactPerson;
	}

	public String getUnchainPerson() {
		return unchainPerson;
	}

	public void setUnchainPerson(String unchainPerson) {
		this.unchainPerson = unchainPerson;
	}

	public String getTransactPersonId() {
		return transactPersonId;
	}

	public void setTransactPersonId(String transactPersonId) {
		this.transactPersonId = transactPersonId;
	}

	public String getUnchainPersonId() {
		return unchainPersonId;
	}

	public void setUnchainPersonId(String unchainPersonId) {
		this.unchainPersonId = unchainPersonId;
	}

	public Integer getDywId() {
		return dywId;
	}

	public void setDywId(Integer dywId) {
		this.dywId = dywId;
	}

	public Boolean getIsHandle() {
		return isHandle;
	}

	public void setIsHandle(Boolean isHandle) {
		this.isHandle = isHandle;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getTractCreateTime() {
		return tractCreateTime;
	}

	public void setTractCreateTime(Date tractCreateTime) {
		this.tractCreateTime = tractCreateTime;
	}

	public Date getUnchainCreateTime() {
		return unchainCreateTime;
	}

	public void setUnchainCreateTime(Date unchainCreateTime) {
		this.unchainCreateTime = unchainCreateTime;
	}

	public BigDecimal getFinalCertificationPrice() {
		return finalCertificationPrice;
	}

	public void setFinalCertificationPrice(BigDecimal finalCertificationPrice) {
		this.finalCertificationPrice = finalCertificationPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	
}