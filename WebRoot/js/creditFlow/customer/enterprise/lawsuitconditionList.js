			
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
	lawsuitconditionList = Ext.extend(Ext.Panel,{
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
			lawsuitconditionList.superclass.constructor.call(this,
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
			
			this.cashflowandSaleIncomeBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createcashflowandSaleIncomeInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletecashflowandSaleIncomeInfo
					}]
				})

	this.grid_cashflowandSaleIncomeInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				name:"cashflowandSaleIncome",
				tbar :this.cashflowandSaleIncomeBar,
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
								url : __ctxPath + '/creditFlow/customer/enterprise/listBpCustEntCashflowAndSaleIncome.do?enterpriseId='+this.enterpriseId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [{
							name : 'cashflowAndSaleIncomeId',
							type : 'int'
						}
						,'month'
						,'mainBusinessIncome'
						,'cashflowincomeMoney'
						,'payGoodsMoney'
						,'cashflowpayMoney'
						,'enterpriseId'
																																	]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),
				{
					header : 'cashflowAndSaleIncomeId',
					dataIndex : 'cashflowAndSaleIncomeId',
					hidden : true
				}
																																																				,{
													header : '月份 ',	
													dataIndex : 'month',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '主营业务收入',	
													dataIndex : 'mainBusinessIncome',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '现金流入额',	
													dataIndex : 'cashflowincomeMoney',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																																												,{
													header : '支付货款金额',	
													dataIndex : 'payGoodsMoney',	sortable : true,
													editor : {
																																													xtype : 'textfield',
																																													readOnly : this.isReadOnly
																																												}

				}
																																												,{
													header : '现金流出额',	
													dataIndex : 'cashflowpayMoney',
													sortable : true,
													editor : {
														xtype : 'textfield',
														readOnly : this.isReadOnly
													}

				}
																					
	
					]
              });
                
				
					this.grid_cashflowandSaleIncomeInfo.getStore().load();

		},
		
		createcashflowandSaleIncomeInfo : function() {
			
			var gridadd = this.grid_cashflowandSaleIncomeInfo;
			var storeadd = this.grid_cashflowandSaleIncomeInfo.getStore();
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
			
		

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deletecashflowandSaleIncomeInfo : function() {
			var griddel = this.grid_cashflowandSaleIncomeInfo;
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
							if (row.data.cashflowAndSaleIncomeId == null || row.data.cashflowAndSaleIncomeId == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/customer/enterprise/deteleBpCustEntCashflowAndSaleIncome.do',
										{
											cashflowAndSaleIncomeId :row.data.cashflowAndSaleIncomeId
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
	
	});
	
	

	
	
