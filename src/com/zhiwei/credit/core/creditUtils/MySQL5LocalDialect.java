package com.zhiwei.credit.core.creditUtils;

import org.hibernate.Hibernate;   
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;   
	  
public class MySQL5LocalDialect extends MySQL5InnoDBDialect{   
	public MySQL5LocalDialect(){   
	   super();   
	   registerFunction("convert", new SQLFunctionTemplate(Hibernate.STRING,  "convert(?1 using ?2)") );   
	}   
} 

