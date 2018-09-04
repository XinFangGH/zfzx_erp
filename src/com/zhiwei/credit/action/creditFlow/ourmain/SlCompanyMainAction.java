package com.zhiwei.credit.action.creditFlow.ourmain;
/*
 北京互融时代软件有限公司————协同办公管理系统   
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
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.model.system.Organization;
import com.zhiwei.credit.service.creditFlow.multiLevelDic.AreaDicService;
import com.zhiwei.credit.service.creditFlow.ourmain.SlCompanyMainService;
import com.zhiwei.credit.service.system.DictionaryService;
import com.zhiwei.credit.service.system.OrganizationService;
/**
 * 
 * @author 
 *
 */
public class SlCompanyMainAction extends BaseAction{
	@Resource
	private SlCompanyMainService slCompanyMainService;
	@Resource
	private DictionaryService dictionaryService;
	@Resource
	private AreaDicService areaDicService;
	@Resource
	private OrganizationService organizationService;
	private SlCompanyMain slCompanyMain;
	
	private Long id;
    
	private String corName;
	
	private String isPledge;
	
	private String query ;
	
	public String getCorName() {
		return corName;
	}

	public void setCorName(String corName) {
		this.corName = corName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SlCompanyMain getSlCompanyMain() {
		return slCompanyMain;
	}

	public void setSlCompanyMain(SlCompanyMain slCompanyMain) {
		this.slCompanyMain = slCompanyMain;
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

	/**
	 * 显示列表
	 */
	public String list(){
		//是否是企业版，显示所有公司
		Boolean isGroup = Boolean.valueOf(this.getRequest().getParameter("isGroup"));
		//String corparationName = "";
		QueryFilter filter=new QueryFilter(getRequest());
		
		//获取查询面板的查询条件值
		int start = filter.getPagingBean().getStart();
		int limit = filter.getPagingBean().getPageSize();
		String lawName = filter.getRequest().getParameter("Q_lawName_S_LK");
		String organizeCode = filter.getRequest().getParameter("Q_organizeCode_S_LK");
		String corName = filter.getRequest().getParameter("Q_corName_S_LK");
		
		/*//判断是否是我方企业主体参照的查询条件。
		if(corName==null||"".equals(corName)||"null".equals(corName)){
			corparationName = filter.getRequest().getParameter("Q_corName_S_LK");
		}else{
			corparationName = corName;
		}*/
		
		PagingBean pb=new PagingBean(start, limit);
		String companyId=null;
		  if(null!=this.getRequest().getParameter("companyId"))
		  {
		   companyId=(String)this.getRequest().getParameter("companyId");
		  } 
		List<SlCompanyMain> list = null;
		if(isGroup){
			list = slCompanyMainService.queryListWithPB(pb);
		}else{
			list = slCompanyMainService.findCompanyListByIsPledge(corName, lawName, organizeCode, pb,companyId);
		}
		
		
		//List<SlCompanyMain> list= slCompanyMainService.getAll(filter);

		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pb.getTotalItems()).append(",result:[");
		for(SlCompanyMain scm:list){
			buff.append("{\"companyMainId\":");
			buff.append(scm.getCompanyMainId());
			buff.append(",\"corName\":'");
			buff.append(scm.getCorName());
			buff.append("',\"simpleName\":'");
			buff.append(scm.getSimpleName());
			buff.append("',\"lawName\":'");
			buff.append(scm.getLawName());
			buff.append("',\"organizeCode\":'");
			buff.append(scm.getOrganizeCode());
			buff.append("',\"businessCode\":'");
			buff.append(scm.getBusinessCode());
			buff.append("',\"haveCharcter\":'");
			if(scm.getHaveCharcter()!=null){
		     	Dictionary dic=dictionaryService.get(scm.getHaveCharcter().longValue());
			    buff.append(dic.getItemValue());
			}else{
				buff.append("");
			}
			
			buff.append("',\"tax\":'");
			buff.append(scm.getTax());
			buff.append("',\"tel\":'");
			buff.append(scm.getTel());
			buff.append("',\"mail\":'");
			buff.append(scm.getMail());
			buff.append("',\"messageAddress\":'");
			buff.append(scm.getMessageAddress());
			buff.append("',\"sjjyAddress\":'");
			buff.append(scm.getSjjyAddress());
			buff.append("',\"postalCode\":'");
			buff.append(scm.getPostalCode());
			buff.append("',\"isPledge\":'");
			buff.append(scm.getIsPledge());
			buff.append("',\"registerMoney\":'");
			buff.append(scm.getRegisterMoney());
			buff.append("',\"hangyeType\":'");
			buff.append(scm.getHangyeType());
			buff.append("',\"hangyeTypeValue\":'");
			if(scm.getHangyeType()!=null){
				try {
					AreaDic dic = areaDicService.getById(scm.getHangyeType());
					if(dic!=null){
						buff.append(dic.getText());
					}
				} catch (Exception e) {
					logger.error("SlCompanyMainAction:"+e.getMessage());
					e.printStackTrace();
				}
			}else{
				buff.append("");
			}
			buff.append("',\"registerStartDate\":'");
			buff.append(scm.getRegisterStartDate());
			buff.append("',\"companyId\":");
			buff.append(scm.getCompanyId());
			buff.append(",\"companyName\":'");
			if(null!=scm.getCompanyId()){
				Organization organization=organizationService.get(scm.getCompanyId());
				if(null!=organization){
					buff.append(organization.getOrgName());
				}
			}
			buff.append("'},");
		}
		if(null!=list && list.size()>0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 获取我方企业主体-参照
	 * @param corName
	 * @param PagingBean
	 * @return
	 */
	public String listReference(){
		
		PagingBean pb=new PagingBean(start, limit);
		
		List<SlCompanyMain> list= slCompanyMainService.findCompanyListReference(corName, pb);
		
		JsonUtil.jsonFromList(list, list==null?0:list.size()) ;
		
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
				slCompanyMainService.remove(new Long(id));
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
		SlCompanyMain slCompanyMain=slCompanyMainService.get(id);
		
		try {
			if(slCompanyMain!=null){
				if(slCompanyMain.getHangyeType()!=null&&!"".equals(slCompanyMain.getHangyeType())){
					AreaDic dic = areaDicService.getById(slCompanyMain.getHangyeType());
					slCompanyMain.setHangyeTypeValue(dic.getText());
				}else{
					slCompanyMain.setHangyeTypeValue("");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("SlCompanyMainAction:"+e.getMessage());
		}
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(slCompanyMain));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		if(slCompanyMain.getCompanyMainId()==null){
			Long companyId=ContextUtil.getLoginCompanyId();
			slCompanyMain.setCompanyId(companyId);
			slCompanyMainService.save(slCompanyMain);
		}else{
			SlCompanyMain orgSlCompanyMain=slCompanyMainService.get(slCompanyMain.getCompanyMainId());
			try{
				BeanUtil.copyNotNullProperties(orgSlCompanyMain, slCompanyMain);
				slCompanyMainService.save(orgSlCompanyMain);
			}catch(Exception ex){
				logger.error("SlCompanyMainAction:"+ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/*
	 * 验证企业名称不能重复
	 * */
	public String verification(){
		List<SlCompanyMain> list=null;
		if(id==null){
		  list=slCompanyMainService.findCompanyList(corName);
		}else{
			SlCompanyMain slCompanyMain=slCompanyMainService.get(id);
			if(null!=slCompanyMain){
				if(!slCompanyMain.getCorName().equals(corName)){
					list=slCompanyMainService.findCompanyList(corName);
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
		
		List<SlCompanyMain> list = slCompanyMainService.queryListForCombo(query);
		JsonUtil.jsonFromList(list) ;
		return SUCCESS;
	}
}
