Ext.ns('ErrandsRegisterOutView');
/**
 * [ErrandsRegister]列表
 */
var ErrandsRegisterOutView = function() {
	return new Ext.Panel({
		id : 'ErrandsRegisterOutView',
		title : '外出情况列表',
		iconCls:'menu-errands',
		layout:'border',
		region : 'center',
		autoScroll : true,
		items : [new Ext.FormPanel({
			id : 'ErrandsRegisterOutSearchForm',
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
						text : '查询条件:'
					}, {
						text : '开始时间:从'
					}, {
						xtype : 'datetimefield',
						format:'Y-m-d H:i:s',
						name : 'Q_startTime_D_GE',
						editable:false
					}, {
						text : '到'
					}, {
						xtype : 'datetimefield',
						format:'Y-m-d H:i:s',
						name : 'Q_endTime_D_LE',
						editable:false
					}, {
						text : '审批状态'
					}, {
						xtype : 'combo',
						hiddenName : 'Q_status_SN_EQ',
						mode : 'local',
						width:80,
						editable : false,
						triggerAction : 'all',
						store : [['0','未审批'],['1','通过审批']]
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext
									.getCmp('ErrandsRegisterOutSearchForm');
							var gridPanel = Ext.getCmp('ErrandsRegisterOutGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					},{
					  xtype:'button',
					  text:'重置',
					  iconCls:'btn-reseted',
					  handler:function(){
					    var searchPanel = Ext
									.getCmp('ErrandsRegisterOutSearchForm');
						searchPanel.getForm().reset();
					  }
					}]
		}), this.setup()]
	});
};
/**
 * 建立视图
 */
ErrandsRegisterOutView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
ErrandsRegisterOutView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'dateId',
					dataIndex : 'dateId',
					hidden : true
				}, {
					header : '描述',
					dataIndex : 'descp'
				}, {
					header : '开始日期',
					dataIndex : 'startTime'
				}, {
					header : '结束日期',
					dataIndex : 'endTime'
				}, {
					header : '审批状态',
					dataIndex : 'status',
					renderer:function(value){
						if(value=='0'){
						  return '未审批';
						}
						if(value=='1'){
						  return '通过审批';
						}
					}
				}, {
					header : '审批意见',
					dataIndex : 'approvalOption'
				}, {
					header : '审批人',
					dataIndex : 'approvalName'
				}, {
					header : '管理',
					dataIndex : 'dateId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.dateId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="ErrandsRegisterOutView.remove('
								+ editId + ')">&nbsp;&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ErrandsRegisterOutView.edit('
								+ editId + ')">&nbsp;&nbsp;</button>';
						return str;
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
				id : 'ErrandsRegisterOutGrid',
				tbar : this.topbar(),
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				region : 'center',
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							ErrandsRegisterOutView.edit(rec.data.dateId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
ErrandsRegisterOutView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath
									+ '/personal/listErrandsRegister.do'
						}),
			    baseParams:{'Q_flag_SN_EQ':2},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'dateId',
										type : 'int'
									}

									, {
										name : 'userName',
										mapping : 'appUser.fullname'
									}, 'descp', 'startTime', 'endTime',
									'approvalId', 'status', 'approvalOption',
									'approvalName', 'flag']
						}),
				remoteSort : true
			});
	store.setDefaultSort('dateId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
ErrandsRegisterOutView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'ErrandsRegisterOutFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加外出单',
							xtype : 'button',
							handler : function() {
								new ErrandsRegisterOutForm();
							}
						}, {
							iconCls : 'btn-del',
							text : '删除外出单',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("ErrandsRegisterOutGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.dateId);
								}

								ErrandsRegisterOutView.remove(ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
ErrandsRegisterOutView.remove = function(id) {
	var grid = Ext.getCmp("ErrandsRegisterOutGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/personal/multiDelErrandsRegister.do',
								params : {
									ids : id
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													start : 0,
													limit : 25
												}
											});
								}
							});
				}
			});
};

/**
 * 
 */
ErrandsRegisterOutView.edit = function(id) {
	new ErrandsRegisterOutForm(id);
}
