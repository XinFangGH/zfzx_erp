/**
 * @author
 * @createtime
 * @class CsElementCategoryForm
 * @extends Ext.Window
 * @description CsElementCategory表单
 * @company 智维软件
 */
CsElementCategoryForm = Ext.extend(Ext.Window, {
	businessType:"SmallLoan",
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg)!="undefined"){
			if(typeof(_cfg.businessType)!="undefined"){
				this.businessType = _cfg.businessType
			}
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CsElementCategoryForm.superclass.constructor.call(this, {
					id : 'CsElementCategoryFormWin',
					layout : 'anchor',
					items : this.formPanel,
					modal : true,
//					height : 400,
					width : 500,
					autoHeight:true,
//					maximizable : true,
					title : '[CsElementCategory]详细信息',
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
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					autoHeight:true,
//					height:'260',
					// id : 'CsElementCategoryForm',
					defaults : {
						anchor : '96%,96%'
					},
					defaultType : 'textfield',
					items : [{
						xtype : "globalCombo",
						hiddenName : "businessTypeId",
						typeName : '业务经营', // xx代表分类名称
						fieldLabel : "业务类别",
						allowBlank : false,
						editable : false,
						//emptyText : "请选择",
						blankText : "业务类别不能为空，请正确填写!",
						anchor : "100%",
						displayField : 'typeName',
						valueField : 'proTypeId',
						triggerAction : 'all',
						readOnly : true,
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								Ext.Ajax.request({
			                   		url : __ctxPath + '/system/getTypeInfoGlobalType.do',
			                   		method : 'POST',
			                   		params : {
										nodeKey : this.businessType
									},
			                  		success : function(response,request) {
										var obj = Ext.util.JSON.decode(response.responseText);
										st.on("load", function() {
											if(combox.getValue()==''){
												var idx = st.find("proTypeId", obj.proTypeId);
												var record = st.getAt(idx);
												combox.setValue(st.getAt(idx).data.proTypeId);
												combox.fireEvent("select", combox,record, 0);
											}else{
												var idx = st.find("proTypeId", combox.getValue());
												var record = st.getAt(idx);
												combox.setValue(st.getAt(idx).data.proTypeId);
											}
										
									})}
                             	})
								
							},
							select : function(combox, record, index) {
								var v = record.data.proTypeId;
								var tname = record.data.typeName;
								var arrStore = new Ext.data.ArrayStore({
									url : __ctxPath + '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
									baseParams : {
										parentId : v
									},
									fields : ['proTypeId', 'typeName'],
									autoLoad : true
								});
							}
						}
				    },{
								name : 'csElementCategory.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							}, {
								fieldLabel : '要素编码',
								name : 'csElementCategory.elementCode',
								allowBlank:false,
								anchor : '100%',
								maxLength : 255
							}, {
								fieldLabel : '方法',
								name : 'csElementCategory.method',
								anchor : '100%',
								maxLength: 255
							}, {
								xtype:"textarea",
								fieldLabel : '要素描述',
								name : 'csElementCategory.description',
								allowBlank:false,
								anchor : '100%',
								maxLength : 255
							}, {
				    	xtype:'hidden',
				    	name:"csElementCategory.businessType",
				    	value:this.businessType
				    }]
				});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath
								+ '/contract/getCsElementCategory.do?id='
								+ this.id,
						root : 'data',
						preName : 'csElementCategory'
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
					url : __ctxPath
							+ '/contract/saveCsElementCategory.do',
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('CsElementCategoryGrid');
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save

});