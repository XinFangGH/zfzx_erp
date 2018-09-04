package com.zhiwei.credit.service.system;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.system.CsDicAreaDynam;



public interface CsDicAreaDynamService extends BaseService<CsDicAreaDynam>{

	/*
	 * 根据ID查找其一级子节点
	 */

	public List<CsDicAreaDynam> getAllItemById(final Long ID);

	/*
	 * 根据父ID为xx的所有节点
	 */

	public List<CsDicAreaDynam> getAllItemByParentId(final Long parentID);
	public String getAllParentBanksTree(String node);
}


