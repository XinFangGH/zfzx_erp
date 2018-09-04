package com.credit.proj.entity;
					  
public class VProjMortDutyPerson  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String name;//姓名
     private String idcard;//身份证号码
     private String phone;//联系电话
     private Double assetvalue;//资产价值(万元)
     private Double modelrangeprice;//模型估价
     private String societyNexusIdValue;//社会关系值--数据字典
     private Integer societyNexusId;//社会关系id
     private String cardtypevalue;//证件类型-数据字典值
     private Integer cardtype;  //证件类型
     private Boolean isCivilServant;//是否为公务员
     private String business;
     private String assets;//主要资产
     private String monthlyIncome;//月收入
     private String zhusuo;//住所
     
    /** default constructor */
    public VProjMortDutyPerson() {
    }

	/** minimal constructor */
    public VProjMortDutyPerson(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortDutyPerson(Integer id, Integer mortgageid, String name, String idcard, 
    		String phone, Double assetvalue, Double modelrangeprice, String societyNexusIdValue,
    		Integer societyNexusId,String cardtypevalue,Integer cardtype,Boolean isCivilServant,
    		String business,String assets,String monthlyIncome,String zhusuo) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.name = name;
        this.idcard = idcard;
        this.phone = phone;
        this.assetvalue = assetvalue;
        this.modelrangeprice = modelrangeprice;
        this.societyNexusIdValue = societyNexusIdValue;
        this.societyNexusId = societyNexusId;
        this.cardtypevalue = cardtypevalue;
        this.cardtype = cardtype;
        this.isCivilServant=isCivilServant;
        this.business=business;
        this.assets=assets;
        this.monthlyIncome=monthlyIncome;
        this.zhusuo =zhusuo;
    }

   
    public String getZhusuo() {
		return zhusuo;
	}

	public void setZhusuo(String zhusuo) {
		this.zhusuo = zhusuo;
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

    public String getIdcard() {
        return this.idcard;
    }
    
    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getPhone() {
        return this.phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Double getAssetvalue() {
        return this.assetvalue;
    }
    
    public void setAssetvalue(Double assetvalue) {
        this.assetvalue = assetvalue;
    }

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
    }

    public String getSocietyNexusIdValue() {
        return this.societyNexusIdValue;
    }
    
    public void setSocietyNexusIdValue(String societyNexusIdValue) {
        this.societyNexusIdValue = societyNexusIdValue;
    }

	public Integer getSocietyNexusId() {
		return societyNexusId;
	}

	public void setSocietyNexusId(Integer societyNexusId) {
		this.societyNexusId = societyNexusId;
	}

	public String getCardtypevalue() {
		return cardtypevalue;
	}

	public void setCardtypevalue(String cardtypevalue) {
		this.cardtypevalue = cardtypevalue;
	}

	public Integer getCardtype() {
		return cardtype;
	}

	public void setCardtype(Integer cardtype) {
		this.cardtype = cardtype;
	}

	public Boolean getIsCivilServant() {
		return isCivilServant;
	}

	public void setIsCivilServant(Boolean isCivilServant) {
		this.isCivilServant = isCivilServant;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getAssets() {
		return assets;
	}

	public void setAssets(String assets) {
		this.assets = assets;
	}

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}
    
}