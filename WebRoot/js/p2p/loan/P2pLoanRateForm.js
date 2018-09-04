/**
 * @author
 * @createtime
 * @class P2pLoanRateForm
 * @extends Ext.Window
 * @description P2pLoanRateForm表单
 * @company 北京互融时代软件有限公司
 */
P2pLoanRateForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pLoanRateForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 220,
					width : 500,
					maximizable : true,
					title : '利率设置信息',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								disabled : this.isReadOnly,
								handler : this.save
							}, /*{
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								disabled : this.isReadOnly,
								handler : this.reset
							}, */{
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath+'/p2p/saveP2pLoanRate.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			labelAlign : 'right',
			buttonAlign : 'center',
			height : 180,
			frame : true ,
			layout : 'column',
			items:[{
				columnWidth : .8,
				labelWidth : 120,
				layout : 'form',
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '贷款期限',
					readOnly : this.isReadOnly,
					allowBlank:false,
					name : 'p2pLoanRate.loanTime'
				}]
			},{
			 	columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 22,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>月</span> ",
					labelSeparator : '',
					anchor : "50%"
							}]
			},{
				columnWidth : .8,
				labelWidth : 120,
				layout : 'form',
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '贷款年化利率',
					readOnly : this.isReadOnly,
					allowBlank:false,
					name : 'p2pLoanRate.yearAccrualRate'
				}]
			},{
			 	columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 22,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "50%"
							}]
			},
				{
				columnWidth : .8,
				labelWidth : 120,
				layout : 'form',
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '管理咨询费年化利率',
					readOnly : this.isReadOnly,
					allowBlank:false,
					name : 'p2pLoanRate.yearManagementConsultingOfRate'
				}]
			},{
			 	columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 22,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "50%"
							}]
			},
				{
				columnWidth : .8,
				labelWidth : 120,
				layout : 'form',
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textfield',
					fieldLabel : '财务服务费年化利率',
					readOnly : this.isReadOnly,
					allowBlank:false,
					name : 'p2pLoanRate.yearFinanceServiceOfRate'
				}]
			},{
			 	columnWidth : .1, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 22,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "50%"
							}]
			},{
				xtype : 'hidden',
				name : 'p2pLoanRate.productId',
				value : this.productId
			},{
				xtype : 'hidden',
				name : 'p2pLoanRate.rateId',
				value : this.rateId
			}]
		});
		// 加载表单对应的数据
		if (this.rateId!= null && this.rateId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/p2p/getP2pLoanRate.do?rateId='+ this.rateId,
				root : 'data',
				preName : 'p2pLoanRate',
				scope : this,
				success : function(resp, options) {
					var result = Ext.decode(resp.responseText);
				}
			});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	
	/**
	 * 保存记录
	 */
	save : function() {
		var gridPanel=this.gridPanel
		var win=this;
		if (this.formPanel.getForm().isValid()) {
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				success : function(form ,action) {
						Ext.ux.Toast.msg('状态', '保存成功!');
						gridPanel.getStore().reload();
						win.destroy();
				},
				failure : function(form, action) {
					if(action.response.status==0){
						Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
					}else if(action.response.status==-1){
						Ext.ux.Toast.msg('状态','连接超时，请重试!');
					}else{
						Ext.ux.Toast.msg('状态','添加失败!');		
					}
				}
			});
		}
	}// end of save

});
