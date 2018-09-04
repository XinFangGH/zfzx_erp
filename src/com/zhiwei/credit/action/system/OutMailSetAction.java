package com.zhiwei.credit.action.system;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.communicate.OutMailUserSeting;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.communicate.OutMailUserSetingService;
import com.zhiwei.credit.service.system.AppUserService;

public class OutMailSetAction extends BaseAction {
	@Resource
	private OutMailUserSetingService outMailUserSetingService;
	@Resource
	private AppUserService appUserService;
	private AppUser appUser;
	private OutMailUserSeting outMailUserSeting;

	public AppUser getAppUser() {
		return appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

	public OutMailUserSeting getOutMailUserSeting() {
		return outMailUserSeting;
	}

	public void setOutMailUserSeting(OutMailUserSeting outMailUserSeting) {
		this.outMailUserSeting = outMailUserSeting;
	}

	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 显示列表
	 */
	@SuppressWarnings("unchecked")
	public String list() {
		PagingBean pb = new PagingBean(start, limit);
		String fullname = getRequest().getParameter("userName");
		
		if (StringUtils.isNotEmpty(fullname)) {
			List<OutMailUserSeting> mail = outMailUserSetingService
					.findByUserAll(fullname, pb);
			StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
					.append(pb.getTotalItems()).append(",result:[");
			for(int i = 0;i<mail.size();i++){
				OutMailUserSeting set = mail.get(i);
				if(set!=null){
					buff.append("{'id':'" + set.getId() + "','userId':'"
							+ set.getUserId() + "'" + ",'userName':'"
							+ set.getUserName() + "'" + ",'mailAddress':'"
							+ set.getMailAddress() + "'" + ",'mailPass':'"
							+ set.getMailPass() + "','smtpHost':'"
							+ set.getSmtpHost() + "','smtpPort':'"
							+ set.getSmtpPort() + "'" + ",'mailAddress':'"
							+ set.getMailAddress() + "','mailAddress':'"
							+ set.getMailAddress() + "'" + ",'popHost':'"
							+ set.getPopHost() + "','popPort':'" + set.getPopPort()
							+ "'}");
				}else {
					buff.append("{'id':'','userId':'','userName':'','mailAddress':'','mailAddress':'','mailPass':'','smtpHost':'','smtpPort':''"
							+ ",'popHost':'','popPort':''}");
				}
			}
			buff.append("]}");
			jsonString = buff.toString();
			
		} else {
			List<Object[]> list = outMailUserSetingService.findByUserAll();
			StringBuffer sb = new StringBuffer("{success:true,'totalCounts':");
			sb.append(list.size()).append(",result:[");
			for (int i = 0; i < list.size(); i++) {
				if (i > 0) {
					sb.append(",");
				}
				OutMailUserSeting set = (OutMailUserSeting) list.get(i)[0];
				AppUser user = (AppUser) list.get(i)[1];
				sb.append("{'userId':'" + user.getUserId() + "','userName':'"
						+ user.getFullname() + "',");
				if (set != null) {
					sb.append("'id':'" + set.getId() + "','mailAddress':'"
							+ set.getMailAddress() + "'" + ",'mailPass':'"
							+ set.getMailPass() + "','smtpHost':'"
							+ set.getSmtpHost() + "','smtpPort':'"
							+ set.getSmtpPort() + "'" + ",'mailAddress':'"
							+ set.getMailAddress() + "','mailAddress':'"
							+ set.getMailAddress() + "'" + ",'popHost':'"
							+ set.getPopHost() + "','popPort':'"
							+ set.getPopPort() + "'}");
				} else {
					sb.append("'id':'','mailAddress':'','mailAddress':'','mailPass':'','smtpHost':'','smtpPort':''"
							+ ",'popHost':'','popPort':''}");
				}
			}
			sb.append("]}");
			jsonString = sb.toString();
		}
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				outMailUserSetingService.remove(new Long(id));
			}
		}

		jsonString = "{success:true}";

		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {

		OutMailUserSeting outMailUserSeting = outMailUserSetingService.get(id);
		Gson gson = new Gson();
		// 将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");

		if (outMailUserSeting != null) {

			sb.append(gson.toJson(outMailUserSeting));
		} else {
			outMailUserSeting = new OutMailUserSeting();
			outMailUserSeting.setUserId(ContextUtil.getCurrentUserId());
			outMailUserSeting.setUserName(ContextUtil.getCurrentUser()
					.getUsername());

			sb.append(gson.toJson(outMailUserSeting));
		}

		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;
	}

	/**
	 * 添加及保存操作
	 */
	public String save() {
		String data = getRequest().getParameter("data");
		if (StringUtils.isNotEmpty(data)) {
			Gson gson = new Gson();
			OutMailUserSeting[] outSet = gson.fromJson(data,
					OutMailUserSeting[].class);
			for (OutMailUserSeting setting : outSet) {
				if (setting.getId() == -1) {
					setting.setId(null);
				}
				if (setting.getReuserId() != null && StringUtils.isNotEmpty(setting.getMailAddress())
						&& StringUtils.isNotEmpty(setting.getPopHost()) && StringUtils.isNotEmpty(setting.getUserName())
						&& StringUtils.isNotEmpty(setting.getSmtpPort()) && StringUtils.isNotEmpty(setting.getSmtpHost())
						&& StringUtils.isNotEmpty(setting.getPopPort()) && StringUtils.isNotEmpty(setting.getMailPass()))
				{
					AppUser appUser = appUserService.get(setting.getReuserId());
					setting.setAppUser(appUser);
					outMailUserSetingService.save(setting);
				}
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
