package com.zhiwei.credit.action.creditFlow.financingAgency.manageMoney;
import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.finance.SlFundDetail;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlManageMoneyPlanBuyinfo;
public class MyCompareOrder implements Comparator<Object>{  
      //降序
    public int compare(Object o0, Object o1) {  
    	PlManageMoneyPlanBuyinfo s1= (PlManageMoneyPlanBuyinfo) o0;  
    	PlManageMoneyPlanBuyinfo s2 = (PlManageMoneyPlanBuyinfo) o1; 
    	
        if (s1.getOptimalDayRate().compareTo(s2.getOptimalDayRate())==1) {  
            return -1; 
        } else if (s1.getOptimalDayRate().compareTo(s2.getOptimalDayRate())==0) {  
            return 0;
        } else {  
            return 1; 
        }  
    }

    
} 
