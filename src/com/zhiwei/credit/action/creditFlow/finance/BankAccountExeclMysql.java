package com.zhiwei.credit.action.creditFlow.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.sql.DataSource;


import org.apache.poi.hssf.usermodel.*;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;

public class BankAccountExeclMysql{
	public static Connection  conn = null; 
	public static PreparedStatement stmt = null; 
	public static boolean connectDB2(String sql) { 
		try
		{ 
	        
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
	         conn = dataSource.getConnection();
	         conn.setAutoCommit(false);
		    stmt = (PreparedStatement)conn.prepareStatement(sql); 
		} 
	    catch (SQLException   sqlex)   { 
	    	sqlex.printStackTrace(); 
	    	return false; 
	    } 
	    	return true; 
		} 

	public static String readExcelToDB2(String path,List<SlBankAccount> listaccount) {
		int  sumrows=0;
		int numbersheet=0;
		try {
	          // List<String[]> 中的元素 行数组String[]为excel中的每一行 
		     List<String[]> list = new ArrayList<String[]>();
		     File target = new File(path);
		     InputStream is = new FileInputStream(target);
		     HSSFWorkbook hwk = new HSSFWorkbook(is);// 将is流实例到 一个excel流里
		     numbersheet= hwk.getNumberOfSheets();
		    
	 for(int t=0;t<numbersheet;t++){
		     HSSFSheet sh = hwk.getSheetAt(t);// 得到book第一个工作薄sheet
		    int rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();  // 总行数
		    sumrows =sumrows+rows;
		     HSSFRow row0 = sh.getRow(0);
		//     if(row0==null)return "没传数据";
		     int cols = row0.getLastCellNum()+1 - row0.getFirstCellNum();  // 该行的总列数`
		     for(int i=1; i<rows; i++){
		        HSSFRow row = sh.getRow(i);
		        String[] str = new String[cols];   // 用来存放该行每一列的值
		        for (int j = 0; j < cols - 1; j++) {
		            if(i == 0){
		            }else{
		            	HSSFCell cell  = row.getCell((short)j);
		            	if(null == cell){
		            		str[j] = null ;
		            		if(j==0 || j==1|| j==3 || j==4 || j==5 || j==6){
		            		 return "第"+(i+1)+"行,"+(j+1)+"列不能为空";
		            		}
		            	}else{
		            		if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING ){
			            		//单元格是字符串
		            			//int a = cell.toString().indexOf(".0");
		            			str[j] = cell.toString();
		            			
		            			if(j==4){
		            				int flag=0;
		            				for(SlBankAccount a:listaccount){
		            				System.out.print("====="+str[j].trim()+"=="+a.getAccount()+"==");
		            				if(	str[j].trim().equals(a.getAccount()))
		            					flag=1;
		            				}
		            				if(flag==1){return "第"+(i+1)+"行,我方账户重复添加";}
		            			}
		            			
		            			if(j==5){boolean a=true; a=testmoney(str[j].trim()); if(a==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		            			else if(j==6){boolean b=true; b=testdate(str[j].trim());if(b==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		          //  			else if(j==3){boolean d=true; d=testtype(str[j].trim());if(d==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		            			else if(j==4 ){boolean c=true; c=testaccount(str[j].trim());if(c==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		            			System.out.print("--1---"+str[j]);
			            	}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			            		//单元格是数字
			            		//System.err.println(cell);
			            		str[j] = cell.toString() ;
			            		System.out.print("--2---"+str[j]);
			            	}
		            	}
		            }
		          
		         }
		        list.add(str);
		      }
		     
		}
		     String[] st = list.get(0);
		     String sql = "insert into sl_bank_account(openType,accountType,name,account,openBankId,rawMoney,recordTime,finalDate,finalMoney)values(?,?,?,?,?,?,?,?,?)" ;
		     StringBuffer sbuf = new StringBuffer(sql);
		     String sqlhj = sbuf.toString() ;
//		     sbuf.append("values");
//		     sbuf.append("(") ;
//		     for(int s = 1 ; s < st.length ; s ++){
//		    	 if(s != st.length - 1){
//		    		 sbuf.append("?,");
//		    	 }else
//		    		 sbuf.append("?)");
//		     }
		     String sqlmo = sbuf.toString() ; 
		     System.out.print(sqlmo);//
		     if(connectDB2(sqlmo)){
		    	 for(int l = 0 ;l < list.size() ; l ++){
			    	 String[] sd = list.get(l) ;

		    		
		    		 if(sd[1]=="基本户"){
		    			 sd[11]="1";
		    		 }else if(sd[1]=="一般户"){
		    			 sd[1]="2";
		    		 }else{
		    			 sd[1]="0";
		    			 
		    		 }
		    		 if(sd[0]=="个人"){
		    			 sd[0]="0";
		    			 sd[1]="0";
		    		 }else{
		    			 sd[0]="1";
		    		 }
			          stmt.setString(1, sd[0]);
		    		 stmt.setString(2, sd[1]);
		    		 stmt.setString(3, sd[3]);
		    		 stmt.setString(4, sd[4]);
		    		 stmt.setString(5, "4842");
		    		 stmt.setString(6, sd[5]);
		    		 stmt.setString(7, sd[6]);
		    		 stmt.setString(8, sd[6]);
		    		 stmt.setString(9, sd[5]);
		    		 stmt.execute();
			     }
		    	 conn.commit();
		     }  
		}catch (Exception e){
			e.printStackTrace() ;
			return "系统出错" ;
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return "系统出错" ;
			}
		}
		return "上传成功！"+"共"+(sumrows-numbersheet)+"记录" ;
	}
	
	
	public static void main(String[] args) {
	
		Pattern p =	Pattern.compile("[0-9]*");
		Matcher m=p.matcher("2011-11-0");
		System.out.print(m.matches());
	
	}
	
  public static boolean testdate(String date){
	  Pattern p =	Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
	//  Pattern p =	Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		Matcher m=p.matcher(date);
	  return m.matches();
  }
  public  static boolean testmoney(String money){
	
		Pattern p =	Pattern.compile("[0-9]{0,8}.[0-9]{0,2}");
			Matcher m=p.matcher(money);
	  return m.matches();
  } 
  public  static boolean testaccount(String account){
		
		Pattern p =	Pattern.compile("[0-9]*");
			Matcher m=p.matcher(account);
	  return m.matches();
} 
  public  static boolean testtype(String type){
		
		Pattern p =	Pattern.compile("[0-1]{1}");
			Matcher m=p.matcher(type);
	  return m.matches();
} 
  }     

