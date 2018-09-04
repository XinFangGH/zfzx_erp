UpdatePassword = Ext.extend(Ext.Window,{

	constructor:function(_cfg){
		Ext.applyIf(this,_cfg);
		this.initUIComponents();
		UpdatePassword.superclass.constructor.call(this,{
					id : 'FormDefFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 200,
					width : 300,
					maximizable : true,
					title : '修改密码',
					iconCls : 'menu-form',
					buttonAlign : 'center',
					buttons : [{
							text : '保存',
							iconCls : 'btn-save',
							scope : this,
							handler : this.save
						}, {
							text : '取消',
							iconCls : 'btn-cancel',
							scope : this,
							handler : this.cancel
						}
			]
		});
		
	},
	initUIComponents:function(){
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					border : false,
					frame:true,
					autoScroll : true,
					labelAlign  :'right',
					// id : 'FormDefForm',
					defaults : {
						labelAlign  :'right'
					},
					defaultType : 'textfield',
					items : [{
						name : 'custId',
						xtype : 'hidden',
						value : this.record.id
					}, {
						fieldLabel : '用户',
						readOnly:true,
						labelAlign  :'right',
						value:this.record.loginname
					}, {
						fieldLabel : '新密码',
						name : 'password',
						labelAlign  :'right',
						xtype : 'textfield'
					}]
				});
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
					url :__ctxPath + '/p2p/updatePasswordBpCustMember.do',
					callback : function(fp, action) {
						this.close();
					}
				});
			}// end of save
});