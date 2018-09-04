/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
SlCompanyMainForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.isReadOnly)!="undefined"){
           this.isReadOnly=_cfg.isReadOnly;
        }
        if(typeof(_cfg.gridpanel)!="undefined"){
           this.gridpanel=_cfg.gridpanel;
        }
		var p = Ext.getCmp("corName");
		var anchor = '100%';
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SlCompanyMainForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 350,
					width : 650,
					maximizable : true,
					title : '企业主体详细信息',
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
		var comId = this.id;
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			//bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : anchor,
				labelWidth : 90,
				columnWidth : 1

			},
			items : [{
						name : 'slCompanyMain.companyMainId',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					}, {
						xtype : 'hidden',
						name : 'slCompanyMain.isPledge',
						value : this.isPledge
					}, {
						columnWidth : 1,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '企业名称',
							readOnly : this.isReadOnly,
							id : 'corName',
							anchor : anchor,
							allowBlank : false,
							name : 'slCompanyMain.corName',
							listeners : {
								scope : this,
								blur : function(p) {
									var corName = p.getValue();

									Ext.Ajax.request({
										url : __ctxPath
												+ '/creditFlow/ourmain/verificationSlCompanyMain.do',
										method : 'POST',
										params : {
											corName : corName,
											id : comId
										},
										success : function(response, request) {

											var xx = response.responseText
													.toString().trim();
											if (xx == "{success:false}") {

												Ext.ux.Toast.msg('操作信息',
														"该企业名称已存在，请重新输入");
												Ext.getCmp("corName")
														.setValue("");
											}
										}
									});
								}
							}
						}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
								xtype : 'textfield',
								fieldLabel : '企业简称',
								anchor : anchor,
								readOnly : this.isReadOnly,
								name : 'slCompanyMain.simpleName'
							}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [
							{
								xtype : 'combo',
								fieldLabel : '证件类型',
								readOnly:false,
								allowBlank : false,
								mode : 'local',
								editable : false,
								displayField : 'typeValue',
				                valueField : 'typeId',
				                triggerAction : 'all',
								hiddenName : 'slCompanyMain.documentType',
								store : new Ext.data.SimpleStore({
							        data : [['三证合一',1],['非三证合一',2]],
							        fields:['typeValue','typeId']
					            }),
								listeners : {
									scope : this,
									afterrender : function(combox) {debugger
										var combo = this.getCmpByName('slCompanyMain.organizeCode'); 
										if(combox.getValue() == 1){
											combo.allowBlank=false;
							 				combo.fieldLabel = '社会信用代码';
											this.findById('cciaa').setVisible(false);  
											this.findById('taxnum').setVisible(false);
										}else{
											combo.allowBlank=false;
							 				combo.fieldLabel = '组织机构代码';
											this.findById('cciaa').setVisible(true);  
											this.findById('taxnum').setVisible(true);
										}
									},
									select:function(combox){debugger
										var combo = this.getCmpByName('slCompanyMain.organizeCode'); 
										if(combox.getValue() == 1){
											var a = combo.el.parent().parent().first();
											combo.allowBlank=false;
							 				a.dom.innerHTML ="<font color='red'>*</font>社会信用代码";
							 				combo.fieldLabel = '社会信用代码';
											this.findById('cciaa').setVisible(false); 
											this.findById('taxnum').setVisible(false);
										}else{
											var a = combo.el.parent().parent().first();
											combo.allowBlank=false;
							 				a.dom.innerHTML ="<font color='red'>*</font>组织机构代码";
							 				combo.fieldLabel = '组织机构代码';
							 				this.findById('cciaa').setVisible(true);  
							 				this.findById('taxnum').setVisible(true);
										}
									}
								}
							}
						]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : 'textfield',
									fieldLabel : '组织机构代码',
									anchor : anchor,
									allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.organizeCode'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						id:'cciaa',
						items : [{
									xtype : 'textfield',
									fieldLabel : '营业执照号码',
									anchor : anchor,
									allowBlank : true,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.businessCode'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						id:'taxnum',
						items : [{
									xtype : 'textfield',
									fieldLabel : '税务登记号码',
									anchor : anchor,
									allowBlank : true,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.taxnum'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
								    xtype:'textfield',
								    fieldLabel : '法人姓名',
									anchor : anchor,
									allowBlank : false,
								    name : 'slCompanyMain.lawName'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : 'numberfield',
									fieldLabel : '注册资本(万元)',
									anchor : anchor,
									allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.registerMoney'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : "dickeycombo",
									hiddenName : "slCompanyMain.bizhong",
									nodeKey : 'gLGuaranteeloan_currencyType', // xx代表分类名称
									fieldLabel : "币种类型",
									readOnly : this.isReadOnly,
									allowBlank : false,
									editable : false,
									emptyText : "请选择",
									blankText : "币种类型不能为空，请正确填写!",
									anchor : anchor,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
								       }
									}
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : "dickeycombo",
									hiddenName : "slCompanyMain.haveCharcter",
									nodeKey : 'smallloan_haveCharcter',
									fieldLabel : "所有制性质",
									allowBlank : false,
									editable : false,
									readOnly : this.isReadOnly,
									anchor : anchor,
									listeners : {
										afterrender : function(combox) {
											var st = combox.getStore();
											st.on("load", function() {
												combox.setValue(combox.getValue());
												combox.clearInvalid();
											})
										}
									}
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [ {
									id : 'slComHangyeTypeValue',
									xtype : "combo",
									triggerClass : 'x-form-search-trigger',
									fieldLabel : "行业类别",
									scope : this,
									readOnly : this.isReadOnly,
									anchor : anchor,
									onTriggerClick : function(e) {
										var obj = this;
										var oobbj = Ext.getCmp('slComHangyeType');
										selectTradeCategory(obj, oobbj);
									}
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : 'textfield',
									fieldLabel : '联系电话',
									anchor : anchor,
									allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.tel'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									xtype : "datefield",
									fieldLabel : "注册时间",
									name : "slCompanyMain.registerStartDate",
									anchor : anchor,
									readOnly : this.isReadOnly,
									format : 'Y-m-d'
								}]
					},{
						columnWidth : .5,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [{
									id : 'slComHangyeType',
									xtype : "hidden",
									name : 'slCompanyMain.hangyeType'
								}, {
									xtype : 'textfield',
									fieldLabel : '传真',
									anchor : anchor,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.tax'
								}]
					}, {
						columnWidth : .5,
						layout : 'form',
						labelWidth : 100,
						items : [  {
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									anchor : anchor,
									//allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.postalCode'
								}]
					}, {
						columnWidth : 1,
						layout : 'form',
						defaults : {
							labelWidth : 90
						},
						items : [ {
									xtype : 'textfield',
									fieldLabel : '邮箱地址',
									anchor : anchor,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.mail'
								},{
									xtype : 'textfield',
									fieldLabel : '实际经营地址',
									anchor : anchor,
									allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.sjjyAddress'
								}, {
									xtype : 'textfield',
									fieldLabel : '通讯地址',
									anchor : anchor,
									allowBlank : false,
									readOnly : this.isReadOnly,
									name : 'slCompanyMain.messageAddress'
								}]
					}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/ourmain/getSlCompanyMain.do?id='
								+ this.id,
						root : 'data',
						preName : 'slCompanyMain',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							Ext.getCmp('slComHangyeTypeValue').setValue(result.data.hangyeTypeValue);
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
							+ '/creditFlow/ourmain/saveSlCompanyMain.do',
					callback : function(fp, action) {
						if(this.gridpanel!=null||typeof(this.gridpanel)!="undefined"){
							this.gridpanel.getStore().reload();
						}
						/*var gridPanel = Ext.getCmp('SlCompanyMainGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}*/
						this.close();
					}
				});
	}// end of save

});

/*var selectSlPerson = function(slPersonMain){
	Ext.getCmp('farenId').setValue(slPersonMain.personMainId);
	Ext.getCmp('farenName').setValue(slPersonMain.name) ;
};*/