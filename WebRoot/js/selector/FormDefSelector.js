var FormDefSelector = {
	getView : function(callback,defId){
		
		////////////////////searchPanel[搜索panel]////////////////
		// 加载数据至store
		var store = new Ext.data.JsonStore({
			url : __ctxPath + "/flow/listFormDef.do?Q_status_SN_EQ=1",
			root : 'result',
			totalProperty : 'totalCounts',
			remoteSort : true,
			fields : [{
				name : 'formDefId',
				type : 'int'
			}, 'formTitle', 'formDesp', 'status']
		});
		store.setDefaultSort('formDefId', 'desc');
		// 加载数据
		store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
		
		var rowActions = new Ext.ux.grid.RowActions({
			header : '管理',
			width : 80,
			actions : [{
				iconCls : 'btn-showDetail',
				qtip : '查看',
				style : 'margin:0 3px 0 3px'
			}]
		});
		
		// 初始化ColumnModel
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect : true});
		var cm = new Ext.grid.ColumnModel({
			columns : [sm, new Ext.grid.RowNumberer(), {
					header : 'formDefId',
					dataIndex : 'formDefId',
					hidden : true
				}, {
					header : '表单标题',
					dataIndex : 'formTitle'
				}, {
					header : '描述',
					dataIndex : 'formDesp',
					sortable:false
				}, rowActions],
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			}
		});
		var searchPanel = new Ext.FormPanel({
			id : 'FormDefSelectorSearchPanel',
			layout : 'hbox',
			region : 'north',
			anchor : '98%',
			url : __ctxPath+'/flow/formDef.do',
			height : 40,
			frame : false,
			layoutConfig : {
				padding : '5px',
				align : 'middle'
			},
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			},
			defaults : {
				xtype : 'label',
				margins : {
					top : 4,
					bottom : 4,
					left : 10,
					right : 10
				}
			},
			items : [{
				text : '表单名称：'
			},{
				anchor : '30%',
				xtype : 'textfield',
				name : 'Q_formTitle_S_LK',
				maxLength : 128
			},{
				xtype : 'button',
				text : '搜索',
				iconCls : 'search',
				handler : this.search,
				scope : this
			}]
		}); // end of this searchPanel
		//////////////////end of this searchPanel////////////////////////////////
	
		/////////////////gridPanel////////////////////////
		var gridPanel = new Ext.grid.GridPanel({
			id : 'FormDefGrid',
			region : 'center',
			stripeRows : true,
			store : store,
			trackMouseOver : true,
			disableSelection : false,
			loadMask : true,
			plugins : rowActions,
			cm : cm,
			sm : sm,
			viewConfig : {
				forceFit : true,
				autoFill : true, // 自动填充
				forceFit : true
			},
			bbar : new HT.PagingBar({store : store})
		});
		rowActions.on('action', this.onRowAction, this);
	
		////////////////end of this gridPanel//////////////////////////

		///////////主窗口/////////////////////////
		var win = new Ext.Window({
			id : 'FormDefSelectorWin',
			title : '流程表单选择',
			iconCls : 'menu-form',
			maximizable : true,
			modal : true,
			width : 650,
			height : 400,
			layout : 'border',
			items : [searchPanel,gridPanel],
			buttonAlign : 'center',
			buttons : [{
				text : '确定',
				iconCls : 'btn-ok',
				handler : function(){
					var record = Ext.getCmp('FormDefGrid').getSelectionModel().getSelections();
					if(record != null && record.length > 0){
						if (callback != null)
							callback.call(this, record[0].data.formDefId, record[0].data.formTitle);
					}else
						callback.call(this,null,null);
					win.close();
				}
			},{
				text : '取消',
				iconCls : 'btn-cancel',
				handler : function(){
					win.close();
				}
			}]
		}); // end of this window
		return win; //返回
		
	},
	
	//搜索
	search : function(){
		var search = Ext.getCmp('FormDefSelectorSearchPanel');
		var grid = Ext.getCmp('FormDefGrid');
		if (search.getForm().isValid()) {
			$search({
				searchPanel :search,
				gridPanel : grid
			});
		}
	},
	
	//管理
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {
			case 'btn-showDetail' :
				this.show(record.data.formDefId);
				break;
		}
	},
	
	//详细信息
	show : function(formDefId){
		FormDefDetailForm.show(formDefId);
	}
};
