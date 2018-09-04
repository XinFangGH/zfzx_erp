package com.credit.proj.entity;


/**
 * CsProcreditMortgageCar entity. @author MyEclipse Persistence Tools
 */

public class ProcreditMortgageCar implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer mortgageid;
	private Integer manufacturer;
	//private String controller;
	//private Integer controlmodeid;
	

	private Integer enregisterinfoid;
	//private String carenregisternumber;
	private java.util.Date leavefactorydate;
	//private String cartype;
	//private Double enginecapacity;
	private Double carprice;
	private Double haveusedtime;
	private Double totalkilometres;
	private Integer accidenttimes;
	//private String statusvalue;
	private Integer carinfoid;
	private Double exchangepriceone;
	private Double exchangepricetow;
	private Double modelrangeprice;
	//private Double finalprice;
	
	//2011.01.18新添加字段
	private String carNumber;//车型编号
	private String carStyle;//车系
	private String carModel;//车型
	private Integer displacement;//排量
	private String configuration;//配置
	private Integer seating;//座位数
	private String carProduce;//车辆产地
	private String carManufacturer;//制造商
	
	private String engineNo;//发动机号
	private String vin;//车架号
	private Integer carColor;//车辆颜色
	private String objectType;//类型  典当物品:pawn,抵质押物:mortgage
	// Constructors

	/** default constructor */
	public ProcreditMortgageCar() {
	}

	/** full constructor */
	public ProcreditMortgageCar(Integer mortgageid, Integer manufacturer,
			Integer enregisterinfoid,java.util.Date leavefactorydate,Double carprice,
			Double haveusedtime, Double totalkilometres, Integer accidenttimes,
			Integer carinfoid, Double exchangepriceone,
			Double exchangepricetow, Double modelrangeprice,
			/*String carNumber,Integer carStyle,
			Integer carModel,Integer displacement,String configuration,Integer seating,*/
			String engineNo,String vin,Integer carColor) {
		this.mortgageid = mortgageid;
		this.manufacturer = manufacturer;
		this.enregisterinfoid = enregisterinfoid;
		this.leavefactorydate = leavefactorydate;
		this.carprice = carprice;
		this.haveusedtime = haveusedtime;
		this.totalkilometres = totalkilometres;
		this.accidenttimes = accidenttimes;
		//this.statusvalue = statusvalue;
		this.carinfoid = carinfoid;
		this.exchangepriceone = exchangepriceone;
		this.exchangepricetow = exchangepricetow;
		this.modelrangeprice = modelrangeprice;
		//this.finalprice = finalprice;
		/*this.carNumber = carNumber;
		this.carStyle = carStyle;
		this.carModel = carModel;
		this.displacement = displacement;
		this.configuration = configuration;
		this.seating = seating;*/
		this.engineNo = engineNo;
		this.vin = vin;
		this.carColor = carColor;
	}

	
	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getCarStyle() {
		return carStyle;
	}

	public void setCarStyle(String carStyle) {
		this.carStyle = carStyle;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
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

	public String getConfiguration() {
		return configuration;
	}

	public void setConfiguration(String configuration) {
		this.configuration = configuration;
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


	public Integer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Integer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getEnregisterinfoid() {
		return this.enregisterinfoid;
	}

	public void setEnregisterinfoid(Integer enregisterinfoid) {
		this.enregisterinfoid = enregisterinfoid;
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

	public Integer getCarinfoid() {
		return this.carinfoid;
	}

	public void setCarinfoid(Integer carinfoid) {
		this.carinfoid = carinfoid;
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

}