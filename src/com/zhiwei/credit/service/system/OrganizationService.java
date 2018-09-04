package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Organization;

public interface OrganizationService extends BaseService<Organization>{
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId);
	/**
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByPIdAndType(Long parentId,Short orgType);
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path);
	
	/**
	 * 删除某个岗位及其下属组织
	 * @param posId
	 */
	public void delCascade(Long orgId,List<AppUser> users);
	
	/**
	 * 取得所有分公司
	 * @return
	 */
    public List<Organization> getBranchCompany(Map<String, String> map);

    
    public Organization getHeadquarters();
    
    public List<Organization> getBranchByPage(int start, int limit);
    
    public long getBranchCount();
    
    /**
     * 取得集团公司 
     * @return
     */
    public Organization getGroupCompany();
    
    /**
     * 根据公司代码取得分公司
     */
    
    public Organization getBranchCompanyByKey(String  companyKey);

    
    
    public List<Organization> getDepartmentByCompany(Long parentId);

    public List<Organization> getByParentOfZC(Long parentId, Long demId);
    
    public List<Organization> getByParentOfZControl(Long parentId, Long demId);
    
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
    
    public void getShopList(Integer start, Integer limit);
    
    public List<Organization> getShopList(PagingBean pb,String str);
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
	 * add by huanggh
	 */
	public Organization getByUserIdToStoreNameAndStoreNameId(Long shopId);
	
	
	  /**
     * 找出所在门店
     */
    public Organization recursionFindOrganiztion(Organization org);
    public List<Organization> listOrgan();
    public Organization organizationOne(String recommendCode);
	int checkSettlementType(Long orgId);

    List<Organization> getByOrgSupId(Long orgId);
}


