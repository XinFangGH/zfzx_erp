package com.credit.proj.entity;


public class VProjMortMachineInfo  implements java.io.Serializable {

     private Integer id;//编号
     private Integer mortgageid;//反担保表id
     private String machinename;//设备名称
     private String controller;//控制权人
     private String machinetype;//设备型号
     private Double newcarprice;//新货价格(万元)
     private java.util.Date leavefactorydate;//出厂日期
     private Double havedusedtime;//使用时间(年)
     private Double secondaryvalueone;//二手价值1
     private Double secondaryvaluetow;//二手价值2
     private Double modelrangeprice;//模型估价
     private String controllerTypeIdValue;//控制权方式值--数据字典
     private String commonGradeIdValue;//通用程度值--数据字典
     private String capabilityStatusIdValue;//性能状况值--数据字典
     private String cashabilityIdValue;//变现能力值--数据字典
     private String registerInfoIdValue;//登记情况值--数据字典
     
     private Integer controllerTypeId;//控制权方式
     private Integer commonGradeId;//通用程度
     private Integer capabilityStatusId;//性能状况
     private Integer cashabilityId;//变现能力
     private Integer registerInfoId;//登记情况
     private String objectType;
    /** default constructor */
    public VProjMortMachineInfo() {
    }

	/** minimal constructor */
    public VProjMortMachineInfo(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortMachineInfo(Integer id, Integer mortgageid, String machinename, 
    		String controller, String machinetype, Double newcarprice, 
    		java.util.Date leavefactorydate, Double havedusedtime, Double secondaryvalueone, 
    		Double secondaryvaluetow, Double modelrangeprice, String controllerTypeIdValue, 
    		String commonGradeIdValue, String capabilityStatusIdValue, String cashabilityIdValue, 
    		String registerInfoIdValue,Integer controllerTypeId,Integer commonGradeId,
    		Integer capabilityStatusId,Integer cashabilityId,Integer registerInfoId) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.machinename = machinename;
        this.controller = controller;
        this.machinetype = machinetype;
        this.newcarprice = newcarprice;
        this.leavefactorydate = leavefactorydate;
        this.havedusedtime = havedusedtime;
        this.secondaryvalueone = secondaryvalueone;
        this.secondaryvaluetow = secondaryvaluetow;
        this.modelrangeprice = modelrangeprice;
        this.controllerTypeIdValue = controllerTypeIdValue;
        this.commonGradeIdValue = commonGradeIdValue;
        this.capabilityStatusIdValue = capabilityStatusIdValue;
        this.cashabilityIdValue = cashabilityIdValue;
        this.registerInfoIdValue = registerInfoIdValue;
        this.controllerTypeId = controllerTypeId;
        this.commonGradeId = commonGradeId;
        this.capabilityStatusId = capabilityStatusId;
        this.cashabilityId = cashabilityId;
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

    public String getMachinename() {
        return this.machinename;
    }
    
    public void setMachinename(String machinename) {
        this.machinename = machinename;
    }

    public String getController() {
        return this.controller;
    }
    
    public void setController(String controller) {
        this.controller = controller;
    }

    public String getMachinetype() {
        return this.machinetype;
    }
    
    public void setMachinetype(String machinetype) {
        this.machinetype = machinetype;
    }

    public Double getNewcarprice() {
        return this.newcarprice;
    }
    
    public void setNewcarprice(Double newcarprice) {
        this.newcarprice = newcarprice;
    }


    public java.util.Date getLeavefactorydate() {
		return leavefactorydate;
	}

	public void setLeavefactorydate(java.util.Date leavefactorydate) {
		this.leavefactorydate = leavefactorydate;
	}

	public Double getHavedusedtime() {
        return this.havedusedtime;
    }
    
    public void setHavedusedtime(Double havedusedtime) {
        this.havedusedtime = havedusedtime;
    }

    public Double getSecondaryvalueone() {
        return this.secondaryvalueone;
    }
    
    public void setSecondaryvalueone(Double secondaryvalueone) {
        this.secondaryvalueone = secondaryvalueone;
    }

    public Double getSecondaryvaluetow() {
        return this.secondaryvaluetow;
    }
    
    public void setSecondaryvaluetow(Double secondaryvaluetow) {
        this.secondaryvaluetow = secondaryvaluetow;
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

    public String getCapabilityStatusIdValue() {
        return this.capabilityStatusIdValue;
    }
    
    public void setCapabilityStatusIdValue(String capabilityStatusIdValue) {
        this.capabilityStatusIdValue = capabilityStatusIdValue;
    }

    public String getCashabilityIdValue() {
        return this.cashabilityIdValue;
    }
    
    public void setCashabilityIdValue(String cashabilityIdValue) {
        this.cashabilityIdValue = cashabilityIdValue;
    }

    public String getRegisterInfoIdValue() {
        return this.registerInfoIdValue;
    }
    
    public void setRegisterInfoIdValue(String registerInfoIdValue) {
        this.registerInfoIdValue = registerInfoIdValue;
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

	public Integer getCapabilityStatusId() {
		return capabilityStatusId;
	}

	public void setCapabilityStatusId(Integer capabilityStatusId) {
		this.capabilityStatusId = capabilityStatusId;
	}

	public Integer getCashabilityId() {
		return cashabilityId;
	}

	public void setCashabilityId(Integer cashabilityId) {
		this.cashabilityId = cashabilityId;
	}

	public Integer getRegisterInfoId() {
		return registerInfoId;
	}

	public void setRegisterInfoId(Integer registerInfoId) {
		this.registerInfoId = registerInfoId;
	}
    
}