/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
OurLawsuitMaterialsForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.operateGrid=_cfg.operateGrid;
		this.initUIComponents();
		OurLawsuitMaterialsForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height:200,
					width : 300,
					maximizable : true,
					title : '贷款资料详细信息',
					buttonAlign : 'center',
					buttons : [{
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
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						name : 'ourProcreditMaterials.materialsId',
						xtype : 'hidden',
						value : this.materialsId == null? '': this.materialsId
					}, {
						fieldLabel : '资料名称',
						name : 'ourProcreditMaterials.materialsName',
						allowBlank : false,
						maxLength : 255
					}, 	   
					{
									fieldLabel : '业务类别',	
	 								allowBlank : false,
	 								name:'btn',
	 								value:'金融担保',
	 								readOnly:true,
	 								maxLength: 255
 							    },
 							    {
									fieldLabel : "业务品种",
									xtype : "combo",
									allowBlank : false,
									displayField : 'itemName',
									store : new Ext.data.SimpleStore({
										baseParams : {
											nodeKey : 'Guarantee'
										},
										autoLoad:this.materialsId != null?true:false ,
										url : __ctxPath+ '/system/getTypeJsonByNodeKeyGlobalType.do',
										fields : ['itemId','itemName']
									}),
									valueField : 'itemId',
									triggerAction : 'all',
									mode : 'remote',
									hiddenName : "ourProcreditMaterials.operationTypeKey",
									editable : false,
									blankText : "业务品种不能为空，请正确填写!",
									anchor : "100%",
									listeners : {
										afterrender : function(combox) {
											    var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
												})
										}
									}
								}]
				});
		//加载表单对应的数据	
		if (this.materialsId != null && this.materialsId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath	+ '/materials/getOurProcreditMaterials.do?materialsId='+ this.materialsId,
						root : 'data',
						preName : 'ourProcreditMaterials'
					});
		}

	},//end of the initcomponents

	/**
	 * 重置
	 * @param {} formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * @param {} window
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
					url : __ctxPath + '/materials/saveOurProcreditMaterials.do',
					callback : function(fp, action) {
						if (this.operateGrid!= null) {
							this.operateGrid.getStore().reload();
						}
						this.close();
					}
				});
	}//end of save

});