package com.zhiwei.credit.action.creditFlow.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.sql.DataSource;


import org.apache.poi.hssf.usermodel.*;

import com.zhiwei.core.util.AppUtil;

public class testexeclmysql{
	public static Connection  conn = null; 
	public static PreparedStatement stmt = null; 
	public static boolean connectDB2(String sql) { 
		try
		{ 
//		    Class.forName( "com.mysql.jdbc.Driver"); 
//		    String url = "jdbc:mysql://192.168.1.103:3306/jsoft?autoReconnect=true&useUnicode=true&characterEncoding=GBK"; 
//		    conn = DriverManager.getConnection(url, "root", "yuseen20110401"); 
	        
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
	         conn = dataSource.getConnection();
	         conn.setAutoCommit(false);
		    stmt = (PreparedStatement)conn.prepareStatement(sql); 
		} 
//	    catch (ClassNotFoundException   cnfex)   { 
//	    	//System.err.println( "装载JDBC驱动程序失败。 "); 
//	    	cnfex.printStackTrace(); 
//	    	return false; 
//	    } 
	    catch (SQLException   sqlex)   { 
	    	//System.err.println( "无法连接数据库 "); 
	    	sqlex.printStackTrace(); 
	    	return false; 
	    } 
	    	return true; 
		} 

	public static String readExcelToDB2(String path) {
		int rows;
		try {
	          // List<String[]> 中的元素 行数组String[]为excel中的每一行 
		     List<String[]> list = new ArrayList<String[]>();
		     File target = new File(path);
		     InputStream is = new FileInputStream(target);
		     HSSFWorkbook hwk = new HSSFWorkbook(is);// 将is流实例到 一个excel流里
		     HSSFSheet sh = hwk.getSheetAt(0);// 得到book第一个工作薄sheet
		     rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();  // 总行数
		     HSSFRow row0 = sh.getRow(0);
		        int cols = row0.getLastCellNum()+1 - row0.getFirstCellNum();  // 该行的总列数`
		     for(int i=0; i<rows; i++){
		        HSSFRow row = sh.getRow(i);
		        String[] str = new String[cols];   // 用来存放该行每一列的值
		        for (int j = 0; j < cols - 1; j++) {
		            if(i == 0){
		            	Object col = row.getCell((short)j);
			             if(null == col){
			            	 str[j] = null ;
			            	
			             }else
			            	 str[j] = col.toString();
		            }else{
		            	HSSFCell cell  = row.getCell((short)j);
		            	if(null == cell){
		            		str[j] = null ;
		            		 return "第"+(i+1)+"行,"+(j+1)+"列不能为空";
		            		
		            	}else{
		            		if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING ){
			            		//单元格是字符串
		            			//int a = cell.toString().indexOf(".0");
		            			str[j] = cell.toString();
		            			
		            			if(j==2){boolean a=true; a=testmoney(str[j].trim()); if(a==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		            			else if(j==4){boolean b=true; b=testdate(str[j].trim());if(b==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
		            			else{boolean c=true; c=testaccount(str[j].trim());if(c==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
			            	}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			            		//单元格是数字
			            		//System.err.println(cell);
			            		str[j] = cell.toString() ;
			            	}
		            	}
		            }
		         }
		        list.add(str);
		      }
		     String[] st = list.get(0);
		     st[5]="notMoney";
		     list.remove(st);
		     String sql = "insert into sl_fund_qlide(" ;
		     StringBuffer sbuf = new StringBuffer(sql);
		     for(int j = 0 ; j < st.length ; j ++){
		    	 if(j != st.length - 1){
		    		 sbuf.append(st[j] + ",");
		    	 }else
		    		 sbuf.append(st[j] + ")");
		     }
		     String sqlhj = sbuf.toString() ;
		     sbuf.append("values");
		     sbuf.append("(") ;
		     for(int s = 0 ; s < st.length ; s ++){
		    	 if(s != st.length - 1){
		    		 sbuf.append("?,");
		    	 }else
		    		 sbuf.append("?)");
		     }
		     String sqlmo = sbuf.toString() ;   //
		     if(connectDB2(sqlmo)){
		    	 for(int l = 0 ;l < list.size() ; l ++){
			    	 String[] sd = list.get(l) ;
			    	/* stmt.setInt(1, Integer.parseInt(sd[1]));
		    		 stmt.setInt(2, Integer.parseInt(sd[2]));
		    		 stmt.setInt(3, Integer.parseInt(sd[3]));
		    		 stmt.setInt(4, Integer.parseInt(sd[4]));*/
                 //  	 stmt.setString(1, sd[0].substring(0,sd[0].indexOf(".0")));
//			    	 stmt.setString(2, sd[1].substring(0,sd[1].indexOf(".0")));
//		    		 stmt.setString(3, sd[2]);
//	    		 stmt.setString(4, sd[3].substring(0,sd[3].indexOf(".0")));
			            stmt.setString(1, sd[0]);
		    		 stmt.setString(2, sd[1]);
		    		 stmt.setString(3, sd[2]);
		    		 stmt.setString(4, sd[3]);
		    		 stmt.setString(5, sd[4]);
		    		 stmt.setString(6, sd[2]);
		    		 stmt.execute();
			     }
		    	 conn.commit();
		     }  
		}catch (Exception e){
			e.printStackTrace() ;
			return "重复添加记录" ;
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
		return "上传成功！"+"共"+(rows-1)+"记录" ;
	}
	
	public static List dfd(String path ,String tableName){
		List<String[]> list = new ArrayList<String[]>();
		File target = new File(path);
		try {
			InputStream is = new FileInputStream(target);
			HSSFWorkbook hwk = new HSSFWorkbook(is);
		     HSSFSheet sh = hwk.getSheetAt(0);
		     int rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();
		     for(int i=0; i<rows; i++){
		        HSSFRow row = sh.getRow(i);
		        int cols = row.getLastCellNum()+1 - row.getFirstCellNum();
		        String[] str = new String[cols - 1];
		        for (int j = 0; j < cols - 1; j++) {
		            if(i == 0){
		            	Object col = row.getCell((short)j);
			             if(null == col){
			            	 str[j] = null ;
			             }else
			            	 str[j] = col.toString();
		            }else{
		            	HSSFCell cell  = row.getCell((short)j);
		            	if(null == cell){
		            		str[j] = null ;
		            	}else{
		            		if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING ){
		            			str[j] = cell.toString();
			            	}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
			            		str[j] = cell.toString() ;
			            	}
		            	}
		            }
		         }
		        list.add(str);
		      }
		     String[] st = list.get(0);
		     list.remove(st);
		     String sql = "insert into "+tableName+"(" ;
		     StringBuffer sbuf = new StringBuffer(sql);
		     for(int j = 0 ; j < st.length ; j ++){
		    	 if(j != st.length - 1){
		    		 sbuf.append(st[j] + ",");
		    	 }else
		    		 sbuf.append(st[j] + ")");
		     }
		     sbuf.append("values");
		     sbuf.append("(") ;
		     for(int s = 0 ; s < st.length ; s ++){
		    	 if(s != st.length - 1){
		    		 sbuf.append("?,");
		    	 }else
		    		 sbuf.append("?)");
		     }
		     String sqlmo = sbuf.toString() ; 
		}catch (Exception e){
			e.printStackTrace() ;
			return null ;
		}
		return list ;
	}
	public static void main(String[] args) {
	
		Pattern p =	Pattern.compile("[0-9]*");
		Matcher m=p.matcher("2011-11-0");
	
	}
	
  public static boolean testdate(String date){
	  Pattern p =	Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
	 // Pattern p =	Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
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
  }     

