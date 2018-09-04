Ext.ns('ContractView');
/**
 * @author:
 * @class ContractView
 * @extends Ext.Panel
 * @description 合同列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
ContractView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ContractView.superclass.constructor.call(this, {
					id : 'ContractView',
					title : '合同列表',
					iconCls : 'menu-contract',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			id : 'ContractSearchForm',
			height : 35,
			region : 'north',
			frame : false,
			border : false,
			layout : 'hbox',
			layoutConfig : {
				padding : '5',
				align : 'middle'
			},
			defaults : {
				style : 'padding:0px 5px 0px 5px;',
				border : false,
				anchor : '98%,98%',
				labelWidth : 75,
				xtype : 'label'
			},
			items : [{
						text : '查询条件:'
					}, {
						text : '合同编号'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_contractNo_S_LK'
					}, {
						text : '合同标题'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_subject_S_LK'
					}, {
						text : '签约人'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_signupUser_S_LK'
					}, {
						text : '所属项目'
					}, {
						xtype : 'textfield',
						width : 80,
						name : 'Q_projectId_S_LK'
					}, {
						xtype : 'button',
						text : '查询',
						iconCls : 'search',
						handler : function() {
							var searchPanel = Ext.getCmp('ContractSearchForm');
							var gridPanel = Ext.getCmp('ContractGrid');
							if (searchPanel.getForm().isValid()) {
								$search({
									searchPanel :searchPanel,
									gridPanel : gridPanel
								});
							}

						}
					}]
		});//end of the searchPanel
		
		this.store = new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/customer/listContract.do'
						}),
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'contractId',
										type : 'int'
									}

									, 'contractNo', 'subject',
									'contractAmount', 'creator', 'createtime',
									'project', 'signupUser', 'signupTime']
						}),
				remoteSort : true
			});//end of the store
	this.store.setDefaultSort('contractId', 'desc');
	this.store.load({
		params : {
			start : 0,
			limit : 25
		}
	})
	
	var sm = new Ext.grid.CheckboxSelectionModel();
	var cm = new Ext.grid.ColumnModel({
		columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'contractId',
					dataIndex : 'contractId',
					hidden : true
				}, {
					header : '合同编号',
					dataIndex : 'contractNo'
				}, {
					header : '合同标题',
					dataIndex : 'subject'
				}, {
					header : '合同金额',
					dataIndex : 'contractAmount',
					renderer : function(value) {
						return value + '元';
					}
				}, {
					header : '签约人',
					dataIndex : 'signupUser'
				}, {
					header : '签约时间',
					dataIndex : 'signupTime'
				}, {
					header : '所属项目',
					dataIndex : 'project',
					renderer : function(value) {
						return value.projectName;
					}
				}, {
					header : '管理',
					dataIndex : 'contractId',
					width : 50,
					sortable : false,
					renderer : function(value, metadata, record, rowIndex,
							colIndex) {
						var editId = record.data.contractId;
						var str = '';
						if (isGranted('_ContractDel')) {
							str = '<button title="删除" value=" " class="btn-del" onclick="ContractView.remove('
									+ editId + ')">&nbsp</button>';
						}
						if (isGranted('_ContractEdit')) {
							str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="ContractView.edit('
									+ editId + ')">&nbsp</button>';
						}
						return str;
					}
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});//end of the cm
	
	this.topbar = new Ext.Toolbar({
				id : 'ContractFootBar',
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
	if (isGranted('_ContractAdd')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-add',
					text : '添加合同',
					handler : function() {
						new ContractForm().show();
					}
				}));

	};
	if (isGranted('_ContractDel')) {
		this.topbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除合同',
					handler : function() {

						var grid = Ext.getCmp("ContractGrid");

						var selectRecords = grid.getSelectionModel()
								.getSelections();

						if (selectRecords.length == 0) {
							Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
							return;
						}
						var ids = Array();
						for (var i = 0; i < selectRecords.length; i++) {
							ids.push(selectRecords[i].data.contractId);
						}

						ContractView.remove(ids);
					}
				}));
	};//end of the topbar
	
	this.gridPanel = new Ext.grid.GridPanel({
				id : 'ContractGrid',
				tbar : this.topbar,
				region : 'center',
				store : this.store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				height : 600,
				cm : cm,
				sm : sm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : this.store})
			});

	this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
							if (isGranted('_ContractEdit')) {
								ContractView.edit(rec.data.contractId);
							}
						});
			});
	}//end of the initUIComponents
});
/**
 * 删除单个记录
 */
ContractView.remove = function(id) {
	var grid = Ext.getCmp("ContractGrid");
	Ext.Msg.confirm('信息确认', '删除合同，则合同下的<font color="red">附件</font>及<font color="red">配置单信息</font>也删除，您确认要删除该记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath
										+ '/customer/multiDelContract.do',
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
ContractView.edit = function(id) {
	new ContractForm({
		contractId : id
	}).show();
}
