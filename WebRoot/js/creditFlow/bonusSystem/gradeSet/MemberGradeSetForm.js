/**
 * SystemBonusRuleForm.js
 */


MemberGradeSetForm = Ext.extend(Ext.Window, {
	isLook : false,
	
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		MemberGradeSetForm.superclass.constructor.call(this, {
				
					layout : 'fit',
					autoScroll:true,
				
					closable:true,
					items : this.formPanel,
					modal : true,
					height : 200,
					width :600,
					maximizable : true,
					title : this.title,
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
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
		
		var leftlabel = 100;
		var rightlabel = 100;
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
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [{
								xtype : 'hidden',
								name : 'memberGradeSet.levelId'
						},{
							columnWidth : 0.5,
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign :'right',
							items:[{
										xtype : 'numberfield',
										baseChars:"0123456789",
										maxValue:99999999999,
										fieldLabel : '会员等级',
										name : 'memberGradeSet.levelName',
										blankText : '会员等级为必填内容',
										allowBlank : false,
										anchor : '100%',
										readOnly : this.isReadOnly,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
							
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : rightlabel,
						labelAlign :'right',
						items : [{
							 
										xtype : 'numberfield',
										fieldLabel : '所需积分',
										baseChars:"0123456789",
										maxValue:99999999999,
										hideTrigger:true,
										name : 'memberGradeSet.levelMinBonus',
										blankText : '所需积分为必填内容',
										allowBlank : false,
										anchor : '100%',
										readOnly : this.isKeyReadOnly,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
					},{
						columnWidth : 1, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : rightlabel,
						labelAlign :'right',
						items : [{
										xtype : 'textfield',
										fieldLabel : '等级描述',
										name : 'memberGradeSet.levelMark',
										anchor : '100%',
										readOnly : this.isKeyReadOnly,
										listeners : {
											'afterrender':function(com){
											    com.clearInvalid();
											}
										}
									}]
					}]
			}]
		});
		
		// 加载表单对应的数据
		if (this.levelId != null && this.levelId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/bonusSystem/findMemberGradeSet.do?levelId='+this.levelId,
				
				root : 'data',
				preName : ['memberGradeSet'],
				scope:this,
				success : function(response, request) {
					var obj=Ext.util.JSON.decode(response.responseText);
					this.getCmpByName('memberGradeSet.levelId').setValue( obj.data.memberGradeSet.levelId);
					this.getCmpByName('memberGradeSet.levelName').setValue( obj.data.memberGradeSet.levelName);
					this.getCmpByName('memberGradeSet.levelMinBonus').setValue(obj.data.memberGradeSet.levelMinBonus);
					this.getCmpByName('memberGradeSet.levelMark').setValue(obj.data.memberGradeSet.levelMark);
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
		var flashTargat =this.flashTargat;
		var formpanelRet=this.formPanel;
		Ext.Msg.confirm('信息确认', '是否确认保存会员等级设置', function(btn) {
			if (btn == 'yes') {
				if(formpanelRet.getForm().isValid()){
					formpanelRet.getForm().submit({
						clientValidation: false,
						url :__ctxPath + '/bonusSystem/saveMemberGradeSet.do',
						method : 'post',
				  	    waitMsg : '数据正在提交，请稍后...',
						scope: this,
						success : function(request,response) {//数据添加成功后进行的处理
							//刷新添加后的数据信息
							flashTargat.getStore().reload();
							formpanelRet.ownerCt.close();
							var obj = Ext.util.JSON.decode(response.response.responseText);
							
							Ext.ux.Toast.msg('操作信息',obj.msg);
							
						},
						failure : function(fp, action) {
							Ext.MessageBox.show({
								title : '操作信息',
								msg : '信息保存出错，请联系管理员！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
							});
						}
					});
				}
			}
		})
		
	}// end of save

});