function reditregistriesListWin(personIdValue,isReadOnly){
	var anchor = '100%';
/*	Ext.Ajax.request({   
    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
   	 	method:'post',   
    	params:{perosnName:cardnumber},   
    	success: function(response, option) {  
        	var obj = Ext.decode(response.responseText);
        	var personIdValue = obj.data.id;*/ 
			var jStoreReditregis = new Ext.data.JsonStore( {
				url : __ctxPath+'/creditFlow/customer/person/queryListProcessPersonCarReditregistries.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {name : 'id'}, {name : 'creditregistriesNo'}, {name : 'queryTime'}, {name:'reportQueryTime'}, {name:'bankAccountNum'}, {name : 'bankCreditBalance'}, {name:'foreHouseNumber'}, {name:'forePostcode'}, {name:'foreProvince'} ],
				baseParams :{personId : personIdValue}
			});
			jStoreReditregis.load({
				params : {
					start : 0,
					limit : 20
				}
			});
			var button_add = new Ext.Button({
				text : '增加',
				tooltip : '增加征信记录信息',
				iconCls : 'addIcon',
				scope : this,
				handler : function() {
					var addReditregisPanel = new Ext.form.FormPanel({
						url : __ctxPath+'/creditFlow/customer/person/addProcessPersonCarReditregistries.do',
						monitorValid : true,
						bodyStyle:'padding:10px',
						labelAlign : 'right',
						autoScroll : false ,
						buttonAlign : 'center',
						autoHeight : true,
						width:1000,
						frame : true ,
						items : [{
							layout : 'column',
							xtype:'fieldset',
							title: '征信记录',
				            collapsible: false,
				            autoHeight:true,
				            width: 600,
				            items : [{
				            	columnWidth : 1,
				            	layout : 'form',
								labelWidth : 80,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '信用报告编号',
									name : 'reditregistries.creditregistriesNo'
								}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 75,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'datefield',
									fieldLabel : '查询日期',
									format : 'Y-m-d',
									name : 'reditregistries.queryTime'
								}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 85,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'datefield',
									fieldLabel : '报告查询日期',
									format : 'Y-m-d',
									name : 'reditregistries.reportQueryTime'
								}]
				            }]
						},{
							
							layout : 'column',
							xtype:'fieldset',
							title: '现住址信息',
				            collapsible: false,
				            autoHeight:true,
				            width: 600,
				            items : [{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 20,
								defaults : {anchor : anchor},
								items :[{
									id : 'nowsheng', 
									xtype : 'textfield',
									fieldLabel : '省',
									name : 'nowProvince',
								 	listeners : {
										'focus':function(){
											selectDictionary('area' ,getObjArray1);
										}
				           	 		}
								},{
									id :'nowshengid',
									xtype : 'hidden',
									name : 'reditregistries.nowProvince'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
				            	labelWidth : 20,
								defaults : {anchor : anchor},
								items :[{
									id : 'nowshi',
									xtype : 'textfield',
									fieldLabel : '市',
									name : 'foreCity',
									readOnly : true
								},{
									id :'nowshiid',
									xtype : 'hidden',
									name : 'reditregistries.nowCity'
								}]
				            },{
				            	columnWidth : .34,
				            	layout : 'form',
				            	labelWidth : 40,
								defaults : {anchor : anchor},
								items :[{
									id : 'nowxian',
									xtype : 'textfield',
									fieldLabel : '区/县',
									name : 'nowDistrict',
									readOnly : true
								},{
									id :'nowxianid',
									xtype : 'hidden',
									name : 'reditregistries.nowDistrict'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '路名',
									name : 'reditregistries.nowRoadName'
								}]
				            },{
				            	columnWidth : .25,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '路号',
									name : 'reditregistries.nowRoadNo'
								}]
				            },{
				            	columnWidth : .42,
				            	layout : 'form',
								labelWidth : 45,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '社区名',
									name : 'reditregistries.nowCommunity'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 45,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '门牌号',
									name : 'reditregistries.nowHouseNumber'
								}]
				            },{
				            	columnWidth : .21,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '邮编',
									name : 'reditregistries.nowPostcode',
									regex : /^[0-9]{6}$/,
									regexText : '邮编格式不正确'
								}]
				            },{
				            	columnWidth : .46,
				            	layout : 'form',
								labelWidth : 105,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'datefield',
									fieldLabel : '居住信息获取时间',
									format : 'Y-m-d',
									name : 'reditregistries.nowResideTime'
								}]
				            }]
						
						},{
							
							layout : 'column',
							xtype:'fieldset',
							title: '前一住址信息',
				            collapsible: false,
				            autoHeight:true,
				            width: 600,
				            items : [{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 20,
								defaults : {anchor : anchor},
								items :[{
									id : 'foresheng',
									xtype : 'textfield',
									fieldLabel : '省',
									name : 'foreProvince',
								 	listeners : {
										'focus':function(){
											selectDictionary('area' ,getObjArray2);
										}
				           	 		}
								},{
									id :'foreshengid',
									xtype : 'hidden',
									name : 'reditregistries.foreProvince'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
				            	labelWidth : 20,
								defaults : {anchor : anchor},
								items :[{
									id : 'foreshi',
									xtype : 'textfield',
									fieldLabel : '市',
									name : 'foreCity',
									readOnly : true
								},{
									id : 'foreshiid',
									xtype : 'hidden',
									name : 'reditregistries.foreCity'
								}]
				            },{
				            	columnWidth : .34,
				            	layout : 'form',
				            	labelWidth : 40,
								defaults : {anchor : anchor},
								items :[{
									id : 'forexian',
									xtype : 'textfield',
									fieldLabel : '区/县',
									name : 'familyxian',
									readOnly : true
								},{
									id : 'forexianid',
									xtype : 'hidden',
									name : 'reditregistries.foreDistrict'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '路名',
									name : 'reditregistries.foreRoadName'
								}]
				            },{
				            	columnWidth : .25,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '路号',
									name : 'reditregistries.foreRoadNo'
								}]
				            },{
				            	columnWidth : .42,
				            	layout : 'form',
								labelWidth : 45,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '社区名',
									name : 'reditregistries.foreCommunity'
								}]
				            },{
				            	columnWidth : .33,
				            	layout : 'form',
								labelWidth : 45,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '门牌号',
									name : 'reditregistries.foreHouseNumber'
								}]
				            },{
				            	columnWidth : .21,
				            	layout : 'form',
								labelWidth : 35,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '邮编',
									name : 'reditregistries.forePostcode',
									regex : /^[0-9]{6}$/,
									regexText : '邮编格式不正确'
								}]
				            },{
				            	columnWidth : .46,
				            	layout : 'form',
								labelWidth : 105,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'datefield',
									fieldLabel : '居住信息获取时间',
									format : 'Y-m-d',
									name : 'reditregistries.foreResideTime'
								}]
				            }]
						},{
							layout : 'column',
							xtype:'fieldset',
							title: '信用情况',
				            collapsible: false,
				            autoHeight:true,
				            width: 600,
				            items : [{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '贷款总笔数',
									name : 'reditregistries.creditAmount'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '当前贷款总额',
									name : 'reditregistries.currentTotalLoans'
				            	}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
					            	xtype : 'numberfield',
									fieldLabel : '银行授信额度',
									name : 'reditregistries.bankauthorizeMoney'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '当前拖欠总额',
									name : 'reditregistries.currentDefaultLoans'
				            	}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '历史贷款总额',
									name : 'reditregistries.historyTotalLoans'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '信用卡总数',
									name : 'reditregistries.creditCardsNum'
				            	}]
				            },	
				            {
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '最近24个月最高连续逾期数',
									name : 'reditregistries.twoYearHighestOverdue'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '当前透支余额',
									name : 'reditregistries.currentOverdraftBalance'
				            	}]
				            },		
				            {
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '当前信用卡数',
									name : 'reditregistries.currentCreditCardNum'
				            	},{
				            		xtype : 'textfield',
									fieldLabel : '社保单位',
									name : 'reditregistries.socialSecurityUnit'
				            	}]
				            },		
				            {
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '最近1个月内的查询机构数',
									name : 'reditregistries.oneMonthQueryMechanismNum'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近1个月内的查询次数',
									name : 'reditregistries.oneMonthQueryNum'
				            	}]
				            },					
				            {
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'numberfield',
									fieldLabel : '最近两年内的查询次数',
									name : 'reditregistries.twoYearQueryNum'
				            	}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items : [{
				            		xtype : 'textfield',
									fieldLabel : '银行信贷账户数',
									name : 'reditregistries.bankAccountNum'
				            	},{
				            		xtype : 'textfield',
									fieldLabel : '银行贷款余额',
									name : 'reditregistries.bankCreditBalance'
				            	}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',	
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items :[{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支1-30天次数',
									name : 'reditregistries.overdrafttime30day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支61-90天次数',
									name : 'reditregistries.overdrafttime90day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支121-150天次数',
									name : 'reditregistries.overdrafttime150day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支180天以上次数',
									name : 'reditregistries.overdrafttimePass180day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月结清的账户数',
									name : 'reditregistries.twoYearEndAccount'
				            	}]
				            },{
				            	columnWidth : .5,
				            	layout : 'form',	
				            	labelWidth : 165,
				            	defaults : {anchor : anchor},
				            	items :[{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支31-60天次数',
									name : 'reditregistries.overdrafttime60day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支91-120天次数',
									name : 'reditregistries.overdrafttime120day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月透支151-180天次数',
									name : 'reditregistries.overdrafttime180day'
				            	},{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月开立的账户数',
									name : 'reditregistries.twoYearBeginAccount'
				            	}]
				            },{
				            	columnWidth : 1,
				            	layout : 'form',	
				            	labelWidth : 250,
				            	defaults : {anchor : anchor},
				            	items :[{
				            		xtype : 'numberfield',
									fieldLabel : '最近24月除结清外其他任何形态的透支账户数',
									name : 'reditregistries.twoYearOtherAccount'
				            	}]
				            },{
				            	xtype : 'hidden',
				            	name : 'reditregistries.mortgagorId',
				            	value : personIdValue
				            }]
				          }]
					})
					var baoButton = new Ext.Button({
						text : '保存',
						tooltip : '保存征信记录信息',
						iconCls : 'submitIcon',
						formBind : true,
						scope : this,
						handler : function() {
								addReditregisPanel.getForm().submit({
									method : 'POST',
									waitTitle : '连接',
									waitMsg : '消息发送中...',
									success : function(form ,action) {
											Ext.ux.Toast.msg('状态', '添加成功!');
													jStoreReditregis.reload();
													Ext.getCmp('addReditregisWindow').destroy();
									},
									failure : function(form, action) {
										if(action.response.status==0){
											Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
										}else if(action.response.status==-1){
											Ext.ux.Toast.msg('状态','连接超时，请重试!');
										}else{
											Ext.ux.Toast.msg('状态','添加失败!');		
										}
									}
								});
							
						}
					})
					var quButton = new Ext.Button({
						text : '取消',
						tooltip : '取消征信记录信息',
						scope : this,
						iconCls : 'cancelIcon',
						handler : function() {
							Ext.getCmp('addReditregisWindow').destroy();
						}
					})
					var reditregisPanel = new Ext.Panel({
						layout : 'fit',
						width :(screen.width-180)*0.6 + 20 - 30,
						autoHeight : true,
						autoScroll : false ,
						tbar :[baoButton],
						items :[addReditregisPanel]
					})
					var addReditregisWindow = new Ext.Window({
						id : 'addReditregisWindow',
						title: '新增征信记录信息',
						layout : 'fit',
						width :(screen.width-180)*0.6 + 20,
						height :390,
						closable : true,
						constrainHeader : true ,
						resizable : true,
						plain : true,
						border : false,
						modal : true,
						autoScroll : true ,
						buttonAlign: 'right',
						iconCls : 'newIcon',
						bodyStyle:'overflowX:hidden',
						items :[reditregisPanel]
					}).show();
				}
			});
			var button_update = new Ext.Button({
				text : '编辑',
				tooltip : '编辑征信记录信息',
				iconCls : 'updateIcon',
				scope : this,
				handler : function() {
					var selected = gPanelReditregis.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/customer/person/seeProcessPersonCarReditregistries.do',
							method : 'POST',
							success : function(response,request) {
								obj = Ext.util.JSON.decode(response.responseText);
									var reditregisData = obj.data;
									var updateReditregisPanel = new Ext.form.FormPanel({
										url : __ctxPath+'/creditFlow/customer/person/updateProcessPersonCarReditregistries.do',
										monitorValid : true,
										bodyStyle:'padding:10px',
										labelAlign : 'right',
										autoScroll : false ,
										buttonAlign : 'center',
										autoHeight : true,
										frame : true ,
										items : [{
											layout : 'column',
											xtype:'fieldset',
											title: '征信记录',
								            collapsible: false,
								            autoHeight:true,
								            width: 600,
								            items : [{
								            	columnWidth : 1,
								            	layout : 'form',
												labelWidth : 80,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '信用报告编号',
													name : 'reditregistries.creditregistriesNo',
													value : reditregisData.creditregistriesNo
												}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 80,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'datefield',
													fieldLabel : '查询日期',
													format : 'Y-m-d',
													name : 'reditregistries.queryTime',
													value : reditregisData.queryTime
												}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 90,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'datefield',
													fieldLabel : '报告查询日期',
													format : 'Y-m-d',
													name : 'reditregistries.reportQueryTime',
													value : reditregisData.reportQueryTime
												}]
								            }]
										},{											
											layout : 'column',
											xtype:'fieldset',
											title: '现住址信息',
								            collapsible: false,
								            autoHeight:true,
								            width: 600,
								            items : [{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 20,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_nowsheng', 
													xtype : 'textfield',
													fieldLabel : '省',
													name : 'nowProvince',
												 	listeners : {
														'focus':function(){
															selectDictionary('area' ,getObjArrayUp1);
														}
								           	 		},
								           	 		value : reditregisData.nowProvinceValue
												},{
													id :'up_nowshengid',
													xtype : 'hidden',
													name : 'reditregistries.nowProvince',
													value : reditregisData.nowProvince
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
								            	labelWidth : 20,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_nowshi',
													xtype : 'textfield',
													fieldLabel : '市',
													name : 'foreCity',
													readOnly : true,
													value : reditregisData.nowCityValue
												},{
													id :'up_nowshiid',
													xtype : 'hidden',
													name : 'reditregistries.nowCity',
													value : reditregisData.nowCity
												}]
								            },{
								            	columnWidth : .34,
								            	layout : 'form',
								            	labelWidth : 40,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_nowxian',
													xtype : 'textfield',
													fieldLabel : '区/县',
													name : 'nowDistrict',
													readOnly : true,
													value : reditregisData.nowDistrictValue
												},{
													id :'up_nowxianid',
													xtype : 'hidden',
													name : 'reditregistries.nowDistrict',
													value : reditregisData.nowDistrict
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '路名',
													name : 'reditregistries.nowRoadName',
													value : reditregisData.nowRoadName
												}]
								            },{
								            	columnWidth : .25,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '路号',
													name : 'reditregistries.nowRoadNo',
													value : reditregisData.nowRoadNo
												}]
								            },{
								            	columnWidth : .42,
								            	layout : 'form',
												labelWidth : 45,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '社区名',
													name : 'reditregistries.nowCommunity',
													value : reditregisData.nowCommunity
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 45,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '门牌号',
													name : 'reditregistries.nowHouseNumber',
													value : reditregisData.nowHouseNumber
												}]
								            },{
								            	columnWidth : .21,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '邮编',
													name : 'reditregistries.nowPostcode',
													regex : /^[0-9]{6}$/,
													regexText : '邮编格式不正确',
													value : reditregisData.nowPostcode
												}]
								            },{
								            	columnWidth : .46,
								            	layout : 'form',
												labelWidth : 105,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'datefield',
													fieldLabel : '居住信息获取时间',
													format : 'Y-m-d',
													name : 'reditregistries.nowResideTime',
													value : reditregisData.nowResideTime
												}]
								            }]
										},{
											layout : 'column',
											xtype:'fieldset',
											title: '前一住址信息',
								            collapsible: false,
								            autoHeight:true,
								            width: 600,
								            items : [{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 20,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_foresheng',
													xtype : 'textfield',
													fieldLabel : '省',
													name : 'foreProvince',
												 	listeners : {
														'focus':function(){
															selectDictionary('area' ,getObjArrayUp2);
														}
								           	 		},
								           	 		value : reditregisData.foreProvinceValue
												},{
													id :'up_foreshengid',
													xtype : 'hidden',
													name : 'reditregistries.foreProvince',
													value : reditregisData.foreProvince
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
								            	labelWidth : 20,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_foreshi',
													xtype : 'textfield',
													fieldLabel : '市',
													name : 'foreCity',
													readOnly : true,
													value : reditregisData.foreCityVlaue
												},{
													id : 'up_foreshiid',
													xtype : 'hidden',
													name : 'reditregistries.foreCity',
													value : reditregisData.foreCity
												}]
								            },{
								            	columnWidth : .34,
								            	layout : 'form',
								            	labelWidth : 40,
												defaults : {anchor : anchor},
												items :[{
													id : 'up_forexian',
													xtype : 'textfield',
													fieldLabel : '区/县',
													name : 'familyxian',
													readOnly : true,
													value : reditregisData.foreDistrictValue
												},{
													id : 'up_forexianid',
													xtype : 'hidden',
													name : 'reditregistries.foreDistrict',
													value : reditregisData.foreDistrict
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '路名',
													name : 'reditregistries.foreRoadName',
													value : reditregisData.foreRoadName
												}]
								            },{
								            	columnWidth : .25,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '路号',
													name : 'reditregistries.foreRoadNo',
													value : reditregisData.foreRoadNo
												}]
								            },{
								            	columnWidth : .42,
								            	layout : 'form',
												labelWidth : 45,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '社区名',
													name : 'reditregistries.foreCommunity',
													value : reditregisData.foreCommunity
												}]
								            },{
								            	columnWidth : .33,
								            	layout : 'form',
												labelWidth : 45,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '门牌号',
													name : 'reditregistries.foreHouseNumber',
													value : reditregisData.foreHouseNumber
												}]
								            },{
								            	columnWidth : .21,
								            	layout : 'form',
												labelWidth : 35,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'textfield',
													fieldLabel : '邮编',
													name : 'reditregistries.forePostcode',
													regex : /^[0-9]{6}$/,
													regexText : '邮编格式不正确',
													value : reditregisData.forePostcode
												}]
								            },{
								            	columnWidth : .46,
								            	layout : 'form',
												labelWidth : 105,
												defaults : {anchor : anchor},
												items :[{
													xtype : 'datefield',
													fieldLabel : '居住信息获取时间',
													format : 'Y-m-d',
													name : 'reditregistries.foreResideTime',
													value : reditregisData.foreResideTime
												}]
								            }]
										},{
											layout : 'column',
											xtype:'fieldset',
											title: '信用情况',
								            collapsible: false,
								            autoHeight:true,
								            width: 600,
								            items : [{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 95,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '贷款总笔数',
													name : 'reditregistries.creditAmount',
													value : reditregisData.creditAmount
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '当前贷款总额',
													name : 'reditregistries.currentTotalLoans',
													value : reditregisData.currentTotalLoans
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
									            	xtype : 'numberfield',
													fieldLabel : '银行授信额度',
													name : 'reditregistries.bankauthorizeMoney',
													value : reditregisData.bankauthorizeMoney
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '当前拖欠总额',
													name : 'reditregistries.currentDefaultLoans',
													value : reditregisData.currentDefaultLoans
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '历史贷款总额',
													name : 'reditregistries.historyTotalLoans',
													value : reditregisData.historyTotalLoans
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '信用卡总数',
													name : 'reditregistries.creditCardsNum',
													value : reditregisData.creditCardsNum
								            	}]
								            },	
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近24个月最高连续逾期数',
													name : 'reditregistries.twoYearHighestOverdue',
													value : reditregisData.twoYearHighestOverdue
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '当前透支余额',
													name : 'reditregistries.currentOverdraftBalance',
													value : reditregisData.currentOverdraftBalance
								            	}]
								            },		
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '当前信用卡数',
													name : 'reditregistries.currentCreditCardNum',
													value : reditregisData.currentCreditCardNum
								            	},{
								            		xtype : 'textfield',
													fieldLabel : '社保单位',
													name : 'reditregistries.socialSecurityUnit',
													value : reditregisData.socialSecurityUnit
								            	}]
								            },		
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近1个月内的查询机构数',
													name : 'reditregistries.oneMonthQueryMechanismNum',
													value : reditregisData.oneMonthQueryMechanismNum
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近1个月内的查询次数',
													name : 'reditregistries.oneMonthQueryNum',
													value : reditregisData.oneMonthQueryNum
								            	}]
								            },					
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近两年内的查询次数',
													name : 'reditregistries.twoYearQueryNum',
													value : reditregisData.twoYearQueryNum
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 95,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'textfield',
													fieldLabel : '银行信贷账户数',
													name : 'reditregistries.bankAccountNum',
													value : reditregisData.bankAccountNum
										          }]
								            },{
								            	columnWidth : 1,
								            	layout : 'form',
								            	labelWidth : 95,
								            	defaults : {anchor : anchor},
								            	items :[{
								            		xtype : 'textfield',
													fieldLabel : '银行贷款余额',
													name : 'reditregistries.bankCreditBalance',
													value : reditregisData.bankCreditBalance
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',	
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items :[{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支1-30天次数',
													name : 'reditregistries.overdrafttime30day',
													value : reditregisData.overdrafttime30day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支61-90天次数',
													name : 'reditregistries.overdrafttime90day',
													value : reditregisData.overdrafttime90day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支121-150天次数',
													name : 'reditregistries.overdrafttime150day',
													value : reditregisData.overdrafttime150day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支180天以上次数',
													name : 'reditregistries.overdrafttimePass180day',
													value : reditregisData.overdrafttimePass180day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月结清的账户数',
													name : 'reditregistries.twoYearEndAccount',
													value : reditregisData.twoYearEndAccount
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',	
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items :[{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支31-60天次数',
													name : 'reditregistries.overdrafttime60day',
													value : reditregisData.overdrafttime60day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支91-120天次数',
													name : 'reditregistries.overdrafttime120day',
													value : reditregisData.overdrafttime120day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月透支151-180天次数',
													name : 'reditregistries.overdrafttime180day',
													value : reditregisData.overdrafttime180day
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月开立的账户数',
													name : 'reditregistries.twoYearBeginAccount',
													value : reditregisData.twoYearBeginAccount
								            	}]
								            },{
								            	columnWidth : 1,
								            	layout : 'form',	
								            	labelWidth : 250,
								            	defaults : {anchor : anchor},
								            	items :[{
								            		xtype : 'numberfield',
													fieldLabel : '最近24月除结清外其他任何形态的透支账户数',
													name : 'reditregistries.twoYearOtherAccount',
													value : reditregisData.twoYearOtherAccount
								            	}]
								            },{
								            	xtype : 'hidden',
								            	name : 'reditregistries.mortgagorId',
								            	value : reditregisData.mortgagorId
								            },{
								            	xtype : 'hidden',
								            	name : 'reditregistries.id',
								            	value : reditregisData.id
								            }]
										}]
									});
									var baoButton = new Ext.Button({
										text : '保存',
										tooltip : '保存征信记录信息',
										iconCls : 'submitIcon',
										formBind : true,
										scope : this,
										handler : function() {
												updateReditregisPanel.getForm().submit({
													method : 'POST',
													waitTitle : '连接',
													waitMsg : '消息发送中...',
													success : function(form ,action) {
															Ext.ux.Toast.msg('状态', '修改成功!');
																	jStoreReditregis.reload();
																	Ext.getCmp('updateReditregisWindow').destroy();
													},
													failure : function(form, action) {
														if(action.response.status==0){
															Ext.ux.Toast.msg('状态','连接失败，请保证服务已开启');
														}else if(action.response.status==-1){
															Ext.ux.Toast.msg('状态','连接超时，请重试!');
														}else{
															Ext.ux.Toast.msg('状态','修改失败!');		
														}
													}
												});
										}
									});
									var quButton = new Ext.Button({
										text : '取消',
										tooltip : '取消征信记录信息',
										scope : this,
										iconCls : 'cancelIcon',
										handler : function() {
											Ext.getCmp('updateReditregisWindow').destroy();
										}
									});
									var reditregisPanel = new Ext.Panel({
										layout : 'fit',
										autoHeight : true,
										width :(screen.width-180)*0.6 + 20 - 30,
										autoScroll : false ,
										tbar :[baoButton],
										items :[updateReditregisPanel]
									});
									
									var updateReditregisWindow = new Ext.Window({
										id : 'updateReditregisWindow',
										title: '编辑征信记录信息',
										layout : 'fit',
										width :(screen.width-180)*0.6 + 20,
										height :390,
										constrainHeader : true ,
										closable : true,
										resizable : true,
										plain : true,
										border : false,
										modal : true,
										autoScroll : true ,
										buttonAlign: 'right',
										iconCls : 'upIcon',
										bodyStyle:'overflowX:hidden',
								       	items :[reditregisPanel]
									}).show();			
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
						});	
					}
				}
			});
		
			var button_delete = new Ext.Button({
				text : '删除',
				tooltip : '删除征信记录信息',
				iconCls : 'deleteIcon',
				scope : this,
				handler : function() {
					var selected = gPanelReditregis.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
					}else{
						var id = selected.get('id');
						Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
							if (btn == 'yes') {
								Ext.Ajax.request({
									url : __ctxPath+'/creditFlow/customer/person/deleteRsProcessPersonCarReditregistries.do',
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										searchByCondition();
									},
									failure : function(result, action) {
										Ext.ux.Toast.msg('状态','删除失败!');
									},
									params: { id: id }
								});
							}
						});
					}
				}
			});
			var button_see = new Ext.Button({
				text : '查看',
				tooltip : '查看征信记录信息',
				iconCls : 'seeIcon',
				scope : this,
				handler : function() {
					var selected = gPanelReditregis.getSelectionModel().getSelected();
					if (null == selected) {
						Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var id = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath+'/creditFlow/customer/person/seeProcessPersonCarReditregistries.do',
						method : 'POST',
						success : function(response,request) {
							obj = Ext.util.JSON.decode(response.responseText);
								var reditregisData = obj.data;
								var seeReditregisPanel = new Ext.form.FormPanel({
									labelAlign : 'right',
									bodyStyle:'padding:10px',
									autoScroll : false ,
									autoHeight : true,
									frame : true ,
									items : [{
										layout : 'column',
										xtype:'fieldset',
										title: '征信记录',
							            collapsible: false,
							            autoHeight:true,
							            width: 600,
							            items : [{
							            	columnWidth : 1,
							            	layout : 'form',
											labelWidth : 80,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '信用报告编号',
												value : reditregisData.creditregistriesNo,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .5,
							            	layout : 'form',
							            	labelWidth : 80,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'datefield',
												fieldLabel : '查询日期',
												format : 'Y-m-d',
												value : reditregisData.queryTime,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .5,
							            	layout : 'form',
							            	labelWidth : 90,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'datefield',
												fieldLabel : '报告查询日期',
												format : 'Y-m-d',
												value : reditregisData.reportQueryTime,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            }]
									},{
										
										layout : 'column',
										xtype:'fieldset',
										title: '现住址信息',
							            collapsible: false,
							            autoHeight:true,
							            width: 600,
							            items : [{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 20,
											defaults : {anchor : anchor},
											items :[{
												//选省
								            	xtype : 'combo',
												fieldLabel : '省',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : __ctxPath+'/creditFlow/customer/person/findAddressPerson.do?parentId='+1
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.nowProvince,
												valueNotFoundText : reditregisData.nowProvinceValue,
												
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
							            	labelWidth : 20,
											defaults : {anchor : anchor},
											items :[{
												//选市
								            	xtype : 'combo',
												fieldLabel : '市',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : ''
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.nowCity,
												valueNotFoundText : reditregisData.nowCityValue,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .34,
							            	layout : 'form',
							            	labelWidth : 40,
											defaults : {anchor : anchor},
											items :[{
												//选区/县
								            	xtype : 'combo',
												fieldLabel : '区/县',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : ''
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.nowDistrict,
												valueNotFoundText : reditregisData.nowDistrictValue,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '路名',
												value : reditregisData.nowRoadName,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .25,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '路号',
												value : reditregisData.nowRoadNo,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .42,
							            	layout : 'form',
											labelWidth : 45,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '社区名',
												value : reditregisData.nowCommunity,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 45,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '门牌号',
												value : reditregisData.nowHouseNumber,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .21,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '邮编',
												value : reditregisData.nowPostcode,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .46,
							            	layout : 'form',
											labelWidth : 105,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'datefield',
												fieldLabel : '居住信息获取时间',
												format : 'Y-m-d',
												value : reditregisData.nowResideTime,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            }]
									
									},{
										
										layout : 'column',
										xtype:'fieldset',
										title: '前一住址信息',
							            collapsible: false,
							            autoHeight:true,
							            width: 600,
							            items : [{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 20,
											defaults : {anchor : anchor},
											items :[{
												//选省
								            	xtype : 'combo',
												fieldLabel : '省',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : __ctxPath+'/creditFlow/customer/person/findAddressPerson.do?parentId='+1
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.foreProvince,
												valueNotFoundText : reditregisData.foreProvinceValue,
												readOnly : true,
									            cls : 'readOnlyClass'			
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
							            	labelWidth : 20,
											defaults : {anchor : anchor},
											items :[{
												//选市
								            	xtype : 'combo',
												fieldLabel : '市',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : ''
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.foreCity,
												valueNotFoundText : reditregisData.foreCityVlaue,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .34,
							            	layout : 'form',
							            	labelWidth : 40,
											defaults : {anchor : anchor},
											items :[{
												//选区/县
								            	xtype : 'combo',
												fieldLabel : '区/县',
												mode: 'romote',
												editable : false,
												forceSelection: true,
												width : 80,
												store : new Ext.data.Store({
													proxy : new Ext.data.HttpProxy({
														url : ''
													}),
													reader : new Ext.data.JsonReader({
														root : 'topics',
														id : 'id'
													},[{name: 'id', mapping: 'id'},{
														name: 'text', mapping: 'text'}
													])
												}),
												displayField : 'text',
												valueField : 'id',
												triggerAction : 'all',
												value : reditregisData.foreDistrict,
												valueNotFoundText : reditregisData.foreDistrictValue,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '路名',
												value : reditregisData.foreRoadName,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .25,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '路号',
												value : reditregisData.foreRoadNo,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .42,
							            	layout : 'form',
											labelWidth : 45,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '社区名',
												value : reditregisData.foreCommunity,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .33,
							            	layout : 'form',
											labelWidth : 45,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '门牌号',
												value : reditregisData.foreHouseNumber,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .21,
							            	layout : 'form',
											labelWidth : 35,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'textfield',
												fieldLabel : '邮编',
												value : reditregisData.forePostcode,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            },{
							            	columnWidth : .46,
							            	layout : 'form',
											labelWidth : 105,
											defaults : {anchor : anchor},
											items :[{
												xtype : 'datefield',
												fieldLabel : '居住信息获取时间',
												format : 'Y-m-d',
												value : reditregisData.foreResideTime,
												readOnly : true,
									            cls : 'readOnlyClass'
											}]
							            }]
									},{
										layout : 'column',
										xtype:'fieldset',
										title: '信用情况',
							            collapsible: false,
							            autoHeight:true,
							            width: 600,
							            items : [{
							            	columnWidth : .5,
							            	layout : 'form',
							            	labelWidth : 95,
							            	defaults : {anchor : anchor},
							            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '贷款总笔数',
													name : 'reditregistries.creditAmount',
													value : reditregisData.creditAmount,
													readOnly : true,
									            	cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '当前贷款总额',
												name : 'reditregistries.currentTotalLoans',
												value : reditregisData.currentTotalLoans,
												readOnly : true,
									            cls:'readOnlyClass'
							            	}]
								         },{
							            	columnWidth : .5,
							            	layout : 'form',
							            	labelWidth : 165,
							            	defaults : {anchor : anchor},
							            	items : [{
								            	xtype : 'numberfield',
												fieldLabel : '银行授信额度',
												name : 'reditregistries.bankauthorizeMoney',
												value : reditregisData.bankauthorizeMoney,
												readOnly : true,
									            cls:'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '当前拖欠总额',
												name : 'reditregistries.currentDefaultLoans',
												value : reditregisData.currentDefaultLoans,
												readOnly : true,
									            cls:'readOnlyClass'
							            	}]
								          },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
									            		xtype : 'numberfield',
														fieldLabel : '历史贷款总额',
														name : 'reditregistries.historyTotalLoans',
														value : reditregisData.historyTotalLoans,
														readOnly : true,
									            		cls:'readOnlyClass'
									            	},{
									            		xtype : 'numberfield',
														fieldLabel : '信用卡总数',
														name : 'reditregistries.creditCardsNum',
														value : reditregisData.creditCardsNum,
														readOnly : true,
									            		cls:'readOnlyClass'
									            }]
								            },	
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近24个月最高连续逾期数',
													name : 'reditregistries.twoYearHighestOverdue',
													value : reditregisData.twoYearHighestOverdue,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '当前透支余额',
													name : 'reditregistries.currentOverdraftBalance',
													value : reditregisData.currentOverdraftBalance,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	}]
								            },		
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '当前信用卡数',
													name : 'reditregistries.currentCreditCardNum',
													value : reditregisData.currentCreditCardNum,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	},{
								            		xtype : 'textfield',
													fieldLabel : '社保单位',
													name : 'reditregistries.socialSecurityUnit',
													value : reditregisData.socialSecurityUnit,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	}]
								            },		
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近1个月内的查询机构数',
													name : 'reditregistries.oneMonthQueryMechanismNum',
													value : reditregisData.oneMonthQueryMechanismNum,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	},{
								            		xtype : 'numberfield',
													fieldLabel : '最近1个月内的查询次数',
													name : 'reditregistries.oneMonthQueryNum',
													value : reditregisData.oneMonthQueryNum,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	}]
								            },					
								            {
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 165,
								            	defaults : {anchor : anchor},
								            	items : [{
								            		xtype : 'numberfield',
													fieldLabel : '最近两年内的查询次数',
													name : 'reditregistries.twoYearQueryNum',
													value : reditregisData.twoYearQueryNum,
													readOnly : true,
									            	cls:'readOnlyClass'
								            	}]
								            },{
								            	columnWidth : .5,
								            	layout : 'form',
								            	labelWidth : 95,
								            	defaults : {anchor : anchor},
								            	items : [{
									            		xtype : 'textfield',
														fieldLabel : '银行信贷账户数',
														name : 'reditregistries.bankAccountNum',
														value : reditregisData.bankAccountNum,
														readOnly : true,
											            cls : 'readOnlyClass'
										            }]
								            },{
							            	columnWidth : 1,
							            	layout : 'form',
							            	labelWidth : 95,
							            	defaults : {anchor : anchor},
							            	items :[{
							            		xtype : 'textfield',
												fieldLabel : '银行贷款余额',
												value : reditregisData.bankCreditBalance,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	}]
							            },{
							            	columnWidth : .5,
							            	layout : 'form',	
							            	labelWidth : 165,
							            	defaults : {anchor : anchor},
							            	items :[{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支1-30天次数',
												value : reditregisData.overdrafttime30day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支61-90天次数',
												value : reditregisData.overdrafttime90day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支121-150天次数',
												value : reditregisData.overdrafttime150day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支180天以上次数',
												value : reditregisData.overdrafttimePass180day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月结清的账户数',
												value : reditregisData.twoYearEndAccount,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	}]
							            },{
							            	columnWidth : .5,
							            	layout : 'form',	
							            	labelWidth : 165,
							            	defaults : {anchor : anchor},
							            	items :[{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支31-60天次数',
												value : reditregisData.overdrafttime60day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支91-120天次数',
												value : reditregisData.overdrafttime120day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月透支151-180天次数',
												value : reditregisData.overdrafttime180day,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	},{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月开立的账户数',
												value : reditregisData.twoYearBeginAccount,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	}]
							            },{
							            	columnWidth : 1,
							            	layout : 'form',	
							            	labelWidth : 250,
							            	defaults : {anchor : anchor},
							            	items :[{
							            		xtype : 'numberfield',
												fieldLabel : '最近24月除结清外其他任何形态的透支账户数',
												value : reditregisData.twoYearOtherAccount,
												readOnly : true,
									            cls : 'readOnlyClass'
							            	}]
							            }]
									}]
								})
								var seeReditregisWindow = new Ext.Window({
									id : 'seeReditregisWindow',
									title: '查看征信记录信息',
									layout : 'fit',
									width :(screen.width-180)*0.6 + 20,
									height :390,
									closable : true,
									resizable : true,
									constrainHeader : true ,
									plain : true,
									border : false,
									autoScroll : true ,
									modal : true,
									iconCls : 'lookIcon',
									bodyStyle:'overflowX:hidden',
							       	items :[seeReditregisPanel]
								}).show();
						},
						failure : function(response) {					
								Ext.ux.Toast.msg('状态','操作失败，请重试');		
						},
						params: { id: id }
					});	
				}
			}
			});
	
			var cModelReditregis = new Ext.grid.ColumnModel([
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),{
					header : "征信报告编号",
					width : 130,
					sortable : true,
					dataIndex : 'creditregistriesNo'
				}, {
					header : "查询日期",
					width : 110,
					sortable : true,
					dataIndex : 'queryTime'
				}, {
					header : "报告查询日期",
					width : 110,
					sortable : true,
					dataIndex : 'reportQueryTime'
				}, {
					header : "银行信贷账户数",
					width : 120,
					sortable : true,
					dataIndex : 'bankAccountNum'
				}, {
					header : "银行信贷余额",
					width : 130,
					sortable : true,
					dataIndex : 'bankCreditBalance'
				}
			]);
	
			var pagingBar = new Ext.PagingToolbar( {
				pageSize : 20,
				store : jStoreReditregis,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
	
			var gPanelReditregis = new Ext.grid.GridPanel( {
				id : 'gPanelReditregis',
				store : jStoreReditregis,
				colModel : cModelReditregis,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				height : 400,
				autoWidth : true,
				bbar : pagingBar,
				tbar : isReadOnly?[button_see]:[button_add,button_see,button_update,button_delete]
			});
			var searchByCondition = function() {
				jStoreReditregis.load({
					params : {
						start : 0,
						limit : 20
					}
				});
			}
			var reditregisWin = new Ext.Window({
				title : '征信记录信息',
				width : (screen.width-180)*0.7 - 50,
				height: 430,
				buttonAlign : 'center',
				border : false,
				layout : 'fit',
				modal : true,
				constrainHeader : true ,
				collapsible : true, 
				items:[gPanelReditregis]
			}).show();
				
    /*	},   
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	}   
	});*/ 
}

var getObjArray1 = function(objArray){
		Ext.getCmp('nowsheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('nowshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('nowshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('nowshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('nowxian').setValue(objArray[0].text);
		Ext.getCmp('nowxianid').setValue(objArray[0].id);
	}
	var getObjArray2 = function(objArray){
		Ext.getCmp('foresheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('foreshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('foreshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('foreshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('forexian').setValue(objArray[0].text);
		Ext.getCmp('forexianid').setValue(objArray[0].id);
	}
	var getObjArrayUp1 = function(objArray){
		Ext.getCmp('up_nowsheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('up_nowshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('up_nowshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('up_nowshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('up_nowxian').setValue(objArray[0].text);
		Ext.getCmp('up_nowxianid').setValue(objArray[0].id);
	}
	var getObjArrayUp2 = function(objArray){
		Ext.getCmp('up_foresheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('up_foreshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('up_foreshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('up_foreshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('up_forexian').setValue(objArray[0].text);
		Ext.getCmp('up_forexianid').setValue(objArray[0].id);
	}