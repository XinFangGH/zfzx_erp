package com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.smallLoan.supervise.SlSuperviseRecordDao;
import com.zhiwei.credit.model.creditFlow.smallLoan.supervise.SlSuperviseRecord;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlSuperviseRecordDaoImpl extends BaseDaoImpl<SlSuperviseRecord> implements SlSuperviseRecordDao{

	public SlSuperviseRecordDaoImpl() {
		super(SlSuperviseRecord.class);
	}

	@Override
	public List<SlSuperviseRecord> getListByProjectId(Long projectId,String baseBusinessType) {
		String hql	= "";
		 if(null !=baseBusinessType && !"".equals(baseBusinessType) ){
			 hql += "from SlSuperviseRecord s where s.projectId="+projectId+" and s.baseBusinessType = '"+baseBusinessType+"' order by s.startDate desc";
		 }else{
			 hql += "from SlSuperviseRecord s where s.projectId="+projectId+" and ( s.baseBusinessType IS NULL or s.baseBusinessType = 'SmallLoan') order by s.startDate desc";
		 }
		 return findByHql(hql);
	}
}