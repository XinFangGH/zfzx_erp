/**
 * @author
 * @createtime
 * @class BankRelationPersonWindow
 * @extends Ext.Window
 * @description BankRelationPersonWindow
 * @company
 */
BankRelationPersonWindow = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		if(typeof(_cfg.listPanel)!='undefined'){
			this.listPanel = _cfg.listPanel;
		}else{
			this.listPanel = null;
		}
		if(typeof(_cfg.isRead)!='undefined'){
			this.isRead = _cfg.isRead;
		}else{
			this.isRead = false;
		}
		if(typeof(_cfg.type)!='undefined'){
			this.type = _cfg.type;
			switch(this.type){
				case 'add':this.title = '新增银行客户详细信息';this.pic = 'btn-add'
				break;
				case 'edit':this.title = '编辑银行客户详细信息';this.pic = 'btn-edit'
				break;
				case 'read':this.title = '查看银行客户详细信息';this.pic = 'btn-readdocument'
				break;
			}
			
		}else{
			this.type = false;
		}
		if(typeof(_cfg.bankRelationPersonData)!='undefined'){
			this.bankRelationPersonData = _cfg.bankRelationPersonData;
		}else{
			this.bankRelationPersonData = null;
		}
		if(typeof(_cfg.url)!='undefined'){
			this.url = _cfg.url;
		}else{
			this.url = '/';
		}
		// 必须先初始化组件
		this.initUIComponents();
		BankRelationPersonWindow.superclass.constructor.call(this, {
					layout : 'anchor',
					iconCls : this.pic,
					title : this.title,
					width :(screen.width-180)* 0.7 - 80,
					height : 520,
					minHeight : 490,
					closable : true,
					items : [
						this.isRead?new Ext.Toolbar({}) :this.topBar,
						this.panel
					],
					maximizable : true,
					border : false,
					modal : true,
					plain : true,
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var bankRelationPersonData = this.bankRelationPersonData;
		var getObjArray = function(objArray){
			Ext.getCmp('bankRelationPersonPanel').getCmpByName('bankname').setValue(objArray[(objArray.length)-1].text);
			Ext.getCmp('bankRelationPersonPanel').getCmpByName('bankRelationPerson.bankid').setValue(objArray[(objArray.length)-1].id);
			
			Ext.getCmp('bankRelationPersonPanel').getCmpByName('zhibankname').setValue(objArray[0].text);
			Ext.getCmp('bankRelationPersonPanel').getCmpByName('bankRelationPerson.fenbankid').setValue(objArray[0].id);
			Ext.getCmp('bankRelationPersonPanel').getCmpByName('zhibankname').readOnly = true
		}
		this.topBar = new Ext.Toolbar({
				scope : this,
				items:[{
						text:'保存并关闭',
						iconCls:'btn-save',
						scope:this,
						handler:function(){
							if (this.panel.getForm().isValid()) {
								this.panel.getForm().submit({
									method : 'post',
									waitMsg : '正在提交数据...',
									scope : this,
									success : function(fp, action) {
										Ext.ux.Toast.msg("操作信息", "成功保存信息！");
										this.listPanel.getStore().reload();
										this.close();
									},
									failure : function(fp, action) {
										Ext.MessageBox.show({
													title : '操作信息',
													msg : '信息保存出错，请联系管理员！',
													buttons : Ext.MessageBox.OK,
													icon : 'ext-mb-error'
												});
										this.close();
									}
								});
						}
						}
					}]
			});
			
			this.topBar = new Ext.Toolbar({
					scope : this,
					items:[{
							text:'保存并关闭',
							iconCls:'btn-save',
							scope:this,
							handler:function(){
								if (this.panel.getForm().isValid()) {
									this.panel.getForm().submit({
										method : 'post',
										waitMsg : '正在提交数据...',
										scope : this,
										success : function(fp, action) {
											Ext.ux.Toast.msg("操作信息", "成功保存信息！");
											this.listPanel.getStore().reload();
											this.close();
										},
										failure : function(fp, action) {
											Ext.MessageBox.show({
														title : '操作信息',
														msg : '信息保存出错，请联系管理员！',
														buttons : Ext.MessageBox.OK,
														icon : 'ext-mb-error'
													});
											this.close();
										}
									});
							}
							}
						}]
				});
		this.panel= new Ext.form.FormPanel({
			id :'bankRelationPersonPanel',
			url : this.url,
			monitorValid : true,
			bodyStyle:'padding:10px',
			//renderTo : 'updateBankRelationPersonDiv',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			layout : 'column',
			height : 460,
			//autoHeight : true,
			frame : true ,
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '基本信息',
	            collapsible: true,
	            autoHeight:true,
	            width : (screen.width-180)* 0.7 - 120 ,
	            items : [{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 80,
					defaults : {anchor : anchor},
					scope : this,
					items : [{
						xtype : 'textfield',
						fieldLabel : '客户姓名',
						name : 'bankRelationPerson.name',
						allowBlank : false,
					    blankText : '必填信息',
					    regex : /^[\u4e00-\u9fa5]{1,10}$/,
						regexText : '只能输入中文',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.name
					},{
						xtype : 'textfield',
						fieldLabel : '所在银行',
						allowBlank : false,
					    blankText : '必填信息',
						name : 'bankname',
						readOnly : true,
						value : bankRelationPersonData == null?null:bankRelationPersonData.bankname
					},{
						xtype : 'textfield',
						fieldLabel : '联系电话',
						allowBlank : false,
						name : 'bankRelationPerson.blmtelephone',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.blmtelephone
					},{
						xtype : 'textfield',
						fieldLabel : '手机号码',
						name : 'bankRelationPerson.blmphone',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.blmphone
					},{
						xtype : 'textfield',
						fieldLabel : '电子邮件',
						//allowBlank : false,
						name : 'bankRelationPerson.email',
						regex : /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/,
						regexText : '电子邮件格式不正确或无效的电子邮件地址',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.email
					},{
						xtype : "dickeycombo",
						nodeKey :'8',
						fieldLabel : '婚姻状况',
						hiddenName : 'bankRelationPerson.marriage',
						//mode : 'romote',
						width : 80 ,
						readOnly : this.isRead,
						editable : false,
						value : bankRelationPersonData == null?null:bankRelationPersonData.marriage,
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
					},{
						xtype : 'textfield',
						fieldLabel : '孩子1姓名',
						name : 'bankRelationPerson.childname1',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.childname1
					},{
						xtype : 'textfield',
						fieldLabel : '孩子2姓名',
						name : 'bankRelationPerson.childname2',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.childname2
					},{
						xtype : 'textfield',
						fieldLabel : '籍贯',
						name : 'bankRelationPerson.nativeplace',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.nativeplace
					}]
	            },{
	            	columnWidth : .5,
					layout : 'form',
					labelWidth : 80,
					defaults : {anchor : anchor},
					items : [{
						xtype : "dickeycombo",
						nodeKey :'sex_key',
						fieldLabel : '性别',
						hiddenName : 'bankRelationPerson.sex',
						readOnly : this.isRead,
						width : 80 ,
						//mode : 'romote',
						allowBlank : false,
					    blankText : '必填信息',
						editable : false,
					    emptyText:'请选性别',
					    readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.sex,
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
						
						},{
						//id : 'updatezhibankname',
						name :'zhibankname',
						xtype : 'trigger',
						triggerClass :'x-form-search-trigger',
						fieldLabel : '银行支行',
						scope : this,
						readOnly : this.isRead,
						onTriggerClick : function(){
							selectDictionary('bank',getObjArray);
						},
						editable : false,
						anchor:anchor,
						allowBlank : false,
						blankText : '必填信息',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.fenbankvalue
					
					},{
						xtype : "hidden",
						name : 'bankRelationPerson.belongedId',
						value : bankRelationPersonData ==null?null:bankRelationPersonData.belongedId
					},{
					
						hiddenName : 'belongedName',
						xtype:'trigger',
						fieldLabel : '客户授权人',
						submitValue : true,
						triggerClass :'x-form-search-trigger',
						editable : false,
						readOnly : isGranted('_editBelongeder_yhkh')?false:true,
						value:bankRelationPersonData ==null?null:bankRelationPersonData.belongedName,
						scope : this,
						onTriggerClick : function(){
						var obj = this;
						var belongedObj = obj.previousSibling();
						var userIds = belongedObj.getValue();
						if (null==obj.getValue() || "" == obj.getValue()) {
							userIds = "";
						}
						new UserDialog({
						  	userIds : userIds,
						  	userName : obj.getValue(),
							single : false,
							title:"客户授权人",
							callback : function(uId, uname) {
								obj.setRawValue(uname);
								belongedObj.setValue(uId);
							}
						}).show();
					}
									
				},{
						xtype : 'textfield',
						fieldLabel : '职务',
						name : 'bankRelationPerson.duty',
						allowBlank : false,
					    blankText : '必填信息',
					    readOnly : this.isRead,
						/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
						regexText : '只能输入中文',*/
						value : bankRelationPersonData == null?null:bankRelationPersonData.duty
					},{
						xtype : 'textfield',
						fieldLabel : '传真号码',
						name : 'bankRelationPerson.fax',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.fax
					},{
						xtype : 'datefield',
						fieldLabel : '联系人生日',
						name : 'bankRelationPerson.birthday',
						format : 'Y-m-d',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.birthday
					},{
						xtype : 'datefield',
						fieldLabel : '婚姻纪念日',
						name : 'bankRelationPerson.marriagedate',
						format : 'Y-m-d',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.marriagedate
					},{
						xtype : 'datefield',
						fieldLabel : '孩子1生日',
						name : 'bankRelationPerson.childbirthday1',
						format : 'Y-m-d',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.childbirthday1
					},{
						xtype : 'datefield',
						fieldLabel : '孩子2生日',
						name : 'bankRelationPerson.childbirthday2',
						format : 'Y-m-d',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.childbirthday2
					}]
	            },{
	            	columnWidth : 1,
					layout : 'form',
					labelWidth : 80,
					defaults : {anchor : anchor},
					scope : this,
					items :[{
						xtype : 'textfield',
						fieldLabel : '家庭住址',
						name : 'bankRelationPerson.homeaddress',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.homeaddress
					},{
						xtype : 'textfield',
						fieldLabel : '户口所在地',
						name : 'bankRelationPerson.hkszd',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.hkszd
					},{
						xtype : 'textfield',
						fieldLabel : '配偶信息',
						name : 'bankRelationPerson.mate',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.mate
					},{
						xtype : 'textfield',
						fieldLabel : '父母信息',
						name : 'bankRelationPerson.parents',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.parents
					}]
	            }]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '兴趣爱好',
	            collapsible: true,
	            autoHeight:true,
	           	width : (screen.width-180)* 0.7 - 150 ,
	            items : [{
	            	columnWidth : 1,
					layout : 'form',
					labelWidth : 80,
					defaults : {anchor : anchor},
					scope : this,
					items :[{
						xtype : 'textarea',
						fieldLabel : '嗜好与兴趣',
						height : 40,
						name : 'bankRelationPerson.interest1',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.interest1
					},{
						xtype : 'textarea',
						fieldLabel : '喝酒或抽烟',
						height : 40,
						name : 'bankRelationPerson.interest2',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.interest2
					},{
						xtype : 'textarea',
						fieldLabel : '就餐口味地点',
						height : 40,
						name : 'bankRelationPerson.interest3',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.interest3
					},{
						xtype : 'textarea',
						fieldLabel : '聊天话题',
						height : 40,
						name : 'bankRelationPerson.interest4',
						readOnly : this.isRead,
						value : bankRelationPersonData == null?null:bankRelationPersonData.interest4
					}]
				}]
			},{
				//id : 'updatebankid' ,
				xtype : 'hidden',
				name : 'bankRelationPerson.bankid',
				value : bankRelationPersonData == null?null:bankRelationPersonData.bankid
			},{
				
				//id : 'updatezhibankid' ,
				xtype : 'hidden',
				name : 'bankRelationPerson.fenbankid',
				value : bankRelationPersonData == null?null:bankRelationPersonData.fenbankid
			},{
				xtype : 'hidden',
				name : 'bankRelationPerson.id',
				value : bankRelationPersonData == null?null:bankRelationPersonData.id
			},{
				xtype : "hidden",
				name:"bankRelationPerson.creater",
				value:bankRelationPersonData==null?null:bankRelationPersonData.creater
		    },{
				xtype : "hidden",
				name:"bankRelationPerson.createrId",
				value:bankRelationPersonData==null?null:bankRelationPersonData.createrId
		    },{
				xtype : "hidden",
				name:"bankRelationPerson.createdate",
				value:bankRelationPersonData==null?null:bankRelationPersonData.createdate
		    },{
				xtype : "hidden",
				name:"bankRelationPerson.companyId",
				value:bankRelationPersonData==null?null:bankRelationPersonData.companyId
		    }]
			
		});
	}// end of the initUIComponents
	
})
