/**
 * @author lisl
 * @extends Ext.Panel
 * @description 融资项目信息管理
 * @company 智维软件
 * @createtime:
 */
ProjectFinished = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_f1');//是否授权显示所有项目记录
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				ProjectFinished.superclass.constructor.call(this, {
							id : 'pojectFinished',
							title : '项目结项',
							region : 'center',
							layout : 'border',
							iconCls : "btn-ok",
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
						value : 1
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
						columnWidth : .14,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 61,
						defaults : {
							anchor : anchor
						},
						items : [{
								fieldLabel:'融资日期',
								name : 'Q_startDate_D_GE',
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
								name : 'Q_startDate_D_LE',
								xtype : 'datefield',
								labelSeparator:"",
								format : 'Y-m-d'
							}]
					}, {
						columnWidth : .07,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 10,
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
				this.topbar = new Ext.Toolbar({
					items : [
						{
							iconCls : 'btn-ok',
							text : '项目结项',
							xtype : 'button',
							scope : this,
							handler : function() {
							
								this.record= this.gridPanel.getSelectionModel().getSelected();
								var tabs = Ext.getCmp('centerTabPanel');
								var gpObj = document.getElementById("ProjectFinishedForm_" + this.record.data.projectId);
								if (gpObj == null) {
									gpObj = new ProjectFinishedForm({record : this.record});
									tabs.add(gpObj);
								}
								tabs.setActiveTab("ProjectFinishedForm_" + this.record.data.projectId);
							}
						}]
				});
	
				this.gridPanel=new HT.GridPanel({
					name : 'FinancingProjectGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					rowActions : false,
					url : __ctxPath + "/flFinancing/projectListFlFinancingProject.do",
					baseParams:{
						'projectStatus' : 1,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId', 'projectStatus','creator','orgName', 'projectName', 'projectNumber','customerName','startDate','endDate','projectMoney','oppositeTypeValue','customerName','projectStatus','operationType','operationTypeValue','taskId','activityName','oppositeId','oppositeType','businessType','businessManagerValue'],
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
								dataIndex : 'activityName'
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
