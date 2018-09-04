package com.zhiwei.credit.model.thirdInterface;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.zhiwei.credit.model.thirdInterface.WebBankcard;

/**
 * 
 * @author 
 *
 */
/**
 * WebBankcard Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WebBankcard extends com.hurong.core.model.BaseModel {
	//绑卡操作
    public static final String BINDCARD_STATUS_REPARE="bindCard_status_repare";//进行绑卡操作插入数据
    public static final String BINDCARD_STATUS_ACCEPT="bindCard_status_accept";//已经受理绑卡操作（易宝绑卡有延迟  ）
    public static final String BINDCARD_STATUS_SUCCESS="bindCard_status_success";//已经绑卡成功
    public static final String BINDCARD_STATUS_FAILD="bindCard_status_faild";//绑卡失败
    public static final String BINDCARD_STATUS_CANCEL="bindCard_status_cancel";//绑卡取消
	
    protected Long cardId;
    /**
     * 第三方标识
     */
	protected String thirdConfig;
	/**
	 * 第三方账号
	 */
	protected String userFlg;
	/**
	 * 银行卡号
	 */
	protected String cardNum;
	/**
	 * 银行简称
	 */
	protected String bankId;
	/**
	 * 银行名称
	 */
	protected String bankname;
	/**
	 * 投资客户名
	 */
	protected String username;

	/**
	 * 开户行名称
	 */
	protected String accountname;
	/**
	 * 分行
	 */
	protected String branchbank;
	/**
	 * 网点
	 */
	protected String  subbranchbank;
	/**
	 * 账户类型：0对私账户，1对公账户
	 */
	protected String accounttype;
	/**
	 * 投资人Id
	 */
	protected Long customerId;

	/**
	 * 开户省份Id
	 */
	protected Long provinceId;
	/**
	 * 开户省份名
	 */
	protected String provinceName;
	/**
	 * 开户城市Id
	 */
	protected Long cityId;
	/**
	 * 开户城市名
	 */
	protected String cityName;
	/**
	 * 投资人类型
	 */
	
	protected Short customerType;
	/**
	 * 进行绑卡操作产生的流水号
	 */
	protected String requestNo;
	/**
	 * 进行绑卡操作的状态（第三方也有状态）
	 */
	protected String bindCardStatus;
	//进行绑卡操作状态描述（数据库没有值）
	protected String bindCardStatusmsg;
	
	public String getBindCardStatusmsg() {
		return bindCardStatusmsg;
	}

	public void setBindCardStatusmsg(String bindCardStatusmsg) {
		this.bindCardStatusmsg = bindCardStatusmsg;
	}

	public String getBindCardStatus() {
		return bindCardStatus;
	}

	public void setBindCardStatus(String bindCardStatus) {
		this.bindCardStatus = bindCardStatus;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public Short getCustomerType() {
		return customerType;
	}

	public void setCustomerType(Short customerType) {
		this.customerType = customerType;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		
		this.customerId = customerId;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getBranchbank() {
		return branchbank;
	}

	public void setBranchbank(String branchbank) {
		this.branchbank = branchbank;
	}

	public String getSubbranchbank() {
		return subbranchbank;
	}

	public void setSubbranchbank(String subbranchbank) {
		this.subbranchbank = subbranchbank;
	}

	public String getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}

	/**
	 * Default Empty Constructor for class WebBankcard
	 */
	public WebBankcard () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WebBankcard
	 */
	public WebBankcard (
		 Long in_cardId
        ) {
		this.setCardId(in_cardId);
    }

    

	/**
	 * 第三方标识（例如huifuConfig 是汇付的标识）	 * @return Long
     * @hibernate.id column="cardId" type="java.lang.Long" generator-class="native"
	 */
	public Long getCardId() {
		return this.cardId;
	}
	
	/**
	 * Set the cardId
	 */	
	public void setCardId(Long aValue) {
		this.cardId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="thirdConfig" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getThirdConfig() {
		return this.thirdConfig;
	}
	
	/**
	 * Set the thirdConfig
	 */	
	public void setThirdConfig(String aValue) {
		this.thirdConfig = aValue;
	}	

	/**
	 * 第三方唯一标识	 * @return String
	 * @hibernate.property column="userFlg" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUserFlg() {
		return this.userFlg;
	}
	
	/**
	 * Set the userFlg
	 */	
	public void setUserFlg(String aValue) {
		this.userFlg = aValue;
	}	

	/**
	 * 卡号	 * @return String
	 * @hibernate.property column="cardNum" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getCardNum() {
		return this.cardNum;
	}
	
	/**
	 * Set the cardNum
	 */	
	public void setCardNum(String aValue) {
		this.cardNum = aValue;
	}	

	/**
	 * 银行标识	 * @return String
	 * @hibernate.property column="bankId" type="java.lang.String" length="30" not-null="false" unique="false"
	 */
	public String getBankId() {
		return this.bankId;
	}
	
	/**
	 * Set the bankId
	 */	
	public void setBankId(String aValue) {
		this.bankId = aValue;
	}	

	/**
	 * 持卡人姓名	 * @return String
	 * @hibernate.property column="username" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Set the username
	 */	
	public void setUsername(String aValue) {
		this.username = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WebBankcard)) {
			return false;
		}
		WebBankcard rhs = (WebBankcard) object;
		return new EqualsBuilder()
				.append(this.cardId, rhs.cardId)
				.append(this.thirdConfig, rhs.thirdConfig)
				.append(this.userFlg, rhs.userFlg)
				.append(this.cardNum, rhs.cardNum)
				.append(this.bankId, rhs.bankId)
				.append(this.username, rhs.username)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.cardId) 
				.append(this.thirdConfig) 
				.append(this.userFlg) 
				.append(this.cardNum) 
				.append(this.bankId) 
				.append(this.username) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("cardId", this.cardId) 
				.append("thirdConfig", this.thirdConfig) 
				.append("userFlg", this.userFlg) 
				.append("cardNum", this.cardNum) 
				.append("bankId", this.bankId) 
				.append("username", this.username) 
				.toString();
	}



}
