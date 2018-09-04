package com.zhiwei.credit.dao.creditFlow.customer.enterprise;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.VEnterprisePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;


public interface EnterpriseDao extends BaseDao<Enterprise>{
	public void ajaxQueryEnterprise(String searchCompanyId,String []userIds,String enterprisename ,String ownership,String shortname,String organizecode,String cciaa,int start ,int limit,String sort,String dir,String customerLevel);
	public Enterprise queryEnterpriseName(String name)throws Exception;
	public Enterprise isEmpty(String organizecode)throws Exception;
	public Enterprise getById(int id);
	public List<Enterprise> getListByLegalPersonId(int legalpersonid);
	public void getList(String customerType,String customerName, String cardNum,String companyId,int start,int limit);
	public List<Enterprise> ajaxQueryEnterpriseForCombo(String query,int start ,int limit);
	public EnterpriseView getByViewId(Integer id);
	void getList(String enterIds,PagingBean pb,String type);
	public void ajaxQueryEnterprise(String searchCompanyId, String[] userIds,
				String enterprisename, String ownership, String shortname,
				String organizecode, String cciaa, int start, int limit,
				String sort, String dir, String customerLevel,String orgUserIds) ;
	 //专门用来保存企业客户和法人以及股东信息方法
	public Enterprise saveSingleEnterprise(Enterprise enterprise,Person person, String gudongInfo);
	public void entList(PageBean<Enterprise> pageBean,Map<String,String> map);
	public Long entCount(HttpServletRequest request,String userIds);
	public List<VEnterprisePerson> getBlackListToExcel(String customerType,String customerName, String cardNum,String companyId,int start,int limit);
	public void ajaxQueryEnterprise(String searchCompanyId, String userIds,
			String enterprisename, String ownership, String shortname,
			String organizecode, String cciaa, int start, int limit,
			String sort, String dir, String customerLevel);
	/**
	 * 查询企业用户开通和绑定P2P的页面
	 * @param start
	 * @param limit
	 * @param request
	 * @return
	 */
	public List<EnterpriseView> enterPriseList(Integer start, Integer limit,
			HttpServletRequest request,String userIdsStr);
	//查询企业客户资金账户表
	public List<EnterpriseView> getAllAccountList(Map map, PagingBean pb);
	
	public Enterprise isEmpty(String organizecode,Integer enterId)throws Exception;

    Enterprise getByOrganizecode(String cardcode) throws Exception;

    void loanEnterpriseQuota(PageBean pageBean);

    BigDecimal getSurplusMoney(String organizecode);
}