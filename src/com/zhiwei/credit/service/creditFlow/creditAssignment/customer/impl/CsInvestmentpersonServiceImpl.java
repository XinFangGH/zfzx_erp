package com.zhiwei.credit.service.creditFlow.creditAssignment.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zhiwei.core.Constants;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.filter.MySessionFilter;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.bank.ObSystemAccountDao;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.customer.CsInvestmentpersonDao;
import com.zhiwei.credit.dao.creditFlow.customer.common.EnterpriseBankDao;
import com.zhiwei.credit.dao.system.OrganizationDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemAccount;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemAccountService;
import com.zhiwei.credit.service.creditFlow.creditAssignment.customer.CsInvestmentpersonService;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
/**
 * 
 * @author 
 *
 */
public class CsInvestmentpersonServiceImpl extends BaseServiceImpl<CsInvestmentperson> implements CsInvestmentpersonService{
	@SuppressWarnings("unused")
	private CsInvestmentpersonDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private EnterpriseBankDao enterpriseBankDao;
	@Resource
	private OrganizationDao organizationDao;
	@Resource
	private ObSystemAccountDao obSystemAccountDao;
	@Resource
	private ObSystemAccountService obSystemAccountService;
	private final Log log = LogFactory.getLog(getClass());
	public CsInvestmentpersonServiceImpl(CsInvestmentpersonDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public void ajaxQueryinvestmentPerson(Map<String,String> map, Integer start,
			Integer limit, String hql, String[] str,
			Object[] obj, String sort, String dir) {
		try{
			List<CsInvestmentperson> list =dao.ajaxQueryinvestmentPerson(map,start,limit,hql,str,obj,dir);
			List<CsInvestmentperson> listCount=dao.ajaxQueryinvestmentPerson(map,null,null,hql,str,obj,dir);
			JsonUtil.jsonFromList(list, null!=listCount?listCount.size():0);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void addInvestment(CsInvestmentperson csInvestmentperson) {
		try{
			String str="";
			if(csInvestmentperson.getInvestId() != null){
				creditBaseDao.updateDatas(csInvestmentperson);
				JsonUtil.responseJsonString("{success:true,exsit:true,msg :'添加成功',newId:'"+csInvestmentperson.getInvestId()+"'}");
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
				
				String cardnumber = csInvestmentperson.getCardnumber() ;
				if(null!=organization){
					csInvestmentperson.setCompanyId(organization.getOrgId());}
				CsInvestmentperson per = queryPersonCardnumber(cardnumber);
				if(null != per){
					JsonUtil.responseJsonString("{success:true,exsit:false,msg :'该人员已存在，是否关闭'}");
					return ;
				}
				if(creditBaseDao.saveDatas(csInvestmentperson)){
					per = queryPersonCardnumber(cardnumber);
					Long pId =0l;
					if(null!=per && null!= per.getInvestId()){
						pId=per.getInvestId();
					};
					/* ===========创建投资人系统虚拟银行账户=========*/
					String[] savemsg=obSystemAccountService.saveAccount(
							csInvestmentperson.getCompanyId().toString(),
							csInvestmentperson.getInvestName(),
							csInvestmentperson.getInvestId().toString(),
							csInvestmentperson.getCardnumber(),
							"0",
							ObSystemAccount.type1.toString());
					if(savemsg[0].equals(Constants.CODE_FAILED)){
						log.info("系统账户添加失败");
						JsonUtil.responseJsonString("{success:true,exsit:true,msg:'"+savemsg[1]+"'}");
					}else{
						log.info("系统账户添加成功");
						JsonUtil.responseJsonString("{success:true,exsit:true,msg:'"+savemsg[1]+"'}");
					}
				}else{
					log.error("线下投资客户添加失败");
				
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	

	@Override
	public VInvestmentPerson queryInvestmentPerson(Long investId) {
		String hql="from VInvestmentPerson  p where p.investId="+investId;
		VInvestmentPerson vInvestmentPerson = new VInvestmentPerson();
		try {
			List list = creditBaseDao.queryHql(hql);
			if(list != null){
				vInvestmentPerson = (VInvestmentPerson)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return  vInvestmentPerson;
	}

	@Override
	public void updatePerson(CsInvestmentperson csInvestmentperson) {
		String str="";
		try{
			String cardnumber = csInvestmentperson.getCardnumber() ;
			CsInvestmentperson pers = (CsInvestmentperson)creditBaseDao.getById(CsInvestmentperson.class, csInvestmentperson.getInvestId());
			if(cardnumber != pers.getCardnumber() && !cardnumber.equals(pers.getCardnumber())){
				if(null != queryPersonCardnumber(cardnumber)){
					JsonUtil.responseJsonString("{success:true,exsit:false,msg :'该人员已存在,是否关闭'}");
					return ;
				}
			}
			creditBaseDao.clearSession();//清session
			if(creditBaseDao.updateDatas(csInvestmentperson)){
				log.info("更新成功");
				str=str.replace("/", "  ");
		   		str=str.replace("\r\n", "   ");
		   		JsonUtil.responseJsonString("{success:true,exsit:true,msg :'个人客户信息更新成功!'}");
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			log.error("更新失败");
			JsonUtil.responseJsonString("{success:true,exsit:false,msg :'个人客户信息更新失败!'}");
		}
	}

	@Override
	public List<VInvestmentPerson> getList(HttpServletRequest request,String companyId,String userIds) {
		return dao.getList(request,companyId,userIds);
	}
	/**查询投资人和银行卡详细信息*/
	@Override
	public List<CsInvestmentperson> getPersonAndBank() {
		
		return dao.getPersonAndBank();
	}

	@Override
	public List<CsInvestmentperson> getAllList(HttpServletRequest request,
			Integer start, Integer limit) {
		return dao.getAllList(request,start,limit);
	}
	/**
	 * 2014-08-01
	 * 
	 * 
	 */
	@Override
	public List<CsInvestmentperson> listInvest(Integer start, Integer limit,HttpServletRequest request) {
		return dao.listInvest(start, limit,request);
	}

	@Override
	public CsInvestmentperson queryPersonCardnumber(String cardNum) {
		String hql = "from CsInvestmentperson AS p where p.cardnumber=? " ;
		List list=null;
		try {
			list = creditBaseDao.queryHql(hql, cardNum);
			if(list == null ){
				return null;
			}else{
			    return (CsInvestmentperson)list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<CsInvestmentperson> getDown(Map<String, String> map,
			Integer start, Integer limit) {
		return dao.getDown(map,start,limit);
	}
}