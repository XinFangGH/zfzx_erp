package com.zhiwei.credit.action.communicate;

/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
 */
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.UIDFolder;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.pop3.POP3Folder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.CertUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.FileUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.communicate.OutMail;
import com.zhiwei.credit.model.communicate.OutMailFolder;
import com.zhiwei.credit.model.communicate.OutMailUserSeting;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.communicate.OutMailFolderService;
import com.zhiwei.credit.service.communicate.OutMailService;
import com.zhiwei.credit.service.communicate.OutMailUserSetingService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.FileAttachService;

/**
 * 
 * @author
 * 
 */
public class OutMailAction extends BaseAction {

	static long FOLDER_ID_RECEIVE = 1;// 收件箱
	static long FOLDER_ID_SEND = 2;// 发件箱
	static long FOLDER_ID_DRAFT = 3;// 草稿箱
	static long FOLDER_ID_DELETE = 4;// 删除箱
	static long FOLDER_TYPE_OTHER = 10;// 其它类型文件夹
	static short OTHER_FOLDER_TYPE = (short) 10;// 其它类型文件夹

	static int FIRST_LEVEL = 1;// 第一层
	static long FIRST_PARENTID = 0;// 第一层文件夹的parentId
	static short HAVE_DELETE = (short) 1;// 已删除
	static short NOT_DELETE = (short) 0;// 未删除
	static short HAVE_READ = (short) 1;// 已读
	static short NOT_READ = (short) 0;// 未读
	static Short HAVE_REPLY = (short) 1;// 已回复
	static short NOT_REPLY = (short) 0;// 未回复
	static String sendType = "smtp";
	static String FILE_PATH_ROOT = AppUtil.getAppAbsolutePath()
			+ "/attachFiles/";
	static String OUT_MAIL_TEMP = FILE_PATH_ROOT + "/outMailTemp/";

	@Resource
	private OutMailService outMailService;
	private OutMail outMail;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	private OutMailUserSeting outMailUserSeting;
	@Resource
	private AppUserService appUserService;
	private AppUser appUser;
	@Resource
	private OutMailFolderService outMailFolderService;
	private OutMailFolder outMailFolder;

	private Long mailId;
	private String outMailIds;// 移动邮件的集合
	private Long fileId;// 附件Id
	private Long folderId;// 文件夹

	public OutMailUserSeting getOutMailUserSeting() {
		return outMailUserSeting;
	}

	public void setOutMailUserSeting(OutMailUserSeting outMailUserSeting) {
		this.outMailUserSeting = outMailUserSeting;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getOutMailIds() {
		return outMailIds;
	}

	public void setOutMailIds(String outMailIds) {
		this.outMailIds = outMailIds;
	}

	public Long getFolderId() {
		return folderId;
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public OutMail getOutMail() {
		return outMail;
	}

	public void setOutMail(OutMail outMail) {
		this.outMail = outMail;
	}

	/**
	 * 邮箱地址内部类
	 * 
	 */

	protected class EmailAddress {
		protected String address;

		protected String name;

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public EmailAddress() {

		}

		public EmailAddress(String address, String name) {
			this.address = address;
			this.name = name;
		}

		public InternetAddress toInternetAddress() throws Exception {
			if (name != null && !name.trim().equals("")) {
				return new InternetAddress(address, MimeUtility.encodeWord(
						name, "utf-8", "Q"));
			}
			return new InternetAddress(address);
		}
	}

	/**
	 * 显示列表
	 */

	public String list() {

		// String isFetch=getRequest().getParameter("isFetch");

		// 如果选择收件箱，就到邮件服务器上，收了新邮件
		// if(isFetch!=null&&isFetch.equals("Y")){
		// isFetch=null;
		// fetch();
		// }

		if (folderId == null || folderId == FOLDER_ID_RECEIVE) {// 默认为收件箱
			setFolderId(new Long(FOLDER_ID_RECEIVE));
		}

		QueryFilter filter = new QueryFilter(getRequest());

		filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId()
				.toString());
		filter.addFilter("Q_outMailFolder.folderId_L_EQ", folderId.toString());

		filter.addSorted("mailId", "desc");
		List<OutMail> list = outMailService.getAll(filter);

		getOutMailJsonStr(list, filter.getPagingBean().getTotalItems());

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {
		OutMailFolder deleteFolder = outMailFolderService.get(FOLDER_ID_DELETE);

		String[] ids = getRequest().getParameterValues("ids");

		if (ids != null) {
			if (getFolderId() == FOLDER_ID_DELETE) {
				for (String id : ids) {
					outMail = outMailService.get(new Long(id));

					if (outMail != null) {

						Set<FileAttach> outMailFiles = outMail
								.getOutMailFiles();
						outMailService.remove(outMail);

						if (outMailFiles != null && outMailFiles.size() > 0) {
							for (FileAttach f : outMailFiles) {
								delPhysicalFile(f);
								fileAttachService.remove(f);
							}
						}

					}

				}
			} else {
				for (String id : ids) {

					outMail = outMailService.get(new Long(id));

					outMail.setOutMailFolder(deleteFolder);
					outMailService.save(outMail);
				}
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
		String opt = getRequest().getParameter("opt");

		if (opt == null || "".equals(opt)) {

			outMail = outMailService.get(mailId);
			getRequest().setAttribute("__haveNextOutMailFlag", "");
		} else {

			String folderId = getRequest().getParameter("folderId");
			if (folderId == null || "".equals(folderId)) {
				folderId = String.valueOf(FOLDER_ID_RECEIVE);// 假如文件夹ID为空,则默认为
				// 收件箱
			}

			List<OutMail> list = null;
			// 使用页面的方法实现获取上一条,下一条的记录
			QueryFilter filter = new QueryFilter(getRequest());
			filter.getPagingBean().setPageSize(1);// 只取一条记录

			filter.addFilter("Q_userId_L_EQ", ContextUtil.getCurrentUserId()
					.toString());// 只取当前用户收到的信息

			filter.addFilter("Q_outMailFolder.folderId_L_EQ", folderId);// 根据文件夹ID来取邮件
			// filter.addSorted("sendTime", "desc");
			if (opt.equals("_next")) {
				// 点击下一条按钮,则取比当前ID大的下一条记录
				filter.addFilter("Q_mailId_L_GT", mailId.toString());
				list = outMailService.getAll(filter);
				if (filter.getPagingBean().getStart() + 1 == filter
						.getPagingBean().getTotalItems()) {
					getRequest().setAttribute("__haveNextOutMailFlag",
							"endNext");
				}
			} else if (opt.equals("_pre")) {
				// 点击上一条按钮,则取比当前小的下一条记 录ID
				filter.addFilter("Q_mailId_L_LT", mailId.toString());
				filter.addSorted("mailId", "desc");
				list = outMailService.getAll(filter);
				if (filter.getPagingBean().getStart() + 1 == filter
						.getPagingBean().getTotalItems()) {
					getRequest()
							.setAttribute("__haveNextOutMailFlag", "endPre");
				}
			}

			if (list.size() > 0) {
				outMail = list.get(0);
			} else {
				outMail = outMailService.get(mailId);
			}
		}

		setOutMail(outMail);
		outMail.setReadFlag(HAVE_READ);// 设为已读
		outMailService.save(outMail);

		if (outMail.getFolderId() == FOLDER_ID_DRAFT) {// 假如为草稿
			List<OutMail> list = new ArrayList<OutMail>();
			list.add(outMail);
			setJsonString(getOutMailJsonStr(list, 1));
			return SUCCESS;
		} else {// 假如为查看
			if (outMail.getReceiverNames() == null
					|| outMail.getReceiverNames().equals("null")) {
				outMail.setReceiverNames("(收信人未写)");
			}
			getRequest().setAttribute("outMail_view", outMail);
			getRequest()
					.setAttribute("outMailFiles", outMail.getOutMailFiles());
			return "detail";
		}

	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		logger.debug("save start...");

		setJsonString("{success:true}");

		String opt = getRequest().getParameter("opt");

		appUser = appUserService.get(new Long(ContextUtil.getCurrentUserId()));// 员工
		outMailUserSeting = outMailUserSetingService.getByLoginId(ContextUtil
				.getCurrentUserId());// 邮箱设置
		outMailUserSeting.setAppUser(appUser);
		outMail.setUserId(outMailUserSeting.getAppUser().getUserId());
		outMail.setSenderAddresses(outMailUserSeting.getMailAddress());
		outMail.setSenderName(outMailUserSeting.getUserName());
		outMail.setMailDate(new Date());
		outMail.setReceiverAddresses(this.getAddressesToStr(outMail
				.getReceiverNames()));
		outMail
				.setReceiverNames(this
						.getNamesToStr(outMail.getReceiverNames()));
		outMail.setcCAddresses(this.getAddressesToStr(outMail.getcCNames()));
		outMail.setcCNames(this.getNamesToStr(outMail.getcCNames()));
		outMail.setReadFlag(new Short("0"));
		outMail.setReplyFlag(new Short("0"));

		// 附件
		Set<FileAttach> outMailFiles = new HashSet<FileAttach>();
		String[] fileIds = outMail.getFileIds().split(",");
		for (String id : fileIds) {
			if (!id.equals("") && !id.equals("null")) {
				outMailFiles.add(fileAttachService.get(new Long(id)));
			}
		}
		outMail.setOutMailFiles(outMailFiles);

		try {

			if (opt != null && opt.trim().equals("attach")) {// 存草稿
				outMailFolder = outMailFolderService.get(FOLDER_ID_DRAFT);// 草稿箱
				outMail.setOutMailFolder(outMailFolder);// 将邮件保存到草稿箱
				outMailService.save(outMail);
			} else {

				OutMail newOutMail = new OutMail();
				BeanUtil.copyNotNullProperties(newOutMail, outMail);
				if (newOutMail.getContent() == null
						|| newOutMail.getContent().equals("")) {
					newOutMail.setContent("		");
				}

				newOutMail.setMailId(null);
				outMailFolder = outMailFolderService.get(FOLDER_ID_SEND);// 发件箱
				newOutMail.setOutMailFolder(outMailFolder);// 将邮件保存到发件箱

				// 解析地址
				List<EmailAddress> reviceList = this.getEMailStrToList(
						newOutMail.getReceiverAddresses(), newOutMail
								.getReceiverNames());// 收件人
				List<EmailAddress> ccList = null;

				if (newOutMail.getcCAddresses() != null
						&& !newOutMail.getcCAddresses().trim().equals("")
						&& !newOutMail.getcCAddresses().trim().equals("null")
						&& newOutMail.getcCAddresses().length() > 2) {
					// 是否有抄送人
					ccList = this.getEMailStrToList(
							newOutMail.getcCAddresses(), newOutMail
									.getcCNames());// 抄送人
				}
				// 发送邮件
				this.send(newOutMail, reviceList, ccList, outMailUserSeting);
				outMailService.save(newOutMail);

			}

		} catch (Exception e) {
			e.printStackTrace();
			setJsonString("{failure:true,msg:'发送邮件失败!请检查书写的邮件格式是否正确!!'}");
			return SUCCESS;
		}
		logger.debug("save end...");
		return SUCCESS;
	}

	/**
	 * 草稿邮件删除附件
	 * 
	 * @return
	 */

	public String attach() {
		String fileIds = getRequest().getParameter("fileIds");
		String filenames = getRequest().getParameter("filenames");
		setOutMail(outMailService.get(mailId));
		Set<FileAttach> mailAttachs = outMail.getOutMailFiles();
		FileAttach remove = fileAttachService.get(fileId);
		delPhysicalFile(remove);// 删除物理文件
		mailAttachs.remove(remove);// 删除表
		outMail.setOutMailFiles(mailAttachs);
		outMail.setFileIds(fileIds);
		outMail.setFileNames(filenames);
		outMailService.save(outMail);
		fileAttachService.remove(fileId);
		return SUCCESS;
	}

	/**
	 * 移动邮件至指定文件夹
	 */
	public String move() {

		StringBuffer msg = new StringBuffer("{");
		if (outMailIds != null && outMailIds.length() > 0 && folderId != null) {// 断判要移到的文件,与文件不为空!
			OutMailFolder moveToFolder = outMailFolderService.get(new Long(
					folderId));// 要移到的文件夹
			String[] ids;// 要移动的文件

			ids = outMailIds.split(",");
			boolean moveSuccess = true;
			if (ids != null && ids.length > 0) {

				if (moveSuccess) {
					// 把id为包含在ids数组里的邮件移动至所选文件夹
					for (String id : ids) {
						if (!"".equals(id)) {
							outMail = outMailService.get(new Long(id));
							outMail.setOutMailFolder(moveToFolder);
							outMailService.save(outMail);

						}
					}
					msg.append("success:true}");
					setJsonString(msg.toString());
				} else {
					// 不合规则,不允许移动
					msg.append("failure:true}");
					setJsonString(msg.toString());
				}
			}
		} else {
			msg.append("msg:'请选择要移动的邮件及文件夹!'");
			msg.append(",failure:true}");
			setJsonString(msg.toString());
		}

		return SUCCESS;
	}

	/**
	 * 邮件回复和转发
	 */
	public String opt() {
		setOutMail(outMailService.get(mailId));
		String opt = getRequest().getParameter("opt");
		// 设为已读
		outMail.setReadFlag(this.HAVE_READ);
		if (opt != null && opt.trim().equals("回复")) {// 如果为回复
			// 设为回复
			outMail.setReplyFlag(this.HAVE_REPLY);

		}
		outMailService.save(outMail);

		StringBuffer newSubject = new StringBuffer(
				"<br><br><br><br><br><br><br><hr>");
		newSubject.append("<br>----<strong>" + opt + "邮件</strong>----");
		newSubject
				.append("<br><strong>发件人</strong>:" + outMail.getSenderName());
		newSubject.append("<br><strong>发送时间</strong>:" + outMail.getMailDate());
		newSubject.append("<br><strong>收件人</strong>:"
				+ outMail.getReceiverNames());
		String copyToNames = outMail.getcCNames();
		if (!"".equals(copyToNames) && copyToNames != null) {
			newSubject.append("<br><strong>抄送人</strong>:" + copyToNames);
		}
		newSubject.append("<br><strong>主题</strong>:" + outMail.getTitle());
		newSubject.append("<br><strong>内容</strong>:<br><br>"
				+ outMail.getContent());
		outMail.setContent(newSubject.toString());
		outMail.setTitle(opt + ":" + outMail.getTitle());

		List<OutMail> list = new ArrayList<OutMail>();
		list.add(outMail);

		setJsonString(getOutMailJsonStr(list, 1));
		return SUCCESS;
	}

	/**
	 * 
	 * @param list
	 * @param totalCounts
	 * @return 将List<OutMail> 转化为jsonstring
	 * 
	 */
	protected String getOutMailJsonStr(List<OutMail> list, int totalCounts) {
		java.text.SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		StringBuffer buff = new StringBuffer("{success:true,\"totalCounts\":")
				.append(totalCounts).append(",result:[");
		for (OutMail mailTemp : list) {

			buff.append("{")

			.append("\"mailId\":").append(mailTemp.getMailId())

			.append(",\"title\":").append(
					"\"" + changSpecialCharacters(mailTemp.getTitle()) + "\"")

			.append(",\"content\":")
					.append(
							"\""
									+ changSpecialCharacters(mailTemp
											.getContent()) + "\"")

					.append(",\"senderAddresses\":").append(
							"\"" + mailTemp.getSenderAddresses() + "\"")

					.append(",\"receiverAddresses\":").append(
							"\"" + mailTemp.getReceiverAddresses() + "\"")

					.append(",\"cCAddresses\":").append(
							"\"" + mailTemp.getcCAddresses() + "\"")

					.append(",\"cCNames\":").append(
							"\""
									+ changSpecialCharacters(mailTemp
											.getcCNames()) + "\"")

					.append(",\"receiverNames\":").append(
							"\""
									+ changSpecialCharacters(mailTemp
											.getReceiverNames()) + "\"")

					.append(",\"senderName\":").append(
							"\""
									+ changSpecialCharacters(mailTemp
											.getSenderName()) + "\"")

					.append(",\"mailDate\":").append(
							"\"" + df.format(mailTemp.getMailDate()) + "\"")

					.append(",\"readFlag\":").append(mailTemp.getReadFlag())

					.append(",\"replyFlag\":").append(mailTemp.getReplyFlag())

					.append(",\"fileIds\":").append(
							"\"" + mailTemp.getFileIds() + "\"")

					.append(",\"fileNames\":").append(
							"\""
									+ changSpecialCharacters(mailTemp
											.getFileNames()) + "\"");

			buff.append("},");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");

		jsonString = buff.toString();
		return jsonString;

	}

	/**
	 *将 转义字附转为正常字附
	 * 
	 */

	protected static String changSpecialCharacters(String special) {
		if (special == null) {
			return "";
		} else {
			// special=decodeText(special);
			special = special.replace("\"", "'").replace("\t", "\\t").replace(
					"\n", "\\n").replace(":", "\\:").replace("[", "\\[")
					.replace("]", "\\]").replace("{", "\\{")
					.replace("}", "\\}").replace(",", "\\,").replace("\r",
							"\\r").replace("null", "");
		}

		return special;
	}

	protected void send(OutMail outMail_, List reviceList, List ccList,
			OutMailUserSeting _outMailUserSeting) throws Exception {
		logger.debug("send start...");
		// 取得通道session
		String user = _outMailUserSeting.getMailAddress();
		String pass = _outMailUserSeting.getMailPass();
		String smtpHost = _outMailUserSeting.getSmtpHost();
		String smtpPort = _outMailUserSeting.getSmtpPort();
		String smtpAuth = "true";
		logger.debug(_outMailUserSeting);

		Properties props = new Properties();
		props.setProperty("mail.smtp.host", smtpHost);
		props.setProperty("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", smtpAuth);

		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.socketFactory.port", smtpPort);

		File cert = null;

		cert = CertUtil.get(smtpHost, Integer.parseInt(smtpPort));

		if (cert != null) {
			logger.debug("ssl connection...");
			Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
			props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			System.setProperty("javax.net.ssl.trustStore", cert
					.getAbsolutePath());
			props.put("javax.net.ssl.trustStore", cert.getAbsolutePath());// 证书
		} else {
			final String TLS_FACTORY = "javax.net.SocketFactory";
			props.setProperty("mail.smtp.socketFactory.class", TLS_FACTORY);
			
			logger.debug("plaintext connection or tls connection...");
			//props.put("mail.smtp.starttls.enable", "true");
		}

		final String username = user;
		final String password = pass;

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		logger.debug("connetion session:" + session);
		EmailAddress sender = new EmailAddress(_outMailUserSeting
				.getMailAddress(), _outMailUserSeting.getUserName()); // 发送人
		BodyPart contentPart = new MimeBodyPart();// 内容
		// 邮件正文
		contentPart.setHeader("Content-Transfer-Encoding", "base64");
		contentPart
				.setContent(outMail_.getContent(), "text/html;charset=utf-8");

		MimeMessage message = new MimeMessage(session);
		Multipart multipart = new MimeMultipart();
		message.setSubject(outMail_.getTitle(), "utf-8");

		message.setText("utf-8", "utf-8");
		message.setSentDate(outMail_.getMailDate() == null ? new Date()
				: outMail_.getMailDate());

		multipart.addBodyPart(contentPart);// 邮件正文

		// 添加发件人
		message.setFrom(sender.toInternetAddress());

		// 添加收件人
		InternetAddress address[] = this.getAddressByType(reviceList);
		if (address != null)
			message.addRecipients(Message.RecipientType.TO, address);
		// 添加抄送人
		if (ccList != null && ccList.size() > 0) {
			address = this.getAddressByType(ccList);
			if (address != null)
				message.addRecipients(Message.RecipientType.CC, address);
		}
		// 添加暗送人
		// address = this.getAddressByType(this.邮件.get暗送人());
		// if (address != null)
		// message.addRecipients(Message.RecipientType.BCC,address);

		// 添加附件
		if (outMail_.getFileIds() != null && outMail_.getFileIds().length() > 0) {// 判断字附串不为空
			String fileids = outMail_.getFileIds();
			String[] fileid_s = fileids.split(",");
			for (int i = 0; i < fileid_s.length; i++) {
				if (!fileid_s[i].equals("") && !fileid_s[i].equals("null")) {
					FileAttach f__attach = fileAttachService.get(new Long(
							fileid_s[i]));
					if (f__attach != null) {

						File file = new File(FILE_PATH_ROOT
								+ f__attach.getFilePath());// 取得路径生成文件

						BodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(file);
						messageBodyPart.setDataHandler(new DataHandler(source));
						messageBodyPart.setFileName(MimeUtility.encodeWord(
								f__attach.getFileName(), "UTF-8", "Q"));
						multipart.addBodyPart(messageBodyPart);

					}
				}

			}
		}

		message.setContent(multipart);
		if (sendType == null)
			sendType = "smtp";

		// session.getTransport(sendType).send(message);
		Transport transport = session.getTransport(sendType);
		transport.connect(_outMailUserSeting.getSmtpHost().toString(),
				username, password);
		transport.send(message);
		transport.close();
		logger.debug("send end");

	}

	/**
	 * 将地地址址转化为 可输送的网络地址
	 */
	protected static InternetAddress[] getAddressByType(List<EmailAddress> list)
			throws Exception {
		if (list != null) {
			InternetAddress address[] = new InternetAddress[list.size()];
			for (int i = 0; i < list.size(); i++) {

				if (list.get(i).toInternetAddress() != null) {
					address[i] = list.get(i).toInternetAddress();

				}
			}
			return address;
		}
		return null;
	}

	/**
	 * 将接收到的字附串,分解出址字附串,以豆号隔开
	 */
	protected static String getAddressesToStr(String str) {
		String address = "";
		if (str != null && str.length() > 0 && str.indexOf(";") >= 0) {// 判断不为空,并有多个别名:receives.indexOf(";")>0
			String[] emails = str.split(";");// 拆分每个地填
			for (int i = 0; i < emails.length; i++) {//

				if (emails[i].length() > 1 && emails[i].indexOf("<") >= 0
						&& emails[i].indexOf(">") > 0) {// 有别名
					String email[] = emails[i].split("<");
					// name+=email[0]+",";
					address += email[1].substring(0, email[1].length() - 1)
							+ ",";
				} else {// 无别名
					// name+=""+",";
					address += emails[i] + ",";
				}

			}
		} else {// 一个地址
			if (str != null && str.indexOf("<") >= 0 && str.indexOf(">") > 0) {// 有别名
				String email[] = str.split("<");
				// name+=email[0]+",";
				address += email[1].substring(0, email[1].length() - 1) + ",";
			} else {
				// name=",";
				address = str + ",";
			}
		}
		// 去最后面的豆号,
		if (address != null && address.length() > 1) {
			address = address.substring(0, address.length() - 1);
		}
		return address;
	}

	/**
	 * 将接收到的字附串,分解别名字附串,以豆号隔开
	 */

	protected static String getNamesToStr(String str) {
		String name = "";
		if (str != null && str.length() > 0 && str.indexOf(";") >= 0) {// 判断不为空,并有多个别名:receives.indexOf(";")>0
			String[] emails = str.split(";");// 拆分每个地填
			for (int i = 0; i < emails.length; i++) {//
				if (emails[i].indexOf("<") >= 0 && emails[i].indexOf(">") > 0) {// 有别名
					String email[] = emails[i].split("<");
					name += email[0] + ",";
					// address+=email[1].substring(0,email[1].length()-1)+",";
				} else {// 无别名
					name += emails[i] + ",";
					// name+=""+",";
					// address+=emails[i]+",";
				}

			}
		} else {// 一个地址
			if (str != null && str.indexOf("<") >= 0 && str.indexOf(">") > 0) {// 有别名
				String email[] = str.split("<");
				name += email[0] + ",";
				// address+=email[1].substring(0,email[1].length()-1)+",";
			} else {
				name = str + ",";
				// name=",";
				// address=str+",";
			}
		}
		if (name != null && name.length() > 1) {
			name = name.substring(0, name.length() - 1);
		}
		if (name == null)
			name = " ";
		return name;
	}

	/**
	 * 将以豆号分隔的地址与别名,组成 List<EmailAddress> 反回
	 * 
	 */

	protected List<EmailAddress> getEMailStrToList(String addresses,
			String names) {
		List<EmailAddress> list = new ArrayList<EmailAddress>();

		// 将发送人字附串,以豆号隔开,组成list
		if (addresses != null && addresses.length() > 1) {// 判断抄送地址不为空,才进行拆分
			String[] revice_a = addresses.split(",");// 拆分地址
			if (names != null && names.length() > 1) {// 判断地址别名不为空,才拆分
				String[] revice_n = names.split(",");
				;// 拆分别名
				for (int i = 0; i < revice_a.length; i++) {
					if (revice_a[i] != null && revice_a[i].length() > 1
							&& revice_a[i].indexOf("@") > 0) {
						if (revice_n.length > i) {// 判断长度足够长,
							EmailAddress add = new EmailAddress(revice_a[i]
									.trim(), revice_n[i]);
							list.add(add);
						} else {// 否则给默认直
							EmailAddress add = new EmailAddress(revice_a[i]
									.trim(), revice_a[i].trim());
							list.add(add);
						}
					} else {
						setJsonString("{failure:true,msg:'收件人地址有误!'}");
						return null;
					}
				}

			} else {// 没有别名
				for (int i = 0; i < revice_a.length; i++) {// 只盾环地址
					if (revice_a[i] != null && revice_a[i].length() > 1
							&& revice_a[i].indexOf("@") > 0) {
						EmailAddress add = new EmailAddress(revice_a[i].trim(),
								revice_a[i].trim());
						list.add(add);
					} else {
						return null;
					}
				}

			}

		} else {
			setJsonString("{failure:true,msg:'收件人不能为空!'}");
			return null;
		}

		return list;
	}

	/**
	 * 
	 * 到邮件服务器收邮件
	 * 
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	public String fetch() {
		logger.debug("fectch start...");
		outMailUserSeting = outMailUserSetingService.getByLoginId(ContextUtil
				.getCurrentUserId());// 邮箱设置
		// 如未设置邮箱设置
		if (outMailUserSeting != null) {

			logger.debug(outMailUserSeting);

			Properties props = new Properties();

			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.port", outMailUserSeting.getPopPort());
			props.setProperty("mail.pop3.socketFactory.port", outMailUserSeting
					.getPopPort());

			// 以下步骤跟一般的JavaMail操作相同
			File cert = null;

			cert = CertUtil.get(outMailUserSeting.getPopHost(), Integer
					.parseInt(outMailUserSeting.getPopPort()));

			if (cert != null) {
				Security
						.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				logger.debug("ssl connection...");
				final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				System.setProperty("javax.net.ssl.trustStore", cert
						.getAbsolutePath());
				props.put("javax.net.ssl.trustStore", cert.getAbsolutePath());// 证书
			} else {
				final String TLS_FACTORY = "javax.net.SocketFactory";
				props.setProperty("mail.smtp.socketFactory.class", TLS_FACTORY);
				
				logger.debug("plaintext connection or tls connection...");
				//props.put("mail.smtp.starttls.enable", "true");
			}

			Session session = Session.getInstance(props,  new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(outMailUserSeting.getMailAddress(), outMailUserSeting.getMailPass());
				}
			});
			// 请将红色部分对应替换成你的邮箱账号和密码
			URLName urln = new URLName("pop3", outMailUserSeting.getPopHost(),
					Integer.parseInt(outMailUserSeting.getPopPort()), null,
					outMailUserSeting.getMailAddress(), outMailUserSeting
							.getMailPass());

			Store store = null;
			POP3Folder inbox = null;

			OutMailFolder fectchFolder = outMailFolderService
					.get(FOLDER_ID_RECEIVE);// 要移到收件箱

			try {
				store = session.getStore(urln);
				store.connect();
				inbox = (POP3Folder) store.getFolder("INBOX");// 主文件夹
				inbox.open(Folder.READ_ONLY);// 只读打开
				FetchProfile profile = new FetchProfile();// 感兴趣的信息
				// profile.add(FetchProfile.Item.ENVELOPE);
				profile.add(UIDFolder.FetchProfileItem.UID);// 邮件标识id
				Message[] messages = inbox.getMessages();
				inbox.fetch(messages, profile);
				// 邮箱中已下载的邮件uid

				Map uidMail = outMailService.getUidByUserId(ContextUtil
						.getCurrentUserId());

				// 邮件列表

				int count = messages.length;
				logger.debug("mail counts :	" + count);

				for (int i = 0; i < count; i++) {
					try {
						if (uidMail.get(inbox.getUID(messages[i])) == null) {// 判断是否已下载该uid
							try {
								logger.debug("");
								logger.debug("开始接收邮件:	"
										+ (messages[i].getSubject()));
								OutMail fetchOutMail = new OutMail();
								// 邮件uid
								fetchOutMail.setUid(inbox.getUID(messages[i]));

								// 邮件发送者
								String from = (messages[i].getFrom()[0]
										.toString());
								InternetAddress ia = new InternetAddress(from);
								String senerAddress = ia.getAddress();
								if (senerAddress == null
										|| senerAddress.equals("")) {
									senerAddress = "	";
								}
								fetchOutMail.setSenderAddresses(senerAddress);
								fetchOutMail.setSenderName(ia.getPersonal());

								// 接受者
								InternetAddress[] ia_re = null;
								try {
									ia_re = (InternetAddress[]) messages[i]
											.getRecipients(Message.RecipientType.TO);
								} catch (javax.mail.internet.AddressException e) {
									e.printStackTrace();
								}
								String re_a = "		";
								String re_n = "		";

								if (ia_re != null && ia_re.length > 0) {

									for (int k = 0; k < ia_re.length; k++) {
										re_a += ia_re[k].getAddress() + ",";
										if (ia_re[k].getPersonal() != null
												&& !ia_re[k].getPersonal()
														.equals("")) {
											re_n += ia_re[k].getPersonal()
													+ ",";
										} else {
											re_n += ia_re[k].getAddress() + ",";
										}
									}

									if (re_a != null && re_a.length() > 1) {
										re_a = re_a.substring(0,
												re_a.length() - 1);
									}
									if (re_n != null && re_n.length() > 1) {
										re_n = re_n.substring(0,
												re_n.length() - 1);
									}

								}

								fetchOutMail.setReceiverAddresses(re_a);
								fetchOutMail.setReceiverNames((re_n));

								// 抄送者
								InternetAddress[] ia_cc = null;
								try {
									ia_cc = (InternetAddress[]) messages[i]
											.getRecipients(Message.RecipientType.CC);
								} catch (javax.mail.internet.AddressException e) {
									e.printStackTrace();
								}
								String cc_a = "	";
								String cc_n = "	";
								if (ia_cc != null && ia_cc.length > 0) {

									for (int k = 0; k < ia_cc.length; k++) {
										cc_a += ia_cc[k].getAddress() + ",";
										cc_n += ia_cc[k].getPersonal() + ",";
									}
									if (cc_a != null && cc_a.length() > 1) {
										cc_a = cc_a.substring(0,
												cc_a.length() - 1);
									}
									if (cc_n != null && cc_n.length() > 1) {
										cc_n = cc_n.substring(0,
												cc_n.length() - 1);
									}

								}

								fetchOutMail.setcCAddresses(cc_a);
								fetchOutMail.setcCNames((cc_n));

								// 暗送者
								InternetAddress[] ia_bcc = null;
								try {
									ia_bcc = (InternetAddress[]) messages[i]
											.getRecipients(Message.RecipientType.BCC);
								} catch (javax.mail.internet.AddressException e) {
									e.printStackTrace();
								}
								String bcc_a = "	";
								String bcc_n = "	";
								if (ia_bcc != null && ia_bcc.length > 0) {

									for (int k = 0; k < ia_bcc.length; k++) {
										bcc_a += ia_bcc[k].getAddress() + ",";
										bcc_n += ia_bcc[k].getPersonal() + ",";
									}
									if (bcc_a != null && bcc_a.length() > 1) {
										bcc_a = bcc_a.substring(0, bcc_a
												.length() - 1);
									}
									if (bcc_n != null && bcc_n.length() > 1) {
										bcc_n = bcc_n.substring(0, bcc_n
												.length() - 1);
									}

								}

								fetchOutMail.setbCCAddresses(bcc_a);
								fetchOutMail.setbCCAnames((bcc_n));
								// 邮件标题

								fetchOutMail
										.setTitle((messages[i].getSubject()));

								// 邮件发送时间
								Date sentDate = null;
								if (messages[i].getSentDate() != null) {
									sentDate = messages[i].getSentDate();
								} else {
									sentDate = new Date();
								}
								fetchOutMail.setMailDate(sentDate);

								// 邮件正文

								String content = (getMailContent(messages[i]));
								if (content == null || content.equals("")) {
									content = "	";
								}

								fetchOutMail.setContent(content);

								// 附件

								Set<FileAttach> outMailFiles = saveFileForFetch(messages[i]);
								fetchOutMail.setOutMailFiles(outMailFiles);

								// 其它

								fetchOutMail.setReadFlag(new Short("0"));
								fetchOutMail.setReplyFlag(new Short("0"));

								// 收件箱
								fetchOutMail.setOutMailFolder(fectchFolder);

								fetchOutMail.setUserId(ContextUtil
										.getCurrentUserId());
								// 保存

								outMailService.save(fetchOutMail);

								outMail = outMailService.get(fetchOutMail
										.getMailId());// 先保一下，让其产生fileid
								// 再取出来，目的在于取出 fileid
								Set<FileAttach> outMailFiles_new = outMail
										.getOutMailFiles();

								// 补回 fileid ,filename,字段,用豆号隔开
								String f_id = "";
								String f_name = "";
								if (outMailFiles_new != null
										&& outMailFiles_new.size() > 0) {
									for (FileAttach f : outMailFiles_new) {
										f_id += f.getFileId().toString() + ",";
										f_name += f.getFileName().toString()
												+ ",";
									}
									if (f_id.length() > 1) {
										f_id = f_id.substring(0,
												f_id.length() - 1);

									}
									if (f_name.length() > 1) {
										f_name = f_name.substring(0, f_name
												.length() - 1);

									}
								}
								outMail.setFileIds(f_id);
								outMail.setFileNames(f_name);
								outMailService.save(outMail);
								logger.debug("接收邮件成功:	"
										+ (messages[i].getSubject()));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								System.gc();
							}

						}
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			} catch (NoSuchProviderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				try {
					inbox.close(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					store.close();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		setJsonString("{success:true,msg:'收取邮件完成！'}");
		logger.debug("fectch end...");
		return SUCCESS;
	}

	/***
	 * 删除物理文件
	 */

	protected boolean delPhysicalFile(FileAttach fileAttach) {
		String fp_p = FILE_PATH_ROOT;// 父目录
		String fp_full = fp_p + fileAttach.getFilePath();// 全目录
		File del_f = new File(fp_full);
		if (del_f.delete()) {
			logger.info("删除文件：" + fp_full);
		} else {
			logger.info("文件不存在：" + fp_full);
		}
		return true;
	}

	/**
	 *解诀中文乱码问题
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	protected static String decodeText(String text) {

		try {
			if (text == null)
				return "";
			if (text.startsWith("=?GB") || text.startsWith("=?gb"))
				text = MimeUtility.decodeText(text);
			else if (text.startsWith("=?ISO-8859-1")
					|| text.startsWith("=?iso-8859-1"))
				text = MimeUtility.decodeText(text);
			else
				text = new String(text.getBytes("ISO8859_1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return text;
	}

	/**
     * 
     * 
     */

	/**
	 * 取得邮件正文
	 * 
	 * @throws MessagingException
	 * @throws IOException
	 * 
	 */
	protected String getMailContent(Part part) {

		String userName = ContextUtil.getCurrentUser().getUsername();
		StringBuffer sb = new StringBuffer();
		sb.append(new String(""));
		try {

			/**
			 * 纯文本或者html格式的,可以直接解析掉
			 */
			if (part.isMimeType("text/plain") || part.isMimeType("text/html")) {
				sb.append(part.getContent());
			} else if (part.isMimeType("multipart/*")) {
				/**
				 * 可供选择的,一般情况下第一个是plain,第二个是html格式的
				 */
				if (part.isMimeType("multipart/alternative")) {
					Multipart mp = (Multipart) part.getContent();
					int index = 0;// 兼容不正确的格式,返回第一个部分
					if (mp.getCount() > 1)
						index = 1;// 第2个部分为html格式的哦~

					/**
					 * 已经根据情况进行了判断,就算不符合标准格式也不怕了.
					 */
					Part tmp = mp.getBodyPart(index);
					sb.append(tmp.getContent());
				} else if (part.isMimeType("multipart/related")) {
					/**
					 * related格式的,那么第一个部分包含了body,里面含有内嵌的内容的链接.
					 */
					Multipart mp = (Multipart) part.getContent();
					Part tmp = mp.getBodyPart(0);
					String body = getMailContent(tmp);
					int count = mp.getCount();
					/**
					 * 要把那些可能的内嵌对象都先读出来放在服务器上,然后在替换相对地址为绝对地址
					 */
					for (int k = 1; count > 1 && k < count; k++) {
						Part att = mp.getBodyPart(k);
						String attname = att.getFileName();
						if (attname != null) {
							attname = MimeUtility.decodeText(attname);
						} else {
							attname = "	";
						}
						try {
							File attFile = new File(FILE_PATH_ROOT, userName
									.concat(attname));

							attFile.createNewFile();
							FileOutputStream fileoutput = new FileOutputStream(
									attFile);
							InputStream is = att.getInputStream();
							BufferedOutputStream outs = new BufferedOutputStream(
									fileoutput);
							byte b[] = new byte[att.getSize()];
							is.read(b);
							outs.write(b);
							outs.close();
						} catch (Exception e) {
							logger
									.error("Error occurred when to get the photos from server");
							//e.printStackTrace();
						}
						String Content_ID[] = att.getHeader("Content-ID");
						if (Content_ID != null && Content_ID.length > 0) {
							String cid_name = Content_ID[0].replaceAll("<", "")
									.replaceAll(">", "");
							body = body.replaceAll("cid:" + cid_name,
									FILE_PATH_ROOT.concat("/").concat(
											userName.concat(attname)));
						}
					}

					sb.append(body);
					return sb.toString();
				} else {
					/**
					 * 其他multipart/*格式的如mixed格式,那么第一个部分包含了body,用递归解析第一个部分就可以了
					 */
					Part tmp = ((Multipart) part.getContent()).getBodyPart(0);
					return getMailContent(tmp);
				}
			} else if (part.isMimeType("message/rfc822")) {
				return getMailContent((Message) part.getContent());
			} else {
				/**
				 * 否则的话,死马当成活马医,直接解析第一部分,
				 */
				Object obj = part.getContent();
				if (obj instanceof String) {
					sb.append(obj);
				} else {
					Multipart mp = (Multipart) obj;
					Part tmp = mp.getBodyPart(0);
					return getMailContent(tmp);
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
			return "解析正文错误!";
		}
		return sb.toString();
	}

	/**
	 * 取得邮件附件
	 * 
	 * @return
	 * @throws MessagingException
	 * @throws IOException
	 */
	protected Set<FileAttach> saveFileForFetch(Message msg) throws IOException,
			MessagingException {

		String contentType = msg.getContentType();

		if (contentType.startsWith("multipart/mixed")) {// 如果为一个集合包
			java.text.SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
			// 单个邮件
			Set<FileAttach> outMailFiles = new HashSet<FileAttach>();
			// getContent() 是获取包裹内容, Part 相当于外包装
			Multipart multipart = (Multipart) msg.getContent();// 获取邮件的内容,
			// 就一个大包裹, //
			// 包含所有邮件内容(正文+附件)
			// 依次处理各个部分
			for (int j = 0, n = multipart.getCount(); j < n; j++) {

				Part part = multipart.getBodyPart(j);// 解包, 取出 MultiPart的各个部分,
				// 每部分可能是邮件内容,
				// 也可能是另一个小包裹(MultipPart)

				// 判断此包裹内容是不是一个小包裹, 一般这一部分是 正文 Content-Type:
				// multipart/alternative
				// Content-Disposition: attachment;
				String disposition = part.getDisposition();// 处理是否为附件信息
				if (disposition != null) {
					FileAttach fileAttach = new FileAttach();
					fileAttach.setCreatetime(new Date());
					fileAttach.setCreator(ContextUtil.getCurrentUser()
							.getFullname());
					String fileName = part.getFileName();
					if (fileName != null) {
						fileName = MimeUtility.decodeText(fileName);
					} else {
						fileName = "	";
					}
					String[] ext = fileName.split("\\.");
					fileAttach.setFileName(fileName);
					fileAttach.setExt(ext[ext.length - 1]);
					fileAttach.setFileType("communicate/outmail/download");
					fileAttach.setNote(String.valueOf(part.getSize())
							+ " bytes");
					//fileAttach.setTotalBytes(new Double(part.getSize()));
					String ym = df.format(new Date());
					java.io.InputStream in = part.getInputStream();// 打开附件的输入流
					// 读取附件字节并存储到文件中
					String fp_p = FILE_PATH_ROOT;// 父目录
					String fp_c = "communicate\\outmail\\download\\"
							+ ContextUtil.getCurrentUser().getUsername() + "\\"
							+ ym + "\\";// 子目录
					fp_p = fp_p.replace("\\", "/");
					fp_c = fp_c.replace("\\", "/");

					String filePath = FileUtil.generateFilename(fileAttach
							.getFileName());
					filePath = filePath.substring(filePath.indexOf("/") + 1,
							filePath.length());

					fileAttach.setFilePath(fp_c + filePath.trim());
					java.io.File f = new java.io.File(fp_p, fp_c);// 创建目录
					if (!f.exists()) {
						if (!f.mkdirs())
							logger.info("目录不存在，创建失败！");
					}

					String f_full_p = fp_p + fileAttach.getFilePath();// 完全路径
					f_full_p = f_full_p.replace("\\", "/");

					java.io.FileOutputStream out = new FileOutputStream(
							f_full_p);
					int data;
					while ((data = in.read()) != -1) {
						out.write(data);
					}
					in.close();
					out.close();
					outMailFiles.add(fileAttach);
					logger.debug("附件:"+fileAttach.getFileName()+","+fileAttach.getFilePath());
				}
			}

			return outMailFiles;
		} else {
			return null;
		}
	}

}
