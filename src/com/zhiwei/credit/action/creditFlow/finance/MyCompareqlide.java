package com.zhiwei.credit.action.creditFlow.finance;
import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;
public class MyCompareqlide implements Comparator<Object>{  
      
    public int compare(Object o0, Object o1) {  
    	SlFundQlide s1= (SlFundQlide) o0;  
    	SlFundQlide s2 = (SlFundQlide) o1; 
    	
        if (CompareDate.compare_date(s1.getFactDate(),s2.getFactDate())==-1) {  
            return 1; // 第一个大于第二个  
        } else if (CompareDate.compare_date(s1.getFactDate(),s2.getFactDate())==0) {  
            return 0;// 等于
        } else {  
            return -1; //   第一个小于第二个  
        }  
    }

    
} 
