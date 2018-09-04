package com.zhiwei.core.jbpm;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;

import com.zhiwei.core.Constants;

/**
 * 尚未开始使用
 * <B><P>Joffice -- http://www.jee-soft.cn</P></B>
 * <B><P>Copyright (C) 2008-2010 GuangZhou HongTian Software Company (广州宏天软件有限公司)</P></B> 
 * <B><P>description:</P></B>
 * <P></P>
 * <P>product:joffice</P>
 * <P></P> 
 * @see com.hurong.core.jbpm.UserAssignHandler
 * <P></P>
 * @author 
 * @version V1
 * @create: 2010-11-23下午02:58:01
 */
public class UserAssignHandler implements AssignmentHandler{
	private Log logger=LogFactory.getLog(UserAssignHandler.class);
	//授予用户ID
	String userIds;
	//授权角色ID
	String groupIds;
	
	@Override
	public void assign(Assignable assignable, OpenExecution execution) throws Exception {
		
		String assignId=(String)execution.getVariable(Constants.FLOW_ASSIGN_ID);
		
		logger.info("assignId:===========>" + assignId);
		
		//在表单提交中指定了固定的执行人员
		if(StringUtils.isNotEmpty(assignId)){
			assignable.setAssignee(assignId);
			return;
		}
		
		//在表单中指定了执行的角色TODO
		
		//在表单中指定了会签人员
		String signUserIds=(String)execution.getVariable(Constants.FLOW_SIGN_USERIDS);
		
		if(signUserIds!=null){
			//TODO 取到该任务,进行会签设置
		}
		
		logger.debug("Enter UserAssignHandler assign method~~~~");
		
		if(userIds!=null){//若用户不为空
			String[]uIds=userIds.split("[,]");
			if(uIds!=null && uIds.length>1){//多于一个人的
				for(String uId:uIds){
					assignable.addCandidateUser(uId);
				}
			}else{
				assignable.setAssignee(userIds);
			}
		}
		
		if(groupIds!=null){//若角色组不为空
			String[]gIds=userIds.split("[,]");
			if(gIds!=null&& gIds.length>1){//多于一个角色的
				for(String gId:gIds){
					assignable.addCandidateGroup(gId);
				}
			}else{
				assignable.addCandidateGroup(groupIds);
			}
		}

	}

}
