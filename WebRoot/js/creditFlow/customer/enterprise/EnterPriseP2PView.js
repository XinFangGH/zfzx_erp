/**
 * 个人客户列表
 * 
 * @class PersonView
 * @extends Ext.Panel
 */

EnterPriseP2PView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		EnterPriseP2PView.superclass.constructor.call(this, {
					id : 'EnterPriseP2PView'+this.open+this.bind,
					height : 450,
					autoScroll : true,
					title:'网站账号管理',
					layout : 'border',
					iconCls : 'btn-tree-team23',
					items : [this.searchPanel, this.centerPanel]
				});
	},
	initUIComponents : function() {
		   var isShow=false;
		   if(RoleType=="control"){
				 isShow=true;
			}
		    this.pageSize = 25;
		    this.store = new Ext.data.JsonStore({
					url : __ctxPath + '/creditFlow/customer/enterprise/listEnterprise.do?isAll='+isGranted('_seeAll_qykh_p2pzh'),
					totalProperty : 'totalProperty',
					root : 'topics',
					remoteSort : true,
					fields : [{
								name : 'id'
							}, {
								name : 'enterprisename'
							}, {
								name : 'cciaa'
							}, {
								name : 'organizecode'
							}, {
								name : 'telephone'
							}, {
								name : 'p2pId'
							}, {
								name : 'loginname'
							}, {
								name : 'truename'
							}, {
								name : 'cardcode'
							},  {
								name : 'p2ptelphone'
							}, {
								name : 'thirdPayFlagId'
							}]
				});
		    var person_store=this.store;
		    this.myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});
				
		 /*  */
				
				
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
					height : 50,
					//labelWidth : 55,
					region : "north",
					bodyStyle : 'padding:7px 0px 7px 10px',
					border : false,
					width : '100%',
					monitorValid : true,
					layout : 'column',
					defaults : {
						layout : 'form',
						border : false,
						bodyStyle : 'padding:5px 0px 0px 20px'
					},
					items : [{
								columnWidth :0.2,
								labelWidth :70,
								//bodyStyle : 'padding:5px 0px 0px 0px',
								items : [{
											xtype : 'textfield',
											fieldLabel : '客户名称',
											name : 'enterprisename',
											anchor : '100%'
										}]
							},{
								columnWidth : 0.25,
								labelWidth : 90,
								items : [{
											xtype : 'textfield',
											fieldLabel : '证件号码',
											name : 'organizecode',
											anchor : '100%'
										}]
							
							}/*,{
								columnWidth : 0.25,
								labelWidth : 90,
								items : [{
											xtype : 'textfield',
											fieldLabel : '营业执照号码',
											name : 'cciaa',
											anchor : '100%'
										}]
							
							}*/,{
								columnWidth : 0.07,
								items : [{
											id : 'searchButton',
											xtype : 'button',
											text : '查询',
											tooltip : '根据查询条件过滤',
											iconCls : 'btn-search',
											width : 60,
											formBind : true,
											scope : this,
											handler : function() {
												this.searchByCondition();
											}
										}]
							},{
							
								columnWidth : 0.07,
								items : [{
									xtype : 'button',
									text : '重置',
									width : 60,
									scope : this,
									iconCls : 'btn-reset',
									handler : this.reset
								}]
							
							}]
					}); // 查询面板结束
		
      
		//this.store.setDefaultSort('name');
		// 加载数据
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : this.pageSize,
						isAll : isGranted('_detail_sygrkh')
					}
				});
        var personStore=this.store;
		var tbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-p2p',
				text : '开通p2p账户',
				xtype : 'button',
				//hidden : (typeof(this.open) != "undefined")?this.open:isGranted('_add_p2p_persion_user') ? false : true,
				scope:this,
				buttonType:"ktzh",
				handler: this.addP2pUser
			},{
				iconCls : 'btn-unablep2p',
				text : '绑定p2p账户',
				xtype : 'button',
				//hidden : (typeof(this.bind) != "undefined")?this.open:isGranted('_add_p2p_persion_user') ? false : true,
				scope:this,
				buttonType:"bdzh",
				handler: this.addP2pUser
			}]
		});
        
		this.centerPanel = new HT.GridPanel({
					region : 'center',
					// title:'个人客户信息',
					tbar : tbar,
					clicksToEdit : 1,
					store : this.store,
					loadMask : this.myMask,
					columns : [{
								header : "客户名称",
								width : 100,
								sortable : true,
								dataIndex : 'enterprisename'
							}/*,{
								header : "营业执照号码",
								width : 70,
								sortable : true,
								dataIndex:'cciaa'
							}*/,{
								header : "证件号码",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex:'organizecode'
							},{
								header : "联系电话",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex : 'telephone'
							},{
								header : "网站登陆账号",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex : 'loginname'
							},{
								header : "网站认证名称",
								width : 100,
								sortable : true,
								dataIndex:'truename'
							},{
								header : "网站认证电话",
								width : 70,
								sortable : true,
								align:'center',
								dataIndex:'p2ptelphone'
							},{
								header : "网站支付账号",
								width : 120,
								sortable : true,
								align:'center',
								dataIndex:'thirdPayFlagId'
							}],
					defaults : {
						sortable : true,
						menuDisabled : false,
						width : 100
					},
					height : 450,
					viewConfig : {
						forceFit : true,
						autoFill : true
					},
					listeners : {
						afteredit : function(e) {
						}
					}
				});
	},//end of initUIComponents

	//查询
	searchByCondition : function() {
		this.store.baseParams.enterprisename = this.searchPanel.getForm().findField('enterprisename').getValue();
		this.store.baseParams.organizecode = this.searchPanel.getForm().findField('organizecode').getValue();
//		this.store.baseParams.cciaa = this.searchPanel.getForm().findField('cciaa').getValue();
		this.store.load({
			scope : this,
			params : {
				start : 0,
				limit : this.pageSize
			}
		});
	},
	
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	
	
	addP2pUser : function(obj,e) {
		var selections = this.centerPanel.getSelectionModel().getSelections();
		var grid=this.centerPanel;
		var buttonType=obj.buttonType;
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		}else if (selections[0].data.loginname){
			Ext.ux.Toast.msg('状态', '该用户P2P账户已经存在!');
			return;
		}
		//开通p2p账户的时候需要验证联系电话
		var personId = selections[0].data.id;
		if(obj.buttonType=="ktzh"){
			var zz=/^1[3|4|5|7|8][0-9]\d{4,8}$/;
			if(selections[0].data.telephone){
				if(selections[0].data.telephone.match(zz)){
					new BpCustRelationForm({
						customerType : "b_loan",//客户类型
						buttonType : buttonType,//业务类型
						userId : personId,
						cardnumber : selections[0].data.organizecode,
						grid : grid
					}).show();
				}else{
					Ext.ux.Toast.msg('状态', '联系电话不合法,请重新编辑!!!');
					return;
				}
			}else{
				Ext.ux.Toast.msg('状态', '联系电话不存在,请重新编辑!!!');
				return;
			}
		}else{
			new BpCustRelationForm({
				customerType : "b_loan",//客户类型
				buttonType : buttonType,//业务类型
				userId : personId,
				cardnumber : selections[0].data.organizecode,
				grid : grid
			}).show();
		}
	}
});