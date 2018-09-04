var groupIndivatorList = function(id) {
	var reader = new Ext.data.JsonReader({
    	root : 'topics',
    	totalProperty : 'totalProperty'
	    },[{
			name : 'id'
		},{
			name : 'indicatorTypeId'
		}, {
			name : 'indicatorType'
		}, {
			name : 'indicatorName'
		}, {
			name : 'indicatorDesc'
		}, {
			name : 'creater'
		}]
    );
    var store = new Ext.data.GroupingStore({
    		url : 'getRatingTemplateInfo.action',
    		reader: reader,
    		baseParams : {id : id},
            sortInfo:{field: 'indicatorTypeId', direction: "DESC"},
            groupField:'indicatorType'
        });
    store.load();
    
    var cm = new Ext.grid.ColumnModel([new Ext.grid.RowNumberer(),
       {header: "指标类型", width: 120, sortable: true, dataIndex: 'indicatorType',hidden:true},
       {header: "指标名称", width: 100, sortable: true, dataIndex: 'indicatorName'},
       {header: "指标选项", width: 20, sortable: true, dataIndex: 'indicatorDesc'}
       ]
    );
    var button_save = new Ext.Button({
		text : '保存',
		tooltip : '保存',
		iconCls : 'saveIcon',
		scope : this,
		handler: uf_save
	});
    var button_add = new Ext.Button({
		text : '管理担保材料',
		tooltip : '管理担保材料',
		iconCls : 'addIcon',
		scope : this,
		handler: uf_add
	});
	
    var materialsItemListToolbar = new Ext.Toolbar({
    	items : [button_add,button_save]
    });
    
    var materialsItemGrid = new Ext.grid.EditorGridPanel({
    	id : 'materialsItemGrid',
        store: store,
        cm: cm,
        view: new Ext.grid.GroupingView({
            forceFit:true,
            groupTextTpl: '{text} ({[values.rs.length]} {[values.rs.length > 1 ? "Items" : "Item"]})'
        }),
        frame:true,
        width: document.body.width,
        height: document.body.clientHeight,
        collapsible: true,
        animCollapse: false,
        iconCls: 'icon-grid',
        tbar : materialsItemListToolbar
    });
    var afteredit = function(obj){
    	var id = obj.record.get("id");
    	var value = obj.record.get("receive");
    	hashMap.put(id,value);
    }
    materialsItemGrid.addListener('afteredit',afteredit);
}
