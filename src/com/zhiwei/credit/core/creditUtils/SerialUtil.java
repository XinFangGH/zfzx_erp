package com.zhiwei.credit.core.creditUtils;

import java.util.Random;

/**
 * @funtion 关于序列号的 工具类
 * @author credit004
 *
 */
public class SerialUtil {
	
	/**
	 * @function 把number 变成 length位 的序号【0001、02456等】	如length小于number的位数 则返回number
	 * @param number
	 * @param length
	 * @return
	 */
	public static String getAppointedLengthNumber(int number, int length){
		
		String strNumber = String.valueOf(number);
		
		if(strNumber.length() >= length){
			return strNumber;
		}
		
		int sub = length - strNumber.length();
		
		for(int i=0; i<sub;i++){
			strNumber = "0"+strNumber;
		}
		
		return strNumber;
	}
	
	/**
	 * @funciton 得到整型随机数
	 * @param limit 随机数 0-limit
	 * @return
	 */
	public static int getIntRandom(int limit){
		Random r = new Random();
		
		return r.nextInt(limit);
	}
	
}
