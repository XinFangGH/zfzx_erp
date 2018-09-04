
package com.zhiwei.credit.model.customer;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
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
 * InvestEnterprise Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class InvestEnterprise extends com.zhiwei.core.model.BaseModel {

    protected Long id;
	protected Integer controlpersonid;
	protected Long legalpersonid;
	protected Integer linkmampersonid;
	protected String enterprisename;
	protected String shortname;
	protected String organizecode;
	protected String ownership;
	protected String tradetype;
	protected String cciaa;
	protected String managescope;
	protected String capitalkind;
	protected Double registermoney;
	protected String address;
	protected String summary;
	protected String factaddress;
	protected String managecity;
	protected String gslname;
	protected String gslexamine;
	protected java.util.Date gslexaminedate;
	protected String taxname;
	protected String taxnum;
	protected String taxexamine;
	protected String website;
	protected String changer;
	protected java.util.Date changedate;
	protected String lkmduty;
	protected java.util.Date taxexaminedate;
	protected String enjoytax;
	protected String dstaxname;
	protected String dstaxnum;
	protected String dstaxexamine;
	protected java.util.Date dsexaminedate;
	protected String enjoyds;
	protected String creater;
	protected java.util.Date createdate;
	protected String telephone;
	protected String fax;
	protected String receivemail;
	protected java.util.Date registerstartdate;
	protected java.util.Date registerenddate;
	protected String creditaccountbum;
	protected String area;
	protected String postcoding;
	protected java.util.Date opendate;
	protected Integer employeetotal;
	protected Integer hangyetype;
	protected String email;
	protected Long createrId;
	protected String createrName;
	protected String belongedName;
	protected String depName;
	protected String belongedId;
	protected String customerLevel;
	protected String isBlack;
	protected String blackReason;
	protected Short jyplace;
	protected java.math.BigDecimal areaMeasure;
	protected java.math.BigDecimal rent;
	protected String isLicense;
	protected Integer headerId;
	protected Integer rootHangYeType;
	protected String mainperson;
	protected String mainHangye;
	
	protected Integer enterpriseYyzzId;
	private String enterpriseYyzzURL;//营业执照扫描件的url
    private String enterpriseYyzzExtendName;
    
    protected Integer enterpriseZzjgId;
	private String enterpriseZzjgURL;
    private String enterpriseZzjgExtendName;
    
    protected Integer enterpriseSwzId;
	private String enterpriseSwzURL;
    private String enterpriseSwzExtendName;
    
    protected Integer enterpriseDkkId;
	private String enterpriseDkkURL;
    private String enterpriseDkkExtendName;
    
    private Date lastCareDate;//最后关怀时间
    
    //add by zcb 2014-03-19
    private String businessType;
    private BigDecimal sumCreditLimit;//总授信额度  （只试用与小贷投资企业客户）
    private BigDecimal nowCreditLimit;//当前授信额度  （只试用与小贷投资企业客户）
    private BigDecimal balanceCreditLimit;//剩余授信额度  （只试用与小贷投资企业客户）
    private String creditMark;
    
	/**
	 * Default Empty Constructor for class InvestEnterprise
	 */
	public InvestEnterprise () {
		super();
	}
	
	public String getCreditMark() {
		return creditMark;
	}

	public void setCreditMark(String creditMark) {
		this.creditMark = creditMark;
	}

	/**
	 * Default Key Fields Constructor for class InvestEnterprise
	 */
	public InvestEnterprise (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	public Date getLastCareDate() {
		return lastCareDate;
	}

	public void setLastCareDate(Date lastCareDate) {
		this.lastCareDate = lastCareDate;
	}

	public Integer getEnterpriseYyzzId() {
		return enterpriseYyzzId;
	}

	public void setEnterpriseYyzzId(Integer enterpriseYyzzId) {
		this.enterpriseYyzzId = enterpriseYyzzId;
	}

	public String getEnterpriseYyzzURL() {
		return enterpriseYyzzURL;
	}

	public void setEnterpriseYyzzURL(String enterpriseYyzzURL) {
		this.enterpriseYyzzURL = enterpriseYyzzURL;
	}

	public String getEnterpriseYyzzExtendName() {
		return enterpriseYyzzExtendName;
	}

	public void setEnterpriseYyzzExtendName(String enterpriseYyzzExtendName) {
		this.enterpriseYyzzExtendName = enterpriseYyzzExtendName;
	}

	public Integer getEnterpriseZzjgId() {
		return enterpriseZzjgId;
	}

	public void setEnterpriseZzjgId(Integer enterpriseZzjgId) {
		this.enterpriseZzjgId = enterpriseZzjgId;
	}

	public String getEnterpriseZzjgURL() {
		return enterpriseZzjgURL;
	}

	public void setEnterpriseZzjgURL(String enterpriseZzjgURL) {
		this.enterpriseZzjgURL = enterpriseZzjgURL;
	}

	public String getEnterpriseZzjgExtendName() {
		return enterpriseZzjgExtendName;
	}

	public void setEnterpriseZzjgExtendName(String enterpriseZzjgExtendName) {
		this.enterpriseZzjgExtendName = enterpriseZzjgExtendName;
	}

	public Integer getEnterpriseSwzId() {
		return enterpriseSwzId;
	}

	public void setEnterpriseSwzId(Integer enterpriseSwzId) {
		this.enterpriseSwzId = enterpriseSwzId;
	}

	public String getEnterpriseSwzURL() {
		return enterpriseSwzURL;
	}

	public void setEnterpriseSwzURL(String enterpriseSwzURL) {
		this.enterpriseSwzURL = enterpriseSwzURL;
	}

	public String getEnterpriseSwzExtendName() {
		return enterpriseSwzExtendName;
	}

	public void setEnterpriseSwzExtendName(String enterpriseSwzExtendName) {
		this.enterpriseSwzExtendName = enterpriseSwzExtendName;
	}

	public Integer getEnterpriseDkkId() {
		return enterpriseDkkId;
	}

	public void setEnterpriseDkkId(Integer enterpriseDkkId) {
		this.enterpriseDkkId = enterpriseDkkId;
	}

	public String getEnterpriseDkkURL() {
		return enterpriseDkkURL;
	}

	public void setEnterpriseDkkURL(String enterpriseDkkURL) {
		this.enterpriseDkkURL = enterpriseDkkURL;
	}

	public String getEnterpriseDkkExtendName() {
		return enterpriseDkkExtendName;
	}

	public void setEnterpriseDkkExtendName(String enterpriseDkkExtendName) {
		this.enterpriseDkkExtendName = enterpriseDkkExtendName;
	}

	/**
	 * 	 * @return Integer
     * @hibernate.id column="id" type="java.lang.Integer" generator-class="native"
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
	 * 人员表的主键id	 * @return Integer
	 * @hibernate.property column="controlpersonid" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getControlpersonid() {
		return this.controlpersonid;
	}
	
	/**
	 * Set the controlpersonid
	 */	
	public void setControlpersonid(Integer aValue) {
		this.controlpersonid = aValue;
	}	

	/**
	 * 人员表的主键id	 * @return Integer
	 * @hibernate.property column="legalpersonid" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Long getLegalpersonid() {
		return this.legalpersonid;
	}
	
	/**
	 * Set the legalpersonid
	 */	
	public void setLegalpersonid(Long aValue) {
		this.legalpersonid = aValue;
	}	

	/**
	 * 通过项目表客户名称id=企业表的id，找到lmpersonid=personid，最终找到人员表的联系人姓名	 * @return Integer
	 * @hibernate.property column="linkmampersonid" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getLinkmampersonid() {
		return this.linkmampersonid;
	}
	
	/**
	 * Set the linkmampersonid
	 */	
	public void setLinkmampersonid(Integer aValue) {
		this.linkmampersonid = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="enterprisename" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getEnterprisename() {
		return this.enterprisename;
	}
	
	/**
	 * Set the enterprisename
	 */	
	public void setEnterprisename(String aValue) {
		this.enterprisename = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="shortname" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getShortname() {
		return this.shortname;
	}
	
	/**
	 * Set the shortname
	 */	
	public void setShortname(String aValue) {
		this.shortname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="organizecode" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getOrganizecode() {
		return this.organizecode;
	}
	
	/**
	 * Set the organizecode
	 */	
	public void setOrganizecode(String aValue) {
		this.organizecode = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="ownership" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getOwnership() {
		return this.ownership;
	}
	
	/**
	 * Set the ownership
	 */	
	public void setOwnership(String aValue) {
		this.ownership = aValue;
	}	

	/**
	 * 企业所属行业	 * @return String
	 * @hibernate.property column="tradetype" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getTradetype() {
		return this.tradetype;
	}
	
	/**
	 * Set the tradetype
	 */	
	public void setTradetype(String aValue) {
		this.tradetype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="cciaa" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getCciaa() {
		return this.cciaa;
	}
	
	/**
	 * Set the cciaa
	 */	
	public void setCciaa(String aValue) {
		this.cciaa = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="managescope" type="java.lang.String" length="220" not-null="false" unique="false"
	 */
	public String getManagescope() {
		return this.managescope;
	}
	
	/**
	 * Set the managescope
	 */	
	public void setManagescope(String aValue) {
		this.managescope = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="capitalkind" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getCapitalkind() {
		return this.capitalkind;
	}
	
	/**
	 * Set the capitalkind
	 */	
	public void setCapitalkind(String aValue) {
		this.capitalkind = aValue;
	}	

	/**
	 * 	 * @return Double
	 * @hibernate.property column="registermoney" type="java.lang.Double" length="22" not-null="false" unique="false"
	 */
	public Double getRegistermoney() {
		return this.registermoney;
	}
	
	/**
	 * Set the registermoney
	 */	
	public void setRegistermoney(Double aValue) {
		this.registermoney = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="address" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getAddress() {
		return this.address;
	}
	
	/**
	 * Set the address
	 */	
	public void setAddress(String aValue) {
		this.address = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="factaddress" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getFactaddress() {
		return this.factaddress;
	}
	
	/**
	 * Set the factaddress
	 */	
	public void setFactaddress(String aValue) {
		this.factaddress = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="managecity" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getManagecity() {
		return this.managecity;
	}
	
	/**
	 * Set the managecity
	 */	
	public void setManagecity(String aValue) {
		this.managecity = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="gslname" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getGslname() {
		return this.gslname;
	}
	
	/**
	 * Set the gslname
	 */	
	public void setGslname(String aValue) {
		this.gslname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="gslexamine" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getGslexamine() {
		return this.gslexamine;
	}
	
	/**
	 * Set the gslexamine
	 */	
	public void setGslexamine(String aValue) {
		this.gslexamine = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="gslexaminedate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getGslexaminedate() {
		return this.gslexaminedate;
	}
	
	/**
	 * Set the gslexaminedate
	 */	
	public void setGslexaminedate(java.util.Date aValue) {
		this.gslexaminedate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="taxname" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getTaxname() {
		return this.taxname;
	}
	
	/**
	 * Set the taxname
	 */	
	public void setTaxname(String aValue) {
		this.taxname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="taxnum" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getTaxnum() {
		return this.taxnum;
	}
	
	/**
	 * Set the taxnum
	 */	
	public void setTaxnum(String aValue) {
		this.taxnum = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="taxexamine" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getTaxexamine() {
		return this.taxexamine;
	}
	
	/**
	 * Set the taxexamine
	 */	
	public void setTaxexamine(String aValue) {
		this.taxexamine = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="website" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getWebsite() {
		return this.website;
	}
	
	/**
	 * Set the website
	 */	
	public void setWebsite(String aValue) {
		this.website = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="changer" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getChanger() {
		return this.changer;
	}
	
	/**
	 * Set the changer
	 */	
	public void setChanger(String aValue) {
		this.changer = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="changedate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getChangedate() {
		return this.changedate;
	}
	
	/**
	 * Set the changedate
	 */	
	public void setChangedate(java.util.Date aValue) {
		this.changedate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="lkmduty" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getLkmduty() {
		return this.lkmduty;
	}
	
	/**
	 * Set the lkmduty
	 */	
	public void setLkmduty(String aValue) {
		this.lkmduty = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="taxexaminedate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getTaxexaminedate() {
		return this.taxexaminedate;
	}
	
	/**
	 * Set the taxexaminedate
	 */	
	public void setTaxexaminedate(java.util.Date aValue) {
		this.taxexaminedate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="enjoytax" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getEnjoytax() {
		return this.enjoytax;
	}
	
	/**
	 * Set the enjoytax
	 */	
	public void setEnjoytax(String aValue) {
		this.enjoytax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dstaxname" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getDstaxname() {
		return this.dstaxname;
	}
	
	/**
	 * Set the dstaxname
	 */	
	public void setDstaxname(String aValue) {
		this.dstaxname = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dstaxnum" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getDstaxnum() {
		return this.dstaxnum;
	}
	
	/**
	 * Set the dstaxnum
	 */	
	public void setDstaxnum(String aValue) {
		this.dstaxnum = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="dstaxexamine" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getDstaxexamine() {
		return this.dstaxexamine;
	}
	
	/**
	 * Set the dstaxexamine
	 */	
	public void setDstaxexamine(String aValue) {
		this.dstaxexamine = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="dsexaminedate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getDsexaminedate() {
		return this.dsexaminedate;
	}
	
	/**
	 * Set the dsexaminedate
	 */	
	public void setDsexaminedate(java.util.Date aValue) {
		this.dsexaminedate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="enjoyds" type="java.lang.String" length="45" not-null="false" unique="false"
	 */
	public String getEnjoyds() {
		return this.enjoyds;
	}
	
	/**
	 * Set the enjoyds
	 */	
	public void setEnjoyds(String aValue) {
		this.enjoyds = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creater" type="java.lang.String" length="45" not-null="false" unique="false"
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
	 * @hibernate.property column="telephone" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getTelephone() {
		return this.telephone;
	}
	
	/**
	 * Set the telephone
	 */	
	public void setTelephone(String aValue) {
		this.telephone = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="fax" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getFax() {
		return this.fax;
	}
	
	/**
	 * Set the fax
	 */	
	public void setFax(String aValue) {
		this.fax = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="receivemail" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getReceivemail() {
		return this.receivemail;
	}
	
	/**
	 * Set the receivemail
	 */	
	public void setReceivemail(String aValue) {
		this.receivemail = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="registerstartdate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRegisterstartdate() {
		return this.registerstartdate;
	}
	
	/**
	 * Set the registerstartdate
	 */	
	public void setRegisterstartdate(java.util.Date aValue) {
		this.registerstartdate = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="registerenddate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getRegisterenddate() {
		return this.registerenddate;
	}
	
	/**
	 * Set the registerenddate
	 */	
	public void setRegisterenddate(java.util.Date aValue) {
		this.registerenddate = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="creditaccountbum" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreditaccountbum() {
		return this.creditaccountbum;
	}
	
	/**
	 * Set the creditaccountbum
	 */	
	public void setCreditaccountbum(String aValue) {
		this.creditaccountbum = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="area" type="java.lang.String" length="448" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="postcoding" type="java.lang.String" length="24" not-null="false" unique="false"
	 */
	public String getPostcoding() {
		return this.postcoding;
	}
	
	/**
	 * Set the postcoding
	 */	
	public void setPostcoding(String aValue) {
		this.postcoding = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="opendate" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getOpendate() {
		return this.opendate;
	}
	
	/**
	 * Set the opendate
	 */	
	public void setOpendate(java.util.Date aValue) {
		this.opendate = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="employeetotal" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getEmployeetotal() {
		return this.employeetotal;
	}
	
	/**
	 * Set the employeetotal
	 */	
	public void setEmployeetotal(Integer aValue) {
		this.employeetotal = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="hangyetype" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getHangyetype() {
		return this.hangyetype;
	}
	
	/**
	 * Set the hangyetype
	 */	
	public void setHangyetype(Integer aValue) {
		this.hangyetype = aValue;
	}	

	/**
	 * 电子邮箱	 * @return String
	 * @hibernate.property column="email" type="java.lang.String" length="100" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="createrName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getCreaterName() {
		return this.createrName;
	}
	
	/**
	 * Set the createrName
	 */	
	public void setCreaterName(String aValue) {
		this.createrName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="belongedName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBelongedName() {
		return this.belongedName;
	}
	
	/**
	 * Set the belongedName
	 */	
	public void setBelongedName(String aValue) {
		this.belongedName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="depName" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getDepName() {
		return this.depName;
	}
	
	/**
	 * Set the depName
	 */	
	public void setDepName(String aValue) {
		this.depName = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="belongedId" type="java.lang.String" length="250" not-null="false" unique="false"
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
	 * 	 * @return String
	 * @hibernate.property column="customerLevel" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getCustomerLevel() {
		return this.customerLevel;
	}
	
	/**
	 * Set the customerLevel
	 */	
	public void setCustomerLevel(String aValue) {
		this.customerLevel = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isBlack" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsBlack() {
		return this.isBlack;
	}
	
	/**
	 * Set the isBlack
	 */	
	public void setIsBlack(String aValue) {
		this.isBlack = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="blackReason" type="java.lang.String" length="250" not-null="false" unique="false"
	 */
	public String getBlackReason() {
		return this.blackReason;
	}
	
	/**
	 * Set the blackReason
	 */	
	public void setBlackReason(String aValue) {
		this.blackReason = aValue;
	}	

	/**
	 * 	 * @return Short
	 * @hibernate.property column="jyplace" type="java.lang.Short" length="5" not-null="false" unique="false"
	 */
	public Short getJyplace() {
		return this.jyplace;
	}
	
	/**
	 * Set the jyplace
	 */	
	public void setJyplace(Short aValue) {
		this.jyplace = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="areaMeasure" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getAreaMeasure() {
		return this.areaMeasure;
	}
	
	/**
	 * Set the areaMeasure
	 */	
	public void setAreaMeasure(java.math.BigDecimal aValue) {
		this.areaMeasure = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="rent" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRent() {
		return this.rent;
	}
	
	/**
	 * Set the rent
	 */	
	public void setRent(java.math.BigDecimal aValue) {
		this.rent = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="isLicense" type="java.lang.String" length="1" not-null="false" unique="false"
	 */
	public String getIsLicense() {
		return this.isLicense;
	}
	
	/**
	 * Set the isLicense
	 */	
	public void setIsLicense(String aValue) {
		this.isLicense = aValue;
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
	 * @hibernate.property column="headerId" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getHeaderId() {
		return this.headerId;
	}
	
	/**
	 * Set the headerId
	 */	
	public void setHeaderId(Integer aValue) {
		this.headerId = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="rootHangYeType" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getRootHangYeType() {
		return this.rootHangYeType;
	}
	
	/**
	 * Set the rootHangYeType
	 */	
	public void setRootHangYeType(Integer aValue) {
		this.rootHangYeType = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mainperson" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getMainperson() {
		return this.mainperson;
	}
	
	/**
	 * Set the mainperson
	 */	
	public void setMainperson(String aValue) {
		this.mainperson = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="mainHangye" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getMainHangye() {
		return this.mainHangye;
	}
	
	/**
	 * Set the mainHangye
	 */	
	public void setMainHangye(String aValue) {
		this.mainHangye = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof InvestEnterprise)) {
			return false;
		}
		InvestEnterprise rhs = (InvestEnterprise) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.controlpersonid, rhs.controlpersonid)
				.append(this.legalpersonid, rhs.legalpersonid)
				.append(this.linkmampersonid, rhs.linkmampersonid)
				.append(this.enterprisename, rhs.enterprisename)
				.append(this.shortname, rhs.shortname)
				.append(this.organizecode, rhs.organizecode)
				.append(this.ownership, rhs.ownership)
				.append(this.tradetype, rhs.tradetype)
				.append(this.cciaa, rhs.cciaa)
				.append(this.managescope, rhs.managescope)
				.append(this.capitalkind, rhs.capitalkind)
				.append(this.registermoney, rhs.registermoney)
				.append(this.address, rhs.address)
				.append(this.factaddress, rhs.factaddress)
				.append(this.managecity, rhs.managecity)
				.append(this.gslname, rhs.gslname)
				.append(this.gslexamine, rhs.gslexamine)
				.append(this.gslexaminedate, rhs.gslexaminedate)
				.append(this.taxname, rhs.taxname)
				.append(this.taxnum, rhs.taxnum)
				.append(this.taxexamine, rhs.taxexamine)
				.append(this.website, rhs.website)
				.append(this.changer, rhs.changer)
				.append(this.changedate, rhs.changedate)
				.append(this.lkmduty, rhs.lkmduty)
				.append(this.taxexaminedate, rhs.taxexaminedate)
				.append(this.enjoytax, rhs.enjoytax)
				.append(this.dstaxname, rhs.dstaxname)
				.append(this.dstaxnum, rhs.dstaxnum)
				.append(this.dstaxexamine, rhs.dstaxexamine)
				.append(this.dsexaminedate, rhs.dsexaminedate)
				.append(this.enjoyds, rhs.enjoyds)
				.append(this.creater, rhs.creater)
				.append(this.createdate, rhs.createdate)
				.append(this.telephone, rhs.telephone)
				.append(this.fax, rhs.fax)
				.append(this.receivemail, rhs.receivemail)
				.append(this.registerstartdate, rhs.registerstartdate)
				.append(this.registerenddate, rhs.registerenddate)
				.append(this.creditaccountbum, rhs.creditaccountbum)
				.append(this.area, rhs.area)
				.append(this.postcoding, rhs.postcoding)
				.append(this.opendate, rhs.opendate)
				.append(this.employeetotal, rhs.employeetotal)
				.append(this.hangyetype, rhs.hangyetype)
				.append(this.email, rhs.email)
				.append(this.createrId, rhs.createrId)
				.append(this.createrName, rhs.createrName)
				.append(this.belongedName, rhs.belongedName)
				.append(this.depName, rhs.depName)
				.append(this.belongedId, rhs.belongedId)
				.append(this.customerLevel, rhs.customerLevel)
				.append(this.isBlack, rhs.isBlack)
				.append(this.blackReason, rhs.blackReason)
				.append(this.jyplace, rhs.jyplace)
				.append(this.areaMeasure, rhs.areaMeasure)
				.append(this.rent, rhs.rent)
				.append(this.isLicense, rhs.isLicense)
				.append(this.companyId, rhs.companyId)
				.append(this.headerId, rhs.headerId)
				.append(this.rootHangYeType, rhs.rootHangYeType)
				.append(this.mainperson, rhs.mainperson)
				.append(this.mainHangye, rhs.mainHangye)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.controlpersonid) 
				.append(this.legalpersonid) 
				.append(this.linkmampersonid) 
				.append(this.enterprisename) 
				.append(this.shortname) 
				.append(this.organizecode) 
				.append(this.ownership) 
				.append(this.tradetype) 
				.append(this.cciaa) 
				.append(this.managescope) 
				.append(this.capitalkind) 
				.append(this.registermoney) 
				.append(this.address) 
				.append(this.factaddress) 
				.append(this.managecity) 
				.append(this.gslname) 
				.append(this.gslexamine) 
				.append(this.gslexaminedate) 
				.append(this.taxname) 
				.append(this.taxnum) 
				.append(this.taxexamine) 
				.append(this.website) 
				.append(this.changer) 
				.append(this.changedate) 
				.append(this.lkmduty) 
				.append(this.taxexaminedate) 
				.append(this.enjoytax) 
				.append(this.dstaxname) 
				.append(this.dstaxnum) 
				.append(this.dstaxexamine) 
				.append(this.dsexaminedate) 
				.append(this.enjoyds) 
				.append(this.creater) 
				.append(this.createdate) 
				.append(this.telephone) 
				.append(this.fax) 
				.append(this.receivemail) 
				.append(this.registerstartdate) 
				.append(this.registerenddate) 
				.append(this.creditaccountbum) 
				.append(this.area) 
				.append(this.postcoding) 
				.append(this.opendate) 
				.append(this.employeetotal) 
				.append(this.hangyetype) 
				.append(this.email) 
				.append(this.createrId) 
				.append(this.createrName) 
				.append(this.belongedName) 
				.append(this.depName) 
				.append(this.belongedId) 
				.append(this.customerLevel) 
				.append(this.isBlack) 
				.append(this.blackReason) 
				.append(this.jyplace) 
				.append(this.areaMeasure) 
				.append(this.rent) 
				.append(this.isLicense) 
				.append(this.companyId) 
				.append(this.headerId) 
				.append(this.rootHangYeType) 
				.append(this.mainperson) 
				.append(this.mainHangye) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("controlpersonid", this.controlpersonid) 
				.append("legalpersonid", this.legalpersonid) 
				.append("linkmampersonid", this.linkmampersonid) 
				.append("enterprisename", this.enterprisename) 
				.append("shortname", this.shortname) 
				.append("organizecode", this.organizecode) 
				.append("ownership", this.ownership) 
				.append("tradetype", this.tradetype) 
				.append("cciaa", this.cciaa) 
				.append("managescope", this.managescope) 
				.append("capitalkind", this.capitalkind) 
				.append("registermoney", this.registermoney) 
				.append("address", this.address) 
				.append("factaddress", this.factaddress) 
				.append("managecity", this.managecity) 
				.append("gslname", this.gslname) 
				.append("gslexamine", this.gslexamine) 
				.append("gslexaminedate", this.gslexaminedate) 
				.append("taxname", this.taxname) 
				.append("taxnum", this.taxnum) 
				.append("taxexamine", this.taxexamine) 
				.append("website", this.website) 
				.append("changer", this.changer) 
				.append("changedate", this.changedate) 
				.append("lkmduty", this.lkmduty) 
				.append("taxexaminedate", this.taxexaminedate) 
				.append("enjoytax", this.enjoytax) 
				.append("dstaxname", this.dstaxname) 
				.append("dstaxnum", this.dstaxnum) 
				.append("dstaxexamine", this.dstaxexamine) 
				.append("dsexaminedate", this.dsexaminedate) 
				.append("enjoyds", this.enjoyds) 
				.append("creater", this.creater) 
				.append("createdate", this.createdate) 
				.append("telephone", this.telephone) 
				.append("fax", this.fax) 
				.append("receivemail", this.receivemail) 
				.append("registerstartdate", this.registerstartdate) 
				.append("registerenddate", this.registerenddate) 
				.append("creditaccountbum", this.creditaccountbum) 
				.append("area", this.area) 
				.append("postcoding", this.postcoding) 
				.append("opendate", this.opendate) 
				.append("employeetotal", this.employeetotal) 
				.append("hangyetype", this.hangyetype) 
				.append("email", this.email) 
				.append("createrId", this.createrId) 
				.append("createrName", this.createrName) 
				.append("belongedName", this.belongedName) 
				.append("depName", this.depName) 
				.append("belongedId", this.belongedId) 
				.append("customerLevel", this.customerLevel) 
				.append("isBlack", this.isBlack) 
				.append("blackReason", this.blackReason) 
				.append("jyplace", this.jyplace) 
				.append("areaMeasure", this.areaMeasure) 
				.append("rent", this.rent) 
				.append("isLicense", this.isLicense) 
				.append("companyId", this.companyId) 
				.append("headerId", this.headerId) 
				.append("rootHangYeType", this.rootHangYeType) 
				.append("mainperson", this.mainperson) 
				.append("mainHangye", this.mainHangye) 
				.toString();
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public BigDecimal getSumCreditLimit() {
		return sumCreditLimit;
	}

	public void setSumCreditLimit(BigDecimal sumCreditLimit) {
		this.sumCreditLimit = sumCreditLimit;
	}

	public BigDecimal getNowCreditLimit() {
		return nowCreditLimit;
	}

	public void setNowCreditLimit(BigDecimal nowCreditLimit) {
		this.nowCreditLimit = nowCreditLimit;
	}

	public BigDecimal getBalanceCreditLimit() {
		return balanceCreditLimit;
	}

	public void setBalanceCreditLimit(BigDecimal balanceCreditLimit) {
		this.balanceCreditLimit = balanceCreditLimit;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}



}
