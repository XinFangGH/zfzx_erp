package com.zhiwei.credit.action.creditFlow.finance;
import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
public class MyComparecharge implements Comparator<Object>{  
      
    public int compare(Object o0, Object o1) {  
    	SlActualToCharge s1= (SlActualToCharge) o0;  
    	SlActualToCharge s2 = (SlActualToCharge) o1; 
    	
        if (CompareDate.compare_date(s1.getIntentDate(),s2.getIntentDate())==-1) {  
            return 1; // 第一个大于第二个  
        } else if (CompareDate.compare_date(s1.getIntentDate(),s2.getIntentDate())==0) {  
            return 0;// 等于
        } else {  
            return -1; //   第一个小于第二个  
        }  
    }

    
} 
