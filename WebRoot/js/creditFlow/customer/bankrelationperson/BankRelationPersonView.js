/**
 * 银行联系人列表
 * @class BankRelationPersonView
 * @extends Ext.Panel
 */
BankRelationPersonView=Ext.extend(Ext.Panel,{
	constructor:function(config){
		Ext.applyIf(this,config);
		this.initUIComponents();
		BankRelationPersonView.superclass.constructor.call(this,{
			id : 'BankRelationPersonView',
			height:450,
			autoScroll:true,
			layout:'border',
			title:'银行客户',
			iconCls:"menu-person",
			items:[
				this.searchPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		
		this.isHiddenBranch = true;
		if (RoleType == "control") {// 此用户角色为管控角色
			this.isHiddenBranch = false;
		}
		
		this.pageSize = 19;
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		//查询面板
		this.searchPanel = new Ext.form.FormPanel( {
			//title : '银行联系人管理',
			height : 45,
			region : "north",
			bodyStyle:'padding:7px 0px 7px 10px',
			border : false,
			//frame : true,
			monitorValid : true,
			layout : 'column',
			defaults : {
				layout : 'form',
				border : false,
				bodyStyle:'padding:5px 0px 0px 20px'
			},
			items : [{
				columnWidth:0.2,
				labelWidth : 100,
				bodyStyle:'padding:5px 0px 0px 0px',
				items :[{
					xtype : 'textfield',
					fieldLabel : '银行联系人姓名',
					name : 'name',
					anchor : '100%'
				}]
			},{
				columnWidth:0.2,
				labelWidth : 70,
				items :[{
					xtype : 'textfield',
					fieldLabel : '联系人职务',
					name : 'duty',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.2,
				labelWidth : 70,
				items :[{
					xtype : 'textfield',
					fieldLabel : '银行名称',
					name : 'bankname',
					anchor : '100%'
				}]
			}, this.isHiddenBranch == false?{
				columnWidth : .20,
				layout : 'form',
				hidden : this.isHiddenBranch,
				labelAlign : 'right',
				labelWidth : 80,
				border : false,
				items : [{
					fieldLabel : '所属分公司',
					xtype : "combo",
					anchor : "100%",
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/system/getControlNameOrganization.do',
						fields : ['companyId', 'companyName']
					})
				}]
			}:{
		     	columnWidth:0.001,
		     	border:false
		   	},{
				columnWidth:0.20,
				items : [{
					id : 'searchButton',
					xtype : 'button',
					text : '查询',
					tooltip : '根据查询条件过滤',
					iconCls : 'searchIcon',
					width : 20,
					formBind : true,
					scope : this,
					handler : function(){
						this.searchByCondition();
					}
				}]
			}]
		});  //查询面板结束
		this.store = new Ext.data.JsonStore( {
			url : __ctxPath + '/creditFlow/customer/bankRelationPerson/queryBankRelationPersonCustomerBankRelationPerson.do?isAll='+isGranted('_detail_syyhkh'),
			totalProperty : 'totalProperty',
			root : 'topics',
			remoteSort: true,
			fields : [ {name : 'id'}, {name : 'name'},{name : 'orgName'}, {name : 'sexvalue'}, {name : 'duty'}, {name : 'blmtelephone'}, {name : 'marriage'}, {name : 'marriagename'}, {name : 'birthday'}, {name : 'bankname'}, {name : 'email'}, {name : 'address'}, {name : 'blmphone'},{name : 'fenbankvalue'},{name : 'bankaddress'} ]
		});
	
		// 加载数据
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});

	
		var tbar=new Ext.Toolbar({
			items:[{
				text : '增加',
				hidden : isGranted('_create_yhkh')?false:true,
				tooltip : '增加银行联系人信息',
				iconCls : 'btn-add',
				scope : this,
				handler : function() {
					new BankRelationPersonWindow({listPanel :this.centerPanel,type :'add',url :__ctxPath + '/creditFlow/customer/bankRelationPerson/addBankRelationPersonCustomerBankRelationPerson.do'}).show();
				}
			},new Ext.Toolbar.Separator({
					hidden : isGranted('_create_yhkh') ? false : true
				}),{
					text : '查看',
					hidden : isGranted('_detail_yhkh')?false:true,
					tooltip : '查看选中的银行联系人信息',
					iconCls : 'btn-detail',
					scope : this,
					handler : function() {
						var rows = this.centerPanel.getSelectionModel().getSelections();
						if(rows.length==0){
							Ext.ux.Toast.msg('操作信息','请选择记录!');
							return;
						}else if(rows.length>1){
							Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
							return;
						}else{
							Ext.Ajax.request({
								url : __ctxPath+'/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
								method : 'POST',
								scope : this,
								success : function(response,request){
									var obj = Ext.util.JSON.decode(response.responseText);
									var bankRelationPersonData = obj.data;	
									new BankRelationPersonWindow({isRead : true,type :'read',bankRelationPersonData : bankRelationPersonData}).show();		
								},
								failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
								},
								params: { id: rows[0].data.id }
							});	
						}
						
					}
				},new Ext.Toolbar.Separator({
					hidden : isGranted('_detail_grkh') ? false : true
				}),{
					text : '编辑',
					hidden : isGranted('_edit_yhkh')?false:true,
					tooltip : '编辑选中的银行联系人信息',
					iconCls : 'btn-edit',
					scope : this,
					handler : function() {
						var rows = this.centerPanel.getSelectionModel().getSelections();
						if(rows.length==0){
							Ext.ux.Toast.msg('操作信息','请选择记录!');
							return;
						}else if(rows.length>1){
							Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
							return;
						}else{
							Ext.Ajax.request({
								url : __ctxPath+'/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
								method : 'POST',
								scope : this,
								success : function(response,request){
									var obj = Ext.util.JSON.decode(response.responseText);
									var bankRelationPersonData = obj.data;	
									new BankRelationPersonWindow({listPanel :this.centerPanel,type :'edit',url :__ctxPath+'/creditFlow/customer/bankRelationPerson/updateCustomerBankRelationPerson.do',bankRelationPersonData : bankRelationPersonData}).show();
								},
								failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
								},
								params: { id: rows[0].data.id }
							});	
						}
						
					}
				},new Ext.Toolbar.Separator({
					hidden : isGranted('_edit_yhkh') ? false : true
				}),{
					text : '删除',
					hidden : isGranted('_remove_yhkh')?false:true,
					tooltip : '删除选中的银行联系人信息',
					iconCls : 'btn-del',
					scope : this,
					handler : function() {
						var selected = this.centerPanel.getSelectionModel().getSelections();
						var thisPanel = this.centerPanel;
						var len = selected.length ;
						var list = "" ;
						for(var j = 0 ; j < len ; j ++){
							if(j == (len-1)){
								list += selected[j].id ;
							}else
								list += selected[j].id + ",";
						}
						if (0 == len ) {
							Ext.MessageBox.alert('状态', '请选择一条记录!');
						}else{
							Ext.MessageBox.confirm('确认删除', '您确认要删除所选记录吗？', function(btn) {
								if (btn == 'yes') {
									Ext.Ajax.request({
										url : __ctxPath+'/creditFlow/customer/bankRelationPerson/deleteBankRelationPersonCustomerBankRelationPerson.do',
										method : 'POST',
										scope : this,
										success : function() {
											Ext.ux.Toast.msg('状态', '删除成功!');
											thisPanel.getStore().reload();
										},
										failure : function(result, action) {
											Ext.ux.Toast.msg('状态','删除失败!');
										},
										params: { listId: list }
									});
								}
							});
						}
					
					}
				},new Ext.Toolbar.Separator({
					hidden : (isGranted('_remove_yhkh') ? false : true)||(isGranted('_export_yhkh')?false:true)
				}),{
					text : '导出到Excel',
					hidden : isGranted('_export_yhkh')?false:true,
					tooltip : '导出所有银行客户到Excel',
					iconCls : 'btn-xls',
					scope : this,
					handler : function() {
						window.open(__ctxPath+'/creditFlow/customer/bankRelationPerson/outputExcelCustomerBankRelationPerson.do','_blank');
					}
				}]
		});
		this.centerPanel= new HT.GridPanel({
			region : 'center',
			id : 'gPanelBankRelationPerson',
			store : this.store,
			colModel : this.cModel,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			autoExpandColumn : 8,
			loadMask : this.myMask,
			autoWidth : true,
			height : Ext.getBody().getHeight()-100,
			tbar : tbar,
			columns :[
			{
				header : '所属分公司',
				hidden :  this.isHiddenBranch?true:false,//
				width : 100,
				dataIndex : 'orgName'
			},
				{
				header : "联系人姓名",
				width : 120,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "性别",
				width : 60,
				sortable : true,
				dataIndex : 'sexvalue'
			}/*, {
				header : "婚姻状况",
				width : 80,
				sortable : true,
				dataIndex : 'marriagename'
			}*/, {
				header : "联系电话",
				width : 120,
				sortable : true,
				dataIndex : 'blmtelephone'
			}, {
				header : "手机号码",
				width : 100,
				sortable : true,
				dataIndex : 'blmphone'
			} ,{
				header : "电子邮件",
				width : 130,
				sortable : true,
				dataIndex : 'email'
			},{
				header : "银行名称",
				width : 170,
				sortable : true,
				dataIndex : 'bankname'
			},{
				header : "支行名称",
				width : 170,
				sortable : true,
				dataIndex : 'fenbankvalue'
			},{
				header : "职务",
				width : 120,
				sortable : true,
				dataIndex : 'duty'
			}
			],
			listeners : {
				scope : this,
				'rowdblclick' : function(grid,index,e){
					//this.seeBankRelationPerson();
				}
			}
		});
	},//end of initUIComponents
	
	//查询
	searchByCondition : function() {
		this.store.baseParams.companyId =this.isHiddenBranch == false? this.searchPanel.getForm().findField('companyId').getValue():1;
		this.store.baseParams.name = this.searchPanel.getForm().findField('name').getValue();
		this.store.baseParams.duty = this.searchPanel.getForm().findField('duty').getValue();		
		this.store.baseParams.bankname = this.searchPanel.getForm().findField('bankname').getValue();
		this.store.load({
			params : {
				start : 0,
				limit : 25
			}
		});
	}
});