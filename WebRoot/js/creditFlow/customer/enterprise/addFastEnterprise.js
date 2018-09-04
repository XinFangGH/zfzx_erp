function addEnterpriseAndPerson() {
	var url = __ctxPath + '/creditFlow/customer/person/addInfoPerson.do';
	var _navHandler = function(direction) {
		var _wizard = Ext.getCmp('wizard').layout;
		var _prev = Ext.getCmp('move-prev');
		var _next = Ext.getCmp('move-next');
		var wi = Ext.getCmp('wizard');
		var _activeId = _wizard.activeItem.id;
		if (_activeId == 'select') {
			if (direction == 1) {
				var rg = Ext.getCmp('wizard').get(0);
				var inputValue = rg.getValue().inputValue;
				if (inputValue == 1) {
					var random = rand(1000000);
					var id_enterprise = "add_enterprise" + random;
					Ext.getCmp('wizard').add(new enterpriseObj({
								winId : id_enterprise
							}));
					Ext.getCmp('wizard').doLayout();
					_wizard.setActiveItem(1);
					Ext.getCmp('wizard').setTitle("客户登记-添加企业");
					var item = wi.getTopToolbar().items.get(0);
					item.show();
				} else if (inputValue == 2) {
					var random = rand(1000000);
					var id_person = "add_person"+random;
					var addPerson=new personObj({
								personData : null,
								url : url,
								id : id_person
							})
					Ext.getCmp('wizard').add(addPerson);
					Ext.getCmp('wizard').doLayout();
					_wizard.setActiveItem(1);
					Ext.getCmp('wizard').setTitle("客户登记-添加个人");
					var item = wi.getTopToolbar().items.get(0);
					item.show();
				}
				wi.setWidth((screen.width - 180) * 0.7 + 160);
				wi.setHeight(460);
				_prev.setDisabled(false);
				_next.setDisabled(true);
			}
		} else {
			if (direction == -1) {
				_wizard.setActiveItem(0);
				_prev.setDisabled(true);
				_next.setDisabled(false);
				Ext.getCmp('wizard').get(1).remove();
				Ext.getCmp('wizard').doLayout();
				Ext.getCmp('wizard').setTitle("客户登记");
				var item = wi.getTopToolbar().items.get(0);
				item.hide();
				wi.setWidth(160);
				wi.setHeight(200);

			}
		}
	};
	var _widow = new Ext.Window({
				id : 'wizard',
				width : 160,
				height : 200,
				title : '客户登记',
				frame : true,
				layout : 'card',
				activeItem : 0,
				bodyStyle : 'overflowX:hidden',
				buttonAlign : 'right',
				iconCls : 'newIcon',
				constrain : true,
				modal : true,
				plain : true,
				defaults : {
					border : false
				},
				bbar : [{
							id : 'move-prev',
							text : '上一步',
							handler : _navHandler.createDelegate(this, [-1]),
							disabled : true
						}, '->', {
							id : 'move-next',
							text : '下一步',
							handler : _navHandler.createDelegate(this, [1])
						}],
				items : [new Ext.form.RadioGroup({
							fieldLabel : "radioGroup",
							id : 'select',
							items : [{
										boxLabel : '企业',
										style : "margin-left:20px;",
										inputValue : "1",
										name : "rg",
										checked : true
									}, {
										boxLabel : '个人',
										name : "rg",
										inputValue : "2",
										style : "margin-left:20px;",
										checked : false
									}]
						})],
				tbar : [new Ext.Button({
							text : '保存',
							tooltip : '保存基本信息',
							iconCls : 'submitIcon',
							hidden : true,
							hideMode : 'offsets',
							handler : function() {
								var _wizard = Ext.getCmp('wizard').layout;
								var _activeId = _wizard.activeItem.id;
								if (_activeId == "add_enterprise") {
									var window_add = Ext.getCmp('wizard');
									var vDates = "";
									var panel_add = window_add.get(1);
									var edGrid = panel_add.getCmpByName

									('gudong_store').get(0).get(1);
									vDates = getGridDate(edGrid);
									var gudonginfo_hidden = panel_add
											.getCmpByName

											('gudongInfo');
									gudonginfo_hidden.setValue(vDates);
									formSavePersonObj

									(panel_add, window_add, null);
								} else if (_activeId == "add_person") {
									var window_add = Ext.getCmp('wizard');
									var panel_add = window_add.get(2);
									formSavePersonObj(panel_add, window_add,
											null);
								}

							}
						})],
				listeners : {
					'beforeclose' : function(panel) {
						_widow.setWidth(160);
						_widow.setHeight(200);
						_widow.destroy();
					},
					'show' : function(win) {
						win.setWidth(160);
						win.setHeight(200);
					}
				}
			});
	_widow.show();
}