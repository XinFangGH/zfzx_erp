package com.zhiwei.credit.service.creditFlow.customer.prosperctive.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.List;

import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.prosperctive.BpCustProspectiveRelationDao;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseShareequity;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProspectiveRelation;
import com.zhiwei.credit.model.creditFlow.customer.prosperctive.BpCustProsperctive;
import com.zhiwei.credit.service.creditFlow.customer.prosperctive.BpCustProspectiveRelationService;

/**
 * 
 * @author 
 *
 */
public class BpCustProspectiveRelationServiceImpl extends BaseServiceImpl<BpCustProspectiveRelation> implements BpCustProspectiveRelationService{
	@SuppressWarnings("unused")
	private BpCustProspectiveRelationDao dao;
	
	public BpCustProspectiveRelationServiceImpl(BpCustProspectiveRelationDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public Integer saveRelationData(String relationData,BpCustProsperctive customer) {
		try{
			if(relationData!=null &&!"".equals(relationData) && !"null".equals(relationData)){
				String[] relationDataArr = relationData.split("@");
				for (int i = 0; i < relationDataArr.length; i++) {
					String str = relationDataArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustProspectiveRelation bpCustProspectiveRelation;
					bpCustProspectiveRelation = (BpCustProspectiveRelation) JSONMapper.toJava(parser.nextValue(),BpCustProspectiveRelation.class);
					bpCustProspectiveRelation.setBpCustProsperctive(customer);
					dao.save(bpCustProspectiveRelation);
				}
			}
			return 1;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}

		
	}

	@Override
	public List<BpCustProspectiveRelation> listByPerId(String perId) {
		// TODO Auto-generated method stub
		return dao.listByPerId(perId);
	}

}