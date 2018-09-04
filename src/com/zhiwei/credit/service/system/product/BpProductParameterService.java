package com.zhiwei.credit.service.system.product;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.credit.proj.entity.ProcreditMortgage;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.materials.OurProcreditMaterialsEnterprise;
import com.zhiwei.credit.model.system.product.BpProductParameter;

/**
 * 
 * @author 
 *
 */
public interface BpProductParameterService extends BaseService<BpProductParameter>{

	public Integer saveOrUpdate(BpProductParameter bpProductParameter,List<OurProcreditMaterialsEnterprise> listMaterials,
						  List<OurProcreditAssuretenet> listAssuretenet, List<ProcreditMortgage> listMortgage,List<SlActualToCharge> listActualToCharge);

	public List<ProcreditMortgage> getByProductId(String productId,String type);
	
}


