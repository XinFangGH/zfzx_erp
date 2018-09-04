package com.zhiwei.credit.action.creditFlow.customer.bankRelationPerson;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.creditUtils.DateUtil;
import com.zhiwei.credit.core.creditUtils.ExcelHelper;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.CustomerBankRelationPerson;
import com.zhiwei.credit.model.creditFlow.customer.bankRelationPerson.VBankBankcontactperson;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.customer.bankRelationPerson.CustomerBankRelationPersonService;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.system.AppUserService;
import com.zhiwei.credit.service.system.OrganizationService;

/**
 * 
 * 
 * @author tian 银行联系人的信息操作
 * 
 */
@SuppressWarnings("all")
public class CustomerBankRelationPersonAction extends BaseAction {
	@Resource
	private CustomerBankRelationPersonService customerBankRelationPersonService;
	@Resource
	private AreaDicService areaDicService ;
	private CustomerBankRelationPerson bankRelationPerson;
	private VBankBankcontactperson vBankBankcontactperson;
	@Resource
	private CreditBaseDao creditBaseDao;
	@Resource
	private AppUserService appUserService;
	@Resource
	private OrganizationService organizationService;

	private int id;
	private int bankId;
	private String name = "";
	private String duty = "";
	private String address = "";
	private String bankname = "";
	
	private String text ;
	private String bankIdValue ;
	
	private String sort;
	private String dir;
	
	private Boolean isAll;
	
	
	public Boolean getIsAll() {
		return isAll;
	}

	public void setIsAll(Boolean isAll) {
		this.isAll = isAll;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void queryBankRelationPerson() {
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String companyId= map.get("companyId");
		String userIds= map.get("userId");
		String[] str = {"name","duty","bankname"};
		Object[] obj = {name,duty,bankname};
		try {
			customerBankRelationPersonService.QueryList(companyId,start, limit, userIds,"from VBankBankcontactperson AS b", str, obj,sort,dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 联系人弹出窗口
	public void queryBankWin() {
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String userIds= map.get("userId");
		try {
			List list = new ArrayList();
			int persontotal = customerBankRelationPersonService
					.queryPersonWindow(bankId);
			list = customerBankRelationPersonService.queryPersonWindow(bankId,name,duty, start,
					limit,userIds);
			JsonUtil.jsonFromList(list, persontotal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 添加银行联系人的信息
	public void addBankRelationPerson() {
		AppUser currentUser = ContextUtil.getCurrentUser();//个人信息
		Long currentUserId = ContextUtil.getCurrentUserId();
		try {
			Long companyId = ContextUtil.getLoginCompanyId();
        	if(null ==bankRelationPerson.getCompanyId()){
        		bankRelationPerson.setCompanyId(companyId);
        	}
			bankRelationPerson.setCreater(currentUser.getFullname());
			bankRelationPerson.setCreaterId(currentUserId);
			bankRelationPerson.setBelongedId(currentUserId.toString());
			bankRelationPerson.setCreatedate(DateUtil.getNowDateTimeTs());
			customerBankRelationPersonService.addPerson(bankRelationPerson);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 删除银行联系人的信息
	public void deleteBankRelationPerson() {
		try {
			String listId=this.getRequest().getParameter("listId");
			String[] ids=listId.split(",");
			for(String id:ids){
				bankRelationPerson = customerBankRelationPersonService.getById(Integer.valueOf(id));
				customerBankRelationPersonService.remove(bankRelationPerson);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	// 查看银行联系人的详细信息
	public void seeBankRelationPerson() {
		try {
			vBankBankcontactperson = (VBankBankcontactperson) customerBankRelationPersonService
					.queryPersonName(id);
			if(vBankBankcontactperson.getBelongedId()!= null && !"".equals(vBankBankcontactperson.getBelongedId())){
				String belongedName = "";
				String []str = vBankBankcontactperson.getBelongedId().split(",");
				Set<AppUser> userSet = appUserService.getAppUserByStr(str);
				int i = 0;
				for(AppUser s:userSet){
					belongedName += s.getFamilyName();
					i++;
					if(i != userSet.size()){
						belongedName= belongedName+",";
					}
				}
				vBankBankcontactperson.setBelongedName(belongedName);
			}
			JsonUtil.jsonFromObject(vBankBankcontactperson, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void queryBankRelationPersonWin() {
		Object ids=this.getRequest().getSession().getAttribute("userIds");
		Map<String,String> map=GroupUtil.separateFactor(getRequest(),ids);
		String userIds= map.get("userId");
		try {
			List list = new ArrayList();
			int persontotal = customerBankRelationPersonService
					.queryPersonWindow(Integer.parseInt(bankIdValue));
			list = customerBankRelationPersonService.queryPersonWindow(Integer.parseInt(bankIdValue),name,duty, start,
					limit,userIds);
			JsonUtil.jsonFromList(list, persontotal);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void isExistBankRelationPerson(){
		try {
			customerBankRelationPersonService.isExist(bankId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void bankAndRelationPersonTree(){
		List list = null ;
		HttpServletRequest req = getRequest();
		String lable = req.getParameter("lable");
		if(!"no".equals(lable) && lable != "no"){
			Integer parentId = Integer.parseInt(req.getParameter("node"));
			if(parentId == 0){
				list = areaDicService.listByLabel(lable);
			}else{
				list = areaDicService.listByParentId(parentId) ;
			}
		}else{
			try {
				Integer parentId = Integer.parseInt(req.getParameter("node"));
				if(text == "" || "".equals(text) && parentId == 0){
					list = areaDicService.listByLabel("bank");
				}else{
					if(parentId == 0){
						//list = bankRelationPersonService.queryBankTree(text) ;
					}else{
						list = areaDicService.listByParentId(parentId) ;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String json = JSONArray.fromObject(list).toString(); 
		JsonUtil.responseJsonString(json);
	}
	
	public void update(){
		try{
			customerBankRelationPersonService.addPerson(bankRelationPerson);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void findByBank(){
		List list = customerBankRelationPersonService.findBank(bankId);
		JsonUtil.jsonFromList(list);
	}
	
	public void outputExcel(){
		List list = null;
		String [] tableHeader = {"序号","联系人姓名","性别","婚姻状况","办公电话","手机号码","电子邮件","银行名称","支行名称","职务"};
		String hql = "from VBankBankcontactperson bp order by  CONVERT(bp.name , 'GBK') ASC";
		try {
			list  = creditBaseDao.queryHql(hql);
			ExcelHelper.export(list,tableHeader,"银行客户列表");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public CustomerBankRelationPerson getBankRelationPerson() {
		return bankRelationPerson;
	}

	public void setBankRelationPerson(CustomerBankRelationPerson bankRelationPerson) {
		this.bankRelationPerson = bankRelationPerson;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDuty() {
		return duty;
	}

	public void setDuty(String duty) {
		this.duty = duty;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public VBankBankcontactperson getvBankBankcontactperson() {
		return vBankBankcontactperson;
	}

	public void setvBankBankcontactperson(
			VBankBankcontactperson vBankBankcontactperson) {
		this.vBankBankcontactperson = vBankBankcontactperson;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}
	

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getBankIdValue() {
		return bankIdValue;
	}

	public void setBankIdValue(String bankIdValue) {
		this.bankIdValue = bankIdValue;
	}

}
