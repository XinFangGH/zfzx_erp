package com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * VLeaseFinanceObjectInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class VLeaseFinanceObjectInfo extends com.zhiwei.core.model.BaseModel {

	protected Long id;
	protected Long suppliorId;
	protected String name;
	protected java.math.BigDecimal buyPrice;
	protected Integer objectCount;
	protected java.math.BigDecimal originalPrice;
	protected String standardSize;
	protected String suppliorName;
	protected String objectComment;
	protected String owner;
	protected Long projectId;
	protected Integer useYears;
	protected Integer fileCount;
	protected java.util.Date buyDate;//购买日期
	protected String projectName;//项目名称	
	protected String projectNumber;//项目编号
	protected Long mineId;//主体Id
	protected Boolean isManaged;    //是否已确认修改以及法务审核确认   2
	protected Boolean isHandled;    //是否已确认修改以及法务审核确认   2
	protected Boolean isBuyBack;    //是否已确认修改以及法务审核确认   2
	protected String destPlace;
	protected String managePersonId;//办理人id
	protected String managePersonName;
	
	
	protected String handlePersonId;//处理人id
	protected String handlePersonName;
	private java.util.Date handleDate;//处置日期
	protected java.math.BigDecimal remnantEvaluatedPrice;//残值估价 
	protected java.math.BigDecimal remnantActualPrice;//实际价值
	protected String handleComment;
	
	
	
	
	

	public String getManagePersonId() {
		return managePersonId;
	}


	public void setManagePersonId(String managePersonId) {
		this.managePersonId = managePersonId;
	}


	public String getManagePersonName() {
		return managePersonName;
	}


	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
	}


	public String getHandlePersonName() {
		return handlePersonName;
	}


	public void setHandlePersonName(String handlePersonName) {
		this.handlePersonName = handlePersonName;
	}


	public String getHandlePersonId() {
		return handlePersonId;
	}


	public void setHandlePersonId(String handlePersonId) {
		this.handlePersonId = handlePersonId;
	}


	public java.util.Date getHandleDate() {
		return handleDate;
	}


	public void setHandleDate(java.util.Date handleDate) {
		this.handleDate = handleDate;
	}


	public java.math.BigDecimal getRemnantEvaluatedPrice() {
		return remnantEvaluatedPrice;
	}


	public void setRemnantEvaluatedPrice(java.math.BigDecimal remnantEvaluatedPrice) {
		this.remnantEvaluatedPrice = remnantEvaluatedPrice;
	}


	public java.math.BigDecimal getRemnantActualPrice() {
		return remnantActualPrice;
	}


	public void setRemnantActualPrice(java.math.BigDecimal remnantActualPrice) {
		this.remnantActualPrice = remnantActualPrice;
	}


	public String getHandleComment() {
		return handleComment;
	}


	public void setHandleComment(String handleComment) {
		this.handleComment = handleComment;
	}


	public String getDestPlace() {
		return destPlace;
	}


	public void setDestPlace(String destPlace) {
		this.destPlace = destPlace;
	}


	public Boolean getIsBuyBack() {
		return isBuyBack;
	}


	public void setIsBuyBack(Boolean isBuyBack) {
		this.isBuyBack = isBuyBack;
	}


	public Boolean getIsHandled() {
		return isHandled;
	}


	public void setIsHandled(Boolean isHandled) {
		this.isHandled = isHandled;
	}


	public Long getMineId() {
		return mineId;
	}


	public void setMineId(Long mineId) {
		this.mineId = mineId;
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


	public Boolean getIsManaged() {
		return isManaged;
	}


	public void setIsManaged(Boolean isManaged) {
		this.isManaged = isManaged;
	}


	public Integer getFileCount() {
		return fileCount;
	}


	public void setFileCount(Integer fileCount) {
		this.fileCount = fileCount;
	}


	public String getOwner() {
		return owner;
	}


	public void setOwner(String owner) {
		this.owner = owner;
	}


	public java.util.Date getBuyDate() {
		return buyDate;
	}


	public void setBuyDate(java.util.Date buyDate) {
		this.buyDate = buyDate;
	}


	/**
	 * Default Empty Constructor for class VLeaseFinanceObjectInfo
	 */
	public VLeaseFinanceObjectInfo () {
		super();
	}
	

	/**
	 * id	 * @return Long
	 * @hibernate.property column="id" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 * @spring.validator type="required"
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 租赁标的名称	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 * @spring.validator type="required"
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}	

	/**
	 * 认购价格	 * @return java.math.BigDecimal
	 * @hibernate.property column="buyPrice" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getBuyPrice() {
		return this.buyPrice;
	}
	
	/**
	 * Set the buyPrice
	 * @spring.validator type="required"
	 */	
	public void setBuyPrice(java.math.BigDecimal aValue) {
		this.buyPrice = aValue;
	}	

	/**
	 * 数量	 * @return Integer
	 * @hibernate.property column="objectCount" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getObjectCount() {
		return this.objectCount;
	}
	
	/**
	 * Set the objectCount
	 */	
	public void setObjectCount(Integer aValue) {
		this.objectCount = aValue;
	}	

	/**
	 * 原价格	 * @return java.math.BigDecimal
	 * @hibernate.property column="originalPrice" type="java.math.BigDecimal" length="10" not-null="true" unique="false"
	 */
	public java.math.BigDecimal getOriginalPrice() {
		return this.originalPrice;
	}
	
	/**
	 * Set the originalPrice
	 * @spring.validator type="required"
	 */	
	public void setOriginalPrice(java.math.BigDecimal aValue) {
		this.originalPrice = aValue;
	}	

	/**
	 * 规格型号	 * @return String
	 * @hibernate.property column="standardSize" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getStandardSize() {
		return this.standardSize;
	}
	
	/**
	 * Set the standardSize
	 * @spring.validator type="required"
	 */	
	public void setStandardSize(String aValue) {
		this.standardSize = aValue;
	}	

	/**
	 * 供货方名称	 * @return String
	 * @hibernate.property column="suppliorName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getSuppliorName() {
		return this.suppliorName;
	}
	
	/**
	 * Set the suppliorName
	 */	
	public void setSuppliorName(String aValue) {
		this.suppliorName = aValue;
	}	

	/**
	 * 金融租赁项目id	 * @return Long
	 * @hibernate.property column="projectId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProjectId() {
		return this.projectId;
	}
	
	/**
	 * Set the projectId
	 * @spring.validator type="required"
	 */	
	public void setProjectId(Long aValue) {
		this.projectId = aValue;
	}	

	/**
	 * 使用年限	 * @return Integer
	 * @hibernate.property column="useYears" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getUseYears() {
		return this.useYears;
	}
	
	
	/**
	 * Set the useYears
	 */	
	public void setUseYears(Integer aValue) {
		this.useYears = aValue;
	}	
	
	

	public Long getSuppliorId() {
		return suppliorId;
	}


	public void setSuppliorId(Long suppliorId) {
		this.suppliorId = suppliorId;
	}
	
	
	

	public String getObjectComment() {
		return objectComment;
	}


	public void setObjectComment(String objectComment) {
		this.objectComment = objectComment;
	}


	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof VLeaseFinanceObjectInfo)) {
			return false;
		}
		VLeaseFinanceObjectInfo rhs = (VLeaseFinanceObjectInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.buyPrice, rhs.buyPrice)
				.append(this.objectCount, rhs.objectCount)
				.append(this.originalPrice, rhs.originalPrice)
				.append(this.standardSize, rhs.standardSize)
				.append(this.suppliorName, rhs.suppliorName)
				.append(this.projectId, rhs.projectId)
				.append(this.useYears, rhs.useYears)
				.append(this.owner, rhs.owner)
				.append(this.isManaged, rhs.isManaged)
				.isEquals();
	}
	


	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.name) 
				.append(this.buyPrice) 
				.append(this.objectCount) 
				.append(this.originalPrice) 
				.append(this.standardSize) 
				.append(this.suppliorName) 
				.append(this.projectId) 
				.append(this.useYears) 
				.append(this.owner) 
				.append(this.isManaged) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("name", this.name) 
				.append("buyPrice", this.buyPrice) 
				.append("objectCount", this.objectCount) 
				.append("originalPrice", this.originalPrice) 
				.append("standardSize", this.standardSize) 
				.append("suppliorName", this.suppliorName) 
				.append("projectId", this.projectId) 
				.append("useYears", this.useYears) 
				.append("owner", this.owner) 
				.append("isManaged", this.isManaged) 
				.toString();
	}



}
