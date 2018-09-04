package com.zhiwei.credit.model.creditFlow.creditAssignment.project;



public class VObligationInvestInfo implements java.io.Serializable{
	
	 	protected Long id;//债权表
	    protected Long investMentPersonId;//投资人表Id
		protected Long obligationId;//债权产品表的id
		protected String obligationName;//债权产品的名称
		protected java.math.BigDecimal obligationAccrual;//投资人匹配债权的利率
		protected Long investQuotient;//投资人投资份额
		protected java.math.BigDecimal investMoney;//投资人投资金额
		protected java.util.Date investStartDate;//投资人投资债权的开始时间
		protected java.util.Date investEndDate;//投资人购买债权的结束时间
		protected Long shopId;//登记投资人投资信息的采集人员所在门店
		protected Long creatorId;//采集人的id
		protected Long companyId;//采集人所在的公司
		protected Short investObligationStatus;//债权的状态（1结束，0进行中）
		protected Short systemInvest;//系统默认投资人（0表示系统默认投资人（例如金帆）；1默认一般投资人）
		protected java.math.BigDecimal investRate;//投资比例
		protected Short fundIntentStatus;//表示当前债权的回款状况（0表示债权未生效，1表示回款中，2表示回款结束）
		

		protected String orgName;//公司的名称
		protected String shopName;//门店的名称
		protected String creatorName;//采集人的姓名
		protected String investName;//投资人姓名
		protected String cardNumber;//投资人身份证号码
		protected String cellPhone;//投资人联系电话
		
		protected String projectName;//债权购买表的项目名称（目前是小贷表）
		protected String projectNumber;//债权购买表的项目编号（目前是小贷表）
		protected String obligationNumber;//债权产品的编号
		protected java.math.BigDecimal projectMoney;//债权产品的发行的金额
		protected java.math.BigDecimal minInvestMent;//最小投资额
		protected java.math.BigDecimal totalQuotient;//投资总分额（totalQuotient=projectMoney/minInvestMent）
		protected Integer payintentPeriod;//债权产品的发行期限
		
		//没有和数据库映射字段
		protected String fundIntentStatusName ;//债权目前状态（未回款 还是回款中，还是结束了）
		protected java.math.BigDecimal allBackMoney;//投资人购买债权本金和收益全部的收益
		protected java.math.BigDecimal alreadyBackMoney;//已经回款的收益和本金
		protected java.math.BigDecimal unBackMoney;//尚未回款的收益和本金
		
		
		public Short getFundIntentStatus() {
			return fundIntentStatus;
		}

		public void setFundIntentStatus(Short fundIntentStatus) {
			this.fundIntentStatus = fundIntentStatus;
		}
		
		public String getFundIntentStatusName() {
			return fundIntentStatusName;
		}

		public void setFundIntentStatusName(String fundIntentStatusName) {
			this.fundIntentStatusName = fundIntentStatusName;
		}
		
		public java.math.BigDecimal getAllBackMoney() {
			return allBackMoney;
		}

		public void setAllBackMoney(java.math.BigDecimal allBackMoney) {
			this.allBackMoney = allBackMoney;
		}

		public java.math.BigDecimal getAlreadyBackMoney() {
			return alreadyBackMoney;
		}

		public void setAlreadyBackMoney(java.math.BigDecimal alreadyBackMoney) {
			this.alreadyBackMoney = alreadyBackMoney;
		}

		public java.math.BigDecimal getUnBackMoney() {
			return unBackMoney;
		}

		public void setUnBackMoney(java.math.BigDecimal unBackMoney) {
			this.unBackMoney = unBackMoney;
		}

		
		
		public String getObligationNumber() {
			return obligationNumber;
		}

		public void setObligationNumber(String obligationNumber) {
			this.obligationNumber = obligationNumber;
		}
		public Integer getPayintentPeriod() {
			return payintentPeriod;
		}

		public void setPayintentPeriod(Integer payintentPeriod) {
			this.payintentPeriod = payintentPeriod;
		}

		protected java.math.BigDecimal rate;
		

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public Long getInvestMentPersonId() {
			return investMentPersonId;
		}

		public void setInvestMentPersonId(Long investMentPersonId) {
			this.investMentPersonId = investMentPersonId;
		}

		public Long getObligationId() {
			return obligationId;
		}

		public void setObligationId(Long obligationId) {
			this.obligationId = obligationId;
		}

		public String getObligationName() {
			return obligationName;
		}

		public void setObligationName(String obligationName) {
			this.obligationName = obligationName;
		}

		public java.math.BigDecimal getObligationAccrual() {
			return obligationAccrual;
		}

		public void setObligationAccrual(java.math.BigDecimal obligationAccrual) {
			this.obligationAccrual = obligationAccrual;
		}

		public Long getInvestQuotient() {
			return investQuotient;
		}

		public void setInvestQuotient(Long investQuotient) {
			this.investQuotient = investQuotient;
		}

		public java.math.BigDecimal getInvestMoney() {
			return investMoney;
		}

		public void setInvestMoney(java.math.BigDecimal investMoney) {
			this.investMoney = investMoney;
		}

		public java.util.Date getInvestStartDate() {
			return investStartDate;
		}

		public void setInvestStartDate(java.util.Date investStartDate) {
			this.investStartDate = investStartDate;
		}

		public java.util.Date getInvestEndDate() {
			return investEndDate;
		}

		public void setInvestEndDate(java.util.Date investEndDate) {
			this.investEndDate = investEndDate;
		}

		public Long getShopId() {
			return shopId;
		}

		public void setShopId(Long shopId) {
			this.shopId = shopId;
		}

		public Long getCreatorId() {
			return creatorId;
		}

		public void setCreatorId(Long creatorId) {
			this.creatorId = creatorId;
		}

		public Long getCompanyId() {
			return companyId;
		}

		public void setCompanyId(Long companyId) {
			this.companyId = companyId;
		}

		public Short getInvestObligationStatus() {
			return investObligationStatus;
		}

		public void setInvestObligationStatus(Short investObligationStatus) {
			this.investObligationStatus = investObligationStatus;
		}

		public String getOrgName() {
			return orgName;
		}

		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}

		public String getShopName() {
			return shopName;
		}

		public void setShopName(String shopName) {
			this.shopName = shopName;
		}

		public String getCreatorName() {
			return creatorName;
		}

		public void setCreatorName(String creatorName) {
			this.creatorName = creatorName;
		}

		public String getInvestName() {
			return investName;
		}

		public void setInvestName(String investName) {
			this.investName = investName;
		}

		public String getProjectName() {
			return projectName;
		}

		public void setProjectName(String projectName) {
			this.projectName = projectName;
		}

		public String getProjectNumber() {
			return projectNumber;
		}

		public void setProjectNumber(String projectNumber) {
			this.projectNumber = projectNumber;
		}

		public java.math.BigDecimal getProjectMoney() {
			return projectMoney;
		}

		public void setProjectMoney(java.math.BigDecimal projectMoney) {
			this.projectMoney = projectMoney;
		}

		public java.math.BigDecimal getMinInvestMent() {
			return minInvestMent;
		}

		public void setMinInvestMent(java.math.BigDecimal minInvestMent) {
			this.minInvestMent = minInvestMent;
		}

		public java.math.BigDecimal getTotalQuotient() {
			return totalQuotient;
		}

		public void setTotalQuotient(java.math.BigDecimal totalQuotient) {
			this.totalQuotient = totalQuotient;
		}

		public java.math.BigDecimal getRate() {
			return rate;
		}

		public void setRate(java.math.BigDecimal rate) {
			this.rate = rate;
		}
		
		public Short getSystemInvest() {
			return systemInvest;
		}

		public void setSystemInvest(Short systemInvest) {
			this.systemInvest = systemInvest;
		}

		public java.math.BigDecimal getInvestRate() {
			return investRate;
		}

		public void setInvestRate(java.math.BigDecimal investRate) {
			this.investRate = investRate;
		}

		public String getCardNumber() {
			return cardNumber;
		}

		public void setCardNumber(String cardNumber) {
			this.cardNumber = cardNumber;
		}

		public String getCellPhone() {
			return cellPhone;
		}

		public void setCellPhone(String cellPhone) {
			this.cellPhone = cellPhone;
		}
}
