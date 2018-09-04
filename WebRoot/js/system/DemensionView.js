/**
 * @author:
 * @class DemensionView
 * @extends Ext.Panel
 * @description [Demension]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
DemensionView = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				DemensionView.superclass.constructor.call(this, {
							id : 'DemensionView',
							iconCls : 'btn-weidu',
							title : '维度管理',
							region : 'center',
							layout : 'border',
							items : [this.searchPanel, this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
				this.searchPanel=new HT.SearchPanel({
					region:'north',
//					height : 35,
					frame : false,
					border:false,
					id : 'AppUserSearchForm',
					layout : 'hbox',
					layoutConfig: {
		                    padding:'5',
		                    align:'middle'
		            },
					defaults : {
						xtype : 'label',
						border:false,
						margins:{top:0, right:4, bottom:4, left:4}
					},
					items:[{
						text : '维度名称'
					},{
						name : 'Q_demName_S_LK',
						xtype : 'textfield'
					},{
						text:'查询',
						scope:this,
						xtype : 'button',
						iconCls:'btn-search',
						handler:this.search
					},{
						text:'重置',
						scope:this,
						xtype : 'button',
						iconCls:'btn-reset',
						handler:this.reset
					}]
				});// end of searchPanel

				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-add',
						text : '添加维度',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					}, {
						iconCls : 'btn-del',
						text : '删除维度',
						xtype : 'button',
						scope:this,
						handler : this.removeSelRs
					}]
				});
	
				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					//使用RowActions
					rowActions:true,
					id:'DemensionGrid',
					url : __ctxPath + "/system/listDemension.do",
					fields : [{
								name : 'demId',
								type : 'int'
								},'demName','demDesc','demType'],
					columns:[{
								header : 'demId',
								dataIndex : 'demId',
								hidden : true
							},{
								header : '维度名称',	
								dataIndex : 'demName'
							},{
								header : '维度描述',	
								dataIndex : 'demDesc'
							},{
								header : 'demType',	
								dataIndex : 'demType',
								hidden : true
							},
							{
								header : '管理',
								dataIndex : 'demId',
								width : 60,
								renderer : function(value, metadata, record, rowIndex,colIndex) {
									var editId = record.data.demId;
									var demType = record.data.demType;
									var str='';
									if (demType==2) {
										str += '<button title="删除" value=" " class="btn-del" onclick="DemensionView.remove('
												+ editId + ')"></button>';
									}
									
									str += '&nbsp;<button title="编辑" value=" " class="btn-edit" onclick="DemensionView.edit('
											+ editId + ')"></button>';
									
									return str;
								}
							},
						new Ext.ux.grid.RowActions({
							header:'管理',
							width:100,
							actions:[
								{
									iconCls:'btn-del',qtip:'删除',style:'margin:0 3px 0 3px'
								},{
									iconCls:'btn-edit',qtip:'编辑',style:'margin:0 3px 0 3px'
								}
							],
							listeners:{
								scope:this,
								'action':this.onRowAction
							},
							hidden:true
						})
					]//end of columns
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new DemensionForm({demId:rec.data.demId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new DemensionForm().show();
			},
			//把选中ID删除
			removeSelRs : function() {
//				$delGridRs({
//					url:__ctxPath + '/system/multiDelDemension.do',
//					grid:this.gridPanel,
//					idName:'demId'
//				});
				
				var grid = Ext.getCmp("DemensionGrid");

				var selectRecords = grid.getSelectionModel().getSelections();

				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				var idsN = '';
				for (var i = 0; i < selectRecords.length; i++) {
					if (selectRecords[i].data.demType != 1) {
						ids.push(selectRecords[i].data.demId);
					} else {
						idsN += selectRecords[i].data.demName + ',';
					}
				}
				if (idsN == '') {
					DemensionView.remove(ids);
				} else {
					Ext.ux.Toast.msg("信息", idsN + "不能被删除！");
				}
				
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/system/multiDelDemension.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//编辑Rs
			editRs : function(record) {
				new DemensionForm({
					demId : record.data.demId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.demId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});

DemensionView.edit = function(editId) {
	new DemensionForm({
		demId: editId
	}).show();
};

DemensionView.remove = function(editId) {
	Ext.Msg.confirm('删除操作', '你确定要删除该用户吗?', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : __ctxPath + '/system/multiDelDemension.do',
				method : 'post',
				params : {
					ids : editId
				},
				success : function(response) {
					var result = Ext.util.JSON.decode(response.responseText);
					if(result.msg == null){
						Ext.ux.Toast.msg("操作信息", "维度删除成功");
					}
					Ext.getCmp('DemensionGrid').getStore().reload();
				},
				failure : function() {
					Ext.ux.Toast.msg("操作信息", "维度删除失败");
				}
			});
		}
	});
}
