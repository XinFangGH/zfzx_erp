package com.zhiwei.credit.model.creditFlow.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class GlobalGuaranteeMaterialsDetail extends com.zhiwei.core.model.BaseModel {

	private static final long serialVersionUID = 1L;
	protected Long proMaterialsId;
	protected String projId;
	protected Long materialsId;
	protected String materialsName;
	protected Boolean isReceive;
	protected Boolean isShow;
	protected Integer datumNums; //线下材料份数
	protected Integer datumNumsOfLine; //线上材料份数
	protected String remark;
	protected Boolean isPigeonhole;
	protected Integer businessTypeId;
	protected Integer parentId;
	protected String parentName;
	protected String archiveConfirmRemark;
	protected String businessTypeKey;
	protected String operationTypeKey;
	protected Boolean isArchiveConfirm;
	protected String ruleExplain;
	protected Integer xxnums;	
    protected Date recieveTime;
    protected Date confirmTime;
    protected String recieveRemarks;
    
    
	public Date getRecieveTime() {
		return recieveTime;
	}

	public void setRecieveTime(Date recieveTime) {
		this.recieveTime = recieveTime;
	}

	public Date getConfirmTime() {
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime) {
		this.confirmTime = confirmTime;
	}

	public String getRecieveRemarks() {
		return recieveRemarks;
	}

	public void setRecieveRemarks(String recieveRemarks) {
		this.recieveRemarks = recieveRemarks;
	}

	public Integer getXxnums() {
		return xxnums;
	}

	public void setXxnums(Integer xxnums) {
		this.xxnums = xxnums;
	}

	public String getRuleExplain() {
		return ruleExplain;
	}

	public void setRuleExplain(String ruleExplain) {
		this.ruleExplain = ruleExplain;
	}

	public Integer getDatumNumsOfLine() {
		return datumNumsOfLine;
	}

	public void setDatumNumsOfLine(Integer datumNumsOfLine) {
		this.datumNumsOfLine = datumNumsOfLine;
	}

	public String getBusinessTypeKey() {
		return businessTypeKey;
	}

	public void setBusinessTypeKey(String businessTypeKey) {
		this.businessTypeKey = businessTypeKey;
	}

	public String getOperationTypeKey() {
		return operationTypeKey;
	}

	public void setOperationTypeKey(String operationTypeKey) {
		this.operationTypeKey = operationTypeKey;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getBusinessTypeId() {
		return businessTypeId;
	}

	public void setBusinessTypeId(Integer businessTypeId) {
		this.businessTypeId = businessTypeId;
	}

	/**
	 * Default Empty Constructor for class SlProcreditMaterials
	 */
	public GlobalGuaranteeMaterialsDetail () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlProcreditMaterials
	 */
	public GlobalGuaranteeMaterialsDetail (
		 Long in_proMaterialsId
        ) {
		this.setProMaterialsId(in_proMaterialsId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="proMaterialsId" type="java.lang.Integer" generator-class="native"
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
	public String getProjId() {
		return this.projId;
	}
	
	/**
	 * Set the projId
	 * @spring.validator type="required"
	 */	
	public void setProjId(String aValue) {
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
	 * 	 * @return Short
	 * @hibernate.property column="isReceive" type="java.lang.Boolean" length="5" not-null="false" unique="false"
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
	 * 	 * @return Short
	 * @hibernate.property column="isShow" type="java.lang.Short" length="5" not-null="true" unique="false"
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
	
	public Boolean getIsPigeonhole() {
		return isPigeonhole;
	}

	public void setIsPigeonhole(Boolean isPigeonhole) {
		this.isPigeonhole = isPigeonhole;
	}
	
	public String getArchiveConfirmRemark() {
		return archiveConfirmRemark;
	}

	public void setArchiveConfirmRemark(String archiveConfirmRemark) {
		this.archiveConfirmRemark = archiveConfirmRemark;
	}

	public Boolean getIsArchiveConfirm() {
		return isArchiveConfirm;
	}

	public void setIsArchiveConfirm(Boolean isArchiveConfirm) {
		this.isArchiveConfirm = isArchiveConfirm;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlProcreditMaterials)) {
			return false;
		}
		SlProcreditMaterials rhs = (SlProcreditMaterials) object;
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
				.toString();
	}



}
