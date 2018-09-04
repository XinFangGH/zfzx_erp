/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
SmallLoanProjectManager = Ext.extend(Ext.Panel, {
	projectStatus : 0,
	titlePrefix : "",
	tabIconCls : "",
	isHiddenBd : true,
	isHiddenAn : true,
	isHiddenCd : true,
	isHiddenSd : true,
	isHiddenPs : true,
	isHiddenSuperviseIn : true,
	isHiddenBreachComment : true,
	dateLabel : "启动时间",
	startDateName : "Q_startDate_D_GE",
	endDateName : "Q_startDate_D_LE",
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projectStatus) != "undefined") {
			this.projectStatus = parseInt(_cfg.projectStatus);
		}
		this.isGrantedShowAllProjects = isGranted('_seeAllPro_p'
				+ this.projectStatus);// 是否授权显示所有项目记录
		switch (this.projectStatus) {
			case 0 :
				this.titlePrefix = "办理中项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
			case 1 :
				this.titlePrefix = "还款中项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				this.isHiddenSuperviseIn = false;
				break;
			case 2 :
				this.titlePrefix = "已完成项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenCd = false;
				this.dateLabel = "完成时间";
				this.startDateName = "Q_endDate_D_GE";
				this.endDateName = "Q_endDate_D_LE";
				break;
			case 3 :
				this.titlePrefix = "已终止项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenSd = false;
				this.isHiddenAn = false;
				this.dateLabel = "终止时间";
				this.startDateName = "Q_endDate_D_GE";
				this.endDateName = "Q_endDate_D_LE";
				break;
			case 7 :
				this.titlePrefix = "放款后综合查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				this.isHiddenPs = false;
				break;
			case 8 :
				this.titlePrefix = "已违约项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				this.isHiddenBreachComment = false;
				break;
			case 4 :
				this.titlePrefix = "已展期项目查询";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
			case 15 :
				this.titlePrefix = "挪用处理";
				this.tabIconCls = "btn-tree-team2";
				this.isHiddenBd = false;
				this.isHiddenAn = false;
				break;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		SmallLoanProjectManager.superclass.constructor.call(this, {
			id : 'SmallLoanProjectManager_' + this.projectStatus,
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
		var startDate = {
			columnWidth : .14,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 60,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : this.dateLabel,
				name : this.startDateName,
				xtype : 'datefield',
				format : 'Y-m-d'

			}]
		};
		var endDate = {
			columnWidth : .112,
			labelAlign : 'right',
			xtype : 'container',
			layout : 'form',
			labelWidth : 25,
			defaults : {
				anchor : anchor
			},
			items : [{
				fieldLabel : '到',
				name : this.endDateName,
				xtype : 'datefield',
				labelSeparator : "",
				format : 'Y-m-d'
			}]
		};
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 70,
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
					name : 'isAll',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},{
					columnWidth : 1,
					layout : 'column',
					border : false,
					items : [{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '项目编号',
						name : 'projectNumber',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '项目名称',
						name : 'projectName',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items:[{
						fieldLabel : "客户类别",
						xtype : "dicIndepCombo",
						displayField : 'itemName',
						nodeKey : 'customerType',
						editable : false,
						anchor : "100%",
						hiddenName : 'oppositeType'
						
					}]
	     		},{
					columnWidth : .25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						xtype : "dickeycombo",
						hiddenName : "archivesBelonging",
						nodeKey : 'archivesBelonging', // xx代表分类名称
						fieldLabel : "档案归属地",
						readOnly : this.isRead,
						editable : false,
						anchor : "100%",
						listeners : {
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
					       }
						}
				
					}]
				},{
	     			columnWidth :.14,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '金额范围',
						name : 'Q_projectMoney_BD_GE',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.11,
					layout : 'form',
					labelWidth : 20,
					labelAlign : 'right',
					border : false,
					items :[{
						fieldLabel : '到',
						labelSeparator : "",
						name : 'Q_projectMoney_BD_LE',
						anchor : "100%",
						xtype : 'numberfield'
					}]
	     		},{
	     			columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items :[{
						name : 'businessManager',
						xtype : 'trigger',
						fieldLabel : '项目经理',
						submitValue : true,
						triggerClass : 'x-form-search-trigger',
						editable : false,
						scope : this,
						anchor : "100%",
						onTriggerClick : function() {
							var obj = this;
							var appuerIdObj = obj.nextSibling();
							var userIds = appuerIdObj.getValue();
							if ("" == obj.getValue()) {
								userIds = "";
							}
							new UserDialog({
								userIds : userIds,
								userName : obj.getValue(),
								single : false,
								title : "选择项目经理",
								callback : function(uId, uname) {
									obj.setValue(uId);
									obj.setRawValue(uname);
									appuerIdObj.setValue(uId);
								}
							}).show();

						}
					}, {
						xtype : "hidden",
						name : 'Q_businessManager_S_LK'
					}]
	     		},{
					columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '所属门店',
						xtype : "combo",
						anchor : "100%",
						hiddenName : "orgUserId",
						displayField : 'orgUserName',
						name :"orgUserId",
						valueField : 'orgUserId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/system/getOrgUserNameOrganization.do',
							fields : ['orgUserId', 'orgUserName']
						})
					}]
				}, this.isHiddenBranch == false ?{
					columnWidth :.18,
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
						name :"companyId",
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
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						width : 30,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
				}]
		});
		if (this.projectStatus == 0) {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看项目',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus)
				}), {
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
							|| !isGranted('_stopPro_p' + this.projectStatus)
				}), {
					iconCls : 'btn-close1',
					text : '终止项目',
					xtype : 'button',
					hidden : isGranted('_stopPro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						stopPro(this.gridPanel, 'SmallLoanProjectInfo_',
								'SmallLoanProjectInfoEdit_','Bpfund')//最后一个参数用于区分是修改sl_smallloan_project还是bp_fund_project
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_stopPro_p' + this.projectStatus)
							|| !isGranted('_removePro_p' + this.projectStatus)
				})/*, {
					iconCls : 'btn-del',
					text : '删除项目',
					xtype : 'button',
					hidden : isGranted('_removePro_p' + this.projectStatus)
							? false
							: true,
					scope : this,
					handler : function() {
						removePro(this.gridPanel, 'SmallLoanProjectInfo_',
								'SmallLoanProjectInfoEdit_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_removePro_p' + this.projectStatus)
							|| !isGranted('_yjysm_p' + this.projectStatus)
				})*/,{
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_yjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_yjysm_p' + this.projectStatus)
							|| !isGranted('_showFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}, /*new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),*/ {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false : */true,
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
				}]
			});
		} 
		else if(this.projectStatus == 1 ){
		     this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看项目',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus)
				}), {
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
				}), {
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_yjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_yjysm_p' + this.projectStatus)
							|| !isGranted('_showFlowImg_p' + this.projectStatus)
				}),{
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),/* {
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
				},*//* new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),*/ {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false :*/ true,
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
				}]
			});
		}
		else if (this.projectStatus == 2
				|| this.projectStatus == 5 || this.projectStatus == 7
				|| this.projectStatus == 8) {
			    this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看项目',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus)
				}), {
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
				}),{
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_yjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_yjysm_p' + this.projectStatus)
							|| !isGranted('_showFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				},/* new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),*/ {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false :*/ true,
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
				}]
			});
		} else if (this.projectStatus == 15) {
			this.topbar = new Ext.Toolbar({
				items : [ {
					iconCls : 'btn-edit',
					text : '挪用处理',
					xtype : 'button',
				/*	hidden : isGranted('_detailPro_p' + this.projectStatus)
							? false
							: true,*/
					scope : this,
					handler : function() {
					var grid=this.gridPanel;
					var selRs = grid.getSelectionModel().getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = grid.getSelectionModel().getSelected();
					new StartDivertPanel({record:record}).show();
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_detailPro_p' + this.projectStatus)
							|| !isGranted('_showFlowImg_p' + this.projectStatus)
				}), {
					iconCls : 'btn-flow-chart',
					text : '挪用详情',
					xtype : 'button',
					scope : this,
				/*	hidden : isGranted('_showFlowImg_p' + this.projectStatus)
							? false
							: true,*/
					handler : function() {
					var grid=this.gridPanel;
					var selRs = grid.getSelectionModel().getSelections();
					if (selRs.length == 0) {
						Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
						return;
					}
					if (selRs.length > 1) {
						Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
						return;
					}
					var record = grid.getSelectionModel().getSelected();
					new DivertList({record:record}).show();
					}
				}, new Ext.Toolbar.Separator({
					hidden:!isGranted('_seeAllPro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus)
				}),{
					iconCls : 'btn-detail',
					text : '贷款详细',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seeAllPro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : (this.projectStatus == 15)?true:(!isGranted('_seeAllPro_p' + this.projectStatus)
							|| !isGranted('_detailPro_p' + this.projectStatus))
				}),/* new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),*/ {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus
							+ this.bmStatus) ? false :*/ true,
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
				}]
			});
		} else {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-detail',
					text : '查看项目',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_seePro_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}, new Ext.Toolbar.Separator({
					hidden : !isGranted('_seePro_p' + this.projectStatus)
							|| !isGranted('_yjysm_p' + this.projectStatus)
				}), {
					iconCls : 'btn-advice',
					text : '意见与说明记录',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_yjysm_p'+ this.projectStatus)
										? false
										: true,
					handler : this.flowRecords
							
				},new Ext.Toolbar.Separator({
					hidden : !isGranted('_yjysm_p' + this.projectStatus)
							|| !isGranted('_showFlowImg_p' + this.projectStatus)
				}),  {
					iconCls : 'btn-flow-chart',
					text : '流程示意图',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_showFlowImg_p' + this.projectStatus)
							? false
							: true,
					handler : function() {
						showFlowImg(this.gridPanel);
					}
				},/* new Ext.Toolbar.Separator({
					hidden : !isGranted('_showFlowImg_p' + this.projectStatus)
							|| !isGranted('_exportPro_p' + this.projectStatus)
				}),*/ {
					iconCls : 'menu-zmexport',
					text : '导出项目',
					xtype : 'button',
					scope : this,
					hidden : /*isGranted('_exportPro_p' + this.projectStatus)
							? false
							:*/ true,
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
							projectStatus : "提前终止贷款"
						});
						tabs.add(gpObj);
						tabs.setActiveTab("ReportPreviewprojectdetail");
					}
				}]
			});
		}
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			name : 'SmallProjectGrid',
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			notmask : true,
			viewConfig:{
					 forceFit:false 
					  },
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/fund/projectListBpFundProject.do",
			baseParams : {
				'projectStatus' : this.projectStatus,
				'isAll' : this.isGrantedShowAllProjects
			},
			fields : [{
				name : 'id',
				type : 'long'
			}, 'projectId','orgName', 'subject', 'creator', 'userId', 'projectName',
					'projectNumber', 'defId', 'runStatus', 'projectMoney',
					'payProjectMoney', 'oppositeType', 'oppositeTypeValue',
					'customerName', 'projectStatus', 'operationType',
					'operationTypeValue', 'taskId', 'activityName',
					'oppositeId', 'businessType', 'startDate', 'endDate',
					'superviseOpinionName', 'supervisionPersonName',
					'processName', 'appUserName', 'breachComment',
					'accrualtype', 'expectedRepaymentDate', 'payintentPeriod',
					'accrual','managementConsultingOfRate','executor','processName','intentDate','supEndDate','businessType',
					'departMentName','departId','ownJointMoney','productId','runId'],

			columns : [{
				header : '所属分公司',
				align:'center',
				hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'orgName'
			},{
				header : '所属门店',
				width : 200,
				summaryRenderer : totalMoney,
				dataIndex : 'departMentName'
			}, {
				header : '项目编号',
				width : 130,
				
				dataIndex : 'projectNumber'
			}, {
				header : '项目名称',
				width : this.isHiddenBranch?410 : 310,
				dataIndex : 'projectName'
			}, {
				header : '客户类别',
				align:'center',
				width : 68,
				dataIndex : 'oppositeTypeValue'
			}, {
				header : '贷款金额(元)',
				align : 'right',
				summaryType : 'sum',
				width : 110,
				sortable : true,
				dataIndex : 'ownJointMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00');
					}
				}
			}, {
				header : '项目经理',
				align:'center',
				width : 70,
				dataIndex : 'appUserName'
			},{
				header : '项目阶段',
				align:'left',
				width : 80,
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				//hidden : this.isHiddenAn,
				dataIndex : 'activityName'
			}, {
				header : '贷款状态',
				width : 80,
				align:'center',
				hidden : this.isHiddenPs,
				dataIndex : 'projectStatus',
				renderer : function(v) {
					switch (v) {
						case 0 :
							return "办理中项目";
							break;
						case 1 :
							return "还款中项目";
							break;
						case 2 :
							return "结项项目";
							break;
						case 3 :
							return "提前终止项目";
							break;
						case 7 :
							return "全部贷款业务";
							break;
						case 8 :
							return "违约处理项目";
							break;
						case 4 :
							return "展期中项目";
							break;
					}
				}
			}, {
				header : '执行人',
				width : 70,
				align:'center',
				hidden : (this.projectStatus==0||this.projectStatus==3)?false:true,
				dataIndex : 'executor'
			}/*, {
				header : '监管人',
				width : 98,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'supervisionPersonName'
			}, {
				header : '监管意见',
				width : 118,
				hidden : this.isHiddenSuperviseIn,
				dataIndex : 'superviseOpinionName'
			}*/, {
				header : '违约说明',
				width : 118,
				hidden : this.isHiddenBreachComment,
				dataIndex : 'breachComment'
			}, {
				header : '项目开始日期',
				align:'center',
				//width : 76,
				//hidden : this.isHiddenBd,
				dataIndex : 'startDate',
				sortable : true
			}, {
				header : '项目到期日期',
				align:'center',
				//width : 76,
				//hidden : this.isHiddenCd,
				dataIndex : 'intentDate',
				sortable : true,
				renderer : function(data, metadata, record, rowIndex, columnIndex, store) {
	               if(null!=record.get('supEndDate') && record.get('supEndDate')!=''){
	               		data=record.get('supEndDate');
	               }
	                return data;
	            }
			}, {
				header : '终止时间',
				align:'center',
				width : 76,
				hidden : this.isHiddenSd,
				dataIndex : 'endDate',
				sortable : true
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
		/*this.searchPanel.getCmpByName('projectStatus').setValue(this.projectStatus);
		this.searchPanel.getCmpByName('isGrantedShowAllProjects').setValue(this.isGrantedShowAllProjects);*/
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
});
