/**
 * @author: YHZ
 * @class ConfSummaryView
 * @extends Ext.Panel
 * @description 会议纪要管理
 * @company 北京互融时代软件有限公司
 * @createtime:2010-10-11
 */
ConfSummaryView = Ext.extend(Ext.Panel,{
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
		ConfSummaryView.superclass.constructor.call(this, {
			id : 'ConfSummaryView',
			title : '会议纪要查询',
			iconCls : 'menu-conf-summary',
			region : 'center',
			bodyStyle : 'padding : 5px 5px 5px 5px',
			layout : 'border',
			items : [ this.searchPanel, this.gridPanel ],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search.createCallback(this),
				scope : this
			}
		});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		// start of this searchPanel
		this.searchPanel = new Ext.FormPanel({
		    id : 'ConfSummary.searchPanel',
			layout : 'form',
			region : 'north',
			width :'100%',
			height : 80,
			items : [{
				xtype : 'container',
				layout : 'column',
				region : 'center',
				border : false,
				layoutConfig : {
					padding : '5px',
					align : 'left'
				},
				items : [{
					columnWidth : .45,
					style : 'margin-top:8px;',
					layout : 'form',
					border : false,
					items : [{
						anchor : '99%',
						fieldLabel : '会议标题',
						name : 'Q_confId.confTopic_S_LK',
						xtype : 'textfield',
						maxLength : 156
					}, {
						anchor : '99%',
						xtype : 'combo',
						fieldLabel : '状态',
						hiddenName : 'Q_status_SN_EQ',
						store : [ [ '0', '待发送' ], [ '1', '发送' ] ],
						editable : false,
						triggerAction : 'all',
						mode : 'local'
					}]
				}, {
					columnWidth : .45,
					style : 'margin-top:8px;',
					layout : 'form',
					border : false,
					items : [{
						anchor : '99%',
						fieldLabel : '纪要内容',
						xtype : 'textfield',
						name : 'Q_sumContent_S_LK',
						maxlength : 4000
					}, {
						fieldLabel : '纪要时间',
						layout : 'column',
						xtype : 'container',
						defaultType : 'datetimefield',
						items : [{
							columnWidth : .49,
							anchor : '99%',
							name : 'Q_createtime_D_GE',
							format : 'Y-m-d'
						},{
							style : 'margin:3px 5px 3px 5px;',
							xtype : 'label',
							text : '至'
						},{
							columnWidth : .49,
							anchor : '99%',
							name : 'Q_createtime_D_LE',
							format : 'Y-m-d'
						}]
					}]
				}, {
					columnWidth : .1,
					style : 'margin-top:8px;',
					layout : 'form',
					defaultType : 'button',
					border : false,
					items : [{
						text : '查询',
						iconCls : 'search',
						handler : this.search.createCallback(this)
					},{
						text : '清空',
						style : 'margin-top:5px;',
						iconCls : 'reset',
						handler : function() {
							Ext.getCmp('ConfSummary.searchPanel').getForm().reset();
						}
					}]
				}]
			}]
		}); // end of this searchPanel
		

		// 加载数据至store
		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + "/admin/listConfSummary.do",
			root : 'result',
			totalProperty : 'totalCounts',
			remoteSort : true,
			fields : [ {
				name : 'sumId',
				type : 'int'
			}, 'confId', 'createtime', 'creator', 'sumContent',
					'status' ]
		});
		this.store.setDefaultSort('sumId', 'desc');
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
		var cm = new Ext.grid.ColumnModel( {
			columns : [
					sm,
					new Ext.grid.RowNumberer(),
					{
						header : 'sumId',
						dataIndex : 'sumId',
						hidden : true
					},
					{
						header : '会议标题',
						dataIndex : 'confId',
						renderer : function(value) {
							return value == null ? '为空' : value.confTopic;
						}
					}, {
						header : '创建人',
						dataIndex : 'creator'
					}, 
					{
						header : '创建日期',
						dataIndex : 'createtime'
					}, {
						header : '纪要内容',
						dataIndex : 'sumContent',
						renderer : function(value){
							 return value.substring(0,20);
						}
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
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				handler : this.delRecords,
				scope : this
			}, '-', {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				handler : this.edit,
				scope : this
			} ]
		});

		this.gridPanel = new Ext.grid.GridPanel( {
			id : 'ConfSummaryGrid',
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
				if(rec.data.status==1){
					Ext.MessageBox.show({
						title : '操作提示',
						msg : '对不起，该数据已经发送，不可以编辑，请谅解！',
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					return ;
				}
				new ConfSummaryForm( {
					sumId : rec.data.sumId
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
			self.searchPanel.getForm().submit({
				waitMsg : '正在提交查询',
				url : __ctxPath + '/admin/listConfSummary.do',
				success : function(formPanel, action) {
					var result = Ext.util.JSON.decode(action.response.responseText);
					self.gridPanel.getStore().loadData(result);
				}
			});
		}
	},

	/**
	 * 添加记录
	 */
	createRecord : function() {
		new ConfSummaryForm().show();
	},
	/**
	 * 按IDS删除记录
	 * 
	 * @param {}
	 *            ids
	 */
	delByIds : function(ids) {
		Ext.Msg.confirm('信息确认','您确认要删除所选记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request( {
					url : __ctxPath + '/admin/multiDelConfSummary.do',
					params : {
						ids : ids
					},
					method : 'POST',
					success : function(response,options) {
						Ext.ux.Toast.msg('操作信息','成功删除该会议纪要信息 ！');
						Ext.getCmp('ConfSummaryGrid').getStore().reload();
					},
					failure : function(response,options) {
						Ext.ux.Toast.msg('操作信息','操作出错，请联系管理员！');
					}
			});
		}
	});// end of comfirm
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = Ext.getCmp('ConfSummaryGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for ( var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.sumId);
		}
		this.delByIds(ids);
	},
	
	/**
	 * 编辑
	 */
	edit : function(){
		var gridPanel = Ext.getCmp('ConfSummaryGrid');
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要编辑的记录！");
			return;
		}
		if(selectRecords[0].data.status==1){
			Ext.MessageBox.show( {
				title : '操作信息',
				msg : '对不起，该数据已经发送，不可以编辑，请原谅！',
				buttons : Ext.MessageBox.OK,
				icon : 'ext-mb-error'
			});
			return ;
		}
		new ConfSummaryForm({
			sumId : selectRecords[0].data.sumId
		}).show();
	},
	
	/**
	 * 编辑记录,查看
	 * 
	 * @param {}
	 *            record
	 */
	editRecord : function(record) {
		if(record.data.status==1){
			Ext.MessageBox.show({
				title : '操作提示',
				msg : '对不起，该数据已经发送，不可以编辑，请谅解！',
				buttons : Ext.MessageBox.OK,
				icon : 'ext-mb-error'
			});
			return ;
		}
		new ConfSummaryForm( {
			sumId : record.data.sumId
		}).show();
	},
	/**
	 * 展示详细信息
	 */
	showDetail : function(sumId) {
		ConfSummaryDetailForm.show(sumId);
	},
	/**
	 * 管理列中的事件处理
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.delByIds(record.data.sumId);
			break;
		case 'btn-showDetail':
			this.showDetail(record.data.sumId);
			break;
		case 'btn-edit':
			this.editRecord(record);
			break;
		default:
			break;
		}
	}
});
