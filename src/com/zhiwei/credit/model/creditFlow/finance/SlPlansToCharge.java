package com.zhiwei.credit.model.creditFlow.finance;
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
 * SlPlansToCharge Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SlPlansToCharge extends com.zhiwei.core.model.BaseModel {
	/**
	 * 主键id
	 */
    protected Long plansTochargeId;
	/**
	 * 费用类型
	 */
	protected String name;
	/**
	 * 类型，0公有还是1私有
	 */
	protected Integer isType;  
	/**
	 *  0 代表百分比  1代表固定金额
	 */
	private Integer planChargeType; 
	/**
	 * 
	 */
	protected java.math.BigDecimal planCharges;
	/**
	 * 费用标准
	 */
	protected String chargeStandard;
	/**
	 *  是否删除，删除0没删除，1删除
	 */
	private Integer isValid;        
	/**
	 * 排序值
	 */
	private Integer sort;   
	/**
	 * 业务类别
	 */
	protected String businessType;
	/**
	 * key主要针对保费、保证金
	 */
	protected String chargeKey;
	/**
	 * 
	 */
	protected Integer isOperationType;
	/**
	 * 产品id
	 */
	protected Long productId;
	/**
	 * 	产品配置的基础费用id，(产品配置中添加时会复制一条，这个字段标记由哪条数据复制而来)
	 */
	protected Long settingId;
	/**
	 * 具体某个业务品种的标识
	 */
	protected String operationType;
	
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSettingId() {
		return settingId;
	}

	public void setSettingId(Long settingId) {
		this.settingId = settingId;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	/**
	 * Default Empty Constructor for class SlPlansToCharge
	 */
	public SlPlansToCharge () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SlPlansToCharge
	 */
	public SlPlansToCharge (
		 Long in_plansTochargeId
        ) {
		this.setPlansTochargeId(in_plansTochargeId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="plansTochargeId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPlansTochargeId() {
		return this.plansTochargeId;
	}
	
	/**
	 * Set the plansTochargeId
	 */	
	public void setPlansTochargeId(Long aValue) {
		this.plansTochargeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="name" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Set the name
	 */	
	public void setName(String aValue) {
		this.name = aValue;
	}
	
	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}


	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getChargeStandard() {
		return chargeStandard;
	}

	public void setChargeStandard(String chargeStandard) {
		this.chargeStandard = chargeStandard;
	}

	/**
	 * 0,公有，1私有	 * @return Integer
	 * @hibernate.property column="isType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIsType() {
		return this.isType;
	}
	
	/**
	 * Set the isType
	 */	
	public void setIsType(Integer aValue) {
		this.isType = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="chargesStandard" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
	 */
		

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SlPlansToCharge)) {
			return false;
		}
		SlPlansToCharge rhs = (SlPlansToCharge) object;
		return new EqualsBuilder()
				.append(this.plansTochargeId, rhs.plansTochargeId)
				.append(this.name, rhs.name)
				.append(this.isType, rhs.isType)
				.append(this.planCharges, rhs.planCharges)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.plansTochargeId) 
				.append(this.name) 
				.append(this.isType) 
				.append(this.planCharges) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("plansTochargeId", this.plansTochargeId) 
				.append("name", this.name) 
				.append("isType", this.isType) 
				.append("chargesStandard", this.planCharges) 
				.toString();
	}

	public Integer getPlanChargeType() {
		return planChargeType;
	}

	public void setPlanChargeType(Integer planChargeType) {
		this.planChargeType = planChargeType;
	}

	public java.math.BigDecimal getPlanCharges() {
		return planCharges;
	}

	public void setPlanCharges(java.math.BigDecimal planCharges) {
		this.planCharges = planCharges;
	}

	public String getChargeKey() {
		return chargeKey;
	}

	public void setChargeKey(String chargeKey) {
		this.chargeKey = chargeKey;
	}

	public Integer getIsOperationType() {
		return isOperationType;
	}

	public void setIsOperationType(Integer isOperationType) {
		this.isOperationType = isOperationType;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

}
