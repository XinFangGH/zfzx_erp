package com.zhiwei.credit.entity;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;



public class Mail {
	private String host; // smtp服务器
	private String from; // 发件人地址
	private String to = ""; // 收件人地址
	private List<String> tos; //收件人地址列表
	private String user; // 用户名
	private String pwd; // 密码
	private String subject = ""; // 邮件标题
	private String content = ""; // 邮件内容
	private Map<String, String> affixs = null;// 附件 <名字-位置>

	public void setAddress(String from, List<String> tos, String subject) {
		this.from = from;
		this.tos = tos;
		this.subject = subject;
	}

	public void setAffixs(Map<String, String> affix) {
		this.affixs = affix;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void send(String host, String user, String pwd) throws Exception {
		this.host = host;
		this.user = user;
		this.pwd = pwd;

		Properties props = new Properties();

		// 设置发送邮件的邮件服务器的属性（这里使用网易的smtp服务器）
		props.put("mail.smtp.host", this.host);
		// 需要经过授权，也就是有户名和密码的校验，这样才能通过验证
		props.put("mail.smtp.auth", "true");
		// 用刚刚设置好的props对象构建一个session
		Session session = Session.getDefaultInstance(props);

		// 有了这句便可以在发送邮件的过程中在console处显示过程信息，供调试使
		// 用（你可以在控制台（console)上看到发送邮件的过程）
		session.setDebug(false);

		// 用session为参数定义消息对象
		MimeMessage message = new MimeMessage(session);
			// 加载发件人地址
			message.setFrom(new InternetAddress(from));
			// 加载收件人地址
			for(String to : tos){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(
						to));
			}
			
			// 加载标题
			message.setSubject(subject);

			// 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件
			Multipart multipart = new MimeMultipart();

			// 设置邮件的文本内容
			BodyPart contentPart = new MimeBodyPart();
			if(this.content!=null && !this.content.trim().equals("")){
				contentPart.setText(this.content);
				multipart.addBodyPart(contentPart);
			}
			if (affixs != null && affixs.size() > 0) {
				Iterator<String> iters = affixs.keySet().iterator();
				while (iters.hasNext()) {
					String affixName = iters.next();
					String affix = affixs.get(affixName);
					affixName = new String(affixName.getBytes(),"GBK");
					if (affix != null && !affix.trim().equals("")) {
						// 添加附件
						BodyPart messageBodyPart = new MimeBodyPart();
						DataSource source = new FileDataSource(affix);
						// 添加附件的内容
						messageBodyPart.setDataHandler(new DataHandler(source));

						// 添加附件的标题
						// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
						sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
						messageBodyPart.setFileName("=?GBK?B?"
								+ enc.encode(affixName.getBytes()) + "?=");
						multipart.addBodyPart(messageBodyPart);
					}

				}

			}
			// 将multipart对象放到message中
			message.setContent(multipart);
			// 保存邮件
			message.saveChanges();
			// 发送邮件
			Transport transport = session.getTransport("smtp");
			// 连接服务器的邮箱
			transport.connect(this.host, this.user, this.pwd);
			// 把邮件发送出去
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		
	}
}
