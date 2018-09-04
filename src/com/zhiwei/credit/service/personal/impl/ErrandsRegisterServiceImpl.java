package com.zhiwei.credit.service.personal.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.action.flow.FlowRunInfo;
import com.zhiwei.credit.dao.personal.ErrandsRegisterDao;
import com.zhiwei.credit.model.personal.ErrandsRegister;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.personal.ErrandsRegisterService;

public class ErrandsRegisterServiceImpl extends BaseServiceImpl<ErrandsRegister> implements ErrandsRegisterService{
	private ErrandsRegisterDao dao;
	
	public ErrandsRegisterServiceImpl(ErrandsRegisterDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 在流程中保存流程申请记录
	 * @param request
	 * @return
	 */
	public Integer saveRegister(FlowRunInfo flowRunInfo){
		AppUser curUser=ContextUtil.getCurrentUser();
		String comments = flowRunInfo.getRequest().getParameter("comments");
		//构造ErrandsRegister
		ErrandsRegister register=new ErrandsRegister();
		register.setUserId(curUser.getUserId());
		
		try{
			BeanUtil.populateEntity(flowRunInfo.getRequest(), register, "errandsRegister");
		}catch(Exception ex){
			logger.error(ex.getMessage());
			return 0;
		}
		register.setApprovalOption(comments);
		if(register.getDateId()!=null){
			ErrandsRegister orgRegister=get(register.getDateId());
			try{
				BeanUtil.copyNotNullProperties(orgRegister,register);
				//加上审批人
				orgRegister.setApprovalId(curUser.getUserId());
				orgRegister.setApprovalName(curUser.getFullname());
				String destName = flowRunInfo.getDestName();
				if(destName.equals("申请人查看结果")){
					orgRegister.setStatus(ErrandsRegister.STATUS_APPROVAL);
				}else{
					orgRegister.setStatus(ErrandsRegister.STATUS_UNAPPROVAL);
				}
				dao.save(orgRegister);
			}catch(Exception ex){
				logger.error(ex);
			}
		}else{
			dao.save(register);
		}
		//加到流程
		flowRunInfo.getVariables().put("dateId", register.getDateId());
		flowRunInfo.setFlowSubject(curUser.getFullname()+"提交请假申请");
		return 1;
	}

}