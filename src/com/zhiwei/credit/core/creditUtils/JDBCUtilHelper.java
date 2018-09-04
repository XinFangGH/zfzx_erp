package com.zhiwei.credit.core.creditUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * @author tianhuiguo
 * 
 * */


public class JDBCUtilHelper{
	public static Connection getConnectionByJDBC(String turl) {   
        Connection conn = null;   
        try { // 装载驱动类   
            Class.forName("com.mysql.jdbc.Driver");   
        } catch (ClassNotFoundException e) {   
            e.printStackTrace();   
        }   
        try { // 建立JDBC连接
        	//add by chencc start...
        	String jdbcUrl = "";
        	String user = "";
        	String psw = "";
        	Properties property=new Properties();
        	File fl=new File(turl+"\\WEB-INF\\classes\\conf\\jdbc.properties");
        	FileInputStream fis;
			try {
				fis = new FileInputStream(fl);
				try {
					property.load(fis);
					jdbcUrl = property.getProperty("jdbc.url");
					user = property.getProperty("jdbc.username");
					psw = property.getProperty("jdbc.password");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//add by chencc end...
            conn = DriverManager.getConnection(jdbcUrl, user,psw);   
        } catch (SQLException e) {   
            e.printStackTrace();   
        }   
        return conn;   
    } 
	
	public static void batchDelete(String[] strTable ,String[] listId,String turl){
		Connection conn = getConnectionByJDBC(turl); 
		Statement stmt = null;
		int tableSum = strTable.length ;
		int idSum = listId.length ;
		if(strTable != null && tableSum > 0 || listId != null && idSum > 0){
			for(int j =0 ; j < idSum ; j ++){
				for(int i = 0 ; i < tableSum ; i ++){
					String[] tableAndFiled  = strTable[i].split("-");
					int filedValue = Integer.parseInt(listId[j]);
					String deleteSql = "DELETE FROM "+tableAndFiled[0]+" WHERE "+tableAndFiled[1]+" = "+filedValue;
					try {
						stmt = conn.createStatement();
						stmt.executeUpdate(deleteSql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
