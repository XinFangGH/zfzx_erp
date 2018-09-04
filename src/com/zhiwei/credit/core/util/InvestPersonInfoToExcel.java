package com.zhiwei.credit.core.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.struts2.ServletActionContext;

import com.hurong.credit.model.creditFlow.financingAgency.PlBidPlan;
import com.zhiwei.credit.model.customer.InvestPersonInfo;

/**
 * 根据投标项目导出项目中的投资人信息列表
 * @author LIUSL
 *
 */
public class InvestPersonInfoToExcel {
	
	private static final Log logger=LogFactory.getLog(InvestPersonInfoToExcel.class);

	
	/**
	 * 导出意见与说明记录
	 * @param baseMap   项目基本信息map
	 * @param dataList  意见与说明记录map
	 * @param headerStr 标题名称
	 * @return
	 */
	/**
	 * @param list
	 * @return
	 */
	public static String export(List<InvestPersonInfo> list)  {
		String headerStr = "投资列表";
		//新建工作簿：
		HSSFWorkbook wb = new HSSFWorkbook();
		//建立新的sheet对象：
		HSSFSheet sheet = wb.createSheet(headerStr);
		try {
			//设置每列默认宽度：
			sheet.setDefaultColumnWidth((short) 13);
			sheet.setColumnWidth((short)0, (short)(256*15));  //第二列默认列宽
			sheet.setColumnWidth((short)1, (short)(256*15));  //第二列默认列宽
			sheet.setColumnWidth((short)2, (short)(256*15));  //第二列默认列宽
			sheet.setColumnWidth((short)3, (short)(256*15));  //第二列默认列宽
	/*
			sheet.setColumnWidth((short)5, (short)(256*15));  //第二列默认列宽
*/		//	sheet.setColumnWidth((short)4, (short)(256*50));   //第五列默认列宽
			
			HSSFRow nRow = null;
			HSSFCell nCell = null;
			Region nRegion = null;
			
			HSSFCellStyle cellTitleStyle = getCellTitleStyle(wb);
			HSSFCellStyle cellStyle = getCellStyle(wb);
			HSSFCellStyle backColorCellStyle = getBackColorCellStyle(wb);
			
			HSSFCellStyle headerStyle = getHeaderStyle(wb);
			HSSFCellStyle cellValueStyle = getCellValueStyle(wb);
			HSSFCellStyle backColorCellTitleStyle = getBackColorCellTitleStyle(wb);
			
			HSSFRow header = sheet.createRow(0);  //创建标题
			header.setHeightInPoints(30f); 			//设置第一行高度
			
			nRegion = new Region(0, (short)0, 0, (short)5);
			sheet.addMergedRegion(nRegion);
			for(int i = 0 ;i < 6 ; i++){   //给标题加上边框
				nCell = header.createCell(i);
				nCell.setCellStyle(headerStyle);
				if(i==0){
					nCell.setCellValue(headerStr);
				}
			}
		
			
			//row 基本信息
		/*	for(int i = 0 ; i < 3 ; i++) {
				nRow = sheet.createRow(sheet.getLastRowNum()+1);
				nRow.setHeightInPoints(25);
				for(int j = 0 ; j< 6 ; j++){
					nCell = nRow.createCell(j);
					nCell.setCellStyle(backColorCellStyle);
					if(i==0){
						if(j==0){
							nCell.setCellValue("项目名称");
							nCell.setCellStyle(backColorCellTitleStyle);
						}
						if(j==1){nCell.setCellValue(plBidPlan.getBidProName());}
						if(j==4){
							nCell.setCellValue("项目编号");
							nCell.setCellStyle(backColorCellTitleStyle);
						}
						if(j==5){nCell.setCellValue(plBidPlan.getBidProNumber());}
					}
					if(i==1){
						if(j==0){
							nCell.setCellValue("客户名称");
							nCell.setCellStyle(backColorCellTitleStyle);
						}
						if(j==1){nCell.setCellValue(plBidPlan.getBidProName());}
					}
					if(i==2){
						if(j==0){nCell.setCellValue("业务名称");nCell.setCellStyle(backColorCellTitleStyle);}
						if(j==1){nCell.setCellValue(plBidPlan.getBidProName());}
						if(j==2){nCell.setCellValue("项目经理");nCell.setCellStyle(backColorCellTitleStyle);}
						if(j==3){nCell.setCellValue(plBidPlan.getBidProName());}
						if(j==4){nCell.setCellValue("风险经理");nCell.setCellStyle(backColorCellTitleStyle);}
						if(j==5){nCell.setCellValue(plBidPlan.getBidProName());}
					}
				}
			}*/
			
			nRegion = new Region(sheet.getLastRowNum()+1, (short)2, sheet.getLastRowNum()+1, (short)3);
			sheet.addMergedRegion(nRegion);
			nRegion = new Region(sheet.getLastRowNum()+1, (short)4, sheet.getLastRowNum()+1, (short)5);
			sheet.addMergedRegion(nRegion);
			nRow = sheet.createRow(sheet.getLastRowNum()+1);
			nRow.setHeightInPoints(25);
			nCell = nRow.createCell(0);
			nCell.setCellValue("序号");
			nCell.setCellStyle(cellTitleStyle);
			nCell = nRow.createCell(1);
			nCell.setCellValue("投资方");
			nCell.setCellStyle(cellTitleStyle);
			nCell = nRow.createCell(2);
			nCell.setCellValue("投资金额");
			nCell.setCellStyle(cellTitleStyle);
			nCell = nRow.createCell(3);
			nCell.setCellStyle(cellTitleStyle);
			nCell = nRow.createCell(4);
			nCell.setCellValue("投资比例");
			nCell.setCellStyle(cellTitleStyle);
			nCell = nRow.createCell(5);
			nCell.setCellStyle(cellTitleStyle);
			
			
			//data数据信息
			for(InvestPersonInfo investPersonInfo : list){
				nRegion = new Region(sheet.getLastRowNum()+1, (short)2, sheet.getLastRowNum()+1, (short)3);
				sheet.addMergedRegion(nRegion);
				nRegion = new Region(sheet.getLastRowNum()+1, (short)4, sheet.getLastRowNum()+1, (short)5);
				sheet.addMergedRegion(nRegion);
				nRow = sheet.createRow(sheet.getLastRowNum()+1);
				for(int j = 0 ; j < 6 ;j++ ){
					nCell = nRow.createCell(j);
					nCell.setCellStyle(cellStyle);
					if(j==0){
						nCell.setCellValue(j+1+"");
						nCell.setCellStyle(cellValueStyle);
					}
					if(j==1){
						nCell.setCellValue(investPersonInfo.getInvestPersonName());
						nCell.setCellStyle(cellValueStyle);
					}
					if(j==2){
						nCell.setCellValue(investPersonInfo.getInvestMoney()+"元");
						nCell.setCellStyle(cellValueStyle);
					}
					if(j==4){
						nCell.setCellValue(investPersonInfo.getInvestPercent()+"%");
						nCell.setCellStyle(cellValueStyle);
					}
					
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel时报错:"+e.getMessage());
		}	
		
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
		HttpServletResponse response = null;// 创建一个HttpServletResponse对象
		OutputStream out = null;// 创建一个输出流对象
		try{
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
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel的IO出错:"+e.getMessage());
			return null;
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
	
	 private static HSSFCellStyle getHeaderStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("微软雅黑");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)14);	 //字体大小
	//	  curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
/*		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THICK);	 //粗实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);	 //比较粗实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
*/		  
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(true); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		 
		  return curStyle;
	 }
	 
	 private static HSSFCellStyle getBackColorCellStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("宋体");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)10);	 //字体大小
	//	  curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(true); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		 
		  curStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		  curStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		  
		  return curStyle;
	 }
	 
	 private static HSSFCellStyle getCellStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("宋体");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)10);	 //字体大小
	//	  curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(true); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		 
		  return curStyle;
	 }
	 
	 
	 private static HSSFCellStyle getBackColorCellTitleStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("微软雅黑");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)12);	 //字体大小
	      curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(false); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		  
		  curStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		  curStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		  return curStyle;
	 }
	 
	 
	 private static HSSFCellStyle getCellTitleStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("微软雅黑");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)10);	 //字体大小
	     // curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(false); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		 
		  return curStyle;
	 }
	 
	 
	 private static HSSFCellStyle getCellValueStyle(HSSFWorkbook wb){
		  HSSFCellStyle curStyle = wb.createCellStyle();
		  HSSFFont curFont = wb.createFont();	 //设置字体
		//curFont.setFontName("Times New Roman");	 //设置英文字体
		  curFont.setFontName("微软雅黑");	 //设置英文字体
		  curFont.setCharSet(HSSFFont.DEFAULT_CHARSET);	 //设置中文字体，那必须还要再对单元格进行编码设置
		  curFont.setFontHeightInPoints((short)10);	 //字体大小
	   // curFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //加粗
		  curStyle.setFont(curFont);
		 
		  curStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);	 //实线
		  curStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);	 //实线
		 
		  curStyle.setWrapText(true); //换行
		  curStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);	 //横向居右对齐
		  curStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);	 //单元格垂直居中
		 
		  return curStyle;
	 }


}
