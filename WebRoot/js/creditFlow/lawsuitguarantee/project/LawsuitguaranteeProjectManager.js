/**
 * @author lisl
 * @extends Ext.Panel
 * @description 小贷项目管理
 * @company 智维软件
 * @createtime:
 */
LawsuitguaranteeProjectManager = Ext.extend(Ext.Panel, {
			projectStatus : 0,
			titlePrefix : "",
			tabIconCls : "",
			isHiddenBd : true,
			isHiddenAn : true,
			isHiddenCd : true,
			isHiddenSd : true,
			isHiddenSuperviseIn : true,
			isHiddenBreachComment : true,
			dateLabel : "启动时间",
			startDateName : "Q_startDate_D_GE",
			endDateName : "Q_startDate_D_LE",
			// 构造函数
			constructor : function(_cfg) {
				if(typeof(_cfg.projectStatus) != "undefined") {
					this.projectStatus = parseInt(_cfg.projectStatus);
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_l'+this.projectStatus);//是否授权显示所有项目记录
				switch(this.projectStatus) {
					case 0 :
						this.titlePrefix = "申请中项目";
						this.tabIconCls = "btn-tree-team17";
						this.isHiddenBd = false;
						this.isHiddenAn = false;
						break;
					case 1 :
						this.titlePrefix = "在保中项目";
						this.tabIconCls = "btn-tree-team18";
						this.isHiddenBd = false;
						this.isHiddenAn = false;
						this.isHiddenSuperviseIn = false;
						break;
					case 2 :
						this.titlePrefix = "已完成项目";
						this.tabIconCls = "btn-tree-team19";
						this.isHiddenCd = false;
						this.dateLabel = "完成时间";
						this.startDateName = "Q_endDate_D_GE";
						this.endDateName = "Q_endDate_D_LE";
						break;
					case 3 :
						this.titlePrefix = "已终止项目";
						this.tabIconCls = "btn-tree-team20";
						this.isHiddenSd = false;
						this.isHiddenAn = false;
						this.dateLabel = "终止时间";
						this.startDateName = "Q_endDate_D_GE";
						this.endDateName = "Q_endDate_D_LE";
						break;
				
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				
				// 调用父类构造
				LawsuitguaranteeProjectManager.superclass.constructor.call(this, {
							id : 'LawsuitguaranteeProjectManager_' + this.projectStatus,
							title : this.titlePrefix,
							region : 'center',
							layout : 'border',
							iconCls : this.tabIconCls,
							items : [
								{xtype:'label',text : '对不起，您能查看的项目数为0。如有错误，请与系统管理员联系，看你的上下级关系是否设置正确!(查询授权规律为：你可以看你自己或你下属的项目)',hidden : true},
								this.searchPanel,
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var anchor='100%';
				var startDate =	{
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
										anchor :anchor
									},
									items : [{
											fieldLabel:'到',
											name : this.endDateName,
											xtype : 'datefield',
											labelSeparator:"",
											format : 'Y-m-d'
										}]
								};
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
							}, {
								name : 'isGrantedShowAllProjects',
								xtype : 'hidden',
								value : this.isGrantedShowAllProjects
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
									fieldLabel:'金额范围',
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
						}, startDate, endDate, {
							columnWidth : .25,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 60,
							defaults : {
								anchor : anchor
							},
							items : [{
									name : 'businessManager',
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
											obj.ownerCt.ownerCt.getCmpByName('Q_businessManager_S_LK').setValue(id);
										}, true).show();//false为多选,true为单选。
									}
								}]
						}, {
							xtype : "hidden",
							name : 'Q_businessManager_S_LK'
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
				});
                if(this.projectStatus == 0){
                	this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_seePro_l'+this.projectStatus)?false:true,		
									handler : function() {
										detailPro(this.gridPanel,'LawsuitguaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_l'+this.projectStatus)||!isGranted('_detailPro_l'+this.projectStatus)
								}), {
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									hidden : isGranted('_detailPro_l'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										detailPro(this.gridPanel,'LawsuitguaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_l'+this.projectStatus)||!isGranted('_stopPro_l'+this.projectStatus)
								}), {
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									hidden : isGranted('_stopPro_l'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										stopPro(this.gridPanel,'LawsuitguaranteeProjectInfo_','LawsuitguaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_l'+this.projectStatus)||!isGranted('_removePro_l'+this.projectStatus)
								}), {
									iconCls : 'btn-del',
									text : '删除项目',
									xtype : 'button',
									hidden : isGranted('_removePro_l'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										removePro(this.gridPanel,'LawsuitguaranteeProjectInfo_','LawsuitguaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_removePro_l'+this.projectStatus)||!isGranted('_showFlowImg_l'+this.projectStatus)
								}), {
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_l'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_l'+this.projectStatus)||!isGranted('_exportPro_l'+this.projectStatus)
								}),{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_l'+this.projectStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewprojectdetail');
                                    	}
											gpObj=ReportMenuSmallloanprojectdetail({
												reportKey:"projectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_businessManager_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName(this.startDateName).getValue(),
												startDatel :searchPanel.getCmpByName(this.endDateName).getValue(),
												projectStatus:"申请中"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewprojectdetail");
									}
								}]
					});
                }else if(this.projectStatus == 1){
                	this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_seePro_l'+this.projectStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'LawsuitguaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_l'+this.projectStatus)||!isGranted('_detailPro_l'+this.projectStatus)
								}), {
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									hidden : isGranted('_detailPro_l'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										detailPro(this.gridPanel,'LawsuitguaranteeProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_detailPro_l'+this.projectStatus)||!isGranted('_showFlowImg_l'+this.projectStatus)
								}), {
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_l'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_l'+this.projectStatus)||!isGranted('_exportPro_l'+this.projectStatus)
								}),{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_l'+this.projectStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewprojectdetail');
                                    	}
											gpObj=ReportMenuSmallloanprojectdetail({
												reportKey:"projectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_businessManager_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName(this.startDateName).getValue(),
												startDatel :searchPanel.getCmpByName(this.endDateName).getValue(),
												projectStatus : '在保中'
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewprojectdetail");
									}
								}]
					});
                }else {
                	this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope:this,
									hidden : isGranted('_seePro_l'+this.projectStatus)?false:true,
									handler : function() {
										detailPro(this.gridPanel,'LawsuitguaranteeProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_l'+this.projectStatus)||!isGranted('_showFlowImg_l'+this.projectStatus)
								}), {
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_l'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_l'+this.projectStatus)||!isGranted('_exportPro_l'+this.projectStatus)
								}),{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_l'+this.projectStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewprojectdetail');
                                    	}
											gpObj=ReportMenuSmallloanprojectdetail({
												reportKey:"projectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
												businessManagerid :searchPanel.getCmpByName('Q_businessManager_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName(this.startDateName).getValue(),
												startDatel :searchPanel.getCmpByName(this.endDateName).getValue(),
												projectStatus:"已终止"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewprojectdetail");
									}
								}]
					});
                }
				this.gridPanel=new HT.GridPanel({
					name : 'LawsuitguaranteeProjectGrid',
					region:'center',
					tbar:this.topbar,
					notmask :true,
					//不使用RowActions
					rowActions : false,
					url : __ctxPath + "/creditFlow/lawsuitguarantee/projectlistSgLawsuitguaranteeProject.do",
					baseParams:{
						'projectStatus' : this.projectStatus,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId','subject', 'creator','userId', 'projectName', 'projectNumber', 'defId', 'runStatus','projectMoney','payProjectMoney','oppositeType','oppositeTypeValue','customerName','projectStatus','operationType','operationTypeValue','taskId','activityName','oppositeId','businessType','startDate','endDate','superviseOpinionName','supervisionPersonName','processName','appuserName','breachComment','accrualtype','expectedRepaymentDate','payintentPeriod'],
					columns : [
							{
								header : 'runId',
								dataIndex : 'runId',
								hidden : true
							},{
								header : 'projectId',
								dataIndex : 'projectId',
								hidden : true
							},{
								header : 'payintentPeriod',
								dataIndex : 'payintentPeriod',
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
								header : 'operationType',
								dataIndex : 'operationType', 
								hidden : true
							},{
								header : 'businessType',
								dataIndex : 'businessType', 
								hidden : true
							},{
								header : '项目编号',
								width : 130,
								dataIndex : 'projectNumber'
							}, {
								header : '项目名称',
								width : 410,
								dataIndex : 'projectName'
							}, {
								header : '客户类别',
								width : 68,
								dataIndex : 'oppositeTypeValue'
							}, {
								header : '贷款金额',
								align : 'right',
								width : 110,
								sortable : true,
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
								width : 70,
								dataIndex : 'appuserName'
							}, {
								header : '项目阶段',
								width : 118,
								hidden : this.isHiddenAn,
								dataIndex : 'activityName'
							}, {
								header : '监管人',
								width : 98,
								hidden : this.isHiddenSuperviseIn,
								dataIndex : 'supervisionPersonName'
							}, {
								header : '监管意见',
								width : 118,
								hidden : this.isHiddenSuperviseIn,
								dataIndex : 'superviseOpinionName'
							}, {
								header : '违约说明',
								width : 118,
								hidden : this.isHiddenBreachComment,
								dataIndex : 'breachComment'
							}, {
								header : '启动时间',
								width : 76,
								hidden : this.isHiddenBd,
								dataIndex : 'startDate',
								sortable : true
							}, {
								header : '完成时间',
								width : 76,
								hidden : this.isHiddenCd,
								dataIndex : 'endDate',
								sortable : true
							}, {
								header : '终止时间',
								width : 76,
								hidden : this.isHiddenSd,
								dataIndex : 'endDate',
								sortable : true
							}
					]
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
