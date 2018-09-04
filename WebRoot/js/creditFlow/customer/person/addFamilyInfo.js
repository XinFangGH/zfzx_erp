//addFamilyInfo

addFamilyInfo = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		if(typeof(_cfg.isReadOnly) != "undefined")
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		addFamilyInfo.superclass.constructor.call(this, {
					id : 'addFamilyInfo',
					layout : 'fit',
					autoScroll:true,
					items : [this.formPanel],
					modal : true,
					height : 400,
					width : 800,
					maximizable : true,
					title : '个人家庭信息',
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
		
		this.formPanel = new Ext.FormPanel( {
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'column',
			labelWidth:100,
			items : [{
							columnWidth : .40,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							items : [{
									xtype : "hidden",
									name : "person.id"
								},{
								xtype : 'radiogroup',
								fieldLabel : '是否户主',
								disabled :this.isRead,
								items : [{
									boxLabel : '是',
									name : 'person.isheadoffamily',
									id:'isheadoffamilytrue',
									inputValue : true,
									check:true
								}, {
									boxLabel : '否',
									name : 'person.isheadoffamily',
									id:'isheadoffamilyfalse',
									inputValue : false
								}]
							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
								columnWidth : .4,
								layout:'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'textfield',
									fieldLabel : '家庭人数',
									name : 'person.personCount',
									readOnly : this.isRead
								}]
							},{
								columnWidth : .6,
								layout : 'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'textfield',
									fieldLabel : '家庭住址',
									name : 'person.familyaddress',
									readOnly : this.isRead
								}]
							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							labelWidth : 80,
							items : [{
								columnWidth : 0.4,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									name : 'person.familypostcode',
									regex:/^[0-9]{6}$/,
									regexText : '邮政编码格式不正确',
									readOnly : this.isRead
								}]
							},{
								columnWidth : 0.60,
								layout : 'form',
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'textfield',
									fieldLabel : '社区名',
									name : 'person.communityname',
									readOnly : this.isRead
								}]
							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
							columnWidth : .4,
							layout : 'form',
							labelWidth : 80,
							items : [{
								xtype : 'textfield',
								fieldLabel : '子女个数',
								name : 'person.childrenCount',
								readOnly : this.isRead,
								anchor : '100%'
							}]
						},{
								columnWidth : 0.6,
								layout : 'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : "dickeycombo",
									nodeKey : 'jzzk',
									fieldLabel : '居住状况',
									hiddenName : 'person.employway',
									emptyText : '请选择居住状况',
									width : 80,
									editable : false,
									readOnly : this.isRead,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}

								}]

							}, {
								columnWidth : 0.4,
								layout : 'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : "dickeycombo",
									nodeKey : 'homeshape',
									fieldLabel : '现住宅形式',
									hiddenName : 'person.homeshape',
									emptyText : '请选择现住宅形式',
									width : 80,
									editable : false,
									readOnly : this.isRead,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												if (combox.getValue() == 0
														|| combox.getValue() == 1
														|| combox.getValue() == ""
														|| combox.getValue() == null) {
													combox.setValue("");
												} else {
													combox.setValue(combox
															.getValue());
												}
												combox.clearInvalid();
											})
										}
									}
								}]
							}, {
								columnWidth : 0.5,
								layout : 'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'numberfield',
									fieldLabel : '现住宅面积',
									name : 'person.housearea',
									readOnly : this.isRead
								}]
							},{								
							columnWidth : .1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							defaults : {
								anchor : anchor
							},
							labelWidth : 40,
							items : [{
								fieldLabel : "平方米 ",
								labelSeparator : ''
							}]								
						}]
						},{
							columnWidth : .4,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								xtype : 'radiogroup',
								fieldLabel : '是否有房产',
								disabled :this.isRead,
								items : [{
									boxLabel : '有',
									name : 'person.havehouse',
									id:'ishavehousetrue',
									inputValue : true
								}, {
									boxLabel : '无',
									name : 'person.havehouse',
									inputValue : false,
									id:'ishavehousefalse',
									check:true
								}]
							}]
						},{
							columnWidth : .6,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								xtype : 'radiogroup',
								fieldLabel : '是否有房贷',
								disabled :this.isRead,
								items : [{
									boxLabel : '有',
									name : 'person.havehouseloan',
									id:'ishavehouseloantrue',
									inputValue : true
								}, {
									boxLabel : '无',
									name : 'person.havehouseloan',
									id:'ishavehouseloanfalse',
									inputValue : false,
									check:true
								}]
							}]
						},{
							columnWidth : .4,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								xtype : 'radiogroup',
								fieldLabel : '是否有车产',
								disabled :this.isRead,
								items : [{
									boxLabel : '有',
									name : 'person.havecar',
									id:"ishavecartrue",
									inputValue : true
								}, {
									boxLabel : '无',
									name : 'person.havecar',
									id:"ishavecarfalse",
									inputValue : false,
									check:true
								}]
							}]
						},{
							columnWidth : .6,
							layout : 'form',
							labelWidth : 80,
							labelAlign : 'right',
							items : [{
								xtype : 'radiogroup',
								fieldLabel : '是否有车贷',
								disabled :this.isRead,
								items : [{
									boxLabel : '有',
									name : 'person.havecarloan',
									id:'ishavecarloantrue',
									inputValue : true
								}, {
									boxLabel : '无',
									name : 'person.havecarloan',
									id:'ishavecarloanfalse',
									inputValue : false,
									check:true
								}]
							}]
						}, {
							columnWidth : 1,
							layout : 'column',
							items : [{
								columnWidth : 0.35,
								layout : 'form',
								labelWidth :80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'numberfield',
									fieldLabel : '税后月收入',
									name : 'person.homeincome',
									readOnly : this.isRead
								}]
							},{								
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								defaults : {
									anchor : anchor
								},
								labelWidth : 20,
								items : [{
									fieldLabel : "元 ",
									labelSeparator : ''
								}]								
							}, {
								columnWidth : 0.45,
								layout : 'form',
								labelWidth : 80,
								defaults : {
									anchor : '100%'
								},
								scope : this,
								items : [{
									xtype : 'numberfield',
									fieldLabel : '月可支配资金',
									name : 'person.disposableCapital',
									readOnly : this.isRead
								}/*{
									xtype : 'numberfield',
									fieldLabel : '家庭月非贷款支出(元)',
									name : 'person.homeexpend',
									readOnly : this.isRead,
									value : personData == null
											? null
											: personData.homeexpend
								}*/]
							},{							
								columnWidth : .05, // 该列在整行中所占的百分比
								layout : "form", // 从上往下的布局
								defaults : {
									anchor : anchor
								},
								labelWidth : 20,
								items : [{
									fieldLabel : "元 ",
									labelSeparator : ''
								}]								
							}]
						}, {
							columnWidth : 1,
							layout : 'form',
							labelWidth : 80,
							defaults : {
								anchor : '100%'
							},
							scope : this,
							items : [{
								xtype : 'textarea',
								fieldLabel : '信用情况说明',
								name : 'person.recordAndRemarks',
								readOnly : this.isRead
							}]
						}]
		})
		
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.personId != null && this.personId != 'undefined') {
			var   panel =this;
			this.formPanel.loadData({
						url : __ctxPath + '/creditFlow/customer/person/getByIdPerson.do?personId='+ this.personId,
						root : 'data',
						preName : 'person',
						scorp:this,
						success : function(response, options) {
							var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								var obj=alarm_fields.data;
							if(null!=obj.isheadoffamily){
								if(obj.isheadoffamily){
									Ext.getCmp("isheadoffamilytrue").setValue(true);
									Ext.getCmp("isheadoffamilyfalse").setValue(false);
								}else{
								   Ext.getCmp("isheadoffamilytrue").setValue(false);
									Ext.getCmp("isheadoffamilyfalse").setValue(true);
								}
							}
								if(null!=obj.havehouse){
								if(obj.havehouse){
									Ext.getCmp("ishavehousetrue").setValue(true);
									Ext.getCmp("ishavehousefalse").setValue(false);
								}else{
								   Ext.getCmp("ishavehousetrue").setValue(false);
									Ext.getCmp("ishavehousefalse").setValue(true);
								}
							}
								if(null!=obj.havehouseloan){
								if(obj.havehouseloan){
									Ext.getCmp("ishavehouseloantrue").setValue(true);
									Ext.getCmp("ishavehouseloanfalse").setValue(false);
								}else{
								   Ext.getCmp("ishavehouseloantrue").setValue(false);
									Ext.getCmp("ishavehouseloanfalse").setValue(true);
								}
							}
								if(null!=obj.havecar){
								if(obj.havecar){
									Ext.getCmp("ishavecartrue").setValue(true);
									Ext.getCmp("ishavecarfalse").setValue(false);
								}else{
								   Ext.getCmp("ishavecartrue").setValue(false);
									Ext.getCmp("ishavecarfalse").setValue(true);
								}
							}
								if(null!=obj.havecarloan){
								if(obj.havecarloan){
									Ext.getCmp("ishavecarloantrue").setValue(true);
									Ext.getCmp("ishavecarloanfalse").setValue(false);
								}else{
								   Ext.getCmp("ishavecarloantrue").setValue(false);
									Ext.getCmp("ishavecarloanfalse").setValue(true);
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
		this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
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
		var win=this;
			var personId=this.getCmpByName("person.id").getValue();
			$postForm({
						formPanel : this.formPanel,
						scope : this,
						url : __ctxPath + '/creditFlow/customer/person/savePartInfoPerson.do',
						params : {
								'id':personId
							},
						callback : function(fp, action) {
							win.close();
						}
					});
		}// end of save

});