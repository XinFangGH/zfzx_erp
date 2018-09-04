

KnowledgePrivilegeWin=Ext.extend(Ext.Window,{

	  gridPanel:null,
      constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUI();
          KnowledgePrivilegeWin.superclass.constructor.call(this,{
                title :this.folderName+'-授权列表',
				iconCls:'menu-folder-shared',
				width : 710,
				height:500,
				modal : true,
				maximizable:true,
				layout : 'fit',
		        items:[this.gridPanel],
		        buttonAlign:'center',
		        buttons:this.buttons
            
          });
      },
      initUI:function(){
          
      	    var sm = new Ext.grid.CheckboxSelectionModel();

			var onMouseDown = function(e, t) {
				if (t.className && t.className.indexOf('x-grid3-cc-' + this.id) != -1) {
					e.stopEvent();
					var index = this.grid.getView().findRowIndex(t);
					var cindex = this.grid.getView().findCellIndex(t);
					var record = this.grid.store.getAt(index);
					var field = this.grid.colModel.getDataIndex(cindex);
					
					if (field != 'rightR') {
	
						var e = {
							grid : this.grid,
							record : record,
							field : field,
							originalValue : record.data[this.dataIndex],
							value : !record.data[this.dataIndex],
							row : index,
							column : cindex,
							cancel : false
						};
						if (this.grid.fireEvent("validateedit", e) !== false
								&& !e.cancel) {
							delete e.cancel;
							record
									.set(this.dataIndex,
											!record.data[this.dataIndex]);
							this.grid.fireEvent("afteredit", e);
						}
	
					} else {
						Ext.ux.Toast.msg("信息提示", "可读为基本权限！");
					}
					
				}
		
			}
		
			var checkColumnR = new Ext.grid.CheckColumn({
						id : 'read',
						header : '可读',
						dataIndex : 'rightR',
						width : 55,
						onMouseDown : onMouseDown
					});
			var checkColumnM = new Ext.grid.CheckColumn({
						header : '可修改',
						dataIndex : 'rightU',
						width : 55,
						onMouseDown : onMouseDown
					});
			var checkColumnD = new Ext.grid.CheckColumn({
						header : '可删除',
						dataIndex : 'rightD',
						width : 55,
						onMouseDown : onMouseDown
					});
		
			this.rowActions = new Ext.ux.grid.RowActions({
				header : '管理',
				width : 120,
				actions : [{
						    iconCls:'btn-del',
						    qtip:'删除',
							style : 'margin:0 3px 0 3px'
						}]
			});
			var cm = new Ext.grid.ColumnModel({
				columns : [sm, new Ext.grid.RowNumberer(), {
							header : 'privilegeId',
							dataIndex : 'privilegeId',
							hidden : true
						}, {
							header : '名称',
							dataIndex : 'udrName'
						}, {
							header : '属性',
							dataIndex : 'flag',
							renderer : function(value, metadata, record) {
								if (value == 1) {
									return '<img title="员工" src="' + __ctxPath
											+ '/images/flag/user.jpg"/>';
								}
								if (value == 2) {
									return '<img title="部门" src="' + __ctxPath
											+ '/images/flag/department.jpg"/>';
								}
								if (value == 3) {
									return '<img title="角色" src="' + __ctxPath
											+ '/images/flag/role.jpg"/>';
								}
							}
						}, checkColumnR, checkColumnM, checkColumnD,this.rowActions],
				defaults : {
					sortable : true,
					menuDisabled : false,
					width : 100
				}
			});
		
			this.store=new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
							url : __ctxPath + '/document/listDocPrivilege.do'
						}),
				baseParams:{
				    'Q_docFolder.folderId_L_EQ' : this.folderId
				},
				reader : new Ext.data.JsonReader({
							root : 'result',
							totalProperty : 'totalCounts',
							id : 'id',
							fields : [{
										name : 'privilegeId',
										type : 'int'
									},{
										name : 'folderName',
										mapping : 'folderName'
									}
									// ,'docId'
									, 'rightR', 'rightU', 'rightD', 'udrId',
									'udrName', 'flag']
						}),
				remoteSort : true
			});
	        this.store.setDefaultSort('privilegeId', 'desc');
			
			
			this.store.load({
						params : {
							start : 0,
							limit : 25
						}
					});
					
			this.toolbar = new Ext.Toolbar({
				height : 30,
				bodyStyle : 'text-align:left',
				items : []
			});
		
			this.toolbar.add(new Ext.Button({
				iconCls : 'btn-add',
				text : '添加文件夹权限',
				scope:this,
				handler : function() {
					var forlderId = this.folderId;
					if (forlderId != null && forlderId > 0) {
						new DocFolderSharedForm({folderId:this.folderId,folderName:this.folderName,grid:this.gridPanel}).show();
					} else {
						Ext.ux.Toast.msg('提示', '请选择文件夹!');
					}
				}
			}));
			
			
			this.toolbar.add(new Ext.Button({
					iconCls : 'btn-del',
					text : '删除文件夹权限',
					scope:this,
					handler :this.delRecords
			}));
			
				
		
			this.gridPanel= new Ext.grid.EditorGridPanel({
						trackMouseOver : true,
						tbar:this.toolbar,
						store : this.store,
						trackMouseOver : true,
						disableSelection : false,
						loadMask : true,
						cm : cm,
						sm : sm,
						plugins : [checkColumnR, checkColumnM, checkColumnD,this.rowActions],
						clicksToEdit : 1,
						viewConfig : {
							forceFit : true,
							enableRowBody : false,
							showPreview : false
						},
						bbar : new HT.PagingBar({store : this.store})
					});
		    this.rowActions.on('action', this.onRowAction, this);
			this.gridPanel.addListener('afteredit', function(e) {
						Ext.Ajax.request({
									url : __ctxPath + '/document/changeDocPrivilege.do',
									params : {
										field : e.field,
										fieldValue : e.value,
										privilegeId : e.record.data.privilegeId
									},
									success : function() {
		
									},
									failure : function() {
										Ext.Msg.show({
													title : '错误提示',
													msg : '修改数据发生错误,操作将被回滚!',
													fn : function() {
														e.record.set(e.field,
																e.originalValue);
													},
													buttons : Ext.Msg.OK,
													icon : Ext.Msg.ERROR
												});
		
									}
		
								});
		
					});
					
		this.buttons=[{
		    xtype:'button',
		    text:'关闭',
		    scope:this,
		    iconCls:'close',
		    handler:function(){
		        this.close();
		    }
		}];
      },
      	/**
	 * 按IDS删除记录
	 * @param {} ids
	 */
	delByIds : function(ids,gridPanel) {
		Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath
										+ '/document/multiDelDocPrivilege.do',
									params : {
										ids : ids
									},
									method : 'POST',
									success : function(response, options) {
										Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
										gridPanel.getStore().reload();
									},
									failure : function(response, options) {
										Ext.ux.Toast
												.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
					}
				});//end of comfirm
	},
	/**
	 * 删除多条记录
	 */
	delRecords : function() {
		var gridPanel = this.gridPanel;
		var selectRecords = gridPanel.getSelectionModel().getSelections();
		if (selectRecords.length == 0) {
			Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
			return;
		}
		var ids = Array();
		for (var i = 0; i < selectRecords.length; i++) {
			ids.push(selectRecords[i].data.privilegeId);
		}
		this.delByIds(ids,gridPanel);
	},
      /**
	 * 管理列中的事件处理
	 * @param {} grid
	 * @param {} record
	 * @param {} action
	 * @param {} row
	 * @param {} col
	 */
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.delByIds(record.data.privilegeId,gridPanel);
				break;
			default :
				break;
		}
	}
});