FundIntent_formulate_RowEditorGrid = Ext.extend(Ext.form.FieldSet,{
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				FundIntent_formulate_RowEditorGrid.superclass.constructor.call(this, {
							items : this.gridPanel,
							modal : true,
							region : 'center',
							layout:'fit',
							maximizable : true,
							autoScroll:true,
							title : '制定款项计划'
				});
			},//end of the constructor
			//初始化组件
			
			initUIComponents : function() {
			rowEditor=new Ext.ux.grid.RowEditor(),
			jStore_FundIntent= new Ext.data.JsonStore({
//				url : __ctxPath + "/project/listSlProcreditAssuretenet.do",
//				baseParams : {
//					'teamTravelerId': teamTravelerId
//				},
				totalProperty : 'totalCounts',
				root : 'result',
				fields : [{
						name : 'id'
					}, {
						name : 'fundType'
					}, {
						name : 'intentDate'
					}, {
						name : 'payMoney'
					}, {
						name : 'incomeMoney'
					}, {
						name : 'remark'
					}]
			}),    
			addBtnMehtod=function(){
	
				
//							Ext.Ajax.request({
//							  url: 'ajaxInsertEmptyBankLoanPlan.action',
//							  method: 'POST',
//							  success: function(response,options) {
//							  	var successCallback = function(obj){
									gird=Ext.getCmp("FundIntent_formulate_RowEditorGrid")
							  		var newRecord = gird.getStore().recordType ;
									var newData = new newRecord({
										id : '',
										fundType : '',
										payMoney:0,
										incomeMoney:0,
										intentDate : '',
										remarks : ''
									});
							  	//	newData.set('id',obj.data) ;
							  	//	rowEditor.stopEditing();
							  		gird.getStore().insert(gird.getStore().getCount(), newData);
							  		gird.getSelectionModel().selectLastRow() ;
							  		rowEditor.startEditing(jStore_FundIntent.getCount()-1) ;
//							  	};
//							  	var failureCallback = function(obj){
//							  		
//							  	};
//							  	handleAjaxRequest(response,options,successCallback,failureCallback) ;
//							  },
//							  failure : function(){
//							  	Ext.Msg.alert('信息提示','增加失败') ;
//							  },
//							  params : {
//							  	projId : projId
//							  }
//							});
						
				}
				updateBtnMethod=function() {
					rowEditor.onRowDblClick.call(rowEditor,g,r,e) ;
				}
				deleteBtnMethod=function() {
					g.removeRow.call(rowEditor);
				}
				this.gridPanel=new Ext.grid.GridPanel({
					id : 'FundIntent_formulate_RowEditorGrid',
					store : jStore_FundIntent,
				 	autoHeight:true,
					colModel : this.cModel_FundIntent,
					enableColumnHide : false,
					viewConfig : {forceFit:true},
					stripeRows : true,
					plugins : rowEditor,
		//			clicksToEdit: 1,
					autoExpandColumn:'autoExpandColumn',
					anchor:'97%',
					saveRow:function(scope,changes,hasChange,r){
					//	alert(this.gridPanel)
						var r =gird=Ext.getCmp("FundIntent_formulate_RowEditorGrid").getSelectionModel().getSelected();
					//	var id = r.data.id ;
					//	changes.id = id ;
						alert(changes.intentDate)
						alert(r.intentDate)
						scope.stopEditing(true,changes,hasChange,r);
//						Ext.Ajax.request({
//						  url: 'ajaxJsonUpdateOneBankLoanPlan.action',
//						  method: 'POST',
//						  success: function(response) {
//						  	delete changes.id ;
//						  	scope.stopEditing(true,changes,hasChange,r);
//						  },
//						  failure : function(){
//						  	scope.stopEditing(false);
//						  },
//						  params : {
//						  	jsonString : Ext.encode(changes)
//						  }
//						});
					},
					removeRow : function(scope){
//						var r = gPanel_bankLoanPlan.getSelectionModel().getSelected();
					//	var id = r.data.id ;
						Ext.Msg.confirm('操作确认','确认删除',function(btn){
							if(btn=='yes'){
//								Ext.Ajax.request({
//								  url: 'delBankLoanPlan.action',
//								  method: 'POST',
//								  success: function(response,options) {
//								  	var successCallback = function(obj){
//								  		try{
//								  			scope.hide();
//								  		}catch(e){}
//								  		jStore_bankLoanPlan.remove(r) ;
//								  	};
//								  	var failureCallback = function(obj){
//								  		Ext.Msg.alert('消息提示','操作失败') ;
//								  	};
//								  	handleAjaxRequest(response,options,successCallback,failureCallback) ;
//								  },
//								  failure : function(){
//								  	
//								  },
//								  params : {
//								  	id : id
//								  }
//								});
							}
						}) ;
					},
					tbar:[{
						text : '增加',
						iconCls : 'btn-add',
						handler : addBtnMehtod
					},'-',{
						iconCls : 'btn-update',
						text : '修改',
						handler : updateBtnMethod
					},'-',{
						iconCls : 'btn-del',
						text : '删除',
						handler : deleteBtnMethod
					}]
				})
			},
		
			cModel_FundIntent:new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),new Ext.grid.CheckboxSelectionModel(),{
					header : '资金类型',
					dataIndex : 'fundType',
					editor: new DicCombo({
									editable : true,
									lazyInit : false,
									forceSelection : false,
									xtype : 'diccombo',
									itemName : '全宗开放形式'
								})
				},{
					header : '计划到账日',
					dataIndex : 'intentDate',
					editor: new Ext.form.DateField({
						allowBlank: false
					})
					
				},{
					header : '支出金额(元)',
					dataIndex : 'payMoney',
					editor: new Ext.form.NumberField({
						allowBlank: false
					})
				},{
					header : '收入金额(元)',
					dataIndex : 'incomeMoney',
					editor: new Ext.form.NumberField({
						allowBlank: false
					})
				},{
					header : '备注',
					dataIndex : 'remark',
					editor: new Ext.form.TextField({
						allowBlank: false
					})
				}
				])
		
});