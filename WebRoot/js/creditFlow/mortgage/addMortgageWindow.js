var addMortgage = function(projectId,refreshMortgageGridStore,businessType,isAllow){
	var win1;
	var data = [['车辆',1],['股权',2],['无限连带责任-公司',3],['无限连带责任-个人',4],['机器设备',5],['存货/商品',6],['住宅',7],['商铺写字楼',8],['住宅用地',9],['商业用地',10],['商住用地',11],['教育用地',12],['工业用地',13],['无形权利',14]];
	var nextPage = new Ext.FormPanel({
		url : __ctxPath +'/credit/mortgage/redirect.do',
		labelAlign : 'right',
		buttonAlign : 'center',
		bodyStyle : 'padding:10px;',
		labelWidth : 120,
		frame : true,
		monitorValid : true,
		items:[{
			id : 'personType',
			xtype : "dickeycombo",
			nodeKey : 'syrlx',
			hiddenName : "procreditMortgage.personType",
			fieldLabel : "所有人类型",
			itemName : '所有人类型', // xx代表分类名称
			anchor : '100%',
			width : 75,
			emptyText : '<----请选择---->',
			allowBlank : false,
			allowText : '必选项',
			listeners : {
				'select' : function(combo,record,index){
					var typeid = combo.getValue();
					if(typeid == 602){
						Ext.getCmp('assuretype').setValue('');
						Ext.getCmp('chooseItem').setValue('');
						Ext.getCmp('chooseItem').getStore().removeAll();
						Ext.getCmp('chooseItem').getStore().loadData(data,true);
						var faren = Ext.getCmp('chooseItem').getStore().getAt(3);
						Ext.getCmp('chooseItem').getStore().remove(faren);
					}else if(typeid == 603){
						Ext.getCmp('assuretype').setValue('');
						Ext.getCmp('chooseItem').setValue('');
						Ext.getCmp('chooseItem').getStore().removeAll();
						Ext.getCmp('chooseItem').getStore().loadData(data,true);
						var ziranren = Ext.getCmp('chooseItem').getStore().getAt(2);
						Ext.getCmp('chooseItem').getStore().remove(ziranren);
					}
				}
			}
		},{
			id : 'assuretype',
			xtype : "dickeycombo",
			nodeKey : 'dblx',
			hiddenName : "procreditMortgage.assuretypeid",
			fieldLabel : "担保类型",
			itemName : '担保类型', // xx代表分类名称
			anchor : '100%',
			width : 75,
			emptyText : '<----请选择---->',
			allowBlank : false,
			allowText : '必选项',
			listeners : {
				afterrender : function(combox) {
					var st = combox.getStore();
					st.on("load", function() {
						combox.setValue(combox.getValue());
						combox.clearInvalid();
					})
				}
			}
		},{
			id : 'chooseItem',
			xtype:'combo',
			hiddenName : 'nextpage',
			anchor : '100%',
			fieldLabel:'抵质押物类型',
			mode : 'local',
			allowBlank : false,
			allowText : '必选项',
			emptyText : '<---请选择--->',
			forceSelection : true, 
			displayField : 'CompanyKind',
			valueField : 'CompanyKindValue',
			editable : false,
			triggerAction : 'all',
			store : new Ext.data.SimpleStore({
				data : data,
				fields:['CompanyKind','CompanyKindValue']
			})
		}],
		buttons:[{
			text : '下一步',
			iconCls : 'btn-transition',
			formBind : true,
			handler : function() {
				var getChooseID = Ext.getCmp('chooseItem').getValue();
				var personType = Ext.getCmp('personType').getValue();
				var assuretype = Ext.getCmp('assuretype').getValue();
				
				if(getChooseID==''){
					Ext.ux.Toast.msg('状态','请选择抵质押物类型!');
					return ;
				};
				if(personType==''){
					Ext.ux.Toast.msg('状态','请选择所有人类型!');
					return ;
				};
				if(assuretype==''){
					Ext.ux.Toast.msg('状态','请选择抵质押物类型!');
					return ;
				};
				
				nextPage.getForm().submit({
					method : 'POST',
					waitTitle : '连接',
					waitMsg : '消息发送中...',
					success : function(form,action) {
					
					
					if(getChooseID == 1){
						addMortgageCar(projectId,personType,assuretype,refreshMortgageGridStore,businessType,isAllow);
					}else if(getChooseID == 2){
						addMortgageStockOwnerShip(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 3){
						addMortgageCompany(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 4){
						addMortgageDutyPerson(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 5){
						addMortgageMachineInfo(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 6){
						addMortgageProduct(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 7){
						addMortgageHouse(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 8){
						addMortgageOfficeBuilding(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 9){
						addMortgageHouseGround(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 10){
						addMortgageBusiness(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 11){
						addMortgageBusinessAndLive(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 12){
						addMortgageEducation(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 13){
						addMortgageIndustry(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else if(getChooseID == 14){
						addMortgageDroit(projectId,personType,assuretype,refreshMortgageGridStore,businessType);
					}else{
						window.location.href="/error.jsp";
					}
				}
			});
			}
		}]
	});
	win1 = new Ext.Window({
		id : 'mortgage_add_win',
		title : '请选择抵质押物类型',
		iconCls : 'btn-add',
		layout : 'fit',
		width : 327,
		height : 175,
		modal : true,
		border : false,
		items : [nextPage]
	});
	win1.show();
}
