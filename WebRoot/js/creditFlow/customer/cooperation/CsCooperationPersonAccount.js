/**
 * @author
 * @class CsCooperationPersonView
 * @extends Ext.Panel
 * @description [CsCooperationPerson]管理
 * @company 智维软件
 * @createtime:
 */
CsCooperationPersonAccount = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		CsCooperationPersonAccount.superclass.constructor.call(this, {
					id : 'CsCooperationPersonAccount'+this.type,
					title : '网站账号管理',
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
								fieldLabel : '网站登录账号',
								name : 'p2ploginname',
								anchor : "100%",
								xtype : 'textfield'
						}]
					},
					{
						columnWidth : .25,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
								fieldLabel : '客户姓名',
								name : 'name',
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
								fieldLabel : '证件号码',
								name : 'cardNumber',
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
					}
					]
				});// end of searchPanel

		this.topbar = new Ext.Toolbar({
					items : [{
								iconCls : 'btn-p2p',
								text : '开通p2p账户',
								xtype : 'button',
								scope : this,
								buttonType:"ktzh",
								handler : this.openP2P
							},{
								iconCls : 'btn-unablep2p',
								text : '绑定p2p账户',
								xtype : 'button',
								scope : this,
								buttonType:"bdzh",
								handler : this.openP2P
							},{
								iconCls : 'btn-startup',
								text : '启用',
								xtype : 'button',
								scope : this,
								handler : this.using
							}, {
								iconCls : 'btn-del1',
								text : '禁用',
								xtype : 'button',
								scope : this,
								handler : this.nousing
							}]
				});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			rowActions : false,
			id : 'CsCooperationPersonGrid',
			url : __ctxPath
					+ "/creditFlow/customer/cooperation/listAccountCsCooperationPerson.do?type="+this.type,
			fields : [{
						name : 'id',
						type : 'int'
					}, 
					'name',
					'sex', 
					'cardType', 
					'cardNumber', 
					'phoneNumber',
					'email', 
					'qqNumber', 
					'weixinNumber', 
					'remark',
					'isUsing',
					'isOpenP2P',
					'p2ploginname',
					'p2ptruename',
					'p2pcardcode',
					'p2ptelphone',
					'p2pemail',
					'p2pisForbidden',
					'p2pid',
					'thirdPayFlagId'
						
					],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						align:'center',
						hidden : true
					},{
						header : '客户姓名',
						align:'center',
						dataIndex : 'name'
					},{
						header : '证件号码',
						align:'center',
						dataIndex : 'cardNumber'
					},{
						header : '联系电话',
						align:'center',
						dataIndex : 'phoneNumber'
					},{
						header : '网站登录账号',
						align:'center',
						dataIndex : 'p2ploginname'
					},{
						header : '网站认证姓名',
						align:'center',
						dataIndex : 'p2ptruename'
					},{
						header : '网站认证证件',
						align:'center',
						dataIndex : 'p2pcardcode'
					},{
						header : '网站认证电话',
						align:'center',
						dataIndex : 'p2ptelphone'
					},{
						header : '网站支付账号',
						align:'center',
						dataIndex : 'thirdPayFlagId'
					}]
				// end of columns
		});

		//this.gridPanel.addListener('rowdblclick', this.rowClick);

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
					new CsCooperationPersonForm({
								id : rec.data.id
							}).show();
				});
	},
	// 创建记录
	createRs : function() {
		new CsCooperationPersonForm().show();
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationPerson.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow/customer/cooperation/multiDelCsCooperationPerson.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		
		var sel = this.gridPanel.getSelectionModel().selections;
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else{
			var record = sel.get(0);
			new CsCooperationPersonForm({
					id : record.data.id
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
			new CsCooperationPersonForm({
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
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationPerson.do',
								method : 'POST',
								success : function(response) {
									var obj = Ext.decode(response.responseText);
									if(obj.msg){
										Ext.ux.Toast.msg('操作信息','启用失败,'+obj.msg);
									}else{
										Ext.ux.Toast.msg('操作信息','启用成功!');
									}
									gridPanelStore.reload();
								},
								params : {
									"csCooperationPerson.isUsing" : "0",
									"csCooperationPerson.id" : record.data.id
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
								url : __ctxPath+ '/creditFlow/customer/cooperation/p2pisForbiddenCsCooperationPerson.do',
								method : 'POST',
								success : function(response) {
									var obj = Ext.decode(response.responseText);
									if(obj.msg){
										Ext.ux.Toast.msg('操作信息','禁用失败,'+obj.msg);
									}else{
										Ext.ux.Toast.msg('操作信息','禁用成功!');
									}
									gridPanelStore.reload();
								},
								params : {
									"csCooperationPerson.isUsing" : "1",
									"csCooperationPerson.id" : record.data.id
								}
							});
							
						}
				},this);
			
		}
	},
	//开通P2P账号
	openP2P : function(obj,e){
		var sel = this.gridPanel.getSelectionModel().selections;
		var grid=this.gridPanel;
		var buttonType=obj.buttonType;
		if(this.type == 'lenders'){//个人债权客户
			customerType = 'p_cooperation';
		}else{//个人理财顾问
			customerType = 'p_financial';
		}
		
		if(sel.length>1){
			Ext.ux.Toast.msg('操作信息','只能选择一条记录');
		}else if (sel.get(0).data.p2ploginname){
			Ext.ux.Toast.msg('状态', '该用户P2P账户已经存在!');
			return;
		}else{
			var record = sel.get(0);
			var personId = record.data.id;
			new BpCustRelationForm({
				customerType : customerType,//客户类型
				buttonType : buttonType,//业务类型
				userId : personId,
				cardnumber : record.data.cardNumber,
				grid : grid
			}).show();
		}
	}
});