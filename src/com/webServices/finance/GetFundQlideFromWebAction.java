package com.webServices.finance;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;

import nc.itf.crd.webservice.izyhtwebservice.CancelDataDocument;
import nc.itf.crd.webservice.izyhtwebservice.IZyhtWebServiceStub;
import nc.itf.crd.webservice.izyhtwebservice.CancelDataDocument.CancelData;

import org.apache.axis2.AxisFault;

import com.webServices.services.factory.modelfactory.ZyVo;
import com.webServices.services.factory.modelfactory.base.ZyhtVoFactory;
import com.webServices.services.factory.urlFactory.WebServicesUrl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;

public class GetFundQlideFromWebAction extends BaseAction {
	public String cancDate() {
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen = WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub = null;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		CancelDataDocument faData = CancelDataDocument.Factory.newInstance();
		CancelData cancelData = CancelData.Factory.newInstance();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String dataTypeStatus = this.getRequest()
				.getParameter("dataTypeStatus");
		String dayDate = this.getRequest().getParameter("factdate");
		Long companyId = ContextUtil.getLoginCompanyId();

		String[] vonames = new String[1];
		if (dataTypeStatus.equals("2")) {
			vonames[0] = "crd_acc_realinte";
		}

		if (dataTypeStatus.equals("3")) {
			vonames[0] = " crd_acc_realfee";
		}

		if (dataTypeStatus.equals("4")) {
			vonames[0] = "crd_acc_realpri";
		}

		if (dataTypeStatus.equals("5")) {
			vonames[0] = "crd_acc_realpi";
		}

		if (dataTypeStatus.equals("6")) {
			vonames[0] = "crd_acc_inteplan";
		}

		if (dataTypeStatus.equals("7")) {
			vonames[0] = "crd_acc_feeplan";
		}

		if (dataTypeStatus.equals("9")) {
			vonames[0] = "crd_acc_overdue";
		}

		if (dataTypeStatus.equals("8")) {
			vonames[0] = "crd_acc_inteaccr";
		}
		if (dataTypeStatus.equals("10")) {
			vonames[0] = "crd_acc_feeaccr";
		}
		ZyhtVoFactory zyvo = new ZyVo();
		cancelData.setStringItemArray(vonames);
		cancelData.setZyhtVO(zyvo.createVo(companyId.toString(), dayDate, 1));
		faData.setCancelData(cancelData);
		String isSuccess = "";
		String result = "";
		if (isOpen) {
			try {
				String[] ss = stub.cancelData(faData).getCancelDataResponse()
						.getReturnArray();
				isSuccess = ss[0];
				result = ss[2];

			} catch (RemoteException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = "Y";
			result = "未开启数据传输功能！";
		}
		StringBuffer sb = new StringBuffer();
		if (isSuccess.equals("Y")) {

			sb = new StringBuffer("{success:true,flag:0,result:'" + result
					+ "'}");
		} else {
			sb = new StringBuffer("{success:true,flag:1}");

		}
		setJsonString(sb.toString());
		return SUCCESS;

	}

	public String cancDateAll() {
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen = WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub = null;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		CancelDataDocument faData = CancelDataDocument.Factory.newInstance();
		CancelData cancelData = CancelData.Factory.newInstance();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String dataTypeStatus = this.getRequest()
				.getParameter("dataTypeStatus");
		String dayDate = this.getRequest().getParameter("factdate");
		Long companyId = ContextUtil.getLoginCompanyId();

		String[] vonames = new String[] { "crd_acc_realinte",
				" crd_acc_realfee", "crd_acc_realinte", "crd_acc_realpri",
				"crd_acc_inteplan", "crd_acc_feeplan", "crd_acc_overdue", };

		ZyhtVoFactory zyvo = new ZyVo();
		cancelData.setStringItemArray(vonames);
		cancelData.setZyhtVO(zyvo.createVo(companyId.toString(), dayDate, 1));
		faData.setCancelData(cancelData);
		String isSuccess = "";
		String result = "";
		if (isOpen) {
			try {
				String[] ss = stub.cancelData(faData).getCancelDataResponse()
						.getReturnArray();
				isSuccess = ss[0];
				result = ss[2];

			} catch (RemoteException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = "Y";
			result = "未开启数据传输功能！";
		}
		StringBuffer sb = new StringBuffer();
		if (isSuccess.equals("Y")) {

			sb = new StringBuffer("{success:true,flag:0,result:'" + result
					+ "'}");
		} else {
			sb = new StringBuffer("{success:true,flag:1}");

		}
		setJsonString(sb.toString());
		return SUCCESS;

	}

	public String cancDateAllAccred() {
		String url = WebServicesUrl.getInstances().createUrl();
		boolean isOpen = WebServicesUrl.getInstances().isOpen();
		IZyhtWebServiceStub stub = null;
		try {
			stub = new IZyhtWebServiceStub(url);
		} catch (AxisFault e) {
			e.printStackTrace();
		}
		CancelDataDocument faData = CancelDataDocument.Factory.newInstance();
		CancelData cancelData = CancelData.Factory.newInstance();

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String dataTypeStatus = this.getRequest()
				.getParameter("dataTypeStatus");
		String dayDate = this.getRequest().getParameter("factdate");
		Long companyId = ContextUtil.getLoginCompanyId();

		String[] vonames = new String[] { "crd_acc_inteaccr", "crd_acc_feeaccr"

		};
		ZyhtVoFactory zyvo = new ZyVo();
		cancelData.setStringItemArray(vonames);
		cancelData.setZyhtVO(zyvo.createVo(companyId.toString(), dayDate, 1));
		faData.setCancelData(cancelData);
		String isSuccess = "";
		String result = "";
		if (isOpen) {
			try {
				String[] ss = stub.cancelData(faData).getCancelDataResponse()
						.getReturnArray();
				isSuccess = ss[0];
				result = ss[2];

			} catch (RemoteException e) {

				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			isSuccess = "Y";
			result = "未开启数据传输功能！";
		}
		StringBuffer sb = new StringBuffer();
		if (isSuccess.equals("Y")) {

			sb = new StringBuffer("{success:true,flag:0,result:'" + result
					+ "'}");
		} else {
			sb = new StringBuffer("{success:true,flag:1}");

		}
		setJsonString(sb.toString());
		return SUCCESS;

	}
}
