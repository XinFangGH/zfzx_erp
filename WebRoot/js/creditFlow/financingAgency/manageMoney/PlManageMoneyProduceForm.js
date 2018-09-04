/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
PlManageMoneyProduceForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		PlManageMoneyProduceForm.superclass.constructor.call(this, {
			id : 'PlManageMoneyProduceForm',
			layout : 'fit',
			border : false,
			items : this.formPanel,
			modal : true,
			height : 460,
			width : 1000,
			// maximizable : true,
			title : '理财产品详细信息',
			buttonAlign : 'center',
			buttons : [{
				text : '保存',
				iconCls : 'btn-save',
				scope : this,
				hidden : this.isAllReadOnly,
				disabled : this.isAllReadOnly,
				handler : this.save
			}, {
				text :"提交",
				iconCls : 'btn-transition',
				hidden : this.isAllReadOnly,
				scope : this,
				handler : this.submit
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				hidden : this.isAllReadOnly,
				disabled : this.isAllReadOnly,
				handler : this.reset
			}, {
				text : '取消',
				iconCls : 'btn-cancel',
				scope : this,
				hidden : this.isAllReadOnly,
				disabled : this.isAllReadOnly,
				handler : this.cancel
			}]
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leffwidth = 100;
		this.mark=this.mmplanId;
		this.uploads= new uploads({
	    	projectId:this.mark,
	    	isHidden : false,
	    	businessType :"fsdf",
	    	isNotOnlyFile : false,
	    	isHiddenColumn : false,
	    	isDisabledButton : false,
	    	setname :'理财协议',
	    	titleName :'理财协议',
	    	tableName :'typeislcjhxy',
	    	uploadsSize :1,
	    	isHiddenOnlineButton :true,
	    	isDisabledOnlineButton :true
	    })
							    
		var storepayintentPeriod = "[";
		for (var i = 1; i < 31; i++) {
			storepayintentPeriod = storepayintentPeriod + "[" + i + ", " + i+ "],";
		}
		storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
		storepayintentPeriod = storepayintentPeriod + "]";
		var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
		this.formPanel = new Ext.FormPanel({
			layout : 'column',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : true,
			plain : true,
			labelAlign : "right",
			defaults : {
				anchor : '96%',
				labelWidth : 80,
				columnWidth : 1,
				layout : 'column'
			},
			items : [{
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{         
					xtype : 'hidden',
                    id:'plManageMoneyPlan.mmplanId',
					name : 'plManageMoneyPlan.mmplanId'
				},{					
					fieldLabel : '理财产品类型',
					xtype : 'textfield',
					hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
					allowBlank : false,
					xtype : "combo",
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					readOnly : this.isEdit,
					store : new Ext.data.ArrayStore({
						url : __ctxPath+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr=mmproduce',
						fields : ['itemId', 'itemName'],
						autoLoad : true
					}),
					mode : 'remote',
					editable : false,
					blankText : "理财计划类型不能为空，请正确填写!",
					anchor : "95%",
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function() {
								combox.setValue(combox.getValue());
							})
							combox.clearInvalid();
						}
					}
				},{
				  	xtype:"hidden",
				 	name : 'plManageMoneyPlan.mmName'
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '产品编号',
					name : 'plManageMoneyPlan.mmNumber',
					allowBlank : false,
					readOnly : this.isEdit,
					xtype : 'textfield',
					anchor : '95%'
				}]
			}, {
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '保障方式',
					xtype : 'textfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.guaranteeWay',
					anchor : '95%'
				}]
			},  {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '收益方式',
					name : 'plManageMoneyPlan.benefitWay',
					allowBlank : false,
					xtype : 'textfield',
					anchor : '95%'
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '购买开放时间',
					name : 'plManageMoneyPlan.buyStartTime',
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					allowBlank : false,
					value : new Date(),
					anchor : '95%'
				}]
			}, {
				columnWidth : .315,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '购买结束时间',
					name : 'plManageMoneyPlan.buyEndTime',
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
					allowBlank : false,
					anchor : '100%',
					listeners : {
						scope : this,
						'blur' : function(nf){
							if(nf.getValue()!=null && nf.getValue()!=""){
								var buyStartTime=this.getCmpByName("plManageMoneyPlan.buyStartTime").getValue();
								if(buyStartTime!=null){
									var formatbuyStartTime=new Date(buyStartTime).getTime();
									var formatbuyEndTime=new Date(nf.getValue()).getTime();
									if(formatbuyStartTime>=formatbuyEndTime){
										Ext.ux.Toast.msg('操作信息',"购买开放时间不能小于等于购买结束时间，请重新填写!");
								 		nf.setValue(null);
									}
								}else{
								 	Ext.ux.Toast.msg('操作信息',"请先填写购买开放时间!");
								 	nf.setValue(null);
								}
							}else{
								Ext.ux.Toast.msg('操作信息',"请先填写购买结束时间!");
							}
						}
					}
				}]
			}, {
				columnWidth : .315,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '投资起点金额',
					name : 'plManageMoneyPlan.startMoney',
					fieldClass : 'field-align',
					allowBlank : false,
					xtype : 'moneyfield',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>元</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .31,
				labelWidth : 94,
				layout : 'form',
				items : [{
					fieldLabel : '递增金额',
					fieldClass : 'field-align',
					xtype : 'moneyfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.riseMoney',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>元</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .31,
				labelWidth : 94,
				layout : 'form',
				items : [{
					fieldLabel : '单笔投资上限',
					xtype : 'moneyfield',
					name : 'plManageMoneyPlan.limitMoney',
					fieldClass : 'field-align',
					allowBlank : false,
					anchor : '100%',
					listeners : {
						scope:this,
						'blur' : function(f){
							if(Number(this.getCmpByName('limitMoney1').hiddenField.value)<Number(this.getCmpByName('startMoney1').hiddenField.value)){
								Ext.Msg.alert('单笔投资上限金额不能小于投资起点金额');
								f.setValue();
							}
						}
					}
				}]
			}, {
				columnWidth : .025, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>元</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .315,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '起息条件',
					xtype : 'textfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.startinInterestCondition',
					anchor : '100%'
				}]
			}, {
				columnWidth : .33,
				labelWidth : 113,
				layout : 'form',
				items : [{
					fieldLabel : '到期赎回方式',
					xtype : 'textfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.expireRedemptionWay',
					anchor : '100%'
				}]
			}, {
				columnWidth : .33,
				labelWidth : 113,
				layout : 'form',
				items : [{
					fieldLabel : '投资范围',
					name : 'plManageMoneyPlan.investScope',
					xtype : 'textfield',
					anchor : '100%'
				}]
			}, {
				columnWidth : .315,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '投资期限',
					name : 'plManageMoneyPlan.investlimit',
					fieldClass : 'field-align',
					allowBlank : false,
					xtype : 'numberfield',
					readOnly : this.isEdit,
					anchor : '100%'
				}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>期</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .3,
				labelWidth : 85,
				layout : 'form',
				items : [{
					fieldLabel : '年化收益率',
					xtype : 'numberfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.yeaRate',
					fieldClass : 'field-align',
					readOnly : this.isEdit,
					anchor : '100%'
				}]
			}, {
				columnWidth : .03, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .325,
				layout : 'form',
				labelWidth : 85,
				items : [{
					  xtype:'combo',
			          mode : 'local',
		              displayField : 'name',
		              valueField : 'id',
		              id :　'accountTypeid',
		              editable : false,
		              readOnly : this.isEdit,
	                  width : 70,
	                  store : new Ext.data.SimpleStore({
			       		  fields : ["name", "id"],
		           		  data : [["一次性", "1"],["非一次性", "0"]/*,["循环出借", "2"]*/]
	              	  }),
		              triggerAction : "all",
	                  hiddenName:"plManageMoneyPlan.isOne",
                	  fieldLabel : '派息类型',	
                	  anchor : "94%",
                	  allowBlank:false,
		          	  name : 'plManageMoneyPlan.isOne',
		          	  listeners : {
          	  			 scope : this,
						 afterrender : function(combox) {
							var st = combox.getStore();
							combox.setValue(combox.getValue());
						 },
					 	 change:function(combox){
							var  flag=combox.getValue();
							if(flag==1){
								Ext.getCmp("meiqihkrq1").setDisabled(true);
								this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
							}else{
								Ext.getCmp("meiqihkrq1").setDisabled(false);
								this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(false);
							}
						 }
				  	 } 
				}]
			},{
						columnWidth : .66,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.278,
							labelWidth : leffwidth,
							layout : 'form',
							items : [{
								xtype : 'radio',
								fieldLabel : '每期派息日',
								boxLabel : '固定在',
								name : 'm1',
								id : "meiqihkrq1",
								inputValue : true,
								anchor : "100%",
								disabled : this.isEdit,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq1").getValue();
										if (flag == true && !this.isBuyApply) {
											this.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("1");
											this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(false);
										}
									}
								}

							}, {
								xtype : "hidden",
								name : "plManageMoneyPlan.isStartDatePay",
								id:"plManageMoneyPlan.isStartDatePay",
								value:'2'

							}]
						}, {
							columnWidth : 0.132,
							labelWidth : 1,
							layout : 'form',
							items : [{
								xtype : 'textfield',
								readOnly : this.isEdit,
								name : "plManageMoneyPlan.payintentPerioDate",
								id:"plManageMoneyPlan.payintentPerioDate",
								xtype : 'combo',
								mode : 'local',
								disabled : true,
								displayField : 'name',
								valueField : 'id',
								editable : true,
								store : new Ext.data.SimpleStore({
											fields : ["name", "id"],
											data : obstorepayintentPeriod
										}),
								triggerAction : "all",
								hiddenName : "plManageMoneyPlan.payintentPerioDate",
								fieldLabel : "",
								anchor : '100%'
							}]
						}, {
							columnWidth : 0.12,
							labelWidth : 45,
							layout : 'form',
							items : [{
										fieldLabel : "号还款",
										labelSeparator : '',
										anchor : "100%"
									}]
						}, {
							columnWidth : 0.47,
							labelWidth : 10,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '按实际投资日派息',
								name : 'm1',
								id : "meiqihkrq2",
								inputValue : true,
								checked : true,
								anchor : "100%",
								disabled : this.isEdit,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("meiqihkrq2"
											).getValue();
										if (flag == true) {
											this.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("2");
											this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setValue(null);
											this.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
										}
									}
								}

							}]
						}]
					
					
					},{
					
					
						columnWidth : .34,
						name : "mqhkri",
						layout : "column",
						items : [{
							columnWidth : 0.5,
							labelWidth : leffwidth,
							layout : 'form',
							items : [{
								xtype : 'radio',
								fieldLabel : '付息周期',
								boxLabel : '日',
								name : 'q1',
								id : "jixizq1",
								inputValue : true,
								anchor : "100%",
								disabled : this.isEdit,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq1").getValue();
										if (flag == true) {
											this.getCmpByName('plManageMoneyPlan.payaccrualType').setValue("dayPay");
										}
									}
								}

							}, {
								xtype : "hidden",
								name : "plManageMoneyPlan.payaccrualType",
								id:"plManageMoneyPlan.payaccrualType",
								value:'monthPay'
							}]
						},  {
							columnWidth : 0.5,
							labelWidth : 10,
							layout : 'form',
							items : [{
								xtype : 'radio',
								boxLabel : '月',
								name : 'q1',
								id : "jixizq2",
								inputValue : true,
								checked : true,
								anchor : "100%",
								disabled : this.isEdit,
								listeners : {
									scope : this,
									check : function(obj, checked) {
										var flag = Ext.getCmp("jixizq2"
											).getValue();
										if (flag == true) {
											this.getCmpByName('plManageMoneyPlan.payaccrualType').setValue("monthPay");
										}
									}
								}

							}]
						}]
					
					
					},  {
						columnWidth : 1,
						labelWidth : leffwidth,
						layout : 'form',
						items : [{
									fieldLabel : '产品说明',
									name : 'plManageMoneyPlan.bidRemark',
									xtype : 'textarea',
									anchor : '100%'
								}]
					}, {
						columnWidth : 1,
						labelWidth : leffwidth,
						layout : 'form',
						items : [this.uploads]
					}]
		});

		// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			var mainObj=this.formPanel;
			this.formPanel.loadData({
				url : __ctxPath
						+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='
						+ this.mmplanId,
				root : 'data',
				preName : 'plManageMoneyPlanfsdfsd',
				success : function(response, options) {
					var respText = response.responseText;
					var alarm_fields = Ext.util.JSON.decode(respText);
					var isStartDatePay=alarm_fields.data.plManageMoneyPlan.isStartDatePay;
					if (isStartDatePay == "1") {
						Ext.getCmp("meiqihkrq1").setValue(true);
						Ext.getCmp("meiqihkrq2").setValue(false);
						mainObj.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("1");
					} else {
						Ext.getCmp("meiqihkrq2").setValue(true);
						Ext.getCmp("meiqihkrq1").setValue(false);
						mainObj.getCmpByName('plManageMoneyPlan.payintentPerioDate').setValue(null);
						mainObj.getCmpByName('plManageMoneyPlan.payintentPerioDate').setDisabled(true);
						mainObj.getCmpByName('plManageMoneyPlan.isStartDatePay').setValue("2");
					}
					
		
					var payaccrualType=alarm_fields.data.plManageMoneyPlan.payaccrualType;
					if (payaccrualType == "dayPay") {
						Ext.getCmp("jixizq1").setValue(true);
						Ext.getCmp("jixizq2").setValue(false);
					} else if (payaccrualType == "monthPay") {
						Ext.getCmp("jixizq2").setValue(true);
						Ext.getCmp("jixizq1").setValue(false);
					}else{
					    Ext.getCmp("jixizq2").setValue(true);
						Ext.getCmp("jixizq1").setValue(false);
					}
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
		var fpl= this.formPanel;
		var mark=null;
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/savePlManageMoneyPlan.do?keystr=mmproduce',
			callback : function(fp, action) {
				var obj = Ext.util.JSON.decode(action.response.responseText);
				fpl.getCmpByName("plManageMoneyPlan.mmplanId").setValue(obj.id);
				this.uploads.projId = obj.id;
				this.uploads.mark=this.uploads.tableName+'.'+obj.id;
				this.uploads.typeisfile="typeisonlyfile";
				
				var mark=this.uploads.tableName+'.'+obj.id;
				var typeisfile="typeisonlyfile";
				var gridstore =this.uploads.grid_UploadsPanel.getStore()
				gridstore.on('beforeload', function(gridstore, o) {
					var new_params = {
						mark : mark,
						typeisfile : typeisfile
					};
					Ext.apply(o.params, new_params);
				});
				var gridPanel = Ext.getCmp('PlManageMoneyProduceGrid'+this.type);
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				// this.close();
			}
		});
			
	}// end of save
,// end of save
    submit:function(){
    	
	var gridPanel=this.gridPanel ;
	$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath
					+ '/creditFlow/financingAgency/savePlManageMoneyPlan.do?keystr=mmproduce',
			callback : function(fp, action) {
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				 this.close();
			}
		});
	
	
}
});