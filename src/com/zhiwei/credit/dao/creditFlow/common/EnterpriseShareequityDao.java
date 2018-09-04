package com.zhiwei.credit.dao.creditFlow.common;

import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;

public interface EnterpriseShareequityDao extends BaseDao<EnterpriseShareequity> {
   public List<EnterpriseShareequity> findShareequityList(int enterpriseId);
   public EnterpriseShareequity load(int id);
   public List<EnterpriseShareequity> findList(int enterpriseId,int personid,String shareholdertype);
   
   public List<EnterpriseShareequity> findByType(int personid,String shareholdertype);
}
