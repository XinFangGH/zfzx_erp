			
	var getupAnddownData = function(grid) {
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
			//var index = str.lastIndexOf(",");
			//str = str.substring(0, index) + "}";
			vDatas += str + '@';
		}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
UpStreamCustom = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		personId :null,
		isHidden:false,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.personId) != "undefined") {
				this.personId = _cfg.personId;
			}
	        if(typeof(_cfg.isHidden) != "undefined"){
	        	this.isHidden=_cfg.isHidden
	        }
	        if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
				this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
			}
			if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
				this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
			}
			if (typeof (_cfg.enableEdit) != "undefined") {
				this.enableEdit = _cfg.enableEdit;
			}
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			UpStreamCustom.superclass.constructor.call(this,
					{
						items : [ this.grid_UpStreamCustomInfo ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})
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
			
			this.UpStreamCustomBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createUpStreamCustomInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteUpStreamCustomInfo
					}]
				})

	this.grid_UpStreamCustomInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				name:"grid_UpStreamCustomInfo",
				tbar :this.UpStreamCustomBar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy(
							{//this.ownerCt.ownerCt.ownerCt.ownerCt.PersonCarView.get(0)
								url : __ctxPath + '/creditFlow/customer/enterprise/UpstreamlistBpCustEntUpanddownstream.do?upAndDownCustomId='+this.upAndDownCustomId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'upCustomId',
							type : 'int'
						}
						,'materialSupplier'
						,'cooperativeDuration'
						,'supplyGoods'
						,'yearSupplyNumber'
						,'marketPrice'
						,'settleType'
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),
				{
					header : 'upCustomId',
					dataIndex : 'upCustomId',
					hidden : true
				}
																																																				,{
													header : '原材料供应商   ',	
													dataIndex : 'materialSupplier',
													sortable : true,
													editor : {
														xtype : 'textfield',
														allowBlank : false,
														readOnly : this.isReadOnly
														
													}

				}
																																												,{
													header :  App.isCustomized4DLTC()?'签订日期':'合作年限',	
													dataIndex : 'cooperativeDuration',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '供货商品',	
													dataIndex : 'supplyGoods',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : App.isCustomized4DLTC()?'销售数量':'年供货量',	
													dataIndex : 'yearSupplyNumber',	sortable : true,
													editor : {
																																													xtype : 'numberfield',
																																													readOnly : this.isReadOnly
																																												}

				}
																																												,{
													header : '市场价格',	
													dataIndex : 'marketPrice',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													},
													renderer : function(value, metaData, record, rowIndex,
															colIndex, store) {
														if(null!=value&&value!=""){
															return Ext.util.Format.number(value,
															',000,000,000.00') + "元";
															
														}else{
														    
															return '';
														}
														
													}

				}
																																												,{
													header : '货款结算方式',	
													dataIndex : 'settleType',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																					
	
					]
              });
                
				
					this.grid_UpStreamCustomInfo.getStore().load();

		},
		
		createUpStreamCustomInfo : function() {
			
			var gridadd = this.grid_UpStreamCustomInfo;
			var storeadd = this.grid_UpStreamCustomInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			/*for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = '';
				p.data[keys[6]] = '';
			}*/
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = null;
			p.data[keys[6]] = null;
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteUpStreamCustomInfo : function() {
			var griddel = this.grid_UpStreamCustomInfo;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.upCustomId == null || row.data.upCustomId == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/UpstreamdeteleBpCustEntUpanddownstream.do',
										{
											upCustomId :row.data.upCustomId
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
	
	
DownStreamCustom = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		personId :null,
		isHidden:false,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.personId) != "undefined") {
				this.personId = _cfg.personId;
			}
	        if(typeof(_cfg.isHidden) != "undefined"){
	        	this.isHidden=_cfg.isHidden
	        }
	        if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
				this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
			}
			if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
				this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
			}
			if (typeof (_cfg.enableEdit) != "undefined") {
				this.enableEdit = _cfg.enableEdit;
			}
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			DownStreamCustom.superclass.constructor.call(this,
					{
						items : [ this.grid_DownStreamCustomInfo ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})
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
			
			this.DownStreamCustomBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createDownStreamCustomInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteDownStreamCustomInfo
					}]
				})

	this.grid_DownStreamCustomInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				name : 'grid_PersonCarInfo',
				tbar :this.DownStreamCustomBar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy(
							{//this.ownerCt.ownerCt.ownerCt.ownerCt.PersonHouseView.getCmpByName("personHouseInfo")
								url : __ctxPath + '/creditFlow/customer/enterprise/DownstreamlistBpCustEntUpanddownstream.do?upAndDownCustomId='+this.upAndDownCustomId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'downCustomId',
							type : 'int'
						}
							,'customName'
							,'cooperativeDuration'
							,'saleGoods'
							,'yearOrderNumber'
							,'salePrice'
							,'settleType'
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),{
					header : 'downCustomId',
					dataIndex : 'downCustomId',
					hidden : true
				}
																																																				,{
													header : '客户名称',	
													dataIndex : 'customName',
													sortable : true,
													editor : {
														xtype : 'textfield',
														allowBlank : false,
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header :  App.isCustomized4DLTC()?'签订日期':'合作年限',	
													dataIndex : 'cooperativeDuration',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '销售商品',	
													dataIndex : 'saleGoods',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '年订货量',	
													dataIndex : 'yearOrderNumber',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : App.isCustomized4DLTC()?'合同价格':'销售价格',	
													dataIndex : 'salePrice',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													},
													renderer : function(value, metaData, record, rowIndex,
															colIndex, store) {
														if(null!=value&&value!=""){
															return Ext.util.Format.number(value,
															',000,000,000.00') + "元";
															
														}else{
														    
															return '';
														}
														
														
													}

				}
																																												,{
													header : '货款结算方式',	
													dataIndex : 'settleType',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}]
              });
                
				
					this.grid_DownStreamCustomInfo.getStore().load();

		},
		
		createDownStreamCustomInfo : function() {
			
			var gridadd = this.grid_DownStreamCustomInfo;
			var storeadd = this.grid_DownStreamCustomInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = null;
			p.data[keys[6]] = null;
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteDownStreamCustomInfo : function() {
			var griddel = this.grid_DownStreamCustomInfo;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.downCustomId == null || row.data.downCustomId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/DownstreamdeteleBpCustEntUpanddownstream.do',
										{
											downCustomId :row.data.downCustomId
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
							griddel.getView().refresh();
							
						}
					}
				})
		}
	});
	
	
