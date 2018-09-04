var getLeaseInsuranceGridDate = function(grid) {
	if (typeof (grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）

	var vCount = vRecords.length; // 得到记录长度

	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			//var index = str.lastIndexOf(",");
			//str = str.substring(0, index) + "}";
			vDatas += str + '@';
		}

		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}
var deleteFun = function(url, prame, sucessFun,i,j) {
	Ext.Ajax.request( {
		url : url,
		method : 'POST',
		success : function(response) {
			if (response.responseText.trim() == '{success:true}') {
				if(i==(j-1)){
				    Ext.ux.Toast.msg('操作信息', '删除成功!');
				}
				sucessFun();
			} else if (response.responseText.trim() == '{success:false}') {
				Ext.ux.Toast.msg('操作信息', '删除失败!');
			}
		},
		params : prame
	});
};
LeaseObjectManageChOwnerInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"leaseObjectChOwnerGrid",
		leaseObjectInsurancesm : null,
		leaseObjectInsurancebar : null,
		bussinessType:null,
		grid_leaseObjectChOwnerGrid : null,
		objectId : null,
		enId : null,
		isHidden : false,
		isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.objectId) != "undefined") {
				this.objectId = _cfg.objectId;
			}
			if (typeof (_cfg.isHidden) != "undefined") {
				this.isHidden = _cfg.isHidden;
			}
			Ext.applyIf(this, _cfg);
			
			this.initUIComponents();
			LeaseObjectManageChOwnerInfo.superclass.constructor.call(this,{
						items : [ 
							this.grid_leaseObjectChOwnerGrid 
						]
					})
		},
		initUIComponents : function() {
			//日期格式化
			this.confirmDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
//					readOnly:!this.isgdEdit,
					allowBlank : false
				})
			
			
			this.leaseObjectInsurancebar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.createLeaseObjectChOwnerRecord
							
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.deleteLeaseObjectChOwnerRecord
					} ]
				})
	var url="";
	if(typeof(this.objectId) !='undefined'){
		url=__ctxPath+ '/creditFlow/leaseFinance/project/listFlLeaseObjectManageOwner.do?leaseObjectId='+this.objectId;
	}
	this.grid_leaseObjectChOwnerGrid = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
				id:"leaseObjectChOwnerGrid",
				tbar : this.leaseObjectInsurancebar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store(
						{
							proxy : new Ext.data.HttpProxy(
									{
										url:url,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'recordId'
									},
									{
										name : 'leaseObjectId'
									},
									{
										name : 'oldOwner'
									},
									{
										name : 'newOwner'
									},
									{
										name : 'changeReason'
									}
							]),
							root : 'result'
						})
			}),
				columns : [
					{
						header : 'recordId',
						hidden:true,
						dataIndex:'recordId'
					},{
						header : 'leaseObjectId',
						hidden:true,
						dataIndex:'leaseObjectId'
					},
					{
						header : '原所有人',
						dataIndex : 'oldOwner',
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						width : 200
					},{
						header : '变更后所有人',
						dataIndex : 'newOwner',
						sortable : true,
						align : "center",
						width : 200,
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						}/*,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }*/
					},{
						header : '变更原因说明',
						dataIndex : 'changeReason',
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						width : 400
					}]

              });
				if (null != this.objectId) {
					this.grid_leaseObjectChOwnerGrid.getStore().load();
				}
     },
     //done by gao  ok
     createLeaseObjectChOwnerRecord : function(){
     	var id = this.ownerCt.id;
    		var gridadd = this.grid_leaseObjectChOwnerGrid;
			var storeadd = this.grid_leaseObjectChOwnerGrid.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data['leaseObjectId'] = id;
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     //done by gao  ok
     deleteLeaseObjectChOwnerRecord : function(){
			var griddel = this.grid_leaseObjectChOwnerGrid;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s.length <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {
					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.recordId == null || row.data.recordId== '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/leaseFinance/project/multiDelFlLeaseObjectManageOwner.do',
										{
											ids :row.data.recordId
										},
										function() {
										},i,s.length)
							}
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
     }
});
