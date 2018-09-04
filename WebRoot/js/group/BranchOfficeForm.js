/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
BranchOfficeForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.operateGrid=_cfg.operateGrid;
		this.initUIComponents();
		BranchOfficeForm.superclass.constructor.call(this, {
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height:340,
					width : 650,
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
        
           this.formPanel= new Ext.FormPanel({
                labelAlign:'right',
                buttonAlign:'right',
                bodyStyle:'padding:5px;',
                width:600,
                scope:this,
                frame:true,//设置了面板的边角是圆弧过度的，底色
                labelWidth:100,
                items:[{
					name : 'organization.orgId',
					xtype : 'hidden',
					value : this.orgId == null ? '' : this.orgId
				},{
					name : 'organization.demId',
					xtype : 'hidden',
					value : 1
				},{
					name : 'organization.orgType',
					xtype : 'hidden',
					value:1
				},{
					name : 'organization.vmName',
					xtype : 'hidden'
				},{
					fieldLabel : '上级组织',
					name : 'organization.orgSupId',
					value:this.orgSupId?this.orgSupId:1,
					xtype : 'hidden'
				},{
                    layout:'column',//在formpanel的itmes加入一个column的定义
                    border:false,
                    labelSeparator:':',
                    items:[{
                        columnWidth:.5,//宽度50%，新起一行
                        layout: 'form',
                        border:false,
                        defaultType : 'textfield',
                        items: [{
                            fieldLabel: '分公司名称',
        					regexText:"分公司名称只能输入中文!",         //正则表达式错误提示
                            name: 'organization.orgName',
                            allowBlank : false,
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'分公司访问后缀',
                            allowBlank : false,
                            vtype:"alpha",//email格式验证
                            name:'organization.key',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'分公司编号',
                            allowBlank : false,
                            name:'organization.branchNO',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'分公司缩写',
                            allowBlank : false,
                            name:'organization.acronym',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'dickeycombo',                            
                            fieldLabel:'股权关系',
							allowBlank : false,
							hiddenName : 'organization.equityRelationship',
							nodeKey : 'equityRelationship',
							emptyText : "请选择",
                            anchor:'90%',	
                            listeners : {
						    afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();						
								})
							}}
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'dickeycombo',                            
                            fieldLabel:'托管方式',
							allowBlank : false,
							hiddenName : 'organization.trusteeshipMode',
							nodeKey : 'trusteeshipMode',
							emptyText : "请选择",
                            anchor:'90%',	
                            listeners : {
						    afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();						
								})
							}}
                        }]
                    },{
                    	columnWidth : .45,
                    	layout : 'form',
                    	border:false,
                    	items : [{
                    		xtype : 'numberfield',
                    		fieldLabel : '资本金',
                    		fieldClass : 'field-align',
                    		allowBlank:false,
                    		anchor:'100%',
                    		name : 'organization.capital'
                    	}]
                    }, {
						columnWidth : .05, 
						layout : "form",
						labelWidth : 20,
						border:false,
						items : [{
									fieldLabel : "元 ",
									labelSeparator : '',
									anchor : "100%"
								}]
					},{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{fieldLabel : '是否可用',
								hiddenName : 'organization.delFlag',
								xtype : 'combo',
								mode : 'local',
								allowBlank:false,
								editable : false,
							    anchor:'90%',
								triggerAction : 'all',
								store : [['1', '激活'], ['0', '禁用']],
								value : 1}]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'联系人',
                            name:'organization.linkman',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'联系电话',
                            regex : /(^(\d{3,4}-)?\d{7,8})$|(13[0-9]{9})|(15[0-9]{8})|(18[0-9]{9})/,
							regexText : '电话号码格式不正确或者无效的号码，座机格式：010-5529192，手机格式：13466761447',
                            name:'organization.linktel',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype : 'textfield',                   
                            fieldLabel:'传真',
                            regex : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
                            regexText : '请输入正确的传真号码如010-5529192',
                            name:'organization.fax',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype : 'textfield',                    
                            fieldLabel:'邮政编码',
                            regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确',
                            name:'organization.postCode',
                            anchor:'90%'
                        }]
                    },{
                        columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textfield',                            
                            fieldLabel:'地址',
                            name:'organization.address',
                            anchor:'95%'
                        }]
                    },{
                    	columnWidth:.5,
                        layout:'form',
                        border:false,
                        items:[{
                        	fieldLabel : '成立时间',
							name : 'organization.foundingTime',
							allowBlank:false,
							xtype : 'datefield',
							anchor : "90%",
							format : 'Y-m-d'
                        }]
                    },{
                        columnWidth:1,
                        layout:'form',
                        border:false,
                        items:[{
                            xtype:'textarea',                           
                            fieldLabel:'备注',
                            name:'organization.orgDesc',
                            anchor:'95%'
                        }]
                    },{
											name : 'chargeIds',
											xtype : 'hidden',
											value:null
					}]
                }]
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
					isShow:false,
					params:{addCompany:true},
				    url : __ctxPath+'/system/saveOrganization.do',
					callback : function(fp, action) {
						if(action.result.success==true){
							    var scope=this.scope?this.scope:this;
										if(this.callback){
											this.callback.call(scope);
								}
								if (this.operateGrid!= null) {
									this.operateGrid.getStore().reload();
								}
							this.close();
						}
						else{
						     Ext.ux.Toast.msg('操作信息', action.result.msg);
						}
					}
				});
	}//end of save

});