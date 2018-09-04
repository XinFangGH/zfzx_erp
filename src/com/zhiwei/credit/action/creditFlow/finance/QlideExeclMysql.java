package com.zhiwei.credit.action.creditFlow.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.finance.SlBankAccount;
import com.zhiwei.credit.service.creditFlow.customer.common.EnterpriseBankService;

public class QlideExeclMysql{
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

	public static String readExcelToDB2(String path,List<SlBankAccount> listaccount, EnterpriseBankService enterpriseBankService) {
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
		    	 int cols = row0.getLastCellNum()+1 - row0.getFirstCellNum();  // 该行的总列数`
		    	 for(int i=1; i<rows; i++){
		    		 HSSFRow row = sh.getRow(i);
		    		 String[] str = new String[cols];   // 用来存放该行每一列的值
		    		 for (int j = 0; j < cols - 1; j++) {
		    			 if(i == 0){
		    				 /*Object col = row.getCell((short)j);
				             if(null == col){
				            	 str[j] = null ;
				             }else{
				            	 str[j] = col.toString();
			    			 }*/
		    			 }else{
		    				 HSSFCell cell  = row.getCell((short)j);
		    				 if(null == cell){
		    					 str[j] = null ;
		    					 if(j==2 || j==6){
		    						 return "第"+(i+1)+"行,"+(j+1)+"列不能为空";
		    					 }
		    				 }else{
		    					 if(cell.getCellType()==HSSFCell.CELL_TYPE_STRING ){
		    						 //单元格是字符串
		    						 //int a = cell.toString().indexOf(".0");
		    						 str[j] = cell.toString();
		    						 if(j==2){
		    							 int flag=0;
		    							 for(SlBankAccount a:listaccount){
		    								 if(str[j].trim().replaceAll(" ","").equals(a.getAccount()))
		    									 flag=1;
		    							 }
		    							 if(flag==0){return "第"+(i+1)+"行,我方账号填写有误";}
		    						 }
			            			if(j==4 || j==5){boolean a=true; a=testmoney(str[j].trim()); if(a==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
			            			else if(j==6){boolean b=true; b=testdate(str[j].trim());if(b==false)return "第"+(i+1)+"行,"+(j+1)+"列格式不对";}
			            			else if(j==2 || j==9 ){
			            				boolean c=true; 
			            				c=testaccount(str[j].trim());
			            				if(!c){
			            					return "第"+(i+1)+"行,"+(j+1)+"列格式不对";
			            				}
			            			}
			            			if(j==9 && !"".equals(str[j].trim())){
			            				boolean flag=true;
			            				QueryFilter filter=new QueryFilter();
			            				filter.addFilter("Q_accountnum_S_EQ",str[j].trim());
			            				List<EnterpriseBank> e_list=enterpriseBankService.getAll(filter);
			            				if(null!=e_list && e_list.size()>0){
			            					if(str[j].trim().replaceAll(" ","").equals(e_list.get(0).getAccountnum())){
			            						flag=false;
			            					}
			            				}
			            				if(flag){
			            					return "第"+(i+1)+"行,对方账号填写有误";
			            				}
			            			}
				            	}else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
				            		//单元格是数字
				            		str[j] = cell.toString() ;
				            	}
			            	}
			            }
		    		 }
		    		 list.add(str);
		    	 }
		     }
		     String[] st = list.get(0);
		     String sql = "insert into sl_fund_qlide(myAccount,currency,incomeMoney,payMoney,factDate,opBankName,opOpenName,opAccount,transactionType,notMoney,isProject,operTime,bankTransactionType,companyId)" ;
		     StringBuffer sbuf = new StringBuffer(sql);
		     sbuf.append("values");
		     sbuf.append("(") ;
		     for(int s = 1 ; s <= st.length ; s ++){
		    	 if(s != st.length){
		    		 sbuf.append("?,");
		    	 }else{
		    		 sbuf.append("?)");
		    	 }
		     }
		     String sqlmo = sbuf.toString() ; 
		     if(connectDB2(sqlmo)){
		    	 for(int l = 0 ;l < list.size() ; l ++){
			    	 String[] sd = list.get(l) ;
			         stmt.setString(1, sd[2]);
		    		 stmt.setString(2, sd[3]);
		    		 stmt.setString(3, sd[4]);
		    		 stmt.setString(4, sd[5]);
		    		 stmt.setString(5, sd[6]);
		    		 stmt.setString(6, sd[7]);
		    		 stmt.setString(7, sd[8]);
		    		 stmt.setString(8, sd[9]);
		    		 stmt.setString(9, sd[10]);
		    		 if(sd[4]!=null){
		    			 stmt.setString(10, sd[4]);	 
		    		 }else{
		    			 stmt.setString(10, sd[5]);	 
		    		 }
		    		 if(sd[4]!=null && sd[5]!=null){
		    			return  "第"+(l+1)+"行,支出金额和收入金额填写有误";
		    		 }
		    		 if(sd[4]==null && sd[5]==null){
		    			 return  "第"+(l+1)+"行,支出金额和收入金额填写有误";
			    	 }
		    		 if(sd[11]==null ||sd[11]==""){
		    			 sd[11]="是";
		    		 }
		    		 if(sd[12] !=null &&sd[12]=="保证金"){
		    			 sd[12]="cautionmoneytype";
		    		 }else{
		    			 sd[12]="normalTransactionType";
		    		 }
		    		 stmt.setString(11, sd[11]);
		    		 stmt.setString(13, sd[12]);
		    		 String nowtime= DateUtil.getNowDateTime();
		    		 stmt.setString(12, nowtime.trim());
		    		 stmt.setString(14, ContextUtil.getLoginCompanyId().toString());
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
	  Pattern p =	Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
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

