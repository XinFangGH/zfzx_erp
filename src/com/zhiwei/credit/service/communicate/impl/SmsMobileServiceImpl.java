package com.zhiwei.credit.service.communicate.impl;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.codehaus.xfire.client.XFireProxyFactory;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.smslib.OutboundMessage;
import org.smslib.Message.MessageEncodings;

import com.zhiwei.core.jms.MobileMessageProducer;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.gms.GmsUtil;
import com.zhiwei.credit.dao.communicate.SmsMobileDao;
import com.zhiwei.credit.model.communicate.SmsMobile;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.SysConfig;
import com.zhiwei.credit.service.communicate.SmsMobileService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.SysConfigService;
import com.sms.soap.SendStatus;
import com.sms.soap.ServicesSoap;

public class SmsMobileServiceImpl extends BaseServiceImpl<SmsMobile> implements
		SmsMobileService {
	@Resource
	private MobileMessageProducer mobileMessageProducer;
	
	@Resource
	private AppUserService appUserService;

	@Resource
	private SmsMobileService smsMobileService;
	
	@Resource
	private SysConfigService sysConfigService;
	private SmsMobileDao dao;

	public SmsMobileServiceImpl(SmsMobileDao dao) {
		super(dao);
		this.dao = dao;
	}
	
/*	@Override
	public SmsMobile save(SmsMobile entity) {
		dao.save(entity);
		mobileMessageProducer.send(entity);
		return entity;
	}*/

	@Override
	public void saveSms(String userIds, String content) {
		// 号码序列,用","分隔

		if (StringUtils.isNotEmpty(userIds)) {
			String[] ids = userIds.split(",");
			for (String id : ids) {
				AppUser recipients = appUserService.get(new Long(id));

				SmsMobile smsMobile = new SmsMobile();
				smsMobile.setPhoneNumber(recipients.getMobile());
				smsMobile.setSendTime(new Date());
				smsMobile.setRecipients(recipients.getFullname());
				smsMobile.setSmsContent(content);
				smsMobile.setUserId(AppUser.SYSTEM_USER);
				smsMobile.setUserName("系统");
				smsMobile.setStatus(SmsMobile.STATUS_NOT_SENDED);

				smsMobileService.save(smsMobile);
			}
		}
	}

	@Override
	public void sendSms() {
		// TODO Auto-generated method stub
		List<SmsMobile> list = smsMobileService.getNeedToSend();
		send(list);
	}

	public void send(List<SmsMobile> list){
		//是否用短信端口
		boolean smsPort = AppUtil.getSmsPort();
		
		if(smsPort){
			SysConfig uri = sysConfigService.findByKey("smsPortUri");
			SysConfig userID = sysConfigService.findByKey("smsPortUserID");
			SysConfig account = sysConfigService.findByKey("smsPortAccount");
			SysConfig pwd = sysConfigService.findByKey("smsPortPwd");
			SendStatus status =null;
			for(SmsMobile smsMobile : list){
				status = DirectSend(uri.getDataValue(),
						userID.getDataValue(), account.getDataValue(), pwd.getDataValue()
						,smsMobile.getPhoneNumber(),smsMobile.getSmsContent(), "", 1, "",1);
				if(status.getRetCode().equals("Sucess")){
					smsMobile.setStatus(SmsMobile.STATUS_SENDED);
					smsMobileService.merge(smsMobile);
						
						//e.printStackTrace();
				}else{
					logger.debug("======Send sms failure!please check the setting of the sms port .===");
				}
			}
		}else{
			OutboundMessage msg;
			org.smslib.Service gmsService = GmsUtil.getGmsService();
			if(list.size()>0){
				try {
					
					gmsService.startService();
						for(SmsMobile smsMobile : list){
							msg = new OutboundMessage(smsMobile.getPhoneNumber(), smsMobile.getSmsContent());
							msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
							gmsService.sendMessage(msg);
							smsMobile.setStatus(SmsMobile.STATUS_SENDED);
							smsMobileService.merge(smsMobile);
							
							
						}
					gmsService.stopService();
				} catch (Exception e) {
					logger.error(e);
					//e.printStackTrace();
				}
			}
		}
		
	}

	@Override
	public List<SmsMobile> getNeedToSend() {
		return dao.getNeedToSend();
	}

	@Override
	public void sendOneSms(SmsMobile smsMobile) {
		//是否用短信端口
		boolean smsPort = AppUtil.getSmsPort();
		
		if(smsPort){
			SysConfig uri = sysConfigService.findByKey("smsPortUri");
			SysConfig userID = sysConfigService.findByKey("smsPortUserID");
			SysConfig account = sysConfigService.findByKey("smsPortAccount");
			SysConfig pwd = sysConfigService.findByKey("smsPortPwd");
			SendStatus status =null;
				status = DirectSend(uri.getDataValue(),
						userID.getDataValue(), account.getDataValue(), pwd.getDataValue()
						,smsMobile.getPhoneNumber(),smsMobile.getSmsContent(), "", 1, "",1);
				if(status.getRetCode().equals("success")){
					smsMobile.setStatus(SmsMobile.STATUS_SENDED);
					smsMobileService.merge(smsMobile);
					
				}else{
					logger.debug("======Send sms failure!please check the setting of the sms port .===");
				}
		}else{
			try {
				OutboundMessage msg;
				org.smslib.Service gmsService = GmsUtil.getGmsService();
				gmsService.startService();
				msg = new OutboundMessage(smsMobile.getPhoneNumber(), smsMobile.getSmsContent());
				msg.setEncoding(MessageEncodings.ENCUCS2); // 中文
				gmsService.sendMessage(msg);
				smsMobile.setStatus(SmsMobile.STATUS_SENDED);
				
				smsMobileService.merge(smsMobile);
			
				gmsService.stopService();
			} catch (Exception e) {
				logger.error(e);
				//e.printStackTrace();
			}
		}
		
	}
	
	public SendStatus DirectSend(String url,String userID,String account,String password,
			String phones,String content,String sendTime,int sendType,String postFixNumber,int sign)
	{
		SendStatus status = null;
		org.codehaus.xfire.service.Service serviceModel = new ObjectServiceFactory().create(ServicesSoap.class);
		ServicesSoap service = null;

		try{
			service = (ServicesSoap)new XFireProxyFactory().create(serviceModel, url);
			status = service.directSend(userID, account, password, phones, content, sendTime, sendType, postFixNumber,sign);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}	
		return status;
	}
}