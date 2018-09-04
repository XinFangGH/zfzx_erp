/**
 * @author lisl
 * @extends Ext.Panel
 * @description 担保项目管理
 * @company 智维软件
 * @createtime:
 */
GuaranteeLoanedProjectManager = Ext.extend(Ext.Panel, {
			managerType : 0,
			titlePrefix : "",
			tabIconCls : "",
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				if(typeof(_cfg.managerType) != "undefined") {
					this.managerType = parseInt(_cfg.managerType);
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_gt10');//是否授权显示所有项目记录
				switch(this.managerType) {
					case 1 :
						this.titlePrefix = "保后监管";
						this.tabIconCls = "btn-tree-team12";
						break;
					case 2 : 
						this.titlePrefix = "违约处理";
						this.tabIconCls = "btn-tree-team59";
						break;
					case 3 : 
						this.titlePrefix = "项目结项";
						this.tabIconCls = "btn-ok";
						break;
				}
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				GuaranteeLoanedProjectManager.superclass.constructor.call(this, {
					id : 'HousepurchasingProjectManager' + this.managerType,
					title : this.titlePrefix,
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
								value : 1
							},{
								name : 'bmStatus',
								xtype : 'hidden',
								value : 0
							}, {
								name : 'businessType',
								xtype : 'hidden',
								value : 'Guarantee'
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
								anchor : anchor
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
							labelWidth : 60,
							defaults : {
								anchor :anchor
							},
							items : [{
									fieldLabel:'项目名称',
									name : 'Q_projectName_S_LK',
									xtype : 'textfield'
									
								}]
						}, {
							columnWidth : .25,
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
							columnWidth : .25,
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
								anchor : anchor
							},
							items : [{
									fieldLabel:'贷款金额',
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
								anchor : anchor
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
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									fieldLabel:'启动时间',
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
							columnWidth : .25,
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
								},{
									xtype : "hidden",
									name : 'Q_businessManager_S_LK'
								}]
						}, {
							columnWidth : .12,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								xtype : 'button'
							},
							//style : 'padding-left:60px;',
							items : [{
										text:'查询',
										fieldLabel : ' ',
										labelSeparator:"",
										scope:this,
										iconCls:'btn-search',
										handler:this.search
									}]
						}, {
							columnWidth : .13,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							
							labelWidth :1,
							defaults : {
								xtype : 'button'
							},
							items : [{
										text:'重置',
										fieldLabel : '',
										labelSeparator:"",
										scope:this,
										iconCls:'btn-reset',
										handler:this.reset
									}]
						}]
					}]
				})

				if(this.managerType == 1) {
					this.topbar = new Ext.Toolbar({
						items : [{
								iconCls : 'btn-tree-team12',
								text : '保后监管',
								xtype : 'button',
								scope:this,
								hidden : !isGranted('superviseManage'),
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
									var projectId = record.data.projectId;
									var businessType= record.data.businessType;
									new flDesignSupervisionManagePlan({projectId : projectId,businessType:businessType,title:"保后监管"}).setVisible(true);
								}
							},new Ext.Toolbar.Separator({
								hidden : !isGranted('superviseManage')
							}),{
								iconCls : 'btn-detail',
								text : '查看项目',
								xtype : 'button',
								scope:this,
								handler : function() {
									detailPro(this.gridPanel,'GuaranteeProjectInfo_');
								}
							}]
					});
				}
				if(this.managerType == 2) {
					this.topbar = new Ext.Toolbar({
					items : [{
							iconCls : 'btn-tree-illegal',
							text : '违约处理',
							xtype : 'button',
							scope:this,
							hidden : !isGranted('startBreachG'),
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
								var projectId = record.data.projectId;
								var tabs = Ext.getCmp('centerTabPanel');
								var gpObj = document.getElementById("BreachHandlePanel_"+projectId);
								if (gpObj == null) {
									gpObj = new BreachHandle({
										record : record,
										gridPanel : this.gridPanel
									});
									tabs.add(gpObj);
								}
								tabs.setActiveTab("BreachHandlePanel_"+projectId);
							}
						},new Ext.Toolbar.Separator({
							hidden : !isGranted('startBreach')
						}),{
							iconCls : 'btn-detail',
							text : '查看项目',
							xtype : 'button',
							scope:this,
							handler : function() {
								detailPro(this.gridPanel,'GuaranteeProjectInfo_');
							}
						}]
					});
				}
				if(this.managerType == 3) {
					this.topbar = new Ext.Toolbar({
					items : [{
							iconCls : 'btn-ok',
							text : '项目结项',
							xtype : 'button',
							scope:this,
							hidden : !isGranted('finishedProject'),
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
								var projectId = record.data.projectId;
								var tabs = Ext.getCmp('centerTabPanel');
								var gpObj = document.getElementById("ProjectFinishedPanel_"+projectId);
								if (gpObj == null) {
									gpObj = new ProjectFinished({
										record : record,
										gridPanel : this.gridPanel
									});
									tabs.add(gpObj);
								}
								tabs.setActiveTab("ProjectFinishedPanel_"+projectId);
							}
						},new Ext.Toolbar.Separator({
							hidden : !isGranted('finishedProject')
						}),{
							iconCls : 'btn-detail',
							text : '查看项目',
							xtype : 'button',
							scope:this,
							handler : function() {
								detailPro(this.gridPanel,'GuaranteeProjectInfo_');
							}
						}]
					});
				}
				this.gridPanel=new HT.GridPanel({
					name : 'GuaranteeLoanedProjectGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					//使用RowActions
					rowActions : false,
					url : __ctxPath + "/creditFlow/guarantee/EnterpriseBusiness/projectListGLGuaranteeloanProject.do",
					baseParams:{
						'projectStatus' : 1,
						'bmStatus' : 0,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
						name : 'runId',
						type : 'int'
					}, 'projectId','subject', 'creator','userId', 'projectName', 'projectNumber', 'defId','createtime', 'runStatus','startDate','projectMoney','oppositeType','oppositeTypeValue','appUserIdOfAValue','projectStatus','bmStatus','operationType','operationTypeValue','taskId','activityName','oppositeId','businessType','appUserName','flowType','dicbankname','customerName'],
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
								header : '项目编号',
								width : 70,
								dataIndex : 'projectNumber'
							}, {
								header : '项目名称',
								width : 400,
								dataIndex : 'projectName'
							}, {
								header : '客户类别',
								fixed : true,
								width : 71,
								dataIndex : 'oppositeTypeValue'
							}, {
								header : '客户',
								width : 130,
								dataIndex : 'customerName'
							}, {
								header : '贷款金额',
								align : 'right',
								fixed : true,
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
								header : '贷款银行',
								width : 115,
								dataIndex : 'dicbankname'
							}, {
								header : '项目阶段',
								width : 62,
								dataIndex : 'activityName'
							}, {
								header : '启动时间',
								fixed : true,
								width : 76,
								dataIndex : 'startDate',
								sortable : true
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
