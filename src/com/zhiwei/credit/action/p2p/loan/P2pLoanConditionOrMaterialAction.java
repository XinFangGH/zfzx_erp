package com.zhiwei.credit.action.p2p.loan;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.p2p.loan.P2pLoanBasisMaterial;
import com.zhiwei.credit.model.p2p.loan.P2pLoanConditionOrMaterial;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanBasisMaterialService;
import com.zhiwei.credit.service.p2p.loan.P2pLoanConditionOrMaterialService;
/**
 * 
 * @author 
 *
 */
public class P2pLoanConditionOrMaterialAction extends BaseAction{
	@Resource
	private P2pLoanConditionOrMaterialService p2pLoanConditionOrMaterialService;
	private P2pLoanConditionOrMaterial p2pLoanConditionOrMaterial;
	@Resource
	private P2pLoanBasisMaterialService p2pLoanBasisMaterialService;
	@Resource
	private FileFormService fileFormService;
	
	private Long conditionId;
	private Long conditionType;
	private Long productId;

	
	public Long getConditionType() {
		return conditionType;
	}

	public void setConditionType(Long conditionType) {
		this.conditionType = conditionType;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getConditionId() {
		return conditionId;
	}

	public void setConditionId(Long conditionId) {
		this.conditionId = conditionId;
	}

	public P2pLoanConditionOrMaterial getP2pLoanConditionOrMaterial() {
		return p2pLoanConditionOrMaterial;
	}

	public void setP2pLoanConditionOrMaterial(P2pLoanConditionOrMaterial p2pLoanConditionOrMaterial) {
		this.p2pLoanConditionOrMaterial = p2pLoanConditionOrMaterial;
	}
	/**
	 * 根据ProductId显示列表
	 */
	public String listByProductId(){
		List<P2pLoanConditionOrMaterial> list=null;
		if(null!=productId && !"".equals(productId) && !"null".equals(productId) && null!=conditionType && !"".equals(conditionType)){
			list= p2pLoanConditionOrMaterialService.getByProductId(Long.valueOf(productId),conditionType);
		}
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':");
		if(null!=list){
			buff.append(list.size()).append(",result:");
		}else{
			buff.append(0).append(",result:");
		}
		Gson gson=new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<P2pLoanConditionOrMaterial> list= p2pLoanConditionOrMaterialService.getAll(filter);
		
		Type type=new TypeToken<List<P2pLoanConditionOrMaterial>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
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
				p2pLoanConditionOrMaterialService.remove(new Long(id));
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
		P2pLoanConditionOrMaterial p2pLoanConditionOrMaterial=p2pLoanConditionOrMaterialService.get(conditionId);
		String mark1="";
		String mark2="";
	    if(null!=p2pLoanConditionOrMaterial.getProjectId() && !"".equals(p2pLoanConditionOrMaterial.getProjectId())){
	    	mark1="p2p_loan_basismaterial.shilitu1."+p2pLoanConditionOrMaterial.getProjectId();
	    	mark2="p2p_loan_basismaterial.shilitu2."+p2pLoanConditionOrMaterial.getProjectId();
	    }
	    else{
	    	mark1="p2p_loan_conditionormaterial.shilitu1."+p2pLoanConditionOrMaterial.getConditionId();
	    	mark2="p2p_loan_conditionormaterial.shilitu2."+p2pLoanConditionOrMaterial.getConditionId();
	    }
		FileForm fileForm1_shilitu1 = fileFormService.getFileByMark(mark1);
		FileForm fileForm2_shilitu2 = fileFormService.getFileByMark(mark2);
		if(fileForm1_shilitu1!= null){
			p2pLoanConditionOrMaterial.setShilitu1Id(fileForm1_shilitu1.getFileid());
			p2pLoanConditionOrMaterial.setShilitu1URL(fileForm1_shilitu1.getWebPath());
			p2pLoanConditionOrMaterial.setShilitu1ExtendName(fileForm1_shilitu1.getExtendname());
		}
		if(fileForm2_shilitu2 != null){
			p2pLoanConditionOrMaterial.setShilitu2Id(fileForm2_shilitu2.getFileid());
			p2pLoanConditionOrMaterial.setShilitu2URL(fileForm2_shilitu2.getWebPath());
			p2pLoanConditionOrMaterial.setShilitu2ExtendName(fileForm2_shilitu2.getExtendname());
		}
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(p2pLoanConditionOrMaterial));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(p2pLoanConditionOrMaterial.getConditionId()==null){
			p2pLoanConditionOrMaterialService.save(p2pLoanConditionOrMaterial);
			Integer shilitu1Id=p2pLoanConditionOrMaterial.getShilitu1Id();
			Integer shilitu2Id=p2pLoanConditionOrMaterial.getShilitu2Id();
			if(shilitu1Id != null && !shilitu1Id.equals("")){ //示例图1
				fileFormService.updateFile("p2p_loan_conditionormaterial.shilitu1."+p2pLoanConditionOrMaterial.getConditionId(), shilitu1Id);
			}
			if(shilitu2Id != null && !shilitu2Id.equals("")){ //示例图2
				fileFormService.updateFile("p2p_loan_conditionormaterial.shilitu2."+p2pLoanConditionOrMaterial.getConditionId(), shilitu2Id);
			}
		}else{
			P2pLoanConditionOrMaterial orgP2pLoanConditionOrMaterial=p2pLoanConditionOrMaterialService.get(p2pLoanConditionOrMaterial.getConditionId());
			try{
				BeanUtil.copyNotNullProperties(orgP2pLoanConditionOrMaterial, p2pLoanConditionOrMaterial);
				p2pLoanConditionOrMaterialService.save(orgP2pLoanConditionOrMaterial);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	//新增产品贷款材料
	public String addInfo(){
		try{
			String materialId = this.getRequest().getParameter("materialId");

			if(null!=materialId && !"".equals(materialId)){
				String[] proArrs = materialId.split(",");
				for(int i = 0;i<proArrs.length;i++){
					P2pLoanBasisMaterial pb = p2pLoanBasisMaterialService.get(Long.valueOf(proArrs[i]));
					P2pLoanConditionOrMaterial pc=new P2pLoanConditionOrMaterial();
					pc.setProductId(productId);
					pc.setProjectId(pb.getMaterialId());
					pc.setConditionState(pb.getMaterialState());
					pc.setConditionContent(pb.getMaterialName());
					pc.setConditionType(new Long("2"));
					pc.setRemark(pb.getRemark());
					p2pLoanConditionOrMaterialService.save(pc);
				}
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}


}
