package com.zhiwei.credit.service.cxf;

import java.util.List;

import javax.jws.WebService;

import com.zhiwei.credit.model.admin.Regulation;

@WebService(targetNamespace="http://www.hurongtime.com")
public interface RegService {
	public List<Regulation> getAll();
}
