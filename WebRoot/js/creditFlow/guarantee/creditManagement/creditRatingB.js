//资信评估
var getCreditRatingStoreCfg = function(piKey){

	return {
		url : 'creditRatingList.action',
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
		    name : 'advise_sb'
		}],
		baseParams : {
			projectId : piKey
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
		width : 200,
		sortable : true,
		dataIndex : 'customerName'
	}, {
		header : "客户类型",
		width : 100,
		sortable : true,
		dataIndex : 'customerType'
		},
	{
		header : "所用评估模板",
		width : 100,
		sortable : true,
		dataIndex : 'creditTemplate'
	}, {
		header : "评估得分",
		width : 100,
		sortable : true,
		dataIndex : 'ratingScore'
	}, {
		header : "建议内容",
		width : 100,
		sortable : true,
		hidden:true,
		dataIndex : 'advise_sb'
	}, {
		header : "资信等级",
		width : 100,
		sortable : true,
		dataIndex : 'creditRegister'
	}, {
		header : "评估人",
		width : 100,
		sortable : true,
		dataIndex : 'ratingMan'
	}, {
		header : "评估日期",
		width : 100,
		sortable : true,
		dataIndex : 'ratingTime'
	}];


var addCreditRatingEB = function (piKey, enterpriseObj, jStore_creditRating, customerType) {
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
			width : 150,
			value : enterpriseObj.data.enterprisename
		}, {
			id : 'creditTemplate',
			xtype : 'combo',
			fieldLabel : '<font color=red>*</font>资信评估模板 ',
			name : 'creditTemplate',
			width : 150,
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
		}, {
			xtype : 'combo',
			fieldLabel : '财务报表文件',
			width : 150,
			name : 'creditRating.financeFile'
		}],
		buttons : [{
			text : '下一步',
			formBind : true,
			handler : function() {
				var creditTemplateId = Ext.getCmp('creditTemplate').getValue();
				var creditTemplateName = formPanel.getForm().getValues()["creditTemplate"];
				win.destroy();
				creditRatingSubEB(piKey, enterpriseObj, creditTemplateId, creditTemplateName, customerType, jStore_creditRating);
			}
		}, {
			text : '重置',
			handler : function() {
				formPanel.getForm().reset();
			}
		}]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		width : 420,
		height : 220,
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

function check(score,id_score) {
	document.getElementById(id_score).innerHTML = score;
}

var countResult = function (sub, win, jStore_creditRating) {
	var arr_id = new Array;
	var arr_score = new Array;
	var score = 0;
	
	var getCK = document.getElementsByTagName('input');
	for (var i = 0; i < getCK.length; i++) {
		whichObj = getCK[i];
		if(whichObj.type == "radio") {
			var radioName = whichObj.name;
			var obj=document.getElementsByName(radioName);
				if(obj!=null){
    				for(var j=0;j<obj.length;j++){
        				if(obj[j].checked){
            				break;
        				}
        				if(j == obj.length-1 && !obj[j].checked){
        					alert('指标评估未完成，请继续评估！');
        					return;
        				}
    				}
				}
		}
		if (whichObj.type == "radio" && whichObj.checked == true) {
			arr_id.push(whichObj.name);
			arr_score.push(Ext.getDom(whichObj.name + 'score').innerHTML);
			var v = Ext.getDom(whichObj.name + 'score').innerHTML;
			score = parseFloat(score) + parseFloat(v);
		}
	}
	Ext.getCmp('arr_id').setValue(arr_id.toString());
	Ext.getCmp('arr_score').setValue(arr_score.toString());
	Ext.getCmp('ratingScore').setValue(score);
	Ext.Ajax.request({
		url : __ctxPath+'/creditFlow/creditmanagement/getScoreGradeCreditRating.do?creditRating.ratingScore=' + score,
		method : 'POST',
		success : function(response,request) {
			obj = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp('creditRegister').setValue(obj.data.grandName);
			Ext.getCmp('advise_sb').setValue(obj.data.advise);
			if (sub == 1) {
				Ext.MessageBox.confirm('确认', '是否确认提交本次评估', function(btn) {
					if (btn == 'yes') {
						Ext.getCmp('addCreditRatingSub').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							timeout : 120000,
							success : function() {
								alert('评估成功!');
								win.destroy();
								jStore_creditRating.load();
							},
							failure : function(form, action) {
								Ext.Msg.alert('状态','提交失败!');
							}
						});
					}
				});
			}
		},
		failure : function(result, action) {
			Ext.Msg.alert('状态','服务器未响应，失败!');
		}
	});
	
};

var creditRatingSubEB = function(piKey, enterpriseObj, creditTemplateId, creditTemplateName, customerType, jStore_creditRating) {
	var fPanel_search = new Ext.form.FormPanel( {
		id : 'addCreditRatingSub',
		url : __ctxPath+'/creditFlow/creditmanagement/addSubCreditRating.do',
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 110,
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
					id : 'customerId',
					xtype : 'hidden',
					name : 'creditRating.customerId',
					value : enterpriseObj.data.customerId
				},{
					id : 'creditTemplateId',
					xtype : 'hidden',
					name : 'creditRating.creditTemplateId',
					value : creditTemplateId
				},{
					id : 'templateScore',
					xtype : 'hidden',
					name : 'creditRating.templateScore'//模版总分值
				}, {
					xtype : 'textfield',
					fieldLabel : '客户类型',
					name : 'creditRating.customerType',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : customerType
				},{
					xtype : 'textfield',
					fieldLabel : '财务报表文件',
					name : 'creditRating.financeFile',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : ''
				}]
			}, {
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'creditRating.customerName',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : enterpriseObj.data.enterprisename
				},{	
					id : 'ratingScore',
					xtype : 'textfield',
					fieldLabel : '得分情况',
					name : 'creditRating.ratingScore',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					xtype : 'textfield',
					fieldLabel : '资信评估模板',
					name : 'creditRating.creditTemplate',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : creditTemplateName
				},{
					id : 'creditRegister',
					xtype : 'textfield',
					fieldLabel : '资信等级',
					name : 'creditRating.creditRegister',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					id : 'advise_sb',
					xtype : 'textfield',
					fieldLabel : '建议内容',
					name : 'creditRating.advise_sb',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				},{
					xtype : 'textfield',
					fieldLabel : '贷款上限',
//					name : 'creditRating.',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			}]// items
		} ],
		buttons : [ {
			text : '计算得分',
			tooltip : '根据选项选择情况计算得分',
			iconCls : 'searchIcon',
			scope : this,
			handler : function() {
				countResult(0, win, jStore_creditRating);
			}
		} ,{
			text : '提交评估',
			tooltip : '重置查询条件',
			iconCls : 'select',
			scope : this,
			handler : function() {
				countResult(1, win, jStore_creditRating);
			}
		}]
	});
	
	var jStore_enterprise = new Ext.data.JsonStore( {
		url : __ctxPath+'/creditFlow/creditmanagement/addJsonCreditRating.do',
		totalProperty : totalProperty,
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'indicatorName'
		}, {
			name : 'indicatorDesc'//选项字符串。暂用
		}, {
			name : 'creater'//分值串。暂用
		},{
			name : 'lastModifier'//得分分值。暂用
		}],
		baseParams : {
			creditTemplateId : creditTemplateId
		}
	});
	
	jStore_enterprise.load();
	
	var cModel_enterprise = new Ext.grid.ColumnModel(
			[
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 50
					}),
					{
						header : "指标",
						width : 200,
						dataIndex : 'indicatorName'
					},
					{
						header : "选项",
						width : 300,
						dataIndex : 'indicatorDesc'
					},
					{
						header : "分值",
						width : 100,
						dataIndex : 'creater'
					},
					{
						header : "得分",
						width : 100,
						/*sortable : true,*/
						dataIndex : 'lastModifier'
						/*renderer : getScore*/
					}]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_enterprise,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
			//jStore_enterprise.setDefaultSort('name', 'DESC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});

	var gPanel_enterprise = new Ext.grid.GridPanel( {
		id : 'gPanel_enterprise',
		store : jStore_enterprise,
		width : Ext.getBody().getWidth()-15,
		height : Ext.getBody().getHeight()-115,
//		height : heightFun(bodyHeight)-110,
		autoScroll : true,
		colModel : cModel_enterprise,
		//autoExpandColumn : 6,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		bbar : pagingBar,
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
};

//车贷-----------------------------------------------------------------------------------------------------------------------------
var addCreditRatingCB = function (piKey, objCar, jStore_creditRating, customerType) {
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
			width : 150,
			readOnly : true,
			value : objCar.creditPersonName
		}, {
			id : 'creditTemplate',
			xtype : 'combo',
			fieldLabel : '<font color=red>*</font>资信评估模板 ',
			name : 'creditTemplate',
			width : 150,
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
		}, {
			xtype : 'combo',
			fieldLabel : '财务报表文件',
			width : 150,
			name : 'creditRating.financeFile'
		}],
		buttons : [{
			text : '下一步',
			formBind : true,
			handler : function() {
				var creditTemplateId = Ext.getCmp('creditTemplate').getValue();
				var creditTemplateName = formPanel.getForm().getValues()["creditTemplate"];
				win.destroy();
				creditRatingSubCB(piKey, objCar, creditTemplateId, creditTemplateName, customerType, jStore_creditRating);
			}
		}, {
			text : '重置',
			handler : function() {
				formPanel.getForm().reset();
			}
		}]
	});
	
	var win = new Ext.Window({
		layout : 'fit',
		width : 420,
		height : 220,
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

var creditRatingSubCB = function(piKey, objCar, creditTemplateId, creditTemplateName, customerType, jStore_creditRating) {
	var fPanel_search = new Ext.form.FormPanel( {
		id : 'addCreditRatingSub',
		url : __ctxPath+'/creditFlow/creditmanagement/addSubCreditRating.do',
		labelAlign : 'left',
		buttonAlign : 'center',
		bodyStyle : 'padding:5px;',
		height : 110,
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
					id : 'customerId',
					xtype : 'hidden',
					name : 'creditRating.customerId',
					value : objCar.creditPid
				},{
					id : 'creditTemplateId',
					xtype : 'hidden',
					name : 'creditRating.creditTemplateId',
					value : creditTemplateId
				},{
					id : 'templateScore',
					xtype : 'hidden',
					name : 'creditRating.templateScore'//模版总分值
				}, {
					xtype : 'textfield',
					fieldLabel : '客户类型',
					name : 'creditRating.customerType',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : customerType
				},{
					xtype : 'textfield',
					fieldLabel : '财务报表文件',
					name : 'creditRating.financeFile',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : ''
				}]
			}, {
				items : [ {
					xtype : 'textfield',
					fieldLabel : '客户名称',
					name : 'creditRating.customerName',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : objCar.creditPersonName
				},{	
					id : 'ratingScore',
					xtype : 'textfield',
					fieldLabel : '得分情况',
					name : 'creditRating.ratingScore',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					xtype : 'textfield',
					fieldLabel : '资信评估模板',
					name : 'creditRating.creditTemplate',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass',
					value : creditTemplateName
				},{
					id : 'creditRegister',
					xtype : 'textfield',
					fieldLabel : '资信等级',
					name : 'creditRating.creditRegister',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			},{
				items : [ {
					id : 'advise_sb',
					xtype : 'textfield',
					fieldLabel : '建议内容',
					name : 'creditRating.advise_sb',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				},{
					xtype : 'textfield',
					fieldLabel : '贷款上限',
//					name : 'creditRating.',
					anchor : '90%',
					readOnly : true,
		            cls : 'readOnlyClass'
				} ]
			}]// items
		} ],
		buttons : [ {
			text : '计算得分',
			tooltip : '根据选项选择情况计算得分',
			iconCls : 'searchIcon',
			scope : this,
			handler : function() {
				countResult(0, win, jStore_creditRating);
			}
		} ,{
			text : '提交评估',
			tooltip : '重置查询条件',
			iconCls : 'select',
			scope : this,
			handler : function() {
				countResult(1, win, jStore_creditRating);
			}
		}]
	});
	
	var jStore_enterprise = new Ext.data.JsonStore( {
		url : __ctxPath+'/creditFlow/creditmanagement/addJsonCreditRating.do',
		totalProperty : totalProperty,
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'indicatorName'
		}, {
			name : 'indicatorDesc'//选项字符串。暂用
		}, {
			name : 'creater'//分值串。暂用
		},{
			name : 'lastModifier'//得分分值。暂用
		}],
		baseParams : {
			creditTemplateId : creditTemplateId
		}
	});
	
	jStore_enterprise.load();
	
	var cModel_enterprise = new Ext.grid.ColumnModel(
			[
					new Ext.grid.RowNumberer( {
						header : '序号',
						width : 50
					}),
					{
						header : "指标",
						width : 200,
						dataIndex : 'indicatorName'
					},
					{
						header : "选项",
						width : 300,
						dataIndex : 'indicatorDesc'
					},
					{
						header : "分值",
						width : 100,
						dataIndex : 'creater'
					},
					{
						header : "得分",
						width : 100,
						/*sortable : true,*/
						dataIndex : 'lastModifier'
						/*renderer : getScore*/
					}]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_enterprise,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
			//jStore_enterprise.setDefaultSort('name', 'DESC');
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});

	var gPanel_enterprise = new Ext.grid.GridPanel( {
		id : 'gPanel_enterprise',
		store : jStore_enterprise,
		width : Ext.getBody().getWidth()-15,
		height : Ext.getBody().getHeight()-115,
//		height : heightFun(bodyHeight)-110,
		autoScroll : true,
		colModel : cModel_enterprise,
		//autoExpandColumn : 6,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		bbar : pagingBar,
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
};