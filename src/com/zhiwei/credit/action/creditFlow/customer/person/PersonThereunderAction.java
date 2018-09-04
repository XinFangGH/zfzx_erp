package com.zhiwei.credit.action.creditFlow.customer.person;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.Enterprise;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.creditFlow.customer.person.PersonThereunder;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.customer.enterprise.EnterpriseService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonThereunderService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.system.DictionaryService;

/**
 * 
 * @author 恒信骏生 feng 人员子表：人员旗下公司的操作
 */
@SuppressWarnings("all")
public class PersonThereunderAction extends BaseAction{
	@Resource
	private PersonThereunderService personThereunderService;
	@Resource
	private PersonService personService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	@Resource
	private EnterpriseService enterpriseService;
	@Resource
	private DictionaryService dictionaryService;
	private PersonThereunder personThereunder;
	private String perosnName;

	private int id;
	private Integer pid;

	// 查询人员旗下公司的信息list,通过人员的pid去select
	public String query() {
		try {
			String projectId=this.getRequest().getParameter("projectId");
			String operationType=this.getRequest().getParameter("operationType");
			if(null==pid && (null!=projectId && !"".equals(projectId)) && (null!=operationType && !"".equals(operationType))){
				SlSmallloanProject sl=slSmallloanProjectService.getListByoperationType(Long.valueOf(projectId), operationType);
				if(null!=sl.getOppositeID()){
					pid=sl.getOppositeID().intValue();
					List<PersonThereunder> list = personThereunderService.getListByPersonId(pid, null);
					for(PersonThereunder p:list){
						if(null!=p.getCompanyname()){
							Enterprise e=enterpriseService.getById(p.getCompanyname());
							if(null!=e){
								p.setShortname(e.getEnterprisename());
							}
						}
						if(null!=p.getLnpid()){
							Person person=personService.getById(p.getLnpid());
							if(null!=person){
								p.setName(person.getName());
							}
						}
						if(null!=p.getRelate()){
							Dictionary dic=dictionaryService.get(p.getRelate().longValue());
							if(null!=dic){
								p.setRelateValue(dic.getItemValue());
							}
						}
					}
					JsonUtil.jsonFromList(list);
				}
			}else{
				int persontotal = 0;
				PagingBean pb=new PagingBean(start,limit);
				List<PersonThereunder> list = personThereunderService.getListByPersonId(pid, pb);
				for(PersonThereunder p:list){
					if(null!=p.getCompanyname()){
						Enterprise e=enterpriseService.getById(p.getCompanyname());
						if(null!=e){
							p.setShortname(e.getEnterprisename());
						}
					}
					if(null!=p.getLnpid()){
						Person person=personService.getById(p.getLnpid());
						if(null!=person){
							p.setName(person.getName());
						}
					}
					if(null!=p.getRelate()){
						Dictionary dic=dictionaryService.get(p.getRelate().longValue());
						if(null!=dic){
							p.setRelateValue(dic.getItemValue());
						}
					}
				}
				List<PersonThereunder> list1 = personThereunderService.getListByPersonId(pid, null);
				if(null!=list1 && list1.size()>0){
					persontotal=list1.size();
				}
				JsonUtil.jsonFromList(list, persontotal);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
    public void getPersonId(){
    	String projectId=this.getRequest().getParameter("projectId");
		String operationType=this.getRequest().getParameter("operationType");
		if(null!=projectId && !"".equals(projectId) && null!=operationType && !"".equals(operationType)){
			SlSmallloanProject sl=slSmallloanProjectService.getListByoperationType(Long.valueOf(projectId), operationType);
			JsonUtil.jsonFromObject(sl, true);
		}
    }
	// 查询人员旗下公司的信息
	public String see() {
		try {
			personThereunder = (PersonThereunder) personThereunderService.getById(id);
			if(null!=personThereunder.getCompanyname()){
				Enterprise e=enterpriseService.getById(personThereunder.getCompanyname());
				if(null!=e){
					personThereunder.setShortname(e.getEnterprisename());
				}
			}
			if(null!=personThereunder.getLnpid()){
				Person person=personService.getById(personThereunder.getLnpid());
				if(null!=person){
					personThereunder.setName(person.getName());
				}
			}
			JsonUtil.jsonFromObject(personThereunder, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 添加人员旗下公司的信息
	public String add() {
		try {
			personThereunderService.addPerson(personThereunder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 删除人员旗下公司的信息
	public String delete() {
		try {
			String[]ids=getRequest().getParameterValues("ids");
			if(null!=ids){
				for(String id:ids){
					personThereunder = (PersonThereunder) personThereunderService.getById(Integer.parseInt(id));
					personThereunderService.remove(personThereunder);
				}
			}
			jsonString="{success:true}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}


	public PersonThereunder getPersonThereunder() {
		return personThereunder;
	}

	public void setPersonThereunder(PersonThereunder personThereunder) {
		this.personThereunder = personThereunder;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPerosnName() {
		return perosnName;
	}

	public void setPerosnName(String perosnName) {
		this.perosnName = perosnName;
	}


}
