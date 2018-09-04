package com.zhiwei.credit.core.util;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;
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
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts2.ServletActionContext;

import com.zhiwei.credit.model.creditFlow.contract.Element;

public class ExcelHelper {
	
	private static final Log logger=LogFactory.getLog(ExcelHelper.class);
	/**
	 * 导出数据到excel
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String export(List list,String [] tableHeader,String headerStr) throws Exception {

		/**
		 *如果是从数据库里面取数据，就让studentList=取数据的函数： 就没必要下面的for循环
		 * 我下面的for循环就是手动给studentList赋值
		 */
		/*
		 * 设置表头：对Excel每列取名(必须根据你取的数据编写)
		 */
		//String[] tableHeader = { "学号", "姓名", "性别", "寝室号", "所在系" };
		/*
		 * 下面的都可以拷贝不用编写
		 */
		short cellNumber = (short) tableHeader.length;// 表的列数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置类型
		style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		style.setBorderLeft((short)1);     //左边框
		style.setBorderRight((short)1);    //右边框
		style.setBorderBottom((short)1);//下边框
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		try {
			/**
			 *根据是否取出数据，设置header信息
			 * 
			 */
			if (list.size() < 1) {
				header.setCenter("查无资料");
			} else {
				header.setCenter(headerStr);
				row = sheet.createRow(0);
				row.setHeight((short) 400);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell(k);// 创建第0行第k列
					cell.setCellValue(tableHeader[k]);// 设置第0行第k列的值
					switch(k){
						case 0 : sheet.setColumnWidth(k, 2000);// 设置列的宽度
						break;
						default : sheet.setColumnWidth(k, 6000);// 设置列的宽度
					}
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					font.setFontHeight((short) 350); // 设置单元字体高度
					font.setFontHeightInPoints((short)11);   //表头字体大小 
					font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   //字体加粗
					style1.setFont(font);// 设置字体风格
					style1.setFillForegroundColor(HSSFColor.ORANGE.index);//添加前景色,内容看的清楚 
					style1.setBorderLeft((short)1);     //左边框
					style1.setBorderRight((short)1);    //右边框
					style1.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);    //顶边框
					style1.setBorderBottom((short)1);//下边框
					cell.setCellStyle(style1);
				}
				/*
				 * // 给excel填充数据这里需要编写
				 */
				for (int i = 0; i < list.size(); i++) {
					
					row = sheet.createRow((short) (i + 1));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高
					cell = row.createCell(0);// 创建第i+1行第0列
					cell.setCellValue(i+1);// 设置第i+1行第0列的值
					cell.setCellStyle(ExcelHelper.setAlign(workbook, 1));// 设置风格
					
					Element et = (Element) list.get(i);// 获取对象
					if (et.getDepict() != null) {
						cell = row.createCell(1); // 创建第i+1行第1列

						cell.setCellValue(et.getDepict());// 设置第i+1行第1列的值
						cell.setCellStyle(style); // 设置风格
					}else{
						cell = row.createCell(1);
						cell.setCellStyle(style);
					}
					// 由于下面的和上面的基本相同，就不加注释了
					if (et.getCode() != null) {
						cell = row.createCell(2);
						cell.setCellValue(et.getCode());
						cell.setCellStyle(style);
					}else{
						cell = row.createCell(2);
						cell.setCellStyle(style);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导出Excel出错:"+e.getMessage());
		}
		/*
		 * 下面的可以不用编写，直接拷贝
		 */
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
			workbook.write(out);
			out.flush();
			workbook.write(out);
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

		return null;
	}
	private static HSSFCellStyle setAlign(HSSFWorkbook workbook ,int align){
		HSSFCellStyle style2 = workbook.createCellStyle(); // 设置类型
		style2.setBorderLeft((short)1);     //左边框
		style2.setBorderRight((short)1);    //右边框
		style2.setBorderBottom((short)1);//下边框
		if(align == 1){
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		}else if(align == 2){
			style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		}else{
			style2.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		}
		return style2;
	}
	private static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                }
            } else {
                age--;
            }
        }
        if(yearNow == yearBirth){
        	age = 1 ;
        }
        if(yearNow==(yearBirth+1)){
        	age=1;
        }
        if(yearNow==(yearBirth+2)){
        	age=2;
        } 
        return age;
    }
}
