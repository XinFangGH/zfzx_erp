package com.credit.proj.entity;
// default package



/**
 * AbstractVProjMortDroitId entity provides the base persistence definition of the VProjMortDroitId entity. @author MyEclipse Persistence Tools
 */

public class VProjMortDroit  implements java.io.Serializable {


    // Fields    

    private Integer id;//主键编号
    private Integer mortgageid;//反担保表id
    private String droitname;//权利名称
    private Double droitpercent;//享有权利比重(%)
    private Double dealdroittime;//已经经营权利时间(年)
    private Double residualdroittime;//享有权利剩余时间(年)
    private Double droitlucre;//最近一年权利净收益(万元)
    private Double modelrangeprice;//模型估值(万元)
    private String negotiabilityIdValue;//流通性值--数据字典
    private String dealStatusIdValue;//经营状况值--数据字典
    private String droitMassIdValue;//权利质量值--数据字典
    private String registerInfoIdValue;//登记情况值--数据字典
    
    private Integer negotiabilityId;//流通性id
    private Integer dealStatusId;//经营状况id
    private Integer droitMassId;//权利质量id
    private Integer registerInfoId;//登记情况id
    private String objectType;

    // Constructors

    /** default constructor */
    public VProjMortDroit() {
    }

	/** minimal constructor */
    public VProjMortDroit(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortDroit(Integer id, Integer mortgageid, String droitname, Double droitpercent, 
    		Double dealdroittime, Double residualdroittime, Double droitlucre, 
    		Double modelrangeprice, String negotiabilityIdValue, String dealStatusIdValue, 
    		String droitMassIdValue, String registerInfoIdValue,Integer negotiabilityId,
    		Integer dealStatusId,Integer droitMassId,Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.droitname = droitname;
        this.droitpercent = droitpercent;
        this.dealdroittime = dealdroittime;
        this.residualdroittime = residualdroittime;
        this.droitlucre = droitlucre;
        this.modelrangeprice = modelrangeprice;
        this.negotiabilityIdValue = negotiabilityIdValue;
        this.dealStatusIdValue = dealStatusIdValue;
        this.droitMassIdValue = droitMassIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.negotiabilityId = negotiabilityId;
        this.dealStatusId = dealStatusId;
        this.droitMassId = droitMassId;
        this.registerInfoId = registerInfoId;
    }

   
    // Property accessors

    public Integer getId() {
        return this.id;
    }
    
    public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

    public String getDroitname() {
        return this.droitname;
    }
    
    public void setDroitname(String droitname) {
        this.droitname = droitname;
    }

    public Double getDroitpercent() {
        return this.droitpercent;
    }
    
    public void setDroitpercent(Double droitpercent) {
        this.droitpercent = droitpercent;
    }

    public Double getDealdroittime() {
        return this.dealdroittime;
    }
    
    public void setDealdroittime(Double dealdroittime) {
        this.dealdroittime = dealdroittime;
    }

    public Double getResidualdroittime() {
        return this.residualdroittime;
    }
    
    public void setResidualdroittime(Double residualdroittime) {
        this.residualdroittime = residualdroittime;
    }

    public Double getDroitlucre() {
        return this.droitlucre;
    }
    
    public void setDroitlucre(Double droitlucre) {
        this.droitlucre = droitlucre;
    }

    public Double getModelrangeprice() {
        return this.modelrangeprice;
    }
    
    public void setModelrangeprice(Double modelrangeprice) {
        this.modelrangeprice = modelrangeprice;
    }

    public String getNegotiabilityIdValue() {
        return this.negotiabilityIdValue;
    }
    
    public void setNegotiabilityIdValue(String negotiabilityIdValue) {
        this.negotiabilityIdValue = negotiabilityIdValue;
    }

    public String getDealStatusIdValue() {
        return this.dealStatusIdValue;
    }
    
    public void setDealStatusIdValue(String dealStatusIdValue) {
        this.dealStatusIdValue = dealStatusIdValue;
    }

    public String getDroitMassIdValue() {
        return this.droitMassIdValue;
    }
    
    public void setDroitMassIdValue(String droitMassIdValue) {
        this.droitMassIdValue = droitMassIdValue;
    }

    public String getRegisterInfoIdValue() {
        return this.registerInfoIdValue;
    }
    
    public void setRegisterInfoIdValue(String registerInfoIdValue) {
        this.registerInfoIdValue = registerInfoIdValue;
    }

	public Integer getNegotiabilityId() {
		return negotiabilityId;
	}

	public void setNegotiabilityId(Integer negotiabilityId) {
		this.negotiabilityId = negotiabilityId;
	}

	public Integer getDealStatusId() {
		return dealStatusId;
	}

	public void setDealStatusId(Integer dealStatusId) {
		this.dealStatusId = dealStatusId;
	}

	public Integer getDroitMassId() {
		return droitMassId;
	}

	public void setDroitMassId(Integer droitMassId) {
		this.droitMassId = droitMassId;
	}

	public Integer getRegisterInfoId() {
		return registerInfoId;
	}

	public void setRegisterInfoId(Integer registerInfoId) {
		this.registerInfoId = registerInfoId;
	}
    
}