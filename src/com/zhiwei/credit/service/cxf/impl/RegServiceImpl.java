package com.zhiwei.credit.service.cxf.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.zhiwei.credit.model.admin.Regulation;
import com.zhiwei.credit.service.admin.RegulationService;
import com.zhiwei.credit.service.cxf.RegService;

@WebService
public class RegServiceImpl implements RegService{
	@Resource
	RegulationService regulationService;
	
	@Override
	public  List<Regulation> getAll() {
		return regulationService.getAll();
	}
}
