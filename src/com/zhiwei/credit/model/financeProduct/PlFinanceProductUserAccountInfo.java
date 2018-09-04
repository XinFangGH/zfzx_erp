package com.zhiwei.credit.model.financeProduct;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
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
 * PlFinanceProductUserAccountInfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class PlFinanceProductUserAccountInfo extends com.zhiwei.core.model.BaseModel {
	/**
	 * 数据库字段
	 * 主键Id
	 */
    protected Long id;
    /**
     * 数据库字段
     * 用户Id :逻辑为非空字段；用户来源是线上P2P用户
     */
	protected Long userId;
	/**
	 * 数据库字段
	 * 专户产品Id:逻辑为非空字段；
	 */
	protected Long productId;
	/**
	 * 数据库字段
	 * 交易金额:逻辑为非空字段；默认金额0.00；有金额正负区别
	 */
	protected java.math.BigDecimal amont=new BigDecimal(0);
	/**
	 * 数据库字段
	 * 交易类型:逻辑为非空字段；1转入，2转出，3收益
	 */
	protected String dealtype;
	/**
	 * 数据库字段
	 * 交易类型描述:逻辑为非空字段；
	 */
	protected String dealtypeName;
	/**
	 * 数据库字段
	 * 交易后账户金额:逻辑为非空字段；默认值为0.00
	 */
	protected java.math.BigDecimal currentMoney=new BigDecimal(0);
	/**
	 * 数据库字段
	 * 交易状态:
	 * -1：交易异常
	 * 0：系统金额（不允许提现）
	 * 1：在途资金（尚未起息资金）
	 * 2：交易成功
	 * 3: 提现未到账金额（等待到账金额）
	 */
	protected Short dealStatus;
	/**
	 * 数据库字段
	 * 交易状态描述:
	 */
	protected String dealStatusName;
	/**
	 * 数据库字段
	 * 交易备注:逻辑为非空字段；
	 */
	protected String remark;
	/**
	 * 数据库字段
	 * 交易流水号:逻辑为非空字段；不允许重复
	 */
	protected String requestNo;
	/**
	 * 数据库字段
	 * 交易日期（收益属于收益产生日期）:逻辑为非空字段；交易日期：转入和转出表示交易申请日期；收益表示产生收益的日期
	 */
	protected Date dealDate;
	/**
	 * 数据库字段
	 * 计息当天年化利率:产生收益的当日年化利率;产生收益记录有值
	 */
	protected java.math.BigDecimal yearRate=new BigDecimal(0);
	/**
	 * 数据库字段
	 * 资金到账日:逻辑为非空字段；资金实际到账日
	 */
	protected Date intentDate;
	/**
	 * 数据库字段
	 * 补派息日期:只有收益记录补派收益的记录才有值
	 */
	protected Date updateDate;
	/**
	 * 数据库字段
	 * 转入加入费率:购买产品时加入费率;默认值为0
	 */
	protected java.math.BigDecimal plateFeeRate=new BigDecimal(0);
	
	/**
	 * 数据库字段
	 * 转入加入费:购买产品时加入费，计算方式，转入（购买）金额*加入费率;默认值为0
	 */
	protected java.math.BigDecimal plateFee=new BigDecimal(0);
	
	/**
	 * 数据库无字段，实体对象字段
	 * 理财专户用户账号Id
	 */
	private Long accountId;
	/**
	 * 数据库无字段，实体对象字段
	 * 理财专户用户账号真实姓名
	 */
	private String userName;
	/**
	 * 数据库无字段，实体对象字段
	 * 理财专户用户账号登陆名
	 */
	private String userloginName;
	/**
	 * 数据库无字段，实体对象字段
	 * 理财专户用户账号登陆名
	 */
	private String productName;

	/**
	 * 审核时的第三方流水号
	 */
	private String preAuthorizationNum;
	
	
	public String getPreAuthorizationNum() {
		return preAuthorizationNum;
	}

	public void setPreAuthorizationNum(String preAuthorizationNum) {
		this.preAuthorizationNum = preAuthorizationNum;
	}

	public java.math.BigDecimal getPlateFeeRate() {
		return plateFeeRate;
	}

	public void setPlateFeeRate(java.math.BigDecimal plateFeeRate) {
		this.plateFeeRate = plateFeeRate;
	}

	public java.math.BigDecimal getPlateFee() {
		return plateFee;
	}

	public void setPlateFee(java.math.BigDecimal plateFee) {
		this.plateFee = plateFee;
	}
	public Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(Date intentDate) {
		this.intentDate = intentDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	public java.math.BigDecimal getYearRate() {
		return yearRate;
	}

	public void setYearRate(java.math.BigDecimal yearRate) {
		this.yearRate = yearRate;
	}
	/**
	 * Default Empty Constructor for class PlFinanceProductUserAccountInfo
	 */
	public PlFinanceProductUserAccountInfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlFinanceProductUserAccountInfo
	 */
	public PlFinanceProductUserAccountInfo (
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
	 * 	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserId() {
		return this.userId;
	}
	
	/**
	 * Set the userId
	 */	
	public void setUserId(Long aValue) {
		this.userId = aValue;
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
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="amont" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAmont() {
		return this.amont;
	}
	
	/**
	 * Set the amont
	 */	
	public void setAmont(java.math.BigDecimal aValue) {
		this.amont = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dealtype" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDealtype() {
		return this.dealtype;
	}
	
	/**
	 * Set the dealtype
	 */	
	public void setDealtype(String aValue) {
		this.dealtype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dealtypeName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDealtypeName() {
		return this.dealtypeName;
	}
	
	/**
	 * Set the dealtypeName
	 */	
	public void setDealtypeName(String aValue) {
		this.dealtypeName = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="currentMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCurrentMoney() {
		return this.currentMoney;
	}
	
	/**
	 * Set the currentMoney
	 */	
	public void setCurrentMoney(java.math.BigDecimal aValue) {
		this.currentMoney = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="dealStatus" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getDealStatus() {
		return this.dealStatus;
	}
	
	/**
	 * Set the dealStatus
	 */	
	public void setDealStatus(Short aValue) {
		this.dealStatus = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dealStatusName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getDealStatusName() {
		return this.dealStatusName;
	}
	
	/**
	 * Set the dealStatusName
	 */	
	public void setDealStatusName(String aValue) {
		this.dealStatusName = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="requestNo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getRequestNo() {
		return this.requestNo;
	}
	
	/**
	 * Set the requestNo
	 */	
	public void setRequestNo(String aValue) {
		this.requestNo = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="companyId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCompanyId() {
		return this.companyId;
	}
	
	/**
	 * Set the companyId
	 */	
	public void setCompanyId(Long aValue) {
		this.companyId = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlFinanceProductUserAccountInfo)) {
			return false;
		}
		PlFinanceProductUserAccountInfo rhs = (PlFinanceProductUserAccountInfo) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.userId, rhs.userId)
				.append(this.productId, rhs.productId)
				.append(this.amont, rhs.amont)
				.append(this.dealtype, rhs.dealtype)
				.append(this.dealtypeName, rhs.dealtypeName)
				.append(this.currentMoney, rhs.currentMoney)
				.append(this.dealStatus, rhs.dealStatus)
				.append(this.dealStatusName, rhs.dealStatusName)
				.append(this.remark, rhs.remark)
				.append(this.requestNo, rhs.requestNo)
				.append(this.companyId, rhs.companyId)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.userId) 
				.append(this.productId) 
				.append(this.amont) 
				.append(this.dealtype) 
				.append(this.dealtypeName) 
				.append(this.currentMoney) 
				.append(this.dealStatus) 
				.append(this.dealStatusName) 
				.append(this.remark) 
				.append(this.requestNo) 
				.append(this.companyId) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("userId", this.userId) 
				.append("productId", this.productId) 
				.append("amont", this.amont) 
				.append("dealtype", this.dealtype) 
				.append("dealtypeName", this.dealtypeName) 
				.append("currentMoney", this.currentMoney) 
				.append("dealStatus", this.dealStatus) 
				.append("dealStatusName", this.dealStatusName) 
				.append("remark", this.remark) 
				.append("requestNo", this.requestNo) 
				.append("companyId", this.companyId) 
				.toString();
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserloginName(String userloginName) {
		this.userloginName = userloginName;
	}

	public String getUserloginName() {
		return userloginName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductName() {
		return productName;
	}



}
