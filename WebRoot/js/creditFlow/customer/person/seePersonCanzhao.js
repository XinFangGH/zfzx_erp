function seePersonCanzhaoWin(id){
	var anchor = '96.5%';
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
		method : 'POST',
		success : function(response,request){
			obj = Ext.util.JSON.decode(response.responseText);
			var personData = obj.data;	
			var borderLayoutCan = new Ext.form.FormPanel({
				autoScroll : true ,
				width :(screen.width-180)*0.7,
				labelAlign : 'right',
				buttonAlign : 'center',
				frame : true ,
				layout : 'column',
				bodyStyle:'padding:10px',
				items : [{
					columnWidth : 1,
					layout : 'fit',
				
					items :[{
						xtype:'fieldset',
			            title: '填写个人基本信息',
			            collapsible: true,
			            autoHeight:true,
			            anchor:'100%',
						layout : 'column',
						items:[{
					
							columnWidth : .5,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : anchor},
							items :[{
								id :'personName',
								xtype : 'textfield',
								fieldLabel : '中文姓名',
								value : personData.name,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .5,
							layout : 'form' ,
							defaults :{anchor : anchor},
							items :[{
								xtype : 'textfield',
								fieldLabel : '拼音/英文姓名',
								value : personData.englishname,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						
					},{
						columnWidth : 1,
						labelWidth : 65,
						layout : 'column',
						items :[{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'combo',
								fieldLabel : '性别',
								value : personData.sexvalue,
								editable : false,
								width : 80,
								readOnly : true,
				            	cls : 'readOnlyClass'
						
							},{
								xtype : 'combo',
								fieldLabel : '公民类型',
								value : personData.peopletypevalue,
								width : 80,
								readOnly : true,
				            	cls : 'readOnlyClass'
							}]
						},{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '籍贯',
								value : personData.homeland,
								readOnly : true,
				            	cls : 'readOnlyClass'
							},{
								xtype : 'combo',
								fieldLabel : '民族',
								editable : false,
								value : personData.nationalityvalue,
								width : 80,
								readOnly : true,
				           	 	cls : 'readOnlyClass'
							}]
						},{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '99.5%'},
							items :[{
								xtype : 'combo',
								fieldLabel : '婚姻状况',
								editable : false,
								value : personData.marryvalue,
								width : 80,
								readOnly : true,
				            cls : 'readOnlyClass'
							},{
								xtype : 'datefield',
								fieldLabel : '出生日期',
								value : personData.birthday,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						}]
					},{
						columnWidth : 1,
						layout : 'form',
						labelWidth : 67,
						defaults : {anchor : '98.5%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '户口所在地',
							value : personData.hukou,
							editable : false,
							readOnly : true,
				            cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 1,
						labelWidth : 65,
						layout : 'column',
						items :[{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'combo',
								fieldLabel : '证件类型',
								value : personData.cardtypevalue,
								width : 80,
								readOnly : true,
				            cls : 'readOnlyClass'
							},{
								xtype : 'textfield',
								fieldLabel : '家庭电话',
								value : personData.telphone,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								id :'cardnumber',
								xtype : 'textfield',
								fieldLabel : '证件号码',
								value : personData.cardnumber,
								readOnly : true,
				            	cls : 'readOnlyClass'
							
							},{
								xtype : 'textfield',
								fieldLabel : '单位电话',
								value : personData.unitphone,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '99.5%'},
							items :[{
								xtype : 'numberfield',
								fieldLabel : '手机号码',
								value : personData.cellphone,
								readOnly : true,
				            cls : 'readOnlyClass'
							},{
								xtype : 'numberfield',
								fieldLabel : '传真号码',
								value : personData.fax,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						}]
						
					},{
						columnWidth : 1,
						labelWidth : 65,
						layout : 'column',
						items :[{
							columnWidth : 1,
							layout : 'form',
							defaults : {anchor : '98.5%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '通信地址',
								value : personData.postaddress,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						}]
					},{
						columnWidth : 1,
						labelWidth : 65,
						layout : 'column',
						items :[{
							columnWidth : 0.66,
							layout : 'form',
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '电子邮箱',
								value : personData.selfemail,
								readOnly : true,
				            cls : 'readOnlyClass'
								
							}]
						},{
							
							columnWidth : 0.33,
							layout : 'form',
							defaults : {anchor : '99.5%'},
							items :[{
								xtype : 'numberfield',
								fieldLabel : '邮政编码',
								value : personData.postcode,
								readOnly : true,
				            cls : 'readOnlyClass'
							}]
						
							
						}]
						}]
						
					}]
				},{
					columnWidth : 1,
					layout : 'fit',
				
					items :[{
						xtype:'fieldset',
			            title: '填写个人教育/工作信息',
			            collapsible: true,
			            autoHeight:true,
			            anchor:'100%',
						layout : 'column',
						items :[{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items : [{
								xtype : 'combo',
								fieldLabel : '学历',
								width : 80,
								editable : false,
								value: personData.dgreevalue,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .66,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '毕业院校',
								value : personData.graduationunversity,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : 1,
							labelWidth : 65,
							layout : 'column',
							items :[{
								columnWidth : 0.33,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'combo',
									fieldLabel : '学位',
									width : 80,
									editable : false,
									value : personData.degreeweivalue,
									readOnly : true,
					            cls : 'readOnlyClass'
							
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								defaults : {anchor : '99.5%'},
								items :[{
									xtype : 'combo',
									fieldLabel : '单位性质',
									editable : false,
									value : personData.unitProvalue,
									width : 80,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								labelWidth : 70,
								defaults : {anchor : '100%'},
								items :[{
			                		xtype : 'textfield',
									fieldLabel : '是否公务员',
									value : (personData.ispublicservant)==true ? "是" : "否",
									readOnly : true,
					            cls : 'readOnlyClass'
			                		
								}]
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items : [{
									xtype : 'combo',
									fieldLabel : '职务',
									editable : false,
									width : 80,
									value : personData.jobvalue,
									readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .66,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '工作单位',
								readOnly : true,
								value : personData.currentcompany,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'combo',
								fieldLabel : '技术职称',
								width : 80,
								editable : false,
								value : personData.sonnelvalue,
								readOnly : true,
					            cls : 'readOnlyClass'
							
							}]
						},{
							columnWidth : .66,
							layout : 'form',
							labelWidth : 150,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'numberfield',
								fieldLabel : '工作单位注册资本(万元)',
								value : personData.registeredcapital,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .40,
							layout : 'form',
							labelWidth : 95,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'datefield',
								fieldLabel : '本工作开始日期',
								value : personData.jobstarttime,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .60,
							layout : 'form',
							labelWidth : 145,
							defaults : {anchor : '98.5%'},
							items:[{
								xtype : 'numberfield',
								fieldLabel : '本工作税后月收入(万元)',
								value : personData.jobincome,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .66,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '单位地址',
								value : personData.unitaddress,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						},{
							columnWidth : .33,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items:[{
								xtype : 'numberfield',
								fieldLabel : '邮政编码',
								value : personData.unitpostcode,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						}]
					}]

				 },{
					columnWidth : 1,
					layout : 'fit',
				
					items :[{
						layout : 'column',
						xtype:'fieldset',
			            title: '填写个人家庭信息',
			            labelWidth : 80,
			            collapsible: true,
			            autoHeight:true,
			            anchor:'100%',
						items :[{
							columnWidth : .40,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
			            		xtype : 'textfield',
								fieldLabel : '是否户主',
								value : (personData.isheadoffamily)==true ? "是" : "否",
								readOnly : true,
					            cls : 'readOnlyClass'
			            		
							}]
						},{
							columnWidth : .59,
							layout : 'form',
							labelWidth : 65,
							defaults : {anchor : '100%'},
							items :[{
								xtype : 'textfield',
								fieldLabel : '配偶姓名',
								readOnly : true,
					            value : personData.mateValue
							}]
						},{
							columnWidth : 1,
							layout : 'column',
							items :[{
								columnWidth : 1,
								layout : 'form',
								labelWidth : 65,
								defaults : {anchor : '100%'},
								items :[{
									//选省
					            	xtype : 'combo',
									fieldLabel : '家庭住址',
									editable : false,
									value : personData.familyaddress,
									width : 80,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							}/*,{
								columnWidth : 0.28,
								layout : 'form',
								labelWidth : 20,
								defaults : {anchor : '100%'},
								items :[{
									//选市
									xtype : 'combo',
									labelSeparator :'',
									editable : false,
									fieldLabel : '省',
									width : 80,
									value : personData.shivalue,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.28,
								layout : 'form',
								labelWidth : 20,
								defaults : {anchor : '100%'},
								items :[{
									//选区县
									xtype : 'combo',
									labelSeparator :'',
									editable : false,
									fieldLabel : '市',
									width : 80,
									value : personData.xianvalue,
									readOnly : true,
					                cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.08,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'label',
									labelSeparator :'',
									text : ' 区/县'
								}]
							
							
							}*/]
						},{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							items :[{
								columnWidth :0.33,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '路名',
									value : personData.roadname,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.33,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'numberfield',
									fieldLabel : '路号',
									value : personData.roadnum,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							
							},{
								columnWidth : 0.33,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '社区名',
									value : personData.communityname,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							}]
						},{
							columnWidth : 1,
							layout : 'column',
							labelWidth : 65,
							items :[{
								columnWidth :0.60,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '门牌号',
									value : personData.doorplatenum,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth :0.39,
								layout : 'form',
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'numberfield',
									fieldLabel : '邮政编码',
									readOnly : true,
									value : personData.familypostcode,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							}]
						},{
							columnWidth : 1,
							layout : 'column',
							items :[{
								columnWidth : 0.33,
								layout :'form',
								labelWidth : 65,
								defaults :{anchor : '100%'},
								items :[{
									xtype : 'combo',
									fieldLabel : '占用方式',
									editable : false,
									readOnly : true,
									width : 80,
									value : personData.employwayvalue,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							
							},{
								columnWidth : 0.33,
								layout :'form',
								labelWidth : 80,
								defaults :{anchor : '100%'},
								items :[{
									xtype : 'combo',
									fieldLabel : '现住宅形式',
									editable : false,
									width : 80,
									value : personData.homeshapevalue,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.33,
								layout :'form',
								labelWidth : 120,
								defaults :{anchor : '100%'},
								items :[{
									xtype : 'numberfield',
									fieldLabel : '现住宅面积(平方米)',
									value : personData.housearea,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							}]
						},{
							columnWidth : 1,
							layout : 'column',
							items:[{
								columnWidth : 0.50,
								layout :'form',
								labelWidth : 120,
								defaults :{anchor : '100%'},
								items:[{
									xtype : 'numberfield',
									fieldLabel : '家庭税后月收入(元)',
									value : personData.homeincome,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							},{
								columnWidth : 0.49,
								layout :'form',
								labelWidth : 130,
								defaults :{anchor : '100%'},
								items :[{
									xtype : 'numberfield',
									fieldLabel : '家庭月非贷款支出(元)',
									value : personData.homeexpend,
									readOnly : true,
					            cls : 'readOnlyClass'
								}]
							}]
						},{
							columnWidth : 0.99,
							layout : 'form',
							labelWidth : 130,
								defaults :{anchor : '100%'},
							items :[{
								xtype : 'numberfield',
								fieldLabel : '家庭月贷款支出(元)',
								value : personData.homecreditexpend,
								readOnly : true,
					            cls : 'readOnlyClass'
							}]
						}]
					}]
				}]
			});

			var windowCanzhao_see = new Ext.Window({
				id : 'windowCanzhao_see',
				title: '查看个人信息',
				layout : 'fit',
				iconCls : 'lookIcon',
				width :(screen.width-180)*0.7,
				height : 460,
				closable : true,
				collapsible : true,
				resizable : true,
				plain : true,
				border : false,
				autoScroll : true ,
				modal : true,
				buttonAlign: 'right',
				items :[borderLayoutCan]
			}).show();
		},
		failure : function(response) {					
				Ext.ux.Toast.msg('状态','操作失败，请重试');		
		},
		params: { id: id }
	});	
}

	
	