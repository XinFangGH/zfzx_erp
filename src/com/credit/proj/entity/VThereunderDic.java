package com.credit.proj.entity;



public class VThereunderDic implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer dicId;
	private String value;
	private Integer personid;
	private String licensenum;
	private String companyname;
	private Integer relate;
	private Integer id;
	private java.util.Date registerdate;
	private Double registercapital;
	private String address;
	private String lnpname;
	private Integer lnpid;
	private String phone;
	private String remarks;
	private String name ;
	private String shortname ;
	private String enterprisename;
	
	
	// Constructors

	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getPersonid() {
		return personid;
	}

	public void setPersonid(Integer personid) {
		this.personid = personid;
	}

	public String getLicensenum() {
		return licensenum;
	}

	public void setLicensenum(String licensenum) {
		this.licensenum = licensenum;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public Integer getRelate() {
		return relate;
	}

	public void setRelate(Integer relate) {
		this.relate = relate;
	}


	public Integer getDicId() {
		return dicId;
	}

	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}

	public java.util.Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(java.util.Date registerdate) {
		this.registerdate = registerdate;
	}

	public Double getRegistercapital() {
		return registercapital;
	}

	public void setRegistercapital(Double registercapital) {
		this.registercapital = registercapital;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLnpname() {
		return lnpname;
	}

	public void setLnpname(String lnpname) {
		this.lnpname = lnpname;
	}

	public Integer getLnpid() {
		return lnpid;
	}

	public void setLnpid(Integer lnpid) {
		this.lnpid = lnpid;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortname() {
		return shortname;
	}

	public void setShortname(String shortname) {
		this.shortname = shortname;
	}

	/** default constructor */
	public VThereunderDic() {
	}

	/** minimal constructor */
	

	/** full constructor */
	public VThereunderDic(Integer id, String value, Integer personid,
			String licensenum, String companyname, Integer relate,
			Integer dicId, java.util.Date registerdate, Double registercapital,
			String address, String lnpname, Integer lnpid, String phone,
			String remarks,String shortname ,String name) {
		this.id = id;
		this.value = value;
		this.personid = personid;
		this.licensenum = licensenum;
		this.companyname = companyname;
		this.relate = relate;
		this.dicId = dicId;
		this.registerdate = registerdate;
		this.registercapital = registercapital;
		this.address = address;
		this.lnpname = lnpname;
		this.lnpid = lnpid;
		this.phone = phone;
		this.remarks = remarks;
	}


}