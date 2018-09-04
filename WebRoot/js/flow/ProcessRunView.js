Ext.ns('ProcessRunView');
/**
 * 申请列表
 */
//var myCreateProjectNames="smallLoanFlow,smallLoanFast,FinancingFlow,guaranteeNormalFlow";
var ProcessRunView = function() {
	return new Ext.Panel({
		id : 'ProcessRunView',
		title : '我发起的项目',
		iconCls:'menu-flowMine',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'ProcessRunSearchForm',
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
				margins : {
					top : 0,
					right : 4,
					bottom : 4,
					left : 4
				}
			},
			items : [{
						text : '请输入查询条件:'
					}, {
						text : '标题'
					}, {
						xtype : 'textfield',
						name : 'Q_subject_S_LK'
					}, {text:'时间 从'},
					   {xtype:'datefield',name:'Q_createtime_D_GT',format:'Y-m-d'},
					   {text:' 至 '},
					   {xtype:'datefield',name:'Q_createtime_D_LT',format:'Y-m-d'},
					   {text:'状态'},
					   {
						   	xtype:'combo',
						   	width:80,
						   	hiddenName:'Q_runStatus_SN_EQ',
						   	editable:false,
						   	mode:'local',
						   	triggerAction : 'all',
						   	store :[['1','正在运行'],['2','结束'],['3','终止']]
					   	}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('ProcessRunSearchForm');
							var gridPanel = Ext.getCmp('ProcessRunGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
ProcessRunView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
ProcessRunView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm,new Ext.grid.RowNumberer(), {
					header : 'runId',
					dataIndex : 'runId',
					hidden : true
				}, {
					header : '标题',
					dataIndex : 'subject',
					width : 866
				},{
					header : '时间',
					dataIndex : 'createtime',
					width : 125
				},  {
					header:'流程状态',
					dataIndex:'runStatus',
					width : 125,
					renderer:function(val){
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
				id : 'ProcessRunGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				tbar : [
					{
						text:'详细',
						iconCls:'btn-flowView',
						scope:this,
						handler : function() {
							ProcessRunView.detail(grid);
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
			
			grid.addListener('rowdblclick', function(gridPanel,
						rowindex, e) {
					gridPanel.getSelectionModel().each(function(rec) {
							ProcessRunView.detail(grid);
					});
			});
	return grid;

};

/**
 * 初始化数据
 */
ProcessRunView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/flow/listProcessRun.do?processName='+myProcessNameFlowKey
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'runId',
										type : 'int'
									}, 'subject','createtime',
									'defId', 'piId','runStatus','businessType','projectId']
						}),
				remoteSort : true
			});
	store.setDefaultSort('runId', 'desc');
	return store;
};

/**
 * 显示明细
 * @param {} runId
 * @param {} name
 */
ProcessRunView.detail = function(grid){
	editPro(grid);
};