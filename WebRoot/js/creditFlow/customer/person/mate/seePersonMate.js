function seePersonMate(cardNum){
	var anchor = '96.5%';
	Ext.Ajax.request({   
    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
   	 	method:'post',   
    	params:{perosnName: cardNum},
    	success: function(response, option) {
    		var obj = Ext.decode(response.responseText);
        	var personIdValue = obj.data.mateid;
        	Ext.Ajax.request({
				url : __ctxPath+'/credit/customer/person/ajaxBeforeCarCreditPersonMesQuery.do',
				method : 'POST',
				success : function(response, request) {
					var obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success==true){
						if(obj.data == null){
							Ext.ux.Toast.msg('状态', '配偶信息为空');
						}else{
							seeMatePersonMess(obj.data) ;
						}
						
					}else{
						Ext.ux.Toast.msg('状态', obj.msg);
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					personId : personIdValue
				}
			});
    	},
    	failure: function(response, option) {   
        	return true;   
        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
    	} 
	})
	
	var seeMatePersonMess = function(obj){
		var seeMatePersonPanel = new Ext.form.FormPanel({
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
	            anchor:'97%',
				items :[{
					columnWidth : .5,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : anchor},
					items :[{
						xtype : 'textfield',
						fieldLabel : '中文姓名',
						value : obj.name,
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
						value : obj.englishname,
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
							xtype : 'textfield',
							fieldLabel : '性别',
							value : obj.sexvalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '公民类型',
							value : obj.peopletypevalue,
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
							value : obj.homeland,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '民族',
							value : obj.nationalityvalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '婚姻状况',
							value : obj.marryvalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '出生日期',
							value : obj.birthday,
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
						value : obj.hukou,
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
							xtype : 'textfield',
							fieldLabel : '证件类型',
							value : obj.cardtypevalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '家庭电话',
							value : obj.telphone,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '证件号码',
							value : obj.cardnumber,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '单位电话',
							value : obj.unitphone,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '手机号码',
							value : obj.cellphone,
							readOnly : true,
		                	cls : 'readOnlyClass'
						},{
							xtype : 'textfield',
							fieldLabel : '传真号码',
							value : obj.fax,
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
							value : obj.selfemail,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							value : obj.postcode,
							readOnly : true,
		                	cls : 'readOnlyClass'
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
	            anchor:'97%',
				items :[{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items : [{
						xtype : 'textfield',
						fieldLabel : '学历',
						emptyText : '请选择学历',
						hiddenName : 'person.dgree',
						value : obj.dgreevalue,
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
							xtype : 'textfield',
							fieldLabel : '学位',
							value : obj.degreeweivalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						defaults : {anchor : '99.5%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '单位性质',
							value : obj.unitpropertiesvalue,
							readOnly : true,
		                	cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout : 'form',
						labelWidth : 70,
						defaults : {anchor : '100%'},
						items :[{
	                		xtype: 'textfield',
							fieldLabel: '是否公务员',
							value : obj.ispublicservant == true ? '是' : '否' ,
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
						xtype : 'textfield',
						fieldLabel : '职务',
						value : obj.jobvalue,
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
						value : obj.currentcompany,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '技术职称',
						value : obj.techpersonnelvalue,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					columnWidth : .66,
					layout : 'form',
					labelWidth : 150,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '工作单位注册资本(万元)',
						value : obj.registeredcapital,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					columnWidth : .40,
					layout : 'form',
					labelWidth : 95,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '本工作开始日期',
						value : obj.jobstarttime,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					columnWidth : .60,
					layout : 'form',
					labelWidth : 145,
					defaults : {anchor : '98.5%'},
					items:[{
						xtype : 'textfield',
						fieldLabel : '本工作税后月收入(万元)',
						value : obj.jobincome,
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
						value : obj.unitaddress,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				},{
					columnWidth : .33,
					layout : 'form',
					labelWidth : 65,
					defaults : {anchor : '100%'},
					items:[{
						xtype : 'textfield',
						fieldLabel : '邮政编码',
						value : obj.unitpostcode,
						readOnly : true,
						cls : 'readOnlyClass'
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
	            anchor:'97%',
				items :[{
				columnWidth : .40,
				layout : 'form',
				labelWidth : 65,
				defaults : {anchor : '100%'},
				items :[{
            		xtype : 'textfield',
					fieldLabel : '是否户主',
					value : (obj.isheadoffamily)==true ? "是" : "否",
					readOnly : true,
		            cls : 'readOnlyClass'
            		
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
						//选省
		            	xtype : 'combo',
						fieldLabel : '家庭住址',
						editable : false,
						value : obj.familyaddress,
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
						value : obj.shivalue,
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
						value : obj.xianvalue,
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
							value : obj.roadname,
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
							value : obj.roadnum,
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
							value : obj.communityname,
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
							value : obj.doorplatenum,
							readOnly : true,
							cls : 'readOnlyClass'
						}]
					},{
						columnWidth :0.39,
						layout : 'form',
						defaults : {anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '邮政编码',
							value : obj.familypostcode,
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
							xtype : 'textfield',
							fieldLabel : '占用方式',
							value : obj.employwayvalue,
							readOnly : true,
							cls : 'readOnlyClass'
						}]
					
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 80,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '现住宅形式',
							value : obj.homeshapevalue,
							readOnly : true,
							cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.33,
						layout :'form',
						labelWidth : 120,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '现住宅面积(平方米)',
							value : obj.housearea,
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
							xtype : 'textfield',
							fieldLabel : '家庭税后月收入(元)',
							value : obj.homeincome,
							readOnly : true,
							cls : 'readOnlyClass'
						}]
					},{
						columnWidth : 0.49,
						layout :'form',
						labelWidth : 130,
						defaults :{anchor : '100%'},
						items :[{
							xtype : 'textfield',
							fieldLabel : '家庭月非贷款支出(元)',
							value : obj.homeexpend,
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
						xtype : 'textfield',
						fieldLabel : '家庭月贷款支出(元)',
						value : obj.homecreditexpend,
						readOnly : true,
						cls : 'readOnlyClass'
					}]
				}]
			}]
		})
		var seeMatePersonLP = new Ext.Panel({
			layout : 'fit',
			height : 500,
			items :[seeMatePersonPanel]
		})
		var seeMatePersonWindow = new Ext.Window({
			id : 'seeMatePersonPanel',
			title : '配偶信息',
			layout : 'fit',
			width : (screen.width-180)*0.6 +100,
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
			items : [seeMatePersonLP]
		})
		seeMatePersonWindow.show();
		
	}
}
var getObjArray = function(objArray){
		Ext.getCmp('matesheng').setValue(objArray[(objArray.length)-1].text);
		Ext.getCmp('mateshengid').setValue(objArray[(objArray.length)-1].id);
		
		Ext.getCmp('mateshi').setValue(objArray[(objArray.length)-2].text);
		Ext.getCmp('mateshiid').setValue(objArray[(objArray.length)-2].id);
		
		Ext.getCmp('matexian').setValue(objArray[0].text);
		Ext.getCmp('matexianid').setValue(objArray[0].id);
		
			
}