/**
 * @author
 * @createtime
 * @class BpProductParameterForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
BpProductParameterAddForm = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	productId:null,
	constructor : function(_cfg) {
		if(typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BpProductParameterAddForm.superclass.constructor.call(this, {
					id : 'BpProductParameterAddFormWin',
					layout : 'fit',
					items : this.outPanel,
					modal : true,
					autoScroll:true,
					maximizable : true,
					frame:true,
					height : 180,
			        width :400,
					title : '新增产品',
					buttonAlign : 'center',
					buttons :this.isHideBtns?null: [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
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
		this.outPanel = new Ext.form.FormPanel({
			modal : true,
			labelWidth : 70,
			
			frame:true,
			buttonAlign : 'center',
			layout : 'form',
		    items:[{
					xtype : "combo",
					anchor : "100%",
					hiddenName : "bpProductParameter.businessType",
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					readOnly : true,
					disable : true,
					store : new Ext.data.SimpleStore({
							autoLoad : true,
							url :  __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do?nodeKey=Business',
								  fields : ['itemId', 'itemName']
							}),
							fieldLabel : "业务类别",
							blankText : "业务类别不能为空，请正确填写!",
							listeners : {
								afterrender : function(combox) {
									var st = combox.getStore();
									st.on("load", function() {
													var record = st.getAt(0);
													var v = record.data.itemId;
													combox.setValue(v);
													
													combox.fireEvent("select",combox, record, 0);
													
												})
										combox.clearInvalid();
									},
									select : function(combox, record, index) {
										var v = record.data.itemId;
										var arrStore = new Ext.data.ArrayStore({
													url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
													fields : ['itemId','itemName'],
													baseParams : {
														nodeKey : v
													},
													autoLoad : true
												});
										var opr_obj = this.ownerCt.ownerCt.getCmpByName('bpProductParameter.operationType')
										opr_obj.clearValue();
										opr_obj.store = arrStore;
										arrStore.load({
													"callback" : test
												});
										function test(r) {
											if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
												opr_obj.view.setStore(arrStore);
											}
											if (typeof(arrStore.getAt(0)) != "undefined") {
												var itmeId = arrStore.getAt(0).data.itemId;
												var itemName = arrStore
														.getAt(0).data.itemName;
												opr_obj.setRawValue(itemName);
												opr_obj.setValue(itmeId);
												var recordN = arrStore.getAt(0);
												opr_obj.fireEvent("select",
														opr_obj, arrStore
																.getAt(0), 0);
											}
										}

									}
								}
							},{
								fieldLabel : "业务品种",
								xtype : "combo",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								mode : 'remote',
								disable : true,
								hiddenName : "bpProductParameter.operationType",
								editable : false,
								blankText : "业务品种不能为空，请正确填写!",
								anchor : "100%",
								listeners : {
									scope : this,
									select : function(combox, record, index) {},
									afterrender : function(combox) {

									}
								}
							},{
								name : 'bpProductParameter.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '产品名称',
								name : 'bpProductParameter.productName',
								xtype : 'textfield',
								allowBlank : false,
								anchor : '100%',
								maxLength : 255
							}]
		});
		

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
		this.outPanel.getForm().submit({
			    clientValidation: false, 
				url : __ctxPath+ '/system/saveBpProductParameter.do',
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				scope: this,
				success : function(fp, action) {
					var gridPanel = Ext.getCmp('BpProductParameterGrid');
					if (gridPanel != null) {
						gridPanel.getStore().reload();
					}
					this.close();
				}
		});
	}
});