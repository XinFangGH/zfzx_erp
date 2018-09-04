package com.zhiwei.credit.action.creditFlow.ourmain;
/*
 * 北京互融时代软件有限公司————协同办公管理系统   
*/
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.ourmain.SlPersonMainService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class SlPersonMainAction extends BaseAction{
	@Resource
	private SlPersonMainService slPersonMainService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private OrganizationService organizationService;
	private SlPersonMain slPersonMain;
	private String cardNum;
	private Long id;
	private String name;
	
	private String isPledge;
	
	private String query ;


	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SlPersonMain getSlPersonMain() {
		return slPersonMain;
	}

	public void setSlPersonMain(SlPersonMain slPersonMain) {
		this.slPersonMain = slPersonMain;
	}
	

	public String getIsPledge() {
		return isPledge;
	}

	public void setIsPledge(String isPledge) {
		this.isPledge = isPledge;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String cardnum = filter.getRequest().getParameter("Q_cardnum_S_LK");
		String name = filter.getRequest().getParameter("Q_name_S_LK");
		
		PagingBean pb=new PagingBean(start, limit);
		
		/*//判断是否是我方个人主体参照的查询条件。
		if(name==null||"".equals(name)||"null".equals(name)){
			personName = filter.getRequest().getParameter("Q_name_S_LK");
		}else{
			personName = name;
		}*/
		String companyId=null;
		  if(null!=this.getRequest().getParameter("companyId"))
		  {
		   companyId=(String)this.getRequest().getParameter("companyId");
		  } 
		List<SlPersonMain> list= slPersonMainService.findPersonListByIsPledge(name, cardnum, pb,companyId);
		//List<SlPersonMain> list= slPersonMainService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:[");
		for(SlPersonMain spm:list){
			buff.append("{\"personMainId\":");
			buff.append(spm.getPersonMainId());
			buff.append(",\"name\":'");
			buff.append(spm.getName());
			buff.append("',\"sex\":");
			buff.append(spm.getSex());
			buff.append(",\"cardtype\":");
			buff.append(spm.getCardtype());
			buff.append(",\"cardtypevalue\":'");
			if(spm.getCardtype()!=null){
				Dictionary dic=dictionaryService.get(spm.getCardtype().longValue());
				buff.append(dic.getItemValue());
			}else{
				buff.append("");
			}
			buff.append("',\"cardnum\":'");
			buff.append(spm.getCardnum());
			buff.append("',\"linktel\":'");
			buff.append(spm.getLinktel());
			buff.append("',\"tel\":'");
			buff.append(spm.getTel());
			buff.append("',\"address\":'");
			buff.append(spm.getAddress());
			buff.append("',\"home\":'");
			buff.append(spm.getHome());
			buff.append("',\"postalCode\":'");
			buff.append(spm.getPostalCode());
			buff.append("',\"tax\":'");
			buff.append(spm.getTax());
			buff.append("',\"companyId\":");
			buff.append(spm.getCompanyId());
			buff.append(",\"companyName\":'");
			if(null!=spm.getCompanyId()){
				Organization organization=organizationService.get(spm.getCompanyId());
				if(null!=organization){
					buff.append(organization.getOrgName());
				}
			
			}
			buff.append("'},");
		}
		if(null!=list && list.size()>0){
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	
	/**
	 * 获取我方个人主体-参照
	 * @param name
	 * @param PagingBean
	 * @return
	 */
	public String listReference(){
		
		int records = 0;
		
		PagingBean pb=new PagingBean(start, limit);
		
		List<SlPersonMain> list= slPersonMainService.findPersonListReference(name, pb);
		
		if(list!=null&&list.size()!=0){
			records = list.size();
			for(int i=0;i<list.size();i++){
				SlPersonMain sl = list.get(i);
				if(sl.getCardtype()!=null){
					Dictionary dic=dictionaryService.get(sl.getCardtype().longValue());
					sl.setCardtypevalue(dic.getItemValue());
				}
			}
		}
		
		JsonUtil.jsonFromList(list, records) ;
		
		return SUCCESS;
	}
	
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				slPersonMainService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		SlPersonMain slPersonMain=slPersonMainService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slPersonMain));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
           
			if(slPersonMain.getPersonMainId()==null){
				Long companyId=ContextUtil.getLoginCompanyId();
		        slPersonMain.setCompanyId(companyId);
				slPersonMainService.save(slPersonMain);
				
			}else{
				SlPersonMain orgSlPersonMain=slPersonMainService.get(slPersonMain.getPersonMainId());
				try{
					BeanUtil.copyNotNullProperties(orgSlPersonMain, slPersonMain);
					slPersonMainService.save(orgSlPersonMain);
				
				}catch(Exception ex){
					logger.error("SlPersonMainAction:"+ex.getMessage());
				}
			}
		setJsonString("{success:true}");

		return SUCCESS;
		
	}
	//验证证件号码是否重复
	public String verification(){
		List<SlPersonMain> list=null;
		if(id==null){
		   list=slPersonMainService.selectCardNum(cardNum);
		}else{
			SlPersonMain slPersonMain=slPersonMainService.get(id);
			if(null!=slPersonMain){
				if(!slPersonMain.getCardnum().equals(cardNum)){
					list=slPersonMainService.selectCardNum(cardNum);
				}
			}
		}
		if(null!=list && list.size()>0){
			setJsonString("{success:false}");

		}else{
			setJsonString("{success:true}");
		}
		return SUCCESS;
	}
	
	public String queryListForCombo(){
		
		List<SlPersonMain> list = slPersonMainService.queryListForCombo(query);
		if(list!=null&&list.size()!=0){
			for(int i=0;i<list.size();i++){
				SlPersonMain slPerson = list.get(i);
				if(slPerson.getCardtype()!=null&&!"".equals(slPerson.getCardtype())){
					Dictionary dic = dictionaryService.get(new Long(slPerson.getCardtype()));
					if(dic!=null){
						slPerson.setCardtypevalue(dic.getItemValue());
					}
				}
			}
		}
		JsonUtil.jsonFromList(list) ;
		return SUCCESS;
	}
}
