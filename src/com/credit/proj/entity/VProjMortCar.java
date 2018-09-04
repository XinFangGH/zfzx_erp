package com.credit.proj.entity;

// default package



/**
 * AbstractVProjMortCarId entity provides the base persistence definition of the VProjMortCarId entity. @author MyEclipse Persistence Tools
 */

public class VProjMortCar  implements java.io.Serializable {


    // Fields    

     private Integer id;//车辆表编号
     private Integer mortgageid;//反担保表id
     private Integer manufacturer;//制造商id--cs_process_car表的id----v_car_dic视图的id
     //private String controller;//控制权人
     //private String carenregisternumber;//机动车辆登记号
     private java.util.Date leavefactorydate;//出厂日期
     //private String cartype;//型号
    // private Double enginecapacity;//排量
     private Double carprice;//新车价格
     private Double haveusedtime;//使用时间
     private Double totalkilometres;//公里数
     private Integer accidenttimes;//事故次数
     private Double exchangepriceone;//市场交易价格1
     private Double exchangepricetow;//市场交易价格2
     private Double modelrangeprice;//模型估价
     private Integer carinfoid;//车况
     private Integer enregisterinfoid;//登记情况
     //private Integer controlmodeid;//控制权方式
     //private String controlModeIdValue;//控制权方式值
     private String enregisterInfoIdValue;//登记情况值
     private String carInfoIdValue;//车况值
     
    // private String manufacturerValue;//制造商多级数据字段值
     
     //2011.01.18新增字段
     private String engineNo;//发动机号
 	 private String vin;//车架号
 	 private Integer carColor;//车辆颜色id
 	// private String carColorValue;//车辆颜色数据字典对应值
 	 
 	 //从车辆制造商视图视图查询值set到此处
 /*	 private String carNumber;//车型编号
	 private String carStyleName;//车系
	 private String carModelName;//车型
	 private String displacementValue;//排量
	 private String configuration;//配置
	 private String seatingValue;//座位数
*/	 private String carNumber;//车型编号
		private String carStyle;//车系
		private String carModel;//车型
		private Integer displacement;//排量
		private String configuration;//配置
		private Integer seating;//座位数
		private String carProduce;//车辆产地
		private String carManufacturer;//制造商
		 private String displacementValue;//排量
		 private String seatingValue;
		 private String objectType;
    // Constructors

    /** default constructor */
    public VProjMortCar() {
    }

	/** minimal constructor */
    public VProjMortCar(Integer id) {
        this.id = id;
    }
    
    /** full constructor */
    public VProjMortCar(Integer id, Integer mortgageid, Integer manufacturer,
    		java.util.Date leavefactorydate,
    		Double carprice, Double haveusedtime, Double totalkilometres, 
    		Integer accidenttimes, Double exchangepriceone, Double exchangepricetow, 
    		Double modelrangeprice, Integer carinfoid,Integer enregisterinfoid,
    		String enregisterInfoIdValue, String carInfoIdValue,String manufacturerValue,
    		String engineNo,String vin,Integer carColor,String carColorValue,String carNumber,
    		String carStyleName,String carModelName,String displacementValue,String configuration,
    		String seatingValue) {
        this.id = id;
        this.mortgageid = mortgageid;
        this.manufacturer = manufacturer;
        this.leavefactorydate = leavefactorydate;
        this.carprice = carprice;
        this.haveusedtime = haveusedtime;
        this.totalkilometres = totalkilometres;
        this.accidenttimes = accidenttimes;
        this.exchangepriceone = exchangepriceone;
        this.exchangepricetow = exchangepricetow;
        this.modelrangeprice = modelrangeprice;
        this.carinfoid = carinfoid;
        this.enregisterinfoid = enregisterinfoid;
        this.enregisterInfoIdValue = enregisterInfoIdValue;
        this.carInfoIdValue = carInfoIdValue;
        //this.manufacturerValue = manufacturerValue;
        this.engineNo = engineNo;
        this.vin = vin;
        this.carColor = carColor;
       // this.carColorValue = carColorValue;
        this.carNumber = carNumber;
       /* this.carStyleName = carStyleName;
        this.carModelName = carModelName;
        this.displacementValue = displacementValue;*/
        this.configuration = configuration;
        //this.seatingValue = seatingValue;
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


    public Integer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

    public java.util.Date getLeavefactorydate() {
		return leavefactorydate;
	}

	public void setLeavefactorydate(java.util.Date leavefactorydate) {
		this.leavefactorydate = leavefactorydate;
	}

	public Double getCarprice() {
        return this.carprice;
    }
    
    public void setCarprice(Double carprice) {
        this.carprice = carprice;
    }


    public Double getHaveusedtime() {
		return haveusedtime;
	}

	public void setHaveusedtime(Double haveusedtime) {
		this.haveusedtime = haveusedtime;
	}

	public Double getTotalkilometres() {
		return totalkilometres;
	}

	public void setTotalkilometres(Double totalkilometres) {
		this.totalkilometres = totalkilometres;
	}

	public Integer getAccidenttimes() {
		return accidenttimes;
	}

	public void setAccidenttimes(Integer accidenttimes) {
		this.accidenttimes = accidenttimes;
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
    
    public Integer getCarinfoid() {
		return carinfoid;
	}

	public void setCarinfoid(Integer carinfoid) {
		this.carinfoid = carinfoid;
	}

	public Integer getEnregisterinfoid() {
		return enregisterinfoid;
	}

	public void setEnregisterinfoid(Integer enregisterinfoid) {
		this.enregisterinfoid = enregisterinfoid;
	}

    public String getEnregisterInfoIdValue() {
        return this.enregisterInfoIdValue;
    }
    
    public void setEnregisterInfoIdValue(String enregisterInfoIdValue) {
        this.enregisterInfoIdValue = enregisterInfoIdValue;
    }

    public String getCarInfoIdValue() {
        return this.carInfoIdValue;
    }
    
    public void setCarInfoIdValue(String carInfoIdValue) {
        this.carInfoIdValue = carInfoIdValue;
    }

/*	public String getManufacturerValue() {
		return manufacturerValue;
	}

	public void setManufacturerValue(String manufacturerValue) {
		this.manufacturerValue = manufacturerValue;
	}*/

	public String getEngineNo() {
		return engineNo;
	}

	public void setEngineNo(String engineNo) {
		this.engineNo = engineNo;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getCarColor() {
		return carColor;
	}

	public void setCarColor(Integer carColor) {
		this.carColor = carColor;
	}

	/*public String getCarColorValue() {
		return carColorValue;
	}

	public void setCarColorValue(String carColorValue) {
		this.carColorValue = carColorValue;
	}*/

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

/*	public String getCarStyleName() {
		return carStyleName;
	}

	public void setCarStyleName(String carStyleName) {
		this.carStyleName = carStyleName;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}
*/
	public String getDisplacementValue() {
		return displacementValue;
	}

	public void setDisplacementValue(String displacementValue) {
		this.displacementValue = displacementValue;
	}

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}

	public Integer getSeating() {
		return seating;
	}

	public void setSeating(Integer seating) {
		this.seating = seating;
	}

	public String getCarProduce() {
		return carProduce;
	}

	public void setCarProduce(String carProduce) {
		this.carProduce = carProduce;
	}

	public String getCarManufacturer() {
		return carManufacturer;
	}

	public void setCarManufacturer(String carManufacturer) {
		this.carManufacturer = carManufacturer;
	}

	public String getSeatingValue() {
		return seatingValue;
	}

	public void setSeatingValue(String seatingValue) {
		this.seatingValue = seatingValue;
	}
    
}