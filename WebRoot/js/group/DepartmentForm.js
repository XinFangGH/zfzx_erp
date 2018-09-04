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
		if(_cfg.operateGrid)
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
							//autoScroll : true,
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
										xtype : 'textfield',
										maxLength : 128,
										anchor : '96%,96%'
									}, {
										fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
//										xtype : 'textfield',
										maxLength : 500,
										anchor : '96%,96%'
									},{xtype:'hidden',name:'organization.orgSupId',value:this.orgSupId},{
										fieldLabel : '上级组织',
										value:this.parentName?this.parentName:"",
										name:'departname',
										xtype : 'textfield',
										anchor : '96%,96%'
									}, { // 行2
							          layout : "column",
							          border : false,
							          items : [{
							             columnWidth : .95,
							             layout : "form",
							             border : false,
							             items : [{
							                fieldLabel : '提成利率',
											name : 'organization.commissionRate',
											columnWidth : .85,
											xtype : 'textfield',
											width : 320
											
							               }]
							            }, {
							            columnWidth: .05,
							             layout : "form",
							             border : false,
							             items : [{
							             fieldLabel : "%",
										 labelSeparator : '',
										 columnWidth: .05
							               }]
							            }]
							         }, {
										
							          	fieldLabel : '部门推荐码',
										xtype:'textfield',
										id : 'recommendCode1',
										hidden: false,
										//hideLabel:true,
										allowBlank : false,
										name : 'organization.recommendCode',
										maxLength : 100,
										anchor : '96%,96%',
										border : false
									},{
										fieldLabel : '部门类型',
										name : 'organization.settlementType',
										hiddenName : 'organization.settlementType',
										maxLength : 100,
										xtype : 'combo',
										anchor : '96%,96%',
										valueField : 'value',
										
										editable : false,
										displayField : 'item',
										mode : 'local',
										store : new Ext.data.SimpleStore({
										fields : ["item", "value"],
										data : [["普通部门", "0"], ["投资推广部", "1"], ["投资部门", "2"],["融资部门", "3"]]
									}),
										listeners :{
											scope : this,
											'afterrender':function(combox){
												var combo = this.getCmpByName('organization.recommendCode');
												var isHidden = Ext.getCmp('recommendCode1');
												if(combox.getValue()==2){
//													isHidden.setValue("");
													isHidden.show();
													isHidden.allowBlank = false;
												}else{
//													isHidden.setValue("");
													isHidden.hide();//hide方法将组件的选择输入框隐藏
													isHidden.allowBlank = true;
　　													isHidden.getEl().up('.x-form-item').setDisplayed(false);
												}
											   },
											'select' : function(combox,record){
												var combo = this.getCmpByName('organization.recommendCode');
												var isHidden = Ext.getCmp('recommendCode1');
												this.getCmpByName('organization.settlementType').setValue(combox.getValue());
												if(combox.getValue()==2){
//													isHidden.setValue("");
													isHidden.show();
													isHidden.allowBlank = false;
													isHidden.getEl().up('.x-form-item').setDisplayed(true);

												}else{
													isHidden.setValue("");
													isHidden.hide();//hide方法将组件的选择输入框隐藏
													isHidden.allowBlank = true;
　　													isHidden.getEl().up('.x-form-item').setDisplayed(false);
												}
											}
										},triggerAction : "all"
									},{
										xtype:'hidden',
										name:'organization.orgType',
										value:'2'//bu'men
									}, {
										xtype:'hidden',
										name:'organization.key',
										value:null
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
				if(action.result.success==true){	
					if (this.operateGrid!= null) {
						this.operateGrid.getStore().reload();
					}
					this.close();
				}else{
					Ext.ux.Toast.msg('操作信息', action.result.msg);
				}
			}
		});	
	}//end of save
});