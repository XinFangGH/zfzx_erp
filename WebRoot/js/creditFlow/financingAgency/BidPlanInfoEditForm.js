/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
BidPlanInfoEditForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BidPlanInfoEditForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 150,
					width : 500,
					maximizable : true,
					title : '招标项目名称更改',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								disabled : this.isReadOnly,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								disabled : this.isReadOnly,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			frame : true,
			items : [{
				xtype : 'hidden',
				name : 'plBidPlan.bidId'
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 100,
				labelAlign : 'right',
				border : false,
				items : [{
					xtype : 'textfield',
					fieldLabel : '招标项目名称',
					name : 'plBidPlan.bidProName',
					anchor : '100%'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 100,
				labelAlign : 'right',
				border : false,
				items : [{
					xtype : 'textfield',
					fieldLabel : '招标项目编号',
					name : 'plBidPlan.bidProNumber',
					readOnly : true,
					anchor : '100%'
				}]
			}]
		});
		if (this.bidId != null && this.bidId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
					+ '/creditFlow/financingAgency/getPlBidPlan.do?bidId='
						+ this.bidId,
				root : 'data',
				preName : 'plBidPlan',
				success : function(response, options) {
					var respText = response.responseText;
					var result = Ext.util.JSON.decode(respText);
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
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					msg : (this.id != null && this.id != 'undefined')?'保存成功':'添加成功',
					url : __ctxPath
							+ '/creditFlow/financingAgency/updateInfoPlBidPlan.do',
					callback : function(fp, action) {
						if(this.gridPanel!=null||typeof(this.gridPanel)!="undefined"){
							this.gridPanel.getStore().reload();
						}
						
						this.close();
					}
				});
	}// end of save

});
