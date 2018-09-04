package com.zhiwei.credit.model.p2p.loan;
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
 * P2pLoanConditionOrMaterial Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * p2p贷款申请条件和贷款材料
 */
public class P2pLoanConditionOrMaterial extends com.zhiwei.core.model.BaseModel {

    protected Long conditionId;
    /**
     * 产品Id
     */
	protected Long productId;
	/**
	 * 条件或材料
	 */
	protected String conditionContent;
	/**
	 * 状态 1=必备，2=可选
	 */
	protected Long conditionState;
	/**
	 * 类型 1=条件，2=材料
	 */
	protected Long conditionType;
	/**
	 * 备注
	 */
    protected String remark;
	/**
	 * 当为材料是，记录由基础材料表p2p_loan_basismaterial哪条数据复制而来
	 */
	protected Long projectId;
    /**
	 * 不与数据库相联系
	 */
	 private Integer shilitu1Id;
	 private String shilitu1URL;
	 private String shilitu1ExtendName;
	    
	 private Integer shilitu2Id;
	 private String shilitu2URL;
     private String shilitu2ExtendName;
     
	public Integer getShilitu1Id() {
		return shilitu1Id;
	}

	public void setShilitu1Id(Integer shilitu1Id) {
		this.shilitu1Id = shilitu1Id;
	}

	public String getShilitu1URL() {
		return shilitu1URL;
	}

	public void setShilitu1URL(String shilitu1url) {
		shilitu1URL = shilitu1url;
	}

	public String getShilitu1ExtendName() {
		return shilitu1ExtendName;
	}

	public void setShilitu1ExtendName(String shilitu1ExtendName) {
		this.shilitu1ExtendName = shilitu1ExtendName;
	}

	public Integer getShilitu2Id() {
		return shilitu2Id;
	}

	public void setShilitu2Id(Integer shilitu2Id) {
		this.shilitu2Id = shilitu2Id;
	}

	public String getShilitu2URL() {
		return shilitu2URL;
	}

	public void setShilitu2URL(String shilitu2url) {
		shilitu2URL = shilitu2url;
	}

	public String getShilitu2ExtendName() {
		return shilitu2ExtendName;
	}

	public void setShilitu2ExtendName(String shilitu2ExtendName) {
		this.shilitu2ExtendName = shilitu2ExtendName;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * Default Empty Constructor for class P2pLoanConditionOrMaterial
	 */
	public P2pLoanConditionOrMaterial () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class P2pLoanConditionOrMaterial
	 */
	public P2pLoanConditionOrMaterial (
		 Long in_conditionId
        ) {
		this.setConditionId(in_conditionId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="conditionId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConditionId() {
		return this.conditionId;
	}
	
	/**
	 * Set the conditionId
	 */	
	public void setConditionId(Long aValue) {
		this.conditionId = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="productId" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 * @spring.validator type="required"
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="conditionContent" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getConditionContent() {
		return this.conditionContent;
	}
	
	/**
	 * Set the conditionContent
	 * @spring.validator type="required"
	 */	
	public void setConditionContent(String aValue) {
		this.conditionContent = aValue;
	}	

	/**
	 * 1=必备，2=可选	 * @return Long
	 * @hibernate.property column="conditionState" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getConditionState() {
		return this.conditionState;
	}
	
	/**
	 * Set the conditionState
	 * @spring.validator type="required"
	 */	
	public void setConditionState(Long aValue) {
		this.conditionState = aValue;
	}	

	/**
	 * 1=条件，2=材料	 * @return Long
	 * @hibernate.property column="conditionType" type="java.lang.Long" length="19" not-null="true" unique="false"
	 */
	public Long getConditionType() {
		return this.conditionType;
	}
	
	/**
	 * Set the conditionType
	 * @spring.validator type="required"
	 */	
	public void setConditionType(Long aValue) {
		this.conditionType = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof P2pLoanConditionOrMaterial)) {
			return false;
		}
		P2pLoanConditionOrMaterial rhs = (P2pLoanConditionOrMaterial) object;
		return new EqualsBuilder()
				.append(this.conditionId, rhs.conditionId)
				.append(this.productId, rhs.productId)
				.append(this.conditionContent, rhs.conditionContent)
				.append(this.conditionState, rhs.conditionState)
				.append(this.conditionType, rhs.conditionType)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.conditionId) 
				.append(this.productId) 
				.append(this.conditionContent) 
				.append(this.conditionState) 
				.append(this.conditionType) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("conditionId", this.conditionId) 
				.append("productId", this.productId) 
				.append("conditionContent", this.conditionContent) 
				.append("conditionState", this.conditionState) 
				.append("conditionType", this.conditionType) 
				.toString();
	}



}
