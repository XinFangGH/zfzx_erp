package com.zhiwei.credit.dao.communicate;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.communicate.PhoneGroup;

/**
 * 
 * @author 
 *
 */
public interface PhoneGroupDao extends BaseDao<PhoneGroup>{
	
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