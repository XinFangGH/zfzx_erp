package com.zhiwei.credit.model.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PlArchivesMaterials Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlArchivesMaterials extends com.zhiwei.core.model.BaseModel {

    protected Long proMaterialsId;
	protected Long projId;
	protected Long materialsId;
	protected String materialsName;
	protected Boolean isReceive;
	protected Boolean isPigeonhole;
	protected Boolean isShow;
	protected Integer datumNums;
	protected String remark;
	protected Integer businessTypeId;
	protected Integer parentId;
	protected String archiveConfirmRemark;
	protected String operationType;
	protected String businessType;
	protected Boolean isArchiveConfirm;
	protected Integer datumNumsOfLine;
	protected String ruleExplain;
	protected Integer xxnums;
    protected Date pigeonholeTime;
    protected Date recieveTime;
    protected Short materialsType;//新添加字段  用于判断归档材料是否必备，1必备，2可选，对应归档材料表中的ispublic字段

    protected Long bidPlanId;
    //产品id
    protected Long productId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	/**
	 * Default Empty Constructor for class PlArchivesMaterials
	 */
	public PlArchivesMaterials () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlArchivesMaterials
	 */
	public PlArchivesMaterials (
		 Long in_proMaterialsId
        ) {
		this.setProMaterialsId(in_proMaterialsId);
    }

	public Long getBidPlanId() {
		return bidPlanId;
	}

	public void setBidPlanId(Long bidPlanId) {
		this.bidPlanId = bidPlanId;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="proMaterialsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getProMaterialsId() {
		return this.proMaterialsId;
	}
	
	/**
	 * Set the proMaterialsId
	 */	
	public void setProMaterialsId(Long aValue) {
		this.proMaterialsId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="projId" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public Long getProjId() {
		return this.projId;
	}
	
	/**
	 * Set the projId
	 * @spring.validator type="required"
	 */	
	public void setProjId(Long aValue) {
		this.projId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="materialsId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getMaterialsId() {
		return this.materialsId;
	}
	
	/**
	 * Set the materialsId
	 */	
	public void setMaterialsId(Long aValue) {
		this.materialsId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="materialsName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMaterialsName() {
		return this.materialsName;
	}
	
	/**
	 * Set the materialsName
	 */	
	public void setMaterialsName(String aValue) {
		this.materialsName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isReceive" type="java.lang.String" length="0" not-null="false" unique="false"
	 */
	public Boolean getIsReceive() {
		return this.isReceive;
	}
	
	/**
	 * Set the isReceive
	 */	
	public void setIsReceive(Boolean aValue) {
		this.isReceive = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isPigeonhole" type="java.lang.String" length="0" not-null="false" unique="false"
	 */
	public Boolean getIsPigeonhole() {
		return this.isPigeonhole;
	}
	
	/**
	 * Set the isPigeonhole
	 */	
	public void setIsPigeonhole(Boolean aValue) {
		this.isPigeonhole = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isShow" type="java.lang.String" length="0" not-null="true" unique="false"
	 */
	public Boolean getIsShow() {
		return this.isShow;
	}
	
	/**
	 * Set the isShow
	 * @spring.validator type="required"
	 */	
	public void setIsShow(Boolean aValue) {
		this.isShow = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="datumNums" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDatumNums() {
		return this.datumNums;
	}
	
	/**
	 * Set the datumNums
	 */	
	public void setDatumNums(Integer aValue) {
		this.datumNums = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="remark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRemark() {
		return this.remark;
	}
	
	/**
	 * Set the remark
	 */	
	public void setRemark(String aValue) {
		this.remark = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="businessTypeId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getBusinessTypeId() {
		return this.businessTypeId;
	}
	
	/**
	 * Set the businessTypeId
	 */	
	public void setBusinessTypeId(Integer aValue) {
		this.businessTypeId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="parentId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getParentId() {
		return this.parentId;
	}
	
	/**
	 * Set the parentId
	 */	
	public void setParentId(Integer aValue) {
		this.parentId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="archiveConfirmRemark" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getArchiveConfirmRemark() {
		return this.archiveConfirmRemark;
	}
	
	/**
	 * Set the archiveConfirmRemark
	 */	
	public void setArchiveConfirmRemark(String aValue) {
		this.archiveConfirmRemark = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="operationType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOperationType() {
		return this.operationType;
	}
	
	/**
	 * Set the operationType
	 */	
	public void setOperationType(String aValue) {
		this.operationType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessType" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getBusinessType() {
		return this.businessType;
	}
	
	/**
	 * Set the businessType
	 */	
	public void setBusinessType(String aValue) {
		this.businessType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isArchiveConfirm" type="java.lang.String" length="0" not-null="false" unique="false"
	 */
	public Boolean getIsArchiveConfirm() {
		return this.isArchiveConfirm;
	}
	
	/**
	 * Set the isArchiveConfirm
	 */	
	public void setIsArchiveConfirm(Boolean aValue) {
		this.isArchiveConfirm = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="datumNumsOfLine" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getDatumNumsOfLine() {
		return this.datumNumsOfLine;
	}
	
	/**
	 * Set the datumNumsOfLine
	 */	
	public void setDatumNumsOfLine(Integer aValue) {
		this.datumNumsOfLine = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="ruleExplain" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getRuleExplain() {
		return this.ruleExplain;
	}
	
	/**
	 * Set the ruleExplain
	 */	
	public void setRuleExplain(String aValue) {
		this.ruleExplain = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="xxnums" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getXxnums() {
		return this.xxnums;
	}
	
	/**
	 * Set the xxnums
	 */	
	public void setXxnums(Integer aValue) {
		this.xxnums = aValue;
	}	

	public Date getPigeonholeTime() {
		return pigeonholeTime;
	}

	public void setPigeonholeTime(Date pigeonholeTime) {
		this.pigeonholeTime = pigeonholeTime;
	}

	public Date getRecieveTime() {
		return recieveTime;
	}

	public void setRecieveTime(Date recieveTime) {
		this.recieveTime = recieveTime;
	}

	public Short getMaterialsType() {
		return materialsType;
	}

	public void setMaterialsType(Short materialsType) {
		this.materialsType = materialsType;
	}
	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlArchivesMaterials)) {
			return false;
		}
		PlArchivesMaterials rhs = (PlArchivesMaterials) object;
		return new EqualsBuilder()
				.append(this.proMaterialsId, rhs.proMaterialsId)
				.append(this.projId, rhs.projId)
				.append(this.materialsId, rhs.materialsId)
				.append(this.materialsName, rhs.materialsName)
				.append(this.isReceive, rhs.isReceive)
				.append(this.isPigeonhole, rhs.isPigeonhole)
				.append(this.isShow, rhs.isShow)
				.append(this.datumNums, rhs.datumNums)
				.append(this.remark, rhs.remark)
				.append(this.businessTypeId, rhs.businessTypeId)
				.append(this.parentId, rhs.parentId)
				.append(this.archiveConfirmRemark, rhs.archiveConfirmRemark)
				.append(this.operationType, rhs.operationType)
				.append(this.businessType, rhs.businessType)
				.append(this.isArchiveConfirm, rhs.isArchiveConfirm)
				.append(this.datumNumsOfLine, rhs.datumNumsOfLine)
				.append(this.ruleExplain, rhs.ruleExplain)
				.append(this.xxnums, rhs.xxnums)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.proMaterialsId) 
				.append(this.projId) 
				.append(this.materialsId) 
				.append(this.materialsName) 
				.append(this.isReceive) 
				.append(this.isPigeonhole) 
				.append(this.isShow) 
				.append(this.datumNums) 
				.append(this.remark) 
				.append(this.businessTypeId) 
				.append(this.parentId) 
				.append(this.archiveConfirmRemark) 
				.append(this.operationType) 
				.append(this.businessType) 
				.append(this.isArchiveConfirm) 
				.append(this.datumNumsOfLine) 
				.append(this.ruleExplain) 
				.append(this.xxnums) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("proMaterialsId", this.proMaterialsId) 
				.append("projId", this.projId) 
				.append("materialsId", this.materialsId) 
				.append("materialsName", this.materialsName) 
				.append("isReceive", this.isReceive) 
				.append("isPigeonhole", this.isPigeonhole) 
				.append("isShow", this.isShow) 
				.append("datumNums", this.datumNums) 
				.append("remark", this.remark) 
				.append("businessTypeId", this.businessTypeId) 
				.append("parentId", this.parentId) 
				.append("archiveConfirmRemark", this.archiveConfirmRemark) 
				.append("operationType", this.operationType) 
				.append("businessType", this.businessType) 
				.append("isArchiveConfirm", this.isArchiveConfirm) 
				.append("datumNumsOfLine", this.datumNumsOfLine) 
				.append("ruleExplain", this.ruleExplain) 
				.append("xxnums", this.xxnums) 
				.toString();
	}



}
