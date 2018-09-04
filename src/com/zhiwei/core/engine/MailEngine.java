package com.zhiwei.core.engine;
import java.io.File;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.htmlparser.Parser;
import org.htmlparser.visitors.HtmlPage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.zhiwei.core.util.AppUtil;
/*
 *  北京互融时代软件有限公司 OA办公自动管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
/**
 * 邮件发送
 * @author 
 *
 */
public class MailEngine {
    private final Log logger = LogFactory.getLog(MailEngine.class);
    private JavaMailSender mailSender;
    private VelocityEngine velocityEngine;
    private String defaultFrom;

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public void setFrom(String from) {
        this.defaultFrom = from;
    }

    /**
     * Send a simple message based on a Velocity template.
     * @param msg the message to populate
     * @param templateName the Velocity template to use (relative to classpath)
     * @param model a map containing key/value pairs
     */
    public void sendMessage(SimpleMailMessage msg, String templateName, Map model) {
        String result = null;

        try {
            result =VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,templateName, model);
        } catch (VelocityException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }

        msg.setText(result);
        send(msg);
    }

    /**
     * Send a simple message with pre-populated values.
     * @param msg the message to send
     * @throws org.springframework.mail.MailException when SMTP server is down
     */
    public void send(SimpleMailMessage msg) throws MailException {
        try {
            mailSender.send(msg);
        } catch (MailException ex) {
            logger.error(ex.getMessage());
            throw ex;
        }
    }

    /**
     * Convenience method for sending messages with attachments.
     * 
     * @param recipients array of e-mail addresses
     * @param sender e-mail address of sender
     * @param resource attachment from classpath
     * @param bodyText text in e-mail
     * @param subject subject of e-mail
     * @param attachmentName name for attachment
     * @throws MessagingException thrown when can't communicate with SMTP server
     */
    public void sendMessage(String[] recipients, String sender, 
                            ClassPathResource resource, String bodyText,
                            String subject, String attachmentName)
    throws MessagingException {
        MimeMessage message = ((JavaMailSenderImpl) mailSender).createMimeMessage();

        // use the true flag to indicate you need a multipart message
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(recipients);

        // use the default sending if no sender specified
        if (sender == null) {
            helper.setFrom(defaultFrom);
        } else {
           helper.setFrom(sender);
        }

        helper.setText(bodyText);
        helper.setSubject(subject);

        helper.addAttachment(attachmentName, resource);

        ((JavaMailSenderImpl) mailSender).send(message);
    }
    
    /**
     * Send a HTML email, the final HTML content is ready. Usually you should
	 * not call this method directly. You may want to use velocity to generate
	 * the mail contents first.
	 * 
	 * @param from
	 * @param tos
	 * @param cc
	 * @param replyTo
	 * @param subject
	 * @param htmlMsgContent
	 * @param attachedFileName
	 * @param attachedFile
	 * @param inline
	 * @return Error message, null if no errors.
	 */
	public String sendMimeMessage(final String from, final String[] tos,
			final String cc, final String replyTo, final String subject,
			final String htmlMsgContent, final String[] attachedFileNames,
			final File []attachedFiles, final boolean inline) {
		if (tos == null || tos.length == 0 || tos[0] == null
				|| "".equals(tos[0])) {
			if (logger.isErrorEnabled()) {
				logger
						.error("Recipient found empty while sending a email, no further processing. Mail subject is:"
								+ subject);
			}
			return "Recipient is empty";
		}
//		JavaMailSenderImpl mailSenders=new JavaMailSenderImpl();
		JavaMailSenderImpl mailSender=(JavaMailSenderImpl)AppUtil.getBean("mailSender");
//		Map configs=AppUtil.getSysConfig();
//		mailSender.setHost((String)configs.get("host"));
//		mailSender.setUsername((String)configs.get("username"));
//		mailSender.setPassword((String)configs.get("password"));
//		mailSender.setDefaultEncoding("UTF-8");
//		mailSender.setProtocol("smtp");
//		Properties props = new Properties();
//		props.put("mail.smtp.auth","true");
//		setFrom((String)configs.get("from"));
//		mailSender.setJavaMailProperties(props);
		MimeMessage message = mailSender.createMimeMessage();
		try {
			// use the true flag to indicate you need a multipart message
			MimeMessageHelper helper = new MimeMessageHelper(message, attachedFiles != null);

			helper.setFrom(from == null ? defaultFrom: from);
			helper.setTo(tos);
			if (cc != null && !"".equals(cc)) {
				helper.setCc(cc);
			}
			if (replyTo != null && !"".equals(replyTo)) {
				helper.setReplyTo(replyTo);
			}

			helper.setSubject(subject);
			// use the true flag to indicate the text included is HTML
			helper.setText(htmlMsgContent, true);

			// add attachments
			if (attachedFiles != null) {
				if (inline) {
					for(int i=0;i<attachedFiles.length;i++){
						helper.addInline(attachedFileNames[i], attachedFiles[i]);
					}
				} else {
					for(int i=0;i<attachedFiles.length;i++){
						helper.addAttachment(attachedFileNames[i], attachedFiles[i]);
					}
					
				}
			}

			mailSender.send(message);
			if (logger.isDebugEnabled()) {
				logger.debug("A email has been sent successfully to: " + StringUtils.join(tos, ','));
			}
		} catch (Throwable e) {
			logger.error("Error occured when sending email.", e);
			return "Error occured when sending email." + e.getMessage();
		}

		return null;
	}
    
    /**
	 * Send email and use template (velocity) to compose the content.
	 * 
	 * @param templateName
	 * @param model
	 * @param subject
	 * @param from
	 * @param tos
	 * @param cc
	 * @param replyTo
	 * @param attachedFileName
	 * @param attachedFile
	 * @param inline
	 * @return Error message, null if no errors.
	 */
	public String sendTemplateMail(final String templateName,
			final Map<String, Object> model, final String subject,
			final String from, final String[] tos, final String cc,
			final String replyTo, final String[] attachedFileNames,
			final File[] attachedFiles, final boolean inline) {
		String mailContent = null;
		String mailSubject = subject;
		try {
			mailContent = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
			if (subject == null) {// parse from mail content title tag
				Parser myParser = Parser.createParser(mailContent,"UTF-8");
				HtmlPage visitor = new HtmlPage(myParser);
				myParser.visitAllNodesWith(visitor);
				mailSubject = visitor.getTitle();
			}
		} catch (Throwable e) { throw new RuntimeException("Email template processing error, Check log for detail infomation. Template path: "
								+ templateName, e);
		}

		return sendMimeMessage(from, tos, cc, replyTo, mailSubject,
				mailContent, attachedFileNames, attachedFiles, inline);
	}

	public String getMailContent( String templateName,
			 Map<String, Object> model){
		String mailContent = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, model);
		return mailContent;
	}
	
}