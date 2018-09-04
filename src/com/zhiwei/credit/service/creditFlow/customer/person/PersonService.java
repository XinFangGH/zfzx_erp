package com.zhiwei.credit.service.creditFlow.customer.person;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;


public interface PersonService extends BaseService<Person>{
	public Person getById(int id);
	public void ajaxQueryPerson(String searchCompanyId,int start, int limit ,String userIds, String hql ,String[] str,
			Object[] obj,String sort,String dir,String customerLevel) throws Exception;
	public void addPerson(Person person)throws Exception;
	public Person queryPersonCardnumber(String cardNumber)throws Exception;
	public VPersonDic queryPerson(int id);
	public void updatePerson(Person person);
	public void deletePerson(String[] strTable ,String[] listId,String turl) throws Exception;
	public List<Person> getAllList();
	public List<VPersonDic> ajaxQueryPersonForCombo(String query,int start ,int limit,String userIds);
	public void addPersonFamily(Person person);
	public VPersonDic queryPerson(Integer id);
	public  void addPersonImport(Person person);
	public void getListBypersonId(Integer start, Integer limit, int intValue,String flag);
	public void addRelationPerson(PersonRelation personRelation);
	public void deletePersonRelationById(int id);
	public void seePersonRelation(int parseInt);
	public void updateRelationPerson(PersonRelation personRelation);
	public void ajaxQueryPersonLevel(String searchCompanyId, int start, int limit,
			String[] userIds, String hql, String[] str, Object[] obj,
			String sort, String dir, String customerLevel,String orgUserId)  throws Exception;
	public List<Person> personList(Integer start, Integer limit, HttpServletRequest request);
	//单纯只保存个人借款客户信息
	public Person saveSinglePerson(Person person);
	//保存客户信息
	public String savePersonInfo(Person person,Spouse spouse,WorkCompany workCompany);
	
	/**
	 * 个人客户列表查询
	 * @param pageBean
	 */
	public void perList(PageBean<Person> pageBean,Map<String,String> map);
	
	public Long personCount(HttpServletRequest request,String [] userIds);
	//查询个人客户资金账户表
	public List<Person> getAllAccountList(Map map, PagingBean pb);
	/**
	 * 
	 * @param start
	 * @param limit
	 * @param request
	 * @param userIdsStr
	 * @return加权限的个人客户查询
	 */
	public List<Person> personList(Integer start, Integer limit, HttpServletRequest request,String userIdsStr);
	
	public List<Person> getPersonByName(String  name,PageBean<Enterprise> pb) ;

	/**
	 *
	 * 查询法人剩余可用额度
	 * @auther: XinFang
	 * @date: 2018/6/21 10:09
	 */
    BigDecimal getAvailableMoney(String name);

    /**
     *查询法人名下企业剩余可用额度
     *
     * @auther: XinFang
     * @date: 2018/6/21 10:12
     */
	List<Enterprise> queryEnterpriseMoneyByName(String name);
}