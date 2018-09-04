package com.zhiwei.credit.action.system.product;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.compass.core.util.reader.StringReader;

import com.credit.proj.entity.ProcreditMortgage;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.creditFlow.assuretenet.OurProcreditAssuretenetService;
import com.zhiwei.credit.service.creditFlow.finance.SlPlansToChargeService;
import com.zhiwei.credit.service.creditFlow.materials.OurProcreditMaterialsEnterpriseService;
import com.zhiwei.credit.service.system.product.BpProductParameterService;

/**
 * s
 * @author 
 *
 */
public class BpProductParameterAction extends BaseAction{
	@Resource
	private BpProductParameterService bpProductParameterService;
	@Resource
	private OurProcreditMaterialsEnterpriseService ourProcreditMaterialsEnterpriseService;
	@Resource
	private OurProcreditAssuretenetService ourProcreditAssuretenetService;
	@Resource
	private SlPlansToChargeService slplansToChargeService;
	private BpProductParameter bpProductParameter;
	
	private long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BpProductParameter getBpProductParameter() {
		return bpProductParameter;
	}

	public void setBpProductParameter(BpProductParameter bpProductParameter) {
		this.bpProductParameter = bpProductParameter;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		filter.addSorted("createTime", "Desc");
		List<BpProductParameter> list= bpProductParameterService.getAll(filter);
		String isMobile=this.getRequest().getParameter("isMobile");
		if(null!=isMobile&&isMobile.equals("1")){
			QueryFilter query = new QueryFilter(getRequest());
			StringBuffer sb=new StringBuffer("");
			if(null!=list && list.size()>0){
				StringBuffer buff = new StringBuffer("[");
				for (BpProductParameter rt : list) {
					buff.append("{\"text\":\"").append(rt.getProductName()).append("\",\"value\":\"")
							.append(rt.getId()).append("\"},");
				}
				if (list.size() > 0) {
					buff.deleteCharAt(buff.length() - 1);
				}
				buff.append("]");
				setJsonString(buff.toString());
			}
			return SUCCESS;
		}
		Type type=new TypeToken<List<BpProductParameter>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
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
				ourProcreditMaterialsEnterpriseService.deleteByProductId(id);
				ourProcreditAssuretenetService.deleteByProductId(id);
				bpProductParameterService.remove(new Long(id));
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
		BpProductParameter bpProductParameter=bpProductParameterService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpProductParameter));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	public String getByProductId(){
		String productId=this.getRequest().getParameter("productId");
		String type=this.getRequest().getParameter("type");
		List<ProcreditMortgage> list= bpProductParameterService.getByProductId(productId,type);
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
	 * 添加及保存操作
	 */
	public String save(){
		try{
			/*String materialsJsonData=this.getRequest().getParameter("materialsJsonData");
			String assuretenetJsonData=this.getRequest().getParameter("assuretenetJsonData");
			String mortgageJsonData=this.getRequest().getParameter("mortgageJsonData");
			String slActualToChargeJsonData=this.getRequest().getParameter("slActualToChargeJsonData");
			List<OurProcreditMaterialsEnterprise> listMaterials = new ArrayList<OurProcreditMaterialsEnterprise>(); // 材料清单
			List<OurProcreditAssuretenet> listAssuretenet = new ArrayList<OurProcreditAssuretenet>(); // 准入原则
			List<ProcreditMortgage> listMortgage = new ArrayList<ProcreditMortgage>(); // 保证、抵质押物担保
			List<SlActualToCharge> listActualToCharge = new ArrayList<SlActualToCharge>(); // 手续费用
			if (null != materialsJsonData && !"".equals(materialsJsonData)) {
				String[] materialsArr = materialsJsonData.split("@");
				for (int i = 0; i < materialsArr.length; i++) {
					String str = materialsArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					OurProcreditMaterialsEnterprise ourProcreditMaterials = (OurProcreditMaterialsEnterprise) JSONMapper.toJava(parser.nextValue(),OurProcreditMaterialsEnterprise.class);
					listMaterials.add(ourProcreditMaterials);
				}
			}
			if (null != assuretenetJsonData && !"".equals(assuretenetJsonData)) {
				String[] assuretenetsArr = assuretenetJsonData.split("@");
				for (int i = 0; i < assuretenetsArr.length; i++) {
					String str = assuretenetsArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					OurProcreditAssuretenet ourProcreditAssuretenet = (OurProcreditAssuretenet) JSONMapper.toJava(parser.nextValue(),OurProcreditAssuretenet.class);
					listAssuretenet.add(ourProcreditAssuretenet);
				}
			}
			if (null != mortgageJsonData && !"".equals(mortgageJsonData)) {
				String[] dZYMortgagesArr = mortgageJsonData.split("@");
				for (int i = 0; i < dZYMortgagesArr.length; i++) {
					String str = dZYMortgagesArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					ProcreditMortgage procreditMortgage = (ProcreditMortgage) JSONMapper.toJava(parser.nextValue(),ProcreditMortgage.class);
					listMortgage.add(procreditMortgage);
				}
			}
			if (null != bzMortgageJsonData && !"".equals(bzMortgageJsonData)) {
				String[] bzMortgagesArr = bzMortgageJsonData.split("@");
				for (int i = 0; i < bzMortgagesArr.length; i++) {
					String str = bzMortgagesArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					ProcreditMortgage procreditMortgage = (ProcreditMortgage) JSONMapper.toJava(parser.nextValue(),ProcreditMortgage.class);
					listMortgage.add(procreditMortgage);
				}
			}
			if (null != slActualToChargeJsonData && !"".equals(slActualToChargeJsonData)) {
				String[] slActualToChargeArr = slActualToChargeJsonData.split("@");
				for (int i = 0; i < slActualToChargeArr.length; i++) {
					String str = slActualToChargeArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlActualToCharge slActualToCharge = (SlActualToCharge) JSONMapper.toJava(parser.nextValue(),SlActualToCharge.class);
					listActualToCharge.add(slActualToCharge);
				}
			}*/
			List<SlPlansToCharge> PlansToCharge = new ArrayList<SlPlansToCharge>(); // 手续费用
			String SlPlansToChargeDates=this.getRequest().getParameter("SlPlansToChargeDates");
			if (null != SlPlansToChargeDates && !"".equals(SlPlansToChargeDates)) {
				String[] SlPlansToChargeArr = SlPlansToChargeDates.split("@");
				for (int i = 0; i < SlPlansToChargeArr.length; i++) {
					String str = SlPlansToChargeArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					SlPlansToCharge slActualToCharge = (SlPlansToCharge) JSONMapper.toJava(parser.nextValue(),SlPlansToCharge.class);
					PlansToCharge.add(slActualToCharge);
				}
			}
			if(null !=PlansToCharge && PlansToCharge.size()>0){
				for(int i=0;i<PlansToCharge.size();i++){
					SlPlansToCharge Charge=PlansToCharge.get(i);
					SlPlansToCharge oldChrge=null;
					if(null !=Charge.getPlansTochargeId() && !"".equals(Charge.getPlansTochargeId())){
						oldChrge=slplansToChargeService.get(Charge.getPlansTochargeId());
					}
					BeanUtil.copyNotNullProperties(oldChrge,Charge);
					slplansToChargeService.merge(oldChrge);
				}
			}
			Integer flag =bpProductParameterService.saveOrUpdate(bpProductParameter,null,null,null,null);
			if(1==flag){
				setJsonString("{success:true}");
			}else{
				setJsonString("{success:false}");
			}
		}catch(Exception e){
			e.printStackTrace();
			setJsonString("{success:false}");
		}
		return SUCCESS;
	}
}
