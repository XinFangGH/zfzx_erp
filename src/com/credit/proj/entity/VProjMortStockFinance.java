package com.credit.proj.entity;


public class VProjMortStockFinance  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String stockownership;//控制权人
     private Integer corporationname;//目标公司名称--存放企业表id
     private String licencenumber;//营业执照号码
     private Double enrolcapital;//注册资本(万元)
     //private Double enroltiame;//注册时间(年)
     private Double managementtime;//经营时间(年)
     private String managementStatusIdValue;//经营状况值--数据字典
     private Double stockownershippercent;//股权(%)
     private Double netassets;//净资产(万元)
     private Double modelrangeprice;//模型估价
     //private String productTraitIdValue;//经营产品特性值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     private Integer managementStatusId;//经营状况id
     //private Integer productTraitId;//经营产品特性id
     private Integer registerInfoId;//登记情况id
     
     private Integer legalpersonid;//法人id
     private String legalperson;//法人代表name
     private java.util.Date registerdate;//注册时间
     private String enterprisename;//企业简称
     
     private String businessType;
     private Integer hangyeType;//行业类型id
     private String hangyeTypeValue;//行业类型数据字典值
     
     /*private String test1;
     private String test2 ;
     private String test3 ;
     private String test4 ;*/
    /** default constructor */
    public VProjMortStockFinance() {
    }

	/** minimal constructor */
    public VProjMortStockFinance(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortStockFinance(Integer id, Integer mortgageid, String stockownership, 
    		Integer corporationname, String licencenumber, Double enrolcapital,
    		Double managementtime, String managementStatusIdValue, Double stockownershippercent, 
    		Double netassets, Double modelrangeprice,String registerInfoIdValue,
    		Integer managementStatusId,Integer registerInfoId,Integer legalpersonid,String legalperson,
    		java.util.Date registerdate,String enterprisename,Integer hangyeType,
    		String hangyeTypeValue,String businessType) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.stockownership = stockownership;
        this.corporationname = corporationname;
        this.licencenumber = licencenumber;
        this.enrolcapital = enrolcapital;
        this.managementtime = managementtime;
        this.managementStatusIdValue = managementStatusIdValue;
        this.stockownershippercent = stockownershippercent;
        this.netassets = netassets;
        this.modelrangeprice = modelrangeprice;
        this.registerInfoIdValue = registerInfoIdValue;
        this.managementStatusId = managementStatusId;
        this.registerInfoId = registerInfoId;
        this.legalpersonid = legalpersonid;
        this.legalperson = legalperson;
        this.registerdate = registerdate;
        this.enterprisename = enterprisename;
        this.hangyeType = hangyeType;
        this.hangyeTypeValue = hangyeTypeValue;
        this.businessType = businessType;
    }

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMortgageid() {
        return this.mortgageid;
    }
    
    public void setMortgageid(Integer mortgageid) {
        this.mortgageid = mortgageid;
    }

    public String getStockownership() {
        return this.stockownership;
    }
    
    public void setStockownership(String stockownership) {
        this.stockownership = stockownership;
    }


    public Integer getCorporationname() {
		return corporationname;
	}

	public void setCorporationname(Integer corporationname) {
		this.corporationname = corporationname;
	}

	public String getLicencenumber() {
        return this.licencenumber;
    }
    
    public void setLicencenumber(String licencenumber) {
        this.licencenumber = licencenumber;
    }

    public Double getEnrolcapital() {
        return this.enrolcapital;
    }
    
    public void setEnrolcapital(Double enrolcapital) {
        this.enrolcapital = enrolcapital;
    }

	public Double getManagementtime() {
		return managementtime;
	}

	public void setManagementtime(Double managementtime) {
		this.managementtime = managementtime;
	}

	public String getManagementStatusIdValue() {
		return managementStatusIdValue;
	}

	public void setManagementStatusIdValue(String managementStatusIdValue) {
		this.managementStatusIdValue = managementStatusIdValue;
	}

	public Double getStockownershippercent() {
        return this.stockownershippercent;
    }
    
    public void setStockownershippercent(Double stockownershippercent) {
        this.stockownershippercent = stockownershippercent;
    }

    public Double getNetassets() {
        return this.netassets;
    }
    
    public void setNetassets(Double netassets) {
        this.netassets = netassets;
    }

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
    }

    public String getRegisterInfoIdValue() {
        return this.registerInfoIdValue;
    }
    
    public void setRegisterInfoIdValue(String registerInfoIdValue) {
        this.registerInfoIdValue = registerInfoIdValue;
    }

	public Integer getManagementStatusId() {
		return managementStatusId;
	}

	public void setManagementStatusId(Integer managementStatusId) {
		this.managementStatusId = managementStatusId;
	}

	public Integer getRegisterInfoId() {
		return registerInfoId;
	}

	public void setRegisterInfoId(Integer registerInfoId) {
		this.registerInfoId = registerInfoId;
	}

	public Integer getLegalpersonid() {
		return legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public String getLegalperson() {
		return legalperson;
	}

	public void setLegalperson(String legalperson) {
		this.legalperson = legalperson;
	}

	public java.util.Date getRegisterdate() {
		return registerdate;
	}

	public void setRegisterdate(java.util.Date registerdate) {
		this.registerdate = registerdate;
	}
	
	
	public String getEnterprisename() {
		return enterprisename;
	}

	public void setEnterprisename(String enterprisename) {
		this.enterprisename = enterprisename;
	}

	public Integer getHangyeType() {
		return hangyeType;
	}

	public void setHangyeType(Integer hangyeType) {
		this.hangyeType = hangyeType;
	}

	public String getHangyeTypeValue() {
		return hangyeTypeValue;
	}

	public void setHangyeTypeValue(String hangyeTypeValue) {
		this.hangyeTypeValue = hangyeTypeValue;
	}

	public String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

}