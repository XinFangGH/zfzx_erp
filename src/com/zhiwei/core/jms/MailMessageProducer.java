package com.zhiwei.core.jms;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.jms.Queue;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.zhiwei.core.engine.MailEngine;
import com.zhiwei.core.model.MailModel;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;
import com.zhiwei.credit.service.thirdInterface.PlThirdInterfaceLogService;
/**
 * 用于发送邮件消息
 * @author csx
 *
 */
public class MailMessageProducer {
	
	private static final Log logger=LogFactory.getLog(MailMessageProducer.class);
	
	@Resource
	private Queue mailQueue;
	
	@Resource
	private JmsTemplate jmsTemplate;
	
	@Resource
	MailEngine mailEngine;
   //第三方插件接口声明
    @Resource
	private PlThirdInterfaceLogService plThirdInterfaceLogService ;
	 private String messageId ="";
	
	public void send(MailModel mailModel){
	  //添加日志
		messageId =	plThirdInterfaceLogService.saveLog1("", "",mailModel.getMailData().get("content").toString(), "邮件发送接口",null, PlThirdInterfaceLog.MEMTYPE0,
				PlThirdInterfaceLog.TYPE3, PlThirdInterfaceLog.TYPENAME3,"收件邮箱："+mailModel.getTo(),"","","");	
		logger.debug("procduce the mail message");
		Long sendTime = new Date().getTime();
		Long endTime =new Long("0");

			//产生邮件发送的消息，加到消息队列中去
		//jmsTemplate.convertAndSend(mailQueue, mailModel);
	    endTime = new Date().getTime();
	    updateLog("8888","发送成功","邮件发送成功,发送邮件："+mailModel.getMailData().get("content").toString(),"收件邮箱："+mailModel.getTo(),"时间："+(endTime-sendTime)+"ms");
		
	}

	/**
	 * 调用更新日志
	 * @param respCode
	 * @param msgExt
	 * @param plain
	 * @param buyerName
	 * @param timeDifference
	 */
	public void updateLog(String respCode,String msgExt,String plain,String buyerName,String timeDifference){
		plThirdInterfaceLogService.updateLog(respCode, msgExt, plain, "邮件接口",null, PlThirdInterfaceLog.MEMTYPE0,
				PlThirdInterfaceLog.TYPE3, PlThirdInterfaceLog.TYPENAME3,buyerName,timeDifference,"","",messageId);
	}
	
	/**
	 * 获取模板的内容
	 * @param mailModel
	 * @return
	 */
	public String getMailContent(MailModel mailModel){
		String msg=mailEngine.getMailContent(mailModel.getMailTemplate(), mailModel.getMailData());
		Document doc = Jsoup.parse(msg);
		Element body=doc.body();
	    msg=ContextUtil.trimHtml2Txt(body.toString());
	  //  System.out.println(msg);
		return msg;
	}
}
