package com.zhiwei.credit.service.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.List;

import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.flow.FormTable;

public interface FormTableGenService extends BaseService<FormTable>{
	public boolean genBean(FormTable[] formTables);
}


