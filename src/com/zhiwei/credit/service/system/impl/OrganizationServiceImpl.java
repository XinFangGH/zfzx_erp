package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.system.OrganizationService;

public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService{
	@SuppressWarnings("unused")
	private OrganizationDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	public OrganizationServiceImpl(OrganizationDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId){
		return dao.getByParent(parentId, demId);
	}
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path){
		return dao.getByPath(path);
	}
	
	/**
	 * 删除某个组织及其下属组织
	 * @param posId
	 */
	public void delCascade(Long orgId,List<AppUser> users){
		Organization org=get(orgId);
		remove(org);
		for(AppUser au:users){
			au.setDepartment(null);
			//au.setOrgId(null);
			au.setDelFlag(Short.valueOf("1"));
			au.setOrgs(null);
		}
		List<Organization> listOrgs=getByPath(org.getPath());
		for(Organization o:listOrgs){
			remove(o);
		}
	}

	@Override
	public List<Organization> getBranchCompany(Map<String, String> map) {
		return dao.getBranchCompany(map);
	}


	@Override
	public Organization getHeadquarters() {
		
		return dao.getHeadquarters();
	}

	@Override
	public List<Organization> getBranchByPage(int start, int limit) {
		
		return dao.getBranchByPage(start, limit);
	}


	@Override
	public Organization getGroupCompany() {

		return dao.getGroupCompany();
	}

	@Override
	public Organization getBranchCompanyByKey(String companyKey) {

		return dao.getBranchByKey(companyKey);
	}


	@Override
	public List<Organization> getDepartmentByCompany(Long parentId) {

		return dao.getDepartmentByCompany(parentId);
	}

	@Override
	public List<Organization> getByParentOfZC(Long parentId, Long demId) {
		
		return dao.getByParentOfZC(parentId, demId);
	}

	@Override
	public List<Organization> getByParentOfZControl(Long parentId, Long demId) {
		
		return dao.getByParentOfZControl(parentId, demId);
	}

	@Override
	public List<Organization> getAllByOrgType() {
		
		return dao.getAllByOrgType();
	}


	@Override
	public boolean isUpdateExit(String property, String str, Long id) {
		// TODO Auto-generated method stub
		return dao.isUpdateExit(property, str, id);
	}

	@Override
	public long getBranchCount() {
		
		return dao.getBranchCount();
	}

	@Override
	public List<Organization> getOrganizationsGroup(Organization o) {
		return dao.getOrganizationsGroup(o);
	}

	 /**
     * 根据流程key查询得到的已经分配了相应流程的所有分公司的id
     * @param orgIds
     * @return
     * add by lu 2013.07.04
     */
    public List<Organization> listByOrgIds(Long[] orgIds){
    	return dao.listByOrgIds(orgIds);
    }

	@Override
	public void getShopList(Integer start, Integer limit) {
		String hql1=" from Organization as o where o.orgType=3";
		List totalList = new ArrayList();
		List<Organization> list = new ArrayList(); 
		int totalProperty = 1 ;
		try {
			totalList = creditBaseDao.queryHql(hql1);
			totalProperty = 0; ;//记录总数
			//查询符合条件的部分记录数，数据库分页
			list = creditBaseDao.queryHql(hql1,start, limit) ;
			if(list!=null&&list.size()>0){
				for(Organization temp :list){
					temp.setDemension(null);
				}
			}

			JsonUtil.jsonFromList(list, totalProperty) ;
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
		}

		
	}

	@Override
	public List<Organization> getByPIdAndType(Long parentId, Short orgType) {
		return this.dao.getByPIdAndType(parentId, orgType);
	}

	@Override
	public List<Organization> getShopList(PagingBean pb,String str) {
		String hql1=" from Organization as o where o.orgType=3";
		/*if(null!=str && !str.equals("")){
			hql1=hql1+" and o.orgId in ("+str+")";
		}*/
		List totalList = new ArrayList();
		List<Organization> list = new ArrayList(); 
		int totalProperty = 1 ;
		try {
			//totalList = creditBaseDao.queryHql(hql1);
			//totalProperty = 0; ;//记录总数
			//查询符合条件的部分记录数，数据库分页
			if(null!=pb){
			list = dao.findByHql(hql1, new Object[]{}, pb);
			}else{
				list = dao.findByHql(hql1, new Object[]{});
			}
			if(list!=null&&list.size()>0){
				for(Organization temp :list){
					dao.evict(temp);
					temp.setDemension(null);
					temp.setAppUsers(null);
					temp.setUserOrgs(null);
				}
			}
			return list;
			
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject("数据库查询出错，请重试", false) ;
		}
		return null;
	}
	 /**
     * 获取所有的分店
     * @param 
     * @return
     * add by pengll 2014.07.29
     */
    public List<Organization> listAllBranch(){
    	return this.dao.listAllBranch();
    }
    /**
	 * 根据当前登陆用户部门Id获得用户所在的门店名称和门店Id
	 * @param 格式为 id
	 * add by huanggh
	 */
    
    public Organization getByUserIdToStoreNameAndStoreNameId(Long shopId) {
		Organization organ=dao.getByUserIdToStoreNameAndStoreNameId(shopId);
		if(null!=organ && organ.getOrgType()==3){
			return organ;
		}else if(null !=organ && !"".equals(organ)){
			return getByUserIdToStoreNameAndStoreNameId(organ.getOrgSupId());
		}else{
			return null;
		}
	}
    
	@Override
	public Organization recursionFindOrganiztion(Organization org) {
		  
		  Organization organization = recursionOrganization(org);
		  return organization;
	}
	
	/**
	 * @param org
	 * @return
	 */
	private Organization recursionOrganization(Organization org){
		Organization organization = org;
		if(null!=organization){
			for(int i =0 ; i <10 ; i++){
				if(null!=organization.getOrgType()&&organization.getOrgType()==3){
					break;
				}
				if(organization.getOrgSupId().compareTo(Long.valueOf(0))!=0){
					organization = this.dao.get(organization.getOrgSupId());
				}
			}
		}
		/**
		 * 循环结束后，再一次判断当前查到的这个组织结构是不是门店，如果是则返回，如果不是则返回NULL
		 */
		if(organization!=null&&null!=organization.getOrgType()&&organization.getOrgType()==3){
			return organization;
		}else{
			return null;
		}
	}

	@Override
	public int checkSettlementType(Long orgId) {
		// TODO Auto-generated method stub
		return this.dao.checkSettlementType(orgId);
	}

	@Override
	public List<Organization> getByOrgSupId(Long orgId) {
		return dao.getByOrgSupId(orgId);
	}

	public List<Organization> listOrgan(){
		return dao.listOrgan();
	}
	public Organization organizationOne(String recommendCode){
		return dao.organizationOne(recommendCode);
	}
}