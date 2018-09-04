package com.zhiwei.credit.service.creditFlow.customer.person.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.credit.proj.entity.VPersonRelationperson;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JDBCUtilHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

@SuppressWarnings({"unchecked","unused"})
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService{
	private PersonDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private OrganizationDao organizationDao;
	private final Log log = LogFactory.getLog(getClass());
	public PersonServiceImpl(PersonDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Person getById(int id) {
		
		return dao.getById(id);
	}

	@Override
	public void ajaxQueryPerson(String searchCompanyId,int start, int limit ,String userIds, String hql ,String[] str,
			Object[] obj,String sort,String dir,String customerLevel) throws Exception {
		dao.ajaxQueryPerson(searchCompanyId, start, limit, userIds, hql, str, obj, sort, dir, customerLevel);
	}
	@Override
	public void ajaxQueryPersonLevel(String searchCompanyId, int start, int limit,
			String[] userIds, String hql, String[] str, Object[] obj,
			String sort, String dir, String customerLevel,String orgUserId) throws Exception {
		dao.ajaxQueryPersonLevel(searchCompanyId, start, limit, userIds, hql, str, obj, sort, dir, customerLevel,orgUserId);
	}
	@Override
	public void addPerson(Person person)throws Exception {
		String str="";
		if(person.getId() != null){
			creditBaseDao.updateDatas(person);
			JsonUtil.responseJsonString("{success:true,exsit:true,msg :'添加成功',newId:'"+person.getId()+"'}");
		}else{
			
			
			Organization organization=null;
			HttpSession session=(HttpSession)MySessionFilter.session.get();
			if(null!=session && null!=session.getAttribute("company")){
			    String companyKey=(String)session.getAttribute("company");
			     organization=organizationDao.getBranchByKey(companyKey);
			}
			else{
				 organization=organizationDao.getGroupCompany();
			}
			
			String cardnumber = person.getCardnumber() ;
			if(null!=organization){
			person.setCompanyId(organization.getOrgId());}
			Person per = queryPersonCardnumber(cardnumber);
			if(null != per){
				JsonUtil.responseJsonString("{success:true,exsit:false,msg :'该人员已存在！'}");
				return ;
			}
			if(creditBaseDao.saveDatas(person)){
				per = queryPersonCardnumber(cardnumber);
				int pId =0;
				if(null!=per && null!= per.getId()){
					pId=per.getId();
				};
				if(null!=per && null!=per.getMateid()){
					Person p = (Person)creditBaseDao.getById(Person.class, person.getMateid());
					
					if(per.getMarry() != null || per.getMarry()!=0){
						per.setMarry(648);
						creditBaseDao.updateDatas(per);
					}
					
					p.setMarry(648);
					p.setMateid(pId);
					creditBaseDao.updateDatas(p);
				}
				log.info("添加成功");
				//JsonUtil.jsonFromObject(null, true);
				//JsonUtil.responseJsonString("{success:true,exsit:false,msg :'业务系统企业信息添加成功!      与财务系统对接信息："+str+"',newId:'"+pId+"'}");
		   		JsonUtil.responseJsonString("{success:true,exsit:true,newId:'"+person.getId()+"'}");
				//return ;
			}else{
				log.error("添加失败");
				JsonUtil.responseJsonString("{success:false,exsit:true}");
				//JsonUtil.jsonFromObject(null, false);
				//JsonUtil.responseJsonString("{success:false,exsit:true,msg :'业务系统企业信息添加失败!      与财务系统对接信息："+str+"'}");
			}
		}
	}
	@Override
	public Person queryPersonCardnumber(String cardNumber)throws Exception{
		return dao.queryPersonCardnumber(cardNumber);
	}

	@Override
	public VPersonDic queryPerson(int id) {
		
		return dao.queryPerson(id);
	};
	@Override
	public void updatePerson(Person person) {
		String str="";
		try{
			
			
			String cardnumber = person.getCardnumber() ;
			Person pers = (Person)creditBaseDao.getById(Person.class, person.getId());
			if(cardnumber != pers.getCardnumber() && !cardnumber.equals(pers.getCardnumber())){
				if(null != queryPersonCardnumber(cardnumber)){
					JsonUtil.responseJsonString("{success:true,exsit:false,msg :'该人员已存在！'}");
					return ;
				}
			}
			creditBaseDao.clearSession();//清session
			if(creditBaseDao.updateDatas(person)){
				if(person.getMateid() != null){
					Person p = (Person)creditBaseDao.getById(Person.class, person.getMateid());
					if(p != null){
						if(person.getMarry() == null || person.getMarry()==0){
							person.setMarry(648) ;
							creditBaseDao.updateDatas(person);
						}
						p.setMateid(person.getId());
						p.setMarry(648);
						creditBaseDao.updateDatas(p);
					}
				}
				log.info("更新成功");
				
				//JsonUtil.responseJsonString("{success:true,exsit:false,msg :'业务系统企业信息更新成功!     与财务系统对接信息："+str+"'}");
		   		JsonUtil.responseJsonString("{success:true,exsit:true,msg :'个人客户信息更新成功!',newId:'"+person.getId()+"'}");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("更新失败");
			JsonUtil.responseJsonString("{success:true,exsit:false,msg :'个人客户信息更新失败!'}");
			//JsonUtil.responseJsonString("{success:false,exsit:false,msg :'业务系统企业信息更新成功!      与财务系统对接信息："+str+"'}");
		}
		
	}
	@Override
	public void deletePerson(String[] strTable ,String[] listId,String turl) throws Exception{
		JDBCUtilHelper.batchDelete(strTable, listId,turl);
		log.info("删除成功");
		JsonUtil.responseJsonSuccess();
		//JsonUtil.jsonFromObject(null, true);
	}

	@Override
	public List<Person> getAllList() {
		
		return dao.getAllList();
	}

	@Override
	public List<VPersonDic> ajaxQueryPersonForCombo(String query, int start,int limit, String userIds) {
		return dao.ajaxQueryPersonForCombo(query, start, limit, userIds);
	}

	@Override
	public void addPersonFamily(Person person) {
		try{
			Person p=dao.getById(person.getId());
			BeanUtil.copyNotNullProperties(p, person);
			dao.merge(p);
			JsonUtil.responseJsonSuccess();
		}catch(Exception e){
			JsonUtil.responseJsonFailure();
			e.printStackTrace();
		}
	}

	@Override
	public VPersonDic queryPerson(Integer id) {
		
		return dao.queryPerson(id);
	}
	@Override
	public  void addPersonImport(Person person){
		try
		{
			dao.save(person);
			String jsonStr = "{success : true}";
			JsonUtil.responseJsonString(jsonStr,"text/html; charset=utf-8");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getListBypersonId(Integer start, Integer limit, int personId,String  flag) {
		try {
			String hql = "from VPersonRelationperson AS d where d.personId=? and d.flag=?";
			List list = null;	
			Object[] params=new Object[]{personId,flag};
			list = creditBaseDao.queryHql(hql, params, start, limit);
			int total = list.size();
			JsonUtil.jsonFromList(list, total);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addRelationPerson(PersonRelation personRelation) {
		try {
			creditBaseDao.saveDatas(personRelation);
			JsonUtil.jsonFromObject(null, true) ;
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null, false) ;
		}
	}

	@Override
	public void deletePersonRelationById(int id) {
		try {
			creditBaseDao.deleteDatas(PersonRelation.class, id);
			JsonUtil.responseJsonString("{success:true,msg:'操作成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.responseJsonString("{success:false,msg:'操作失败!'}");
		}
	}

	@Override
	public void seePersonRelation(int id) {
		VPersonRelationperson vPersonRelationperson;
		try {
			vPersonRelationperson = (VPersonRelationperson)creditBaseDao.getById(VPersonRelationperson.class, id);
			JsonUtil.jsonFromObject(vPersonRelationperson, true);
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.jsonFromObject(null,false);
		}
	}

	@Override
	public void updateRelationPerson(PersonRelation personRelation) {
		try {
			creditBaseDao.updateDatas(personRelation);
			JsonUtil.responseJsonString("{success:true,msg:'操作成功!'}");
		} catch (Exception e) {
			e.printStackTrace();
			JsonUtil.responseJsonString("{success:false,msg:'操作失败!'}");
		}
	}

	@Override
	public List<Person> personList(Integer start, Integer limit, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.personList(start, limit,request);
	}

	@Override
	public Person saveSinglePerson(Person person) {
		
		return dao.saveSinglePerson(person);
	}
	@Override
	public String savePersonInfo(Person person, Spouse spouse,WorkCompany workCompany) {
	
		return dao.savePersonInfo(person, spouse, workCompany);
	}

	@Override
	public void perList(PageBean<Person> pageBean,Map<String,String> map) {
		dao.perList(pageBean,map);
	}

	@Override
	public Long personCount(HttpServletRequest request, String[] userIds) {
		// TODO Auto-generated method stub
		return dao.personCount(request, userIds);
	}
	//查询个人客户资金账户表
	@Override
	public List<Person> getAllAccountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.getAllAccountList(map,pb);
	}

	/**
	 * 
	 * @param start
	 * @param limit
	 * @param request
	 * @param userIdsStr
	 * @return加权限的个人客户查询
	 */
	public List<Person> personList(Integer start, Integer limit,
			HttpServletRequest request, String userIdsStr) {
		// TODO Auto-generated method stub
		return dao.personList(start, limit, request, userIdsStr);
	}
	@Override
	public List<Person> getPersonByName(String name,PageBean<Enterprise> pb) {
		return dao.getPersonByName(name,pb);
	}

	@Override
	public BigDecimal getAvailableMoney(String name) {


		return dao.getAvailableMoney(name);
	}

	@Override
	public List<Enterprise> queryEnterpriseMoneyByName(String name) {
		return dao.queryEnterpriseMoneyByName(name);
	}


}