var getLeaseOwnerGridDate = function(grid) {
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
LeaseObjectManageChPlaceInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"leaseObjectManageChPlaceInfo",
		leaseObjectInsurancesm : null,
		leaseObjectInsurancebar : null,
		bussinessType:null,
		grid_leaseObjectChPlaceGrid : null,
		objectId : null,
		enId : null,
		isHidden : false,
		isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.objectId) != "undefined") {
				this.objectId = _cfg.objectId;
			}
			
			Ext.applyIf(this, _cfg);
			
			this.initUIComponents();
			LeaseObjectManageChPlaceInfo.superclass.constructor.call(this,{
						items : [ 
							this.grid_leaseObjectChPlaceGrid 
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
						handler : this.createLeaseObjectChPlaceRecord
							
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.deleteLeaseObjectChPlaceRecord
					} ]
				})
	var url="";
	if(typeof(this.objectId) !='undefined'){
		url=__ctxPath+ '/creditFlow/leaseFinance/project/listFlLeaseObjectManagePlace.do?leaseObjectId='+this.objectId;
	}
	this.grid_leaseObjectChPlaceGrid = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
				id:"leaseObjectChPlaceGrid",
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
										name : 'id'
									},
									{
										name : 'leaseObjectId'
									},
									{
										name : 'oldPlace'
									},
									{
										name : 'destPlace'
									},
									{
										name : 'moveDate'
									},
									{
										name : 'operationPersonId'
									},
									{
										name : 'operationPersonName'
									},
									{
										name : 'operationDate'
									},
									{
										name : 'standardSize'
									}
							]),
							root : 'result'
						})
			}),
				columns : [
					{
						header : 'id',
						hidden:true,
						dataIndex:'id'
					},{
						header : 'leaseObjectId',
						hidden:true,
						dataIndex:'leaseObjectId'
					},{
						header : 'operationPersonId',
						hidden:true,
						dataIndex:'operationPersonId'
					},
					{
						header : '原始存放地点',
						dataIndex : 'oldPlace',
						sortable : true,
						align : "center",
						width : 200
					},{
						header : '转移地点',
						dataIndex : 'destPlace',
						sortable : true,
						align : "center",
						width : 200,
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},{
						header : '设备型号',
						dataIndex : 'standardSize',
						sortable : true,
						align : "center",
						width : 200
					},
					{
						header : '转移时间',
						dataIndex : 'moveDate',						
						sortable : true,
						align : 'right',
						editor : this.confirmDatefield/*,
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)*/
					},  {
						header : '记录人',
						dataIndex : 'operationPersonName',
						sortable : true,
						align : "center"
					}, {
						header : '操作时间',
						dataIndex : 'operationDate',						
						sortable : true,
						align : 'right',
						renderer : function(value){
							return Ext.util.Format.date(value,'Y-m-d')
						}
					}]

              });
				if (null != this.objectId) {
					this.grid_leaseObjectChPlaceGrid.getStore().load();
				}
     },
     //done by gao
     createLeaseObjectChPlaceRecord : function(){
     	var id = this.ownerCt.id;
     	var standardSize = this.ownerCt.standardSize;
     	var destPlace = this.ownerCt.destPlace;
     	var jsUserInfoObj = Ext.util.JSON.decode(userInfo);
    		var gridadd = this.grid_leaseObjectChPlaceGrid;
			var storeadd = this.grid_leaseObjectChPlaceGrid.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			var operationPersonId = jsUserInfoObj.user.userId;
			var operationPersonName = jsUserInfoObj.user.fullname;
			for ( var i = 1; i < keys.length; i++) {
				p.data['leaseObjectId'] = id;
				p.data['standardSize'] = standardSize;
				p.data['oldPlace'] = destPlace;
				p.data['operationPersonId'] = operationPersonId;
				p.data['operationPersonName'] = operationPersonName;
				p.data['operationDate'] =new Date();
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     //done by gao
     deleteLeaseObjectChPlaceRecord : function(){
			var griddel = this.grid_leaseObjectChPlaceGrid;
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
							if (row.data.id == null || row.data.id== '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/leaseFinance/project/multiDelFlLeaseObjectManagePlace.do',
										{
											ids :row.data.id
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
