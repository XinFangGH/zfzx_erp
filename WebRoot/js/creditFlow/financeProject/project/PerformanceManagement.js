/**
 * @author lisl
 * @extends Ext.Panel
 * @description 融资项目信息管理
 * @company 智维软件
 * @createtime:
 */
PerformanceManagement = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				PerformanceManagement.superclass.constructor.call(this, {
							id : 'PerformanceManagement',
							title : '业绩管理',
							region : 'center',
							layout : 'border',
							iconCls : "menu-finance-report",
							items : [
								this.searchPanel,
								this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				var anchor='100%';
				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.isGrantedShowAllProjects = isGranted('_seeAllPerformances');//是否授权显示所有项目记录
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
								fieldLabel:'融资日期',
								name : 'Q_startDate_D_GE',
								xtype : 'datefield',
								format : 'Y-m-d'
								
							}]
					}, {
						columnWidth : .11,
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
					},{
						columnWidth : .25,
						labelAlign : 'right',
						xtype : 'container',
						layout : 'form',
						labelWidth : 61,
						defaults : {
							anchor : anchor
						},
						items : [{
								name : 'department',
								xtype:'trigger',
								fieldLabel : '所属部门',
								submitValue : true,
								triggerClass :'x-form-search-trigger',
								editable : false,
								scope : this,
								onTriggerClick : function(){
								    var obj = this;
									DepSelector.getView(function(id, name) {
//										alert("id:"+id+" name:"+name);
										obj.setValue(name);
										obj.ownerCt.ownerCt.getCmpByName('departmentId').setValue(id);
									}, true).show();//false为多选,true为单选。
								}
							}]
					
					}, {
						xtype:'hidden',
						name:'departmentId'
					},{
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
                this.topbar = new Ext.Toolbar({
					items : [
						{
							iconCls : 'btn-xls',
							text : '导出Excel',
							xtype : 'button',
							scope : this,
							handler : function() {
								var departmentId = this.getCmpByName("departmentId").getValue();
								window.open(__ctxPath+ "/flFinancing/exportExcelFlFinancingProject.do?isGrantedShowAllProjects="+this.isGrantedShowAllProjects+"&projectStatus="+1+"&departmentId="+departmentId, '_blank');
								
							}
						}]
				});
	
				this.gridPanel=new HT.GridPanel({
					name : 'PerformanceManagementGrid',
					region : 'center',
					tbar : this.topbar,
					notmask :true,
					rowActions : false,
					plugins: [summary],
					url : __ctxPath + "/flFinancing/projectListFlFinancingProject.do",
					baseParams:{
						'projectStatus' : 1,
						'isGrantedShowAllProjects' : this.isGrantedShowAllProjects
					},
					fields : [{
								name : 'runId',
								type : 'int'
							}, 'projectId','creator','orgName','accrual','accrualTypeValue', 'projectNumber','startDate','endDate','projectMoney','businessManagerValue','isAheadPay','customerName'],
					columns : [
							{
								header : 'projectId',
								dataIndex : 'projectId',
								hidden : true
							}, {
								header : '所属分公司',
								hidden : isShow?false : true,
								width : 100,
								dataIndex : 'orgName'
							}, {
								header : '项目编号',
								width : 160,
								summaryType: 'count',
								summaryRenderer: totalMoney,
								dataIndex : 'projectNumber'
							}, {
								header :'客户名称',
								dataIndex :'customerName'
							},{
								header : '融资金额',
								align : "right",
								summaryType: 'sum',
								width : 140,
								dataIndex : 'projectMoney',
								renderer : function(value) {
									if(value == "") {
										return "0.00元";
									}else {
										return Ext.util.Format.number(value,',000,000,000.00')+"元";
									}
								}
							}, {
								header : '融资起始日',
								width : 85,
								dataIndex : 'startDate',
								sortable : true
							}, {
								header : '融资利率',
								width : 85,
								dataIndex : 'accrual',
								sortable : true,
								renderer : function(value) {
									if(value == "") {
										return "";
									}else {
										return Ext.util.Format.number(value,'0.00')+"%";
									}
								}
							}, {
								header : '计息方式',
								width : 85,
								dataIndex : 'accrualTypeValue',
								sortable : true
							}, {
								header : '业务经理',
								width : 85,
								dataIndex : 'businessManagerValue'
							}, {
								header : '是否进行提前还款',
								width : 420,
								dataIndex : 'isAheadPay',
								sortable : true,
								renderer : function(value) {
									if(value == "0") {
										return "否";
									}else if(value == "1"){
										return "是";
									}
								}
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
