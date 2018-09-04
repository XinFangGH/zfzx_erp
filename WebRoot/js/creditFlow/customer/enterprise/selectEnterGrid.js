/*
var jStore_enterpriseForSelectCfg =  {
	url : __ctxPath+'/credit/customer/enterprise/ajaxQueryEnterprise.do?isGrantedSeeAllEnterprises='+isGranted('_seeAll_qykh'),
	totalProperty : 'totalProperty',
	root : 'topics',
	fields : [ {
		name : 'id'
	},{
		name : 'shortname'
	}, {
		name : 'tradetype'
	}, {
		name : 'ownership'
	}, {
		name : 'registermoney'
	}, {
		name : 'registerdate'
	},{
		name : 'organizecode'
	},{
		name : 'enterprisename'
	},{
		name : 'linkmanname'
	},{
		name : 'legalperson'
	},{
		name : 'controlpersonname'
	},{
		name : 'controlpersonid'
	},{
		name : 'legalpersonid'
	},{
		name : 'linkmampersonid'
	},{
		name : 'area'
	},{
		name : 'linkperson'
	},{
		name : 'legalperson'
	},{
		name : 'controlperson'
	},{
		name : 'linkpersonjob'
	},{
		name : 'linkpersonmobile'
	},{
		name : 'linkpersontel'
	},{
		name : 'tradetypev'
	},{
		name : 'ownershipv'
	},{
		name : 'cciaa'
	},{
		name : 'address'
	},{
		name : 'hangyetype'
	},{
		name : 'hangyetypevalue'
	
	},{
		name : 'factaddress'
	}],
//服务器端排序 by chencc
//		sortInfo :  {field: "enterprisename", direction: "DESC"}
		remoteSort: true
} ;*/
var jsonIsicRev1 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
		url : ''
	}),
	reader : new Ext.data.JsonReader({
		root : 'topics'
	}, [{ 
		name : 'code',
		mapping : 'code'
	}, {
		name : 'description',
		mapping : 'description'
	}, {
		name : 'sortorder',
		mapping : 'sortorder'
	}, {
		name : 'explanatoryNoteInclusion',
		mapping : 'explanatoryNoteInclusion'
	}, {
		name : 'explanatoryNoteExclusion',
		mapping : 'explanatoryNoteExclusion'
	}])
});
//jsonIsicRev1.load();

// 第二个下拉框
var jsonIsicRev2 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
	//	url : __ctxPath+'/creditFlow/multiLevelDic/getComboxTotalIsicRev4TextChinese.do'
	}),
	reader : new Ext.data.JsonReader({
		root : 'topics'
	}, [{
		name : 'code',
		mapping : 'code'
	}, {
		name : 'description',
		mapping : 'description'
	}, {
		name : 'sortorder',
		mapping : 'sortorder'
	}, {
		name : 'explanatoryNoteInclusion',
		mapping : 'explanatoryNoteInclusion'
	}, {
		name : 'explanatoryNoteExclusion',
		mapping : 'explanatoryNoteExclusion'
	}])
});

	// 第三个下拉框
var jsonIsicRev3 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
	//	url : __ctxPath+'/creditFlow/multiLevelDic/getComboxTotalIsicRev4TextChinese.do'
	}),
	reader : new Ext.data.JsonReader({
		root : 'topics'
	}, [{
		name : 'code',
		mapping : 'code'
	}, {
		name : 'description',
		mapping : 'description'
	}, {
		name : 'sortorder',
		mapping : 'sortorder'
	}, {
		name : 'explanatoryNoteInclusion',
		mapping : 'explanatoryNoteInclusion'
	}, {
		name : 'explanatoryNoteExclusion',
		mapping : 'explanatoryNoteExclusion'
	}])
});
// 第四个下拉框
var jsonIsicRev4 = new Ext.data.Store({
	proxy : new Ext.data.HttpProxy({
	//	url : __ctxPath+'/creditFlow/multiLevelDic/getComboxTotalIsicRev4TextChinese.do'
	}),
	reader : new Ext.data.JsonReader({
		root : 'topics'
	}, [{
		name : 'code',
		mapping : 'code'
	}, {
		name : 'description',
		mapping : 'description'
	}, {
		name : 'sortorder',
		mapping : 'sortorder'
	}, {
		name : 'explanatoryNoteInclusion',
		mapping : 'explanatoryNoteInclusion'
	}, {
		name : 'explanatoryNoteExclusion',
		mapping : 'explanatoryNoteExclusion'
	}])
});
var seeRegistermoney = function(val){
		if(val != "0" || val != null){
			return val+'万元';
		}else{
			return '' ;
		}
}
function selectEnterGridWin(funName) {
	
	var jStore_enterpriseForSelectCfg =  {
		url : __ctxPath+'/creditFlow/customer/enterprise/ajaxQueryEnterprise.do?isGrantedSeeAllEnterprises='+isGranted('_seeAll_qykh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [ {
			name : 'id'
		},{
			name : 'shortname'
		}, {
			name : 'tradetype'
		}, {
			name : 'ownership'
		}, {
			name : 'registermoney'
		}, {
			name : 'registerdate'
		},{
			name : 'organizecode'
		},{
			name : 'enterprisename'
		},{
			name : 'linkmanname'
		},{
			name : 'legalperson'
		},{
			name : 'controlpersonname'
		},{
			name : 'controlpersonid'
		},{
			name : 'legalpersonid'
		},{
			name : 'linkmampersonid'
		},{
			name : 'area'
		},{
			name : 'linkperson'
		},{
			name : 'legalperson'
		},{
			name : 'controlperson'
		},{
			name : 'linkpersonjob'
		},{
			name : 'linkpersonmobile'
		},{
			name : 'linkpersontel'
		},{
			name : 'tradetypev'
		},{
			name : 'ownershipv'
		},{
			name : 'cciaa'
		},{
			name : 'address'
		},{
			name : 'hangyetype'
		},{
			name : 'hangyetypevalue'
		
		},{
			name : 'factaddress'
		}],
	//服务器端排序 by chencc
	//		sortInfo :  {field: "enterprisename", direction: "DESC"}
			remoteSort: true
	} ;
	//alert("isGranted('_seeAll_qykh')==="+isGranted('_seeAll_qykh'))
	var pageSize = 15 ;
	var listWindowHeight = 465 ;
	var defaultLabelWidth = 110 ;//默认标签的宽度
	var defaultTextfieldWidth = 135 ;//默认文本输入域宽度
	var enterpriseJsonObj ;
	var anchor = '100%';
	var a ;
	//加载数据的jsonstore，有load（加载完成）和loadexception（加载失败）2个事件
	var jStore_enterpriseForSelect = new Ext.data.JsonStore(jStore_enterpriseForSelectCfg) ;
	jStore_enterpriseForSelect.addListener('load',function(){gPanel_enterpriseSelect.getSelectionModel().selectFirstRow() ;},this);
	jStore_enterpriseForSelect.addListener('loadexception',function(proxy, options,  response,  err){
		Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');
	},this);
	var cModel_enterprise = new Ext.grid.ColumnModel([
		new Ext.grid.RowNumberer( {
			header : '序号',
			width : 35
		}),
		{
			id : 'enterpriseName',
			header : "企业名称",
			width : 130,
			sortable : true,
			dataIndex : 'enterprisename'
		}, {
			header : "企业简称",
			width : 140,
			sortable : true,
			dataIndex : 'shortname'
		}, {
			header : "所有制性质",
			width : 130,
			sortable : true,
			dataIndex : 'ownershipv'
		}, {
			header : "注册资金",
			width : 80,
			sortable : true,
			dataIndex : 'registermoney',
			renderer : seeRegistermoney
		},{
			header : "注册时间",
			width : 80,
			sortable : true,
			dataIndex : 'registerdate'
		} ]);
	var pagingBar_Enterprise = new Ext.PagingToolbar( {
		pageSize : pageSize,
		store : jStore_enterpriseForSelect,
		autoWidth : true,
		hideBorders : true,
		displayInfo : true,
		displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
		emptyMsg : "没有符合条件的记录······"
	});
	var myMask_EnterpriseSelect = new Ext.LoadMask(Ext.getBody(), {
		msg : "加载数据中······,请稍后······"
	});
	var gPanel_enterpriseSelect = new Ext.grid.GridPanel( {
		id : 'gPanel_enterpriseSelect',
		border : false,
		store : jStore_enterpriseForSelect,
		colModel : cModel_enterprise,
		autoExpandColumn : 'enterpriseName',
		selModel : new Ext.grid.RowSelectionModel(),
		stripeRows : true,
		border : false,
		loadMask : myMask_EnterpriseSelect,
		bbar : pagingBar_Enterprise,
		tbar : [{
			text : '新增',
			iconCls : 'addIcon',
			handler : function(btn,e){
				var random=rand(1000000);
	            var id="add_enterprise"+random;
	            
				var window_add = new Ext.Window({
					id : 'window_addEnterprise',
					title: '新增企业信息',
					layout : 'fit',
					height : 460,
					width: (screen.width-180)*0.5 + 100,
					constrain : true ,
					collapsible : true, 
					iconCls : 'newIcon',
					closable : true,
					resizable : true,
					autoScroll : true ,
					bodyStyle:'overflowX:hidden',
					border : false,
					modal : true,
					items :[new enterpriseObj({winId:id})],
					tbar :[new Ext.Button({text : '保存',tooltip : '保存企业基本信息',iconCls : 'submitIcon',hideMode:'offsets',
						handler :function(){
							
							var vDates="";
							var panel_add=window_add.get(0);
						    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
					        vDates=getGridDate(edGrid);
					        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
						    gudonginfo_hidden.setValue(vDates);
						    formSavePersonObj(panel_add,window_add,jStore_enterpriseForSelect);
						}
					})]
				}).show();
			}
		},'-',{
			text : '查看',
			iconCls : 'seeIcon',
			handler : function(btn,e){
				var selected = gPanel_enterpriseSelect.getSelectionModel().getSelected();
				var len = selected.length ;
				if (len>1) {
					Ext.ux.Toast.msg('状态','只能选择一条记录') ;
				}else if(0==len){
					Ext.ux.Toast.msg('状态','请选择一条记录') ;
				}else{
					var enterpriseId = selected.get('id');
					seeEnterpriseCustomer(enterpriseId);
				}
			}
		},'-',{
			text : '编辑',
			iconCls : 'updateIcon',
			handler : function(btn,e){
				var selected = gPanel_enterpriseSelect.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
					var id = selected.get('id');
					editEnterpriseInfo(id,jStore_enterpriseForSelect);
				}
			}
		
		}],
		listeners:{
			'rowdblclick' : function(grid,rowIndex,e) {
				var selected = grid.getStore().getAt(rowIndex) ;
				callbackFun(selected,funName);
				window_EnterpriseForSelect.destroy();
			}
		}
	});
	var window_EnterpriseForSelect = new Ext.Window({
		title : '企业列表',
		border : false,
		collapsible : true ,
		width: (screen.width-180)*0.5+100,
		height : listWindowHeight-10,
		collapsible : true, 
		border : true,
		modal : true ,
		constrainHeader : true ,
		items : [gPanel_enterpriseSelect],
		layout : 'fit',
		buttonAlign : 'center',
			buttons : [{
	  		xtype:'button',
		    text:'关闭',
		    iconCls:'close',
		    handler:function(){
		    	window_EnterpriseForSelect.close();
		    }
       	}]
	});
	window_EnterpriseForSelect.show();
	jStore_enterpriseForSelect.load({
		params : {
			start : 0,
			limit : pageSize
		}
	});
	var callbackFun = function(selected,funName){
		enterpriseJsonObj = {
					id : selected.get('id'),
					shortname : selected.get('shortname'),
					hangyetype : selected.get('hangyetype'),
					ownership : selected.get('ownership'),
					registermoney : selected.get('registermoney'),
					registerdate : selected.get('registerdate'),
					enterprisename : selected.get('enterprisename'),
					controlpersonid : selected.get('controlpersonid'),
					legalpersonid : selected.get('legalpersonid'),
					linkmampersonid : selected.get('linkmampersonid'),
					area : selected.get('area'),
					legalperson : selected.get('legalperson'),
					linkperson : selected.get('linkperson'),
					controlperson : selected.get('controlperson'),
					linkpersonjob : selected.get('linkpersonjob'),
					linkpersonmobile : selected.get('linkpersonmobile'),
					linkpersontel : selected.get('linkpersontel'),
					hangyetypevalue : selected.get('hangyetypevalue'),
					cciaa : selected.get('cciaa'),
					address : selected.get('address'),
					organizecode:selected.get('organizecode'),
					factaddress : selected.get('factaddress')
				}
		funName(enterpriseJsonObj);
	}
}
var selectLegalpersonShare = function(obj){
	Ext.getCmp('selegalpersonNameShare').setValue(obj.name);
	Ext.getCmp('selegalpersonIdShare').setValue(obj.id) ;
}
var selectControlpersonShare = function(obj){
	Ext.getCmp('secontrolpersonNameShare').setValue(obj.name);
	Ext.getCmp('secontrolpersonIdShare').setValue(obj.id) ;
}
var upselectLegalpersonShare = function(obj){
	Ext.getCmp('upselegalpersonNameShare').setValue(obj.name);
	Ext.getCmp('upselegalpersonIdShare').setValue(obj.id) ;
}
var upselectControlpersonShare = function(obj){
	Ext.getCmp('upsecontrolpersonNameShare').setValue(obj.name);
	Ext.getCmp('upsecontrolpersonIdShare').setValue(obj.id) ;
}
var getEnterAreaObjArraySimple = function(objArray){
	Ext.getCmp('entermanagecitysim').setValue(objArray[(objArray.length)-1].text + "_" + objArray[(objArray.length)-2].text+"_"+objArray[0].text);
	Ext.getCmp('entermanagecitysimid').setValue(objArray[(objArray.length)-1].id+","+objArray[(objArray.length)-2].id+","+objArray[0].id);
}
var getEnterAreaObjArraySimpleUp = function(objArray){
		Ext.getCmp('upentermanagecitysim').setValue(objArray[(objArray.length)-1].text + "_" + objArray[(objArray.length)-2].text+"_"+objArray[0].text);
		Ext.getCmp('upentermanagecitysimid').setValue(objArray[(objArray.length)-1].id+","+objArray[(objArray.length)-2].id+","+objArray[0].id);
}