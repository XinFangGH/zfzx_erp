			
	var getbplawsuitData = function(grid) {
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
	bplawsuitList = Ext.extend(Ext.Panel,{
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
			bplawsuitList.superclass.constructor.call(this,
					{
						items : [ this.grid_bplawsuitListInfo ]
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
			
			this.bplawsuitListBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createbplawsuitListInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletebplawsuitListInfo
					}]
				})
			this.datafield1 = new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly : this.isReadOnly
			});
	this.grid_bplawsuitListInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				name:"bplawsuitList",
				tbar :this.bplawsuitListBar,
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
								url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntLawsuit.do?enterpriseId='+this.enterpriseId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'lawsuitId',
							type : 'int'
						}
						,'registerDate'
						,'registerReason'
						,'relatedMoney'
						,'role'
						,'recordUser'
						,'recordTime'
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
													header : '立案时间 ',	
													dataIndex : 'registerDate',
													sortable : true,
													format : 'Y-m-d',
													editor : this.datafield1
									/*				renderer : function(value, metaData, record, rowIndex,
															colIndex, store) {
											
														v= Ext.util.Format.date(value, 'Y-m-d');
																						
														return v;
													}*/
													

				}
																																												,{
													header : '立案原因',	
													dataIndex : 'registerReason',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '金额',	
													dataIndex : 'relatedMoney',
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
													header : '角色',	
													dataIndex : 'role',	sortable : true,
													editor : {
													xtype : 'textfield',
													eadOnly : this.isReadOnly
												}

				}
					,{
													header : '录入人',	
													dataIndex : 'recordUser',
													sortable : true
													
								
												},{
													header : '录入时间',	
													dataIndex : 'recordTime',
													format : 'Y-m-d',
													sortable : true
													
								
								}
																					
	
					]
              });
                
				
					this.grid_bplawsuitListInfo.getStore().load();

		},
		
		createbplawsuitListInfo : function() {
			
			var gridadd = this.grid_bplawsuitListInfo;
			var storeadd = this.grid_bplawsuitListInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			/*for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = '';
				p.data[keys[6]] = '';
			}*/
			p.data[keys[0]] = null;
			p.data[keys[1]] = Ext.util.Format.date(new Date(), 'Y-m-d');
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
		
			
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deletebplawsuitListInfo : function() {
			var griddel = this.grid_bplawsuitListInfo;
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
							if (row.data.lawsuitId == null || row.data.lawsuitId == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/multiDelBpCustEntLawsuit.do',
										{
											lawsuitId :row.data.lawsuitId
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
	
	

	
	
