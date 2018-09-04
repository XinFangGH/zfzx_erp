//ChangeProspertiveType.js
ChangeProspertiveType= Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	investPersonInfo:null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		var leftlabel = 75;
		ChangeProspertiveType.superclass.constructor.call(this, {
					layout : 'fit',
					autoScroll:true,
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 500,
					maximizable : true,
					title : this.titleChange,
					buttonAlign : 'center',
					buttons : [{
						text : '保存',
						iconCls : 'btn-save',
						hidden : this.isLook,
						scope : this,
						handler : this.save
					},{
						text : '下一步',
						iconCls : 'btn-save',
						hidden : true,
						listeners:{
							scope : this,
							'click':this.doNext
						}
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						hidden : this.isLook,
						scope : this,
						handler : this.cancel
					}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {

		this.formPanel = new Ext.form.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
					anchor : '96%',
					columnWidth : 1,
					labelWidth : 60
				},
			items : [{
							xtype : "hidden",
							name : "bpCustProsperctive.perId",
							value:this.perId
					},{
							columnWidth : 1, // 该列在整行中所占的百分比
							layout : "form", // 从上往下的布局
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								xtype : 'combo',
								fieldLabel : '客户类型',
								mode : 'local',
								store : new Ext.data.SimpleStore({
									fields : ['itemName', 'itemId'],
									data : [['已转化正式客户', 1],['意向客户', 2],['已排除意向客户', 3]]
								}),
								hiddenName : "bpCustProsperctive.prosperctiveType",
								allowBlank : false,
								editable : false,
								displayField : 'itemName',// 显示字段值
								valueField : 'itemId',
								triggerAction : 'all',
								anchor : '100%',
								listeners : {
									scope:this,
									'select' : function(combox) {
										//借款端客户
										/*if(this.personType == '0'){
											this.buttons[0].show();
											this.buttons[1].hide();
										}else{*/
											//投资端客户
											if(combox.getValue()==1){
												this.buttons[0].hide();
												this.buttons[1].show();
											}else{
												this.buttons[0].show();
												this.buttons[1].hide();
											}
										/*}*/
									}}
							}]
						},{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								xtype : 'textarea',
								fieldLabel : '转化理由',
								name : 'bpCustProsperctive.conversionReason',
								anchor : '100%'
							}]
						}]
		});
				// 加载表单对应的数据
		if (this.perId != null && this.perId != 'undefined') {
			var panel=this
			this.formPanel.loadData({
						url:__ctxPath + "/creditFlow/customer/customerProsperctive/getBpCustProsperctive.do?perId="+this.perId,
						root : 'data',
						preName : ['bpCustProsperctive',"object"],
						success : function(resp, options) {
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
		var panel=this;
		var formPanel1 =this.formPanel
		var flashTargat=this.flashTargat
		if(formPanel1.getForm().isValid()){
			formPanel1.getForm().submit({
					url:__ctxPath+ '/creditFlow/customer/customerProsperctive/changePersonTypeBpCustProsperctive.do',
					params : {
						"perId":this.perId
					},
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var object = Ext.util.JSON.decode(action.response.responseText)
						var flag =object.flag;
						var personId=panel.personId;
						Ext.ux.Toast.msg('操作信息', '保存信息成功!');
						panel.close();
						flashTargat.getStore().reload();
					},
					failure : function(fp, action) {
						Ext.MessageBox.show({
							title : '操作信息',
							msg : '信息保存出错，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
					}
				});
		}else{
			return;
		}
		},// end of save
		doNext:function(){
								var panel=this;
								var randomId = rand(100000);
								var personData={
									investName:this.parRecord.data.customerName,//姓名
									cellphone:this.parRecord.data.telephoneNumber,//联系电话
									sex:this.parRecord.data.sex,//性别
									customerChannel:this.parRecord.data.customerChannel,//客户来源
									postaddress:this.parRecord.data.postaddress,//通讯地址
									postcode:this.parRecord.data.postalcode,//邮政编码
									selfemail:this.parRecord.data.email,//邮箱
									shopName:this.parRecord.data.department,//门店名称
									shopId:this.parRecord.data.departmentId//门店id
								};
								var customerObj=new investmentObj({
											personData : personData,
											url :  __ctxPath+ '/creditFlow/creditAssignment/customer/addPersonCsInvestmentperson.do',
											id : "add_person"+randomId,
											isHiddenBank:true
								 });
								 
								var title="新增投资客户详细信息";
								if(this.personType == '0'){
									if(this.parRecord.data.customerType=="1"){//企业借款人
										title="新增企业借款客户详细信息";
										var enterpriseData={
											enterprisename:this.parRecord.data.customerName,//姓名
											telephone:this.parRecord.data.telephoneNumber,
											email:this.parRecord.data.email,//邮箱
											postcoding:this.parRecord.data.postalcode,//邮编
											managecityName:this.parRecord.data.area,//地区
											managecity:this.parRecord.data.areaId,//地区
											shopName:this.parRecord.data.department,
											shopId:this.parRecord.data.departmentId,
											area:this.parRecord.data.postaddress,
											enterpriseYyzzId:0,
											enterpriseZzjgId:0,
											enterpriseGsdjzId:0
										}
										customerObj=new enterpriseObj({
										     enterprise:enterpriseData,
										     winId:"add_enterprise"+randomId
									     })
									}else{//个人借款人
										title="新增个人借款客户详细信息";
										var personData={
											name:this.parRecord.data.customerName,//姓名
											cellphone:this.parRecord.data.telephoneNumber,
											selfemail:this.parRecord.data.email,//邮箱
											postcoding:this.parRecord.data.postalcode,//邮编
											shopName:this.parRecord.data.department,
											shopId:this.parRecord.data.departmentId,
											postaddress:this.parRecord.data.postaddress
										}
										customerObj=new personObj({
											personData : personData,
											url : __ctxPath+ '/creditFlow/customer/person/addInfoPerson.do',
											id : "add_person" + randomId 
										})
									}
									
									
								}
								
								var window_add = new Ext.Window({
									title : title,
									height : 460,
									constrainHeader : true,
									collapsible : true,
									frame : true,
									iconCls : 'btn-add',
									border : false,
									bodyStyle : 'overflowX:hidden',
									buttonAlign : 'right',
									iconCls : 'newIcon',
									width : (screen.width - 180) * 0.7 + 160,
									resizable : true,
									layout : 'fit',
									autoScroll : false,
									constrain : true,
									closable : true,
									modal : true,
									items : [customerObj],
									tbar : [new Ext.Button({
										text : '保存',
										tooltip : '保存基本信息',
										iconCls : 'submitIcon',
										hideMode : 'offsets',
										scope:this,
										handler : function() {
											var vDates = "";
											var panel_add = window_add.get(0);
											if(panel_add.getForm().isValid()){
												panel_add.getForm().submit({
													method : 'POST',
													waitTitle : '连接',
													waitMsg : '消息发送中...',
													formBind : true,
													clientValidation : true,
													success : function(form ,action) {
														obj = Ext.util.JSON.decode(action.response.responseText);
														if(obj.exsit == false){
																	window_add.destroy();
																	Ext.ux.Toast.msg('状态','新增用户已经存在了');	
																	panel.close();
														}else{
															window_add.close();
															panel.save();
														};
													},
													failure : function(form, action) {
														//Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整!');	
														window_add.close();
													}
												})
												return true;
											}
											
										}
									})],
									listeners : {
										'beforeclose' : function(panel) {
											window_add.destroy();
										}
									}
								}).show();
							
			
	}

});