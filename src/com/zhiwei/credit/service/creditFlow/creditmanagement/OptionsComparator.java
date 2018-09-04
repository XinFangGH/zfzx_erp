package com.zhiwei.credit.service.creditFlow.creditmanagement;

import java.util.Comparator;

import com.zhiwei.credit.model.creditFlow.creditmanagement.Options;

public class OptionsComparator  implements Comparator{
	 public int compare(Object arg0, Object arg1) {
		 Options user0=(Options)arg0;
		 Options user1=(Options)arg1;
		/*System.out.println(user0.getComparableValue()+"===========");
		System.out.println(user0.getComparableValue());
		System.out.println((user0.getComparableValue()).compareTo(user0.getComparableValue()));*/
		   return (user0.getComparableValue()).compareTo(user1.getComparableValue());

		 }
}
