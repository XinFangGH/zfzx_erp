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
			var index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
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
LeaseObjectInsuranceInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"gudong_info",
		leaseObjectInsurancesm : null,
		leaseObjectInsurancebar : null,
		bussinessType:null,
		grid_leaseObjectInsurance : null,
		projectId : null,
		enId : null,
		isHidden : false,
		isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.projectId) != "undefined") {
				this.projectId = _cfg.projectId;
			}
			if (typeof (_cfg.enId) != "undefined") {
				this.enId = _cfg.enId;
			}
			if (typeof (_cfg.bussinessType) != "undefined") {
				this.bussinessType = _cfg.bussinessType;
			}
			if (_cfg.isHidden) {
				this.isHidden = _cfg.isHidden;
			}
			if (typeof (_cfg.isTitle) != "undefined") {
				this.isTitle = _cfg.isTitle;
			}
			
			Ext.applyIf(this, _cfg);
			
			this.initUIComponents();
			LeaseObjectInsuranceInfo.superclass.constructor.call(this,{
						items : [ 
							{xtype:'panel',
							border:false,
							hidden : this.isTitle==true?false:true,
							bodyStyle:'margin-bottom:5px'
//							html : this.enId!=null?"":'<B><font class="x-myZW-fieldset-title">【租赁物保险信息】：</font></B>'
							},
							this.grid_leaseObjectInsurance 
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
						handler : this.createLeaseObjectInsurance
							
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.deleteLeaseObjectInsurance
					} ]
				})
	var url="";
	if(typeof(this.projectId) !='undefined'){
		url=__ctxPath+ '/creditFlow/leaseFinance/project/listFlLeaseFinanceInsuranceInfo.do?projectId='+this.projectId;
	}
	this.grid_leaseObjectInsurance = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
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
										name : 'insuranceId'
									},
									{
										name : 'insuranceName'
									},
									{
										name : 'insuranceCompanyName'
									},
									{
										name : 'insuranceCode'
									},
									{
										name : 'insuranceMoney'
									},
									{
										name : 'startDate'
									},
									{
										name : 'endDate'
									},
									{
										name : 'insurancePerson'
									},{
									
									    name : 'insuranceComment'
									},
									{
										name : 'proejctId'
									}
							]),
							root : 'result'
						})
			}),
				columns : [
					{
						header : 'insuranceId',
						hidden:true,
						dataIndex:'insuranceId'
					},{
						header : 'projectId',
						hidden:true,
						dataIndex:'projectId'
					},
					{
						header : '保险名称',
						dataIndex : 'insuranceName',
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
						header : '保险公司名称',
						dataIndex : 'insuranceCompanyName',
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
						header : '保单编号',
						dataIndex : 'insuranceCode',
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
					},
					{
						header : '金额(万元)',
						dataIndex : 'insuranceMoney',						
						sortable : true,
						align : 'right',
						editor : {
							xtype : 'numberfield',
							readOnly:this.isHidden,
							allowBlank:false,
							style: {imeMode:'disabled'}
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							return Ext.util.Format.number(value,'0,000.00')+"万元"	
						}
					}, {
						header : '保险起始日期',
						width : 100,
						dataIndex : 'startDate',
						sortable : true,
						editor : this.confirmDatefield,/*{
							xtype : 'datefield',
							format : 'Y-m-d',					
//							readOnly:!this.isgdEdit,
							allowBlank : false
						},*/
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)
					}, {
						header : '截止日期',
						width : 100,
						dataIndex : 'endDate',
						sortable : true,
						editor :this.confirmDatefield,/*{
							xtype : 'datefield',
							format : 'Y-m-d',					
//							readOnly:!this.isgdEdit,
							allowBlank : false
						}*/
						renderer : ZW.ux.dateRenderer(this.confirmDatefield)
					}, {
						header : '保险受益人',
						dataIndex : 'insurancePerson',
						sortable : true,
						align : "center",
						width : 200,
						editor : {
							xtype : 'textfield'
//							readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},{
						header : '备注',
						dataIndex : 'insuranceComment',
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
					} ]

              });
				if (null != this.projectId) {
					this.grid_leaseObjectInsurance.getStore().load();
				}
     },
     createLeaseObjectInsurance : function(){
    	 var gridadd = this.grid_leaseObjectInsurance;
			var storeadd = this.grid_leaseObjectInsurance.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = null;
				p.data[keys[2]] = '';
				p.data[keys[4]] = 0;
				p.data[keys[8]]='';
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     deleteLeaseObjectInsurance : function(){
			var griddel = this.grid_leaseObjectInsurance;
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
							if (row.data.insuranceId == null || row.data.insuranceId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/leaseFinance/project/multiDelFlLeaseFinanceInsuranceInfo.do',
										{
											ids :row.data.insuranceId
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
     },
     seeLeaseObjectInsurance : function(){
			var s = this.grid_leaseObjectInsurance.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					if(record.data.shareholdertype=='company_shareequity'){
						seeEnterpriseCustomer(record.data.personid)
			
					}else{
						seePersonCustomer(record.data.personid)
					}
				}
     }
});
