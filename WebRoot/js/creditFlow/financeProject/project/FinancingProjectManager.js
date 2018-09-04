/**
 * @author lisl
 * @extends Ext.Panel
 * @description 融资项目信息管理
 * @company 智维软件
 * @createtime:
 */
FinancingProjectManager = Ext.extend(Ext.Panel, {
			// 构造函数
			projectStatus : 0,
			titlePrefix : "",
			tabIconCls : "",
			isHiddenBd : true,
			isHiddenCd : true,
			isHiddenSd : true,
			dateLabel : "启动时间",
			startDateName : "Q_startDate_D_GE",
			endDateName : "Q_startDate_D_LE",
			constructor : function(_cfg) {
				if(typeof(_cfg.projectStatus) != "undefined") {
					this.projectStatus = parseInt(_cfg.projectStatus);
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_f'+this.projectStatus);//是否授权显示所有项目记录
				switch(this.projectStatus) {
					case 0 :
						this.titlePrefix = "办理中";
						this.tabIconCls = "menu-flowNew";
						this.isHiddenBd = false;
						break;
					case 1 :
						this.titlePrefix = "还款中";
						this.tabIconCls = "menu-flowMine";
						this.isHiddenBd = false;
						this.isHidden = true;
						break;
					case 2 :
						this.titlePrefix = "已完成";
						this.tabIconCls = "menu-flowEnd";
						this.isHiddenCd = false;
						this.dateLabel = "完成时间";
						this.isHidden = true;
						this.startDateName = "Q_endDate_D_GE";
						this.endDateName = "Q_endDate_D_LE";
						break;
					case 3 :
						this.titlePrefix = "提前终止";
						this.tabIconCls = "menu-del";
						this.isHiddenSd = false;
						this.dateLabel = "终止时间";
						this.startDateName = "Q_endDate_D_GE";
						this.endDateName = "Q_endDate_D_LE";
						break;
					case 6 :
						this.titlePrefix = "全部融资业务";
						this.tabIconCls = "btn-tree-team16";
						break;
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				FinancingProjectManager.superclass.constructor.call(this, {
							id : 'FinancingProjectManager_' + this.projectStatus,
							title : this.titlePrefix + '项目管理',
							region : 'center',
							layout : 'border',
							iconCls : this.tabIconCls,
							items : [
								this.searchPanel,
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var anchor='100%';
				var isShow = false;
				if (RoleType == "control") {// 此用户角色为管控角色
					isShow = true;
				}
				// 初始化搜索条件Panel
				this.searchPanel = new Ext.FormPanel({
					height : 65,
					//labelWidth : 55,
					region : "north",
					bodyStyle : 'padding:7px 0px 7px 10px',
					border : false,
					width : '100%',
					monitorValid : true,
					layout : 'column',
					defaults : {
						layout : 'form',
						border : false,
						bodyStyle : 'padding:5px 0px 0px 20px'
					},
					items : [{
						name : 'projectStatus',
						xtype : 'hidden',
						value : this.projectStatus == 6?null:this.projectStatus
					}, {
						name : 'isGrantedShowAllProjects',
						xtype : 'hidden',
						value : this.isGrantedShowAllProjects
					}, isShow?{
						columnWidth : .25,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 70,
						defaults : {
							anchor : '100%'
						},
							border : false,
							items : [{
								xtype : "combo",
								anchor : "100%",
								fieldLabel : '所属分公司',
								hiddenName : "companyId",
								displayField : 'companyName',
								valueField : 'companyId',
								triggerAction : 'all',
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath+'/system/getControlNameOrganization.do',
									fields : ['companyId', 'companyName']
								})
							}
						]}:{ 
						columnWidth:0.001,
				     	border:false								
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
					}, {
						columnWidth : .14,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 61,
						defaults : {
							anchor : anchor
						},
						items : [{
								fieldLabel : this.dateLabel,
								name : this.startDateName,
								xtype : 'datefield',
								format : 'Y-m-d'
								
							}]
					}, {
						columnWidth : .112,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 22,
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
					}, {
						columnWidth : .25,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 70,
						defaults : {
							anchor : anchor
						},
						items : [{
								fieldLabel:'客户',
								name : 'Q_customerName_S_LK',
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
				})
                if(this.projectStatus == 0){
                					this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seePro_f'+this.projectStatus)?false:true,	
									handler : function() {
										detailPro(this.gridPanel,'FinancingProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_f'+this.projectStatus)||!isGranted('_editPro_f'+this.projectStatus)
								}), {
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									hidden : isGranted('_editPro_f'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										detailPro(this.gridPanel,'FinancingProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_editPro_f'+this.projectStatus)||!isGranted('_stopPro_f'+this.projectStatus)
								}), {
									iconCls : 'btn-close1',
									text : '终止项目',
									xtype : 'button',
									hidden : this.isHidden,
									scope:this,
									hidden : isGranted('_stopPro_f'+this.projectStatus)?false:true,
									handler: function() {
										stopPro(this.gridPanel)
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_stopPro_f'+this.projectStatus)||!isGranted('_removePro_f'+this.projectStatus)
								}), {
									iconCls : 'btn-del',
									text : '删除项目',
									xtype : 'button',
									hidden : isGranted('_removePro_f'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										removePro(this.gridPanel,'FinancingProjectInfoEdit_','FinancingProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_removePro_f'+this.projectStatus)||!isGranted('_showFlowImg_f'+this.projectStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_f'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_f'+this.projectStatus)||!isGranted('_exportPro_f'+this.projectStatus)
								})
								/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_f'+this.projectStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewfinancingprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewfinancingprojectdetail');
                                    	}
											gpObj=ReportMenuFinancingprojectdetail({
												reportKey:"financingprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
										//		businessManagerid :searchPanel.getCmpByName('Q_creator_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_LE").getValue(),
												projectStatus:"融资前"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewfinancingprojectdetail");
									}}*/
									]
				});
                	
                }else if(this.projectStatus == 1 || this.projectStatus == 2|| this.projectStatus == 6){
                					this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seePro_f'+this.projectStatus)?false:true,	
									handler : function() {
										detailPro(this.gridPanel,'FinancingProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_f'+this.projectStatus)||!isGranted('_editPro_f'+this.projectStatus)
								}), {
									iconCls : 'btn-edit',
									text : '编辑项目',
									xtype : 'button',
									hidden : isGranted('_editPro_f'+this.projectStatus)?false:true,
									scope:this,
									handler: function() {
										detailPro(this.gridPanel,'FinancingProjectInfoEdit_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_editPro_f'+this.projectStatus)||!isGranted('_removePro_f'+this.projectStatus)
								}),{
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_f'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_f'+this.projectStatus)||!isGranted('_exportPro_f'+this.projectStatus)
								})
								/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_f'+this.projectStatus)?false:true,
									handler : function() {
										var status="";
										if(this.projectStatus == 1){
										status="还款中";
										}else if(this.projectStatus == 2){
										   status="已完成";
										}
										
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewfinancingprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewfinancingprojectdetail');
                                    	}
											gpObj=ReportMenuFinancingprojectdetail({
												reportKey:"financingprojectdetail",
													projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
										//		businessManagerid :searchPanel.getCmpByName('Q_creator_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_LE").getValue(),
												projectStatus:status
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewfinancingprojectdetail");
									}
								}*/
								]
				});
                }else {
                	this.topbar = new Ext.Toolbar({
						items : [
								{
									iconCls : 'btn-detail',
									text : '查看项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_seePro_f'+this.projectStatus)?false:true,	
									handler : function() {
										detailPro(this.gridPanel,'FinancingProjectInfo_')
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_seePro_f'+this.projectStatus)||!isGranted('_showFlowImg_f'+this.projectStatus)
								}), {
									iconCls : 'btn-flow-chart',
									text : '流程示意图',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_showFlowImg_f'+this.projectStatus)?false:true,
									handler : function() {
										showFlowImg(this.gridPanel);
									}
								},new Ext.Toolbar.Separator({
									hidden : !isGranted('_showFlowImg_f'+this.projectStatus)||!isGranted('_exportPro_f'+this.projectStatus)
								})
								/*,{
									iconCls : 'menu-zmexport',
									text : '导出项目',
									xtype : 'button',
									scope : this,
									hidden : isGranted('_exportPro_f'+this.projectStatus)?false:true,
									handler : function() {
										var searchPanel=this.searchPanel;
										var tabs = Ext.getCmp('centerTabPanel');
                                    	var gpObj = document.getElementById('ReportPreviewfinancingprojectdetail');
                                    	if (gpObj != null) {
                                         tabs.remove('ReportPreviewfinancingprojectdetail');
                                    	}
											gpObj=ReportMenuFinancingprojectdetail({
												reportKey:"financingprojectdetail",
												projectMoney :searchPanel.getCmpByName('Q_projectMoney_BD_GE').getValue(),
												projectMoneyl :searchPanel.getCmpByName('Q_projectMoney_BD_LE').getValue(),
												
												projectNumber :searchPanel.getCmpByName('Q_projectNumber_S_LK').getValue(),
												projectName :searchPanel.getCmpByName('Q_projectName_S_LK').getValue(),
												oppositeTypeValue :searchPanel.getCmpByName('Q_oppositeTypeValue_S_EQ').getValue(),
												customerName :searchPanel.getCmpByName('Q_customerName_S_LK').getValue(),
										//		businessManagerid :searchPanel.getCmpByName('Q_creator_S_LK').getValue(),
												businessManager :searchPanel.getCmpByName('businessManager').getValue(),
												
												startDateg :searchPanel.getCmpByName("Q_startDate_D_GE").getValue(),
												startDatel :searchPanel.getCmpByName("Q_startDate_D_LE").getValue(),
												projectStatus:"提前终止"
											});
											tabs.add(gpObj);
                                    	tabs.setActiveTab("ReportPreviewfinancingprojectdetail");
									}
								}*/
								]
				});
                }
				var projectStatus = this.projectStatus;
				this.gridPanel=new HT.GridPanel({
					name : 'FinancingProjectGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					rowActions : false,
					url : __ctxPath + "/flFinancing/projectListFlFinancingProject.do",
					baseParams:{
						'projectStatus' : this.projectStatus == 6?null:this.projectStatus,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects,
						'keyWord' : this.keyWord
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId', 'projectStatus','creator','orgName', 'executor','projectName', 'projectNumber','customerName','startDate','endDate','projectMoney','payProjectMoney','oppositeTypeValue','customerName','projectStatus','operationType','operationTypeValue','taskId','activityName','oppositeId','oppositeType','businessType','businessManagerValue'],
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
								header : 'operationType',
								dataIndex : 'operationType', 
								hidden : true
							},{
								header : 'businessType',
								dataIndex : 'businessType', 
								hidden : true
							}, {
								header : '所属分公司',
								hidden : isShow?false : true,
								width : 100,
								dataIndex : 'orgName'
							}, {
								header : '项目编号',
								width : 130,
								dataIndex : 'projectNumber'
							}, {
								header : '客户名称',
								width : 68,
								dataIndex : 'customerName'
							}, {
								header : '融资金额',
								align : "right",
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
								width : 70,
								dataIndex : 'businessManagerValue'
							}, {
								header : '项目阶段',
								width : 118,
								hidden : this.isHidden,
								dataIndex : 'activityName',
								renderer : function(value, cellmeta, record, rowIndex, columnIndex, store) {
									if(projectStatus == 6) {
										var ps = parseInt(record.data["projectStatus"]);
										switch(ps) {
											case 0 :
												return "办理中";
												break;
											case 1 :
												return "还款中";
												break;
											case 2 :
												return "已完成";
												break;
											case 3 :
												return "提前终止";
												break;
											default : 
												break;
										}
									}else {
										return value;
									}
								}
							}, {
								header : '执行人',
								width : 70,
								hidden : (this.projectStatus==2||this.projectStatus==3)?true:false,
								dataIndex : 'executor'
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
								dataIndex : 'endDate'
							}, {
								header : '终止时间',
								width : 76,
								hidden : this.isHiddenSd,
								dataIndex : 'endDate'
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
