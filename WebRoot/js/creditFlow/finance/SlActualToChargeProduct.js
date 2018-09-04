var getSlActualInfoData = function(grid) {
	if (typeof (grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.items.get(0).getStore().getRange(0, grid.items.get(0).getStore().getCount()); // 得到修改的数据（记录对象）
	var vCount = vRecords.length; // 得到记录长度
	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
		var str = Ext.util.JSON.encode(vRecords[i].data);
		vDatas += str + '@';
	}
		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
}
SlActualToChargeProduct= Ext.extend(Ext.Panel, {
	isProduct:false,
	productId:null,
	projectId:null,
	projectName:null,
	projectNumber:null,
	isFlow:true,
	constructor : function(_cfg) {
		if(typeof(_cfg.isProduct)!="undefined"){
	          this.isProduct=_cfg.isProduct;
	    }
	    if(typeof(_cfg.projectId)!="undefined"){
	          this.projectId=_cfg.projectId;
	    }
	    if(typeof(_cfg.projectName)!="undefined"){
	          this.projectName=_cfg.projectName;
	    }
	    if(typeof(_cfg.projectNumber)!="undefined"){
	          this.projectNumber=_cfg.projectNumber;
	    }
	    if(typeof(_cfg.productId)!="undefined"){
	          this.productId=_cfg.productId;
	    }
	    if (typeof (_cfg.isFlow) != "undefined") {
			this.isFlow = _cfg.isFlow;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlActualToChargeProduct.superclass.constructor.call(this, {
			layout : 'anchor',
			autoWidth:true,
			items : [/*{
				xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>',
				hidden:this.isFlow
			},*/this.gridPanel]
		});

	},
	initUIComponents : function() {
		this.topbar =this.isAllReadOnly?null:new Ext.Toolbar( {
			items : [{
				text : '增加',
				iconCls : 'btn-add',
				scope : this,
				hidden:this.isHiddenAdd,
				handler : this.createRs
			}, '-', {
				iconCls : 'btn-del',
				scope : this,
				text : '删除',
				hidden:this.isHiddenDel,
				handler : this.removeSelRs
			}]
		});
		var url="";
		if(null!=this.projectId){
			url=__ctxPath +'/creditFlow/finance/getByProjectIdSlActualToCharge.do?projectId='+this.projectId;
		}else{
			url=__ctxPath +'/creditFlow/finance/getByProductIdSlActualToCharge.do?productId='+this.productId
		}
		this.gridPanel = new Ext.grid.EditorGridPanel( {
			tbar :this.topbar,
			autoHeight : true,
			clicksToEdit :1,
			stripeRows : true,
			enableDragDrop : false,
			viewConfig : {
				forceFit : true
			},
			sm : this.isAllReadOnly?null:new Ext.grid.CheckboxSelectionModel({}),
			autoExpandColumn:'remark',
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url : url,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'actualChargeId'
						},{
							name : 'typeName'
						},{
							name : 'chargeStandard'
						},{
							name : 'planChargeId'
						},{
							name : 'remark'
						}
					]),
					root : 'result'
				})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),new Ext.grid.RowNumberer({width :20}),
				{
					header : 'actualChargeId',
					dataIndex : 'actualChargeId',
					hidden : true
				}, {
					header : '费用类型',
					dataIndex : 'typeName',
					editor : {
						xtype : 'textfield',
						readOnly :this.isReadOnly
					}
				}, {
					header : '费用标准',
					dataIndex : 'chargeStandard',
					editor : {
						readOnly : this.isReadOnly,
						xtype : 'numberfield'
					}
				}, {
					header : '备注',
					dataIndex : 'remark',
					align : "center",
					width : 438,
					editor : new Ext.form.TextField({
						readOnly : this.isReadOnly,
						allowBlank : false
					})
				}
			]
		});
		this.gridPanel.getStore().load();
	},
	createRs : function() {
		if(this.isFlow){
			var newRecord = this.gridPanel.getStore().recordType;
			var newData = new newRecord({
				actualChargeId :null,
				typeName : '',
				chargeStandard : 0,
				remark : ''
			});
			this.gridPanel.stopEditing();
			this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
			this.gridPanel.getView().refresh();
			this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
			this.gridPanel.startEditing(0, 0);
		}else{
			new SlActualToChargeProductForm({
				gridPanel:this.gridPanel,
				productId:this.productId
			}).show();
		}
	},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		alert("vRecords=="+vRecords);
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if(vCount > 0){
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.actualChargeId != null && vRecords[i].data.actualChargeId != "") {
					if(null!=this.projectId){
						st = {
							"actualChargeId" : vRecords[i].data.actualChargeId,
							"typeName" : vRecords[i].data.typeName,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"planChargeId":vRecords[i].data.planChargeId,
							"projectName":this.projectName,
							"projectNumber":this.projectNumber,
							"projectId":this.projectId,
							"slChargeDetails":null,
							"businessType":'SmallLoan'
						};
					}else{
						st = {
							"actualChargeId" : vRecords[i].data.actualChargeId,
							"typeName" : vRecords[i].data.typeName,
							"planChargeId":vRecords[i].data.planChargeId,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"projectName":'产品',
							"projectNumber":"_"+i,
							"projectId":i,
							"slChargeDetails":null
						};
					}
				} else {
					if(null!=this.projectId){
						st = {
							"actualChargeId" :null,
							"typeName" : vRecords[i].data.typeName,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"projectName":this.projectName,
							"planChargeId":vRecords[i].data.planChargeId,
							"projectNumber":this.projectNumber,
							"projectId":this.projectId,
							"slChargeDetails":null,
							"businessType":'SmallLoan'
						};
					}else{
						st = {
							"actualChargeId" :null,
							"typeName" : vRecords[i].data.typeName,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"planChargeId":vRecords[i].data.planChargeId,
							"projectName":'产品',
							"projectNumber":"_"+i,
							"projectId":i,
							"slChargeDetails":null
						};
					}
				}
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	removeSelRs : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
		} else {
			if(selected.data.actualChargeId){
				 $delGridRs({
//					url : __ctxPath+ '/creditFlow/finance/deleteByIdsSlActualToCharge.do',
				 	url : __ctxPath+ '/creditFlow/finance/multiDelSlActualToCharge.do',
					grid : this.gridPanel,
					idName : 'actualChargeId'
				});
			}else{
				grid.store.remove(selected);
			}
		}
			   
		/*var fundIntentGridPanel = this.gridPanel;
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
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
		var a = fundIntentGridPanel.getSelectionModel().getSelections();
		if (a <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选择要删除的记录');
			return false;
		};
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
			if (btn == "yes") {
				var s = fundIntentGridPanel.getSelectionModel().getSelections();
				for (var i = 0; i < s.length; i++) {
					var row = s[i];
					if (row.data.actualChargeId == null
							|| row.data.actualChargeId == '') {
						fundIntentGridPanel.getStore().remove(row);
					} else {
						deleteFun(
								__ctxPath+ '/creditFlow/finance/deleteSlActualToCharge.do',
								{
									actualChargeId : row.data.actualChargeId
								}, function() {
									fundIntentGridPanel.getStore().remove(row);
									fundIntentGridPanel.getStore().reload();
								}, i, s.length);

					}
				}
			}
		});*/
	}
});