			
	var getupAnddownDataContract = function(grid) {
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
	UpStreamContract = Ext.extend(Ext.Panel,{
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
			UpStreamContract.superclass.constructor.call(this,
					{
						items : [ this.grid_UpStreamContractInfo ]
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
			
			this.UpStreamContractBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createUpStreamContractInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteUpStreamContractInfo
					}]
				})

	this.grid_UpStreamContractInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				name:"grid_UpStreamContractInfo",
				tbar :this.UpStreamContractBar,
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
								url : __ctxPath + '/creditFlow/customer/enterprise/UpstreamlistBpCustEntUpanddowncontract.do?upAndDownContractId='+this.upAndDownContractId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'upContractId',
							type : 'int'
						}
						,'mainBuyer'
						,'buyerContractMoney'
						,'buyerContractDuration'
						,'contractPolicy'
						
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),
				{
					header : 'upContractId',
					dataIndex : 'upContractId',
					hidden : true
				}
																																																				,{
													header : '主要采购对象   ',	
													dataIndex : 'mainBuyer',
													sortable : true,
													editor : {
														xtype : 'textfield',
														allowBlank : false,
														readOnly : this.isReadOnly
														
													}

				}
																																												,{
													header : '采购合同金额',	
													dataIndex : 'buyerContractMoney',
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
													header : '合同期限',	
													dataIndex : 'buyerContractDuration',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '合同政策',	
													dataIndex : 'contractPolicy',	sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												
																																												
																					
	
					]
              });
                
				
					this.grid_UpStreamContractInfo.getStore().load();

		},
		
		createUpStreamContractInfo : function() {
			var gridadd = this.grid_UpStreamContractInfo;
		
			var storeadd = this.grid_UpStreamContractInfo.getStore();
			
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;

		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
			
		},
		deleteUpStreamContractInfo : function() {
			var griddel = this.grid_UpStreamContractInfo;
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
							if (row.data.upContractId == null || row.data.upContractId == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/UpstreamdeteleBpCustEntUpanddowncontract.do',
										{
											upContractId :row.data.upContractId
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
	
	
DownStreamContract = Ext.extend(Ext.Panel,{
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
			DownStreamContract.superclass.constructor.call(this,
					{
						items : [ this.grid_DownStreamContractInfo ]
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
			
			this.DownStreamContractBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createDownStreamContractInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteDownStreamContractInfo
					}]
				})

	this.grid_DownStreamContractInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				name : 'grid_DownStreamContractInfo',
				tbar :this.DownStreamContractBar,
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
								url : __ctxPath + '/creditFlow/customer/enterprise/DownstreamlistBpCustEntUpanddowncontract.do?upAndDownContractId='+this.upAndDownContractId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'downContractId',
							type : 'int'
						}
							,'dateMode'
							,'count'
							,'contractMoney'
							,'saleIncomeyMoney'
							,'noContractSaleMoney'
							
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),{
					header : 'downContractId',
					dataIndex : 'downContractId',
					hidden : true
				}
																																																				,{
													header : '日期模型',	
													dataIndex : 'dateMode',
													sortable : true,
													editor : {
														xtype : 'textfield',
														allowBlank : false,
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '件数',	
													dataIndex : 'count',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '合同金额',	
													dataIndex : 'contractMoney',
													sortable : true,
													editor : {
														xtype : 'textfield',
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
													header : '销售收入实现金额',	
													dataIndex : 'saleIncomeyMoney',
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
													header : '无合同销售额',	
													dataIndex : 'noContractSaleMoney',
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
				]
              });
                
				
					this.grid_DownStreamContractInfo.getStore().load();

		},
		
		createDownStreamContractInfo : function() {
			var gridadd = this.grid_DownStreamContractInfo;
			var storeadd = this.grid_DownStreamContractInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = null;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = null;
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteDownStreamContractInfo : function() {
			var griddel = this.grid_DownStreamContractInfo;
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
							if (row.data.downContractId == null || row.data.downContractId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/DownstreamdeteleBpCustEntUpanddowncontract.do',
										{
											downContractId :row.data.downContractId
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
	
	
