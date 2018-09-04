package com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * PlManageMoneyPlanBuyinfo Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * 理财计划加入/产品购买情况库
 */
public class PlManageMoneyPlanBuyinfo extends com.zhiwei.core.model.BaseModel {
	//线上客户
    public static final short P_TYPE0=0;
    //线下客户
    public static final short P_TYPE1=1;
    
    public static final short pic_count=10;
    /**
     * 主键/订单id
     */
    protected Long orderId;
	/**
	 * 客户类型
	 * 1：线下 ，0：线上
	 */
    protected Short persionType;
    /**
     * 投资人Id
     */
	protected Long investPersonId;
	/**
     * 投资人姓名
     */
	protected String investPersonName;
	/**
     * 类型
     * mmplan：D计划，mmproduce：理财产品，experience：体验标，UPlan计划
     */
	protected String keystr;
	/**
     * 购买时间
     */
	protected java.util.Date buyDatetime;
	/**
     * 购买金额
     */
	protected java.math.BigDecimal buyMoney;
	/**
     * 起息日期
     */
	protected java.util.Date startinInterestTime;
	/**
     * 止息日期
     */
	protected java.util.Date endinInterestTime;
	/**
     *投资期限 
     */
	protected Integer orderlimit;
	/**
     * 预期年化利率
     */
	protected java.math.BigDecimal promisYearRate;
	/**
     * 预期月化利率
     */
	protected java.math.BigDecimal promisMonthRate;
	/**
     * 预期日化利率
     */
	protected java.math.BigDecimal promisDayRate;
	/**
     * 预期总收益
     */
	protected java.math.BigDecimal promisIncomeSum;
	/**
     * 当前可匹配金额
     */
	protected java.math.BigDecimal currentMatchingMoney;
	/**
     * 当前已获收益
     */
	protected java.math.BigDecimal currentGetedMoney;
	/**
     * 最优日化利率
     */
	protected java.math.BigDecimal optimalDayRate;
	/**
     * 外键  mmplanId
     */
	protected PlManageMoneyPlan plManageMoneyPlan;
	/**
     * 已匹配不同债权个数
     */
	protected Integer firstProjectIdcount;
	/**
     * 已匹配不同债权Id
     */
	protected String firstProjectIdstr;
	/**
     * 是否托管
     * 1：进行托管，0：不托管
     */
	protected Integer isAtuoMatch;
	/**
     * 交易流水号
     */
	protected String dealInfoNumber;
	/**
     * 订单名称
     */
	protected String mmName;
	/**
     * 状态
     * -2 ：关闭（流标），0：尚未支付购买金额，1：购买冻结成功，2：购买成功,7：提前支取申请中，8：提前支取，10：完成
     */
	@Expose
	protected Short state;
	/**
     * 富友预授权合同号
     */
	protected String preAuthorizationNum;
	/**
     * 是否在执行其他流程
     * 1：正在进行提前赎回流程
     */
	protected Short isOtherFlow;
	/**
     * 提前退出日期
     */
	protected java.util.Date earlierOutDate;
	/**
     * 合同编号
     */
	protected String contractNo;
	/**
     * 理财产品订单编号
     */
	private String recordNumber;
	/**
     * 优惠券金额
     */
	protected java.math.BigDecimal couponsMoney;
	/**
     * 投资比例
     */
	protected java.math.BigDecimal investmentProportion;
	/**
     * 优惠券Id
     */
	protected Long couponId;
	/**
     * 投资收益类型
     * 1：收益再投资,2：提取主账户
     */
	protected Integer investType;
	/**
     * 加入费用
     */
	protected java.math.BigDecimal joinMoney;
	/**
	 * 是否已经生成过奖励
	 * 1：没有生成 ，2：已经生成
	 */
	protected Integer isCreateReward;
	
	//不与数据库关联,用来传值
	protected java.math.BigDecimal incomeMoney;
	protected java.util.Date intentDate;
	protected java.util.Date factDate;
	
	public String getRecordNumber() {
		return recordNumber;
	}

	public void setRecordNumber(String recordNumber) {
		this.recordNumber = recordNumber;
	}

	protected Long runId;
	
	public Long getRunId() {
		return runId;
	}

	public void setRunId(Long runId) {
		this.runId = runId;
	}

	public Short getState() {
		return state;
	}

	public void setState(Short state) {
		this.state = state;
	}

	public java.util.Date getEarlierOutDate() {
		return earlierOutDate;
	}

	public void setEarlierOutDate(java.util.Date earlierOutDate) {
		this.earlierOutDate = earlierOutDate;
	}

	/*public java.util.Date getCreatEarlierOutTime() {
		return creatEarlierOutTime;
	}

	public void setCreatEarlierOutTime(java.util.Date creatEarlierOutTime) {
		this.creatEarlierOutTime = creatEarlierOutTime;
	}*/

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Short getIsOtherFlow() {
		return isOtherFlow;
	}

	public void setIsOtherFlow(Short isOtherFlow) {
		this.isOtherFlow = isOtherFlow;
	}

	public String getPreAuthorizationNum() {
		return preAuthorizationNum;
	}

	public void setPreAuthorizationNum(String preAuthorizationNum) {
		this.preAuthorizationNum = preAuthorizationNum;
	}

	/**
	 * Default Empty Constructor for class PlManageMoneyPlanBuyinfo
	 */
	public PlManageMoneyPlanBuyinfo () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class PlManageMoneyPlanBuyinfo
	 */
	public PlManageMoneyPlanBuyinfo (
		 Long in_orderId
        ) {
		this.setOrderId(in_orderId);
    }

    

	public String getDealInfoNumber() {
		return dealInfoNumber;
	}

	public void setDealInfoNumber(String dealInfoNumber) {
		this.dealInfoNumber = dealInfoNumber;
	}

	/**
	 * id	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */


	public PlManageMoneyPlan getPlManageMoneyPlan() {
		return plManageMoneyPlan;
	}




	public Integer getIsAtuoMatch() {
		return isAtuoMatch;
	}

	public void setIsAtuoMatch(Integer isAtuoMatch) {
		this.isAtuoMatch = isAtuoMatch;
	}

	public Integer getFirstProjectIdcount() {
		return firstProjectIdcount;
	}

	public void setFirstProjectIdcount(Integer firstProjectIdcount) {
		this.firstProjectIdcount = firstProjectIdcount;
	}

	public String getFirstProjectIdstr() {
		return firstProjectIdstr;
	}

	public void setFirstProjectIdstr(String firstProjectIdstr) {
		this.firstProjectIdstr = firstProjectIdstr;
	}

	public void setPlManageMoneyPlan(PlManageMoneyPlan plManageMoneyPlan) {
		this.plManageMoneyPlan = plManageMoneyPlan;
	}

	/**
	 * bidId	 * @return Long
	 * @hibernate.property column="mmplanId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */

	
	public String getKeystr() {
		return keystr;
	}

	public void setKeystr(String keystr) {
		this.keystr = keystr;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * Set the mmplanId
	 */	


	/**
	 * 	 * @return Long
	 * @hibernate.property column="investerType" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */


	/**
	 * 投资人账号	 * @return String
	 * @hibernate.property column="userName" type="java.lang.String" length="50" not-null="false" unique="false"
	 */

	/**
	 * 投资人id	 * @return Long
	 * @hibernate.property column="userId" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */

	/**
	 * 	 * @return String
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
	 * 	 * @return java.util.Date
	 * @hibernate.property column="buyDatetime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getBuyDatetime() {
		return this.buyDatetime;
	}
	
	/**
	 * Set the buyDatetime
	 */	
	public void setBuyDatetime(java.util.Date aValue) {
		this.buyDatetime = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="buyMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getBuyMoney() {
		return this.buyMoney;
	}
	
	/**
	 * Set the buyMoney
	 */	
	public void setBuyMoney(java.math.BigDecimal aValue) {
		this.buyMoney = aValue;
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
	 * 	 * @return Integer
	 * @hibernate.property column="investlimit" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */


	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="promisYearRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPromisYearRate() {
		return this.promisYearRate;
	}
	
	/**
	 * Set the promisYearRate
	 */	
	public void setPromisYearRate(java.math.BigDecimal aValue) {
		this.promisYearRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="promisMonthRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPromisMonthRate() {
		return this.promisMonthRate;
	}
	
	/**
	 * Set the promisMonthRate
	 */	
	public void setPromisMonthRate(java.math.BigDecimal aValue) {
		this.promisMonthRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="promisDayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPromisDayRate() {
		return this.promisDayRate;
	}
	
	/**
	 * Set the promisDayRate
	 */	
	public void setPromisDayRate(java.math.BigDecimal aValue) {
		this.promisDayRate = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="promisIncomeSum" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getPromisIncomeSum() {
		return this.promisIncomeSum;
	}
	
	/**
	 * Set the promisIncomeSum
	 */	
	public void setPromisIncomeSum(java.math.BigDecimal aValue) {
		this.promisIncomeSum = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="currentMatchingMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCurrentMatchingMoney() {
		return this.currentMatchingMoney;
	}
	
	/**
	 * Set the currentMatchingMoney
	 */	
	public void setCurrentMatchingMoney(java.math.BigDecimal aValue) {
		this.currentMatchingMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="currentGetedMoney" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getCurrentGetedMoney() {
		return this.currentGetedMoney;
	}
	
	/**
	 * Set the currentGetedMoney
	 */	
	public void setCurrentGetedMoney(java.math.BigDecimal aValue) {
		this.currentGetedMoney = aValue;
	}	

	/**
	 * 	 * @return java.math.BigDecimal
	 * @hibernate.property column="optimalDayRate" type="java.math.BigDecimal" length="20" not-null="false" unique="false"
	 */
	public java.math.BigDecimal getOptimalDayRate() {
		return this.optimalDayRate;
	}
	
	/**
	 * Set the optimalDayRate
	 */	
	public void setOptimalDayRate(java.math.BigDecimal aValue) {
		this.optimalDayRate = aValue;
	}	



	public Short getPersionType() {
		return persionType;
	}

	public void setPersionType(Short persionType) {
		this.persionType = persionType;
	}

	public Long getInvestPersonId() {
		return investPersonId;
	}

	public void setInvestPersonId(Long investPersonId) {
		this.investPersonId = investPersonId;
	}

	public String getInvestPersonName() {
		return investPersonName;
	}

	public void setInvestPersonName(String investPersonName) {
		this.investPersonName = investPersonName;
	}

	public Integer getOrderlimit() {
		return orderlimit;
	}

	public void setOrderlimit(Integer orderlimit) {
		this.orderlimit = orderlimit;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof PlManageMoneyPlanBuyinfo)) {
			return false;
		}
		PlManageMoneyPlanBuyinfo rhs = (PlManageMoneyPlanBuyinfo) object;
		return new EqualsBuilder()
				.append(this.orderId, rhs.orderId)
				.append(this.mmName, rhs.mmName)
				.append(this.buyDatetime, rhs.buyDatetime)
				.append(this.buyMoney, rhs.buyMoney)
				.append(this.startinInterestTime, rhs.startinInterestTime)
				.append(this.endinInterestTime, rhs.endinInterestTime)
				.append(this.promisYearRate, rhs.promisYearRate)
				.append(this.promisMonthRate, rhs.promisMonthRate)
				.append(this.promisDayRate, rhs.promisDayRate)
				.append(this.promisIncomeSum, rhs.promisIncomeSum)
				.append(this.currentMatchingMoney, rhs.currentMatchingMoney)
				.append(this.currentGetedMoney, rhs.currentGetedMoney)
				.append(this.optimalDayRate, rhs.optimalDayRate)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.orderId) 
				.append(this.mmName) 
				.append(this.buyDatetime) 
				.append(this.buyMoney) 
				.append(this.startinInterestTime) 
				.append(this.endinInterestTime) 
				.append(this.promisYearRate) 
				.append(this.promisMonthRate) 
				.append(this.promisDayRate) 
				.append(this.promisIncomeSum) 
				.append(this.currentMatchingMoney) 
				.append(this.currentGetedMoney) 
				.append(this.optimalDayRate) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("orderId", this.orderId) 
				.append("mmName", this.mmName) 
				.append("buyDatetime", this.buyDatetime) 
				.append("buyMoney", this.buyMoney) 
				.append("startinInterestTime", this.startinInterestTime) 
				.append("endinInterestTime", this.endinInterestTime) 
				.append("promisYearRate", this.promisYearRate) 
				.append("promisMonthRate", this.promisMonthRate) 
				.append("promisDayRate", this.promisDayRate) 
				.append("promisIncomeSum", this.promisIncomeSum) 
				.append("currentMatchingMoney", this.currentMatchingMoney) 
				.append("currentGetedMoney", this.currentGetedMoney) 
				.append("optimalDayRate", this.optimalDayRate) 
				.toString();
	}

	public java.math.BigDecimal getCouponsMoney() {
		return couponsMoney;
	}

	public void setCouponsMoney(java.math.BigDecimal couponsMoney) {
		this.couponsMoney = couponsMoney;
	}

	public java.math.BigDecimal getInvestmentProportion() {
		return investmentProportion;
	}

	public void setInvestmentProportion(java.math.BigDecimal investmentProportion) {
		this.investmentProportion = investmentProportion;
	}

	public java.math.BigDecimal getIncomeMoney() {
		return incomeMoney;
	}

	public void setIncomeMoney(java.math.BigDecimal incomeMoney) {
		this.incomeMoney = incomeMoney;
	}

	public java.util.Date getIntentDate() {
		return intentDate;
	}

	public void setIntentDate(java.util.Date intentDate) {
		this.intentDate = intentDate;
	}

	public java.util.Date getFactDate() {
		return factDate;
	}

	public void setFactDate(java.util.Date factDate) {
		this.factDate = factDate;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Integer getInvestType() {
		return investType;
	}

	public void setInvestType(Integer investType) {
		this.investType = investType;
	}

	public java.math.BigDecimal getJoinMoney() {
		return joinMoney;
	}

	public void setJoinMoney(java.math.BigDecimal joinMoney) {
		this.joinMoney = joinMoney;
	}

	public Integer getIsCreateReward() {
		return isCreateReward;
	}

	public void setIsCreateReward(Integer isCreateReward) {
		this.isCreateReward = isCreateReward;
	}



}
