/**
 * @author: YHZ
 * @class ZanCunConferenceView
 * @extends Ext.Panel
 * @description 暂存会议查询
 * @company 北京互融时代软件有限公司
 * @createtime:2010-10-11 PM
 */
ZanCunConferenceView = Ext.extend(Ext.Panel, {
	//搜索
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
		ZanCunConferenceView.superclass.constructor.call(this, {
			id : 'ZanCunConferenceView',
			title : '暂存会议查询',
			iconCls : 'menu-conference_zancun',
			region : 'center',
			layout : 'border',
			items : [ this.searchPanel,this.gridPanel ]
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		
		//start of this searchPanel
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			region : 'north',
			width : '100%',
			height : 80,
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			},
			items : [{
				border : false,
				layout : 'column',
				layoutConfig : {
					padding : '5',
					align : 'middle'
				},
				defaults : {
					xtype : 'label'
				},
				items : [{
					columnWidth : .3,
					style : 'margin-top:8px;',
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '会议标题',
						name : 'Q_confTopic_S_LK',
						maxLength : 256
					}, {
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '会议室名称',
						name : 'Q_roomName_S_LK',
						maxLength : 156
					}]
				},{
					columnWidth : .3,
					style : 'margin-top:8px;',
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						xtype : 'combo',
						name : 'Q_confProperty_S_LK',
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
					}, {
						anchor : '99%',
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
					}]
				}, {
					columnWidth : .3,
					style : 'margin-top:8px;',
					xtype : 'container',
					layout : 'form',
					items : [{
						anchor : '99%',
						fieldLabel : '会议内容',
						xtype : 'textfield',
						name : 'Q_confContent_S_LK',
						maxLength : 4000
					}, {
						xtype : 'container',
						layout : 'column',
						fieldLabel : '会议时间',
						items : [{
							columnWidth : .49,
							xtype : 'datefield',
							name : 'Q_startTime_D_GE',
							format : 'Y-m-d'
						}, {
							xtype : 'label',
							style : 'margin-top:3px;',
							text : ' 至 '
						}, {
							columnWidth : .49,
							anchor : '99%',
							xtype : 'datefield',
							name : 'Q_endTime_D_LE',
							format : 'Y-m-d'
						}]
					}]
				}, {
					columnWidth : .1,
					style : 'margin-top:8px;margin-left:5px;',
					layout : 'form',
					xtype : 'container',
					defaultType : 'button',
					items : [{
						iconCls : 'search',
						text : '搜索',
						handler : this.search.createCallback(this)
					}, {
						iconCls : 'reset',
						style : 'margin-top:5px;',
						text : '清空',
						handler : this.reset.createCallback(this)
					}]
				}]
			}]
		}); // end of this searchPanel
		
		// 加载数据至store
		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + "/admin/zanCunConference.do",
			root : 'result',
			totalProperty : 'totalCounts',
			remoteSort : true,
			fields : [ {
				name : 'confId',
				type : 'int'
			}, 'confTopic', 'compereName', 'roomName', 'roomLocation', 'confContent',
					'attendUsersName', 'feeBudget', 'startTime', 'endTime', 'status' ]
		});
		this.store.setDefaultSort('confId', 'desc');
		// 加载数据
		this.store.load( {
			params : {
				start : 0,
				limit : 25
			}
		});

		this.rowActions = new Ext.ux.grid.RowActions( {
			header : '管理',
			width : 80,
			actions : [ {
				iconCls : 'btn-del',
				qtip : '删除',
				style : 'margin:0 3px 0 3px'
			},{
				iconCls : 'btn-edit',
				qtip : '编辑',
				style : 'margin:0 3px 0 3px'
			},{
				iconCls : 'btn-showDetail',
				qtip : '查看',
				style : 'margin:0 3px 0 3px'
			} ]
		});

		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel( {
			columns : [ sm, new Ext.grid.RowNumberer(), {
				header : 'confId',
				dataIndex : 'confId',
				hidden : true
			}, {
				header : '会议标题',
				dataIndex : 'confTopic'
			}, {
				header : '会议内容',
				dataIndex : 'confContent'
			}, {
				header : '会议室名',
				dataIndex : 'roomName'
			}, {
				header : '会议地址',
				dataIndex : 'roomLocation'
			}, {
				header : '参与人员',
				dataIndex : 'attendUsersName'
			}, {
				header : '开始时间',
				dataIndex : 'startTime'
			}, {
				header : '结束时间',
				dataIndex : 'endTime'
			}, this.rowActions ],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});
		// 初始化工具栏
		this.topbar = new Ext.Toolbar( {
			height : 30,
			bodyStyle : 'text-align:left',
			items : [ {
				text : '删除',
				iconCls : 'btn-del',
				xtype : 'button',
				handler : this.delRecords
			}, '-', {
				text : '编辑',
				iconCls : 'btn-edit',
				xtype : 'button',
				handler : this.edit
			} ]
		});

		this.gridPanel = new Ext.grid.GridPanel( {
			id : 'ZanCunConferenceGrid',
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
			bbar : new Ext.PagingToolbar( {
				pageSize : 25,
				store : this.store,
				displayInfo : true,
				displayMsg : '当前页记录索引{0}-{1}， 共{2}条记录',
				emptyMsg : "当前没有记录"
			})
		});

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
				var confId = rec.data.confId;
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
			});
		});
		this.rowActions.on('action', this.onRowAction, this);
	},// end of the initComponents()

	/**
	 * 搜索
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
						Ext.getCmp('ZanCunConferenceGrid').getStore().reload();
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
		var gridPanel = Ext.getCmp('ZanCunConferenceGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for ( var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.confId);
		}
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
						Ext.getCmp('ZanCunConferenceGrid').getStore().reload();
					},
					failure : function(response, options) {
						Ext.Msg.alert('操作信息', '操作出错，请联系管理员！');
					}
				});
			}
		});// end of comfirm
	},
	
	/**
	 * 查看详细信息
	 */
	showDetail : function(confId) {
		ConferenceDetailForm.show(confId);
	},
	
	/**
	 * 编辑
	 */
	edit : function(){
		var gridPanel = Ext.getCmp('ZanCunConferenceGrid');
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
		case 'btn-del':
			this.delByIds(record.data.confId);
			break;
		case 'btn-edit':
			this.editRecord(record);
			break;
		case 'btn-showDetail':
			this.showDetail(record.data.confId);
			break;
		}
	}
});
