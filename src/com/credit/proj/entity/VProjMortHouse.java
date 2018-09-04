package com.credit.proj.entity;


public class VProjMortHouse  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String houseaddress;//房地产地点
     private String certificatenumber;//证件号码
     private String propertyperson;//产权人
     private String mutualofperson;//共有人
     private Double buildacreage;//建筑面积(M2)
     private java.util.Date buildtime;//建成年份
     private Double residualyears;//剩余年限
     private Double mortgagesbalance;//按揭余额(万元)
     private Double exchangepriceone;//同等房产单位交易单价1(元/M2)
     private Double exchangepricetow;//同等房产单位交易单价2(元/M2)
     private Double exchangepricethree;//同等房产单位交易单价3(元/M2)
     private Double exchangefinalprice;//新房交易单价(元/M2)
     private Double modelrangeprice;//模型估值(万元)
     private String propertyRightIdValue;//产权性质值--数据字典
     private String constructionTypeIdValue;//建筑式样值--数据字典
     private String constructionFrameIdValue;//建筑结构值--数据字典
     private String houseTypeIdValue;//户型结构值--数据字典
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     
     private Integer propertyRightId;//产权性质id
     private Integer constructionTypeId;//建筑式样id
     private Integer constructionFrameId;//建筑结构id
     private Integer houseTypeId;//户型结构id
     private Integer descriptionId;//地段描述id
     private Integer registerInfoId;//登记情况id
     private String type;
     private String objectType;
    /** default constructor */
    public VProjMortHouse() {
    }

	/** minimal constructor */
    public VProjMortHouse(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortHouse(Integer id, Integer mortgageid, String houseaddress, 
    		String certificatenumber, String propertyperson, String mutualofperson, 
    		Double buildacreage, java.util.Date buildtime, Double residualyears, 
    		Double mortgagesbalance, Double exchangepriceone, Double exchangepricetow, 
    		Double exchangepricethree, Double exchangefinalprice, Double modelrangeprice, 
    		String propertyRightIdValue, String constructionTypeIdValue, 
    		String constructionFrameIdValue, String houseTypeIdValue, String descriptionIdValue, 
    		String registerInfoIdValue,Integer propertyRightId,Integer constructionTypeId,
    		Integer constructionFrameId,Integer houseTypeId,Integer descriptionId,Integer registerInfoId,String type) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.houseaddress = houseaddress;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.mutualofperson = mutualofperson;
        this.buildacreage = buildacreage;
        this.buildtime = buildtime;
        this.residualyears = residualyears;
        this.mortgagesbalance = mortgagesbalance;
        this.exchangepriceone = exchangepriceone;
        this.exchangepricetow = exchangepricetow;
        this.exchangepricethree = exchangepricethree;
        this.exchangefinalprice = exchangefinalprice;
        this.modelrangeprice = modelrangeprice;
        this.propertyRightIdValue = propertyRightIdValue;
        this.constructionTypeIdValue = constructionTypeIdValue;
        this.constructionFrameIdValue = constructionFrameIdValue;
        this.houseTypeIdValue = houseTypeIdValue;
        this.descriptionIdValue = descriptionIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.propertyRightId = propertyRightId;
        this.constructionTypeId = constructionTypeId;
        this.constructionFrameId = constructionFrameId;
        this.houseTypeId = houseTypeId;
        this.descriptionId = descriptionId;
        this.registerInfoId = registerInfoId;
        this.type=type;
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

    public String getHouseaddress() {
        return this.houseaddress;
    }
    
    public void setHouseaddress(String houseaddress) {
        this.houseaddress = houseaddress;
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

    public String getMutualofperson() {
        return this.mutualofperson;
    }
    
    public void setMutualofperson(String mutualofperson) {
        this.mutualofperson = mutualofperson;
    }

    public Double getBuildacreage() {
        return this.buildacreage;
    }
    
    public void setBuildacreage(Double buildacreage) {
        this.buildacreage = buildacreage;
    }

    public java.util.Date getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(java.util.Date buildtime) {
		this.buildtime = buildtime;
	}

	public Double getResidualyears() {
        return this.residualyears;
    }
    
    public void setResidualyears(Double residualyears) {
        this.residualyears = residualyears;
    }

    public Double getMortgagesbalance() {
        return this.mortgagesbalance;
    }
    
    public void setMortgagesbalance(Double mortgagesbalance) {
        this.mortgagesbalance = mortgagesbalance;
    }

    public Double getExchangepriceone() {
        return this.exchangepriceone;
    }
    
    public void setExchangepriceone(Double exchangepriceone) {
        this.exchangepriceone = exchangepriceone;
    }

    public Double getExchangepricetow() {
        return this.exchangepricetow;
    }
    
    public void setExchangepricetow(Double exchangepricetow) {
        this.exchangepricetow = exchangepricetow;
    }

    public Double getExchangepricethree() {
        return this.exchangepricethree;
    }
    
    public void setExchangepricethree(Double exchangepricethree) {
        this.exchangepricethree = exchangepricethree;
    }

    public Double getExchangefinalprice() {
        return this.exchangefinalprice;
    }
    
    public void setExchangefinalprice(Double exchangefinalprice) {
        this.exchangefinalprice = exchangefinalprice;
    }

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
    }

    public String getPropertyRightIdValue() {
        return this.propertyRightIdValue;
    }
    
    public void setPropertyRightIdValue(String propertyRightIdValue) {
        this.propertyRightIdValue = propertyRightIdValue;
    }

    public String getConstructionTypeIdValue() {
        return this.constructionTypeIdValue;
    }
    
    public void setConstructionTypeIdValue(String constructionTypeIdValue) {
        this.constructionTypeIdValue = constructionTypeIdValue;
    }

    public String getConstructionFrameIdValue() {
        return this.constructionFrameIdValue;
    }
    
    public void setConstructionFrameIdValue(String constructionFrameIdValue) {
        this.constructionFrameIdValue = constructionFrameIdValue;
    }

    public String getHouseTypeIdValue() {
        return this.houseTypeIdValue;
    }
    
    public void setHouseTypeIdValue(String houseTypeIdValue) {
        this.houseTypeIdValue = houseTypeIdValue;
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

	public Integer getPropertyRightId() {
		return propertyRightId;
	}

	public void setPropertyRightId(Integer propertyRightId) {
		this.propertyRightId = propertyRightId;
	}

	public Integer getConstructionTypeId() {
		return constructionTypeId;
	}

	public void setConstructionTypeId(Integer constructionTypeId) {
		this.constructionTypeId = constructionTypeId;
	}

	public Integer getConstructionFrameId() {
		return constructionFrameId;
	}

	public void setConstructionFrameId(Integer constructionFrameId) {
		this.constructionFrameId = constructionFrameId;
	}

	public Integer getHouseTypeId() {
		return houseTypeId;
	}

	public void setHouseTypeId(Integer houseTypeId) {
		this.houseTypeId = houseTypeId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
    
}