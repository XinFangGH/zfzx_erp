package com.credit.proj.entity;


public class VProjMortEducation  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String address;//土地地点
     private String certificatenumber;//证件号码
     private String propertyperson;//产权人
     private Double acreage;//占地面积(M2)
     private java.util.Date buytime;//购买年份
     private Double residualyears;//剩余年限(年)
     private Double builacreage;//建筑面积(M2)
     private Double groundrentpriceformonth;//同等土地每月出租价格(元/月/M2)
     private Double buildrentpriceformonth;//同等建筑物每月出租价格(元/月/M2)
     private Double groundtenancyrangeprice;//土地租赁模型估值(万元)
     private Double buildtenancyrangeprice;//建筑物租赁模型估值(万元)
     private String groundCharacterIdValue;//土地性质值--数据字典
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典

     private Integer groundCharacterId;//土地性质id
     private Integer descriptionId;//地段描述id
     private Integer registerInfoId;//登记情况id
     private String objectType;
    /** default constructor */
    public VProjMortEducation() {
    }

	/** minimal constructor */
    public VProjMortEducation(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortEducation(Integer id, Integer mortgageid, String address, 
    		String certificatenumber, String propertyperson, Double acreage, 
    		java.util.Date buytime, Double residualyears, Double builacreage, 
    		Double groundrentpriceformonth, Double buildrentpriceformonth, 
    		Double groundtenancyrangeprice, Double buildtenancyrangeprice, 
    		String groundCharacterIdValue, String descriptionIdValue, String registerInfoIdValue,
    		Integer groundCharacterId,Integer descriptionId,Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.address = address;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.acreage = acreage;
        this.buytime = buytime;
        this.residualyears = residualyears;
        this.builacreage = builacreage;
        this.groundrentpriceformonth = groundrentpriceformonth;
        this.buildrentpriceformonth = buildrentpriceformonth;
        this.groundtenancyrangeprice = groundtenancyrangeprice;
        this.buildtenancyrangeprice = buildtenancyrangeprice;
        this.groundCharacterIdValue = groundCharacterIdValue;
        this.descriptionIdValue = descriptionIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.groundCharacterId = groundCharacterId;
        this.descriptionId = descriptionId;
        this.registerInfoId = registerInfoId;
    }
   
    public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

    public String getAddress() {
        return this.address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }

    public String getCertificatenumber() {
        return this.certificatenumber;
    }
    
    public void setCertificatenumber(String certificatenumber) {
        this.certificatenumber = certificatenumber;
    }

    public String getPropertyperson() {
        return this.propertyperson;
    }
    
    public void setPropertyperson(String propertyperson) {
        this.propertyperson = propertyperson;
    }

    public Double getAcreage() {
        return this.acreage;
    }
    
    public void setAcreage(Double acreage) {
        this.acreage = acreage;
    }

    

    public java.util.Date getBuytime() {
		return buytime;
	}

	public void setBuytime(java.util.Date buytime) {
		this.buytime = buytime;
	}

	public Double getResidualyears() {
        return this.residualyears;
    }
    
    public void setResidualyears(Double residualyears) {
        this.residualyears = residualyears;
    }

    public Double getBuilacreage() {
        return this.builacreage;
    }
    
    public void setBuilacreage(Double builacreage) {
        this.builacreage = builacreage;
    }

    public Double getGroundrentpriceformonth() {
        return this.groundrentpriceformonth;
    }
    
    public void setGroundrentpriceformonth(Double groundrentpriceformonth) {
        this.groundrentpriceformonth = groundrentpriceformonth;
    }

    public Double getBuildrentpriceformonth() {
        return this.buildrentpriceformonth;
    }
    
    public void setBuildrentpriceformonth(Double buildrentpriceformonth) {
        this.buildrentpriceformonth = buildrentpriceformonth;
    }

    public Double getGroundtenancyrangeprice() {
        return this.groundtenancyrangeprice;
    }
    
    public void setGroundtenancyrangeprice(Double groundtenancyrangeprice) {
        this.groundtenancyrangeprice = groundtenancyrangeprice;
    }

    public Double getBuildtenancyrangeprice() {
        return this.buildtenancyrangeprice;
    }
    
    public void setBuildtenancyrangeprice(Double buildtenancyrangeprice) {
        this.buildtenancyrangeprice = buildtenancyrangeprice;
    }

    public String getGroundCharacterIdValue() {
        return this.groundCharacterIdValue;
    }
    
    public void setGroundCharacterIdValue(String groundCharacterIdValue) {
        this.groundCharacterIdValue = groundCharacterIdValue;
    }

    public String getDescriptionIdValue() {
        return this.descriptionIdValue;
    }
    
    public void setDescriptionIdValue(String descriptionIdValue) {
        this.descriptionIdValue = descriptionIdValue;
    }

    public String getRegisterInfoIdValue() {
        return this.registerInfoIdValue;
    }
    
    public void setRegisterInfoIdValue(String registerInfoIdValue) {
        this.registerInfoIdValue = registerInfoIdValue;
    }

	public Integer getGroundCharacterId() {
		return groundCharacterId;
	}

	public void setGroundCharacterId(Integer groundCharacterId) {
		this.groundCharacterId = groundCharacterId;
	}

	public Integer getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}

	public Integer getRegisterInfoId() {
		return registerInfoId;
	}

	public void setRegisterInfoId(Integer registerInfoId) {
		this.registerInfoId = registerInfoId;
	}
    
}