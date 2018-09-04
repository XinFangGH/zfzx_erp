package com.zhiwei.credit.service.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.jbpm.jpdl.Node;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.dao.flow.ProUserAssignDao;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.flow.ProUserAssign;
import com.zhiwei.credit.service.flow.JbpmService;
import com.zhiwei.credit.service.flow.ProUserAssignService;

public class ProUserAssignServiceImpl extends BaseServiceImpl<ProUserAssign> implements ProUserAssignService{
	private ProUserAssignDao dao;
	@Resource
	private ProDefinitionDao proDefinitionDao;
	@Resource
	private JbpmService jbpmService;
	public ProUserAssignServiceImpl(ProUserAssignDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public List<ProUserAssign> getByDeployId(String deployId){
		return dao.getByDeployId(deployId);
	}
	/*
	 * (non-Javadoc)
	 * @see com.zhiwei.credit.service.flow.ProUserAssignService#getByDeployIdActivityName(java.lang.String, java.lang.String)
	 */
	public ProUserAssign getByDeployIdActivityName(String deployId,String activityName){
		return dao.getByDeployIdActivityName(deployId, activityName);
	}
	/**
	 * 把旧版本的流程的人员配置复制至新版本上去
	 * @param oldDeplyId
	 * @param newDeployId
	 * @param subCompanyKey
	 */
	public void copyNewConfig(String oldDeployId,String newDeployId,String subCompanyKey){
		List<ProUserAssign> list=getByDeployId(oldDeployId);
		ProDefinition p = proDefinitionDao.getByDeployId(newDeployId);
		try{
			if(p!=null){
				List<Node> nodes = jbpmService.getTaskNodesByDefId(p.getDefId());
				for(Node temps:nodes){
					int exsist =0;//0表示旧版本中没有该节点，1表示有这个节点
					ProUserAssign temp=new ProUserAssign();
					for(ProUserAssign assign:list){
						if(temps.getName().equals(assign.getActivityName())){
							exsist=1;
							BeanUtil.copyNotNullProperties(temp, assign);
							if(subCompanyKey!=null&&!"".equals(subCompanyKey)){
								String taskSequence = assign.getTaskSequence();
								if(taskSequence!=null&&taskSequence.indexOf("_")!=-1){
									String[] current = taskSequence.split("_");
									String currentNodeKey = "";
									if(current.length>2){//集团分公司的情况,如：10_beijing_flowNodeKeyName
										currentNodeKey = current[2];
									}else{
										currentNodeKey = current[1];//非集团分公司的情况,如：10_flowNodeKeyName
									}
									Long currentNodeNumber = new Long(current[0]);
									temp.setTaskSequence(currentNodeNumber+"_"+subCompanyKey+"_"+currentNodeKey);
								}else{
									temp.setTaskSequence("0_error_error");
									logger.error("设置流程的key不符合节点(顺序_分公司key_节点key)格式,如(100_beijing_flowNodeKey)的规则,请修改!deployId为："+newDeployId+"---分公司key为："+subCompanyKey);
								}
							}
							temp.setAssignId(null);
							temp.setDeployId(newDeployId);
							break;
						}
					}
					if(exsist==0){
						ProUserAssign pu = new ProUserAssign();
						pu.setDeployId(newDeployId);
						pu.setActivityName(temps.getName());
						pu.setUserId("__start");
						pu.setUsername("[发起人]");
						pu.setIsSigned(Short.valueOf("0"));
						pu.setPosUserFlag(Short.valueOf("0"));
						pu.setTaskTimeLimit(Short.valueOf("3"));
						pu.setTaskTimeLimit(Short.valueOf("1"));
						pu.setIsJumpToTargetTask(Short.valueOf("1"));
						pu.setTaskSequence("0");
						pu.setIsProjectChange(Short.valueOf("1"));
						dao.save(pu);
					}else{
						dao.save(temp);
					}
					
				}
				
			}/*   //这个会产生废数据，因此注释了
			for(ProUserAssign assign:list){
				ProUserAssign temp=new ProUserAssign();
				BeanUtil.copyNotNullProperties(temp, assign);
				if(subCompanyKey!=null&&!"".equals(subCompanyKey)){
					String taskSequence = assign.getTaskSequence();
					if(taskSequence!=null&&taskSequence.indexOf("_")!=-1){
						String[] current = taskSequence.split("_");
						String currentNodeKey = "";
						if(current.length>2){//集团分公司的情况,如：10_beijing_flowNodeKeyName
							currentNodeKey = current[2];
						}else{
							currentNodeKey = current[1];//非集团分公司的情况,如：10_flowNodeKeyName
						}
						Long currentNodeNumber = new Long(current[0]);
						temp.setTaskSequence(currentNodeNumber+"_"+subCompanyKey+"_"+currentNodeKey);
					}else{
						temp.setTaskSequence("0_error_error");
						logger.error("设置流程的key不符合节点(顺序_分公司key_节点key)格式,如(100_beijing_flowNodeKey)的规则,请修改!deployId为："+newDeployId+"---分公司key为："+subCompanyKey);
					}
				}
				temp.setAssignId(null);
				temp.setDeployId(newDeployId);
				dao.save(temp);
			}
			if(p!=null){//新的流程可能比旧的流程多出某些节点也可能修改了节点名称，以下进行比对如果旧版本没有的则添加。add by lu 2013.07.05
				//有问题吧，每更新一次，则会增加 (n-1)*n个proUserAssign个对象  update by gao
				List<Node> nodes = jbpmService.getTaskNodesByDefId(p.getDefId());
				for (int i = 0; i < nodes.size(); i++) {
					String nodeName = nodes.get(i).getName();
					Boolean flag = false;//默认false   为不包含
					for(int j=0;j<list.size();j++){
						if(nodeName.equals(list.get(j).getActivityName())){
							flag = true;
							break;
						}
					}
					if(!flag){
						ProUserAssign pu = new ProUserAssign();
						pu.setDeployId(newDeployId);
						pu.setActivityName(nodeName);
						pu.setUserId("__start");
						pu.setUsername("[发起人]");
						pu.setIsSigned(Short.valueOf("0"));
						pu.setPosUserFlag(Short.valueOf("0"));
						pu.setTaskTimeLimit(Short.valueOf("3"));
						pu.setTaskTimeLimit(Short.valueOf("1"));
						pu.setIsJumpToTargetTask(Short.valueOf("1"));
						pu.setTaskSequence("0");
						pu.setIsProjectChange(Short.valueOf("1"));
						dao.save(pu);
					
					}
				}
			}
		*/}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	/**
	 * 取得某流程某个任务的授权信息
	 * @param deployId
	 * @param flowNodeKey
	 * @return
	 * add by lu 2012.07.09
	 */
	public ProUserAssign getByDeployIdFlowNodeKey(String deployId,String flowNodeKey){
		return dao.getByDeployIdFlowNodeKey(deployId, flowNodeKey);
	}
	
	/**
	 * @description 判断并列节点的某个节点是否为主干节点
	 * @return activityName
	 */
	public boolean isMainStemNode(String activityName,String beJuxtaposedFlowNodeKeys){
		boolean isCorrespond = false;
		List<ProUserAssign> list = dao.findByActivityName(activityName);
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				ProUserAssign proUserAssign = list.get(i);
				if(proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())&&proUserAssign.getTaskSequence().indexOf("_")!=-1){
					String[] proArrs = proUserAssign.getTaskSequence().split("_");
					String taskSequence = "";
					if(proArrs.length>2){
						taskSequence = proArrs[2];//集团分公司的情况,如：10_beijing_flowNodeKeyName
					}else{
						taskSequence = proArrs[1];//非集团分公司的情况,如：10_flowNodeKeyName
					}
					if(beJuxtaposedFlowNodeKeys.contains(taskSequence)){
						isCorrespond = true;
						break;
					}
				}
			}
		}
		
		return isCorrespond;
	}
	
	/**
	 * @description 获取存在并列节点的流程的key
	 * @return activityName
	 */
	public String findProcessNameByActivityName(String activityName,String keyElement){
		String processName = "";
		List<ProUserAssign> list = dao.findByActivityName(activityName);
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				ProUserAssign proUserAssign = list.get(i);
				if(proUserAssign.getTaskSequence()!=null&&!"".equals(proUserAssign.getTaskSequence())&&proUserAssign.getTaskSequence().indexOf("_")!=-1){
					if(proUserAssign.getTaskSequence().contains(keyElement)){//可以多重判断如果不同的节点有并列节点的话
						ProDefinition proDefinition = proDefinitionDao.getByDeployId(proUserAssign.getDeployId());
						if(proDefinition!=null){
							processName = proDefinition.getProcessName();
						}
					}
				}
			}
		}
		
		return processName;
	}
	
	/**
	 * 判断某个节点是否为会签节点。直接返回boolean类型。
	 * @param deployId
	 * @param activityName
	 * @return
	 * add by lu 2013.06.17
	 */
	public boolean isCountersignNode(String deployId,String activityName){
		boolean isCountersignNode = false;
		ProUserAssign curProUserAssign = dao.getByDeployIdActivityName(deployId, activityName);
		if(curProUserAssign!=null&&curProUserAssign.getIsSigned()!=null){
			if(curProUserAssign.getIsSigned().toString().equals("1")){//表示为会签节点。字符串比对。
				isCountersignNode = true;
			}
		}
		return isCountersignNode;
	}
}