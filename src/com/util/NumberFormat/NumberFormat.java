package com.util.NumberFormat;

import java.math.BigDecimal;
import java.util.EnumMap;

/**
 * 
 * @author linyan
 *
 */
public class NumberFormat {
	
    // 1. 定义数字单位的枚举类型
    public enum UnitOfMeasurement {
       /**
        * 利用构造函数传参:万，百万，千万，亿，十亿，百亿，千亿    
        * 由于没有对应的英文单词表示
        */
       QIANYI(11),BAIIYI(10),YI(8),QIANWAN (7),BAIWAN (6),WAN (4),GEWEI(0);
 
       // 定义私有变量
       private int nCode ;
 
       // 构造函数，枚举类型只能为私有
       private UnitOfMeasurement( int _nCode) {
           this . nCode = _nCode;
       }
 
       @Override
       public String toString() {
           return String.valueOf ( this . nCode );
       }
    }
	
	/**
	 * 创建数据计量单位的枚举类型数据值
	 * @return
	 */
    private static  EnumMap<UnitOfMeasurement, String> creatEnumMap(String type) {
        // 1. 演示定义 EnumMap 对象， EnumMap 对象的构造函数需要参数传入 , 默认是 key 的类的类型
        EnumMap<UnitOfMeasurement, String> currEnumMap = new EnumMap<UnitOfMeasurement, String>(UnitOfMeasurement. class );
        currEnumMap.put(UnitOfMeasurement. GEWEI , type );
        currEnumMap.put(UnitOfMeasurement. WAN , "万" );
        currEnumMap.put(UnitOfMeasurement. BAIWAN , "百万 " );
        currEnumMap.put(UnitOfMeasurement. QIANWAN , "千万" );
        currEnumMap.put(UnitOfMeasurement. YI , "亿" );
        currEnumMap.put(UnitOfMeasurement. BAIIYI , "百亿 " );
        currEnumMap.put(UnitOfMeasurement. QIANYI , "千亿" );
        return currEnumMap;
     }
	
	
	/**
	 * 将BigDecimal 数据整理格式
	 * 计算方式万，百万，千万，亿，
	 * bigDecimal 最大值只支持到十亿
	 * @param number
	 * @return
	 */
	public static  String numberFormat(BigDecimal number,String type){
		String numberFormat="0";
		try{
			if(type!=null){
				numberFormat=numberFormat+type;
			}
			if(number!=null){
				 EnumMap<UnitOfMeasurement, String> currEnumMap =creatEnumMap(type);
				 for(UnitOfMeasurement aUnit : UnitOfMeasurement.values ()){
					 BigDecimal mu=(new BigDecimal(10)).pow(aUnit.nCode);//求幂值计算
					 if(number.compareTo(mu)>=0){
						    String numberString=number.divide(mu, 2, BigDecimal.ROUND_HALF_UP).toString();
						    System.out.println("numberString=="+numberString);
						    String[] ret=numberString.split("\\.");
						    if(ret[1].equals("00")){//去除小数位全部为0情况
						    	numberString=ret[0];
						    }else{
						    	if(ret[1].endsWith("0")){//去除只有末尾为0的情况
						    		numberString=numberString.substring(0, numberString.length()-1);
						    	}
						    }
							numberFormat=numberString+currEnumMap.get(aUnit);
							break;
					 }
				 }
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return numberFormat.trim();
	}
	
	
	
	
/* * test方法*/
  /* public static void main(String[] args ) {
      //元
	  String ss= numberFormat(new BigDecimal(210000),"元");
	  System.out.println("ss=="+ss);
	  //2万 1.9
	  String ss1= numberFormat(new BigDecimal(19999),"元");
	  System.out.println("ss=="+ss1);
	  //十万21
	  String ss2= numberFormat(new BigDecimal(210000),"元");
	  System.out.println("ss=="+ss2);
	  //百万2.21
	  String ss3= numberFormat(new BigDecimal(2210000),"元");
	  System.out.println("ss=="+ss3);
	  //千万1.89
	  String ss4= numberFormat(new BigDecimal(18900000),"元");
	  System.out.println("ss=="+ss4);
	  //亿
	  String ss5= numberFormat(new BigDecimal(189000000),"元");
	  System.out.println("ss=="+ss5);
	  //十亿
	  String ss6= numberFormat(new BigDecimal(1890000000),"元");
	  System.out.println("ss=="+ss6);
	  
	  
   }*/

}
