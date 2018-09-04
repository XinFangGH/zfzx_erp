function selectSlPersonMain(slPersonMain) {
	var slPersonMian_jStore = new Ext.data.JsonStore({
		url : __ctxPath + "/creditFlow/ourmain/listReferenceSlPersonMain.do",
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {name : 'personMainId'
				}, {name : 'name'
				}, {name : 'sex'
				}, {name : 'cardtype'
				}, {name : 'cardnum'
				}, {name : 'linktel'
				}, {name : 'tel'
				}, {name : 'address'
				}, {name : 'home'
				}, {name : 'postalCode'
				}, {name : 'tax'
				}, {name : 'isPledge'
				}, {name : 'cardtypevalue'
				}]
		});

	var anchor = '100%';
	var pageSize = 15;
	var listWindowHeight = 465;
	var slPersonMianObject;

	slPersonMian_jStore.addListener('load', function() {
				slPersonMain_gridPanel.getSelectionModel().selectFirstRow();
			}, this);

	var slPersonMain_cModel = new Ext.grid.ColumnModel([

			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : 'id',
				dataIndex : 'personMainId',
				hidden : true
			},{
				id : 'personMainName',
				header : '姓名',	
				dataIndex : 'name'
			}, {
				header : '性别',	
				dataIndex : 'sex',
				renderer : function(v) {
					switch (v) {
						case 0 :
						return '男';
						break;
						case 1 :
						return '女';
						break;
					}
				}
			}, {
				header : '证件类型',	
				dataIndex : 'cardtypevalue'
			}, {
				header : '证件号码',	
				dataIndex : 'cardnum'
			}, {
				header : '联系电话',	
				dataIndex : 'linktel'
			}, {
				header : '固定电话',	
				dataIndex : 'tel'
			},{
				header : '通讯地址',	
				dataIndex : 'address'
			}
	]);
	var slPersonMain_pagingBar = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : slPersonMian_jStore,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_slPersonMain = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});

	var slPersonMain_gridPanel = new Ext.grid.GridPanel({
				id : 'slPersonMain_gridPanel',
				store : slPersonMian_jStore,
				colModel : slPersonMain_cModel,
				autoExpandColumn : 'personMainName',
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : true,
				bbar : slPersonMain_pagingBar,
				tbar : [{
							text : '新增',
							iconCls : 'addIcon',
							handler : function(btn, e) {
								new SlPersonMainForm({
									isPledge : 1,
									isReadOnlyPerson : false,
									gridPanelPerson : slPersonMain_gridPanel
								}).show();
							}
						}, '-', {
							text : '编辑',
							iconCls : 'updateIcon',
							handler : function(btn, e) {
								var selected = slPersonMain_gridPanel.getSelectionModel().getSelected();
								if (null == selected) {
									Ext.MessageBox.alert('状态', '请选择一条记录!');
								}else{
									var personMainId = selected.get('personMainId');
									new SlPersonMainForm({
										id : personMainId,
										isReadOnlyPerson : false,
										gridPanelPerson : slPersonMain_gridPanel
									}).show();
								}
							}
						}, '-', {
							text : '查看',
							iconCls : 'seeIcon',
							handler : function(btn, e) {
								var selected = slPersonMain_gridPanel.getSelectionModel().getSelected();
								if (null == selected) {
									Ext.MessageBox.alert('状态', '请选择一条记录!');
								}else{
									var personMainId = selected.get('personMainId');
									new SlPersonMainForm({
										id : personMainId,
										isReadOnlyPerson : true,
										gridPanelPerson : slPersonMain_gridPanel
									}).show();
								}
							}
						}, '-', new Ext.form.Label({
									text : '姓名：'
								}), new Ext.form.TextField({
									id : 'slPersonMain_name_refer',
									width : 110
								}), {
							text : '查找',
							iconCls : 'searchIcon'
						}],
				listeners : {
					'rowdblclick' : function(grid, rowIndex, e) {
						var selected = grid.getStore().getAt(rowIndex);
						callBackslPersonMainObj(selected, slPersonMain);
						slPersonMain_window.destroy();
						myMask_slPersonMain.hide();
					}
				}
			});
	Ext.getCmp('slPersonMain_name_refer').on('blur', function() {
				var value = Ext.get('slPersonMain_name_refer').dom.value;
				slPersonMian_jStore.baseParams.name = value;
				slPersonMian_jStore.load({
							params : {
								start : 0,
								limit : 15
							}
						});
			});
	var slPersonMain_window = new Ext.Window({
				title : '我方个人主体列表',
				border : false,
				width : (screen.width - 180) * 0.75,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [slPersonMain_gridPanel],
				layout : 'fit',
				buttonAlign : 'center',
				buttons : [{
							xtype : 'button',
							text : '关闭',
							iconCls : 'close',
							handler : function() {
								slPersonMain_window.close();
							}
						}]
			});
	slPersonMain_window.show();
	slPersonMian_jStore.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

	var callBackslPersonMainObj = function(selected, slPersonMain) {
		slPersonMianObject = {
			personMainId : selected.get('personMainId'),
			name : selected.get('name'),
			sex : selected.get('sex'),
			cardtype : selected.get('cardtype'),
			cardnum : selected.get('cardnum'),
			linktel : selected.get('linktel'),
			tel : selected.get('tel'),
			address : selected.get('address'),
			home : selected.get('home'),
			postalCode : selected.get('postalCode'),
			tax : selected.get('tax'),
			isPledge : selected.get('isPledge'),
			cardtypevalue : selected.get('cardtypevalue')
		}
		slPersonMain(slPersonMianObject);
	}
}