var DepreWin=function(assetsId){
	var formPanel=new Ext.form.FormPanel({
	     id:'workCapacityForm',
	     layout:'form',
	     url : __ctxPath + '/admin/depreciateDepreRecord.do',
	     style:'padding-top:7px;',
	     frame:true,
	     items:[{
	           xtype:'hidden',
	           name:'ids',
	           value:assetsId
	     },{
	           xtype:'hidden',
	           name:'depreRecord.recordId'
	     },{
			   fieldLabel : '当前折旧工作量',
			   xtype:'textfield',
			   name:'depreRecord.workCapacity',
	           anchor : '100%',
	           allowBlank:false
	     }]
	});
	
	var window = new Ext.Window({
		id:'depreWin',
		title: '按工作量折算',
		region:'west',
        width: 500,
        height:350,
        layout: 'form',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items:[this.setup(assetsId),formPanel],
        buttons:[{
					text : '保存',
					iconCls : 'btn-save',
					handler : function() {
						var fp = Ext.getCmp('workCapacityForm');
						if (fp.getForm().isValid()) {
							fp.getForm().submit({
								method : 'post',
								waitMsg : '正在提交数据...',
								success : function(fp, action) {
									Ext.ux.Toast.msg('操作信息', '成功保存信息！');
									window.close();
								},
								failure : function(fp, action) {
									Ext.MessageBox.show({
												title : '操作信息',
												msg : '信息保存出错，请联系管理员！',
												buttons : Ext.MessageBox.OK,
												icon : 'ext-mb-error'
											});
									window.close();
								}
							});
						}
					}
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					handler : function() {
						window.close();
					}
				}]
	});			
	window.show();
}

DepreWin.prototype.setup = function(assetsId) {
	return this.grid(assetsId);
};
/**
 * 建立DataGrid
 */
DepreWin.prototype.grid = function(assetsId) {
	var cm = new Ext.grid.ColumnModel({
		columns : [new Ext.grid.RowNumberer(), {
					header : 'recordId',
					dataIndex : 'recordId',
					hidden : true
				}, {
					header : '资产名称',
					dataIndex : 'assets'
				}
				, {
					header : '工作量',
					id:'workCapacity',
					dataIndex : 'workCapacity'
				}
				,{
					header : '折旧后值',
					dataIndex : 'depreAmount'
				}
				, {
					header : '计算时间',
					dataIndex : 'calTime'
				}],
		defaults : {
			sortable : true,
			menuDisabled : false,
			width : 100
		}
	});

	var store = this.initData(assetsId);
	store.load({
				params : {
					start : 0,
					limit : 6
				}
			});
	var grid = new Ext.grid.GridPanel({
				title:'折旧记录',
				store : store,
				trackMouseOver : true,
				disableSelection : false,
				loadMask : true,
				height:223,
				cm : cm,
				viewConfig : {
					forceFit : true,
					enableRowBody : false,
					showPreview : false
				},
				bbar : new HT.PagingBar({store : store})
			});

	return grid;

};


DepreWin.prototype.initData=function(assetsId){
	var store = new Ext.data.Store({  
        proxy: new Ext.data.HttpProxy({  
            url: __ctxPath+'/admin/listDepreRecord.do'
            
        }),  
        baseParams : {'Q_fixedAssets.assetsId_L_EQ':assetsId},
        reader: new Ext.data.JsonReader({ 
         root : 'result',
         totalProperty : 'totalCounts',
		 id : 'id',
		 fields : [{
					name : 'recordId',
					type : 'int'
				}

				,{ name:'assets',
				   mapping:'fixedAssets.assetsName'
				}, 'workCapacity',
				'depreAmount', 'calTime']
	}),remoteSort : true 
    }); 
    store.setDefaultSort('recordId', 'desc');
    return store;
};


