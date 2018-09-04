/**
 * @author lyy
 * @class PhoneBookForm
 * @extends Ext.Window
 */
PhoneBookForm = Ext.extend(Ext.Window,{
     formPanel:null,
     buttons:null,
     constructor:function(_cfg){
          Ext.applyIf(this,_cfg);
          this.initUIComponents();
          PhoneBookForm.superclass.constructor.call(this,{
                id : 'PhoneBookForm',
				title : '联系人详细信息',
				iconCls:"menu-personal-phoneBook",
				width : 520,
				height : 340,
				layout : 'fit',
				defaults:{
				   padding:'5'
				},
				plain : true,
				border:false,
				buttonAlign : 'center',
				items:this.formPanel,
				buttons:this.buttons
          });
     },
     initUIComponents:function(){
          	var _url = __ctxPath+'/communicate/listPhoneGroup.do?method=1&&isPublic='+this.isPublic;
			var phoneGroupSelector =  new TreeSelector('phoneGroupSelect',_url,'分组*','groupId');
			this.formPanel = new Ext.FormPanel( {
				url : __ctxPath + '/communicate/savePhoneBook.do',
				layout : 'form',
				id : 'PhoneBookFormPanel',
				frame : false,
				formId : 'PhoneBookFormId',
		        items: [{
		        	     xtype:'hidden',
		        	     name:'phoneBook.phoneId',
		        	     id:'phoneId'
		                },{
		                layout: 'column',
		                border:false,
		                items: [{
		                	layout:'form',
		                	columnWidth:.6,	
		                	border:false,
		                    items:[phoneGroupSelector,
		                    {
		                     xtype:'hidden',
		                     id:'groupId',
		                     allowBlank:false,
		                     name:'phoneBook.phoneGroup.groupId'
		                    },{
		                    xtype:'textfield',
		                    fieldLabel: '姓名*',
		                    allowBlank:false,
		                    width:162,
		                    name: 'phoneBook.fullname',
		                    id:'fullname'
		                },{
		                    xtype:'datefield',
		                    fieldLabel: '出生时间',
		                    name:'phoneBook.birthday',
		                    id:'birthday',
		                    editable : false,
		    				format : 'Y-m-d',
		    				length : 50,
		    				width:162
		                
		            }]},{
		            	layout:'form',
		            	columnWidth:.4,
		            	border:false,
		            	items:[
				            {
								fieldLabel : '称谓*',
								xtype : 'combo',
								anchor:'95%',
								allowBlank:false,
								name : 'phoneBook.title',
								id:'title',
								mode : 'local',
								editable : false,
								triggerAction : 'all',
								store : ['先生', '女士']
							},{
								layout:'form',
								border:false,
								hidden:this.isPublic,
								defaults:{
								   anchor : '96%,96%'
								},
								items:[{
						           	 xtype : 'combo',
						           	 fieldLabel: '是否共享*',
						           	 allowBlank:false,
						             hiddenName: 'phoneBook.isShared',
						             id:'isShared',
						             mode : 'local',
						             value:'0',
									 editable : false,
									 triggerAction : 'all',
									 store : [['0','否'],['1','是']],
						             anchor:'95%'
						            }]
				            },{
				                xtype:'textfield',
				                fieldLabel: '昵称',
				                anchor:'95%',
				                name: 'phoneBook.nickName',
				                id:'nickName'
				             }]}]
				        },{
				            xtype:'tabpanel',
				            plain:true,
				            activeTab: 0,
				            height:180,
				            defaults:{bodyStyle:'padding:10px'},
				            items:[{
				                title:'联系方式',
				                layout:'form',
				                defaults: {width: 300},
				                defaultType: 'textfield',
				                items: [{
				                    fieldLabel: '手机号码',
				                    allowBlank : false,
									blankText : '手机号码不能为空!',
				                    name: 'phoneBook.mobile',
				                    id:'mobile',
				                    maxLength : 12,
									regex : /(86)*0*1\d{10}/,
									regexText : '移动电话输入有误！'
				                },{
				                    fieldLabel: '固定电话',
				                    name: 'phoneBook.phone',
				                    id:'phone',
				                    maxLength : 12,
									regex : /(^(\d{3,4}-)?\d{7,8})$|(1[0-9]{10})/,
									regexText : '电话号码输入有误！'
				                },{
				                    fieldLabel: 'Email',
				                    name: 'phoneBook.email',
				                    vtype : 'email',
									vtypeText : '邮箱格式不正确!',
									allowBlank : false,
									blankText : '邮箱不能为空!',
				                    id:'email',
				                    maxLength : 32
				                }, {
				                    fieldLabel: 'QQ',
				                    name: 'phoneBook.qqNumber',
				                    id:'qqNumber',
				                    maxLength : 32
				                }, {
				                    fieldLabel: 'MSN',
				                    name: 'phoneBook.msn',
				                    id:'msn',
				                    maxLength : 128
				                }]
				            },{
				                title:'公司',
				                layout:'form',
				                defaults: {width: 300},
				                defaultType: 'textfield',
				                items: [{
				                    fieldLabel: '职务',
				                    name: 'phoneBook.duty',
				                    id:'duty',
				                    maxLength : 50
				                },{
				                    fieldLabel: '单位名称',
				                    name: 'phoneBook.companyName',
				                    id:'companyName',
				                    maxLength : 100
				                },{
				                    fieldLabel: '单位地址',
				                    name: 'phoneBook.companyAddress',
				                    id:'companyAddress',
				                    maxLength : 128
				                },{
				                    fieldLabel: '单位电话',
				                    name: 'phoneBook.companyPhone',
				                    id:'companyPhone',
				                    maxLength : 12 //,
//									regex : /(^(\d{3,4}-)?\d{7,8})$|(13[0-9]{9})/,
//									regexText : '电话号码输入有误！'
				                },{
				                    fieldLabel: '单位传真',
				                    name: 'phoneBook.companyFax',
				                    id:'companyFax',
				                    maxLength : 32
				                }]
				            },{
				                title:'家庭',
				                layout:'form',
				                defaults: {width: 300},
				                defaultType: 'textfield',
				                items: [{
				                    fieldLabel: '家庭住址',
				                    name: 'phoneBook.homeAddress',
				                    id:'homeAddress',
				                    maxLength : 128
				                },{
				                    fieldLabel: '家庭邮编',
				                    name: 'phoneBook.homeZip',
				                    id:'homeZip',
				                    maxLength : 12
				                },{
				                    fieldLabel: '配偶',
				                    name: 'phoneBook.spouse',
				                    id:'spouse',
				                    maxLength : 32
				                },{
				                    fieldLabel: '子女',
				                    name: 'phoneBook.childs',
				                    id:'childs',
				                    maxLength : 40
				                }]
				            },{
				                cls:'x-plain',
				                title:'备注',
				                layout:'fit',
				                items: {
				                    xtype:'textarea',                    
				                    id:'note',
				                    fieldLabel:'备注',
				                    name:'phoneBook.note',
				                    maxLength : 500
				                }
				            }]
		        }]
			});
			if (this.phoneId != null && this.phoneId != 'undefined') {
				this.formPanel.getForm().load(
						{
							deferredRender : false,
							url : __ctxPath + '/communicate/getPhoneBook.do?phoneId='
									+ this.phoneId,
						    method:'post',				    
							waitMsg : '正在载入数据...',
							success : function(form, action) {
							    var birthday=action.result.data.birthday;
		                        if(birthday!=null){
		                        var birthdayField=Ext.getCmp('birthday');
		                        birthdayField.setValue(new Date(getDateFromFormat(birthday, "yyyy-MM-dd HH:mm:ss")));
		                        }
								var groupNameField=Ext.getCmp('phoneGroupSelect');
								var groupIdField=Ext.getCmp('groupId');
								var groupName=action.result.data.phoneGroup.groupName;
								var groupId=action.result.data.phoneGroup.groupId;
								if(groupName!=null&&groupId!=null){
									groupNameField.setValue(groupName);
		                        	groupIdField.setValue(groupId);
								}
		                        
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('编辑', '载入失败');
						}
						});
			}
			
          this.buttons = [{
              xtype:'button',
              text:'保存',
              iconCls:'btn-save',
              handler:this.save.createCallback(this.formPanel,this)
          },{
              xtype:'button',
              text:'取消',
              iconCls:'btn-cancel',
              handler:this.cancel.createCallback(this)
          }];
     },
     save:function(fp,win){
			var phoneGroupSelect=Ext.getCmp('phoneGroupSelect').getValue();
			if(phoneGroupSelect!=null&&phoneGroupSelect!=''&&phoneGroupSelect!='undefined'){
			if (fp.getForm().isValid()) {
				fp.getForm().submit( {
					method : 'post',
					waitMsg : '正在提交数据...',
					success : function(fp, action) {
						Ext.ux.Toast.msg('操作信息', '成功信息保存！');
						var phoneBookView=Ext.getCmp('PhoneBookView');
						if(phoneBookView!=null){
							var store=phoneBookView.dataView.getStore();;
							store.reload( {
								params : {
									start : 0,
									limit : 15
								}
							});	
						}
						var publicPhoneBookView=Ext.getCmp('PublicPhoneBookView');
						if(publicPhoneBookView!=null){
						   var store=publicPhoneBookView.store;;
							store.reload({
								params : {
									start : 0,
									limit : 15
								}
							});
						}
						win.close();
					},
					failure : function(fp, action) {
						Ext.MessageBox.show( {
							title : '操作信息',
							msg : '信息保存出错，请联系管理员！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
						});
						win.close();
					}
				});
			}
			}else{
			   Ext.ux.Toast.msg('操作提示', '分组不能为空！');
			}
     },//save method end
     cancel:function(self){
        self.close();
     }//cancel method end
});
