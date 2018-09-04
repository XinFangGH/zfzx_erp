/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
ShopForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(_cfg.operateGrid)
		this.operateGrid=_cfg.operateGrid;
		this.initUIComponents();
		ShopForm.superclass.constructor.call(this, {
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
							layout : 'column',
							border : false,
							scope : this,
							bodyStyle : 'padding:10px',
							defaults : {
								anchor : '100%',
								//columnWidth : 1,
								labelAlign:"right",
								isFormField : true,
								labelWidth : 86
							},
							//defaultType : 'column',
							items : [{
										name : 'organization.orgId',
										xtype : 'hidden',
										value : this.orgId == null ? '' : this.orgId
									}, {
										xtype:'hidden',
										name:'organization.demId',
										value:1//表示为行政维度
									}, {
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										
										border : false,
										items : [{
											fieldLabel : '门店名称',
											labelAlign:"right",
											name : 'organization.orgName',
											allowBlank : false,
											xtype:'textfield',
											anchor : "100%"// 控制文本框的长度

									}]
										/*fieldLabel : '部门名称',
										name : 'organization.orgName',
										allowBlank : false,
										maxLength : 128*/
									}, {
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '门店编号',
											xtype:'textfield',
											labelAlign:"right",
											allowBlank : false,
											name : 'organization.branchNO',
											anchor : "98%"// 控制文本框的长度

									}]
										/*fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500*/
									},{
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '负责人',
											xtype:'textfield',
											labelAlign:"right",
											allowBlank : false,
											name : 'organization.linkman',
											anchor : "100%"// 控制文本框的长度

									}]
										/*fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500*/
									},{
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '手机号码',
											xtype:'textfield',
											labelAlign:"right",
											allowBlank : false,
											name : 'organization.linktel',
											anchor : "98%"// 控制文本框的长度

									}]
										/*fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500*/
									},{
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '门店电话',
											xtype:'textfield',
											labelAlign:"right",
											allowBlank : false,
											name : 'organization.shopPhone',
											anchor : "100%"// 控制文本框的长度
									}]
										/*fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500*/
									},{
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '传真',
											xtype:'textfield',
											labelAlign:"right",
											name : 'organization.fax',
											anchor : "98%"// 控制文本框的长度

									}]
										/*fieldLabel : '部门描述',
										xtype:'textarea',
										name : 'organization.orgDesc',
										maxLength : 500*/
									},{xtype:'hidden',name:'organization.orgSupId',value:this.orgSupId},{
										columnWidth :1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '地址',
											//value:this.parentName?this.parentName:"",
											name:'organization.address',
											xtype : 'textfield',
											labelAlign:"right",
											anchor : "98%"// 控制文本框的长度

									}]
										/*fieldLabel : '上级组织',
										value:this.parentName?this.parentName:"",
										name:'departname',
										xtype : 'textfield'*/
									},{
										columnWidth :1, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										border : false,
										items : [{
											fieldLabel : '备注',
											//value:this.parentName?this.parentName:"",
											name:'organization.orgDesc',
											xtype : 'textarea',
											labelAlign:"right",
											anchor : "98%"// 控制文本框的长度

									}]
										/*fieldLabel : '上级组织',
										value:this.parentName?this.parentName:"",
										name:'departname',
										xtype : 'textfield'*/
									},/*{
										columnWidth :0.5, // 该列在整行中所占的百分比
										layout : "form", // 从上往下的布局
										labelWidth : 85,
										items : [{
											fieldLabel : '上级组织',
											value:this.parentName?this.parentName:"",
											name:'departname',
											xtype : 'textfield',
											anchor : "100%"// 控制文本框的长度

									}]
										fieldLabel : '上级组织',
										value:this.parentName?this.parentName:"",
										name:'departname',
										xtype : 'textfield'
									},*/ {
										xtype:'hidden',
										name:'organization.orgType',
										value:'3'//门店
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
						if (this.operateGrid!= null) {
							this.operateGrid.getStore().reload();
						}
						this.close();
					}
				});
	}//end of save

});