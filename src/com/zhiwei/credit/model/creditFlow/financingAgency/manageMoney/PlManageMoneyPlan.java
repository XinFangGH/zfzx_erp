package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * PlManageMoneyPlan Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 理财计划/产品库
 */
public class PlManageMoneyPlan extends com.zhiwei.core.model.BaseModel {
	/**
	 * 未发标
	 */
	public static final int STATE0=0;
	/**
	 * 招标中
	 */
	public static final int STATE1=1;
	/**
	 * 已齐标
	 */
	public static final int STATE2=2;
	/**
	 * 已过期
	 */
	public static final int STATE_4=4;
	/**
	 * 已流标   
	 */
	public static final int STATE3=-2;
	/**
	 * 关闭状态
	 */
	public static final int STATE4=-1; 
	/**
	 * 已起息
	 */
	public static final int STATE7=7;
	
	/**
	 * 完成
	 */
	public static final int STATE10=10;
	
	/**
	 * 主键
	 */
	@Expose
    protected Long mmplanId;
	/**
	 * 理财产品名称
	 */
	@Expose
	protected String mmName;
	/**
	 * 理财产品编号
	 */
	@Expose
	protected String mmNumber;
	/**
	 * 理财产品类型Id
	 */
	@Expose
	protected Long manageMoneyTypeId;
	/**
	 * 类型
	 * mmplan：D计划，mmproduce：理财产品，experience：体验标，Uplan：U计划
	 */
	@Expose
	protected String keystr;
	/**
	 * 投资范围
	 */
	@Expose
	protected String investScope;
	/**
	 * 收益方式
	 */
	@Expose
	protected String benefitWay;
	/**
	 * 购买开放时间
	 */
	@Expose
	protected java.util.Date buyStartTime;
	/**
	 * 购买结束时间
	 */
	@Expose
	protected java.util.Date buyEndTime;
	/**
	 * 投资起点金额
	 */
	@Expose
	protected java.math.BigDecimal startMoney;
	/**
	 * 递增金额
	 */
	@Expose
	protected java.math.BigDecimal riseMoney;
	/**
	 * 单笔投资上限
	 */
	@Expose
	protected java.math.BigDecimal limitMoney;
	/**
	 * 起息模式
	 * 1:投标日起息，2:投标日+1起息，3:满标日起息，4:招标截止日起息
	 */
	@Expose
	protected String startinInterestCondition;
	/**
	 * 到期赎回方式
	 */
	@Expose
	protected String expireRedemptionWay;
	/**
	 * 费用收取方式
	 */
	@Expose
	protected String chargeGetway;
	/**
	 * 保障方式
	 */
	@Expose
	protected String guaranteeWay;
	/**
	 * 年化收益率
	 */
	@Expose
	protected java.math.BigDecimal yeaRate; 
	/**
	 * 最大年化收益率
	 */
	protected java.math.BigDecimal maxYearRate; 
	/**
	 * 投资期限
	 */
	@Expose
	protected Integer investlimit;
	/**
	 * 招标金额
	 */
	@Expose
	protected java.math.BigDecimal sumMoney;
	/**
	 * 状态
	 * 0：未发标，1：招标中，2：已满标，-1：关闭，-2:已流标,7：已起息，10：完成
	 */
	@Expose
	protected Integer state;
	/**
	 * 是否循环出借         
	 * 此字段已废弃
	 */
	@Expose
	protected Integer isCyclingLend;
	/**
	 * 派息类型
	 * 1：一次性，0：非一次性
	 */
	protected Integer isOne;
	/**
	 * 风险保证金比例
	 * 此字段已废弃
	 */
	protected java.math.BigDecimal riskProtectionRate;
	/**
	 * 计息起日
	 */
	@Expose
	protected java.util.Date startinInterestTime;
	/**
	 * 计息止日
	 */
	@Expose
	protected java.util.Date endinInterestTime;
	/**
	 * 是否按固定日还款
	 * 1：是，2，否
	 */
	protected String isStartDatePay; 
	/**
	 * 每期还款日
	 */
	protected Integer payintentPerioDate; 
	/**
	 * 付息周期
	 */
	protected String payaccrualType;  
	/**
	 * 已购买总金额
	 */
	@Expose
	protected java.math.BigDecimal investedMoney;
	/**
	 * 产品说明
	 */
	@Expose
	protected String bidRemark;
	/**
	 * 此字段为废弃字段
	 */
	@Expose
	protected String htmlPath;
	/**
	 * 创建时间
	 */
	@Expose
	protected java.util.Date createtime;
	/**
	 * 修改时间
	 */
	protected java.util.Date updatetime;
	/**
	 * 
	 */
	protected java.util.Set plManageMoneyPlanBuyinfos = new java.util.HashSet();
	
	
	/**
	 * 锁定期限
	 */
	protected Integer lockingLimit; 
	/**
	 * 锁定截至日期
	 */
	protected java.util.Date lockingEndDate;
	/**
	 * 收款用户
	 */
	protected String moneyReceiver; 
	/**
	 * 提前退出费率
	 */
	protected java.math.BigDecimal earlierOutRate; 
	/**
	 * 授权平台无密协助还款流水号
	 */
	protected String requestNo;
	/**
	 * 授权平台无密协助还款状态
	 */
	protected Short authorityStatus;
	/**
	 * 体验标开放投标时间
	 */
	protected Integer plmmStartTime;
	/**
	 * 体验标月化利率
	 */
	protected java.math.BigDecimal  monthRate;
	/**
	 * 体验标日化利率
	 */
	protected java.math.BigDecimal  dayRate;
	/**
	 * 加息利率
	 */
	 @Expose
	 protected BigDecimal addRate;
	 /**
	  * 面值折现比
	  */
	 @Expose
	 protected BigDecimal returnRatio;
	 /**
	  * 是否可用优惠券
	  *  1：是，0：否
	  */
	 @Expose
	 protected Integer coupon;
	 /**
	  * 是否新手专享
	  * 1：是，0：否
	  */
	 @Expose
	 protected Integer novice;
	 /**
	  * 募集期利率
	  */
	 @Expose
	 protected BigDecimal raiseRate;
	 /**
      * 单笔最大面值
	  */
	 @Expose
	 protected BigDecimal maxCouponMoney;
	 /**
	  * 返利类型
	  * 1：返现 ，2：返息，3：返息现，4：加息，0：普通加息
	  */
	 @Expose
	 protected Integer rebateType;
	 /**
	  * 返利方式
	  * 1：立返 ，2：随期，3：到期
	  */
	 @Expose
	 protected Integer rebateWay;
	 /**
	  * 预售开放时间
	  */
	 protected java.util.Date preSaleTime;
	/**
	 * 加入费率
	 */
	 protected java.math.BigDecimal joinRate;
	 /**
	  * 收款类型
	  * zc：注册用户收款
	  * pt：平台账户收款
	  */
	 protected String receiverType;
	 /**
		 * 联动优势建立标的状态:
		 * -1表示建立标了
		 * 0表示开标了
		 * 1表示投标中
		 * 2还款中
		 * 3已还款
		 * 4结束
		 */
	 protected Short umPayBidStatus;//联动优势建立标的状态
	 
	/**
	  * 剩余债权金额
	  */
	 protected BigDecimal balanceMoney;
	 
	
	 //不与数据库映射
	 protected java.util.Date intentDate;
	 protected java.math.BigDecimal  sumIncomeMoney;
	 protected java.util.Date factDate;
	 @Expose
	 protected java.math.BigDecimal afterMoney;
	 @Expose
	 protected int persionNum;
	 @Expose
	 protected double progress;
	 @Expose
	 protected String manageMoneyTypeName;//理财类别
	/**
	 * 授权方式 1是打开页面。0是直接授权
	 */
	 protected String isPage;
	
	
	public String getIsPage() {
		return isPage;
	}
	public void setIsPage(String isPage) {
		this.isPage = isPage;
	}
	public Short getUmPayBidStatus() {
		return umPayBidStatus;
	}
	public void setUmPayBidStatus(Short umPayBidStatus) {
		this.umPayBidStatus = umPayBidStatus;
	}
	public BigDecimal getAddRate() {
		return addRate;
	}
	public void setAddRate(BigDecimal addRate) {
		this.addRate = addRate;
	}
	public BigDecimal getReturnRatio() {
		return returnRatio;
	}
	public void setReturnRatio(BigDecimal returnRatio) {
		this.returnRatio = returnRatio;
	}
	public Integer getCoupon() {
		return coupon;
	}
	public void setCoupon(Integer coupon) {
		this.coupon = coupon;
	}
	public Integer getNovice() {
		return novice;
	}
	public void setNovice(Integer novice) {
		this.novice = novice;
	}
	public BigDecimal getRaiseRate() {
		return raiseRate;
	}
	public void setRaiseRate(BigDecimal raiseRate) {
		this.raiseRate = raiseRate;
	}
	public BigDecimal getMaxCouponMoney() {
		return maxCouponMoney;
	}
	public void setMaxCouponMoney(BigDecimal maxCouponMoney) {
		this.maxCouponMoney = maxCouponMoney;
	}
	public Integer getRebateType() {
		return rebateType;
	}
	public void setRebateType(Integer rebateType) {
		this.rebateType = rebateType;
	}
	public Integer getRebateWay() {
		return rebateWay;
	}
	public void setRebateWay(Integer rebateWay) {
		this.rebateWay = rebateWay;
	}
	
	
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public Short getAuthorityStatus() {
		return authorityStatus;
	}
	public void setAuthorityStatus(Short authorityStatus) {
		this.authorityStatus = authorityStatus;
	}

	/**
	 * Default Empty Constructor for class PlManageMoneyPlan
	 */
	public PlManageMoneyPlan () {
		super();
	}
	public String getManageMoneyTypeName() {
		return manageMoneyTypeName;
	}

	public java.math.BigDecimal getMaxYearRate() {
		return maxYearRate;
	}
	public void setMaxYearRate(java.math.BigDecimal maxYearRate) {
		this.maxYearRate = maxYearRate;
	}
	public void setManageMoneyTypeName(String manageMoneyTypeName) {
		this.manageMoneyTypeName = manageMoneyTypeName;
	}

	public String getIsStartDatePay() {
		return isStartDatePay;
	}
	public void setIsStartDatePay(String isStartDatePay) {
		this.isStartDatePay = isStartDatePay;
	}
	public Integer getPayintentPerioDate() {
		return payintentPerioDate;
	}
	public void setPayintentPerioDate(Integer payintentPerioDate) {
		this.payintentPerioDate = payintentPerioDate;
	}
	public String getPayaccrualType() {
		return payaccrualType;
	}
	public void setPayaccrualType(String payaccrualType) {
		this.payaccrualType = payaccrualType;
	}
	public java.util.Date getLockingEndDate() {
		return lockingEndDate;
	}
	public void setLockingEndDate(java.util.Date lockingEndDate) {
		this.lockingEndDate = lockingEndDate;
	}
	public java.math.BigDecimal getRiskProtectionRate() {
		return riskProtectionRate;
	}
	public void setRiskProtectionRate(java.math.BigDecimal riskProtectionRate) {
		this.riskProtectionRate = riskProtectionRate;
	}
	public Integer getIsOne() {
		return isOne;
	}
	public void setIsOne(Integer isOne) {
		this.isOne = isOne;
	}
	
	/**
	 * Default Key Fields Constructor for class PlManageMoneyPlan
	 */
	public PlManageMoneyPlan (
		 Long in_mmplanId
        ) {
		this.setMmplanId(in_mmplanId);
    }

    
	public Integer getLockingLimit() {
		return lockingLimit;
	}
	public void setLockingLimit(Integer lockingLimit) {
		this.lockingLimit = lockingLimit;
	}
	public String getMoneyReceiver() {
		return moneyReceiver;
	}
	public void setMoneyReceiver(String moneyReceiver) {
		this.moneyReceiver = moneyReceiver;
	}
	public java.math.BigDecimal getEarlierOutRate() {
		return earlierOutRate;
	}
	public void setEarlierOutRate(java.math.BigDecimal earlierOutRate) {
		this.earlierOutRate = earlierOutRate;
	}

	public java.util.Set getPlManageMoneyPlanBuyinfos() {
		return plManageMoneyPlanBuyinfos;
	}

	public void setPlManageMoneyPlanBuyinfos(java.util.Set plManageMoneyPlanBuyinfos) {
		this.plManageMoneyPlanBuyinfos = plManageMoneyPlanBuyinfos;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public int getPersionNum() {
		return persionNum;
	}

	public void setPersionNum(int persionNum) {
		this.persionNum = persionNum;
	}

	public Integer getIsCyclingLend() {
		return isCyclingLend;
	}

	public void setIsCyclingLend(Integer isCyclingLend) {
		this.isCyclingLend = isCyclingLend;
	}

	public java.math.BigDecimal getAfterMoney() {
		return afterMoney;
	}

	public void setAfterMoney(java.math.BigDecimal afterMoney) {
		this.afterMoney = afterMoney;
	}

	/**
	 * bidId	 * @return Long
     * @hibernate.id column="mmplanId" type="java.lang.Long" generator-class="native"
	 */
	public Long getMmplanId() {
		return this.mmplanId;
	}
	
	/**
	 * Set the mmplanId
	 */	
	public void setMmplanId(Long aValue) {
		this.mmplanId = aValue;
	}	

	/**
	 * 理财名称	 * @return String
	 * @hibernate.property column="mmName" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getMmName() {
		return this.mmName;
	}
	
	/**
	 * Set the mmName
	 */	
	public void setMmName(String aValue) {
		this.mmName = aValue;
	}	

	/**
	 * 理财编号	 * @return String
	 * @hibernate.property column="mmNumber" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getMmNumber() {
		return this.mmNumber;
	}
	
	/**
	 * Set the mmNumber
	 */	
	public void setMmNumber(String aValue) {
		this.mmNumber = aValue;
	}	

	/**
	 * 理财编号	 * @return Long
	 * @hibernate.property column="manageMoneyTypeId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getManageMoneyTypeId() {
		return this.manageMoneyTypeId;
	}
	
	/**
	 * Set the manageMoneyTypeId
	 */	
	public void setManageMoneyTypeId(Long aValue) {
		this.manageMoneyTypeId = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="keystr" type="java.lang.String" length="50" not-null="false" unique="false"
	 */
	public String getKeystr() {
		return this.keystr;
	}
	
	/**
	 * Set the keystr
	 */	
	public void setKeystr(String aValue) {
		this.keystr = aValue;
	}	

	/**
	 * 理财编号	 * @return String
	 * @hibernate.property column="investScope" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getInvestScope() {
		return this.investScope;
	}
	
	/**
	 * Set the investScope
	 */	
	public void setInvestScope(String aValue) {
		this.investScope = aValue;
	}	

	/**
	 * 收益方式	 * @return String
	 * @hibernate.property column="benefitWay" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getBenefitWay() {
		return this.benefitWay;
	}
	
	/**
	 * Set the benefitWay
	 */	
	public void setBenefitWay(String aValue) {
		this.benefitWay = aValue;
	}	

	/**
	 * 收益方式	 * @return java.util.Date
	 * @hibernate.property column="buyStartTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuyStartTime() {
		return this.buyStartTime;
	}
	
	/**
	 * Set the buyStartTime
	 */	
	public void setBuyStartTime(java.util.Date aValue) {
		this.buyStartTime = aValue;
	}	

	/**
	 * 收益方式	 * @return java.util.Date
	 * @hibernate.property column="buyEndTime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuyEndTime() {
		return this.buyEndTime;
	}
	
	/**
	 * Set the buyEndTime
	 */	
	public void setBuyEndTime(java.util.Date aValue) {
		this.buyEndTime = aValue;
	}	

	/**
	 * 投资起点金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="startMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getStartMoney() {
		return this.startMoney;
	}
	
	/**
	 * Set the startMoney
	 */	
	public void setStartMoney(java.math.BigDecimal aValue) {
		this.startMoney = aValue;
	}	

	/**
	 * 递增金额	 * @return java.math.BigDecimal
	 * @hibernate.property column="riseMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getRiseMoney() {
		return this.riseMoney;
	}
	
	/**
	 * Set the riseMoney
	 */	
	public void setRiseMoney(java.math.BigDecimal aValue) {
		this.riseMoney = aValue;
	}	

	/**
	 * 单一投资人投资上限	 * @return java.math.BigDecimal
	 * @hibernate.property column="limitMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getLimitMoney() {
		return this.limitMoney;
	}
	
	/**
	 * Set the limitMoney
	 */	
	public void setLimitMoney(java.math.BigDecimal aValue) {
		this.limitMoney = aValue;
	}	

	/**
	 * 单一投资人投资上限	 * @return String
	 * @hibernate.property column="startinInterestCondition" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getStartinInterestCondition() {
		return this.startinInterestCondition;
	}
	
	/**
	 * Set the startinInterestCondition
	 */	
	public void setStartinInterestCondition(String aValue) {
		this.startinInterestCondition = aValue;
	}	

	/**
	 * 单一投资人投资上限	 * @return String
	 * @hibernate.property column="expireRedemptionWay" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getExpireRedemptionWay() {
		return this.expireRedemptionWay;
	}
	
	/**
	 * Set the expireRedemptionWay
	 */	
	public void setExpireRedemptionWay(String aValue) {
		this.expireRedemptionWay = aValue;
	}	

	/**
	 * 费用收取方式	 * @return String
	 * @hibernate.property column="chargeGetway" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getChargeGetway() {
		return this.chargeGetway;
	}
	
	/**
	 * Set the chargeGetway
	 */	
	public void setChargeGetway(String aValue) {
		this.chargeGetway = aValue;
	}	

	/**
	 * 保障方式	 * @return String
	 * @hibernate.property column="guaranteeWay" type="java.lang.String" length="100" not-null="false" unique="false"
	 */
	public String getGuaranteeWay() {
		return this.guaranteeWay;
	}
	
	/**
	 * Set the guaranteeWay
	 */	
	public void setGuaranteeWay(String aValue) {
		this.guaranteeWay = aValue;
	}	

	/**
	 * 年化收益率	 * @return java.math.BigDecimal
	 * @hibernate.property column="yeaRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getYeaRate() {
		return this.yeaRate;
	}
	
	/**
	 * Set the yeaRate
	 */	
	public void setYeaRate(java.math.BigDecimal aValue) {
		this.yeaRate = aValue;
	}	

	/**
	 * 投资期限	 * @return Integer
	 * @hibernate.property column="investlimit" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getInvestlimit() {
		return this.investlimit;
	}
	
	/**
	 * Set the investlimit
	 */	
	public void setInvestlimit(Integer aValue) {
		this.investlimit = aValue;
	}	

	/**
	 * 总额度	 * @return java.math.BigDecimal
	 * @hibernate.property column="sumMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getSumMoney() {
		return this.sumMoney;
	}
	
	/**
	 * Set the sumMoney
	 */	
	public void setSumMoney(java.math.BigDecimal aValue) {
		this.sumMoney = aValue;
	}	

	/**
	 * 状态 （0 未发布 1招标中 2已齐标 3已流标4已过期）	 * @return Integer
	 * @hibernate.property column="state" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getState() {
		return this.state;
	}
	
	/**
	 * Set the state
	 */	
	public void setState(Integer aValue) {
		this.state = aValue;
	}	

	/**
	 * 总额度	 * @return java.util.Date
	 * @hibernate.property column="startinInterestTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getStartinInterestTime() {
		return this.startinInterestTime;
	}
	
	/**
	 * Set the startinInterestTime
	 */	
	public void setStartinInterestTime(java.util.Date aValue) {
		this.startinInterestTime = aValue;
	}	

	/**
	 * 计息止日	 * @return java.util.Date
	 * @hibernate.property column="endinInterestTime" type="java.util.Date" length="10" not-null="false" unique="false"
	 */
	public java.util.Date getEndinInterestTime() {
		return this.endinInterestTime;
	}
	
	/**
	 * Set the endinInterestTime
	 */	
	public void setEndinInterestTime(java.util.Date aValue) {
		this.endinInterestTime = aValue;
	}	

	/**
	 * 计息止日	 * @return java.math.BigDecimal
	 * @hibernate.property column="investedMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getInvestedMoney() {
		return this.investedMoney;
	}
	
	/**
	 * Set the investedMoney
	 */	
	public void setInvestedMoney(java.math.BigDecimal aValue) {
		this.investedMoney = aValue;
	}	

	/**
	 * 招标说明	 * @return String
	 * @hibernate.property column="bidRemark" type="java.lang.String" length="65535" not-null="false" unique="false"
	 */
	public String getBidRemark() {
		return this.bidRemark;
	}
	
	/**
	 * Set the bidRemark
	 */	
	public void setBidRemark(String aValue) {
		this.bidRemark = aValue;
	}	

	/**
	 * 生成路径	 * @return String
	 * @hibernate.property column="htmlPath" type="java.lang.String" length="255" not-null="false" unique="false"
	 */
	public String getHtmlPath() {
		return this.htmlPath;
	}
	
	/**
	 * Set the htmlPath
	 */	
	public void setHtmlPath(String aValue) {
		this.htmlPath = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="createtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getCreatetime() {
		return this.createtime;
	}
	
	/**
	 * Set the createtime
	 */	
	public void setCreatetime(java.util.Date aValue) {
		this.createtime = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="updatetime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	/**
	 * Set the updatetime
	 */	
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlManageMoneyPlan)) {
			return false;
		}
		PlManageMoneyPlan rhs = (PlManageMoneyPlan) object;
		return new EqualsBuilder()
				.append(this.mmplanId, rhs.mmplanId)
				.append(this.mmName, rhs.mmName)
				.append(this.mmNumber, rhs.mmNumber)
				.append(this.manageMoneyTypeId, rhs.manageMoneyTypeId)
				.append(this.keystr, rhs.keystr)
				.append(this.investScope, rhs.investScope)
				.append(this.benefitWay, rhs.benefitWay)
				.append(this.buyStartTime, rhs.buyStartTime)
				.append(this.buyEndTime, rhs.buyEndTime)
				.append(this.startMoney, rhs.startMoney)
				.append(this.riseMoney, rhs.riseMoney)
				.append(this.limitMoney, rhs.limitMoney)
				.append(this.startinInterestCondition, rhs.startinInterestCondition)
				.append(this.expireRedemptionWay, rhs.expireRedemptionWay)
				.append(this.chargeGetway, rhs.chargeGetway)
				.append(this.guaranteeWay, rhs.guaranteeWay)
				.append(this.yeaRate, rhs.yeaRate)
				.append(this.investlimit, rhs.investlimit)
				.append(this.sumMoney, rhs.sumMoney)
				.append(this.state, rhs.state)
				.append(this.startinInterestTime, rhs.startinInterestTime)
				.append(this.endinInterestTime, rhs.endinInterestTime)
				.append(this.investedMoney, rhs.investedMoney)
				.append(this.bidRemark, rhs.bidRemark)
				.append(this.htmlPath, rhs.htmlPath)
				.append(this.createtime, rhs.createtime)
				.append(this.updatetime, rhs.updatetime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.mmplanId) 
				.append(this.mmName) 
				.append(this.mmNumber) 
				.append(this.manageMoneyTypeId) 
				.append(this.keystr) 
				.append(this.investScope) 
				.append(this.benefitWay) 
				.append(this.buyStartTime) 
				.append(this.buyEndTime) 
				.append(this.startMoney) 
				.append(this.riseMoney) 
				.append(this.limitMoney) 
				.append(this.startinInterestCondition) 
				.append(this.expireRedemptionWay) 
				.append(this.chargeGetway) 
				.append(this.guaranteeWay) 
				.append(this.yeaRate) 
				.append(this.investlimit) 
				.append(this.sumMoney) 
				.append(this.state) 
				.append(this.startinInterestTime) 
				.append(this.endinInterestTime) 
				.append(this.investedMoney) 
				.append(this.bidRemark) 
				.append(this.htmlPath) 
				.append(this.createtime) 
				.append(this.updatetime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("mmplanId", this.mmplanId) 
				.append("mmName", this.mmName) 
				.append("mmNumber", this.mmNumber) 
				.append("manageMoneyTypeId", this.manageMoneyTypeId) 
				.append("keystr", this.keystr) 
				.append("investScope", this.investScope) 
				.append("benefitWay", this.benefitWay) 
				.append("buyStartTime", this.buyStartTime) 
				.append("buyEndTime", this.buyEndTime) 
				.append("startMoney", this.startMoney) 
				.append("riseMoney", this.riseMoney) 
				.append("limitMoney", this.limitMoney) 
				.append("startinInterestCondition", this.startinInterestCondition) 
				.append("expireRedemptionWay", this.expireRedemptionWay) 
				.append("chargeGetway", this.chargeGetway) 
				.append("guaranteeWay", this.guaranteeWay) 
				.append("yeaRate", this.yeaRate) 
				.append("investlimit", this.investlimit) 
				.append("sumMoney", this.sumMoney) 
				.append("state", this.state) 
				.append("startinInterestTime", this.startinInterestTime) 
				.append("endinInterestTime", this.endinInterestTime) 
				.append("investedMoney", this.investedMoney) 
				.append("bidRemark", this.bidRemark) 
				.append("htmlPath", this.htmlPath) 
				.append("createtime", this.createtime) 
				.append("updatetime", this.updatetime) 
				.toString();
	}
	public Integer getPlmmStartTime() {
		return plmmStartTime;
	}
	public void setPlmmStartTime(Integer plmmStartTime) {
		this.plmmStartTime = plmmStartTime;
	}
	public java.math.BigDecimal getMonthRate() {
		return monthRate;
	}
	public void setMonthRate(java.math.BigDecimal monthRate) {
		this.monthRate = monthRate;
	}
	public java.math.BigDecimal getDayRate() {
		return dayRate;
	}
	public void setDayRate(java.math.BigDecimal dayRate) {
		this.dayRate = dayRate;
	}
	public java.util.Date getIntentDate() {
		return intentDate;
	}
	public void setIntentDate(java.util.Date intentDate) {
		this.intentDate = intentDate;
	}
	public java.math.BigDecimal getSumIncomeMoney() {
		return sumIncomeMoney;
	}
	public void setSumIncomeMoney(java.math.BigDecimal sumIncomeMoney) {
		this.sumIncomeMoney = sumIncomeMoney;
	}
	public java.util.Date getFactDate() {
		return factDate;
	}
	public void setFactDate(java.util.Date factDate) {
		this.factDate = factDate;
	}
	public java.util.Date getPreSaleTime() {
		return preSaleTime;
	}
	public void setPreSaleTime(java.util.Date preSaleTime) {
		this.preSaleTime = preSaleTime;
	}
	public java.math.BigDecimal getJoinRate() {
		return joinRate;
	}
	public void setJoinRate(java.math.BigDecimal joinRate) {
		this.joinRate = joinRate;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}
	public BigDecimal getBalanceMoney() {
		return balanceMoney;
	}
	public void setBalanceMoney(BigDecimal balanceMoney) {
		this.balanceMoney = balanceMoney;
	}



}
