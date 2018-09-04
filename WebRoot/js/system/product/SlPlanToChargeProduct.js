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
SlPlanToChargeProduct= Ext.extend(Ext.Panel, {
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
		SlPlanToChargeProduct.superclass.constructor.call(this, {
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
				text : '选择基础费用',
				iconCls : 'btn-add',
				scope : this,
				hidden:this.hiddenAdd,
				handler : this.createRs
			},  "-",{
				iconCls : 'btn-edit',
				text : '新增产品费用',
				xtype : 'button',
				scope : this,
				hidden:this.hiddenedit,
				handler : this.addnewRs
			},'-', {
				iconCls : 'btn-del',
				scope : this,
				text : '删除产品费用',
				hidden:this.hiddenDel,
				handler : this.removeSelRs
			}]
		});
		var url="";
		if(null!=this.projectId){
			url=__ctxPath +'/creditFlow/finance/listSlPlansToCharge.do?projectId='+this.projectId;
		}else{
			url=__ctxPath +'/creditFlow/finance/listByProjectIdSlPlansToCharge.do?productId='+this.productId+"&businessType="+this.businessType
		}
		
		this.comboType1= new Ext.form.ComboBox({
			mode : 'local',
			displayField : 'name',
			valueField : 'id',
			editable : false,
			triggerAction : 'all',
			width : 70,
			store : new Ext.data.SimpleStore({
				fields : ["name", "id"],
				data : [["公有", "0"],["私有", "1"]]
			})
		})
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
							name : 'plansTochargeId'
						},{
							name : 'name'
						},{
							name : 'productId'
						},{
							name : 'chargeStandard'
						},{
							name : 'businessType'
						},{
							name : 'isType'
						}
					]),
					root : 'result'
				})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),new Ext.grid.RowNumberer({width :20}),
				{
					header : 'plansTochargeId',
					dataIndex : 'plansTochargeId',
					hidden : true
				}, {
					header : '费用类型',
					dataIndex : 'name',
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
					header : '类型',
					dataIndex : 'isType',
					align : "center",
					width : 438,
					editor :this.comboType1,
					renderer:ZW.ux.comboBoxRenderer(this.comboType1)
					
				}
			]
		});
		this.gridPanel.getStore().load();
	},
	createRs : function() {
			new SlPlanToChargeProductForm({
				operateObj:this.gridPanel,
				businessType:this.businessType,
				isDelete:false,
				productId:this.productId
			}).show();
	},
	addnewRs:function(){
		new AddProductOwnPlanChargeForm({
				operateObj:this.gridPanel,
				businessType:this.businessType,
				productId:this.productId
			}).show();
	},
	getGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if(vCount > 0){
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.actualChargeId != null && vRecords[i].data.actualChargeId != "") {
					if(null!=this.projectId){
						st = {
							"actualChargeId" : vRecords[i].data.actualChargeId,
							"costType" : vRecords[i].data.costType,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"projectName":this.projectName,
							"projectNumber":this.projectNumber,
							"projectId":this.projectId,
							"slChargeDetails":null,
							"businessType":'SmallLoan'
						};
					}else{
						st = {
							"actualChargeId" : vRecords[i].data.actualChargeId,
							"costType" : vRecords[i].data.costType,
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
							"costType" : vRecords[i].data.costType,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
							"projectName":this.projectName,
							"projectNumber":this.projectNumber,
							"projectId":this.projectId,
							"slChargeDetails":null,
							"businessType":'SmallLoan'
						};
					}else{
						st = {
							"actualChargeId" :null,
							"costType" : vRecords[i].data.costType,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"remark":vRecords[i].data.remark,
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
	
	getSlPlansGridDate : function() {
		var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if(vCount > 0){
			for (var i = 0; i < vCount; i++) {
				if (vRecords[i].data.plansTochargeId != null && vRecords[i].data.plansTochargeId != "") {
						st = {
							"plansTochargeId" : vRecords[i].data.plansTochargeId,
							"name" : vRecords[i].data.name,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"isType":vRecords[i].data.isType
						};
				} else {
						st = {
							"plansTochargeId" :null,
							"name" : vRecords[i].data.name,
							"chargeStandard":vRecords[i].data.chargeStandard,
							"isType":this.isType
						};
				}
				vDatas += Ext.util.JSON.encode(st) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
	removeSelRs : function() {
		new SlPlanToChargeProductForm({
				operateObj:this.gridPanel,
				businessType:this.businessType,
				isDelete:true,
				productId:this.productId
			}).show();}
});