/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
PlPawnProjectManager = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projectStatus) != "undefined") {
			this.projectStatus = parseInt(_cfg.projectStatus);
		}
		this.isGrantedShowAllProjects = isGranted('_seeAllPawnPro_p'+ this.projectStatus);// 是否授权显示所有项目记录
		switch (this.projectStatus) {
			case 0 :
				this.titlePrefix = "审批中业务";
				this.tabIconCls = "btn-tree-team17";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
			case 1 :
				this.titlePrefix = "在当业务";
				this.tabIconCls = "btn-tree-team18";
				break;
			case 3 :
				this.titlePrefix = "提前终止业务";
				this.tabIconCls = "btn-tree-team18";
				break;
			case 4 :
				this.titlePrefix = "续当业务";
				this.tabIconCls = "btn-tree-team19";
				break;
			case 5 :
				this.titlePrefix = "赎当业务";
				this.tabIconCls = "btn-tree-team20";
				break;
			case 6 :
				this.titlePrefix = "绝当业务";
				this.tabIconCls = "btn-tree-team16";
				break;
			case 7 :
				this.titlePrefix = "全部典当业务";
				this.tabIconCls = "btn-tree-team16";
				break;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PlPawnProjectManager.superclass.constructor.call(this, {
			id : 'PlPawnProjectManager_' + this.projectStatus,
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : this.tabIconCls,
			items : [{
				xtype : 'label',
				text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看您的上下级关系是否设置正确!(查询授权规律为：您可以查看自己或您下属的项目)',
				hidden : true
			}, this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';

		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
					name : 'projectStatus',
					xtype : 'hidden',
					value : this.projectStatus
				}, {
					name : 'isGrantedShowAllProjects',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '当票号',
						name : 'phnumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '客户名称',
						name : 'customerName',
						anchor : "98%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth : .2,
		     		layout : 'form',
		     		labelWidth : 80,
		     		labelAlign : 'right',
		     		border : false,
		     		hidden : (this.projectStatus==7)?false:true,
		     		items : [{
			     		xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["审批中项目", "0"],
										["在当项目", "1"],
										["续当项目", "4"],
										["赎当项目", "5"],
										["绝当项目", "6"],
										["提前终止项目", "3"],
										["全部项目", ""]]
						}),
						triggerAction : "all",
						hiddenName:"pawnProjectStatus",
						fieldLabel : '项目状态',
				        anchor : "100%"
		     		
		     		}]
		     	}, this.isHiddenBranch == false ?{
					columnWidth :.2,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属分公司',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "companyId",
						displayField : 'companyName',
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
					}]
				}:{ 
					columnWidth:0.001,
			     	border:false
		     	},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.07,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						xtype : 'button',
						scope : this,
						width : 40,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		if (this.projectStatus == 0) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePawnPro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePawnPro_p' + this.projectStatus)
							|| !isGranted('_detailPawnPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-edit',
					text : '编辑典当详情',
					xtype : 'button',
					hidden : isGranted('_detailPawnPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_detailPawnPro_p' + this.projectStatus)
							|| !isGranted('_stopPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-close1',
					text : '终止项目',
					xtype : 'button',
					hidden : isGranted('_stopPawnPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						stopPro(this.gridPanel, 'PlPawnProjectInfo_',
								'PlPawnProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_stopPawnPro_p' + this.projectStatus)
							|| !isGranted('_removePawnPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-del',
					text : '删除项目',
					xtype : 'button',
					hidden : isGranted('_removePawnPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						removePro(this.gridPanel, 'PlPawnProjectInfo_',
								'PlPawnProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_removePawnPro_p' + this.projectStatus)
							|| !isGranted('_pawnyjysm_p' + this.projectStatus)
				}),{
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawnyjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_pawnyjysm_p' + this.projectStatus)
							|| !isGranted('_showPawnFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showPawnFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}/*, new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}), {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false : true,
					handler : function() {
						var searchPanel = this.searchPanel;
						var tabs = Ext.getCmp('centerTabPanel');
						var gpObj = document
								.getElementById('ReportPreviewprojectdetail');
						if (gpObj != null) {
							tabs.remove('ReportPreviewprojectdetail');
						}
						gpObj = ReportMenuSmallloanprojectdetail({
							reportKey : "projectdetail",
							projectMoney : searchPanel
									.getCmpByName('Q_projectMoney_BD_GE')
									.getValue(),
							projectMoneyl : searchPanel
									.getCmpByName('Q_projectMoney_BD_LE')
									.getValue(),

							projectNumber : searchPanel
									.getCmpByName('Q_projectNumber_S_LK')
									.getValue(),
							projectName : searchPanel
									.getCmpByName('Q_projectName_S_LK')
									.getValue(),
							oppositeTypeValue : searchPanel
									.getCmpByName('Q_oppositeTypeValue_S_EQ')
									.getValue(),
							customerName : searchPanel
									.getCmpByName('Q_customerName_S_LK')
									.getValue(),
							businessManagerid : searchPanel
									.getCmpByName('Q_businessManager_S_LK')
									.getValue(),
							businessManager : searchPanel
									.getCmpByName('businessManager').getValue(),

							startDateg : searchPanel
									.getCmpByName(this.startDateName)
									.getValue(),
							startDatel : searchPanel
									.getCmpByName(this.endDateName).getValue(),
							companyId: searchPanel.getCmpByName('companyId').getRawValue(),
							projectStatus : "办理中贷款"
						});
						tabs.add(gpObj);
						tabs.setActiveTab("ReportPreviewprojectdetail");
					}
				}*/]
			});
		} 
		else if(this.projectStatus == 1 || this.projectStatus == 4){
		     this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePawnPro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePawnPro_p' + this.projectStatus)
							|| !isGranted('_detailPawnPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-edit',
					text : '编辑典当详情',
					xtype : 'button',
					hidden : isGranted('_detailPawnPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_detailPawnPro_p' + this.projectStatus)
							|| !isGranted('_pawnyjysm_p' + this.projectStatus)
				}), {
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawnyjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_pawnyjysm_p' + this.projectStatus)
							|| !isGranted('_showPawnFlowImg_p' + this.projectStatus)
				}),{
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showPawnFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}/*, new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-zmexport',
					text : '打印发票',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showFlowImg_p' + this.projectStatus)? false: true,
					handler : function() {
						var rows = this.gridPanel.getSelectionModel().getSelections();
						var list = "";
						var len = rows.length;
					    for (var j = 0; j < len; j++) {
							if (j == (len - 1)) {
								list += rows[j].data.projectId;
							} else
								list += rows[j].data.projectId + ",";
						}
						if (0 == len) {
							Ext.MessageBox.alert('状态', '请选择一条记录!');
							return false;
						}
						var url=__ctxPath+"/creditFlow/printfkWorkOrderCreditProject.do?ids="+list+"&reportKey=tj_zyht_sl_jkjj";
						var ifMerge = 110;  
			           	var flag = '1'; //用来标识是打印还是打印预览：0表示打印，1表示打印预览  
			           	var width = screen.width-100;  
			           	var height = screen.height-18
						var newWindow=window.open("", "newwin");
					    var newContent = "<html><head><title>A New Doc</title></head>"
					    newContent += "<body bgcolor='coral'><h1>This document is brand new.</h1>"
					    newContent += "</body></html>"
					    newWindow.document.write("<div  style='background-color:#EEEEEE'><div><center><object classid = 'clsid:8AD9C840-044E-11D1-B3E9-00805F499D93'"+  
	                    "codebase = 'http://java.sun.com/products/plugin/1.3/jinstall-13-win32.cab#Version=1,3,0,0'"+  
	                    "WIDTH = '"+width+"' HEIGHT = '"+height+"' >"+  
	                    "<PARAM NAME = CODE VALUE = 'org.cssm.tos.action.JRPrinterApplet.class'>"+  
	                    "<PARAM NAME = CODEBASE VALUE = 'applet/'>"+  
	                    "<PARAM NAME = ARCHIVE  VALUE = 'reportprint.jar,commons-collections-3.1.jar,commons-logging.jar,rt.jar,print.cer'>"+  
	                    "<param name = 'type' value = 'application/x-java-applet;version=1.6'>"+  
	                    "<PARAM NAME = 'scriptable'  VALUE='false'>"+  
	                    "<PARAM NAME = 'REPORT_FLAG' VALUE="+flag+">"+  
	                    "<PARAM NAME = 'REPORT_URL'  VALUE=\""+url+"\">"+  
	                    "</object></center></div></div>")
	                    newWindow.document.close();
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}), {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false : true,
					handler : function() {
						var status = "";
						if (this.projectStatus == 1) {
							status = "放款后贷款";
						} else if (this.projectStatus == 2) {
							status = "已完成贷款";
						} else if (this.projectStatus == 5) {
							status = "展期中贷款";
						}else if (this.projectStatus == 7) {
								status = "全部贷款业务";
						}else if (this.projectStatus == 8) {
							status = "违约贷款";
						}

						var searchPanel = this.searchPanel;
						var tabs = Ext.getCmp('centerTabPanel');
						var gpObj = document
								.getElementById('ReportPreviewprojectdetail');
						if (gpObj != null) {
							tabs.remove('ReportPreviewprojectdetail');
						}
						gpObj = ReportMenuSmallloanprojectdetail({
							reportKey : "projectdetail",
							projectMoney : searchPanel
									.getCmpByName('Q_projectMoney_BD_GE')
									.getValue(),
							projectMoneyl : searchPanel
									.getCmpByName('Q_projectMoney_BD_LE')
									.getValue(),

							projectNumber : searchPanel
									.getCmpByName('Q_projectNumber_S_LK')
									.getValue(),
							projectName : searchPanel
									.getCmpByName('Q_projectName_S_LK')
									.getValue(),
							oppositeTypeValue : searchPanel
									.getCmpByName('Q_oppositeTypeValue_S_EQ')
									.getValue(),
							customerName : searchPanel
									.getCmpByName('Q_customerName_S_LK')
									.getValue(),
							businessManagerid : searchPanel
									.getCmpByName('Q_businessManager_S_LK')
									.getValue(),
							businessManager : searchPanel
									.getCmpByName('businessManager').getValue(),

							startDateg : searchPanel
									.getCmpByName(this.startDateName)
									.getValue(),
							startDatel : searchPanel
									.getCmpByName(this.endDateName).getValue(),
							projectStatus : status,
							companyId: searchPanel.getCmpByName('companyId').getRawValue()
							
						});
						tabs.add(gpObj);
						tabs.setActiveTab("ReportPreviewprojectdetail");
					}
				}*/]
			});
		}
		else if (this.projectStatus == 5
				|| this.projectStatus == 6 || this.projectStatus == 3 ) {
			    this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePawnPro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePawnPro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus)
				})/*, {
					iconCls : 'btn-edit',
					text : '编辑项目',
					xtype : 'button',
					hidden : isGranted('_detailPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_detailPro_p' + this.projectStatus)
							|| !isGranted('_yjysm_p' + this.projectStatus)
				})*/,{
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawnyjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_pawnyjysm_p' + this.projectStatus)
							|| !isGranted('_showPawnFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showPawnFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}/*, new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}), {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false : true,
					handler : function() {
						var status = "";
						if (this.projectStatus == 1) {
							status = "放款后贷款";
						} else if (this.projectStatus == 2) {
							status = "已完成贷款";
						} else if (this.projectStatus == 5) {
							status = "展期中贷款";
						}else if (this.projectStatus == 7) {
								status = "全部贷款业务";
						}else if (this.projectStatus == 8) {
							status = "违约贷款";
						}

						var searchPanel = this.searchPanel;
						var tabs = Ext.getCmp('centerTabPanel');
						var gpObj = document
								.getElementById('ReportPreviewprojectdetail');
						if (gpObj != null) {
							tabs.remove('ReportPreviewprojectdetail');
						}
						gpObj = ReportMenuSmallloanprojectdetail({
							reportKey : "projectdetail",
							projectMoney : searchPanel
									.getCmpByName('Q_projectMoney_BD_GE')
									.getValue(),
							projectMoneyl : searchPanel
									.getCmpByName('Q_projectMoney_BD_LE')
									.getValue(),

							projectNumber : searchPanel
									.getCmpByName('Q_projectNumber_S_LK')
									.getValue(),
							projectName : searchPanel
									.getCmpByName('Q_projectName_S_LK')
									.getValue(),
							oppositeTypeValue : searchPanel
									.getCmpByName('Q_oppositeTypeValue_S_EQ')
									.getValue(),
							customerName : searchPanel
									.getCmpByName('Q_customerName_S_LK')
									.getValue(),
							businessManagerid : searchPanel
									.getCmpByName('Q_businessManager_S_LK')
									.getValue(),
							businessManager : searchPanel
									.getCmpByName('businessManager').getValue(),

							startDateg : searchPanel
									.getCmpByName(this.startDateName)
									.getValue(),
							startDatel : searchPanel
									.getCmpByName(this.endDateName).getValue(),
							projectStatus : status,
							companyId: searchPanel.getCmpByName('companyId').getRawValue()
							
						});
						tabs.add(gpObj);
						tabs.setActiveTab("ReportPreviewprojectdetail");
					}
				}*/]
			});
		} else{
			 this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看典当详情',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePawnPro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePawnPro_p' + this.projectStatus)
							|| !isGranted('_detailPawnPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-edit',
					text : '编辑典当详情',
					xtype : 'button',
					hidden : isGranted('_detailPawnPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						detailPro(this.gridPanel, 'PlPawnProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_detailPawnPro_p' + this.projectStatus)
							|| !isGranted('_yjysm_p' + this.projectStatus)
				}),{
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_pawnyjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_pawnyjysm_p' + this.projectStatus)
							|| !isGranted('_showPawnFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showPawnFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}]
			});
		}
		this.gridPanel = new HT.GridPanel({
			name : 'PawnProjectGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/creditFlow/pawn/project/projectListPlPawnProject.do",
			baseParams : {
				'projectStatus' : this.projectStatus,
				'isGrantedShowAllProjects' : this.isGrantedShowAllProjects,
				'keyWord' : this.keyWord
			},
			fields : [{
				name : 'runId',
				type : 'long'
			}, 'projectId','subject', 'creator', 'userId', 'defId', 'piId',
					'createtime', 'runStatus', 'processName', 'businessType',
					 'projectNumber', 'accrual',
					'customerName', 'projectStatus', 'operationType',
					'appUserId', 'companyId', 'comprehensiveCost',
					'createDate', 'dayOfEveryPeriod', 'flowType', 'intentDate',
					'isInterestByOneTime', 'isPreposePayAccrual',
					'isStartDatePay', 'mineId', 'mineType',
					'monthFeeRate', 'operationType', 'oppositeID',
					'oppositeType','overdueRate','overdueRateLoan','pawnApplication',
					'pawnType','payaccrualType','payintentPeriod','payintentPerioDate',
					'phnumber','projectMoney','projectStatus','remarks','startDate','taskId',
					'activityName','taskCreateTime','endTime','taskLimitTime','piDbid','projectName',
					'appUserName','executor','orgName','continueCount','continuedstartDate','continuedIntentDate','redeemDate','vastDate'],

			columns : [{
				header : '所属分公司',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			}, {
				header : '当票号',
				width : 130,
				dataIndex : 'phnumber'
			}, {
				header : '客户名称',
				dataIndex : 'customerName'
			}, {
				header : '典当开始日期',
				dataIndex : 'startDate'
			}, {
				header : '计划到期日期',
				dataIndex : 'intentDate'
			}, {
				header : '典当金额',
				align : 'right',
				width : 110,
				sortable : true,
				dataIndex : 'projectMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00元";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '续当开始日期',
				hidden : (this.projectStatus==4)?false:true,
				dataIndex : 'continuedstartDate'
			}, {
				header : '续当到期日期',
				hidden : (this.projectStatus==4)?false:true,
				dataIndex : 'continuedIntentDate'
			}, {
				header : '项目经理',
				width : 70,
				dataIndex : 'appUserName'
			},{
				header : '项目阶段',
				width : 80,
				hidden : (this.projectStatus==0)?false:true,
				dataIndex : 'activityName'
			}, {
				header : '执行人',
				width : 70,
				hidden : (this.projectStatus==0)?false:true,
				dataIndex : 'executor'
			}, {
				header : '启动时间',
				hidden : (this.projectStatus==0)?false:true,
				dataIndex : 'createDate'
			},{
				header : '续当次数',
				hidden : (this.projectStatus==4 || this.projectStatus==5 || this.projectStatus==6 )?false:true,
				dataIndex : 'continueCount'
			},{
				header : '赎当日期',
				hidden : (this.projectStatus==5)?false:true,
				dataIndex : 'redeemDate'
			},{
				header : '绝当日期',
				hidden : (this.projectStatus==6)?false:true,
				dataIndex : 'vastDate'
			}]
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);
		/*if (!this.isGrantedShowAllProjects) {
			this.gridPanel.getStore().on("load", function(store) {
				if (store.getCount() < 1) {
					this.get(0).setVisible(true);
					this.searchPanel.setVisible(false);
					this.gridPanel.setVisible(false);
					this.doLayout();
				}
			}, this)
		}*/
	},// end of the initComponents()
	
		// 查看意见与说明记录 
	flowRecords : function() {
		var selRs = this.gridPanel.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		var runId =selRs[0].get('runId');
		var businessType=selRs[0].get('businessType');
		new SlProcessRunView({
						runId : runId,
						businessType : businessType
					}).show();
	},
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
