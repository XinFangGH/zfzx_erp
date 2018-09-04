/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
BranchOfficeForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.operateGrid=_cfg.operateGrid;
		this.initUIComponents();
		BranchOfficeForm.superclass.constructor.call(this, {
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
										fieldLabel:'维度',
										xtype:'combo',
										hiddenName:'organization.demId',
										displayField:'name',
										valueField:'id',
										editable : false,
										mode : 'local',
										width:180,
										triggerAction : 'all',
										store:new Ext.data.ArrayStore({
											autoLoad:true,
											url:__ctxPath+'/system/comboDemension.do?idUpdate=true',
											fields:['id','name'],
											listeners:{
												scope:this,
												'load':function(store){
													var cmp=this.getCmpByName('organization.demId');
													if(cmp.getValue()==''){
														cmp.setValue(1);
													}else if(cmp.getValue()==1){
														cmp.setValue(1);
														cmp.readOnly = true;
													}
												}
											}
										}),
										editable : false,
 										triggerAction: 'all'
									}, {
										fieldLabel : '分公司名称',
										name : 'organization.orgName',
										allowBlank : false,
										maxLength : 128
									},{
										fieldLabel : '上级组织',
										name : 'organization.orgSupId',
										value:this.orgSupId?this.orgSupId:1,
										xtype : 'hidden'
									}, {
										name : 'organization.orgType',
										xtype : 'hidden',
										value:1
									  }, {
											xtype : 'compositefield',
											fieldLabel : '负责人',
											allowBlank : false,
											items : [{
													xtype : 'textfield',
													readOnly : true,
													width : 270,
													name : 'chargeNames'
												},{
													xtype : 'button',
													text : '选择',
													scope : this,
													iconCls : 'btn-select',
													handler : function(){
														var fPanel = this.formPanel;
														new UserDialog({
															scope:this,
															single:false,
															callback:function(ids,fullnames){
																fPanel.getCmpByName('chargeNames').setValue(fullnames);
																fPanel.getCmpByName('chargeIds').setValue(ids);
															}
														}).show();
													}
												}]
										}, {
										fieldLabel : '分公司描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500
									},{
											name : 'chargeIds',
											xtype : 'hidden'
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