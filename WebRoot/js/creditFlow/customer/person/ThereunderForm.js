/**
 * @author
 * @createtime
 * @class SlCompanyMainForm
 * @extends Ext.Window
 * @description SlCompanyMain表单
 * @company 北京互融时代软件有限公司
 */
ThereunderForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		ThereunderForm.superclass.constructor.call(this, {
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
		var selectSpousePerson = function(obj){
			Ext.getCmp('therepoxm').setValue(obj.name);
			Ext.getCmp('therepoid').setValue(obj.id) ;
			Ext.getCmp('phone').setValue(obj.cellphone)
		}
		var selectCompanyname = function(obj){
				Ext.getCmp('encompanyname').setValue(obj.enterprisename);
				Ext.getCmp('encompanynameid').setValue(obj.id) ;
				Ext.getCmp('licensenum').setValue(obj.cciaa);
				Ext.getCmp('registercapital').setValue(obj.registermoney);
				Ext.getCmp('address').setValue(obj.factaddress);
		}
		var anchor = '100%';
		this.formPanel = new Ext.FormPanel({
			url : __ctxPath+'/creditFlow/customer/person/addPersonThereunder.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			//autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			height : 240,
			frame : true ,
			layout : 'column',
			items:[{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
	            	id : 'encompanyname',
	            	xtype : 'combo',
	                fieldLabel: '<font color=red>*</font>公司名称',
	                triggerClass :'x-form-search-trigger',
	                hiddenName : 'companyname',
	                readOnly : this.isReadOnly,
	                onTriggerClick : function(){
						selectEnterGridWin(selectCompanyname);
					},
					resizable : true,
					mode : 'romote',
					editable : false,
					lazyInit : false,
					allowBlank : false,
					typeAhead : true,
					minChars : 1,
					store : new Ext.data.JsonStore({
						url : __ctxPath+'/creditFlow/customer/enterprise/ajaxQueryForComboEnterprise.do',
						root : 'topics',
						autoLoad : true,
						fields : [{
									name : 'id'
								}, {
									name : 'name'
								}],
						listeners : {
							'load' : function(s,r,o){
								if(s.getCount()==0){
									Ext.getCmp('encompanyname').markInvalid('没有查找到匹配的记录') ;
								}
							}
						}
					}),
					displayField : 'name',
					valueField : 'id',
					triggerAction : 'all',
					listeners : {
						'select' : function(combo,record,index){
							Ext.getCmp('encompanynameid').setValue(record.get('id'));
							Ext.getCmp('encompanyname').setValue(record.get('enterprisename'));
						},'blur' : function(f){
							if(f.getValue()!=null&&f.getValue()!=''){
								Ext.getCmp('encompanynameid').setValue(f.getValue());
							}
						}
					}
				},{
					id : 'encompanynameid',
					xtype : 'hidden',
					name : 'personThereunder.companyname'
				}]
			},{
				columnWidth : .5,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
					xtype : "dickeycombo",
					nodeKey :'guanxi',
					width : 70,
					readOnly : this.isReadOnly,
					fieldLabel : '<font color=red>*</font>关系',
					hiddenName : 'personThereunder.relate',
					//dicId : vwtcoaDicId,
					allowBlank : false,
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								if(combox.getValue() == 0||combox.getValue()==1){
									combox.setValue("");
								}else{
									combox.setValue(combox
										.getValue());
								}
								combox.clearInvalid();
							})
						}
					}
				},
					{
		            	id : 'therepoxm',
		            	xtype : 'combo',
		                fieldLabel: '联系人',
		                triggerClass :'x-form-search-trigger',
		                hiddenName : 'lnpname',
		                readOnly : this.isReadOnly,
		                onTriggerClick : function(){
							selectPWName(selectSpousePerson);
						},
						resizable : true,
						mode : 'romote',
						editable : false,
						lazyInit : false,
						allowBlank : false,
						typeAhead : true,
						minChars : 1,
						listWidth : 150,
						store : new Ext.data.JsonStore({
							url : __ctxPath+'/creditFlow/customer/person/ajaxQueryForComboPerson.do?isAll='+isGranted('_detail_sygrkh'),
							root : 'topics',
							autoLoad : true,
							fields : [{
										name : 'id'
									}, {
										name : 'name'
									}],
							listeners : {
								'load' : function(s,r,o){
									if(s.getCount()==0){
										Ext.getCmp('poxm').markInvalid('没有查找到匹配的记录') ;
									}
								}
							}
						}),
						displayField : 'name',
						valueField : 'id',
						triggerAction : 'all',
						listeners : {
							'select' : function(combo,record,index){
								Ext.getCmp('therepoid').setValue(record.get('id'));
								Ext.getCmp('therepoxm').setValue(record.get('name'));
							},'blur' : function(f){
								if(f.getValue()!=null&&f.getValue()!=''){
									Ext.getCmp('therepoid').setValue(f.getValue());
								}
							}
						}
					}
				,{
					id :'therepoid',
					xtype : 'hidden',
					name : 'personThereunder.lnpid'
				}]
			},{
				columnWidth : .5,
				labelWidth : 90,
				layout : 'form',
				defaults : {anchor : anchor},
				items :[{
					id : 'licensenum',
					xtype : 'textfield',
					fieldLabel : '营业执照号码',
					readOnly : this.isReadOnly,
					name : 'personThereunder.licensenum'
					//regex : /^[A-Za-z0-9]+$/,
					//regexText : '格式错误'
				},{
					id: 'phone',
					xtype : 'textfield',
					fieldLabel : '联系人电话',
					readOnly : this.isReadOnly,
					name : 'personThereunder.phone'
					//regex : /^(\d{3,4})-(\d{7,8})/,
					//regexText : '电话格式错误或无效的电话号码'
				}]
			},{
				columnWidth : 0.5,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'datefield',
					fieldLabel : '注册时间',
					readOnly : this.isReadOnly,
					name : 'personThereunder.registerdate',
					format : 'Y-m-d'
				}]
			},{
				columnWidth : 0.5,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
					id : 'registercapital',
					xtype : 'textfield',
					fieldLabel : '注册资本(万元)',
					readOnly : this.isReadOnly,
					name : 'personThereunder.registercapital',
					regex : /^\d+(\.\d+)?$/ ,
					regexText : '数据格式不对'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
					id : 'address',
					xtype : 'textfield',
					fieldLabel : '经营地址',
					readOnly : this.isReadOnly,
					name : 'personThereunder.address'
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 90,
				defaults : {anchor : anchor},
				items :[{
					xtype : 'textarea',
					fieldLabel : '备注',
					readOnly : this.isReadOnly,
					name : 'personThereunder.remarks'
				}]
			},{
				xtype : 'hidden',
				name : 'personThereunder.personid',
				value : this.personId
			},{
				xtype : 'hidden',
				name : 'personThereunder.id',
				value : this.id
			}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/creditFlow/customer/person/seePersonThereunder.do?id='+ this.id,
				root : 'data',
				preName : 'personThereunder',
				scope : this,
				success : function(resp, options) {
					var result = Ext.decode(resp.responseText);
					Ext.getCmp('encompanyname').setValue(result.data.shortname);
					Ext.getCmp('therepoxm').setValue(result.data.name);
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
		var gridPanel=this.gridPanel
		var win=this;
		this.formPanel.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			success : function(form ,action) {
					Ext.ux.Toast.msg('状态', '保存成功!');
					gridPanel.getStore().reload();
					win.destroy();
			},
			failure : function(form, action) {
				if(action.response.status==0){
					Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
				}else if(action.response.status==-1){
					Ext.ux.Toast.msg('状态','连接超时，请重试!');
				}else{
					Ext.ux.Toast.msg('状态','添加失败!');		
				}
			}
		});
	}// end of save

});

/*var selectSlPerson = function(slPersonMain){
	Ext.getCmp('farenId').setValue(slPersonMain.personMainId);
	Ext.getCmp('farenName').setValue(slPersonMain.name) ;
};*/