/**
 * @author
 * @class PlManageMoneyTypeView
 * @extends Ext.Panel
 * @description [PlManageMoneyType]管理
 * @company 智维软件
 * @createtime:
 */
UPlanTypeView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		UPlanTypeView.superclass.constructor.call(this, {
					id : 'UPlanTypeView'+this.keystr,
					title : this.keystr=='UPlan'?'U计划类别管理':'D计划类别管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					id : 'UPlanTypeViewSearchPanel'+this.keystr,
					layout : 'form',
					border : false,
					region : 'north',
					height : 65,
					anchor : '70%',
					keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
					items : [{
						border : false,
						layout : 'column',
						style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
						layoutConfig : {
							align : 'middle',
							padding : '5px'
						},
						defaults : {
							xtype : 'label',
							anchor : '95%'

						},
						items : [{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel :this.keystr=='UPlan'?'U计划类型名称':'D计划类型名称',
										name : 'Q_name_S_LK',
										xtype : 'textfield'
										

									}]
			            },{
							columnWidth : .3,
							labelAlign : 'right',
							xtype : 'container',
							layout : 'form',
							labelWidth : 120,
							defaults : {
								anchor : '100%'
							},
							items : [ {
										fieldLabel : '收款专户',
										name : 'Q_receivablesAccount_S_LK',
										xtype : 'textfield'
										

									}]
			            }, {
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '查询',
								scope : this,
								iconCls : 'btn-search',
								handler : this.search
							}]
							},{
							columnWidth : .07,
							xtype : 'container',
							layout : 'form',
							defaults : {
								xtype : 'button'
							},
							style : 'padding-left:10px;',
							items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
						}]
					}]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-add',
								text : '添加',
								xtype : 'button',
								scope : this,
								handler : this.createRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-edit',
								text : '编辑',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							}, new Ext.Toolbar.Separator({
							}), {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'UPlanTypeViewGrid'+this.keystr,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlManageMoneyType.do?Q_keystr_S_EQ="+this.keystr+"&Q_state_N_EQ=0",
			fields : [{
						name : 'manageMoneyTypeId',
						type : 'int'
					}, 'name', 'keystr', 'remark','receivablesAccount','accountType'],
			columns : [{
						header : 'manageMoneyTypeId',
						dataIndex : 'manageMoneyTypeId',
						hidden : true
					}, {
						header : '计划类型',
						width:60,
						dataIndex : 'name'
					},{
						header : '收款类型',
						dataIndex : 'accountType',
						width:60,
						renderer : function(v){
							if(v=="zc"){
								return '注册用户';
							}else if(v=='pt'){
								return '平台用户';
							}else{
								return v;
							}
						}
					},{
						header : '收款专户',
						dataIndex : 'receivablesAccount',
						width:60
					}, {
						header : '说明',
						dataIndex : 'remark',
						width:180
					}]
				// end of columns
			});


	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// 创建记录
	createRs : function() {
		new UPlanTypeForm({
			isReadOnly:false,
			keystr:this.keystr
		}).show();
	},
	// 把选中ID删除
	removeSelRs : function() {
		var keystr=this.keystr;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			Ext.MessageBox.confirm('确认办理','您确认要删除吗？', function(btn){
			if (btn == 'yes') {
				var manageMoneyTypeId=selectRs[0].data.manageMoneyTypeId;
				Ext.Ajax.request({
		           url:  __ctxPath + '/creditFlow/financingAgency/deletePlManageMoneyType.do?manageMoneyTypeId='+manageMoneyTypeId,
		           method : 'POST',
		           success : function(response,request) {
						var obj=Ext.util.JSON.decode(response.responseText);
		        		Ext.ux.Toast.msg('操作信息',obj.result);
		        		var gridPanel = Ext.getCmp('UPlanTypeViewGrid'+keystr);
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
		           }
				});
			}
		    })
		}
	},
	// 编辑Rs
	editRs : function() {
		var keystr=this.keystr
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		} else if (selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		} else {
			var record=selectRs[0];
			var manageMoneyTypeId=record.data.manageMoneyTypeId;
			Ext.Ajax.request({
               url:  __ctxPath + '/creditFlow/financingAgency/yesOrNoPlManageMoneyType.do?manageMoneyTypeId='+manageMoneyTypeId,
               method : 'POST',
               success : function(response,request) {
					var obj=Ext.util.JSON.decode(response.responseText);
            		if(obj.result=="1"){					                            			
            			Ext.ux.Toast.msg('操作信息',"此类型已使用，不能编辑！");
            		}else{
				        new UPlanTypeForm({
							manageMoneyTypeId : record.data.manageMoneyTypeId,
							isReadOnly:false,
							keystr:keystr
						}).show();
            		}
               }
			});
		}
	}

});
