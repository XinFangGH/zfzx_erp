package com.zhiwei.credit.model.p2p.materials;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * PlWebShowMaterials Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p页面显示贷款材料清单
 */
public class PlWebShowMaterials extends com.zhiwei.core.model.BaseModel {

    protected Long webMaterialsId;
	protected Long proMaterialsId;
	protected String projId;
	protected Long materialsId;
	protected String materialsName;
	protected String isReceive;
	protected String isPigeonhole;
	protected Integer datumNums;
	protected String remark;
	protected Integer businessTypeId;
	protected Integer parentId;
	protected String archiveConfirmRemark;
	protected String operationTypeKey;
	protected String businessTypeKey;
	protected String isArchiveConfirm;
	protected Integer datumNumsOfLine;
	protected String ruleExplain;
	protected Integer xxnums;
	protected java.util.Date recieveTime;
	protected java.util.Date confirmTime;
	

	protected String recieveRemarks;
	protected java.util.Date createTime;//添加时间svn:songwj
	/**
	 * 是否是由线上材料来的
	 * 默认为0，为否，1为是
	 */
	protected Short isOnline;

	//不与数据库映射
	protected String parentName;
	
	public Short getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Short isOnline) {
		this.isOnline = isOnline;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	
	
	
	
	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * Default Empty Constructor for class PlWebShowMaterials
	 */
	public PlWebShowMaterials () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlWebShowMaterials
	 */
	public PlWebShowMaterials (
		 Long in_webMaterialsId
        ) {
		this.setWebMaterialsId(in_webMaterialsId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="webMaterialsId" type="java.lang.Long" generator-class="native"
	 */
	public Long getWebMaterialsId() {
		return this.webMaterialsId;
	}
	
	/**
	 * Set the webMaterialsId
	 */	
	public void setWebMaterialsId(Long aValue) {
		this.webMaterialsId = aValue;
	}	

	/**
	 * 贷款材料列表id	 * @return Long
	 * @hibernate.property column="proMaterialsId" type="java.lang.Long" length="19" not-null="false" unique="false"
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
	 * 项目表id	 * @return String
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
	 * 贷款材料配置表id	 * @return Long
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
	 * 贷款材料名称	 * @return String
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
	public String getIsReceive() {
		return this.isReceive;
	}
	
	/**
	 * Set the isReceive
	 */	
	public void setIsReceive(String aValue) {
		this.isReceive = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isPigeonhole" type="java.lang.String" length="0" not-null="false" unique="false"
	 */
	public String getIsPigeonhole() {
		return this.isPigeonhole;
	}
	
	/**
	 * Set the isPigeonhole
	 */	
	public void setIsPigeonhole(String aValue) {
		this.isPigeonhole = aValue;
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
	 * @hibernate.property column="operationTypeKey" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getOperationTypeKey() {
		return this.operationTypeKey;
	}
	
	/**
	 * Set the operationTypeKey
	 */	
	public void setOperationTypeKey(String aValue) {
		this.operationTypeKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="businessTypeKey" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getBusinessTypeKey() {
		return this.businessTypeKey;
	}
	
	/**
	 * Set the businessTypeKey
	 */	
	public void setBusinessTypeKey(String aValue) {
		this.businessTypeKey = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isArchiveConfirm" type="java.lang.String" length="0" not-null="false" unique="false"
	 */
	public String getIsArchiveConfirm() {
		return this.isArchiveConfirm;
	}
	
	/**
	 * Set the isArchiveConfirm
	 */	
	public void setIsArchiveConfirm(String aValue) {
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

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="recieveTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRecieveTime() {
		return this.recieveTime;
	}
	
	/**
	 * Set the recieveTime
	 */	
	public void setRecieveTime(java.util.Date aValue) {
		this.recieveTime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="confirmTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getConfirmTime() {
		return this.confirmTime;
	}
	
	/**
	 * Set the confirmTime
	 */	
	public void setConfirmTime(java.util.Date aValue) {
		this.confirmTime = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="recieveRemarks" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRecieveRemarks() {
		return this.recieveRemarks;
	}
	
	/**
	 * Set the recieveRemarks
	 */	
	public void setRecieveRemarks(String aValue) {
		this.recieveRemarks = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlWebShowMaterials)) {
			return false;
		}
		PlWebShowMaterials rhs = (PlWebShowMaterials) object;
		return new EqualsBuilder()
				.append(this.webMaterialsId, rhs.webMaterialsId)
				.append(this.proMaterialsId, rhs.proMaterialsId)
				.append(this.projId, rhs.projId)
				.append(this.materialsId, rhs.materialsId)
				.append(this.materialsName, rhs.materialsName)
				.append(this.isReceive, rhs.isReceive)
				.append(this.isPigeonhole, rhs.isPigeonhole)
				.append(this.datumNums, rhs.datumNums)
				.append(this.remark, rhs.remark)
				.append(this.businessTypeId, rhs.businessTypeId)
				.append(this.parentId, rhs.parentId)
				.append(this.archiveConfirmRemark, rhs.archiveConfirmRemark)
				.append(this.operationTypeKey, rhs.operationTypeKey)
				.append(this.businessTypeKey, rhs.businessTypeKey)
				.append(this.isArchiveConfirm, rhs.isArchiveConfirm)
				.append(this.datumNumsOfLine, rhs.datumNumsOfLine)
				.append(this.ruleExplain, rhs.ruleExplain)
				.append(this.xxnums, rhs.xxnums)
				.append(this.recieveTime, rhs.recieveTime)
				.append(this.confirmTime, rhs.confirmTime)
				.append(this.recieveRemarks, rhs.recieveRemarks)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.webMaterialsId) 
				.append(this.proMaterialsId) 
				.append(this.projId) 
				.append(this.materialsId) 
				.append(this.materialsName) 
				.append(this.isReceive) 
				.append(this.isPigeonhole) 
				.append(this.datumNums) 
				.append(this.remark) 
				.append(this.businessTypeId) 
				.append(this.parentId) 
				.append(this.archiveConfirmRemark) 
				.append(this.operationTypeKey) 
				.append(this.businessTypeKey) 
				.append(this.isArchiveConfirm) 
				.append(this.datumNumsOfLine) 
				.append(this.ruleExplain) 
				.append(this.xxnums) 
				.append(this.recieveTime) 
				.append(this.confirmTime) 
				.append(this.recieveRemarks) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("webMaterialsId", this.webMaterialsId) 
				.append("proMaterialsId", this.proMaterialsId) 
				.append("projId", this.projId) 
				.append("materialsId", this.materialsId) 
				.append("materialsName", this.materialsName) 
				.append("isReceive", this.isReceive) 
				.append("isPigeonhole", this.isPigeonhole) 
				.append("datumNums", this.datumNums) 
				.append("remark", this.remark) 
				.append("businessTypeId", this.businessTypeId) 
				.append("parentId", this.parentId) 
				.append("archiveConfirmRemark", this.archiveConfirmRemark) 
				.append("operationTypeKey", this.operationTypeKey) 
				.append("businessTypeKey", this.businessTypeKey) 
				.append("isArchiveConfirm", this.isArchiveConfirm) 
				.append("datumNumsOfLine", this.datumNumsOfLine) 
				.append("ruleExplain", this.ruleExplain) 
				.append("xxnums", this.xxnums) 
				.append("recieveTime", this.recieveTime) 
				.append("confirmTime", this.confirmTime) 
				.append("recieveRemarks", this.recieveRemarks) 
				.toString();
	}



}
