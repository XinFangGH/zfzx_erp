var personData;
var jStorePersonWin;
var selectPWName = function(funName) {
	var anchor = '96.5%';
	//Ext.onReady(function() {
	jStorePersonWin = new Ext.data.JsonStore({
		url : __ctxPath+'/creditFlow/customer/person/perQueryListPerson.do?isAll='+isGranted('_detail_sygrkh'),
		totalProperty : 'totalProperty',
		root : 'topics',
		fields : [{
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
				name : 'marryvalue'
			}, {
				name : 'cardnumber'
			}, {
				name : 'cellphone'
			}, {
				name : 'birthday'
			}, {
				name : 'mateid'
			}, {
				name : 'telphone'
			}, {
				name : 'creditbank'
			}, {
				name : 'creditperson'
			}, {
				name : 'creditaccount'
			}, {
				name : 'wagebank'
			}, {
				name : 'wageperson'
			}, {
				name : 'wageaccount'
			}, {
				name : 'cardtype'
			}, {
				name : 'job'
			}, {
				name : 'sex'
			}, {
				name : 'dgree'
			}, {
				name : 'dgreevalue'
			}, {
				name : 'currentcompany'
			}, {
				name : 'unitproperties'
			}, {
				name : 'unitpropertiesvalue'
			}, {
				name : 'techpersonnel'
			}, {
				name : 'sonnelvalue'
			}, {
				name : 'unitphone'
			}, {
				name : 'familyaddress'
			}, {
				name : 'selfemail'
			}, {
				name : 'pinyinname'
			}, {
				name : 'marry'
			}, {
				name : 'monthIncomes'
			}, {
				name : 'hukou'
			}, {
				name : 'beforeName'
			}, {name:'postaddress'},{name:'postcode'},{
				name : 'steadyWage'
			},{name : 'age'}, {
				name : 'personSFZZId'
			}, {
				name : 'personSFZZUrl'
			}, {
				name : 'personSFZFId'
			}, {
				name : 'personSFZFUrl'
			},{
			    name : 'zhusuo'
			},{
				name : 'ispublicservant'
			},{
				name : 'bankName'
			},{
				name : 'bankNum'
			},{
				name : 'unitaddress'
			},{
				name : 'bankId'
			},{
				name : 'openType'
			},{
				name : 'accountType'
			},{
				name : 'khname'
			},{
				name : 'enterpriseBankId'
			},{
				name : 'areaId'
			},{
				name : 'areaName'
			},{
				name : 'bankOutletsId'
			},{
				name : 'archivesBelonging'
			}, {
				name : 'isCheckCard'
			}
			],
			remoteSort: true//服务器端排序 by chencc
		});
	
		
		var ageTransition = function(val){
			if(val != ""){
				return val+'岁';
			}else{
				return '' ;
			}
		}
		//var sm = new Ext.grid.CheckboxSelectionModel();
		var cModelPersonWin = new Ext.grid.ColumnModel([/*sm,*/
				new Ext.grid.RowNumberer({
							header : '序号',
							width : 40
						}), {
					header : "姓名",
					sortable : true,
					dataIndex : 'name'
				}, {
					header : "性别",
					sortable : true,
					dataIndex : 'sexvalue'
				}, {
					header : "年龄",
					sortable : true,
					dataIndex : 'age',
					renderer : ageTransition
				}/*, {
					header : "出生日期",
					width : 80,
					sortable : true,
					dataIndex : 'birthday'
				},{
					header : "职务",
					width : 100,
					sortable : true,
					dataIndex : 'jobvalue'
				}*/, {
					header : "证件类型",
					sortable : true,
					dataIndex : 'cardtypevalue'
				}, {
					header : "证件号码",
					sortable : true,
					dataIndex : 'cardnumber'
				}, {
					header : "手机号码",
					sortable : true,
					dataIndex : 'cellphone'

				},{
					header : "家庭电话",
					sortable : true,
					dataIndex : 'telphone'	
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
			tooltip : '增加一条新的个人信息',
			iconCls : 'btn-add',
			scope : this,
			handler : function() {
				var randomId=rand(100000);
					var id="add_person"+randomId;
					var url=__ctxPath +'/creditFlow/customer/person/addInfoPerson.do';
					var window_add = new Ext.Window({
								title : '新增个人客户详细信息',
								height : 460,
								constrainHeader : true,
								collapsible : true,
								frame : true,
								iconCls : 'btn-add',
								border : false,
								bodyStyle : 'overflowX:hidden',
								buttonAlign : 'right',
								iconCls : 'newIcon',
								width : (screen.width - 180) * 0.7 + 160,
								resizable : true,
								layout : 'fit',
								autoScroll : false,
								constrain : true,
								closable : true,
								modal : true,
								items : [new personObj({
											personData : null,
											url:url,
											id:id
										})],
								tbar : [new Ext.Button({
											text : '保存',
											tooltip : '保存基本信息',
											iconCls : 'submitIcon',
											hideMode : 'offsets',
											handler : function() {
												var vDates = "";
												var panel_add = window_add.get(0);
												formSavePersonObj(panel_add,window_add,personStore);
											}
										})],
								listeners : {
									'beforeclose' : function(panel) {
										window_add.destroy();
									}
								}
							});
					window_add.show();
			}
		});	
		var button_fastadd = new Ext.Button({
			text : '快速新增',
			tooltip : '快速新增个人信息',
			iconCls : 'btn-add',
			scope : this,
			handler : function() {
				
				/*
				var window_add = new Ext.Window({
					id : 'w_fastadd',
					title : '快速录入个人信息',
					layout : 'fit',
					width: (screen.width-180)*0.7,
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
					iconCls : 'newIcon',
					tbar :[new Ext.Button({text : '保存',tooltip : '快速保存个人基本信息',iconCls : 'submitIcon',hideMode:'offsets',
					handler :function(){
						formSave('addFastPersonPanel', window_add ,jStorePersonWin);
					}
					})],
					autoLoad:{
						url : __ctxPath+ '/credit/customer/person/addFastPerson.jsp',
						scripts : true
					}
				}).show();
			*/}
		});	
		var button_update = new Ext.Button({
			text : '编辑',
			tooltip : '编辑选中的个人信息',
			iconCls : 'btn-edit',
			scope : this,
			handler : function() {
				var rows = gPanelPersonWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else{
					var isCheckCard=rows[0].data.isCheckCard
					Ext.Ajax.request({
						url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
						method : 'POST',
						scope : this,
						success : function(response,request){
							obj = Ext.util.JSON.decode(response.responseText);
							var personData = obj.data;	
								var randomId=rand(100000);
								var id="update_person"+randomId;
								if(isCheckCard){
									personData.isCardcodeReadOnly=true;
								}else{
									personData.isCardcodeReadOnly=false;
								}
					            var url= __ctxPath	+ '/creditFlow/customer/person/updateInfoPerson.do';
					            var window_update = new Ext.Window({
								title : '编辑个人客户详细信息',
								height : 460,
								constrainHeader : true,
								collapsible : true,
								frame : true,
								iconCls : 'btn-edit',
								border : false,
								bodyStyle : 'overflowX:hidden',
								buttonAlign : 'right',
								iconCls : 'newIcon',
								width : (screen.width - 180) * 0.7 + 160,
								resizable : true,
								layout : 'fit',
								autoScroll : false,
								constrain : true,
								closable : true,
								modal : true,
								items : [new personObj({
											personData : personData,
											url:url,
											id:id
										})],
								tbar : [new Ext.Button({
											text : '更新',
											tooltip : '更新基本信息',
											iconCls : 'btn-refresh',
											hideMode : 'offsets',
											handler : function() {
												var vDates = "";
												var panel_add = window_update.get(0);
												formSavePersonObj(panel_add,window_update,personStore);
											}
										})],
								listeners : {
									'beforeclose' : function(panel) {
										window_update.destroy();
									}
								}
							});
							window_update.show();
							
							//new PersonWindow({listPanel :gPanelPersonWin,type :'edit',url :__ctxPath+'/credit/customer/person/updatePersonMessage.do',personData : personData}).show();
						},
						failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
						},
						params: { id: rows[0].data.id }
					});	
				}
				/*
			var selected = gPanelPersonWin.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				Ext.Ajax.request({
					url : __ctxPath+'/credit/customer/person/seePersonMessage.do',
					method : 'POST',
					success : function(response,request) {
						obj = Ext.util.JSON.decode(response.responseText);
						personData = obj.data;
							var window_update = new Ext.Window({
								id : 'window_update',
								title: '编辑个人基本信息',
								layout : 'fit',
								width :(screen.width-180)*0.7 + 160,
								height : 460,
								closable : true,
								collapsible : true,
								resizable : true,
								autoScroll : true ,
								plain : true,
								border : false,
								modal : true,
								buttonAlign: 'right',
								iconCls : 'upIcon',
								bodyStyle:'overflowX:hidden',
								tbar :[new Ext.Button({text : '保存',tooltip : '保存个人基本信息',iconCls : 'submitIcon',hideMode:'offsets',
								handler :function(){
									formSave('updatePersonPanelManage', window_update ,jStorePersonWin);
								}
								})],
						        autoLoad:{
									url : __ctxPath+ '/credit/customer/person/updatePerson.jsp',
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
		
			*/}
		});
		var button_see = new Ext.Button({
			text : '查看',
			tooltip : '查看选中的人员信息',
			iconCls : 'btn-detail',
			scope : this,
			handler : function() {
				var rows = gPanelPersonWin.getSelectionModel().getSelections();
				if(rows.length==0){
					Ext.ux.Toast.msg('操作信息','请选择记录!');
					return;
				}else if(rows.length>1){
					Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
					return;
				}else{
					Ext.Ajax.request({
						url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
						method : 'POST',
						scope : this,
						success : function(response,request){
							   obj = Ext.util.JSON.decode(response.responseText);
								var personData = obj.data;	
						     	var randomId=rand(100000);
								var id="see_person"+randomId;
								var anchor = '100%';
							    var window_see = new Ext.Window({
											title : '查看个人客户详细信息',
											layout : 'fit',
											width : (screen.width - 180) * 0.7 + 160,
											maximizable:true,
											height : 460,
											closable : true,
											modal : true,
											plain : true,
											border : false,
											items : [new personObj({url:null,id:id,personData:personData,isReadOnly:true})],
											listeners : {
												'beforeclose' : function(panel) {
													window_see.destroy();
												}
											}
										});
								window_see.show();	
						},
						failure : function(response) {					
							Ext.ux.Toast.msg('状态','操作失败，请重试');		
						},
						params:{id:rows[0].data.id}
					});	
				}
				
			
				/*
				var selected = gPanelPersonWin.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var id = selected.get('id');
					Ext.Ajax.request({
						url : __ctxPath+'/credit/customer/person/seePersonMessage.do',
						method : 'POST',
						success : function(response,request){
							obj = Ext.util.JSON.decode(response.responseText);
							personData = obj.data;	
							var window_see = new Ext.Window({
								id : 'window_see',
								title: '查看个人信息',
								layout : 'fit',
								width: (screen.width-180)*0.7 + 160,
								height : 460,
								closable : true,
								collapsible : true,
								resizable : true,
								plain : true,
								border : false,
								autoScroll : true ,
								modal : true,
								buttonAlign: 'right',
								iconCls : 'lookIcon',
								bodyStyle:'overflowX:hidden',
							        autoLoad:{
										url : __ctxPath+'/credit/customer/person/seePerson.jsp',
										scripts : true
								}
							});
							window_see.show();			
							},
							failure : function(response) {					
									Ext.ux.Toast.msg('状态','操作失败，请重试');		
							},
							params: { id: id }
							});	
						
							}
						*/}
					});
		var gPanelPersonWin = new Ext.grid.GridPanel({
			id : 'gPanelPersonWin',
			store : jStorePersonWin,
			colModel : cModelPersonWin,
//			autoExpandColumn : 7,
			//selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : true,
			bbar : pagingBar,
			tbar : [button_add/*,'-',button_fastadd*/,'-',button_see,'-',button_update,'-' ,new Ext.form.Label({text : '姓名：'}),new Ext.form.TextField({id:'personNameRefer',width:80}), new Ext.form.Label({text : '性别：'}),{id:'personSexRefer',width:80,xtype : "dickeycombo",
						nodeKey :'sex_key'},{text:'查找',iconCls : 'btn-search'}],
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
			title : '人员列表',
			border : false,
			width: (screen.width-180)*0.75,
			height : 425,
			constrainHeader : true ,
			layout : 'fit',
			buttonAlign : 'center',
			items : [gPanelPersonWin],
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
					id : selected.get('id'),
					mateid : selected.get('mateid'),
					name : selected.get('name'),
					cardtype : selected.get('cardtype'),
					cardnumber : selected.get('cardnumber'),
					cellphone : selected.get('cellphone'),
					telphone : selected.get('telphone'),
					job : selected.get('job'),
					creditbank : selected.get('creditbank'),
					creditperson : selected.get('creditperson'),
					creditaccount : selected.get('creditaccount'),
					wagebank : selected.get('wagebank'),
					wageperson : selected.get('wageperson'),
					wageaccount : selected.get('wageaccount'),
					sex : selected.get('sex'),
					birthday : selected.get('birthday'),
					dgree : selected.get('dgree'),
					currentcompany : selected.get('currentcompany'),
					unitproperties : selected.get('unitproperties'),
					techpersonnel : selected.get('techpersonnel'),
					unitphone : selected.get('unitphone'),
					familyaddress : selected.get('familyaddress'),
					selfemail : selected.get('selfemail'),
					pinyinname : selected.get('pinyinname'),
					marry : selected.get('marry'),
					monthIncomes : selected.get('monthIncomes'),
					sexvalue : selected.get('sexvalue'),
					marryvalue : selected.get('marryvalue'),
					cardtypevalue : selected.get('cardtypevalue'),
					jobvalue : selected.get('jobvalue'),
					dgreevalue : selected.get('dgreevalue'),
					sonnelvalue : selected.get('sonnelvalue'),
					unitpropertiesvalue : selected.get('unitpropertiesvalue'),
					hukou : selected.get('hukou'),
					beforeName : selected.get('beforeName'),
					steadyWage : selected.get('steadyWage'),
					postaddress : selected.get('postaddress'),
					isheadoffamily : selected.get('isheadoffamily'),
					
					familyshengname : selected.get('familyshengname'),
					familysheng : selected.get('familysheng'),
					familyshiname : selected.get('familyshiname'),
					familyshi : selected.get('familyshi'),
					familyxianname : selected.get('familyxianname'),
					familyxian : selected.get('familyxian'),
					roadname : selected.get('roadname'),
					roadnum : selected.get('roadnum'),
					communityname : selected.get('communityname'),
					doorplatenum : selected.get('doorplatenum'),
					
					familypostcode : selected.get('postcode'),
					employway : selected.get('employway'),
					homeshape : selected.get('homeshape'),
					housearea : selected.get('housearea'),
					homeincome : selected.get('homeincome'),
					homeotherincome : selected.get('homeotherincome'),
					communityname : selected.get('communityname'),
					household : selected.get('household'),
					homeexpend : selected.get('homeexpend'),
					homecreditexpend : selected.get('homecreditexpend'),
					
					homeshapevalue : selected.get('homeshapevalue'),
					personSFZZId : selected.get('personSFZZId'),
					personSFZZUrl : selected.get('personSFZZUrl'),
					personSFZFId : selected.get('personSFZFId'),
					personSFZFUrl : selected.get('personSFZFUrl'),
					employwayvalue : selected.get('employwayvalue'),
					zhusuo : selected.get('zhusuo'),
					ispublicservant : selected.get('ispublicservant'),
					bankName : selected.get('bankName'),
					bankNum : selected.get('bankNum'),
					unitaddress : selected.get('unitaddress'),
					bankId : selected.get('bankId'),
					openType : selected.get('openType'),
					accountType : selected.get('accountType'),
					khname : selected.get('khname'),
					enterpriseBankId : selected.get('enterpriseBankId'),
					areaId : selected.get('areaId'),
					areaName : selected.get('areaName'),
					bankOutletsId : selected.get('bankOutletsId'),
					archivesBelonging : selected.get('archivesBelonging'),
					isCheckCard : selected.get('isCheckCard')
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
}
var selectDictionaryByPerson=function(val,funName){
	Ext.onReady(function() {
	var anchor = '96.5%';
	Ext.BLANK_IMAGE_URL = basepath()+'ext3/resources/images/default/s.gif';
	Ext.QuickTips.init();
	//Ext.form.Field.prototype.msgTarget = 'under';
	var treeLoader = new Ext.tree.TreeLoader({
		dataUrl :__ctxPath+'/creditFlow/multiLevelDic/getDictionaryTreeWindowAreaDic.do',
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
}

