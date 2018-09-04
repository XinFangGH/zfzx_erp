
var selectCarList = function (funName){
	var anchor = '90%';
	jStoreCar = new Ext.data.JsonStore( {
		url : __ctxPath +'/credit/customer/carMessage/queryAllCarListMessage.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {
			name : 'id' 
		},{
			name : 'carNumber'
		},{
			name : 'carFirm'
		}, {
			name : 'carStyle'
		},{
			name : 'carModel'
		}, {
			name : 'carProduce'
		}, {
			name : 'displacement'
		}, {
			name : 'configuration'
		},{
			name : 'displacementValue'
		},{
			name : 'seatingValue'
		},{
			name : 'carFirmName'
		},{
			name : 'carStyleName'
		},{
			name : 'carModelName'
		},{
			name : 'seating'
		} ]
	});
	jStoreCar.load({
		params : {
			start : 0,
			limit : 20
		}
	});

	var button_add = new Ext.Button({
		text : '增加',
		tooltip : '增加一条新的汽车',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var addCarMessageWin = new Ext.Window({
				id : 'addAttachfileWin',
				title: '新增车辆信息',
				layout : 'fit',
				width : 600,
				height :250,
				closable : true,
				resizable : true,
				plain : true,
				collapsible : true,
				border : false,
				modal : true,
				buttonAlign: 'right',
				bodyStyle : 'padding: 5',
				items : [
						new Ext.form.FormPanel( {
							id : 'fPanel_addCar',
							url : __ctxPath +'/credit/customer/carMessage/addCarMessage.do',
							labelAlign : 'right',
							frame : true,
							layout : 'column',
							buttonAlign : 'center',
							height : 300,
							monitorValid : true,
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : .5
							},
							items : [ {
								items : [ {
									xtype : 'textfield',
									fieldLabel : '车型编号',
									name : 'processCar.carNumber',
									//allowBlank : false,
									//blankText : '必填信息',
									width : anchor
								},{
									id : 'carsmodelname',
									xtype : 'textfield',
									fieldLabel : '车型',
									//name : 'carsmodelname',
									readOnly  : true,
									cls : 'readOnlyClass',
									width : anchor
								},{
									id : 'processPersonCarAssurecar.carsmodel',
									xtype : 'hidden',
									fieldLabel : '车型ID',
									name : 'processCar.carModel'
								},{
									xtype : "dickeycombo",
									nodeKey : 'pl',
									hiddenName : "processCar.displacement",
									fieldLabel : "排量",
									itemName : '排量', // xx代表分类名称
									allowBlank : false,
								    blankText : '必填信息',
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox
														.getValue());
												combox.clearInvalid();
											})
										}
									}
								},{
									xtype : "dickeycombo",
									nodeKey : 'zws',
									hiddenName : "processCar.seating",
									fieldLabel : "座位数",
									itemName : '座位数', // xx代表分类名称
									allowBlank : false,
								    blankText : '必填信息',
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox
														.getValue());
												combox.clearInvalid();
											})
										}
									}
								}]
							}, {
								defaults : {
									anchor : anchor
								},
								items : [ {
									id : 'carsfirmname',
									xtype : 'trigger',
									triggerClass :'x-form-search-trigger',
									fieldLabel : '车辆制造商',
									onTriggerClick : function(){
										selectDictionary('car',getMainAssureCarArray);	
									},
									emptyText : '点击选择车辆厂商',
									editable : false
								},{
									id : 'processPersonCarAssurecar.carsfirm',
					            	xtype : 'hidden',
									fieldLabel : '车辆制造商（品牌）主键',
									name : 'processCar.carFirm'
								},{
									id : 'carsstylename',
									xtype : 'textfield',
									fieldLabel : '车系',
									//name : 'carsstylename',
									readOnly  : true,
									cls : 'readOnlyClass',
									width : anchor
								},{
									id : 'processPersonCarAssurecar.carsstyle',
									xtype : 'hidden',
									fieldLabel : '车系ID',
									name : 'processCar.carStyle'
								},{
									xtype : 'textfield',
									fieldLabel : '配置',
									name : 'processCar.configuration',
									allowBlank : false,
								    blankText : '必填信息',
									width : anchor
								},{
									xtype : 'textfield',
									fieldLabel : '车辆产地',
									name : 'processCar.carProduce',
									allowBlank : false,
								    blankText : '必填信息',
									width : anchor
								}]
							}],// items
							 buttons : [{
									text : '提交',
									formBind : true,
									iconCls : 'submitIcon',
									handler : function() {
										Ext.getCmp('fPanel_addCar').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function() {
											Ext.ux.Toast.msg('状态','添加成功');
											jStoreCar.reload();
											addCarMessageWin.destroy();
										
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '添加失败!');
												// top.getForm().reset();
											}
										});
									}
								}, {
									text : '取消',
									iconCls : 'cancelIcon',
									handler : function() {
										addCarMessageWin.destroy();
									}
								}]
						})
				 ]
			}).show();
		
		}
	});
	
	//jiang
	var function_update = function(id){
		/*异步加载项目信息*/
		Ext.Ajax.request({
		  url: __ctxPath +'/credit/customer/carMessage/getCarInfo.do',
		  method: 'POST',
		  success: function(response) {
			  var obj = Ext.util.JSON.decode(response.responseText);
			  if(obj){
				showWin(obj);
			  }
		  },
		  params:{
		  	id : id
		  }
		});
		
		var showWin =function(obj){
			var updateCarMessageWin = new Ext.Window({
				id : 'updateCarMessageWin',
				title: '修改车辆信息',
				layout : 'fit',
				width : 600,
				height :250,
				closable : true,
				resizable : true,
				plain : true,
				collapsible : true,
				border : false,
				modal : true,
				buttonAlign: 'center',
				bodyStyle : 'padding: 5',
				items : [
						new Ext.form.FormPanel( {
							id : 'fPanel_updateCar',
							url : __ctxPath +'/credit/customer/carMessage/updateCarMessage.do',
							labelAlign : 'right',
							frame : true,
							layout : 'column',
							buttonAlign : 'center',
							height : 300,
							monitorValid : true,
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : .5
							},
							items : [ {
								items : [ {
									xtype : 'textfield',
									fieldLabel : '车型编号',
									name : 'processCar.carNumber',
									//allowBlank : false,
									//allowBlank : false,
									//blankText : '必填信息',
									width : anchor,
									value : obj.data.carNumber
								},{
									xtype : 'hidden',
									name : 'processCar.id',
									value : obj.data.id
								},{
									id : 'carsmodelname4',
									xtype : 'textfield',
									fieldLabel : '车型',
									name : 'carsmodelname',
									readOnly  : true,
									cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.carModelName
								},{
									id : 'processPersonCarAssurecar.carsmodel4',
									xtype : 'hidden',
									fieldLabel : '车型ID',
									name : 'processCar.carModel',
									value : obj.data.carModel
								},{
									xtype : "dickeycombo",
									nodeKey :'pl',
									hiddenName : "processCar.displacement",
									value : obj.data.displacement,
									fieldLabel : "排量",
									editable :false,
									itemName : '排量', // xx代表分类名称
									allowBlank : false,
								    blankText : '必填信息',
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if(combox.getValue() == 0||combox.getValue()==1){
													combox.setValue("");
												}else{
													combox.setValue(combox
														.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								},{
									xtype : "dickeycombo",
									nodeKey :'zws',
									hiddenName : "processCar.seating",
									value : obj.data.seating,
									fieldLabel : "座位数",
									editable :false,
									itemName : '座位数', // xx代表分类名称
									allowBlank : false,
								    blankText : '必填信息',
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if(combox.getValue() == 0||combox.getValue()==1){
													combox.setValue("");
												}else{
													combox.setValue(combox
														.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								}]
							}, {
								defaults : {
										anchor : anchor
								},
								items : [ {
									id : 'carsfirmname4',
									xtype : 'trigger',
									triggerClass :'x-form-search-trigger',
									fieldLabel : '车辆制造商',
									onTriggerClick : function(){
										selectDictionary('car',getUpdateMainAssureCarArray);
									},
									emptyText : '点击选择车辆厂商',
									editable : false,
									name : 'carsfirmname',
									value : obj.data.carFirmName
								},{
									id : 'processPersonCarAssurecar.carsfirm4',
									xtype : 'hidden',
									fieldLabel : '车辆制造商（品牌）主键',
									name : 'processCar.carFirm',
									value : obj.data.carFirm
								},{
									id : 'carsstylename4',
									xtype : 'textfield',
									fieldLabel : '车系',
									name : 'carsstylename',
									readOnly  : true,
									cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.carStyleName
								},{
									xtype : 'textfield',
									fieldLabel : '配置',
									name : 'processCar.configuration',
									allowBlank : false,
									blankText : '必填信息',
									width : anchor,
									value : obj.data.configuration
								},{
									xtype : 'textfield',
									fieldLabel : '车辆产地',
									name : 'processCar.carProduce',
									allowBlank : false,
									blankText : '必填信息',
									width : anchor,
									value : obj.data.carProduce
								}]
							}, {
								items : [ {
									id : 'processPersonCarAssurecar.carsstyle4',
									xtype : 'hidden',
									fieldLabel : '车系ID',
									name : 'processCar.carStyle',
									value : obj.data.carStyle
								}]
							}]// items
						})
				 ],
				  buttons : [{
					text : '提交',
					iconCls : 'submitIcon',
					formBind : true,
					handler : function() {
						Ext.getCmp('fPanel_updateCar').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function() {
							Ext.ux.Toast.msg('状态','修改成功');
							jStoreCar.reload();
							updateCarMessageWin.destroy();
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '修改失败!');
								// top.getForm().reset();
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'cancelIcon',
					handler : function() {
						updateCarMessageWin.destroy();
					}
				}]
			}).show();
		}//showWin
		
		
	}
	
	
	var button_update = new Ext.Button({
		text : '修改',
		tooltip : '修改选中的车辆信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanelCar.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				function_update(id);
			}
		
		}
	});
	
	var button_delete = new Ext.Button({
		text : '删除',
//		tooltip : '删除信息',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanelCar.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					  url: __ctxPath +'/credit/customer/carMessage/deleteCarMessage.do',
					  method: 'POST',
					  success: function(response) {
					Ext.ux.Toast.msg('状态','删除成功');
						  jStoreCar.reload();
					  },
					  params:{
					  	id : id
					  }
					});
			}
		}
	});
	
	//jiang
	var function_see = function(id){
		/*异步加载项目信息*/
		Ext.Ajax.request({
		  url: __ctxPath +'/credit/customer/carMessage/getCarInfo.do',
		  method: 'POST',
		  success: function(response) {
			  var obj = Ext.util.JSON.decode(response.responseText);
			  if(obj){
				showWin(obj);
			  }
		  },
		  params:{
		  	id : id
		  }
		});
		
		var showWin =function(obj){
			
			var seeCarMessageWin = new Ext.Window({
				id : 'seeCarMessageWin',
				title: '查看车辆信息',
				layout : 'fit',
				width : 600,
				height :250,
				closable : true,
				resizable : true,
				plain : true,
				collapsible : true,
				border : false,
				modal : true,
				buttonAlign: 'center',
				bodyStyle : 'padding: 5',
				items : [
						new Ext.form.FormPanel( {
							id : 'fPanel_seeCar',
							url : __ctxPath +'/credit/customer/carMessage/updateCarMessage.do',
							labelAlign : 'right',
							frame : true,
							layout : 'column',
//							buttonAlign : 'center',
							height : 300,
							monitorValid : true,
							defaults : {
								layout : 'form',
								border : false,
								columnWidth : .5
							},
							items : [ {
								items : [ {
									xtype : 'textfield',
									fieldLabel : '车型编号',
									name : 'processCar.carNumber',
									width : anchor,
									readOnly : true,
	  								cls : 'readOnlyClass',
									value : obj.data.carNumber
								},{
									xtype : 'textfield',
									fieldLabel : '车型',
									name : 'carsmodelname',
									readOnly : true,
	  								cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.carModelName
								},{
									xtype : 'textfield',
									fieldLabel : '排量',
									name : 'processCar.displacement',
									width : anchor,
									readOnly : true,
	  								cls : 'readOnlyClass',
									value : obj.data.displacementValue
								},{
									xtype : 'textfield',
									fieldLabel : '座位数',
									name : 'processCar.seating',
									width : anchor,
									readOnly : true,
	  								cls : 'readOnlyClass',
									value : obj.data.seatingValue
								}]
							}, {
								items : [ {
									xtype : 'textfield',
									fieldLabel : '车辆制造商',
									name : 'carsfirmname',
									readOnly : true,
	  								cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.carFirmName
								},{
									xtype : 'textfield',
									fieldLabel : '车系',
									name : 'carsstylename',
									width : anchor,
									readOnly : true,
	  								cls : 'readOnlyClass',
									value : obj.data.carStyleName
								},{
									xtype : 'textfield',
									fieldLabel : '配置',
									name : 'processCar.configuration',
									readOnly : true,
	  								cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.configuration
								},{
									xtype : 'textfield',
									fieldLabel : '车辆产地',
									name : 'processCar.carProduce',
									readOnly : true,
	  								cls : 'readOnlyClass',
									width : anchor,
									value : obj.data.carProduce
								}]
							}]// items
							
						})
				 ],
				  buttons : [{
						text : '关闭',
						iconCls : 'close',
						handler : function() {
							seeCarMessageWin.destroy();
						}
					}]
			}).show();
		}//showWin
		
		
	}//function_see
	
	var button_see = new Ext.Button({
		text : '查看',
		tooltip : '查看选中的车辆信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			var selected = gPanelCar.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				function_see(id);
			}
		
		}
	});
	
	
	/*查询条件	jiang*/
	var src_Num = new Ext.form.TextField({
		id : 'src_Num',
		name : 'src_Num',
		width : 80,
		emptyText : '车型编号',
		hideLabel : true
	});
	
	var src_carFirmName = new Ext.form.TextField({
		id : 'src_carFirmName',
		name : 'src_carFirmName',
		width : 80,
		emptyText : '品牌',
		hideLabel : true
	});
	
	var searchByCondition = function() {
		//alert(processType.getValue());
		jStoreCar.baseParams.src_Num = src_Num.getValue();
		jStoreCar.baseParams.src_carFirmName = src_carFirmName.getValue();
		jStoreCar.load({
			params : {
				start : 0,
				limit : 20
			}
		});
	}
	
	var button_search = new Ext.Button({
		text : '查找',
		tooltip : '根据条件查找结果',
		iconCls : 'searchIcon',
		scope : this,
		handler : function() {
				searchByCondition();
			}//end of function
	});
	
	
	var cModelAttachfile = new Ext.grid.ColumnModel(
			[
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 30
				}),
				{
					header : "车型编号",
					width : 100,
					sortable : true,
				    dataIndex : 'carNumber'
				}, {
					header : "车辆厂商",
					width : 80,
					sortable : true,
					dataIndex : 'carFirmName'
				},{
					header : "车系",
					width : 80,
					sortable : true,
					dataIndex : 'carStyleName'
				},{
					header : "车型",
					width : 100,
					sortable : true,
					dataIndex : 'carModelName'
				},{
					id : 'id_autoExpandColumn',
					header : "车辆产地",
					width : 80,
					sortable : true,
					dataIndex : 'carProduce'
				}
				]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStoreCar,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});

	var gPanelCar = new Ext.grid.GridPanel( {
		id : 'gPanelCar',
		store : jStoreCar,
		colModel : cModelAttachfile,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		autoExpandColumn : 'id_autoExpandColumn',
		loadMask : myMask,
		bbar : pagingBar,
		tbar : [button_add,button_update,button_see,button_delete,'->','-',{text : '车型编号:'},src_Num,{text : '品牌:'},src_carFirmName,button_search],
		listeners : {
			'rowdblclick' : function(grid, index, e) {
				var selected = grid.getStore().getAt(index) ;
				var id = selected.get('id');
				callbackFun(selected,funName);
				winCarListRefer.destroy();
			}
		}
	});

	var winCarListRefer = new Ext.Window({
		    id : 'winCarListRefer',
			title : '汽车列表',
			collapsible : true,
			width : 600,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gPanelCar],
			modal : true 
		}).show();
		
		//页签    汽车的树  增加
var getMainAssureCarArray = function(objArray){
	var i=objArray.length;
	if(i==2){
		//alert("carsstylename(车系名称)="+objArray[0].text+"   carsstyle(车系id)="+objArray[0].id+"    carsmodelname(车型名称)="+objArray[1].text+"    carsmodel(车型id)="+carsmodel+"    carsfirmname车辆制造商(品牌)="+objArray[1].text+"    carsfirm车辆制造商(品牌)id="+objArray[1].id);
		Ext.getCmp('carsstylename').setValue( objArray[0].text );
		Ext.getCmp('processPersonCarAssurecar.carsstyle').setValue( objArray[0].id );
		Ext.getCmp('carsmodelname').setValue( objArray[1].text );
		Ext.getCmp('processPersonCarAssurecar.carsmodel').setValue( objArray[1].id );
		Ext.getCmp('carsfirmname').setValue( objArray[1].text );
		Ext.getCmp('processPersonCarAssurecar.carsfirm').setValue( objArray[1].id );
	}else{
		//alert("carsstylename(车系名称)="+objArray[0].text+"   carsstyle(车系id)="+objArray[0].id+"    carsmodelname(车型名称)="+objArray[1].text+"    carsmodel(车型id)="+objArray[1].id+"    carsfirmname车辆制造商(品牌)="+objArray[2].text+"    carsfirm车辆制造商(品牌)id="+objArray[2].id);
		//alert( objArray[0].text+objArray[0].id+objArray[1].text+objArray[1].id+objArray[2].text+objArray[2].id);
		Ext.getCmp('carsstylename').setValue( objArray[0].text );
		Ext.getCmp('processPersonCarAssurecar.carsstyle').setValue( objArray[0].id );
		Ext.getCmp('carsmodelname').setValue( objArray[1].text );
		Ext.getCmp('processPersonCarAssurecar.carsmodel').setValue( objArray[1].id );
		Ext.getCmp('carsfirmname').setValue( objArray[2].text );
		Ext.getCmp('processPersonCarAssurecar.carsfirm').setValue( objArray[2].id );
	}
}

//页签    汽车的树  修改
var getUpdateMainAssureCarArray = function(objArray){
	var i=objArray.length;
	if(i==2){
		Ext.getCmp('carsstylename4').setValue( objArray[0].text );
		Ext.getCmp('processPersonCarAssurecar.carsstyle4').setValue( objArray[0].id );k
		Ext.getCmp('processPersonCarAssurecar.carsmodel4').setValue( objArray[1].id );
		Ext.getCmp('carsfirmname4').setValue( objArray[1].text );
		Ext.getCmp('processPersonCarAssurecar.carsfirm4').setValue( objArray[1].id );
	}else{
		Ext.getCmp('carsmodelname4').setValue( objArray[0].text );
		Ext.getCmp('processPersonCarAssurecar.carsmodel4').setValue( objArray[0].id );
		Ext.getCmp('carsstylename4').setValue( objArray[1].text );
		Ext.getCmp('processPersonCarAssurecar.carsstyle4').setValue( objArray[1].id );
		Ext.getCmp('carsfirmname4').setValue( objArray[2].text );
		Ext.getCmp('processPersonCarAssurecar.carsfirm4').setValue( objArray[2].id );
	}
}
	
	var callbackFun = function(selected,funName){
		carJsonObj = {
					id : selected.get('id'),
					carNumber : selected.get('carNumber'),
					carFirm : selected.get('carFirm'),
					carStyle : selected.get('carStyle'),
					carModel : selected.get('carModel'),
					carProduce : selected.get('carProduce'),
					//vin : selected.get('vin'),
					//engineNo : selected.get('engineNo'),
					displacement : selected.get('displacement'),
					configuration : selected.get('configuration'),
					displacementValue : selected.get('displacementValue'),
					seatingValue : selected.get('seatingValue'),
					carFirmName : selected.get('carFirmName'),
					carStyleName : selected.get('carStyleName'),
					carModelName : selected.get('carModelName'),
					seating : selected.get('seating')
				}
		funName(carJsonObj);
	}
	
}