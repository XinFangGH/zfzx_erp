var getLeaseObjectInsurancePayGridData = function(grid) {
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
			// var index = str.lastIndexOf(",");
			// str = str.substring(0, index) + "}";
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
LeaseObjectManageInsurancePayInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"leaseObjectInsurancePayGrid",
		leaseObjectInsurancesm : null,
		leaseObjectInsurancebar : null,
		bussinessType:null,
		grid_leaseObjectInsurancePayGrid : null,
		objectId : null,
		enId : null,
		isHidden : false,
		isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.objectId) != "undefined") {
				this.objectId = _cfg.objectId;
			}
			
			Ext.applyIf(this, _cfg);
			if (typeof (_cfg.isHidden) != "undefined") {
				this.isHidden = _cfg.isHidden;
			}
			
			this.initUIComponents();
			LeaseObjectManageInsurancePayInfo.superclass.constructor.call(this,{
						items : [ 
							this.grid_leaseObjectInsurancePayGrid 
						]
					})
		},
		initUIComponents : function() {
			// 日期格式化
			this.confirmDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
// readOnly:!this.isgdEdit,
					allowBlank : false
				})
				
			this.comboType1= new Ext.form.ComboBox({
									displayField : 'insuranceCode',
								    valueField : 'insuranceCode',
								    triggerAction : 'all',
								   // readOnly:this.isHidden,
								    store : new Ext.data.JsonStore({
								    autoLoad :true,
									url : __ctxPath+ '/creditFlow/leaseFinance/project/listFlLeaseFinanceInsuranceInfo.do?projectId='+this.objectId,
									fields : [{name:'insuranceCode'},{name : 'insuranceCompanyName'}],
									root:'result'
									}),
									listeners:{
									  'select': function(combo,record,index ) {
												 var grid=Ext.getCmp("leaseObjectInsurancePayGrid");
												 	 grid.getSelectionModel().getSelected().data['insuranceCompanyName']=record.data.insuranceCompanyName;
								           }
								} 
								
			})
			//this.comboType1.store.reload();
			this.comboType2= new Ext.form.ComboBox({
									mode: 'local',
								    triggerAction : 'all',
								    readOnly:this.isHidden,
								    store: new Ext.data.ArrayStore({
								    	autoDestroy: true,
								        id: 0,
								        fields: [
								            'id',
								            'name'
								        ],
								        data: [[1, '已经理赔'], [2, '已报案'],[3,'免赔'],[4,'未理赔']]
								    }),
								    valueField: 'id',
								    displayField: 'name',
									listeners:{
									  'select': function(combo,record,index ) {
												 	var grid=Ext.getCmp("leaseObjectInsurancePayGrid");
												 	 grid.getSelectionModel().getSelected().data['status']=record.data.id; 
								           }
								          
									} 
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
		url=__ctxPath+ '/creditFlow/leaseFinance/project/listFlLeaseFinanceInsurancePay.do?leaseObjectId='+this.objectId;
	}
	this.grid_leaseObjectInsurancePayGrid = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
				id:"leaseObjectInsurancePayGrid",
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
										name : 'insuranceCode'
									},
									{
										name : 'insuranceCompanyName'
									},
									{
										name : 'standardSize'
									},
									{
										name : 'outInsuranceDate'
									},
									{
										name : 'submitDate'
									},
									{
										name : 'outInsuranceReason'
									},
									{
										name : 'loseMoney'
									},
									{
										name : 'repayMoney'
									},
									{
										name : 'status'
									},{
										name:'payDate'
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
					},
					{
						header : '保单编号',
						dataIndex : 'insuranceCode',
						editor :this.comboType1,
						width : 170,
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
								var objcom = this.editor;
								var objStore = objcom.getStore();
								var idx = objStore.find("insuranceCode", value);
								if (idx != "-1") {
									   return objStore.getAt(idx).data.insuranceCode;
								} else {
										if(record.data.insuranceCode==""){
											
										record.data.insuranceCode=value
											
										}else{
											var x = store.getModifiedRecords();
											if(x!=null && x!=""){
												record.data.insuranceCode=value
											}
									}
									    return record.get("insuranceCode");
								}	
							}
					},{
						header : '保险单位',
						dataIndex : 'insuranceCompanyName',
						sortable : true,
						align : "center",
						width : 200
					},{
						header : '设备型号',
						dataIndex : 'standardSize',
						sortable : true,
						align : "center",
						width : 200
					},
					{
						header : '出险时间',
						dataIndex : 'outInsuranceDate',						
						sortable : true,
						align : 'right',
						editor : this.confirmDatefield,
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)
					},  {
						header : '报案时间',
						dataIndex : 'submitDate',
						sortable : true,
						align : 'right',
						editor : this.confirmDatefield,
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)
					},{
						header:'出险原因',
						dataIndex:'outInsuranceReason',
						editor: {
								 readOnly:this.isHidden,
								xtype : 'textfield'
								}
					}, {
								header : '定损金额',
								dataIndex : 'loseMoney',
								align :'right',
								width : 127,
// summaryType: 'sum',
								editor : {
								 readOnly:this.isHidden,
								xtype : 'numberfield'
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									 
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
						},{
								header : '赔付金额',
								dataIndex : 'repayMoney',
								align :'right',
								width : 127,
// summaryType: 'sum',
								editor : {
								 readOnly:this.isHidden,
								xtype : 'numberfield'
								},
								renderer : function(value,metaData, record,rowIndex, colIndex,store){
									 
													return Ext.util.Format.number(value,',000,000,000.00')+"元"
												  
								}
							
						},{
								header : '理赔状态',
								dataIndex : 'status',
								editor :this.comboType2,
								width : 170,
								renderer : function(value,metaData, record,rowIndex, colIndex,store) {
										var objcom = this.editor;
										var objStore = objcom.getStore();
										var idx = objStore.find("id", value);
										if (idx != "-1") {
											   return objStore.getAt(idx).data.name;
										} else {
												if(record.data.status==""){
												record.data.status=value
													
												}else{
													var x = store.getModifiedRecords();
													if(x!=null && x!=""){
														record.data.status=value
													}
											}
											            return record.get("status");
										}	
					
								}
						},{
							header : '赔付时间',
							dataIndex : 'payDate',
							sortable : true,
							align : 'right',
							editor : this.confirmDatefield,
							renderer : ZW.ux.dateRenderer(this.confirmDatefield)
						}]
              });
				if (null != this.objectId) {
					this.grid_leaseObjectInsurancePayGrid.getStore().load();
				}
     },
     // done by gao
     createLeaseObjectChPlaceRecord : function(){
     	var id = this.ownerCt.id;
     	var standardSize = this.ownerCt.standardSize;
    		var gridadd = this.grid_leaseObjectInsurancePayGrid;
			var storeadd = this.grid_leaseObjectInsurancePayGrid.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data['leaseObjectId'] = id;
				p.data['standardSize'] = standardSize;
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     // done by gao
     deleteLeaseObjectChPlaceRecord : function(){
			var griddel = this.grid_leaseObjectInsurancePayGrid;
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
										__ctxPath + '/creditFlow/leaseFinance/project/multiDelFlLeaseFinanceInsurancePay.do',
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
