/**
 * 词条管理
 * @class SystemWordView
 * @extends Ext.Panel
 */
SystemWordView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		SystemWordView.superclass.constructor.call(this,{
			id:"SystemWordView"+this.wordKey,
			autoScroll:true,
			layout : 'fit',
			title:'词条管理',
			iconCls:'menu-globalType',
			items:[
//				this.searchPanel,
				this.gridPanel
			]
		});
	},
	initUIComponents:function(){
		 this.searchPanel =new Ext.FormPanel({
			layout : 'form',
			border : false,
			region : 'north',
			height : 65,
			anchor : '70%',
			url : __ctxPath + '/system/subSystemWord.do?wordKey='+this.wordKey,
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			}],
			items : [
				{
				columnWidth : .10,
				labelAlign : 'right',
				xtype : 'container',
				layout : 'form',
				labelWidth : 60,
				defaults : {
					anchor : '30%'
				},
				items : [{
						fieldLabel:'词条名称',
						id : 'wordName',
						name : 'systemWord.wordName',
						xtype : 'textfield'
						
					}]
			}, {
				columnWidth : .12,
				labelAlign : 'right',
				xtype : 'container',
				layout : 'form',
				labelWidth : 60,
				defaults : {
					xtype : 'button'
				},
				//style : 'padding-left:60px;',
				items : [{
							text:'查询',
							fieldLabel : ' ',
							labelSeparator:"",
							scope:this,
							iconCls:'btn-search',
							handler:this.search
						}]
			}]
		 });
		this.store = new Ext.data.JsonStore({
					url : __ctxPath + "/system/subSystemWord.do",
					baseParams : {wordKey : this.wordKey},
					root : 'result',
					remoteSort : true,
					fields : [{
								name : 'wordId',
								type : 'int'
							}, 'wordName', 'parentId', 'wordKey',  'wordDescription']
				});
		//this.store.setDefaultSort('proTypeId', 'desc');
		
		this.store.load();
//		this.rowActions = new Ext.ux.grid.RowActions({
//			header : '管理',
//			width : 80,
//			actions : [{
//						iconCls : 'btn-last',
//						qtip : '向下',
//						style : 'margin:0 3px 0 3px'
//					}, {
//						iconCls : 'btn-up',
//						qtip : '向上',
//						style : 'margin:0 3px 0 3px'
//					}]
//		});
//		this.rowActions.on('action', this.onRowAction, this);
		
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cm = new Ext.grid.ColumnModel({
					columns : [sm, new Ext.grid.RowNumberer(), {
								header : 'wordId',
								dataIndex : 'wordId',
								hidden : true
							}, {
								header : '名称',
								dataIndex : 'wordName'
							}, {
								header : '词条Key',
								dataIndex : 'wordKey'
							},{
								header : '词条描述',
								width : 730,
								dataIndex : 'wordDescription'
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					}
		});
	
		var tbar=new Ext.Toolbar({
			items:[
			{
				text:'添加',
				iconCls:'btn-add',
				scope : this,
				hidden : isGranted('_create_ctgl')?false:true,
				handler:function(){
					var systemWordForm=new SystemWordForm({
                 		isReadOnly : false,
                 		wordKey : this.wordKey,
                 		callback : function(){
                 			Ext.getCmp('systemWordGrid').getStore().reload();
                 		}
                 	});
                 	systemWordForm.show();
				}
			},'-',{
				xtype : 'button',
				text : '编辑',
				iconCls : 'btn-save',
				scope : this,
				hidden : isGranted('_edit_ctgl')?false:true,
				handler : function() {
					this.updateWord(this.gridPanel);
				}
			},'-',{
				text:'删除',
				iconCls:'btn-del',
				scope:this,
				hidden : isGranted('_remove_ctgl')?false:true,
				handler:function(){
					var gridPanel = this.gridPanel;
					var selectRecords = gridPanel.getSelectionModel().getSelections();
					if (selectRecords.length == 0) {
						Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
						return;
					}
					var ids = Array();
					for (var i = 0; i < selectRecords.length; i++) {
						ids.push(selectRecords[i].data.wordId);
					}
					
					Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
										url : __ctxPath + '/system/multiDelSystemWord.do',
										params : {
											ids : ids
										},
										method : 'POST',
										success : function(response, options) {
											Ext.ux.Toast.msg('操作信息','删除成功！');
											gridPanel.getStore().reload();
										},
										failure : function(response, options) {
											Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
										}
									});
						}
					});
				
				}
			}
			]
		});
		
		this.gridPanel= new Ext.grid.EditorGridPanel({
			region : 'center',
			tbar:tbar,
			clicksToEdit: 1,
			id:'systemWordGrid',
	        store: this.store,
//	        plugins : this.rowActions,
	        sm:sm,
	        cm: cm,
	        height:450,
	        listeners : {
				scope : this,
				rowdblclick : function(grid, rowIndex, e) {
					this.updateWord(grid);
				}
			}
		});
		
	},//end of initUIComponents
	updateWord : function(grid) {
		var selRs = grid.getSelectionModel().getSelections();
		if(selRs.length==0){
		   Ext.ux.Toast.msg('操作信息','请选择一条记录！');
		   return;
		}
		if(selRs.length>1){
		   Ext.ux.Toast.msg('操作信息','只能选择一条记录！');
		   return;
		}
		var wordId = grid.getSelectionModel().getSelected().get('wordId');
		var systemWordForm=new SystemWordForm({
     		isReadOnly : true,
     		wordId : wordId,
     		callback : function(){
     			Ext.getCmp('systemWordGrid').getStore().reload();
     		}
     	});
     	systemWordForm.show();
	},
	
	search : function() {
		this.searchPanel.getForm().submit({
			method : 'POST',
			waitMsg : '正在提交数据...',
			scope : this,
			success : function(fp, action) {
			},
			failure : function(fp, action) {
				Ext.MessageBox.show({
					title : '操作信息',
					msg : '查询数据出错，请联系管理员！',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}
//	onRowAction : function(gridPanel, record, action, row, col) {
//		var grid=Ext.getCmp('globalTypeCenGrid');
//		var store=grid.getStore();
//		switch (action) {
//			case 'btn-up' :
//				if(row==0){
//					Ext.ux.Toast.msg('操作信息','已经为第一条!');
//					return ;
//				}
//				
//				var rd1=store.getAt(row-1);
//				var rd2=store.getAt(row);
//				store.removeAt(row);
//				store.removeAt(row-1);
//				store.insert(row-1,rd2);
//				store.insert(row,rd1);
//				break;
//			case 'btn-last' :
//				if(row==store.getCount()-1){
//					Ext.ux.Toast.msg('操作信息','已经为最后一条!');
//					return ;
//				}
//				var rd1=store.getAt(row);
//				var rd2=store.getAt(row+1);
//				
//				store.removeAt(row+1);
//				store.removeAt(row);
//			
//				store.insert(row,rd2);
//				store.insert(row+1,rd1);
//
//				break;
//			default :
//				break;
//		}
//	}
});