/**
 * @author
 * @createtime
 * @class BpActivityManageForm
 * @extends Ext.Window
 * @description BpActivityManage表单
 * @company 智维软件
 */
BpActivityManageForm1 = Ext.extend(Ext.Window, {
	// 构造函数
	activityLabel : '',
	label1 : '',
	label2 : '',
	flag:'',
	constructor : function(_cfg) {
		if (_cfg.type) {
			this.type = _cfg.type;
		}
		if (_cfg.type == 'integral') {
			this.activityLabel = '活动派送积分配置';
			this.label1 = '积分信息';
			this.label2 = '奖励分值';
			this.flag = '0';
		} else if (_cfg.type == 'redPacket') {
			this.activityLabel = '活动派送红包配置';
			this.label1 = '红包信息';
			this.label2 = '面值';
			this.flag = '1';
		} else if (_cfg.type == 'coupon') {
			this.activityLabel = '活动派送优惠券配置';
			this.label1 = '优惠券信息';
			this.label2 = '面值';
			this.flag = '2';
		}else if(_cfg.type == 'integralExchange'){
			this.activityLabel = '积分兑换';
			this.label2 = '面值';
			this.flag='3';
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BpActivityManageForm1.superclass.constructor.call(this, {
					id : 'BpActivityManageFormWin1_' + this.type,
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					closable:true,
					height :this.type=='integralExchange'?300:410,
					width : 600,
					maximizable : true,
					title : this.activityLabel,
					buttonAlign : 'center',
					buttons : [{
						text : '提交',
						iconCls : 'btn-save',
						scope : this,
						hidden : this.readOnly,
						handler : this.save
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
		
		Ext.apply(Ext.form.VTypes, {  
	        dateRange: function(val, field){
	            if(field.dateRange){  
	                var beginId = field.dateRange.begin;  
	                this.beginField = Ext.getCmp(beginId);  
	                var endId = field.dateRange.end;  
	                this.endField = Ext.getCmp(endId);  
	                var beginDate = this.beginField.getValue();  
	                var endDate = this.endField.getValue(); 
	                if(!Ext.isEmpty(beginDate)&&!Ext.isEmpty(endDate)){
	                  	if(beginDate.getTime()>endDate.getTime()){
	                  		this.beginField.setValue('');
	                   		return false;
	                  	}else{
	                  		return true;
	                  	}
	                }
	                return true;
	            }  
	            
	        },  
	        //验证失败信息  
	        dateRangeText: '活动开始时间不能晚于活动截止时间'  
	    });
    
		var leftlabel = 90;
		var rightlabel = 100;
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			url : __ctxPath + '/activity/saveBpActivityManage.do',
			bodyStyle : 'padding:5px',
			autoScroll : true,
			frame : true,
			labelAlign : 'right',
			defaults : {
				anchor : '96%',
				columnWidth : 1,
				labelWidth : 60
			},
			items : [{
				layout : "column",
				border : false,
				scope : this,
				defaults : {
					anchor : '100%',
					columnWidth : 1,
					isFormField : true,
					labelWidth : leftlabel
				},
				items : [/*{
					xtype:'label',
					style : 'padding:5px 10px 15px;',
					hidden:this.type=='integralExchange'?true:false,
					html:'<font style="padding-left:10px;color:red">提示：同一活动发送类型在同一活动起止期间内不能有双重规则</font>'
				},*/{
					xtype : 'hidden',
					name : 'bpActivityManage.flag',
					value:this.flag
				},{
					xtype : 'hidden',
					name : 'bpActivityManage.activityNumber',
					listeners:{
						scope : this,
						'afterrender':function(v){
							Ext.Ajax.request({
								url : __ctxPath+ '/activity/findActivityNumberBpActivityManage.do',
								method : 'post',
								success : function(response,request) {
									var obj= Ext.decode(response.responseText);
									v.setValue(obj.data);
								},
								failure : function(response) {
								},
								params : {
									flag : this.flag
								}
							});
						}
					}
				},{
					columnWidth : 0.5,
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					hidden:this.type=='integralExchange'?true:false,
					labelAlign :'right',
					items:[{
//								xtype : "dickeycombo",
//								disabled:this.type=='integralExchange'?true:false,
//								nodeKey : 'sendType_key',
//								hiddenName : "bpActivityManage.sendType",
//								fieldLabel : '活动发送类型',
//								anchor : '100%',
//								editable : false,
//								allowBlank : false,
//								listeners : {
//									scope : this,
//									afterrender : function(combox) {
//										var st = combox.getStore();
//										st.on("load", function() {
//											combox.setValue(combox.getValue());
//											combox.clearInvalid();
//										})
//									}
//								}
							
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								valueField : 'id',
								editable : false,
								triggerAction : 'all',
								allowBlank : false,
								readOnly : this.readOnly,
								anchor : '100%',
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [

											/*["注册", "1"],*/
											["充值", "4"],
											["投标", "3"],
											["首投", "8"],
											["邀请", "2"]
											/*["累计充值", "7"],
											["累计投资", "6"],
											["累计推荐投资", "9"],
											["邀请好友第一次投标", "5"]*/
									]
								}),
								hiddenName : 'bpActivityManage.sendType',
								fieldLabel : '活动发送类型'
						
						
						
					},{
						fieldLabel : '活动开始日期',
						name : 'bpActivityManage.activityStartDate',
						id : 'bpActivityManage.activityStartDate',
						disabled:this.type=='integralExchange'?true:false,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						anchor : '100%',
						readOnly : this.readOnly,
						allowBlank : false,
						editable : false,
						dateRange: {begin: 'bpActivityManage.activityStartDate', end: 'bpActivityManage.activityEndDate' },  
						vtype: 'dateRange'
					} ]
				},{
					columnWidth : .5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					labelAlign :'right',
					hidden:this.type=='integralExchange'?true:false,
					items : [{
						xtype : "combo",
						disabled:this.type=='integralExchange'?true:false,
						fieldLabel : '是否开启',
						hiddenName : "bpActivityManage.status",
						anchor : '100%',
						mode : 'local',
						valueField : 'id',
						displayField : 'value',
						editable : false,
						value:0,
						readOnly:true,
						triggerAction : "all",
						store : new Ext.data.SimpleStore({
							fields : ["value","id"],
							data : [["开启","0"], ["关闭","1"]]
						})
					},{
						fieldLabel : '活动截止日期',
						name : 'bpActivityManage.activityEndDate',
						id : 'bpActivityManage.activityEndDate',
						disabled:this.type=='integralExchange'?true:false,
						xtype : 'datefield',
						format : 'Y-m-d H:i:s',
						anchor : '100%',
						editable : false,
						readOnly : this.readOnly,
						allowBlank : false,
						dateRange: {begin: 'bpActivityManage.activityStartDate', end: 'bpActivityManage.activityEndDate' }, 
						vtype: 'dateRange' 
					}]
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					hidden:this.type=='integralExchange'?true:false,
					items : [{
						xtype : 'textarea',
						disabled:this.type=='integralExchange'?true:false,
						fieldLabel : '活动说明',
						allowBlank : false,
						readOnly : this.readOnly,
						name : 'bpActivityManage.activityExplain',
						anchor : '100%',
						maxLength:100
					}]
				},{
					xtype:'label',
					style : 'padding:5px;',
					hidden:this.type=='integralExchange'?true:false,
//					html:'<div width="80%" style="border-bottom:1 double red;height:20px;"/><font style="color:red">'+this.label1+'</font></div>'
					html:'<font style="padding-left:10px;color:red">'+this.label1+'</font>'
				},{
					columnWidth : .5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?false:true,
					items : [{
						xtype : "dickeycombo",
						nodeKey : 'couponType_key',
						hiddenName : "bpActivityManage.couponType",
						disabled:this.type == 'coupon' || this.type=='integralExchange'?false:true,
						fieldLabel : '优惠券类型',
						anchor : '100%',
						editable : false,
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					columnWidth : this.type == 'coupon' || this.type=='integralExchange'?.47:.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					items : [{
						xtype : 'numberfield',
						fieldLabel :this.label2,
						//给客户部署时state
					//	readOnly : this.readOnly,
						//给客户部署时end
						
						//自己平台测试state
						readOnly : this.readOnly,
//						value:1,
						//自己平台测试end
						allowBlank : false,
						name : 'bpActivityManage.parValue',
						anchor : '100%'
					}]
				},{								
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 15,
					style : 'padding-top:10px;',
					items : [{
						fieldLabel : this.type == 'integral'?"分":"元 ",
						labelSeparator : ''
					}]								
				},{
					columnWidth :.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?false:true,
					items : [{
						xtype : 'numberfield',
						fieldLabel :'有效期',
						hidden:this.type == 'coupon' || this.type=='integralExchange'?false:true,
						name : 'bpActivityManage.validNumber',
						anchor : '100%'
					}]
				},{				
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 15,
					style : 'padding-top:10px;',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?false:true,
					items : [{
						fieldLabel : "天",
						labelSeparator : ''
					}]								
				},{
					columnWidth : .47, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : rightlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					hidden:this.type=='integralExchange'?false:true,
					items : [{
						xtype : "combo",
						disabled:this.type=='integralExchange'?false:true,
						fieldLabel : '是否开启',
						hiddenName : "bpActivityManage.status",
						anchor : '100%',
						mode : 'local',
						valueField : 'id',
						displayField : 'value',
						editable : false,
						triggerAction : "all",
						value:0,
						readOnly:true,
						store : new Ext.data.SimpleStore({
							fields : ["value","id"],
							data : [["开启","0"], ["关闭","1"]]
						})
					}]
				},{
					columnWidth :.5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					hidden:this.type=='integralExchange'?false:true,
					items : [{
						xtype : 'numberfield',
						fieldLabel :'需要积分',
						disabled:this.type=='integralExchange'?false:true,
						name : 'bpActivityManage.needIntegral',
						anchor : '100%'
					}]
				},{				
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 15,
					style : 'padding-top:10px;',
					hidden:this.type=='integralExchange'?false:true,
					items : [{
						fieldLabel : "分",
						labelSeparator : ''
					}]								
				},{
					columnWidth : 1, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					hidden:this.type=='integralExchange'?false:true,
					items : [{
						xtype : 'textarea',
						fieldLabel : '活动说明',
						disabled:this.type=='integralExchange'?false:true,
						name : 'bpActivityManage.activityExplain',
						anchor : '100%',
						maxLength:100
					}]
				},{
					xtype:'label',
					style : 'padding:5px;',
//					html:'<div width="80%" style="border-bottom:1 double red;height:20px;"/><font style="color:red">使用规则</font></div>'
					hidden:this.type == 'coupon' || this.type=='integralExchange'?true:false,
					html:'<font style="padding-left:10px;color:red">奖励规则</font>'
				},{
					columnWidth : 1,
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?true:false,
					items:[{
						hiddenName : "bpActivityManage.referenceUnit",
						fieldLabel : '参照单位',
						allowBlank : false,
						readOnly : this.readOnly,
						anchor:'50%',
						editable : false,
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [
									["无条件", "0"],
//									["小于等于条件", "1"],
									["大于等于条件", "2"]
//									["满足条件金额成倍奖励", "3"]
									]
						}),
						listeners : {
							scope : this,
							select:function(combox){
								if("无条件"==combox.getRawValue()){
									this.formPanel.findById('manageMoney').setVisible(false);  
									this.formPanel.findById('yuan').setVisible(false);  
									this.formPanel.findById('managelevelId').setVisible(false);  
								}else{
									this.formPanel.findById('manageMoney').setVisible(true);  
									this.formPanel.findById('yuan').setVisible(true);  
									this.formPanel.findById('managelevelId').setVisible(true);  
								}
							}
						}
					}]
				},{
					columnWidth : .5, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					id:'manageMoney',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?true:false,
					items : [{
						xtype : 'numberfield',
						disabled:this.type == 'coupon' || this.type=='integralExchange'?true:false,
						fieldLabel :'金额',
						readOnly : this.readOnly,
					//	allowBlank : false,
						name : 'bpActivityManage.money',
						anchor : '100%'
					}]
				},{								
					columnWidth : .03, // 该列在整行中所占的百分比
					layout : "form", // 从上往下的布局
					labelWidth : 15,
					id:'yuan',
					style : 'padding-top:10px;',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?true:false,
					items : [{
						fieldLabel : "元 ",
						labelSeparator : ''
					}]								
				},{
					columnWidth : .47,
					layout : "form", // 从上往下的布局
					labelWidth : leftlabel,
					labelAlign :'right',
					style : 'padding-top:10px;',
					id:'managelevelId',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?true:false,
					items:[{
						xtype : "combo",
						disabled:this.type == 'coupon' || this.type=='integralExchange'?true:false,
						fieldLabel : '会员等级',
						readOnly : this.readOnly,
					//	allowBlank : false,
						editable : false,
						anchor : "100%",
						hiddenName : "bpActivityManage.levelId",
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						hidden:this.type == 'coupon'?true:false,
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath+ '/bonusSystem/jsonArrMemberGradeSet.do',
							fields : ['itemId', 'itemName']
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
									combox.clearInvalid();
								})
							}
						}
					}]
				},{
					xtype:'label',
					style : 'padding:5px;',
					hidden:this.type == 'coupon' || this.type=='integralExchange'?false:true,
					html:'<font style="padding-left:10px;color:red">备注:默认无条件,优惠券的有效开始日期为派发日</font>'
				}]
			}]
		});
		
		//加载表单对应的数据	
		if (this.activityId != null && this.activityId != 'undefined') {
			this.formPanel.loadData({
						url : __ctxPath + '/activity/findBpActivityManage.do?activityId='+ this.activityId,
						root : 'data',
						preName : 'bpActivityManage'
					});
		}
		
	},// end of the initcomponents
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
		var type=this.type;
		if(this.formPanel.getForm().isValid()){
			this.formPanel.getForm().submit({
				method : 'POST',
				waitTitle : '连接',
				waitMsg : '消息发送中...',
				success : function(response, request) {
					var obj= Ext.util.JSON.decode(request.response.responseText);
					if(obj.flag){
						var gridPanel = Ext.getCmp('BpActivityManageGrid_'+type);
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						Ext.getCmp('BpActivityManageFormWin1_'+type).close();
					}
					Ext.ux.Toast.msg('操作提示',obj.msg);
				}
			})
		}
	}// end of save
});