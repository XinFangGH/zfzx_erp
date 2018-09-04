function addPersonFamily(page){
	if(page == "add"){
			var personNameValue = Ext.get('cardnumber').dom.value;
			Ext.Ajax.request({   
		    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
		   	 	method:'post',   
		    	params:{perosnName:personNameValue},   
		    	success: function(response, option) {   
		        	var obj = Ext.decode(response.responseText);
		        	var personFamilyDate = obj.data ;
		        	showWin(personFamilyDate,false);
		    	},   
		    	failure: function(response, option) {   
		        	return true;   
		        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
		    	}   
			});  
	}else{
		var personNameValue = Ext.get('cardnumber').dom.value;
		Ext.Ajax.request({   
	    	url: __ctxPath+'/credit/customer/person/queryPersonByNameMessage.do',   
	   	 	method:'post',   
	    	params:{perosnName:personNameValue},   
	    	success: function(response, option) {   
	        	var obj = Ext.decode(response.responseText);
	        	var personFamilyDate = obj.data ;
	        	showWin(personFamilyDate,true);
	    	},   
	    	failure: function(response, option) {   
	        	return true;   
	        	Ext.ux.Toast.msg('友情提示',"异步通讯失败，请于管理员联系！");   
	    	}   
		});  
	
	}
	var showWin = function(data,isReadOnly){
		var addFamilyPerson = new Ext.form.FormPanel({
		url : __ctxPath+'/creditFlow/customer/person/addFamilyPerson.do',
		monitorValid : true,
		bodyStyle:'padding:10px',
		autoScroll : true ,
		labelAlign : 'right',
		buttonAlign : 'center',
		frame : true ,
		items :[{
			layout : 'table',
			xtype:'fieldset',
            title: '个人家庭经济情况',
            collapsible: true,
            labelWidth : 90,
            autoHeight:true,
            width: 430,
            defaults: {
       	 		bodyStyle:'padding:1.5px;',
       	 		width :430
    		},
    		layoutConfig: {
        		columns: 2
    		},
    		items: [{
    			colspan: 2,
    			html:'<hr>'
    		},{
    			colspan :2,
    			layout: 'form',
            	defaults : {anchor:'99%'},
            	width: 400,
            	items :[{
            		xtype : 'numberfield',
					fieldLabel : '总资产(万元)',
					name : 'person.grossasset',
					readOnly:isReadOnly,
					value :  data.grossasset
            	}]
    		},{
    			colspan :2,
	        	layout: 'form',
            	defaults : {anchor:'99%'},
            	width: 400,
            	items :[{
            		xtype : 'numberfield',
					fieldLabel : '家庭财产(万元)',
					name : 'person.homeasset',
					readOnly:isReadOnly,
					value : data.homeasset
            	}]
    		},{
		    	colspan: 2,
	    		layout: 'form',
	    		width: 400,
	    		defaults : {anchor:'99%'},
		    	items :[{
            		xtype : 'numberfield',
					fieldLabel : '总负债(万元)',
					readOnly:isReadOnly,
					name : 'person.grossdebt',
					value : data.grossdebt
		    	}]
		    
    		},{
		    	colspan: 2,
	    		layout: 'form',
	    		width: 400,
	    		defaults : {anchor:'99%'},
		    	items :[{
            		xtype : 'numberfield',
            		readOnly:isReadOnly,
					fieldLabel : '年总支出(万元)',
					name : 'person.yeargrossexpend',
					value :  data.yeargrossexpend
		    	}]
    		},{
    			colspan: 2,
    			html:'<hr>'
    		},{
    			rowspan :3,
    			width: 430,
				items :[{
					layout: 'table',
					layoutConfig: {
        				columns: 2
    				},
    				defaults: {
    					bodyStyle:'padding:1.5px;',
       	 				width :430
    				},
    				items :[{
    					width: 120,
    					rowspan :3,
    					html :'<span style="font-size:10pt">贷款账户开户情况</span>'
    				},{
    					width :280,
    					layout: 'form',
            			defaults : {anchor:'95%'},
            			items :[{
            				xtype : 'textfield',
							fieldLabel : '贷款开户行',
							readOnly:isReadOnly,
							name : 'person.creditbank',
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '开户行必须为中文',*/
							value : data.creditbank
            			}]
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'textfield',
							fieldLabel : '贷款开户人',
							readOnly:isReadOnly,
							name : 'person.creditperson',
							value : data.creditperson
    					}]
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'numberfield',
    						readOnly:isReadOnly,
							fieldLabel : '贷款开户账号',
							name : 'person.creditaccount',
							/*regex : /^(\d{4}[\s\-]?){4}\d{3}$/g,
							regexText : '账号格式不正确',*/
							value : data.creditaccount
    					}]
    				},{
    					colspan: 2,
    					html:'<hr>'
    				},//贷款end
    				{
    					width: 120,
    					rowspan :3,
    					html :'<span style="font-size:10pt">本人工资代发信息</span>'
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'textfield',
							fieldLabel : '工资开户行',
							readOnly:isReadOnly,
							name : 'person.wagebank',
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '开户行必须为中文',*/
							value : data.wagebank
    					}]
    				
    				},{
    					
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'textfield',
							fieldLabel : '工资开户人',
							readOnly:isReadOnly,
							name : 'person.wageperson',
							value : data.wageperson
    					}]
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'numberfield',
							fieldLabel : '工资开户账号',
							readOnly:isReadOnly,
							name : 'person.wageaccount',
							/*regex : /^(\d{4}[\s\-]?){4}\d{3}$/g,
							regexText : '账号格式不正确',*/
							value : data.wageaccount
							
    					}]
    				
    				},{
		    			colspan: 2,
		    			html:'<hr>'
    				},//工资end
    				{
    					width: 120,
    					rowspan :3,
    					html :'<span style="font-size:10pt">配偶工资代发信息</span>'
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'textfield',
							fieldLabel : '工资开户行',
							readOnly:isReadOnly,
							name : 'person.matebank',
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '开户行必须为中文',*/
							value : data.matebank
    					}]
    				},{
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'textfield',
							fieldLabel : '工资开户人',
							name : 'person.mateperson',
							readOnly:isReadOnly,
							/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
							regexText : '开户行必须为中文',*/
							value : data.mateperson
    					}]
    				},{
    					
    					width :280,
    					layout : 'form',
    					defaults : {anchor:'95%'},
    					items :[{
    						xtype : 'numberfield',
							fieldLabel : '工资开户账号',
							name : 'person.mateaccount',
							readOnly:isReadOnly,
							/*regex : /^(\d{4}[\s\-]?){4}\d{3}$/g,
							regexText : '账号格式不正确',*/
							value : data.mateaccount
    					}]
    				},{
    					colspan: 2,
		    			html:'<hr>'	
    				}]
				}]
				
    		},{
				xtype : 'hidden',
				name : 'person.id',
				value : data.id
    		}
    		]
		}]
	})
	var addFamilyPersonWindow = new Ext.Window({
		id : 'addFamilyPersonWindow',
		title : '家庭经济情况',
		layout : 'fit',
		width:500,
		height : 430,
		closable : true,
		constrainHeader : true ,
		collapsible : true,
		resizable : true,
		plain : true,
		border : false,
		autoScroll : true ,
		modal : true,
		bodyStyle:'overflowX:hidden',
		buttonAlign : 'right',
		items : [new Ext.Panel({
					layout : 'fit',
					height : 500,
					tbar :isReadOnly?null:[new Ext.Button({
						text : '保存',
						tooltip : '保存个人家庭经济情况',
						iconCls : 'submitIcon',
						formBind : true,
						scope : this,
						handler : function() {
							addFamilyPerson.getForm().submit({
								method : 'POST',
								waitTitle : '连接',
								waitMsg : '消息发送中...',
								success : function(form ,action) {
									Ext.ux.Toast.msg('状态', '保存成功!');
											Ext.getCmp('addFamilyPersonWindow').destroy();
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
					})],
					items :[addFamilyPerson]
		})]
	}).show();
	}
}