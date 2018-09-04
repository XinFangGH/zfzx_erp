/**
 * @author 
 * @createtime 
 * @class BpCustMemberForm
 * @extends Ext.Window
 * @description BpCustMember表单
 * @company 智维软件
 */
BpCustMemberForm2 = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				BpCustMemberForm2.superclass.constructor.call(this, {
							id : 'BpCustMemberFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 400,
							width : 500,
							maximizable : true,
							title : '[用户]详细信息',
							buttonAlign : 'center',
							buttons : [
										{
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
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll:true,
							//id : 'BpCustMemberForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'bpCustMember.id',
								xtype : 'hidden',
								value : this.id == null ? '' : this.id
							},{
									fieldLabel : '登录名',	
	 								name : 'bpCustMember.loginname',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '真实姓名',	
	 								name : 'bpCustMember.truename'
	 								,maxLength: 255
	 							}
																																			,{
									fieldLabel : '密码（加密）',	
	 								name : 'bpCustMember.password'
	 								,maxLength: 255
	 							}
																																			,{
									fieldLabel : '登录密码',	
	 								name : 'bpCustMember.plainpassword'
	 								,maxLength: 255
	 							}
																																			,{
									fieldLabel : '手机号码',	
	 								name : 'bpCustMember.telphone'
	 								,maxLength: 255
	 							}
																																			,{
									fieldLabel : '邮箱',	
	 								name : 'bpCustMember.email'
	 								,maxLength: 255
	 							}
																																			,{
									fieldLabel : '会员类型（0个人，1企业）',	
	 								name : 'bpCustMember.type'
	 								,xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '性别（0男性，1女性）',	
	 								name : 'bpCustMember.sex'
	 								,xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '证件类型',	
	 								name : 'bpCustMember.cardtype'
	 								,xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '证件号码',	
	 								name : 'bpCustMember.cardcode',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '出生日期',	
	 								name : 'bpCustMember.birthday',
	 								xtype:'datefield',
									format:'Y-m-d'
	 							}
																																			,{
									fieldLabel : '头像',	
	 								name : 'bpCustMember.headImage',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '籍贯省',	
	 								name : 'bpCustMember.nativePlaceProvice',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '籍贯市',	
	 								name : 'bpCustMember.nativePlaceCity',
	 								maxLength: 10
	 							}
																																			,{
									fieldLabel : '民族',	
	 								name : 'bpCustMember.nation',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '家庭电话',	
	 								name : 'bpCustMember.homePhone',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '联系地址',	
	 								name : 'bpCustMember.relationAddress',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '邮编',	
	 								name : 'bpCustMember.postCode',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : 'qq',	
	 								name : 'bpCustMember.QQ',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : 'msn',	
	 								name : 'bpCustMember.MSN',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '支付密码',	
	 								name : 'bpCustMember.paymentCode',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '密码保护问题',	
	 								name : 'bpCustMember.securityQuestion',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '密码保护答案',	
	 								name : 'bpCustMember.securityAnswer',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '角色ID',	
	 								name : 'bpCustMember.roleId',
	 								xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '注册时间',	
	 								name : 'bpCustMember.registrationDate',
	 								xtype:'datefield',
									format:'Y-m-d',
									value:new Date()
	 							}
																																			,{
									fieldLabel : '居住城市省',	
	 								name : 'bpCustMember.liveProvice',
	 								maxLength: 20
	 							}
																																			,{
									fieldLabel : '居住城市-市',	
	 								name : 'bpCustMember.liveCity',
	 								maxLength: 20
	 							}
																																			,{
									fieldLabel : '婚姻状况：0未婚，1已婚',	
	 								name : 'bpCustMember.marry',
	 								xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '传真',	
	 								name : 'bpCustMember.fax',
	 								maxLength: 255
	 							}
																																			,{
									fieldLabel : '会员等级',	
	 								name : 'bpCustMember.memberOrderId',
	 								xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '是否删除',	
	 								name : 'bpCustMember.isDelete',
	 								xtype:'numberfield'
	 							}
																																			,{
									fieldLabel : '是否禁用',	
	 								name : 'bpCustMember.isForbidden',
	 								xtype:'numberfield'
	 							}
																																			]
						});
				//加载表单对应的数据	
				if (this.userId != null && this.userId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath + '/p2p/getBpCustMember.do?id='+ this.userId,
								root : 'data',
								preName : 'bpCustMember'
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
						formPanel:this.formPanel,
						scope:this,
						url:__ctxPath + '/p2p/saveBpCustMember.do',
						callback:function(fp,action){
							var gridPanel = Ext.getCmp('BpCustMemberGrid');
							if (gridPanel != null) {
								gridPanel.getStore().reload();
							}
							this.close();
						}
					}
				);
			}//end of save

		});