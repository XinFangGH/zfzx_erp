Ext.ns('DutySectionView');
/**
 * 班次定义列表
 */
var DutySectionView = function() {
	return new Ext.Panel({
		iconCls:'menu-dutySection',
		id : 'DutySectionView',
		title : '班次定义列表',
		autoScroll : true,
		layout:'border',
		region : 'center',
		items : [this.setup()]
	});
};
/**
 * 建立视图
 */
DutySectionView.prototype.setup = function() {
	return this.grid();
};
/**
 * 建立DataGrid
 */
DutySectionView.prototype.grid = function() {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'sectionId',
					dataIndex : 'sectionId',
					hidden : true
				}, {
					header:'班次名称',
					dataIndex:'sectionName'
				},{
					header : '开始签到',
					dataIndex : 'startSignin1'
				}, {
					header : '上班时间',
					dataIndex : 'dutyStartTime1'
				}, {
					header : '签到结束时间',
					dataIndex : 'endSignin1'
				}, {
					header : '早退计时',
					dataIndex : 'earlyOffTime1'
				}, {
					header : '下班时间',
					dataIndex : 'dutyEndTime1'
				}, {
					header : '签退结束',
					dataIndex : 'signOutTime1'
				}, {
					header : '管理',
					dataIndex : 'sectionId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.sectionId;
						var str='';
						if(isGranted('_DutySectonDel')){
							str= '<button title="删除" value=" " class="btn-del" onclick="DutySectionView.remove('
								+ editId + ')">&nbsp;&nbsp;</button>';
						}
						if(isGranted('_DutySectonEdit')){
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DutySectionView.edit('
								+ editId + ')">&nbsp;&nbsp;</button>';
						}
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
				id : 'DutySectionGrid',
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
					     if(isGranted('_DutySectonEdit')){
							DutySectionView.edit(rec.data.sectionId);
					     }
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
DutySectionView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/personal/listDutySection.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'sectionId',
										type : 'int'
									}
									,'sectionName'
									, 'startSignin1', 'dutyStartTime1',
									'endSignin1', 'earlyOffTime1',
									'dutyEndTime1', 'signOutTime1']
						}),
				remoteSort : true
			});
	store.setDefaultSort('sectionId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
DutySectionView.prototype.topbar = function() {
	var toolbar = new Ext.Toolbar({
				id : 'DutySectionFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if(isGranted('_DutySectonAdd')){
	   toolbar.add(new Ext.Button({
	        iconCls : 'btn-add',
			text : '添加班次定义',
			handler : function() {
				new DutySectionForm();
			}
	   }));
	}
	if(isGranted('_DutySectonDel')){
	   toolbar.add(new Ext.Button({
	        iconCls : 'btn-del',
			text : '删除班次定义',
			handler : function() {
				var grid = Ext.getCmp("DutySectionGrid");
				var selectRecords = grid.getSelectionModel()
						.getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.sectionId);
				}

				DutySectionView.remove(ids);
			}
	   }));
	}
	return toolbar;
};

/**
 * 删除单个记录
 */
DutySectionView.remove = function(id) {
	var grid = Ext.getCmp("DutySectionGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/personal/multiDelDutySection.do',
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
DutySectionView.edit = function(id) {
	new DutySectionForm(id);
}
