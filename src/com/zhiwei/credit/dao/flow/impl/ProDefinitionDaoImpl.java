package com.zhiwei.credit.dao.flow.impl;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.flow.ProDefinitionDao;
import com.zhiwei.credit.model.flow.ProDefinition;
import com.zhiwei.credit.model.system.AppRole;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.system.GlobalTypeService;

public class ProDefinitionDaoImpl extends BaseDaoImpl<ProDefinition> implements ProDefinitionDao{

	@Resource
	private GlobalTypeService globalTypeService;
	
	public ProDefinitionDaoImpl() {
		super(ProDefinition.class);
	}
	
	public ProDefinition getByDeployId(String deployId){
		String hql="from ProDefinition pd where pd.deployId=?";
		return (ProDefinition)findUnique(hql, new Object[]{deployId});
	}
	
	public ProDefinition getByName(String name){
		String hql="from ProDefinition pd where pd.name=?";
		return (ProDefinition)findUnique(hql, new Object[]{name});
	}

	@Override
	public List<ProDefinition> getByRights(AppUser curUser, ProDefinition proDefinition,QueryFilter filter) {
		String uIds = "%,"+curUser.getUserId()+",%";
		String dIds = "%,"+curUser.getDepartment().getDepId()+",%";
		
		List params = new ArrayList();
		StringBuffer hql =new StringBuffer("select pd from ProDefRights pr right join pr.proDefinition pd  where 1=1 ");
		
		if(proDefinition!=null && proDefinition.getName()!=null){
			hql.append("and pd.name like = ?");
			params.add("%"+proDefinition.getName()+"%");
		}
		
		hql.append("and (pr.userIds like ?  or pr.depIds like ? ");
		params.add(uIds);
		params.add(dIds);
		
		Set<AppRole> roles = curUser.getRoles();
		for(Iterator it = roles.iterator();it.hasNext();){
			AppRole role = (AppRole)it.next();
			hql.append("or pr.roleIds like ? ");
			String rIds = "%,"+role.getRoleId()+",%";
			params.add(rIds);
		}
		
		hql.append(")");
				
		List<ProDefinition> result = findByHql(hql.toString(),params.toArray());
		filter.getPagingBean().setTotalItems(result.size());
		return result;
	}

	@Override
	public boolean checkNameByVo(ProDefinition proDefinition) {
		boolean idIsNull = false;
		if(proDefinition.getDefId() ==null){
			idIsNull = true;
		}
		StringBuffer hql = new StringBuffer("from ProDefinition pd where pd.name = ?");
		List params = new ArrayList();
		params.add(proDefinition.getName());
		if(!idIsNull){
			hql.append("and pd.defId != ?");
			params.add(proDefinition.getDefId());
		}
		List result = findByHql(hql.toString(), params.toArray());
		if(result.size()>0){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public boolean checkProcessNameByVo(ProDefinition proDefinition) {
		boolean idIsNull = false;
		if(proDefinition.getDefId() ==null){
			idIsNull = true;
		}
		StringBuffer hql = new StringBuffer("from ProDefinition pd where pd.processName = ?");
		List params = new ArrayList();
		params.add(proDefinition.getProcessName());
		if(!idIsNull){
			hql.append("and pd.defId != ?");
			params.add(proDefinition.getDefId());
		}
		List result = findByHql(hql.toString(), params.toArray());
		if(result.size()>0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 按Ids 来取到所有的列表
	 * @param docIds
	 * @return
	 */
	private List<ProDefinition> getByIds(List defIds){

		String docHql="from ProDefinition pd where pd.defId in (";
		StringBuffer sbIds=new StringBuffer();
		
		for(int i=0;i<defIds.size();i++){
			sbIds.append(defIds.get(i).toString()).append(",");
		}
		
		if(defIds.size()>0){
			sbIds.deleteCharAt(sbIds.length()-1);
			docHql+=sbIds.toString() + ")";
			return(List<ProDefinition>) findByHql(docHql);
		}else{
			return new ArrayList();
		}
	}
	@Override
	public List<ProDefinition> findRunningPro(ProDefinition proDefinition,
			Short runstate, PagingBean pb) {
		StringBuffer hql=new StringBuffer("select distinct pd.defId from ProcessRun pr join pr.proDefinition pd  where pr.runStatus=?");
		List params=new ArrayList();
		params.add(runstate);
		if(proDefinition!=null&&StringUtils.isNotEmpty(proDefinition.getName())){
			hql.append(" and pd.name like ?");
			params.add("%" +proDefinition.getName()+ "%");
		}
		hql.append(" order by pd.defId desc");
		
		List<Long> defIds=find(hql.toString(),params.toArray(), pb);
		
		return getByIds(defIds);
	}

	@Override
	public ProDefinition getByProcessName(String processName) {
		// TODO Auto-generated method stub
		String hql="from ProDefinition pd where pd.processName=?";
		return (ProDefinition)findUnique(hql, new Object[]{processName});
	}

	@Override
	public List<ProDefinition> getByProTypeId(Long proTypeId,boolean isGroupVersion,Long branchCompanyId) {
	
		String hql="from ProDefinition pd where pd.proType.proTypeId=? and pd.status=1";
		
		if(isGroupVersion){//集团版
			hql+=" and pd.branchCompanyId is not null and pd.branchCompanyId !=0 and pd.branchCompanyId="+branchCompanyId;
		}else{//add by gao 非集团版 不显示集团版流程
			hql+=" and pd.branchCompanyId = 0 ";
		}
		
		return findByHql(hql, new Object[]{proTypeId});
	}
	
	@Override
	public List<ProDefinition> getNormalFlowByProTypeId(Long proTypeId) {
	
		//String hql="from ProDefinition pd where pd.proType.proTypeId=? and pd.status=1 and (pd.branchCompanyId is null or pd.branchCompanyId=0)";
		String hql="from ProDefinition pd where pd.proType.proTypeId=? and (pd.branchCompanyId is null or pd.branchCompanyId=0)";
		
		return findByHql(hql, new Object[]{proTypeId});
	}

	@Override
	public ProDefinition getByKey(String key) {
		// TODO Auto-generated method stub
		
	    String hql="from ProDefinition pd where pd.DEFKEY=? and pd.status=1";
		
		return findByHql(hql, new Object[]{key}).get(0);
	}
	
	/**
	 * @description 根据分公司id查询对应流程信息列表
	 * @param branchCompanyId
	 */
	public List<ProDefinition> getByBranchCompanyId(Long branchCompanyId,PagingBean pb,String flowName,String flowDesc){
		String hql="from ProDefinition pd where 1=1";
		if(branchCompanyId==0){
			hql+=" and pd.branchCompanyId=? or pd.branchCompanyId is null";//标准版流程
		}else if(branchCompanyId==-1){
			branchCompanyId = new Long(0);//标准版的参数值
			hql+=" and pd.branchCompanyId!=? and pd.branchCompanyId is not null";//非标准版流程
		}else{
			hql+=" and pd.branchCompanyId=?";//分公司对应流程
		}
		
		if(flowName!=null&&!"".equals(flowName)){
			hql+=" and pd.name like '%"+flowName+"%'";
		}
		if(flowDesc!=null&&!"".equals(flowDesc)){
			hql+=" and pd.name like '%"+flowDesc+"%'";
		}
		
		return findByHql(hql, new Object[] { branchCompanyId }, pb);
	}
	
	/**
	 * 根据companyId和processName查询对应的流程定义
	 * @param companyId
	 * @param processName
	 * add by lu 2012.12.27
	 */
	public ProDefinition getByCompanyIdProcessName(Long companyId,String processName){
		String hql = "from ProDefinition pd where pd.branchCompanyId=? and pd.processName like '%"+processName+"%'";
		List<ProDefinition> list=findByHql(hql,new Object[]{companyId});
		ProDefinition pdf = null;
		if(list!=null&&list.size()!=0){
			pdf = list.get(0);
		}
		return pdf;
	}
	
	/**
	 * @description 根据流程的key查询已经分配了该流程的分公司
	 * @param processName
	 * add by lu 2013.07.04
	 */
	public List<ProDefinition> listByProcessName(String processName){
		String hql = "from ProDefinition pd where pd.processName like '%"+processName+"%'"+" and pd.branchCompanyId<>0 order by pd.branchCompanyId desc";
		return findByHql(hql);
	}

	@Override
	public ProDefinition getDefByKey(String key) {
		// TODO Auto-generated method stub
		 String hql="from ProDefinition pd where pd.DEFKEY=?";
			return findByHql(hql, new Object[]{key}).get(0);
	}
}