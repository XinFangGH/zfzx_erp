//updateCommontInfo.js
updateCommontInfo = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	investPersonInfo:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		updateCommontInfo.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 500,
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
		var leftlabel = 75;
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
							xtype : "hidden",
							name : "bpCustProspectiveFollowup.followId",
							value:this.followId
					},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							items : [{
								xtype : "combo",
								triggerClass : 'x-form-search-trigger',
								hiddenName : "commentorrId1",
								editable : false,
								fieldLabel : "点评人",
								readOnly : this.isRead,
								anchor : "100%",
								allowBlank : false,
								onTriggerClick : function(cc) {
									var obj = this;
									var appuerIdObj = obj.nextSibling();
									var userIds = appuerIdObj.getValue();
									if ("" == obj.getValue()) {
										userIds = "";
									}
									new UserDialog({
										userIds : userIds,
										userName : obj.getValue(),
										single : false,
										title : "选择点评人",
										callback : function(uId, uname) {
											obj.setValue(uId);
											obj.setRawValue(uname);
											appuerIdObj.setValue(uId);
										}
									}).show();
								}
							},{
	                       	 	xtype : 'hidden',
	                        	name : 'bpCustProspectiveFollowup.commentorrId'
							}]
						},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					items : [{
								xtype : 'textarea',
								fieldLabel : '点评内容',
								anchor : '100%',
								allowBlank : false,
								readOnly : this.isRead,
								name : 'bpCustProspectiveFollowup.commentRemark'
									}]
				}]
		});
		// 加载表单对应的数据
		if (this.followId != null && this.followId != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url:__ctxPath + "/creditFlow/customer/customerProsperctiveFollowup/getBpCustProspectiveFollowup.do?followId="+this.followId,
						root : 'data',
						preName : ['bpCustProspectiveFollowup',"object"],
						success : function(resp, options) {
							var respText = resp.responseText;
							var alarm_fields = Ext.util.JSON.decode(respText);
							var commentorrName= alarm_fields.data.bpCustProspectiveFollowup.commentorName;
							var commentorrId= alarm_fields.data.bpCustProspectiveFollowup.commentorrId;
							if(""!=commentorrId &&  null!=commentorrId){
							   this.getCmpByName('commentorrId1').setValue(commentorrId);
							   this.getCmpByName('commentorrId1').setRawValue(commentorrName);
							   this.getCmpByName('commentorrId1').nextSibling().setValue(commentorrId);
							}
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
		var panel=this;
		var formPanel1 =this.formPanel
		var flashTargat=this.flashTargat
		if(formPanel1.getForm().isValid()){
			formPanel1.getForm().submit({
				    clientValidation: false, 
					url:__ctxPath+ '/creditFlow/customer/customerProsperctiveFollowup/updateBpCustProspectiveFollowup.do',
					params : {
						"followId":this.followId
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						panel.close();
						flashTargat.getStore().reload();
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
	}// end of save

});