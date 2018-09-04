package com.zhiwei.credit.util;

import java.math.BigDecimal;

import com.zhiwei.credit.core.project.StatsPro;
import com.zhiwei.credit.model.creditFlow.financeProject.FlFinancingProject;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;

public class ProjectActionUtil {
	
	public ProjectActionUtil(){
		
	}
/**
 * 	
 * 小额贷款年化净利率
 */
	public  void getSmallloanMode(SlSmallloanProject slSmallloanProject){
		String operationType=slSmallloanProject.getOperationType();//业务品种 6574贷款项目，6575融资项目
    	String accrualTypeID="";
    	String payaccrualTyepID="";
    	if(slSmallloanProject.getAccrualtype()!=null && !slSmallloanProject.getAccrualtype().equals("")){ //计息方式
    		accrualTypeID=slSmallloanProject.getAccrualtype();
    	}
    	if(slSmallloanProject.getPayaccrualType()!=null && !slSmallloanProject.getPayaccrualType().equals("")){ //周期
    		payaccrualTyepID=slSmallloanProject.getPayaccrualType();
    	}
    	
    	Double accrual=0.0;
    	if(slSmallloanProject.getAccrual()!=null){
    		accrual= slSmallloanProject.getAccrual().doubleValue();
    	}
	
		
		double projectMoney=0;
		if(slSmallloanProject.getProjectMoney()!=null){
			projectMoney=slSmallloanProject.getProjectMoney().doubleValue();
		}
		String date1="";
		String date2="";
		if(slSmallloanProject.getStartDate()!=null){
			date1=slSmallloanProject.getStartDate().toLocaleString();
		}
		if(slSmallloanProject.getIntentDate()!=null){
			date2=slSmallloanProject.getIntentDate().toLocaleString();
		}
		double managementConsultingOfRate=0;
		if(slSmallloanProject.getManagementConsultingOfRate()!=null){
			managementConsultingOfRate=slSmallloanProject.getManagementConsultingOfRate().doubleValue();
		}
		double financeServiceOfRate=0;
		if(slSmallloanProject.getFinanceServiceOfRate()!=null){
			financeServiceOfRate=slSmallloanProject.getFinanceServiceOfRate().doubleValue();
		}
		double incomechargeMoney=0;
		if(slSmallloanProject.getIncomechargeMoney()!=null){
			incomechargeMoney=slSmallloanProject.getIncomechargeMoney().doubleValue();
		}
		double paychargeMoney=0;
		if(slSmallloanProject.getPaychargeMoney()!=null){
			paychargeMoney=slSmallloanProject.getPaychargeMoney().doubleValue();
		}
		double NetProfit=0;
		int dayOfEveryPeriod=1;
		if(slSmallloanProject.getDayOfEveryPeriod()!=null){
			dayOfEveryPeriod=slSmallloanProject.getDayOfEveryPeriod();
		}
			
			NetProfit=StatsPro.calculLoanNetProfit(accrualTypeID, accrual, projectMoney, managementConsultingOfRate, financeServiceOfRate, date1, date2,incomechargeMoney,paychargeMoney,payaccrualTyepID,dayOfEveryPeriod);
		
    	slSmallloanProject.setAnnualNetProfit(BigDecimal.valueOf(NetProfit));
   
	}
	/**
	 * 
	 * 融资年化净利率
	 */
	public  void getFinanceMode(FlFinancingProject flFinancingProject){
		String operationType=flFinancingProject.getOperationType();//业务品种 6574贷款项目，6575融资项目
    	String accrualTypeID="";
    	if(flFinancingProject.getAccrualtype()!=null && !flFinancingProject.getAccrualtype().equals("")){ //利率方式
    		accrualTypeID=flFinancingProject.getAccrualtype();
    	}
    	int accrualType=0;
    	if(accrualTypeID.equals("ontTimeAccrual")){
    		accrualType=1;
    	}
    	else if(accrualTypeID.equals("dayAccrual")){
    		accrualType=2;
    	}else if(accrualTypeID.equals("monthAccrual")){
    		accrualType=3;
    	}else if(accrualTypeID.equals("yearAccrual")){
    		accrualType=4;
    	}else if(accrualTypeID.equals("seasonAccrual")){
    		accrualType=5;
    	}else if(accrualTypeID.equals("halfYearAccrual")){
    		accrualType=6;
    	}
    	//计息方式  326-一次性利息 327-日利率 328月利率 331年利率
    	Double accrual=0.0;
    	if(flFinancingProject.getAccrual()!=null){
    		accrual= flFinancingProject.getAccrual().doubleValue();
    	}
	
		
		double projectMoney=0;
		if(flFinancingProject.getProjectMoney()!=null){
			projectMoney=flFinancingProject.getProjectMoney().doubleValue();
		}
		String date1="";
		String date2="";
		if(flFinancingProject.getStartDate()!=null){
			date1=flFinancingProject.getStartDate().toLocaleString();
		}
		if(flFinancingProject.getIntentDate()!=null){
			date2=flFinancingProject.getIntentDate().toLocaleString();
		}
		double managementConsultingOfRate=0;
		if(flFinancingProject.getManagementConsultingOfRate()!=null){
			managementConsultingOfRate=flFinancingProject.getManagementConsultingOfRate().doubleValue();
		}
		double financeServiceOfRate=0;
		if(flFinancingProject.getFinanceServiceOfRate()!=null){
			financeServiceOfRate=flFinancingProject.getFinanceServiceOfRate().doubleValue();
		}
		double incomechargeMoney=0;
		if(flFinancingProject.getIncomechargeMoney()!=null){
			incomechargeMoney=flFinancingProject.getIncomechargeMoney().doubleValue();
		}
		double paychargeMoney=0;
		if(flFinancingProject.getPaychargeMoney()!=null){
			paychargeMoney=flFinancingProject.getPaychargeMoney().doubleValue();
		}
		double NetProfit=0;
		if(null!=operationType && operationType.equals("FinancingBusiness"))   //小额贷款下 - 小额贷款项目
		{
			
		//	NetProfit=StatsPro.calculLoanNetProfit(accrualType, accrual, projectMoney, managementConsultingOfRate, financeServiceOfRate, date1, date2,incomechargeMoney,paychargeMoney);
		}
    	
		flFinancingProject.setAnnualNetProfit(BigDecimal.valueOf(NetProfit));
   
	}

}
