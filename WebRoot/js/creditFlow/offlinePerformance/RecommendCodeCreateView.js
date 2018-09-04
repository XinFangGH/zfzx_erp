/**
 * 人员架构列表
 * @class RecommendCodeCreateView
 * @extends Ext.Panel
 */

RecommendCodeCreateView = Ext.extend(Ext.Panel, {
	constructor : function(config) {
		Ext.applyIf(this, config);
		this.initUIComponents();
		RecommendCodeCreateView.superclass.constructor.call(this, {
			id : 'RecommendCodeCreateView',
			height : 450,
			autoScroll : true,
			layout : 'border',
			title : '员工推荐码生成',
			iconCls:"menu-finance",
			// items : [this.searchPanel, this.centerPanel]
			items : [ this.centerPanel]
		});
	},
	initUIComponents : function() {
		var isShow = false;
		if (RoleType == "control") {
			isShow = true;
		}
		this.pageSize = 25;
		this.store = new Ext.data.JsonStore({
			url : __ctxPath+ '/system/userListAppUser.do',
			totalProperty : 'totalCounts',
			root : 'result',
			remoteSort : true,
			fields : [{name : 'userId'},{name:'fullname'},{name:'userNumber'},{name:'username'},{name:'p2pAccount'},
					  {name : 'plainpassword'},{name:'mobile'},{name:'email'},{name:'status'},{name:'isForbidden'},
					  {name : 'bpCustMemberId'}]
		});
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});

		// 查询面板
		this.searchPanel = new Ext.form.FormPanel({
			height : 45,
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
			items : [isShow ? {
				columnWidth : 0.2,
				labelWidth : 65,
				bodyStyle : 'padding:5px 0px 0px 0px',
				items : [{
					xtype : "combo",
					anchor : "100%",
					fieldLabel : '所属分公司',
					hiddenName : "companyId",
					displayField : 'companyName',
					valueField : 'companyId',
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath+ '/system/getControlNameOrganization.do',
						fields : ['companyId', 'companyName']
					})
				}]
			}: {columnWidth : 0.01}, {
				columnWidth : isShow ? 0.15 : 0.2,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '姓名',
					name : 'fullname',
					anchor : '100%'
				}]
			}, {
				columnWidth : isShow ? 0.12 : 0.14,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '推荐码',
					name : 'plainpassword',
					anchor : '100%'
				}]
			}, {
				columnWidth : isShow ? 0.12 : 0.14,
				labelWidth : 40,
				items : [{
					xtype : 'textfield',
					fieldLabel : '手机号',
					name : 'mobile',
					anchor : '100%'
				}]
			},{
				columnWidth : 0.07,
				items : [{
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
			}, {
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

		// 加载数据
		this.store.load({
			scope : this,
			params : {
				start : 0,
				limit : this.pageSize,
				isAll : isGranted('_seeAll_erp')
			}
		});
		var personStore = this.store;
		var tbar = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-p2p',
				text : '开通P2P账户',
				xtype : 'button',
				hidden : isGranted('_openingP2PAccount') ? false : true,
				scope : this,
				type:0,
				handler : this.addP2pUser
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_openingP2PAccount') ? false : true
			}),{
				iconCls : 'btn-unablep2p',
				text : '绑定P2P账户',
				xtype : 'button',
				hidden : isGranted('_bindingP2PAccount') ? false : true,
				scope : this,
				type:1,
				handler : this.addP2pUser
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_bindingP2PAccount') ? false : true
			}),{
				iconCls : 'btn-forbid',
				text : '禁用P2P账户',
				xtype : 'button',
				hidden : isGranted('_closeP2PAccount') ? false : true,
				scope : this,
				type:0,
				handler : this.changeP2PStatus
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_closeP2PAccount') ? false : true
			}),{
				iconCls : 'btn-add1',
				text : '开启P2P账户',
				xtype : 'button',
				hidden : isGranted('_openP2PAccount') ? false : true,
				scope : this,
				type:1,
				handler : this.changeP2PStatus
			}, new Ext.Toolbar.Separator({
				hidden : isGranted('_openP2PAccount') ? false : true
			}),{
				text : '导出列表',
				iconCls : 'btn-xls',
				scope : this,
				hidden : isGranted('_exportP2PAccount') ? false : true,
				handler : function() {
					var companyId = (isShow==true?this.getCmpByName("companyId").getValue():null);
					var plainpassword=this.getCmpByName("plainpassword").getValue();//推荐码
					var fullname = this.getCmpByName("fullname").getValue();//姓名
					var mobile = this.getCmpByName("mobile").getValue();//手机号
					window.open(__ctxPath+ '/customer/exportExcelBpCustRelation.do?type=0&companyId='+companyId
								+'&plainpassword='+plainpassword+'&fullname='+fullname+"&mobile="+mobile,'_blank');
				}
			}]
		});

		this.centerPanel = new HT.GridPanel({
			region : 'center',
			tbar : tbar,
			clicksToEdit : 1,
			store : this.store,
			height : 450,
			loadMask : this.myMask,
			defaults : {
				sortable : true,
				menuDisabled : false,
				width : 100
			},
			viewConfig : {
				forceFit : true,
				autoFill : true
			},
			columns : [{
				header : "所属分公司",
				align:'center',
				width : 160,
				sortable : true,
				hidden : RoleType == "control" ? false : true,
				dataIndex : 'companyName'
			}, {
				header : "姓名",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'fullname'
			}, {
				header : "工号",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'userNumber'
			},{
				header : "ERP账号",
				align:'center',
				width : 100,
				sortable : true,
				dataIndex : 'username',
				renderer : function(v) {
					return v.split('@')[0];
				}
			}, {
				header : "P2P账号",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'p2pAccount'
			}, {
				header : "推荐码",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'plainpassword'
			}, {
				header : "手机号",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'mobile'
			},{
				header : "邮箱",
				align:'center',
				width : 100,
				sortable : true,
				dataIndex : 'email'
			},{
				header : "ERP是否禁用",
				align:'center',
				width : 100,
				sortable : true,
				dataIndex : 'status',
				renderer : function(v) {
					if (v == "0")
						return "是";
					if (v == "1")
						return "否";
				}
			},{
				header : "P2P是否禁用",
				width : 100,
				align:'center',
				sortable : true,
				dataIndex : 'isForbidden',
				renderer : function(v,a,b,c,d) {
					//先判断是否开通过p2p账号
					if(b.data.p2pAccount){
						if(v == "1"){
							return "是";
						}else{
							return "否";
						}
					}
				}
			}]
		});
	},

	// 查询
	searchByCondition : function() {
		this.store.baseParams.fullname = this.searchPanel.getForm().findField('fullname').getValue();
		this.store.baseParams.plainpassword = this.searchPanel.getForm().findField('plainpassword').getValue();
		this.store.baseParams.mobile = this.searchPanel.getForm().findField('mobile').getValue();
		if (null != this.searchPanel.getForm().findField('companyId')) {
			this.store.baseParams.companyId = this.searchPanel.getForm().findField('companyId').getValue();
		}
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
	//添加p2p账户
	addP2pUser : function(v) {
		new RecommendCodeCreateForm({
			type:v.type//0代表开通，1代表绑定
		}).show();
		
		/*var selections = this.centerPanel.getSelectionModel().getSelections();
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		} else if (selections[0].data.p2pAccount!="") {
			Ext.ux.Toast.msg('状态', '已开通的P2P账号!!!');
			return;
		}
		var userId = selections[0].data.userId;
		var fullname = selections[0].data.fullname;//姓名
		var mobile = selections[0].data.mobile;//手机号
		var email = selections[0].data.email;//邮箱
		new RecommendCodeCreateForm({
			userId : userId,
			fullname:fullname,
			mobile:mobile,
			email:email,
			type:v.type//0代表开通，1代表绑定
		}).show();*/
	},
	//禁用P2P账号
	changeP2PStatus:function(v){
		var gridPanel=this.centerPanel;
		var selections = gridPanel.getSelectionModel().getSelections();
		var len = selections.length;
		if (len > 1) {
			Ext.ux.Toast.msg('状态', '只能选择一条记录');
			return;
		} else if (0 == len) {
			Ext.ux.Toast.msg('状态', '请选择一条记录');
			return;
		} else if (selections[0].data.p2pAccount=="") {
			Ext.ux.Toast.msg('状态', '请先开通P2P账号!!!');
			return;
		}
		var isForbidden=0;
		//type用来标识是禁用操作还是开启操作
		if(v.type==0){
			isForbidden=1;
		}
		Ext.Ajax.request({
			url :__ctxPath + '/customer/changeP2PStatusBpCustRelation.do',
			params : {
				bpCustMemberId : selections[0].data.bpCustMemberId,
				isForbidden:isForbidden
			},
			scope:this,
			method : 'post',
			success : function(response) {
				var result = Ext.util.JSON.decode(response.responseText);
				if (result.success) {
					Ext.ux.Toast.msg('提示信息', result.msg);
					gridPanel.getStore().reload();
					this.close();
				} else {
					Ext.ux.Toast.msg('提示信息', result.msg);
				}
			}
		})
	}
});