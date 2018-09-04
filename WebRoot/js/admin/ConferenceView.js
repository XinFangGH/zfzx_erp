/**
 * @author: YHZ
 * @class ConferenceView
 * @extends Ext.Panel
 * @description 会议信息管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-10-8 PM
 */
ConferenceView = Ext.extend(Ext.Panel, {
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
		ConferenceView.superclass.constructor.call(this, {
					id : 'ConferenceView',
					title : '会议信息管理',
					region : 'center',
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		//start of this searchPanel
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			region : 'north',
			width : '98%',
			height : 120,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			},
			items : [{
				border : false,
				layout : 'hbox',
				region : 'center',
				layoutConfig : {
					padding : '5',
					align : 'left'
				},
				defaults : {
					xtype : 'label',
					margins : {
						top : 0,
						right : 4,
						bottom : 0,
						left : 4
					}
				},
				items : [{
					text : '会议信息:',
					style : 'padding-right : 65px;padding-left : 0px;'
				},{
					text : '会议标题:'
				},{
					xtype : 'textfield',
					name : 'Q_confTopic_S_LK',
					width : 200,
					maxLength : 256,
					maxLengthText : '会议标题输入长度不能超过256个字符！'
				},{
					style : 'padding-right : 13px;',
					text : '会议类型：'
				},{
					width : 200,
					xtype : 'combo',
					hiddenName : 'Q_confProperty_S_LK',
					fieldLabel : '会议类型',
					valueField : 'typeId',
					displayField : 'typeName',
					mode : 'local',
					editable : false,
					triggerAction : 'all',
					forceSelection : true,
					store : new Ext.data.SimpleStore({
								url : __ctxPath + '/admin/getTypeAllConference.do',
								autoLoad : true,
								fields : ['typeId', 'typeName']
					})
				},{
					style : 'padding-right : 5px;',
					text : '会议内容：'
				},{
					xtype : 'textfield',
					name : 'Q_confContent_S_LK',
					width : 200,
					maxLength : 4000,
					maxLengthText : '会议内容长度不能超过4000个字符！'
				}]
			},{
				border : false,
				layout : 'hbox',
				region : 'center',
				layoutConfg : {
					align : 'left'
				},
				defaults : {
					xtype : 'label',
					margins : {
						left : 4,
						right : 4,
						top : 0,
						bottom : 0
					}
				},
				items : [
					{
						text : '会议室信息:',
						style : 'padding-right : 40px;padding-left : 0px;'
					},{
						text : '会议室名称：'
					},{
						xtype : 'textfield',
						name : 'Q_roomName_S_LK',
						width : 200,
						maxLength : 156,
						maxLengthText : '会议室输入字符不能超过156长度！'
					},{
						text : '会议室类型：'
					},{
						width : 200,
						xtype : 'combo',
						hiddenName : 'Q_roomId_L_EQ',
						fieldLabel : '会议室类型',
						valueField : 'roomId',
						displayField : 'roomName',
						mode : 'local',
						editable : false,
						triggerAction : 'all',
						forceSelection : true,
						store : new Ext.data.SimpleStore({
							url : __ctxPath + '/admin/getBoardrooConference.do',
							autoLoad : true,
							fields : ['roomId', 'roomName']
						})
					},{
						text : '会议室地址:'
					},{
						width : 200,
						xtype : 'textfield',
						name : 'Q_roomLocation_S_LK',
						maxLength : 156,
						maxLengthText : '会议室地址输入字符不能超过156个字符！'
					}				
				]
			},{
				layout : 'hbox',
				region : 'center',
				border : false,
				layoutConfig : {
					padding : '5px',
					align : 'center'
				},
				defaults : {
					xtype : 'label',
					margins : {
						left : 4,
						top : 0,
						right : 4,
						bottom : 0
					}
				},
				items : [{
					style : 'padding-right : 125px;',
					text : '会议时间:'
				},{
					xtype : 'datefield',
					name : 'Q_startTime_D_GE',
					format : 'Y-m-d',
					width : 90
				},{
					text : '至'
				},{
					xtype : 'datefield',
					name : 'Q_endTime_D_LE',
					format : 'Y-m-d',
					width : 90
				}]
			},{
				layout : 'hbox',
				region : 'center',
				border : false,
				layoutConfig : {
					padding : '5px',
					align : 'center'
				},
				defaults : {
					xtype : 'button',
					margins : {
						left : 4,
						top : 0,
						right : 4,
						bottom : 0
					}
				},
				items : [{
					xtype : 'label',
					style : 'padding-left:40%;',
					text : ' '
				},{
					iconCls : 'search',
					text : '搜索',
					handler : this.search.createCallback(this)
				},{
					iconCls : 'btn-reset',
					style : 'padding-right:40%;',
					text : '重置',
					handler : this.reset.createCallback(this)
				}]
			}]
		}); // end of this searchPanel
		
		// 加载数据至store
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/admin/listConference.do",
					root : 'result',
					totalProperty : 'totalCounts',
					remoteSort : true,
					fields : [{
								name : 'confId',
								type : 'int'
							}, 'confTopic', 'compereName', 'roomName',
							'roomLocation', 'attendUsersName', 'startTime',
							'endTime', 'status']
				});
		this.store.setDefaultSort('confId', 'desc');
		// 加载数据
		this.store.load({
					params : {
						start : 0,
						limit : 25
					}
				});

		this.rowActions = new Ext.ux.grid.RowActions({
					header : '管理',
					width : 80,
					actions : [{
								iconCls : 'btn-del',
								qtip : '删除',
								style : 'margin:0 3px 0 3px'
							}, {
								iconCls : 'btn-edit',
								qtip : '编辑',
								style : 'margin:0 3px 0 3px'
							}, {
								iconCls : 'btn-showDetail',
								qtip : '查看',
								style : 'margin:0 3px 0 3px'
							}]
				});

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'confId',
								dataIndex : 'confId',
								hidden : true
							}, {
								header : '会议标题',
								dataIndex : 'confTopic'
							}, {
								header : '主持人',
								dataIndex : 'compereName'
							}, {
								header : '与会人员',
								dataIndex : 'attendUsersName'
							},  {
								header : '会议室名',
								dataIndex : 'roomName'
							}, {
								header : '会议地址',
								dataIndex : 'roomLocation'
							}, {
								header : '开始时间',
								dataIndex : 'startTime'
							}, {
								header : '结束时间',
								dataIndex : 'endTime'
							},this.rowActions],
					defaults : {
						sortable : true,
						menuDisabled : true,
						width : 100
					}
				});
		// 初始化工具栏
		this.topbar = new Ext.Toolbar({
					height : 30,
					bodyStyle : 'text-align:left',
					items : [{
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								handler : this.delRecords,
								scope : this
							},{
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								handler : this.edit,
								scope : this
							}, {
								text : '会议申请',
								iconCls : 'btn-add',
								xtype : 'button',
								handler : function() {
									new ConferenceForm().show();
								}
							}]
				});

		this.gridPanel = new Ext.grid.GridPanel({
					id : 'ConferenceGrid',
					region : 'center',
					stripeRows : true,
					tbar : this.topbar,
					store : this.store,
					trackMouseOver : true,
					disableSelection : false,
					loadMask : true,
					cm : cm,
					sm : sm,
					plugins : this.rowActions,
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
				new ConferenceForm({
					confId : rec.data.confId
				}).show();
			});
		});
		this.rowActions.on('action', this.onRowAction, this);
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
	reset : function(self) {
		self.searchPanel.getForm().reset();
	},

	/**
	 * 添加记录
	 */
	createRecord : function() {
		new ConferenceForm().show();
	},
	/**
	 * 按IDS删除记录
	 */
	delByIds : function(ids) {
		Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath
							+ '/admin/multiDelConference.do',
					params : {
						ids : ids
					},
					method : 'POST',
					success : function(response, options) {
						Ext.ux.Toast.msg('操作信息', '成功删除该会议信息！');
						Ext.getCmp('ConferenceGrid').getStore().reload();
					},
					failure : function(response, options) {
						Ext.Msg.alert('操作信息', '操作出错，请联系管理员！');
					}
				});
			}
		});// end of comfirm
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('ConferenceGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.confId);
		}
		this.delByIds(ids);
	},

	/**
	 * 编辑
	 */
	edit : function(){
		var gridPanel = Ext.getCmp('ConferenceGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if(selectRecords.length == 0){
			Ext.ux.Toast.msg('操作提示','请选择要编辑的记录！');
			return;
		}
		var confId = selectRecords[0].data.confId;
		Ext.Ajax.request({
			url : __ctxPath + '/admin/allowUpdaterConfPrivilege.do',
			params : {
				confId : confId
			},
			waitMsg : '数据正在提交，请稍后...',
			method : 'post',
			success : function(response,op){
				var res = Ext.util.JSON.decode(response.responseText);
				if(res.success){
					new ConferenceForm({
						confId : confId
					}).show();
				}else {
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : res.msg,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
				}
			}
		});
	},
	
	/**
	 * 查看详细信息
	 */
	showDetail : function(confId) {
		ConferenceDetailForm.show(confId);
	},

	/**
	 * 编辑记录,首先判断用户权限
	 */
	editRecord : function(record) {
		var confId = record.data.confId;
		Ext.Ajax.request({
			url : __ctxPath + '/admin/allowUpdaterConfPrivilege.do',
			params : {
				confId : confId
			},
			waitMsg : '数据正在提交，请稍后...',
			method : 'post',
			success : function(response,op){
				var res = Ext.util.JSON.decode(response.responseText);
				if(res.success)
					new ConferenceForm({
						confId : confId
					}).show();
				else
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : res.msg,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
			}
		});
	},
	/**
	 * 管理列中的事件处理
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.delByIds(record.data.confId);
				break;
			case 'btn-showDetail' :
				this.showDetail(record.data.confId);
				break;
			case 'btn-edit' :
				this.editRecord(record);
				break;
		}
	}
});
