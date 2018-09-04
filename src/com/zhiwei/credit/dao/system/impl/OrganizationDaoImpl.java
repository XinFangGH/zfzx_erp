package com.zhiwei.credit.dao.system.impl;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.system.Organization;

@SuppressWarnings("unchecked")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDao{

	public OrganizationDaoImpl() {
		super(Organization.class);
	}
	
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	public List<Organization> getByParent(Long parentId,Long demId){
		ArrayList params=new ArrayList();
		String hql="from Organization p where p.orgSupId=?";
		params.add(parentId);
		if(demId!=null && demId!=0){
			hql+= " and p.demId=? ";
			params.add(demId);
		}
		hql+= " order by p.orgId asc";
		return findByHql(hql, params.toArray());
	}
	/**
	 * 取得某个节点下的所有子节点
	 * @param parentId
	 * @param demId
	 * @return
	 */
	@Override
	public List<Organization> getByPIdAndType(Long parentId,Short orgType){
		ArrayList params=new ArrayList();
		String hql="from Organization p where p.orgSupId=?";
		params.add(parentId);
		if(orgType!=null && orgType!=0){
			hql+= " and p.orgType=? ";
			params.add(orgType);
		}
		hql+= " order by p.orgId asc";
		return findByHql(hql, params.toArray());
	}
	/**
	 * 按路径查找所有节点
	 * @param path
	 * @return
	 */
	public List<Organization> getByPath(String path){
		String hql="from Organization p where p.path like ?";
		
		return findByHql(hql,new Object[]{path+"%"});
	}

	@Override
	public List<Organization> getBranchCompany(Map<String, String> map) {
		String hql="from Organization p where p.orgSupId!=0 and p.orgType=1";
		if(null!=map)
		{
			String orgName=map.get("orgName");
			if(null!=orgName && !"".equals(orgName))
			{
				hql+="and p.orgName like '%"+orgName+"%'";
			}
		}
		return findByHql(hql);
	}
	@Override
	public Organization getHeadquarters() {
		String hql="from Organization p where p.orgType=0";
		Organization or=null;
		List<Organization> list=findByHql(hql);
		if(null!=list && list.size()>0){
			or=list.get(0);
		}
		return or;
	}

	@Override
	public List<Organization> getBranchByPage(int start, int limit) {
		String hql="from Organization p where p.orgType=1 or p.orgType=0";
		return getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}
	@Override
	public long getBranchCount(){
		String hql="select count(*) from Organization p where p.orgType=1 or p.orgType=0";
		long count=0;
		List list=getSession().createQuery(hql).list();
		if(null!=list && list.size()>0){
			count=(Long) list.get(0);
		}
		return count;
	}
	@Override
	public Organization getGroupCompany() {
		String hql="from Organization p where p.orgSupId=0 and p.orgType=0";
		List<Organization>  list=findByHql(hql);
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Organization getBranchByKey(String key) {
		String hql="from Organization p where p.orgSupId!=0 and p.orgType=1 and p.key=?";
		ArrayList params=new ArrayList();
		params.add(key);
		List<Organization> list=findByHql(hql, params.toArray());
		if(null!=list && list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public String getBranchIdsStr() {
		
		HttpSession session=(HttpSession)MySessionFilter.session.get();
		
		String ActiveCompanyId="";
		if(null!=session.getAttribute("ActiveCompanyId")){
			ActiveCompanyId=(String)session.getAttribute("ActiveCompanyId");
			
		}
		return ActiveCompanyId;
	}

	@Override
	public List<Organization> getDepartmentByCompany(Long parentId) {
		ArrayList params=new ArrayList();
		String hql="from Organization p where p.orgType=2 and p.orgSupId=?";
		//String hql="from Organization p where (p.orgType=2 and p.path like ?) or (p.orgType=0 and p.orgId=1)";
		params.add(parentId);
		return findByHql(hql, params.toArray());
	}

	@Override
	public List<Organization> getByParentOfZC(Long parentId, Long demId) {
		ArrayList params=new ArrayList();
		String hql="from Organization p where p.orgSupId=? and p.orgType!=1";
		params.add(parentId);
		if(demId!=null && demId!=0){
			hql+= " and p.demId=? ";
			params.add(demId);
		}
		return findByHql(hql, params.toArray());
	}

	@Override
	public List<Organization> getByParentOfZControl(Long parentId, Long demId) {
		ArrayList params=new ArrayList();
		String strs=ContextUtil.getBranchIdsStr();
		String hql="from Organization p where 1=1";
		if(null!=strs && !"".equals(strs)){
			hql=hql+" and p.orgId in ("+strs+")";
		}
		//params.add(parentId);
		if(demId!=null && demId!=0){
			hql+= " and p.demId=? ";
			params.add(demId);
		}
		return findByHql(hql, params.toArray());
	}

	@Override
	public List<Organization> getAllByOrgType() {
		String hql="from Organization as r where r.orgType=0 or r.orgType=1";
		return getSession().createQuery(hql).list();
	}
	@Override
	public boolean isUpdateExit(String property, String str, Long id) {
		String hql="from Organization p where p."+property+"='"+str+"' and p.orgId!="+id;
		Organization or=null;
		List<Organization> list=findByHql(hql);
		if(null!=list && list.size()>0){
			or=list.get(0);
		}
		if(or!=null){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public List<Organization> getOrganizationsGroup(Organization o) {
		//update by lu 2012.12.06 分公司组织结构人员查询问题。以下注释部分为原来的代码。
		String hql = "";
		if(o.getOrgId().toString().equals("1")){//总公司
			hql="from Organization o where  o.orgType=2 and o.orgSupId="+o.getOrgId()+" or o.orgSupId in (select orgId from Organization  where org_type=2 and org_sup_id=1)";
		//	String pathHql = "from Organization  where org_type=2 and org_sup_id=1";
		//update hgh  修改原因：无法检索到门店下部门人员	
			String pathHql = "from Organization  where org_type in(2,3) and org_sup_id=1";
			List<Organization> list = findByHql(pathHql);
			if(list!=null&&list.size()>0){
				hql = "from Organization o where 1=1 and o.path like '%"+list.get(0).getPath()+"%'";
				if(list.size()>1){
					for(int i=1;i<list.size();i++){
						hql += " or o.path like '%"+list.get(i).getPath()+"%'";
						if(i==list.size()-1){
							hql += ")";
						}
					}
				}else{
					hql += ")";
				}
			}
			System.out.println("hql="+hql);
			return findByHql(hql);
		}else{
			hql="from Organization o where o.path like '%"+o.getPath()+"%'";//分公司
			return findByHql(hql);
		}
		
		/*String hql="from Organization o where  o.orgType=2  and o.orgSupId=? or o.orgSupId in (select orgId from Organization  where org_type=2 and org_sup_id=1)";
		
		return findByHql(hql,new Object[]{o.getOrgId()});*/
	}
	
	 /**
     * 根据流程key查询得到的已经分配了相应流程的所有分公司的id
     * @param orgIds
     * @return
     * add by lu 2013.07.04
     */
    public List<Organization> listByOrgIds(Long[] orgIds){
    	StringBuffer sb = new StringBuffer();
    	StringBuffer hql = new StringBuffer("from Organization o where o.orgType in(0,1) and (o.delFlag=1 or o.delFlag is null)");
    	if(orgIds.length > 0) {
			for(Long ids : orgIds) {
				sb.append(ids);
				sb.append(",");
			}
		}
    	String companyIds = "";
		if(!"".equals(sb.toString())) {
			companyIds = sb.toString().substring(0, sb.length()-1);
		}
		hql.append(" and o.orgId in("+companyIds+") order by o.orgId asc");
    	return findByHql(hql.toString());
    }
    
    /**
     * 根据子公司id 获取orgnization对象
     * @param companyId
     * @return
     * @author gaoqingrui 
     */
	public Organization getBranchById(Long companyId) {
		String hql="from Organization p where p.orgSupId!=0 and p.orgType=1 and p.orgId=?";
		ArrayList params=new ArrayList();
		params.add(companyId);
		List<Organization> list=findByHql(hql, params.toArray());
		if(null!=list && list.size()>0){
			return list.get(0);
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
    	StringBuffer sb = new StringBuffer();
    	StringBuffer hql = new StringBuffer("from Organization o where o.orgType in(3) and (o.delFlag=1 or o.delFlag is null)");
    	
    	return findByHql(hql.toString());
    }
    
    public Organization getByUserIdToStoreNameAndStoreNameId(Long shopId) {
		Organization org=null;
		String hql="from Organization as os where os.orgId=?";
		List<Organization> list=findByHql(hql, new Object[]{shopId});
		if(null !=list && !"".equals(list) && list.size()>0){
			org=list.get(0);
		}
		
		return org;
	}

	@Override
	public int checkSettlementType(Long orgId) {
		// TODO Auto-generated method stub
		String sql = "select count(*) from organization where settlementType = 1";
		if(orgId!=null){
			sql = sql+" and org_id <> "+orgId;
		}
		return ((BigInteger)this.getSession().createSQLQuery(sql).uniqueResult()).intValue();
	}

	@Override
	public List<Organization> getByOrgSupId(Long orgId) {

    	String sql = "From Organization where orgSupId= ?";

		List<Organization> byHql = findByHql(sql, new Object[]{orgId});

		return byHql;
	}

	/**
	 * 查询投资推广部、投资部门
	 * @return
	 */
	public List<Organization> listOrgan(){
		String sql = "select * from organization where settlementType in (1,2) ORDER BY createtime desc";
		return this.getSession().createSQLQuery(sql).addEntity(Organization.class).list();
	}
	
	public Organization organizationOne(String recommendCode){
		String sql = "select * from organization where recommendCode = ?";
		return (Organization) this.getSession().createSQLQuery(sql).addEntity(Organization.class).setParameter(0, recommendCode).uniqueResult();
		
	}
	
}