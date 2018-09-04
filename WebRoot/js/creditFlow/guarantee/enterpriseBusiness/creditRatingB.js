//资信评估
var getCreditRatingStoreCfg = function(piKey,customerId,customerType){

	return {
		url : __ctxPath+'/creditFlow/creditmanagement/listOfJDCreditRating.do',
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'customerName'
		}, {
			name : 'customerType'
		}, {
			name : 'creditTemplate'
		},{
			name : 'ratingScore'
		},{
			name : 'templateScore'
		},{
			name : 'creditRegister'
		},{
			name : 'ratingMan'
		},{
			name : 'ratingTime'
		},{
			name:'advise_sb'
		},{
		    name:'pgtime'
		}],
		baseParams : {
			projectId : piKey,
			customerId:customerId,
			customerType:customerType
		}
	} ;
};

var expanderCreditRating = new Ext.ux.grid.RowExpander({
    tpl : new Ext.Template(
        '<p> &nbsp;&nbsp;&nbsp;&nbsp;<b>客户名称:</b> {customerName}</p>',
        '<p> &nbsp;&nbsp;&nbsp;&nbsp;<b>所用评估模板:</b> {creditTemplate}</p>',
        '<p> &nbsp;&nbsp;&nbsp;&nbsp;<b>评估得分:</b> {ratingScore}</p>'
    )
});

var creditRatingModelCfg = [expanderCreditRating,
	new Ext.grid.RowNumberer( {
		header : '序号',
		width : 40
	}),
	{
		header : "客户名称",
		width : 300,
		sortable : true,
		dataIndex : 'customerName'
	}, {
		header : "客户类型",
		width : 65,
		sortable : true,
		dataIndex : 'customerType'
		},
	{
		header : "所用评估模板",
		width : 134,
		sortable : true,
		dataIndex : 'creditTemplate'
	}, {
		header : "评估百分制总分",
		width : 100,
		sortable : true,
		dataIndex : 'ratingScore'
	}, {
		header : "资信评级",
		width : 60,
		sortable : true,
		dataIndex : 'creditRegister'
	}, {
		header : "评级含义",
		width : 100,
		sortable : true,
		hidden:true,
		dataIndex : 'advise_sb'
	}, {
		header : "评估人",
		width : 80,
		sortable : true,
		dataIndex : 'ratingMan'
	}, {
		header : "评估日期",
		width : 76,
		sortable : true,
		dataIndex : 'ratingTime'
	}, {
		header : "耗时",
		width : 76,
		sortable : true,
		dataIndex : 'pgtime'
	}];

var task=""
 function runtime (){  
	var arr = new Array();  
	arr[0]=0;  
	arr[1]=0;  
	arr[2]=':';  
	arr[3]=0;  
	arr[4]=0;  
	arr[5]=':';  
	arr[6]=0;  
	arr[7]=0;  
	  
	var str =''; 
	task={
		   
	      
	    run:function () {   
	        if(arr[7]==9) {  
	            arr[7]=0;  
	            if(arr[6]==5) {  
	                arr[6] = 0;  
	                if(arr[4]==9){  
	                    arr[4] = 0;  
	                    if(arr[3]==5){  
	                        arr[3]=0;  
	                        if(arr[1]==9){  
	                            arr[1] =0;  
	                            if(arr[0]==5){  
	                                arr[0]=0;  
	                            }else {  
	                                arr[0]=arr[0]+1;  
	                            }  
	                        }else {  
	                            arr[1]=arr[1]+1;  
	                        }  
	                    }else {  
	                        arr[3] = arr[3]+1;  
	                    }  
	                }else {  
	                    arr[4]=arr[4]+1;  
	                }  
	            }else{  
	                arr[6] = arr[6]+1;  
	            }  
	        }else {  
	        arr[7] = arr[7]+1;    
	        }  
	      
	      
	        for (var i = 0; i< arr.length;i++) {  
	            str = str + arr[i];  
	        }  
	          
	        if (true) { 
	            Ext.getCmp("pgtime_new").setText("<b>评估时间：</b>"+str,false);
	             Ext.getCmp("pgtime").setValue(str);
	            str='';  
	         }  
	          
	     },  
	     interval:1000   
	  
	
	}
 
	   Ext.TaskMgr.start(task)  



};
var addCreditRatingEB = function (piKey, customerObj, jStore_creditRating, customerType) {
	var formPanel = new Ext.FormPanel({
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:25px 25px 25px',
		labelWidth : 110,
		frame : true,
		waitMsgTarget : true,
		monitorValid : true,
		width : 500,
		items : [{
			id : 'customerName',
			xtype : 'textfield',
			fieldLabel : '<font color=red>*</font>客户名称',
			name : 'customerName',
			readOnly : true,
			width : 200,
			value : (typeof(customerObj.data.enterprisename)!='undefined')?customerObj.data.enterprisename:customerObj.data.name
		}, {
			id : 'creditTemplate',
			xtype : 'combo',
			fieldLabel : '<font color=red>*</font>资信评估模板 ',
			name : 'creditTemplate',
			width : 200,
			mode : 'romote',
			allowBlank : false,
			blankText : '必填信息',
			store : new Ext.data.JsonStore({
				url : __ctxPath+'/creditFlow/creditmanagement/rtListRatingTemplate.do',
				root : 'topics',
				fields : [{
					name : 'id'
				}, {
					name : 'templateName'
				}]
			}),
			displayField : 'templateName',
			valueField : 'id',
			triggerAction : 'all'
		}/*, {
			xtype : 'combo',
			fieldLabel : '财务报表文件',
			width : 150,
			name : 'creditRating.financeFile'
		}*/],
		buttons : [{
			text : '下一步',
			formBind : true,
			handler : function() {
				var creditTemplateId = Ext.getCmp('creditTemplate').getValue();
				
				var creditTemplateName = formPanel.getForm().getValues()["creditTemplate"];
				win.destroy();
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/creditmanagement/getCurrentUserCreditRating.do',
					method : 'POST',
					success : function(response, request) {
						var obj=Ext.util.JSON.decode(response.responseText);
						var username=obj.username
						runtime()
						creditRatingSubEB(piKey, customerObj, creditTemplateId, creditTemplateName, customerType, jStore_creditRating,username);
					}
				});
				
			}
		}, {
			text : '重置',
			handler : function() {
				Ext.getCmp("creditTemplate").setValue("");
				//formPanel.getForm().reset();
			}
		}]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		width : 420,
		height : 170,
		closable : true,
		resizable : false,
		buttonAlign : 'center',
		plain : true,
		border : false,
		modal : true,
		items : [formPanel],
		title : '新建资信评估',
		collapsible : true
	});
	win.show();
}
var getElementsByName = function(tag, name){
    var returns = document.getElementsByName(name);
    if(returns.length > 0) return returns;
    returns = new Array();
    var e = document.getElementsByTagName(tag);
    for(var i = 0; i < e.length; i++){
        if(e[i].getAttribute("name") == name){
            returns[returns.length] = e[i];
        }
    }
    return returns;
}
function isIE(){ //ie? 
   if (window.navigator.userAgent.toLowerCase().indexOf("msie")>=1) 
    return true; 
   else 
    return false; 
} 
function check(score,quanzhong,maxStore,id_score,id_maxStore,opid_score,id_quanzhong) {

	document.getElementById(id_score).innerHTML = score;
	document.getElementById(id_quanzhong).innerHTML = quanzhong;
	document.getElementById(id_maxStore).innerHTML = maxStore;
	var x=getElementsByName("label","opt"+id_score)
    if(!isIE()){ //firefox innerText define
	   HTMLElement.prototype.__defineGetter__(     "innerText", 
	    function(){
	     var anyString = "";
	     var childS = this.childNodes;
	     for(var i=0; i<childS.length; i++) {
	      if(childS[i].nodeType==1)
	       anyString += childS[i].tagName=="BR" ? '\n' : childS[i].innerText;
	      else if(childS[i].nodeType==3)
	       anyString += childS[i].nodeValue;
	     }
	     return anyString;
	    } 
	   ); 
	   HTMLElement.prototype.__defineSetter__(     "innerText", 
	    function(sText){ 
	     this.textContent=sText; 
	    } 
	   ); 
}
	//var x=document.getElementsByName("opt"+id_score)
	for(var i=0;i<x.length;i++){
		x[i].innerHTML=x[i].innerText
	}
	document.getElementById(opid_score).innerHTML ="<font color='red'><strong>"+score+"</strong></font>" ;
	
}

var countResult = function (sub, win, jStore_creditRating) {

	var arr_id = new Array;
	var arr_score = new Array;

	var score = 0;
	var maxScore=0;
	var getCK = document.getElementsByTagName("input");
   
	for (var i =0; i <getCK.length; i++) {
		
		var whichObj = getCK[i];
		
		if(whichObj.type == "radio" && whichObj.name.indexOf("jumpPath_")==-1 && whichObj.name.indexOf("customerType")==-1) {
             
			var radioName = whichObj.name;
			
			var obj=document.getElementsByName(radioName);

				if(obj!=null){
    				for(var j=0;j<obj.length;j++){
    				  
        				if(obj[j].checked){
            				break;
        				}
        				
        				if(j == obj.length-1 && !obj[j].checked){
                            var index=radioName.lastIndexOf("s");
                            var k=radioName.substring(index+1,radioName.length)
                           
        					Ext.ux.Toast.msg('操作信息', '第'+k+'个指标评估未完成，请继续评估!');
        					return;
        				}
    				}
				}
		
		}
		
		if (whichObj.type == "radio" && whichObj.checked == true && whichObj.name.indexOf("jumpPath_")==-1 && whichObj.name.indexOf("customerType")==-1) {
            var radioName=whichObj.name
			var index=radioName.lastIndexOf("s");
            radioName=radioName.substring(0,index)
			arr_id.push(radioName);
			arr_score.push(Ext.getDom(radioName + 'score').innerHTML);
			
			var v = Ext.getDom(radioName + 'score').innerHTML;
			var q= Ext.getDom(radioName + 'quanzhong').innerHTML
			var m=Ext.getDom(radioName + 'maxStore').innerHTML
			score = parseFloat(score) + parseFloat(v)*parseFloat(q);
			maxScore=parseFloat(maxScore) + parseFloat(m)*parseFloat(q);
		}
	}

	Ext.getCmp('arr_id').setValue(arr_id.toString());
	Ext.getCmp('arr_score').setValue(arr_score.toString());
	Ext.getCmp('ratingScore').setValue(Math.round((score/maxScore*100)*Math.pow(10, 2))/Math.pow(10, 2));

	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/creditmanagement/getScoreGradeCreditRating.do?creditRating.ratingScore=' + score/maxScore*100+"&creditTemplateId="+Ext.getCmp("creditTemplateId").getValue(),
		method : 'POST',
		success : function(response,request) {
			obj = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp('creditRegister').setValue(obj.data.grandname);
			Ext.getCmp('advise_sb').setValue(obj.data.hanyi);
			if (sub == 1) {
				Ext.MessageBox.confirm('确认', '是否确认提交本次评估', function(btn) {
					if (btn == 'yes') {
						Ext.getCmp('addCreditRatingSub').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							timeout : 120000,
							success : function() {
							    Ext.ux.Toast.msg('操作信息', '评估成功!');				    
								win.destroy();
								jStore_creditRating.load();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('操作信息', '提交失败!');
							}
						});
					}
				});
			}
		},
		failure : function(result, action) {
			Ext.ux.Toast.msg('操作信息', '服务器未响应，失败!');
		}
	});
	
};

var creditRatingSubEB = function(piKey, customerObj, creditTemplateId, creditTemplateName, customerType, jStore_creditRating,username) {

	var fPanel_search = new Ext.form.FormPanel( {
		id : 'addCreditRatingSub',
		url :__ctxPath+'/creditFlow/creditmanagement/addSubCreditRating.do',
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 100,
		width : Ext.getBody().getWidth(),
		frame : true,
		labelWidth : 80,
		monitorValid : true,
		items : [ {
			layout : 'column',
			border : false,
			labelSeparator : ':',
			defaults : {
				layout : 'form',
				border : false,
				columnWidth : .25
			},
			items : [ {
				items : [{
					id : 'projectId',
					xtype : 'hidden',
					name : 'creditRating.projectId',
					value : piKey
				},{
					id : 'arr_id',
					xtype : 'hidden',
					name : 'creditRating.arr_id'
				},{
					id : 'arr_score',
					xtype : 'hidden',
					name : 'creditRating.arr_score'
				},{
				    id:'ratingScore',
				    xtype:'hidden',
				    name:'creditRating.ratingScore'
				},{
					id : 'customerId',
					xtype : 'hidden',
					name : 'creditRating.customerId',
					value : customerObj.data.id
				},{
					id : 'creditTemplateId',
					xtype : 'hidden',
					name : 'creditRating.creditTemplateId',
					value : creditTemplateId
				},{
				    id:'creditRegister',
				    xtype:'hidden',
				    name:'creditRating.creditRegister'
				},{
				    id:'advise_sb',
				    xtype:'hidden',
				    name:'creditRating.advise_sb'
				},{
					id : 'templateScore',
					xtype : 'hidden',
					name : 'creditRating.templateScore'//模版总分值
				},{
				     xtype:'hidden',
				     name : 'creditRating.customerType',
				     value:customerType
				},{
				     xtype:'hidden',
				     name : 'creditRating.customerName',
				     value:(typeof(customerObj.data.enterprisename)!='undefined')?customerObj.data.enterprisename:customerObj.data.name
				},{
				    xtype:'hidden',
				    name : 'creditRating.creditTemplate',
				    value:creditTemplateName
				},{
					id:'pgtime',
				    xtype:'hidden',
				    name:'creditRating.pgtime'
				} , {
	
					html :"<b>客户类型：</b>"+ customerType
				},{
				    html:'<br>'
				},{
					html : "<b>评估人：</b>"+username
				}]
			}, {
				items : [ {

		            html:"<b>客户名称：</b>"+((typeof(customerObj.data.enterprisename)!='undefined')?customerObj.data.enterprisename:customerObj.data.name)
				},{
				    html:'<br>'
				},{	
				
					xtype : 'label',
					id:'pgtime_new',
					anchor : '90%',
					readOnly : true
				} ]
			},{
				items : [ {
			
					html : "<b>资信评估模板：</b>"+creditTemplateName
				} ]
			}]// items
		} ],
		buttons : [{
			text : '提交评估',
			tooltip : '重置查询条件',
			iconCls : 'select',
			scope : this,
			handler : function() {
				countResult(1, win, jStore_creditRating);
			}
		}]
	});
	
	var sreader = new Ext.data.JsonReader({
			    totalProperty : 'totalCounts',
				root : 'result'
			}, [ {
					name : 'id'
				},{
					name : 'indicatorId'
				}, {
					name : 'indicatorName'
				}, {
					name : 'optionName'
				},{
					name : 'score'
				},{
				    name:'defen'
				},{
				    name:'xuhao'
				}]
	       );

      jStore_enterprise = new Ext.data.GroupingStore({
			url : __ctxPath+'/creditFlow/creditmanagement/addJsonCreditRating.do',
			reader : sreader,
			baseParams : {
				creditTemplateId : creditTemplateId
			},
			groupField : 'indicatorId'
		});

	
	jStore_enterprise.load();
	var incName=function(data, cellmeta, record){

                  return record.get("indicatorName");
    }
	var cModel_enterprise = new Ext.grid.ColumnModel(
			[
					{
						header : '',
						width : 10,
						dataIndex : 'xuhao'
					},
					{
						header : '',
						width : 200,
						dataIndex : 'indicatorId',
						hidden:true,
						renderer:incName
					},
					{
						header : "选项",
						width : 300,
						dataIndex : 'optionName'
					},
					{
						header : "分值",
						width : 100,
						dataIndex : 'score'
					},
					{
						header : "得分",
						width : 100,
						sortable : true,
						dataIndex : 'defen',
						hidden:true
					}]);


	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var gPanel_enterprise = new Ext.grid.GridPanel( {
		id : 'gPanel_enterprise',
		store : jStore_enterprise,
		width : Ext.getBody().getWidth()-15,
		height : Ext.getBody().getHeight()-125,
		view : new Ext.grid.GroupingView({
			forceFit : true,
			groupTextTpl : '{text}'
		
		    }),
		autoScroll : true,
		colModel : cModel_enterprise,
		//autoExpandColumn : 6,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,

		//tbar : [button_add,button_see,button_update,button_delete],
		listeners : {
			'rowdblclick' : function(grid,index,e){
				var id = grid.getSelectionModel().getSelected().get('id');
//				seeEnterprise(id);
			}
		}
	});
	
	var panel_enterprise = new Ext.Panel( {
		autoHeight : true,
		autoScroll : true ,
		items : [fPanel_search,gPanel_enterprise]
	});
	
	var win = new Ext.Window({
		
		layout : 'fit',
		width : Ext.getBody().getWidth(),
		height : Ext.getBody().getHeight(),
		closable : true,
		resizable : false,
		buttonAlign : 'center',
		plain : true,
		border : false,
		modal : true,
		items : [panel_enterprise],
		title : '新建资信评估',
		collapsible : true
	});
	
	win.show();
	win.addListener({
		'destroy' : function() {
			Ext.TaskMgr.stop(task)
		}
	});	
};

