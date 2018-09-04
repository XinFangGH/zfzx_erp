package com.zhiwei.credit.model.creditFlow.customer.person;

import com.zhiwei.core.model.BaseModel;

/**
 * CsProcessPersonCarReditregistries entity. @author MyEclipse Persistence Tools
 */

public class ProcessPersonCarReditregistries extends BaseModel {

	// Fields
	private Integer id;
	private Integer mortgagorId;
	private String creditregistriesNo;
	private java.util.Date queryTime;
	private java.util.Date reportQueryTime;
	private String nowProvince;
	private String nowCity;
	private String nowDistrict;
	private String nowRoadName;
	private String nowRoadNo;
	private String nowCommunity;
	private String nowHouseNumber;
	private String nowPostcode;
	private java.util.Date nowResideTime;
	private String foreProvince;
	private String foreCity;
	private String foreDistrict;
	private String foreRoadName;
	private String foreRoadNo;
	private String foreCommunity;
	private String foreHouseNumber;
	private String forePostcode;
	private java.util.Date foreResideTime;
	private String bankAccountNum;
	private Double bankauthorizeMoney;
	private Double bankCreditBalance;
	private Integer overdrafttime30day;
	private Integer overdrafttime60day;
	private Integer overdrafttime90day;
	private Integer overdrafttime120day;
	private Integer overdrafttime150day;
	private Integer overdrafttime180day;
	private Integer overdrafttimePass180day;
	private String twoYearBeginAccount;
	private String twoYearEndAccount;
	private String twoYearOtherAccount;
	private Integer creditOverdue;
	private Integer creditMaxOverdue;
	private Integer creditAmount;
	private Integer overdueAmount;
	//added by luowenyan
	private Double currentTotalLoans;
	private Double currentDefaultLoans;
	private Double historyTotalLoans;
	private Integer twoYearHighestOverdue;
	private Integer creditCardsNum;
	private Double currentOverdraftBalance;
	private Integer currentCreditCardNum;
	private Integer oneMonthQueryMechanismNum;
	private Integer oneMonthQueryNum;
	private Integer twoYearQueryNum;
	private String socialSecurityUnit;
	
	//不与数据库映射
	private String nowProvinceValue;
	private String nowCityValue;
	private String nowDistrictValue;
	private String foreProvinceValue;
	private String foreCityVlaue;
	private String foreDistrictValue;
	
	// Constructors

	/** default constructor */
	public ProcessPersonCarReditregistries() {
	}
	/** full constructor */
	
	public Integer getId() {
		return id;
	}
	public ProcessPersonCarReditregistries(Integer id, Integer mortgagorId,
			String creditregistriesNo, java.util.Date queryTime,
			java.util.Date reportQueryTime, String nowProvince, String nowCity,
			String nowDistrict, String nowRoadName, String nowRoadNo,
			String nowCommunity, String nowHouseNumber, String nowPostcode,
			java.util.Date nowResideTime, String foreProvince, String foreCity,
			String foreDistrict, String foreRoadName, String foreRoadNo,
			String foreCommunity, String foreHouseNumber, String forePostcode,
			java.util.Date foreResideTime, String bankAccountNum,
			Double bankauthorizeMoney, Double bankCreditBalance,
			Integer overdrafttime30day, Integer overdrafttime60day,
			Integer overdrafttime90day, Integer overdrafttime120day,
			Integer overdrafttime150day, Integer overdrafttime180day,
			Integer overdrafttimePass180day, String twoYearBeginAccount,
			String twoYearEndAccount, String twoYearOtherAccount,
			Integer creditOverdue, Integer creditMaxOverdue,
			Integer creditAmount, Integer overdueAmount,Double currentTotalLoans,
			Double currentDefaultLoans,Double historyTotalLoans,Integer twoYearHighestOverdue,
			Integer creditCardsNum,Double currentOverdraftBalance,Integer currentCreditCardNum,
			Integer oneMonthQueryMechanismNum,Integer oneMonthQueryNum,Integer twoYearQueryNum,
			String socialSecurityUnit) {
		super();
		this.id = id;
		this.mortgagorId = mortgagorId;
		this.creditregistriesNo = creditregistriesNo;
		this.queryTime = queryTime;
		this.reportQueryTime = reportQueryTime;
		this.nowProvince = nowProvince;
		this.nowCity = nowCity;
		this.nowDistrict = nowDistrict;
		this.nowRoadName = nowRoadName;
		this.nowRoadNo = nowRoadNo;
		this.nowCommunity = nowCommunity;
		this.nowHouseNumber = nowHouseNumber;
		this.nowPostcode = nowPostcode;
		this.nowResideTime = nowResideTime;
		this.foreProvince = foreProvince;
		this.foreCity = foreCity;
		this.foreDistrict = foreDistrict;
		this.foreRoadName = foreRoadName;
		this.foreRoadNo = foreRoadNo;
		this.foreCommunity = foreCommunity;
		this.foreHouseNumber = foreHouseNumber;
		this.forePostcode = forePostcode;
		this.foreResideTime = foreResideTime;
		this.bankAccountNum = bankAccountNum;
		this.bankauthorizeMoney = bankauthorizeMoney;
		this.bankCreditBalance = bankCreditBalance;
		this.overdrafttime30day = overdrafttime30day;
		this.overdrafttime60day = overdrafttime60day;
		this.overdrafttime90day = overdrafttime90day;
		this.overdrafttime120day = overdrafttime120day;
		this.overdrafttime150day = overdrafttime150day;
		this.overdrafttime180day = overdrafttime180day;
		this.overdrafttimePass180day = overdrafttimePass180day;
		this.twoYearBeginAccount = twoYearBeginAccount;
		this.twoYearEndAccount = twoYearEndAccount;
		this.twoYearOtherAccount = twoYearOtherAccount;
		this.creditOverdue = creditOverdue;
		this.creditMaxOverdue = creditMaxOverdue;
		this.creditAmount = creditAmount;
		this.overdueAmount = overdueAmount;
		this.currentTotalLoans = currentTotalLoans;
		this.currentDefaultLoans= currentDefaultLoans;
		this.historyTotalLoans = historyTotalLoans;
		this.twoYearHighestOverdue = twoYearHighestOverdue;
		this.creditCardsNum = creditCardsNum;
		this.currentOverdraftBalance = currentOverdraftBalance;
		this.currentCreditCardNum = currentCreditCardNum;
		this.oneMonthQueryMechanismNum = oneMonthQueryMechanismNum;
		this.oneMonthQueryNum = oneMonthQueryNum;
		this.twoYearQueryNum = twoYearQueryNum;
		this.socialSecurityUnit = socialSecurityUnit;
	}
	
	
	public String getNowProvinceValue() {
		return nowProvinceValue;
	}
	public void setNowProvinceValue(String nowProvinceValue) {
		this.nowProvinceValue = nowProvinceValue;
	}
	public String getNowCityValue() {
		return nowCityValue;
	}
	public void setNowCityValue(String nowCityValue) {
		this.nowCityValue = nowCityValue;
	}
	public String getNowDistrictValue() {
		return nowDistrictValue;
	}
	public void setNowDistrictValue(String nowDistrictValue) {
		this.nowDistrictValue = nowDistrictValue;
	}
	public String getForeProvinceValue() {
		return foreProvinceValue;
	}
	public void setForeProvinceValue(String foreProvinceValue) {
		this.foreProvinceValue = foreProvinceValue;
	}
	public String getForeCityVlaue() {
		return foreCityVlaue;
	}
	public void setForeCityVlaue(String foreCityVlaue) {
		this.foreCityVlaue = foreCityVlaue;
	}
	public String getForeDistrictValue() {
		return foreDistrictValue;
	}
	public void setForeDistrictValue(String foreDistrictValue) {
		this.foreDistrictValue = foreDistrictValue;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMortgagorId() {
		return mortgagorId;
	}
	public void setMortgagorId(Integer mortgagorId) {
		this.mortgagorId = mortgagorId;
	}
	public String getCreditregistriesNo() {
		return creditregistriesNo;
	}
	public void setCreditregistriesNo(String creditregistriesNo) {
		this.creditregistriesNo = creditregistriesNo;
	}
	public java.util.Date getQueryTime() {
		return queryTime;
	}
	public void setQueryTime(java.util.Date queryTime) {
		this.queryTime = queryTime;
	}
	public java.util.Date getReportQueryTime() {
		return reportQueryTime;
	}
	public void setReportQueryTime(java.util.Date reportQueryTime) {
		this.reportQueryTime = reportQueryTime;
	}
	public String getNowProvince() {
		return nowProvince;
	}
	public void setNowProvince(String nowProvince) {
		this.nowProvince = nowProvince;
	}
	public String getNowCity() {
		return nowCity;
	}
	public void setNowCity(String nowCity) {
		this.nowCity = nowCity;
	}
	public String getNowDistrict() {
		return nowDistrict;
	}
	public void setNowDistrict(String nowDistrict) {
		this.nowDistrict = nowDistrict;
	}
	public String getNowRoadName() {
		return nowRoadName;
	}
	public void setNowRoadName(String nowRoadName) {
		this.nowRoadName = nowRoadName;
	}
	public String getNowRoadNo() {
		return nowRoadNo;
	}
	public void setNowRoadNo(String nowRoadNo) {
		this.nowRoadNo = nowRoadNo;
	}
	public String getNowCommunity() {
		return nowCommunity;
	}
	public void setNowCommunity(String nowCommunity) {
		this.nowCommunity = nowCommunity;
	}
	public String getNowHouseNumber() {
		return nowHouseNumber;
	}
	public void setNowHouseNumber(String nowHouseNumber) {
		this.nowHouseNumber = nowHouseNumber;
	}
	public String getNowPostcode() {
		return nowPostcode;
	}
	public void setNowPostcode(String nowPostcode) {
		this.nowPostcode = nowPostcode;
	}
	public java.util.Date getNowResideTime() {
		return nowResideTime;
	}
	public void setNowResideTime(java.util.Date nowResideTime) {
		this.nowResideTime = nowResideTime;
	}
	public String getForeProvince() {
		return foreProvince;
	}
	public void setForeProvince(String foreProvince) {
		this.foreProvince = foreProvince;
	}
	public String getForeCity() {
		return foreCity;
	}
	public String getForeRoadName() {
		return foreRoadName;
	}
	public void setForeRoadName(String foreRoadName) {
		this.foreRoadName = foreRoadName;
	}
	public String getForeRoadNo() {
		return foreRoadNo;
	}
	public void setForeRoadNo(String foreRoadNo) {
		this.foreRoadNo = foreRoadNo;
	}
	public String getForeCommunity() {
		return foreCommunity;
	}
	public void setForeCommunity(String foreCommunity) {
		this.foreCommunity = foreCommunity;
	}
	public String getForeHouseNumber() {
		return foreHouseNumber;
	}
	public void setForeHouseNumber(String foreHouseNumber) {
		this.foreHouseNumber = foreHouseNumber;
	}
	public String getForePostcode() {
		return forePostcode;
	}
	public void setForePostcode(String forePostcode) {
		this.forePostcode = forePostcode;
	}
	public java.util.Date getForeResideTime() {
		return foreResideTime;
	}
	public void setForeResideTime(java.util.Date foreResideTime) {
		this.foreResideTime = foreResideTime;
	}
	public String getBankAccountNum() {
		return bankAccountNum;
	}
	public void setBankAccountNum(String bankAccountNum) {
		this.bankAccountNum = bankAccountNum;
	}
	public Double getBankauthorizeMoney() {
		return bankauthorizeMoney;
	}
	public void setBankauthorizeMoney(Double bankauthorizeMoney) {
		this.bankauthorizeMoney = bankauthorizeMoney;
	}
	public Double getBankCreditBalance() {
		return bankCreditBalance;
	}
	public void setBankCreditBalance(Double bankCreditBalance) {
		this.bankCreditBalance = bankCreditBalance;
	}
	public Integer getOverdrafttime30day() {
		return overdrafttime30day;
	}
	public void setOverdrafttime30day(Integer overdrafttime30day) {
		this.overdrafttime30day = overdrafttime30day;
	}
	public Integer getOverdrafttime60day() {
		return overdrafttime60day;
	}
	public void setOverdrafttime60day(Integer overdrafttime60day) {
		this.overdrafttime60day = overdrafttime60day;
	}
	public Integer getOverdrafttime90day() {
		return overdrafttime90day;
	}
	public void setOverdrafttime90day(Integer overdrafttime90day) {
		this.overdrafttime90day = overdrafttime90day;
	}
	public Integer getOverdrafttime120day() {
		return overdrafttime120day;
	}
	public void setOverdrafttime120day(Integer overdrafttime120day) {
		this.overdrafttime120day = overdrafttime120day;
	}
	public Integer getOverdrafttime150day() {
		return overdrafttime150day;
	}
	public void setOverdrafttime150day(Integer overdrafttime150day) {
		this.overdrafttime150day = overdrafttime150day;
	}
	public Integer getOverdrafttime180day() {
		return overdrafttime180day;
	}
	public void setOverdrafttime180day(Integer overdrafttime180day) {
		this.overdrafttime180day = overdrafttime180day;
	}
	public Integer getOverdrafttimePass180day() {
		return overdrafttimePass180day;
	}
	public void setOverdrafttimePass180day(Integer overdrafttimePass180day) {
		this.overdrafttimePass180day = overdrafttimePass180day;
	}
	
	public String getForeDistrict() {
		return foreDistrict;
	}
	public void setForeDistrict(String foreDistrict) {
		this.foreDistrict = foreDistrict;
	}
	public String getTwoYearBeginAccount() {
		return twoYearBeginAccount;
	}
	public void setTwoYearBeginAccount(String twoYearBeginAccount) {
		this.twoYearBeginAccount = twoYearBeginAccount;
	}
	public String getTwoYearEndAccount() {
		return twoYearEndAccount;
	}
	public void setTwoYearEndAccount(String twoYearEndAccount) {
		this.twoYearEndAccount = twoYearEndAccount;
	}
	public String getTwoYearOtherAccount() {
		return twoYearOtherAccount;
	}
	public void setTwoYearOtherAccount(String twoYearOtherAccount) {
		this.twoYearOtherAccount = twoYearOtherAccount;
	}
	public void setForeCity(String foreCity) {
		this.foreCity = foreCity;
	}
	public Integer getCreditOverdue() {
		return creditOverdue;
	}
	public void setCreditOverdue(Integer creditOverdue) {
		this.creditOverdue = creditOverdue;
	}
	public Integer getCreditMaxOverdue() {
		return creditMaxOverdue;
	}
	public void setCreditMaxOverdue(Integer creditMaxOverdue) {
		this.creditMaxOverdue = creditMaxOverdue;
	}
	public Integer getCreditAmount() {
		return creditAmount;
	}
	public void setCreditAmount(Integer creditAmount) {
		this.creditAmount = creditAmount;
	}
	public Integer getOverdueAmount() {
		return overdueAmount;
	}
	public void setOverdueAmount(Integer overdueAmount) {
		this.overdueAmount = overdueAmount;
	}
	public Double getCurrentTotalLoans() {
		return currentTotalLoans;
	}
	public void setCurrentTotalLoans(Double currentTotalLoans) {
		this.currentTotalLoans = currentTotalLoans;
	}
	public Double getCurrentDefaultLoans() {
		return currentDefaultLoans;
	}
	public void setCurrentDefaultLoans(Double currentDefaultLoans) {
		this.currentDefaultLoans = currentDefaultLoans;
	}
	public Double getHistoryTotalLoans() {
		return historyTotalLoans;
	}
	public void setHistoryTotalLoans(Double historyTotalLoans) {
		this.historyTotalLoans = historyTotalLoans;
	}
	public Integer getTwoYearHighestOverdue() {
		return twoYearHighestOverdue;
	}
	public void setTwoYearHighestOverdue(Integer twoYearHighestOverdue) {
		this.twoYearHighestOverdue = twoYearHighestOverdue;
	}
	public Integer getCreditCardsNum() {
		return creditCardsNum;
	}
	public void setCreditCardsNum(Integer creditCardsNum) {
		this.creditCardsNum = creditCardsNum;
	}
	public Double getCurrentOverdraftBalance() {
		return currentOverdraftBalance;
	}
	public void setCurrentOverdraftBalance(Double currentOverdraftBalance) {
		this.currentOverdraftBalance = currentOverdraftBalance;
	}
	public Integer getCurrentCreditCardNum() {
		return currentCreditCardNum;
	}
	public void setCurrentCreditCardNum(Integer currentCreditCardNum) {
		this.currentCreditCardNum = currentCreditCardNum;
	}
	public Integer getOneMonthQueryMechanismNum() {
		return oneMonthQueryMechanismNum;
	}
	public void setOneMonthQueryMechanismNum(Integer oneMonthQueryMechanismNum) {
		this.oneMonthQueryMechanismNum = oneMonthQueryMechanismNum;
	}
	public Integer getOneMonthQueryNum() {
		return oneMonthQueryNum;
	}
	public void setOneMonthQueryNum(Integer oneMonthQueryNum) {
		this.oneMonthQueryNum = oneMonthQueryNum;
	}
	public Integer getTwoYearQueryNum() {
		return twoYearQueryNum;
	}
	public void setTwoYearQueryNum(Integer twoYearQueryNum) {
		this.twoYearQueryNum = twoYearQueryNum;
	}
	public String getSocialSecurityUnit() {
		return socialSecurityUnit;
	}
	public void setSocialSecurityUnit(String socialSecurityUnit) {
		this.socialSecurityUnit = socialSecurityUnit;
	}
	
	// Property accessors
	
}