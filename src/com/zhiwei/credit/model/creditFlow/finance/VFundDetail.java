// default package

package com.zhiwei.credit.model.creditFlow.finance;


/**
 * VFundDetailId entity. @author MyEclipse Persistence Tools
 */

public class VFundDetail  extends com.zhiwei.core.model.BaseModel {


    // Fields    

     private String projectName;
     private String projectNumber;
     private String funType;
     private Long projectId;
     private java.util.Date intentDate;
     private Double intentpayMoney;
     private Double intentincomeMoney;
     private java.util.Date factDate;
     private Long fundDetailId;
     private Long fundIntentId;
     private Long fundQlideId;
     private Long overdueNum;
     private Double overdueAccrual;
     private java.util.Date operTime;
     private Double afterMoney;
     private String transactionType;
     private String myAccount;
     private String currency;
     private Double qlidepayMoney;
     private Double qlideincomeMoney;
     private String isCash;
     private String businessType;
     private String fundTypeName;
     private String qlidetransactionType;
     protected java.lang.Short iscancel;
 	protected String cancelremark;
 	protected String checkuser;
	protected String orgName;


    // Constructors

    /** default constructor */
    public VFundDetail() {
    }

	/** minimal constructor */
    public VFundDetail(Long fundDetailId) {
        this.fundDetailId = fundDetailId;
    }
    
    /** full constructor */
    public VFundDetail(String projectName, String projectNumber, String funType, Long projectId, java.util.Date intentDate, Double intentpayMoney, Double intentincomeMoney, java.util.Date factDate, Long fundDetailId, Long fundIntentId, Long fundQlideId, Long overdueNum, Double overdueAccrual, java.util.Date operTime, Double afterMoney, String transactionType, String myAccount, String currency, Double qlidepayMoney, Double qlideincomeMoney, String isCash, String businessType, String fundTypeName,String qlidetransactionType,Short iscancel, String cancelremark, String checkuser) {
        this.projectName = projectName;
        this.projectNumber = projectNumber;
        this.funType = funType;
        this.projectId = projectId;
        this.intentDate = intentDate;
        this.intentpayMoney = intentpayMoney;
        this.intentincomeMoney = intentincomeMoney;
        this.factDate = factDate;
        this.fundDetailId = fundDetailId;
        this.fundIntentId = fundIntentId;
        this.fundQlideId = fundQlideId;
        this.overdueNum = overdueNum;
        this.overdueAccrual = overdueAccrual;
        this.operTime = operTime;
        this.afterMoney = afterMoney;
        this.transactionType = transactionType;
        this.myAccount = myAccount;
        this.currency = currency;
        this.qlidepayMoney = qlidepayMoney;
        this.qlideincomeMoney = qlideincomeMoney;
        this.isCash = isCash;
        this.businessType = businessType;
        this.fundTypeName = fundTypeName;
        this.qlidetransactionType = qlidetransactionType;
        this.iscancel = iscancel;
        this.cancelremark = cancelremark;
        this.checkuser = checkuser;
    }

   
    // Property accessors

    public String getProjectName() {
        return this.projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNumber() {
        return this.projectNumber;
    }
    
    public void setProjectNumber(String projectNumber) {
        this.projectNumber = projectNumber;
    }

    public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public java.lang.Short getIscancel() {
		return iscancel;
	}

	public void setIscancel(java.lang.Short iscancel) {
		this.iscancel = iscancel;
	}

	public String getCancelremark() {
		return cancelremark;
	}

	public void setCancelremark(String cancelremark) {
		this.cancelremark = cancelremark;
	}

	public String getCheckuser() {
		return checkuser;
	}

	public void setCheckuser(String checkuser) {
		this.checkuser = checkuser;
	}

	public String getQlidetransactionType() {
		return qlidetransactionType;
	}

	public void setQlidetransactionType(String qlidetransactionType) {
		this.qlidetransactionType = qlidetransactionType;
	}

	public String getFunType() {
        return this.funType;
    }
    
    public void setFunType(String funType) {
        this.funType = funType;
    }

    public Long getProjectId() {
        return this.projectId;
    }
    
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public java.util.Date getIntentDate() {
        return this.intentDate;
    }
    
    public void setIntentDate(java.util.Date intentDate) {
        this.intentDate = intentDate;
    }

    public Double getIntentpayMoney() {
        return this.intentpayMoney;
    }
    
    public void setIntentpayMoney(Double intentpayMoney) {
        this.intentpayMoney = intentpayMoney;
    }

    public Double getIntentincomeMoney() {
        return this.intentincomeMoney;
    }
    
    public void setIntentincomeMoney(Double intentincomeMoney) {
        this.intentincomeMoney = intentincomeMoney;
    }

    public java.util.Date getFactDate() {
        return this.factDate;
    }
    
    public void setFactDate(java.util.Date factDate) {
        this.factDate = factDate;
    }

    public Long getFundDetailId() {
        return this.fundDetailId;
    }
    
    public void setFundDetailId(Long fundDetailId) {
        this.fundDetailId = fundDetailId;
    }

    public Long getFundIntentId() {
        return this.fundIntentId;
    }
    
    public void setFundIntentId(Long fundIntentId) {
        this.fundIntentId = fundIntentId;
    }

    public Long getFundQlideId() {
        return this.fundQlideId;
    }
    
    public void setFundQlideId(Long fundQlideId) {
        this.fundQlideId = fundQlideId;
    }

    public Long getOverdueNum() {
        return this.overdueNum;
    }
    
    public void setOverdueNum(Long overdueNum) {
        this.overdueNum = overdueNum;
    }

    public Double getOverdueAccrual() {
        return this.overdueAccrual;
    }
    
    public void setOverdueAccrual(Double overdueAccrual) {
        this.overdueAccrual = overdueAccrual;
    }

    public java.util.Date getOperTime() {
        return this.operTime;
    }
    
    public void setOperTime(java.util.Date operTime) {
        this.operTime = operTime;
    }

    public Double getAfterMoney() {
        return this.afterMoney;
    }
    
    public void setAfterMoney(Double afterMoney) {
        this.afterMoney = afterMoney;
    }

    public String getTransactionType() {
        return this.transactionType;
    }
    
    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getMyAccount() {
        return this.myAccount;
    }
    
    public void setMyAccount(String myAccount) {
        this.myAccount = myAccount;
    }

    public String getCurrency() {
        return this.currency;
    }
    
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getQlidepayMoney() {
        return this.qlidepayMoney;
    }
    
    public void setQlidepayMoney(Double qlidepayMoney) {
        this.qlidepayMoney = qlidepayMoney;
    }

    public Double getQlideincomeMoney() {
        return this.qlideincomeMoney;
    }
    
    public void setQlideincomeMoney(Double qlideincomeMoney) {
        this.qlideincomeMoney = qlideincomeMoney;
    }

    public String getIsCash() {
        return this.isCash;
    }
    
    public void setIsCash(String isCash) {
        this.isCash = isCash;
    }

    public String getBusinessType() {
        return this.businessType;
    }
    
    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getFundTypeName() {
        return this.fundTypeName;
    }
    
    public void setFundTypeName(String fundTypeName) {
        this.fundTypeName = fundTypeName;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof VFundDetail) ) return false;
		 VFundDetail castOther = ( VFundDetail ) other; 
         
		 return ( (this.getProjectName()==castOther.getProjectName()) || ( this.getProjectName()!=null && castOther.getProjectName()!=null && this.getProjectName().equals(castOther.getProjectName()) ) )
 && ( (this.getProjectNumber()==castOther.getProjectNumber()) || ( this.getProjectNumber()!=null && castOther.getProjectNumber()!=null && this.getProjectNumber().equals(castOther.getProjectNumber()) ) )
 && ( (this.getFunType()==castOther.getFunType()) || ( this.getFunType()!=null && castOther.getFunType()!=null && this.getFunType().equals(castOther.getFunType()) ) )
 && ( (this.getProjectId()==castOther.getProjectId()) || ( this.getProjectId()!=null && castOther.getProjectId()!=null && this.getProjectId().equals(castOther.getProjectId()) ) )
 && ( (this.getIntentDate()==castOther.getIntentDate()) || ( this.getIntentDate()!=null && castOther.getIntentDate()!=null && this.getIntentDate().equals(castOther.getIntentDate()) ) )
 && ( (this.getIntentpayMoney()==castOther.getIntentpayMoney()) || ( this.getIntentpayMoney()!=null && castOther.getIntentpayMoney()!=null && this.getIntentpayMoney().equals(castOther.getIntentpayMoney()) ) )
 && ( (this.getIntentincomeMoney()==castOther.getIntentincomeMoney()) || ( this.getIntentincomeMoney()!=null && castOther.getIntentincomeMoney()!=null && this.getIntentincomeMoney().equals(castOther.getIntentincomeMoney()) ) )
 && ( (this.getFactDate()==castOther.getFactDate()) || ( this.getFactDate()!=null && castOther.getFactDate()!=null && this.getFactDate().equals(castOther.getFactDate()) ) )
 && ( (this.getFundDetailId()==castOther.getFundDetailId()) || ( this.getFundDetailId()!=null && castOther.getFundDetailId()!=null && this.getFundDetailId().equals(castOther.getFundDetailId()) ) )
 && ( (this.getFundIntentId()==castOther.getFundIntentId()) || ( this.getFundIntentId()!=null && castOther.getFundIntentId()!=null && this.getFundIntentId().equals(castOther.getFundIntentId()) ) )
 && ( (this.getFundQlideId()==castOther.getFundQlideId()) || ( this.getFundQlideId()!=null && castOther.getFundQlideId()!=null && this.getFundQlideId().equals(castOther.getFundQlideId()) ) )
 && ( (this.getOverdueNum()==castOther.getOverdueNum()) || ( this.getOverdueNum()!=null && castOther.getOverdueNum()!=null && this.getOverdueNum().equals(castOther.getOverdueNum()) ) )
 && ( (this.getOverdueAccrual()==castOther.getOverdueAccrual()) || ( this.getOverdueAccrual()!=null && castOther.getOverdueAccrual()!=null && this.getOverdueAccrual().equals(castOther.getOverdueAccrual()) ) )
 && ( (this.getOperTime()==castOther.getOperTime()) || ( this.getOperTime()!=null && castOther.getOperTime()!=null && this.getOperTime().equals(castOther.getOperTime()) ) )
 && ( (this.getAfterMoney()==castOther.getAfterMoney()) || ( this.getAfterMoney()!=null && castOther.getAfterMoney()!=null && this.getAfterMoney().equals(castOther.getAfterMoney()) ) )
 && ( (this.getTransactionType()==castOther.getTransactionType()) || ( this.getTransactionType()!=null && castOther.getTransactionType()!=null && this.getTransactionType().equals(castOther.getTransactionType()) ) )
 && ( (this.getMyAccount()==castOther.getMyAccount()) || ( this.getMyAccount()!=null && castOther.getMyAccount()!=null && this.getMyAccount().equals(castOther.getMyAccount()) ) )
 && ( (this.getCurrency()==castOther.getCurrency()) || ( this.getCurrency()!=null && castOther.getCurrency()!=null && this.getCurrency().equals(castOther.getCurrency()) ) )
 && ( (this.getQlidepayMoney()==castOther.getQlidepayMoney()) || ( this.getQlidepayMoney()!=null && castOther.getQlidepayMoney()!=null && this.getQlidepayMoney().equals(castOther.getQlidepayMoney()) ) )
 && ( (this.getQlideincomeMoney()==castOther.getQlideincomeMoney()) || ( this.getQlideincomeMoney()!=null && castOther.getQlideincomeMoney()!=null && this.getQlideincomeMoney().equals(castOther.getQlideincomeMoney()) ) )
 && ( (this.getIsCash()==castOther.getIsCash()) || ( this.getIsCash()!=null && castOther.getIsCash()!=null && this.getIsCash().equals(castOther.getIsCash()) ) )
 && ( (this.getBusinessType()==castOther.getBusinessType()) || ( this.getBusinessType()!=null && castOther.getBusinessType()!=null && this.getBusinessType().equals(castOther.getBusinessType()) ) )
 && ( (this.getFundTypeName()==castOther.getFundTypeName()) || ( this.getFundTypeName()!=null && castOther.getFundTypeName()!=null && this.getFundTypeName().equals(castOther.getFundTypeName()) ) )
		 && ( (this.getQlidetransactionType()==castOther.getQlidetransactionType()) || ( this.getQlidetransactionType()!=null && castOther.getQlidetransactionType()!=null && this.getQlidetransactionType().equals(castOther.getQlidetransactionType()) ) )
		&& ( (this.getIscancel()==castOther.getIscancel()) || ( this.getBusinessType()!=null && castOther.getIscancel()!=null && this.getIscancel().equals(castOther.getIscancel()) ) )
		 && ( (this.getCancelremark()==castOther.getCancelremark()) || ( this.getCancelremark()!=null && castOther.getCancelremark()!=null && this.getCancelremark().equals(castOther.getCancelremark()) ) )
				 && ( (this.getCheckuser()==castOther.getCheckuser()) || ( this.getCheckuser()!=null && castOther.getCheckuser()!=null && this.getCheckuser().equals(castOther.getCheckuser()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getProjectName() == null ? 0 : this.getProjectName().hashCode() );
         result = 37 * result + ( getProjectNumber() == null ? 0 : this.getProjectNumber().hashCode() );
         result = 37 * result + ( getFunType() == null ? 0 : this.getFunType().hashCode() );
         result = 37 * result + ( getProjectId() == null ? 0 : this.getProjectId().hashCode() );
         result = 37 * result + ( getIntentDate() == null ? 0 : this.getIntentDate().hashCode() );
         result = 37 * result + ( getIntentpayMoney() == null ? 0 : this.getIntentpayMoney().hashCode() );
         result = 37 * result + ( getIntentincomeMoney() == null ? 0 : this.getIntentincomeMoney().hashCode() );
         result = 37 * result + ( getFactDate() == null ? 0 : this.getFactDate().hashCode() );
         result = 37 * result + ( getFundDetailId() == null ? 0 : this.getFundDetailId().hashCode() );
         result = 37 * result + ( getFundIntentId() == null ? 0 : this.getFundIntentId().hashCode() );
         result = 37 * result + ( getFundQlideId() == null ? 0 : this.getFundQlideId().hashCode() );
         result = 37 * result + ( getOverdueNum() == null ? 0 : this.getOverdueNum().hashCode() );
         result = 37 * result + ( getOverdueAccrual() == null ? 0 : this.getOverdueAccrual().hashCode() );
         result = 37 * result + ( getOperTime() == null ? 0 : this.getOperTime().hashCode() );
         result = 37 * result + ( getAfterMoney() == null ? 0 : this.getAfterMoney().hashCode() );
         result = 37 * result + ( getTransactionType() == null ? 0 : this.getTransactionType().hashCode() );
         result = 37 * result + ( getMyAccount() == null ? 0 : this.getMyAccount().hashCode() );
         result = 37 * result + ( getCurrency() == null ? 0 : this.getCurrency().hashCode() );
         result = 37 * result + ( getQlidepayMoney() == null ? 0 : this.getQlidepayMoney().hashCode() );
         result = 37 * result + ( getQlideincomeMoney() == null ? 0 : this.getQlideincomeMoney().hashCode() );
         result = 37 * result + ( getIsCash() == null ? 0 : this.getIsCash().hashCode() );
         result = 37 * result + ( getBusinessType() == null ? 0 : this.getBusinessType().hashCode() );
         result = 37 * result + ( getFundTypeName() == null ? 0 : this.getFundTypeName().hashCode() );
         result = 37 * result + ( getQlidetransactionType() == null ? 0 : getQlidetransactionType().hashCode() );
         result = 37 * result + ( getIscancel() == null ? 0 : this.getIscancel().hashCode() );
         result = 37 * result + ( getCancelremark() == null ? 0 : this.getCancelremark().hashCode() );
         result = 37 * result + ( getCheckuser() == null ? 0 : this.getCheckuser().hashCode() );
         return result;
   }   





}