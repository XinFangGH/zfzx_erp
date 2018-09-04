/**
 * SystemBonusRuleForm.js
 */


SystemBonusRuleForm = Ext.extend(Ext.Window, {
	isLook : false,
	//isReadOnly : false,
	// 构造函数
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SystemBonusRuleForm.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 400,
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
				items : [
						{
							columnWidth : 1,
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign :'right',
							items:[{
								html : "<font color=red>积分规则————开启条件</font>"
							}]
						},{
								xtype : 'hidden',
								name : 'webBonusSetting.bonusId'
						},{
							columnWidth : .5, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : rightlabel,
							labelAlign :'right',
							items : [/*{
											xtype : 'textfield',
											fieldLabel : '积分规则Key',
											name : 'webBonusSetting.flagKey',
											blankText : 'Action方法名为必填内容',
											allowBlank : false,
											anchor : '100%',
											readOnly : this.isKeyReadOnly,
											listeners : {
												'afterrender':function(com){
												    com.clearInvalid();
												}
											}
									},*/{
									xtype : 'combo',
									mode : 'local',
									fieldLabel : '积分规则Key',
									hiddenName : 'webBonusSetting.flagKey',
									displayField : 'name',
									valueField : 'id',
									editable : false,
									allowBlank : false,
									triggerAction : 'all',
									readOnly : this.isKeyReadOnly,
									anchor : '100%',
									store : new Ext.data.SimpleStore({
										fields : ["name", "id"],
										data : [
										["登录", "Login"],
										["注册", "register"],
										["投标", "tender"],
										["充值", "recharge"],
										["推荐", "invite"],
										["推荐第一次投标", "inviteOnce"]
										]
									})
								}]
						},{
							columnWidth : 0.5,
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign :'right',
							items:[{
										xtype : 'textfield',
										fieldLabel : 'Service类名',
										name : 'webBonusSetting.userAction',
										blankText : 'Action类名为必填内容',
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
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : rightlabel,
						labelAlign :'right',
						items : [{
										xtype : 'textfield',
										fieldLabel : 'Service方法名',
										name : 'webBonusSetting.userActionKey',
										blankText : 'Action方法名为必填内容',
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
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : rightlabel,
						labelAlign :'right',
						items : [
								   {					
										fieldLabel : '会员等级要求',
										xtype : 'textfield',
										hiddenName : 'webBonusSetting.memberLevel',
										allowBlank : false,
										readOnly : this.isReadOnly,
										anchor : '100%',
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										triggerAction : 'all',
										store : new Ext.data.ArrayStore({
											url :__ctxPath + '/bonusSystem/jsonArrMemberGradeSet.do',
											fields : ['itemId', 'itemName'],
											autoLoad : true
										}),
										mode : 'remote',
										editable : false,
										blankText : "会员等级要求不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
												})
												combox.clearInvalid();
											}
										}
								},{
								  	xtype:"hidden",
								  	name : 'webBonusSetting.memberLevelValue'
								}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						labelAlign :'right',
						items : [{
								fieldLabel : '是否开启',
								hiddenName : 'webBonusSetting.bonusswitch',
								anchor : "100%",
								xtype : 'combo',
								mode : 'local',
								valueField : 'value',
								allowBlank : false,
								editable : false,
								readOnly : this.isReadOnly,
								displayField : 'item',
								store : new Ext.data.SimpleStore({
									fields : ["item","value"],
									data : [["开启",0], ["关闭",1]]
								}),
								triggerAction : "all"
							}]
					},{
							columnWidth : 1,
							layout : "form", // 从上往下的布局
							labelWidth : leftlabel,
							labelAlign :'right',
							items:[{
								html : "<font color=red>积分规则————奖励配置</font>"
							}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						labelAlign :'right',
						items : [{
								fieldLabel : '奖惩措施',
								hiddenName : 'webBonusSetting.isBonus',
								anchor : "100%",
								xtype : 'combo',
								mode : 'local',
								valueField : 'value',
								allowBlank : false,
								editable : false,
								readOnly : this.isReadOnly,
								displayField : 'item',
								store : new Ext.data.SimpleStore({
									fields : ["item","value"],
									data : [["增加积分",1], ["扣除积分",2]]
								}),
								triggerAction : "all"
							}]
					},{
						columnWidth : .5, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : leftlabel,
						labelAlign :'right',
						items : [{
								fieldLabel : '积分类型',
								hiddenName : 'webBonusSetting.bonusType',
								anchor : "100%",
								xtype : 'combo',
								mode : 'local',
								valueField : 'value',
								allowBlank : false,
								editable : false,
								readOnly : this.isReadOnly,
								displayField : 'item',
								store : new Ext.data.SimpleStore({
									fields : ["item","value"],
									data : [
										["普通积分",1],
										["论坛积分",3]
									]
								}),
								triggerAction : "all"
							}]
					},{
				            	columnWidth:0.5,
				                layout: 'form',
				                labelWidth : leftlabel,
				                labelAlign :'right',
				                items :[{
										xtype : 'textfield',
										fieldLabel : '奖励周期时长',
										name : 'webBonusSetting.bomusPeriod',
										allowBlank : false,
										anchor : '100%',
										blankText : '奖励周期时长',
										readOnly : this.isReadOnly
									}]
			            },{
				            	columnWidth:0.5,
				                layout: 'form',
				                labelWidth : leftlabel,
				                labelAlign :'right',
				                items :[{
									fieldLabel : '奖励周期单位',
									hiddenName : 'webBonusSetting.bomusPeriodType',
									anchor : "100%",
									xtype : 'combo',
									mode : 'local',
									valueField : 'value',
									allowBlank : false,
									editable : false,
									readOnly : this.isReadOnly,
									displayField : 'item',
									store : new Ext.data.SimpleStore({
										fields : ["item","value"],
										data : [["一次性","once"],["分钟","min"], ["小时","hour"], ["天","date"]]
									}),
									triggerAction : "all"
								}]
				            },{
									columnWidth : .5, // 该列在整行中所占的百分比
									layout : "form", // 从上往下的布局
									labelWidth : rightlabel,
									labelAlign :'right',
									items : [{
										xtype : 'textfield',
										fieldLabel : '周期内奖励次数',
										name : 'webBonusSetting.periodBonusLimit',
										allowBlank : false,
										anchor : '100%',
										blankText : '周期内奖励次数为必填内容',
										readOnly : this.isReadOnly
									}]
							},{
								columnWidth : .5, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '奖励分值',
											name : 'webBonusSetting.bonus',
											allowBlank : false,
											anchor : '100%',
											blankText : '奖励分值为必填内容',
											readOnly : this.isReadOnly
										}]
							},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{
											xtype : 'textfield',
											fieldLabel : '积分用意(限10字内)',
											name : 'webBonusSetting.bonusIntention',
											maxLength: 10,
											anchor : '100%',
											blankText : '积分用意',
											allowBlank : false,
											readOnly : this.isReadOnly
										}]
					
							},{
								columnWidth : 1, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								labelWidth : rightlabel,
								labelAlign :'right',
								items : [{
											xtype : 'textarea',
											fieldLabel : '描述',
											name : 'webBonusSetting.description',
											anchor : '100%',
											blankText : '描述',
											readOnly : this.isReadOnly
										}]
					
								}]
			}]
		});
		
		// 加载表单对应的数据
		if (this.bonusId != null && this.bonusId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/bonusSystem/getWebBonusSetting.do',
				params : {
					bonusId : this.bonusId
				},
				root : 'data',
				preName : ['webBonusSetting','record'],
				success : function(response, options) {
					var obj=Ext.util.JSON.decode(response.responseText);
					
					var sendMsgType=obj.data.webBonusSetting.sendMsgType;
					
					if(sendMsgType!=""){
						
						var ss=sendMsgType.split(",")
						for(var i=0;i<ss.length;i++){
							if(ss[i]=="mail"){
								
							}
						}
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
		Ext.Msg.confirm('信息确认', '是否确认保存奖励规则', function(btn) {
			if (btn == 'yes') {
				if(formpanelRet.getForm().isValid()){
					formpanelRet.getForm().submit({
						clientValidation: false,
						url :__ctxPath + '/bonusSystem/saveWebBonusSetting.do',
						method : 'post',
						waitMsg : '数据正在提交，请稍后...',
						scope: this,
						success : function(fp, action) {
							 
							var obj=Ext.util.JSON.decode(action.response.responseText)
							Ext.ux.Toast.msg('操作信息',obj.msg);
							flashTargat.getStore().reload();
							formpanelRet.ownerCt.close()
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