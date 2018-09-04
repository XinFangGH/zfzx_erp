package com.zhiwei.credit.action.communicate;

/*
 *  北京互融时代软件有限公司 JOffice协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2009 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
 */
import java.security.Security;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;

import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.CertUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;

import com.zhiwei.credit.model.communicate.OutMailUserSeting;
import com.zhiwei.credit.service.communicate.OutMailUserSetingService;

import com.sun.mail.pop3.POP3Folder;

/**
 * 
 * @author
 * 
 */
public class OutMailUserSetingAction extends BaseAction {
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	private OutMailUserSeting outMailUserSeting;

	public OutMailUserSeting getOutMailUserSeting() {
		return outMailUserSeting;
	}

	public void setOutMailUserSeting(OutMailUserSeting outMailUserSeting) {
		this.outMailUserSeting = outMailUserSeting;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<OutMailUserSeting> list = outMailUserSetingService.getAll(filter);

		Type type = new TypeToken<List<OutMailUserSeting>>() {
		}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");

		Gson gson = new Gson();
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
				outMailUserSetingService.remove(new Long(id));
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

		OutMailUserSeting outMailUserSeting = outMailUserSetingService
				.getByLoginId(ContextUtil.getCurrentUserId());

		Gson gson=new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").create();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		if (outMailUserSeting != null) {
			sb.append(gson.toJson(outMailUserSeting));
		} else {
			outMailUserSeting = new OutMailUserSeting();
			outMailUserSeting.setUserId(ContextUtil.getCurrentUserId());
			outMailUserSeting.setUserName(ContextUtil.getCurrentUser()
					.getUsername());

			sb.append(gson.toJson(outMailUserSeting));
		}

		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		boolean send = false;
		boolean fetch = false;

		send = this.send(outMailUserSeting);

		fetch = this.fetch(outMailUserSeting);
		
		
		if (send && fetch) {
			setJsonString("{success:true}");
		} else if (!send && !fetch) {
			setJsonString("{failure:true,msg:'连接到smtp,pop服务器失败，请检查书写是否正确!!'}");
		} else if (!send) {
			setJsonString("{failure:true,msg:'连接到smtp服务器失败，请检查书写是否正确!!'}");
		} else if (!fetch) {
			setJsonString("{failure:true,msg:'连接到pop服务器失败，请检查书写是否正确!!'}");
		}
		
		if(outMailUserSeting.getId()==null){
			outMailUserSeting.setUserId(ContextUtil.getCurrentUserId());
			outMailUserSetingService.save(outMailUserSeting);
			logger.debug(">>>"+outMailUserSeting);
		}else{
			OutMailUserSeting seting = outMailUserSetingService.get(outMailUserSeting.getId());
			try {
				BeanUtil.copyNotNullProperties(seting, outMailUserSeting);
				outMailUserSetingService.save(seting);
				logger.debug(">>>"+seting);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return SUCCESS;
	}

	@SuppressWarnings("static-access")
	protected boolean send(OutMailUserSeting os) {
		logger.debug("send start...");
		Transport transport = null;
		Session session = null;
		try {

			// 设置属性
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", os.getSmtpHost());
			props.setProperty("mail.smtp.port", os.getSmtpPort());
			props.put("mail.smtp.auth", "true");
			props.setProperty("mail.smtp.socketFactory.fallback", "false");
			props.setProperty("mail.smtp.socketFactory.port", os.getSmtpPort());

			// 安装证书
			File certstmp = CertUtil.get(os.getSmtpHost(), Integer.parseInt(os
					.getSmtpPort()));
			if (certstmp != null) {
				logger.debug("ssl connection...");
				
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);

				System.setProperty("javax.net.ssl.trustStore", certstmp
						.getAbsolutePath());
				props.setProperty("javax.net.ssl.trustStore", certstmp
						.getAbsolutePath());// 证书
			} else {
				final String TLS_FACTORY = "javax.net.SocketFactory";
				props.setProperty("mail.smtp.socketFactory.class", TLS_FACTORY);
				
				logger.debug("plaintext connection or tls connection...");
				//props.put("mail.smtp.starttls.enable", "true");
			}
			
			
			
			final String username = os.getMailAddress();
			final String password = os.getMailPass();
			// 认证
			session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					// logger.debug("认证类="+new PasswordAuthentication(username,
					// password));
					return new PasswordAuthentication(username, password);
				}
			});
			logger.debug("connetion session:"+session);
			MimeMessage message = new MimeMessage(session);
			Multipart multipart = new MimeMultipart();
			message.setSubject("智维邮件测试", "utf-8");
			BodyPart contentPart = new MimeBodyPart();// 内容
			// 邮件正文
			contentPart.setHeader("Content-Transfer-Encoding", "base64");
			contentPart.setContent("智维邮件测试", "text/html;charset=utf-8");
			message.setText("智维邮件测试", "utf-8");
			message.setSentDate(new Date());

			multipart.addBodyPart(contentPart);// 邮件正文

			// 添加发件人
			message.setFrom(new InternetAddress(os.getMailAddress(),
					MimeUtility.encodeWord(os.getUserName(), "utf-8", "Q")));

			// 添加收件人
			InternetAddress address[] = new InternetAddress[1];
			address[0] = new InternetAddress(os.getMailAddress(), MimeUtility
					.encodeWord(os.getUserName(), "utf-8", "Q"));
			message.addRecipients(Message.RecipientType.TO, address);
			message.saveChanges();

			// session.getTransport("smtp").send(message);

			transport = session.getTransport("smtp");
//			transport.connect(os.getSmtpHost().toString(), username, password);

			transport.send(message);
			logger.debug("send end...");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("连接smtp 服务器失败");
			e.printStackTrace();
			return false;
		} finally {
			try {
				transport.close();

			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				logger.info("关闭连接失败");
				e.printStackTrace();
			}
		}
		
	}

	protected boolean fetch(OutMailUserSeting os) {
		logger.debug("fectch start...");
		

		Store store = null;
		POP3Folder inbox = null;
		try {
			// 安装证书
			File certpop = CertUtil.get(os.getPopHost(), Integer.parseInt(os
					.getPopPort()));

			// 设置属性
			Properties props = new Properties();

			props.setProperty("mail.pop3.socketFactory.fallback", "false");
			props.setProperty("mail.pop3.port", os.getPopPort());
			props.setProperty("mail.pop3.socketFactory.port", os.getPopPort());

			if (certpop != null) {
				logger.debug("ssl connection...");
				Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
				final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
				props.setProperty("mail.pop3.socketFactory.class", SSL_FACTORY);
				System.setProperty("javax.net.ssl.trustStore", certpop
						.getAbsolutePath());
				props.setProperty("javax.net.ssl.trustStore", certpop
						.getAbsolutePath());// 证书
			} else {
				final String TLS_FACTORY = "javax.net.SocketFactory";
				props.setProperty("mail.smtp.socketFactory.class", TLS_FACTORY);
				
				logger.debug("plaintext connection or tls connection...");
				//props.put("mail.smtp.starttls.enable", "true");
			}

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(outMailUserSeting.getMailAddress(), outMailUserSeting.getMailPass());
				}
			});
			// 请将红色部分对应替换成你的邮箱账号和密码
			URLName urln = new URLName("pop3", os.getPopHost(), Integer
					.parseInt(os.getPopPort()), null, os.getMailAddress(), os
					.getMailPass());

			store = session.getStore(urln);
			store.connect();
			inbox = (POP3Folder) store.getFolder("INBOX");// 主文件夹
			inbox.open(Folder.READ_ONLY);// 只读打开
			// FetchProfile profile = new FetchProfile();//感兴趣的信息
			// profile.add(FetchProfile.Item.ENVELOPE);
			// profile.add(UIDFolder.FetchProfileItem.UID);//邮件标识id
			Message[] messages = inbox.getMessages();
			// inbox.fetch(messages, profile);
			// 邮箱中已下载的邮件uid

			// 邮件列表

			int count = messages.length;
			logger.debug("mail count:"+count);
			logger.debug("fectch end...");
			if (count > 0) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			logger.info("连接pop 服务器失败");
			e.printStackTrace();
			return false;
		} finally {

			try {
				inbox.close(false);
			} catch (Exception e) {
				logger.info("关闭连接失败");
				e.printStackTrace();
			}
			try {
				store.close();
			} catch (Exception e) {
				logger.info("关闭连接失败");
				e.printStackTrace();
			}

		}

	}

}
