package com.zhiwei.credit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.velocity.runtime.resource.ContentResource;
import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

import com.zhiwei.core.util.AppUtil;

public class ExcelUtils extends ServletEndpointSupport {

	public static String[] createExcel(String f, String date, String t,
			String t1, String[] head, List<String[]> list, int k) {
		String[] ret = new String[2];

		String path = "attachFiles" + "\\" + f + "\\" + t + "\\" + t1 + "\\"
				+ date + "\\";
		String fileNamePath = path;
		String fileName = t1 + ".xls";

		newFolder(fileNamePath);
		int rowNum = 0;
		try {
			fileNamePath = fileNamePath + fileName;
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					fileNamePath));
			// 得到类别
			WritableSheet sheet = book.createSheet(t1, 0);
			if (!(head.length == 0 || head == null)) {// 表头数据
				for (int i = 0; i < head.length; i++) {
					Label label = new Label(i, 0, head[i].toString());
					sheet.addCell(label);
				}
				rowNum++;
			}
			if (!(list.size() == 0 || list == null)) {
				for (int cell = 0; cell < list.size(); cell++) {
					String[] str = list.get(cell);
					for (int j = 0; j < str.length; j++) {
						Label labelStrList = new Label(j, rowNum, str[j]
								.toString());
						sheet.addCell(labelStrList);
					}
					rowNum++;
				}
			}
			book.write();
			book.close();
			ret[0] = "Y";
			ret[1] = path + fileName;
		} catch (Exception e) {
			ret[0] = "N";
			ret[1] = "数据生成失败！错误提示：" + e;
		}
		return ret;
	}

	public static String[] createExcel(String f, String date, String t,
			String t1, String[] head, List<String[]> list) {
		String[] ret = new String[2];

		String rootPath = AppUtil.getAppAbsolutePath();

		String path = "attachFiles" + "\\" + f + "\\" + t + "\\" + t1 + "\\"
				+ date + "\\";
		String fileNamePath = rootPath + path;
		String fileName = t1 + ".xls";

		newFolder(fileNamePath);
		int rowNum = 0;
		try {
			fileNamePath = fileNamePath + fileName;
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					fileNamePath));
			// 得到类别
			WritableSheet sheet = book.createSheet(t1, 0);
			if (!(head.length == 0 || head == null)) {// 表头数据
				for (int i = 0; i < head.length; i++) {
					Label label = new Label(i, 0, head[i].toString());
					sheet.addCell(label);
				}
				rowNum++;
			}
			if (!(list.size() == 0 || list == null)) {
				for (int cell = 0; cell < list.size(); cell++) {
					String[] str = list.get(cell);
					for (int j = 0; j < str.length; j++) {
						if (str[j] == null) {
							str[j] = "";
						}
						Label labelStrList = new Label(j, rowNum, str[j]
								.toString());
						sheet.addCell(labelStrList);
					}
					rowNum++;
				}
			}
			book.write();
			book.close();
			ret[0] = "Y";
			ret[1] = path + fileName;
		} catch (Exception e) {
			e.printStackTrace();
			ret[0] = "N";
			ret[1] = "数据生成失败！错误提示：" + e;
		}
		return ret;
	}

	public static String[] createDataToExcel(String f, String date, String t,
			String t1, String[] head, List<String[]> list, int k) {
		String[] ret = new String[2];

		String path = "attachFiles" + "\\" + f + "\\" + t + "\\" + t1 + "\\"
				+ date + "\\";
		String fileNamePath = path;
		String fileName = t1 + ".xls";

		newFolder(fileNamePath);
		int rowNum = 0;
		try {
			fileNamePath = fileNamePath + fileName;
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(
					fileNamePath));
			// 得到类别
			WritableSheet sheet = book.createSheet(t1, 0);
			if (!(head.length == 0 || head == null)) {// 表头数据
				for (int i = 0; i < head.length; i++) {
					Label label = new Label(i, 0, head[i].toString());
					sheet.addCell(label);
				}
				rowNum++;
			}
			if (!(list.size() == 0 || list == null)) {
				for (int cell = 0; cell < list.size(); cell++) {
					String[] str = list.get(cell);
					for (int j = 0; j < str.length; j++) {
						Label labelStrList = new Label(j, rowNum, str[j]
								.toString());
						sheet.addCell(labelStrList);
					}
					rowNum++;
				}
			}
			book.write();
			book.close();
			ret[0] = "Y";
			ret[1] = path + fileName;
		} catch (Exception e) {
			ret[0] = "N";
			ret[1] = "数据生成失败！错误提示：" + e;
		}
		return ret;
	}

	/**
	 * 生成多个excel页签 数据
	 * 
	 */
	public static void createExcel(String f, String date, String t, String t1,
			ArrayList typeArrList, List<String[]> head, Vector<String> data) {

		String rootPath = AppUtil.getAppAbsolutePath();

		String path = "attachFiles" + "\\" + f + "\\" + t + "\\" + t1 + "\\"
				+ date + "\\";
		String fileNamePath = rootPath + path;
		String fileName = t1 + ".xls";
		String filePath = fileNamePath + fileName;
		try {

			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(filePath));
			// 得到类别
			for (int i = 0; i < typeArrList.size(); i++) {
				WritableSheet sheet = book.createSheet(typeArrList.get(i)
						.toString(), i);
				for (int j = 0; j < head.size(); j++) {
					String[] strHead = head.get(i);
					for (int x = 0; x < strHead.length; x++) {
						Label label = new Label(x, 0, strHead[x].toString());
						sheet.addCell(label);
					}
				}

			}
			book.write();
			book.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param fileName
	 *            文件路径
	 * @param usersList
	 *            读取数据
	 * @return
	 */
	public static List<List<String>> ImportExcelData(String f, String date,
			String t, String t1, int inputFileSheetIndex) {

		String rootPath = AppUtil.getAppAbsolutePath();
		String path = "attachFiles" + "\\" + f + "\\" + t + "\\" + t1 + "\\"
				+ date + "\\";
		String fileNamePath = rootPath + path;
		String fileName = t1 + ".xls";
		String filePath = fileNamePath + fileName;

		List<List<String>> list = new ArrayList<List<String>>();
		Workbook book = null;
		Cell cell = null;
		File file = new File(filePath);
		InputStream stream = null;
		try {
			stream = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stream != null) {
			try {
				book = Workbook.getWorkbook(stream);
			} catch (Exception e) {
				e.printStackTrace();
			}

			Sheet sheet = book.getSheet(inputFileSheetIndex);
			for (int rowIndex = 1; rowIndex < sheet.getRows(); rowIndex++) {// 行
				List<String> strData = new ArrayList<String>();
				for (int colIndex = 0; colIndex < sheet.getColumns(); colIndex++) {// 列
					cell = sheet.getCell(colIndex, rowIndex);
					strData.add(cell.getContents());
				}
				list.add(strData);
			}
			book.close();
		}else{
			//list.add(new ArrayList<String>("0"));
		}
		return list;
	}
	/**
	 * @param sourceProductImagePath
	 * @return
	 * 检查 权限数据
	 */
	public static String[] checkAccessExcelData(String sourceProductImagePath) {

		String[] str=new String[6];
		int dataNum;
		String ids="";
		String rights="";
		String roleNames="";
		String error="";
		HSSFWorkbook book = null;
		HSSFRow idRow=null;
		HSSFRow headRow=null;
		HSSFRow conentRow=null;
		
		HSSFCell cell;
		File file = new File(sourceProductImagePath);
		InputStream stream = null;
		//HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(new File("F:/tt.xls")));
		try {
			stream = new FileInputStream(file);
		} catch (Exception e) {
			e.printStackTrace();
			error="文件读取错误！";
		}
		if (stream != null) {
			try {
				book = new HSSFWorkbook(stream);

				HSSFSheet sheet = book.getSheetAt(0);//第几个页签
				int rows=sheet.getLastRowNum();//行数
				 headRow = sheet.getRow(0);//获取第0行
				int cols=headRow.getLastCellNum();//列数
				dataNum=rows;
				if(rows<=0){
					error="文件格式不正确！";
				}else{

					if(cols>=3){ //3列以上才 有角色 名称
						for(int colIndex=2;colIndex<cols;colIndex++){
							roleNames=roleNames+headRow.getCell(colIndex).getStringCellValue()+",";//角色名称 字符串
						}
						}else{
							roleNames="";
						}
					for (int rowIndex = 1; rowIndex <= rows; rowIndex++) {// 行
						idRow = sheet.getRow(rowIndex);//获取第rowIndex行
						ids=ids+idRow.getCell(0).getStringCellValue()+","; //id 字符串
					}

					for(int col=2;col<headRow.getLastCellNum();col++){ //第 col 列
						
						for(int rowIndex = 1; rowIndex <= rows; rowIndex++){
							conentRow=sheet.getRow(rowIndex);//获取第rowIndex行
							if(conentRow.getCell(col).getStringCellValue().equals("有")){
								rights=rights+conentRow.getCell(0).getStringCellValue()+",";//权限字符串
							}
						}
						int endIndex=rights.lastIndexOf(",");
						rights=rights.substring(0,endIndex)+"@";
						
					}
					
					int endIndex=rights.lastIndexOf("@");
					rights=rights.substring(0,endIndex);
				
					int endIndexName=roleNames.lastIndexOf(",");
					roleNames=roleNames.substring(0,endIndexName);
					
					int endIndexid=ids.lastIndexOf(",");
					ids=ids.substring(0,endIndexid);
					str[0]=String.valueOf(dataNum);
					str[1]=roleNames;
					str[2]=ids;
					str[3]=rights;
				}
			
			} catch (Exception e) {
				error="读取excel文件错误！";
			}
			//book.close();
		}
		str[4]=error;
		return str;
	
	}
	

	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 新建文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String 文件内容
	 * @return boolean
	 */
	public static void newFile(String filePathAndName, String fileContent) {

		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			File myFilePath = new File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.createNewFile();
			}
			FileWriter resultFile = new FileWriter(myFilePath);
			PrintWriter myFile = new PrintWriter(resultFile);
			String strContent = fileContent;
			myFile.println(strContent);
			resultFile.close();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件
	 * 
	 * @param filePathAndName
	 *            String 文件路径及名称 如c:/fqf.txt
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹
	 * 
	 * @param filePathAndName
	 *            String 文件夹路径及名称 如c:/fqf
	 * @param fileContent
	 *            String
	 * @return boolean
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 删除文件夹里面的所有文件
	 * 
	 * @param path
	 *            String 文件夹路径 如 c:/fqf
	 */
	public static void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[1444];
				int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 复制整个文件夹内容
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf/ff
	 * @return boolean
	 */
	public static void copyFolder(String oldPath, String newPath) {

		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	/**
	 * 移动文件到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFile(String oldPath, String newPath) {
		copyFile(oldPath, newPath);
		delFile(oldPath);

	}

	/**
	 * 移动文件夹到指定目录
	 * 
	 * @param oldPath
	 *            String 如：c:/fqf.txt
	 * @param newPath
	 *            String 如：d:/fqf.txt
	 */
	public static void moveFolder(String oldPath, String newPath) {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);

	}

	public static String dateFomat(Date d, String strFmt) {
		String ret = "";
		if (strFmt != null && !strFmt.equals("")) {
			SimpleDateFormat dirSdf = new SimpleDateFormat(strFmt);
			ret = dirSdf.format(new Date());
		}
		return ret;

	}

	

}
