package com.zhiwei.credit.dao.creditFlow.smallLoan.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.finance.SlAlterAccrualRecordDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlAlterAccrualRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlAlterAccrualRecordDaoImpl extends BaseDaoImpl<SlAlterAccrualRecord> implements SlAlterAccrualRecordDao{

	public SlAlterAccrualRecordDaoImpl() {
		super(SlAlterAccrualRecord.class);
	}
	
	public List<SlAlterAccrualRecord> getByProjectId(Long projectId,String businessType) {
		if(null == businessType||"".equals(businessType)){//默认支持小贷系统，businessType为后来新增字段
			businessType = "SmallLoan";
		}
		String hql = "from SlAlterAccrualRecord sa where sa.projectId=" + projectId + " and sa.baseBusinessType='"+businessType +"' order by sa.opTime desc";
		return findByHql(hql);
	}
}