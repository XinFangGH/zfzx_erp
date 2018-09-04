package com.zhiwei.core.web.servlet;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.FileUtil;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.system.FileAttachService;
import java.io.*;
import java.util.*;

import org.apache.tools.zip.*;
import org.apache.tools.zip.ZipEntry;

/**
 * 文件上传类
 * 
 * @author 智维软件
 * 
 */
public class JasperUploadServlet extends HttpServlet {

	private Log logger = LogFactory.getLog(FileUploadServlet.class);

	private ServletConfig servletConfig = null;

	private FileAttachService fileAttachService = (FileAttachService) AppUtil
			.getBean("fileAttachService");

	private String uploadPath = ""; // 上传文件的目录
	private String tempPath = ""; // 临时文件目录

	private String fileCat = "others";
	// 指定保存至某个目录,若提交时，指定了该参数值，则表示保存的操作　
	private String filePath = "";

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String extractZip = req.getParameter("extractZip");

		try {

			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 缓存大小
			factory.setSizeThreshold(4096);
			factory.setRepository(new File(tempPath));
			ServletFileUpload fu = new ServletFileUpload(factory);

			List<FileItem> fileItems = fu.parseRequest(req);
			// 取得相关参数值
			for (FileItem fi : fileItems) {
				if ("file_cat".equals(fi.getFieldName())) {
					fileCat = fi.getString();
					// break;
				}
				if ("file_path".equals(fi.getFieldName())) {
					filePath = fi.getString();
				}
			}

			Iterator i = fileItems.iterator();
			// 目前处理每次只上传一个文件
			while (i.hasNext()) {
				/**************** 上传文件 *************************************************/
				FileItem fi = (FileItem) i.next();
				String fileContentType = fi.getContentType();
				if (fileContentType == null) {
					continue;
				}
				if (fi.getContentType() == null) {
					continue;
				}

				// 返回文件名及路径及fileId.
				String path = fi.getName();

				int start = path.lastIndexOf("\\");

				// 原文件名
				String fileName = path.substring(start + 1);

				String relativeFullPath = null;
				String generName = FileUtil.generateFilename(fileName);

				int sindex = generName.lastIndexOf("/");
				int eindex = generName.lastIndexOf(".");
				String generDir = generName.substring(sindex + 1, eindex);

				generName = generName.substring(0, sindex) + "/" + generDir
						+ "/"
						+ generName.substring(sindex + 1, generName.length());

				if (!"".equals(filePath)) {
					relativeFullPath = filePath;
				} else {
					relativeFullPath = fileCat + "/" + generName;
				}

				int index = relativeFullPath.lastIndexOf("/");

				File dirPath = new File(uploadPath + "/"
						+ relativeFullPath.substring(0, index + 1));

				if (!dirPath.exists()) {
					dirPath.mkdirs();
				}

				File temFile = new File(uploadPath + "/" + relativeFullPath);
				fi.write(temFile);

				/******** 解压文件 *********************************************************/
				if (fileContentType.equals("application/zip")) {
					byte b[] = new byte[1024];

					int length;

					org.apache.tools.zip.ZipFile zipFile = new ZipFile(temFile);

					Enumeration enumeration = zipFile.getEntries();

					ZipEntry zipEntry = null;

					while (enumeration.hasMoreElements()) {

						zipEntry = (ZipEntry) enumeration.nextElement();

						if (zipEntry.getName().endsWith(".jasper")) {
							int indx = relativeFullPath.lastIndexOf("/");
							relativeFullPath = relativeFullPath.substring(0,
									indx);
							relativeFullPath = relativeFullPath + "/"
									+ zipEntry.getName();
						}

						File loadFile = new File(dirPath + "/"
								+ zipEntry.getName());

						if (zipEntry.isDirectory()) {

							loadFile.mkdirs();

						} else {

							if (!loadFile.getParentFile().exists()) {

								loadFile.getParentFile().mkdirs();

							}
							OutputStream outputStream = new FileOutputStream(
									loadFile);

							InputStream inputStream = zipFile
									.getInputStream(zipEntry);

							while ((length = inputStream.read(b)) > 0)

								outputStream.write(b, 0, length);
							outputStream.close();
							inputStream.close();
						}

					}

					/****** 删除文件 ************************************************************************/
					zipFile.close();
					temFile.delete();

				}

				/****** 保数据库 ************************************************************************/

				FileAttach file = null;
				if (!"".equals(filePath)) {
					file = fileAttachService.getByPath(filePath);
				}

				if (file == null) {
					logger.debug("relativeFullPath=" + relativeFullPath);
					file = new FileAttach();
					file.setCreatetime(new Date());
					AppUser curUser = ContextUtil.getCurrentUser();
					if (curUser != null) {
						file.setCreator(curUser.getFullname());
					} else {
						file.setCreator("UNKown");
					}
					int dotIndex = fileName.lastIndexOf(".");
					file.setExt(fileName.substring(dotIndex + 1));
					file.setFileName(fileName);
					file.setFilePath(relativeFullPath);
					file.setFileType(fileCat);
					file.setNote(fi.getSize() + " bytes");
					file.setTotalBytes(fi.getSize());
					fileAttachService.save(file);
				}

				StringBuffer sb = new StringBuffer("{success:true");
				sb.append(",fileId:").append(file.getFileId()).append(
						",fileName:'").append(file.getFileName()).append(
						"',filePath:'").append(file.getFilePath()).append(
						"',message:'upload file success.(" + fi.getSize()
								+ " bytes)'");
				sb.append("}");

				resp.setContentType("text/html;charset=UTF-8");
				PrintWriter writer = resp.getWriter();
				writer.println(sb.toString());

			}

		} catch (Exception e) {
			e.printStackTrace();
			resp.getWriter().write(
					"{'success':false,'message':'error..." + e.getMessage()
							+ "'}");
		}

	}

	// 中文编码
	public static String make8859toGB(String str) {
		try {
			String str8859 = new String(str.getBytes("8859_1"), "GB2312");
			return str8859;
		} catch (UnsupportedEncodingException ioe) {
			ioe.printStackTrace();
			return str;
		}
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		this.servletConfig = config;
	}

	public void init() throws ServletException {

		// 初始化上传的路径及临时文件路径

		uploadPath = getServletContext().getRealPath("/attachFiles/");

		File uploadPathFile = new File(uploadPath);
		if (!uploadPathFile.exists()) {
			uploadPathFile.mkdirs();
		}
		tempPath = uploadPath + "/temp";

		File tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
	}

	/*------------------------------------------------------------
	保存文档到服务器磁盘，返回值true，保存成功，返回值为false时，保存失败。
	--------------------------------------------------------------*/
	public boolean saveFileToDisk(String officefileNameDisk) {
		File officeFileUpload = null;
		FileItem officeFileItem = null;

		boolean result = true;
		try {
			if (!"".equalsIgnoreCase(officefileNameDisk)
					&& officeFileItem != null) {
				officeFileUpload = new File(uploadPath + officefileNameDisk);
				officeFileItem.write(officeFileUpload);
			}
		} catch (FileNotFoundException e) {

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

}
