package com.credit.proj.entity;

public class VProjMortProduct  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String name;//商品名称
     private String controller;//控制权人
     private String brand;//品牌
     private String type;//型号
     private String depositary;//存放地点
     private Integer amount;//数量
     private Double priceone;//市场单价1
     private Double pricetow;//市场单价2
     private Double modelrangeprice;//模型估价
     private String controllerTypeIdValue;//控制权方式值--数据字典
     private String commonGradeIdValue;//通用程度值--数据字典
     private String cashabilityIdValue;//变现能力值--数据字典
     private Integer controllerTypeId;//控制权方式id
     private Integer commonGradeId;//通用程度id
     private Integer cashabilityId;//变现能力id
     private String objectType;
    /** default constructor */
    public VProjMortProduct() {
    }

	/** minimal constructor */
    public VProjMortProduct(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortProduct(Integer id, Integer mortgageid, String name, String controller, 
    		String brand, String type, String depositary, Integer amount, Double priceone, 
    		Double pricetow, Double modelrangeprice, String controllerTypeIdValue, 
    		String commonGradeIdValue, String cashabilityIdValue,Integer controllerTypeId,
    		Integer commonGradeId,Integer cashabilityId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.name = name;
        this.controller = controller;
        this.brand = brand;
        this.type = type;
        this.depositary = depositary;
        this.amount = amount;
        this.priceone = priceone;
        this.pricetow = pricetow;
        this.modelrangeprice = modelrangeprice;
        this.controllerTypeIdValue = controllerTypeIdValue;
        this.commonGradeIdValue = commonGradeIdValue;
        this.cashabilityIdValue = cashabilityIdValue;
        this.controllerTypeId = controllerTypeId;
        this.commonGradeId = commonGradeId;
        this.cashabilityId = cashabilityId;
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

    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public String getController() {
        return this.controller;
    }
    
    public void setController(String controller) {
        this.controller = controller;
    }

    public String getBrand() {
        return this.brand;
    }
    
    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    public String getDepositary() {
        return this.depositary;
    }
    
    public void setDepositary(String depositary) {
        this.depositary = depositary;
    }

    public Integer getAmount() {
        return this.amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPriceone() {
        return this.priceone;
    }
    
    public void setPriceone(Double priceone) {
        this.priceone = priceone;
    }

    public Double getPricetow() {
        return this.pricetow;
    }
    
    public void setPricetow(Double pricetow) {
        this.pricetow = pricetow;
    }

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
    }

    public String getControllerTypeIdValue() {
        return this.controllerTypeIdValue;
    }
    
    public void setControllerTypeIdValue(String controllerTypeIdValue) {
        this.controllerTypeIdValue = controllerTypeIdValue;
    }

    public String getCommonGradeIdValue() {
        return this.commonGradeIdValue;
    }
    
    public void setCommonGradeIdValue(String commonGradeIdValue) {
        this.commonGradeIdValue = commonGradeIdValue;
    }

    public String getCashabilityIdValue() {
        return this.cashabilityIdValue;
    }
    
    public void setCashabilityIdValue(String cashabilityIdValue) {
        this.cashabilityIdValue = cashabilityIdValue;
    }

	public Integer getControllerTypeId() {
		return controllerTypeId;
	}

	public void setControllerTypeId(Integer controllerTypeId) {
		this.controllerTypeId = controllerTypeId;
	}

	public Integer getCommonGradeId() {
		return commonGradeId;
	}

	public void setCommonGradeId(Integer commonGradeId) {
		this.commonGradeId = commonGradeId;
	}

	public Integer getCashabilityId() {
		return cashabilityId;
	}

	public void setCashabilityId(Integer cashabilityId) {
		this.cashabilityId = cashabilityId;
	}
    
    
}