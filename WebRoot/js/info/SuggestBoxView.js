/**
 * @author:
 * @class SuggestBoxView
 * @extends Ext.Panel
 * @description [SuggestBox]管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-01-16
 */
SuggestBoxView = Ext.extend(Ext.Panel, {
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
		SuggestBoxView.superclass.constructor.call(this, {
					id : 'SuggestBoxView',
					title : this.title ? this.title : '意见箱管理',
					region : 'center',
					iconCls : 'menu-suggestbox',
					layout : 'border',
					height : 600,
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
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
								text : '标题'
							}, {
								width : '10%',
								name : 'Q_subject_S_LK',
								xtype : 'textfield'
							}, {
								text : '日期  从'
							}, {
								name : 'Q_createtime_D_GE',
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								text : '至'
							}, {
								name : 'Q_createtime_D_LE',
								xtype : 'datefield',
								format : 'Y-m-d'
							}, {
								text : '发送人'
							}, {
								width : '10%',
								name : 'Q_senderFullname_S_LK',
								xtype : 'textfield',
								maxLength : 125
							}, {
								text : '发送人IP'
							}, {
								width : '10%',
								name : 'Q_senderIp_S_LK',
								xtype : 'textfield'
							}, {
								text : '是否公开'
							}, {
								hiddenName : 'Q_isOpen_SN_EQ',
								xtype : 'combo',
								editable : false,
								anchor : '10%',
								mode : 'local',
								triggerAction : 'all',
								store : [['', '全部'], ['0', '公开'], ['1', '未公开']]

							}, {
								xtype : 'button',
								text : '查询',
								iconCls : 'search',
								handler : this.search.createCallback(this),
								scope : this
							}, {
								xtype : 'button',
								text : '重置',
								iconCls : 'reset',
								handler : this.reset.createCallback(this),
								scope : this
							}, {
								name : 'Q_senderId_SN_EQ',
								id : 'SuggestBoxViewSearchSenderId',
								xtype : 'hidden'
							}]
				});// end of the searchPanel

		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/info/listSuggestBox.do",
					root : 'result',
					baseParams : {
						'Q_senderId_L_EQ' : this.userId
					},
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'boxId',
								type : 'int'
							}, 'subject', 'content', 'createtime', 'recUid',
							'recFullname', 'senderId', 'senderFullname',
							'senderIp', 'phone', 'email', 'isOpen',
							'replyContent', 'replyTime', 'replyId',
							'replyFullname', 'status', 'queryPwd']
				});
		this.store.setDefaultSort('boxId', 'desc');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		// this.rowActions = new Ext.ux.grid.RowActions({
		// header : '管理',
		// width : 80,
		// actions : [{
		// iconCls : 'btn-del',
		// qtip : '删除',
		// style : 'margin:0 3px 0 3px'
		// }, {
		// iconCls : 'btn-edit',
		// qtip : '编辑',
		// style : 'margin:0 3px 0 3px'
		// }, {
		// iconCls : 'btn-add',
		// qtip : '回复',
		// style : 'margin:0 3px 0 3px'
		// }, {
		// iconCls : 'btn-ok',
		// qtip : '浏览',
		// stype : 'margin:0 3px 0 3px'
		// }]
		// });

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var isOutSide = this.isOutSide;
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
						header : 'boxId',
						dataIndex : 'boxId',
						hidden : true
					}, {
						header : '意见标题',
						dataIndex : 'subject'
					}, {
						header : '是否公开',
						dataIndex : 'isOpen',
						renderer : function(value) {
							if (value == 0) {
								return '<font color="green">公开</font>';
							} else {
								return '<font color="red">未公开</font>';
							}
						}
					}, {
						header : '查询密码',
						dataIndex : 'queryPwd',
						renderer : function(value, metadata, record) {
							var isOpen = record.data.isOpen;
							if (value != null && isOpen == 1) {
								return '<font color="red">需要密码</font>';
							} else {
								return '<font color="green">无密码</font>';
							}
						}
					}, {
						header : '创建日期',
						dataIndex : 'createtime',
						renderer : function(value) {
							return value.substring(0, 10);
						}
					}, {
						header : '发送人',
						dataIndex : 'senderFullname',
						renderer : function(value) {
							if (value != null && value != '') {
								return value;
							} else {
								return '匿名';
							}
						}
					}, {
						header : '发送人IP',
						dataIndex : 'senderIp'
					}, {
						header : '状态',
						dataIndex : 'status',
						renderer : function(value) {
							if (value == 0) {
								return '<font color="blue">草稿</font>';
							} else if (value == 1) {
								return '<font color="red">提交</font>';
							} else {
								return '<font color="green">已回复</font>';
							}
						}
					}, {
						header : '管理',
						dataIndex : 'newsId',
						renderer : function(value, metadata, record, rowIndex,
								colIndex) {
							var status = record.data.status;
							var isOpen = record.data.isOpen;
							var queryPwd = record.data.queryPwd;
							var needPwd = false;
							if(isOpen == 1 && queryPwd !=null && queryPwd !=''){
								needPwd = true;
							}
							var editId = record.data.boxId;
							var str = '';
							if(!isOutSide){
								str = '<button title="删除" value=" " class="btn-del" onclick="SuggestBoxView.delByIds('
										+ editId + ')">&nbsp</button>';
	
								if (status == 0) {
									str += '<button title="编辑" value=" " class="btn-edit" onclick="SuggestBoxView.editRecord('
											+ editId + ')">&nbsp</button>';
								} else if (status == 1 && this.userId == null) {
									str += '<button title="回复" value=" " class="btn-suggest-reply" onclick="SuggestBoxView.reply('
											+ editId + ')">&nbsp</button>';
								}
							}
							str += '<button title="浏览" value=" " class="btn-suggest-scan" onclick="SuggestBoxView.scan('
									+ editId + ','+needPwd+')">&nbsp</button>';

							return str;
						}
					}],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		// 初始化工具栏
		this.topbar = new Ext.Toolbar({
			height : 30,
			bodyStyle : 'text-align:left',
			items : [{
				iconCls : 'btn-add',
				text : '添加',
				xtype : 'button',
				handler : this.createRecord
			}]
		});
				
		if(!this.isOutSide){
			this.topbar.add('-');
			this.topbar.add( {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				handler : this.delRecords,
				scope : this
			});
		}
		
				
		this.gridPanel = new Ext.grid.GridPanel({
					id : 'SuggestBoxGrid',
					region : 'center',
					stripeRows : true,
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
//					height : 600,
					cm : cm,
					sm : sm,
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					bbar : new Ext.PagingToolbar({
						pageSize : 25,
						store : this.store,
						displayInfo : true,
						displayMsg : '当前页记录索引{0}-{1}， 共{2}条记录',
						emptyMsg : "当前没有记录"
					})
				});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
				var status = rec.data.status;
				var isOpen = rec.data.isOpen;
				var queryPwd = rec.data.queryPwd;
				var needPwd = false;
				if(isOpen == 1 && queryPwd !=null && queryPwd !=''){
					needPwd = true;
				}
				if (status == 0) {
					new SuggestBoxForm({
								boxId : rec.data.boxId
							}).show();
				} else {
					SuggestBoxView.scan(rec.data.boxId,needPwd);
				}
			});
		});
	},// end of the initComponents()

	/**
	 * 
	 * @param {}
	 *            self 当前窗体对象
	 */
	search : function(self) {
		if (self.searchPanel.getForm().isValid()) {// 如果合法
			$search({
				searchPanel :self.searchPanel,
				gridPanel : self.gridPanel
			});
		}
	},

	/**
	 * 清空
	 */
	reset : function(self){
		self.searchPanel.getForm().reset();
	},
	
	/**
	 * 添加记录
	 */
	createRecord : function() {
		new SuggestBoxForm().show();
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('SuggestBoxGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.boxId);
		}
		SuggestBoxView.delByIds(ids);
	}
});
SuggestBoxView.delByIds = function(ids) {
	Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
								url : __ctxPath + '/info/multiDelSuggestBox.do',
								params : {
									ids : ids
								},
								method : 'POST',
								success : function(response, options) {
									Ext.ux.Toast.msg('操作信息',
											'成功删除该[SuggestBox]！');
									Ext.getCmp('SuggestBoxGrid').getStore()
											.reload();
								},
								failure : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
								}
							});
				}
			});// end of comfirm
};
/**
 * 编辑
 * 
 * @param {}
 *            id
 */
SuggestBoxView.editRecord = function(id) {
	new SuggestBoxForm({
				boxId : id
			}).show();
};
/**
 * 回复
 */
SuggestBoxView.reply = function(id) {
	new SuggestBoxReplyForm({
				boxId : id
			}).show();
};
/**
 * 浏览
 */
SuggestBoxView.scan = function(id,needPwd) {
	if(needPwd){
		var form = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					url : __ctxPath + '/info/matchSuggestBox.do',
					defaults : {
						anchor : '98%,98%'
					},
					items : [{
								fieldLabel : '访问密码',
								name : 'suggestBox.queryPwd',
								id : 'queryPwd',
								xtype : 'textfield',
								inputType : 'password'
							}, {
								name : 'suggestBox.boxId',
								value : id,
								xtype : 'hidden'
							}]
				});
		var win = new Ext.Window({
					layout : 'fit',
					iconCls : 'btn-add',
					items : form,
					modal : true,
					minHeight : 149,
					minWidth : 499,
					height : 150,
					width : 500,
					maximizable : true,
					title : '访问密码',
					buttonAlign : 'center',
					buttons : [{
						text : '确定',
						iconCls : 'btn-save',
						handler : function() {
							if (form.getForm().isValid()) {
								form.getForm().submit({
									method : 'POST',
									waitMsg : '正在提交数据...',
									success : function(fp, action) {
										win.close();
										new SuggestBoxDisplay({
											boxId : id
										}).show();
									},
									failure : function() {
										Ext.ux.Toast.msg('操作信息', '访问密码不正确！');
									}
								});
							}
						}
					},{
						text : '取消',
						iconCls : 'btn-cancel',
						handler : function() {
							win.close();
						}
					}]
				});
		win.show();
	}else{
		new SuggestBoxDisplay({
				boxId : id
			}).show();
	}
	
};