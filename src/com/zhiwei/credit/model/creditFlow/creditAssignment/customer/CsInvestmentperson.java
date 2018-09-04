package com.zhiwei.credit.model.creditFlow.creditAssignment.customer;
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
 * CsInvestmentperson Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class CsInvestmentperson extends com.zhiwei.core.model.BaseModel {

	public String getBelongedShopId() {
		return belongedShopId;
	}

	public void setBelongedShopId(String belongedShopId) {
		this.belongedShopId = belongedShopId;
	}

	/**
	 * 主键
	 */
    protected Long investId;
    /**
	 * 投资人姓名
	 */
	protected String investName;
	/**
	 * 性别
	 * nodeKey : 'sex_key',  313:女  ，312,:男
	 */
	protected Integer sex;
	/**
	 * 证件类型
	 * nodeKey : 'card_type_key',
	 */
	protected Integer cardtype;
	/**
	 * 证件号码
	 */
	protected String cardnumber;
	/**
	 * 手机号码
	 */
	protected String cellphone;
	/**
	 * 邮编
	 */
	protected String postcode;
	/**
	 * 地址
	 */
	protected String postaddress;
	/**
	 * 创建者
	 */
	protected String creater;
	/**
	 * 邮箱
	 */
	protected String selfemail;
	/**
	 * 门店Id
	 */
	protected Long shopId;
	/**
	 * 门店名称
	 */
	protected String shopName;
	/**
	 * 创建者Id
	 */
	protected Long createrId;
	/**
	 * 创建日期
	 */
	protected java.util.Date createdate;
	/**
	 * 共享人Id
	 */
	protected String belongedId;
	/**
	 * 合同状态
	 */
	protected Integer contractStatus;
	/**
	 * 
	 */
	protected Integer changeCardStatus;
	/**
	 * 生日
	 */
	protected String birthDay;
	/**
	 * P2P账号
	 */
	protected String p2pName;
	/**
	 * P2P姓名
	 */
	protected String p2pTrueName;
	/**
	 * P2P证件号码
	 */
	protected String p2pcardnumber;
	/**
	 * P2P手机号码
	 */
	protected String p2pcellphone;
	/**
	 * P2P邮箱
	 */
	protected String p2pemail;
	/**
	 * 共享人部门Id
	 */
	protected String belongedShopId;
	
	
	//线下客户注册第三方数据
	protected String thirdPayCongfig;
	protected String thirdPayCongfigId;//注册第三方支付的账号
	protected String registNum;//开通第三方时的请求号码
	


	//与表无映射的字段
	private String belongedName;
	private String creator;
	private java.math.BigDecimal totalMoney;
	private String accountNumber;
	private String sexvalue;
	private String cardtypevalue;
	private String orgName;

	
	

	public String getThirdPayCongfig() {
		return thirdPayCongfig;
	}

	public void setThirdPayCongfig(String thirdPayCongfig) {
		this.thirdPayCongfig = thirdPayCongfig;
	}

	public String getThirdPayCongfigId() {
		return thirdPayCongfigId;
	}

	public void setThirdPayCongfigId(String thirdPayCongfigId) {
		this.thirdPayCongfigId = thirdPayCongfigId;
	}

	public String getRegistNum() {
		return registNum;
	}

	public void setRegistNum(String registNum) {
		this.registNum = registNum;
	}
	
	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	/**
	 * Default Empty Constructor for class CsInvestmentperson
	 */
	public CsInvestmentperson () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class CsInvestmentperson
	 */
	public CsInvestmentperson (
		 Long in_investId
        ) {
		this.setInvestId(in_investId);
    }

    

	public String getBelongedName() {
		return belongedName;
	}

	public void setBelongedName(String belongedName) {
		this.belongedName = belongedName;
	}

	public java.math.BigDecimal getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(java.math.BigDecimal totalMoney) {
		this.totalMoney = totalMoney;
	}

	/**
	 * 	 * @return Long
     * @hibernate.id column="investId" type="java.lang.Long" generator-class="native"
	 */
	public Long getInvestId() {
		return this.investId;
	}
	
	/**
	 * Set the investId
	 */	
	public void setInvestId(Long aValue) {
		this.investId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="investName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getInvestName() {
		return this.investName;
	}
	
	/**
	 * Set the investName
	 */	
	public void setInvestName(String aValue) {
		this.investName = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="sex" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getSex() {
		return this.sex;
	}
	
	/**
	 * Set the sex
	 */	
	public void setSex(Integer aValue) {
		this.sex = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="cardtype" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getCardtype() {
		return this.cardtype;
	}
	
	/**
	 * Set the cardtype
	 */	
	public void setCardtype(Integer aValue) {
		this.cardtype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cardnumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCardnumber() {
		return this.cardnumber;
	}
	
	/**
	 * Set the cardnumber
	 */	
	public void setCardnumber(String aValue) {
		this.cardnumber = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cellphone" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getCellphone() {
		return this.cellphone;
	}
	
	/**
	 * Set the cellphone
	 */	
	public void setCellphone(String aValue) {
		this.cellphone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="postcode" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getPostcode() {
		return this.postcode;
	}
	
	/**
	 * Set the postcode
	 */	
	public void setPostcode(String aValue) {
		this.postcode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="postaddress" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getPostaddress() {
		return this.postaddress;
	}
	
	/**
	 * Set the postaddress
	 */	
	public void setPostaddress(String aValue) {
		this.postaddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creater" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreater() {
		return this.creater;
	}
	
	/**
	 * Set the creater
	 */	
	public void setCreater(String aValue) {
		this.creater = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="selfemail" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getSelfemail() {
		return this.selfemail;
	}
	
	/**
	 * Set the selfemail
	 */	
	public void setSelfemail(String aValue) {
		this.selfemail = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="shopId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getShopId() {
		return this.shopId;
	}
	
	/**
	 * Set the shopId
	 */	
	public void setShopId(Long aValue) {
		this.shopId = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="shopName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getShopName() {
		return this.shopName;
	}
	
	/**
	 * Set the shopName
	 */	
	public void setShopName(String aValue) {
		this.shopName = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="createrId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getCreaterId() {
		return this.createrId;
	}
	
	/**
	 * Set the createrId
	 */	
	public void setCreaterId(Long aValue) {
		this.createrId = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createdate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreatedate() {
		return this.createdate;
	}
	
	/**
	 * Set the createdate
	 */	
	public void setCreatedate(java.util.Date aValue) {
		this.createdate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="belongedId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBelongedId() {
		return this.belongedId;
	}
	
	/**
	 * Set the belongedId
	 */	
	public void setBelongedId(String aValue) {
		this.belongedId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="contractStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getContractStatus() {
		return this.contractStatus;
	}
	
	/**
	 * Set the contractStatus
	 */	
	public void setContractStatus(Integer aValue) {
		this.contractStatus = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="changeCardStatus" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getChangeCardStatus() {
		return this.changeCardStatus;
	}
	
	/**
	 * Set the changeCardStatus
	 */	
	public void setChangeCardStatus(Integer aValue) {
		this.changeCardStatus = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CsInvestmentperson)) {
			return false;
		}
		CsInvestmentperson rhs = (CsInvestmentperson) object;
		return new EqualsBuilder()
				.append(this.investId, rhs.investId)
				.append(this.investName, rhs.investName)
				.append(this.sex, rhs.sex)
				.append(this.cardtype, rhs.cardtype)
				.append(this.cardnumber, rhs.cardnumber)
				.append(this.cellphone, rhs.cellphone)
				.append(this.postcode, rhs.postcode)
				.append(this.postaddress, rhs.postaddress)
				.append(this.creater, rhs.creater)
				.append(this.selfemail, rhs.selfemail)
				.append(this.shopId, rhs.shopId)
				.append(this.companyId, rhs.companyId)
				.append(this.shopName, rhs.shopName)
				.append(this.createrId, rhs.createrId)
				.append(this.createdate, rhs.createdate)
				.append(this.belongedId, rhs.belongedId)
				.append(this.contractStatus, rhs.contractStatus)
				.append(this.changeCardStatus, rhs.changeCardStatus)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.investId) 
				.append(this.investName) 
				.append(this.sex) 
				.append(this.cardtype) 
				.append(this.cardnumber) 
				.append(this.cellphone) 
				.append(this.postcode) 
				.append(this.postaddress) 
				.append(this.creater) 
				.append(this.selfemail) 
				.append(this.shopId) 
				.append(this.companyId) 
				.append(this.shopName) 
				.append(this.createrId) 
				.append(this.createdate) 
				.append(this.belongedId) 
				.append(this.contractStatus) 
				.append(this.changeCardStatus) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("investId", this.investId) 
				.append("investName", this.investName) 
				.append("sex", this.sex) 
				.append("cardtype", this.cardtype) 
				.append("cardnumber", this.cardnumber) 
				.append("cellphone", this.cellphone) 
				.append("postcode", this.postcode) 
				.append("postaddress", this.postaddress) 
				.append("creater", this.creater) 
				.append("selfemail", this.selfemail) 
				.append("shopId", this.shopId) 
				.append("companyId", this.companyId) 
				.append("shopName", this.shopName) 
				.append("createrId", this.createrId) 
				.append("createdate", this.createdate) 
				.append("belongedId", this.belongedId) 
				.append("contractStatus", this.contractStatus) 
				.append("changeCardStatus", this.changeCardStatus) 
				.toString();
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setSexvalue(String sexvalue) {
		this.sexvalue = sexvalue;
	}

	public String getSexvalue() {
		return sexvalue;
	}

	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
	}

	public String getCardtypevalue() {
		return cardtypevalue;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreator() {
		return creator;
	}

	public String getP2pName() {
		return p2pName;
	}

	public void setP2pName(String p2pName) {
		this.p2pName = p2pName;
	}

	public String getP2pTrueName() {
		return p2pTrueName;
	}

	public void setP2pTrueName(String p2pTrueName) {
		this.p2pTrueName = p2pTrueName;
	}

	public String getP2pcardnumber() {
		return p2pcardnumber;
	}

	public void setP2pcardnumber(String p2pcardnumber) {
		this.p2pcardnumber = p2pcardnumber;
	}

	public String getP2pcellphone() {
		return p2pcellphone;
	}

	public void setP2pcellphone(String p2pcellphone) {
		this.p2pcellphone = p2pcellphone;
	}
	
	public String getP2pemail() {
		return p2pemail;
	}

	public void setP2pemail(String p2pemail) {
		this.p2pemail = p2pemail;
	}



}
