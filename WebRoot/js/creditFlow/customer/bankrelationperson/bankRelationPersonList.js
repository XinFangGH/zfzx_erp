//var marriageDicId = 150 ;
var jStoreBankRelationPerson;
var bankRelationPersonData;
var mark5 = true;    //
var anchor = '100%';
Ext.onReady(function() {
	
	function request(paras){ 
        var url = location.href; 
        var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
        var paraObj = {} 
        for (i=0; j=paraString[i]; i++){ 
            paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
        } 

        var returnValue = paraObj[paras.toLowerCase()]; 
        if(typeof(returnValue)=="undefined"){ 
            return ""; 
        }else{ 
            return returnValue; 
        } 
	}

	var searchcondition=decodeURI(request('searchcondition'));
	
	Ext.BLANK_IMAGE_URL = __ctxPath+ '/ext3/resources/images/default/s.gif';
	Ext.form.Field.prototype.msgTarget = 'qtip';
	Ext.QuickTips.init();
	jStoreBankRelationPerson = new Ext.data.JsonStore( {
		url : __ctxPath + '/creditFlow/customer/bankRelationPerson/queryBankRelationPersonCustomerBankRelationPerson.do?isAll='+isGranted('_detail_syyhkh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		remoteSort: true,
		fields : [ {name : 'id'}, {name : 'name'}, {name : 'sexvalue'}, {name : 'duty'}, {name : 'blmtelephone'}, {name : 'marriage'}, {name : 'marriagename'}, {name : 'birthday'}, {name : 'bankname'}, {name : 'email'}, {name : 'address'}, {name : 'blmphone'},{name : 'fenbankvalue'},{name : 'bankaddress'} ]
	});
	
	if(searchcondition!=null){
		jStoreBankRelationPerson.baseParams.name = searchcondition;
	
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		
	}
	
	jStoreBankRelationPerson.load({
		params : {
			start : 0,
			limit : 15
		}
	});

	var button_add = new Ext.Button({
		text : '增加',
		hidden : isGranted('_create_yhkh')?false:true,
		tooltip : '增加银行联系人信息',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var addBankRelationPersonWin = new Ext.Window({
				id : 'addBankRelationPersonWin',
				title : '新增银行联系人信息',
				layout : 'fit',
				width : (screen.width-180)*0.7 - 100,
				iconCls : 'newIcon',
				height : 410,
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
				tbar :[new Ext.Button({text : '保存',tooltip : '保存基本信息',iconCls : 'submitIcon',hideMode:'offsets',
						handler :function(){
							formSave('addBankRelationPersonPanel', addBankRelationPersonWin ,jStoreBankRelationPerson);
						}
				})],
				autoLoad:{
        			url : __ctxPath +'/creditFlow/customer/bankRelationPerson/addBankRelationPersonCustomerBankRelationPerson.do',
        			scripts : true
        		}
			}).show();
		}
	});
	var button_update = new Ext.Button({
		text : '编辑',
		hidden : isGranted('_edit_yhkh')?false:true,
		tooltip : '编辑选中的银行联系人信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selected = gPanelBankRelationPerson.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
					method : 'POST',
					success : function(response,request) {
						obj = Ext.util.JSON.decode(response.responseText);
							bankRelationPersonData = obj.data;
							var updateBankRelationPersonWin = new Ext.Window({
								id : 'updateBankRelationPersonWin',
								title: '编辑银行联系人信息',
								layout : 'fit',
								width : (screen.width-180)*0.7 - 100,
								iconCls : 'upIcon',
								height : 410,
								constrainHeader : true ,
								autoScroll : true ,
								closable : true,
								resizable : true,
								plain : true,
								border : false,
								modal : true,
								bodyStyle:'overflowX:hidden',
								buttonAlign: 'right',
								tbar :[new Ext.Button({text : '保存',tooltip : '保存基本信息',iconCls : 'submitIcon',hideMode:'offsets',
										handler :function(){
											formSave('updateBankRelationPersonPanel', updateBankRelationPersonWin ,jStoreBankRelationPerson);
										}
								})],
						       	autoLoad:{
									url : __ctxPath +'/credit/customer/bankRelationPerson/updateBankRelationPerson.jsp',
									scripts : true
								}
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
		hidden : isGranted('_remove_yhkh')?false:true,
		tooltip : '删除选中的银行联系人信息',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = gPanelBankRelationPerson.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/customer/bankRelationPerson/deleteBankRelationPersonCustomerBankRelationPerson.do',
							method : 'POST',
							success : function() {
								Ext.ux.Toast.msg('状态', '删除成功!');
								jStoreBankRelationPerson.reload();
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
	//导出按钮
	var button_outputExcelbank = new Ext.Button({
		text : '导出到Excel',
		hidden : isGranted('_export_yhkh')?false:true,
		tooltip : '导出所有银行客户到Excel',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			window.open(__ctxPath+'/creditFlow/customer/bankRelationPerson/outputExcelCustomerBankRelationPerson.do','_blank');
		}
	});
	var button_see = new Ext.Button({
		text : '查看',
		hidden : isGranted('_detail_yhkh')?false:true,
		tooltip : '查看选中的银行联系人信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			seeBankRelationPerson();
		}
	}); 
	var fPanelBankRelationPerson = new Ext.form.FormPanel( {
		title : '银行联系人管理',
		height : 75,
		region : "north",
		bodyStyle:'padding:0px 0px 0px 0px',
		border : false,
		frame : true,
		monitorValid : true,
		layout : 'column',
		defaults : {
			layout : 'form',
			border : false,
			bodyStyle:'padding:5px 0px 0px 20px'
		},
		items : [{
			columnWidth:0.2,
			labelWidth : 100,
			bodyStyle:'padding:5px 0px 0px 0px',
			items :[{
				xtype : 'textfield',
				fieldLabel : '银行联系人姓名',
				name : 'name',
				anchor : '100%'
			}]
		},{
			columnWidth:0.2,
			labelWidth : 70,
			items :[{
				xtype : 'textfield',
				fieldLabel : '联系人职务',
				name : 'duty',
				anchor : '100%'
			}]
		},{
			columnWidth : 0.2,
			labelWidth : 70,
			items :[{
				xtype : 'textfield',
				fieldLabel : '银行名称',
				name : 'bankname',
				anchor : '100%'
			}]
		},{
			columnWidth : 0.2,
			labelWidth : 70,
			items :[{
				xtype : 'textfield',
				fieldLabel : '家庭地址',
				name : 'address',
				anchor : '100%'
			}]
		},{
			columnWidth:0.20,
			items : [{
				id : 'searchButton',
				xtype : 'button',
				text : '查询',
				tooltip : '根据查询条件过滤',
				iconCls : 'searchIcon',
				width : 20,
				formBind : true,
				scope : this,
				handler : function(){
					searchByCondition();
				}
			}]
		}]
	});  //查询面板结束
		var cModelBankRelationPerson = new Ext.grid.ColumnModel(
		[
			new Ext.grid.RowNumberer( {
				header : '序号',
				width : 35
			}),
			 {
				header : "联系人姓名",
				width : 120,
				sortable : true,
				dataIndex : 'name'
			}, {
				header : "性别",
				width : 60,
				sortable : true,
				dataIndex : 'sexvalue'
			}, {
				header : "婚姻状况",
				width : 80,
				sortable : true,
				dataIndex : 'marriagename'
			}, {
				header : "办公电话",
				width : 120,
				sortable : true,
				dataIndex : 'blmtelephone'
			}, {
				header : "手机号码",
				width : 100,
				sortable : true,
				dataIndex : 'blmphone'
			} ,{
				header : "电子邮件",
				width : 130,
				sortable : true,
				dataIndex : 'email'
			},{
				header : "银行名称",
				width : 170,
				sortable : true,
				dataIndex : 'bankname'
			},{
				header : "支行名称",
				width : 170,
				sortable : true,
				dataIndex : 'fenbankvalue'
			},{
				header : "职务",
				width : 120,
				sortable : true,
				dataIndex : 'duty'
			}]);
	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStoreBankRelationPerson,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	var gPanelBankRelationPerson = new Ext.grid.GridPanel( {
		region : 'center',
		id : 'gPanelBankRelationPerson',
		store : jStoreBankRelationPerson,
		colModel : cModelBankRelationPerson,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		autoExpandColumn : 8,
		loadMask : myMask,
		autoWidth : true,
		height : Ext.getBody().getHeight()-100,
		bbar : pagingBar,
		tbar : [button_add,button_see,button_update,button_delete,button_outputExcelbank],
		listeners : {
			'rowdblclick' : function(grid,index,e){
				seeBankRelationPerson();
			}
		}
	});
	var vPortBankRelationPerson = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [ fPanelBankRelationPerson ,gPanelBankRelationPerson]
    });
	
	var searchByCondition = function() {
		jStoreBankRelationPerson.baseParams.name = fPanelBankRelationPerson.getForm().findField('name').getValue();
		jStoreBankRelationPerson.baseParams.duty = fPanelBankRelationPerson.getForm().findField('duty').getValue();		
		jStoreBankRelationPerson.baseParams.bankname = fPanelBankRelationPerson.getForm().findField('bankname').getValue();
		jStoreBankRelationPerson.baseParams.address = fPanelBankRelationPerson.getForm().findField('address').getValue();
		jStoreBankRelationPerson.load({
			params : {
				start : 0,
				limit : 15
			}
		});
	}
	var resize = function(){
		
	}
	window.onresize = function(){
		resize(); 
	}
	parent.window.onresize = function(){
		resize();
	}
	var seeBankRelationPerson = function(){
		var selected = gPanelBankRelationPerson.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var id = selected.get('id');
			Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/customer/bankRelationPerson/seeBankRelationPersonCustomerBankRelationPerson.do',
				method : 'POST',
				success : function(response,request){
					obj = Ext.util.JSON.decode(response.responseText);
					bankRelationPersonData = obj.data;	
					var seeBankRelationPersonWin = new Ext.Window({
						id : 'seeBankRelationPersonWin',
						title: '查看银行联系人信息',
						layout : 'fit',
						width : (screen.width-180)*0.7 - 100,
						height : 410,
						iconCls : 'lookIcon',
						closable : true,
						collapsible : true,
						resizable : true,
						plain : true,
						border : false,
						autoScroll : true ,
						modal : true,
						constrainHeader : true ,
						buttonAlign: 'right',
						bodyStyle:'overflowX:hidden',
					        autoLoad:{
								url : __ctxPath +'/credit/customer/bankRelationPerson/seeBankRelationPerson.jsp',
								scripts : true
							}
					}).show();
					},
					failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
					},
					params: { id: id }
			});	
		}
	};
	var formSave = function(formPanelId ,winObj ,storeObj){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			//formBind : true,
			success : function(form ,action) {
				Ext.ux.Toast.msg('状态', '保存成功!');
					storeObj.reload();
					if(null != winObj){
						winObj.destroy();
					}
			},
			failure : function(form, action) {
				Ext.ux.Toast.msg('状态','保存失败!可能数据没有填写完整');
			}
		})
	}
});
