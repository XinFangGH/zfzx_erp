package com.zhiwei.credit.dao.creditFlow.customer.person.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.google.gson.Gson;
import com.hurong.core.util.BeanUtil;
import com.opensymphony.module.sitemesh.Page;
import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.jbpm.pv.TaskInfo;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.PersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.SpouseDao;
import com.zhiwei.credit.dao.creditFlow.customer.person.workcompany.WorkCompanyDao;
import com.zhiwei.credit.dao.creditFlow.fileUploads.FileFormDao;
import com.zhiwei.credit.model.creditFlow.customer.common.EnterpriseBank;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonRelation;
import com.zhiwei.credit.model.creditFlow.customer.person.Spouse;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.model.creditFlow.customer.person.workcompany.WorkCompany;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;




@SuppressWarnings("unchecked")
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao{
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private FileFormDao fileFormDao;
	@Resource
	private SpouseDao spouseDao;
	@Resource
	private WorkCompanyDao workCompanyDao;
	public PersonDaoImpl() {
		super(Person.class);
	}
	@Override
	public Person getById(int id) {
		String hql="from Person as p where p.id=?";
		return (Person)getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
	@Override
	public void ajaxQueryPerson(String searchCompanyId,int start, int limit ,String userIds, String hql ,String[] str,
			Object[] obj,String sort,String dir,String customerLevel) throws Exception{
		StringBuffer strBuffer = new StringBuffer(hql);
		if(null!=userIds){
			strBuffer.append(" where fn_check_repeat(p.belongedId,'"+userIds+"') = 1 and (p.isBlack=false or p.isBlack is null) and ");
			if(null!=customerLevel && !"".equals(customerLevel)){
				strBuffer.append("p.customerLevel='"+customerLevel+"' and ");
			}
		}else{
			strBuffer.append(" where (p.isBlack=false or p.isBlack is null) and ");
			if(null!=customerLevel && !"".equals(customerLevel)){
				strBuffer.append("p.customerLevel='"+customerLevel+"' and ");
			}
		}
		if(null!=searchCompanyId && !"".equals(searchCompanyId)){
			strBuffer.append(" fn_check_repeat(p.belongedCompanyId,'"+searchCompanyId+"') = 1 and p.belongedCompanyId is not NULL and ");
		}
		int len = obj.length;
		List list = null;
		int total = 0;
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ;){
				if(obj[i] == null){
					obj[i] = "";
				}
				strBuffer.append("(p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"' or(p."+str[j]+" is null and '' = '"+obj[i]+"'))");
				if(len != j+1){
					strBuffer.append(" and ");
				}
				break;
			}
		}
		if(sort!=null && dir!=null){
			if(!"age".equals(sort)){
				strBuffer.append(" order by p."+sort+" "+dir);
			}else{
				dir=("DESC".equals(dir))?"ASC":"DESC";
				strBuffer.append(" order by  p.birthday "+dir);
			}
		}else{
			strBuffer.append("and p.createrId is not null AND  p.belongedId is not null order by p.orgName desc");
		}
		list = creditBaseDao.queryHql(strBuffer.toString());
		if(null != list && !"".equals(list)){
			total = list.size();
		}
		list = creditBaseDao.queryHql(strBuffer.toString(), start, limit);
		JsonUtil.jsonFromList(list, total);
	};
	
	
	
	@Override
	public void ajaxQueryPersonLevel(String searchCompanyId,int start, int limit ,String [] userIds, String hql ,String[] str,
			Object[] obj,String sort,String dir,String customerLevel,String orgUserId) throws Exception{
		StringBuffer strBuffer = new StringBuffer(hql);
		Boolean flag=Boolean.valueOf(AppUtil.getSystemIsGroupVersion());
	    String  roleType=ContextUtil.getRoleTypeSession();
		
		String userIdsStr = "";
		if (userIds != null) {
			StringBuffer sb1 = new StringBuffer();
			for(String userid : userIds) {
				sb1.append(userid);
				sb1.append(",");
			}
			userIdsStr = sb1.toString().substring(0,sb1.length()-1);
		}
		if(flag && roleType.equals("control")){ //如果是集团版本  并且当前roleType为管控角色
			userIdsStr="";
		}
		if(userIdsStr != ""){
			strBuffer.append(" where fn_check_repeat(p.belongedId,'"+userIdsStr+"') = 1 and (p.isBlack=false or p.isBlack is null) and ");
			if(null!=customerLevel && !"".equals(customerLevel)){
				strBuffer.append("p.customerLevel='"+customerLevel+"' and ");
			}
		}else{
			strBuffer.append(" where (p.isBlack=false or p.isBlack is null) and ");
			if(null!=customerLevel && !"".equals(customerLevel)){
				strBuffer.append("p.customerLevel='"+customerLevel+"' and ");
			}
		}
		//String strs=ContextUtil.getBranchIdsStr();//(39,40)
		if(null!=searchCompanyId){
			searchCompanyId=searchCompanyId;
		}
		if(null!=searchCompanyId && !"".equals(searchCompanyId)){
			strBuffer.append(" p.companyId in ("+searchCompanyId+") and");
		}
		if(null!=orgUserId && !"".equals(orgUserId)){
			strBuffer.append(" p.orgUserId ="+Long.valueOf(orgUserId)+" and");
		}
		int len = obj.length;
		List list = null;
		int total = 0;
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ; i ++){
				if(obj[i] == null){
					obj[i] = "";
				}
				//strBuffer.append("p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"'");
				strBuffer.append("(p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"' or(p."+str[j]+" is null and '' = '"+obj[i]+"'))");
				if(len != j+1){
					strBuffer.append(" and ");
				}
			break;
			}
		}
		if(sort!=null && dir!=null){
			if(!"age".equals(sort)){
				//strBuffer.append(" order by CONVERT(p."+sort+" , 'GBK') "+dir);
				strBuffer.append(" order by p."+sort+" "+dir);
			}else{
				dir=("DESC".equals(dir))?"ASC":"DESC";
				strBuffer.append(" order by  p.birthday "+dir);
			}
			
		}else{
			strBuffer.append("and p.createrId is not null AND  p.belongedId is not null order by p.id desc");
		}
		
		list = creditBaseDao.queryHql(strBuffer.toString());
		if(null != list && !"".equals(list)){
			total = list.size();
		}
		list = creditBaseDao.queryHql(strBuffer.toString(), start, limit);
		List arrayList = new ArrayList();
		for(int i = 0 ; i < list.size() ; i ++){
			VPersonDic vperson = (VPersonDic)list.get(i);
			if(vperson.getBirthday() != null){
				Integer age = getAge(vperson.getBirthday());
				if(age == 0){
					vperson.setAge("");
				}else
					vperson.setAge(age.toString());
			}else{
				vperson.setAge("");
			}
			if(null!=vperson.getId()){
				List<EnterpriseBank> elist=enterpriseBankDao.getBankList(vperson.getId(), Short.valueOf("1"), Short.valueOf("0"),Short.valueOf("0"));
				if(null!=elist && elist.size()>0){
					EnterpriseBank bank=elist.get(0);
					vperson.setBankName(bank.getBankname());
					vperson.setBankNum(bank.getAccountnum());
					vperson.setEnterpriseBankId(bank.getId());
					vperson.setBankId(bank.getBankid());
					vperson.setAccountType(bank.getAccountType());
					vperson.setKhname(bank.getName());
					vperson.setOpenType(bank.getOpenType());
					vperson.setAreaId(bank.getAreaId());
					vperson.setAreaName(bank.getAreaName());
					vperson.setBankOutletsName(bank.getBankOutletsName());
				}
			/*	List<UserOrg> olist=userOrgDao.listByUserId(vperson.getCreaterId());
				StringBuffer sb=new StringBuffer("");
				for(UserOrg uo:olist){
					Organization o=uo.getOrganization();
					String path=o.getPath();
					 
					String s=path.replace(".", ",");
					sb.append(s);
				}
				if(null!=olist && olist.size()>0){
					sb.deleteCharAt(sb.length()-1);
				}
				List<Organization> orglist = organizationService.getShopList(null,sb.toString());
				if(null!=orglist && orglist.size()>0){
					Organization org=orglist.get(0);
					vperson.setOrgUserId(org.getOrgId());
					vperson.setOrgUserName(org.getOrgName());
				}*/
			}
			FileForm fileForm = fileFormDao.getFileByMark("cs_person_tx."+vperson.getId());
			if(fileForm.getFileid() != null){
				vperson.setPersonPhotoId(fileForm.getFileid());
				vperson.setPersonPhotoUrl(fileForm.getWebPath());
			}
			FileForm fileForm2 = fileFormDao.getFileByMark("cs_person_sfzz."+vperson.getId());
			if(fileForm2.getFileid() != null){
				vperson.setPersonSFZZId(fileForm2.getFileid());
				vperson.setPersonSFZZUrl(fileForm2.getWebPath());
			}
			FileForm fileForm3 = fileFormDao.getFileByMark("cs_person_sfzf."+vperson.getId());
			if(fileForm3.getFileid() != null){
				vperson.setPersonSFZFId(fileForm3.getFileid());
				vperson.setPersonSFZFUrl(fileForm3.getWebPath());
			}
			arrayList.add(vperson);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalProperty':")
		.append(total).append(",topics:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		JsonUtil.responseJsonString(buff.toString());
	};
	
	
	@Override
	public int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();
        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
        int age = yearNow - yearBirth;
        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                } else {
                }
            } else {
                age--;
            }
        }
        if(yearNow == yearBirth){
        	age = 1 ;
        }
        if(yearNow==(yearBirth+1)){
        	age=1;
        }
        if(yearNow==(yearBirth+2)){
        	age=2;
        } 
        return age;
    }
	@Override
	public Person queryPersonCardnumber(String cardNumber)throws Exception{
		String hql = "from Person AS p where p.cardnumber=? " ;
		List list = creditBaseDao.queryHql(hql, cardNumber) ;
		if(list == null ){
			return null;
		}else
		    return (Person)list.get(0);
	};
	@Override
	public VPersonDic queryPerson(int id) {
		String hql="from VPersonDic  p where p.id="+id;
		VPersonDic vPersonDic = new VPersonDic();
		try {
			List list = creditBaseDao.queryHql(hql);
			if(list != null){
				vPersonDic = (VPersonDic)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  vPersonDic;
	}
	@Override
	public List<Person> getAllList() {
		String hql = "from Person p where (p.isBlack=false or p.isBlack is null) order by  CONVERT(p.name , 'GBK') ASC";
		return getSession().createQuery(hql).list();
	}
	@Override
	public List<VPersonDic> ajaxQueryPersonForCombo(String query,int start ,int limit,String userIds) {
		if(query==null){
			query="" ;
		}else{
			query.replaceAll(" ", "") ;
		}
		String hql ="from VPersonDic as p where fn_check_repeat(p.belongedId,'"+userIds+"') = 1 and p.name like '%" + query + "%' ";
		if(null==userIds || ""==userIds){
			hql = "from VPersonDic as p where p.name like '%" + query + "%'" ;
		}
		List<VPersonDic> list =this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
		return list;
	}
	@Override
	public VPersonDic queryPerson(Integer id) {
		String hql="from VPersonDic as p where p.id=?";
		return (VPersonDic) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
	}
	@Override
	public Person getPersonById(Integer id) {
		String hql = "from Person sa where sa.id=?";
		Person person=(Person) this.getSession().createQuery(hql).setParameter(0, id).uniqueResult();
		return person;
	}
	@Override
	public List<PersonRelation> getPersonByPersonId(int personId) {
		String hql = "from PersonRelation AS d where d.personId="+personId;
		return this.getSession().createQuery(hql).list();
	}
	@Override
	public PersonRelation getByRelationShip(int personId, int ship) {
		String hql = "from PersonRelation d where d.personId="+personId+" and d.relationShip="+ship;
		List<PersonRelation> list=this.getSession().createQuery(hql).list();
		if(null!=list){
			return list.get(0);
		}else{
			return null;
		}
	}
	/**查询个人客户
	 * 孙贝贝
	 * 2014-08-01
	 */
	@Override
	public List<Person> personList(Integer start, Integer limit, HttpServletRequest request) {
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String cardtype=request.getParameter("cardtype");
		String cardnumber=request.getParameter("cardnumber");
		String[] str={"name","sex","cardtype","cardnumber"};
		String[] obj={name,sex,cardtype,cardnumber};
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" where (p.isBlack=false or p.isBlack is null) and ");
		int len = obj.length;
		List list1 = null;
		int total = 0;
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ; i ++){
				if(obj[i] == null){
					obj[i] = "";
				}
				//strBuffer.append("p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"'");
				strBuffer.append("(p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"' or(p."+str[j]+" is null and '' = '"+obj[i]+"'))");
				if(len != j+1){
					strBuffer.append(" and ");
				}
			break;
			}
		}
		String sql="select"+
		" p.id,"+
		" p.`name`,"+
		"m.loginname as p2pName,"+
		"m.truename as p2pTrueName,"+
		"p.cardtype,"+
		"p.cardnumber,"+
		"m.cardcode as p2pcardnumber,"+
		"p.cellphone,"+
		"m.telphone as p2pcellphone,"+
		"p.selfemail,"+
		"m.email as p2pemail,"+
		"d16.itemValue as cardtypevalue, "+
		"m.thirdPayFlagId  as thirdPayFlagId "+
		" from cs_person p left join bp_cust_relation b on p.id=b.offlineCusId and b.offlineCustType='p_loan' "+
		" left join bp_cust_member m on b.p2pCustId=m.id"
		+" left join dictionary d16 ON d16.dicId = p.cardtype"+strBuffer.toString();
		System.out.println("-->>>"+sql);
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.INTEGER).//主键id
			 addScalar("name",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p真实姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 addScalar("thirdPayFlagId",Hibernate.STRING).//p2p支付账号
			 setResultTransformer(Transformers.aliasToBean(Person.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else{
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.INTEGER).
			 addScalar("name",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 addScalar("thirdPayFlagId",Hibernate.STRING).//p2p支付账号
			 setResultTransformer(Transformers.aliasToBean(Person.class)).
			 list();
		}
		return list;
	}
	//单纯只保存个人客户信息
	@Override
	public Person saveSinglePerson(Person person) {
		// TODO Auto-generated method stub
	    try {
	    	Long companyId = ContextUtil.getLoginCompanyId();
			String currentUserId = ContextUtil.getCurrentUserId().toString();
		   if(person.getId()!=null){
				Person pPersistent = this.getById(person.getId());
				BeanUtil.copyNotNullProperties(pPersistent, person);
				this.merge(pPersistent);
				logger.info("更新个人客户成功，个人客户Id："+pPersistent.getId());
				return pPersistent;
			}else{
				 person.setCompanyId(companyId);
				 person.setCreater(ContextUtil.getCurrentUser().getFullname());
				 person.setBelongedId(currentUserId);
		    	 person.setCreaterId(Long.valueOf(currentUserId));
		    	 person.setCreatedate(new Date());
		    	 this.save(person);
		    	 logger.info("保存个人客户成功，个人客户Id："+person.getId());
		    	 return person;
			}
	    }catch (Exception e) {
			e.printStackTrace();
			logger.error("保存个人客户信息报错，报错时间："+new Date());
			logger.error("保存个人客户信息报错，报错原因："+e.getMessage());
			return null;
	    }
		
	}
	@Override
	public String savePersonInfo(Person person, Spouse spouse,WorkCompany workCompany) {
		String str="";
		try{
			if(null==person.getId() || person.getId()==0){
				this.save(person);
			}else{
				Person p=this.getById(person.getId());
				BeanUtil.copyNotNullProperties(p, person);
				this.merge(p);
			}
			if(null!=person.getMarry() && person.getMarry()==317){
				//保存配偶信息
				if(null!=spouse){
					spouse.setPersonId(person.getId());
					if(null==spouse.getSpouseId()){
						spouseDao.save(spouse);
					}else{
						Spouse orgSpouse=spouseDao.get(spouse.getSpouseId());
						BeanUtil.copyNotNullProperties(orgSpouse, spouse);
						spouseDao.merge(orgSpouse);
					}
					str=str+"spouseId:"+spouse.getSpouseId()+",";
				}
			}
			//保存公司信息
			if(null!=workCompany){
				workCompany.setPersonId(person.getId());
				if(null==workCompany.getId()){
					workCompanyDao.save(workCompany);
				}else{
					WorkCompany orgWorkCompany=workCompanyDao.get("id", workCompany.getId());
					BeanUtil.copyNotNullProperties(orgWorkCompany, workCompany);
					workCompanyDao.merge(orgWorkCompany);
				}
				str=str+"workCompanyId:"+workCompany.getId()+",";
			}
		
			str=str+"personId:"+person.getId();
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
	@Override
	public void perList(PageBean<Person> pageBean,Map<String,String> map) {
		HttpServletRequest request=pageBean.getRequest();
		/*--------查询总条数---------*/
		StringBuffer totalCounts = new StringBuffer ("select count(*) from (");
		StringBuffer hql=new StringBuffer("SELECT "
				+"p.id,"
				+"p.name,"
				+"p.sex,"
				+"d.itemValue as sexvalue,"
				+"p.cardtype,"
				+"d1.itemValue as cardtypevalue,"
				+"dir.itemValue as marryvalue,"
				+"p.cardnumber,"
				+"p.cellphone,"
				+"p.telphone,"
				+"p.selfemail,"
				+"p.marry,"
				+"p.postcode,"
				+"p.postaddress,"
				+"p.archivesBelonging,"
				+"p.createrId,"
				+"p.belongedId,"
				+"p.companyId,"
				+"p.shopId,"
				+"p.shopName,"
				+"p.age,"
				+"o.org_name as companyName, "
				+"m.isCheckCard as isCheckCard "
				+"from cs_person as p "
				+"left join  dictionary as d on p.sex=d.dicId "
				+"left join dictionary as d1 on d1.dicId=p.cardtype "
				+"left join bp_cust_relation b on p.id=b.offlineCusId and b.offlineCustType='p_loan' "
				+"left join bp_cust_member m on b.p2pCustId=m.id "
				+"left join organization as o on o.org_id=p.companyId " 
				+"left join dictionary as dir on p.marry=dir.dicId "
				+"where (p.isBlack=false or p.isBlack is null) ");
		String companyId= map.get("companyId");
		String userIds= map.get("userId");
		if(null!=userIds && !"".equals(userIds)){
			hql.append(" and fn_check_repeat(p.belongedId,'"+userIds+"') = 1");
		}
		if(null!=companyId && !"".equals(companyId)){
			hql.append(" and p.companyId in ("+companyId+")");
		}
		String name=request.getParameter("name");
		if(null!=name && !name.equals("")){
			hql.append(" and p.name  like '%"+name+"%'");
		}
		String sex=request.getParameter("sex");
		if(null!=sex && !sex.equals("")){
			hql.append(" and p.sex="+Integer.valueOf(sex));
		}
		String cardtype=request.getParameter("cardtype");
		if(null!=cardtype && !cardtype.equals("")){
			hql.append(" and p.cardtype="+Integer.valueOf(cardtype));
		}
		String cardnumber=request.getParameter("cardnumber");
		if(null!=cardnumber && !cardnumber.equals("")){
			hql.append(" and p.cardnumber like '%"+cardnumber+"%'");
		}
		String shopId=request.getParameter("shopId");
		if(null!=shopId && !shopId.equals("")){
			hql.append(" and p.shopId="+Long.valueOf(shopId));
		}
		hql.append(" order by p.id desc");
		totalCounts.append(hql).append(") as b");
		SQLQuery query=this.getSession().createSQLQuery(hql.toString())
				.addScalar("id",Hibernate.INTEGER)
				.addScalar("name",Hibernate.STRING)
				.addScalar("sex",Hibernate.INTEGER)
				.addScalar("sexvalue",Hibernate.STRING)
				.addScalar("marryvalue",Hibernate.STRING)
				.addScalar("cardtype",Hibernate.INTEGER)
				.addScalar("cardtypevalue",Hibernate.STRING)
				.addScalar("cardnumber",Hibernate.STRING)
				.addScalar("cellphone",Hibernate.STRING)
				.addScalar("telphone",Hibernate.STRING)
				.addScalar("selfemail",Hibernate.STRING)
				.addScalar("marry",Hibernate.INTEGER)
				.addScalar("postcode",Hibernate.STRING)
				.addScalar("postaddress",Hibernate.STRING)
				.addScalar("archivesBelonging",Hibernate.STRING)
				.addScalar("createrId",Hibernate.LONG)
				.addScalar("belongedId",Hibernate.STRING)
				.addScalar("companyId",Hibernate.LONG)
				.addScalar("shopId",Hibernate.LONG)
				.addScalar("shopName",Hibernate.STRING)
				.addScalar("age",Hibernate.INTEGER)
				.addScalar("isCheckCard",Hibernate.STRING)//是否实名认证
				.addScalar("companyName",Hibernate.STRING);
		if(null!=pageBean.getStart() && null!=pageBean.getLimit()){
			query.setResultTransformer(Transformers.aliasToBean(Person.class)).setFirstResult(pageBean.getStart()).setMaxResults(pageBean.getLimit());
		}else{
			query.setResultTransformer(Transformers.aliasToBean(Person.class));
		}
		pageBean.setResult(query.list());
		BigInteger total = (BigInteger) this.getHibernateTemplate().getSessionFactory().getCurrentSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pageBean.setTotalCounts(total.intValue());
	}
	@Override
	public Long personCount(HttpServletRequest request, String[] userIds) {
		StringBuffer hql=new StringBuffer("SELECT "
				+"count(p.id) "
				+"from cs_person AS p "
				+" where (p.isBlack=false or p.isBlack is null) ");
		String companyId=request.getParameter("companyId");
		String str=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !companyId.equals("")){
			str=companyId;
		}
		if(null!=str && !str.equals("")){
			hql.append(" and p.companyId in ("+str+")");
		}
		String userIdsStr = "";
		if (userIds != null) {
			StringBuffer sb1 = new StringBuffer();
			for(String userid : userIds) {
				sb1.append(userid);
				sb1.append(",");
			}
			userIdsStr = sb1.toString().substring(0,sb1.length()-1);
		}
	
		if(userIdsStr != ""){
			hql.append(" and fn_check_repeat(p.belongedId,'"+userIdsStr+"') = 1 ");
		}
		String name=request.getParameter("name");
		if(null!=name && !name.equals("")){
			hql.append(" and p.name  like '%"+name+"%'");
		}
		String sex=request.getParameter("sex");
		if(null!=sex && !sex.equals("")){
			hql.append(" and p.sex="+Integer.valueOf(sex));
		}
		String cardtype=request.getParameter("cardtype");
		if(null!=cardtype && !cardtype.equals("")){
			hql.append(" and p.cardtype="+Integer.valueOf(cardtype));
		}
		String cardnumber=request.getParameter("cardnumber");
		if(null!=cardnumber && !cardnumber.equals("")){
			hql.append(" and p.cardnumber like '%"+cardnumber+"%'");
		}
		String shopId=request.getParameter("shopId");
		if(null!=shopId && !shopId.equals("")){
			hql.append(" and p.shopId="+Long.valueOf(shopId));
		}
		Long count=0l;
		List list=this.getSession().createSQLQuery(hql.toString()).list();
		if(null!=list && list.size()>0){
			if(null!=list.get(0)){
				BigInteger c=(BigInteger) list.get(0);
				count=c.longValue();
			}
		}
		return count;
	}
	@Override
	public List<Person> getAllAccountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"cp.id AS id, "+
						"cp.`name` AS `name` , "+
						"cp.selfemail AS selfemail, "+
						"cp.cardnumber AS cardnumber, "+
						"cp.cellphone AS cellphone, "+
						"o.id AS accountId, "+
						"o.accountName AS accountName, "+
						"o.accountNumber AS accountNumber, "+
						"o.investmentPersonId AS investmentPersonId, "+
						"o.investPersionType AS investPersionType, "+
						"o.totalMoney AS totalMoney "+
					"FROM "+
						"cs_person AS cp "+
					"LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'p_loan' AND p.offlineCusId = cp.id) "+
					"LEFT JOIN ob_system_account AS o ON ( p.p2pCustId = o.investmentPersonId AND o.investPersionType = 0) "+
					"where 1=1 ";
		
		if(!map.isEmpty()){
			Object accountNumber=map.get("accountNumber");
			if(accountNumber!=null&&!"".equals(accountNumber.toString())){//查询出个人合作机构的各种类型
				sql=sql +" and o.accountNumber like '%"+accountNumber.toString()+"%'";
			}
			
			Object name=map.get("name");
			if(name!=null&&!"".equals(name.toString())){//客户姓名
				sql=sql +" and cp.name like '%"+name.toString()+"%'";
			}
			
			Object cardNumber=map.get("cardNumber");
			if(cardNumber!=null&&!"".equals(cardNumber.toString())){//客户证件号码
				sql=sql +" and cp.cardnumber like '%"+cardNumber.toString()+"%'";
			}
			Object userIdsStr=map.get("userIdsStr");
			if(userIdsStr!=null&&!"".equals(userIdsStr.toString())){//拥有的客户
			  sql=sql+(" and fn_check_repeat(cp.belongedId,'"+userIdsStr+"') = 1");
			}
			
		}
	//	System.out.println(sql);
		List<Person>  listCount=this.getSession().createSQLQuery(sql).
												addScalar("id",Hibernate.INTEGER).                                                                         
												addScalar("name",Hibernate.STRING).
												addScalar("selfemail",Hibernate.STRING).
												addScalar("cardnumber",Hibernate.STRING).
												addScalar("cellphone",Hibernate.STRING).
												addScalar("accountId",Hibernate.LONG).
												addScalar("accountName",Hibernate.STRING).
												addScalar("accountNumber",Hibernate.STRING).
												addScalar("investmentPersonId",Hibernate.LONG).
												addScalar("investPersionType",Hibernate.SHORT).
												addScalar("totalMoney", Hibernate.BIG_DECIMAL). 
												setResultTransformer(Transformers.aliasToBean(Person.class)).
												list();
		
		if(pb!=null){
			pb.setTotalItems(listCount.size());
			if(pb.getStart()!=null&&pb.getPageSize()!=null){
				List<Person>  list=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.INTEGER).                                                                         
				addScalar("name",Hibernate.STRING).
				addScalar("selfemail",Hibernate.STRING).
				addScalar("cardnumber",Hibernate.STRING).
				addScalar("cellphone",Hibernate.STRING).
				addScalar("accountId",Hibernate.LONG).
				addScalar("accountName",Hibernate.STRING).
				addScalar("accountNumber",Hibernate.STRING).
				addScalar("investmentPersonId",Hibernate.LONG).
				addScalar("investPersionType",Hibernate.SHORT).
				addScalar("totalMoney", Hibernate.BIG_DECIMAL).
				setResultTransformer(Transformers.aliasToBean(Person.class)).
			    setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).
				list();
				return list;
			}else{
				return listCount;
			}
		}else{
			return listCount;
		}
	}
	/**
	 * 
	 * @param start
	 * @param limit
	 * @param request
	 * @param userIdsStr
	 * @return加权限的个人客户查询
	 */
	@Override
	public List<Person> personList(Integer start, Integer limit,
			HttpServletRequest request, String userIdsStr) {
		// TODO Auto-generated method stub
		String name=request.getParameter("name");
		String sex=request.getParameter("sex");
		String cardtype=request.getParameter("cardtype");
		String cardnumber=request.getParameter("cardnumber");
		String[] str={"name","sex","cardtype","cardnumber"};
		String[] obj={name,sex,cardtype,cardnumber};
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append(" where (p.isBlack=false or p.isBlack is null) and ");
		int len = obj.length;
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ;){
				if(obj[i] == null){
					obj[i] = "";
				}
				strBuffer.append("(p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"' or(p."+str[j]+" is null and '' = '"+obj[i]+"'))");
				if(len != j+1){
					strBuffer.append(" and ");
				}
			break;
			}
		}
		String sql="select"+
		" p.id,"+
		" p.`name`,"+
		"m.loginname as p2pName,"+
		"m.truename as p2pTrueName,"+
		"p.cardtype,"+
		"p.cardnumber,"+
		"m.cardcode as p2pcardnumber,"+
		"p.cellphone,"+
		"m.telphone as p2pcellphone,"+
		"p.selfemail,"+
		"m.email as p2pemail,"+
		"d16.itemValue as cardtypevalue, "+
		"m.thirdPayFlagId  as thirdPayFlagId "+
		" from cs_person p left join bp_cust_relation b on p.id=b.offlineCusId and b.offlineCustType='p_loan' "+
		" left join bp_cust_member m on b.p2pCustId=m.id"
		+" left join dictionary d16 ON d16.dicId = p.cardtype"+strBuffer.toString();
		if(null!=userIdsStr && !userIdsStr.equals("")){
			sql=sql+(" and fn_check_repeat(p.belongedId,'"+userIdsStr+"') = 1");
		}
	//	System.out.println("-->>>"+sql);
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.INTEGER).//主键id
			 addScalar("name",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p真实姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 addScalar("thirdPayFlagId",Hibernate.STRING).//p2p支付账号
			 setResultTransformer(Transformers.aliasToBean(Person.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else{
			list=this.getSession().createSQLQuery(sql).
			 addScalar("id",Hibernate.INTEGER).
			 addScalar("name",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 addScalar("thirdPayFlagId",Hibernate.STRING).//p2p支付账号
			 setResultTransformer(Transformers.aliasToBean(Person.class)).
			 list();
		}
		return list;
	}
	
	@Override
	public List<Person> getPersonByName(String  name,PageBean<Enterprise> pb) {
		List<Object> params=new ArrayList<Object>();
		String hql = "select * from cs_person";
		String totalCounts = "select count(id) from cs_person";
		BigInteger total = (BigInteger) this.getSession().createSQLQuery(totalCounts.toString()).uniqueResult();
		pb.setTotalCounts(total.intValue());
		return this.getSession().createSQLQuery(hql)
				.addScalar("id",Hibernate.INTEGER)
				.setResultTransformer(Transformers.aliasToBean(Person.class))
				.setFirstResult(pb.getStart())
				.setMaxResults(pb.getLimit())
				.list();
	}

	@Override
	public BigDecimal getAvailableMoney(String name) {
		String sql = "SELECT\n" +
				"\t( 1000000 -IFNULL(s.ccc,0) - IFNULL(a.ddd,0) ) AS `availableMoney` \n" +
				"FROM\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\t\tSUM( p.bidMoney ) AS `ccc` \n" +
				"\tFROM\n" +
				"\t\tpl_bid_plan p,\n" +
				"\t\t( SELECT loginname FROM bp_cust_member WHERE legalPerson = '"+name+"' ) AS m \n" +
				"\tWHERE\n" +
				"\t\tp.receiverP2PAccountNumber = m.loginname \n" +
				"\t\tAND p.state IN ( 0, 1, 2, 5, 6) \n" +
				"\t) s,\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\t\tSUM( intent.`notMoney` ) AS `ddd` \n" +
				"\tFROM\n" +
				"\t\tbp_fund_intent intent \n" +
				"\tWHERE\n" +
				"\t\tintent.`fundType` = 'principalRepayment' \n" +
				"\t\tAND intent.`bidPlanId` IN (\n" +
				"\t\tSELECT\n" +
				"\t\t\tp.bidId \n" +
				"\t\tFROM\n" +
				"\t\t\tpl_bid_plan p,\n" +
				"\t\t\t( SELECT loginname FROM bp_cust_member WHERE legalPerson = '"+name+"' ) AS m \n" +
				"\t\tWHERE\n" +
				"\t\t\tp.receiverP2PAccountNumber = m.loginname \n" +
				"\t\t\tAND p.state != 6 \n" +
				"\t\t) \n" +
				"\t) AS a";

		Person  p = (Person) this.getSession().createSQLQuery(sql)
				.addScalar("availableMoney", Hibernate.BIG_DECIMAL)
				.setResultTransformer(Transformers.aliasToBean(Person.class))
				.list().get(0);
		return  p.getAvailableMoney();
	}

	@Override
	public List<Enterprise> queryEnterpriseMoneyByName(String name) {
		String hql = "SELECT\n" +
				"\t`id`,\n" +
				"\t`enterprisename`,\n" +
				"\t`organizecode`,\n" +
				"\tlinkman,\n" +
				"\t`tenderingMoney`,\n" +
				"\t`repayingMoney`,\n" +
				"\t`totalMoney`,\n" +
				"\t( 1000000- `repayingMoney` - `tenderingMoney` ) AS `surplusMoney` \n" +
				"FROM\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\te.`id`,\n" +
				"\te.`enterprisename`,\n" +
				"\te.`organizecode`,\n" +
				"\te.linkman,\n" +
				"\tIFNULL(\n" +
				"\t\t(\n" +
				"\t\tSELECT\n" +
				"\t\t\tSUM( `bidMoney` ) \n" +
				"\t\tFROM\n" +
				"\t\t\tpl_bid_plan plan \n" +
				"\t\tWHERE\n" +
				"\t\t\tplan.`receiverP2PAccountNumber` = member.`loginname` \n" +
				"\t\t\tAND plan.`state` IN ( 0, 1, 2, 6 ) \n" +
				"\t\t),\n" +
				"\t\t0 \n" +
				"\t) AS `tenderingMoney`,\n" +
				"\tIFNULL(\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\t\tSUM( intent.`notMoney` ) \n" +
				"\tFROM\n" +
				"\t\tbp_fund_intent intent \n" +
				"\tWHERE\n" +
				"\t\tintent.`fundType` = 'principalRepayment' \n" +
				"\t\tAND intent.`bidPlanId` IN ( SELECT p.bidId FROM pl_bid_plan p WHERE p.`receiverP2PAccountNumber` = member.`loginname` AND p.state != 6 ) \n" +
				"\t),\n" +
				"\t0 \n" +
				") AS `repayingMoney`,\n" +
				"IFNULL(\n" +
				"\t(\n" +
				"\tSELECT\n" +
				"\t\tSUM( `bidMoney` ) \n" +
				"\tFROM\n" +
				"\t\tpl_bid_plan plan \n" +
				"\tWHERE\n" +
				"\t\tplan.`receiverP2PAccountNumber` = member.`loginname` \n" +
				"\t\tAND plan.`state` IN ( 0, 1, 2, 6, 7, 10 ) \n" +
				"\t),\n" +
				"\t0 \n" +
				") AS `totalMoney` \n" +
				"FROM\n" +
				"\tcs_enterprise e\n" +
				"\tLEFT JOIN bp_cust_member member ON member.`cardcode` = e.`organizecode` \n" +
				"WHERE\n" +
				"\tmember.isCheckCard = 1 \n" +
				") AS en \n" +
				"WHERE\n" +
				"\t1 = 1 and linkman = '"+name+"'";
		System.out.println(hql);
		List<Enterprise> list = this.getSession().createSQLQuery(hql).addScalar("id", Hibernate.INTEGER).
				addScalar("enterprisename", Hibernate.STRING).
				addScalar("organizecode", Hibernate.STRING).
				addScalar("linkman", Hibernate.STRING).
				addScalar("tenderingMoney", Hibernate.BIG_DECIMAL).//-- 招标中金额
				addScalar("repayingMoney", Hibernate.BIG_DECIMAL).//-- 还款中金额
				addScalar("surplusMoney", Hibernate.BIG_DECIMAL).//-- 可用额度
				addScalar("totalMoney", Hibernate.BIG_DECIMAL).//totalMoney    -- 总借款金额
				setResultTransformer(Transformers.aliasToBean(Enterprise.class)).
				list();

		return list;
	}
}