package com.zhiwei.credit.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.creditFlow.contract.VProcreditContractDao;
import com.zhiwei.credit.model.admin.ContractElement;
import com.zhiwei.credit.model.creditFlow.contract.Element;
import com.zhiwei.credit.model.creditFlow.contract.ElementCode;

public class ElementUtil {
	@Resource
	private VProcreditContractDao vProcreditContractDao;
	/** 查看系统要素 obj业务品种实体 */
	public static List getListElement(Object obj) {
		Class temp = obj.getClass();
		// Field[] fields = temp.getDeclaredFields();//update by gao
		Field[] fields = temp.getFields();
		List list = new ArrayList();
		String text = "";
		int n = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				// Field field = temp.getDeclaredField(fieldvalue); //update by
				// gao
				Field field = temp.getField(fieldvalue); // update by gao
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (j <= fields.length - 3) {
					if (n > 3) {
						String str[] = text.split("@");
						Element element = new Element();
						element.setDepict(str[0]);
						element.setValue(str[1]);
						element.setCode(str[2]);
						list.add(element);
						n = 0;
						text = "";
						text += object.toString() + "@";
						n++;
					} else {
						text += object.toString() + "@";
					}
				} else {
					if (n == 3) {
						String str[] = text.split("@");
						Element element = new Element();
						element.setDepict(str[0]);
						element.setValue("");
						element.setCode(object.toString());
						list.add(element);
					} else {
						text += object.toString() + "@";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * <p>
	 * 下载合同，return url
	 * </p>
	 * @param url
	 *文件路径, name文件名称
	 * */
	public static String getUrl(String url, String name) {
		String newUrl = url.substring(0, url.lastIndexOf("/")) + "/" + name;//@HT_version3.0
		FileHelper.copyFile(url, newUrl.trim());
		return newUrl.trim();
	}

	private static String serversPath(String path) {
		String s = path, res = "";
		String[] a = new String[7];
		for (int i = 0; i < 7; i++) {
			int j = s.indexOf("\\", 0);
			String b = s.substring(0, j);
			s = s.substring(j + 1);
			a[i] = b;
			res = res + a[i] + "\\";
			if (a[i].trim().equals("webapps")) {
				res = res + s.substring(0, s.indexOf("\\", 0));
				break;
			}
		}
		return res.trim();
	}

	/**
	 * <p>
	 * 查找要素编码 要素编码以数组的形式返回
	 * </p>
	 * 
	 * @param 要素描述实体
	 * 
	 * */
	public static String[] findEleCodeArray(Object obj) {
		Class temp = obj.getClass();
		// Field[] fields = temp.getDeclaredFields();//update by gao
		Field[] fields = temp.getFields();// 支持父类属性查询，但只查询 public字段
		String[] text = new String[(fields.length) / 3];
		int n = 0, m = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				// Field field = temp.getDeclaredField(fieldvalue);//update by
				// gao
				Field field = temp.getField(fieldvalue);
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (n == 3) {
					text[m] = object.toString();
					m++;
					n = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * <p>
	 * 查找要素所对应的值 以数组的形式返回
	 * </p>
	 * 
	 * @param 要素描述实体
	 * 
	 * */
	public static String[] findEleCodeValueArray(Object obj) {
		Class temp = obj.getClass();
		// Field[] fields = temp.getDeclaredFields();//update by gao
		Field[] fields = temp.getFields();// 支持父类属性查询，但只查询 public字段
		String[] text = new String[(fields.length) / 3];
		int n = 0, m = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				// Field field = temp.getDeclaredField(fieldvalue); //update by
				// gao
				Field field = temp.getField(fieldvalue);
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (n == 2) {
					if(null!=object){
						text[m] = object.toString();
					}
					m++;
					n = 0;
					j++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * <p>
	 * 查找要素所对应的值 以list形式返回， list封装Element实体，Element封装了每一个要素的信息
	 * </p>
	 * 
	 * @param obj
	 *            要素描述实体 ,code 模板里所有的要素
	 * 
	 * */
	public static List findEleCodeValueArray(Object obj, String[] code) {
		int len = code.length;
		List list = new ArrayList();
		try {
			for (int i = 0; i < len; i++) {
				Class temp = obj.getClass();
				// Field[] fields = temp.getDeclaredFields();
				Field[] fields = temp.getFields(); // update by gao
				// getDeclaredFields获取不到父类的属性,getFields
				// 只能获取public属性，我勒个擦
				String text = "";
				int n = 0;
				for (int j = 0; j < fields.length; j++) {
					String fieldvalue = fields[j].getName().toString().trim();
					// Field field = temp.getDeclaredField(fieldvalue);
					Field field = temp.getField(fieldvalue);
					field.setAccessible(true);
					Object object = field.get(obj);
					n++;
					if (n > 3) {
						n = 0;
						text = "";
						text += object.toString() + "@";
						n++;
					} else {
						text += object.toString() + "@";
					}
					if (object.toString().lastIndexOf("]") != -1) {
						String objectStr = object.toString().substring(2,
								object.toString().lastIndexOf("#]"));
						if (objectStr.trim().equals(code[i].trim())) {
							String str[] = text.split("@");
							Element element = new Element();
							element.setDepict(str[0]);
							element.setValue(str[1]);
							element.setCode(str[2]);
							list.add(element);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * <p>
	 * 查找要素所对应的值 以list形式返回， list封装Element实体，Element封装了每一个要素的信息
	 * </p>
	 * 
	 * @param obj要素描述实体
	 *            ,code 模板里所有的要素 add by zcb
	 * */
	public static List findEleCodeValueArray(Object obj, String[] code,
			String categoryId) {
		int len = code.length;
		List list = new ArrayList();
		try {
			for (int i = 0; i < len; i++) { // 模板
				Class temp = obj.getClass();
				Field[] fields = temp.getFields();
				String text = "";
				int n = 0;
				boolean flag = false;
				for (int j = 0; j < fields.length; j++) {
					String fieldvalue = fields[j].getName().toString().trim();
					Field field = temp.getField(fieldvalue);
					field.setAccessible(true);
					Object object = field.get(obj);
					n++;
					if (null != object) {
						if (n > 3) {
							n = 0;
							text = "";
							text += object.toString() + "@";
							n++;
						} else {
							text += object.toString() + "@";
						}
						if (object.toString().lastIndexOf("]") != -1) {
							String objectStr = object.toString().substring(2,
									object.toString().lastIndexOf("#]"));
							if (objectStr.trim().equals(code[i].trim())) {
								String str[] = text.split("@");
								if(str.length==3){
									Element element = new Element();
									element.setDepict(str[0]);
									element.setValue(str[1]);
									element.setCode(str[2]);
									element.setElementType("系统要素");
									list.add(element);
									flag = true;
								}
							}
						}
					}
				}
				// 如果不存在模板要素
				if (!flag && !"true".equals(code[i])) {
					VProcreditContractDao vProcreditContractDao = (VProcreditContractDao) AppUtil
							.getBean("vProcreditContractDao");
					String hql = "from ContractElement as e  where e.contractId = ?";
					List list2 = null;
					if(null != categoryId){
						list2 =vProcreditContractDao.getListByCategoryId(Integer.parseInt(categoryId));
					}
					if (null != list2 && list2.size()>0) {
						for (int k = 0; k < list2.size(); k++) {
							Element element = new Element();
							ContractElement cElement = (ContractElement) list2
									.get(k);
							element.setValue(cElement.getElementValue());
							element.setDepict(cElement.getElementName());
							element.setCode("[#" + code[i] + "#]");
							element.setElementType("自定义要素");
							if (code[i].equals(cElement.getElementName())) {
								list.add(element);
							}
						}
					} else {
						Element element = new Element();
						element.setValue("");
						element.setDepict(code[i]);
						element.setCode("[#" + code[i] + "#]");
						element.setElementType("自定义要素");
						list.add(element);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * <p>
	 * 以HTML格式打开模板文件.
	 * </p>
	 * 
	 * @param path
	 *            模板文件路径 ,name 模板文件名称
	 * 
	 * */
	public static String openFileByHTML(String path) {
		BufferedWriter writer = null;
		BufferedReader br = null;
		File file = null;
		String uploadPath = path;
		String htmlName = "", readPath = "", newFolderPath = "", htmlPath = "", tempString = "";
		StringBuffer sbuffer = new StringBuffer();
		htmlName = "test.htm";
		newFolderPath = uploadPath.substring(0, uploadPath.lastIndexOf("\\"))
				+ "\\testfile";
		file = new File(newFolderPath);
		if (!file.exists()) {
			FileHelper.newFolder(newFolderPath);
		}
		htmlPath = newFolderPath + "\\" + htmlName;
		file = new File(htmlPath);
		if (!file.exists()) {
			JacobWord.getInstance().ChageFormat(uploadPath, htmlPath);
		}
		return htmlPath;
	}

	/**
	 * <p>
	 * 查找要素编码 要素编码以数组的形式返回
	 * </p>
	 * 
	 * @param 要素描述实体
	 *            add by zcb 2013-10-09
	 * */
	public static String[] findAllEleCodeArray(Object obj, String categoryId) {
		Class temp = obj.getClass();
		Field[] fields = temp.getFields();
		// ---add by zcb begin
		// 如果存在模板要素
		VProcreditContractDao vProcreditContractDao = (VProcreditContractDao) AppUtil
				.getBean("vProcreditContractDao");
		List list2 = null;
		try {
			list2 =vProcreditContractDao.getListByCategoryId(Integer.parseInt(categoryId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = null != list2 ? list2.size() + (fields.length) / 3
				: (fields.length) / 3;
		// ---add by zcb end
		String[] text = new String[size];
		int n = 0, m = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				Field field = temp.getField(fieldvalue);
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (n == 3) {
					text[m] = object.toString();
					m++;
					n = 0;
				}
			}
			if (null != list2) {
				for (int k = 0; k < list2.size(); k++) {
					ContractElement cElement = (ContractElement) list2.get(k);
					text[m] = "[#" + cElement.getElementName() + "#]";
					m++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * <p>
	 * 查找要素所对应的值 以数组的形式返回
	 * </p>
	 * 
	 * @param 要素描述实体
	 *            add by zcb 2013-10-09
	 * */
	public static String[] findAllEleCodeValueArray(Object obj,
			String categoryId) {
		Class temp = obj.getClass();
		Field[] fields = temp.getFields();
		// 如果存在模板要素
		VProcreditContractDao vProcreditContractDao = (VProcreditContractDao) AppUtil
				.getBean("vProcreditContractDao");
		List list2 = null;
		try {
			list2 =vProcreditContractDao.getListByCategoryId(Integer.parseInt(categoryId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = null != list2 ? list2.size() + (fields.length) / 3
				: (fields.length) / 3;
		// ---add by zcb end
		String[] text = new String[size];
		int n = 0, m = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				Field field = temp.getField(fieldvalue);
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (n == 2) {
					if (null != object) {
						text[m] = object.toString();
					}
					m++;
					n = 0;
					j++;
				}
			}
			if (null != list2) {
				for (int k = 0; k < list2.size(); k++) {
					ContractElement cElement = (ContractElement) list2.get(k);
					text[m] = cElement.getElementValue();
					m++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
	 * <p>
	 * 替换模板中的要素
	 * </p>
	 * 
	 * @param
	 * 
	 * */
	public static String replaceAllEleme(Object obj, String path, String name) {
		BufferedReader br = null;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		File file = null;
		String uploadPath = path;
		String fileName = name;
		String htmlName = "", newFolderPath = "", htmlPath = "", tempString = "";
		StringBuffer sbuffer = new StringBuffer();
		file = new File(path);
		if (file.exists()) {
			htmlPath = uploadPath
					.substring(0, uploadPath.lastIndexOf("\\") + 1)
					+ name;
			file = new File(htmlPath);
			if (!file.exists()) {
				FileHelper.copyFile(path, htmlPath);
			}
		} else {
			htmlPath = "false";
		}
		// --
		/*
		 * if(fileName.indexOf(".") == -1){ htmlName = fileName + ".htm" ; }
		 * htmlName = fileName.substring(0, fileName.lastIndexOf("."))+ ".htm" ;
		 * newFolderPath = uploadPath.substring(0,
		 * uploadPath.lastIndexOf("\\"))+"\\testfile"; file = new
		 * File(newFolderPath); if (!file.exists()) {
		 * FileHelper.newFolder(newFolderPath); } htmlPath = newFolderPath +
		 * "\\" +htmlName; file = new File(htmlPath) ; if(!file.exists()){
		 * JacobWord.getInstance().ChageFormat(uploadPath,htmlPath) ; } try { br
		 * = new BufferedReader(new InputStreamReader(new FileInputStream(file),
		 * "gbk")) ; int line = 1 , i = 0; while ((tempString = br.readLine())
		 * != null){ sbuffer.append(tempString); line ++ ; } } catch
		 * (IOException e) { e.printStackTrace(); }finally{ try { if(null !=
		 * br){ br.close() ; } } catch (IOException e) { e.printStackTrace(); }
		 * } Class temp = obj.getClass(); Field[] fields =
		 * temp.getDeclaredFields(); tempString =
		 * sbuffer.toString().replace("charset=gb2312", "charset=UTF-8") ;
		 * String text = "" ; int n = 0 ; try{ for(int j = 0 ; j < fields.length
		 * ; j ++){ String fieldvalue = fields[j].getName().toString().trim() ;
		 * Field field = temp.getDeclaredField(fieldvalue);
		 * field.setAccessible(true); Object object = field.get(obj) ; n ++ ;
		 * if(n > 3){ String str[] = text.split("@"); tempString =
		 * tempString.replace(str[2].trim(), str[1].trim()) ; n = 0 ; text = ""
		 * ; text += object.toString() + "@"; n ++ ; }else{ text +=
		 * object.toString() + "@"; } } }catch(Exception e){ e.printStackTrace()
		 * ; } String readPath =serversPath(uploadPath)+
		 * "\\credit\\document\\contract.html" ; try { fos = new
		 * FileOutputStream(new File(readPath)); osw = new
		 * OutputStreamWriter(fos, "UTF-8"); osw.write(tempString.toString()); }
		 * catch (Exception e) { e.printStackTrace(); }finally{ try { if(null !=
		 * osw){ osw.close() ; }if(null != fos){ fos.close() ; } } catch
		 * (IOException e) { e.printStackTrace(); } }
		 */// --
		return htmlPath;
	}

	/**
	 * <p>
	 * 下载文档
	 * </p>
	 * */
	public static void downloadFile(String path, HttpServletResponse response) {
		boolean isInline = false; // 是否允许直接在浏览器内打开
		InputStream fis = null;
		OutputStream toClient = null;
		File file = null;
		try {
			file = new File(path);
			String filename = new String(file.getName().getBytes(), "ISO8859-1");
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();
			response.reset();
			String inlineType = isInline ? "inline" : "attachment"; // 是否内联附件
			// response.setHeader ("Content-Disposition", inlineType +
			// ";filename=\"" + filename + "\"");
			response.setHeader("content-disposition", "attachment;filename=\""
					+ filename + "\"");
			fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream;charset=gbk");
			toClient.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
				if (null != toClient) {
					toClient.close();
					toClient.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void downloadFile(File file, HttpServletResponse response) {
		boolean isInline = false; // 是否允许直接在浏览器内打开
		InputStream fis = null;
		OutputStream toClient = null;
		try {
			String filename = new String(file.getName().getBytes(), "ISO8859-1");
			String ext = filename.substring(filename.lastIndexOf(".") + 1)
					.toUpperCase();
			response.reset();
			String inlineType = isInline ? "inline" : "attachment"; // 是否内联附件
			response.setHeader("content-disposition", "attachment;filename=\""
					+ filename + "\"");
			fis = new BufferedInputStream(new FileInputStream(file.getPath()));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream;charset=gbk");
			toClient.write(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fis) {
					fis.close();
				}
				if (null != toClient) {
					toClient.close();
					toClient.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		ElementCode elementCode = new ElementCode();
		String h[] = findEleCodeArray(elementCode);
	}
	
	/** 查看定量要素
	 * add by linyan
	 * 2013-10-31
	 *  */
	public static List getCreditManageListElement(Object obj) {
		Class temp = obj.getClass();
		Field[] fields = temp.getFields();
		List list = new ArrayList();
		String text = "";
		int n = 0;
		try {
			for (int j = 0; j < fields.length; j++) {
				String fieldvalue = fields[j].getName().toString().trim();
				// Field field = temp.getDeclaredField(fieldvalue); //update by
				// gao
				Field field = temp.getField(fieldvalue); // update by gao
				field.setAccessible(true);
				Object object = field.get(obj);
				n++;
				if (j <= fields.length - 5) {
					if (n > 5) {
						String str[] = text.split("@");
						Element element = new Element();
						element.setDepict(str[0]);
						element.setValue(str[1]);
						element.setCode(str[2]);
						element.setOperationType(str[3]);
						element.setOperationTypeKey(str[4]);
						list.add(element);
						n = 0;
						text = "";
						text += object.toString() + "@";
						n++;
					} else {
						text += object.toString() + "@";
					}
				} else {
					if (n == 5) {
						String str[] = text.split("@");
						Element element = new Element();
						element.setDepict(str[0]);
						element.setValue(str[1]);
						element.setCode(str[2]);
						element.setOperationType(str[3]);
						element.setOperationTypeKey(object.toString());
						list.add(element);
					} else {
						text += object.toString() + "@";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
