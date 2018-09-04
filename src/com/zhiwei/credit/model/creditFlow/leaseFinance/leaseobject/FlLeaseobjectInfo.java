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
 * FlLeaseobjectInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FlLeaseobjectInfo extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected String name;
	protected java.math.BigDecimal buyPrice;
	protected String standardSize;
	protected java.math.BigDecimal originalPrice;
	protected Integer useYears;
	protected Integer objectCount;
	protected Long suppliorId;
	protected String objectComment;
	protected Long projectId;
	private java.util.Date buyDate;//购买日期
	private java.util.Date manageDate;//办理日期
	protected String owner;
	protected String managePersonId;
	protected String manageComment;
	protected Boolean isManaged;    //是否已确认修改以及法务审核确认   2
	protected Boolean isBuyBack;    //是否已确认修改以及法务审核确认   2
	//租赁物处置  字段属性
	protected Boolean isHandled;    //是否已确认修改以及法务审核确认   2
	protected String handlePersonId;//处理人id
	private java.util.Date handleDate;//处置日期
	protected java.math.BigDecimal remnantEvaluatedPrice;//残值估价 
	protected java.math.BigDecimal remnantActualPrice;//实际价值
	protected String handleComment;
	
	
	private String managePersonName; //无数据库对应字段，便于查询使用  add by gaoqingrui
	private String handlePersonName; //无数据库对应字段，便于查询使用  add by gaoqingrui

	
	
	
	
	
	
	public String getHandlePersonName() {
		return handlePersonName;
	}

	public void setHandlePersonName(String handlePersonName) {
		this.handlePersonName = handlePersonName;
	}

	public String getHandleComment() {
		return handleComment;
	}

	public void setHandleComment(String handleComment) {
		this.handleComment = handleComment;
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

	public String getManagePersonName() {
		return managePersonName;
	}

	public void setManagePersonName(String managePersonName) {
		this.managePersonName = managePersonName;
	}

	public Boolean getIsManaged() {
		return isManaged;
	}

	public void setIsManaged(Boolean isManaged) {
		this.isManaged = isManaged;
	}

	public java.util.Date getManageDate() {
		return manageDate;
	}

	public void setManageDate(java.util.Date manageDate) {
		this.manageDate = manageDate;
	}

	public String getManagePersonId() {
		return managePersonId;
	}

	public void setManagePersonId(String managePersonId) {
		this.managePersonId = managePersonId;
	}

	public String getManageComment() {
		return manageComment;
	}

	public void setManageComment(String manageComment) {
		this.manageComment = manageComment;
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
	 * Default Empty Constructor for class FlLeaseobjectInfo
	 */
	public FlLeaseobjectInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class FlLeaseobjectInfo
	 */
	public FlLeaseobjectInfo (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * id	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
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
	 * 供货单位id	 * @return Long
	 * @hibernate.property column="suppliorId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getSuppliorId() {
		return this.suppliorId;
	}
	
	/**
	 * Set the suppliorId
	 * @spring.validator type="required"
	 */	
	public void setSuppliorId(Long aValue) {
		this.suppliorId = aValue;
	}	

	/**
	 * 租赁标的信息	 * @return String
	 * @hibernate.property column="objectComment" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getObjectComment() {
		return this.objectComment;
	}
	
	/**
	 * Set the objectComment
	 */	
	public void setObjectComment(String aValue) {
		this.objectComment = aValue;
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
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FlLeaseobjectInfo)) {
			return false;
		}
		FlLeaseobjectInfo rhs = (FlLeaseobjectInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.name, rhs.name)
				.append(this.buyPrice, rhs.buyPrice)
				.append(this.standardSize, rhs.standardSize)
				.append(this.originalPrice, rhs.originalPrice)
				.append(this.useYears, rhs.useYears)
				.append(this.objectCount, rhs.objectCount)
				.append(this.objectCount, rhs.owner)
				.append(this.suppliorId, rhs.suppliorId)
				.append(this.objectComment, rhs.objectComment)
				.append(this.projectId, rhs.projectId)
				.append(this.manageDate, rhs.manageDate)
				.append(this.manageComment, rhs.manageComment)
				.append(this.managePersonId, rhs.managePersonId)
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
				.append(this.standardSize) 
				.append(this.originalPrice) 
				.append(this.useYears) 
				.append(this.objectCount) 
				.append(this.suppliorId) 
				.append(this.objectComment) 
				.append(this.owner) 
				.append(this.projectId) 
				.append(this.manageDate) 
				.append(this.manageComment) 
				.append(this.managePersonId) 
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
				.append("standardSize", this.standardSize) 
				.append("originalPrice", this.originalPrice) 
				.append("useYears", this.useYears) 
				.append("objectCount", this.objectCount) 
				.append("suppliorId", this.suppliorId) 
				.append("objectComment", this.objectComment) 
				.append("projectId", this.projectId) 
				.append("owner", this.owner) 
				.append("manageDate", this.manageDate) 
				.append("manageComment", this.manageComment) 
				.append("managePersonId", this.managePersonId) 
				.append("isManaged", this.isManaged) 
				.toString();
	}



}
