package com.zhiwei.credit.core.creditUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;

/**
 * 关于表头和字段名称的对应的设置
 * @param headers   表头
 * @param fields	对应属性名称
 * @param list		对象列表
 * @param fileName	文件名称
 * @param clasz		对象类
 * header，fields，object
 * String[] tableHeader = { "序号", "项目名称", "项目编号", "资金类型", "计划收入金额",
				"计划到帐日", "实际到帐日", "计息开始日期", "计息结束日期", "已对账金额", "未对账金额",
				"核销金额","顺延至","宽限至","罚息总额","罚息已对账金额","状态","备注" };
   String[] fields = {"projectName","projectNumber","fundTypeName","incomeMoney",
				"intentDate","factDate","","InterestEndTime","afterMoney","notMoney",
				"flatMoney","continueDay","graceDay","accrualMoney","faxiAfterMoney","tabType","remark"};
 * 
 * ***/
public class ExportExcel {
	
	private static final Log logger=LogFactory.getLog(ExcelHelper.class);
	
	private static HSSFWorkbook wb = null;//excel文档对象
	 
    private static HSSFSheet sheet = null;//

    private static HSSFRow row = null;//行

    private static HSSFCell cell = null;//列

    private static HSSFCellStyle titleStyle = null;//样式

    private static HSSFCellStyle headStyle = null;

    private static HSSFCellStyle bodyStyle = null;
    
    private static HSSFHeader header = null;    
	
	public static void export(String[] headers,String[] fields,List list,String fileName,Class clasz) throws Exception{
		  wb = new HSSFWorkbook();
          sheet = wb.createSheet();
          init();
          header = sheet.getHeader();
          header.setCenter(fileName);
          createHeader(headers);
          createBody(fields, list, clasz);
          downLoad(fileName);
	}
	//初始化信息
	private static void init(){
		titleFont();
		headFont();
		bodyFont();
	}
	//初始化标题
	private static void titleFont(){
		HSSFFont titleFont = wb.createFont();
        titleFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleFont.setFontName("宋体");
        titleFont.setFontHeightInPoints((short) 18);
        titleStyle = wb.createCellStyle();
        titleStyle.setFont(titleFont);
        titleStyle.setBorderBottom((short)1);
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	}
	//初始化表头
	private static void headFont(){
		HSSFFont headFont = wb.createFont();
        headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short) 11);
        headStyle = wb.createCellStyle();
        headStyle.setFont(headFont);
        headStyle.setBorderTop((short)1);
        headStyle.setBorderRight((short)1);
        headStyle.setBorderBottom((short)1);
        headStyle.setBorderLeft((short)1);
        headStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	}
	//初始化body
    private static void bodyFont() {
        HSSFFont bodyFont = wb.createFont();
        bodyFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        bodyFont.setFontName("宋体");
        bodyFont.setFontHeightInPoints((short) 9);
        bodyStyle = wb.createCellStyle();
        bodyStyle.setFont(bodyFont);
        bodyStyle.setBorderTop((short)1);
        bodyStyle.setBorderRight((short)1);
        bodyStyle.setBorderBottom((short)1);
        bodyStyle.setBorderLeft((short)1);
        bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
    }
    private  static void createTitle(String fileName){
    	/*row = sheet.createRow(0);
    	cell = row.createCell(0);
    	cell.*/
    }
    //生成表头
    private static void createHeader(String[] headers){
    	row = sheet.createRow(0);
    	for(int i=0; i<headers.length;i++){
    		cell = row.createCell(i);
    		cell.setCellStyle(headStyle);
    		cell.setCellValue(headers[i]);
    		sheet.autoSizeColumn(i);
    	}
    }
    //生成Excel主题
    private static void createBody(String[] fields,List list,Class clasz) throws Exception{
    	for(int j=0;j<list.size();j++){
    		Object object = list.get(j);
    		if(object==null){
    			continue;
    		}
    		row = sheet.createRow((short) (j + 1));// 创建第i+1行
    		cell = row.createCell(0);// 创建第i+1行第0列
    		cell.setCellStyle(bodyStyle);
    		cell.setCellValue(j+1);// 设置第i+1行第0列的值
    		for(int i =0; i<fields.length;i++){
    			Field field = clasz.getDeclaredField(fields[i]);
    			Method method = clasz.getMethod("get"+Character.toUpperCase(fields[i].charAt(0))+fields[i].substring(1), null);
    			Object o = method.invoke(object, null);
    			cell = row.createCell(i+1);
    			if(o==null){
    				cell.setCellValue("");
    			}else{
    				cell.setCellValue(o.toString());	
    			}
    			cell.setCellStyle(bodyStyle);
    			sheet.autoSizeColumn(i);
    		}
    	}
    }
    //生成文件
    private static void downLoad(String headerStr){
    	HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try {
			response = ServletActionContext.getResponse();// 初始化HttpServletResponse对象
			out = response.getOutputStream();//
	        headerStr =new String( headerStr.getBytes("gb2312"), "ISO8859-1" );//headerString为中文时转码
			response.setHeader("Content-disposition", "attachment; filename="
					+ headerStr+".xls");// filename是下载的xls的名，建议最好用英文
			response.setContentType("application/msexcel;charset=GB2312");// 设置类型
			response.setHeader("Pragma", "No-cache");// 设置头
			response.setHeader("Cache-Control", "no-cache");// 设置头
			response.setDateHeader("Expires", 0);// 设置日期头
			wb.write(out);
			out.flush();
			wb.write(out);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
		} finally {
			try {

				if (out != null) {
					out.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }
}
