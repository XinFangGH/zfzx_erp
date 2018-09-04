Ext.ns('MyFileAttachView');
/**
 * @description MyFileAttachView我的附件信息
 * @extend Panel
 * @author YHZ
 * @createtime 2011-3-24AM
 * 附件信息列表
 */
MyFileAttachView = Ext.extend(Ext.Panel, {
	
	treePanel : null, // 树
	searchPanel : null, // 搜索
	gridPanel : null, // gridPanel
	
	constructor : function(_cfg){
		Ext.applyIf(this, _cfg);
		this.initUIComponent();
		MyFileAttachView.superclass.constructor.call(this, {
			id : '1MyFileAttachView',
			title : '我的附件',
			iconCls : 'menu-attachment',
			layout : 'border',
			region : 'center',
			autoScroll : true,
		    items : [this.searchPanel, this.treePanel, this.gridPanel]
		});
	},
	
	initUIComponent : function(){
		
		////////////////////////////////
		this.treePanel = new Ext.tree.TreePanel({
			layout : 'form',
			region : 'west',
			id : 'myFileAttachViewTreePanel',
			title : '附件分类',
			collapsible : true,
			autoScroll : true,
			split : true,
			width : 180,
			tbar : new Ext.Toolbar({
				items : [{
					xtype : 'button',
					iconCls : 'btn-refresh',
					text : '刷新',
					handler : function() {
						Ext.getCmp('myFileAttachViewTreePanel').root.reload();
					}
				}, '-', {
					xtype : 'button',
					text : '展开',
					iconCls : 'btn-expand',
					handler : function() {
						Ext.getCmp('myFileAttachViewTreePanel').expandAll();
					}
				}, '-', {
					xtype : 'button',
					text : '收起',
					iconCls : 'btn-collapse1',
					handler : function() {
						Ext.getCmp('myFileAttachViewTreePanel').collapseAll();
					}
				}]
			}),
			loader : new Ext.tree.TreeLoader({
				 url : __ctxPath + '/system/treeGlobalType.do?catKey=ATTACHFILE_TYPE'
			}),
			root : new Ext.tree.AsyncTreeNode({
				expanded : true
			}),
			rootVisible : false,
			listeners : {
				'click' : this.nodeClick
			}
		}); // end of this treePanel
		///////////////////////////////
		
		var toolbar = new Ext.Toolbar({
			id : 'myFileAttachViewToolbar',
			height : 30,
			bodyStyle : 'text-align:left',
			defaultType : 'button',
			items : [{
				text : '删除',
				iconCls : 'btn-del',
				handler : this.removeAll,
				scope : this
			}]
		}); // end of toolbar
		
		this.searchPanel = new Ext.FormPanel({
			region : 'north',
			height : 35,
			frame : false,
			border : false,
			id : 'MyFileAttachSearchForm',
			layout : 'hbox',
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			layoutConfig: {
		        padding:'5',
		        align:'middle'
		    },
			defaults : {
				xtype : 'label',
				margins : {top : 0, right : 4, bottom : 4, left : 4}
			},
			items : [{
				text : '请输入查询条件:'
			}, {
				text : '文件名'
			}, {
				xtype : 'textfield',
				width : 80,
				name : 'Q_fileName_S_LK'
			}, {
				text : '创建时间'
			}, {
				xtype : 'datefield',
				format : 'Y-m-d H:i:s',
				width : 80,
				name : 'Q_createtime_D_GE'
			}, {
				text : '扩展名'
			}, {
				xtype : 'textfield',
				width : 60,
				name : 'Q_ext_S_LK'
			}, {
				text : '上传者'
			}, {
				xtype : 'textfield',
				width : 80,
				name : 'Q_creator_S_LK'
			}, {
				xtype : 'button',
				text : '查询',
				iconCls : 'search',
				handler : this.search
			}, {
				xtype : 'button',
				text : '重置',
				iconCls : 'reset',
				handler : this.reset
			}]
		}); // end of this searchPanel
		var store = new Ext.data.Store({
			proxy : new Ext.data.HttpProxy({
				url : __ctxPath + '/system/listAllFileAttach.do'
			}),
			baseParams: {
				'Q_delFlag_N_EQ' : 0 ,//查找未删除的
				'Q_creatorId_L_EQ' : curUserInfo.userId
			},
			reader : new Ext.data.JsonReader({
				root : 'result',
				totalProperty : 'totalCounts',
				id : 'id',
				fields : [{
					name : 'fileId',
					type : 'int'
				} , 'fileName', 'filePath', 'createtime',
				'ext', 'type', 'note', 'creator','fileType','fileTypeName']
			}),
			remoteSort : true
		});
		store.setDefaultSort('fileId', 'desc');

		var rowActions = new Ext.ux.grid.RowActions({
			header : '管理',
			width : 80,
			actions : [ {
				iconCls : 'btn-del',
				qtip : '删除',
				style : 'margin:0 3px 0 3px'
			}, {
				iconCls : 'btn-detail',
				qtip : '查看',
				style : 'margin:0 3px 0 3px'
			}]
		});
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
				header : 'fileId',
				dataIndex : 'fileId',
				hidden : true
			}, {
				header : '文件名',
				dataIndex : 'fileName'
			}, {
				header : '文件路径',
				dataIndex : 'filePath'
			}, {
				header : '创建时间',
				dataIndex : 'createtime'
			}, {
				header : '扩展名',
				dataIndex : 'ext'
			}, {
				header : '附件类型',
				dataIndex : 'fileType'
			}, {
				header : '类型名称',
				sortable : false,
				dataIndex : 'fileTypeName'
			}, {
				header : '说明',
				dataIndex : 'note'
			}, {
				header : '上传者',
				dataIndex : 'creator'
			}, rowActions],
			defaults : {
				sortable : true,
				menuDisabled : true,
				width : 100
			}
		});
		store.load({
			params : {
				start : 0,
				limit : 25
			}
		});

		this.gridPanel = new Ext.grid.GridPanel({
			id : 'MyFileAttachGrid',
//			layout : 'border',
			region : 'center',
			tbar : toolbar,
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			cm : cm,
			sm : sm,
			plugins : rowActions,
			viewConfig : {
				forceFit : true,
				enableRowBody : false,
				showPreview : false
			},
			bbar : new HT.PagingBar({store : store})
		}); // end of grid

		this.gridPanel.addListener('rowdblclick', function(grid, rowindex, e) {
			grid.getSelectionModel().each(function(rec) {
				if (isGranted('_FileAttachEdit')) {
					FileAttachDetail.show(rec.data.fileId);
				}
			});
		});
		rowActions.on('action', this.onRowAction, this);
		
	}, // end of initUIComponent
	
	/**
	 * 查看
	 */
	detail : function(id) {
		if(id != null && id != '' && id != 'undefined')
			FileAttachDetail.show(id);
	},

	/**
	 * 搜索
	 */
	search : function() {
		var searchPanel = Ext.getCmp('MyFileAttachSearchForm');
		var gridPanel = Ext.getCmp('MyFileAttachGrid');
		if (searchPanel.getForm().isValid()) {
			$search({
				searchPanel :searchPanel,
				gridPanel : gridPanel
			});
		}
	},

	/**
	 * 清空
	 */
	reset : function(){
		var searchPanel = Ext.getCmp('MyFileAttachSearchForm');
		searchPanel.getForm().reset();
	},
	
	/**
	 * 删除多条记录操作
	 */
	removeAll : function(){
		var grid = Ext.getCmp("MyFileAttachGrid");
		var selectRecords = grid.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg('操作提示', '请选择要删除的记录！');
			return;
		}
		var ids = '';
		for (var i = 0; i < selectRecords.length; i++) {
			ids += selectRecords[i].data.fileId + ',';
		}
		if(ids != ''){
			ids = ids.substring(0, ids.length);
		}
		this.remove(ids);
	},
	/**
	 * 删除单个记录
	 */
	remove : function(id) {
		var grid = Ext.getCmp("MyFileAttachGrid");
		Ext.Msg.confirm('信息确认', '您确认要删除该记录吗？', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/system/multiDelFileAttach.do',
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
	},
	
	/**
	 * 节点单击事件
	 * @param {} node
	 */
	nodeClick : function(node){
		if (node != null) {
			var file_type = '';
			if(node.getDepth() > 1 && node.attributes.nodeKey != 'file-type'){
				file_type = node.attributes.nodeKey;
			}
			var store = Ext.getCmp('MyFileAttachGrid').getStore();
			store.url = __ctxPath + '/system/listFileAttach.do';
			store.reload({
				params : {
					start : 0,
					limit : 25,
					'Q_fileType_S_LK' : file_type,
					'Q_delFlag_N_EQ' : 0 //查找未删除的
				}
			});
		}
	},
	
	/**
	 * 管理列中的事件处理
	 */
	onRowAction:function(gridPanel, record, action, row, col) {
		switch(action) {
			case 'btn-detail':
				this.detail(record.data.fileId);
				break;
			case 'btn-del':
				this.remove(record.data.fileId);
				break;
			default:
				break;
		}
	}
});
