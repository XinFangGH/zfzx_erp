var personData;
var jStorePersonWin;
/*var selectInvestPerson = function(funName) {
	var anchor = '96.5%';
	//Ext.onReady(function() {
	jStorePersonWin = new Ext.data.JsonStore({
		url : __ctxPath+'/customer/listInvestPerson.do?isAll='+ isGranted('_detail_sygrtzkh'),
		totalProperty : 'totalCounts',
		root : 'result',
		fields : [{
				name : 'perId'
			}, {
				name : 'cardNumber'
			}, {
				name : 'cardType'
			}, {
				name : 'isdelete'
			}, {
				name : 'perName'
			}, {
				name : 'perSex'
			}, {
				name : 'phoneNumber'
			}, {
				name : 'postAddress'
			}, {
				name : 'postCode'
			}, {
				name : 'perBirthdayStr'
			},{
				name:'areaId'
			},{
				name:'remarks'
			},{
				name:'preEmail'
			},{
				name:'areaText'
			},{
				name:'filiation'
			},{
				name:'linkmanName'
			},{
				name:'linkmanPhone'
			},{
				name : 'customerLevel'
			},{
				name : 'personSFZZUrl'
			},{
				name : 'personSFZZId'
			},{
				name : 'personSFZZExtendName'
			},{
				name : 'personSFZFUrl'
			},{
				name : 'personSFZFId'
			},{
				name : 'personSFZFExtendName'
			}],
			remoteSort: true//服务器端排序 by chencc
		});
	
		var sm = new Ext.grid.CheckboxSelectionModel();
		var cModelPersonWin = new Ext.grid.ColumnModel([sm,
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "姓名",
					width : 80,
//					sortable : true,
					dataIndex : 'perName'
				}, {
					header : "性别",
					width : 60,
//					sortable : true,
					dataIndex : 'perSex',
					renderer : function(value){
						if(value=='312' || value==312){
							return '男';
						} else if(value=='313' || value==313){
							return '女';
						} else{
							return '';
						}
					}
				}, {
					header : "出生日期",
					width : 100,
					sortable : true,
					dataIndex : 'perBirthdayStr'
				}, {
					header : "证件类型",
					width : 120,
//					sortable : true,
					dataIndex : 'cardType',
					renderer : function(value){
						if(value=='309' || value==309){
							return '身份证';
						} else if(value=='310' || value==310){
							return '军官证';
						} else if(value=='311' || value==311){
							return '护照';
						} else if(value=='834' || value==834){
							return '临时身份证';
						} else if(value=='835' || value==835){
							return '港澳台通行证';
						}  else{
							return '其他';
						}
					}
				}, {
					header : "证件号码",
					width : 120,
//					sortable : true,
					dataIndex : 'cardNumber'
				}, {
					header : "手机号码",
					width : 120,
//					sortable : true,
					dataIndex : 'phoneNumber'

				},{
					header : "通信地址",
					width : 200,
//					sortable : true,
					dataIndex : 'postAddress'	
				}]);
		var pagingBar = new Ext.PagingToolbar({
			pageSize : 15,
			store : jStorePersonWin,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var personStore=jStorePersonWin;
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "正在加载数据中······,请稍候······"
		});
		var button_add = new Ext.Button({
			text : '增加',
			tooltip : '增加一条新的投资人员信息',
			iconCls : 'btn-add',
			scope : this,
			handler : function() {
				var investPersonPanel = gInvestPersonWin;
				new InvestPersonForm({
					isRead : false,
					investPersonPanel : investPersonPanel
				}).show();
			}
		});	
		var button_update = new Ext.Button({
			text : '编辑',
			tooltip : '编辑选中的投资人员信息',
			iconCls : 'btn-edit',
			scope : this,
			handler : function() {
				var investPersonPanel = gInvestPersonWin;
				var rows = gInvestPersonWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择一条记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else {
					record = rows[0];
					Ext.Ajax.request({
						url : __ctxPath + '/customer/getInvestPerson.do?perId=' + record.data.perId,
						method : 'POST',
						scope : this,
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var investPerson = obj.data;
				            new InvestPersonForm({
				            	investPerson : investPerson,
								isRead : false
							}).show();
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
						}
					});
				}
			}
		});
		var button_see = new Ext.Button({
			text : '查看',
			tooltip : '查看选中的投资人员信息',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				var rows = gInvestPersonWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择一条记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else {
					record = rows[0];
					Ext.Ajax.request({
						url : __ctxPath + '/customer/getInvestPerson.do?perId=' + record.data.perId,
						method : 'POST',
						scope : this,
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							var investPerson = obj.data;
				            new InvestPersonForm({
				            	investPerson : investPerson,
								isHiddenEdit : true,
								isRead : true,
								isLook : true
							}).show();
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
						}
					});
				}
			}
		});
		var button_fastadd = new Ext.Button({});	
				
		var gInvestPersonWin = new Ext.grid.GridPanel({
			id : 'gInvestPersonWin',
			store : jStorePersonWin,
			colModel : cModelPersonWin,
//			autoExpandColumn : 7,
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : true,
			bbar : pagingBar,
			tbar : [button_add,'-',button_fastadd,'-',button_update,'-',button_see,'-' ,new Ext.form.Label({text : '姓名：'}),new Ext.form.TextField({id:'personNameRefer',width:80}), new Ext.form.Label({text : '性别：'}),{id:'personSexRefer',width:80,xtype : 'csRemoteCombo',dicId : sexDicId},{text:'查找',iconCls : 'btn-search'}],
			listeners : {																																							
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					personWin.destroy();
				}
			}
		});
		Ext.getCmp('personNameRefer').on('blur',function(field){
			var value = Ext.get('personNameRefer').dom.value;
			jStorePersonWin.baseParams.name = value ;
			jStorePersonWin.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		});
		Ext.getCmp('personSexRefer').on('blur',function(field){
			var value = Ext.get('personSexRefer').dom.value;
			jStorePersonWin.baseParams.sexvalue = value ;
			jStorePersonWin.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		});
		var personWin = new Ext.Window({
			title : '投资人员列表',
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gInvestPersonWin],
			modal : true,
			buttonAlign : 'center'
		});
		//加载框开始就加载开始  by chencc
		personWin.show();
		jStorePersonWin.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		//加载框开始就加载结束  by chencc
		var searchByCondition = function() {
			jStorePersonWin.load({
						params : {
							start : 0,
							limit : 15
						}
		});
		}
		
		var callbackFun = function(selected,funName){
		personJsonObj = {
					perId : selected.get('perId'),
					cardNumber : selected.get('cardNumber'),
					cardType : selected.get('cardType'),
					isdelete : selected.get('isdelete'),
					perName : selected.get('perName'),
					perSex : selected.get('perSex'),
					phoneNumber : selected.get('phoneNumber'),
					postAddress : selected.get('postAddress'),
					postCode : selected.get('postCode'),
					perBirthday : selected.get('perBirthdayStr'),
					remarks:selected.get('remarks'),
					areaId:selected.get('areaId'),
					preEmail:selected.get('preEmail'),
					areaText:selected.get('areaText'),
					filiation: selected.get('filiation'),
					linkmanName: selected.get('linkmanName'),
					customerLevel : selected.get('customerLevel'),
					linkmanPhone: selected.get('linkmanPhone'),
					personSFZZUrl:selected.get('personSFZZUrl'),
					personSFZZId:selected.get('personSFZZId'),
					personSFZZExtendName: selected.get('personSFZZExtendName'),
					personSFZFUrl: selected.get('personSFZFUrl'),
					personSFZFId : selected.get('personSFZFId'),
					personSFZFExtendName: selected.get('personSFZFExtendName')
				}
		funName(personJsonObj);
	}
	var formSave = function(formPanelId ,winObj ,storeObj){
		var formObj = Ext.getCmp(formPanelId);
		formObj.getForm().submit({
			method : 'POST',
			waitTitle : '连接',
			waitMsg : '消息发送中...',
			formBind : true,
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
}*/
/*var selectDictionaryByPerson=function(val,funName){
	Ext.onReady(function() {
	var anchor = '96.5%';
	Ext.BLANK_IMAGE_URL = basepath()+'ext3/resources/images/default/s.gif';
	Ext.QuickTips.init();
	//Ext.form.Field.prototype.msgTarget = 'under';
	var treeLoader = new Ext.tree.TreeLoader({
		dataUrl :__ctxPath+'/creditDictionary/getDictionaryTreeWindow.do',
		baseParams : {lable : val}
	});
	var businessTree = new Ext.tree.TreePanel({
		border : false,
		iconCls : 'icon-nav',
		rootVisible : false,
		autoScroll : true,
		loader : treeLoader,
		root : new Ext.tree.AsyncTreeNode({
			id: '-1',
			text:'根'})
	});
	var ondblclicktree = function(n){
		var objArray = new Array();
		var node = n;
		for(i=0;;i++){
			objArray[i] = node;
			node = node.parentNode;
			if(node.id == '-1')
				break;
		}
		funName(objArray);
		dictionaryWindow.destroy();
	}
	businessTree.addListener('dblclick',ondblclicktree);
	var permissionmanager = new Ext.Panel({
		id : 'permissionmanager',
		height : 400,
		frame : true,
		autoScroll : true ,
		titleCollapse : true,
		expandDefaults: {
			duration:.85
			},
			collapseDefaults: {
			duration:.85
			},
			items : businessTree
	});
	var dictionaryWindow = new Ext.Window({
		width: (screen.width-180)*0.4,
		title : '数据字典',
		height : 400 ,
		collapsible : true,
		layout : 'form',
		buttonAlign : 'center',
		modal : true,
		resizable : false,
		items : [permissionmanager]
	});
	dictionaryWindow.show();
});
}*/

/*var selectInvestEnterPrise = function(funName,r) {
	var anchor = '96.5%';
	jStore_enterprise = new Ext.data.JsonStore({
		url : __ctxPath + '/credit/customer/enterprise/getInvestListEnterprise.do?isGrantedSeeAllEnterprises='+isGranted('_seeAll_qykh'),
		totalProperty : 'totalCounts',
		root : 'topics',
		fields : [{
				name : 'id'
			}, {
				name : 'enterprisename'
			}, {
				name : 'shortname'
			}, {
				name : 'organizecode'
			}, {
				name : 'telephone'
			}, {
				name : 'legalperson'
			}, {
				name : 'postcoding'
			}, {
				name : 'cciaa'
			}, {
				name : 'area'
			}],
			listeners : {
				'loadexception' : function(proxy, options, response, err) {
					var data = Ext.decode(response.responseText);
					Ext.ux.Toast.msg('提示', '数据加载失败，请保证网络畅通！');
				}
			}
		});
	
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect :false});
		var cModelFundWin = new Ext.grid.ColumnModel([sm,
				new Ext.grid.RowNumberer({
					header : '序号',
					width : 40
				}), {
					header : "企业名称",
					width : 160,
					sortable : true,
					dataIndex : 'enterprisename'
				}, {
					header : "企业简称",
					width : 120,
					sortable : true,
					dataIndex : 'shortname'
				}, {
					header : "组织机构代码",
					width : 120,
					sortable : true,
					dataIndex : 'organizecode'
				}, {
					header : "营业执照号码",
					width : 120,
					sortable : true,
					dataIndex : 'cciaa'
				}, {
					header : "法人",
					width : 55,
					fixed : true,
					sortable : true,
					dataIndex : 'legalperson'
				}, {
					header : "联系电话",
					width : 100,
					sortable : true,
					dataIndex : 'telephone'
				}]);
		var pagingBar = new Ext.PagingToolbar({
			pageSize : 15,
			store : jStore_enterprise,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var button_add = new Ext.Button({
			text : '增加',
			iconCls : 'btn-add',
			scope : this,
			handler : function() {
				addInvestEnterprise(this.jStore_enterprise);
			}
		});	
		var button_update = new Ext.Button({
			text : '编辑',
			tooltip : '编辑选中的基金信息',
			iconCls : 'btn-edit',
			scope : this,
			handler : function() {
				var investFundPanel = gInvestFundWin;
				var rows = gInvestFundWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择一条记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else {
					var enterpriseId =rows[0].data.id;
					editEnterpriseInfo(enterpriseId,this.jStore_enterprise,null);
				}
			}
		});
		var button_see = new Ext.Button({
			text : '查看',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				var rows = gInvestFundWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择一条记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else {
					enterpriseId = rows[0].data.id;
					seeInvestEnterprise(enterpriseId);
				}
			}
		});
		var gInvestInvestEnterpriseWin = new Ext.grid.GridPanel({
			id : 'gInvestInvestEnterprise',
			store : jStore_enterprise,
			colModel : cModelFundWin,
			selModel : sm,
			stripeRows : true,
			loadMask : true,
			bbar : pagingBar,
			tbar : [button_add,'-',button_update,'-',button_see,'-' ,new Ext.form.Label({text : '企业名称：'}),new Ext.form.TextField({id:'InvestEnterpriseNameRefer',width:80}),{text:'查找',iconCls : 'btn-search'}],
			listeners : {																																							
				'rowdblclick' : function(grid, index, e) {
					var selected = grid.getStore().getAt(index) ;
					callbackFun(selected,funName);
					fundWin.destroy();
				}
			}
		});
		Ext.getCmp('InvestEnterpriseNameRefer').on('blur',function(field){
			var value = Ext.get('InvestEnterpriseNameRefer').dom.value;
			jStore_enterprise.baseParams.name = value ;
			jStore_enterprise.load({
				params : {
					start : 0,
					limit : 15,
					fundResource:r
				}
			});
		});
		var fundWin = new Ext.Window({
			title : '投资企业客户列表',
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gInvestInvestEnterpriseWin],
			modal : true,
			buttonAlign : 'center'
		});
		//加载框开始就加载开始  by chencc
		fundWin.show();
		jStore_enterprise.load({
			params : {
				start : 0,
				limit : 15
			}
		});
		//加载框开始就加载结束  by chencc
		var searchByCondition = function() {
			jStore_enterprise.load({
				params : {
					start : 0,
					limit : 15
				}
			});
		}
		
		var callbackFun = function(selected,funName){
		fundJsonObj = {
			id : selected.get('id'),
			InvestEnterpriseName : selected.get('enterprisename')
		}
		funName(fundJsonObj);
	}*/
	
//企业客户
var selectInvestEnterPrise = function(funName,r,isAll) {
		var callbackFun = function(selected){
			fundJsonObj = {
				id : selected.get('id'),
				InvestEnterpriseName : selected.get('enterprisename')
			}
			funName(fundJsonObj);
		}
		var fundWin = new Ext.Window({
			id:'selectInvestEnterPriseWin',
			title :1==r?'企业客户':("Pawn"==r?'典当投资企业客户列表':'小贷投资企业客户列表'),
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [new InvestEnterpriseView({
				businessType:r,
				isAll:isAll,
				isHiddenSearchPanel:true,
				callbackFun:callbackFun
			})],
			modal : true,
			buttonAlign : 'center'
		}).show();
};

//个人客户
var selectInvestPerson=function(funName,r) {
		var callbackFun = function(selected){
			fundJsonObj = {
				id : selected.get('perId'),
				InvestEnterpriseName : selected.get('perName')
			}
			funName(fundJsonObj);
		}
		var fundWin = new Ext.Window({
			id:'selectInvestPersonWin',
			title : '投资个人客户管理',
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [new InvestPersonView({
				isHiddenSearchPanel:true,
				callbackFun:callbackFun
			})],
			modal : true,
			buttonAlign : 'center'
		}).show();
};
//个人客户
var selectInvest=function(funName,r) {
		var callbackFun = function(selected){
			funName(selected.data);
			
		}
		var fundWin = new Ext.Window({
			id:'selectInvestPersonWin',
			title : '投资个人客户管理',
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [new InvestPersonView({
				isHiddenSearchPanel:true,
				callbackFun:callbackFun
			})],
			modal : true,
			buttonAlign : 'center'
		}).show();
};
//个人投资客户
var selectInvestmentPerson = function(funName,r){
		var callbackFun = function(selected){
			funName(selected.data);
			funWin.close();
		}
		var funWin = new Ext.Window({
			id:'selectInvestmentPersonWin',
			title : '投资个人客户管理',
			border : false,
			width: (screen.width-160)*0.75,
			height : 450,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [new investmentPersonView({
				isHiddenSearchPanel:true,
				callbackFun:callbackFun
			})],
			modal : true,
			buttonAlign : 'center'
		}).show();
}