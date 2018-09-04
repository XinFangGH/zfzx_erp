Ext.ns('ConfApplyForm');
/**
 * @description 会议审核信息，添加会议审核描述
 * @author YHZ
 * @company www.credit-software.com
 * @datetime 2010-11-26 PM
 */
ConfApplyForm = Ext.extend(Ext.Window, {
	formPanel : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponent();
		ConfApplyForm.superclass.constructor.call(this, {
			id : 'ConfApplyForm',
			iconCls : 'menu-daiConfApply',
			title : '审核信息',
			layout : 'fit',
			region : 'center',
			width : 350,
			height : 200,
			minWidth : 350,
			minHeight : 200,
			plain : true,
			border : false,
			modal : true,
			items : this.formPanel,
			buttonAlign : 'center',
			buttons : [ {
				text : '同意',
				iconCls : 'btn-confApply-yes',
				handler : this.agree.createCallback(this)
			}, {
				text : '不同意',
				iconCls : 'btn-confApply-no',
				handler : this.unAgree.createCallback(this)
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				handler : this.close,
				scope : this
			} ],
			keys : {
				key : Ext.EventObject.ENTER,
				fn : this.agree.createCallback(this),
				scope : this
			}
		});
	},// end of this constructor

	initUIComponent : function() {
		this.formPanel = new Ext.FormPanel( {
			id : 'ConfApplyFormPanel',
			layout : 'form',
			region : 'center',
			style : 'padding:5px 5px 5px 5px;',
			defaultType : 'textfield',
			url : __ctxPath + '/admin/applyConference.do',
			items : [ {
				name : 'confId',
				xtype : 'hidden',
				value : this.confId
			}, {
				style : 'margin-top : 5px;',
				name : 'confTopic',
				fieldLabel : '会议标题',
				width : 200,
				readOnly : true,
				value : this.confTopic
			}, {
				name : 'checkReason',
				fieldLabel : '审核描述',
				width : 200,
				height : 80,
				xtype : 'textarea',
				maxLength : 512,
				maxLengthText : '会议审核描述不能超过512个字符！'
			} ]
		});
	},// end of this initUIComponent

	/**
	 * 清空
	 */
	reset : function() {
		Ext.getCmp('ConfApplyFormPanel').getCmpByName('checkReason').setValue(
				'');
	},

	/**
	 * 审核通过[首先判断会议室是否处于空闲状态，方可通过审核]
	 */
	agree : function(self) {
		if (self.formPanel.getForm().isValid()) {
			self.formPanel.getForm().submit( {
				url : __ctxPath + '/admin/applyConference.do?status=1',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function() {
					Ext.ux.Toast.msg('操作提示', '恭喜您,会议审核成功！');
					self.close();
					Ext.getCmp('DaiConfApplyGrid').getStore().reload({
						params : {
							limit : 25,
							start : 0
						}
					});
				},
				failure : function(fm, action) {
					var res = Ext.util.JSON.decode(action.response.responseText);
					if(res.msg == null)
						res ='会议审核异常！';
					Ext.MessageBox.show( {
						title : '操作信息',
						msg : res.msg,
						buttons : Ext.MessageBox.OK,
						icon : 'ext-mb-error'
					});
					self.close();
					Ext.getCmp('DaiConfApplyGrid').getStore().reload({
						params : {
							limit : 25,
							start : 0
						}
					});
				}
			});
		}
	},

	/**
	 * 未通过
	 */
	unAgree : function(self) {
		if (self.formPanel.getForm().isValid()) {
			self.formPanel.getForm().submit( {
				url : __ctxPath + '/admin/applyConference.do?status=2',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				success : function() {
					Ext.ux.Toast.msg('操作提示', '恭喜您,会议审核成功！');
					Ext.getCmp('DaiConfApplyGrid').getStore().reload({
						params : {
							limit : 25,
							start : 0
						}
					});
					self.close();
				},
				failure : function(fm, action) {
					Ext.ux.Toast.msg('操作提示', '对不起，会议审核未通过失败！');
					Ext.getCmp('ConfApplyFormPanel').getCmpByName('checkReason').setValue(
					'');
				}
			});
		}
	}
});