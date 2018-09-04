			
	var getbppaytaxData = function(grid) {
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
	bppaytaxList = Ext.extend(Ext.Panel,{
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
			bppaytaxList.superclass.constructor.call(this,
					{
						items : [ this.grid_bppaytaxListInfo ]
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
			
			this.bppaytaxListBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createbppaytaxListInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletebppaytaxListInfo
					}]
				})
			this.datafield1 = new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly : this.isReadOnly
			});
	this.grid_bppaytaxListInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				name:"bppaytaxList",
				tbar :this.bppaytaxListBar,
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
								url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntPaytax.do?enterpriseId='+this.enterpriseId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'paytaxsId',
							type : 'int'
						}
						,'year'
						,'increaseTaxe'
						,'salesTax'
						,'incomeTax'
						,'customersTax'
						,'additionTax'
						,'ohterTax'
						,'sumTax'
					
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),
				{
					header : 'lawsuitId',
					dataIndex : 'lawsuitId',
					hidden : true
				}
																																																				,{
													header : '年度 ',	
													dataIndex : 'year',
													sortable : true,
													editor : {
														xtype : 'numberfield',
														readOnly : this.isReadOnly
													}
													
													

				}
																																												,{
													header : '增值税',	
													dataIndex : 'increaseTaxe',
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
													header : '营业税',	
													dataIndex : 'salesTax',
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
													header : '所得税',	
													dataIndex : 'incomeTax',	sortable : true,
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
													header : '关税',	
													dataIndex : 'customersTax',
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
													
								
												},{
													header : '附加税',	
													dataIndex : 'additionTax',
													format : 'Y-m-d',
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
													header : '其他',	
													dataIndex : 'ohterTax',
													format : 'Y-m-d',
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
													
								
								}						,{
									header : '合计',	
									dataIndex : 'sumTax',
									format : 'Y-m-d',
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
	
					],			listeners : {
				scope : this,
				afteredit : function(e) {
				var sumTax=0;
				//	if (e.originalValue != e.value) {//编辑行数据发生改变
		
				if(e.field!='year'){
					if(e.record.data['salesTax']!=null){
						sumTax=sumTax+e.record.data['salesTax']
					}
					if(e.record.data['increaseTaxe']!=null){
						sumTax=sumTax+e.record.data['increaseTaxe']
					}
					if(e.record.data['incomeTax']!=null){
						sumTax=sumTax+e.record.data['incomeTax']
					}
					if(e.record.data['customersTax']!=null){
						sumTax=sumTax+e.record.data['customersTax']
					}
					if(e.record.data['additionTax']!=null){
						sumTax=sumTax+e.record.data['additionTax']
					}
					if(e.record.data['ohterTax']!=null){
						sumTax=sumTax+e.record.data['ohterTax']
					}
					
				   e.record.data['sumTax']=sumTax;
					 this.grid_bppaytaxListInfo.getView().refresh();
				}
					
				//	}
						
				
		            //    this.grid_bppaytaxListInfo.getView().refresh();
				},
				rowdblclick : function(grid, rowIndex, e) {
					//this.seeMortgageInfo();
				}
			} 
              });
                
				
					this.grid_bppaytaxListInfo.getStore().load();

		},
		
		createbppaytaxListInfo : function() {
			
			var gridadd = this.grid_bppaytaxListInfo;
			var storeadd = this.grid_bppaytaxListInfo.getStore();
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
			p.data[keys[7]] = null;
			p.data[keys[8]] = null;
			
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deletebppaytaxListInfo : function() {
			var griddel = this.grid_bppaytaxListInfo;
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
							if (row.data.paytaxsId == null || row.data.paytaxsId == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/multiDelBpCustEntPaytax.do',
										{
											paytaxsId :row.data.paytaxsId
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
	
	

	
	
