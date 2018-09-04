package com.zhiwei.credit.service.creditFlow.financingAgency.typeManger;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.financingAgency.typeManger.PlKeepProtype;

/**
 * 
 * @author 
 *
 */
public interface PlKeepProtypeService extends BaseService<PlKeepProtype>{

	public void deleteProtype(Long id);
	
	public List<PlKeepProtype> getList(Short isDelete,PagingBean pb);
}


