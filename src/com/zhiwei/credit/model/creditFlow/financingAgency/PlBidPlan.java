package com.zhiwei.credit.model.creditFlow.financingAgency;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
 */

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.google.gson.annotations.Expose;

/**
 * @author
 */

/**
 * PlBidPlan Base Java Bean, base class for the.credit.model, mapped directly to database table
 * <p>
 * Avoid changing this file if not necessary, will be overwritten.
 * <p>
 * 招标计划
 */
public class PlBidPlan extends com.zhiwei.core.model.BaseModel {

    /*
     * 关闭状态
     */
    public static final int STATE_1 = -1;
    /*
     * 未发标
     */
    public static final int STATE0 = 0;
    /*
     * 招标中
     */
    public static final int STATE1 = 1;
    /*
     * 已齐标
     */
    public static final int STATE2 = 2;
    /*
     * 已流标
     */
    public static final int STATE3 = 3;
    /**
     * 已过期
     */
    public static final int STATE4 = 4;

    /*
     * 进债权库的散标状态为5
     */
    public static final int STATE5 = 5;
    /*
     * 起息办理中
     */
    public static final int STATE6 = 6;


    /*
     * 还款中
     */
    public static final int STATE7 = 7;


    /*
     * 展期中
     */
    public static final int STATE9 = 9;

    /**
     * 完成
     */
    public static final int STATE10 = 10;

    //2015-06-02新增字段
    /**
     * 表示收款人姓名，债券标表示原始债权人姓名，直投标表示借款人姓名
     */
    @Expose
    protected String receiverName;//表示收款人姓名，债券标表示原始债权人姓名，直投标表示借款人姓名
    @Expose
    /**
     * 表示收款人P2P账号，债券标表示原始债权人P2P账号，直投标表示借款人P2P账号
     */
    protected String receiverP2PAccountNumber;//表示收款人P2P账号，债券标表示原始债权人P2P账号，直投标表示借款人P2P账号
    /**
     * 债券标表示原始债权人，直投标表示借款人  类型（来之企业个人用户还是合作机构）
     */
    protected String reciverType;//债券标表示原始债权人，直投标表示借款人  类型（来之企业个人用户还是合作机构）
    /**
     * 债券标表示原始债权人，直投标表示借款人 Id（来之企业个人用户还是合作机构）
     */
    protected Long reciverId;//债券标表示原始债权人，直投标表示借款人 Id（来之企业个人用户还是合作机构）


    /**
     * 主键
     */
    @Expose
    protected Long bidId;
    /**
     * 招标项目名称
     */
    @Expose
    protected String bidProName;
    /**
     * 招标项目编号
     */
    @Expose
    protected String bidProNumber;
    /**
     * 标的类型，P_Dir个人直投标，P_Or个人债权标，B_Dir企业直投标，B_Or企业债权标
     */
    @Expose
    protected String proType;
    /**
     * 本次招标金额
     */
    @Expose
    protected java.math.BigDecimal bidMoney;
    /**
     * 占比
     */
    @Expose
    protected Double bidMoneyScale;
    /**
     * 判断是否启动
     */
    @Expose
    private Integer isStart;//判断是否启动
    /**
     * 投资起点金额
     */
    @Expose
    protected java.math.BigDecimal startMoney;
    /**
     * 满标时间
     */
    @Expose
    protected java.util.Date fullTime;
    /**
     * 递增单位
     */
    @Expose
    protected java.math.BigDecimal riseMoney;
    /**
     * 创建时间
     */
    @Expose
    protected java.util.Date createtime;
    /**
     * 更新时间
     */
    @Expose
    protected java.util.Date updatetime;
    /**
     * 招标计划状态
     */
    @Expose
    protected Integer state;
    /**
     * 状态备注
     */
    @Expose
    protected String stateRemark;
    /**
     * 起息日类型[['0', 'T(投标日+1天)'],	['1', 'T(招标截止日+1天)'],['2', 'T(满标日+1天)']],
     */
    @Expose
    protected Integer startInterestType;
    /**
     * 招标开放期限
     */
    @Expose
    protected Integer bidStartTime;
    /**
     * 开始投标时间
     */
    @Expose
    protected java.util.Date publishSingeTime;
    /**
     * 预售公告时间
     */
    protected java.util.Date prepareSellTime;
    /**
     * 招标截至时间
     */
    @Expose
    protected java.util.Date bidEndTime;
    /**
     * 招标说明
     */
    @Expose
    protected String bidRemark;
    /**
     * html路径
     */
    @Expose
    protected String htmlPath;
    /**
     * 包装借款项目类型
     */
    @Expose
    protected String proKeepType;//包装借款项目类型
    /**
     * 包装借款项目信用等级
     */
    @Expose
    protected String creditLevel;//包装借款项目信用等级
    /**
     * 投标进度
     */
    @Expose
    protected double progress;//投标进度
    /**
     * 投标人数
     */
    @Expose
    protected int persionNum;//投标人数
    /**
     * 剩余金额
     */
    @Expose
    protected BigDecimal afterMoney;//剩余金额
    /**
     * 还款方式
     */
    @Expose
    protected String payIntersetWay; //还款方式
    /**
     * 还款时间
     */
    @Expose
    protected Integer payMoneyTime; //还款时间
    /**
     * 还款时间方式 D 天/M月/Q季/Y年
     */
    @Expose
    protected String payMoneyTimeType; //还款时间方式 D 天/M月/Q季/Y年
    /**
     * 借款总额  多容易用到
     */
    @Expose
    protected BigDecimal loanTotalMoney; //借款总额  多容易用到
    /**
     * 合作机构金额  多容易用到
     */
    @Expose
    protected BigDecimal orgMoney; //合作机构金额  多容易用到
    /**
     * 是否放款，1：已放款
     */
    @Expose
    protected Short isLoan;//是否放款，1：已放款
    /**
     * 起息日期
     */
    @Expose
    protected java.util.Date startIntentDate;//起息日期
    /**
     * 止息日期
     */
    @Expose
    protected java.util.Date endIntentDate;//止息日期
    /**
     * 借款人p2p账号
     */
    @Expose
    protected String loginName;//借款人p2p账号
    /**
     * 完成
     */
    @Expose
    protected BigDecimal maxMoney;
    /**
     * 项目id
     */
    @Expose
    protected Long projId;//项目id
    /**
     * 完成
     */
    @Expose
    protected String oppositeType;
    /**
     * 资金方案id
     */
    @Expose
    protected Long moneyPlanId;//资金方案id
    /**
     * 期限
     */
    @Expose
    protected Integer interestPeriod;//期限
    /**
     * 年化利率
     */
    @Expose
    protected Integer yearRate;//年化利率
    /**
     * 年化利率(有映射)
     */
    @Expose
    protected java.math.BigDecimal yearInterestRate;
    /**
     * 信用等级id
     */
    @Expose
    protected Long creditLeveId;//信用等级id
    /**
     * 正在启动的流程      0没有启动任何流程,1提前还款，2展期
     */
    private Short isOtherFlow;//正在启动的流程      0没有启动任何流程,1提前还款，2展期
    /**
     * 授权标的自动还款流水号
     */
    private String requestNo;//授权标的自动还款流水号
    /**
     * 是否授权，1表示已经授权，null表示没有授权或者取消了授权
     */
    private Short authorizationStatus;//是否授权，1表示已经授权，null表示没有授权或者取消了授权
    /**
     * 罚息天数
     */
    protected Integer penaltyDays;//罚息天数
    /**
     * 招标进度
     */
    protected BigDecimal bidSchedule;//招标进度
    /**
     * 启动办理流程之前的状态
     */
    protected Integer originalState;//启动办理流程之前的状态
    /**
     * 债权类型
     */
    private String childType;//债权类型
    /**
     * 加息利率
     */
    @Expose
    protected BigDecimal addRate;//加息利率

    /**
     * 展示加息利率
     */
    @Expose
    protected BigDecimal showRate;//展示加息利率
    /**
     * 返现比例
     */
    @Expose
    protected BigDecimal returnRatio;//返现比例
    /**
     * 是否可用优惠券   1是，0否
     */
    @Expose
    protected Integer coupon;//是否可用优惠券   1是，0否
    /**
     * 是否新手专享   1是，0否
     */
    @Expose
    protected Integer novice;//是否新手专享   1是，0否
    /**
     * 募集期利率
     */
    @Expose
    protected BigDecimal raiseRate;//募集期利率
    /**
     * 单笔最大面值
     */
    @Expose
    protected BigDecimal maxCouponMoney;//单笔最大面值
    /**
     * 返利类型 1=返现 ，2=返息，3=返息现，4=加息
     */
    @Expose
    protected Integer rebateType;//返利类型 1=返现 ，2=返息，3=返息现，4=加息
    /**
     * 返利方式 1=立返 ，2=随期，3=到期
     */
    @Expose
    protected Integer rebateWay;//返利方式 1=立返 ，2=随期，3=到期

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
     * 信用等级
     */
    private String keepCreditlevelName;//信用等级


    //与数据库无映射
    //start
    protected String name;
    protected BigDecimal yearAccrualRate;
    protected String projectName;
    protected Long projectId;
    protected Long id;

    protected BigDecimal investMoney;
    /**
     * 四张基础表的proId
     */
    protected Long proId;
    /**
     * 四张基础表的proName
     */
    protected String proName;
    /**
     * 起息模式名称
     */
    protected String typeName;
    /**
     * 担保机构Id
     */
    protected Long guarantorsId;//担保机构Id
    /**
     * 保障方式['本金保障',1],['本息保障',2],['全部保障',3]
     */
    protected String guaranteeWay;
    /**
     * 担保费率
     */
    protected BigDecimal guaranteeRate;
    /**
     * 保费收取方式：1为一次性前置收取，2为随期收取
     */
    protected String guaranteeFeeWay;
    /**
     * 是否为vip  0 否 1是
     *
     * @return
     */
    protected Short isVip;

    /**   富滇银行对接保存数据     */

    /**
     * 发标的订单日期
     */
    protected String loanOrderDate;
    /**
     * 发标的订单流水号
     */
    protected String loanOrderNo;
    /**
     * 三方返回的标的号
     */
    protected String loanTxNo;
    /**
     * 三方返回的标的的账户号
     */
    protected String loanAccNo;


    /**
     * 版本类型，用来控制前台显示样式
     */
    protected Short versionType;

    public BigDecimal getShowRate() {
        return showRate;
    }

    public void setShowRate(BigDecimal showRate) {
        this.showRate = showRate;
    }

    public String getLoanAccNo() {
        return loanAccNo;
    }

    public void setLoanAccNo(String loanAccNo) {
        this.loanAccNo = loanAccNo;
    }

    public String getLoanTxNo() {
        return loanTxNo;
    }

    public void setLoanTxNo(String loanTxNo) {
        this.loanTxNo = loanTxNo;
    }

    public String getLoanOrderDate() {
        return loanOrderDate;
    }

    public void setLoanOrderDate(String loanOrderDate) {
        this.loanOrderDate = loanOrderDate;
    }

    public String getLoanOrderNo() {
        return loanOrderNo;
    }

    public void setLoanOrderNo(String loanOrderNo) {
        this.loanOrderNo = loanOrderNo;
    }


    public Long getGuarantorsId() {
        return guarantorsId;
    }

    public void setGuarantorsId(Long guarantorsId) {
        this.guarantorsId = guarantorsId;
    }

    public String getGuaranteeWay() {
        return guaranteeWay;
    }

    public void setGuaranteeWay(String guaranteeWay) {
        this.guaranteeWay = guaranteeWay;
    }

    public BigDecimal getGuaranteeRate() {
        return guaranteeRate;
    }

    public void setGuaranteeRate(BigDecimal guaranteeRate) {
        this.guaranteeRate = guaranteeRate;
    }

    public String getGuaranteeFeeWay() {
        return guaranteeFeeWay;
    }

    public void setGuaranteeFeeWay(String guaranteeFeeWay) {
        this.guaranteeFeeWay = guaranteeFeeWay;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public BigDecimal getRaiseRate() {
        return raiseRate;
    }

    public Long getProId() {
        return proId;
    }

    public void setProId(Long proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverP2PAccountNumber() {
        return receiverP2PAccountNumber;
    }

    public void setReceiverP2PAccountNumber(String receiverP2PAccountNumber) {
        this.receiverP2PAccountNumber = receiverP2PAccountNumber;
    }

    public String getOppositeType() {
        return oppositeType;
    }

    public void setOppositeType(String oppositeType) {
        this.oppositeType = oppositeType;
    }

    public java.math.BigDecimal getYearInterestRate() {
        return yearInterestRate;
    }

    public void setYearInterestRate(java.math.BigDecimal yearInterestRate) {
        this.yearInterestRate = yearInterestRate;
    }

    public String getStateRemark() {
        return stateRemark;
    }

    public void setStateRemark(String stateRemark) {
        this.stateRemark = stateRemark;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public Short getUmPayBidStatus() {
        return umPayBidStatus;
    }

    public void setUmPayBidStatus(Short umPayBidStatus) {
        this.umPayBidStatus = umPayBidStatus;
    }


    public Integer getNovice() {
        return novice;
    }

    public void setNovice(Integer novice) {
        this.novice = novice;
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

    public Integer getOriginalState() {
        return originalState;
    }

    public void setOriginalState(Integer originalState) {
        this.originalState = originalState;
    }

    public BigDecimal getBidSchedule() {
        return bidSchedule;
    }

    public void setBidSchedule(BigDecimal bidSchedule) {
        this.bidSchedule = bidSchedule;
    }

    public Integer getPenaltyDays() {
        return penaltyDays;
    }

    public void setPenaltyDays(Integer penaltyDays) {
        this.penaltyDays = penaltyDays;
    }

    public Short getIsOtherFlow() {
        return isOtherFlow;
    }

    public void setIsOtherFlow(Short isOtherFlow) {
        this.isOtherFlow = isOtherFlow;
    }

    public Long getCreditLeveId() {
        return creditLeveId;
    }

    public void setCreditLeveId(Long creditLeveId) {
        this.creditLeveId = creditLeveId;
    }

    public Integer getInterestPeriod() {
        return interestPeriod;
    }

    public void setInterestPeriod(Integer interestPeriod) {
        this.interestPeriod = interestPeriod;
    }

    public Integer getYearRate() {
        return yearRate;
    }

    public void setYearRate(Integer yearRate) {
        this.yearRate = yearRate;
    }

    public java.util.Date getPrepareSellTime() {
        return prepareSellTime;
    }

    public void setPrepareSellTime(java.util.Date prepareSellTime) {
        this.prepareSellTime = prepareSellTime;
    }

    public Long getProjId() {
        return projId;
    }

    public void setProjId(Long projId) {
        this.projId = projId;
    }

    public Long getMoneyPlanId() {
        return moneyPlanId;
    }

    public void setMoneyPlanId(Long moneyPlanId) {
        this.moneyPlanId = moneyPlanId;
    }

    public BigDecimal getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(BigDecimal maxMoney) {
        this.maxMoney = maxMoney;
    }

    public Integer getIsStart() {
        return isStart;
    }

    public void setIsStart(Integer isStart) {
        this.isStart = isStart;
    }

    public Short getIsLoan() {
        return isLoan;
    }

    public java.util.Date getStartIntentDate() {
        return startIntentDate;
    }

    public void setStartIntentDate(java.util.Date startIntentDate) {
        this.startIntentDate = startIntentDate;
    }

    public java.util.Date getEndIntentDate() {
        return endIntentDate;
    }

    public void setEndIntentDate(java.util.Date endIntentDate) {
        this.endIntentDate = endIntentDate;
    }

    public void setIsLoan(Short isLoan) {
        this.isLoan = isLoan;
    }

    public BigDecimal getLoanTotalMoney() {
        return loanTotalMoney;
    }

    public void setLoanTotalMoney(BigDecimal loanTotalMoney) {
        this.loanTotalMoney = loanTotalMoney;
    }

    public BigDecimal getOrgMoney() {
        return orgMoney;
    }

    public void setOrgMoney(BigDecimal orgMoney) {
        this.orgMoney = orgMoney;
    }

    public String getPayIntersetWay() {
        return payIntersetWay;
    }

    public void setPayIntersetWay(String payIntersetWay) {
        this.payIntersetWay = payIntersetWay;
    }

    public Integer getPayMoneyTime() {
        return payMoneyTime;
    }

    public void setPayMoneyTime(Integer payMoneyTime) {
        this.payMoneyTime = payMoneyTime;
    }


    public String getPayMoneyTimeType() {
        return payMoneyTimeType;
    }

    public void setPayMoneyTimeType(String payMoneyTimeType) {
        this.payMoneyTimeType = payMoneyTimeType;
    }

    public int getPersionNum() {
        return persionNum;
    }

    public void setPersionNum(int persionNum) {
        this.persionNum = persionNum;
    }

    public BigDecimal getAfterMoney() {
        return afterMoney;
    }

    public void setAfterMoney(BigDecimal afterMoney) {
        this.afterMoney = afterMoney;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public String getProKeepType() {
        return proKeepType;
    }

    public void setProKeepType(String proKeepType) {
        this.proKeepType = proKeepType;
    }

    public String getCreditLevel() {
        return creditLevel;
    }

    public void setCreditLevel(String creditLevel) {
        this.creditLevel = creditLevel;
    }

    @Expose
    protected com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro bpPersionOrPro;
    @Expose
    protected com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlBiddingType plBiddingType;
    @Expose
    protected com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro bpBusinessDirPro;
    @Expose
    protected com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro bpPersionDirPro;
    @Expose
    protected com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro bpBusinessOrPro;

    protected java.util.Set plBidInfos = new java.util.HashSet();

    /**
     * Default Empty Constructor for class PlBidPlan
     */
    public PlBidPlan() {
        super();
    }

    /**
     * Default Key Fields Constructor for class PlBidPlan
     */
    public PlBidPlan(
            Long in_bidId
    ) {
        this.setBidId(in_bidId);
    }


    public com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro getBpPersionOrPro() {
        return bpPersionOrPro;
    }

    public void setBpPersionOrPro(com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro in_bpPersionOrPro) {
        this.bpPersionOrPro = in_bpPersionOrPro;
    }

    public com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlBiddingType getPlBiddingType() {
        return plBiddingType;
    }

    public void setPlBiddingType(com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlBiddingType in_plBiddingType) {
        this.plBiddingType = in_plBiddingType;
    }

    public com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro getBpBusinessDirPro() {
        return bpBusinessDirPro;
    }

    public void setBpBusinessDirPro(com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro in_bpBusinessDirPro) {
        this.bpBusinessDirPro = in_bpBusinessDirPro;
    }

    public com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro getBpPersionDirPro() {
        return bpPersionDirPro;
    }

    public void setBpPersionDirPro(com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro in_bpPersionDirPro) {
        this.bpPersionDirPro = in_bpPersionDirPro;
    }

    public com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro getBpBusinessOrPro() {
        return bpBusinessOrPro;
    }

    public void setBpBusinessOrPro(com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro in_bpBusinessOrPro) {
        this.bpBusinessOrPro = in_bpBusinessOrPro;
    }

    public java.util.Set getPlBidInfos() {
        return plBidInfos;
    }

    public void setPlBidInfos(java.util.Set in_plBidInfos) {
        this.plBidInfos = in_plBidInfos;
    }


    public Short getVersionType() {
        return versionType;
    }

    public void setVersionType(Short versionType) {
        this.versionType = versionType;
    }

    /**
     * bidId	 * @return Long
     *
     * @hibernate.id column="bidId" type="java.lang.Long" generator-class="native"
     */
    public Long getBidId() {
        return this.bidId;
    }

    /**
     * Set the bidId
     */
    public void setBidId(Long aValue) {
        this.bidId = aValue;
    }

    /**
     * 招标项目名称	 * @return String
     *
     * @hibernate.property column="bidProName" type="java.lang.String" length="255" not-null="false" unique="false"
     */
    public String getBidProName() {
        return this.bidProName;
    }

    /**
     * Set the bidProName
     */
    public void setBidProName(String aValue) {
        this.bidProName = aValue;
    }

    /**
     * 招标项目编号	 * @return String
     *
     * @hibernate.property column="bidProNumber" type="java.lang.String" length="100" not-null="false" unique="false"
     */
    public String getBidProNumber() {
        return this.bidProNumber;
    }

    /**
     * Set the bidProNumber
     */
    public void setBidProNumber(String aValue) {
        this.bidProNumber = aValue;
    }

    /**
     * 招标类型	 * @return Long
     */
    public Long getBiddingTypeId() {
        return this.getPlBiddingType() == null ? null : this.getPlBiddingType().getBiddingTypeId();
    }

    /**
     * Set the biddingTypeId
     */
    public void setBiddingTypeId(Long aValue) {
        if (aValue == null) {
            plBiddingType = null;
        } else if (plBiddingType == null) {
            plBiddingType = new com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlBiddingType(aValue);
            plBiddingType.setVersion(new Integer(0));//set a version to cheat hibernate only
        } else {
            //
            plBiddingType.setBiddingTypeId(aValue);
        }
    }

    /**
     * 个人债权项目id	 * @return Long
     */
    public Long getPOrProId() {
        return this.getBpPersionOrPro() == null ? null : this.getBpPersionOrPro().getPorProId();
    }

    /**
     * Set the pOrProId
     */
    public void setPOrProId(Long aValue) {
        if (aValue == null) {
            bpPersionOrPro = null;
        } else if (bpPersionOrPro == null) {
            bpPersionOrPro = new com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionOrPro(aValue);
            bpPersionOrPro.setVersion(new Integer(0));//set a version to cheat hibernate only
        } else {
            //
            bpPersionOrPro.setPorProId(aValue);
        }
    }

    /**
     * 个人直投项目id	 * @return Long
     */
    public Long getPDirProId() {
        return this.getBpPersionDirPro() == null ? null : this.getBpPersionDirPro().getPdirProId();
    }

    /**
     * Set the pDirProId
     */
    public void setPDirProId(Long aValue) {
        if (aValue == null) {
            bpPersionDirPro = null;
        } else if (bpPersionDirPro == null) {
            bpPersionDirPro = new com.zhiwei.credit.model.creditFlow.financingAgency.persion.BpPersionDirPro(aValue);
            bpPersionDirPro.setVersion(new Integer(0));//set a version to cheat hibernate only
        } else {
            //
            bpPersionDirPro.setPdirProId(aValue);
        }
    }

    /**
     * 项目类型  企业直投  B_Dir  企业 债权 B_Or  个人直投 P_Dir 个人债权 P_Or	 * @return String
     *
     * @hibernate.property column="proType" type="java.lang.String" length="50" not-null="false" unique="false"
     */
    public String getProType() {
        return this.proType;
    }

    /**
     * Set the proType
     */
    public void setProType(String aValue) {
        this.proType = aValue;
    }

    /**
     * 企业直投项目id	 * @return Long
     */
    public Long getBdirProId() {
        return this.getBpBusinessDirPro() == null ? null : this.getBpBusinessDirPro().getBdirProId();
    }

    /**
     * Set the bdirProId
     */
    public void setBdirProId(Long aValue) {
        if (aValue == null) {
            bpBusinessDirPro = null;
        } else if (bpBusinessDirPro == null) {
            bpBusinessDirPro = new com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessDirPro(aValue);
            bpBusinessDirPro.setVersion(new Integer(0));//set a version to cheat hibernate only
        } else {
            //
            bpBusinessDirPro.setBdirProId(aValue);
        }
    }

    /**
     * 本招标金额	 * @return java.math.BigDecimal
     *
     * @hibernate.property column="bidMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
     */
    public java.math.BigDecimal getBidMoney() {
        return this.bidMoney;
    }

    /**
     * Set the bidMoney
     */
    public void setBidMoney(java.math.BigDecimal aValue) {
        this.bidMoney = aValue;
    }

    /**
     * 本招标金额所在项目比率	 * @return Double
     *
     * @hibernate.property column="bidMoneyScale" type="java.lang.Double" length="22" not-null="false" unique="false"
     */
    public Double getBidMoneyScale() {
        return this.bidMoneyScale;
    }

    /**
     * Set the bidMoneyScale
     */
    public void setBidMoneyScale(Double aValue) {
        this.bidMoneyScale = aValue;
    }

    /**
     * 投资起点金额	 * @return java.math.BigDecimal
     *
     * @hibernate.property column="startMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
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
     *
     * @hibernate.property column="riseMoney" type="java.math.BigDecimal" length="10" not-null="false" unique="false"
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
     * * @return java.util.Date
     *
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
     * * @return java.util.Date
     *
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
     * 状态 （0 未发布 1招标中 2已齐标 3已流标4手动关闭）	 * @return Integer
     *
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
     * 起息日类型（0投标日—+1 ；1投标截至日+1 ；2标满日+1）	 * @return Integer
     *
     * @hibernate.property column="startInterestType" type="java.lang.Integer" length="10" not-null="false" unique="false"
     */
    public Integer getStartInterestType() {
        return this.startInterestType;
    }

    /**
     * Set the startInterestType
     */
    public void setStartInterestType(Integer aValue) {
        this.startInterestType = aValue;
    }

    /**
     * 招标开发期限（小时为单位）	 * @return Integer
     *
     * @hibernate.property column="bidStartTime" type="java.lang.Integer" length="10" not-null="false" unique="false"
     */
    public Integer getBidStartTime() {
        return this.bidStartTime;
    }

    /**
     * Set the bidStartTime
     */
    public void setBidStartTime(Integer aValue) {
        this.bidStartTime = aValue;
    }

    /**
     * 发标时间	 * @return java.util.Date
     *
     * @hibernate.property column="publishSingeTime" type="java.util.Date" length="19" not-null="false" unique="false"
     */
    public java.util.Date getPublishSingeTime() {
        return this.publishSingeTime;
    }

    /**
     * Set the publishSingeTime
     */
    public void setPublishSingeTime(java.util.Date aValue) {
        this.publishSingeTime = aValue;
    }

    /**
     * 招标截至日期	 * @return java.util.Date
     *
     * @hibernate.property column="bidEndTime" type="java.util.Date" length="19" not-null="false" unique="false"
     */
    public java.util.Date getBidEndTime() {
        return this.bidEndTime;
    }

    /**
     * Set the bidEndTime
     */
    public void setBidEndTime(java.util.Date aValue) {
        this.bidEndTime = aValue;
    }

    /**
     * 招标说明	 * @return String
     *
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
     *
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
     * 企业债权项目id	 * @return Long
     */
    public Long getBorProId() {
        return this.getBpBusinessOrPro() == null ? null : this.getBpBusinessOrPro().getBorProId();
    }

    /**
     * Set the borProId
     */
    public void setBorProId(Long aValue) {
        if (aValue == null) {
            bpBusinessOrPro = null;
        } else if (bpBusinessOrPro == null) {
            bpBusinessOrPro = new com.zhiwei.credit.model.creditFlow.financingAgency.business.BpBusinessOrPro(aValue);
            bpBusinessOrPro.setVersion(new Integer(0));//set a version to cheat hibernate only
        } else {
            //
            bpBusinessOrPro.setBorProId(aValue);
        }
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    public boolean equals(Object object) {
        if (!(object instanceof PlBidPlan)) {
            return false;
        }
        PlBidPlan rhs = (PlBidPlan) object;
        return new EqualsBuilder()
                .append(this.bidId, rhs.bidId)
                .append(this.bidProName, rhs.bidProName)
                .append(this.bidProNumber, rhs.bidProNumber)
                .append(this.proType, rhs.proType)
                .append(this.bidMoney, rhs.bidMoney)
                .append(this.bidMoneyScale, rhs.bidMoneyScale)
                .append(this.startMoney, rhs.startMoney)
                .append(this.riseMoney, rhs.riseMoney)
                .append(this.createtime, rhs.createtime)
                .append(this.updatetime, rhs.updatetime)
                .append(this.state, rhs.state)
                .append(this.startInterestType, rhs.startInterestType)
                .append(this.bidStartTime, rhs.bidStartTime)
                .append(this.publishSingeTime, rhs.publishSingeTime)
                .append(this.bidEndTime, rhs.bidEndTime)
                .append(this.bidRemark, rhs.bidRemark)
                .append(this.htmlPath, rhs.htmlPath)
                .isEquals();
    }

    /**
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return new HashCodeBuilder(-82280557, -700257973)
                .append(this.bidId)
                .append(this.bidProName)
                .append(this.bidProNumber)
                .append(this.proType)
                .append(this.bidMoney)
                .append(this.bidMoneyScale)
                .append(this.startMoney)
                .append(this.riseMoney)
                .append(this.createtime)
                .append(this.updatetime)
                .append(this.state)
                .append(this.startInterestType)
                .append(this.bidStartTime)
                .append(this.publishSingeTime)
                .append(this.bidEndTime)
                .append(this.bidRemark)
                .append(this.htmlPath)
                .toHashCode();
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return new ToStringBuilder(this)
                .append("bidId", this.bidId)
                .append("bidProName", this.bidProName)
                .append("bidProNumber", this.bidProNumber)
                .append("proType", this.proType)
                .append("bidMoney", this.bidMoney)
                .append("bidMoneyScale", this.bidMoneyScale)
                .append("startMoney", this.startMoney)
                .append("riseMoney", this.riseMoney)
                .append("createtime", this.createtime)
                .append("updatetime", this.updatetime)
                .append("state", this.state)
                .append("startInterestType", this.startInterestType)
                .append("bidStartTime", this.bidStartTime)
                .append("publishSingeTime", this.publishSingeTime)
                .append("bidEndTime", this.bidEndTime)
                .append("bidRemark", this.bidRemark)
                .append("htmlPath", this.htmlPath)
                .toString();
    }

    public void setRequestNo(String requestNo) {
        this.requestNo = requestNo;
    }

    public String getRequestNo() {
        return requestNo;
    }

    public void setAuthorizationStatus(Short authorizationStatus) {
        this.authorizationStatus = authorizationStatus;
    }

    public Short getAuthorizationStatus() {
        return authorizationStatus;
    }

    public String getChildType() {
        return childType;
    }

    public void setChildType(String childType) {
        this.childType = childType;
    }

    public String getReciverType() {
        return reciverType;
    }

    public void setReciverType(String reciverType) {
        this.reciverType = reciverType;
    }

    public Long getReciverId() {
        return reciverId;
    }

    public void setReciverId(Long reciverId) {
        this.reciverId = reciverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getYearAccrualRate() {
        return yearAccrualRate;
    }

    public void setYearAccrualRate(BigDecimal yearAccrualRate) {
        this.yearAccrualRate = yearAccrualRate;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeepCreditlevelName() {
        return keepCreditlevelName;
    }

    public void setKeepCreditlevelName(String keepCreditlevelName) {
        this.keepCreditlevelName = keepCreditlevelName;
    }

    public java.util.Date getFullTime() {
        return fullTime;
    }

    public void setFullTime(java.util.Date fullTime) {
        this.fullTime = fullTime;
    }

    public BigDecimal getInvestMoney() {
        return investMoney;
    }

    public void setInvestMoney(BigDecimal investMoney) {
        this.investMoney = investMoney;
    }

    public Short getIsVip() {
        return isVip;
    }

    public void setIsVip(Short isVip) {
        this.isVip = isVip;
    }


}
