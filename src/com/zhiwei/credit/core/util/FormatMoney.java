package com.zhiwei.credit.core.util;

import java.math.BigDecimal;
import java.util.Hashtable;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatMoney {



	/**
	     　　* 用于存储整数部分
	     　　*/
	    private String integerPart;
	    
	   /**
	    　　 * 用于存储小数部分
	    　　 */
	    private String floatPart;
	    
	    /**
	    　　 * 用于存储0-9大写的哈希表
	     　　*/
	    private static final Map ZerotoNineHt;
	    
	    /**
	     　　* 用于存储十百千大写的哈希表
	    　　 */
	    private static final Map thHuTenHt;
	    
	    
	    /**
	     　　* 用于存储萬億兆大写的哈希表
	    　　 */
	    private static final Map wanYiZhaoHt;
	    
	    static
	    {
	     ZerotoNineHt=new Hashtable();
	        
	      ZerotoNineHt.put("0", "零");
	     ZerotoNineHt.put("1", "壹");
	     ZerotoNineHt.put("2", "贰");
	     ZerotoNineHt.put("3", "叁");
	     ZerotoNineHt.put("4", "肆");
	     ZerotoNineHt.put("5", "伍");
	     ZerotoNineHt.put("6", "陆");
	     ZerotoNineHt.put("7", "柒");
	     ZerotoNineHt.put("8", "捌");
	     ZerotoNineHt.put("9", "玖");
	        
	     thHuTenHt=new Hashtable();
	     thHuTenHt.put(0, "");
	     thHuTenHt.put(1, "拾");
	     thHuTenHt.put(2, "佰");
	     thHuTenHt.put(3, "仟");
	        
	     wanYiZhaoHt=new Hashtable();
	     wanYiZhaoHt.put(0, "");
	     wanYiZhaoHt.put(1, "万");
	     wanYiZhaoHt.put(2, "亿");
	     wanYiZhaoHt.put(3, "兆");
	    }
	    
	    
	    private static String getWanYiZhao(int level)
	    {
	    String retval="";
	        
	        do
	        {
	            retval+=wanYiZhaoHt.get(level % 4);
	            level-=3;
	        }while(level>3);
	        return retval;
	    }
	    
	    /**
	     * 构造函数
	     * @param number
	     * @throws NumberFormatException
	     */
	    public FormatMoney(float number) throws NumberFormatException
	    {
	        this(String.valueOf(number));
	    }
	    
	    /**
	     * 构造函数
	     * @param number
	     * @throws NumberFormatException
	     */
	    public FormatMoney(double number) throws NumberFormatException
	    {
	        this(String.valueOf(number));
	    }
	    
	    /**
	     * 构造函数
	     * @param number
	     * @throws NumberFormatException
	     */
	    public FormatMoney(int number) throws NumberFormatException
	    {
	        this(String.valueOf(number));
	    }
	    
	    /**
	     * 构造函数
	     * @param number
	     * @throws NumberFormatException
	     */
	    public FormatMoney(long number) throws NumberFormatException
	    {
	        this(String.valueOf(number));
	    }
	    
	    /**
	     * 构造函数
	     * @param number
	     * @throws NumberFormatException
	     */
	    public FormatMoney(String number) throws NumberFormatException
	    {
	        String formalNumber=formatNumber(number);
	        
	        // 辟分以给整数部分和小数部分赋值
	        String[] arr=formalNumber.split("[.]");
	        if(arr.length==2)
	        {
	           // 有小数点
	            integerPart=arr[0];
	            floatPart=arr[1];
	       }
	        else
	        {
	            // 无小数点
	            integerPart=arr[0];
	        }
	    }
	    
	    public String toString()
	    {
	        String retval="";
	        
	        if(integerPart!=null)
	        {
	            retval+=parseIntegerPart();
	        }
	        
	        if(floatPart!=null&&!"0".equals(floatPart)&&!"00".equals(floatPart))
	        {
	            retval+=parseFloatPart();
	       }
	        else
	        {
//	            retval+="整";
	        	retval+="";//去掉整字
	        }
	        return retval;
	    }
	    
	    /**
	     * 得到整数部分的汉字大写表示
	     * @return
	     */
	    private String parseIntegerPart()
	    {
	        String retval="";
	        
	        // 将整数部分逆序，因为需要反向读取
	        String reverseIntegerPart="";
	        
	        for(int i=integerPart.length()-1;i>-1;i--)
	        {
	            reverseIntegerPart+=integerPart.charAt(i);
	        }
	        
	        // 将整数部分按四位分段
	        Pattern p = Pattern.compile("\\d{4}",Pattern.CASE_INSENSITIVE);

	        Matcher m = p.matcher(reverseIntegerPart);
	        StringBuffer sb = new StringBuffer();

	        boolean result = m.find();
	        while (result) 
	        {
	            // 每找到四位放一个逗号
	            m.appendReplacement(sb, m.group(0) + ",");
	            result = m.find();
	        }
	        m.appendTail(sb);
	        
	        // 按逗号劈分，得到四位分组数据的数组
	        String[] arr=sb.toString().split(",");        
	        
	        int j;
	        String str;
	        for(int i=arr.length-1;i>=0;i--)
	        {
	            String temp=arr[i];

	            // 阿拉伯数字转大写汉字加单位（千百十）
	            for(j=temp.length()-1;j>=0;j--)
	            {
	                str=String.valueOf(temp.charAt(j));
	                retval+=ZerotoNineHt.get(str).toString()+thHuTenHt.get(j);
	          }
	            
	            retval=retval.replaceAll("(零)($)", "$2");// 零在末尾则去掉
	            // 加单位（兆亿万）
	            retval+=getWanYiZhao(i);
	        }

	        // 零替换
	        retval=retval.replaceAll("(零[仟佰拾])", "零");
	        retval=retval.replaceAll("(零{2,})", "零");
	        retval=retval.replaceAll("(零)($)", "$2");// 零在末尾则去掉
	    
	        return retval;
	    }

	    
	    /**
	     * 得到小数部分的汉字大写表示
	     * @return
	     */
	    private String parseFloatPart()
	    {
	        String retval="点";
	        
	        for(int i=0;i<=floatPart.length()-1;i++)
	        {
	            String temp=String.valueOf(floatPart.charAt(i));
	            if(i==0){
	            	retval=ZerotoNineHt.get(temp)+"角";
	            }
	            if(i==1){
	            	retval+=ZerotoNineHt.get(temp)+"分";
	            }
	           
	        }
	        return retval;
	    }
	    
	    /**
	     * 对输入的字符串进行验证，如果不能转化为数字形式则抛出数字转化异常
	     * ，注意这是一个运行时异常(非检查型异常)，程序不用显式捕获
	     * @param number
	     * @throws NumberFormatException
	     */
	    private String formatNumber(String number) throws NumberFormatException
	    {        
	        return (new BigDecimal(number)).toString();        
	    }
}
