package com.zhiwei.credit.action.htmobile.util;

/*
 *  广州宏天软件有限公司 OA办公管理系统   -- http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.util.UUIDGenerator;

public class FileUtil {

	private static Log logger = LogFactory.getLog(FileUtil.class);

	public static String generateFilename(String originalFilename) {

		SimpleDateFormat dirSdf = new SimpleDateFormat("yyyyMM");
		String filePre = dirSdf.format(new Date());

		String fileExt = "";
		int lastIndex = originalFilename.lastIndexOf('.');
		// 取得文件的扩展名
		if (lastIndex != -1) {
			fileExt = originalFilename.substring(lastIndex);
		}

		String filename = filePre + "/" + UUIDGenerator.getUUID() + fileExt;

		return filename;
	}

	/**
	 * 把数据写至文件中
	 * 
	 * @param filePath
	 * @param data
	 */
	public static void writeFile(String filePath, String data) {
		FileOutputStream fos = null;
		OutputStreamWriter writer = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			writer = new OutputStreamWriter(fos, "UTF-8");
			writer.write(data);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 读取文件内容
	 * 
	 * @param filePath
	 * @return
	 */
	public static String readFile(String filePath) {
		StringBuffer buffer = new StringBuffer();
		// 读出这个文件的内容
		try {
			File file = new File(filePath);
			FileInputStream fis = null;
			BufferedReader breader = null;
			try {
				fis = new FileInputStream(file);
				InputStreamReader isReader = new InputStreamReader(fis, "UTF-8");
				breader = new BufferedReader(isReader);
				String line;
				while ((line = breader.readLine()) != null) {
					buffer.append(line);
					buffer.append("\r\n");
				}
				breader.close();
				isReader.close();
				fis.close();

			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return buffer.toString();
	}

	/**
	 * stream 转为字符串
	 * 
	 * @param input
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String inputStream2String(InputStream input, String charset)
			throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(input,
				charset));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}
		return buffer.toString();

	}

	/**
	 * 获取文件的字符集
	 * 
	 * @param file
	 * @return
	 */
	public static String getCharset(File file) {
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			boolean checked = false;
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			bis.mark(0);
			int read = bis.read(first3Bytes, 0, 3);
			if (read == -1)
				return charset;
			if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
				charset = "UTF-16LE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {
				charset = "UTF-16BE";
				checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {
				charset = "UTF-8";
				checked = true;
			}
			bis.reset();

			if (!checked) {
				// int loc = 0;
				while ((read = bis.read()) != -1) {
					// loc++;
					if (read >= 0xF0)
						break;
					// 单独出现BF以下的，也算是GBK
					if (0x80 <= read && read <= 0xBF)
						break;
					if (0xC0 <= read && read <= 0xDF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
							// (0x80 -
							// 0xBF),也可能在GB编码内
							continue;
						else
							break;
						// 也有可能出错，但是几率较小
					} else if (0xE0 <= read && read <= 0xEF) {
						read = bis.read();
						if (0x80 <= read && read <= 0xBF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								charset = "UTF-8";
								break;
							} else
								break;
						} else
							break;
					}
				}

			}
			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}

	/**
	 * 判断一个文件是否存在
	 * 
	 * @param filePath
	 *            文件路径
	 * @return 存在返回true，否则返回false
	 */
	public static boolean isExist(String filePath) {
		return new File(filePath).exists();
	}

	public static String getClassesPath() {
		String templatePath = FileUtil.class.getClassLoader().getResource("/").getPath();
		String rootPath = "";
		//windows下
		if("\\".equals(File.separator)){
			rootPath = templatePath.replace("/", "\\");
		}
		
		//linux下
		if("/".equals(File.separator)){
			rootPath = templatePath.replace("\\", "/");
		}
		
		System.out.println("测试路径："+rootPath);
		return rootPath;
	}
}
