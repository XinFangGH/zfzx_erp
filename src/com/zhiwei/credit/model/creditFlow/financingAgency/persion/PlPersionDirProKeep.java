package com.zhiwei.credit.model.creditFlow.financingAgency.persion;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * PlPersionDirProKeep Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 个人直投项目维护
 */
public class PlPersionDirProKeep extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键
	 */
	@Expose
    protected Long keepId;
	/**
	 * 项目描述
	 */
	@Expose
	protected String proDes;
	/**
	 * 资金用途
	 */
	@Expose
	protected String proUseWay;
	/**
	 * 还款来源
	 */
	@Expose
	protected String proPayMoneyWay;
	/**
	 * 风险控制
	 */
	@Expose
	protected String proRiskCtr;
	/**
	 * 营业范围
	 */
	@Expose
	protected String proBusinessScope;
	/**
	 * 经营情况
	 */
	@Expose
	protected String proBusinessStatus;
	/**
	 * 保机构意见
	 */
	@Expose
	protected String proGtOrjIdea;
	/**
	 * 贷款材料展示项
	 */
	@Expose
	protected String proLoanMaterShow;
	/**
	 * 抵质押担保展示项
	 */
	@Expose
	protected String proLoanLlimitsShow;
	/**
	 * 信用担保展示项
	 */
	@Expose
	protected String proLoanLevelShow;
	/**
	 * 创建日期
	 */
	@Expose
	protected java.util.Date createDate;
	/**
	 * 更新日期
	 */
	@Expose
	protected java.util.Date updateDate;
	/**
	 * 标类型个人直投 P_Dir 个人债权 P_Or
	 */
	@Expose
	protected String proType;
	@Expose
	protected com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro bpPersionDirPro;
	@Expose
	protected com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro bpPersionOrPro;
	@Expose
	protected com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel plKeepCreditlevel;
	@Expose
	protected com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz plKeepGtorz;
	@Expose
	protected com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype plKeepProtype;

	
	/**
	 * 担保机构来源（来源于普通的担保机构，还是合作机构）
	 */
	protected String guarantorsType;//担保机构来源（来源于普通的担保机构，还是合作机构）
	/**
	 * 担保机构Id
	 */
	protected Long  guarantorsId;//担保机构Id
	/**
	 * 担保机构名称
	 */
	protected String guarantorsName;//担保机构名称
	/**
	 * 担保机构简介
	 */
    protected String guarantorsDes;//担保机构简介
	/**
	 * 担保机构意见（针对此维护项目）
	 */
    protected String guarantorsAdvise;//担保机构意见（针对此维护项目）
    /**
     * 借款人主要财务
     */
    protected String mainFinance;
    /**
     * 借款人主要债务
     */
    protected String mainDebt;
    /**
     * 保障方式
     */
    protected String guaranteeWay;
    
    public String getGuaranteeWay() {
		return guaranteeWay;
	}

	public void setGuaranteeWay(String guaranteeWay) {
		this.guaranteeWay = guaranteeWay;
	}

	public String getGuarantorsType() {
		return guarantorsType;
	}

	public void setGuarantorsType(String guarantorsType) {
		this.guarantorsType = guarantorsType;
	}
	
    public Long getGuarantorsId() {
		return guarantorsId;
	}

	public void setGuarantorsId(Long guarantorsId) {
		this.guarantorsId = guarantorsId;
	}

	public String getGuarantorsName() {
		return guarantorsName;
	}

	public void setGuarantorsName(String guarantorsName) {
		this.guarantorsName = guarantorsName;
	}

	public String getGuarantorsDes() {
		return guarantorsDes;
	}

	public void setGuarantorsDes(String guarantorsDes) {
		this.guarantorsDes = guarantorsDes;
	}

	public String getGuarantorsAdvise() {
		return guarantorsAdvise;
	}

	public void setGuarantorsAdvise(String guarantorsAdvise) {
		this.guarantorsAdvise = guarantorsAdvise;
	}


	/**
	 * Default Empty Constructor for class PlPersionDirProKeep
	 */
	public PlPersionDirProKeep () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlPersionDirProKeep
	 */
	public PlPersionDirProKeep (
		 Long in_keepId
        ) {
		this.setKeepId(in_keepId);
    }

	
	public com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro getBpPersionDirPro () {
		return bpPersionDirPro;
	}	
	
	public void setBpPersionDirPro (com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro in_bpPersionDirPro) {
		this.bpPersionDirPro = in_bpPersionDirPro;
	}
	
	public com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro getBpPersionOrPro () {
		return bpPersionOrPro;
	}	
	
	public void setBpPersionOrPro (com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro in_bpPersionOrPro) {
		this.bpPersionOrPro = in_bpPersionOrPro;
	}
	
	public com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel getPlKeepCreditlevel () {
		return plKeepCreditlevel;
	}	
	
	public void setPlKeepCreditlevel (com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel in_plKeepCreditlevel) {
		this.plKeepCreditlevel = in_plKeepCreditlevel;
	}
	
	public com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz getPlKeepGtorz () {
		return plKeepGtorz;
	}	
	
	public void setPlKeepGtorz (com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz in_plKeepGtorz) {
		this.plKeepGtorz = in_plKeepGtorz;
	}
	
	public com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype getPlKeepProtype () {
		return plKeepProtype;
	}	
	
	public void setPlKeepProtype (com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype in_plKeepProtype) {
		this.plKeepProtype = in_plKeepProtype;
	}
    

	/**
	 * 维护项目id	 * @return Long
     * @hibernate.id column="keepId" type="java.lang.Long" generator-class="native"
	 */
	public Long getKeepId() {
		return this.keepId;
	}
	
	/**
	 * Set the keepId
	 */	
	public void setKeepId(Long aValue) {
		this.keepId = aValue;
	}	

	/**
	 * 包装借款项目类型	 * @return Long
	 */
	public Long getTypeId() {
		return this.getPlKeepProtype()==null?null:this.getPlKeepProtype().getTypeId();
	}
	
	/**
	 * Set the typeId
	 */	
	public void setTypeId(Long aValue) {
	    if (aValue==null) {
	    	plKeepProtype = null;
	    } else if (plKeepProtype == null) {
	        plKeepProtype = new com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype(aValue);
	        plKeepProtype.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			plKeepProtype.setTypeId(aValue);
	    }
	}	

	/**
	 * 包装借款项目信用等级	 * @return Long
	 */
	public Long getCreditLevelId() {
		return this.getPlKeepCreditlevel()==null?null:this.getPlKeepCreditlevel().getCreditLevelId();
	}
	
	/**
	 * Set the creditLevelId
	 */	
	public void setCreditLevelId(Long aValue) {
	    if (aValue==null) {
	    	plKeepCreditlevel = null;
	    } else if (plKeepCreditlevel == null) {
	        plKeepCreditlevel = new com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepCreditlevel(aValue);
	        plKeepCreditlevel.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			plKeepCreditlevel.setCreditLevelId(aValue);
	    }
	}	

	/**
	 * 项目 描述	 * @return String
	 * @hibernate.property column="proDes" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProDes() {
		return this.proDes;
	}
	
	/**
	 * Set the proDes
	 */	
	public void setProDes(String aValue) {
		this.proDes = aValue;
	}	

	/**
	 * 资金用途	 * @return String
	 * @hibernate.property column="proUseWay" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProUseWay() {
		return this.proUseWay;
	}
	
	/**
	 * Set the proUseWay
	 */	
	public void setProUseWay(String aValue) {
		this.proUseWay = aValue;
	}	

	/**
	 * 还款来源	 * @return String
	 * @hibernate.property column="proPayMoneyWay" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProPayMoneyWay() {
		return this.proPayMoneyWay;
	}
	
	/**
	 * Set the proPayMoneyWay
	 */	
	public void setProPayMoneyWay(String aValue) {
		this.proPayMoneyWay = aValue;
	}	

	/**
	 * 风险控制	 * @return String
	 * @hibernate.property column="proRiskCtr" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProRiskCtr() {
		return this.proRiskCtr;
	}
	
	/**
	 * Set the proRiskCtr
	 */	
	public void setProRiskCtr(String aValue) {
		this.proRiskCtr = aValue;
	}	

	/**
	 * 营业范围	 * @return String
	 * @hibernate.property column="proBusinessScope" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProBusinessScope() {
		return this.proBusinessScope;
	}
	
	/**
	 * Set the proBusinessScope
	 */	
	public void setProBusinessScope(String aValue) {
		this.proBusinessScope = aValue;
	}	

	/**
	 * 经营情况	 * @return String
	 * @hibernate.property column="proBusinessStatus" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProBusinessStatus() {
		return this.proBusinessStatus;
	}
	
	/**
	 * Set the proBusinessStatus
	 */	
	public void setProBusinessStatus(String aValue) {
		this.proBusinessStatus = aValue;
	}	

	/**
	 * 担保机构id	 * @return Long
	 */
	public Long getProGtOrzId() {
		return this.getPlKeepGtorz()==null?null:this.getPlKeepGtorz().getProjGtOrzId();
	}
	
	/**
	 * Set the proGtOrzId
	 */	
	public void setProGtOrzId(Long aValue) {
	    if (aValue==null) {
	    	plKeepGtorz = null;
	    } else if (plKeepGtorz == null) {
	        plKeepGtorz = new com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepGtorz(aValue);
	        plKeepGtorz.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			plKeepGtorz.setProjGtOrzId(aValue);
	    }
	}	

	/**
	 * 个人直投项目id	 * @return Long
	 */
	public Long getPDirProId() {
		return this.getBpPersionDirPro()==null?null:this.getBpPersionDirPro().getPdirProId();
	}
	
	/**
	 * Set the pDirProId
	 */	
	public void setPDirProId(Long aValue) {
	    if (aValue==null) {
	    	bpPersionDirPro = null;
	    } else if (bpPersionDirPro == null) {
	        bpPersionDirPro = new com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro(aValue);
	        bpPersionDirPro.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			bpPersionDirPro.setPdirProId(aValue);
	    }
	}	

	/**
	 * 个人债权项目id	 * @return Long
	 */
	public Long getPOrProId() {
		return this.getBpPersionOrPro()==null?null:this.getBpPersionOrPro().getPorProId();
	}
	
	/**
	 * Set the pOrProId
	 */	
	public void setPOrProId(Long aValue) {
	    if (aValue==null) {
	    	bpPersionOrPro = null;
	    } else if (bpPersionOrPro == null) {
	        bpPersionOrPro = new com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro(aValue);
	        bpPersionOrPro.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
	    	//
			bpPersionOrPro.setPorProId(aValue);
	    }
	}	

	/**
	 * 担保机构意见	 * @return String
	 * @hibernate.property column="proGtOrjIdea" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getProGtOrjIdea() {
		return this.proGtOrjIdea;
	}
	
	/**
	 * Set the proGtOrjIdea
	 */	
	public void setProGtOrjIdea(String aValue) {
		this.proGtOrjIdea = aValue;
	}	

	/**
	 * 贷款材料展示项	 * @return String
	 * @hibernate.property column="proLoanMaterShow" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProLoanMaterShow() {
		return this.proLoanMaterShow;
	}
	
	/**
	 * Set the proLoanMaterShow
	 */	
	public void setProLoanMaterShow(String aValue) {
		this.proLoanMaterShow = aValue;
	}	

	/**
	 * 抵质押担保展示项	 * @return String
	 * @hibernate.property column="proLoanLlimitsShow" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProLoanLlimitsShow() {
		return this.proLoanLlimitsShow;
	}
	
	/**
	 * Set the proLoanLlimitsShow
	 */	
	public void setProLoanLlimitsShow(String aValue) {
		this.proLoanLlimitsShow = aValue;
	}	

	/**
	 * 信用担保展示项	 * @return String
	 * @hibernate.property column="proLoanLevelShow" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getProLoanLevelShow() {
		return this.proLoanLevelShow;
	}
	
	/**
	 * Set the proLoanLevelShow
	 */	
	public void setProLoanLevelShow(String aValue) {
		this.proLoanLevelShow = aValue;
	}	

	/**
	 * 创建时间	 * @return java.util.Date
	 * @hibernate.property column="createDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreateDate() {
		return this.createDate;
	}
	
	/**
	 * Set the createDate
	 */	
	public void setCreateDate(java.util.Date aValue) {
		this.createDate = aValue;
	}	

	/**
	 * 修改时间	 * @return java.util.Date
	 * @hibernate.property column="updateDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}
	
	/**
	 * Set the updateDate
	 */	
	public void setUpdateDate(java.util.Date aValue) {
		this.updateDate = aValue;
	}	

	/**
	 * 缓存项目类型个人直投 P_Dir 个人债权 P_Or	 * @return String
	 * @hibernate.property column="proType" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getProType() {
		return this.proType;
	}
	
	/**
	 * Set the proType
	 */	
	public void setProType(String aValue) {
		this.proType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlPersionDirProKeep)) {
			return false;
		}
		PlPersionDirProKeep rhs = (PlPersionDirProKeep) object;
		return new EqualsBuilder()
				.append(this.keepId, rhs.keepId)
								.append(this.proDes, rhs.proDes)
				.append(this.proUseWay, rhs.proUseWay)
				.append(this.proPayMoneyWay, rhs.proPayMoneyWay)
				.append(this.proRiskCtr, rhs.proRiskCtr)
				.append(this.proBusinessScope, rhs.proBusinessScope)
				.append(this.proBusinessStatus, rhs.proBusinessStatus)
										.append(this.proGtOrjIdea, rhs.proGtOrjIdea)
				.append(this.proLoanMaterShow, rhs.proLoanMaterShow)
				.append(this.proLoanLlimitsShow, rhs.proLoanLlimitsShow)
				.append(this.proLoanLevelShow, rhs.proLoanLevelShow)
				.append(this.createDate, rhs.createDate)
				.append(this.updateDate, rhs.updateDate)
				.append(this.proType, rhs.proType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.keepId) 
								.append(this.proDes) 
				.append(this.proUseWay) 
				.append(this.proPayMoneyWay) 
				.append(this.proRiskCtr) 
				.append(this.proBusinessScope) 
				.append(this.proBusinessStatus) 
										.append(this.proGtOrjIdea) 
				.append(this.proLoanMaterShow) 
				.append(this.proLoanLlimitsShow) 
				.append(this.proLoanLevelShow) 
				.append(this.createDate) 
				.append(this.updateDate) 
				.append(this.proType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("keepId", this.keepId) 
								.append("proDes", this.proDes) 
				.append("proUseWay", this.proUseWay) 
				.append("proPayMoneyWay", this.proPayMoneyWay) 
				.append("proRiskCtr", this.proRiskCtr) 
				.append("proBusinessScope", this.proBusinessScope) 
				.append("proBusinessStatus", this.proBusinessStatus) 
										.append("proGtOrjIdea", this.proGtOrjIdea) 
				.append("proLoanMaterShow", this.proLoanMaterShow) 
				.append("proLoanLlimitsShow", this.proLoanLlimitsShow) 
				.append("proLoanLevelShow", this.proLoanLevelShow) 
				.append("createDate", this.createDate) 
				.append("updateDate", this.updateDate) 
				.append("proType", this.proType) 
				.toString();
	}

	public String getMainFinance() {
		return mainFinance;
	}

	public void setMainFinance(String mainFinance) {
		this.mainFinance = mainFinance;
	}

	public String getMainDebt() {
		return mainDebt;
	}

	public void setMainDebt(String mainDebt) {
		this.mainDebt = mainDebt;
	}



}
