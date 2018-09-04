package com.zhiwei.credit.service.creditFlow.customer.enterprise;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseView;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.VEnterprisePerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;



public interface EnterpriseService extends BaseService<Enterprise>{
	public void ajaxQueryEnterprise(String searchCompanyId,String userIds,String enterprisename ,String ownership,String shortname,String organizecode,String cciaa,int start ,int limit,String sort,String dir,String customerLevel);
	public void ajaxQueryEnterprise(String searchCompanyId,String []userIds,String enterprisename ,String ownership,String shortname,String organizecode,String cciaa,int start ,int limit,String sort,String dir,String customerLevel);
	public void ajaxAddEnterprise(Enterprise enterprise,Person person,List<EnterpriseShareequity> listES,String personSFZZId,String personSFZFId,Map<String,String> enterpriseMap)throws Exception;
	public Enterprise queryEnterpriseName(String name)throws Exception;
	public Enterprise isEmpty(String organizecode)throws Exception;
	public Enterprise getById(int id);
	public List<Enterprise> getListByLegalPersonId(int legalpersonid);
	public void ajaxDeleteEnterpriseWithId(String[] strTable ,String[] listId,String turl) throws Exception;
	public void getSlCompanyInfo(int id);
	public void getList(String customerType,String customerName, String cardNum,String companyId,int start,int limit);
	 public void ajaxQueryEnterpriseForCombo(String query,int start ,int limit);
	 public EnterpriseView getByViewId(Integer id);
	 public EnterpriseView setEnterpriseView(EnterpriseView enView) throws Exception;
	public void getEnterByProject(int parseLong);
	
	//List<EnterpriseView> getList(String enterIds,PagingBean pb,String type);
	void getList(String enterIds,PagingBean pb,String type);
	public void ajaxQueryEnterprise(String searchCompanyId, String[] userIds,
			String enterprisename, String ownership, String shortname,
			String organizecode, String cciaa, int start, int limit,
			String sort, String dir, String customerLevel,String orgUserIds);
	public Enterprise saveSingleEnterprise(Enterprise enterprise,Person person, String gudongInfo);
	/**
	 * 企业客户列表查询
	 * @param request
	 * @param userIds
	 * @param start
	 * @param limit
	 * @return
	 */
	public void entList(PageBean<Enterprise> pageBean,Map<String,String> map);
	
	public Long entCount(HttpServletRequest request,String userIds);
	public List<VEnterprisePerson> getBlackListToExcel(String customerType,String customerName, String cardNum,String companyId,int start,int limit);
	
	/**
	 * 查询出来企业客户来开通和绑定P2P账号
	 * @param start
	 * @param limit
	 * @param request
	 * @return
	 */
	public List<EnterpriseView> enterPriseList(Integer start, Integer limit,
			HttpServletRequest request,String userIdsStr);
	//查询企业客户资金账户表
	public List<EnterpriseView> getAllAccountList(Map map, PagingBean pb);

    Enterprise getByOrganizecode(String cardcode) throws Exception;

	/**
	 * 根据页面传过来的条件查询企业剩余借款额度
	 * ps:此功能是由于富滇银行对企业借款的100w的额度限制
	 * @param pageBean
	 */
	void loanEnterpriseQuota(PageBean pageBean);

    BigDecimal getSurplusMoney(String organizecode);
}