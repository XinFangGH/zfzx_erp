package com.credit.proj.entity;

public class VProjMortHouseGround  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String address;//土地地点
     private String certificatenumber;//证件号
     private String propertyperson;//产权人
     private Double acreage;//占地面积
     private Double anticipateacreage;//预规划住宅面积(M2)
     private Double mortgagesbalance;//土地抵质押贷款余额(万元)
     private Double exchangeprice;//同等土地成交单价(元/M2)
     private Double exchangepriceone;//同等土地成交单价1(元/M2)
     private Double exchangepricetow;//同等土地成交单价2(元/M2)
     private Double modelrangepriceone;//模型估值1(万元)
     private Double modelrangepricetow;//模型估值2(万元)
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     
     private Integer registerInfoId;//登记情况id
     private Integer descriptionId;//地段描述id
     private String objectType;
    /** default constructor */
    public VProjMortHouseGround() {
    }

	/** minimal constructor */
    public VProjMortHouseGround(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortHouseGround(Integer id, Integer mortgageid, String address, 
    		String certificatenumber, String propertyperson, Double acreage, 
    		Double anticipateacreage, Double mortgagesbalance, Double exchangeprice, 
    		Double exchangepriceone, Double exchangepricetow, Double modelrangepriceone, 
    		Double modelrangepricetow, String descriptionIdValue, String registerInfoIdValue,
    		Integer registerInfoId,Integer descriptionId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.address = address;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.acreage = acreage;
        this.anticipateacreage = anticipateacreage;
        this.mortgagesbalance = mortgagesbalance;
        this.exchangeprice = exchangeprice;
        this.exchangepriceone = exchangepriceone;
        this.exchangepricetow = exchangepricetow;
        this.modelrangepriceone = modelrangepriceone;
        this.modelrangepricetow = modelrangepricetow;
        this.descriptionIdValue = descriptionIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.registerInfoId = registerInfoId;
        this.descriptionId = descriptionId;
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
		return acreage;
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

    public Double getExchangeprice() {
        return this.exchangeprice;
    }
    
    public void setExchangeprice(Double exchangeprice) {
        this.exchangeprice = exchangeprice;
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

    public Double getModelrangepriceone() {
        return this.modelrangepriceone;
    }
    
    public void setModelrangepriceone(Double modelrangepriceone) {
        this.modelrangepriceone = modelrangepriceone;
    }

    public Double getModelrangepricetow() {
        return this.modelrangepricetow;
    }
    
    public void setModelrangepricetow(Double modelrangepricetow) {
        this.modelrangepricetow = modelrangepricetow;
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

	public Integer getRegisterInfoId() {
		return registerInfoId;
	}

	public void setRegisterInfoId(Integer registerInfoId) {
		this.registerInfoId = registerInfoId;
	}

	public Integer getDescriptionId() {
		return descriptionId;
	}

	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}
}