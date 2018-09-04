package com.zhiwei.credit.action.customer;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.contract.VProcreditContract;
import com.zhiwei.credit.model.customer.Contract;
import com.zhiwei.credit.model.customer.ContractConfig;
import com.zhiwei.credit.model.system.FileAttach;
import com.zhiwei.credit.service.creditFlow.contract.VProcreditContractService;
import com.zhiwei.credit.service.customer.ContractConfigService;
import com.zhiwei.credit.service.customer.ContractService;
import com.zhiwei.credit.service.system.FileAttachService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class ContractAction extends BaseAction{
	@Resource
	private ContractService contractService;
	@Resource
	private ContractConfigService contractConfigService;
	@Resource 
	private FileAttachService fileAttachService;
	private Contract contract;
	@Resource 
	private VProcreditContractService vProcreditContractService;
	
	private Long contractId;

	private String contractAttachIDs;
	private String data;
	public Long getContractId() {
		return contractId;
	}

	public void setContractId(Long contactId) {
		this.contractId = contactId;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getContractAttachIDs() {
		return contractAttachIDs;
	}

	public void setContractAttachIDs(String contractAttachIDs) {
		this.contractAttachIDs = contractAttachIDs;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Contract> list= contractService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		JSONSerializer json = JsonUtil.getJSONSerializer("signupTime");
		buff.append(json.exclude(new String[]{"class","contractConfigs"}).serialize(list));
		buff.append("}");
		
		jsonString=buff.toString();
		
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
				contractService.remove(new Long(id));
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
		Contract contract=contractService.get(contractId);
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		//JSONSerializer json = JsonUtil.getJSONSerializer("validDate","expireDate","signupTime","createTime");
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		//sb.append(json.exclude(new String[]{"class"}).serialize(contract));
		sb.append(gson.toJson(contract));
		sb.append(",projectId:"+contract.getProjectId());
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		
		//验证
		boolean pass = false;
		StringBuffer buff = new StringBuffer("{");
		if(!(contract.getValidDate().getTime()>contract.getExpireDate().getTime())){
			pass = true;
		}else{
			buff.append("msg:'合同失效日期不能早于生效日期,请重新填写!',");
		}
		
		//logger.info("data:"+data);
		if(pass){
			//录入人和录入时间
			contract.setCreator(ContextUtil.getCurrentUser().getFullname());
			contract.setCreatetime(new Date());
			//合同附件
			String[] fileIDs = getContractAttachIDs().split(",");
			Set<FileAttach> contractAttachs = new HashSet<FileAttach>();
			for (String id : fileIDs) {
      			if (!id.equals("")) {
      				contractAttachs.add(fileAttachService.get(new Long(id)));
      			}
      		}
			contract.setContractFiles(contractAttachs);
			contractService.save(contract);
			if(StringUtils.isNotEmpty(data)){
				Gson gson=new Gson();
				ContractConfig[] contractConfigs=gson.fromJson(data, ContractConfig[].class);
				for(ContractConfig contractConfig:contractConfigs){
					if(contractConfig.getConfigId()==-1){//若为-1，则代表尚未持久化
						contractConfig.setConfigId(null);
						contractConfig.setContractId(contract.getContractId());
					}
					contractConfigService.save(contractConfig);
				}
			}
			buff.append("success:true}");
		}else{
			buff.append("failure:true}");
		}
		setJsonString(buff.toString());
		return SUCCESS;
	}
	
	/**
	 * 删除合同附件
	 * @return
	 */
	public String removeFile(){
		setContract(contractService.get(contractId));
		Set<FileAttach> contractFiles = contract.getContractFiles();
		FileAttach removeFile = fileAttachService.get(new Long(contractAttachIDs));
		contractFiles.remove(removeFile);
		contract.setContractFiles(contractFiles);
		contractService.save(contract);
		setJsonString("{success:true}");
		return SUCCESS;
	}
	/**
	 * 获得合同文件路径
	 * @return
	 */
	public String getUrl(){
		QueryFilter filter=new QueryFilter(getRequest());
		String contractId = getRequest().getParameter("contractId");
		String url="";
		if(!"".equals(contractId)){
			String projectId = getRequest().getParameter("projectId");
			String businessType = getRequest().getParameter("businessType");
			String htType = getRequest().getParameter("htType");
			filter.addFilter("Q_projId_S_EQ", projectId);
			filter.addFilter("Q_businessType_S_EQ", businessType);
			filter.addFilter("Q_htType_S_EQ", htType);
			List<VProcreditContract> list=vProcreditContractService.getList(projectId, businessType, htType, Integer.parseInt(contractId));
			if(!list.isEmpty()){
				VProcreditContract vp=list.get(0);
				url=vp.getUrl();
			}
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{success:true,\"url\":");
		url=(null==url?"":url.replace("\\", "/"));
		sb.append("\""+url);
		sb.append("\""+"}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
}
