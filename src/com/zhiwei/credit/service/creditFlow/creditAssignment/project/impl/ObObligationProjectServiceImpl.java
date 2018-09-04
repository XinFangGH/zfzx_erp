package com.zhiwei.credit.service.creditFlow.creditAssignment.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.project.ObObligationProjectDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;
import com.zhiwei.credit.service.creditFlow.creditAssignment.project.ObObligationProjectService;

/**
 * 
 * @author 
 *
 */
public class ObObligationProjectServiceImpl extends BaseServiceImpl<ObObligationProject> implements ObObligationProjectService{
	@SuppressWarnings("unused")
	private ObObligationProjectDao dao;
	
	public ObObligationProjectServiceImpl(ObObligationProjectDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<ObObligationProject> getProjectList(String projectNumber,
			String obligationName, String obligationNumber,
			String projectMoney, String payintentPeriod,String obligationState) {
		// TODO Auto-generated method stub
		return this.dao.getProjectList(projectNumber,obligationName,obligationNumber,projectMoney,payintentPeriod,obligationState);
	}
	@Override
	public ObObligationProject getInfo(String projectId) {
		return dao.getInfo(projectId);
	}


	@Override
	public List<ObObligationProject> getProjectListInfo(String projectName,
			String projectNumber, String payintentPeriod) {
		return this.dao.getProjectListInfo(projectName,projectNumber,payintentPeriod);
	}
	//债权转让系统中定时器定时调用方法用来自动关闭债权到期的债权产品
	public void  obligationAutoClose(){
		try{
			Date CurrentDate =new Date();
			String hql ="from ObObligationProject as obligation where obligation.obligationStatus<>2 order by obligation.id asc";
			List<ObObligationProject> list =this.dao.findByHql(hql, null);
			if(list!=null&&list.size()>0){
				for(ObObligationProject temp:list){
					if(temp.getIntentDate().compareTo(CurrentDate)<=0){
						temp.setObligationStatus(Short.valueOf("2"));
						temp.setFactEndDate(CurrentDate);
						this.dao.merge(temp);
						logger.info("成功关闭了【"+temp.getObligationName()+"】债权产品");
					}else{
						logger.info("不能关闭【"+temp.getObligationName()+"】债权产品，由于还没有到关闭时间");
					}
				}
			}else{
				logger.info("查询需要可能需要关闭的债权产品的list为null");
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("定时器自动关闭到期债权产品出错----"+e.getMessage());
		}
	}
}