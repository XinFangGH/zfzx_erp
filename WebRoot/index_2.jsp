<%@page import="com.zhiwei.credit.service.system.AppUserService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.zhiwei.core.util.AppUtil"%>
<%@page import="com.zhiwei.core.util.ContextUtil"%>

<%
	String basePath=request.getContextPath();
	//登录成功后，需要把该用户显示至在线用户
	AppUtil.addOnlineUser(request.getSession().getId(), ContextUtil.getCurrentUser());
	AppUserService appUserService=(AppUserService)AppUtil.getBean("appUserService");
   %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" >
<html>
<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="msthemecompatible" content="no">
		<title><%=AppUtil.getCompanyName()%></title>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-all-notheme.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/resources/css/ext-patch.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Portal.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.UploadDialog.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/admin.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/ux-all.css"/>
		<!--图片列表显示 框 css-->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/data-view.css"/>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/caltask/calendar.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/publicmodel/uploads/css/UploadPanel.css" />
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/icons.css"/><!-- 担保项目图标样式 chencc -->
		

		<!-- load the extjs libary -->
		<script type="text/javascript" src="<%=basePath%>/js/dynamic.jsp"></script>
		
		<!-- Ext 核心JS -->
		<script type="text/javascript" src="<%=basePath%>/ext3/adapter/ext/ext-base-debug.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-all-debug.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-basex.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ext-lang-zh_CN.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/RowExpander.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/TreeFilterX.js"></script>
		<!--使用iframe加载的依赖JS  -->
		<script type="text/javascript" src="<%=basePath%>/ext3/miframe-debug.js"></script>
		
		<!-- ExtJS总计组件 css -->
		<link rel="stylesheet" type="text/css" href="<%=basePath%>/ext3/ux/css/Ext.ux.grid.GridSummary.css"/>
		<!-- ExtJS总计组件 js-->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.GridSummary.js"></script>
		
		<!-- FCK控件JS -->
		<script type="text/javascript" src="<%=basePath%>/js/fckeditor/fckeditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Fckeditor.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/fckeditor/custom/plugins/flvPlayer/swfobject.js"></script>
    	
		<!-- 附件上传对话框 -->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/UploadDialog.js"></script>
		<!-- 附件明细JS,多处用到 -->
		<script type="text/javascript" src="<%=basePath%>/js/system/FileAttachDetail.js"></script>
		<!-- AppUtil.js中引用附件上传的JS -->
        <script type="text/javascript" src="<%=basePath%>/js/system/FileUploadManager.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/system/FileUploadImageDetailForm.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/fileupload/swfobject.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/fileupload/FlexUploadDialog.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/system/GlobalTypeForm.js"></script>
        
		<!-- 分页栏JS HTExt.js中引用 -->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/PageComboResizer.js"></script>
		
		<!-- 提示信息JS -->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Toast.js"></script>
		
		<!-- GirdPanel中引用 -->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/Ext.ux.grid.RowActions.js"></script>
		
		<!-- 需要的 JS-->
		<script type="text/javascript" src="<%=basePath%>/js/App.import.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/XmlTreeLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/TabCloseMenu.js"></script>		
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/DateTimeField.js"></script>
		<!--图片列表显示 框 js-->
		<script type="text/javascript" src="<%=basePath%>/ext3/ux/js/DataView-more.js"></script>

		<!-- 用户选择器,多处用到 -->
		<script type="text/javascript" src="<%=basePath%>/js/selector/UserSelector.js"></script>
		<!-- 部门选择器,多处用到 -->
		<script type="text/javascript" src="<%=basePath%>/js/selector/DepSelector.js"></script>
		<!-- 在线用户选择器,在主页面显示在线用户时用到 -->
		<script type="text/javascript" src="<%=basePath%>/js/selector/OnlineUserSelector.js"></script>
		<!-- 树型下拉选择器,多处用到 -->
		<script type="text/javascript" src="<%=basePath%>/js/core/TreeSelector.js"></script>
		
		<!-- core 工具JS -->
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/HTExt.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ScriptMgr.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/AppUtil.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/TreePanelEditor.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/TreeXmlLoader.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/WebOffice.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/core/ux/TreeCombo.js"></script>
		
		<!-- 日期转换JS,目前AppUtil.js已提供$parseDate()方法 ,后续可将此JS换掉-->
		<script type="text/javascript" src="<%=basePath%>/js/core/date.js"></script>
		
		<!-- 接收站内短消息时所用JS -->
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageWin.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageReplyWin.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/info/MessageDetail.js"></script>
		
		<!-- 启动流程JS,多处用到-->
		<script type="text/javascript" src="<%=basePath%>/js/flow/ProcessRunStart.js"></script>
		
		<!-- 公文流程任务结点JS,多处用到-->
		<script type="text/javascript" src="<%=basePath%>/js/archive/ArchivesNode.js"></script>
		
        <!-- 首页站内搜索JS -->
        <script type="text/javascript" src="<%=basePath%>/js/search/SearchForm.js"></script>
        
        <!-- 抵质押物信息,小额贷款,金融担保等业务流程每个节点都用到,所以第一次就进行加载  add by luqh--> 
        <script type="text/javascript" src="<%=basePath%>/js/global.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/globalDicProperty.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/globalFunction.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/dictionary/dictionaryTree.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/carListWin.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/ourmain/selectSlCompanyMain.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/ourmain/selectSlPersonMain.js" charset="utf-8"></script> 
		<script type="text/javascript" src="<%=basePath%>/js/system/TradeCategorySelecter.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/vehicle/cs_tn_seeVehicle.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/vehicle/cs_tn_vehicle.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/vehicle/cs_tn_vehicle_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/stockownership/cs_tn_seeStockownership.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/stockownership/cs_tn_stockownership.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/stockownership/cs_tn_stockownership_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/product/cs_tn_seeProduct.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/product/cs_tn_product_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/product/cs_tn_product.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/officebuilding/cs_tn_seeOfficebuilding.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/machineinfo/cs_tn_seeMachineinfo.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/industry/cs_tn_seeIndustry.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/industry/cs_tn_industry_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/industry/cs_tn_industry.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/houseground/cs_tn_seeHouseground.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/houseground/cs_tn_houseground_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/houseground/cs_tn_houseground.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/house/cs_tn_seeHouse.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/house/cs_tn_house.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/house/cs_tn_house_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/education/cs_tn_seeEducation.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/education/cs_tn_education_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/education/cs_tn_education.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/dutyperson/cs_tn_seeDutyperson.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/droit/cs_tn_seeDroit.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/droit/cs_tn_droit_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/droit/cs_tn_droit.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/company/cs_tn_seeCompany.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/company/cs_tn_company_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/company/cs_tn_company.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/businessandlive/cs_tn_seeBusinessandlive.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/business/cs_tn_seeBusiness.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/business/cs_tn_business_update.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/business/cs_tn_business.js" charset="utf-8"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/vehicle/cs_tn_vehicle_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/stockownership/cs_tn_stockownership_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/product/cs_tn_product_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/officebuilding/cs_tn_officebuilding_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/machineinfo/cs_tn_machineinfo_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/industry/cs_tn_industry_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/houseground/cs_tn_houseground_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/house/cs_tn_house_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/education/cs_tn_education_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/dutyperson/cs_tn_dutyperson_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/droit/cs_tn_droit_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/company/cs_tn_company_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/businessandlive/cs_tn_businessandlive_see.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/business/cs_tn_business_see.js" charset="utf-8"></script>
		
		
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/addMortgageWindow.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/SmallLoanMortgageView.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/GuaranteeMortgageView.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/relieveMortgageWindow.js" charset="utf-8"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/mortgage/banliMortgageWindow.js" charset="utf-8"></script>
		
		 <!-- credit文件上传start... chencc -->
	    <script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/cs_picViewer.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/cs_showDownload.js"></script>
	
	   	<script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/UploadPanel.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/upload.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/swfupload.js"></script>
	    
	    <script type="text/javascript" src="<%=basePath%>/publicmodel/uploads/js/uploads.js"></script>
	    <!-- credit文件上传end... chencc -->
	    <!-- ht在线编辑start... chencc -->
	    <script type="text/javascript" src="<%=basePath%>/js/core/ntkoffice/NtkOfficePanel.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/archive/OfficeTemplateView.js"></script>
	    <!-- ht在线编辑end... chencc -->
	    <!-- 企业客户 start... chencc -->
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/selectEnterprise.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/public.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/selectEnterGrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/js/Ext.ux.util.js"></script>
        <script type="text/javascript" src="<%=basePath%>/ext3/ux/js/Ext.ux.form.LovCombo.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/selectEnterGrid.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/addEnterprise.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/addFastEnterprise.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/updateEnterprise.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/seeEnterprise.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/ajaxValidation.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/bankInfoList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/managecaseList.js"></script><!--
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/shareequityList.js"></script>
        --><script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/leadteamList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/debtList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/creditorList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/outassureList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/outinvestList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/relatedataList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/prizeList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/companyList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/employeeStructure.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/financeInfo.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/seeEnterpriseCanzhao.js"></script><!--
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/businessRecordRelationEnterprise.js"></script>
       	--><script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationWin.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluationGua.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/enterpriseBusiness/creditRating.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/common/EnterpriseShareequity.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/PersonBusiness.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/PersonMortgage.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/Shareequity.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/LegalPerson.js"></script>
	     <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/personAll.js"></script>
	    <script type="text/javascript" src="<%=basePath%>/js/creditFlow/businessDealings/EnterpriseAll.js"></script>
        <!-- 企业客户end... chencc -->
        <!-- 个人客户 start... chencc -->
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/ajaxValidation.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/selectPersonWin.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/seePersonCanzhao.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/enterprise/relationPersonList.js"></script>
        <script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/personWindowObjList.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/addFamilyPerson.js"></script>
		<!--<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/businessRecordList.js"></script>
		--><script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/listReditregistries.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/thereunderList.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/public.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/common/common.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/relationPerson/relationPersonWin.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/person/PersonView.js"></script>
		<!-- 个人客户 end... chencc -->
	
	<script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGridSorter.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGridColumnResizer.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGridNodeUI.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGridLoader.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGridColumns.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/treegrid/TreeGrid.js"></script>
    <script type="text/javascript" src="<%=basePath%>/js/dictionary/TreeNodeChecked.js" charset="utf-8"></script>
		<!-- 银行联系人start... chencc -->
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/bankrelationperson/BankRelationPersonView.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/bankrelationperson/BankRelationPersonWindow.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/customer/bankrelationperson/bankDaRelationPersonList.js"></script>
		<!-- 银行联系人 end... chencc -->
		
		<!-- 合同相关start... chencc -->
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/contract/SlContractView.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/contract/SeeThirdContractWindow.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/contract/OperateContractWindow.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/contract/OperateThirdContractWindow.js"></script>
		<!-- 合同相关end... chencc -->
		
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/common/ProjectAppUtil.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/common/ProjectStop.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/project/GuaranteeProjectInfoEditPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/project/GuaranteeProjectInfoPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/guarantee/project/ProjectInfoNavigation.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/project/SmallLoanProjectInfoEditPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/project/SmallLoanProjectInfoPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/smallLoan/project/SmallLoanProjectInfoNavigation.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/financeProject/project/FinancingProjectInfoPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/financeProject/project/FinancingProjectInfoEditPanel.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/creditFlow/financeProject/project/FinancingProjectInfoNavigation.js"></script>
		
		<script type="text/javascript" src="<%=basePath%>/js/selector/UserDialog.js"></script>
		<!-- 首页样式加载 -->
    	<link href="<%=basePath%>/css/desktop.css" rel="stylesheet" type="text/css" />	
	    <script type="text/javascript">
	       var __companyName="<%=AppUtil.getCompanyName()%>";
	       var userInfo="<%=appUserService.getCurUserInfo()%>";
		   Ext.onReady(function(){
			   	  var storeTheme=getCookie('theme');
			   	  if(storeTheme==null || storeTheme==''){
				   	  storeTheme='ext-all-css04';
			   	  }
			      Ext.util.CSS.swapStyleSheet("theme", __ctxPath+"/ext3/resources/css/"+storeTheme+".css");  
			      
			      //待办项目定时更新开始...
			      var taskMgr = new Ext.util.TaskRunner();
			      var task1 = {
    					run : function(){
					        //console.log('test');
					        ZW.refreshTaskPanelView();
					    },
					    interval : 600000
				};  
			    taskMgr.start(task1);  
			    //代办项目定时更新结束。。。
		    });
	    </script>
	   
	    <!-- 担保系统 自定义组件，比如CS,add by chencc-->
	    <!-- 登录信息,工具JS -->
	     <script type="text/javascript" src="<%=basePath%>/js/App.Init.Original.js"></script>
		<script type="text/javascript" src="<%=basePath%>/js/App.js"></script>	
		
	     <!-- 个人首页JS -->
	    <script type="text/javascript" src="<%=basePath%>/js/IndexPage.js"></script>
		
	</head>
	<body oncontextmenu="return false" onbeforeunload="return '确认要离开？';">
		<div id="loading">
             <div class="loading-indicator">
                  <img src="<%=basePath%>/images/loading.gif" alt="" width="153" height="16" style="margin-right:8px;" align="absmiddle"/>
                  <div class="clear"></div>
         		    正在加载，请稍候......
             </div>
        </div>
        <div id="loading-mask"></div>
		<div id="app-header" class="x-toolbar">
			<div id="header-left" style="background-image:url('<%=basePath+AppUtil.getCompanyLogo()%>'); width:231px; height:50px;">
				<div style="width:130px; height:20px; margin:0px 0px 0px 90px;"><span style=" font-size:14px; font-weight:bold; font-family:'微软雅黑';"><%=AppUtil.getSystemName()%></span></div>
                <div style="width:130px; height:20px; margin:5px 0px 0px 90px; font-size:12px; font-family:'arial';"><span style=" font-size:12px; font-family:'arial';"><%=AppUtil.getSystemVersion()%></span></div>
			</div>
			<div id="header-main">
				<div id="header-info" >
				<div  style="float:left;">
				<!-- 	<a href="#" onclick="App.MyDesktopClick()" style="text-indent:25px;text-indent:0px!ie;padding-left: 28px;height:0px!ie; color:#000000; text-decoration: none" class="menu-index-company" >公司主页</a> -->
					<a href="#" onclick="App.clickTopTab('AppHome')" style="text-indent:25px; padding-left: 20px;color:#000000;text-decoration: none" class="menu-desktop" title="个人桌面" >&nbsp;</a>
					<a href="#" onclick="App.clickTopTab('ActivityTaskView')" style="text-indent:25px; padding-left: 20px;color:#000000;text-decoration: none" class="index-menu-flowWait" title="项目待办事项" >&nbsp;</a>
					<a href="#" onclick="App.clickTopTab('PersonalMailBoxView')" style="text-indent:25px; padding-left: 20px; color:#000000;text-decoration: none" class="menu-mail_box" title="邮件" >&nbsp;</a>
					<a href="#" onclick="ZW.setcookieTab('Mod_credit')" style="text-indent:25px; padding-left: 20px;color:#000000;text-decoration: none" class="menu-oa" title="协同办公" >&nbsp;</a>
					<a href="#" onclick="App.Topcollapse(this)" style="text-indent:17px; padding-left: 25px; color:#000000;text-decoration: none" class="index-btn-expand" title="收起头部" >&nbsp;</a>
					&nbsp;
					欢迎您，<security:authentication property="principal.fullname"/>，[<a href="#" onclick = "App.Logout()">注销</a>]
					</div>
					<div style="float:left;padding-left:10px;margin-top:-5">
						<div id="top-subject-change">
						</div>
					</div>
				<div id="header-nav" style="margin-top:4px!ie"></div>
			</div>
			<div id="header-right">
				<div id="setting">
				  <!--  <a href="#" target="blank">帮助</a>
					<%//if(ContextUtil.getCurrentUser().isSuperAdmin()){ %>
						|&nbsp;<a href="#" onclick="App.clickTopTab('SysConfigView')">设置</a>
					<%//} %>
					|&nbsp;<a href="/jforum" target="blank">论坛</a> 
					技术支持 <a href=http://www.zxzbol.com target="_blank">升升投</a>-->
				</div>
				<div id="searchFormDisplay" style="width:260px;height:30px;float:right;padding-top:8px;">&nbsp;</div>
			</div>
		</div>
		
	</body>
</html>