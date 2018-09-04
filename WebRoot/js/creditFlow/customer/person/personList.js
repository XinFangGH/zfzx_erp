var cardtypeDicId = 144;
var marryDicId = 150;
var peopletypeDicId = 145 ;
var dgreeDicId = 147 ;
var sexDicId = 149;
var techpersonnelDicId = 148;
var nationalityDicId = 146;
var relationtypeDicId = 218;
var jStore_person;
var personData ;
var bodyWidth = Ext.getBody().getWidth();
var bodyHeight = Ext.getBody().getHeight();
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
	
	
	
	/*Ext.BLANK_IMAGE_URL = '/creditBusiness1.0/ext/resources/images/default/s.gif';
	Ext.form.Field.prototype.msgTarget = 'under';
	Ext.QuickTips.init();*/
	var widthFun = function(bodyWidth){
		return ((bodyWidth-6)<400) ? 400 : (bodyWidth-6);
	}
	var heightFun = function(bodyHeight){
		return ((bodyHeight<400) ? 400 : (bodyHeight));	
	}
	jStore_person = new Ext.data.JsonStore( {
		url : __ctxPath+'/creditFlow/customer/person/queryListPerson.do?isAll='+isGranted('_detail_sygrkh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		remoteSort: true,
		fields : [ {
			name : 'id'
		}, {
			name : 'name'
		}, {
			name : 'sexvalue'
		}, {
			name : 'jobvalue'
		}, {
			name : 'cardtypevalue'
		}, {
			name : 'cardnumber'
		}, {
			name : 'cellphone'  
		}, {
			name : 'birthday'
		}, {
			name : 'nationalityvalue'
		}, {
			name : 'peopletypevalue'
		}, {
			name : 'dgreevalue'
		}, {
			name : 'techpersonnel'
		} ,{
			name : 'age'
		},{
			name :'marryvalue'
		},{
			name : 'telphone'
		},{
			name : 'englishname'
		}]
	});
	
	if(searchcondition!=null){
		jStore_person.baseParams.name = searchcondition;
	
		jStore_person.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		
	}
	
	jStore_person.load({
		params : {
			start : 0,
			limit : 15
		}
	});
	var ageTransition = function(val){
		if(val != ""){
			return val+'岁';
		}else{
			return '' ;
		}
	}
	var button_add = new Ext.Button({
		text : '增加',
		tooltip : '增加一条新的个人信息',
		iconCls : 'addIcon',
		hidden : isGranted('_create_grkh')?false:true,
		scope : this,
		handler : function() {
			var window_add = new Ext.Window({
				id : 'w_add',
				title : '新增个人信息',
				layout : 'fit',
				height : 460,
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
				iconCls : 'newIcon',
				width :(screen.width-180)*0.7 + 160,
				tbar :[new Ext.Button({text : '保存',tooltip : '保存基本信息',iconCls : 'submitIcon',hideMode:'offsets',
						handler :function(){
							formSavePerson('addPersonPanel', window_add ,jStore_person,"personId");
						}
				})],
				autoLoad:{
        			url : __ctxPath +'/credit/customer/person/addPerson.jsp',
        			scripts : true
        		},
        		listeners : {
					'beforeclose' : function(){
							if(Ext.getCmp('addPersonPanel').getForm().isDirty()){
								Ext.Msg.confirm('操作提示','数据被修改过,是否保存',function(btn){
									if(btn=='yes'){
										Ext.getCmp('addPersonPanel').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '编辑成功!');
															jStore_person.removeAll();
															jStore_person.reload();
															window_add.destroy();
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
										Ext.getCmp('addPersonPanel').getForm().reset() ;
										window_add.destroy() ;
									}
								}) ;
								return false ;
							}
					}
				}
			}).show();
		}
	});
	
	var button_fastadd = new Ext.Button({
		text : '快速新增',
		tooltip : '快速新增个人信息',
		iconCls : 'addIcon',
		scope : this,
		handler : function() {
			var window_add = new Ext.Window({
				id : 'w_fastadd',
				title : '快速新增个人信息',
				layout : 'fit',
				iconCls : 'newIcon',
				width :(screen.width-180)*0.7,
				height : 150,
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
							formSave('addFastPersonPanel', window_add ,jStore_person);
						}
				})],
				autoLoad:{
					url : __ctxPath +'/credit/customer/person/addFastPerson.jsp',
					scripts : true
				}
			}).show();
		}
	});
	
	var button_update = new Ext.Button({
		text : '编辑',
		hidden : isGranted('_edit_grkh')?false:true,
		tooltip : '编辑选中的个人信息',
		iconCls : 'updateIcon',
		scope : this,
		handler : function() {
			var selections = gPanel_person.getSelectionModel().getSelections();
			var len = selections.length ;
			if(len>1){
				Ext.ux.Toast.msg('状态','只能选择一条记录') ;
				return;
			}else if(0==len){
				Ext.ux.Toast.msg('状态','请选择一条记录') ;
				return;
			}
			var personId = selections[0].data.id ;
			editPersonInfo(personId);
		}
	});
	
	var button_delete = new Ext.Button({
		text : '删除',
		hidden : isGranted('_remove_grkh')?false:true,
		tooltip : '删除选中的个人信息',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			var selected = checkModel.getSelections();
			var len = selected.length ;
			var list = "" ;
			for(var j = 0 ; j < len ; j ++){
				if(j == (len-1)){
					list += selected[j].id ;
				}else
					list += selected[j].id + ",";
			}
			if (0 == len ) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				Ext.MessageBox.confirm('确认删除', '是否确认删除 选中的<font color=red>'
						+ len + '</font>条记录', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/customer/person/deletePerson.do',
							method : 'POST',
							success : function() {
								Ext.ux.Toast.msg('状态', '删除成功!');
								searchByCondition();
							},
							failure : function(result, action) {
								Ext.ux.Toast.msg('状态','删除失败!');
							},
							params: { listId: list }
						});
					}
				});
			}
		}
	});
	var button_outputExcel = new Ext.Button({
		text : '导出到Excel',
		hidden : isGranted('_export_grkh')?false:true,
		tooltip : '导出所有个人客户到Excel',
		iconCls : 'deleteIcon',
		scope : this,
		handler : function() {
			window.open(__ctxPath+'/credit/customer/person/outputExcel.do','_blank');
		}
	});
	var button_see = new Ext.Button({
		text : '查看',
		hidden : isGranted('_detail_grkh')?false:true,
		tooltip : '查看选中的人员信息',
		iconCls : 'seeIcon',
		scope : this,
		handler : function() {
			var selected = gPanel_person.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.MessageBox.alert('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				seePersonCustomer(id);
			}
		}
	}); 
	
	//查询面板
	var fPanel_search = new CS.form.FormPanel( {
		height : 75,
		labelWidth : 55,
		region : "north",
		title : '个人信息维护列表',
		bodyStyle:'padding:0px 0px 0px 0px',
		border : false,
		width : (((bodyWidth-6)<500) ? 500 : (bodyWidth-6)),
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
			labelWidth : 40,
			bodyStyle:'padding:5px 0px 0px 0px',
			items :[{
				xtype : 'textfield',
				fieldLabel : '姓名',
				name : 'name',
				anchor : '100%'
			}]
		},{
			columnWidth:0.2,
			labelWidth : 40,
			items :[{
				xtype : 'csRemoteCombo',
				fieldLabel : '职务',
				hiddenName : 'job',
				dicId : positionDicId,
				anchor : '100%'
			}]
		},{
			columnWidth : 0.2,
			labelWidth : 40,
			items :[{
				xtype : 'csRemoteCombo',
				fieldLabel : '性别',
				hiddenName : 'sex',
				dicId : sexDicId,
				anchor : '100%'
			}]
		},{
			columnWidth : 0.2,
			items :[{
				xtype : 'textfield',
				fieldLabel : '手机号码',
				name : 'cellphone',
				anchor : '100%'
			}]
		},{
			columnWidth:0.2,
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
	var checkModel = new Ext.grid.CheckboxSelectionModel();
	var cModel_person = new Ext.grid.ColumnModel(
			[checkModel,
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35
				}),
				{
					header : "姓名",
					width : 100,
					sortable : true,
					dataIndex : 'name'
				},
//					{
//					header : "英文姓名",
//					width : 100,
//					sortable : true,
//					dataIndex : 'englishname'
//				}, 
					{
					header : "性别",
					width : 50,
					sortable : true,
					dataIndex : 'sexvalue'
				},{
					header : "年龄",
					width : 50,
					sortable : true,
					dataIndex : 'age',
					renderer : ageTransition
				}, {
					header : "职务",
					width : 100,
					sortable : true,
					dataIndex : 'jobvalue'
				}, {
					header : "婚姻状况",
					width : 100,
					sortable : true,
					dataIndex : 'marryvalue'
				},{
					header : "证件类型",
					width : 100,
					sortable : true,
					dataIndex : 'cardtypevalue'
				}, {
					header : "证件号码",
					width : 120,
					sortable : true,
					dataIndex : 'cardnumber'
				}, {
					header : "手机号码",
					width : 100,
					sortable : true,
					dataIndex : 'cellphone'					
				},{
					header : "家庭电话",
					width : 100,
					sortable : true,
					dataIndex : 'telphone'					
				}, {
					header : "出生日期",
					width : 110,
					sortable : true,
					dataIndex : 'birthday'
				} ]);

	var pagingBar = new Ext.PagingToolbar( {
		pageSize : 15,
		store : jStore_person,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	
	var gPanel_person = new Ext.grid.GridPanel( {
		id : 'gPanel_person',
		region : "center",
		store : jStore_person,
		colModel : cModel_person,
		sm: checkModel,
		autoExpandColumn : 9,
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		loadMask : myMask,
		width : widthFun(bodyWidth),
		height : Ext.getBody().getHeight()-100,
		bbar : pagingBar,
		tbar : [button_add/*,button_fastadd*/,button_see,button_update,button_delete,button_outputExcel],
		listeners : {
			'rowdblclick' : function(grid,index,e){
				var id = grid.getSelectionModel().getSelected().get('id');
				seePersonCustomer(id);
			}
		}
	});
	
	var vPort_person = new Ext.Viewport({
		enableTabScroll : true,
		layout : 'border',
		items : [ fPanel_search ,gPanel_person]
    });
	
	var searchByCondition = function() {
		jStore_person.baseParams.name = fPanel_search.getForm().findField('name').getValue();
		jStore_person.baseParams.job = fPanel_search.getForm().findField('job').getValue();
		jStore_person.baseParams.sex = fPanel_search.getForm().findField('sex').getValue();
		jStore_person.baseParams.cellphone = fPanel_search.getForm().findField('cellphone').getValue();
		jStore_person.load({
			params : {
				start : 0,
				limit : 20
			}
		});
	}
	var resize = function(){
		innerPanelWidth = widthFun(bodyWidth);
		fPanel_search.setWidth(innerPanelWidth);
		gPanel_person.setWidth(innerPanelWidth);
		gPanel_person.setHeight(heightFun(bodyHeight)-140) ;
		gPanel_person.doLayout() ;
		
		vPort_person.doLayout();
	}
	
	window.onresize = function(){
		resize(); 
	}
	
	parent.window.onresize = function(){
		resize();
	}
	
	/*var isEmpty = function(formObj){
		var textfieldArray = formObj.findByType('textfield');
		var datefieldArray = formObj.findByType('datefield');
		var numberfieldArray = formObj.findByType('numberfield');
		var radiogroupArray = formObj.findByType('radiogroup');
		var comboArray = formObj.findByType('combo');
		var csRemoteComboArray = formObj.findByType('csRemoteCombo');
		for(var i = 0 ; i < textfieldArray.length ; i ++){
			var textfieldObj = textfieldArray[i] ;
			var val = textfieldObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}for(var i = 0 ; i < datefieldArray.length ; i ++){
			var datefieldObj = datefieldArray[i] ;
			var val = datefieldObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}for(var i = 0 ; i < numberfieldArray.length ; i ++){
			var numberfieldObj = numberfieldArray[i] ;
			var val = numberfieldObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}for(var i = 0 ; i < radiogroupArray.length ; i ++){
			var radiogroupObj = radiogroupArray[i] ;
			var val = radiogroupObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}for(var i = 0 ; i < comboArray.length ; i ++){
			var comboObj = comboArray[i] ;
			var val = comboObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}for(var i = 0 ; i < csRemoteComboArray.length ; i ++){
			var csRemoteComboObj = csRemoteComboArray[i] ;
			var val = csRemoteComboObj.getValue();
			if("" != val){
				Ext.ux.Toast.msg('状态','是否保存数据');
				return ;
			}
		}
	}
	var formSubmit = function(formPanelId ){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			success : function(form ,action) {
				Ext.Msg.alert('状态', '添加成功!',
				function(btn, text) {
					jStore_person.removeAll();
					jStore_person.reload();
					var childComponent = new Array();
					var textfield = new Object();
					childComponent = addPersonPanel.findByType('textfield');
					for(var i=0;i<childComponent.length;i++){
						textfield = childComponent[i];
						textfield.setDisabled(true);
					}
					Ext.getCmp('submit').setDisabled(true);
					addPersonPanel.doLayout();
				});
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
		})
	}
	var formBack = function(formPanelId){
		var formObj = Ext.getCmp(formPanelId);
		isEmpty(formObj);
	}*/
});
