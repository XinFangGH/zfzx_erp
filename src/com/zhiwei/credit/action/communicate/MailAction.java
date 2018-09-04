package com.zhiwei.credit.action.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.StringUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.communicate.Mail;
import com.zhiwei.credit.model.communicate.MailBox;
import com.zhiwei.credit.model.communicate.MailFolder;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.communicate.MailBoxService;
import com.zhiwei.credit.service.communicate.MailFolderService;
import com.zhiwei.credit.service.communicate.MailService;
import com.zhiwei.credit.service.info.InMessageService;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;

/**
 * 
 * @author csx
 * 
 */
public class MailAction extends BaseAction {
	static long FOLDER_ID_RECEIVE = 1l;// 收件箱
	static long FOLDER_ID_SEND = 2l;// 发件箱
	static long FOLDER_ID_DRAFT = 3l;// 草稿箱
	static long FOLDER_ID_DELETE = 4l;// 删除箱

	static short HAVE_DELETE = (short) 1;// 已删除
	static short NOT_DELETE = (short) 0;// 未删除
	static short HAVE_READ = (short) 1;// 已读
	static short NOT_READ = (short) 0;// 未读
	static Short HAVE_REPLY = (short) 1;// 已回复
	static short NOT_REPLY = (short) 0;// 未回复
	static short SYSTEM_MESSAGE = (short) 4;// 短信息中的系统信息类型
	static short COMMON = (short) 1;// 重要程度一般
	// static short IMPORTANT = (short)2;//重要
	// static short VERY_IMPORTANT = (short)3;//非常重要
	static short IS_DRAFT = (short) 0;// 邮件为草稿
	static short IS_MAIL = (short) 1;// 邮件为正式邮件
	@Resource
	private MailService mailService;
	@Resource
	private FileAttachService fileAttachService;
	@Resource
	private AppUserService appUserService;
	@Resource
	private MailFolderService mailFolderService;
	@Resource
	private MailBoxService mailBoxService;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private InMessageService inMessageService;

	private Mail mail;

	private Long mailId;

	private AppUser appUser;

	private Long folderId;

	private Long boxId;

	private String sendMessage;

	private Long replyBoxId;

	private String boxIds;// 移动邮件的集合

	private Long fileId;// 附件Id

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	public Mail getMail() {
		return mail;
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public Long getFolderId() {
		if (folderId == null) {
			return 1l;
		} else {
			return folderId;
		}
	}

	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}

	public Long getBoxId() {
		return boxId;
	}

	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}

	public String getBoxIds() {
		return boxIds;
	}

	public void setBoxIds(String boxIds) {
		this.boxIds = boxIds;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public Long getReplyBoxId() {
		return replyBoxId;
	}

	public void setReplyBoxId(Long replyBoxId) {
		this.replyBoxId = replyBoxId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		if(folderId==null||folderId==FOLDER_ID_RECEIVE){//folderId = 1 表示收件箱
			setFolderId(new Long(FOLDER_ID_RECEIVE));
		}
		filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		filter.addFilter("Q_mailFolder.folderId_L_EQ", folderId.toString());
		if(folderId != FOLDER_ID_DELETE){
			filter.addFilter("Q_delFlag_SN_EQ", "0");//假如不是删除箱的数据则只取出未删除的数据
		}
		filter.addSorted("sendTime", "desc");
		List<MailBox> list = mailBoxService.getAll(filter);

		//Gson gson=new Gson();
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:[");
		for(MailBox mailBoxTemp : list){
			Mail mailTemp = mailBoxTemp.getMail();
			buff.append("{boxId:'")
				.append(mailBoxTemp.getBoxId())
				.append("',sendTime:'")
				.append(mailBoxTemp.getSendTime())
				.append("',delFlag:'")
				.append(mailBoxTemp.getDelFlag())
				.append("',readFlag:'")
				.append(mailBoxTemp.getReadFlag())
				.append("',replyFlag:'")
				.append(mailBoxTemp.getReplyFlag())
				.append("',mailId:'")
				.append(mailTemp.getMailId())
				.append("',importantFlag:'")
				.append(mailTemp.getImportantFlag())
				.append("',mailStatus:'")
				.append(mailTemp.getMailStatus())
				.append("',fileIds:'")
				.append(mailTemp.getFileIds())
				.append("',subject:'")
				.append(gson.toJson(mailTemp.getSubject()).replace("\"", ""))
				.append("',recipientNames:'")
				.append(mailTemp.getRecipientNames())
				.append("',sender:'")
				.append(mailTemp.getSender())
				.append("',content:'");
			
			String content = mailTemp.getContent();
			if(StringUtils.isNotEmpty(content)){
				content = StringUtil.html2Text(content.replace("&nbsp;", ""));
				content = gson.toJson(content).replace("\"", "");
				
				if(content.length()>100){
					content = content.substring(0,100)+"...";
				}
				buff.append(content);
			}
			
			buff.append("'},");
		}
		if(list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		MailFolder deleteFolder = mailFolderService.get(FOLDER_ID_DELETE);
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			if (getFolderId() == FOLDER_ID_DELETE) {
				for (String id : ids) {
					mailBoxService.remove(new Long(id));
				}
			} else {
				for (String id : ids) {
					MailBox mailBoxDelete = mailBoxService.get(new Long(id));
					mailBoxDelete.setDelFlag(HAVE_DELETE);
					mailBoxDelete.setMailFolder(deleteFolder);
					mailBoxService.save(mailBoxDelete);
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
		MailBox mailBox = null;
		if (opt == null || "".equals(opt)) {
			mailBox = mailBoxService.get(boxId);
			getRequest().setAttribute("__haveNextMailFlag", "");
		} else {

			String folderId = getRequest().getParameter("folderId");
			if (folderId == null || "".equals(folderId)) {
				folderId = "1";// 假如文件夹ID为空,则默认为 收件箱
			}

			// 使用页面的方法实现获取上一条,下一条的记录
			QueryFilter filter = new QueryFilter(getRequest());
			filter.getPagingBean().setPageSize(1);// 只取一条记录
			List<MailBox> list = null;
			filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil
					.getCurrentUserId().toString());// 只取当前用户收到的信息
			filter.addFilter("Q_delFlag_SN_EQ", "0");// 只取未被删除的信息
			filter.addFilter("Q_mailFolder.folderId_L_EQ", folderId);// 根据文件夹ID来取邮件
			// filter.addSorted("sendTime", "desc");
			if (opt.equals("_next")) {
				// 点击下一条按钮,则取比当前ID大的下一条记录
				filter.addFilter("Q_boxId_L_GT", boxId.toString());
				list = mailBoxService.getAll(filter);
				if (filter.getPagingBean().getStart() + 1 == filter
						.getPagingBean().getTotalItems()) {
					getRequest().setAttribute("__haveNextMailFlag", "endNext");
				}
			} else if (opt.equals("_pre")) {
				// 点击上一条按钮,则取比当前ID小的下一条记录
				filter.addFilter("Q_boxId_L_LT", boxId.toString());
				filter.addSorted("boxId", "desc");
				list = mailBoxService.getAll(filter);
				if (filter.getPagingBean().getStart() + 1 == filter
						.getPagingBean().getTotalItems()) {
					getRequest().setAttribute("__haveNextMailFlag", "endPre");
				}
			}
			if (list.size() > 0) {
				mailBox = list.get(0);
			} else {
				mailBox = mailBoxService.get(boxId);
			}
		}

		setMail(mailBox.getMail());
		mailBox.setReadFlag(HAVE_READ);
		mailBoxService.save(mailBox);

		if (mail.getMailStatus() != 1) {// 假如为草稿
			JSONSerializer serializer = new JSONSerializer();
			// 将数据转成JSON格式
			StringBuffer sb = new StringBuffer(
					"{success:true,totalCounts:1,data:");
			sb.append(serializer.exclude(
					new String[] { "class", "mail.appUser",
							"appUser.department", "mailFolder.appUser" })
					.serialize(mail));
			sb.append("}");
			setJsonString(sb.toString());
			return SUCCESS;
		} else {
			getRequest().setAttribute("mail", mail);
			getRequest().setAttribute("boxId", mailBox.getBoxId());

			getRequest().setAttribute("mailAttachs", mail.getMailAttachs());
			return "detail";
		}

	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (mail.getMailStatus() == IS_MAIL) {
			// 假如发送邮件,发件人不能为空
			if (StringUtils.isEmpty(mail.getRecipientIDs())) {
				setJsonString("{failure:true,msg:'收件人不能为空!'}");
				return SUCCESS;
			}
			// 邮件主题不能为空
			if (StringUtils.isEmpty(mail.getSubject())) {
				setJsonString("{failure:true,msg:'邮件主题不能为空!'}");
				return SUCCESS;
			}
			// 邮件内容不能为空
//			if (StringUtils.isEmpty(mail.getContent())) {
//				setJsonString("{failure:true,msg:'邮件内容不能为空!'}");
//				return SUCCESS;
//			}
			// 假如是回复邮件,则原邮件改回复标识
			if (replyBoxId != null && !"".equals(replyBoxId)) {
				MailBox reply = mailBoxService.get(replyBoxId);
				reply.setReplyFlag(HAVE_READ);
				mailBoxService.save(reply);
			}

			MailFolder receiveFolder = mailFolderService.get(FOLDER_ID_RECEIVE);// 收件箱
			MailFolder sendFolder = mailFolderService.get(FOLDER_ID_SEND);// 发件箱
			String[] recipientIDs = mail.getRecipientIDs().split(",");// 收件人IDs
			String[] copyToIDs = mail.getCopyToIDs().split(",");// 抄送人IDs

			if (mail.getMailId() == null) {// 当该邮件是新建邮件时
				SaveMail();

				// 邮件保存到已发送

				MailBox mailBox = new MailBox();
				mailBox.setMail(mail);
				mailBox.setMailFolder(sendFolder);
				mailBox.setAppUser(ContextUtil.getCurrentUser());
				mailBox.setSendTime(mail.getSendTime());
				mailBox.setDelFlag(NOT_DELETE);
				mailBox.setReadFlag(NOT_READ);
				mailBox.setNote("已发送的邮件");
				mailBox.setReplyFlag(NOT_REPLY);
				mailBoxService.save(mailBox);
			} else {
				Mail old = mailService.get(mail.getMailId());
				try {
					BeanUtil.copyNotNullProperties(old, mail);
					Set<FileAttach> mailAttachs = new HashSet<FileAttach>();
					old.setSendTime(new Date());
					String[] fileIds = mail.getFileIds().split(",");
					for (String id : fileIds) {
						if (!id.equals("")) {
							mailAttachs
									.add(fileAttachService.get(new Long(id)));
						}
					}
					old.setMailAttachs(mailAttachs);
					setMail(old);
					mailService.save(old);
				} catch (Exception ex) {
					logger.error(ex.getMessage());
				}
				MailBox drafted = mailBoxService.get(boxId);
				drafted.setMailFolder(sendFolder);
				drafted.setNote("已发送的邮件");
				mailBoxService.save(drafted);
			}
			// 发送系统提示信息
			if (sendMessage != null && sendMessage.equals("on")) {
				StringBuffer msgContent = new StringBuffer("<font color=\"green\">");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				msgContent.append(mail.getSender())
						  .append("</font>")
						  .append("在<font color=\"red\">")
						  .append(sdf.format(mail.getSendTime()))
						  .append("</font>")
						  .append("给您发了一封邮件，请注意查收。");
				shortMessageService.save(AppUser.SYSTEM_USER, mail.getRecipientIDs(), msgContent.toString(),ShortMessage.MSG_TYPE_SYS);
			}
			// 发送邮件
			for (String id : recipientIDs) {
				if (!"".equals(id)) {
					MailBox mailBoxSend = new MailBox();
					mailBoxSend.setMail(mail);
					mailBoxSend.setMailFolder(receiveFolder);
					mailBoxSend.setAppUser(appUserService.get(new Long(id)));
					mailBoxSend.setSendTime(mail.getSendTime());
					mailBoxSend.setDelFlag(NOT_DELETE);
					mailBoxSend.setReadFlag(NOT_READ);
					mailBoxSend.setNote("发送出去的邮件");
					mailBoxSend.setReplyFlag(NOT_REPLY);
					mailBoxService.save(mailBoxSend);
				}

			}
			// 发送抄送邮件
			for (String id : copyToIDs) {
				if (!"".equals(id)) {
					MailBox mailBoxCopy = new MailBox();
					mailBoxCopy.setMail(mail);
					mailBoxCopy.setMailFolder(receiveFolder);
					mailBoxCopy.setAppUser(appUserService.get(new Long(id)));
					mailBoxCopy.setSendTime(mail.getSendTime());
					mailBoxCopy.setDelFlag(NOT_DELETE);
					mailBoxCopy.setReadFlag(NOT_READ);
					mailBoxCopy.setNote("抄送出去的邮件");
					mailBoxCopy.setReplyFlag(NOT_REPLY);
					mailBoxService.save(mailBoxCopy);
				}
			}

		} else {
			// 草稿时邮件主题不能为空
			if (StringUtils.isEmpty(mail.getSubject())) {
				setJsonString("{failure:true,msg:'邮件主题不能为空!'}");
				return SUCCESS;
			}
			
			// 邮件内容不能为空
//			if (StringUtils.isEmpty(mail.getContent())) {
//				setJsonString("{failure:true,msg:'邮件内容不能为空!'}");
//				return SUCCESS;
//			}
			
			SaveMail();// 先存邮件到mail表,再存到mailBox表

			// 存草稿
			MailFolder draftFolder = mailFolderService.get(FOLDER_ID_DRAFT);// 拿到草稿箱
			MailBox mailBox = new MailBox();
			mailBox.setMail(mail);
			mailBox.setMailFolder(draftFolder);
			mailBox.setAppUser(ContextUtil.getCurrentUser());
			mailBox.setSendTime(mail.getSendTime());
			mailBox.setDelFlag(NOT_DELETE);
			mailBox.setReadFlag(NOT_READ);
			mailBox.setNote("存草稿");
			mailBox.setReplyFlag(NOT_REPLY);
			mailBoxService.save(mailBox);
		}

		setJsonString("{success:true}");
		return SUCCESS;
	}

	public void SaveMail() {
		// 把邮件保存到mail 表里
		Set<FileAttach> mailAttachs = new HashSet<FileAttach>();
		setAppUser(ContextUtil.getCurrentUser());
		mail.setAppSender(appUser);
		mail.setSendTime(new Date());
		mail.setSender(appUser.getFullname());
		String[] fileIds = mail.getFileIds().split(",");
		for (String id : fileIds) {
			if (!id.equals("")) {
				mailAttachs.add(fileAttachService.get(new Long(id)));
			}
		}
		mail.setMailAttachs(mailAttachs);
		mailService.save(mail);
	}

	/**
	 * 邮件回复和转发
	 */
	public String opt() {
		setMail(mailService.get(mailId));
		String opt = getRequest().getParameter("opt");
		Mail reply = new Mail();
		//邮件转发时把附件也转发
		reply.setFileIds(mail.getFileIds());
		reply.setFilenames(mail.getFilenames());
		StringBuffer newSubject = new StringBuffer(
				"<br><br><br><br><br><br><br><hr>");
		newSubject.append("<br>----<strong>" + opt + "邮件</strong>----");
		newSubject.append("<br><strong>发件人</strong>:" + mail.getSender());
		newSubject.append("<br><strong>发送时间</strong>:" + mail.getSendTime());
		newSubject.append("<br><strong>收件人</strong>:"
				+ mail.getRecipientNames());
		String copyToNames = mail.getCopyToNames();
		if (!"".equals(copyToNames) && copyToNames != null) {
			newSubject.append("<br><strong>抄送人</strong>:" + copyToNames);
		}
		newSubject.append("<br><strong>主题</strong>:" + mail.getSubject());
		newSubject.append("<br><strong>内容</strong>:<br><br>"
				+ mail.getContent());
		reply.setContent(newSubject.toString());
		reply.setSubject(opt + ":" + mail.getSubject());
		reply.setImportantFlag(COMMON);
		if (opt.equals("回复")) {
			MailBox replyBox = mailBoxService.get(boxId);
			replyBox.setReplyFlag(HAVE_REPLY);
			mailBoxService.save(replyBox);
			reply.setRecipientIDs("" + mail.getAppSender().getUserId());
			reply.setRecipientNames(mail.getSender());
		}
		JSONSerializer serializer = new JSONSerializer();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(serializer.exclude(new String[] { "class", "appUser" })
				.serialize(reply));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 移动邮件至指定文件夹
	 */
	public String move() {
		MailFolder moveToFolder = mailFolderService.get(new Long(folderId));
		String[] ids = boxIds.split(",");
		StringBuffer msg = new StringBuffer("{");
		boolean moveSuccess = false;
		if (ids[0] != null && !"".equals(ids[0])) {// 判断草稿不能移到其他,除了删除箱,判断正式邮件不能移至草稿箱
			Mail moveTest = mailBoxService.get(new Long(ids[0])).getMail();
			if (moveTest.getMailStatus() == IS_DRAFT) {
				// 假如是草稿
				if (folderId == FOLDER_ID_DRAFT || folderId == FOLDER_ID_DELETE) {
					// 假如是草稿箱或者是删除箱,则允许移动
					moveSuccess = true;
				} else
					msg.append("msg:'草稿只能移至草稿箱或是垃圾箱(移至垃圾箱相当于删除,请注意!)'");
			} else if (moveTest.getMailStatus() == IS_MAIL) {
				// 假如是正式邮件
				if (folderId != FOLDER_ID_DRAFT) {
					// 假如不是草稿箱,允许移动
					moveSuccess = true;
				} else
					msg.append("msg:'正式邮件不能移至草稿箱'");
			}
		}
		if (moveSuccess) {
			// 把id为包含在ids数组里的邮件移动至所选文件夹
			for (String id : ids) {
				if (!"".equals(id)) {
					MailBox mailBoxMove = mailBoxService.get(new Long(id));
					mailBoxMove.setMailFolder(moveToFolder);
					if (folderId != FOLDER_ID_DELETE) {
						mailBoxMove.setDelFlag(NOT_DELETE);
					} else {
						mailBoxMove.setDelFlag(HAVE_DELETE);
					}
					mailBoxService.save(mailBoxMove);
				}
			}
			msg.append("success:true}");
			setJsonString(msg.toString());
		} else {
			// 不合规则,不允许移动
			msg.append(",failure:true}");
			setJsonString(msg.toString());
		}
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
		setMail(mailService.get(mailId));
		Set<FileAttach> mailAttachs = mail.getMailAttachs();
		FileAttach remove = fileAttachService.get(fileId);
		mailAttachs.remove(remove);
		mail.setMailAttachs(mailAttachs);
		mail.setFileIds(fileIds);
		mail.setFilenames(filenames);
		mailService.save(mail);
		fileAttachService.remove(fileId);
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String search() {
		PagingBean pb = getInitPagingBean();
		String searchContent = getRequest().getParameter("searchContent");
		List<MailBox> list = mailBoxService.findBySearch(searchContent, pb);
		Gson gson = new Gson();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(list.size()).append(",result:[");
		for (MailBox mailBoxTemp : list) {
			Mail mailTemp = mailBoxTemp.getMail();
			buff.append("{boxId:'")
				.append(mailBoxTemp.getBoxId())
				.append("',sendTime:'")
				.append(mailBoxTemp.getSendTime())
				.append("',delFlag:'")
				.append(mailBoxTemp.getDelFlag())
				.append("',readFlag:'")
				.append(mailBoxTemp.getReadFlag())
				.append("',replyFlag:'")
				.append(mailBoxTemp.getReplyFlag())
				.append("',mailId:'")
				.append(mailTemp.getMailId())
				.append("',importantFlag:'")
				.append(mailTemp.getImportantFlag())
				.append("',mailStatus:'").append(mailTemp.getMailStatus())
				.append("',fileIds:'").append(mailTemp.getFileIds())
				.append("',subject:'").append(gson.toJson(mailTemp.getSubject()))
				.append("',recipientNames:'")
				.append(mailTemp.getRecipientNames()).append("',sender:'")
				.append(mailTemp.getSender()).append("',content:'");
			String content = mailTemp.getContent();
			if(StringUtils.isNotEmpty(content)){
				content = StringUtil.html2Text(content.replace("&nbsp;", ""));
				content = gson.toJson(content);
				if(content.length()>100){
					content = content.substring(0,100)+"...";
				}
				buff.append(content);
			}
			
			
			buff.append("'},");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");

		jsonString = buff.toString();

		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String display() {

		QueryFilter filter = new QueryFilter(getRequest());
		filter.addFilter("Q_appUser.userId_L_EQ", ContextUtil.getCurrentUserId().toString());
		filter.addFilter("Q_mailFolder.folderId_L_EQ", Long.toString(MailAction.FOLDER_ID_RECEIVE));
		filter.addFilter("Q_delFlag_SN_EQ", Short.toString(MailAction.NOT_DELETE));
		filter.addSorted("sendTime", "desc");
		filter.addSorted("readFlag", "desc");
		List<MailBox> list = mailBoxService.getAll(filter);
		getRequest().setAttribute("mailBoxList",list);
		return "display";
	}
	
	
	/**
	 * 此方法用来过滤掉String 中的 HTML 代码
	 * @param inputString
	 * @return
	 */
	}
