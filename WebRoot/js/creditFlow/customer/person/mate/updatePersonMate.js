function updatePersonMate(mateid){
	
	var updateMatePersonMess = function(obj){
		var anchor = '96.5%';
		var updateMatePersonPanel = new Ext.form.FormPanel({
			url : __ctxPath+'/creditFlow/customer/person/updateInfoPerson.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			items :[{
				layout : 'column',
				xtype:'fieldset',
	            title: '填写个人基本信息',
	            collapsible: true,
	            autoHeight:true,
	            width: 520,
				items :[{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : anchor},
					items :[{
						xtype : 'textfield',
						fieldLabel : '中文姓名',
						name : 'person.name',
						value : obj.name,
						allowBlank : false,
						blankText : '姓名为必填内容'
						/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
						regexText : '只能输入中文'*/
					},{
						xtype : 'hidden',
						fieldLabel : '中文姓名id',
						name : 'person.id',
						value : obj.id
					}]
				},{
					columnWidth : .5,
					layout : 'form' ,
					defaults :{anchor : anchor},
					items :[{
						xtype : 'textfield',
						fieldLabel : '拼音/英文姓名',
//						allowBlank : false,
//						blankText : '拼音/英文姓名为必填内容',
						value : obj.englishname,
						name : 'person.englishname'
						/*regex : /^[A-Za-z]+$/i,
						regexText : '只能输入英文'*/
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
							xtype : 'csRemoteCombo',
							fieldLabel : '性别',
							emptyText : '请选性别',
							hiddenName : 'person.sex',
							value : obj.sex,
							valueNotFoundText : obj.sexvalue,
							allowBlank : false,
							blankText : '性别为必填内容',
							listWidth : 80,
							dicId : sexDicId
						},{
							xtype : 'csRemoteCombo',
							fieldLabel : '公民类型',
							emptyText : '请选公民类型',
							hiddenName : 'person.peopletype',
							value : obj.peopletype,
							valueNotFoundText : obj.peopletypevalue,
							listWidth : 80,
							dicId : peopletypeDicId
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '籍贯',
							value : obj.homeland,
							name : 'person.homeland'
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '籍贯必须为中文'*/
						},{
							xtype : 'csRemoteCombo',
							fieldLabel : '民族',
							emptyText : '请选择民族',
							hiddenName : 'person.nationality',
							value : obj.nationality,
							valueNotFoundText : obj.nationalityvalue,
							listWidth : 80,
							dicId : nationalityDicId
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'csRemoteCombo',
							fieldLabel : '婚姻状况',
							emptyText : '请选婚姻状况',
							hiddenName : 'person.marry',
							value : obj.marry,
							valueNotFoundText : obj.marryvalue,
							listWidth : 80,
							dicId : marryDicId
						},{
							xtype : 'datefield',
							fieldLabel : '出生日期',
							name : 'person.birthday',
							value : obj.birthday,
							format : 'Y-m-d'
							/*regex : /^(\d{4})\-(\d{2})\-(\d{2})$/,
							regexText : '日期格式不正确'*/
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
						value : obj.hukou,
						name : 'person.hukou'
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
							xtype : 'csRemoteCombo',
							fieldLabel : '证件类型',
							emptyText : '请选证件类型',
							hiddenName : 'person.cardtype',
							value : obj.cardtype,
							valueNotFoundText : obj.cardtypevalue,
							listWidth : 80,
							allowBlank : false,
							blankText : '证件类型为必填内容',
							dicId : cardtypeDicId,
							listeners : {
								'change' : function(field,newValue,oldValue){
									if(newValur = 628){
										Ext.getCmp('cardnumber_mate').regex = /^\d{15}(\d{2}[A-Za-z0-9])?$/;
										Ext.getCmp('cardnumber_mate').regexText = '身份证号码无效';
									}else if(newValur = 629){
										Ext.getCmp('cardnumber_mate').regex ;
										Ext.getCmp('cardnumber_mate').regexText = '军官证号码无效';
									}else if(newValue = 630){
										Ext.getCmp('cardnumber_mate').regex ;
										Ext.getCmp('cardnumber_mate').regexText = '护照号码无效';
									}else {
										
									}
								}
							}
						},{
							xtype : 'textfield',
							fieldLabel : '家庭电话',
							value : obj.telphone,
							name : 'person.telphone'
							//regex : /^(\d{3,4})-(\d{7,8})/,
							//regexText : '电话号码格式不正确或者无效的号码'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							id : 'cardnumber_mate',
							xtype : 'textfield',
							fieldLabel : '证件号码',
							name : 'person.cardnumber',
							value : obj.cardnumber,
							allowBlank : false,
							blankText : '证件类型为必填内容'
						},{
							xtype : 'textfield',
							fieldLabel : '单位电话',
							value : obj.unitphone,
							name : 'person.unitphone'
							//regex : /^(\d{3,4})-(\d{7,8})/,
							//regexText : '电话号码格式不正确或者无效的号码'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '手机号码',
							value : obj.cellphone,
							name : 'person.cellphone'
							//regex : /^1[358]\d{9}$/,
							//regexText : '手机号码格式不正确或者无效的号码'
						},{
							xtype : 'numberfield',
							fieldLabel : '传真号码',
							value : obj.fax,
							name : 'person.fax'
							//regex : /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/,
							//regexText : '传真号码格式不正确'
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
							value : obj.postaddress,
							name : 'person.postaddress'
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
							name : 'person.selfemail',
							value : obj.selfemail,
							regex : /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/,
							regexText : '电子邮箱格式不正确'
							
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '邮政编码',
							name : 'person.postcode',
							value : obj.postcode,
							regex : /^[0-9]{6}$/,
							regexText : '邮政编码格式不正确'
						}]
					}]
				}]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '填写个人教育/工作信息',
	            labelWidth : 80,
	            collapsible: true,
	            autoHeight:true,
	            width: 520,
				items :[{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'csRemoteCombo',
						fieldLabel : '学历',
						emptyText : '请选择学历',
						hiddenName : 'person.dgree',
						value : obj.dgree,
						valueNotFoundText : obj.dgreevalue,
						listWidth : 80,
						dicId : dgreeDicId
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '毕业院校',
						value : obj.graduationunversity,
						name : 'person.graduationunversity'
						/*regex : /[^0-9]/,
						regexText : '输入错误'*/
						
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
							xtype : 'csRemoteCombo',
							fieldLabel : '学位',
							emptyText : '请选择学位',
							hiddenName : 'person.degreewei',
							value : obj.degreewei,
							valueNotFoundText : obj.degreeweivalue,
							listWidth : 80,
							dicId:degreeDicId
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'csRemoteCombo',
							fieldLabel : '单位性质',
							emptyText : '请选择单位性质',
							hiddenName : 'person.unitproperties',
							value : obj.unitproperties,
							valueNotFoundText : obj.unitpropertiesvalue,
							listWidth : 120,
							dicId :unitPropertiesDicId
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : '100%'},
						items :[{
	            	 		xtype: 'radiogroup',
	                		fieldLabel: '是否公务员',
	                		items: [
	                    		{boxLabel: '是', name: 'person.ispublicservant', inputValue: true},
	                    		{boxLabel: '否', name: 'person.ispublicservant', inputValue: false}
	                		]
	            
						}]
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'csRemoteCombo',
						fieldLabel : '职务',
						emptyText : '请选择职务',
						hiddenName : 'person.job',
						value : obj.job,
						valueNotFoundText : obj.jobvalue,
						listWidth : 80,
						dicId : positionDicId
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '工作单位',
						value : obj.currentcompany,
						name : 'person.currentcompany'
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'csRemoteCombo',
						fieldLabel : '技术职称',
						emptyText : '请选择技术职称',
						hiddenName : 'person.techpersonnel',
						value : obj.techpersonnel,
						valueNotFoundText : obj.techpersonnelvalue,
						listWidth : 80,
						dicId : techpersonnelDicId
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 150,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'numberfield',
						fieldLabel : '工作单位注册资本(万元)',
						value : obj.registeredcapital,
						name : 'person.registeredcapital'
					}]
				},{
					columnWidth : .40,
					layout : 'form',
					labelWidth : 95,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'datefield',
						fieldLabel : '本工作开始日期',
						name : 'person.jobstarttime',
						value : obj.jobstarttime,
						format : 'Y-m-d'
						/*regex : /^(\d{4})\-(\d{2})\-(\d{2})$/,
						regexText : '日期格式不正确'*/
					}]
				},{
					columnWidth : .60,
					layout : 'form',
					labelWidth : 145,
					defaults : {anchor : '98.5%'},
					items:[{
						xtype : 'numberfield',
						fieldLabel : '本工作税后月收入(万元)',
						value : obj.jobincome,
						name : 'person.jobincome'
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '单位地址',
						value : obj.unitaddress,
						name : 'person.unitaddress'
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items:[{
						xtype : 'numberfield',
						fieldLabel : '邮政编码',
						name : 'person.unitpostcode',
						value : obj.unitpostcode,
						regex : /^[0-9]{6}$/,
						regexText : '邮政编码格式不正确'
					}]
				}]
			
			},{
				
				layout : 'column',
				id : 'personfamily',
				xtype:'fieldset',
	            title: '填写个人家庭信息',
	            labelWidth : 80,
	            collapsible: true,
	            autoHeight:true,
	            width: 520,
				items :[{
					columnWidth : .40,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
	        	 		xtype: 'radiogroup',
	            		fieldLabel: '是否户主',
	            		items: [
	                		{boxLabel: '是', name: 'person.isheadoffamily', inputValue: true, checked: true},
	                		{boxLabel: '否', name: 'person.isheadoffamily', inputValue: false}
	            		]
					}]
				},{
					columnWidth : 1,
					layout : 'column',
					items :[{
						columnWidth : 0.36,
						layout : 'form',
						labelWidth : 65,
						defaults : {anchor : '100%'},
						items :[{
							id : 'matesheng',
							xtype : 'textfield',
							fieldLabel : '家庭住址',
							name : 'person.familyaddress',
							value : obj.familyaddress,
						 	listeners : {
								'focus':function(){
									selectDictionary('area' ,getObjArray);
								}
	               	 		}
						}/*,{
							id :'mateshengid',
							xtype : 'hidden',
							value : obj.familysheng,
							name : 'person.familysheng'
						}*/]
					}/*,{
						columnWidth : 0.28,
						layout : 'form',
						labelWidth : 20,
						defaults : {anchor : '100%'},
						items :[{
							id : 'mateshi',
							xtype : 'textfield',
							fieldLabel : '省',
							labelSeparator :'',
							name : 'familyshi',
							value : obj.shivalue,
							readOnly : true
						},{
							id : 'mateshiid',
							xtype : 'hidden',
							value : obj.familyshi,
							name : 'person.familyshi'
						}]
					},{
						columnWidth : 0.28,
						layout : 'form',
						labelWidth : 20,
						defaults : {anchor : '100%'},
						items :[{
							id : 'matexian',
							xtype : 'textfield',
							fieldLabel : '县',
							labelSeparator :'',
							name : 'familyxian',
							value : obj.xianvalue,
							readOnly : true
						},{
							id : 'matexianid',
							xtype : 'hidden',
							value : obj.familyxian,
							name : 'person.familyxian'
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
							value : obj.roadname,
							name : 'person.roadname'
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/ ,
							regexText : '只能输入中文'*/
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '路号',
							value : obj.roadnum,
							name : 'person.roadnum'
						}]
					
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '社区名',
							value : obj.communityname,
							name : 'person.communityname'
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/ ,
							regexText : '只能输入中文'*/
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
							value : obj.doorplatenum,
							name : 'person.doorplatenum'
						}]
					},{
						columnWidth :0.39,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '邮编',
							value : obj.familypostcode,
							name : 'person.familypostcode'
							/*regex : /^[0-9]{6}$/,
							regexText : '邮编格式不正确'*/
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
							xtype : 'csRemoteCombo',
							emptyText : '请选择占用方式',
							fieldLabel : '占用方式',
							hiddenName : 'person.employway',
							value : obj.employway,
							valueNotFoundText : obj.employwayvalue,
							listWidth : 100,
							dicId : employwayDicId
						}]
					
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 80,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'csRemoteCombo',
							emptyText : '请选择现住宅形式',
							fieldLabel : '现住宅形式',
							hiddenName : 'person.homeshape',
							value : obj.homeshape,
							valueNotFoundText : obj.homeshapevalue,
							listWidth : 100,
							dicId : homeshapeDicId
						}]
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 120,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '现住宅面积(平方米)',
							value : obj.housearea,
							name : 'person.housearea'
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
							value : obj.homeincome,
							name : 'person.homeincome'
						}]
					},{
						columnWidth : 0.49,
						layout :'form',
						labelWidth : 130,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '家庭月非贷款支出(元)',
							value : obj.homeexpend,
							name : 'person.homeexpend'
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
						value : obj.homecreditexpend,
						name : 'person.homecreditexpend'
					}]
				}]
			
			},{
				
				layout : 'column',
				id : 'relationperson',
				xtype:'fieldset',
	            title: '填写关系人信息(非配偶)',
	            labelWidth : 80,
	            collapsible: true,
	            autoHeight:true,
	            width: 520,
				items :[{
					columnWidth : 1,
					layout : 'column',
					items:[{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 70,
						defaults :{anchor : '100%'},
						items:[{
							xtype : 'textfield',
							fieldLabel : '关系人姓名',
							name : 'person.relationname'
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '只能输入中文'*/
						}]
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 65,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'csRemoteCombo',
							fieldLabel : '关系',
							emptyText : '请选择关系人关系',
							hiddenName : 'person.relationship',
							listWidth : 100
							//dicId : 
						}]
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 65,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'numberfield',
							fieldLabel : '手机',
							name : 'person.relationcellphone'
							//regex : /^1[358]\d{9}$/,
							//regexText : '手机号码格式不正确或者无效的号码'
						}]
					}]
				
				},{
					columnWidth : 0.99,
					layout : 'form',
					labelWidth : 120,
					defaults :{anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '固定电话(单位/家庭)',
						name : 'person.relationphone'
						//regex : /^(\d{3,4})-(\d{7,8})/,
						//regexText : '电话号码格式不正确或者无效的号码'
					}]
					
				}]
			
			}]
		});
		
		var baoButton = new Ext.Button({
			text : '保存',
			tooltip : '保存配偶基本信息',
			iconCls : 'submitIcon',
			formBind : true,
			scope : this,
			handler : function() {
					updateMatePersonPanel.getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form ,action) {
								Ext.ux.Toast.msg('状态', '编辑成功!');
										jStore_person.removeAll();
										jStore_person.reload();
										Ext.getCmp('updateMatePersonWindow').destroy();
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
		});
		
		var updateMatePersonLP = new Ext.Panel({
			layout : 'fit',
			height : 500,
			tbar :[baoButton],
			items :[updateMatePersonPanel]
		});
		var updateMatePersonWindow = new Ext.Window({
			id : 'updateMatePersonWindow',
			title : '编辑配偶信息',
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 460,
			closable : true,
			constrainHeader : true ,
			collapsible : true,
			resizable : true,
			plain : true,
			border : false,
			//autoScroll : true ,
			modal : true,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items : [updateMatePersonLP],
			listeners : {
				'beforeclose' : function(){
						if(updateMatePersonPanel.getForm().isDirty()){
							Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
								if(btn=='yes'){
									updateMatePersonPanel.getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function(form ,action) {
												Ext.ux.Toast.msg('状态', '编辑成功!');
														jStore_person.removeAll();
														jStore_person.reload();
														Ext.getCmp('updateMatePersonWindow').destroy();
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
								}else{
									updateMatePersonPanel.getForm().reset() ;
									Ext.getCmp('updateMatePersonWindow').destroy();
								}
							}) ;
							return false ;
						}
				}
			}
		});
		updateMatePersonWindow.show();
		
	}
	
	Ext.Ajax.request({
		url : __ctxPath+'/credit/customer/person/ajaxBeforeCarCreditPersonMesQuery.do',
		method : 'POST',
		success : function(response, request) {
			var obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				if(obj.data == null){
					Ext.ux.Toast.msg('状态', '配偶信息为空');
				}else{
					updateMatePersonMess(obj.data) ;
				}
				
			}else{
				Ext.ux.Toast.msg('状态', obj.msg);
			}
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		},
		params : {
			personId : mateid
		}
	});
}
var getObjArray = function(objArray){
	var familyaddressValue = objArray[(objArray.length)-1].text+'_'+objArray[(objArray.length)-2].text+'_'+objArray[(objArray.length)-3].text+'_'+objArray[0].text;
	Ext.getCmp('matesheng').setValue(familyaddressValue);
		/*Ext.getCmp('matesheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('mateshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('mateshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('mateshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('matexian').setValue(objArray[0].text);
		Ext.getCmp('matexianid').setValue(objArray[0].id);*/
		
			
}