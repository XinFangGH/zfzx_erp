package com.webServices.services.zwSerDao.impl;

import com.webServices.services.md.zwmd.ZwMd;
import com.webServices.services.zwSerDao.ZwInfo;

public class ZwInfoImpl implements ZwInfo {

	@Override
	public boolean userIsTrue(ZwMd zwMd) {
		boolean ret = false;
		if (zwMd.getAppcode().equals("002") && zwMd.getPassword().equals("1")) {
			ret = true;
		} else {
			ret = false;
		}
		return ret;
	}

}
