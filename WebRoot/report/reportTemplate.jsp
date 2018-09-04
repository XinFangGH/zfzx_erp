<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>
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
<%@page import="net.sf.jasperreports.engine.export.JRHtmlExporterParameter"%>
<%
	request.setCharacterEncoding("utf-8");
	response.setContentType("text/html;charset=utf-8");
	response.setCharacterEncoding("utf-8");
	
	//报表类型
	String reportType = request.getParameter("reportType");
	
	Map<String,String> configMap = ReportUtil.getReportPro();
	
	Map<String,String> parameters = new HashMap<String,String>();
	Map<?,?> valueMap = request.getParameterMap();
	Iterator<?> it = valueMap.entrySet().iterator();
	while (it.hasNext()) {
		Map.Entry<?,?> entry = (Map.Entry<?,?>) it.next();
		String key = (String) entry.getKey();
		String[] values = (String[]) entry.getValue();
		String value=URLDecoder.decode(values[0], "UTF-8");
		parameters.put(key, value);
	}
	String reportKey=request.getParameter("reportKey");
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
			byte[] bytes = JasperRunManager.runReportToPdf(fullPath.getPath(), parameters, conn);
			//设置报表生成类型为PDF
			response.setContentType("application/pdf;charset=utf-8");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentLength(bytes.length);
			reportName = reportName+".pdf";
			response.setHeader( "Content-Disposition", "attachment;filename="  + new String( reportName.getBytes("gb2312"), "ISO8859-1" ) );
			ouputStream.write(bytes, 0, bytes.length);
		} else if ("xls".equals(reportType)) {
			//如果接受到的参数为xls(excel类型)的话,则生成xls类型的报表
			JRXlsExporter exporter = new JRXlsExporter();
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath.getPath(), parameters, conn);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,oStream);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,Boolean.FALSE);
			exporter.exportReport();
			byte[] bytes = oStream.toByteArray();
			//设置报表生成类型为excel
			response.setContentType("application/vnd.ms-excel");
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			response.setContentLength(bytes.length);
			reportName = reportName+".xls";
			response.setHeader( "Content-Disposition", "attachment;filename="  + new String( reportName.getBytes("gb2312"), "ISO8859-1" ) );
			ouputStream.write(bytes, 0, bytes.length);
		} else if("word".equals(reportType)){
			reportName = reportName+".docx";
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
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
			JasperPrint jasperPrint = JasperFillManager.fillReport(fullPath.getPath(), parameters, conn);
			//这里也是解决HTML报表图片不显示的
			session.setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
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
			ouputStream.write(bytes, 0, bytes.length);
		}
		ouputStream.flush();
		ouputStream.close();
		conn.close();
		out.clear();
		out = pageContext.pushBody();
	} catch (Exception ex) {
		ex.printStackTrace();
	}
%>