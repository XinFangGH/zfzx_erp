var ReportParamView = function(reportId,name) {
	this.title=name;	
	var window=new Ext.Window({
	    id : 'ReportParamView',
		title : this.title+'--查询参数设置',
		width : 650,
		autoHeight :true,
		x:200,
		y:50,
		modal : true,
		layout : 'fit',
		plain : true,
		bodyStyle : 'padding:5px;',
		buttonAlign : 'center',
		items : [this.setup(reportId)]
	});
	
	window.show();
	
};
/**
 * 建立视图
 */
ReportParamView.prototype.setup = function(reportId) {
	return this.grid(reportId);
};
/**
 * 建立DataGrid
 */
ReportParamView.prototype.grid = function(reportId) {
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'paramId',
					dataIndex : 'paramId',
					hidden : true
				}, {
					header : '参数名称',
					dataIndex : 'paramName'
				}, {
					header : '参数Key',
					dataIndex : 'paramKey'
				}, {
					header : '缺省值',
					dataIndex : 'defaultVal'
				}, {
					header : '类型',
					dataIndex : 'paramType',
					renderer:function(value){
					
					switch(value){
					
					case 'textfield':
						return '文件输入框';
						break;
					case 'datefield':
						return '日期输入框';
						break;
					case 'datetimefield':
						return '时间输入框';
						break;
					case 'numberfield':
						return '数字输入框';
						break;
					case 'combo':
						return '下拉框';
						break;
					case 'diccombo':
						return '数据字典';
						break;
					default:	
						return '文件输入框';
					}
					
				}
				}, {
					header : '序列号',
					dataIndex : 'sn'
				}, {
					header : '管理',
					dataIndex : 'paramId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.paramId;
						var str = '<button title="删除" value=" " class="btn-del" onclick="ReportParamView.remove('
								+ reportId +',\''+editId+ '\')">&nbsp;&nbsp;</button>';
						str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ReportParamView.edit('
								 + reportId +',\''+editId+ '\')">&nbsp;&nbsp;</button>';
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
					reportId:reportId
				}
			});
	var grid = new Ext.grid.GridPanel({
				id : 'ReportParamGrid',
				tbar : this.topbar(reportId),
				store : store,
				height:250,
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
				}
			});

	grid.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							ReportParamView.edit(reportId,rec.data.paramId);
						});
			});
	return grid;

};

/**
 * 初始化数据
 */
ReportParamView.prototype.store = function() {
	var store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/system/listReportParam.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							id : 'id',
							fields : [{
										name : 'paramId',
										type : 'int'
									}

									, 'reportId', 'paramName', 'paramKey',
									'defaultVal', 'paramType', 'sn']
						}),
				remoteSort : true
			});
	store.setDefaultSort('paramId', 'desc');
	return store;
};

/**
 * 建立操作的Toolbar
 */
ReportParamView.prototype.topbar = function(reportId) {
	var toolbar = new Ext.Toolbar({
				id : 'ReportParamFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : [{
							iconCls : 'btn-add',
							text : '添加参数',
							xtype : 'button',
							handler : function() {
								new ReportParamForm(reportId);
							}
						}, {
							iconCls : 'btn-del',
							text : '删除参数',
							xtype : 'button',
							handler : function() {

								var grid = Ext.getCmp("ReportParamGrid");

								var selectRecords = grid.getSelectionModel()
										.getSelections();

								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
									return;
								}
								var ids = Array();
								for (var i = 0; i < selectRecords.length; i++) {
									ids.push(selectRecords[i].data.paramId);
								}

								ReportParamView.remove(reportId,ids);
							}
						}]
			});
	return toolbar;
};

/**
 * 删除单个记录
 */
ReportParamView.remove = function(reportId,pramId) {
	
	var grid = Ext.getCmp("ReportParamGrid");
	Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/system/multiDelReportParam.do',
								params : {
									ids : pramId
								},
								method : 'post',
								success : function() {
									Ext.ux.Toast.msg("信息提示", "成功删除所选记录！");
									grid.getStore().reload({
												params : {
													reportId:reportId
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
ReportParamView.edit = function(reportId,paramId) {
	new ReportParamForm(reportId,paramId);
}
