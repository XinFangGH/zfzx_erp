package com.zhiwei.credit.workflow.event;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.listener.EventListener;
import org.jbpm.api.listener.EventListenerExecution;
import org.jbpm.api.model.OpenProcessInstance;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.personal.ErrandsRegister;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.info.ShortMessageService;
import com.zhiwei.credit.service.personal.ErrandsRegisterService;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
/**
 * 当请假单由上级审批完成后，会调用该事件进行相关的处理
 * @author 
 *
 */
public class HolidayApprovalListener implements EventListener {
	
	private Log logger=LogFactory.getLog(HolidayApprovalListener.class);
	
	/**
	 * 审批的选择
	 */
	Short choice;
	
	@Override
	public void notify(EventListenerExecution execution) throws Exception {
		
		if(logger.isDebugEnabled()){
			logger.info("enter the HolidayApprovalListener notify method...");
		}
		
		OpenProcessInstance pi=execution.getProcessInstance();
		//上司的dateId
		Long dateId=(Long)pi.getVariable("dateId");
		//上司的意见
		String superOption=(String)pi.getVariable("superOption");
		
		if(dateId!=null){
			
			ErrandsRegisterService erService=(ErrandsRegisterService)AppUtil.getBean("errandsRegisterService");
			ShortMessageService smService=(ShortMessageService)AppUtil.getBean("shortMessageService");
			ErrandsRegister errandsRegister=erService.get(dateId);
			
			if(errandsRegister!=null){
				errandsRegister.setStatus(choice);
				errandsRegister.setApprovalOption(superOption);
				erService.save(errandsRegister);
				
				//发送短消息
				smService.save(AppUser.SYSTEM_USER, errandsRegister.getUserId().toString(), "你的请假申请  （" + errandsRegister.getDescp() + "），最终审批意见：" + superOption, ShortMessage.MSG_TYPE_SYS);
			}
			
		}
		
		
	}

}
