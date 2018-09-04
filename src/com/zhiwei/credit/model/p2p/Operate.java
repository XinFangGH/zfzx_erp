package com.zhiwei.credit.model.p2p;

import java.math.BigDecimal;

public class Operate {

    private BigDecimal sumMoney;//累计交易总额
    private Integer sumCount;//累计交易笔数
    private BigDecimal balanceLoanMoney;//借贷余额
    private Integer balanceLoanCount;//借贷余额笔数
    private BigDecimal sumProfit;//利息余额
    private BigDecimal interestBalance;//利息余额
    private Integer sumPayPeople;//累计出借人数量
    private Integer payPeople;//当前出借人数量
    private Integer sumIncomePeople;//累计借款人数量
    private Integer incomePeople;//当前借款人数量
    private BigDecimal tenIncomeMoneyProporion;//前十大借款人待还金额占比
    private BigDecimal maxIncomeMoneyProporion;//最大单一借款人待还金额占比
    private BigDecimal avgSumIncomePeople;//人均累计借款金额
    private BigDecimal avgSumPayPeople;//人均累计出借金额
    private BigDecimal maxPayPeopleProporion;//最大单户出借余额占比
    private BigDecimal tenPayPeopleProporion;//最大10户出借余额占比
    private BigDecimal avgIncomePeople;//人均借款额度   借贷余额/借贷人数
    private Integer avgIncomeDay;//人均借款期限  累计投资期限/累计借款笔数
    private BigDecimal avgIncomeCost;//人均借款成本  借款总利息/借款人数
    private Integer avgPayDay;//人均投资期限  累计投资期限/累计投资笔数
    private BigDecimal avgInterestMoney;//人均投资回报
    private  Integer avgFullTime;//平均满标用时
    private  Integer avgRate;//平均借款利率

    public Operate() {
    }

    public Operate(BigDecimal sumMoney, Integer sumCount, BigDecimal balanceLoanMoney, Integer balanceLoanCount, BigDecimal interestBalance, Integer sumPayPeople, Integer payPeople, Integer sumIncomePeople, Integer incomePeople, BigDecimal tenIncomeMoneyProporion, BigDecimal maxIncomeMoneyProporion, BigDecimal avgSumIncomePeople, BigDecimal avgSumPayPeople, BigDecimal maxPayPeopleProporion, BigDecimal tenPayPeopleProporion, BigDecimal avgIncomePeople, Integer avgIncomeDay, BigDecimal avgIncomeCost, Integer avgPayDay, BigDecimal avgInterestMoney, Integer avgFullTime, Integer avgRate) {
        this.sumMoney = sumMoney;
        this.sumCount = sumCount;
        this.balanceLoanMoney = balanceLoanMoney;
        this.balanceLoanCount = balanceLoanCount;
        this.interestBalance = interestBalance;
        this.sumPayPeople = sumPayPeople;
        this.payPeople = payPeople;
        this.sumIncomePeople = sumIncomePeople;
        this.incomePeople = incomePeople;
        this.tenIncomeMoneyProporion = tenIncomeMoneyProporion;
        this.maxIncomeMoneyProporion = maxIncomeMoneyProporion;
        this.avgSumIncomePeople = avgSumIncomePeople;
        this.avgSumPayPeople = avgSumPayPeople;
        this.maxPayPeopleProporion = maxPayPeopleProporion;
        this.tenPayPeopleProporion = tenPayPeopleProporion;
        this.avgIncomePeople = avgIncomePeople;
        this.avgIncomeDay = avgIncomeDay;
        this.avgIncomeCost = avgIncomeCost;
        this.avgPayDay = avgPayDay;
        this.avgInterestMoney = avgInterestMoney;
        this.avgFullTime = avgFullTime;
        this.avgRate = avgRate;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public BigDecimal getBalanceLoanMoney() {
        return balanceLoanMoney;
    }

    public void setBalanceLoanMoney(BigDecimal balanceLoanMoney) {
        this.balanceLoanMoney = balanceLoanMoney;
    }

    public Integer getBalanceLoanCount() {
        return balanceLoanCount;
    }

    public void setBalanceLoanCount(Integer balanceLoanCount) {
        this.balanceLoanCount = balanceLoanCount;
    }

    public BigDecimal getInterestBalance() {
        return interestBalance;
    }

    public void setInterestBalance(BigDecimal interestBalance) {
        this.interestBalance = interestBalance;
    }

    public Integer getSumPayPeople() {
        return sumPayPeople;
    }

    public void setSumPayPeople(Integer sumPayPeople) {
        this.sumPayPeople = sumPayPeople;
    }

    public Integer getPayPeople() {
        return payPeople;
    }

    public void setPayPeople(Integer payPeople) {
        this.payPeople = payPeople;
    }

    public Integer getSumIncomePeople() {
        return sumIncomePeople;
    }

    public void setSumIncomePeople(Integer sumIncomePeople) {
        this.sumIncomePeople = sumIncomePeople;
    }

    public Integer getIncomePeople() {
        return incomePeople;
    }

    public void setIncomePeople(Integer incomePeople) {
        this.incomePeople = incomePeople;
    }

    public BigDecimal getTenIncomeMoneyProporion() {
        return tenIncomeMoneyProporion;
    }

    public void setTenIncomeMoneyProporion(BigDecimal tenIncomeMoneyProporion) {
        this.tenIncomeMoneyProporion = tenIncomeMoneyProporion;
    }

    public BigDecimal getMaxIncomeMoneyProporion() {
        return maxIncomeMoneyProporion;
    }

    public void setMaxIncomeMoneyProporion(BigDecimal maxIncomeMoneyProporion) {
        this.maxIncomeMoneyProporion = maxIncomeMoneyProporion;
    }

    public BigDecimal getAvgSumIncomePeople() {
        return avgSumIncomePeople;
    }

    public void setAvgSumIncomePeople(BigDecimal avgSumIncomePeople) {
        this.avgSumIncomePeople = avgSumIncomePeople;
    }

    public BigDecimal getAvgSumPayPeople() {
        return avgSumPayPeople;
    }

    public void setAvgSumPayPeople(BigDecimal avgSumPayPeople) {
        this.avgSumPayPeople = avgSumPayPeople;
    }

    public BigDecimal getMaxPayPeopleProporion() {
        return maxPayPeopleProporion;
    }

    public void setMaxPayPeopleProporion(BigDecimal maxPayPeopleProporion) {
        this.maxPayPeopleProporion = maxPayPeopleProporion;
    }

    public BigDecimal getTenPayPeopleProporion() {
        return tenPayPeopleProporion;
    }

    public void setTenPayPeopleProporion(BigDecimal tenPayPeopleProporion) {
        this.tenPayPeopleProporion = tenPayPeopleProporion;
    }

    public BigDecimal getAvgIncomePeople() {
        return avgIncomePeople;
    }

    public void setAvgIncomePeople(BigDecimal avgIncomePeople) {
        this.avgIncomePeople = avgIncomePeople;
    }

    public Integer getAvgIncomeDay() {
        return avgIncomeDay;
    }

    public void setAvgIncomeDay(Integer avgIncomeDay) {
        this.avgIncomeDay = avgIncomeDay;
    }

    public BigDecimal getAvgIncomeCost() {
        return avgIncomeCost;
    }

    public void setAvgIncomeCost(BigDecimal avgIncomeCost) {
        this.avgIncomeCost = avgIncomeCost;
    }

    public Integer getAvgPayDay() {
        return avgPayDay;
    }

    public void setAvgPayDay(Integer avgPayDay) {
        this.avgPayDay = avgPayDay;
    }

    public BigDecimal getAvgInterestMoney() {
        return avgInterestMoney;
    }

    public void setAvgInterestMoney(BigDecimal avgInterestMoney) {
        this.avgInterestMoney = avgInterestMoney;
    }

    public Integer getAvgFullTime() {
        return avgFullTime;
    }

    public void setAvgFullTime(Integer avgFullTime) {
        this.avgFullTime = avgFullTime;
    }

    public Integer getAvgRate() {
        return avgRate;
    }

    public void setAvgRate(Integer avgRate) {
        this.avgRate = avgRate;
    }

    public BigDecimal getSumProfit() {
        return sumProfit;
    }

    public void setSumProfit(BigDecimal sumProfit) {
        this.sumProfit = sumProfit;
    }
}
