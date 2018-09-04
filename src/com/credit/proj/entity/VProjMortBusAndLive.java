package com.credit.proj.entity;

public class VProjMortBusAndLive  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String address;//土地地点
     private String certificatenumber;//证件号码
     private String propertyperson;//产权人
     private Double acreage;//占地面积(M2)
     private Double anticipateacreage;//预规划住宅面积(M2)
     private Double mortgagesbalance;//土地抵质押贷款余额(万元)
     private Double exchangepriceground;//同等土地成交单价
     private Double rentpriceformonth;//同等商业房屋每月出租价格(元/M2)
     private Double exchangepricebusiness;//同等商业房屋成交价格(元/M2)
     private Double exchangepricehouse;//同等住宅成交单价(元/M2)
     private Double modelrangeprice;//模型估值1(万元)
     private Double tenancyrangeprice;//租赁模型估值(万元)
     private Double venditionrangeprice;//销售模型估值(万元)
     private String descriptionIdValue;//地段描述值--数据字典
     private String registerInfoIdValue;////登记情况值--数据字典
     
     private Integer descriptionId;//地段描述id
     private Integer registerInfoId;//登记情况id
     private String objectType;
    // Constructors

    /** default constructor */
    public VProjMortBusAndLive() {
    }

	/** minimal constructor */
    public VProjMortBusAndLive(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortBusAndLive(Integer id, Integer mortgageid, String address, 
    		String certificatenumber, String propertyperson, Double acreage, 
    		Double anticipateacreage, Double mortgagesbalance, Double exchangepriceground, 
    		Double rentpriceformonth, Double exchangepricebusiness, Double exchangepricehouse, 
    		Double modelrangeprice, Double tenancyrangeprice, Double venditionrangeprice, 
    		String descriptionIdValue, String registerInfoIdValue,Integer descriptionId,
    		Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.address = address;
        this.certificatenumber = certificatenumber;
        this.propertyperson = propertyperson;
        this.acreage = acreage;
        this.anticipateacreage = anticipateacreage;
        this.mortgagesbalance = mortgagesbalance;
        this.exchangepriceground = exchangepriceground;
        this.rentpriceformonth = rentpriceformonth;
        this.exchangepricebusiness = exchangepricebusiness;
        this.exchangepricehouse = exchangepricehouse;
        this.modelrangeprice = modelrangeprice;
        this.tenancyrangeprice = tenancyrangeprice;
        this.venditionrangeprice = venditionrangeprice;
        this.descriptionIdValue = descriptionIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.descriptionId = descriptionId;
        this.registerInfoId = registerInfoId;
    }

   
    // Property accessors
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

    public Double getExchangepriceground() {
        return this.exchangepriceground;
    }
    
    public void setExchangepriceground(Double exchangepriceground) {
        this.exchangepriceground = exchangepriceground;
    }

    public Double getRentpriceformonth() {
        return this.rentpriceformonth;
    }
    
    public void setRentpriceformonth(Double rentpriceformonth) {
        this.rentpriceformonth = rentpriceformonth;
    }

    public Double getExchangepricebusiness() {
        return this.exchangepricebusiness;
    }
    
    public void setExchangepricebusiness(Double exchangepricebusiness) {
        this.exchangepricebusiness = exchangepricebusiness;
    }

    public Double getExchangepricehouse() {
        return this.exchangepricehouse;
    }
    
    public void setExchangepricehouse(Double exchangepricehouse) {
        this.exchangepricehouse = exchangepricehouse;
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