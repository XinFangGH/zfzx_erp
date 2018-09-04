package com.zhiwei.credit.model.thirdInterface;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
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
 * WebBankCode Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class WebBankCode extends com.hurong.core.model.BaseModel {

    protected Long id;
    /**
     * 银行名称
     */
	protected String bankName;
	/**
	 * 名称简写
	 */
	protected String bankCode;
	/**
	 * 银行logo
	 */
	protected String bankLogo;
	/**
	 * 第三方标识
	 */
	protected String thirdPayConfig;
	/**
	 * 银行业务类型
	 * 1：个人网银
	 * 2：企业网银
	 * 3：快捷支付
	 * 银行既可以做个人网银或企业网银   拼接为12    其他类似  查询是用like查询
	 */
	protected String bankType;
	/**
	 * 是否显示
	 * 0 表示不显示
	 * 1表示显示
	 */
	protected Integer orderNum;
	
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	protected String imgURL;
	
	
	


	public String getImgURL() {
		return imgURL;
	}

	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	/**
	 * Default Empty Constructor for class WebBankCode
	 */
	public WebBankCode () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WebBankCode
	 */
	public WebBankCode (
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
	 * 	 * @return String
	 * @hibernate.property column="bankName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBankName() {
		return this.bankName;
	}
	
	/**
	 * Set the bankName
	 */	
	public void setBankName(String aValue) {
		this.bankName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="bankCode" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getBankCode() {
		return this.bankCode;
	}
	
	/**
	 * Set the bankCode
	 */	
	public void setBankCode(String aValue) {
		this.bankCode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="bankLogo" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getBankLogo() {
		return this.bankLogo;
	}
	
	/**
	 * Set the bankLogo
	 */	
	public void setBankLogo(String aValue) {
		this.bankLogo = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="thirdPayConfig" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getThirdPayConfig() {
		return this.thirdPayConfig;
	}
	
	/**
	 * Set the thirdPayConfig
	 */	
	public void setThirdPayConfig(String aValue) {
		this.thirdPayConfig = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WebBankCode)) {
			return false;
		}
		WebBankCode rhs = (WebBankCode) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.bankName, rhs.bankName)
				.append(this.bankCode, rhs.bankCode)
				.append(this.bankLogo, rhs.bankLogo)
				.append(this.thirdPayConfig, rhs.thirdPayConfig)
				.append(this.orderNum, rhs.orderNum)
				.append(this.imgURL, rhs.imgURL)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.bankName) 
				.append(this.bankCode) 
				.append(this.bankLogo) 
				.append(this.thirdPayConfig) 
				.append(this.orderNum) 
				.append(this.imgURL) 
					 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("bankName", this.bankName) 
				.append("bankCode", this.bankCode) 
				.append("bankLogo", this.bankLogo) 
				.append("thirdPayConfig", this.thirdPayConfig) 
				.append("orderNum", this.orderNum) 
				.append("imgURL", this.imgURL)
				
				.toString();
	}



}
