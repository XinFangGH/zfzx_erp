package com.zhiwei.credit.dao.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.Organization;

/**
 * 
 * @author 
 *
 */
public interface OrganizationDao extends BaseDao<Organization>{
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId);
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path);
	
	
	public List<Organization> getBranchCompany(Map<String, String> map);
	/**
	 * 总公司
	 * @return
	 */
	public Organization getHeadquarters();
	/**
	 * 分页的分公司
	 * @return
	 */
	public List<Organization> getBranchByPage(int start,int limit);
	
	public long getBranchCount();
	 /**
     * 取得集团公司 
     * @return
     */
    public Organization getGroupCompany();
    
    
    /**
     * 根据分公司key值取得公司对象 by shendexuan 20120911
     */
    
    public Organization getBranchByKey(String key);
    
    
    /**
     * 查询当前登录用户session所在公司的ID 值  总公司多个 分公司单个
     */
    
    public String getBranchIdsStr();
    
    
    /**
     * 查询当前公司所有的部门
     */
    
    public List<Organization> getDepartmentByCompany(Long parentId);
    
    public List<Organization> getByParentOfZC(Long parentId,Long demId);
    
    public List<Organization> getByParentOfZControl(Long parentId,Long demId);
    
    public List<Organization> getAllByOrgType();
    /**
     * 更新分公司时判断字段是否重复
     * @param property 字段名称
     * @param str 字段值
     * @param id  排除的对象ID
     * @return
     */
    public boolean isUpdateExit(String property,String str,Long id);

    
    public List<Organization> getOrganizationsGroup(Organization o);
    
    /**
     * 根据流程key查询得到的已经分配了相应流程的所有分公司的id
     * @param orgIds
     * @return
     * add by lu 2013.07.04
     */
    public List<Organization> listByOrgIds(Long[] orgIds);
    
    /**
     * 根据子公司id 获取orgnization对象
     * @param companyId
     * @return
     * @author gaoqingrui 
     */
	public Organization getBranchById(Long companyId);
	List<Organization> getByPIdAndType(Long parentId, Short orgType);
	 /**
     * 获取所有的分店（包括总公司）
     * @param 
     * @return
     * add by pengll 2014.07.29
     */
    public List<Organization> listAllBranch();
    /**
	 * 根据当前登陆用户部门Id获得用户所在的门店名称和门店Id
	 * @param 格式为 id
	 */
	public Organization getByUserIdToStoreNameAndStoreNameId(Long shopId);
    public List<Organization> listOrgan();
    public Organization organizationOne(String recommendCode);
	int checkSettlementType(Long orgId);

    List<Organization> getByOrgSupId(Long orgId);
}