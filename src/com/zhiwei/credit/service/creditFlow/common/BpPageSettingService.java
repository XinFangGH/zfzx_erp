package com.zhiwei.credit.service.creditFlow.common;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.creditFlow.common.BpPageSetting;

/**
 * 
 * @author 
 *
 */
public interface BpPageSettingService extends BaseService<BpPageSetting>{
	public List<BpPageSetting> listByParentId(Long parentId);
	public List<BpPageSetting> listByPageKey(String pageKey); 
}


