/**
 * @author lisl
 * @extends Ext.Panel
 * @description 担保项目管理
 * @company 智维软件
 * @createtime:
 */
LeaseFinanceProjectManager = Ext.extend(Ext.Panel, {
			projectStatus : 0,
			bmStatus : 0,
			titlePrefix : "",
			tabIconCls : "",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.projectStatus) != "undefined") {
					this.projectStatus = parseInt(_cfg.projectStatus);
				}if(typeof(_cfg.bmStatus) != "undefined") {
					this.bmStatus = parseInt(_cfg.bmStatus);
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_fl'+this.projectStatus+this.bmStatus);//是否授权显示所有项目记录
				switch(this.projectStatus) {
					case 0 :
						this.titlePrefix = "进行中租赁";
						this.tabIconCls = "menu-flowNew";
						break;
					case 1 :
						this.titlePrefix = "还款中租赁";
						this.tabIconCls = "menu-flowNew";
						break;
					case 2 :
						this.titlePrefix = "已完成租赁";
						this.tabIconCls = "menu-flowNew";
						break;
					case 3 :
						this.titlePrefix = "已否决租赁";
						this.tabIconCls = "menu-flowNew";
						break;
					case 8 :
						this.titlePrefix = "违约处置租赁";
						this.tabIconCls = "menu-flowNew";
						break;
					default:
						this.titlePrefix = "租赁";
						this.tabIconCls = "menu-flowNew";
						break;
				}
				
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				LeaseFinanceProjectManager.superclass.constructor.call(this, {
							id : 'LeaseFinanceProjectManager_' + this.projectStatus+this.bmStatus,
							title : this.titlePrefix + '项目管理',
							iconCls : this.tabIconCls,
							region : 'center',
							layout : 'border',
							items : [
								{xtype:'label',text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看你的上下级关系是否设置正确!(查询授权规律为：你可以看你自己或你下属的项目)',hidden : true},
								this.searchPanel,
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				this.isHiddenBranch = true;
				if (RoleType == "control") {// 此用户角色为管控角色
					this.isHiddenBranch = false;
				}
				// 初始化搜索条件Panel
				var anchor='100%';
				this.searchPanel =new Ext.FormPanel({
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor :anchor

						},
						items : [
							{
								name : 'projectStatus',
								xtype : 'hidden',
								value : this.projectStatus
							},{
								name : 'bmStatus',
								xtype : 'hidden',
								value : this.bmStatus
							}, {
								name : 'businessType',
								xtype : 'hidden',
								value : 'LeaseFinance'
							}, {
								name : 'isGrantedShowAllProjects',
								xtype : 'hidden',
								value : this.isGrantedShowAllProjects
							},{
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : '99%'
							},
							items : [{
									fieldLabel:'项目编号',
									name : 'Q_projectNumber_S_LK',
									xtype : 'textfield'
								}]
						}, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 70,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'项目名称',
									name : 'Q_projectName_S_LK',
									xtype : 'textfield'
									
								}]
						}, {
							columnWidth : .23,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									editable : false,
									displayField : 'value',
									store : new Ext.data.SimpleStore({
												fields : ["value"],
												data : [["企业"],
														["个人"]]
											}),
									triggerAction : "all",
									name : 'Q_oppositeTypeValue_S_EQ',
									fieldLabel : '客户类别'
									
								}]
						}, {
							columnWidth : .27,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'客户',
									name : 'Q_customerName_S_LK',
									xtype : 'textfield'
								}]
						}, {
							columnWidth : .14,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
//								anchor : anchor
								anchor : '99%'
							},
							items : [{
									fieldLabel:'租赁成本',
									name : 'Q_projectMoney_BD_GE',
									xtype : 'numberfield'
								}]
						}, {
							columnWidth : .11,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
//								anchor : anchor
								anchor : '99%'
							},
						
							items : [{
									fieldLabel:'到',
									labelSeparator:"",
									name : 'Q_projectMoney_BD_LE',
									xtype : 'numberfield'
								}]
						}, {
							columnWidth : .14,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 70,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'租赁起始日',
									name : 'Q_startDate_D_GE',
									xtype : 'datefield',
									format : 'Y-m-d'
									
								}]
						}, {
							columnWidth : .112,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 20,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'到',
									name : 'Q_startDate_D_LE',
									xtype : 'datefield',
									labelSeparator:"",
									format : 'Y-m-d'
								}]
						}, {
							columnWidth : .23,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									name : 'appUserIdOfA',
									xtype:'trigger',
									fieldLabel : '项目经理',
									submitValue : true,
									triggerClass :'x-form-search-trigger',
									editable : false,
									scope : this,
									onTriggerClick : function(){
									    var obj = this;
										UserSelector.getView(function(id, name) {
											obj.setValue(name);
											obj.ownerCt.ownerCt.getCmpByName('Q_appUserId_S_LK').setValue(id);
										}, true).show();//false为多选,true为单选。
									}
								}]
						},{
							xtype : "hidden",
							name : 'Q_appUserId_S_LK'
						}, this.isHiddenBranch == false ?{
							columnWidth :.145,
							layout : 'form',
							xtype : 'container',
							labelWidth : 70,
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
							columnWidth : .0001,
							border : false
						}, {
							columnWidth : .075,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							items : [{
										text:'查询',
										style :'margin-left:30px',
										width:50,
										scope:this,
										iconCls:'btn-search',
										handler:this.search
									}]
						}, {
							columnWidth : .05,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							items : [{
										text:'重置',
										width:50,
										scope:this,
										iconCls:'btn-reset',
										handler:this.reset
									}]
						}]
					}]
				})
				if(this.projectStatus == 0) {
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !(isGranted('_seePro_fl' + this.projectStatus + this.bmStatus)||isGranted('_editPro_fl' + this.projectStatus + this.bmStatus)),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !(isGranted('_seePro_fl' + this.projectStatus + this.bmStatus)||isGranted('_editPro_fl' + this.projectStatus + this.bmStatus))
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_',this.projectStatus)
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_fl' + this.projectStatus + this.bmStatus)||!isGranted('_hangPro_fl' + this.projectStatus + this.bmStatus)
								}),{
									iconCls : 'btn-suspended',
									text : '挂起项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_hangPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										suspendPro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_',1);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_hangPro_fl' + this.projectStatus + this.bmStatus)||!isGranted('_stopPro_fl' + this.projectStatus + this.bmStatus)
								}),{
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_fl' + this.projectStatus + this.bmStatus)||!isGranted('_removePro_fl' + this.projectStatus + this.bmStatus)
								}), {
									iconCls : 'btn-del',
									text : '删除项目',
									xtype : 'button',
									hidden : isGranted('_removePro_fl'+this.projectStatus+this.bmStatus)?false:true,
									scope:this,
									handler: function() {
										removePro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_removePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),/*{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewleaseFinanceprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewleaseFinanceprojectdetail');
                                    	}
											gpObj=ReportMenuLeaseFinanceprojectdetail({
												reportKey:"leaseFinanceprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_LE").getValue(),
												projectStatus:"租赁-办理中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewleaseFinanceprojectdetail");
									}
								},*/{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					} 
					
				} else if (this.projectStatus == 1) {
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_fl' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),/*{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewleaseFinanceprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewleaseFinanceprojectdetail');
                                    	}
											gpObj=ReportMenuLeaseFinanceprojectdetail({
												reportKey:"leaseFinanceprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewleaseFinanceprojectdetail");
									}
								},*/{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					} 
				}else if(this.projectStatus == 2){
					
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_fl' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),/*{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewleaseFinanceprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewleaseFinanceprojectdetail');
                                    	}
											gpObj=ReportMenuLeaseFinanceprojectdetail({
												reportKey:"leaseFinanceprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewleaseFinanceprojectdetail");
									}
								},*/{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					} 
				}else if(this.projectStatus == 3){
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_fl' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),/*{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewleaseFinanceprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewleaseFinanceprojectdetail');
                                    	}
											gpObj=ReportMenuLeaseFinanceprojectdetail({
												reportKey:"leaseFinanceprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewleaseFinanceprojectdetail");
									}
								},*/{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					}
				}else if(this.projectStatus == 8){
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_fl' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-del',
									text : '删除项目',
									xtype : 'button',
									hidden : isGranted('_removePro_fl'+this.projectStatus+this.bmStatus)?false:true,
									scope:this,
									handler: function() {
										removePro(this.gridPanel,'LeaseFinanceProjectInfo_','LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_removePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),/*{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewleaseFinanceprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewleaseFinanceprojectdetail');
                                    	}
											gpObj=ReportMenuLeaseFinanceprojectdetail({
												reportKey:"leaseFinanceprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_appUserIdOfA_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('appUserIdOfA').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												projectStatus:"保中-进行中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewleaseFinanceprojectdetail");
									}
								},*/{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					}
				}else if(this.projectStatus == 100){//表示查看全部项目
					if(this.bmStatus == 0) {
						this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_seePro_fl' + this.projectStatus + this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									scope:this,
									hidden : !isGranted('_editPro_fl'+this.projectStatus+this.bmStatus),
									handler : function() {
										detailPro(this.gridPanel,'LeaseFinanceProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)
								}),new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_fl'+this.projectStatus+this.bmStatus)||!isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_fl'+this.projectStatus+this.bmStatus)||!isGranted('_exportPro_fl'+this.projectStatus+this.bmStatus)
								}),{
									iconCls : 'menu-zmexport',
									text : '意见与说明',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seeLeaseFinanceAgreements_fl'+this.projectStatus+this.bmStatus)?false:true,
									handler : function() {	
										var selRs = this.gridPanel.getSelectionModel().getSelections();
                                    	if (selRs.length == 0) {
                                    		Ext.ux.Toast.msg('操作信息', '请选择一条记录！');
                                    		return;
                                    	}
                                    	if (selRs.length > 1) {
                                    		Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
                                    		return;
                                    	}
                                    	var record = this.gridPanel.getSelectionModel().getSelected();
										new SlProcessRunView({
											runId : record.data.runId,
											projectStatus : record.data.projectStatus,
											bmStatus : this.bmStatus,
											businessType : "LeaseFinance",
											isAutoHeight : false
										}).show();
									}
								}]
						});
					}
				}
				
	
				this.gridPanel=new HT.GridPanel({
					name : 'LeaseFinanceProjectGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					//使用RowActions
					rowActions : false,
					url : __ctxPath + "/creditFlow/leaseFinance/project/projectListFlLeaseFinanceProject.do",
					baseParams:{
						'businessType' : "LeaseFinance",
						'projectStatus' : this.projectStatus,
						'bmStatus' : this.bmStatus,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId','orgName','subject','executor', 'creator','userId', 'projectName', 'projectNumber', 'defId','createtime',
							'runStatus','startDate','projectMoney','oppositeType','oppositeTypeValue','appUserIdValue','projectStatus','bmStatus','operationType',
							'operationTypeValue','taskId','activityName','oppositeId','businessType','appUserName','flowType','intentDate','breachComment','superviseStartDate','superviseEndDate'],
					columns : [
							{
								header : 'projectId',
								dataIndex : 'projectId',
								hidden : true
							},{
								header : 'taskId',
								dataIndex : 'taskId',
								hidden : true
							},{
								header : 'oppositeId',
								dataIndex : 'oppositeId', 
								hidden : true
							},{
								header : 'oppositeType',
								dataIndex : 'oppositeType', 
								hidden : true
							},{
								header : 'businessType',
								dataIndex : 'businessType', 
								hidden : true
							},{
								header : '所属分公司',
								hidden :  this.isHiddenBranch?true :false,
								width : 100,
								dataIndex : 'orgName'
							}, {
								header : '项目名称',
								width : 480,
								dataIndex : 'projectName'
							},{
								header : '项目编号',
								width : 130,
								dataIndex : 'projectNumber'
							}, {
								header : '客户类别',
								width : 71,
								dataIndex : 'oppositeTypeValue'
							}, {
								header : '租赁成本',
								align : 'right',
								width : 110,
								dataIndex : 'projectMoney',
								renderer : function(value) {
									if(value == "") {
										return "0.00元";
									}else {
										return Ext.util.Format.number(value,',000,000,000.00')+"元";
									}
								}
							}, {
								header : '项目经理',
								width : 90,
								dataIndex : 'appUserIdValue'
							},{
								header:'违约说明',
								width:100,
								hidden:this.projectStatus == 8 ?false:true,
								dataIndex:'breachComment'
							}, {
								header : '项目阶段',
								width : 116,
								dataIndex : 'activityName'
							}, {
								header : '执行人',
								width : 70,
								hidden : (this.bmStatus==2||this.bmStatus==3)?true:false,
								dataIndex : 'executor'
							}, {
								header : '租赁起始日',
								width : 76,
								dataIndex : 'startDate',
								sortable : true
							}, {
								header : '租赁截止日',
								width : 76,
								dataIndex : 'intentDate',
								sortable : true
							},{
								header : '展期起始日期',
								width : 76,
								hidden:(this.projectStatus==1||this.projectStatus==8||this.projectStatus==2)?false:true,//还款中租赁项目，已完成租赁项目，违约处置租赁项目显示展期起始日期
								dataIndex : 'superviseStartDate',
								sortable : true
							},{
								header : '展期到期日期',
								width : 76,
								hidden:(this.projectStatus==1||this.projectStatus==8||this.projectStatus==2)?false:true,//还款中租赁项目，已完成租赁项目，违约处置租赁项目显示展期到期日期
								dataIndex : 'superviseEndDate',
								sortable : true
							},{
								header : '项目状态',
								dataIndex : 'projectStatus',
								renderer:function(value){
									switch (value){
										case 0:
											return '办理中租赁项目';
										case 1:
											return '还款中租赁项目';
										case 2:
											return '已完成租赁项目';
										case 3:
											return '已否决租赁项目';
										case 8:
											return '违约处置租赁项目';
									}
								},
								hidden : this.projectStatus==100?false:true
							}
					]//end of columns
				});
				this.gridPanel.addListener('afterrender', function(){
					 this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl (), {
						 msg  : '正在加载数据中······,请稍候······',
						 store:this.gridPanel.store,
						 removeMask : true// 完成后移除
					});
					this.loadMask1 .show(); //显示
					
				},this);
				if(!this.isGrantedShowAllProjects) {
					this.gridPanel.getStore().on("load",function(store) {
						if(store.getCount() < 1) {
							this.get(0).setVisible(true);
							this.searchPanel.setVisible(false);
							this.gridPanel.setVisible(false);
							this.doLayout();
						}
					},this)
				}
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			}
});
