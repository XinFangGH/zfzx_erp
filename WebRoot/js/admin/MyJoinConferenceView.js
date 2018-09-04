/**
 * @author: YHZ
 * @class MyJoinConferenceView
 * @extends Ext.Panel
 * @description 待我参加会议查询
 * @company 北京互融时代软件有限公司
 * @createtime:2010-10-11 PM
 */
MyJoinConferenceView = Ext.extend(Ext.Panel, {
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
		MyJoinConferenceView.superclass.constructor.call(this, {
			id : 'MyJoinConferenceView',
			iconCls : 'menu-conference_myjoin',
			title : '待我参加会议查询',
			region : 'center',
			layout : 'border',
			items : [this.searchPanel, this.gridPanel ]
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
//						name : 'Q_confTopic_S_LK',
						name : 'conference.confTopic',
						maxLength : 256
					}, {
						anchor : '99%',
						xtype : 'textfield',
						fieldLabel : '会议室名称',
//						name : 'Q_roomName_S_LK',
						name : 'conference.roomName',
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
//						name : 'Q_confProperty_S_LK',
						name : 'conference.confProperty',
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
//						hiddenName : 'Q_roomId_L_EQ',
						hiddenName : 'conference.roomId',
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
//						name : 'Q_confContent_S_LK',
						name : 'conference.confContent',
						maxLength : 4000
					}, {
						xtype : 'container',
						layout : 'column',
						fieldLabel : '会议时间',
						items : [{
							columnWidth : .49,
							xtype : 'datefield',
//							name : 'Q_startTime_D_GE',
							name : 'conference.startTime',
							format : 'Y-m-d'
						}, {
							xtype : 'label',
							style : 'margin-top:3px;',
							text : ' 至 '
						}, {
							columnWidth : .49,
							anchor : '99%',
							xtype : 'datefield',
//							name : 'Q_endTime_D_LE',
							name : 'conference.endTime',
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
			url : __ctxPath + "/admin/myJoinConference.do",
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
			},{
				header : '开始时间',
				dataIndex : 'startTime'
			}, {
				header : '结束时间',
				dataIndex : 'endTime'
			},  {
				header : '会议内容',
				dataIndex : 'confContent'
			},this.rowActions ],
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
			items : []
		});

		this.gridPanel = new Ext.grid.GridPanel( {
			id : 'MyJoinConferenceGrid',
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
				ConferenceDetailForm.show(rec.data.confId);
			});
		});
		this.rowActions.on('action', this.onRowAction, this);
	},// end of the initComponents()

	/**
	 * 搜索
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
	 * 查看详细信息
	 * 
	 * @param {}
	 *            confId
	 */
	showDetail : function(confId) {
		ConferenceDetailForm.show(confId);
	},

	/**
	 * 管理列中的事件处理
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
		case 'btn-showDetail':
			this.showDetail(record.data.confId);
			break;
		}
	}
});
