package com.zhiwei.credit.core.creditUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class POIdataBaseMysql {

	public static Connection  conn = null; 
	public static PreparedStatement stmt = null; 
	public static boolean connectDB2(String sql) { 
		try
		{ 
		    Class.forName( "com.mysql.jdbc.Driver"); 
		    String url = "jdbc:mysql://localhost:3306/cs_credit?autoReconnect=true&useUnicode=true&characterEncoding=GBK"; 
		    conn = DriverManager.getConnection(url, "root", "admin"); 
		    conn.setAutoCommit(false);
		    stmt = (PreparedStatement)conn.prepareStatement(sql); 
		} 
	    catch (ClassNotFoundException   cnfex)   { 
	    	//System.err.println( "装载JDBC驱动程序失败。 "); 
	    	cnfex.printStackTrace(); 
	    	return false; 
	    } 
	    catch (SQLException   sqlex)   { 
	    	//System.err.println( "无法连接数据库 "); 
	    	sqlex.printStackTrace(); 
	    	return false; 
	    } 
	    	return true; 
		} 

	public static boolean readExcelToDB2(String path) {
		try {
	          // List<String[]> 中的元素 行数组String[]为excel中的每一行 
		     List<String[]> list = new ArrayList<String[]>();
		     File target = new File(path);
		     InputStream is = new FileInputStream(target);
		     HSSFWorkbook hwk = new HSSFWorkbook(is);// 将is流实例到 一个excel流里
		     HSSFSheet sh = hwk.getSheetAt(0);// 得到book第一个工作薄sheet
		     int rows = sh.getLastRowNum()+1 - sh.getFirstRowNum();  // 总行数
		     for(int i=0; i<rows; i++){
		        HSSFRow row = sh.getRow(i);
		        int cols = row.getLastCellNum()+1 - row.getFirstCellNum();  // 该行的总列数`
		        String[] str = new String[cols - 1];   // 用来存放该行每一列的值
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
			            		//单元格是字符串
		            			//int a = cell.toString().indexOf(".0");
		            			str[j] = cell.toString();
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
		     list.remove(st);
		     String sql = "insert into cs_enterprise(" ;
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
			    	 stmt.setString(1, sd[0]);
		    		 stmt.setString(2, sd[1]);
		    		 stmt.setString(3, sd[2]);
		    		 stmt.setString(4, sd[3]);
		    		 if(sd[4] == null){
		    			 stmt.setString(5, ""); 
		    		 }else{
		    			 stmt.setString(5, sd[4]);
		    		 }if(sd[5] == null){
		    			 stmt.setString(6, ""); 
		    		 }else{
		    			 stmt.setString(6, sd[5]);
		    		 }if(sd[6] == null){
		    			 stmt.setString(7, "");
		    		 }else{
		    			 stmt.setString(7, sd[6].substring(0,sd[6].indexOf(".0")));
		    		 }if(sd[7] == null){
		    			 stmt.setString(8, "");
		    		 }else{
		    			 stmt.setString(8, sd[7]);
		    		 }
		    		 if(sd[8].indexOf(".0")<1){
		    			 stmt.setString(9, sd[8]);
		    		 }else{
		    			 stmt.setString(9, sd[8].substring(0,sd[8].indexOf(".0")));
		    		 }
		    		 stmt.setString(10, sd[9]);
		    		 stmt.setString(11, sd[10]);
		    		 stmt.setString(12, sd[11]);
		    		 stmt.setString(13, sd[12]);
		    		 stmt.setString(14, sd[13]);
		    		 stmt.setString(15, sd[14]);
		    		 //stmt.setDouble(16, Double.parseDouble(sd[16]));
		    		 stmt.setString(16, sd[15]);
		    		 stmt.setString(17, sd[16]);
		    		 stmt.setString(18, sd[17]);
		    		 stmt.setString(19, sd[18]);
		    		 stmt.setString(20, sd[19]);
		    		 //stmt.setInt(21, Integer.parseInt(sd[21]));
		    		 stmt.setString(21, sd[20]);
		    		 stmt.setString(22, sd[21]);
		    		 stmt.setString(23, sd[22]);
		    		 stmt.setString(24, sd[23]);
		    		 //stmt.setInt(25, Integer.parseInt(sd[25]));
		    		 stmt.setString(25, sd[24]);
		    		 stmt.setString(26, sd[25]);
		    		 stmt.setString(27, sd[26]);
		    		 stmt.setString(28, sd[27]);
		    		 stmt.setString(29, sd[28]);
		    		 stmt.setString(30, sd[29]);
		    		 stmt.setString(31, sd[30]);
		    		 stmt.setString(32, sd[31]);
		    		 stmt.setString(33, sd[32]);
		    		 //stmt.setInt(34, Integer.parseInt(sd[34]));
		    		 stmt.setString(34, sd[33]);
		    		 stmt.setString(35, sd[34]);
		    		 stmt.setString(36, sd[35]);
		    		 stmt.setString(37, sd[36]);
		    		 stmt.setString(38, sd[37]);
		    		 stmt.setString(39, sd[38]);
		    		 stmt.setString(40, sd[39]);
		    		 stmt.setString(41, sd[40]);
		    		 stmt.execute();
			     }
		    	 conn.commit();
		     }  
		}catch (Exception e){
			e.printStackTrace() ;
			return false ;
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return false ;
			}
		}
		return true ;
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
		//POIdataBaseMysql.readExcelToDB2();
		//insert into cs_enterprise(id,controlpersonid,legalpersonid,linkmampersonid,enterprisename,shortname,organizecode,ownership,tradetype,area,cciaa,businessterm,managescope,registerdate,capitalkind,registermoney,address,factaddress,managecity,gslname,gslexamine,gslexaminedate,taxname,taxnum,taxexamine,website,changer,changedate,lkmduty,taxexaminedate,enjoytax,dstaxname,dstaxnum,dstaxexamine,dsexaminedate,enjoyds,creater,createdate,telephone,fax,receivemail)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)
	}
}
