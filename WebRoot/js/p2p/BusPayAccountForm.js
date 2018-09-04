/**
 * @author
 * @createtime
 * @class BusPayAccountForm
 * @extends Ext.Window
 * @description BusPayAccountForm表单
 * @company 智维软件
 */
BusPayAccountForm = Ext.extend(Ext.Window, {
	accountId:null,
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg && typeof(_cfg.accountId) != "undefined") {
			this.accountId = _cfg.accountId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BusPayAccountForm.superclass.constructor.call(this, {
			id : 'BusPayAccountForm',
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height: 180,
			width : 320,
			frame:true,
			maximizable : true,
			title : '支付账户信息',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				handler : this.save
			},{
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			}]
		});
	},
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
			items : [{
				name : 'busPayAccount.id',
				xtype : 'hidden',
				value:this.accountId
			},{
				name : 'busPayAccount.accountName',
				xtype : 'hidden'
			},{
				fieldLabel : "支付账户类型",
				xtype : "dickeycombo",
				hiddenName : "busPayAccount.accountType",
				nodeKey : 'yun_accountType',
				editable : false,
				listeners : {
					afterrender : function(combox) {
						var st = combox.getStore();
						st.on("load", function() {
							combox.setValue(combox.getValue());
							combox.clearInvalid();
						})
			       }
				}
			},{
				fieldLabel : '真实姓名',
				name : 'busPayAccount.trueName',
				allowBlank :false
			},{
				fieldLabel : '账号',
				name : 'busPayAccount.account',
				allowBlank :false
			}]
		});
		if(this.accountId){
			this.formPanel.loadData({
				url:__ctxPath+'/p2p/getBusPayAccount.do?id='+this.accountId,
				root:'data',
				preName:'busPayAccount',
				scope:this,
				success:function(resp,options){
					var object = Ext.util.JSON.decode(resp.responseText)
				}
			});
		}
	},
	/**
	 * 取消
	 * @param {}
	 * window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var accountName=this.getCmpByName('busPayAccount.accountName');
		var trueName=this.getCmpByName('busPayAccount.trueName');
		var account=this.getCmpByName('busPayAccount.account');
		if(trueName.getValue() && account.getValue() ){
			accountName.setValue(trueName.getValue()+"+"+account.getValue());
		}
		$postForm({
			formPanel:this.formPanel,
			scope:this,
			url:__ctxPath + '/p2p/saveBusPayAccount.do',
			callback:function(fp,action){
				var gridPanel = Ext.getCmp('BusPayAccountGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}
});