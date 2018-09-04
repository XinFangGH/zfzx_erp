package com.zhiwei.credit.dao.flow.impl;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
 */
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ParamUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.ProcessRunDao;
import com.zhiwei.credit.model.flow.ProcessRun;

/**
 * @description 运行中的流程管理
 * @class ProcessRunDaoImpl
 * @author 智维软件
 * @updater YHZ
 * @company www.credit-software.com
 * @data 2010-12-28PM
 * 
 */
@SuppressWarnings("unchecked")
public class ProcessRunDaoImpl extends BaseDaoImpl<ProcessRun> implements
		ProcessRunDao {

	public ProcessRunDaoImpl() {
		super(ProcessRun.class);
	}

	/**
	 * get ProcessRun by PiId
	 * 
	 * @param piId
	 * @return
	 */
	public ProcessRun getByPiId(String piId) {
		String hql = "from ProcessRun pr where pr.piId=?";
		ProcessRun pr=(ProcessRun) findUnique(hql, new Object[] { piId });
		return pr;
	}

	/**
	 * ProcessRun
	 * 
	 * @param defId
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByDefId(Long defId, PagingBean pb) {
		String hql = " from ProcessRun pr where pr.proDefinition.defId=? ";
		return findByHql(hql, new Object[] { defId }, pb);
	}

	/**
	 * 按标题模糊查询某个用户所参与的流程列表
	 * 
	 * @param userId
	 * @param processName
	 * @param pb
	 * @return
	 */
	public List<ProcessRun> getByUserIdSubject(Long userId, String subject,
			PagingBean pb) {

		ArrayList params = new ArrayList();
		String hql = "select pr from ProcessRun as pr join pr.processForms as pf where pf.creatorId=? group by pr.runId order by pr.createtime desc";
		params.add(userId);
		if (StringUtils.isNotEmpty(subject)) {
			hql += " and pr.subject like ?";
			params.add("%" + subject + "%");
		}

		return findByHql(hql, params.toArray(), pb);
	}

	/**
	 * 根据流程定义Id查询对应的数据，如果存在：true
	 */
	@Override
	public boolean checkRun(Long defId) {
		String hql = "select r from ProcessRun r where r.proDefinition.defId = ?";
		Object[] paramList = { defId };
		List<ProcessRun> list = findByHql(hql, paramList);
		return list != null && list.size() > 0 ? true : false;
	}

	@Override
	public List<ProcessRun> getProcessRunning(Long defId) {
		String hql = "from ProcessRun r where r.proDefinition.defId = ? and r.runStatus=1";
		Object[] paramList = { defId };
		return findByHql(hql,paramList);
	}
	
	/**
	 * 项目事项追回-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @return
	 * add by lu 2012.03.08
	 */
	public List<ProcessRun> getProcessRunsByUserIdProcessName(Long userId,String processName,PagingBean pb){
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		hql.append("select distinct vo from ProcessRun as vo join vo.processForms as pf where pf.creatorId=? and vo.runStatus=?");
		
		params.add(userId);
		params.add(ProcessRun.RUN_STATUS_RUNNING);
		
		if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
			String[] proArrs = processName.split(",");
			hql.append(" and (vo.processName like ? ");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				for(int k=1;k<proArrs.length;k++){
					hql.append(" or vo.processName like ? ");
					params.add("%" + proArrs[k] + "%");
					if(k==proArrs.length-1){
						hql.append(")");
					}
				}
			}else{
				hql.append(")");
			}
		}
		hql.append(" order by vo.createtime desc");
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 我参与的项目-根据用户id和业务流程key查询相应任务
	 * @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject){
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		try{
			hql.append("select distinct vo from ProcessRun as vo join vo.processForms as pf where pf.creatorId=?");
			params.add(userId);
			
			if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
				String[] proArrs = processName.split(",");
				hql.append(" and (vo.processName like ? ");
				params.add("%" + proArrs[0] + "%");
				if(proArrs.length>1){
					for(int k=1;k<proArrs.length;k++){
						hql.append(" or vo.processName like ? ");
						params.add("%" + proArrs[k] + "%");
						if(k==proArrs.length-1){
							hql.append(")");
						}
					}
				}else{
					hql.append(")");
				}
			}
			//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
			if(createTimeStart!=null&&!"".equals(createTimeStart)&&!"null".equals(createTimeStart)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Object value=ParamUtil.convertObject("DL", createTimeStart);
				hql.append(" and vo.createtime >= '").append(sdf.format(value)).append("'");
			}
			if(createTimeEnd!=null&&!"".equals(createTimeEnd)&&!"null".equals(createTimeEnd)){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Object value=ParamUtil.convertObject("DG", createTimeEnd);
				hql.append(" and vo.createtime <= '").append(sdf.format(value)).append("'");
			}
			if(runStatus!=null&&!"".equals(runStatus)&&!"null".equals(runStatus)){
				short runs = Short.parseShort(runStatus);
				hql.append(" and vo.runStatus=").append(runs);
			}
			if(subject!=null&&!"".equals(subject)&&!"null".equals(subject)){
				hql.append(" and vo.subject like '%").append(subject).append("%'");
			}
			hql.append(" order by vo.createtime desc");
		}catch(Exception e){
			e.printStackTrace();
		}
		return findByHql(hql.toString(),params.toArray(),pb);
	}

	/**
	 * 我发起的项目-根据用户id和业务流程key查询相应任务
	* @param userId
	 * @param processName
	 * @param PagingBean
	 * @param createTimeStart
	 * @param createTimeEnd
	 * @param runStatus
	 * @param subject
	 * @return
	 * add by lu 2012.03.09
	 */
	public List<ProcessRun> getMyCreateProjectsByUserIdProcessName(Long userId,String processName,PagingBean pb,String createTimeStart,String createTimeEnd,String runStatus,String subject){
		List<Object> params=new ArrayList<Object>();
		StringBuffer hql=new StringBuffer();
		
		hql.append("select distinct vo from ProcessRun as vo where vo.appUser.userId=?");
		params.add(userId);
		
		if(StringUtils.isNotEmpty(processName)&&!"ALL".equals(processName)){
			String[] proArrs = processName.split(",");
			hql.append(" and (vo.processName like ? ");
			params.add("%" + proArrs[0] + "%");
			if(proArrs.length>1){
				for(int k=1;k<proArrs.length;k++){
					hql.append(" or vo.processName like ? ");
					params.add("%" + proArrs[k] + "%");
					if(k==proArrs.length-1){
						hql.append(")");
					}
				}
			}else{
				hql.append(")");
			}
		}
		
		//第一次查询时候为null,把查询条件的值去掉后为""或者"null",多重并列判断。
		if(createTimeStart!=null&&!"".equals(createTimeStart)&&!"null".equals(createTimeStart)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Object value=ParamUtil.convertObject("DL", createTimeStart);
			hql.append(" and vo.createtime >= '").append(sdf.format(value)).append("'");
		}
		if(createTimeEnd!=null&&!"".equals(createTimeEnd)&&!"null".equals(createTimeEnd)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Object value=ParamUtil.convertObject("DG", createTimeEnd);
			hql.append(" and vo.createtime <= '").append(sdf.format(value)).append("'");
		}
		if(runStatus!=null&&!"".equals(runStatus)&&!"null".equals(runStatus)){
			short runs = Short.parseShort(runStatus);
			hql.append(" and vo.runStatus=").append(runs);
		}
		if(subject!=null&&!"".equals(subject)&&!"null".equals(subject)){
			hql.append(" and vo.subject like '%").append(subject).append("%'");
		}
		
		hql.append(" order by vo.createtime desc");
		return findByHql(hql.toString(),params.toArray(),pb);
	}
	
	/**
	 * 查询小贷常规流程、快速流程是否存在展期流程。add by lu 2012.04.24
	 * @param projectId
	 * @param businessType
	 * @param processName
	 * @return
	 * add by lu 2012.04.24
	 */
	public List<ProcessRun> getByProcessNameProjectId(Long projectId,String businessType,String processName){
		String hql = " from ProcessRun pr where pr.projectId=? and pr.businessType=? and pr.processName like '%"+processName+"%' order by pr.runId desc";
		return findByHql(hql, new Object[] { projectId,businessType });
	}

	@Override
	public ProcessRun getByBusinessTypeProjectId(Long projectId,
			String businessType) {
		String hql="from ProcessRun pr where pr.projectId=? and pr.businessType=?";
		List<ProcessRun> list=findByHql(hql,new Object[]{projectId,businessType});
		ProcessRun pr=null;
		if(null!=list && list.size()>0){
			pr=list.get(0);
		}
		return pr;
	}
}