package com.zhiwei.credit.model.creditFlow.customer.prosperctive;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * BpCustProsperctive Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class BpCustProsperctive extends com.zhiwei.core.model.BaseModel {

	/**
	 * 主键
	 */
    protected Long perId;
    /**
	 * 客户类型    
	 * 1：企业，2：个人
	 */
	protected Short customerType;
	/**
	 * 客户名称
	 */
	protected String customerName;
	/**
	 * 联系电话
	 */
	protected String telephoneNumber;
	/**
	 * 通讯地址
	 */
	protected String postaddress;
	/**
	 * 客户类型
	 */
	protected String customerChannel;

	/**
	 * 邮箱
	 */
	protected String email;
	/**
	 * 行业类别
	 */
	protected String hangyeType;
	/**
	 * 主营业务
	 */
	protected String hangyeName;
	/**
	 * 地区
	 */
	protected String area;
	/**
	 * 地区Id
	 */
	protected String areaId;
	/**
	 * 用款金额
	 */
	protected java.math.BigDecimal loanMoney;
	/**
	 * 用款期限
	 */
	protected Integer loanPeriod;
	/**
	 * 用款时间
	 */
	protected java.util.Date loanDate;
	/**
	 * 贷款方式
	 */
	protected Integer loanType;
	/**
	 * 是否有抵质押物   
	 *  1：是，2：否
	 */
	protected Short isMortgage;
	/**
	 * 
	 */
	protected String mortgageRemark;
	/**
	 * 备注说明
	 */
	protected String remark;
	/**
	 * 创建者Id
	 */
	protected String creatorId;
	/**
	 * 创建时间
	 */
	protected java.util.Date createDate;
	/**
	 * 共享人Id
	 */
	protected String belongId;
	/**
	 * 所属分公司
	 */
	protected Long companyId;
	/**
	 * 客户状态
	 * 1: 正式客户
	 * 2：潜在客户
	 * 3：已排除客户
	 * 
	 */
	protected Short prosperctiveType;

	/**
	 *  跟进状态
	 *  数据字典配置  nodeKey : 'customer_followUpStatus'
	 */
	protected Long followUpType;
	/**
	 * 下次跟进时间
	 */
	protected java.util.Date nextFollowDate;
	/**
	 * 提醒内容
	 */
	protected String followUpRemark;
	/**
	 * 跟进次数
	 */
	protected String followUpcount;


	protected java.util.Set bpCustProspectiveFollowups = new java.util.HashSet();
	protected java.util.Set bpCustProspectiveRelations = new java.util.HashSet();

	/**
	 * 创建者姓名
	 */
	protected String creatorName;
	/**
	 * 共享人姓名
	 */
	protected String  belongName;
	/**
	 * 最后跟进时间
	 */
	protected java.util.Date lastFollowUpDate;
	
	//中金亿信新增字段
	/**
	 * 性别
	 * 数据字典项，nodeKey : 'sex_key',
	 */
	protected Integer sex;//性别
	/**
	 * 年龄
	 */
	protected Integer age;//年龄
	/**
	 * 固定电话
	 */
	protected String fixedTelephone;//固定电话
	/**
	 * 联系电话2
	 */
	protected String telephoneNumber2;//联系方式2
	/**
	 * 客户来源类型
	 * 1代表投资端客户,0代表借款端客户
	 */
	protected Integer personType;//客户类型1代表投资端客户,0代表借款端客户
	/**
	 * 资金量
	 */
	protected BigDecimal amountMoney;//资金量
	/**
	 * 意向投资产品
	 */
	protected String intentionProduct;//意向投资产品
	/**
	 * 投资经验
	 */
	protected String investExperience;//投资经验
	
	//鑫纵联新增字段
	/**
	 * 邮政编码
	 */
	private String postalcode;
	/**
	 * 所属门店
	 */
	private String department;
	/**
	 * 所属门店Id
	 */
	private Long departmentId;
	/**
	 * 投资期限
	 */
	private String investMonth;
	/**
	 * 有效截止日
	 */
	private Date validEnddate;
	/**
	 * 有无单笔投资最低或最高额度限制
	 */
	private String isHaveinvest;
	/**
	 * 有无投资经验
	 */
	private String isHaveinvestExperience;
	/**
	 * 风险保障要求
	 */
	private String riskRequire;
	/**
	 * 转化理由
	 */
	private String conversionReason;//转化理由
	
	//不与数据库映射字段
	protected String orgName; //add by zcb 2014.8.20
	
	
	public String getConversionReason() {
		return conversionReason;
	}

	public void setConversionReason(String conversionReason) {
		this.conversionReason = conversionReason;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public java.util.Date getLastFollowUpDate() {
		return lastFollowUpDate;
	}

	public void setLastFollowUpDate(java.util.Date lastFollowUpDate) {
		this.lastFollowUpDate = lastFollowUpDate;
	}

	public String getBelongName() {
		return belongName;
	}

	public void setBelongName(String belongName) {
		this.belongName = belongName;
	}

	public Short getProsperctiveType() {
		return prosperctiveType;
	}

	public void setProsperctiveType(Short prosperctiveType) {
		this.prosperctiveType = prosperctiveType;
	}
	
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public String getHangyeName() {
		return hangyeName;
	}

	public void setHangyeName(String hangyeName) {
		this.hangyeName = hangyeName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	
	/**
	 * Default Empty Constructor for class BpCustProsperctive
	 */
	public BpCustProsperctive () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class BpCustProsperctive
	 */
	public BpCustProsperctive (
		 Long in_perId
        ) {
		this.setPerId(in_perId);
    }


	public java.util.Set getBpCustProspectiveFollowups () {
		return bpCustProspectiveFollowups;
	}	
	
	public void setBpCustProspectiveFollowups (java.util.Set in_bpCustProspectiveFollowups) {
		this.bpCustProspectiveFollowups = in_bpCustProspectiveFollowups;
	}

	public java.util.Set getBpCustProspectiveRelations () {
		return bpCustProspectiveRelations;
	}	
	
	public void setBpCustProspectiveRelations (java.util.Set in_bpCustProspectiveRelations) {
		this.bpCustProspectiveRelations = in_bpCustProspectiveRelations;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="perId" type="java.lang.Long" generator-class="native"
	 */
	public Long getPerId() {
		return this.perId;
	}
	
	/**
	 * Set the perId
	 */	
	public void setPerId(Long aValue) {
		this.perId = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="customerType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getCustomerType() {
		return this.customerType;
	}
	
	/**
	 * Set the customerType
	 * @spring.validator type="required"
	 */	
	public void setCustomerType(Short aValue) {
		this.customerType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="customerName" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getCustomerName() {
		return this.customerName;
	}
	
	/**
	 * Set the customerName
	 * @spring.validator type="required"
	 */	
	public void setCustomerName(String aValue) {
		this.customerName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="telephoneNumber" type="java.lang.String" length="255" not-null="true" unique="false"
	 */
	public String getTelephoneNumber() {
		return this.telephoneNumber;
	}
	
	/**
	 * Set the telephoneNumber
	 * @spring.validator type="required"
	 */	
	public void setTelephoneNumber(String aValue) {
		this.telephoneNumber = aValue;
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
	 * @hibernate.property column="customerChannel" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCustomerChannel() {
		return this.customerChannel;
	}
	
	/**
	 * Set the customerChannel
	 */	
	public void setCustomerChannel(String aValue) {
		this.customerChannel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getEmail() {
		return this.email;
	}
	
	/**
	 * Set the email
	 */	
	public void setEmail(String aValue) {
		this.email = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="hangyeType" type="java.lang.String" length="16777215" not-null="false" unique="false"
	 */
	public String getHangyeType() {
		return this.hangyeType;
	}
	
	/**
	 * Set the hangyeType
	 */	
	public void setHangyeType(String aValue) {
		this.hangyeType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="area" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getArea() {
		return this.area;
	}
	
	/**
	 * Set the area
	 */	
	public void setArea(String aValue) {
		this.area = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="loanMoney" type="java.math.BigDecimal" length="40" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLoanMoney() {
		return this.loanMoney;
	}
	
	/**
	 * Set the loanMoney
	 */	
	public void setLoanMoney(java.math.BigDecimal aValue) {
		this.loanMoney = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="loanPeriod" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLoanPeriod() {
		return this.loanPeriod;
	}
	
	/**
	 * Set the loanPeriod
	 */	
	public void setLoanPeriod(Integer aValue) {
		this.loanPeriod = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="loanDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getLoanDate() {
		return this.loanDate;
	}
	
	/**
	 * Set the loanDate
	 */	
	public void setLoanDate(java.util.Date aValue) {
		this.loanDate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="loanType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLoanType() {
		return this.loanType;
	}
	
	/**
	 * Set the loanType
	 */	
	public void setLoanType(Integer aValue) {
		this.loanType = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="isMortgage" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getIsMortgage() {
		return this.isMortgage;
	}
	
	/**
	 * Set the isMortgage
	 */	
	public void setIsMortgage(Short aValue) {
		this.isMortgage = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mortgageRemark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMortgageRemark() {
		return this.mortgageRemark;
	}
	
	/**
	 * Set the mortgageRemark
	 */	
	public void setMortgageRemark(String aValue) {
		this.mortgageRemark = aValue;
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
	 * @hibernate.property column="creatorId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getCreatorId() {
		return this.creatorId;
	}
	
	/**
	 * Set the creatorId
	 */	
	public void setCreatorId(String aValue) {
		this.creatorId = aValue;
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
	 * 	 * @return String
	 * @hibernate.property column="belongId" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBelongId() {
		return this.belongId;
	}
	
	/**
	 * Set the belongId
	 */	
	public void setBelongId(String aValue) {
		this.belongId = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="followUpType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getFollowUpType() {
		return this.followUpType;
	}
	
	/**
	 * Set the followUpType
	 */	
	public void setFollowUpType(Long aValue) {
		this.followUpType = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="nextFollowDate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getNextFollowDate() {
		return this.nextFollowDate;
	}
	
	/**
	 * Set the nextFollowDate
	 */	
	public void setNextFollowDate(java.util.Date aValue) {
		this.nextFollowDate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="followUpRemark" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFollowUpRemark() {
		return this.followUpRemark;
	}
	
	/**
	 * Set the followUpRemark
	 */	
	public void setFollowUpRemark(String aValue) {
		this.followUpRemark = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="followUpcount" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getFollowUpcount() {
		return this.followUpcount;
	}
	
	/**
	 * Set the followUpcount
	 */	
	public void setFollowUpcount(String aValue) {
		this.followUpcount = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof BpCustProsperctive)) {
			return false;
		}
		BpCustProsperctive rhs = (BpCustProsperctive) object;
		return new EqualsBuilder()
				.append(this.perId, rhs.perId)
				.append(this.customerType, rhs.customerType)
				.append(this.customerName, rhs.customerName)
				.append(this.telephoneNumber, rhs.telephoneNumber)
				.append(this.postaddress, rhs.postaddress)
				.append(this.customerChannel, rhs.customerChannel)
				.append(this.email, rhs.email)
				.append(this.hangyeType, rhs.hangyeType)
				.append(this.area, rhs.area)
				.append(this.loanMoney, rhs.loanMoney)
				.append(this.loanPeriod, rhs.loanPeriod)
				.append(this.loanDate, rhs.loanDate)
				.append(this.loanType, rhs.loanType)
				.append(this.isMortgage, rhs.isMortgage)
				.append(this.mortgageRemark, rhs.mortgageRemark)
				.append(this.remark, rhs.remark)
				.append(this.creatorId, rhs.creatorId)
				.append(this.createDate, rhs.createDate)
				.append(this.belongId, rhs.belongId)
				.append(this.companyId, rhs.companyId)
				.append(this.followUpType, rhs.followUpType)
				.append(this.nextFollowDate, rhs.nextFollowDate)
				.append(this.followUpRemark, rhs.followUpRemark)
				.append(this.followUpcount, rhs.followUpcount)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.perId) 
				.append(this.customerType) 
				.append(this.customerName) 
				.append(this.telephoneNumber) 
				.append(this.postaddress) 
				.append(this.customerChannel) 
				.append(this.email) 
				.append(this.hangyeType) 
				.append(this.area) 
				.append(this.loanMoney) 
				.append(this.loanPeriod) 
				.append(this.loanDate) 
				.append(this.loanType) 
				.append(this.isMortgage) 
				.append(this.mortgageRemark) 
				.append(this.remark) 
				.append(this.creatorId) 
				.append(this.createDate) 
				.append(this.belongId) 
				.append(this.companyId) 
				.append(this.followUpType) 
				.append(this.nextFollowDate) 
				.append(this.followUpRemark) 
				.append(this.followUpcount) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("perId", this.perId) 
				.append("customerType", this.customerType) 
				.append("customerName", this.customerName) 
				.append("telephoneNumber", this.telephoneNumber) 
				.append("postaddress", this.postaddress) 
				.append("customerChannel", this.customerChannel) 
				.append("email", this.email) 
				.append("hangyeType", this.hangyeType) 
				.append("area", this.area) 
				.append("loanMoney", this.loanMoney) 
				.append("loanPeriod", this.loanPeriod) 
				.append("loanDate", this.loanDate) 
				.append("loanType", this.loanType) 
				.append("isMortgage", this.isMortgage) 
				.append("mortgageRemark", this.mortgageRemark) 
				.append("remark", this.remark) 
				.append("creatorId", this.creatorId) 
				.append("createDate", this.createDate) 
				.append("belongId", this.belongId) 
				.append("companyId", this.companyId) 
				.append("followUpType", this.followUpType) 
				.append("nextFollowDate", this.nextFollowDate) 
				.append("followUpRemark", this.followUpRemark) 
				.append("followUpcount", this.followUpcount) 
				.toString();
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getFixedTelephone() {
		return fixedTelephone;
	}

	public void setFixedTelephone(String fixedTelephone) {
		this.fixedTelephone = fixedTelephone;
	}

	public String getTelephoneNumber2() {
		return telephoneNumber2;
	}

	public void setTelephoneNumber2(String telephoneNumber2) {
		this.telephoneNumber2 = telephoneNumber2;
	}

	public Integer getPersonType() {
		return personType;
	}

	public void setPersonType(Integer personType) {
		this.personType = personType;
	}

	public BigDecimal getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
	}

	public String getIntentionProduct() {
		return intentionProduct;
	}

	public void setIntentionProduct(String intentionProduct) {
		this.intentionProduct = intentionProduct;
	}

	public String getInvestExperience() {
		return investExperience;
	}

	public void setInvestExperience(String investExperience) {
		this.investExperience = investExperience;
	}

	public String getInvestMonth() {
		return investMonth;
	}

	public void setInvestMonth(String investMonth) {
		this.investMonth = investMonth;
	}

	public Date getValidEnddate() {
		return validEnddate;
	}

	public void setValidEnddate(Date validEnddate) {
		this.validEnddate = validEnddate;
	}

	public String getIsHaveinvest() {
		return isHaveinvest;
	}

	public void setIsHaveinvest(String isHaveinvest) {
		this.isHaveinvest = isHaveinvest;
	}

	public String getIsHaveinvestExperience() {
		return isHaveinvestExperience;
	}

	public void setIsHaveinvestExperience(String isHaveinvestExperience) {
		this.isHaveinvestExperience = isHaveinvestExperience;
	}

	public String getRiskRequire() {
		return riskRequire;
	}

	public void setRiskRequire(String riskRequire) {
		this.riskRequire = riskRequire;
	}

	

}
