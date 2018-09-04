package com.credit.proj.entity;


public class VProjMortIndustry  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String address;//土地地点
     private String certificatenumber;//证件号
     private String propertyperson;//产权人
     private Double occupyacreage;//占地面积(M2)
     private java.util.Date buytime;//购买年份
     private Double residualyears;//剩余年限(年)
     private Double mortgagesbalance;//土地抵质押贷款余额(万元)
     private Double rentpriceformonthone;//同等土地每月出租价格1(元/月/M2)
     private Double rentpriceformonthtow;//同等土地每月出租价格2(元/月/M2)
     private Double exchangepriceone;//同等土地成交价格1(元/月/M2)
     private Double exchangepricetow;//同等土地成交价格2(元/月/M2)
     private Double exchangeprice;//市场交易价格(元/M2)
     private Double tenancyrangeprice;//租赁模型估值(万元)
     private Double venditionrangeprice;//销售模型估值(万元)
     private String groundCharacterIdValue;//土地性质值--数据字典
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     
     private Integer groundCharacterId;//土地性质id
     private Integer descriptionId;//地段描述id
     private Integer registerInfoId;//登记情况id
     private String objectType;
    /** default constructor */
    public VProjMortIndustry() {
    }

	/** minimal constructor */
    public VProjMortIndustry(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortIndustry(Integer id, Integer mortgageid, String address, 
    		String certificatenumber, String propertyperson, Double occupyacreage, 
    		java.util.Date buytime, Double residualyears, Double mortgagesbalance, 
    		Double rentpriceformonthone, Double rentpriceformonthtow, Double exchangepriceone, 
    		Double exchangepricetow, Double exchangeprice, Double tenancyrangeprice, 
    		Double venditionrangeprice, String groundCharacterIdValue, String descriptionIdValue, 
    		String registerInfoIdValue,Integer groundCharacterId,Integer descriptionId,
    		Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.address = address;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.occupyacreage = occupyacreage;
        this.buytime = buytime;
        this.residualyears = residualyears;
        this.mortgagesbalance = mortgagesbalance;
        this.rentpriceformonthone = rentpriceformonthone;
        this.rentpriceformonthtow = rentpriceformonthtow;
        this.exchangepriceone = exchangepriceone;
        this.exchangepricetow = exchangepricetow;
        this.exchangeprice = exchangeprice;
        this.tenancyrangeprice = tenancyrangeprice;
        this.venditionrangeprice = venditionrangeprice;
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

    public Double getOccupyacreage() {
        return this.occupyacreage;
    }
    
    public void setOccupyacreage(Double occupyacreage) {
        this.occupyacreage = occupyacreage;
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

    public Double getMortgagesbalance() {
        return this.mortgagesbalance;
    }
    
    public void setMortgagesbalance(Double mortgagesbalance) {
        this.mortgagesbalance = mortgagesbalance;
    }

    public Double getRentpriceformonthone() {
        return this.rentpriceformonthone;
    }
    
    public void setRentpriceformonthone(Double rentpriceformonthone) {
        this.rentpriceformonthone = rentpriceformonthone;
    }

    public Double getRentpriceformonthtow() {
        return this.rentpriceformonthtow;
    }
    
    public void setRentpriceformonthtow(Double rentpriceformonthtow) {
        this.rentpriceformonthtow = rentpriceformonthtow;
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

    public Double getExchangeprice() {
        return this.exchangeprice;
    }
    
    public void setExchangeprice(Double exchangeprice) {
        this.exchangeprice = exchangeprice;
    }

    public Double getTenancyrangeprice() {
        return this.tenancyrangeprice;
    }
    
    public void setTenancyrangeprice(Double tenancyrangeprice) {
        this.tenancyrangeprice = tenancyrangeprice;
    }

    public Double getVenditionrangeprice() {
        return this.venditionrangeprice;
    }
    
    public void setVenditionrangeprice(Double venditionrangeprice) {
        this.venditionrangeprice = venditionrangeprice;
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