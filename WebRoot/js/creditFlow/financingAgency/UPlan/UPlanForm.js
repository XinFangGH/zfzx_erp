/**
 * @author
 * @createtime
 * @class SlBankAccountForm
 * @extends Ext.Window
 * @description SlBankAccount表单
 * @company 智维软件
 */
UPlanForm = Ext.extend(Ext.Window, {
	tempAccount:'',
	balanceMoneyObj:{},
	task:null,
	runner:new Ext.util.TaskRunner(),
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		UPlanForm.superclass.constructor.call(this, {
			id : 'UPlanForm',
			layout : 'fit',
			border : false,
			items : [this.formPanel],
			modal : true,
			height : 585,
			width : 1000,
			// maximizable : true,
			title : this.keystr=='UPlan'?'U计划详细信息':'D计划详细信息',
			buttonAlign : 'center',
			buttons : [{
				text :"起息",
				iconCls : 'btn-transition1',
				hidden : this.isHidden,
				scope : this,
				handler : this.start
			}, {
				text :"保存",
				iconCls : 'btn-save',
				hidden : this.isAllReadOnly,
				scope : this,
				handler : this.submit
			}, {
				text : '重置',
				iconCls : 'btn-reset',
				scope : this,
				hidden : this.isAllReadOnly,
				handler : this.reset
			}, {
				text : '关闭',
				iconCls : 'close',
				scope : this,
				hidden : this.isAllReadOnly,
				handler : this.cancel
			}],
			listeners:{
				scope:this,
				'close':function(){
					if(this.runner){
						this.runner.stop(this.task);
					}
				}
			}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var leffwidth = 100;
		
	    this.UPlanOrder=	new UPlanOrder({
	      mmplanId: this.mmplanId,
	      keystr:this.keystr,
	      isHidden :this.isHidden
	    });	
	    
	     //投资人优惠券生成
	    this.PlManageMoneyPlanCoupons=	new PlManageMoneyPlanCoupons({
	      	mmplanId: this.mmplanId,
	      	keystr:this.keystr,
	      	seeIsCreateIntent:this.seeIsCreateIntent
	  	});
	  	
	  	//每隔5秒刷新剩余债权金额
	  	this.task = {
		    run: function(){
		    	var win=Ext.getCmp('UPlanForm');
		    	Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/financingAgency/findBalanceMoneyPlManageMoneyPlan.do',
					method : 'post',
					params : {
						account:win.tempAccount,
						keystr:win.keystr
					},
					success : function(response,request) {
						var obj = Ext.decode(response.responseText);
						win.balanceMoneyObj.setValue(Ext.util.Format.number(obj.money, '0,000.00'));
						win.balanceMoneyObj.hiddenField.value=obj.money;
					}
				});
		    },
		    interval: 120000 //2 minute
		}
	  	
		this.formPanel = new Ext.FormPanel({
			name : 'UPlanFormPanel',
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
					name : 'plManageMoneyPlan.isStartDatePay',
					value:'2'
				},{
					xtype : 'hidden',
					name : 'plManageMoneyPlan.payaccrualType',
					value:'monthPay'
				},{
					xtype : 'hidden',
					id : 'plManageMoneyPlan.mmplanId',
					name : 'plManageMoneyPlan.mmplanId'
				},{
					xtype : 'hidden',
					id : 'plManageMoneyPlan.receiverType',
					name : 'plManageMoneyPlan.receiverType',
					value:'zc'
				}, {
					fieldLabel : '债权类型',
					hiddenName : 'plManageMoneyPlan.manageMoneyTypeId',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					xtype : "combo",
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					store : new Ext.data.ArrayStore({
						url : __ctxPath+ '/creditFlow/financingAgency/getListbykeystrPlManageMoneyType.do?keystr='+this.keystr,
						fields : ['itemId', 'itemName','receivablesAccount','accountType'],
						autoLoad : true
					}),
					mode : 'remote',
					editable : false,
					blankText : "类型不能为空，请正确填写!",
					anchor : "100%",
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							st.on("load", function(comstore) {
								combox.setValue(combox.getValue());
							})
							combox.clearInvalid();
						},
						select: function(com,record){
							//此处有一个隐患就是编辑的时候加载该页面没有办法获得record对象,导致不能执行5秒刷新
							this.getCmpByName('plManageMoneyPlan.moneyReceiver').setValue(record.get("receivablesAccount"));
							this.getCmpByName('plManageMoneyPlan.receiverType').setValue(record.get("accountType"));
							if(record){
								this.tempAccount=record.get("receivablesAccount");
								this.balanceMoneyObj=this.getCmpByName('balanceMoney1');
								this.runner.start(this.task);
							}
						}
					}
				}, {
					xtype : "hidden",
					name : 'plManageMoneyPlan.mmName'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '原始债权人',
					name : 'plManageMoneyPlan.moneyReceiver',
					readOnly :true,
					allowBlank : false,
					xtype : 'textfield',
					anchor : '100%'
				}]
			},{
				
				columnWidth : .32,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '剩余债权金额',
					name : 'plManageMoneyPlan.balanceMoney',
					xtype : 'moneyfield',
					readOnly : true,
					allowBlank : false,
					anchor : '100%'
				}]
			},{
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>元</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '预售开放时间',
					name : 'plManageMoneyPlan.preSaleTime',
					xtype : 'datetimefield',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					format : 'Y-m-d H:i:s',
					value : new Date(),
					anchor : '100%'
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '购买开放时间',
					name : 'plManageMoneyPlan.buyStartTime',
					xtype : 'datetimefield',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					format : 'Y-m-d H:i:s',
					value : new Date(),
					anchor : '100%'
				}]
			}, {
				columnWidth : .34,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '购买结束时间',
					name : 'plManageMoneyPlan.buyEndTime',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					xtype : 'datetimefield',
					format : 'Y-m-d H:i:s',
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
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '计划金额',
					name : 'plManageMoneyPlan.sumMoney',
					xtype : 'moneyfield',
					fieldClass : 'field-align',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					anchor : '100%',
					listeners : {
						scope : this,
						'change' : function(nf) {
							var currMoney = nf.getValue();
							var balanceMoney=this.getCmpByName('balanceMoney1').hiddenField.value;
							if(balanceMoney){
								if(Number(currMoney)>Number(balanceMoney)){
									Ext.ux.Toast.msg('操作信息',"计划金额不能超出债权剩余金额!");
									nf.setValue();
								}else{
								    this.getCmpByName('limitMoney1').setValue(Ext.util.Format.number(currMoney, '0,000.00'));
								    this.getCmpByName('limitMoney1').hiddenField.value=currMoney;
								}
							}else{
								Ext.ux.Toast.msg('操作信息',"请先选择债权类型!");
								nf.setValue();
							}
						}
					}
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '计划编号',
					name : 'plManageMoneyPlan.mmNumber',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					xtype : 'textfield',
					anchor : '100%',	
					listeners : {
						scope:this,
						'blur' : function(f) {
							var manageMoneyTypeId=this.getCmpByName("plManageMoneyPlan.manageMoneyTypeId").getValue();
							if(manageMoneyTypeId){
								var mmNumber = f.getValue();
								Ext.Ajax.request({
				                   	url:  __ctxPath + '/creditFlow/financingAgency/CheckNumDoublePlManageMoneyPlan.do?manageMoneyTypeId='+manageMoneyTypeId+"&mmNumber="+mmNumber,
				                  	method : 'POST',
				                   	success : function(response,request) {
										var obj=Ext.util.JSON.decode(response.responseText);
	                            		if(obj.flag=="1"){					                            			
	                            			Ext.ux.Toast.msg('操作信息',"该类型的编号已经重复，请重新输入");
	                            			f.setValue("");
	                            		}
			                      	}
	                            });
							}else{
								Ext.ux.Toast.msg('操作信息',"请先选择债权类型，再添加计划编号");
	                            f.setValue("");
							}
							
						}
					}
				}]
			},{
				columnWidth : .32,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '投资期限',
					name : 'plManageMoneyPlan.investlimit',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					fieldClass : 'field-align',
					xtype : 'numberfield',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>月</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					xtype : 'combo',
					fieldLabel : '收益方式',
					triggerAction : "all",
					width : 70,
					mode : 'local',
					anchor : '100%',
					editable : false,
					valueField : 'id',
					displayField : 'name',
					readOnly : this.isAllReadOnly,
					allowBlank:false,
					hiddenName : "plManageMoneyPlan.isOne",
					store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["一次性支付利息", "1"],["按期付息,到期还本", "0"]]
					}),
					listeners : {
						afterrender : function(combox) {
							var st = combox.getStore();
							combox.setValue(combox.getValue());
						}

					}
				}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '预期年化收益率',
					xtype : 'numberfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.yeaRate',
					readOnly : this.isAllReadOnly,
					fieldClass : 'field-align',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .32,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '锁定期限',
					name : 'plManageMoneyPlan.lockingLimit',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					fieldClass : 'field-align',
					xtype : 'numberfield',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>月</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '起投金额',
					name : 'plManageMoneyPlan.startMoney',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					fieldClass : 'field-align',
					xtype : 'moneyfield',
					anchor : '100%',
					listeners : {
						scope:this,
						'blur' : function(f){
							if(Number(this.getCmpByName('sumMoney1').hiddenField.value)<Number(this.getCmpByName('startMoney1').hiddenField.value)){
								Ext.Msg.alert('温馨提示：','<font color=red>投资起点金额 不能大于计划金额</font>');
								f.setValue();
							}
						}
					}
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
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '递增金额',
					fieldClass : 'field-align',
					xtype : 'moneyfield',
					name : 'plManageMoneyPlan.riseMoney',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
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
				columnWidth : .32,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '单笔投资上限',
					xtype : 'moneyfield',
					name : 'plManageMoneyPlan.limitMoney',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					fieldClass : 'field-align',
					anchor : '100%',
					listeners : {
						scope:this,
						'blur' : function(f){
							if(Number(this.getCmpByName('limitMoney1').hiddenField.value)<Number(this.getCmpByName('startMoney1').hiddenField.value)){
								Ext.Msg.alert('温馨提示：','<font color=red>单笔投资上限不能小于投资起点金额 </font>');
								f.setValue();
							}
							else if(Number(this.getCmpByName('sumMoney1').hiddenField.value)<Number(this.getCmpByName('limitMoney1').hiddenField.value)){
								Ext.Msg.alert('温馨提示：','<font color=red>单笔投资上限不能大于计划金额 </font>');
								f.setValue();
							}
						}
					}
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
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '加入费率',
					xtype : 'numberfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.joinRate',
					readOnly : this.isAllReadOnly,
					fieldClass : 'field-align',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .33,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '起息模式',
					xtype : 'combo',
					mode : 'local',
					width : 70,
					anchor : '100%',
					valueField : 'id',
					editable : false,
					allowBlank : false,
					displayField : 'name',
					triggerAction : "all",
					readOnly : this.isAllReadOnly,
					hiddenName : "plManageMoneyPlan.startinInterestCondition",
					store : new Ext.data.SimpleStore({
						fields : ["name", "id"],
						data : [["投标日起息", "1"],["投标日+1起息", "2"],["满标日起息", "3"],["招标截止日起息", "4"]]
					}),
					listeners : {
						scope : this,
						afterrender : function(combox) {
							var st = combox.getStore();
							combox.setValue(combox.getValue());
							if(!this.isAllReadOnly){
								var flag=combox.getValue();
								var a=this.getCmpByName('plManageMoneyPlan.raiseRate');
								if(flag=='1'){
									a.setValue();
									a.setReadOnly(true);
								}else if(flag=='2'){
									a.setValue();
									a.setReadOnly(true);
								}else if(flag=='3'){
									a.setReadOnly(false);
								}else if(flag=='4'){
									a.setReadOnly(false);
								}
							}
						},
						select:function(combox){
							if(!this.isAllReadOnly){
								var  flag=combox.getValue();
								var a=this.getCmpByName('plManageMoneyPlan.raiseRate');
								if(flag=='1'){
									a.setValue();
									a.setReadOnly(true);
									if(a.getEl().dom.className.indexOf("readOnlyClass")==-1){
										a.getEl().dom.className+=" readOnlyClass";
									}
								}else if(flag=='2'){
									a.setValue();
									a.setReadOnly(true);
									if(a.getEl().dom.className.indexOf("readOnlyClass")==-1){
										a.getEl().dom.className+=" readOnlyClass";
									}
								}else if(flag=='3'){
									a.setReadOnly(false);
									a.getEl().dom.className=a.getEl().dom.className.replace("readOnlyClass","");
								}else if(flag=='4'){
									a.setReadOnly(false);
									a.getEl().dom.className=a.getEl().dom.className.replace("readOnlyClass","");
								}
							}
						}
					}
				}]
			}, {
				columnWidth : .32,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '退出费率',
					xtype : 'numberfield',
					allowBlank : false,
					name : 'plManageMoneyPlan.earlierOutRate',
					readOnly : this.isAllReadOnly,
					fieldClass : 'field-align',
					anchor : '100%'
				}]
			}, {
				columnWidth : .02, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 18,
				items : [{
					fieldLabel : "<span style='margin-left:1px'>%</span> ",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : 1,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '保息保障计划',
					xtype : 'textfield',
					name : 'plManageMoneyPlan.guaranteeWay',
					readOnly : this.isAllReadOnly,
					allowBlank : false,
					anchor : '100%',
					value : this.guaranteeWay
				}]
			},{
				columnWidth : 1,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '投资范围',
					name : 'plManageMoneyPlan.investScope',
					readOnly : this.isAllReadOnly,
					xtype : 'textfield',
					anchor : '100%',
					value : this.investScope
				}]
			},{
				columnWidth : 1,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '到期退出方式',
					xtype : 'textfield',
					name : 'plManageMoneyPlan.expireRedemptionWay',
					allowBlank : false,
					readOnly : this.isAllReadOnly,
					anchor : '100%',
					value : this.expireRedemptionWay
				}]
			},{
				columnWidth : 1,
				labelWidth : leffwidth,
				layout : 'form',
				items : [ {
					xtype : 'button',
					text:'Logo上传',
					fieldLabel : 'Logo上传',
					value:'上传',
					anchor : "100%",
					width:1,
					scope : this,
					handler : this.upload
				}]
			},{
				columnWidth : .33,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '计息起日',
					name : 'plManageMoneyPlan.startinInterestTime',
					xtype : 'datefield',
					hidden : !this.isAllReadOnly,
					hideLabel : !this.isAllReadOnly,
					allowBlank:this.isAllReadOnly?false:true,
					format : 'Y-m-d H:i:s',
					anchor : '100%',
					readOnly:this.jxqrReadOnly==true?true:this.seeHidden,//用来查看理财计划使用的限制
					listeners : {
						scope : this,
						'change' : function(nf){
							var month=this.getCmpByName('plManageMoneyPlan.investlimit').getValue();
							setIntentDate('monthPay','',month, nf.getValue(),this.getCmpByName("plManageMoneyPlan.endinInterestTime"),this)
							/*
							var dtstr = Ext.util.Format.date(nf.getValue(), 'Y-m-d');
							var month=this.getCmpByName('plManageMoneyPlan.investlimit').getValue();
							var n=parseInt(month);
							var s=dtstr.split("-");
						    var yy=parseInt(s[0]);
						    var mm=parseInt(s[1])-1;
						    var dd=parseInt(s[2]);
						    var dt=new Date(yy,mm,dd);
						    dt.setMonth(dt.getMonth()+n);
						    if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) ){
						   		dt=new Date(dt.getYear(),dt.getMonth(),0);
						    }
					        this.getCmpByName("plManageMoneyPlan.endinInterestTime").setValue(dt);
					        var month1=this.getCmpByName("plManageMoneyPlan.lockingLimit").getValue();
					        var n=parseInt(month1);
					        var s=dtstr.split("-");
						    var yy=parseInt(s[0]);
						    var mm=parseInt(s[1])-1;
						    var dd=parseInt(s[2]);
						    var dt=new Date(yy,mm,dd);
						    dt.setMonth(dt.getMonth()+n);
						    if( (dt.getYear()*12+dt.getMonth()) > (yy*12+mm + n) ){
						    	dt=new Date(dt.getYear(),dt.getMonth(),0);
						    }
					        this.getCmpByName("plManageMoneyPlan.lockingEndDate").setValue(dt);
						*/}
					}
				}]
			}, {
				columnWidth : .67,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '计息止日',
					name : 'plManageMoneyPlan.endinInterestTime',
					hidden : !this.isAllReadOnly,
					hideLabel : !this.isAllReadOnly,
					allowBlank:this.isAllReadOnly?false:true,
					xtype : 'datefield',
				    format : 'Y-m-d H:i:s',
					readOnly:true,
					anchor : '50%'
				}]
			},{
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '可用优惠券',
					name : 'plBidPlan.coupon',
					id : 'check_coupon',
					xtype : 'checkbox',
					boxLabel : '是',
					allowBlank : true,
					readOnly : this.readOnly,
					value :0,
					anchor : '100%',
					listeners : {
						scope : this,
						check : function(obj, checked) {
							if(!checked){
								Ext.getCmp("plBidPlan.coupon").setValue("0");
								Ext.getCmp("maxMoney_text").setValue("");
								Ext.getCmp("ratio_text").setValue("");
								Ext.getCmp('plBidPlan.rebateType').setValue("");
								Ext.getCmp('plBidPlan.rebateWay').setValue("");
								Ext.getCmp("hide_a").hide();
								Ext.getCmp("hide_b").hide();
								Ext.getCmp("hide_f").hide();
								Ext.getCmp("hide_g").hide();
								Ext.getCmp("hide_h").hide();
								Ext.getCmp("check_f").setValue(false);
								Ext.getCmp("check_g").setValue(false);
								Ext.getCmp("check_h").setValue(false);
								Ext.getCmp("check_e").setValue(false);
								Ext.getCmp("check_ff").setValue(false);
								Ext.getCmp("check_ee").setValue(false);
								Ext.getCmp("check_ii").setValue(false);
								
							}else{
								Ext.getCmp("plBidPlan.coupon").setValue("1");
								Ext.getCmp("hide_a").show();
								Ext.getCmp("hide_b").show();
								Ext.getCmp("hide_f").show();
								Ext.getCmp("hide_g").show();
								Ext.getCmp("hide_h").show();
								Ext.getCmp("check_addRate").setValue(false);
								Ext.getCmp("addRate_text").setValue("0");
								Ext.getCmp("hide_addRate_1").hide();
								Ext.getCmp("hide_addRate_2").hide();
								Ext.getCmp('plBidPlan.rebateType').setValue("");
								Ext.getCmp('plBidPlan.rebateWay').setValue("");
							}
						}
					}
				}]
			}, {
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '普通加息',
					name : 'checkaddRate',
					id : 'check_addRate',
					xtype : 'checkbox',
					boxLabel : '是',
					allowBlank : true,
					readOnly : this.readOnly,
					value :0,
					anchor : '100%',
					listeners : {
						scope : this,
						check : function(obj, checked) {
							if(!checked){
								Ext.getCmp("hide_addRate_1").hide();
								Ext.getCmp("hide_addRate_2").hide();
								Ext.getCmp("addRate_text").setValue("");
								Ext.getCmp('plBidPlan.rebateType').setValue("");
								Ext.getCmp('plBidPlan.rebateWay').setValue("");
							}else{
								Ext.getCmp("plBidPlan.coupon").setValue("0");
								Ext.getCmp("hide_f").hide();
								Ext.getCmp("hide_g").hide();
								Ext.getCmp("hide_h").hide();
								Ext.getCmp("check_f").setValue(false);
								Ext.getCmp("check_g").setValue(false);
								Ext.getCmp("check_h").setValue(false);
								Ext.getCmp("check_e").setValue(false);
								Ext.getCmp("check_ff").setValue(false);
								Ext.getCmp("check_ee").setValue(false);
								Ext.getCmp("check_ii").setValue(false);
								Ext.getCmp("check_coupon").setValue(false);
								Ext.getCmp("maxMoney_text").setValue("");
								Ext.getCmp("ratio_text").setValue("");
								Ext.getCmp("ratio_text").removeClass("readOnlyClass");
								Ext.getCmp("ratio_text").setReadOnly(false);
								Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
								Ext.getCmp("maxMoney_text").setReadOnly(false);
								Ext.getCmp("hide_addRate_1").show();
								Ext.getCmp("hide_addRate_2").show();
								//(系统默认)rebateType=0普通加息
								//(可选)rebateWay=1,立还。rebateWay=2随期。rebateWay=3到期
								Ext.getCmp('plBidPlan.rebateType').setValue("0");
								Ext.getCmp('plBidPlan.rebateWay').setValue("2");
							}
						}
					}
				}]
			},{
				columnWidth : .22,
				layout : 'form',
				labelWidth : leffwidth,
				items : [{
					fieldLabel : '是否新手专享',
					name : 'plBidPlan.novice',
					xtype : 'checkbox',
					boxLabel : '是',
					allowBlank : true,
					id : 'check_novice',
					value :0,
					anchor : '100%',
					handler : function(ck, checked) {
						if(!checked){
							Ext.getCmp("plBidPlan.novice").setValue("");
						}else{
							Ext.getCmp("plBidPlan.novice").setValue("1");
						}
					}
				}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				layout : 'form',
				items : [{
					fieldLabel : '募集期利率',
					xtype : 'numberfield',
					name : 'plManageMoneyPlan.raiseRate',
					readOnly : this.isAllReadOnly,
					fieldClass : 'field-align',
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
				columnWidth : .31, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : leffwidth,
				id : 'hide_addRate_1',
				hidden: true,
				allowBlank : false,
				labelAlign : 'right',
				items : [{
					fieldLabel : '<font color="red">*</font>普通加息利率',
					name : 'plManageMoneyPlan.addRate',
					id : 'addRate_text',
					xtype : 'numberfield',
					anchor : '100%'
				}]
			}, {
				columnWidth : .69, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 310,
				id : 'hide_addRate_2',
				hidden: true,
				labelAlign : 'right',
				items : [{
					fieldLabel : "%/年 (普通用户加息，随期奖励，不需要使用优惠券)",
					labelSeparator : '',
					anchor : "100%"
				}]
			}, {
				columnWidth : .31,
				labelWidth : leffwidth,
				id : 'hide_a',
				hidden: true,
				layout : 'form',
				items : [{
					fieldLabel : '<font color="red">*</font>面值折现比',
					name : 'plManageMoneyPlan.returnRatio',
					id :'ratio_text',
					xtype : 'numberfield',
					anchor : '100%'
				},{
					fieldLabel : '<font color="red">*</font>单笔最大面值',
					name : 'plManageMoneyPlan.maxCouponMoney',
					id : 'maxMoney_text',
					xtype : 'numberfield',
					anchor : '100%'
				},{
					fieldLabel : '返利类型',
					name : 'check_e',
					xtype : 'checkbox',
					boxLabel : '返现',
					id : 'check_e',
					allowBlank : false,
					readOnly : this.readOnly,
					anchor : '100%',
					listeners : {
						"check" : function(el, checked) {
						 	if (checked) {
						 	 	Ext.getCmp('plBidPlan.rebateType').setValue("1");
								Ext.getCmp("check_f").setValue(false);
								Ext.getCmp("check_g").setValue(false);
								Ext.getCmp("check_h").setValue(false);
								Ext.getCmp("hide_a").show();
								Ext.getCmp("hide_b").show();
								Ext.getCmp("ratio_text").removeClass("readOnlyClass");
								Ext.getCmp("ratio_text").setReadOnly(false);
								Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
								Ext.getCmp("maxMoney_text").setReadOnly(false);
						    }
						}
					}
				},{
					fieldLabel : '返利方式',
					name : 'check_ee',
					xtype : 'checkbox',
					id : 'check_ee',
					boxLabel : '立返',
					allowBlank : false,
					readOnly : this.readOnly,
					anchor : '100%',
					listeners : {
						"check" : function(el, checked) {
						 	if (checked) {
						 		Ext.getCmp('plBidPlan.rebateWay').setValue("1");
								Ext.getCmp("check_ff").setValue(false);
								Ext.getCmp("check_ii").setValue(false);
								Ext.getCmp("hide_a").show();
								Ext.getCmp("hide_b").show();
						    }
						}
					}
				}]
			}, {
				columnWidth : .69,
				labelWidth : 385,
				id : 'hide_b',
				hidden: true,
				style:'padding-top:4px',
				layout : 'form',
				items : [{
					fieldLabel : "% (例如：如果优惠券面值为1000元折现比例10%则有效面值为100元)",
					labelSeparator : '',
					anchor : "100%"
				},{
					fieldLabel : "元 (例如：单笔最大面值1000元此标不能使用1000元以上面值的优惠券)",
					labelSeparator : '',
					anchor : "100%"
				}]
			},{
				columnWidth : .2, // 该列在整行中所占的百分比
				layout : "form", // 从上往下的布局
				labelWidth : 55,
				id : 'hide_f',
				hidden: true,
				labelAlign : 'right',
				items : [{
					fieldLabel : '',
					name : 'check_f',
					xtype : 'checkbox',
					id : 'check_f',
					boxLabel : '返息',
					anchor : '100%',
					listeners : {
						"check" : function(el, checked) {
							 if (checked) {
						 	 	Ext.getCmp('plBidPlan.rebateType').setValue("2");
								Ext.getCmp("check_e").setValue(false);
								Ext.getCmp("check_g").setValue(false);
								Ext.getCmp("check_h").setValue(false);
								Ext.getCmp("hide_a").show();
								Ext.getCmp("hide_b").show();
								
								Ext.getCmp("ratio_text").removeClass("readOnlyClass");
								Ext.getCmp("ratio_text").setReadOnly(false);
								Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
								Ext.getCmp("maxMoney_text").setReadOnly(false);
						    }
						}
					}
				},{
					fieldLabel : '',
					name : 'check_ff',
					xtype : 'checkbox',
					id : 'check_ff',
					boxLabel : '随期',
					anchor : '100%',
					listeners : {
						"check" : function(el, checked) {
						 	if (checked) {
						 		Ext.getCmp('plBidPlan.rebateWay').setValue("2");
								Ext.getCmp("check_ee").setValue(false);
								Ext.getCmp("check_ii").setValue(false);
								Ext.getCmp("hide_a").show();
								Ext.getCmp("hide_b").show();
						    }
						}
					}
				}]
			},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_g',
						hidden: true,
						labelAlign : 'right',
						items : [{
							fieldLabel : '',
							name : 'check_g',
							xtype : 'checkbox',
							id : 'check_g',
							boxLabel : '返息现',
							anchor : '100%',
							listeners : {
								"check" : function(el, checked) {
								 	if (checked) {
								 	 	Ext.getCmp('plBidPlan.rebateType').setValue("3");
										Ext.getCmp("check_e").setValue(false);
										Ext.getCmp("check_f").setValue(false);
										Ext.getCmp("check_h").setValue(false);
										Ext.getCmp("hide_a").show();
										Ext.getCmp("hide_b").show();
										
										Ext.getCmp("ratio_text").removeClass("readOnlyClass");
										Ext.getCmp("ratio_text").setReadOnly(false);
										Ext.getCmp("maxMoney_text").removeClass("readOnlyClass");
										Ext.getCmp("maxMoney_text").setReadOnly(false);
								    }
								}
							}
						},{
							fieldLabel : '',
							name : 'check_ii',
							xtype : 'checkbox',
							id : 'check_ii',
							boxLabel : '到期',
							anchor : '100%',
							listeners : {
								"check" : function(el, checked) {
								 	if (checked) {
								 		Ext.getCmp('plBidPlan.rebateWay').setValue("3");
										Ext.getCmp("check_ee").setValue(false);
										Ext.getCmp("check_ff").setValue(false);
										Ext.getCmp("hide_a").show();
										Ext.getCmp("hide_b").show();
								    }
								}
							}
						}]
					},{
						columnWidth : .2, // 该列在整行中所占的百分比
						layout : "form", // 从上往下的布局
						labelWidth : 55,
						id : 'hide_h',
						hidden: true,
						labelAlign : 'right',
						items : [{
							fieldLabel : '',
							name : 'check_h',
							xtype : 'checkbox',
							boxLabel : '加息',
							id : 'check_h',
							anchor : '100%',
							listeners : {
								"check" : function(el, checked) {
								 	if (checked) {
								 	    Ext.getCmp('plBidPlan.rebateType').setValue("4");
								 	    Ext.getCmp("maxMoney_text").setValue("");
								 	    Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
								 	    Ext.getCmp("maxMoney_text").setReadOnly(true);
								 	          
										Ext.getCmp("ratio_text").setValue("");
										Ext.getCmp("ratio_text").addClass("readOnlyClass");
										Ext.getCmp("ratio_text").setReadOnly(true);
										
										Ext.getCmp("check_e").setValue(false);
										Ext.getCmp("check_f").setValue(false);
										Ext.getCmp("check_g").setValue(false);
								    }
								}
							}
						}]
					}, {
						columnWidth : .34,
						layout : 'form',
						labelWidth : leffwidth,
						hidden :true,
						items : [{
							fieldLabel : '锁定截至日期',
						    name : 'plManageMoneyPlan.lockingEndDate',
				            id:'plManageMoneyPlan.lockingEndDate',
							xtype : 'datefield',
							format : 'Y-m-d H:i:s',
							readOnly:true,
							anchor : '100%'
						}]
				}, {
					columnWidth : 1,
					labelWidth : leffwidth,
					layout : 'form',
					items : [this.isAllReadOnly==true?{
							fieldLabel : '招标说明',
							name : 'plManageMoneyPlan.bidRemark1',
							readOnly : this.isAllReadOnly,
							xtype : 'label',
							anchor : '100%'
							}:{
							fieldLabel : '招标说明',
							name : 'plManageMoneyPlan.bidRemark',
							xtype : 'fckeditor',
							anchor : '100%'
						},{
							xtype:"hidden",
							id : 'plBidPlan.rebateType',
						    name : 'plManageMoneyPlan.rebateType'
						},{
							xtype:"hidden",
							id : 'plBidPlan.rebateWay',
						    name : 'plManageMoneyPlan.rebateWay'
						},{
							xtype:"hidden",
							id : 'plBidPlan.coupon',
						    name : 'plManageMoneyPlan.coupon'
						},{
							xtype:"hidden",
							id : 'plBidPlan.novice',
						    name : 'plManageMoneyPlan.novice'
						}]
			}, { 
				columnWidth : 1,
				hidden : this.seeHidden,
				labelWidth : leffwidth,
				layout : 'form',
				items : [this.UPlanOrder,this.PlManageMoneyPlanCoupons]
			}]
		});

		// 加载表单对应的数据
		if (this.mmplanId != null && this.mmplanId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath+ '/creditFlow/financingAgency/getPlManageMoneyPlan.do?mmplanId='+ this.mmplanId,
				root : 'data',
				scope:this,
				success : function(response, options) {
					 var respText = response.responseText;  
				     var result = Ext.util.JSON.decode(respText); 
				     //是否可用优惠券
					 if(result.data.plManageMoneyPlan.coupon==1){
					 	Ext.getCmp("check_coupon").setValue(true);
				 		if(result.data.plManageMoneyPlan.state!=0){
						 	Ext.getCmp("check_coupon").setDisabled(true);
				 		}
					 }
				     //是否新手专享
				     if(result.data.plManageMoneyPlan.novice==1){
					 	Ext.getCmp("check_novice").setValue(true);
				 		if(result.data.plManageMoneyPlan.state!=0){
						 	Ext.getCmp("check_novice").setDisabled(true);
				 		}
					 }
					 //普通加息
					 if(result.data.plManageMoneyPlan.addRate>0){
						 Ext.getCmp("check_addRate").setValue(true);
				 		 if(result.data.plManageMoneyPlan.state!=0){
						 	Ext.getCmp("check_addRate").setDisabled(true);
						 	Ext.getCmp("addRate_text").addClass("readOnlyClass");
						 	Ext.getCmp("addRate_text").setReadOnly(true);
				 		 }
					 }else{
					 	//返利类型
						 if(result.data.plManageMoneyPlan.rebateType==1){
						  	Ext.getCmp("check_e").setValue(true);
						 }else if(result.data.plManageMoneyPlan.rebateType==2){
						  	Ext.getCmp("check_f").setValue(true);
						 }else if(result.data.plManageMoneyPlan.rebateType==3){
						  	Ext.getCmp("check_g").setValue(true);
						 }else if(result.data.plManageMoneyPlan.rebateType==4){
						  	Ext.getCmp("check_h").setValue(true);
						 }
						 //返利方式
						 if(result.data.plManageMoneyPlan.rebateWay==1){
						 	Ext.getCmp("check_ee").setValue(true);
						 
						 }else if(result.data.plManageMoneyPlan.rebateWay==2){
						 	Ext.getCmp("check_ff").setValue(true);
						 	
						 }else if(result.data.plManageMoneyPlan.rebateWay==3){
						 	Ext.getCmp("check_ii").setValue(true);
						 	
						 }
						 
				     }
				     //除了state=0以外的其他状态，不可编辑
				 	 if(result.data.plManageMoneyPlan.state!=0){
						 Ext.getCmp("check_e").setDisabled(true);
						 Ext.getCmp("check_f").setDisabled(true);
						 Ext.getCmp("check_g").setDisabled(true);
						 Ext.getCmp("check_h").setDisabled(true);
						 Ext.getCmp("check_ii").setDisabled(true);
						 Ext.getCmp("check_ff").setDisabled(true);
						 Ext.getCmp("check_ee").setDisabled(true);
						 Ext.getCmp("check_coupon").setDisabled(true);
						 Ext.getCmp("check_novice").setDisabled(true);
						 Ext.getCmp("check_addRate").setDisabled(true);
						 Ext.getCmp("maxMoney_text").addClass("readOnlyClass");
						 Ext.getCmp("maxMoney_text").setReadOnly(true);
						 Ext.getCmp("ratio_text").addClass("readOnlyClass");
						 Ext.getCmp("ratio_text").setReadOnly(true);
				 	 }
					if(this.isAllReadOnly==true){
						this.getCmpByName('plManageMoneyPlan.bidRemark1').getEl().dom.innerHTML="<p style='padding-left:20px;padding-top:3px'></p>"+result.data.plManageMoneyPlan.bidRemark;
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
	start : function() {
		var fpl = this.formPanel;
		var flag=true;
		var order=this.UPlanOrder.gridPanel.store.data;
		var date=null;
		if(order){
			for(var i=0;i<order.length;i++){
				date=order.items[0].data;
				if(date.couponsMoney){//先判断有无使用优惠券   有使用优惠券
					var couponsOrder=this.PlManageMoneyPlanCoupons.gridPanel.store.data;
					if(couponsOrder.length<1){
						flag=false;
						break;
					}
				}
			}
		}
		if(flag){
			$postForm({
				formPanel : this.formPanel,
				scope : this,
				url : __ctxPath
						+ '/creditFlow/financingAgency/startUPlanPlManageMoneyPlan.do?keystr=UPlan',
				callback : function(fp, action) {
					this.close();
					this.gp.getStore().reload();
				}
			});
		}else{
			Ext.Msg.alert('操作信息','<font color=red>请先生成投资人奖励明细台账！<font>')
		}
	},// end of save
	
	
	//保存
    submit:function(){
    	var coupon = this.getCmpByName('plManageMoneyPlan.coupon').getValue();
		var checkaddRate = this.getCmpByName('checkaddRate').getValue();
		if(coupon==true){
			var rebateType=this.getCmpByName('plManageMoneyPlan.rebateType').getValue();
			if(rebateType==""){
				Ext.Msg.alert('操作信息','请选择返利类型');
				return;
			}
			if(rebateType!=4){
				var returnRatio = this.getCmpByName('plManageMoneyPlan.returnRatio').getValue();
				var maxCouponMoney = this.getCmpByName('plManageMoneyPlan.maxCouponMoney').getValue();
				if(returnRatio==""){
					Ext.Msg.alert('操作信息','面值折现例不能为空');
					return;
				}
				if(maxCouponMoney==""){
					Ext.Msg.alert('操作信息','单笔最大面值不能为空');
					return;
				}
			}
			var rebateWay=this.getCmpByName('plManageMoneyPlan.rebateWay').getValue();
			if(rebateWay==""){
				Ext.Msg.alert('操作信息','请选择返利方式');
				return;
			}
		}
		if(checkaddRate==true){
			var addRate = this.getCmpByName('plManageMoneyPlan.addRate').getValue();
			if(addRate==""){
				Ext.Msg.alert('操作信息','请填写普通加息利率');
				return;
			}
		}
		this.runner.start(this.task);
		var planMoney=this.getCmpByName('sumMoney1');
		var balanceMoney=this.getCmpByName('balanceMoney1');
		
		if(Number(planMoney.hiddenField.value)>balanceMoney.value){
			Ext.ux.Toast.msg('操作信息', '计划金额不能超出剩余债权金额');
			return;
		}
    	var keystr=this.keystr
	    if (this.formPanel.getForm().isValid()) {
    		this.formPanel.getForm().submit({
			    clientValidation: false, 
				url : __ctxPath + '/creditFlow/financingAgency/savePlManageMoneyPlan.do',
				params : {
					'keystr':keystr
				},
				method : 'post',
				waitMsg : '数据正在提交，请稍后...',
				scope: this,
				success : function(fp, action) {
				
					var obj = Ext.util.JSON.decode(action.response.responseText)
						Ext.getCmp('plManageMoneyPlan.mmplanId').setValue(obj.id)
				        Ext.getCmp('plManageMoneyPlan.mmplanId').getValue();
				        var message=obj.msg
				        Ext.ux.Toast.msg('操作信息', message);
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
	    }
	},
    upload : function() {
    	var mmplanId=Ext.getCmp('plManageMoneyPlan.mmplanId').getValue();
    	if(mmplanId !=null && mmplanId !='' && mmplanId !='undefined'){
    		new SlSubjectLogoUpload({
	    	    tablename:'pl_managemoney_plan',
	    	    proIdupload:mmplanId
			}).show();
    	}else{
    		Ext.Msg.alert('操作信息','请先保存基本信息后再上传Logo！');
    	}
    }
});

var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel){
		Ext.Ajax.request({
			url : __ctxPath + "/project/getIntentDateSlSmallloanProject.do",
			method : 'POST',
			scope:this,
			params : {
				payAccrualType:payAccrualType,
				dayOfEveryPeriod:dayOfEveryPeriod,
				payintentPeriod:payintentPeriod,
				startDate:startDate
			},
			success : function(response, request) {
				obj = Ext.util.JSON.decode(response.responseText);
				intentDatePanel.setValue(obj.intentDate);
			},
			failure : function(response,request) {
				Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
			}
		});
}