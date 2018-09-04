/**
 * 个人客户列表
 * 
 * @class PersonView
 * @extends Ext.Panel
 */

PersonP2PView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		if (config == null) {
			config = {};
		}
		if (typeof(config.open) != "undefined" ) {//带有预览button
			this.open=config.open;
		}else if(typeof(_cfg.bind) != "undefined" ){//带有发布button
			this.bind=cfg.bind;
		}
		Ext.applyIf(this, config);
		this.initUIComponents();
		PersonP2PView.superclass.constructor.call(this, {
					id : 'PersonP2PView'+this.open+this.bind,
					height : 450,
					autoScroll : true,
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
					url : __ctxPath
							+ '/creditFlow/customer/person/personListPerson.do?isAll='
							+ isGranted('_detail_sygrkh_p2pzh'),
									
					totalProperty : 'totalProperty',
					root : 'topics',
					remoteSort : true,
					fields : [{
								name : 'id'
							}, {
								name : 'name'
							}, {
								name : 'sexvalue'
							}, {
								name : 'jobvalue'
							}, {
								name : 'cardtypevalue'
							}, {
								name : 'cardnumber'
							}, {
								name : 'cellphone'
							}, {
								name : 'birthday'
							}, {
								name : 'nationalityvalue'
							}, {
								name : 'peopletypevalue'
							}, {
								name : 'dgreevalue'
							}, {
								name : 'techpersonnel'
							}, {
								name : 'age'
							}, {
								name : 'marryvalue'
							}, {
								name : 'telphone'
							}, {
								name : 'englishname'
							}, {
								name : 'orgName'
							},{
								name : 'selfemail'
							},{
								name : 'p2pName'
							},{
								name : 'p2pcardnumber'
							},{
								name : 'p2pcellphone'
							},{
								name : 'p2pemail'
							},{
								name : 'p2pTrueName'
							},{
								name : 'thirdPayFlagId'
							}]
				});
		    var person_store=this.store;
		    this.myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});
				
		    /*var importButton = new Ext.Button({
			text : '导入个人数据',
			iconCls : 'addIcon',
			tooltip : '导入个人原始记录',
			scope : this,
			disabled : false,
			handler : function() {
				new Ext.Window({
					id : 'importEnterpriseWin',
					title : '导入数据',
					layout : 'fit',
					width : (screen.width - 180) * 0.6 - 150,
					height : 130,
					closable : true,
					resizable : true,
					plain : false,
					bodyBorder : false,
					border : false,
					modal : true,
					constrainHeader : true,
					bodyStyle : 'overflowX:hidden',
					buttonAlign : 'right',
					items : [new Ext.form.FormPanel({
						id : 'importEnterpriseFrom',
						monitorValid : true,
						labelAlign : 'right',
						url :  __ctxPath +'/creditFlow/customer/person/batchImportPBatchImportDatabase.do',
						buttonAlign : 'center',
						enctype : 'multipart/form-data',
						fileUpload : true,
						layout : 'column',
						frame : true,
						items : [{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 90,
							defaults : {
								anchor : '95%'
							},
							items : [{}, {
								xtype : 'textfield',
								fieldLabel : '请选择文件',
								allowBlank : false,
								blankText : '文件不能为空',
								inputType : 'file',
								id : 'fileBatch',
								name : 'fileBatch'
							}]
						}],
						buttons : [{
							text : '导入',
							iconCls : 'uploadIcon',
							formBind : true,
							handler : function() {
								Ext.getCmp('importEnterpriseFrom').getForm()
										.submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function(form, action) {
												Ext.ux.Toast.msg('状态', '导入成功!');
												Ext.getCmp('importEnterpriseWin').destroy();
												person_store.reload();
													
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('状态', '导入失败!');
											}
										});
							}
						}]
					})]
				}).show();
			}
		})*/
		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
					height : 60,
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
					items : [isShow?{
				columnWidth : 0.2,
				labelWidth : 65,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [
				{
						xtype : "combo",
						anchor : "100%",
						fieldLabel : '所属分公司',
						hiddenName : "companyId",
						displayField : 'companyName',
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath+'/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
				}
			]}:{columnWidth : 0.01},{
								columnWidth :isShow? 0.15:0.17,
								labelWidth : 40,
								//bodyStyle : 'padding:5px 0px 0px 0px',
								items : [{
											xtype : 'textfield',
											fieldLabel : '姓名',
											name : 'name',
											anchor : '100%'
										}]
							},{
								columnWidth : isShow? 0.15:0.17,
								labelWidth : 55,
								items : [{
										xtype : "dickeycombo",
										nodeKey : 'card_type_key',
										hiddenName : "cardtype",
										fieldLabel : "证件类型",
										anchor:'100%',
										editable : true,
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
														combox.setValue(combox.getValue());
														combox.clearInvalid();
												})
											}
										}
									}]		
									
									
							},{
								columnWidth : isShow? 0.2:0.22,
								labelWidth : 55,
								items : [{
											xtype : 'textfield',
											fieldLabel : '证件号码',
											name : 'cardnumber',
											anchor : '100%'
										}]
							
							},{
								columnWidth : isShow? 0.2:0.25,
								labelWidth : 55,
								items : [{
											xtype : 'textfield',
											fieldLabel : '联系电话',
											name : 'cellphone',
											anchor : '100%'
										}]
							
							},{
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
				//hidden :this.open==1?false:true,//? (typeof(this.open) != "undefined")?this.open:isGranted('_add_p2p_persion_user') ? false : true,
				scope:this,
				buttonType:"ktzh",
				handler: this.addP2pUser
			},{
				iconCls : 'btn-unablep2p',
				text : '绑定p2p账户',
				xtype : 'button',
				//hidden : this.bind==1?false:true,//(typeof(this.bind) != "undefined")?this.open:isGranted('_add_p2p_persion_user') ? false : true,
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
								header : "客户姓名",
								width : 50,
								align:'center',
								sortable : true,
								dataIndex : 'name'
							},{
								header : "证件类型",
								width : 50,
								align:'center',
								sortable : true,
								dataIndex : 'cardtypevalue'
							},{
								header : "证件号码",
								width : 90,
								align:'center',
								sortable : true,
								dataIndex : 'cardnumber'
							},{
								header : "联系号码",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex : 'cellphone'
							},/*{
								header : "邮箱",
								width : 80,
								sortable : true,
								dataIndex:'selfemail'
							},*/{
								header : "网站登陆账号",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex:'p2pName'
							},{
								header : "网站认证姓名",
								width : 50,
								align:'center',
								sortable : true,
								dataIndex:'p2pTrueName'
							},{
								header : "网站认证证件",
								width : 90,
								align:'center',
								sortable : true,
								dataIndex:'p2pcardnumber'
							},{
								header : "网站认证电话",
								width : 70,
								align:'center',
								sortable : true,
								dataIndex:'p2pcellphone'
							},{
								header : "网站支付账号",
								width : 120,
								align:'center',
								sortable : true,
								dataIndex:'thirdPayFlagId'
							}
							/* {
								header : "家庭电话",
								width : 70,
								sortable : true,
								dataIndex : 'telphone'
							}, {
								header : "出生日期",
								width : 110,
								sortable : true,
								dataIndex : 'birthday'
							}*/],
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
		this.store.baseParams.name = this.searchPanel.getForm().findField('name').getValue();
		this.store.baseParams.cardtype = this.searchPanel.getForm().findField('cardtype').getValue();
		this.store.baseParams.cardnumber = this.searchPanel.getForm().findField('cardnumber').getValue();
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
	// 添加p2p账户
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
		}else if (selections[0].data.p2pName){
			Ext.ux.Toast.msg('状态', '该用户P2P账户已经存在!');
			return;
		}
		var personId = selections[0].data.id;
		new BpCustRelationForm({
			customerType : "p_loan",//客户类型
			buttonType : buttonType,//业务类型
			userId : personId,
			cardnumber : selections[0].data.cardnumber,
			grid : grid
		}).show();
	}
});