package com.zhiwei.credit.action.htmobile;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.action.htmobile.util.JacksonMapper;
import com.zhiwei.credit.action.htmobile.util.RequestUtil;
import com.zhiwei.credit.model.communicate.PhoneBook;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.communicate.PhoneBookService;
import com.zhiwei.credit.service.system.AppUserService;

import oracle.net.aso.i;



public class MyAppUserAction extends BaseAction{

	@Resource
	private AppUserService appUserService;
	@Resource
	private PhoneBookService phoneBookService;
	
	JacksonMapper mapper = new JacksonMapper(true, "yyyy-MM-dd HH:mm:ss");
	//显示所有用户
	public String list(){
		String isOut=RequestUtil.getString(getRequest(), "isOut");
		if ("0".equals(isOut)) {
			List<AppUser> list = appUserService.getAll();
			for (int i = 0; i < list.size(); i++) {
				if ("-1".equals(list.get(i).getId())) {
					list.remove(i);
				}
			}
			JacksonMapper mapper = new JacksonMapper(true, "yyyy-MM-dd HH:mm:ss");
			jsonString = mapper.toPageJson(list, list.size());
		}else if ("1".equals(isOut)) {
			List<PhoneBook> phoneBooks = phoneBookService.getAll();
			jsonString = mapper.toPageJson(phoneBooks, phoneBooks.size());
		}
		
		return SUCCESS;
	}
}
