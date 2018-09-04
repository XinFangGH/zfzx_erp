package com.zhiwei.credit.service.system.product.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.axis2.jaxws.context.utils.ContextUtils;

import com.credit.proj.entity.ProcreditMortgage;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.assuretenet.OurProcreditAssuretenetDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.materials.OurProcreditMaterialsEnterpriseDao;
import com.zhiwei.credit.dao.system.product.BpProductParameterDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.system.product.BpProductParameter;
import com.zhiwei.credit.service.system.product.BpProductParameterService;
import com.webServices.mortgage.ProcreditMortgageService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class BpProductParameterServiceImpl extends BaseServiceImpl<BpProductParameter> implements BpProductParameterService{
	private BpProductParameterDao dao;
	@Resource
	private OurProcreditMaterialsEnterpriseDao ourProcreditMaterialsEnterpriseDao;
	@Resource
	private OurProcreditAssuretenetDao ourProcreditAssuretenetDao;
	@Resource
	private ProcreditMortgageService procreditMortgageService;
	@Resource
	private SlActualToChargeDao slActualToChargeDao;
	@Resource
	private CreditBaseDao creditBaseDao;
	
	public BpProductParameterServiceImpl(BpProductParameterDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Integer saveOrUpdate(BpProductParameter bpProductParameter,List<OurProcreditMaterialsEnterprise> listMaterials,
					List<OurProcreditAssuretenet> listAssuretenet,List<ProcreditMortgage> listMortgage,List<SlActualToCharge> listActualToCharge) {
		try{
			if(bpProductParameter.getId()==null){
				bpProductParameter.setCreateTime(new Date());
				dao.save(bpProductParameter);
			}else{
				BpProductParameter orgBpProductParameter=dao.get(bpProductParameter.getId());
				BeanUtil.copyNotNullProperties(orgBpProductParameter, bpProductParameter);
				orgBpProductParameter.setCreateTime(new Date());
				dao.merge(orgBpProductParameter);
			}
			if(null!=listMaterials && listMaterials.size()>0){
				for(int i=0;i<listMaterials.size();i++){
					OurProcreditMaterialsEnterprise materials=listMaterials.get(i);
					materials.setProductId(bpProductParameter.getId());
					if(materials.getMaterialsId()!=null){
						OurProcreditMaterialsEnterprise  old=ourProcreditMaterialsEnterpriseDao.get(materials.getMaterialsId());
						BeanUtil.copyNotNullProperties(old, materials);
						ourProcreditMaterialsEnterpriseDao.save(old);
					}else{
						if(materials.getProjectId()!=null){
							OurProcreditMaterialsEnterprise o =ourProcreditMaterialsEnterpriseDao.get(materials.getProjectId());
							if(o!=null){
								materials.setLeaf(o.getLeaf());
								materials.setParentId(o.getParentId());
								materials.setOperationTypeKey(o.getOperationTypeKey());
							}
						}else if(materials.getProjectId()==null&&materials.getParentId()!=null){
							OurProcreditMaterialsEnterprise o =ourProcreditMaterialsEnterpriseDao.get(Long.valueOf(materials.getParentId().toString()));
							if(o!=null){
								materials.setLeaf(1);
								materials.setOperationTypeKey(o.getOperationTypeKey());
							}
						}
						ourProcreditMaterialsEnterpriseDao.save(materials);
					}
				}
			}
			if(null!=listAssuretenet && listAssuretenet.size()>0){
				for(int i=0;i<listAssuretenet.size();i++){
					OurProcreditAssuretenet assuretenets=listAssuretenet.get(i);
					assuretenets.setProductId(bpProductParameter.getId());
					ourProcreditAssuretenetDao.save(assuretenets);
				}
			}
			if(null!=listMortgage && listMortgage.size()>0){
				for(int i=0;i<listMortgage.size();i++){
					ProcreditMortgage mortgages=listMortgage.get(i);
					mortgages.setProductId(bpProductParameter.getId());
					procreditMortgageService.save(mortgages);
				}
			}
			if(null!=listActualToCharge && listActualToCharge.size()>0){
				for(int i=0;i<listActualToCharge.size();i++){
					SlActualToCharge slActualToCharge=listActualToCharge.get(i);
					slActualToCharge.setProductId(bpProductParameter.getId());
					slActualToChargeDao.save(slActualToCharge);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	@Override
	public List<ProcreditMortgage> getByProductId(String productId,String type) {
		try {
			String hql="from ProcreditMortgage e where 1=1 ";
			if(null!=productId && !"".equals(productId) && !"null".equals(productId)){
				hql+=" and e.productId="+productId;
				if(null!=type && !"".equals(type) && !"null".equals(type)){
					hql+=" and e.assuretypeid="+type;
				}
				return  creditBaseDao.queryHql(hql);
			}else{
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}