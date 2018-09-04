function selectSlCompanyMain(slCompanyMain) {
	var slCompanyMian_jStore = new Ext.data.JsonStore({
		url : __ctxPath + "/creditFlow/ourmain/listReferenceSlCompanyMain.do",
		/*url : __ctxPath + '/credit/customer/enterprise/ajaxQueryEnterprise.do',*/
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {name : 'companyMainId'
				}, {name : 'corName'
				}, {name : 'simpleName'
				}, {name : 'lawName'
				}, {name : 'organizeCode'
				}, {name : 'businessCode'
				}, {name : 'haveCharcter'
				}, {name : 'tax'
				}, {name : 'tel'
				}, {name : 'mail'
				}, {name : 'messageAddress'
				}, {name : 'sjjyAddress'
				}, {name : 'postalCode'
				}, {name : 'isPledge'
				}, {name : 'registerMoney'
				}, {name : 'hangyeType'
				}, {name : 'hangyeTypeValue'
				}, {name : 'registerStartDate'
				}, {name : 'personMainId'
				}]
		});

	var anchor = '100%';
	var pageSize = 15;
	var listWindowHeight = 465;
	var slCompanyMianObject;

	slCompanyMian_jStore.addListener('load', function() {
				slCompanyMain_gridPanel.getSelectionModel().selectFirstRow();
			}, this);

	var slCompanyMain_cModel = new Ext.grid.ColumnModel([

			new Ext.grid.RowNumberer({
						header : '序号',
						width : 35
					}), {
				header : 'id',
				dataIndex : 'companyMainId',
				hidden : true
			},{
				id : 'corName',
				header : '企业名称',
				dataIndex : 'corName'
			}, {
				header : '组织机构代码',
				dataIndex : 'organizeCode'
			}, {
				header : '营业执照号码',
				dataIndex : 'businessCode'
			}, {
				header : '联系电话',
				dataIndex : 'tel'
			}, /*{
				header : '所有制性质',
				dataIndex : 'haveCharcter'
			}, */{
				header : '法人姓名',
				dataIndex : 'lawName'
			}
	]);
	var slCompanyMain_pagingBar = new Ext.PagingToolbar({
				pageSize : pageSize,
				store : slCompanyMian_jStore,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
	var myMask_slCompanyMain = new Ext.LoadMask(Ext.getBody(), {
				msg : "正在加载数据中······,请稍候······"
			});

	var slCompanyMain_gridPanel = new Ext.grid.GridPanel({
				id : 'slCompanyMain_gridPanel',
				store : slCompanyMian_jStore,
				colModel : slCompanyMain_cModel,
				autoExpandColumn : 'corName',
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : true,
				bbar : slCompanyMain_pagingBar,
				tbar : [{
							text : '新增',
							iconCls : 'addIcon',
							handler : function(btn, e) {
								new SlCompanyMainForm({isPledge:1,isReadOnly : false,gridpanel : slCompanyMain_gridPanel}).show();
							}
						}, '-', {
							text : '编辑',
							iconCls : 'updateIcon',
							handler : function(btn, e) {
								var selected = slCompanyMain_gridPanel.getSelectionModel().getSelected();
								if (null == selected) {
									Ext.MessageBox.alert('状态', '请选择一条记录!');
								}else{
									var companyMainId = selected.get('companyMainId');
									new SlCompanyMainForm( {id : companyMainId,isReadOnly : false,gridpanel : slCompanyMain_gridPanel}).show();
								}
							}
						}, '-', {
							text : '查看',
							iconCls : 'seeIcon',
							handler : function(btn, e) {
								var selected = slCompanyMain_gridPanel.getSelectionModel().getSelected();
								if (null == selected) {
									Ext.MessageBox.alert('状态', '请选择一条记录!');
								}else{
									var companyMainId = selected.get('companyMainId');
									new SlCompanyMainForm( {id : companyMainId,isReadOnly : true}).show();
								}
							}
						}, '-', new Ext.form.Label({
									text : '企业名称：'
								}), new Ext.form.TextField({
									id : 'slCompanyMain_entName_refer',
									width : 110
								}), {
							text : '查找',
							iconCls : 'searchIcon'
						}],
				listeners : {
					'rowdblclick' : function(grid, rowIndex, e) {
						var selected = grid.getStore().getAt(rowIndex);
						callBackSlCompanyMainObj(selected, slCompanyMain);
						slCompanyMain_window.destroy();
						myMask_slCompanyMain.hide();
					}
				}
			});
	Ext.getCmp('slCompanyMain_entName_refer').on('blur', function() {
				var value = Ext.get('slCompanyMain_entName_refer').dom.value;
				slCompanyMian_jStore.baseParams.corName = value;
				slCompanyMian_jStore.load({
							params : {
								start : 0,
								limit : 15
							}
						});
			});
	var slCompanyMain_window = new Ext.Window({
				title : '我方企业主体列表',
				border : false,
				width : (screen.width - 180) * 0.75,
				height : listWindowHeight - 30,
				collapsible : true,
				modal : true,
				constrainHeader : true,
				items : [slCompanyMain_gridPanel],
				layout : 'fit',
				buttonAlign : 'center',
				buttons : [{
							xtype : 'button',
							text : '关闭',
							iconCls : 'close',
							handler : function() {
								slCompanyMain_window.close();
							}
						}]
			});
	slCompanyMain_window.show();
	slCompanyMian_jStore.load({
				params : {
					start : 0,
					limit : pageSize
				}
			});

	var callBackSlCompanyMainObj = function(selected, slCompanyMain) {
		slCompanyMianObject = {
			companyMainId : selected.get('companyMainId'),
			corName : selected.get('corName'),
			simpleName : selected.get('simpleName'),
			lawName : selected.get('lawName'),
			organizeCode : selected.get('organizeCode'),
			businessCode : selected.get('businessCode'),
			haveCharcter : selected.get('haveCharcter'),
			tel : selected.get('tel'),
			mail : selected.get('mail'),
			messageAddress : selected.get('messageAddress'),
			sjjyAddress : selected.get('sjjyAddress'),
			postalCode : selected.get('postalCode'),
			isPledge : selected.get('isPledge'),
			registerMoney : selected.get('registerMoney'),
			hangyeType : selected.get('hangyeType'),
			hangyeTypeValue : selected.get('hangyeTypeValue'),
			registerStartDate : selected.get('registerStartDate'),
			personMainId : selected.get('personMainId')
		}
		slCompanyMain(slCompanyMianObject);
	}
}