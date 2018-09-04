package com.zhiwei.credit.service.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.communicate.PhoneGroup;

public interface PhoneGroupService extends BaseService<PhoneGroup>{
	public Integer findLastSn(Long userId);
	public PhoneGroup findBySn(Integer sn,Long userId);
	public List<PhoneGroup> findBySnUp(Integer sn,Long userId);
	public List<PhoneGroup> findBySnDown(Integer sn,Long userId);
	public List<PhoneGroup> getAll(Long userId);
	
	public Integer findPublicLastSn();
	public PhoneGroup findPublicBySn(Integer sn);
	public List<PhoneGroup> findPublicBySnUp(Integer sn);
	public List<PhoneGroup> findPublicBySnDown(Integer sn);
	public List<PhoneGroup> getPublicAll();
}


