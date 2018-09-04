package com.zhiwei.credit.service.creditFlow.leaseFinance.supplior.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.core.commons.CreditException;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.supplior.FlObjectSuppliorDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.supplior.FlObjectSupplior;
import com.zhiwei.credit.service.creditFlow.leaseFinance.supplior.FlObjectSuppliorService;

/**
 * 
 * @author 
 *
 */
public class FlObjectSuppliorServiceImpl extends BaseServiceImpl<FlObjectSupplior> implements FlObjectSuppliorService{
	@SuppressWarnings("unused")
	private FlObjectSuppliorDao dao;
	
	
	public FlObjectSuppliorServiceImpl(FlObjectSuppliorDao dao) {
		super(dao);
		this.dao=dao;
	}
	public void updateSuppliorByObjectId(Long suppliorId,FlObjectSupplior flObjectSupplior){
		if(flObjectSupplior!=null){
			try{
				FlObjectSupplior flObjectSupplior1 = this.dao.get(suppliorId);
				BeanUtil.copyNotNullProperties(flObjectSupplior1, flObjectSupplior);
				dao.save(flObjectSupplior1);
			}catch(Exception e){
				logger.error("更新租赁标的物信息出错:"+e.getMessage());
				//e.printStackTrace();
				throw new CreditException(e);
			}
		}
	}
}