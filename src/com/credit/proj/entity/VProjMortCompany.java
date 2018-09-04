package com.credit.proj.entity;

public class VProjMortCompany  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     //private String enterprisename;//公司名称
     private String licensenumber;//营业执照号码
     private String registeraddress;//注册地址
     private String corporate;//法人代表
     private String corporatetel;//法人代表电话
     private Double netassets;//净资产(万元)
     private Double modelrangeprice;//模型估价
     private String societyNexusIdValue;//社会关系值--数据字典
     private Integer societyNexusId;//社会关系id
     private String telephone;//公司的联系电话
     private String business;//主营业务
     private String assets;//主要业务
     private String monthlyIncome;//月营业额
   //企业表中法人代表id，用于修改时候显示的值，否则不选择客户名称的话则此值为空，修改错误
     private Integer legalpersonid;

    /** default constructor */
    public VProjMortCompany() {
    }

	/** minimal constructor */
    public VProjMortCompany(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortCompany(Integer id, Integer mortgageid,
    		String licensenumber, String registeraddress, String corporate, 
    		String corporatetel, Double netassets, Double modelrangeprice, 
    		String societyNexusIdValue,Integer societyNexusId,Integer legalpersonid,
    		String telephone,String business,String assets,String monthlyIncome) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.licensenumber = licensenumber;
        this.registeraddress = registeraddress;
        this.corporate = corporate;
        this.corporatetel = corporatetel;
        this.netassets = netassets;
        this.modelrangeprice = modelrangeprice;
        this.societyNexusIdValue = societyNexusIdValue;
        this.societyNexusId = societyNexusId;
        this.legalpersonid = legalpersonid;
        this.telephone=telephone;
        this.business=business;
        this.assets=assets;
        this.monthlyIncome=monthlyIncome;
    }

   
    // Property accessors

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

    public String getLicensenumber() {
        return this.licensenumber;
    }
    
    public void setLicensenumber(String licensenumber) {
        this.licensenumber = licensenumber;
    }

    public String getRegisteraddress() {
        return this.registeraddress;
    }
    
    public void setRegisteraddress(String registeraddress) {
        this.registeraddress = registeraddress;
    }

    public String getCorporate() {
        return this.corporate;
    }
    
    public void setCorporate(String corporate) {
        this.corporate = corporate;
    }

    public String getCorporatetel() {
        return this.corporatetel;
    }
    
    public void setCorporatetel(String corporatetel) {
        this.corporatetel = corporatetel;
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

	public Integer getLegalpersonid() {
		return legalpersonid;
	}

	public void setLegalpersonid(Integer legalpersonid) {
		this.legalpersonid = legalpersonid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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