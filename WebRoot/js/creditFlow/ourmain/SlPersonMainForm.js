/**
 * @author 
 * @createtime 
 * @class SlPersonMainForm
 * @extends Ext.Window
 * @description SlPersonMain表单
 * @company 北京互融时代软件有限公司
 */
SlPersonMainForm = Ext.extend(Ext.Window, {
	//构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.isReadOnlyPerson)!="undefined"){
           this.isReadOnlyPerson=_cfg.isReadOnlyPerson;
        }
        if(typeof(_cfg.gridPanelPerson)!="undefined"){
           this.gridPanelPerson=_cfg.gridPanelPerson;
        }
		var p=Ext.getCmp("cardNum");
		var anchor = '96%';
       	var s=0;
		Ext.applyIf(this, _cfg);
		//必须先初始化组件
		this.initUIComponents();
		SlPersonMainForm.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 230,
			width : 550,
			maximizable : true,
			title : '个人主体详细信息',
			buttonAlign : 'center',
			buttons : [ {
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				disabled : this.isReadOnlyPerson,
				handler : this.save
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				disabled : this.isReadOnlyPerson,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				handler : this.cancel
			} ]
		});
	},//end of the constructor
	//初始化组件
	initUIComponents : function() {
		var perId=this.id;
		this.formPanel = new Ext.FormPanel( {
			
			layout:'column',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			monitorValid : true,
			frame : true,
		    plain : true,
		    labelAlign:'right',
			defaults : {
				anchor : anchor,
				labelWidth : 70,
				columnWidth : 1,
			    layout : 'column'
			},
			//defaultType : 'textfield',
			items : [ {
				name : 'slPersonMain.personMainId',
				xtype : 'hidden',
				value : this.id == null ? '' : this.id
			},{
				xtype : 'hidden',
				name : 'slPersonMain.isPledge',
				value : this.isPledge
			},{
				columnWidth : .5,
				layout : 'form',

				items : [ {
					xtype:'textfield',
					fieldLabel : '姓名',
					allowBlank : false,
					anchor : anchor,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.name'
				}, {
					xtype : "dickeycombo",
					hiddenName : "slPersonMain.cardtype",
					id:"cardType",
					nodeKey : 'card_type_key', // xx代表分类名称
					fieldLabel : "证件类型",
					readOnly : this.isReadOnlyPerson,
					allowBlank : false,
					editable : false,
					emptyText : "请选择",
					blankText : "证件类型不能为空，请正确填写!",
					anchor : "96%",
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox.getValue());
								combox.clearInvalid();
							})
				       }
					}
				}, {
					xtype:'textfield',
					fieldLabel : '联系电话',
					labelWidth : 60,
					anchor : anchor,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.linktel'
				}]
			}
			, {
				columnWidth : .5,
				layout : 'form',

				items : [ {
					
					xtype:'combo',
					id:'sex',
					mode : 'local',
				    displayField : 'name',
				    valueField : 'id',
				    width : 70,
				    store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["男", "0"],
									["女", "1"]]
					}),
					triggerAction : "all",
					hiddenName:"slPersonMain.sex",
					fieldLabel : '性别',
			        anchor : anchor,
					allowBlank : false,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.sex'
				}, {
					xtype:'textfield',
					id:'cardNum',
					fieldLabel : '证件号码',
					labelWidth :70,
			        anchor : anchor,
					allowBlank : false,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.cardnum',
					listeners: {  
                        blur: function(p) {  
                            var cardnum=p.getValue();

                            Ext.Ajax.request({
			                   url:  __ctxPath + '/creditFlow/ourmain/verificationSlPersonMain.do',
			                   method : 'POST',
			                   params : {
										cardNum : cardnum,
										id:perId
									},
			                  success : function(response,request) {
									var xx=response.responseText.toString().trim();
                            		if(xx=="{success:false}"){
                            			Ext.ux.Toast.msg('操作信息',"该证件号码已存在，请重新输入");
                            			Ext.getCmp("cardNum").setValue("");
                            		}
                            		if(Ext.getCmp("cardNum").getValue().length == 18 && Ext.getCmp("cardType").getValue() == 309){
                            		   var sex = (Ext.getCmp("cardNum").getValue().slice(16,17)%2 ==0) ?"1":"0"
                            		   Ext.getCmp('sex').setValue(sex);
                            		}
		                      }
                             });  
                        }
				   }
				} , {
					xtype:'textfield',
					fieldLabel : '传真号码',
					labelWidth : 60,
					anchor : anchor,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.tax'
				}]
			},{
				columnWidth : 0.5,
				layout : 'form',
				items : [ {
					xtype:'textfield',
					fieldLabel : '通讯地址',
					labelWidth : 60,
					anchor : '98%',
					allowBlank:false,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.address'
				} ]
			},{
				columnWidth : 0.5,
				layout : 'form',
				items : [ {
					xtype:'textfield',
					fieldLabel : '邮政编码',
					labelWidth : 60,
					anchor : '98%',
					allowBlank:false,
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.postalCode'
				} ]
			}
			,{
				columnWidth : 0.5,
				layout : 'form',
				items : [ {
					xtype:'textfield',
					fieldLabel : '住所',
					labelWidth : 60,
					anchor : '98%',					
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.home'
				} ]
			} ,{
				columnWidth : 0.5,
				layout : 'form',
				items : [ {
					xtype:'textfield',
					fieldLabel : '固定电话',
					labelWidth : 60,
					anchor : '98%',
					readOnly : this.isReadOnlyPerson,
					name : 'slPersonMain.tel'
				} ]
			}]
		});
		//加载表单对应的数据	
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.loadData( {
				url : __ctxPath + '/creditFlow/ourmain/getSlPersonMain.do?id=' + this.id,
				root : 'data',
				preName : 'slPersonMain'
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
		$postForm( {
			formPanel : this.formPanel,
			scope : this,
			msg : (this.id != null && this.id != 'undefined')?'保存成功':'添加成功',
			url : __ctxPath + '/creditFlow/ourmain/saveSlPersonMain.do',
			callback : function(fp, action) {
				if(this.gridPanelPerson!=null||typeof(this.gridPanelPerson)!="undefined"){
					this.gridPanelPerson.getStore().reload();
				}
				//刷新datagrid
				/*var gridPanel = Ext.getCmp('SlPersonMainGrid');
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}*/
				this.close();
			}
		});
	}//end of save

   
});