Ext.ns('DutyRegisterPersonView');
/**
 * 个人考勤 
 */
var DutyRegisterPersonView = function() {
	return new Ext.Panel({
		id : 'DutyRegisterPersonView',
		title : '个人考勤查询列表',
		iconCls:'menu-person',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'PersonDutyRegisterSearchForm',
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
						text : '登记时间从'
					}, {
						xtype:'datefield',
						format:'Y-m-d',
						name : 'Q_registerDate_DL_GE'
					}, {
						text:'至'
					},{
						xtype:'datefield',
						format:'Y-m-d',
						name : 'Q_registerDate_DG_LE'
					},{
						text : '上下班'
					}, {
						hiddenName : 'Q_inOffFlag_SN_EQ',
						xtype : 'combo',
						width: 100,
						mode : 'local',
						editable : true,
						triggerAction : 'all',
						store : [['1', '上班'], ['2', '下班']]
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('PersonDutyRegisterSearchForm');
							var gridPanel = Ext.getCmp('DutyRegisterPersonGrid');
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
DutyRegisterPersonView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
DutyRegisterPersonView.prototype.grid = function() {
	var weekdays=['周日','周一','周二','周三','周四','周五','周六'];
	var cm = new Ext.grid.ColumnModel({
		columns : [ new Ext.grid.RowNumberer(), {
					header : 'registerId',
					dataIndex : 'registerId',
					hidden : true
				}, {
					header : '登记时间',
					dataIndex : 'registerDate'
				}, {
					header : '登记人',
					dataIndex : 'fullname'
				}, {
					header : '登记标识',
					dataIndex : 'regFlag',
					renderer:function(val){
						if(val==1){
							return '<font color="green">√</font>';
						}else if (val==2){
							return '<font color="red">迟到</font>';
						}else if (val==3){
							return '<font color="red">早退</font>';
						}
					}
				},{
					header : '周几',
					dataIndex : 'dayOfWeek',
					renderer:function(val){
						return weekdays[val-1];
					}
				}, {
					header : '上下班标识',
					dataIndex : 'inOffFlag',
					renderer:function(val){
						if(val==1){
							return "上班";
						}else{
							return "下班";
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
				id : 'DutyRegisterPersonGrid',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
//				height : 400,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});
	return grid;

};

/**
 * 初始化数据
 */
DutyRegisterPersonView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/personal/personDutyRegister.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'registerId',
										type : 'int'
									}, 'registerDate', 'fullname', 'regFlag',
									'regMins', 'reason', 'dayOfWeek',
									'inOffFlag']
						}),
				remoteSort : true
			});
	store.setDefaultSort('registerId', 'desc');
	return store;
};