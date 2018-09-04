package com.credit.proj.entity;

public class VProjMortBusiness  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String address;//土地地点
     private String certificatenumber;//证件号码
     private String propertyperson;//产权人
     private Double acreage;//占地面积(M2)
     private Double anticipateacreage;//预规划住宅面积(M2)
     private Double mortgagesbalance;//土地抵质押贷款余额(万元)
     private Double groundexchangeprice;//同等土地成交单价(元/M2)
     private Double lendpriceformonthtow;//同等商业房屋每月出租价格2(元/M2)
     private Double lendpriceformonthone;//同等商业房屋每月出租价格1(元/M2)
     private Double exchangepriceone;//同等商业房屋成交价格1
     private Double exchangepricetow;//同等商业房屋成交价格2
     private Double modelrangeprice;//模型估值1(万元)
     private Double tenancyrangeprice;//租赁模型估值(万元)
     private Double venditionrangeprice;//销售模型估值(万元)
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     
     private Integer descriptionId;//地段描述id
     private Integer registerInfoId;//登记情况id
     private String objectType;
    public VProjMortBusiness() {
    }

    public VProjMortBusiness(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortBusiness(Integer id, Integer mortgageid, String address, 
    		String certificatenumber, String propertyperson, Double acreage, 
    		Double anticipateacreage, Double mortgagesbalance, Double groundexchangeprice, 
    		Double lendpriceformonthtow, Double lendpriceformonthone, Double exchangepriceone, 
    		Double exchangepricetow, Double modelrangeprice, Double tenancyrangeprice, 
    		Double venditionrangeprice, String descriptionIdValue, String registerInfoIdValue,
    		Integer descriptionId,Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.address = address;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.acreage = acreage;
        this.anticipateacreage = anticipateacreage;
        this.mortgagesbalance = mortgagesbalance;
        this.groundexchangeprice = groundexchangeprice;
        this.lendpriceformonthtow = lendpriceformonthtow;
        this.lendpriceformonthone = lendpriceformonthone;
        this.exchangepriceone = exchangepriceone;
        this.exchangepricetow = exchangepricetow;
        this.modelrangeprice = modelrangeprice;
        this.tenancyrangeprice = tenancyrangeprice;
        this.venditionrangeprice = venditionrangeprice;
        this.descriptionIdValue = descriptionIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
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

    public Double getAnticipateacreage() {
        return this.anticipateacreage;
    }
    
    public void setAnticipateacreage(Double anticipateacreage) {
        this.anticipateacreage = anticipateacreage;
    }

    public Double getMortgagesbalance() {
        return this.mortgagesbalance;
    }
    
    public void setMortgagesbalance(Double mortgagesbalance) {
        this.mortgagesbalance = mortgagesbalance;
    }

    public Double getGroundexchangeprice() {
        return this.groundexchangeprice;
    }
    
    public void setGroundexchangeprice(Double groundexchangeprice) {
        this.groundexchangeprice = groundexchangeprice;
    }

    public Double getLendpriceformonthtow() {
        return this.lendpriceformonthtow;
    }
    
    public void setLendpriceformonthtow(Double lendpriceformonthtow) {
        this.lendpriceformonthtow = lendpriceformonthtow;
    }

    public Double getLendpriceformonthone() {
        return this.lendpriceformonthone;
    }
    
    public void setLendpriceformonthone(Double lendpriceformonthone) {
        this.lendpriceformonthone = lendpriceformonthone;
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

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
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