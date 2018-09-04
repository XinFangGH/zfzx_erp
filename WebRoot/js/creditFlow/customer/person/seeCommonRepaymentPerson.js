function seeCommonRepaymentPerson(id){
	var anchor = '100%';
	Ext.Ajax.request({
		url : __ctxPath+'/credit/customer/person/seeTogetherReplyPerson.do',
		method : 'POST',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
					var seeCommonRepaymentPersonPanel = new Ext.form.FormPanel({
						bodyStyle:'padding:5px',
						autoScroll : true ,
						labelAlign : 'right',
						buttonAlign : 'center',
						frame : true ,
						items :[{
							layout : 'column',
							xtype:'fieldset',
				            title: '查看基本信息',
				            collapsible: false,
				            autoHeight:true,
				            width: 563,
							items :[{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '中文姓名',
									value : obj.data.name
								},{
									xtype : 'textfield',
									fieldLabel : '证件类型',
									value : obj.data.cardtypevalue
								},{
									xtype : 'textfield',
									fieldLabel : '出生日期',
									value : obj.data.birthday
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '拼音/英文姓名',
									value : obj.data.englishname
								},{
									xtype : 'textfield',
									fieldLabel : '证件号码',
									value : obj.data.cardnumber
								},{
									xtype : 'textfield',
									fieldLabel : '家庭电话',
									value : obj.data.telphone
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '性别',
									value : obj.data.sexvalue
								},{
									xtype : 'textfield',
									fieldLabel : '婚姻状况',
									value : obj.data.marryvalue
								},{
									xtype : 'textfield',
									fieldLabel : '手机号码',
									value : obj.data.cellphone
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'textfield',
									fieldLabel : '电子邮箱',
									value : obj.data.selfemail
								}]
							}]
						},{
							layout : 'column',
							xtype:'fieldset',
				            title: '填写家庭基本信息',
				            collapsible: false,
				            autoHeight:true,
				            width: 563,
							items :[{
								columnWidth : 0.7,
								layout : 'form',
								labelWidth : 110,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '借款人户口所在地',
									value : obj.data.hukou
								}]
							},{
								columnWidth : 0.3,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
				        	 		xtype: 'textfield',
				            		fieldLabel: '是否户主',
									value : (obj.data.isheadoffamily == true)? '是': '否'
									
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
					            	xtype : 'textfield',
									fieldLabel : '省',
									value : obj.data.familyshi
								},{
									xtype : 'textfield',
									fieldLabel : '路名',
									value : obj.data.roadname
								},{
									xtype : 'textfield',
									fieldLabel : '门牌号',
									value : obj.data.doorplatenum
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '市',
									value : obj.data.familyshi
								},{
									xtype : 'textfield',
									fieldLabel : '路号',
									value : obj.data.roadnum
								},{
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									value : obj.data.unitpostcode
								}]
							},{
								columnWidth : 0.34,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items :[{
									//选区县
									xtype : 'textfield',
									fieldLabel : '区/县',
									value : obj.data.familyxian
								},{
									xtype : 'textfield',
									fieldLabel : '社区名',
									value : obj.data.communityname
								},{
									xtype : 'textfield',
									fieldLabel : '占用方式',
									value : obj.data.employwayvalue
								}]
							},{
								columnWidth : 0.4,
								layout : 'form',
								labelWidth : 75,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '现住宅形式',
									value : obj.data.homeshapevalue
								}]
							},{
								columnWidth : 0.6,
								layout : 'form',
								labelWidth : 160,
								defaults : {anchor : anchor},
								items:[{
									xtype : 'textfield',
									fieldLabel : '现住宅面积(平方米)',
									value : obj.data.housearea
								}]
							},{
								columnWidth : 0.5,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '家庭税后月收入(元)',
									value : obj.data.homeincome
								},{
									xtype : 'textfield',
									fieldLabel : '家庭月贷款支出(元)',
									value : obj.data.homecreditexpend
								}]
							},{
								columnWidth : 0.5,
								layout : 'form',
								labelWidth : 135,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '家庭月非贷款支出(元)',
									value : obj.data.homeexpend
								},{
									xtype : 'textfield',
									fieldLabel : '供养人口(元)',
									value : obj.data.household
								}]
							}]
						},{
							layout : 'column',
							xtype:'fieldset',
				            title: '填写教育/工作信息',
				            collapsible: false,
				            autoHeight:true,
				            width: 563,
							items :[{
								columnWidth : .34,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items : [{
									xtype : 'textfield',
									fieldLabel : '学历',
									value : obj.data.dgreevalue
								}]
							},{
								columnWidth : .66,
								layout : 'form',
								labelWidth : 120,
								defaults : {anchor : anchor},
								items :[{
									xtype : 'textfield',
									fieldLabel : '学士或以上毕业院校',
									value : obj.data.graduationunversity
								}]
							},{
								columnWidth : 1,
								labelWidth : 65,
								layout : 'column',
								items :[{
									columnWidth : 0.34,
									layout : 'form',
									defaults : {anchor : '100%'},
									items :[{
										xtype : 'textfield',
										fieldLabel : '学位',
										value : obj.data.degreeweivalue
								},{
									columnWidth : 0.33,
									layout : 'form',
									defaults : {anchor : '99.5%'},
									items :[{
										xtype : 'textfield',
										fieldLabel : '单位性质',
										value : obj.data.unitpropervalue
									}]
								},{
									columnWidth : 0.33,
									layout : 'form',
									labelWidth : 75,
									defaults : {anchor : '100%'},
									items :[{
				            	 		xtype: 'textfield',
				                		fieldLabel: '是否公务员',
										value : (obj.data.ispublicservant == true)? '是': '否'
									}]
								}]
							},{
								columnWidth : .34,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items : [{
										xtype : 'textfield',
										fieldLabel : '职务',
										value : obj.data.jobvalue
								}]
							},{
								columnWidth : .66,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '工作单位',
									value : obj.data.currentcompany
								}]
							},{
								columnWidth : .34,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '职称',
									value : obj.data.techpersonvalue
								}]
							},{
								columnWidth : .66,
								layout : 'form',
								labelWidth : 150,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '本工作开始日期',
									value : obj.data.jobstarttime
								}]
							},{
								columnWidth : .40,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '单位电话',
									value : obj.data.unitphone
								}]
							},{
								columnWidth : .60,
								layout : 'form',
								labelWidth : 145,
								defaults : {anchor : anchor},
								items:[{
									xtype : 'textfield',
									fieldLabel : '本工作税后月收入(万元)',
									value : obj.data.jobincome
								}]
							},{
								columnWidth : .66,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '单位地址',
									value : obj.data.unitaddress
								}]
							},{
								columnWidth : .34,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : anchor},
								items:[{
									xtype : 'textfield',
									fieldLabel : '邮政编码',
									value : obj.data.postcode
								}]
							}]
						}]
					}]
					});
					
					var window_see = new Ext.Window({
						id : 'window_see1',
						title: '查看共同还款人信息',
						layout : 'fit',
						constrainHeader : true ,
						width:620,
						height : 490,
						autoScroll : true ,
						x : (Ext.getBody().getWidth()-600)/2,
						y : 20,
						closable : true,
						resizable : true,
						plain : true,
						collapsible : true,
						border : false,
						modal : true,
						buttonAlign: 'right',
						minHeight: 250,	//resizable为true有效	        
						minWidth: 500,//resizable为true有效
						bodyStyle : 'padding: 5',
						items : [seeCommonRepaymentPersonPanel]
				});
					window_see.show();
				},
				failure :function(response) {					
					Ext.ux.Toast.msg('状态','操作失败，请重试');		
				},
				params: { id: id }
			})	
}