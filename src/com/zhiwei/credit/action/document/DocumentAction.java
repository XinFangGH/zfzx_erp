package com.zhiwei.credit.action.document;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.jfree.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;

import com.zhiwei.credit.model.document.DocFolder;
import com.zhiwei.credit.model.document.Document;
import com.zhiwei.credit.model.document.DocumentFile;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Department;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.document.DocFolderService;
import com.zhiwei.credit.service.document.DocPrivilegeService;
import com.zhiwei.credit.service.document.DocumentService;
import com.zhiwei.credit.service.iText.HtmlContentToPdf;
import com.zhiwei.credit.service.iText.PdfToSwf;
import com.zhiwei.credit.service.system.FileAttachService;

/**
 * @description 文档管理
 * @class DocumentAction
 * @extends BaseAction
 * @author csx,YHZ
 * @company www.credit-software.com
 * @createtime 2011-6-10AM
 * 
 */
public class DocumentAction extends BaseAction {
	@Resource
	private DocumentService documentService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private DocFolderService docFolderService;
	@Resource
	private DocPrivilegeService docPrivilegeService;

	private Long docId;
	private AppUser appUser;
	private Document document;
	private Date from;
	private Date to;

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public Long getDocId() {
		return docId;
	}

	public void setDocId(Long docId) {
		this.docId = docId;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * 文档共享
	 * 
	 * @return
	 */
	public String share() {
		HttpServletRequest request = getRequest();
		String userIds = request.getParameter("sharedUserIds");
		String depIds = request.getParameter("sharedDepIds");
		String roleIds = request.getParameter("sharedRoleIds");
		String docId = request.getParameter("docId");
		String userNames = request.getParameter("sharedUserNames");
		String depNames = request.getParameter("sharedDepNames");
		String roleNames = request.getParameter("sharedRoleNames");
		if (StringUtils.isNotEmpty(userIds) || StringUtils.isNotEmpty(depIds)
				|| StringUtils.isNotEmpty(roleIds)) {
			Document doc = documentService.get(new Long(docId));
			doc.setSharedUserIds(userIds);
			doc.setSharedRoleIds(roleIds);
			doc.setSharedDepIds(depIds);
			doc.setSharedUserNames(userNames);
			doc.setSharedDepNames(depNames);
			doc.setSharedRoleNames(roleNames);
			doc.setIsShared(Document.SHARED);
			documentService.save(doc);
		} else {
			Document doc = documentService.get(new Long(docId));
			doc.setSharedUserIds("");
			doc.setSharedRoleIds("");
			doc.setSharedDepIds("");
			doc.setSharedUserNames("");
			doc.setSharedDepNames("");
			doc.setSharedRoleNames("");
			doc.setIsShared(Document.NOT_SHARED);
			documentService.save(doc);

		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 文档共享
	 * 
	 * @return
	 */
	public String unshare() {
		HttpServletRequest request = getRequest();
		String docId = request.getParameter("docId");
		if (StringUtils.isNotEmpty(docId)) {
			Document doc = documentService.get(new Long(docId));
			doc.setSharedUserIds("");
			doc.setSharedRoleIds("");
			doc.setSharedDepIds("");
			doc.setSharedUserNames("");
			doc.setSharedDepNames("");
			doc.setSharedRoleNames("");
			doc.setIsShared(Document.NOT_SHARED);
			documentService.save(doc);
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示共享列表
	 */

	public String shareList() {
		PagingBean pb = getInitPagingBean();
		AppUser appUser = ContextUtil.getCurrentUser();
		Set<AppRole> appRoles = appUser.getRoles();
		Long depId = null;
		if (!appUser.getUserId().equals(AppUser.SUPER_USER)) {
			Department dep = appUser.getDepartment();
			depId = dep.getDepId();
		}
		Iterator<AppRole> it = appRoles.iterator();
		ArrayList<Long> arrayList = new ArrayList<Long>();
		while (it.hasNext()) {
			arrayList.add(it.next().getRoleId());
		}
		List<Document> list = documentService.findByIsShared(document, from,
				to, appUser.getUserId(), arrayList, depId, pb);
		Type type = new TypeToken<List<Document>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		setJsonString(buff.toString());
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_docFolder.appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		String folderId = getRequest().getParameter("folderId");
		String path = null;
		if (StringUtils.isNotEmpty(folderId) && !"0".equals(folderId)) {
			path = docFolderService.get(new Long(folderId)).getPath();
		}
		if (path != null) {
			filter.addFilter("Q_docFolder.path_S_LK", path + "%");
		}
		List<Document> list = documentService.getAll(filter);
		Type type = new TypeToken<List<Document>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 公共文档列表
	 */

	public String publicList() {
		PagingBean pb = getInitPagingBean();
		String strFolderId = getRequest().getParameter("folderId");
		String path = null;
		if (StringUtils.isNotEmpty(strFolderId)) {
			Long folderId = new Long(strFolderId);
			if (folderId > 0) {
				path = docFolderService.get(new Long(strFolderId)).getPath();
			}
		}
		List<Document> list = documentService.findByPublic(path, document,
				from, to, ContextUtil.getCurrentUser(), pb);
		Type type = new TypeToken<List<Document>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				// TODO 删除对应的附件文件
				documentService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		Document document = documentService.get(docId);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(document));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		String msg = "{success:true}";
		String fileIds = getRequest().getParameter("fileIds");
		String folderId = getRequest().getParameter("folderId");
		document.setSharedDepIds(document.getSharedDepIds());
		document.setSharedRoleIds(document.getSharedRoleIds());
		document.setSharedUserIds(document.getSharedUserIds());
		if (StringUtils.isNotEmpty(fileIds)) {
			document.getAttachFiles().clear();
			String[] fIds = fileIds.split(",");
			for (int i = 0; i < fIds.length; i++) {
				FileAttach fileAttach = fileAttachService
						.get(new Long(fIds[i]));
				document.getAttachFiles().add(fileAttach);
			}
		}

		if (StringUtils.isNotEmpty(folderId) && !"0".equals(folderId)) {
			DocFolder folder = docFolderService.get(new Long(folderId));
			document.setDocFolder(folder);
		}
		if (document.getDocId() == null) {
			AppUser appUser = ContextUtil.getCurrentUser();
			document.setAppUser(appUser);
			document.setFullname(appUser.getFullname());
			document.setCreatetime(new Date());
			document.setUpdatetime(new Date());
			document.setIsShared(Document.NOT_SHARED);
			// 包括附件
			if (document.getAttachFiles().size() > 0) {
				document.setHaveAttach(Document.HAVE_ATTACH);
			} else {
				document.setHaveAttach(Document.NOT_HAVE_ATTACH);
			}
			documentService.save(document);

		} else {
			Document doc = documentService.get(document.getDocId());
			doc.setUpdatetime(new Date());
			doc.setDocName(document.getDocName());
			doc.setContent(document.getContent());
			doc.setAuthor(document.getAuthor());
			doc.setKeywords(document.getKeywords());
			doc.setDocFolder(document.getDocFolder());
			doc.setAttachFiles(document.getAttachFiles());
			if (document.getAttachFiles().size() > 0) {
				doc.setHaveAttach(Document.HAVE_ATTACH);
			} else {
				doc.setHaveAttach(Document.NOT_HAVE_ATTACH);
			}
//			Document docNew = documentService.save(doc);
//			if (docNew != null) {
//				docId = docNew.getDocId();
//				msg = toSwf();
//			}
			documentService.save(doc);
		}
		setJsonString(msg);
		return SUCCESS;
	}

	/**
	 * 文档页面详细信息显示
	 * 
	 * 
	 * 
	 */
	public String detail() {
		String strDocId = getRequest().getParameter("docId");
		if (StringUtils.isNotEmpty(strDocId)) {
			Long docId = Long.parseLong(strDocId);
			document = documentService.get(docId);
		}
		return "detail";
	}

	public String publicDetail() {
		String strDocId = getRequest().getParameter("docId");
		if (StringUtils.isNotEmpty(strDocId)) {
			Long docId = Long.parseLong(strDocId);
			document = documentService.get(docId);
		}
		return "publicDetail";
	}

	/**
	 * 获取权限的分配
	 * 
	 * @return
	 */
	public String right() {
		String strDocId = getRequest().getParameter("docId");
		Integer right = 0;
		Document document = new Document();
		AppUser appUser = ContextUtil.getCurrentUser();
		if (StringUtils.isNotEmpty(strDocId)) {
			Long docId = new Long(strDocId);
			right = docPrivilegeService.getRightsByDocument(appUser, docId);
			document = documentService.get(docId);
		}
		Integer rightD = 0;
		Integer rightM = 0;
		String strRight = Integer.toBinaryString(right);
		char[] cc = strRight.toCharArray();
		if (cc.length == 2) {
			if (cc[0] == '1') {
				rightM = 1;
			}
		}
		if (cc.length == 3) {
			if (cc[0] == '1') {
				rightD = 1;
			}
			if (cc[1] == '1') {
				rightM = 1;
			}
		}

		setJsonString("{success:true,rightM:'" + rightM + "',rightD:'" + rightD
				+ "',docName:'" + document.getDocName() + "'}");
		return SUCCESS;
	}

	public String search() {
		PagingBean pb = getInitPagingBean();
		String content = getRequest().getParameter("content");
		AppUser appUser = ContextUtil.getCurrentUser();
		List<Document> list = documentService.searchDocument(appUser, content,
				pb);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
				.create();
		Type type = new TypeToken<List<Document>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(pb.getTotalItems()).append(",result:");
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 首页显示的我的文档列表
	 */
	public String display() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_docFolder.appUser.userId_L_EQ", ContextUtil
				.getCurrentUserId().toString());
		List<Document> list = documentService.getAll(filter);
		getRequest().setAttribute("documentList", list);
		return "display";
	}

	/********************************* -在线文档- ******************************************/

	public String saveOnline() {
		String fileId = getRequest().getParameter("documentFileId");
		if (StringUtils.isNotEmpty(fileId)) {
			String folderId = getRequest().getParameter("folderId");
			if (StringUtils.isNotEmpty(folderId) && !"0".equals(folderId)) {
				DocFolder folder = docFolderService.get(new Long(folderId));
				document.setDocFolder(folder);
			}
			if (document.getDocId() == null) {
				FileAttach fileAttach = fileAttachService.get(new Long(fileId));
				document.getAttachFiles().add(fileAttach);
				document.setIsShared(Document.ONLINE_DOCUMENT);
				document.setAuthor(ContextUtil.getCurrentUser().getFullname());
				document.setFullname(ContextUtil.getCurrentUser().getFullname());
				document.setCreatetime(new Date());
				document.setUpdatetime(new Date());
				documentService.save(document);
			} else {
				Document orgDocument = documentService.get(document.getDocId());
				orgDocument.setDocName(document.getDocName());
				orgDocument.setUpdatetime(new Date());
				orgDocument.setDocFolder(document.getDocFolder());
				documentService.save(orgDocument);
			}
			setJsonString("{success:true}");
		} else {
			setJsonString("{success:false}");
		}

		return SUCCESS;
	}

	public String onlineList() {
		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_isShared_SN_EQ",
				Document.ONLINE_DOCUMENT.toString());
		List<Document> list = documentService.getAll(filter);
		List<DocumentFile> lists = new ArrayList<DocumentFile>();
		for (Document doc : list) {
			DocumentFile file = new DocumentFile();
			Set<FileAttach> filed = doc.getAttachFiles();
			FileAttach afile = filed.iterator().next();
			file.setFileId(doc.getDocId());
			file.setFileName(doc.getDocName());
			file.setFileSize(afile.getNote());
			file.setFileType("文档");
			file.setIsFolder(DocumentFile.NOT_FOLDER);
			file.setIsShared(doc.getIsShared());
			// file.setRightRead(rightR);
			// file.setRightMod(rightM);
			file.setAuthor(doc.getAuthor());
			file.setKeywords(doc.getKeywords());
			file.setUpdateTime(doc.getUpdatetime());
			// file.setRightDel(rightD);
			lists.add(file);
		}

		Gson gson = new Gson();
		Type type = new TypeToken<List<DocumentFile>>() {
		}.getType();
		StringBuffer sb = new StringBuffer("{success:true,result:");
		sb.append(gson.toJson(lists, type));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 将html内容转化为swf文件 步骤：1.html内容到pdf文档，2.pdf文档到swf文件
	 * 
	 * @return 转化成功:{success:true}，否则:{success:false,msg:''}
	 */
	@SuppressWarnings("unused")
	private String toSwf() {
		String msg = "{success:true}";
		Document documentInfo = documentService.get(docId);
		HtmlContentToPdf htmlContentToPdf = new HtmlContentToPdf();
		String fontPath = AppUtil.getAppAbsolutePath().replace("\\", "/")
				+ "pages/iText/fonts/SIMSUN.TTC";
		String sysType = getPropertiesOfValue("type", "windows");
		if (sysType.equals("linux")) { // linux操作系统的font字体文件
			fontPath = AppUtil.getAppAbsolutePath().replace("\\", "/")
					+ "pages/iText/fonts/linux/gkai00mp.ttf";
		}
		String outPdfPath = getPath(documentInfo.getDocId(), true); // pages/iText/pdf/year/month/文档id.pdf
		String pdfMsg = htmlContentToPdf.contentToPdf(
				documentInfo.getContent(), fontPath, outPdfPath);
		if (pdfMsg.equals("true")) {
			PdfToSwf pdfToSwf = new PdfToSwf();
			// String exeFilePath = getExeFilePath(); // exe可执行文件的url
			String exeFilePath = getPropertiesOfValue("swftools.exe.filepath",
					"C:/Program Files/SWFTools/pdf2swf.exe");
			String inputFilePath = getPath(documentInfo.getDocId(), true); // pdf文档的url
			String outputFilePath = getPath(documentInfo.getDocId(), false); // swf文件的url
			String swfMsg = pdfToSwf.start(exeFilePath, inputFilePath,
					outputFilePath);
			if (swfMsg.equals("true")) {
				Document swfDocument = documentService.get(docId);
				outputFilePath = outputFilePath
						.substring(AppUtil.getAppAbsolutePath().length(),
								outputFilePath.length());
				swfDocument.setSwfPath(outputFilePath); // 保存swf文件的路径
				Log.debug("生成pdf文档目录：" + inputFilePath);
				Log.debug("生成swf文件目录：" + outputFilePath);
				documentService.save(swfDocument);
				msg = "{success:true}";
			} else {
				msg = "{success:false,msg:'" + swfMsg + "'}";
			}
		} else {
			msg = "{success:false,msg:'" + pdfMsg + "'}";
		}
		return msg;
	}

	/**
	 * 构造pdf,swf文件的路径,eg: swf : attachFiles/iText/swf/year/month/dId.swf pdf :
	 * ../../attachFiles/iText/pdf/year/month/dId.pdf
	 * 
	 * @param dId
	 *            Long文档id
	 * @param isPdf
	 *            pdf文档:true,swf文件:false
	 * @return 完整的路径
	 */
	private String getPath(Long dId, boolean isPdf) {
		File file = null;
		try {
			StringBuffer sb = new StringBuffer(AppUtil.getAppAbsolutePath()
					.replace("\\", "/") + "attachFiles/iText/");
			// start create pdf|swf dictionary
			sb.append(isPdf ? "pdf" : "swf");
			file = new File(sb.toString());
			if (!file.exists()) {
				if (!file.mkdir()) {
					return "创建[" + sb.toString() + "]目录失败，请重新操作！";
				}
			}
			file = null;
			// end create pdf|swf dictionary

			sb.append("/");

			// start create year dictionary
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			sb.append(year);
			file = new File(sb.toString());
			if (!file.exists()) {
				if (!file.mkdir()) {
					return "创建[" + sb.toString() + "]目录失败，请重新操作！";
				}
			}
			file = null;
			// end create year dictionary

			sb.append("/");

			// start create month dictionary
			int month = cal.get(Calendar.MONTH) + 1;
			sb.append(month);
			file = new File(sb.toString());
			if (!file.exists()) {
				if (!file.mkdir()) {
					return "创建[" + sb.toString() + "]目录失败，请重新添加！";
				}
			}
			file = null;
			// end create month dictionary
			sb.append("/" + dId + ".");
			sb.append(isPdf ? "pdf" : "swf");
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "异常";
	}

	/**
	 * 从properties文件中获取SWFTools工具的安装目录
	 */
//	private String getExeFilePath() {
//		String path = "C:/Program Files/SWFTools/pdf2swf.exe"; // 默认路径
//		Properties prop = new Properties();
//		try {
//			String filePath = getSession().getServletContext().getRealPath(
//					"/WEB-INF/classes");
//			FileInputStream in = new FileInputStream(filePath
//					+ "/swftools.properties");
//			Reader reader = new InputStreamReader(in, "UTF-8");
//			prop.load(reader);
//			path = prop.getProperty("swftools.exe.filepath");
//		} catch (IOException e) {
//			e.printStackTrace();
//			Log.debug("读取swftools.properties文件异常！");
//		}
//		return path;
//	}

	/**
	 * 根据key值从properties文件中获取对应的value值
	 * 
	 * @param key
	 *            key值
	 * @param defaultValue
	 *            value的默认值
	 * @return value值
	 */
	private String getPropertiesOfValue(String key, String defaultValue) {
		String value = "";
		Properties prop = new Properties();
		try {
			String filePath = getSession().getServletContext().getRealPath(
					"/WEB-INF/classes");
			FileInputStream in = new FileInputStream(filePath
					+ "/swftools.properties");
			Reader reader = new InputStreamReader(in, "UTF-8");
			prop.load(reader);
			if (prop.getProperty(key) != null
					&& !prop.getProperty(key).equals("")) {
				value = prop.getProperty(key);
			} else {
				value = prop.getProperty(key, defaultValue);
			}
		} catch (IOException e) {
			e.printStackTrace();
			Log.debug("读取swftools.properties文件异常！");
		}
		return value;
	}
}
