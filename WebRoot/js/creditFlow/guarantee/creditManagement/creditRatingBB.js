var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
var getCreditRatingResult = function(selected) {
	var fPanel_search = new Ext.form.FormPanel( {
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 110,
		width : (((Ext.getBody().getWidth()-6)<800) ? 800 : (Ext.getBody().getWidth()-6)),
		frame : true,
		labelWidth : 80,
		monitorValid : true,
		items : [ {
			layout : 'column',
			border : false,
			labelSeparator : ':',
			defaults : {
				layout : 'form',
				border : false,
				columnWidth : .25
			},
			items : [ {
				items : [{
					id : 'creditRating.arr_id',
					xtype : 'hidden',
					name : 'creditRating.arr_score'
				},{
					id : 'creditRating.arr_score',
					xtype : 'hidden',
					name : 'creditRating.arr_score'
				}, {
					xtype : 'textfield',
					fieldLabel : '客户类型',
					name : 'customerType',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass',
					value : selected.get('customerType')
				},{
					xtype : 'textfield',
					fieldLabel : '财务报表文件',
					name : 'financeFile',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass',
					value : selected.get('financeFile')
				}]
			}, {
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'customerName',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass',
					value : selected.get('customerName')
				},{	
					id : 'creditRating.ratingScore',
					xtype : 'textfield',
					fieldLabel : '得分情况',
					name : 'creditRating.ratingScore',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass',
					value : selected.get('ratingScore')
				} ]
			},{
				items : [ {
					xtype : 'textfield',
					fieldLabel : '资信评估模板',
					name : 'name',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass',
					value : selected.get('creditTemplate')
				},{
					id : 'creditRating.creditRegister',
					xtype : 'textfield',
					fieldLabel : '资信等级',
					name : 'creditRating.creditRegister',
					width : defaultTextfieldWidth,
					readOnly : true,
					value : selected.get('creditRegister')
				} ]
			},{
				items : [ {
					id : 'creditRating.advise_sb',
					xtype : 'textfield',
					fieldLabel : '建议内容',
					name : 'creditRating.advise_sb',
					width : defaultTextfieldWidth,
					readOnly : true,
					value : selected.get('advise_sb')
				},{
					xtype : 'textfield',
					fieldLabel : '贷款上限',
					name : 'username',
					width : defaultTextfieldWidth,
					readOnly : true,
	            cls : 'readOnlyClass'
				} ]
			}]// items
		} ]
	});
	
	var jStore_enterprise = new Ext.data.JsonStore( {
		url : 'CreditRatingResult.action',
		totalProperty : totalProperty,
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'indicatorType'
		},{
			name : 'indicatorName'
		}, {
			name : 'optionResult'
		}, {
			name : 'scoreResult'
		}],
		baseParams : {
			id : selected.get('id')
		}
	});
	
	jStore_enterprise.load({
		params : {
			start : start,
			limit : pageSize
		}
	});
	
	
	var cModel_enterprise = new Ext.grid.ColumnModel(
			[
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 50
					}),
					{
						header : "指标类型",
						width : 200,
						dataIndex : 'indicatorType'
					},{
						header : "指标",
						width : 200,
						dataIndex : 'indicatorName'
					},
					{
						header : "评估结果",
						width : 300,
						dataIndex : 'optionResult'
					},
					{
						header : "得分",
						width : 100,
						dataIndex : 'scoreResult'
					}]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 1000,
		store : jStore_enterprise,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
			//jStore_enterprise.setDefaultSort('name', 'DESC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});

	var gPanel_enterprise = new Ext.grid.GridPanel( {
		id : 'gPanel_enterprise',
		store : jStore_enterprise,
		width : Ext.getBody().getWidth() - 15,
		height : Ext.getBody().getHeight()-210,
		autoScroll : true,
		colModel : cModel_enterprise,
		//autoExpandColumn : 6,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		bbar : pagingBar,
		//tbar : [button_add,button_see,button_update,button_delete],
		listeners : {
		}
	});
	
	var panel_enterprise = new Ext.Panel( {
//			title : '资信评估结果',
		autoHeight : true,
//			autoScroll : true ,
		items : [fPanel_search,gPanel_enterprise]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		width : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight()-50,
		closable : true,
		//resizable : false,
		buttonAlign : 'center',
//			autoScroll : true,
		plain : true,
		border : false,
		modal : true,
		items : [panel_enterprise],
		title : '资信评估结果',
		collapsible : true
	});
	win.show();
}

var projectCreditRating = function (bool, projObj) {
//---------------------------企业资信评估开始-----------------------------------
	var jStore_creditRating = new Ext.data.JsonStore(getCreditRatingStoreCfg(projObj.data.id));
		
	jStore_creditRating.addListener('load',function(){
		Ext.getCmp('gPanel_creditRating').getSelectionModel().selectFirstRow() ;
	});
	jStore_creditRating.load();
	var cModel_creditRating = new Ext.grid.ColumnModel(creditRatingModelCfg);
	
	var button_addCreditRating = new Ext.Button({
		text : '新建',
		tooltip : '新建一条新的资信评估',
		iconCls : 'addIcon',
		disabled : bool,
		scope : this,
		handler : function() {
			addCreditRatingEB(projObj.data.id, projObj, jStore_creditRating, '企业');
		}
	});
	
	var button_see = new Ext.Button({
		text : '查看',
		tooltip : '查看资信评估数据',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_creditRating.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				getCreditRatingResult(selected);
			}
		}
	});
	
	var button_deleteCreditRating = new Ext.Button({
		text : '删除',
		tooltip : '删除此条资信评估',
		iconCls : 'deleteIcon',
		disabled : bool,
		scope : this,
		handler : function() {
			var selected = gPanel_creditRating.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : 'deleteCreditRating.action',
							method : 'POST',
							success : function(response) {
								if (response.responseText == '{success:true}') {
									Ext.Msg.alert('状态', '删除成功!');
									jStore_creditRating.removeAll();
									jStore_creditRating.reload();
								} else if(response.responseText == '{success:false}') {
									Ext.Msg.alert('状态','删除失败!');
								} else {
									Ext.Msg.alert('状态','删除失败!');
								}
							},
							failure : function(result, action) {
								Ext.Msg.alert('状态','服务器未响应，删除失败!');
							},
							params: { id: id }
						});
					}
				});
			
			}
		}
	});
	
	var gPanel_creditRating = new Ext.grid.GridPanel({
		id : 'gPanel_creditRating',
		store : jStore_creditRating,
//	 	autoHeight:true,
		colModel : cModel_creditRating,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		plugins : expanderCreditRating,
		viewConfig : {forceFit : true},
//		style : 'margin-top:20px;',
		tbar : ['->', button_deleteCreditRating, button_see, button_addCreditRating],
		listeners : {}
	});
	
	var win = new Ext.Window({
		title : '企业资信评估',
		layout:'fit',
		width : (screen.width-180)*0.5,
		height : (screen.height-180)*0.5,
		closable : true,
		resizable : true,
		frame : true,
		border : false,
		modal : true,
		buttonAlign : 'center',
		items : [gPanel_creditRating]
	}) ;
	win.show() ;
	//---------------------------企业资信评估结束-----------------------------------
}

var carCreditRating = function(projId) {

	Ext.Ajax.request({
		url : 'ajaxQueryCreditCarMessage.action',
		method : 'POST',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			showCarCreditRatingWin(projId, obj.data);
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			projId : projId
		}
	});
	var showCarCreditRatingWin = function (projId, obj) {
		////////////////////////////////开始///////////////////////////////
		var jStore_creditRating = new Ext.data.JsonStore(getCreditRatingStoreCfg(projId));
		
		jStore_creditRating.addListener('load',function(){
			Ext.getCmp('gPanel_creditRating').getSelectionModel().selectFirstRow() ;
		});
		jStore_creditRating.load();
		var cModel_creditRating = new Ext.grid.ColumnModel(creditRatingModelCfg);
		
		var gPanel_creditRating = new Ext.grid.GridPanel({
			id : 'gPanel_creditRating',
			store : jStore_creditRating,
		 	//autoScroll : true,
		 	height : 310,
		 	width : (screen.width-180)*0.75,
		 	
			colModel : cModel_creditRating,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			plugins : expanderCreditRating,
			viewConfig : {forceFit : true},
			//tbar : ['', '->', button_deleteCreditRating, button_addCreditRating],
			listeners : {
				'rowcontextmenu' : function(grid,index,e){
				e.stopEvent() ;
				var selected = grid.getSelectionModel().getSelected();
				var menu = new Ext.menu.Menu({
					items : [{
								text : '新建',
								tooltip : '新建一条新的资信评估',
								iconCls : 'addIcon',
								scope : this,
								handler : function() {
									addCreditRatingCB(projId, obj, jStore_creditRating, '个人');
								}
							},'-',{
								text : '查看',
								tooltip : '查看资信评估数据',
								iconCls : 'addIcon',
								scope : this,
								handler : function() {
									var selected = gPanel_creditRating.getSelectionModel().getSelected();
									if (null == selected) {
										Ext.MessageBox.alert('状态', '请选择一条记录!');
									}else{
										var id = selected.get('id');
										getCreditRatingResult(selected);
									}
								}
							},'-',{
								text : '删除',
								tooltip : '删除此条资信评估',
								iconCls : 'deleteIcon',
								scope : this,
								handler : function() {
									var selected = gPanel_creditRating.getSelectionModel().getSelected();
									if (null == selected) {
										Ext.MessageBox.alert('状态', '请选择一条记录!');
									}else{
										var id = selected.get('id');
										Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
											if (btn == 'yes') {
												Ext.Ajax.request({
													url : 'deleteCreditRating.action',
													method : 'POST',
													success : function(response) {
														if (response.responseText == '{success:true}') {
															Ext.Msg.alert('状态', '删除成功!');
															jStore_creditRating.removeAll();
															jStore_creditRating.reload();
														} else if(response.responseText == '{success:false}') {
															Ext.Msg.alert('状态','删除失败!');
														} else {
															Ext.Msg.alert('状态','删除失败!');
														}
													},
													failure : function(result, action) {
														Ext.Msg.alert('状态','服务器未响应，删除失败!');
													},
													params: { id: id }
												});
											}
										});
									}
								}
							} ]
				});
				menu.showAt(e.getXY());
			},'headercontextmenu' : function(grid,index,e){
				e.stopEvent() ;
				var menu = new Ext.menu.Menu({
					items : [{
									text : '新建',
									tooltip : '新建一条新的资信评估',
									iconCls : 'addIcon',
									scope : this,
									handler : function() {
										addCreditRatingCB(projId, obj, jStore_creditRating, '个人');
									}
							} ]
				});
				menu.showAt(e.getXY());
			}
			}
		});
		/////////////////////结束//////////////////////////////
		var fPanel_addCarMessForm = new Ext.FormPanel({
			id:'beforeCarBalanceSaveForm',
			buttonAlign :'center',
			frame : true ,
			//autoScroll : true ,
			labelAlign : 'right',
			monitorValid: true,
			labelWidth : 125,
			bodyStyle : 'overflowX:hidden',
			bodyStyle:'padding:5px',
			items:[ gPanel_creditRating ]
		});
		
		var window_carProj_node1 = new Ext.Window({
			id : 'window_carProj_node1',
			title : '风险评估表',
			layout : 'fit',
			width : (screen.width-180)*0.77,
			height : 350,
			//autoScroll : true,
			collapsible : true,
			closable : true,
			resizable : true,
			closeAction : 'close',
			border : false,
			modal : true,
			minHeight : 250, // resizable为true有效
			minWidth : 500,// resizable为true有效
			items : [ 
			         new Ext.Panel( {
			        	// autoScroll : true ,
				    	 items : [fPanel_addCarMessForm]
					})
				],
			listeners : {
				'hide' : function() {
					fPanel_addCarMessForm.getForm().reset();
				}
			}
		});
		window_carProj_node1.show();
	}
	
}
