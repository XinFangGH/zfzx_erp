/**
 *	backCompensatoryRecord.js
 *	散标代偿款项台账回款流水登记表单
 *	主要作用：散标代偿款项台账回款流水登记表单
 *  add by linyan  2015-10-26
 */
 
 backCompensatoryRecord = Ext.extend(Ext.Window, {
	isLook : false,
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		win = this;
		backCompensatoryRecord.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 500,
					width : 1000,
					maximizable : true,
					title : this.titleChange,
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		//代偿款项信息
		this.compensatoryFiances=new compensatoryFiances({
		
		});
		this.CompensatoryFlow=new CompensatoryFlow({
			compensatoryId:this.compensatoryId,
			autoHeight:true,
			showPaging:false
		});
		//回款登记信息
		this.compensatoryRecordForm	=new compensatoryRecordForm({
			compensatoryId:this.compensatoryId
		});
		
		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
			items : [{

				xtype : 'fieldset',
				title : '代偿款项信息',
				bodyStyle : 'padding-left: 0px',
				collapsible : true,
				items : [this.compensatoryFiances]
			},{

				xtype : 'fieldset',
				title : '已回款列表',
				bodyStyle : 'padding-left: 0px',
				collapsible : true,
				items : [this.CompensatoryFlow]
			},{
				xtype : 'fieldset',
				title : '回款登记信息',
				collapsible : true,
				items : [this.compensatoryRecordForm]
			}]
		});
		
		// 加载表单对应的数据
		if (this.compensatoryId != null && this.compensatoryId != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url:__ctxPath + "/compensatory/getPlBidCompensatory.do?id="+this.compensatoryId,
						root : 'data',
						preName : 'plBidCompensatory',
						success : function(resp, options) {
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
		//this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
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
		var flashTargat =this.flashTargat;
		var formPanel1 =this.formPanel;
		if(formPanel1.getForm().isValid()){
		formPanel1.getForm().submit({
			 clientValidation: false,
					url :__ctxPath+ '/compensatory/savePlBidCompensatoryFlow.do',
					params : {
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var obj = Ext.util.JSON.decode(action.response.responseText);
						Ext.ux.Toast.msg('操作信息', obj.msg);
						this.close();
						flashTargat.reload();
					},
					failure : function(fp, action) {
						var obj = Ext.util.JSON.decode(action.response.responseText);
						Ext.MessageBox.show({
							title : '操作信息',
							msg : obj.msg,
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					}
				});
		}
	}// end of save

});
 
 




 