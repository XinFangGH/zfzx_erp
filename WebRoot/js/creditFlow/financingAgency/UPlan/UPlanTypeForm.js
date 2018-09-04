/**
 * @author
 * @createtime
 * @class PlManageMoneyTypeForm
 * @extends Ext.Window
 * @description PlManageMoneyType表单
 * @company 智维软件
 */
UPlanTypeForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		UPlanTypeForm.superclass.constructor.call(this, {
					id : 'UPlanTypeForm',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 300,
					width : 500,
					maximizable : true,
					title : this.keystr=='UPlan'?'U计划类型详情':'D计划类型详情',
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
					frame:true,
					defaults : {
						anchor : '96%,96%'
					},
					labelAlign : "right",
					defaultType : 'textfield',
					items : [{
						name : 'plManageMoneyType.manageMoneyTypeId',
						xtype : 'hidden',
						value : this.manageMoneyTypeId == null ? '' : this.manageMoneyTypeId
					}, {
						fieldLabel : '计划类型名称',
						name : 'plManageMoneyType.name',
						readOnly:this.isReadOnly,
						maxLength : 10,
						allowBlank : false
					},{
						fieldLabel : '收款类型',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						id : 'accountTypeid',
						editable : false,
						maxLength : 20,
						readOnly:true,
//						readOnly : this.isAllReadOnly ? true : (this.keystr == 'UPlan' ? true : false),
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["注册用户", "zc"],["平台账户", "pt"]]
						}),
						triggerAction : "all",
						hiddenName : "plManageMoneyType.accountType",
						name : 'keystr',
						value:"zc",
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								combox.setValue("zc");
								combox.fireEvent('select',combox);
							},
						    select:function(combox){
								var  flag=combox.getValue();
								var a=this.getCmpByName('plManageMoneyType.receivablesAccount');
								/*if(flag=='pt'){
									a.setValue('平台账户');
									a.setReadOnly(true);
									a.getEl().dom.className+=" readOnlyClass";
								}else{
									a.setValue();
									a.setReadOnly(false);
									a.getEl().dom.className=a.getEl().dom.className.replace("readOnlyClass ","");
								}*/
							}
						}
					},{
						fieldLabel : '原始债权人专户',
						xtype : 'combo',
						editable : false,
						allowBlank : false,
						triggerClass : 'x-form-search-trigger',
						name : 'plManageMoneyType.receivablesAccount',
						onTriggerClick : function() {
							var op = this.ownerCt.ownerCt;
							var EnterpriseNameStockUpdateNew = function(obj) {
								if (null != obj.p2pAccount && "" != obj.p2pAccount){
									op.getCmpByName('plManageMoneyType.receivablesAccount').setValue(obj.p2pAccount);
								}
							}
							selectCsCooperationPerson(EnterpriseNameStockUpdateNew,'lenders');//个人债权客户
						}
						/*fieldLabel : '收款专户',
						name : 'plManageMoneyType.receivablesAccount',
						readOnly:this.isReadOnly,
						maxLength : 20,
						allowBlank : false,
						listeners : {//校验收款专户是否存在
							scope : this,
							'blur' : function(f){
								var loginName=f.getValue();
								if(loginName!='' && loginName !='平台账户'){
									Ext.Ajax.request({
					                   url:  __ctxPath + '/p2p/isExistBpCustMember.do?loginName='+loginName,
					                   method : 'POST',
					                   success : function(response,request) {
											var obj=Ext.util.JSON.decode(response.responseText);
		                            		if(obj.result=="1"){					                            			
		                            			//收款账户存在，不做任何处理
		                            		}else{
		                            			//收款账户不存在，提示用户重新填写
		                            			Ext.ux.Toast.msg('操作信息',"该收款账户不存在，请重新输入！");
		                            			f.setValue("");
		                            		}
				                      }
				                    });
								}
							}
						}*/
					}, {
						fieldLabel : '说明',
						name : 'plManageMoneyType.remark',
						readOnly:this.isReadOnly,
						xtype:"textarea",
						maxLength : 100
					}]
				});
		// 加载表单对应的数据
		if (this.manageMoneyTypeId != null
				&& this.manageMoneyTypeId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyType.do?manageMoneyTypeId='
						+ this.manageMoneyTypeId,
				root : 'data',
				preName : 'plManageMoneyType'
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
		var keystr=this.keystr;
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/creditFlow/financingAgency/savePlManageMoneyType.do?keystr='+keystr,
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('UPlanTypeViewGrid'+keystr);
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						var obj = Ext.util.JSON.decode(action.response.responseText);
						Ext.ux.Toast.msg('操作信息',obj.msg);
						this.close();
					}
				});
	}// end of save

});