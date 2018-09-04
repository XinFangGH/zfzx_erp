package com.zhiwei.credit.util.xmlToWord;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class DocumentHandler {
	private Configuration configuration = null;

	
	public DocumentHandler(String ftlSrc) {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		try {
			configuration.setDirectoryForTemplateLoading(new File(ftlSrc));
		} catch (IOException e) {
			e.printStackTrace();
		}
	} 

	/**
	 * 要把模版生成合同
	 * @param dataMap   数据map集合
	 * @param fileSrc   生成后的文件路径
	 */
	public void createDoc(Map<String,Object> dataMap,String fileSrc) {
		//设置模本装置方法和路径,FreeMarker支持多种模板装载方法。可以重servlet，classpath，数据库装载，
		//这里我们的模板是放在/com/zhiwei/credit/util/xmlToWord/firstCreditor包下面
	//	configuration.setClassForTemplateLoading(this.getClass(), "/com/zhiwei/credit/util/xmlToWord/firstCreditor");
	//	configuration.setServletContextForTemplateLoading(ServletActionContext.getContext(), "WEB-INF/templates");
	//	configuration.setDirectoryForTemplateLoading(arg0);
		Template t=null;
		try {
			//test.ftl为要装载的模板
			t = configuration.getTemplate("firstCreditor.ftl");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出文档路径及名称
		File outFile = new File(fileSrc);
		 //如果输出目标文件夹不存在，则创建
        if (!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
        	//生成
			t.process(dataMap, out);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				//关闭流
	            try {
	            	out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
	/**
	 * 生成对账单
	 * @param dataMap   数据map集合
	 * @param fileSrc   生成后的文件路径
	 */
	public void createOrderAndDown(Map<String,Object> dataMap,String fileSrc) {
		Template t=null;
		try {
			//test.ftl为要装载的模板
			t = configuration.getTemplate("order.ftl");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//输出文档路径及名称
		File outFile = new File(fileSrc);
		 //如果输出目标文件夹不存在，则创建
        if (!outFile.getParentFile().exists()){
            outFile.getParentFile().mkdirs();
        }
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
        	//生成
			t.process(dataMap, out);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(out!=null){
				//关闭流
	            try {
	            	out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	

}
