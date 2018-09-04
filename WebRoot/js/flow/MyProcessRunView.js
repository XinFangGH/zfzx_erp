Ext.ns('MyProcessRunView');
/**
 * 申请列表
 */
//var myProcessName="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
var MyProcessRunView = function() {
	return new Ext.Panel({
		id : 'MyMyProcessRunView',
		title : '我参与的项目',
		iconCls:'btn-team2',
		layout:'border',
		region:'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id:'MyProcessRunSearchForm',
			region:'north',
			height : 40,
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				xtype : 'label',
				margins : '0 4 4 4'
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '标题'
					}, {
						xtype : 'textfield',
						name : 'Q_subject_S_LK'
					}, {text:'时间 从'},
					   {xtype:'datefield',name:'Q_vo.createtime_D_GT',format:'Y-m-d'},
					   {text:' 至 '},
					   {xtype:'datefield',name:'Q_vo.createtime_D_LT',format:'Y-m-d'},
					   {text:'状态'},
					   {
						   	xtype:'combo',
						   	width:80,
						   	hiddenName:'Q_runStatus_SN_EQ',
						   	editable:false,
						   	mode:'local',
						   	triggerAction : 'all',
						   	store :[['1','正在运行'],['2','结束'],['3','终止']]
					   	},
					   {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('MyProcessRunSearchForm');
							var gridPanel = Ext.getCmp('MyProcessRunGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					},{
						xtype : 'button',
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						handler : function(){
						  Ext.getCmp('MyProcessRunSearchForm').getForm().reset();
						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
MyProcessRunView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
MyProcessRunView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm,new Ext.grid.RowNumberer(), {
					header : 'runId',
					dataIndex : 'runId',
					align:'center',
					hidden : true
				}, {
					header : '标题',
					dataIndex : 'subject',
					width : 750
				},{
					header : '任务名称',
					sortable : false,
					dataIndex : 'currentActivityName',
					width : 120
				},{
					header : '时间',
					sortable : false,
					align:'center',
					dataIndex : 'createtime',
					width : 125
				},  {
					header : '流程状态',
					dataIndex : 'runStatus',
					align:'center',
					width : 125,
					renderer : function(val){
						if(val==0){
							return '<font color="red">草稿</font>';
						}else if(val==1){
							return '<font color="green">正在运行</font>';
						}else if(val==2){
							return '<font color="gray">结束</font>';
						}else if(val==3){
							return '<font color="red">终止</font>';
						}else if(val==10){
							return '<font color="red">已挂起</font>';
						}
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.store();
	store.load({
				params : {
					start : 0,
					limit : 25
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'MyProcessRunGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				tbar : [/*{
						text:'贷款详细',
						iconCls:'btn-flowView',
						scope:this,
						handler : function() {
							MyProcessRunView.detail(grid);
						}
					},*/ {
						iconCls : 'btn-flow-chart',
						text : '流程示意图',
						xtype : 'button',
						scope : this,
						handler : function() {
							MyProcessRunView.MyShowFlowImg(grid);
						}
					}
				],
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
			bbar : new HT.PagingBar({store : store})
			});
			
			/*grid.addListener('rowdblclick', function(gridPanel,
						rowindex, e) {
					gridPanel.getSelectionModel().each(function(rec) {
							MyProcessRunView.detail(grid);
					});
			});*/
						
	return grid;

};
/**
 * 初始化数据
 */
MyProcessRunView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/myProcessRun.do?processName='+myProcessNameFlowKey
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'runId',
										type : 'int'
									}, 'subject','createtime',
									'defId', 'piId','runStatus','businessType','projectId','currentActivityName']
						}),
				remoteSort : true
			});
	return store;
};
/**
 * 显示明细
 * @param {} runId
 * @param {} name
 */
MyProcessRunView.detail = function(grid){
	editPro(grid);
};

MyProcessRunView.MyShowFlowImg = function(grid){
		var selRs = grid.getSelectionModel().getSelections();
		if (selRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录！');
			return;
		}
		if (selRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录！');
			return;
		}
		
		var flowImagePanel = new Ext.Panel({
		autoHeight : true,
		border : false,
		html : '<img src="' + __ctxPath + '/jbpmImage?runId='
				+ selRs[0].get('runId')
				+ '&rand=' + Math.random() + '"/>'
		});

		var panel = new Ext.Panel({
					autoHeight : true,
					layout : 'form',
					border : false,
					items : [flowImagePanel]
				});

		// 若当前为子流程，则显示子流程
		if (this.isSubFlow) {
			panel.add({
						xtype : 'panel',
						autoHeight : true,
						border : false,
						html : '<img src="' + __ctxPath + '/jbpmImage?runId='
								+ selRs[0].get('runId')
								+ '&isSubFlow=true&rand=' + Math.random()
								+ '"/>'
					});
			panel.doLayout();
		}

		new Ext.Window({
			autoScroll : true,
			iconCls : 'btn-flow-chart',
			bodyStyle : 'background-color:white',
			maximizable : true,
			title : '流程示意图',
			width : 800,
			height : 600,
			modal : true,
			layout : 'fit',
			items : panel
		}).show();
};


