/**
 * @author
 * @class CsCooperationEnterpriseView
 * @extends Ext.Panel
 * @description [CsCooperationEnterprise]管理
 * @company 智维软件
 * @createtime:
 */
CsCooperationEnterpriseView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsCooperationEnterpriseView.superclass.constructor.call(this, {
					id : 'CsCooperationEnterpriseView'+this.type,
					title : '客户档案管理',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					border : false,
					hieght : 100,
					anchor : '100%',
					layoutConfig:{
						align : 'middle'
					},
					items : [
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								
								xtype : "dickeycombo",
								readOnly : this.readOnly,
								nodeKey : 'csCooperationEnterpriseType',
								hiddenName : "Q_type_S_EQ",
								fieldLabel : "机构类型",
								anchor : '100%',
								editable : false,
								listeners : {
									scope : this,
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
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								fieldLabel : '机构名称',
								name : 'Q_name_S_LK',
								anchor : "100%",
								xtype : 'textfield'
						}]
					},{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								fieldLabel : '营业执照号码',
								name : 'Q_licenseNumber_S_LK',
								anchor : "100%",
								xtype : 'textfield'
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							text : '查询',
							xtype : 'button',
							scope : this,
							width : 30,
							style : 'margin-left:5px',
							iconCls : 'btn-search',
							handler : this.search
						}]
					},{
						columnWidth : .05,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							text : '重置',
							xtype : 'button',
							scope : this,
							width : 30,
							style : 'margin-left:5px',
							iconCls : 'btn-reset',
							handler : this.reset
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
							},  {
								iconCls : 'btn-detail',
								text : '查看',
								xtype : 'button',
								scope : this,
								handler : this.seeRs
							},{
								iconCls : 'btn-edit',
								text : '修改',
								xtype : 'button',
								scope : this,
								handler : this.editRs
							}, {
								iconCls : 'btn-del',
								text : '删除',
								xtype : 'button',
								scope : this,
								handler : this.removeSelRs
							}/*, {
								iconCls : 'btn-del',
								text : '启用',
								xtype : 'button',
								scope : this,
								handler : this.using
							}, {
								iconCls : 'btn-del',
								text : '禁用',
								xtype : 'button',
								scope : this,
								handler : this.nousing
							}, {
								iconCls : 'btn-add',
								text : '开通p2p账户',
								xtype : 'button',
								scope : this,
								handler : this.openP2P
							}*/]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			//id : 'CsCooperationEnterpriseGrid',
			url : __ctxPath
					+ "/creditFlow/customer/cooperation/listCsCooperationEnterprise.do?Q_type_S_EQ ="+this.type+"&isHideP2PAccount=true",
			fields : [{
						name : 'id',
						type : 'int'
					}, 'name', 'type', 'typeFrom', 'licenseNumber',
					'organizationNumber', 'tellPhone', 'fax',
					'registeredMoney', 'buildDate', 'cooperationDate',
					'companyURL', 'companyAddress', 'companyIntro', 'pname',
					'psex', 'pphone', 'pappellation', 'pemail', 'pcardNumber',
					'isUsing', 'isOpenP2P'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						align:'center',
						hidden : true
					},/*  {
						header : '机构类型',
						dataIndex : 'type'
					},*/ {
						header : '机构名称',
						dataIndex : 'name'
					}, {
						header : '组织机构号码',
						align:'center',
						dataIndex : 'organizationNumber'
					}, {
						header : '营业执照号码',
						align:'center',
						dataIndex : 'licenseNumber'
					}, {
						header : '公司电话',
						align:'center',
						dataIndex : 'tellPhone'
					},  {
						header : '联系人',
						dataIndex : 'pname'
					}, {
						header : '联系人电话',
						align:'center',
						dataIndex : 'pphone'
					}, {
						header : '邮箱',
						align:'center',
						dataIndex : 'pemail'
					}, {
						header : '是否启用',
						align:'center',
						dataIndex : 'isUsing',
						renderer : function(val){
							if(val=="0"){
								return "是"
							}
							return "否"
						}
					} , {
						header : '是否开通P2P',
						dataIndex : 'isOpenP2P',
						align:'center',
						renderer : function(val){
							if(val=="0"){
								return "是"
							}
							return "否"
						}
					} ]
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new CsCooperationEnterpriseForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		var gridPanel=this.gridPanel;
		new CsCooperationEnterpriseForm({
			type : this.type,
			gridPanel : gridPanel
		}).show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationEnterprise.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationEnterprise.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		var gridPanel=this.gridPanel;
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			new CsCooperationEnterpriseForm({
				id : record.data.id,
				gridPanel : gridPanel
			}).show();
		}
	},
	// 查看Rs
	seeRs : function(record) {
		
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			new CsCooperationEnterpriseForm({
				id : record.data.id,
				readOnly : true
			}).show();
		}
	},
	//启用
	using : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			Ext.Msg.confirm("提示!",'你确定要启用吗',
					function(btn) {
						if (btn == "yes") {
							var gridPanelStore = this.gridPanel.getStore();
							var sel = this.gridPanel.getSelectionModel().selections;
							var record = sel.get(0);
							//ajax更改状态
							Ext.Ajax.request( {
								url : __ctxPath+ '/creditFlow/customer/cooperation/saveCsCooperationEnterprise.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','启用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationEnterprise.isUsing" : "0",
									"csCooperationEnterprise.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	//禁用
	nousing : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			Ext.Msg.confirm("提示!",'你确定要禁用吗',
					function(btn) {
						if (btn == "yes") {
							var gridPanelStore = this.gridPanel.getStore();
							var sel = this.gridPanel.getSelectionModel().selections;
							var record = sel.get(0);
							//ajax更改状态
							Ext.Ajax.request( {
								url : __ctxPath+ '/creditFlow/customer/cooperation/saveCsCooperationEnterprise.do',
								method : 'POST',
								success : function(response) {
									Ext.ux.Toast.msg('操作信息','禁用成功!');
									gridPanelStore.reload();
								},
								params : {
									"csCooperationEnterprise.isUsing" : "1",
									"csCooperationEnterprise.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	openP2P : function(){
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			var personId = record.data.id;
			var cellphone = record.data.pPhone;
			var selfemail = record.data.pEmail;
			var cardnumber = record.data.pCardNumber;
			new BpCustRelationForm({
				type:"e_cooperation",
				bStr:true,
				pType:6,
				userId:personId,
				cellphone:cellphone,
				selfemail:selfemail,
				cardnumber:cardnumber
			}).show();
			
		}
	}
	
	
	
});
