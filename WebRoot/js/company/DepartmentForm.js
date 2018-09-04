/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
DepartmentForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.operateGrid=_cfg.operateGrid;
		this.initUIComponents();
		DepartmentForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height:300,
					width : 500,
					maximizable : true,
					title : this.title,
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
										name : 'organization.orgId',
										xtype : 'hidden',
										value : this.orgId == null ? '' : this.orgId
									}, {
										xtype:'hidden',
										name:'organization.demId',
										value:1//表示为行政维度
									}, {
										fieldLabel : '部门名称',
										name : 'organization.orgName',
										allowBlank : false,
										maxLength : 128
									}, {
										fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500
									},{xtype:'hidden',name:'organization.orgSupId',value:this.orgSupId},{
										fieldLabel : '上级组织',
										value:this.parentName?this.parentName:"",
										name:'departname',
										xtype : 'textfield'
									}, {
										xtype:'hidden',
										name:'organization.orgType',
										value:'2'//bu'men
									}
									]
						});
		//加载表单对应的数据	
		if (this.orgId != null && this.orgId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath	+ '/system/getOrganization.do?orgId='+ this.orgId,
				root : 'data',
				preName : 'organization'
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
				    url : __ctxPath+'/system/saveOrganization.do',
					callback : function(fp, action) {
						
					    var scope=this.scope?this.scope:this;
								if(this.callback){
									this.callback.call(scope);
						}
						if (this.operateGrid!= null) {
							this.operateGrid.getStore().reload();
						}
						this.close();
					}
				});
	}//end of save

});