package com.zhiwei.credit.model.creditFlow.customer.enterprise;

import com.zhiwei.core.model.BaseModel;

/**
 * CsEnterpriseLeadteam entity. @author MyEclipse Persistence Tools
 */

public class EnterpriseLeadteam extends BaseModel {

	// Fields

	private Integer id;
	private Integer enterpriseid;
	private String name ;     //姓名
	private Boolean sex;      //性别
	private Integer cardtype; //证件类型
	private String cardnum;   //证件号码
	private String phone;       //固定电话
	private String fax ;      //传真号码
	private String cellphone; //手机号码
	private Integer age;        //年龄
	private Integer dgree;       //学历
	private String gschool;  // 毕业学校
	private String nowjobunit  ; // 现工作单位
	private String duty;      //职务
	private Integer rzhitotal;    //任职年限
	private Integer jobtotal;  //工作年限
	private String tradeexper;   //行业经验
	private String manageexper;  //管理经验
	private String mainrecord ;  //主要履历
	private String techtitle; //技术职称
	private Boolean director;  //是否董事成员
	private String remarks;   
	
	private String linkway;
	private Integer personid;  
	private String personname;
	//不跟数据库映射
	private String cardtypeValue;
	private String techtitleValue;
	
	

	// Constructors

	/** default constructor */
	public EnterpriseLeadteam() {
	}
	public EnterpriseLeadteam(Integer id, Integer enterpriseid, String name,
			Boolean sex, Integer cardtype, String cardnum, String phone,
			String fax, String cellphone, Integer age, Integer dgree,
			String gschool, String nowjobunit, String duty, Integer rzhitotal,
			Integer jobtotal, String tradeexper, String manageexper,
			String mainrecord, String techtitle, Boolean director,
			String remarks, String linkway, Integer personid, String personname) {
		super();
		this.id = id;
		this.enterpriseid = enterpriseid;
		this.name = name;
		this.sex = sex;
		this.cardtype = cardtype;
		this.cardnum = cardnum;
		this.phone = phone;
		this.fax = fax;
		this.cellphone = cellphone;
		this.age = age;
		this.dgree = dgree;
		this.gschool = gschool;
		this.nowjobunit = nowjobunit;
		this.duty = duty;
		this.rzhitotal = rzhitotal;
		this.jobtotal = jobtotal;
		this.tradeexper = tradeexper;
		this.manageexper = manageexper;
		this.mainrecord = mainrecord;
		this.techtitle = techtitle;
		this.director = director;
		this.remarks = remarks;
		this.linkway = linkway;
		this.personid = personid;
		this.personname = personname;
	}
	
	
	public String getCardtypeValue() {
		return cardtypeValue;
	}
	public void setCardtypeValue(String cardtypeValue) {
		this.cardtypeValue = cardtypeValue;
	}
	public String getTechtitleValue() {
		return techtitleValue;
	}
	public void setTechtitleValue(String techtitleValue) {
		this.techtitleValue = techtitleValue;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEnterpriseid() {
		return enterpriseid;
	}
	public void setEnterpriseid(Integer enterpriseid) {
		this.enterpriseid = enterpriseid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getSex() {
		return sex;
	}
	public void setSex(Boolean sex) {
		this.sex = sex;
	}
	public Integer getCardtype() {
		return cardtype;
	}
	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardnum() {
		return cardnum;
	}
	public void setCardnum(String cardnum) {
		this.cardnum = cardnum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getDgree() {
		return dgree;
	}
	public void setDgree(Integer dgree) {
		this.dgree = dgree;
	}
	public String getGschool() {
		return gschool;
	}
	public void setGschool(String gschool) {
		this.gschool = gschool;
	}
	public String getNowjobunit() {
		return nowjobunit;
	}
	public void setNowjobunit(String nowjobunit) {
		this.nowjobunit = nowjobunit;
	}
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	public Integer getRzhitotal() {
		return rzhitotal;
	}
	public void setRzhitotal(Integer rzhitotal) {
		this.rzhitotal = rzhitotal;
	}
	public Integer getJobtotal() {
		return jobtotal;
	}
	public void setJobtotal(Integer jobtotal) {
		this.jobtotal = jobtotal;
	}
	public String getTradeexper() {
		return tradeexper;
	}
	public void setTradeexper(String tradeexper) {
		this.tradeexper = tradeexper;
	}
	public String getManageexper() {
		return manageexper;
	}
	public void setManageexper(String manageexper) {
		this.manageexper = manageexper;
	}
	public String getMainrecord() {
		return mainrecord;
	}
	public void setMainrecord(String mainrecord) {
		this.mainrecord = mainrecord;
	}
	public String getTechtitle() {
		return techtitle;
	}
	public void setTechtitle(String techtitle) {
		this.techtitle = techtitle;
	}
	public Boolean getDirector() {
		return director;
	}
	public void setDirector(Boolean director) {
		this.director = director;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getLinkway() {
		return linkway;
	}
	public void setLinkway(String linkway) {
		this.linkway = linkway;
	}
	public Integer getPersonid() {
		return personid;
	}
	public void setPersonid(Integer personid) {
		this.personid = personid;
	}
	public String getPersonname() {
		return personname;
	}
	public void setPersonname(String personname) {
		this.personname = personname;
	}

	
}