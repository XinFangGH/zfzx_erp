/*-------选项列表start---------------------------------------------------------------------------------*/
var start = 0 ;
var pageSize = 15 ;
var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
var innerPanelWidth = bodyWidth-6 ;/** 暂时未用到，调整窗体大小使用*/
var defaultLabelWidth = 120 ;//默认标签的宽度
var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
var fieldWidth = 150 ;
var root = 'topics' ;
var totalProperty = 'totalProperty' ;
var templateIndicatorId;
	
	var button_addOption = new Ext.Button({
		text : '增加',
		tooltip : '增加一条新的选项',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var fPanel_addOption = new Ext.FormPanel({
			url:__ctxPath+'/creditFlow/creditmanagement/addTemplateOption.do',
			labelAlign : 'left',
			buttonAlign : 'center',
			bodyStyle : 'padding:5px;',
			height : 100,
			frame : true,
			labelWidth : defaultLabelWidth,
			monitorValid : true,
			items : [ {
				layout : 'column',
				border : false,
				labelSeparator : ':',
				defaults : {
					layout : 'form',
					border : false,
					columnWidth : .5
				},
				items : [ {
					items : [{
						xtype : 'textfield',
						fieldLabel : '选项号',
						name : 'templateOption.sortNo',
						width : defaultTextfieldWidth,
						allowBlank : false,
						blankText : '必填信息'
					} ]
					},{
						items : [ {
							xtype : 'hidden',
							name : 'templateOption.indicatorId',
							value : templateIndicatorId
						},{
							xtype : 'textfield',
							fieldLabel : '选项分值',
							name : 'templateOption.score',
							width : defaultTextfieldWidth,
							allowBlank : false,
							blankText : '必填信息'
						} ]
					}]// items
				},{
					layout : 'column',
					border : false,
					labelSeparator : ':',
					defaults : {
						layout : 'form',
						border : false,
						columnWidth : 1
					},
					items : [ {
						items : [{
							xtype : 'textfield',
							fieldLabel : '选项名称',
							name : 'templateOption.optionName',
							width : 427,
							allowBlank : false,
							blankText : '必填信息'
						} ]
					}]// items
			} ],
			
			buttons : [ {
				text : '提交',
				formBind : true,
				handler : function() {
					fPanel_addOption.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function() {
							Ext.ux.Toast.msg('状态','添加成功')
							jStore_option.removeAll();
							jStore_option.reload({
								params : {
									templateIndicatorId : templateIndicatorId
								}
							});
							window_addOption.destroy();
						
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态','添加失败!');
							}
						});
					}
				},{
					text : '取消',
					handler : function(){
						window_addOption.destroy();
					}
				} ]
			});
							
			var window_addOption = new Ext.Window({
				id : 'w_addOption',
				title: '新增选项',
				layout : 'fit',
				width : 600,
				height : 200,
				x : (Ext.getBody().getWidth()-600)/2,
				y : 20,
				closable : true,
				resizable : true,
				plain : true,
				border : false,
				modal : true,
				buttonAlign: 'right',
				minHeight: 250,	//resizable为true有效	        
				minWidth: 500,//resizable为true有效
				bodyStyle : 'padding: 5',
				items : [fPanel_addOption],
		        manager:this.windowGroup,
		        listeners : {
		        	
		        }
			});
			window_addOption.show();
		}
	});
	
	var button_updateOption = new Ext.Button({
		text : '修改',
		tooltip : '修改一条选项',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_option.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				updateOption(selected);
			}
		}
	});
	
	var updateOption = function(selected) {
		var id = selected.get('id');
		var sortNo = selected.get('sortNo');
		var optionName = selected.get('optionName');
		var score = selected.get('score');
		
		var fPanel_updateOption = new Ext.FormPanel({
		url:__ctxPath+'/creditFlow/creditmanagement/updateTemplateOption.do',
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 100,
		frame : true,
		labelWidth : 100,
		monitorValid : true,
		items : [ {
			layout : 'column',
			border : false,
			labelSeparator : ':',
			defaults : {
				layout : 'form',
				border : false,
				columnWidth : .5
			},
			items : [ {
				items : [{
					xtype : 'textfield',
					fieldLabel : '选项号',
					name : 'templateOption.sortNo',
					width : defaultTextfieldWidth,
					allowBlank : false,
					blankText : '必填信息',
					value : sortNo
				} ]
				},{
					items : [ {
						xtype : 'hidden',
						name : 'templateOption.indicatorId',
						value : templateIndicatorId
					},{
						xtype : 'textfield',
						fieldLabel : '选项分值',
						name : 'templateOption.score',
						width : defaultTextfieldWidth,
						allowBlank : false,
						blankText : '必填信息',
						value : score
					} ]
				}]// items
			},{
				layout : 'column',
				border : false,
				labelSeparator : ':',
				defaults : {
					layout : 'form',
					border : false,
					columnWidth : 1
				},
				items : [ {
					items : [{
						xtype : 'textfield',
						fieldLabel : '选项名称',
						name : 'templateOption.optionName',
						width : 427,
						allowBlank : false,
						blankText : '必填信息',
						value : optionName
					},{
						xtype : 'hidden',
						name : 'templateOption.id',
						value : id
					} ]
				}]// items
		} ],
		
		buttons : [ {
			text : '提交',
			iconCls : 'submitIcon',
			formBind : true,
			handler : function() {
				fPanel_updateOption.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function() {
						Ext.ux.Toast.msg('状态', '修改成功!')
						jStore_option.removeAll();
						jStore_option.reload({
							params : {
								templateIndicatorId : templateIndicatorId
							}
						});
						window_updateOption.destroy();
						
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态','添加失败!');
						}
					});
				}
			},{
				text : '取消',
				handler : function(){
					window_updateOption.destroy();
				}
			} ]
		});
						
		var window_updateOption = new Ext.Window({
			id : 'w_addOption',
			title: '修改选项',
			layout : 'fit',
			width : 600,
			height : 200,
			x : (Ext.getBody().getWidth()-600)/2,
			y : 20,
			closable : true,
			resizable : true,
			plain : true,
			border : false,
			modal : true,
			buttonAlign: 'right',
			minHeight: 250,	//resizable为true有效	        
			minWidth: 500,//resizable为true有效
			bodyStyle : 'padding: 5',
			items : [fPanel_updateOption],
	        manager:this.windowGroup,
	        listeners : {
	        	
	        }
		});
		window_updateOption.show();
	}
	
	var button_deleteOption = new Ext.Button({
		text : '删除选项',
		tooltip : '删除选中的指标选项',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_option.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.MessageBox.confirm('确认删除', '是否确认执行删除 '
						+ selected.get('optionName'), function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/creditmanagement/deleteRsTemplateOption.do',
							method : 'POST',
							success : function(response) {
								if (response.responseText == '{success:true}') {
									Ext.ux.Toast.msg('状态', '删除成功!');
									jStore_option.removeAll();
									jStore_option.reload({
										params : {
											templateIndicatorId : templateIndicatorId
										}
									});
								} else if(response.responseText == '{success:false}') {
									Ext.ux.Toast.msg('状态','删除失败!');
								} else {
									//Ext.Msg.alert('状态','权限不足，删除失败!');
									Ext.ux.Toast.msg('状态','删除失败!');
								}
							},
							failure : function(result, action) {
								Ext.ux.Toast.msg('状态','服务器未响应，删除失败!');
							},
							params: { id: id }
						});
					}
				});
			}
		}
	});
	
	
	var jStore_option = new Ext.data.JsonStore({
			url : __ctxPath+'/creditFlow/creditmanagement/tpListTemplateOption.do',
			totalProperty : 15,
			root : 'topics',
			fields : [{
						name : 'id'
					},{
						name : 'sortNo'
					}, {
						name : 'optionName'
					}, {
						name : 'score'
					}]
		});



		var cModel_option = new Ext.grid.ColumnModel([{
					header : "序号",
					width : 50,
					sortable : true,
					dataIndex : 'sortNo'
				}, {
					header : "选项",
					width : 300,
					sortable : true,
					dataIndex : 'optionName'
				}, {
					header : "分值",
					width : 40,
					sortable : true,
					dataIndex : 'score'
				}]);

		var gPanel_option = new Ext.grid.GridPanel({
			id : 'gPanel_option',
			store : jStore_option,
			colModel : cModel_option,
			selModel : new Ext.grid.RowSelectionModel(),
			autoExpandColumn : 2,
			stripeRows : true,
			tbar : [button_addOption, button_updateOption, button_deleteOption]

		});

		var win_option = new Ext.Window({
			id : 'win_option',
			title : '编辑指标选项',
			layout : 'fit',
			width : 410,
			height : 220,
			autoScroll : false,
			x : (Ext.getBody().getWidth() - 600) / 2,
			y : 20,
			closable : true,
			resizable : true,
			plain : true,
			closeAction : 'hide',
			border : false,
			modal : true,
			buttonAlign : 'right',
			minHeight : 250, //resizable为true有效	        
			minWidth : 500,//resizable为true有效
			bodyStyle : 'padding: 5',
			items : [gPanel_option],
			manager : this.windowGroup,
	        listeners : {
	        	'hide' : function() {
	        		store.load({
						params : {
							id : templateId
						}
					});
	        	}
	        }
		});
/*var jStore_option = new Ext.data.JsonStore({
			url : 'optionList.action',
			totalProperty : 'totalProperty',
			root : 'topics',
			fields : [{
						name : 'sortNo'
					}, {
						name : 'optionName'
					}, {
						name : 'score'
					}]
		});



var cModel_option = new Ext.grid.ColumnModel([{
					header : "序号",
					width : 100,
					sortable : true,
					dataIndex : 'sortNo'
				}, {
					header : "选项",
					width : 100,
					sortable : true,
					dataIndex : 'optionName'
				}, {
					header : "分值",
					width : 100,
					sortable : true,
					dataIndex : 'score'
				}]);

var gPanel_option = new Ext.grid.GridPanel({
			id : 'gPanel_option',
			store : jStore_option,
			colModel : cModel_option,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			tbar : [button_addOption]

		});

var win_option = new Ext.Window({
			id : 'win_option',
			title : '编辑指标选项',
			layout : 'fit',
			width : 500,
			height : 300,
			//autoScroll : true ,
			x : (Ext.getBody().getWidth() - 600) / 2,
			y : 20,
			closable : true,
			resizable : true,
			plain : true,
			closeAction : 'hide',
			border : false,
			modal : true,
			buttonAlign : 'right',
			minHeight : 250, //resizable为true有效	        
			minWidth : 500,//resizable为true有效
			bodyStyle : 'padding: 5',
			items : [gPanel_option],
			manager : this.windowGroup
		});*/