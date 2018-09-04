package com.zhiwei.credit.service.creditFlow.thirdRalation;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/


import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.thirdRalation.SVEnterprisePerson;




public interface SVEnterprisePersonService extends BaseService<SVEnterprisePerson>{
	public SVEnterprisePerson findSVEnterprisePerson(long customerId,String type);
}


