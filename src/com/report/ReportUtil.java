package com.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

/* import java.util;
<%@page import="java.io.File"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.sql.Connection"%>
<%@page import="javax.sql.DataSource"%>
<%@page import="com.report.ReportUtil"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@page import="net.sf.jasperreports.j2ee.servlets.*"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>
<%@page import="net.sf.jasperreports.engine.JasperRunManager"%>
<%@page import="net.sf.jasperreports.engine.JasperFillManager"%>
<%@page import="net.sf.jasperreports.engine.JRAbstractExporter"%>
<%@page import="net.sf.jasperreports.engine.JRExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporter"%>
<%@page import="org.apache.lucene.store.jdbc.support.JdbcTemplate"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporter"%>
<%@page import="net.sf.jasperreports.engine.export.ooxml.JRDocxExporter"%>
<%@page import="net.sf.jasperreports.engine.export.JRXlsExporterParameter"%>
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>*/

import com.thirdPayInteface.SinaPay.SinaPayIntefaceUtil;
import com.zhiwei.core.util.AppUtil;

/**
 * 初始化加载report_config.properties
 * 该文件配置的是系统中所有的报表信息key=报表名称
 * @author zhangcb
 *
 */
@SuppressWarnings("unchecked")
public class ReportUtil {
	
	private static Log logger=LogFactory.getLog(SinaPayIntefaceUtil.class);

	public static Map<String,String> getReportPro(){
		Map<String,String> reportConfigMap=new HashMap<String,String>();
		try{
			Properties props =  new  Properties();    
	        InputStream in = ReportUtil.class.getResourceAsStream("/com/report/report_config.properties"); 
	        props.load(in);
	    	Iterator it= props.keySet().iterator();
	    	while(it.hasNext()){
	    		String key=(String)it.next();
	    		reportConfigMap.put(key, props.getProperty(key));
	    	}
	    	return reportConfigMap;
		}catch(Exception e){
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}
	
	
	public static void createReportHtml(Map<String,String> map,String reportKey,String reportType,HttpServletRequest  request){
		HttpServletResponse response = ServletActionContext.getResponse();
		//报表类型
		Map<String,String> configMap = ReportUtil.getReportPro();
		String rootPath = ReportUtil.class.getResource("").getPath().replaceAll("%20"," ");
		String reportPath=configMap.get(reportKey);
		String[] str=reportPath.split("/");
		String reportName=str[str.length-1];
		try {
			DataSource dataSource = (DataSource) AppUtil.getBean("dataSource");
			Connection conn = dataSource.getConnection();
			File fullPath = new File(rootPath+reportPath+".jasper");
			ServletOutputStream ouputStream = response.getOutputStream();
			//将解析完的参数传入报表模板中并生成报表
			//如果接收到的参数为pdf类型的话,则生成pdf的报表
			if ("pdf".equals(reportType)) {
				byte[] bytes = JasperRunManager.runReportToPdf(fullPath.getPath(), map, conn);
				//设置报表生成类型为PDF
				response.setContentType("application/pdf;charset=utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentLength(bytes.length);
				reportName = reportName+".pdf";
				response.setHeader( "Content-Disposition", "attachment;filename="  + new String( reportName.getBytes("gb2312"), "ISO8859-1" ) );
				ouputStream.write(bytes, 0, bytes.length);
			} else if ("xls".equals(reportType)) {
				//如果接受到的参数为xls(excel类型)的话,则生成xls类型的报表
				JRXlsExporter exporter = new JRXlsExporter();
				ByteArrayOutputStream oStream = new ByteArrayOutputStream();
				JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath.getPath(), map, conn);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,oStream);
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
				exporter.exportReport();
				byte[] bytes = oStream.toByteArray();
				//设置报表生成类型为excel
				response.setContentType("application/vnd.ms-excel");
				response.setCharacterEncoding("utf-8");
				request.setCharacterEncoding("utf-8");
				response.setContentLength(bytes.length);
				reportName = reportName+".xls";
				response.setHeader( "Content-Disposition", "attachment;filename="  + new String( reportName.getBytes("gb2312"), "ISO8859-1" ) );
				ouputStream.write(bytes, 0, bytes.length);
			} else if("word".equals(reportType)){
				reportName = reportName+".docx";
				JRAbstractExporter   exporter = new JRDocxExporter();
				List<?> jasperPrintList = (List<?>)request.getSession().getAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_LIST_SESSION_ATTRIBUTE);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList); 
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());  
	            response.setContentType("application/msword;charset=utf-8"); 
	            response.setHeader( "Content-Disposition", "attachment;filename="  + new String( reportName.getBytes("utf-8"), "ISO8859-1" ) );
			    request.setCharacterEncoding("utf-8");
			    exporter.exportReport();
			}else{
				//否则生成html类型的报表
				JRHtmlExporter exporter = new JRHtmlExporter();
				ByteArrayOutputStream oStream = new ByteArrayOutputStream();
				JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath.getPath(), map, conn);
				//这里也是解决HTML报表图片不显示的
				request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
				exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);
				//下面这一句是解决HTML报表图片不显示的问题，注意URI得写对，这里URI是指jasper文件所在的目录
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,"jasper?"+"time="+(new Date()).toString()+"&image=");     
				exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,jasperPrint);
				exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING,"utf-8");
				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM, oStream);
				exporter.exportReport();
				byte[] bytes = oStream.toByteArray();
				//设置报表生成类型的html
				response.setContentType("text/html;charset=utf-8");
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");
				response.setContentLength(bytes.length);
				response.setStatus(200);
				ouputStream.write(bytes, 0, bytes.length);
			}
			ouputStream.flush();
			ouputStream.close();
			conn.close();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}