/**
 * 新增指标界面
 * 
 */
IndicatorGrid = Ext.extend(Ext.Window, {
	constructor : function(_cfg) {
		if (_cfg != null) {
			Ext.apply(this, _cfg);
		}
		this.initUIComponents();
		IndicatorGrid.superclass.constructor.call(this, {
					id : "qwer",
					items : this.formpanel,
					title : this.title,
					layout : 'fit',
					width : 600,
					height : 400,
					x : (Ext.getBody().getWidth() - 600) / 2,
					y : 20,
					closable : true,
					resizable : true,
					plain : true,
					border : false,
					modal : true,
					buttonAlign : 'right',
					minHeight : 250, // resizable为true有效
					minWidth : 500,// resizable为true有效
					bodyStyle : 'padding: 5'
				});
	},

	initUIComponents : function() {
		var restore = this.restore;
		var obj = this.obj;
		var model = (this.model == null ? "2" : this.model);// 0 添加模式 1 修改模式 2
		// 查询模式 ，根据模式产生差异
		var subtitle = "提交";
		var indicatorType = this.indicatorType;
		var indicatorTypeId = this.indicatorTypeId;
		var isedit = false;
		var isadd = false;

		if (model == "0") {
			var ysRead = true;
			var nameRead = false;
			var infoRead = false;
			this.title = "添加新指标";
			isadd = true;
		} else if (model == "1") {
			var ysRead = true;
			var nameRead = true;
			var infoRead = false;
			this.title = "修改指标";
			subtitle = "修改"
			isedit = true;
		} else {
			var ysRead = true;
			var nameRead = true;
			var infoRead = true;
			this.title = "查看指标";

		}
		// 初始化所需要参数
		id = this.id;
		var gPanel_option = new OptionList1({
					isedit : (isadd || isedit)
				});
		if (id != null && typeof(id) != "undefined") {
			gPanel_option.getCmpByName("gPanel_option").getStore().baseParams['indicatorId'] = id
			gPanel_option.getCmpByName("gPanel_option").getStore().load()
		}
		var p_item = {
			id : "",
			typeId : "",
			indicatorName : "",
			indicatorCombo : "", // 指标
			indicatorDesc : "",
			option : ""
		};

		var p_button = {
			subMethod : "",
			button : ""
		}

		p_item.id = {
			id : 'indicator.id',
			xtype : 'textfield',
			name : 'indicator.id',
			value : obj == null ? '' : obj.data.id
		};
		p_item.typeId = {
			id : 'indicator.indicatorTypeId',
			xtype : 'textfield',
			name : 'indicator.indicatorTypeId',
			value : indicatorTypeId
		};
		p_item.indicatorName = {
			xtype : 'textfield',
			fieldLabel : '要素名称',
			name : 'indicator.indicatorType',
			value : obj == null ? '' : obj.data.indicatorType,
			readOnly : ysRead
		};
		p_item.indicatorCombo = {
			id : "c_score",
			xtype : 'combo',
			triggerClass : 'x-form-search-trigger',
			fieldLabel : '指标名称',
			width : 300,
			name : 'indicator.indicatorName',
			value : obj == null ? '' : obj.data.indicatorName,
			allowBlank : false,
			blankText : '必填信息',
			resizable : true,
			onTriggerClick : function() {
				seeMakeScore();
			}
		};
		p_item.indicatorDesc = {
			xtype : 'textarea',
			fieldLabel : '指标说明',
			name : 'indicator.indicatorDesc',
			width : 300,
			readOnly : infoRead,
			value : obj == null ? '' : obj.data.indicatorDesc
		};
		p_item.option = {
			xtype : 'fieldset',
			title : '指标选项',
			items : [gPanel_option]

		};
		var vail = function() {
			var datas = Ext.getCmp("gPanel_option").store.data.items;
			if (datas != null) {
				for (var i = 0; i < datas.length; i++) {
					for (var j = i + 1; j < datas.length; j++) {
						if (datas[i].data.score == datas[j].data.score) {
							Ext.Msg.alert('Status', '分数重了，重写');
							return;
						}
						if (datas[i].data.optionName == datas[j].data.optionName) {
							Ext.Msg.alert('Status', '指标重了，重写');
							return;
						}
					}

				};
			}

			var vDates = getOptionGridDate(Ext.getCmp("gPanel_option"));
			return vDates;
		}

		p_button.subMethod = function(v) {

			if (v == "save") {
				var a = function() {
					vDates = vail();
					formpanle.getForm().submit({
						url : __ctxPath
								+ '/creditFlow/creditmanagement/addIndicator.do',
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						params : {
							optionStr : vDates
						},
						success : function() {
							Ext.ux.Toast.msg('状态', '添加成功!');
							restore.removeAll();
							restore.reload();
							Ext.getCmp("qwer").destroy();

						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态', '添加失败!');
						}
					});
				}
				return a;
			} else if (v == "update") {
				var b = function() {
					vDates = vail();
					formpanle.getForm().submit({
						url : __ctxPath
								+ '/creditFlow/creditmanagement/updateIndicator.do',
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						params : {
							optionStr : vDates
						},
						success : function() {
							Ext.ux.Toast.msg('状态', '修改成功!');
							Ext.getCmp("grid_json").removeAll();
							Ext.getCmp("grid_json").reload();
							Ext.getCmp("qwer").destroy();

						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态', '修改失败!');
						}
					});
				}
				return b;

			}
		}

		p_button.button = {
			text : isadd ? '提交' : '修改',
			iconCls : 'submitIcon',
			formBind : true,
			handler : isadd ? p_button.subMethod("save") : p_button
					.subMethod("update")

		}
		var formpanle = new Ext.FormPanel({
					labelAlign : 'right',
					buttonAlign : 'center',
					height : 100,
					frame : true,
					autoScroll : true,
					labelWidth : 80,
					monitorValid : true,
					items : [{
						layout : 'column',
						border : false,
						labelSeparator : ':',
						defaults : {
							layout : 'form',
							border : false,
							columnWidth : 1
						},
						items : [{
							items : [p_item.id, p_item.typeId,
									p_item.indicatorName,
									p_item.indicatorCombo,
									p_item.indicatorDesc, p_item.option]
						}]
					}],
					buttons : [p_button.button]
				});

		this.formpanel = formpanle;
	}

});

seeMakeScore = function() {
	var form = getMyForm();
	var win = new Ext.Window({
				id : "c_win",
				layout : 'fit',
				maximizable : false,// 窗口最大化
				minimizable : false,
				resizable : false,
				width : 380,
				height : 374,
				resizable : true,
				modal : true,
				closable : true,
				maximizable : true,
				minimizable : true,
				buttonAlign : 'center',
				items : form
			});
	win.show();
}

getMyForm = function() {
	var jsonstore = new Ext.data.JsonStore({
				url : __ctxPath
						+ '/credit/indicator/getSubjectForJson_makeScore.do',
				root : "root",
				fields : ['name', 'type']
			});
	jsonstore.load();

	// 定义列
	var column = new Ext.grid.ColumnModel({
				columns : [new Ext.grid.RowNumberer({
									header : "序号",
									width : 40,
									dataIndex : ''
								}), {
							header : '指标',
							dataIndex : 'name'
						}, {
							header : '适用单位',
							dataIndex : 'type'
						}, {
							header : '分类模板',
							dataIndex : 'ptype'
						}]
			});

	var pager = new Ext.PagingToolbar({
				pageSize : 10,
				store : jsonstore,
				listeners : {
					"beforechange" : function(bbar, params) {
						var grid = bbar.ownerCt;
						var store = grid.getStore();
						var start = params.start;
						var limit = params.limit;
						return false;
					}
				}
			});

	var dbc = function(grid, rowIndex, e) {
		var name = grid.getSelectionModel().getSelected().get('name');
		Ext.getCmp("c_score").setValue(name);
		Ext.getCmp("c_win").destroy();
	}
	var grid = new Ext.grid.GridPanel({
				height : 324,
				store : jsonstore,
				bbar : pager,
				colModel : column
			});
	grid.addListener('rowdblclick', dbc);
	var form = new Ext.form.FormPanel({

				frame : true,
				fileUpload : true,
				style : 'margin:10px',
				items : [grid]
			});
	return form;
}