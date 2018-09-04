package com.zhiwei.credit.action.creditFlow.finance;
import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.finance.SlChargeDetail;
public class MyComparechargedetail implements Comparator<Object>{  
      
    public int compare(Object o0, Object o1) {  
    	SlChargeDetail s1= (SlChargeDetail) o0;  
    	SlChargeDetail s2 = (SlChargeDetail) o1; 
    	
        if (CompareDate.compare_date(s1.getSlFundQlide().getFactDate(),s2.getSlFundQlide().getFactDate())==-1) {  
            return 1; // 第一个大于第二个  
        } else if (CompareDate.compare_date(s1.getSlFundQlide().getFactDate(),s2.getSlFundQlide().getFactDate())==0) {  
            return 0;// 等于
        } else {  
            return -1; //   第一个小于第二个  
        }  
    }

    
} 
