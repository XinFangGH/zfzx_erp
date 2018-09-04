/**
 * 绯荤粺瀵煎叆鐨勬ā鍧梛s锛屼富瑕佺敤浜庡悗鍔犺浇鏂瑰紡锛岄渶瑕佷娇鐢ㄦ煇浜沯s鏃讹紝闇�鍦ㄦ鎸囧畾鍔犺浇鍝竴浜涖�
 */
Ext.ns("App");
App.importJs = {
	VPunishDetail : [__ctxPath + '/js/creditFlow/finance/VPunishDetail.js'],
	SystemBaseParaView:[
		__ctxPath + '/js/system/SystemBaseParaView.js',
		__ctxPath + '/js/system/UploadLogoFile.js',
		__ctxPath + '/js/system/SMSTemplate.js',
		__ctxPath + '/js/system/SMSTest.js',
		__ctxPath + '/js/system/EmailTest.js'
	],
	SystemEvementView:[
		__ctxPath + '/js/system/SystemEvementView.js'
	],
	ChangeMessage:[
		__ctxPath + '/js/system/ChangeMessage.js'
	],
	MessageAccountViewP2P:[
		__ctxPath + '/js/system/UpdateMessInfo.js',
		__ctxPath + '/js/system/SMSTest.js',
		__ctxPath + '/js/system/MessageAccountViewP2P.js'
	],
	MessageAccountView:[
		__ctxPath + '/js/system/ChangeMessage.js',
		__ctxPath + '/js/system/SMSTemplate.js',
		__ctxPath + '/js/system/SMSTest.js',
		__ctxPath + '/js/system/MessageAccountView.js'
	],
	SystemSmsTemplateView:[
		__ctxPath + '/js/system/SystemSmsTemplateView.js',
		__ctxPath + '/js/system/SystemUpdateSmsTemplateView.js',
		__ctxPath + '/js/system/SystemSendSmsTemplateView.js'
	],
	SystemP2PSmsTemplateView:[
		__ctxPath + '/js/system/SystemP2PSmsTemplateView.js',
		__ctxPath + '/js/system/SystemP2PUpdateSmsTemplateView.js',
		__ctxPath + '/js/system/SystemP2PSendSmsTemplateView.js'
	],

	P2PSystemBaseParaView:[
		__ctxPath + '/js/system/P2PSystemBaseParaView.js',
		__ctxPath + '/js/system/UploadLogoFile.js',
		__ctxPath + '/js/system/SMSTemplate.js',
		__ctxPath + '/js/system/SMSTest.js',
		__ctxPath + '/js/system/EmailTest.js'
	],

	
	BidPlanDeskTop:[
		__ctxPath + '/js/team/BidPlanDeskTop.js',
		__ctxPath +'/highchart/ownChart/BidExceptionMap.js',
		__ctxPath +'/highchart/ownChart/BidPlanCharts.js',
		__ctxPath +'/highchart/ownChart/BidSaleStatistics.js',
		__ctxPath +'/highchart/ownChart/BidTypeProportion.js',
		__ctxPath +'/highchart/ownChart/monthTypeSalesTrends.js'
	],

	PersonalDeskNew : [
		__ctxPath + '/js/team/PersonalDeskNew.js',
		__ctxPath +'/highchart/ownChart/wealthSalesTrends.js',
		__ctxPath +'/highchart/ownChart/monthTypeSalesTrends.js'
	],

	PersonalTeamDesktop : [
		__ctxPath + '/js/team/PersonalTeamDesktop.js',
		__ctxPath +'/highchart/ownChart/loanMoneyYearTrends.js',
		__ctxPath +'/highchart/ownChart/customerOverdueTrends.js'
	],
    PersonalCommonDesktop : [
		__ctxPath + '/js/team/PersonalCommonDesktop.js',
		__ctxPath +'/highchart/ownChart/loanMoneyYearTrends.js',
		__ctxPath +'/highchart/ownChart/riskControlTrends.js',
		__ctxPath +'/highchart/ownChart/loanOverdueTrends.js'
	],
    PersonalFinancialDesktop : [
		__ctxPath + '/js/team/PersonalFinancialDesktop.js',
		__ctxPath +'/highchart/ownChart/loanIncomePayTrends.js',
		__ctxPath +'/highchart/ownChart/loanMoneyYearTrends.js',
		__ctxPath +'/highchart/ownChart/loanOverdueTrends.js'
	],
	

	ManageplanDesktop : [
		__ctxPath + '/js/team/ManageplanDesktop.js',
		__ctxPath +'/highchart/ownChart/planAssignment.js',
		__ctxPath +'/highchart/ownChart/planMarket.js',
		__ctxPath +'/highchart/ownChart/planProportion.js',
		__ctxPath +'/highchart/ownChart/planFundAnalyze.js'
	],

	DeskTopForm:[
		__ctxPath +'/js/system/DeskTopForm.js',
		__ctxPath + '/ext3/ux/CheckTreePanel.js',
		__ctxPath +'/js/system/DeskFeature.js'
	],
	TaskProxyView:[
		__ctxPath+'/js/flow/TaskProxyView.js',
		__ctxPath+'/js/flow/TaskProxyForm.js'
	],
	
	BusPayAccountView:[
		__ctxPath +'/js/p2p/BusPayAccountView.js',
		__ctxPath +'/js/p2p/BusPayAccountForm.js'
	],
	
	
	EarlyOutListTab:[
		__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyOutListTab.js',
		__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyOutList.js',
		__ctxPath + '/js/creditFlow/financingAgency/manageMoney/EarlyOutDetail.js'
	],
	MenuView : [__ctxPath + '/js/system/MenuView.js',
			__ctxPath + '/js/system/MenuForm.js',
			__ctxPath + '/js/system/MenuFunctionForm.js',
			__ctxPath + '/js/system/MenuUrlForm.js',
			__ctxPath + '/js/system/IconSelector.js'],
	AppRoleView : [__ctxPath + '/js/system/AppRoleView.js',
			__ctxPath +'/js/system/DeskRoleForm.js',
			__ctxPath +'/js/system/DeskFeature.js',
			__ctxPath + '/ext3/ux/CheckTreePanel.js',
			__ctxPath + '/js/system/RoleGrantRightView.js',
			__ctxPath + '/js/system/AppRoleFormOnly.js',
			__ctxPath + '/js/creditFlow/finance/SlAccessView.js'],
	PersonalDocumentView : [__ctxPath + '/js/document/PersonalDocumentView.js',
			__ctxPath + '/js/document/DocumentView.js',
			__ctxPath + '/js/document/DocumentForm.js',
			__ctxPath + '/js/document/DocumentSharedForm.js',
			__ctxPath + '/js/document/DocFolderForm.js', 
			__ctxPath + '/js/selector/RoleSelector.js'],
	DocumentSharedView : [__ctxPath + '/js/document/DocumentSharedView.js',
			__ctxPath + '/js/document/DocumentSharedDetail.js'],
	DocFolderSharedView : [
			__ctxPath + '/js/document/FindPublicDocumentView.js',
			__ctxPath + '/js/document/DocFolderView.js',
			__ctxPath + '/js/document/DocFolderForm.js',
			__ctxPath + '/js/document/DocFolderSharedView.js',
			__ctxPath + '/js/document/DocFolderSharedForm.js',
			__ctxPath + '/js/document/DocPrivilegeForm.js',
			__ctxPath + '/js/document/DocPrivilegeView.js',
			__ctxPath + '/ext3/ux/CheckColumn.js'],
	FindPublicDocumentView : [
			__ctxPath + '/js/document/FindPublicDocumentView.js',
			__ctxPath + '/js/document/PublicDocumentView.js',
			__ctxPath + '/js/document/PublicDocumentDetail.js',
			__ctxPath + '/js/document/NewPublicDocumentForm.js',
			__ctxPath + '/js/document/DocFolderSelector.js'],
	NewPublicDocumentForm : [
			__ctxPath + '/js/document/NewPublicDocumentForm.js',
			__ctxPath + '/js/document/DocFolderSelector.js'],
	DocFolderMoveForm : [__ctxPath + '/js/document/DocFolderMoveForm.js',
			__ctxPath + '/js/document/PersonalDocFolderSelector.js'],
	NoticeView : [__ctxPath + '/js/info/NoticeView.js',
			__ctxPath + '/js/info/NoticeForm.js',
			__ctxPath + '/js/selector/SectionSelector.js'],
	ReportTemplateView : [__ctxPath + '/js/system/ReportTemplateView.js',
			__ctxPath + '/js/system/ReportTemplateForm.js',
			__ctxPath + '/js/system/ReportParamForm.js',
			__ctxPath + '/js/system/ReportParamView.js',
			__ctxPath + '/js/system/ReportTemplatePreview.js',
			__ctxPath + '/ext3/ux/ext-basex.js'],
	MessageView : [__ctxPath + '/js/info/MessageView.js',
			__ctxPath + '/js/info/MessageForm.js',
			__ctxPath + '/js/info/MessageWin.js'],
	MessageManageView : [__ctxPath + '/js/info/MessageManageView.js',
			__ctxPath + '/js/info/MessageForm.js'],
	PhoneBookView : [__ctxPath + '/js/communicate/PhoneBookView.js',
			__ctxPath + '/js/communicate/PhoneGroupForm.js',
			__ctxPath + '/js/communicate/PhoneBookForm.js'],
	AppUserView : [__ctxPath + '/js/system/AppUserView.js',
			__ctxPath + '/ext3/ux/ItemSelector.js',
			__ctxPath + '/ext3/ux/MultiSelect.js',
			__ctxPath + '/js/system/DynamicPwdForm.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js'],
	ProfileForm : [__ctxPath + '/js/system/ProfileForm.js',
			__ctxPath + '/js/system/ResetPasswordForm.js'],
	NewsView : [__ctxPath + '/js/info/NewsView.js',
			__ctxPath + '/js/info/NewsForm.js',
			__ctxPath + '/js/selector/SectionSelector.js'],
	CompanyView : [__ctxPath + '/js/system/CompanyView.js'],
	FileAttachView : [__ctxPath + '/js/system/FileAttachView.js',
			__ctxPath + '/js/system/FileAttachDetail.js'],
	DiaryView : [__ctxPath + '/js/system/DiaryView.js',
			__ctxPath + '/js/system/DiaryForm.js'],
	MySubUserDiaryView : [__ctxPath + '/js/system/MySubUserDiaryView.js',
			__ctxPath + '/js/system/DiaryDetail.js'],
	PersonalMailBoxView : [__ctxPath + '/ext3/ux/RowExpander.js',
			__ctxPath + '/js/communicate/PersonalMailBoxView.js',
			__ctxPath + '/js/communicate/MailView.js',
			__ctxPath + '/js/communicate/MailForm.js',
			__ctxPath + '/js/communicate/MailFolderForm.js'],
	MailForm : [__ctxPath + '/js/communicate/MailForm.js'],
	PersonalPhoneBookView : [
			__ctxPath + '/js/communicate/PersonalPhoneBookView.js',
			__ctxPath + '/js/communicate/PhoneBookView.js',
			__ctxPath + '/js/communicate/PhoneGroupForm.js',
			__ctxPath + '/js/communicate/PhoneBookForm.js'],
	SharedPhoneBookView : [
			__ctxPath + '/js/communicate/SharedPhoneBookView.js',
			__ctxPath + '/js/communicate/SharedPhoneBookWin.js'],
	FlowManagerView : [__ctxPath + '/js/flow/ProTypeForm.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/flow/ProDefRightsForm.js',
			__ctxPath + '/js/flow/ProDefinitionForm.js',
			__ctxPath + '/js/flow/ProDefinitionView.js',
			__ctxPath + '/js/flow/FlowManagerView.js',
			__ctxPath + '/js/flow/ProDefinitionDetail.js',
			__ctxPath + '/js/flow/ProDefinitionSetting.js',
			__ctxPath + '/js/flow/MyTaskView.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/FormDesignWindow.js',
			__ctxPath + '/js/flow/FormEditorWindow.js',
			__ctxPath + '/js/flowDesign/FlowDesignerWindow.js',
			__ctxPath + '/js/selector/FormDefSelector.js',
			__ctxPath + '/js/flow/FormDefForm.js',
			__ctxPath + '/js/flow/FormDefDetailForm.js',
			__ctxPath + '/js/selector/JobSelector.js',
			__ctxPath + '/js/flow/FieldRightsForm.js',
			__ctxPath + '/js/flow/TaskSignForm.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/selector/RelativeJobSelector.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/ReJobDialog.js',
			__ctxPath + '/js/selector/DepSelector.js'],
	AssignFlowView : [__ctxPath + '/js/flow/CreateBusinessFlowView.js',
			__ctxPath + '/js/flow/ProTypeForm.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/flow/ProDefRightsForm.js',
			__ctxPath + '/js/flow/ProDefinitionForm.js',
			__ctxPath + '/js/flow/AssignFlowProDefinitionView.js',
			__ctxPath + '/js/flow/AssignFlowView.js',
			__ctxPath + '/js/flow/ProDefinitionDetail.js',
			__ctxPath + '/js/flow/ProDefinitionSetting.js',
			__ctxPath + '/js/flow/MyTaskView.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/FormDesignWindow.js',
			__ctxPath + '/js/flow/FormEditorWindow.js',
			__ctxPath + '/js/flowDesign/FlowDesignerWindow.js',
			__ctxPath + '/js/selector/FormDefSelector.js',
			__ctxPath + '/js/flow/FormDefForm.js',
			__ctxPath + '/js/flow/FormDefDetailForm.js',
			__ctxPath + '/js/selector/JobSelector.js',
			__ctxPath + '/js/flow/FieldRightsForm.js',
			__ctxPath + '/js/flow/TaskSignForm.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/selector/RelativeJobSelector.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/ReJobDialog.js',
			__ctxPath + '/js/selector/DepSelector.js'],
	NormalFlowMnagerView : [
			__ctxPath + '/js/flow/UpdateBranchCompanyFlowView.js',
			__ctxPath + '/js/flow/CreateBusinessFlowView.js',
			__ctxPath + '/js/flow/ProTypeForm.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/flow/ProDefRightsForm.js',
			__ctxPath + '/js/flow/ProDefinitionForm.js',
			// __ctxPath+'/js/flow/ProDefinitionView.js',
			__ctxPath + '/js/flow/NormalFlowMnagerProDefinitionView.js',
			__ctxPath + '/js/flow/NormalFlowMnagerView.js',
			__ctxPath + '/js/flow/ProDefinitionDetail.js',
			__ctxPath + '/js/flow/ProDefinitionSetting.js',
			__ctxPath + '/js/flow/MyTaskView.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/FormDesignWindow.js',
			__ctxPath + '/js/flow/FormEditorWindow.js',
			__ctxPath + '/js/flowDesign/FlowDesignerWindow.js',
			__ctxPath + '/js/selector/FormDefSelector.js',
			__ctxPath + '/js/flow/FormDefForm.js',
			__ctxPath + '/js/flow/FormDefDetailForm.js',
			__ctxPath + '/js/selector/JobSelector.js',
			__ctxPath + '/js/flow/FieldRightsForm.js',
			__ctxPath + '/js/flow/TaskSignForm.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/selector/RelativeJobSelector.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/ReJobDialog.js',
			__ctxPath + '/js/selector/DepSelector.js'],
	BranchFlowMnagerView : [
			__ctxPath + '/js/flow/CreateBusinessFlowView.js',
			__ctxPath + '/js/flow/ProTypeForm.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/flow/ProDefRightsForm.js',
			__ctxPath + '/js/flow/ProDefinitionForm.js',
			// __ctxPath+'/js/flow/ProDefinitionView.js',
			__ctxPath + '/js/flow/BranchFlowMnagerProDefinitionView.js',
			__ctxPath + '/js/flow/BranchFlowMnagerView.js',
			__ctxPath + '/js/flow/ProDefinitionDetail.js',
			__ctxPath + '/js/flow/ProDefinitionSetting.js',
			__ctxPath + '/js/flow/MyTaskView.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/FormDesignWindow.js',
			__ctxPath + '/js/flow/FormEditorWindow.js',
			__ctxPath + '/js/flowDesign/FlowDesignerWindow.js',
			__ctxPath + '/js/selector/FormDefSelector.js',
			__ctxPath + '/js/flow/FormDefForm.js',
			__ctxPath + '/js/flow/FormDefDetailForm.js',
			__ctxPath + '/js/selector/JobSelector.js',
			__ctxPath + '/js/flow/FieldRightsForm.js',
			__ctxPath + '/js/flow/TaskSignForm.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/selector/RelativeJobSelector.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/ReJobDialog.js',
			__ctxPath + '/js/selector/DepSelector.js'],
	FlowManagerViewForCustomer : [
			__ctxPath + '/js/flow/FlowManagerViewForCustomer.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/selector/RelativeJobSelector.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/ReJobDialog.js',
			__ctxPath + '/js/selector/DepSelector.js',
			__ctxPath + '/js/flow/TaskSignForm.js',
			__ctxPath + '/js/flow/ProDefinitionViewForCustomer.js',
			__ctxPath + '/js/flow/ProDefinitionSettingForCustomer.js'],
	
	NewProcess : [__ctxPath + '/js/flow/NewProcess.js',
			__ctxPath + '/js/flow/ProDefinitionDetail.js',
			__ctxPath + '/js/flow/ProDefinitionView.js'
	// __ctxPath+'/js/flow/ProcessRunStart.js'
	],
	ProcessRunView : [__ctxPath + '/js/flow/ProcessRunView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'
	// __ctxPath+'/js/flow/ProcessRunStart.js'
	],
	ProcessRunView_p : [__ctxPath + '/js/flow/ProcessRunView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'
	// __ctxPath+'/js/flow/ProcessRunStart.js'
	],
	ProcessHistoryView : [__ctxPath + '/js/flow/ProcessHistoryView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'],
	MyTaskView : [__ctxPath + '/js/flow/MyTaskView.js',
			__ctxPath + '/js/flow/ChangeTaskView.js',
			__ctxPath + '/js/flow/ProcessNextForm.js'],
	ProcessRunFinishView : [__ctxPath + '/js/flow/ProcessRunFinishView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'],

	BookManageView : [__ctxPath + '/js/admin/BookManageView.js',
			__ctxPath + '/js/admin/BookView.js',
			__ctxPath + '/js/admin/BookForm.js',
			__ctxPath + '/js/admin/BookTypeForm.js',
			__ctxPath + '/js/admin/BookBorrowForm.js',
			__ctxPath + '/js/admin/BookAmountForm.js',
			__ctxPath + '/js/selector/BookSelector.js'],
	BookTypeView : [__ctxPath + '/js/admin/BookTypeView.js',
			__ctxPath + '/js/admin/BookTypeForm.js'],
	BookBorrowView : [__ctxPath + '/js/admin/BookBorrowView.js',
			__ctxPath + '/js/admin/BookBorrowForm.js',
			__ctxPath + '/js/admin/BookReturnForm.js',
			__ctxPath + '/js/selector/BookSelector.js'],
	BookReturnView : [__ctxPath + '/js/admin/BookReturnView.js',
			__ctxPath + '/js/admin/BookReturnForm.js',
			__ctxPath + '/js/selector/BookSelector.js'],
	OfficeGoodsManageView : [__ctxPath + '/js/admin/OfficeGoodsManageView.js',
			__ctxPath + '/js/admin/OfficeGoodsTypeForm.js',
			__ctxPath + '/js/admin/OfficeGoodsView.js',
			__ctxPath + '/js/admin/OfficeGoodsForm.js'],
	InStockView : [__ctxPath + '/js/admin/InStockView.js',
			__ctxPath + '/js/admin/InStockForm.js',
			__ctxPath + '/js/selector/GoodsSelector.js'],
	GoodsApplyView : [__ctxPath + '/js/admin/GoodsApplyView.js',
			__ctxPath + '/js/admin/GoodsApplyForm.js',
			__ctxPath + '/js/admin/GoodsCheckForm.js',
			__ctxPath + '/js/selector/GoodsSelector.js'],
	CarView : [__ctxPath + '/js/admin/CarView.js',
			__ctxPath + '/js/admin/CarForm.js'],
	CartRepairView : [__ctxPath + '/js/admin/CartRepairView.js',
			__ctxPath + '/js/admin/CartRepairForm.js',
			__ctxPath + '/js/selector/CarSelector.js'],
	CarApplyView : [__ctxPath + '/js/admin/CarApplyView.js',
			__ctxPath + '/js/admin/CarApplyForm.js',
			__ctxPath + '/js/admin/CarCheckForm.js',
			__ctxPath + '/js/selector/CarSelector.js'],
	AppointmentView : [__ctxPath + '/js/task/AppointmentView.js',
			__ctxPath + '/js/task/AppointmentForm.js'],
	CalendarPlanView : [__ctxPath + '/js/task/CalendarPlanView.js',
			__ctxPath + '/js/task/CalendarPlanForm.js',
			__ctxPath + '/js/task/CalendarPlanFinishForm.js'],
	MyPlanTaskView : [__ctxPath + '/js/task/CalendarPlanView.js',
			__ctxPath + '/js/task/CalendarPlanForm.js',
			__ctxPath + '/js/task/CalendarPlanFinishForm.js',
			__ctxPath + '/ext3/ux/caltask/e2cs_zh_CN.js',
			__ctxPath + '/ext3/ux/caltask/calendar.gzjs',
			__ctxPath + '/ext3/ux/caltask/scheduler.gzjs',
			__ctxPath + '/ext3/ux/caltask/monthview.gzjs',
			__ctxPath + '/ext3/ux/caltask/weekview.gzjs',
			__ctxPath + '/ext3/ux/caltask/dayview.gzjs',
			__ctxPath + '/ext3/ux/caltask/task.gzjs',
			__ctxPath + '/js/task/MyPlanTaskView.gzjs',
			__ctxPath + '/js/task/CalendarPlanDetailView.js'],
	PlanTypeView : [__ctxPath + '/js/task/PlanTypeView.js',
			__ctxPath + '/js/task/PlanTypeForm.js'],
	WorkPlanView : [__ctxPath + '/js/task/WorkPlanView.js',
			__ctxPath + '/js/task/NewWorkPlanForm.js',
			__ctxPath + '/ext3/ux/Ext.ux.IconCombob.js'

	],
	PersonalWorkPlanView : [__ctxPath + '/js/task/PersonalWorkPlanView.js',
			__ctxPath + '/js/task/PersonalWorkPlanForm.js',
			__ctxPath + '/js/task/WorkPlanDetail.js',
			__ctxPath + '/js/task/PersonalPlanTypeForm.js',
			__ctxPath + '/ext3/ux/Ext.ux.IconCombob.js'],
	NewWorkPlanForm : [__ctxPath + '/js/task/NewWorkPlanForm.js',
			__ctxPath + '/ext3/ux/Ext.ux.IconCombob.js'],
	DepWorkPlanView : [__ctxPath + '/js/task/DepWorkPlanView.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/task/DepWorkPlanForm.js',
			__ctxPath + '/js/task/WorkPlanDetail.js',
			__ctxPath + '/ext3/ux/Ext.ux.IconCombob.js'],
	CustomerView : [__ctxPath + '/js/customer/CustomerView.js',
			__ctxPath + '/js/customer/CustomerForm.js',
			__ctxPath + '/js/customer/CusLinkmanForm.js',
			__ctxPath + '/js/customer/SendMailForm.js',
			__ctxPath + '/js/selector/CustomerSelector.js'],
	CusLinkmanView : [__ctxPath + '/js/customer/CusLinkmanView.js',
			__ctxPath + '/js/customer/CusLinkmanForm.js',
			__ctxPath + '/js/selector/CustomerSelector.js'],
	FixedAssetsManageView : [__ctxPath + '/js/admin/FixedAssetsManageView.js',
			__ctxPath + '/js/admin/FixedAssetsView.js',
			__ctxPath + '/js/admin/FixedAssetsForm.js',
			__ctxPath + '/js/admin/AssetsTypeForm.js',
			__ctxPath + '/js/admin/DepreWin.js',
			__ctxPath + '/js/admin/WorkGrossWin.js'],
	DepreTypeView : [__ctxPath + '/js/admin/DepreTypeForm.js',
			__ctxPath + '/js/admin/DepreTypeView.js'],
	DepreRecordView : [__ctxPath + '/js/admin/DepreRecordForm.js',
			__ctxPath + '/js/admin/DepreRecordView.js'],
	CusConnectionView : [__ctxPath + '/js/customer/CusConnectionView.js',
			__ctxPath + '/js/customer/CusConnectionForm.js',
			__ctxPath + '/js/selector/CustomerSelector.js'],
	ProjectView : [__ctxPath + '/js/customer/ProjectView.js',
			__ctxPath + '/js/customer/ProjectForm.js',
			__ctxPath + '/js/customer/ContractForm.js',
			__ctxPath + '/js/customer/ContractConfigView.js',
			__ctxPath + '/ext3/ux/RowEditor.js',
			__ctxPath + '/js/selector/CustomerSelector.js',
			__ctxPath + '/js/selector/ProjectSelector.js'],
	ContractView : [__ctxPath + '/js/customer/ContractView.js',
			__ctxPath + '/js/customer/ContractForm.js',
			__ctxPath + '/js/customer/ContractConfigView.js',
			__ctxPath + '/ext3/ux/RowEditor.js',
			__ctxPath + '/js/selector/ProjectSelector.js'],
	ProductView : [__ctxPath + '/js/customer/ProductView.js',
			__ctxPath + '/js/customer/ProductForm.js',
			__ctxPath + '/js/selector/ProviderSelector.js'],
	ProviderView : [__ctxPath + '/js/customer/ProviderView.js',
			__ctxPath + '/js/customer/ProviderForm.js',
			__ctxPath + '/js/customer/SendMailForm.js'],
	// -------------personal moduels------------------------
	HolidayRecordView : [__ctxPath + '/js/personal/HolidayRecordView.js',
			__ctxPath + '/js/personal/HolidayRecordForm.js'],
	DutySectionView : [__ctxPath + '/js/personal/DutySectionView.js',
			__ctxPath + '/js/personal/DutySectionForm.js'],
	DutySystemView : [__ctxPath + '/js/personal/DutySystemView.js',
			__ctxPath + '/js/personal/DutySystemForm.js',
			__ctxPath + '/js/selector/DutySectionSelector.js'],
	SignInOffView : [__ctxPath + '/js/personal/SignInOffView.js'],
	DutyRegisterPersonView : [__ctxPath
			+ '/js/personal/DutyRegisterPersonView.js'],
	DutyRegisterView : [__ctxPath + '/js/personal/DutyRegisterView.js',
			__ctxPath + '/js/personal/DutyRegisterForm.js'],
	ErrandsRegisterView : [__ctxPath + '/js/personal/ErrandsRegisterView.js',
			__ctxPath + '/js/personal/ErrandsRegisterDetail.js',
			__ctxPath + '/js/personal/ErrandsRegisterForm.js',
			__ctxPath + '/js/flow/ProcessNextForm.js'],
	ErrandsRegisterOutView : [
			__ctxPath + '/js/personal/ErrandsRegisterOutView.js',
			__ctxPath + '/js/personal/ErrandsRegisterOutForm.js'],
	SysConfigView : [__ctxPath + '/js/system/SysConfigView.js',
			__ctxPath + '/js/communicate/SmsMobileForm.js'],
	// -------------personal moduels------------------------
	// -------------Home Message Detail moduels-------------
	NoticeDetail : [__ctxPath + '/js/info/NoticeDetail.js'],
	NewsDetail : [__ctxPath + '/js/info/NewsDetail.js'],
	PublicDocumentDetail : [__ctxPath + '/js/document/PublicDocumentDetail.js'],
	MailDetail : [__ctxPath + '/js/communicate/MailDetail.js',
			__ctxPath + '/js/communicate/MailForm.js'],
	CalendarPlanDetail : [__ctxPath + '/js/task/CalendarPlanDetail.js'],
	AppointmentDetail : [__ctxPath + '/js/task/AppointmentDetail.js'],
	// -------------Home Message Detail moduels-------------
	// -------------Search moduels--------------------------
	SearchNews : [__ctxPath + '/js/search/SearchNews.js',
			__ctxPath + '/js/info/NewsDetail.js'],
	SearchMail : [__ctxPath + '/ext3/ux/RowExpander.js',
			__ctxPath + '/js/search/SearchMail.js',
			__ctxPath + '/js/communicate/MailView.js',
			__ctxPath + '/js/communicate/MailForm.js'],
	SearchNotice : [__ctxPath + '/js/search/SearchNotice.js'],
	SearchDocument : [__ctxPath + '/js/search/SearchDocument.js',
			__ctxPath + '/js/document/PublicDocumentDetail.js'],
	HireIssueView : [__ctxPath + '/js/hrm/HireIssueView.js',
			__ctxPath + '/js/hrm/HireIssueForm.js',
			__ctxPath + '/js/hrm/HireIssueCheckWin.js'],
	ResumeView : [__ctxPath + '/js/hrm/ResumeView.js',
			__ctxPath + '/js/hrm/ResumeForm.js'],
	// -------------Search moduels--------------------------
	NewsCommentView : [__ctxPath + '/js/info/NewsCommentView.js',
			__ctxPath + '/ext3/ux/RowExpander.js'],
	DictionaryView : [__ctxPath + '/js/system/DictionaryView.js',
			__ctxPath + '/js/system/DictionaryForm.js'],
	SalaryItemView : [__ctxPath + '/js/hrm/SalaryItemForm.js',
			__ctxPath + '/js/hrm/SalaryItemView.js'],
	StandSalaryForm : [__ctxPath + '/js/hrm/StandSalaryForm.js',
			__ctxPath + '/js/hrm/StandSalaryItemView.js',
			__ctxPath + '/js/selector/SalaryItemSelector.js'],
	StandSalaryView : [__ctxPath + '/js/hrm/StandSalaryView.js',
			__ctxPath + '/js/hrm/StandSalaryForm.js',
			__ctxPath + '/js/hrm/StandSalaryItemView.js',
			__ctxPath + '/js/hrm/CheckStandSalaryForm.js',
			__ctxPath + '/js/hrm/CheckStandSalaryItemView.js',
			__ctxPath + '/js/selector/SalaryItemSelector.js'],
	JobChangeForm : [__ctxPath + '/js/hrm/JobChangeForm.js',
			__ctxPath + '/js/selector/EmpProfileSelector.js',
			__ctxPath + '/js/selector/PositionDialog.js'],
	JobChangeView : [__ctxPath + '/js/hrm/JobChangeView.js',
			__ctxPath + '/js/hrm/JobChangeForm.js',
			__ctxPath + '/js/selector/EmpProfileSelector.js',
			__ctxPath + '/js/hrm/CheckJobChangeWin.js',
			__ctxPath + '/js/selector/PositionDialog.js'],
	EmpProfileForm : [__ctxPath + '/js/hrm/EmpProfileForm.js',
			__ctxPath + '/js/selector/PositionDialog.js'],
	EmpProfileView : [__ctxPath + '/js/hrm/EmpProfileView.js',
			__ctxPath + '/js/hrm/EmpProfileForm.js',
			__ctxPath + '/js/hrm/CheckEmpProfileForm.js',
			__ctxPath + '/js/hrm/RecoveryProfileWin.js'],
	SalaryPayoffForm : [__ctxPath + '/js/hrm/SalaryPayoffForm.js',
			__ctxPath + '/js/selector/EmpProfileSelector.js'],
	SalaryPayoffView : [__ctxPath + '/js/hrm/SalaryPayoffForm.js',
			__ctxPath + '/js/selector/EmpProfileSelector.js',
			__ctxPath + '/js/hrm/CheckSalaryPayoffForm.js',
			__ctxPath + '/js/hrm/SalaryPayoffView.js'],
	PersonalSalaryView : [__ctxPath + '/ext3/ux/RowExpander.js',
			__ctxPath + '/js/personal/PersonalSalaryView.js'],
	SystemLogView : [__ctxPath + '/js/system/SystemLogView.js'],
	MyProcessRunView : [__ctxPath + '/js/flow/MyProcessRunView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'],
	MyRunningTaskView : [__ctxPath + '/js/flow/MyRunningTaskView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'],
	MyRunningTaskView_p : [__ctxPath + '/js/flow/MyRunningTaskView.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js'],
	PersonalTipsView : [__ctxPath + '/js/info/PersonalTipsView.js'],

	OutMailUserSetingForm : [__ctxPath
			+ '/js/communicate/OutMailUserSetingForm.js'],
	OutMailBoxView : [__ctxPath + '/ext3/ux/RowExpander.js',
			__ctxPath + '/js/communicate/OutMailBoxView.js',
			__ctxPath + '/js/communicate/OutMailView.js',
			__ctxPath + '/js/communicate/OutMailForm.js',
			__ctxPath + '/js/communicate/OutMailFolderForm.js',
			__ctxPath + '/js/selector/EMailSelector.js'],
	OutMailForm : [__ctxPath + '/js/communicate/OutMailForm.js',
			__ctxPath + '/js/selector/EMailSelector.js'],
	SmsMobileView : [__ctxPath + '/js/communicate/SmsMobileView.js',
			__ctxPath + '/js/communicate/SmsMobileForm.js'],
	GlobalTypeManager : [__ctxPath + '/js/system/GlobalTypeManager.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/system/TypeKeyForm.js'],
	PrivateDocumentView : [__ctxPath + '/js/document/PrivateDocumentView.js',
			__ctxPath + '/js/document/DocumentForm.js',
			__ctxPath + '/js/document/DocFolderForm.js',
			__ctxPath + '/js/document/DocumentSharedForm.js',
			__ctxPath + '/js/document/FileDetailShowWin.js',
			__ctxPath + '/js/selector/RoleSelector.js'],
	KnowledgeManageView : [__ctxPath + '/js/document/KnowledgeManageView.js',
			__ctxPath + '/js/document/KnowledgeRightsForm.js',
			__ctxPath + '/js/document/KnowledgeForm.js',
			__ctxPath + '/js/document/DocFolderForm.js',
			__ctxPath + '/js/document/DocFolderSelector.js',
			__ctxPath + '/js/document/FileDetailShowWin.js',
			__ctxPath + '/js/document/DocumentDetailWin.js',
			__ctxPath + '/js/document/KnowledgePrivilegeWin.js',
			__ctxPath + '/js/document/DocFolderSharedForm.js',
			__ctxPath + '/ext3/ux/CheckColumn.js',
			__ctxPath + '/js/selector/RoleSelector.js'],
	SuggestBoxView : [__ctxPath + '/js/info/SuggestBoxView.js',
			__ctxPath + '/js/info/SuggestBoxForm.js',
			__ctxPath + '/js/info/SuggestBoxReplyForm.js',
			__ctxPath + '/js/info/SuggestBoxDisplay.js'],
	GoodsCheckView : [__ctxPath + '/js/admin/GoodsCheckView.js',
			__ctxPath + '/js/admin/GoodsCheckForm.js'],
	CarCheckView : [__ctxPath + '/js/admin/CarCheckView.js',
			__ctxPath + '/js/admin/CarCheckForm.js'],
	PublicPhoneBookView : [
			__ctxPath + '/js/communicate/PublicPhoneBookView.js',
			__ctxPath + '/js/communicate/PublicPhoneGroupForm.js',
			__ctxPath + '/js/communicate/PhoneBookForm.js'],
	RegulationView : [__ctxPath + '/js/admin/RegulationForm.js',
			__ctxPath + '/js/admin/RegulationView.js',
			__ctxPath + '/js/admin/RegulationScanWin.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js'],
	RegulationScanView : [__ctxPath + '/js/admin/RegulationScanView.js',
			__ctxPath + '/js/admin/RegulationScanWin.js'],
	LeaveManageView : [__ctxPath + '/js/personal/LeaveManageView.js',
			__ctxPath + '/js/personal/LeaveManageWin.js'],
	OnlineDocumentManageView : [
			__ctxPath + '/js/document/OnlineDocumentManageView.js',
			__ctxPath + '/js/document/OnlineDocumentForm.js',
			__ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
			__ctxPath + '/js/selector/SealSelector.js',
			__ctxPath + '/js/selector/PaintTemplateSelector.js',
			__ctxPath + '/js/document/DocFolderForm.js',
			__ctxPath + '/ext3/ux/CheckColumn.js',
			__ctxPath + '/js/document/DocFolderSharedForm.js',
			__ctxPath + '/js/document/DocFolderSelector.js',
			__ctxPath + '/js/document/FileDetailShowWin.js',
			__ctxPath + '/js/document/KnowledgePrivilegeWin.js',
			__ctxPath + '/js/document/OnlineDocumentDetail.js',
			__ctxPath + '/js/selector/RoleSelector.js'],
	PaintTemplateView : [__ctxPath + '/js/document/PaintTemplateView.js',
			__ctxPath + '/js/document/PaintTemplateForm.js',
			__ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
			__ctxPath + '/js/document/DocumentTemplateForm.js',
			__ctxPath + '/js/selector/SealSelector.js',
			__ctxPath + '/js/selector/PaintTemplateSelector.js'],
	SealView : [__ctxPath + '/js/document/SealView.js',
			__ctxPath + '/js/document/SealForm.js',
			__ctxPath + '/js/core/ntkosign/NtkoSignPanel.js',
			__ctxPath + '/js/document/MakeSealForm.js',
			__ctxPath + '/js/document/SealShowPanel.js'],
	SectionList : [__ctxPath + '/js/info/SectionList.js',
			__ctxPath + '/js/info/SectionForm.js',
			__ctxPath + '/js/selector/SectionSelector.js'],
	SectionView : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/info/SectionView.js',
			__ctxPath + '/js/info/SectionForm.js',
			__ctxPath + '/js/selector/SectionSelector.js'

	],
	FormDefView : [__ctxPath + '/js/flow/FormDefView.js',
			__ctxPath + '/js/flow/FormDefForm.js',
			__ctxPath + '/js/fckdesign/Fckdesigner.js',
			__ctxPath + '/js/fckdesign/FormDesignPanelForm.js',
			__ctxPath + '/js/flow/FormDefDetailWin.js'],
	FlowFormProsView : [
			__ctxPath + '/js/flow/FlowFormProsView.js',
			// __ctxPath + '/js/flow/FlowFormQueryView.js',
			__ctxPath + '/js/flow/FlowFormQueryForms.js',
			__ctxPath + '/js/flow/FlowFormQueryEntity.js',
			__ctxPath + '/js/flow/FlowFormEntityView.js'],
	OutMailSetView : [__ctxPath + '/js/system/OutMailSetForm.js',
			__ctxPath + '/js/system/OutMailSetView.js'],
	ProInstanceMgr : [__ctxPath + '/js/flow/ProInstanceMgr.js',
			__ctxPath + '/js/flow/ProInstanceView.js',
			__ctxPath + '/js/flow/ProInstanceDetail.js',
			__ctxPath + '/js/flow/PathChangeWindow.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/TaskHandlerWindow.js',
			__ctxPath + '/js/flow/TaskDueDateWindow.js'],
	JforumView : [__ctxPath + '/js/info/JforumView.js'],
	MyFileAttachView : [__ctxPath + '/js/system/MyFileAttachView.js'],
	ReportTemplateMenu : [__ctxPath + '/js/system/ReportTemplateMenu.js',
			__ctxPath + '/js/system/ReportTemplatePreview.js'],
	ReportTemplateMenu1 : [__ctxPath + '/js/system/ReportTemplateMenu1.js',
			__ctxPath + '/js/system/ReportTemplatePreview1.js'],
	DutyView : [__ctxPath + '/js/personal/DutyView.js',
			__ctxPath + '/js/personal/DutyForm.js'],
	ComIndexPage : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/info/ComIndexPage.js'],
	AppHome : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/App.home.js'],
	DicManager : [__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/system/DicManager.js',
			__ctxPath + '/js/system/DicTypeChangeWin.js',
			__ctxPath + '/js/core/ux/TreeCombo.js',
			__ctxPath + '/js/system/DictionaryForm.js'],
	DicIndepManager : [__ctxPath + '/js/system/GlobalTypeIndepForm.js',
			__ctxPath + '/js/system/DicIndepManager.js',
			__ctxPath + '/js/system/DicIndepTypeChangeWin.js',
			__ctxPath + '/js/core/ux/TreeCombo.js',
			__ctxPath + '/js/system/DictionaryIndepForm.js'],
	DicManagerOfType : [__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/system/DicManagerOfType.js',
			__ctxPath + '/js/system/DicTypeChangeWin.js',
			__ctxPath + '/js/core/ux/TreeCombo.js',
			__ctxPath + '/js/system/DictionaryForm.js'],
	BoardRooView : [__ctxPath + '/js/admin/BoardRooView.js',
			__ctxPath + '/js/admin/BoardRooForm.js'],
	BoardTypeView : [__ctxPath + '/js/admin/BoardTypeView.js',
			__ctxPath + '/js/admin/BoardTypeForm.js'],
	AddConfSummaryView : [__ctxPath + '/js/admin/AddConfSummaryView.js',
			__ctxPath + '/js/selector/ConferenceSelector.js'],
	AddConferenceView : [__ctxPath + '/js/admin/AddConferenceView.js'],
	ConfSummaryView : [__ctxPath + '/js/admin/ConfSummaryView.js',
			__ctxPath + '/js/admin/ConfSummaryForm.js',
			__ctxPath + '/js/admin/ConfSummaryDetailForm.js'],
	MyJoinConferenceView : [__ctxPath + '/js/admin/MyJoinConferenceView.js',
			__ctxPath + '/js/admin/ConferenceForm.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js'],
	MyJoinedConferenceView : [
			__ctxPath + '/js/admin/MyJoinedConferenceView.js',
			__ctxPath + '/js/admin/ConferenceForm.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js'],
	ZanCunConferenceView : [__ctxPath + '/js/admin/ZanCunConferenceView.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js',
			__ctxPath + '/js/admin/ConferenceForm.js'],
	YiKaiConferenceView : [__ctxPath + '/js/admin/YiKaiConferenceView.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js',
			__ctxPath + '/js/admin/ConferenceForm.js'],
	DaiKaiConferenceView : [__ctxPath + '/js/admin/DaiKaiConferenceView.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js',
			__ctxPath + '/js/admin/ConferenceForm.js'],
	DaiConfApplyView : [__ctxPath + '/js/admin/DaiConfApplyView.js',
			__ctxPath + '/js/admin/ConfApplyForm.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js'],
	ConfApplyView : [__ctxPath + '/js/admin/ConfApplyView.js',
			__ctxPath + '/js/admin/ConferenceDetailForm.js'],

	// 妗ｆ绠＄悊
	ArchFondView : [__ctxPath + '/js/arch/ArchFondView.js',
			__ctxPath + '/js/arch/ArchFondForm.js',
			__ctxPath + '/ext3/ux/PagingStore.js',
			__ctxPath + '/js/system/GlobalTypeForm.js'],

	ArchRollView : [__ctxPath + '/js/arch/ArchRollView.js',
			__ctxPath + '/js/arch/ArchRollForm.js',
			__ctxPath + '/ext3/ux/PagingStore.js',
			__ctxPath + '/js/system/GlobalTypeForm.js'],
	RollFileView : [__ctxPath + '/js/arch/RollFileView.js',
			__ctxPath + '/js/arch/RollFileForm.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/arch/RollFileListView.js',
			// __ctxPath + '/js/arch/RollFileListForm.js',//灞忚斀鏌ョ湅闄勪欢鏄庣粏
			__ctxPath + '/js/arch/ViewFileWindow.js'],
	TidyFileView : [
			__ctxPath + '/js/arch/TidyFileView.js',
			// __ctxPath + '/js/arch/RollFileForm.js',//灞忚斀澧炲姞
			__ctxPath + '/js/arch/TidyFileForm.js',
			// __ctxPath + '/js/system/GlobalTypeForm.js',//灞忚斀澧炲姞鍒嗙被
			__ctxPath + '/js/arch/ViewFileWindow.js',
			__ctxPath + '/js/arch/MyBorrowFileViewWindow.js',
			__ctxPath + '/js/arch/MyBorrowFileSlaveListGrid.js',
			__ctxPath + '/ext3/ux/PagingMemoryProxy.js',
			__ctxPath + '/ext3/ux/PagingStore.js',
			__ctxPath + '/js/core/ux/TreeCombo.js'],
	NewBorrowRecordFormPan : [__ctxPath + '/js/arch/NewBorrowRecordFormPan.js',
			__ctxPath + '/ext3/ux/PagingStore.js',
			__ctxPath + '/js/arch/BorrowFileListView.js',
			__ctxPath + '/js/arch/SelectFondWindow.js',
			__ctxPath + '/js/arch/SelectRollWindow.js',
			__ctxPath + '/js/arch/SelectFileWindow.js'

	],
	CheckBorrowRecordView : [
			__ctxPath + '/js/arch/CheckBorrowRecordView.js',
			__ctxPath + '/js/arch/CheckBorrowRecordForm.js',
			// __ctxPath + '/js/arch/CheckBorrowFileListView.js',
			__ctxPath + '/js/arch/MyBorrowFilePanel.js',
			__ctxPath + '/js/arch/MyBorrowFileTypePanel.js',
			__ctxPath + '/js/arch/MyBorrowFileListPanel.js',
			__ctxPath + '/js/arch/ViewFileWindow.js',

			__ctxPath + '/js/arch/MyBorrowFileViewWindow.js',
			__ctxPath + '/js/arch/MyBorrowFileSlaveListGrid.js'

	],
	MyBorrowRecordView : [
			__ctxPath + '/js/arch/MyBorrowRecordView.js',
			__ctxPath + '/js/arch/MyBorrowFilePanel.js',
			__ctxPath + '/js/arch/MyBorrowFileTypePanel.js',
			__ctxPath + '/js/arch/MyBorrowFileListPanel.js',
			__ctxPath + '/js/arch/ViewFileWindow.js',
			__ctxPath + '/ext3/ux/PagingStore.js',
			__ctxPath + '/js/arch/MyBorrowFileViewWindow.js',
			__ctxPath + '/js/arch/MyBorrowFileSlaveListGrid.js',
			/** ********************************************** */
			__ctxPath + '/js/arch/BorrowRecordForm.js',
			__ctxPath + '/js/arch/BorrowFileListView.js',
			__ctxPath + '/js/arch/SelectFondWindow.js',
			__ctxPath + '/js/arch/SelectRollWindow.js',
			__ctxPath + '/js/arch/SelectFileWindow.js'],
	StatisticsArchYearReportPanel : [__ctxPath
			+ '/js/arch/StatisticsArchYearReportPanel.js'],
	StatisticsArchRollReportPanel : [__ctxPath
			+ '/js/arch/StatisticsArchRollReportPanel.js'],
	StatisticsArchFileReportPanel : [__ctxPath
			+ '/js/arch/StatisticsArchFileReportPanel.js'],
	archSetingManager : [__ctxPath + '/js/arch/archSetingManager.js',
			// __ctxPath + '/js/system/GlobalTypeForm.js',
			// __ctxPath + '/js/system/DicTypeChangeWin.js',
			__ctxPath + '/js/system/DictionaryForm.js'],
	ArchiveTypeTempView : [__ctxPath + '/js/archive/ArchiveTypeTempView.js',
			__ctxPath + '/js/system/GlobalTypeForm.js',
			__ctxPath + '/js/archive/ArchTemplateView.js',
			__ctxPath + '/js/archive/ArchTemplateForm.js',
			__ctxPath + '/js/archive/OfficeTemplateView.js',
			__ctxPath + '/js/selector/GlobalTypeSelector.js',
			__ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js'],
	ArchFlowConfView : [__ctxPath + '/js/archive/ArchFlowConfView.js',
			__ctxPath + '/js/selector/FlowSelector.js'],
	ArchivesSignView : [__ctxPath + '/js/archive/ArchivesSignView.js',
			__ctxPath + '/js/archive/ArchivesDetailWin.js'],
	ArchivesMonitor : [__ctxPath + '/js/archive/ArchivesMonitor.js',
			__ctxPath + '/js/archive/ArchivesDetailWin.js',
			__ctxPath + '/js/archive/ArchHastenForm.js',
			__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/flow/ProcessRunDetail.js']

	// start: Generated for ProcessModule From Template: App.import.js.vm
	,
	ProcessModuleView : [__ctxPath + '/js/flow/ProcessModuleView.js',
			__ctxPath + '/js/flow/ProcessModuleForm.js',
			__ctxPath + '/js/selector/FlowSelector.js']

	// end: Generated for ProcessModule From Template: App.import.js.vm

	,
	OrgSettingView : [__ctxPath + '/js/system/OrgSettingView.js',
			__ctxPath + '/js/system/DemensionForm.js',
			__ctxPath + '/js/system/PositionForm.js',
			__ctxPath + '/js/system/OrganizationForm.js',
			__ctxPath + '/js/system/CompanyWin.js',
			__ctxPath + '/js/system/DepartmentWin.js',
			__ctxPath + '/js/selector/RoleDialog.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/UserDialog.js'],
	PositionUserView : [__ctxPath + '/js/system/PositionUserView.js',
			__ctxPath + '/js/system/PositionForm.js',
			__ctxPath + '/js/selector/PositionDialog.js',
			__ctxPath + '/js/selector/UserDialog.js'],
	DepUserView : [__ctxPath + '/js/system/DepUserView.js',
			__ctxPath + '/js/system/DepForm.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/system/RelativeUserView.js',
			__ctxPath + '/js/system/RelativeUserForm.js',
			__ctxPath + '/js/system/RelativeJobView.js',
			__ctxPath + '/js/system/RelativeJobForm.js'],
	UserFormPanel : [__ctxPath + '/js/system/UserFormPanel.js',
			__ctxPath + '/js/system/setPasswordForm.js']
	// start: Generated for KnowledgeRights From Template: App.import.js.vm
	,
	KnowledgeRightsView : [__ctxPath + '/js/document/KnowledgeRightsView.js',
			__ctxPath + '/js/document/KnowledgeRightsForm.js']

	// end: Generated for KnowledgeRights From Template: App.import.js.vm

	,
	OrgStructureView : [__ctxPath + '/js/system/OrgStructureView.js'],
	DemensionView : [__ctxPath + '/js/system/DemensionView.js',
			__ctxPath + '/js/system/DemensionForm.js'],
	// 鏂板缓椤圭洰
	newProjectForm : [__ctxPath + '/js/commonFlow/NewProjectForm.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/smallLoan/quickenLoan/LendForm.js',
			__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js'],
	NewProjectFormEnter : [__ctxPath + '/js/commonFlow/NewProjectFormEnter.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/smallLoan/quickenLoan/LendForm.js',
			__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js'],
	NewProjectFormEnterPrise: [
			__ctxPath + '/js/commonFlow/NewProjectFormEnterPrise.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js'],
	// 鏂板缓椤圭洰2
	NewProjectFormSSZZ : [
			__ctxPath + '/js/commonFlow/NewProjectFormSSZZ.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js'],

	CsDicAreaDynamView : [__ctxPath + '/js/system/CsDicAreaDynamView.js',
			__ctxPath + '/js/system/CsDicAreaDynamForm.js']
	// 浼佷笟涓讳綋
	,
	SlCompanyMainView : [
			__ctxPath + '/js/creditFlow/ourmain/SlPersonMainForm.js',
			__ctxPath + '/js/creditFlow/ourmain/SlCompanyMainView.js',
			__ctxPath + '/js/creditFlow/ourmain/SlCompanyMainForm.js']

	// 涓汉涓讳綋
	,
	SlPersonMainView : [
			__ctxPath + '/js/creditFlow/ourmain/SlPersonMainView.js',
			__ctxPath + '/js/creditFlow/ourmain/SlPersonMainForm.js']
	// 绗笁鏂逛繚璇�
	,
	SVEnterprisePersonView : [__ctxPath
			+ '/js/ourmain/SVEnterprisePersonView.js']
	// 瀹¤捶浼氬喅璁�
	,
	SlApprovalForm : [__ctxPath + '/js/commonFlow/SlApprovalForm.js',
			__ctxPath + '/js/ourmain/SVEnterprisePersonView.js',
			__ctxPath + '/js/commonFlow/NoteRecords.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js']

	// 妯℃澘娴嬭瘯
	,
	SlReportTemplateView : [
			__ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
			__ctxPath + '/publicmodel/uploads/js/cs_showDownload.js',
			__ctxPath + '/publicmodel/uploads/js/data-view.js',
			__ctxPath + '/publicmodel/uploads/js/UploadPanel.js',
			__ctxPath + '/publicmodel/uploads/js/upload.js',
			__ctxPath + '/publicmodel/uploads/js/swfupload.js',
			__ctxPath + '/js/document/OnlineDocumentManageView.js',
			__ctxPath + '/js/document/OnlineDocumentForm.js',
			__ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
			__ctxPath + '/js/selector/SealSelector.js',
			__ctxPath + '/js/selector/PaintTemplateSelector.js',
			__ctxPath + '/js/document/DocFolderForm.js',
			__ctxPath + '/ext3/ux/CheckColumn.js',
			__ctxPath + '/js/document/DocFolderSharedForm.js',
			__ctxPath + '/js/document/DocFolderSelector.js',
			__ctxPath + '/js/document/FileDetailShowWin.js',
			__ctxPath + '/js/document/KnowledgePrivilegeWin.js',
			__ctxPath + '/js/document/OnlineDocumentDetail.js',
			__ctxPath + '/js/selector/RoleSelector.js',
			__ctxPath + '/js/reporttemplate/SlReportTemplateView.js'],
	SlContractView : [__ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
			__ctxPath + '/publicmodel/uploads/js/cs_showDownload.js',
			__ctxPath + '/publicmodel/uploads/js/data-view.js',
			__ctxPath + '/publicmodel/uploads/js/UploadPanel.js',
			__ctxPath + '/publicmodel/uploads/js/upload.js',
			__ctxPath + '/publicmodel/uploads/js/swfupload.js',
			// __ctxPath + '/ext3/ux/js/RowEditor.js',
			__ctxPath + '/js/slContract/SlContractView.js'],
	SlContractQSView : [__ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
			__ctxPath + '/publicmodel/uploads/js/cs_showDownload.js',
			__ctxPath + '/publicmodel/uploads/js/data-view.js',
			__ctxPath + '/publicmodel/uploads/js/UploadPanel.js',
			__ctxPath + '/publicmodel/uploads/js/upload.js',
			__ctxPath + '/publicmodel/uploads/js/swfupload.js',
			__ctxPath + '/js/slContract/SlContractQSView.js']

	// 鑲′笢淇℃伅
	,
	CsEnterpriseShareequityView : [
			__ctxPath + '/js/customer/CsEnterpriseShareequityView.js',
			__ctxPath + '/js/customer/CsEnterpriseShareequityForm.js'],
	FinanceMortgageMenuView : [
			__ctxPath + '/js/creditFlow/ourmain/SlCompanyMainForm.js',
			__ctxPath + '/js/creditFlow/ourmain/SlPersonMainForm.js',
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageListView.js',
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageListView.js',
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageMenuView.js'
	// __ctxPath+'/js/creditFlow/ourmortgage/SlMortgageView.js',
	// __ctxPath+'/js/creditFlow/ourmortgage/SlMortgageForm.js'

	]

	,
	CashLoanPlan : [

			__ctxPath + '/js/fastFlow/CashLoanPlan.js',
			__ctxPath + '/js/fastFlow/Opinion.js',
			__ctxPath + '/js/ourmain/SVEnterprisePersonView.js',

			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js'

	]
	// 璧勯噾娴佸垎鏋�
	,
	ReportTemplateMenuCapital : [
			__ctxPath + '/js/system/ReportTemplateMenuCapital.js',
			__ctxPath + '/js/system/ReportTemplatePreviewCapital.js'],
	// 椤圭洰淇℃伅(蹇�娴佺▼)
	ProjectInfo : [__ctxPath + '/js/fastFlow/ProjectInfo.js',
			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js'

	]
	// 鍚堝悓鍒朵綔(蹇�娴佺▼)
	,
	ContractManufacturing : [
			__ctxPath + '/js/fastFlow/ContractManufacturing.js',
			__ctxPath + '/js/fastFlow/Opinion.js',
			__ctxPath + '/js/ourmain/SVEnterprisePersonView.js',
			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js'

	],
	// 鍚堝悓绛剧讲
	ContractSigning : [__ctxPath + '/js/fastFlow/ContractSigning.js',
			__ctxPath + '/js/fastFlow/Opinion.js',
			__ctxPath + '/js/ourmain/SVEnterprisePersonView.js',
			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js']
	// 椤圭洰鏀炬
	,
	ProjectLoan : [__ctxPath + '/js/fastFlow/ProjectLoan.js',
			__ctxPath + '/js/fastFlow/Opinion.js',
			__ctxPath + '/js/ourmain/SVEnterprisePersonView.js',

			__ctxPath + '/js/commonFlow/EnterpriseShareequity.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js'

	],
	DiligenceFormMeeting : [__ctxPath
			+ '/js/commonFlow/DiligenceFormMeeting.js'],
	SlFundIntentView : [
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentView.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',

			__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/CashCheck.js',
			__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'

	]

	,
	SlFundIntentForm : [__ctxPath
			+ '/js/creditFlow/finance/SlFundIntentForm.js'

	]
	// end: Generated for SlFundIntent From Template: App.import.js.vm
	// start: Generated for SlFundDetail From Template: App.import.js.vm
	,
	SlFundDetailView : [
			__ctxPath + '/js/creditFlow/finance/SlFundDetailView.js',
			__ctxPath + '/js/creditFlow/finance/SlFundDetailForm.js'],
	SlConferenceRecordView : [__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/project/SlConferenceRecordView.js',
			__ctxPath + '/js/project/SlConferenceRecordForm.js']

	// end: Generated for SlFundDetail From Template: App.import.js.vm
	,
	projectYearProfitView : [__ctxPath
			+ '/js/creditFlow/finance/projectYearProfitView.js'],
	projectYearRateView : [__ctxPath
			+ '/js/creditFlow/finance/projectYearRateView.js'],
	projectYearRateReport : [__ctxPath
			+ '/js/creditFlow/finance/projectYearRateReport.js']
	// start: Generated for SlMortgageFinancing From Template: App.import.js.vm

	// 浠诲姟绠＄悊-寰呭鐞嗕换鍔�
	,
	
	// 浠诲姟绠＄悊-寰呭鐞嗕换鍔�鎵嬫満鐗�

	ActivityTaskViewMobile : [__ctxPath + '/js/flow/ProcessNextForm.js',
			__ctxPath + '/js/creditFlow/smallLoan/project/SlProcessRunView.js',
			__ctxPath + '/js/creditFlow/common/ActivityTaskViewMobile.js']
	// 浠诲姟绠＄悊-宸插畬鎴愪换鍔�
	,
	Resolution : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/Resolution.js'],
	BusinessRegistration : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/BusinessRegistration.js'

	],
	Financial : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/Financial.js'],
	Contract : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/Contract.js'],
	GuaranteeCommitment : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/GuaranteeCommitment.js'],
	EBDiligenceForm : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/EBDiligenceForm.js'
	// __ctxPath+'/js/system/SlMortgageFinancingForm.js'
	],
	EnterpriseEvaluation : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/EnterpriseEvaluation.js']
	// 鍙嶆媴淇濇帾鏂界櫥璁�chencc
	,
	opponentMortgageCheckView : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/opponentMortgageCheckView.js']
	// 淇濅腑鐩戠 chencc
	,
	MiddleSupervision : [
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/enterpriseBusiness/MiddleSupervision.js']
	// 鍚堝悓纭 chencc
	,
	GlContract : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/GlContract.js']
	// end: Generated for SlMortgageFinancing From Template: App.import.js.vm
	,
	CreditlineReleaseForm : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/CreditlineReleaseForm.js'],
	BankGuaranteeMoney : [
			__ctxPath
					+ '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/BankGuaranteeMoney.js',
			__ctxPath + '/js/creditFlow/finance/.js'],
	CustomerGuaranteeMoney : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/CustomerGuaranteeMoney.js'],
	GuaranteeMoney : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/guaranteeModel/GuaranteeMoney.js'],
	ReturnGuaranteeMoney : [
			__ctxPath
					+ '/js/creditFlow/guarantee/enterpriseBusiness/ReturnGuaranteeMoney.js',
			__ctxPath + '/publicmodel/uploads/js/uploads.js'],
	ArchiveConfirm : [__ctxPath
			+ '/js/creditFlow/guarantee/enterpriseBusiness/ArchiveConfirm.js']

	,
	RemoveAntiguarantStep : [
			__ctxPath
					+ '/js/creditFlow/guarantee/enterpriseBusiness/RemoveAntiguarantStep.js',
			__ctxPath + '/publicmodel/uploads/js/uploads.js']

	,
	OurProcreditMaterialsEnterpriseView : [
			__ctxPath + '/js/dictionary/treegrid/TreeGridSorter.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumnResizer.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridNodeUI.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridLoader.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumns.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGrid.js',
			__ctxPath + '/js/dictionary/TreeNodeChecked.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseView.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseForm.js'],
	OurLeaseFinanceMortgageView : [// copy by gaoqingrui
			__ctxPath + '/js/dictionary/treegrid/TreeGridSorter.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumnResizer.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridNodeUI.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridLoader.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumns.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGrid.js',
			__ctxPath + '/js/dictionary/TreeNodeChecked.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/materials/OurLeaseFinanceMortgageView.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/materials/OurLeaseFinanceMortgageForm.js'],
	SystemWordView : [__ctxPath + '/js/system/SystemWordView.js',
			__ctxPath + '/js/system/SystemWordTypeForm.js',
			__ctxPath + '/js/system/SystemWordForm.js'],
	RepaymentSource : [__ctxPath + '/js/commonFlow/RepaymentSource.js'],
	SlActualToChargeVM : [
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeVM.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeCashCheck.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	SlActualToChargeView : [
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeView.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeCashCheck.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	PerformanceManagement : [
			__ctxPath + '/js/commonFlow/PerformanceManagement.js',
			__ctxPath + '/js/commonFlow/department.js']
	// start: Generated for SlSuperviseIn From Template: App.import.js.vm
	,
	
	OurProcreditAssuretenetLoanView : [
			__ctxPath
					+ '/js/creditFlow/assuretenet/OurProcreditAssuretenetLoanView.js',
			__ctxPath
					+ '/js/creditFlow/assuretenet/OurProcreditAssuretenetForm.js'],
	OurFinanceProductMaterialsView : [
			__ctxPath
					+ '/js/creditFlow/smallLoan/materials/OurFinanceProductMaterialsView.js',
			__ctxPath
					+ '/js/creditFlow/smallLoan/materials/OurSmallloanMaterialsForm.js']

	,
	OurProcreditMaterialsEnterpriseView : [
			__ctxPath + '/js/dictionary/treegrid/TreeGridSorter.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumnResizer.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridNodeUI.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridLoader.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumns.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGrid.js',
			__ctxPath + '/js/dictionary/TreeNodeChecked.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseView.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseForm.js'],
	SystemWordView : [__ctxPath + '/js/system/SystemWordView.js',
			__ctxPath + '/js/system/SystemWordTypeForm.js',
			__ctxPath + '/js/system/SystemWordForm.js'],
	RepaymentSource : [__ctxPath + '/js/commonFlow/RepaymentSource.js'],
	SlActualToChargeView : [
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeView.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeCashCheck.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	SlActualToChargeIncomeView : [
			__ctxPath
					+ '/js/creditFlow/finance/loanfinance/SlActualToChargeIncomeView.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeDetailView.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/chargeCashCheck.js',
			__ctxPath + '/js/creditFlow/finance/chargeeditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlActualToChargeForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	PerformanceManagement : [
			//__ctxPath + '/js/commonFlow/PerformanceManagement.js',
			__ctxPath + '/js/creditFlow/financeProject/project/PerformanceManagement.js',
			__ctxPath + '/js/commonFlow/department.js']
	// start: Generated for SlSuperviseIn From Template: App.import.js.vm
	,
	

	// end: Generated for SlSuperviseIn From Template: App.import.js.vm
	// start: Generated for SlSuperviseRecord From Template: App.import.js.vm
	
	SlSuperviseRecordView : [
			__ctxPath + '/js/supervise/SlSuperviseRecordView.js',
			__ctxPath + '/js/supervise/SlSuperviseRecordForm.js']

	// end: Generated for SlSuperviseRecord From Template: App.import.js.vm
	// 灏忚捶璐峰悗椤圭洰绠＄悊
	,
	SmallLoanedProjectManager : [
			__ctxPath
					+ '/js/creditFlow/smallLoan/project/SmallLoanedProjectManager.js',
			__ctxPath
					+ '/js/creditFlow/smallLoan/project/AddSupervisionRecord.js',
		    __ctxPath + '/js/creditFlow/leaseFinance/project/GlobalSupervisonRecordView.js',
			__ctxPath + '/js/creditFlow/finance/calculateFundIntent.js',
			__ctxPath + '/js/creditFlow/finance/calulateFinancePanel.js',
			__ctxPath + '/js/creditFlow/finance/caluateIntentGrid.js',
			__ctxPath + '/js/creditFlow/finance/calulateloadDataCommon.js'

	]
	// 灏忚捶椤圭洰鏌ヨ
	,
	FinancingProjectManager : [
			__ctxPath
					+ '/js/creditFlow/financeProject/project/FinancingProjectManager.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuFinancingprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingprojectdetail.js']
	// 鎷呬繚椤圭洰绠＄悊
	,
	GuaranteeProjectManager : [
			__ctxPath
					+ '/js/creditFlow/guarantee/project/GuaranteeProjectManager.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuGuaranteeprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewGuarnteeprojectdetail.js'

	]
	// 璇夎鎷呬繚椤圭洰绠＄悊
	,
	LawsuitguaranteeProjectManager : [
			__ctxPath
					+ '/js/creditFlow/lawsuitguarantee/project/LawsuitguaranteeProjectManager.js',
			__ctxPath
					+ '/js/creditFlow/lawsuitguarantee/project/LawsuitguaranteeProjectInfo.js',
			__ctxPath
					+ '/js/creditFlow/lawsuitguarantee/project/LawsuitguaranteeProjectInfoEdit.js']
	// 閾惰淇濊瘉閲戣处鎴风鐞�
	,
	AccountBankManage : [
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/AccountBankManage.js',
			__ctxPath + '/js/creditFlow/customer/dictionary/dictionaryTree.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/accountBankManage/allParentBanksTreeWin.js']
	// 淇濊瘉閲戣处鎴烽」鐩褰曠鐞�
	,
	AccountBankManageProj : [
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/AccountBankManageProj.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/accountBankManage/accountBankManageTreeWin.js',
			__ctxPath + '/js/creditFlow/customer/dictionary/dictionaryTree.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/accountBankManage/selectProjectList.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/guaranteefinance/accountBankManage/allParentBanksTreeWin.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/project/GuaranteeProjectInfo.js']
	// 鎸囨爣搴撶鐞�
	,
	IndicatorManagement : [
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/indicatorManagement.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/optionsList.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/indicatorstoreTree.js'],
	PlArchivesView : [__ctxPath + '/js/creditFlow/archives/PlArchivesView.js',
			__ctxPath + '/js/creditFlow/archives/PlArchivesForm.js'

	],
		flContractManager : [
			__ctxPath + '/js/creditFlow/document/flContractManager.js',
			__ctxPath
					+ '/js/creditFlow/smallLoan/contract/BatchSignThirdContractWindow.js',
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageView.js',// 
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageListView.js',
			__ctxPath
					+ '/js/creditFlow/smallLoan/contract/mortgageContractView.js'],
	SysLogView : [// 绯荤粺鐧诲綍鏃ュ織
	__ctxPath + '/js/system/SysLogView.js'],
	SystemLogView : [ // 绯荤粺鎿嶄綔鏃ュ織
	__ctxPath + '/js/system/SystemLogView.js']

	,
	AreaDicView : [__ctxPath + '/js/dictionary/AreaDicView.js'],
	CreditTreeDictionaryView : [__ctxPath
			+ '/js/dictionary/CreditTreeDictionaryView.js']

	,
	SysDatabaseBRView : [__ctxPath + '/js/system/SysDatabaseBRView.js']

	,
	ReportMenuGuaranteeprojectdetail : [
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuGuaranteeprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewGuarnteeprojectdetail.js'],
	ReportMenuGuaranteeprojectintentdetail : [
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuGuaranteeprojectintentdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewGuarnteeprojectintentdetail.js'],
	ReportMenuSmallloanprojectdetail : [
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuSmallloanprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewSmallloanprojectdetail.js'],
	ReportMenuSmallloanprojectintentdetail : [
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuSmallloanprojectintentdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewSmallloanprojectintentdetail.js']

	,
	BankRelationPersonView : [
			__ctxPath
					+ '/js/creditFlow/customer/bankrelationperson/BankRelationPersonView.js',
			__ctxPath
					+ '/js/creditFlow/customer/bankrelationperson/BankRelationPersonWindow.js'],
	FinanceMortgageMenuQuery : [
			__ctxPath + '/js/creditFlow/mortgage/FinanceMortgageMenuQuery.js',
			__ctxPath + '/js/selector/UserDialog.js']
	// 鍥㈤槦鐗堜釜浜烘闈㈠紩鐢╦s gengjj
	,
	AppHomeTeam : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/team/App.Team.home.js'

	],
	LoanAccept : [__ctxPath + '/js/creditFlow/smallLoan/project/LoanAccept.js'],
	RiskReview : [__ctxPath + '/js/creditFlow/smallLoan/project/RiskReview.js'],
	LoanApproval : [__ctxPath
			+ '/js/creditFlow/smallLoan/project/LoanApproval.js'],
	LoanProvide : [__ctxPath
			+ '/js/creditFlow/smallLoan/project/LoanProvide.js'],
	LoanClosing : [__ctxPath
			+ '/js/creditFlow/smallLoan/project/LoanClosing.js'],
	OurLawsuitMaterialsView : [
			__ctxPath
					+ '/js/creditFlow/lawsuitguarantee/materials/OurLawsuitMaterialsForm.js',
			__ctxPath
					+ '/js/creditFlow/lawsuitguarantee/materials/OurLawsuitMaterialsView.js'],
	BranchOfficeView : [__ctxPath + '/js/group/BranchOfficeView.js',
			__ctxPath + '/js/group/BranchOfficeForm.js'],
	OrganizationStructureView : [
			__ctxPath + '/js/group/OrganizationStructureView.js',
			__ctxPath + '/js/group/BranchOfficeForm.js',
			__ctxPath + '/js/group/DepartmentForm.js'],
	CompanyOfUesrView : [
			__ctxPath + '/js/group/CompanyOfUesrView.js',
			__ctxPath + '/js/group/DepartmentForm.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/system/RelativeUserView.js',
			__ctxPath + '/js/system/RelativeUserForm.js',
			__ctxPath + '/js/system/RelativeJobView.js',
			__ctxPath + '/js/system/RelativeJobForm.js',
			__ctxPath + '/js/group/ShopForm.js',
			__ctxPath + '/js/system/setPasswordForm.js'
			],
			
	
	TabSlFundIntentConstBack : [
			__ctxPath
					+ '/js/creditFlow/finance/loanfinance/TabSlFundIntentConstBack.js',
			__ctxPath
					+ '/js/creditFlow/finance/loanfinance/SlFundIntentConstBackAlready.js',
			__ctxPath
					+ '/js/creditFlow/finance/loanfinance/SlFundIntentConstBackAll.js',
			__ctxPath
					+ '/js/creditFlow/finance/loanfinance/SlFundIntentConstBackShould.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
			__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/CashCheck.js',
			__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	PrincipalDivert : [
			__ctxPath + '/js/creditFlow/finance/loanfinance/PrincipalDivert.js',

			__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
			__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
			__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
			__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/punishDetailView.js'],
	ControlRoleManager : [__ctxPath + '/js/system/ControlRoleManager.js',
			__ctxPath + '/ext3/ux/CheckTreePanel.js',
			__ctxPath + '/js/system/RoleGrantRightView.js',
			__ctxPath + '/js/system/AppRoleForm.js',
			__ctxPath + '/js/creditFlow/finance/SlAccessView.js'],
	BusinessRoleManager : [__ctxPath + '/js/system/BusinessRoleManager.js',
			__ctxPath + '/ext3/ux/CheckTreePanel.js',
			__ctxPath + '/js/system/RoleGrantRightView.js',
			__ctxPath + '/js/system/AppRoleForm.js',
			__ctxPath + '/js/creditFlow/finance/SlAccessView.js'

	],
	SlDataListView : [__ctxPath + '/js/creditFlow/finance/SlDataListView.js',
			__ctxPath + '/js/creditFlow/finance/SlDataInfoWindow.js'],
	SlLoanAccountErrorLogView : [__ctxPath
			+ '/js/creditFlow/finance/SlLoanAccountErrorLogView.js']
	// start: Generated for SlPunishInterest From Template: App.import.js.vm
	,
	SlPunishInterestView : [
			__ctxPath + '/js/creditFlow.finance/SlPunishInterestView.js',
			__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
			__ctxPath + '/js/creditFlow.finance/SlPunishInterestForm.js']

	// end: Generated for SlPunishInterest From Template: App.import.js.vm
	// start: Generated for SlAccrued From Template: App.import.js.vm
	,
	SlAccruedView : [__ctxPath + '/js/creditFlow.finance/SlAccruedView.js',
			__ctxPath + '/js/creditFlow.finance/SlAccruedForm.js']

	// end: Generated for SlAccrued From Template: App.import.js.vm
	// start: Generated for SlPunishDetail From Template: App.import.js.vm
	,
	SlPunishDetailView : [
			__ctxPath + '/js/creditFlow.finance/SlPunishDetailView.js',
			__ctxPath + '/js/creditFlow.finance/SlPunishDetailForm.js']

	// end: Generated for SlPunishDetail From Template: App.import.js.vm
	// start: Generated for CsBank From Template: App.import.js.vm
	,
	CsBankView : [__ctxPath + '/js/creditFlow/common/CsBankView.js',
			__ctxPath + '/js/creditFlow/common/CsBankForm.js']
	,
	CsBankTab : [
		     __ctxPath + '/js/thirdInterface/CsBankTab.js',
		     __ctxPath + '/js/creditFlow/common/CsBankView1.js',
		     __ctxPath + '/js/creditFlow/common/CsBankForm1.js'
	 ]

	// end: Generated for CsBank From Template: App.import.js.vm
	,
	ReportMenuLoanTerm : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewLoanTerm.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanTerm.js'],
	ReportMenuMortgageMode : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewMortgageMode.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuMortgageMode.js'],
	ReportMenuCommon : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewCommon.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuCommon.js'],
	ReportMenuLoanLimitInterval : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewLoanLimitInterval.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanLimitInterval.js'],
	ReportMenuAverageDailyBalance : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewAverageDailyBalance.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuAverageDailyBalance.js'],
	ReportMenuTenCustomer : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewTenCustomer.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuTenCustomer.js'],
	ReportMenuCustomerDailyBalance : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewCustomerDailyBalance.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuCustomerDailyBalance.js'],
	ReportMenuLoanSummary : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewLoanSummary.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanSummary.js'],
	ProfitRateMaintenanceView : [
			__ctxPath + '/js/creditFlow/finance/ProfitRateMaintenanceView.js',
			__ctxPath + '/ext3/ux/ColumnHeaderGroup.js'],
	ReportMenuProfitRate : [
			__ctxPath + '/js/creditFlow/report/ReportPreviewProfitRate.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuProfitRate.js']
	,
	SystemConfigView : [__ctxPath + '/js/system/SystemConfigView.js'

	]
	// start: Generated for CsHoliday From Template: App.import.js.vm
	,
	CsHolidayView : [__ctxPath + '/js/system/CsHolidayView.js',
			__ctxPath + '/js/system/CsHolidayForm.js']
	// 鎷呬繚鏉愭枡娓呭崟绠＄悊
	,
	OurProcreditMaterialsEnterpriseView : [
			__ctxPath + '/js/dictionary/treegrid/TreeGridSorter.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumnResizer.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridNodeUI.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridLoader.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGridColumns.js',
			__ctxPath + '/js/dictionary/treegrid/TreeGrid.js',
			__ctxPath + '/js/dictionary/TreeNodeChecked.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseView.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/materials/OurProcreditMaterialsEnterpriseForm.js']
	// 鎷呬繚鍑嗗叆鍘熷垯澶勭悊
	,
	OurProcreditAssuretenetGuaranteeView : [
			__ctxPath
					+ '/js/creditFlow/assuretenet/OurProcreditAssuretenetGuaranteeView.js',
			__ctxPath
					+ '/js/creditFlow/assuretenet/OurProcreditAssuretenetForm.js']
	// 鎷呬繚淇濆悗椤圭洰鐩戠
	,
	GuaranteeLoanedProjectManager : [
			__ctxPath
					+ '/js/creditFlow/guarantee/project/GuaranteeLoanedProjectManager.js',
			__ctxPath + '/js/creditFlow/smallLoan/project/DesignSupervisionManagePlan.js',// 鐩戠璁″垝
			__ctxPath + '/js/creditFlow/leaseFinance/project/GlobalSupervisonRecordView.js',
			__ctxPath + '/js/creditFlow/leaseFinance/project/flDesignSupervisionManagePlan.js',// 鐩戠璁″垝
			__ctxPath + '/js/creditFlow/smallLoan/project/SlSupervisonRecordView.js',// 鐩戠璁板綍
			__ctxPath + '/js/creditFlow/smallLoan/supervise/SuperviseMangeReport.js',
			__ctxPath + '/js/creditFlow/guarantee/project/BreachHandle.js',// 杩濈害澶勭悊
			__ctxPath + '/js/creditFlow/guarantee/project/ProjectFinished.js'// 椤圭洰缁撻」
	]

	// 璧勯噾铻嶈祫椤圭洰缁撻」
	,
	ProjectFinished : [
			__ctxPath
					+ '/js/creditFlow/financeProject/project/ProjectFinished.js',
			__ctxPath
					+ '/js/creditFlow/financeProject/project/ProjectFinishedForm.js']
	// 璧勯噾铻嶈祫鎻愬墠杩樻
	,
	AdvanceRepayment : [
			__ctxPath
					+ '/js/creditFlow/financeProject/project/AdvanceRepayment.js',
			__ctxPath
					+ '/js/creditFlow/financeProject/project/FinancingEarlyRepaymentView.js',
			__ctxPath
					+ '/js/creditFlow/financeProject/project/FinancingEarlyRepaymentPanel.js',
			__ctxPath
					+ '/js/creditFlow/financeProject/project/FinanceEarlyRepaymentSlFundIntent.js',
			__ctxPath
					+ '/js/creditFlow/finance/Financing_FundIntent_formulate_editGrid.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js']
	// 铻嶈祫椤圭洰鎶曡祫瀹㈡埛
	,
	InvestPersonView : [__ctxPath + '/js/customer/InvestPersonView.js',
			__ctxPath + '/js/customer/InvestPersonForm.js',
			__ctxPath + '/js/customer/InvestPersonCareForm.js',
			__ctxPath + '/js/customer/BpCustRelationForm.js'],
	ReportMenuLoanCustomerCount : [
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanCustomerCount.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewLoanCustomerCount.js'],
	ReportMenuIncome : [
			__ctxPath + '/js/creditFlow/report/ReportMenuIncome.js',
			__ctxPath + '/js/creditFlow/report/ReportPreviewIncome.js'],
	ReportMenuLoanInfoSummary : [
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanInfoSummary.js',
			__ctxPath + '/js/creditFlow/report/ReportPreviewLoanInfoSummary.js'],
	ReportMenuLoanCommon : [
			__ctxPath + '/js/creditFlow/report/ReportMenuLoanCommon.js',
			__ctxPath + '/js/creditFlow/report/ReportPreviewLoanCommon.js']
	// end: Generated for CsHoliday From Template: App.import.js.vm
	// 铻嶈祫涓汉妗岄潰寮曠敤js gengjj
	,
	AppHomeTeamFinancing : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/team/App.Team.home.Financing.js'

	],
	AppHomeTeamGuarantee : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/team/App.Team.home.Guarantee.js'],
	AppHomeTeamPawn : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/team/App.Team.home.Pawn.js'],
	AppHomeTeamLeaseFinance : [__ctxPath + '/ext3/ux/Portal.js',
			__ctxPath + '/ext3/ux/PortalColumn.js',
			__ctxPath + '/ext3/ux/Portlet.js',
			__ctxPath + '/js/system/ResetPasswordForm.js',
			__ctxPath + '/js/system/setPasswordForm.js',
			__ctxPath + '/js/team/App.Team.home.LeaseFinance.js'],
	AppHomeTeamCreditAssignment : [
	__ctxPath + '/ext3/ux/Portal.js',
	__ctxPath + '/ext3/ux/PortalColumn.js',
	__ctxPath + '/ext3/ux/Portlet.js',
	__ctxPath + '/js/system/ResetPasswordForm.js',
	__ctxPath + '/js/system/setPasswordForm.js',
	__ctxPath + '/js/team/App.Team.Home.CreditAssignment.js'],

	ReportMenuFinancingprojectdetail : [
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuFinancingprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingprojectdetail.js'],
	ReportMenuFinancingprojectInterestPayment : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingprojectInterestPayment.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuFinancingprojectInterestPayment.js'],
	ReportMenuFinancingproject : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingproject.js',
			__ctxPath + '/js/creditFlow/report/ReportMenuFinancingproject.js'],
	ReportMenuFinancingprojectAchievement : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingprojectAchievement.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuFinancingprojectAchievement.js'],
	ReportMenuFinancingprojectAnalysis : [
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewFinancingprojectAnalysis.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuFinancingprojectAnalysis.js'],
	OurPawnMaterialsView : [
			__ctxPath + '/js/creditFlow/pawn/materials/OurPawnMaterialsForm.js',
			__ctxPath + '/js/creditFlow/pawn/materials/OurPawnMaterialsView.js'

	]
	
	,
	FlObjectSuppliorView : [
			__ctxPath
					+ '/js/creditFlow.leaseFinance.supplior/FlObjectSuppliorView.js',
			__ctxPath
					+ '/js/creditFlow.leaseFinance.supplior/FlObjectSuppliorForm.js']

	// end: Generated for FlObjectSupplior From Template: App.import.js.vm
	// start: Generated for FlLeaseobjectInfo From Template: App.import.js.vm
	,
	FlLeaseobjectInfoView : [
			__ctxPath
					+ '/js/creditFlow.leaseFinance.leaseobject/FlLeaseobjectInfoView.js',
			__ctxPath
					+ '/js/creditFlow.leaseFinance.leaseobject/FlLeaseobjectInfoForm.js'],
	TabLeaseFundIntentPrincipalPay : [
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/TabLeaseFundIntentPrincipalPay.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentPrincipalPayAlready.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentPrincipalPayAll.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentPrincipalPayShould.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentPrincipalPayOwe.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/CashCheck.js',
			__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js'],
	TabLeaseFundIntentIncome : [
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/TabLeaseFundIntentIncome.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentIncomeActual.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentIncomeShould.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentIncomeAll.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/finance/LeaseFundIntentIncomeOwe.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm.js',
			__ctxPath + '/js/creditFlow/finance/detailView.js',
			__ctxPath + '/js/creditFlow/finance/editAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/editIsOverdueForm.js',
			__ctxPath + '/js/creditFlow/finance/CashCheck.js',
			__ctxPath + '/js/creditFlow/finance/editQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/SlFundIntentForm1.js',
			__ctxPath + '/js/creditFlow/finance/selectAccountlForm.js',
			__ctxPath + '/js/creditFlow/finance/SlRepaymentView.js',
			__ctxPath + '/js/creditFlow/finance/SlPunishInterestView.js',
			__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm.js',
			__ctxPath + '/js/creditFlow/finance/SlPunishInterestForm1.js',
			__ctxPath + '/js/creditFlow/finance/punishEditQlideCheck.js',
			__ctxPath + '/js/creditFlow/finance/editPunishAfterMoneyForm.js',
			__ctxPath + '/js/creditFlow/finance/punishDetailView.js'

	],
	// 瀹氶噺棰樺簱
	IndicatorManagement1 : [
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/indicatorManagement1.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/IndicatorGrid.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/optionsList1.js',
					__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/optionsList2.js',
			__ctxPath
					+ '/js/creditFlow/guarantee/creditManagement/indicatorstoreTree2.js',
			__ctxPath+ '/js/creditFlow/guarantee/creditManagement/selectCreditmanagerElementCode.js'
			],
	// 瀹㈡埛绠＄悊
	LfOfferView : [
			__ctxPath + '/js/creditFlow/leaseFinance/customer/LfOfferForm.js',
			__ctxPath + '/js/creditFlow/leaseFinance/customer/LfOfferView.js'],
	PlPawnProjectManager : [
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectManager.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfo.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfoEdit.js',
			__ctxPath
					+ '/js/creditFlow/pawn/project/PlPawnProjectInfoEditPanel.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfo.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfoPanel.js',
			__ctxPath
					+ '/js/creditFlow/pawn/project/PlPawnProjectNavigation.js']

	// 鏉愭枡娓呭崟绠＄悊
	,
	OurLeaseFinanceMaterialsView : [
			__ctxPath
					+ '/js/creditFlow/leaseFinance/materials/OurLeaseFinanceMaterialsView.js',
			__ctxPath
					+ '/js/creditFlow/leaseFinance/materials/OurLeaseFinanceMaterialsForm.js'],
	PawnProjectManagment : [
			__ctxPath + '/js/creditFlow/pawn/project/PawnProjectManagment.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnContinuedWindow.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnContinuedForm.js',
			__ctxPath
					+ '/js/creditFlow/pawn/finance/PawnContinuedFundIntentView.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnRedeemWindow.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnRedeemForm.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnVastWindow.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnVastForm.js',
			__ctxPath + '/js/creditFlow/pawn/finance/PlFundIntentViewVM.js',
			__ctxPath + '/js/creditFlow/pawn/project/loadPawnContinuedData.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnContinuedView.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfo.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnTicketLossWindow.js',
			__ctxPath + '/js/creditFlow/pawn/project/PawnTicketLossForm.js',
			__ctxPath + '/js/creditFlow/pawn/project/pawnTicketReissueWindow.js',
			__ctxPath + '/js/creditFlow/pawn/project/pawnTicketReissueForm.js',
			__ctxPath + '/js/creditFlow/pawn/project/PlPawnProjectInfoPanel.js',
			__ctxPath+ '/js/creditFlow/pawn/project/PlPawnProjectNavigation.js']

	// 绉熻祦椤圭洰绠＄悊
	,
	LeaseFinanceProjectManager : [
			__ctxPath
					+ '/js/creditFlow/leaseFinance/project/LeaseFinanceProjectManager.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportMenuLeaseFinanceprojectdetail.js',
			__ctxPath
					+ '/js/creditFlow/report/ReportPreviewLeaseFinanceprojectdetail.js'],
	PawnItemsListManagment : [
			__ctxPath
					+ '/js/creditFlow/pawn/pawnItems/PawnItemsListManagment.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/PawnCrkRecordWin.js',
			__ctxPath
					+ '/js/creditFlow/pawn/pawnItems/PawnInspectionRecordWin.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeVehicleInfo.js',
			__ctxPath
					+ '/js/creditFlow/pawn/pawnItems/SeeStockownershipInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeMachineInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeProductInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseInfo.js',
			__ctxPath
					+ '/js/creditFlow/pawn/pawnItems/SeeOfficeBuildingInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeHouseGroundInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeBusinessInfo.js',
			__ctxPath
					+ '/js/creditFlow/pawn/pawnItems/SeeBusinessAndLiveInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeEducationInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeIndustryInfo.js',
			__ctxPath + '/js/creditFlow/pawn/pawnItems/SeeDroitUpdateInfo.js',
			__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsView.js',
			__ctxPath + '/js/creditFlow/pawn/materials/PawnMaterialsForm.js'

	],
	LeaseObjectManagement : [
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManagement.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManagementChOwner.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManageChOwnerInfo.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManagementChPlace.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManageChPlaceInfo.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManagementInsurancePay.js',// 淇濋櫓鐞嗚禂win
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManageInsurancePayInfo.js',// 淇濋櫓鐞嗚禂form
			__ctxPath+ '/js/creditFlow/leaseFinance/objectList/LeaseObjectManagementObjectHandle.js',
			__ctxPath+ '/js/creditFlow/leaseFinance/leaseobject/seeObjectInfo.js'// 绉熻祦鏍囩殑鏌ョ湅鍔熻兘
	],
	newProjectDKForm : [__ctxPath + '/js/commonFlow/NewProjectForm.js',
			__ctxPath + '/js/commonFlow/NewProjectDKForm.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js'],
	newProjectRZForm : [__ctxPath + '/js/commonFlow/NewProjectForm.js',
			__ctxPath + '/js/commonFlow/NewProjectRZForm.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/selector/UserDialog.js',
			__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
			__ctxPath + '/js/customer/InvestPersonCareForm.js',
			__ctxPath + '/js/customer/CustomerCareRecords.js'],
	LeaseFinanceProjectManagerAfter:[
		__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFinanceProjectManagerAfter.js'
	],
	AreaManagement : [
		__ctxPath + '/js/creditFlow/common/AreaManagement.js'
	],
	SystemConfigViewForCustomer : [
		__ctxPath + '/js/system/SystemConfigViewForCustomer.js'
	],
	ReportMenuPawnDetail : [
		__ctxPath + '/js/creditFlow/pawn/report/ReportMenuPawnDetail.js',
		__ctxPath + '/js/creditFlow/pawn/report/ReportPreviewPawnDetail.js'
	],
	ReportMenuMonthPawnPayment : [
		__ctxPath + '/js/creditFlow/pawn/report/ReportMenuMonthPawnPayment.js',
		__ctxPath + '/js/creditFlow/pawn/report/ReportPreviewMonthPawnPayment.js'
	],
	ReportMenuLeaseFinanceDetail : [
		__ctxPath + '/js/creditFlow/leaseFinance/report/ReportMenuLeaseFinanceDetail.js',
		__ctxPath + '/js/creditFlow/leaseFinance/report/ReportPreviewLeaseFinanceDetail.js'
	]
//  start:  Generated for CsElementCategory From Template: App.import.js.vm
,


InvestEnterpriseView :[
	__ctxPath + '/js/customer/InvestEnterpriseView.js',
	__ctxPath + '/js/customer/InvestEnterpriseForm.js',
	__ctxPath + '/js/customer/InvestPersonView.js',
	__ctxPath + '/js/customer/InvestPersonWindowObjList.js',
	__ctxPath + '/js/customer/CustomeLinkmanGridPanel.js',
	__ctxPath+'/js/customer/CustomerCareRecords.js',
	
	__ctxPath+'/js/customer/InvestEnterpriseCareForm.js'
],
InvestEnterpriseCareView :[
	__ctxPath + '/js/customer/InvestEnterpriseCareView.js',
    __ctxPath+'/js/customer/CustomerCareRecords.js',
    __ctxPath+'/js/customer/CareEditForm.js',
    __ctxPath+'/js/customer/InvestEnterpriseCareForm.js'
],
EnterpriseGrantManagerView:[

		__ctxPath + '/js/customer/EnterpriseGrantManagerView.js',
		__ctxPath + '/js/customer/InvestEnterpriseForm.js',
		__ctxPath+'/js/customer/CustomerCareRecords.js',
		__ctxPath + '/js/customer/CustomeLinkmanGridPanel.js'

],
AlterAccrualView:[
	__ctxPath + '/js/creditFlow/financeProject/project/AlterAccrualView.js',
	__ctxPath + '/js/creditFlow/financeProject/project/FinanceAlterAccrualPanel.js',
	__ctxPath + '/js/creditFlow/financeProject/project/FinancingAlterAccrualSlFundIntent.js',
	__ctxPath + '/js/creditFlow/financeProject/project/FinancingAlterAccrualView.js',
	__ctxPath + '/js/creditFlow/finance/SlFundIntentViewVM.js'
]
 ,
confirmRechargeView:[//鍏呭�纭椤甸潰
	__ctxPath + '/js/creditFlow/creditAssignment/finance/confirmRechargeView.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/public.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
	
],
FinanceSlFundIntententView:[
	__ctxPath + '/js/creditFlow/creditAssignment/finance/FinanceSlFundIntententView.js',
	__ctxPath+'/js/creditFlow/finance/SlFundIntentForm.js',
	__ctxPath+'/js/creditFlow/finance/detailView.js',
	__ctxPath+'/js/creditFlow/finance/editAfterMoneyForm.js',
	__ctxPath+'/js/creditFlow/finance/editIsOverdueForm.js',
	__ctxPath+'/js/creditFlow/finance/CashCheck.js',
	__ctxPath+'/js/creditFlow/finance/editQlideCheck.js',
	__ctxPath+'/js/creditFlow/finance/SlFundIntentForm1.js',
	__ctxPath+'/js/creditFlow/finance/selectAccountlForm.js'

],
obligationproductInfoView:[//鍊烘潈浜у搧绠＄悊
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationproductInfoView.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/loadDataCommon.js',
	__ctxPath + '/js/creditFlow/creditAssignment/commonSelect/selectSmallLoadProject.js',
	__ctxPath + '/js/creditFlow/creditAssignment/commonSelect/selsectSystemAccount.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/obligationInvestInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/addInvestment.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/seeObligationMappingInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/ObligationProductInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/finance/obligationFundIntentViewVM.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obProjectInfo.js',
	__ctxPath + '/js/commonFlow/ExtUD.Ext.js'
],
obligationProductTab:[
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationproductInfoView.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/loadDataCommon.js',
	__ctxPath + '/js/creditFlow/creditAssignment/commonSelect/selectSmallLoadProject.js',
	__ctxPath + '/js/creditFlow/creditAssignment/commonSelect/selsectSystemAccount.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/obligationInvestInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/addInvestment.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/seeObligationMappingInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/ObligationProductInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/finance/obligationFundIntentViewVM.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obProjectInfo.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationProductTab.js'
],
mappingObligationToInvestView:[//鎶曡祫浜哄尮閰嶅�鏉�
	__ctxPath + '/js/creditFlow/creditAssignment/project/mappingObligationToInvestView.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/loadDataCommon.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationInvestInfo/addInvestObligation.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/selectCsPerson.js',
	__ctxPath + '/js/creditFlow/creditAssignment/finance/obligationFundIntentViewVM.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/selectProduct.js'
	
],
investmentObligationRemindView:[//鍊烘潈鍒版湡鎻愰啋
	__ctxPath + '/js/creditFlow/creditAssignment/finance/obligationFundIntentViewVM.js',
	__ctxPath + '/js/creditFlow/creditAssignment/project/investmentObligationRemindView.js'
],
obligationMappingInfoView:[//鍊烘潈搴撴煡璇�
	__ctxPath + '/js/creditFlow/creditAssignment/project/obligationMappingInfoView.js',
	__ctxPath + '/js/creditFlow/creditAssignment/finance/obligationFundIntentViewVM.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/obligationAndIncome.js'
],
investmentEnchashmentListView:[//鍙栫幇瀹℃牳娓呭崟
	__ctxPath + '/js/creditFlow/creditAssignment/finance/investmentEnchashmentListView.js'
],
branchCompanyStastistic :[
	__ctxPath + '/js/creditFlow/creditAssignment/finance/branchCompanyStastistic.js'
]

















//  start:  Generated for BpCustPersonEducation From Template: App.import.js.vm
,BpCustPersonEducationView : [
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonEducationView.js',
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonEducationForm.js'
]

//  end:  Generated for BpCustPersonEducation From Template: App.import.js.vm
//  start:  Generated for BpCustPersonNegativeSurvey From Template: App.import.js.vm
,BpCustPersonNegativeSurveyView : [
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonNegativeSurveyView.js',
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonNegativeSurveyForm.js'
]

//  end:  Generated for BpCustPersonNegativeSurvey From Template: App.import.js.vm
//  start:  Generated for BpCustPersonPublicActivity From Template: App.import.js.vm
,BpCustPersonPublicActivityView : [
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonPublicActivityView.js',
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonPublicActivityForm.js'
]

//  end:  Generated for BpCustPersonPublicActivity From Template: App.import.js.vm
//  start:  Generated for BpCustPersonWorkExperience From Template: App.import.js.vm
,BpCustPersonWorkExperienceView : [
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonWorkExperienceView.js',
    	__ctxPath+'/js/creditFlow.customer.person/BpCustPersonWorkExperienceForm.js'
]
               // end:  Generated for BpCustPersonWorkExperience From Template: App.import.js.vm
//  start:  Generated for BpPersonPhonecheckInfo From Template: App.import.js.vm
,BpPersonPhonecheckInfoView : [
    	__ctxPath+'/js/creditFlow/personrelation/phonecheck/BpPersonPhonecheckInfoView.js'
]

//  end:  Generated for BpPersonPhonecheckInfo From Template: App.import.js.vm
//  start:  Generated for BpPersonNetCheckInfo From Template: App.import.js.vm
,BpPersonNetCheckInfoView : [
    	__ctxPath+'/js/creditFlow/personrelation/netcheck/BpPersonNetCheckInfoView.js'
]
,InvestIntentionView : [
    	__ctxPath+'/js/customer/InvestIntentionView.js',
    	__ctxPath+'/js/customer/InvestPersonList.js',
    	__ctxPath+'/js/customer/InvestPersonForm.js',
    	__ctxPath+'/js/customer/InvestIntentionForm.js'
]
,FundsToPromoteView:[
		__ctxPath+'/js/creditFlow/finance/FundsToPromoteView.js',
		__ctxPath + '/js/creditFlow/finance/FundsToPromoteForm.js',
		__ctxPath + '/js/creditFlow/finance/FundsToPromotePanel.js',
		__ctxPath + '/js/creditFlow/finance/FundsToPromoteWindow.js',
		__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
		__ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
		__ctxPath + '/js/customer/InvestPersonWindowObjList.js',
		__ctxPath + '/js/customer/PromotePackageListWindow.js',
		__ctxPath + '/js/customer/PromotePersonListWindow.js'
]
,MatchFundsTab : [
    	__ctxPath+'/js/creditFlow/finance/MatchFundsTab.js',
		__ctxPath + '/js/creditFlow/finance/MatchFundsView.js',
		__ctxPath + '/js/creditFlow/finance/MatchFundsSuccessView.js',
		__ctxPath + '/js/creditFlow/finance/MatchFundsFailView.js',
		__ctxPath + '/js/creditFlow/finance/MatchFundsAllView.js',
		__ctxPath + '/js/creditFlow/finance/MatchFundsForm.js',
		__ctxPath + '/js/customer/RegistrationIntentForm.js',
		__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
		__ctxPath + '/js/customer/InvestPersonWindowObjList.js',
		__ctxPath + '/js/customer/InvestPersonView.js',
		__ctxPath + '/js/customer/InvestEnterpriseView.js',
		__ctxPath + '/js/customer/InvestEnterpriseForm.js',
		__ctxPath + '/js/customer/CustomeLinkmanGridPanel.js',
		__ctxPath + '/js/customer/InvestPersonForm.js',
		__ctxPath + '/js/customer/InvestPersonInfoPanelView.js',
		__ctxPath + '/js/creditFlow/finance/FundIntent_formulate_editGrid.js',
		__ctxPath + '/js/creditFlow/finance/SlFundIntentFapView.js',
		__ctxPath + '/js/creditFlow/fund/project/platFormFund.js',
    	__ctxPath+'/js/customer/InvestBankNoForm.js',
    	__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommonCredit.js',// 鍔犺浇鏁版嵁JS
    	__ctxPath + '/js/creditFlow/smallLoan/project/loadDataCommonZjpp.js'
],
PromotePackage:[
	__ctxPath + '/publicmodel/uploads/js/uploads.js',
	__ctxPath + '/publicmodel/uploads/js/cs_picViewer.js',
	 __ctxPath + '/publicmodel/uploads/js/cs_showDownload.js',
	 __ctxPath + '/publicmodel/uploads/js/data-view.js',
	 __ctxPath + '/publicmodel/uploads/js/UploadPanel.js',
	 __ctxPath + '/publicmodel/uploads/js/singleUpload.js',
	 __ctxPath + '/publicmodel/uploads/js/swfupload.js',
	 __ctxPath+'/js/creditFlow/finance/PromotePackage.js'
]
,PlBusinessProPlanTab : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirPlanView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirPlanForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessProPlanTab.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlDirBidListForm.js',
    	//鍊烘潈鏍�
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrPlanView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrPlanForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlOrBidListForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrPlanAutomatchForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/BidPlanInfoEditForm.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js'
],
PlBusinessDirPlanView:[
	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirPlanView.js',
	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirPlanForm.js',
	__ctxPath+'/js/creditFlow/financingAgency/business/PlDirBidListForm.js',
	__ctxPath+'/js/creditFlow/financingAgency/BidPlanInfoEditForm.js'
],

PlPersionProPlanTab : [
    	// 涓汉
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirPlanView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirPlanForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidListForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionProPlanTab.js',
    	//鍊烘潈鏍�
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrPlanView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrPlanForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrBidListForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrPlanAutomatchForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/BidPlanInfoEditForm.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js'
    	
]

//  end:  Generated for PlBusinessOrsingeScheme From Template: App.import.js.vm
//  start:  Generated for PlDirsingeProjectKeep From Template: App.import.js.vm
,BpBusinessDirProStat0View : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessDirProStat0View.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',// 璐锋鏉愭枡
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessProKeepTab.js',
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
]
,PlBusinessDirProKeepView : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',// 璐锋鏉愭枡
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
]
,BpBusinessOrProStat0View : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',// 璐锋鏉愭枡
    	__ctxPath+'/js/creditFlow/financingAgency/business/BpBusinessOrProStat0View.js',
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
]
,PlBusinessOrProKeepView : [
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
    	__ctxPath+'/js/selector/OrOrzSelector.js',
    	__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',// 璐锋鏉愭枡
    	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrProKeepView.js',
    	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
    	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
    	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js',
    	__ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
]
,PlKeepGtorzView : [
    	__ctxPath+'/js/creditFlow/financingAgency/typeManger/PlKeepGtorzView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/typeManger/PlKeepGtorzForm.js'
]


,PlPersionBidPublishTab:[
__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionOrBidPublish.js',
__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirBidPublish.js',
__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionBidPublishTab.js',
__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrBidPublish.js',
__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
]
,PlBusinessBidPublishTab:[
__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirBidPublish.js',
__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessOrBidPublish.js',
__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessBidPublishTab.js',
__ctxPath+'/js/creditFlow/finance/CusterFundIntentView.js'
]
,plateInvestDeal : [
    	__ctxPath+'/js/p2p/plateFormDeal/plateInvestDeal.js'
]
,BpCustRelationView : [
    	__ctxPath+'/js/customer/BpCustRelationView.js',
    	__ctxPath+'/js/customer/BpCustRelationForm.js'
]

//  end:  Generated for BpCustRelation From Template: App.import.js.vm
//  start:  Generated for BpBidLoan From Template: App.import.js.vm
,BpBidLoanView : [
    	__ctxPath+'/js/pay/BpBidLoanView.js',
    	__ctxPath+'/js/pay/BpBidLoanForm.js'
]
,PlManageMoneyPlanTypeView : [
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanTypeView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanTypeForm.js'
]
,PlManageMoneyPlanView : [
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanForm.js',
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanOrder.js',
    		__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js'
]
,PlManageMoneyPlanTab:[
       __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanTab.js',
        __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanBidPublish.js',
         __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanBidStart.js',
           __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanForm.js',
            __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanOrder.js',
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js'
]
//  end:  Generated for PlManageMoneyPlanBuyinfo From Template: App.import.js.vm
//  start:  Generated for PlMmObligatoryRightChildren From Template: App.import.js.vm
,PlMmObligatoryRightChildrenView : [
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlMmObligatoryRightChildrenView.js',
    	__ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlMmObligatoryRightChildrenForm.js'
]

//  end:  Generated for PlMmObligatoryRightChildren From Template: App.import.js.vm
//  start:  Generated for PlMmOrderChildrenOr From Template: App.import.js.vm
,PlMmOrderChildrenOrView : [
    	__ctxPath+'/js/creditFlow.financingAgency.manageMoney/PlMmOrderChildrenOrView.js',
    	__ctxPath+'/js/creditFlow.financingAgency.manageMoney/PlMmOrderChildrenOrForm.js'
]
,InvestFundIntentView0:[
		__ctxPath+'/js/creditFlow/finance/InvestFundIntentView0.js'
]
,InvestFundIntentView:[
		__ctxPath+'/js/creditFlow/finance/InvestFundIntentView.js',
		__ctxPath+'/js/creditFlow/finance/Repayment.js'
],ThirdPayConfigView : [
    	__ctxPath+'/js/system/ThirdPayConfigView.js'
],
 NewProjectFormHistory : [
			__ctxPath + '/js/commonFlow/NewProjectFormHistory.js',
			__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
			__ctxPath + '/js/commonFlow/SmallInfoHistory.js',
			__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
			__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js']
//  end:  Generated for PlMmOrderChildrenOr From Template: App.import.js.vm
//  start:  Generated for PlThirdInterfaceLog From Template: App.import.js.vm
,PlThirdInterfaceLogView : [
    __ctxPath+'/js/thirdInterface/PlThirdInterfaceLogView.js',
    __ctxPath+'/js/thirdInterface/PlThirdInterfaceLogForm.js'
],


MemberGradeSet:[//会员等级设置
    __ctxPath+'/js/creditFlow/bonusSystem/gradeSet/MemberGradeSet.js',
    __ctxPath+'/js/creditFlow/bonusSystem/gradeSet/MemberGradeSetForm.js'
]

//  end:  Generated for PlThirdInterfaceLog From Template: App.import.js.vm
//  start:  Generated for WebTemplateMessage From Template: App.import.js.vm
,WebTemplateMessageView : [
    	__ctxPath+'/js/web/WebTemplateMessageView.js',
    	__ctxPath+'/js/web/WebTemplateMessageForm.js'
],BpFinanceApplyView : [
    	__ctxPath+'/js/p2p/BpFinanceApplyView.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyForm.js'
],

WebBankCodeView: [
    	__ctxPath+'/js/thirdInterface/WebBankCodeForm.js',
    	__ctxPath+'/js/thirdInterface/WebBankCodeView.js'
],

//  end:  Generated for WebTemplateMessage From Template: App.import.js.vm
//  start:  Generated for Student From Template: App.import.js.vm


BpComFinanceApplyView : [
                         __ctxPath+'/js/p2p/BpComFinanceApplyView.js',
                         __ctxPath+'/js/p2p/BpFinanceApplyForm.js',
                         __ctxPath+'/js/p2p/BpComApplyNotAccepted.js',
                         __ctxPath+'/js/p2p/BpComApplyAccepted.js',
                         __ctxPath+'/js/p2p/BpComApplyUserOfficialUser.js',
                         __ctxPath+'/js/p2p/BpComApplyUserRejected.js'
]
,BpPerFinanceApplyView : [
                         __ctxPath+'/js/p2p/BpPerFinanceApplyView.js',
                         __ctxPath+'/js/p2p/BpFinanceApplyForm.js',
                         __ctxPath+'/js/p2p/BpPerApplyNotAccepted.js',
                         __ctxPath+'/js/p2p/BpPerApplyAccepted.js',
                         __ctxPath+'/js/p2p/BpPerApplyUserOfficialUser.js',
                         __ctxPath+'/js/p2p/BpPerApplyUserRejected.js'
],





//  end:  Generated for Student From Template: App.import.js.vm
//  start:  Generated for BpFinanceApplyUser From Template: App.import.js.vm
                                                   
BpFinanceApplyUserNewView:[
	__ctxPath+'/js/p2p/BpFinanceApplyUserNewView.js',
	__ctxPath+'/js/p2p/BpFinanceApplyUserTypeView.js',
	__ctxPath+'/js/p2p/P2PBpCustMemberForm.js',
	__ctxPath + '/js/commonFlow/CreateNewProjectFrom.js',
	__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
	__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
	__ctxPath + '/js/commonFlow/NewProjectFormEnterPrise.js',
	__ctxPath + '/js/selector/UserDialog.js',
	__ctxPath + '/js/commonFlow/ExtUD.Ext.js',
	__ctxPath + '/js/creditFlow/report/SlCompanyInfoView.js',
	__ctxPath + '/js/creditFlow/customer/enterprise/public.js',
	__ctxPath + '/js/creditFlow/common/EnterpriseShareequity.js',
	__ctxPath + '/js/creditFlow/creditAssignment/customer/shop.js'
],
BpFinanceApplyUserView : [
    	__ctxPath+'/js/p2p/BpFinanceApplyUserView.js',
    	//__ctxPath+'/js/p2p/LookApply.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserForm.js',
    	__ctxPath+'/js/p2p/BpCustMemberForm.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserNotAcceptView.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserNotSubmitView.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserSupplementaryMaterials.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserUploadMaterials.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserPassed.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserFlowReject.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserAcceptView.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserApproved.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserOfficialUser.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserRejected.js',
    	__ctxPath+'/js/p2p/BpFinanceApplyUserBid.js',
    	__ctxPath+'/js/p2p/materials/BpCustMemberPicView.js'
]
,
BidPlanLoanedView : [
    	__ctxPath+'/js/creditFlow/financingAgency/BidPlanLoanedView.js',
    	__ctxPath+"/js/creditFlow/finance/SeeFundIntentView.js",
		__ctxPath + '/js/creditFlow/finance/BpFundIntentFapView.js',
		__ctxPath + '/js/creditFlow/finance/CusterFundIntentView.js'
],
PlbidplanLoanManager:[
	__ctxPath + '/js/creditFlow/financingAgency/PlbidplanLoanManager.js',
	__ctxPath + '/js/creditFlow/financingAgency/PlBidPlanInfoCusterFundIntent.js'
]
,
SlFundIntentPeriodOrView : [//债权标还款管理
    	__ctxPath+'/js/creditFlow/finance/fundintentmerge/SlFundIntentPeriodOrView.js',
    	__ctxPath+'/js/creditFlow/finance/fundintentmerge/BpFundIntentPeriodView.js'
],

//平台账户台账View表单
PlateFormAccountManagerView:[
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountManager/PlateFormAccountManagerView.js',
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountManager/PlateFormCreateAccountForm.js',
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountManager/PlateFormAccountRechargeForm.js',
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountManager/PlateFormAccountWithdraw.js'
],

//担保账户台账view表单
PlateFormGuaranteeAccountManagerView:[
__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountManager/PlateFormGuaranteeAccountManagerView.js'

],
//风险保证金代偿台账
PlateFormFianceRiskAccountPayDetailView:[
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceRiskAccountPayDetailView.js',
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/LoanerRepaymentRecord.js'
],
//随息台账
PlateFormFianceBidIncomeDetailView:[
	__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceBidIncomeDetailView.js'
],
//一次性收费台账
PlateFormFianceOneTimeFeeDetailView:[
__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceOneTimeFeeDetailView.js'
],

//担保费台账
PlateFormFianceGuaranteeAccountIncomeDetailView:[
__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceGuaranteeAccountIncomeDetailView.js'
],
//会费台账
PlateFormFianceMembershipFeesDetailView:[
__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceMembershipFeesDetailView.js'

],
//代金券发放台账
PlateFormFianceVoucherDetailView:[
__ctxPath + '/js/creditFlow/finance/plateFormFinanceManage/PlateFormAccountDetailFormManager/PlateFormFianceVoucherDetailView.js'

],
//系统账户配置信息
SystemAccountSettingInfoView:[
	__ctxPath+"/js/creditFlow/creditAssignment/bank/SystemAccountSettingInfoView.js",
	__ctxPath+"/js/creditFlow/creditAssignment/bank/EditSystemAccountSettingItem.js"
],

CsSalesRecordView:[
	__ctxPath + '/js/customer/salesRecord/CsSalesRecordView.js',
	__ctxPath + '/js/customer/salesRecord/CsSalesRecordForm.js',
	__ctxPath + '/js/system/selectAppUser.js'
]
,UploadLogView : [
    	__ctxPath+'/js/system/UploadLogView.js',
    	__ctxPath+'/js/system/UploadLogForm.js'
]
,PlBidSaleTab : [
    	__ctxPath+'/js/creditFlow/financingAgency/sale/PlBidSaleTab.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/PlBidSaleList.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/transferingList.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/transferedList.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/closeedList.js',
    	__ctxPath+'/js/creditFlow/financingAgency/sale/transfereToOrList.js',
		__ctxPath+'/js/creditFlow/financingAgency/sale/ingTransferList.js',
		__ctxPath+'/js/creditFlow/financingAgency/sale/ConfirmTransferFeeWindow.js'
]
,
AllBidPlanView:[
	__ctxPath+'/js/creditFlow/financingAgency/AllBidPlanView.js',
	__ctxPath+'/js/creditFlow/financingAgency/BidPlanInfoEditForm.js',
	__ctxPath+'/js/creditFlow/financingAgency/persion/PlPersionDirProKeepForm.js',
	__ctxPath+'/js/creditFlow/financingAgency/business/PlBusinessDirProKeepForm.js',
	__ctxPath+'/js/selector/OrOrzSelector.js',
	__ctxPath + '/js/creditFlow/smallLoan/materials/SlProcreditSmallLoanMaterialsView.js',
	__ctxPath+'/js/p2p/materials/P2pShowProcreditMaterialsView.js',
	__ctxPath+'/js/p2p/materials/P2pFileUpload.js',
	__ctxPath+'/js/p2p/materials/P2pMaterialsDownLoad.js'

],
PageSettingView : [
			__ctxPath + '/js/creditFlow/common/PageSettingView.js',
			__ctxPath + '/js/creditFlow/common/PageSettingForm.js'
],
PersonalDesktop : [
		__ctxPath + '/js/team/PersonalDesktop.js'
]
,

CsCooperationAccountManage : [
	__ctxPath+'/js/creditFlow/customer/cooperation/CsCooperationAccountManage.js'
],
CsCooperationAccountDetail : [
	__ctxPath+'/js/creditFlow/customer/cooperation/CsCooperationAccountDetail.js'
]
,SystemServiceUpdateView : [
		__ctxPath+'/js/system/update/SystemUploadWin.js',
		__ctxPath+'/js/system/update/SystemServiceUpdateView.js',
    	__ctxPath+'/js/system/update/SystemServiceUpdateForm.js',
    	__ctxPath+'/js/system/update/SystemServiceUpdateUpLoadWin.js'
],

UPlanTab:[
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanTab.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanBidPublish.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanForm.js',
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/UPlanOrder.js',
        __ctxPath+'/js/creditFlow/financingAgency/manageMoney/PlManageMoneyPlanCoupons.js',
        __ctxPath+'/js/p2p/materials/SlSubjectLogoUpload.js'
],
PlMmChildrenObligatoryViewUPlan:[
        __ctxPath+'/js/creditFlow/financingAgency/UPlan/PlMmChildrenObligatoryViewUPlan.js',
        __ctxPath+'/js/creditFlow/financingAgency/manageMoney/ChangeObligationEndTime.js'
],
YgIndexShowView : [
	__ctxPath+'/js/p2p/ygIndexShow/YgIndexShowView.js',
	__ctxPath+'/js/p2p/ygIndexShow/YgIndexShowForm.js'

]
,

enterpriseFianceAccount:[
	__ctxPath+'/js/creditFlow/customer/enterprise/enterpriseFianceAccount.js'
],
EnterPriseP2PView:[
	__ctxPath+'/js/creditFlow/customer/enterprise/EnterPriseP2PView.js',
	__ctxPath + '/js/customer/BpCustRelationForm.js'
]

,CoreParameterConfigForm : [
    	__ctxPath+'/js/p2p/CoreParameterConfigForm.js'
]


//  end:  Generated for OaNewsMessagerinfo From Template: App.import.js.vm
//  start:  Generated for BusPayAccount From Template: App.import.js.vm
,BusPayAccountView : [
    	__ctxPath+'/js/p2p/BusPayAccountView.js',
    	__ctxPath+'/js/p2p/BusPayAccountForm.js'
],
PlBidPlanView:[
		__ctxPath+'/js/creditFlow/financingAgency/PlBidPlanView.js'
],
compensatoryFianceAll:[
		__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryFianceAll.js',
		__ctxPath+'/js/creditFlow/finance/overdueManager/compensatoryFiance.js'

]
,TaskProxyView : [
    	__ctxPath+'/js/flow/TaskProxyView.js',
    	__ctxPath+'/js/flow/TaskProxyForm.js'
]

,BatchRunRecordView : [
    	__ctxPath+'/js/creditFlow/log/BatchRunRecordView.js',
    	__ctxPath+'/js/creditFlow/log/BatchRunRecordForm.js'
],ReportPlLoanBid : [
		__ctxPath+'/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportPlLoanBid.js'
]
//  end:  Generated for TaskProxy From Template: App.import.js.vm
//  start:  Generated for SettlementReviewerPay From Template: App.import.js.vm
,SettlementReviewerPayView : [
    	__ctxPath+'/js/creditFlow.fund.project/SettlementReviewerPayView.js',
    	__ctxPath+'/js/creditFlow.fund.project/SettlementReviewerPayForm.js'
]

//  end:  Generated for SettlementReviewerPay From Template: App.import.js.vm
//  start:  Generated for SettlementInfo From Template: App.import.js.vm
,SettlementInfoView : [
    	__ctxPath+'/js/creditFlow.fund.project/SettlementInfoView.js',
    	__ctxPath+'/js/creditFlow.fund.project/SettlementInfoForm.js'
],ReportInvestReMainMoney : [
	__ctxPath+'/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportInvestReMainMoney.js'
],ReportFinanceReMainMoney : [
	__ctxPath+'/js/creditFlow/report/InternetReport/loosePlPlanBid/ReportFinanceReMainMoney.js'
],ThirdPayLogView:[
	__ctxPath+'/js/p2p/thirdPayLog/ThirdPayLogView.js'
],LoanEnterpriseQuota : [
        __ctxPath+'/js/commonFlow/LoanEnterpriseQuota.js'
]

//  end:  Generated for SettlementInfo From Template: App.import.js.vm
//  start:  Generated for OaHolidayMessage From Template: App.import.js.vm


//  end:  Generated for OaHolidayMessage From Template: App.import.js.vm
};
/**客户中心
 * 
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.customer.js"></script>');
/**
 * 贷款业务
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.loanBusiness.js"></script>');
/**
 * 财富业务
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.treasureBusiness.js"></script>');
/**
 * 信用借款业务，P2P中介业务,C2P中介业务,PA2P中介业务，CA2P业务
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.honorBorrow.js"></script>');
/**
 * 债权交易
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.saleTransaction.js"></script>');
/**
 * D债权转让业务，U债权转让业务
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.udPlanTransfer.js"></script>');
/**
 * 活期理财业务
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.hrBao.js"></script>');
/**
 * 运营中心
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.p2pCentre.js"></script>');
/**
 * 风控中心
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.controlCentre.js"></script>');
/**
 * 台账报表
 */
document.writeln('<script type="text/javascript" src="'+__ctxPath+'/js/App.import.report.js"></script>');