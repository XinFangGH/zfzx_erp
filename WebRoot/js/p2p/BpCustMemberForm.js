/**
 * @author 
 * @createtime 
 * @class BpCustMemberForm
 * @extends Ext.Window
 * @description BpCustMember表单
 * @company 智维软件
 */
BpCustMemberForm = Ext.extend(Ext.Window, {
		memObj:null,
		appUser:null,
			//构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				if (_cfg.memObj) {
					this.memObj = _cfg.memObj;
				}
				if (_cfg.appUser) {
					this.appUser = _cfg.appUser;
				}
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				
				BpCustMemberForm.superclass.constructor.call(this, {
					id : 'BpCustMemberFormWin',
					layout : 'fit',
					items : this.tabpanel,
					modal : true,
					height : 450,
					width : 800,
					maximizable : true,
					title : '详细信息'+'【'+this.userName+'】',
					buttonAlign : 'center'
		});
				
				
				
				//BpCustMemberForm.superclass.constructor.call(this, {});
			},//end of the constructor
			//初始化组件、UIComponents : function() {},//end of the initcomponents

			// 初始化组件
			initUIComponents : function() {
				this.tabpanel= new Ext.TabPanel({
					resizeTabs : true, // turn on tab resizing
					enableTabScroll : true,
					isHiddenEdit : false,
					Active : 0,
					width:1000,
					height:600,
					region : 'center', 
					deferredRender : false,
					activeTab : 0, // first tab initially active
					xtype : 'tabpanel',
					items : [
						{
							columnWidth : 1, // 该列在整行中所占的百分比
							monitorValid : true,
							bodyStyle:'padding:10px',
							autoScroll : true ,
							labelAlign : 'right',
							buttonAlign : 'center',
							frame : true ,
							border : false,
							layout : 'column',
							title:'借款申请',
							items : [
											{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '产品名称',
													name : 'productName',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.productName
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '借款标题',
													name : 'loanTitle',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.loanTitle
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '借款金额',
													name : 'loanMoney',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.loanMoney
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '借款期限',
													name : 'loanTimeLen',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.loanTimeLen
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '年化利率',
													name : 'expectAccrual',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.expectAccrual
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '还款方式',
													name : 'payIntersetWay',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.payIntersetWay
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													
													fieldLabel : "贷款用途",
													xtype : "dickeycombo",
													displayField : 'itemName',
													readOnly : true,
													itemName : '贷款用途',
													nodeKey : 'smallloan_purposeType',
													emptyText : "请选择",
													editable : false,
													anchor : "100%",
													allowBlank : false,
													value:(this.memObj==null?'':this.appUser.loanUse),
													listeners : {
														scope : this,
														afterrender : function(combox) {
																		var st = combox.getStore();
																		st.on("load", function() {
																			if (combox.getValue() == 0
																					|| combox.getValue() == 1
																					|| combox.getValue() == ""
																					|| combox.getValue() == null) {
																				combox.setValue("");
																			} else {
																				combox.setValue(combox
																						.getValue());
																			}
																			combox.clearInvalid();
																		})
																	}
													}
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '每月还本金及利息',
													name : 'monthlyInteres',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.monthlyInteres
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '每月交管理费',
													name : 'monthlyCharge',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.monthlyCharge
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '期初服务费',
													name : 'startCharge',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.startCharge
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '借款申请时间',
													name : 'createTime',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.createTime
												}]
											},{
												columnWidth : .33, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textfield",
													fieldLabel : '申请状态',
													name : 'state',
													allowBlank : false,
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.state,
													listeners : {
														scope : this,
														afterrender : function(nf) {
															if(this.appUser.state==1){
																nf.setValue('未受理')
															}else if(this.appUser.state==2){
																nf.setValue('已受理')
															}else if(this.appUser.state==4){
																nf.setValue('已通过审核')
															}else if(this.appUser.state==5){
																nf.setValue('已启动借款审批项目')
															}else{
																nf.setValue('已驳回')
															}
														}
													}
												}]
											},{
												columnWidth : .99, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												labelWidth : 110,
												labelAlign : 'right',
												items : [{
													xtype : "textarea",
													fieldLabel : '备注',
													name : 'remark',
													readOnly : true,
													anchor : '100%',
													value:this.memObj==null?'':this.appUser.remark
												}]
											}
									       ]
								},
								{
									columnWidth : 1, // 该列在整行中所占的百分比
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									layout : 'column',
									title:'个人信息',
									items : [
													{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '真实姓名',
															name : 'truename',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.truename
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '身份证号',
															name : 'cardcode',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.cardcode
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '出生日期',
															name : 'birthday',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.birthday
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
																xtype : "dickeycombo",
																nodeKey : 'dgree',
																fieldLabel : '学历',
																hiddenName : 'person.dgree',
																emptyText : '请选择学历',
																width : 80,
																editable : false,
																value:this.memObj==null?'':this.memObj.collegeDegree,
																readOnly : true,
																listeners : {
																	afterrender : function(combox) {
																		var st = combox.getStore();
																		st.on("load", function() {
																			if (combox.getValue() == 0
																					|| combox.getValue() == 1
																					|| combox.getValue() == ""
																					|| combox.getValue() == null) {
																				combox.setValue("");
																			} else {
																				combox.setValue(combox
																						.getValue());
																			}
																			combox.clearInvalid();
																		})
																	}
																}
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '入学年份',
															name : 'collegeYear',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.collegeYear
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '毕业学校',
															name : 'collegename',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.collegename
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentLiveCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : this.memObj==null?null:this.memObj.parentNativePlaceCity,
															fieldLabel : "籍贯:省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentNativePlaceCity=(this.memObj==null?null:this.memObj.parentNativePlaceCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentNativePlaceCity);
																			})
																	combox.clearInvalid();
																}
															}
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															
														xtype : "combo",
														anchor : "100%",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=(memObj1==null?null:memObj1.parentNativePlaceCity)
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue(memObj1==null?'':memObj1.nativePlaceCity)
																	}
															}
														}
							
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentLiveCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : this.memObj==null?null:this.memObj.parentLiveCity,
															fieldLabel : "居住城市:省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentLiveCity=(this.memObj==null?null:this.memObj.parentLiveCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentLiveCity);
																			})
																	combox.clearInvalid();
																}
															}
														
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "combo",
														anchor : "100%",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=(memObj1==null?null:memObj1.parentLiveCity)
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue(memObj1==null?'':memObj1.liveCity)
																	}
															}
														}
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '邮编',
															name : 'postCode',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.postCode
														}]
													},{
														columnWidth : .3, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textfield",
															fieldLabel : '居住电话',
															name : 'homePhone',
															allowBlank : false,
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.homePhone
														}]
													},{
														columnWidth : .9, // 该列在整行中所占的百分比
														layout : "form", // 从上往下的布局
														labelWidth : 90,
														labelAlign : 'right',
														items : [{
															xtype : "textarea",
															fieldLabel : '居住地址',
															name : 'relationAddress',
															
															readOnly : true,
															anchor : '100%',
															value:this.memObj==null?'':this.memObj.relationAddress
														}]
													}
									        ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									layout : 'column',
									title:'家庭信息',
									items : [{
										columnWidth : 1,
										layout : 'column',
										items :[{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "dickeycombo",
														nodeKey : '8',
														hiddenName : 'person.marry',
														fieldLabel : "婚姻状况",
														value:this.memObj==null?null:this.memObj.marry,
														editable : true,
														readOnly : true,
														anchor : '100%',
														listeners : {
															scope : this,
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																	if (combox.getValue() == 0
																			|| combox
																					.getValue() == 1
																			|| combox
																					.getValue() == ""
																			|| combox
																					.getValue() == null) {
																		combox.setValue("");
																	} else {
																		combox.setValue(combox
																				.getValue());
																	}
																	combox.clearInvalid();
																})
															}
														}
									
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '子女个数',
														name : 'strChildren',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.havechildren
													}]
												}]
									},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '直系亲属姓名',
														name : 'relDirName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relDirName
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype :"dickeycombo",
														nodeKey : 'gxrgx',
														fieldLabel : '直系亲属关系',
														editable : false,
														allowBlank : false,
														anchor : '100%',
														readOnly : true,
														value:this.memObj==null?'':this.memObj.relDirType,
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																			if (combox.getValue() == 0
																					|| combox
																							.getValue() == 1) {
																				combox.setValue("");
																			} else {
																				combox.setValue(combox
																						.getValue());
																			}
																			combox.clearInvalid();
																		})
															}
														}
														
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '直系亲属号码',
														name : 'relDirPhone',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relDirPhone
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '其他亲属姓名',
														name : 'relOtherName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relOtherName
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype :"dickeycombo",
														nodeKey : 'gxrgx',
														fieldLabel : '其他亲属关系',
														editable : false,
														allowBlank : false,
														anchor : '100%',
														readOnly : true,
														value:this.memObj==null?'':this.memObj.relOtherType,
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																			if (combox.getValue() == 0
																					|| combox
																							.getValue() == 1) {
																				combox.setValue("");
																			} else {
																				combox.setValue(combox
																						.getValue());
																			}
																			combox.clearInvalid();
																		})
															}
														}
														
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '其他亲属号码',
														name : 'relOtherPhone',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relOtherPhone
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '其他人姓名',
														name : 'relFriendName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relFriendName
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype :"dickeycombo",
														nodeKey : 'gxrgx',
														fieldLabel : '其他人关系',
														editable : false,
														allowBlank : false,
														anchor : '100%',
														readOnly : true,
														value:this.memObj==null?'':this.memObj.relFriendType,
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																			if (combox.getValue() == 0
																					|| combox
																							.getValue() == 1) {
																				combox.setValue("");
																			} else {
																				combox.setValue(combox
																						.getValue());
																			}
																			combox.clearInvalid();
																		})
															}
														}
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '其他人号码',
														name : 'relFriendPhone',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:this.memObj==null?'':this.memObj.relFriendPhone
													}]
												}
									   ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									monitorValid : true,
									//name : productName2,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									name : 'wd',
									hidden : ((this.appUser.productId==14)?false:true),
									layout : 'column',
									title:'网店信息',
									items : [
												{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店名称',
														name : 'bpCustMember.webshopName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireCompanyname
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店收入',
														name : 'bpCustMember.webshopMonthlyincome',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireMonthlyincome
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店邮箱',
														name : 'bpCustMember.webshopEmail',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireEmail
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentHireCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : (this.memObj==null)?null:this.memObj.parentHireCity,
															fieldLabel : "网店：省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentHireCity=((this.memObj==null)?null:this.memObj.parentHireCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentHireCity);
																			})
																	combox.clearInvalid();
																}
															}
														
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
														anchor : "100%",
														hiddenName : "memObj.webshopCity",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=((memObj1==null)?null:memObj1.parentHireCity)
																/*Ext.Ajax.request({
																	url : __ctxPath + '/creditFlow/multiLevelDic/listByParentIdAreaDic.do?parentId='+v,
																	method : 'post',
																	success : function(resp, op) {
																		var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
																		if(record==''){
																			opr_obj.setValue("");
																		}else{
																			opr_obj.setValue(record[0][1]);
																		}
																		
																	},
																	failure : function() {
																		Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
																	}
																})*/
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue((memObj1==null)?'':memObj1.hireCity)
																	}
															}
														}
														
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店地址',
														name : 'bpCustMember.webshopAddress',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireAddress
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店起始时间',
														name : 'bpCustMember.webshopStartyear',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireStartyear
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '网店电话',
														name : 'bpCustMember.webshopPhone',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireCompanyphone
													}]
												}
									   ]
								},
									{
									columnWidth : 1, // 该列在整行中所占的百分比
									title:'工作信息',
									//name : productName1,
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									name : 'gongzuoxinxi',
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									hidden : (this.appUser.productId==12?false:true),   
									//value:this.appUser.productName,
									layout : 'column',
									items : [
												/*{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														
														xtype : 'combo',
														anchor : '100%',
														fieldLabel:'职位',
														readOnly : true,
														mode : 'local',
														forceSelection : true, 
														displayField : 'typeValue',
														valueField : 'hirePosition',
														editable : false,
														triggerAction : 'all',
														value : this.memObj.hirePosition,														
														anchor : '100%'
														store : new Ext.data.SimpleStore({
															data : [['网商',0],['工薪',1],['私营企业主',2],['教师',3]],
															fields:['typeValue','typeId']
														})
									
														
													}]
												},*/
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司名称',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireCompanyname
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 100,
													labelAlign : 'right',																								
														//scope : this,
						                             	items : [{
							                        	xtype : "dickeycombo",
							                        	anchor : '100%',
								                        nodeKey : 'zhiwujob',
							                        	fieldLabel : '职务',
														hiddenName : 'memObj.hirePosition',
														allowBlank : this.companyHidden,
														// emptyText : '请选择职务',
														//width : 80,
														editable : false,
														readOnly : true,
														value : (this.memObj==null)?'':this.memObj.hirePosition,														
														listeners : {
															afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																if (combox.getValue() == 0
																|| combox.getValue() == 1
																|| combox.getValue() == ""
																|| combox.getValue() == null) {
																	combox.setValue("");
																} else {
																	combox.setValue(combox
																		.getValue());
																}
																	combox.clearInvalid();
																})
															 }
															}

														}]
																					
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司收入',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireMonthlyincome
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作邮箱',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireEmail
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentHireCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : (this.memObj==null)?null:this.memObj.parentHireCity,
															fieldLabel : "工作城市：省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentHireCity=((this.memObj==null)?null:this.memObj.parentHireCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentHireCity);
																			})
																	combox.clearInvalid();
																}
															}
														
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
														anchor : "100%",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=((memObj1==null)?null:memObj1.parentHireCity)
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue((memObj1==null)?'':memObj1.hireCity)
																	}
															}
														}
														
													}]
												},
												{
										        	columnWidth : .6, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司行业',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireCompanycategoryName
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "dickeycombo",
														nodeKey : 'companyScale',
														fieldLabel : "公司规模",
														editable : true,
														value:(this.memObj==null)?'':this.memObj.hireCompanysize,
														readOnly : true,
														anchor : '100%',
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																	if (combox.getValue() == 0
																			|| combox
																					.getValue() == 1
																			|| combox
																					.getValue() == ""
																			|| combox
																					.getValue() == null) {
																		combox.setValue("");
																	} else {
																		combox.setValue(combox
																				.getValue());
																	}
																	combox.clearInvalid();
																})
															}
														}
								
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作起始日期',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireStartyear
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司电话',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireCompanyphone
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "",
														fieldLabel : '',
														name : '',
														readOnly : true,
														anchor : '100%',
														value:''
													}]
												},
												{
										        	columnWidth : .9, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textarea",
														fieldLabel : '公司地址',
														name : 'bpFinanceApply.productName',
														
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.hireAddress
													}]
												}
									   ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									title:'企业信息',
									//name : productName1,
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									name : 'qiyexinxi',
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									hidden : (this.appUser.productId==13?false:true),   
									//value:this.appUser.productName,
									layout : 'column',
									items : [
												/*{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														
														xtype : 'combo',
														anchor : '100%',
														fieldLabel:'职位',
														readOnly : true,
														mode : 'local',
														forceSelection : true, 
														displayField : 'typeValue',
														valueField : 'hirePosition',
														editable : false,
														triggerAction : 'all',
														value : this.memObj.hirePosition,														
														anchor : '100%'
														store : new Ext.data.SimpleStore({
															data : [['网商',0],['工薪',1],['私营企业主',2],['教师',3]],
															fields:['typeValue','typeId']
														})
									
														
													}]
												},*/
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司名称',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossCompanyName
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 100,
													labelAlign : 'right',																								
														//scope : this,
						                             	items : [{
							                        	xtype : "dickeycombo",
							                        	anchor : '100%',
								                        nodeKey : 'zhiwujob',
							                        	fieldLabel : '职位',
														hiddenName : 'memObj.bossPosition',
														allowBlank : this.companyHidden,
														// emptyText : '请选择职务',
														//width : 80,
														editable : false,
														readOnly : true,
														value : (this.memObj==null)?'':this.memObj.bossPosition,														
														listeners : {
															afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																if (combox.getValue() == 0
																|| combox.getValue() == 1
																|| combox.getValue() == ""
																|| combox.getValue() == null) {
																	combox.setValue("");
																} else {
																	combox.setValue(combox
																		.getValue());
																}
																	combox.clearInvalid();
																})
															 }
															}

														}]
																					
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '月收入',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossMonthlyIncome
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作邮箱',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossEmail
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentHireCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : (this.memObj==null)?null:this.memObj.parentHireCity,
															fieldLabel : "工作城市：省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentHireCity=((this.memObj==null)?null:this.memObj.parentHireCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentHireCity);
																			})
																	combox.clearInvalid();
																}
															}
														
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
														anchor : "100%",
														hiddenName : "memObj.bossCity",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=((memObj1==null)?null:memObj1.parentHireCity)
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue((memObj1==null)?'':memObj1.bossCity)
																	}
															}
														}
														
													}]
												},
												{
										        	columnWidth : .6, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司行业',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossCompanyCategory
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "dickeycombo",
														nodeKey : 'companyScale',
														fieldLabel : "公司规模",
														editable : true,
														value:(this.memObj==null)?'':this.memObj.bossCompanySize,
														readOnly : true,
														anchor : '100%',
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																	if (combox.getValue() == 0
																			|| combox
																					.getValue() == 1
																			|| combox
																					.getValue() == ""
																			|| combox
																					.getValue() == null) {
																		combox.setValue("");
																	} else {
																		combox.setValue(combox
																				.getValue());
																	}
																	combox.clearInvalid();
																})
															}
														}
								
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作起始日期',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossStartYear
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司电话',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossCompanyPhone
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "",
														fieldLabel : '',
														name : '',
														readOnly : true,
														anchor : '100%',
														value:''
													}]
												},
												{
										        	columnWidth : .9, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textarea",
														fieldLabel : '公司地址',
														name : 'bpFinanceApply.productName',
														
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.bossAddress
													}]
												}
									   ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									title:'教师信息',
									//name : productName1,
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									name : 'jiaoshixinxi',
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									hidden : (this.appUser.productId==15?false:true),   
								//	value:this.appUser.productName,
									layout : 'column',
									items : [
												/*{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														
														xtype : 'combo',
														anchor : '100%',
														fieldLabel:'职位',
														readOnly : true,
														mode : 'local',
														forceSelection : true, 
														displayField : 'typeValue',
														valueField : 'hirePosition',
														editable : false,
														triggerAction : 'all',
														value : this.memObj.hirePosition,														
														anchor : '100%'
														store : new Ext.data.SimpleStore({
															data : [['网商',0],['工薪',1],['私营企业主',2],['教师',3]],
															fields:['typeValue','typeId']
														})
									
														
													}]
												},*/
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '单位名称',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherCompanyName
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 100,
													labelAlign : 'right',																								
														//scope : this,
						                             	items : [{
							                        	xtype : "dickeycombo",
							                        	anchor : '100%',
								                        nodeKey : 'zhiwujob',
							                        	fieldLabel : '职位',
														hiddenName : 'memObj.teacherPosition',
														allowBlank : this.companyHidden,
														// emptyText : '请选择职务',
														//width : 80,
														editable : false,
														readOnly : true,
														value : (this.memObj==null)?'':this.memObj.teacherPosition,														
														listeners : {
															afterrender : function(combox) {
															
															var st = combox.getStore();
															st.on("load", function() {
																if (combox.getValue() == 0
																|| combox.getValue() == 1
																|| combox.getValue() == ""
																|| combox.getValue() == null) {
																	combox.setValue("");
																} else {
																	//(this.memObj.teacherPosition=='956'?"一线老师":(this.memObj.teacherPosition=='957'?"二线工勤":"管理研究"))
																	
																	Ext.Ajax.request({
																		url : __ctxPath + '/system/queryDicValueDictionary.do?dicId='+combox.getValue(),
																		method : 'post',
																		success : function(resp, op) {
																			var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
																			if(record.result=='null'){
																				combox.setValue("");
																			}else{
																				//alert("record.result=="+record.result);
																				combox.setValue(record.result);
																			}
																			
																		},
																		failure : function() {
																			Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
																		}
																	})
																}
																	combox.clearInvalid();
																})
															 }
															}

														}]
																					
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '月收入',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherMonthlyIncome
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作邮箱',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherEmail
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
															anchor : "100%",
															hiddenName : "parentHireCity",
															displayField : 'itemName',
															valueField : 'itemId',
															triggerAction : 'all',
															readOnly : true,
															store : new Ext.data.SimpleStore({
																autoLoad : true,
																url : __ctxPath
																		+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																fields : ['itemId', 'itemName'],
																baseParams:{parentId:6591}
															}),
															value : (this.memObj==null)?null:this.memObj.parentHireCity,
															fieldLabel : "工作城市：省",
															listeners : {
																scope : this,
																afterrender : function(combox) {
																	var parentHireCity=((this.memObj==null)?null:this.memObj.parentHireCity)
																	var st = combox.getStore();
																	st.on("load", function() {
																				combox.setValue(parentHireCity);
																			})
																	combox.clearInvalid();
																}
															}
														
													}]
												},
												{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
															xtype : "combo",
														anchor : "100%",
														hiddenName : "memObj.teacherCity",
														displayField : 'itemName',
														valueField : 'itemId',
														triggerAction : 'all',
														readOnly : true,
														store : new Ext.data.SimpleStore({
															url : __ctxPath
																	+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
															fields : ['itemId', 'itemName']
														}),
														fieldLabel : "市",
														listeners : {
															scope : this,
															afterrender : function(opr_obj) {
																var memObj1=this.memObj
																var v=((memObj1==null)?null:memObj1.parentHireCity)
																	var arrStore = new Ext.data.SimpleStore({
																		url : __ctxPath
																				+ '/creditFlow/multiLevelDic/listByParentIdAreaDic.do',
																		fields : ['itemId', 'itemName'],
																		baseParams:{parentId:v}
																	})
																	opr_obj.clearValue();
																	opr_obj.store = arrStore;
																	arrStore.load({
																				"callback" : test
																			});
																	function test(r) {
																		if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
																			opr_obj.view.setStore(arrStore);
																		}
																		opr_obj.setValue((memObj1==null)?'':memObj1.hireCity)
																	}
															}
														}
														
													}]
												}/*,
												{
										        	columnWidth : .6, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司行业',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null || this.memObj.careerType==0)?'':this.memObj.hireCompanycategoryName
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "dickeycombo",
														nodeKey : 'companyScale',
														fieldLabel : "公司规模",
														editable : true,
														value:(this.memObj==null || this.memObj.careerType==0)?'':this.memObj.hireCompanysize,
														readOnly : true,
														anchor : '100%',
														listeners : {
															afterrender : function(combox) {
																var st = combox.getStore();
																st.on("load", function() {
																	if (combox.getValue() == 0
																			|| combox
																					.getValue() == 1
																			|| combox
																					.getValue() == ""
																			|| combox
																					.getValue() == null) {
																		combox.setValue("");
																	} else {
																		combox.setValue(combox
																				.getValue());
																	}
																	combox.clearInvalid();
																})
															}
														}
								
													}]
												}*/,{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '工作起始日期',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherStartYear
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '公司电话',
														name : 'bpFinanceApply.productName',
														allowBlank : false,
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherCompanyPhone
													}]
												},{
										        	columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "",
														fieldLabel : '',
														name : '',
														readOnly : true,
														anchor : '100%',
														value:''
													}]
												},
												{
										        	columnWidth : .9, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textarea",
														fieldLabel : '公司地址',
														name : 'bpFinanceApply.productName',
														
														readOnly : true,
														anchor : '100%',
														value:(this.memObj==null)?'':this.memObj.teacherAddress
													}]
												}
									   ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									title:'资产信息',
									monitorValid : true,
									bodyStyle:'padding:10px',
									autoScroll : true ,
									labelAlign : 'right',
									buttonAlign : 'center',
									frame : true ,
									border : false,
									layout : 'column',
									items : [
												{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '是否有房',
														name : 'bpCustMember.havehouse',
														readOnly : true,
														labelAlign : 'right',
														anchor : '100%',
														listeners : {
															scope : this,
															afterrender : function(nf) {
																if(this.memObj.havehouse=='1'){
																	nf.setValue('是')
																}else{
																	nf.setValue('否')
																}
															}
														}
													}]
												},
												{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '是否有房贷',
														name : 'bpCustMember.strHouseLoan',
														readOnly : true,
														anchor : '100%',
														listeners : {
															scope : this,
															afterrender : function(nf) {
																if(this.memObj.havehouseloan=='1'){
																	nf.setValue('是')
																}else{
																	nf.setValue('否')
																}
															}
														}
													}]
												},{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '是否有车',
														name : 'bpCustMember.strCar',
														readOnly : true,
														anchor : '100%',
														listeners : {
															scope : this,
															afterrender : function(nf) {
																if(this.memObj.havecar=='1'){
																	nf.setValue('是')
																}else{
																	nf.setValue('否')
																}
															}
														}
													}]
												},
												{
													columnWidth : .3, // 该列在整行中所占的百分比
													layout : "form", // 从上往下的布局
													labelWidth : 90,
													labelAlign : 'right',
													items : [{
														xtype : "textfield",
														fieldLabel : '是否有车贷',
														name : 'bpCustMember.strCarLoan',
														readOnly : true,
														anchor : '100%',
														listeners : {
															scope : this,
															afterrender : function(nf) {
																if(this.memObj.havecarloan=='1'){
																	nf.setValue('是')
																}else{
																	nf.setValue('否')
																}
															}
														}
													}]
												}
												
									   ]
								},{
									columnWidth : 1, // 该列在整行中所占的百分比
									layout : "border", // 从上往下的布局
									title:'资料清单',

									items :[new HT.GridPanel({
										region:'center',
										tbar:this.topbar,
										//使用RowActions
										rowActions:true,
										id:'WebFinanceApplyUploadsGrid',
										url : __ctxPath + "/p2p/listWebFinanceApplyUploads.do?userId="+this.userId,
										fields : [{
														name : 'id',
														type : 'int'
													}
													,'userID'
													,'materialstype'
													,'files'
													,'status'
													,'pictureNum'
													,'lastuploadtime'
												],
										columns:[
													{
														header : 'id',
														dataIndex : 'id',
														hidden : true
													}
													,{
														header : '用户ID',	
														dataIndex : 'userID',
														hidden:true
													}
													,{
														header : '材料类型',	
														dataIndex : 'materialstype'
													}
													,{
														header : '数量',	
														dataIndex :'pictureNum'
													}
													,{
														header : '状态',	
														dataIndex : 'status',
														renderer:function(v){
					                                    if(v==0){
														return '未上传';
					                                    }else if(v==1){
					                                    	return '已上传、待审查或补充材料';
					                                    }else if(v==2){
					                                    	return '已驳回';
					                                    }else if(v==3){
					                                    	return '已认证';
					                                    }
													}
													}
													,{
							                            header : '上传时间',	
														dataIndex : 'lastuploadtime'
													}
													, new Ext.ux.grid.RowActions({
														header:'管理',
														width:100,
														actions:[	{
																         iconCls:'btn-readdocument',qtip:'浏览',style:'margin:0 3px 0 3px'
																	},{
																		 iconCls:'btn-add',qtip:'通过',style:'margin:0 3px 0 3px'
																	},{
																		 iconCls:'btn-del',qtip:'驳回',style:'margin:0 3px 0 3px'
																	}
														],
														listeners:{
															scope:this,
															'action':this.onRowAction
														}
													})
										]//end of columns
									})]
								}
							]
				});
				if(this.appUser.productId==12){
					this.tabpanel.remove(this.tabpanel.getCmpByName('wd')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('qiyexinxi')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('jiaoshixinxi'))
					
				}else if(this.appUser.productId==13){
					this.tabpanel.remove(this.tabpanel.getCmpByName('wd')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('gongzuoxinxi')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('jiaoshixinxi'))
					
				}else if(this.appUser.productId==14){
					this.tabpanel.remove(this.tabpanel.getCmpByName('gongzuoxinxi')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('qiyexinxi')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('jiaoshixinxi'))
					
				}else{
					this.tabpanel.remove(this.tabpanel.getCmpByName('wd')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('qiyexinxi')),
					this.tabpanel.remove(this.tabpanel.getCmpByName('gongzuoxinxi'))
					
				}
				
			},
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
			updateRs : function(id,c) {
				var state;
				if(c=='a'){
					state='2';
				}else if(c=='b'){
					state='3'
				}
				Ext.Ajax.request({
					url : __ctxPath + '/p2p/saveWebFinanceApplyUploads.do',
					method:'post',
					params:{'id':id,'state':state},
					async:false,
					success: function(resp,opts) {
						Ext.ux.Toast.msg('操作信息', '操作成功!');
						this.HT.GridPanel.getStore().reload();
					}
				});
			},//end of save
			rejectRs : function(id,c,grid) {
					var formPanel=new Ext.FormPanel({
						border : false,
						autoScroll : true,
						monitorValid : true,
						layout : 'column',
						frame : true,
						items : [{
							columnWidth : 1,
							layout : 'form',
							labelWidth : 60,
							labelAlign : 'right',
							items : [{
								xtype : 'textarea',
								name : 'rejectReason',
								fieldLabel : '驳回原因',
								anchor : '100%'
							}]
						}]
					})
					var win=new Ext.Window({
						layout : 'fit',
						modal : true,
						maximizable : true,
						title : '驳回原因',
						width : 500,
						height : 150,
						items : formPanel,
						buttonAlign : 'center',
						buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : function(){
									var rejectReason=formPanel.getCmpByName('rejectReason').getValue()
									Ext.Ajax.request({
										url : __ctxPath + '/p2p/saveWebFinanceApplyUploads.do',
										method:'post',
										params:{'id':id,'state':'2','webFinanceApplyUploads.rejectReason':rejectReason},
										//async:false,
										success: function(resp,opts) {
											Ext.ux.Toast.msg('操作信息', '操作成功!');
											grid.getStore().reload();
											win.close()
										}
									});
								}
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : function(){
									formPanel.getForm().reset();
								}
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : function(){
									win.close()
								}
							}]
					}).show()
					
			},
			
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				// 
				switch (action) {
					case 'btn-del' :
						this.rejectRs.call(this,record.data.id,"a",grid);
						break;
					case 'btn-add':
						this.updateRs.call(this,record.data.id,"b");
						break;
					case 'btn-readdocument':
					 var webId=grid.getStore().getAt(row).get("userID");
					 
					 
					 var materialstype=grid.getStore().getAt(row).get("materialstype");
					 var mark=webId+"."+materialstype;
				     BpCustMemberPicView(mark,"readOnly","typeisfile",/*grid.ownerCt.projId*/this.projId,/*grid.ownerCt.businessType*/this.businessType);
					 break;
					 default :
					break;
				}
			}
		});






