package com.zhiwei.credit.model.financeProduct;
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
 * PlFinanceProductRate Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlFinanceProductRate extends com.zhiwei.core.model.BaseModel {
   /**
    * 数据库字段
    * 主键Id
    */
    protected Long id;
    /**
     * 数据库字段
     * 利率生效日期:逻辑为非空字段
     */
	protected java.util.Date intentDate;
	/**
	 * 数据库字段
	 * 年化利率:逻辑为非空字段
	 */
	protected java.math.BigDecimal yearRate;
	/**
	 * 数据库字段
	 * 七日平均年化利率:逻辑为非空字段,计算方式当日利率向前取六天的年化利率平均值
	 */
	protected java.math.BigDecimal sevYearRate;
	/**
	 * 数据库字段
	 * 日化利率:逻辑为非空字段
	 */
	protected java.math.BigDecimal dayRate;
	/**
	 * 数据库字段
	 * 七日平均日化利率:逻辑为非空字段,计算方式当日利率向前取六天的日化利率平均值
	 */
	protected java.math.BigDecimal sevDayRate;
	/**
	 * 数据库字段
	 * 理财专户产品主键Id:逻辑为非空字段
	 */
	protected Long productId;
	/**
	 * 数据库字段
	 * 理财专户产品利率创建日期:逻辑为非空字段
	 */
	protected java.util.Date createDate;
	/**
	 * 数据库字段
	 * 理财专户产品利率修改日期:逻辑为非空字段
	 */
	protected java.util.Date modifyDate;
	/**
	 * 数据库字段
	 * 理财专户产品利率修改用户Id:逻辑为非空字段;创建时为生成用户Id，修改时修改为修改人Id
	 */
	protected Long createUserId;
	/**
	 * 数据库字段
	 * 理财专户产品利率修改用户名称:逻辑为非空字段;创建时为生成用户名称，修改时修改为修改人名称
	 */
	protected String createUserName;
    /**
     * 数据库无字段，实体对象字段
     * 理财专户产品名称：(用于列表显示理财专户产品名称)
     */
    private String productName;
    /**
     * 数据库无字段，实体对象字段
     * 累计前六日年化利率（可能不足六日）
     */
    private java.math.BigDecimal seveYearRate;
    /**
     * 数据库无字段，实体对象字段
     * 累计前六日日化利率（可能不足六日）
     */
    private java.math.BigDecimal seveDayRate;
    /**
     *  数据库无字段，实体对象字段
     *  累计前六日记录数（可能不足六条）
     */
    private Integer counts;
	/**
	 * Default Empty Constructor for class PlFinanceProductRate
	 */
	public PlFinanceProductRate () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlFinanceProductRate
	 */
	public PlFinanceProductRate (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="intentDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getIntentDate() {
		return this.intentDate;
	}
	
	/**
	 * Set the intentDate
	 */	
	public void setIntentDate(java.util.Date aValue) {
		this.intentDate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="yearRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getYearRate() {
		return this.yearRate;
	}
	
	/**
	 * Set the yearRate
	 */	
	public void setYearRate(java.math.BigDecimal aValue) {
		this.yearRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="sevYearRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSevYearRate() {
		return this.sevYearRate;
	}
	
	/**
	 * Set the sevYearRate
	 */	
	public void setSevYearRate(java.math.BigDecimal aValue) {
		this.sevYearRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="dayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getDayRate() {
		return this.dayRate;
	}
	
	/**
	 * Set the dayRate
	 */	
	public void setDayRate(java.math.BigDecimal aValue) {
		this.dayRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="sevDayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSevDayRate() {
		return this.sevDayRate;
	}
	
	/**
	 * Set the sevDayRate
	 */	
	public void setSevDayRate(java.math.BigDecimal aValue) {
		this.sevDayRate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="productId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getProductId() {
		return this.productId;
	}
	
	/**
	 * Set the productId
	 */	
	public void setProductId(Long aValue) {
		this.productId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="modifyDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getModifyDate() {
		return this.modifyDate;
	}
	
	/**
	 * Set the modifyDate
	 */	
	public void setModifyDate(java.util.Date aValue) {
		this.modifyDate = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="createUserId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreateUserId() {
		return this.createUserId;
	}
	
	/**
	 * Set the createUserId
	 */	
	public void setCreateUserId(Long aValue) {
		this.createUserId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="createUserName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCreateUserName() {
		return this.createUserName;
	}
	
	/**
	 * Set the createUserName
	 */	
	public void setCreateUserName(String aValue) {
		this.createUserName = aValue;
	}	



	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlFinanceProductRate)) {
			return false;
		}
		PlFinanceProductRate rhs = (PlFinanceProductRate) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.intentDate, rhs.intentDate)
				.append(this.yearRate, rhs.yearRate)
				.append(this.sevYearRate, rhs.sevYearRate)
				.append(this.dayRate, rhs.dayRate)
				.append(this.sevDayRate, rhs.sevDayRate)
				.append(this.productId, rhs.productId)
				.append(this.createDate, rhs.createDate)
				.append(this.modifyDate, rhs.modifyDate)
				.append(this.createUserId, rhs.createUserId)
				.append(this.createUserName, rhs.createUserName)
				.append(this.companyId, rhs.companyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.intentDate) 
				.append(this.yearRate) 
				.append(this.sevYearRate) 
				.append(this.dayRate) 
				.append(this.sevDayRate) 
				.append(this.productId) 
				.append(this.createDate) 
				.append(this.modifyDate) 
				.append(this.createUserId) 
				.append(this.createUserName) 
				.append(this.companyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("intentDate", this.intentDate) 
				.append("yearRate", this.yearRate) 
				.append("sevYearRate", this.sevYearRate) 
				.append("dayRate", this.dayRate) 
				.append("sevDayRate", this.sevDayRate) 
				.append("productId", this.productId) 
				.append("createDate", this.createDate) 
				.append("modifyDate", this.modifyDate) 
				.append("createUserId", this.createUserId) 
				.append("createUserName", this.createUserName) 
				.append("companyId", this.companyId) 
				.toString();
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}

	public void setSeveYearRate(java.math.BigDecimal seveYearRate) {
		this.seveYearRate = seveYearRate;
	}

	public java.math.BigDecimal getSeveYearRate() {
		return seveYearRate;
	}

	public void setSeveDayRate(java.math.BigDecimal seveDayRate) {
		this.seveDayRate = seveDayRate;
	}

	public java.math.BigDecimal getSeveDayRate() {
		return seveDayRate;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

	public Integer getCounts() {
		return counts;
	}



}
