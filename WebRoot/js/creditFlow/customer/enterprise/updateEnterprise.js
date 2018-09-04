function updateEnterprise(enterpriseData,personData,jStore_enterprise,formObj) {
	var anchor = '100%';
	var random=rand(1000000);
	var id="update_enterprise"+random;
	var window_update = new Ext.Window({
		title : '编辑企业信息',
		layout : 'fit',
		iconCls : 'btn-edit',
		width : (screen.width - 180) * 0.7 + 160,
		constrainHeader : true,
		bodyStyle : 'overflowX:hidden',
		collapsible : true,
		autoScroll : true,
		frame : true,
		height : 460,
		closable : true,
		constrain : true,
		resizable : true,
		modal : true,
		buttonAlign : 'center',
		items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,winId:id})],
		tbar : [new Ext.Button({
					text : '更新',
					tooltip : '保存基本信息',
					iconCls : 'btn-refresh',
					hideMode : 'offsets',
					handler : function() {
						var vDates="";
						var panel_add=window_update.get(0);
					   /* var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
				        vDates=getGridDate(edGrid);
				        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					    gudonginfo_hidden.setValue(vDates);*/
					    formSavePersonObj(panel_add,window_update,jStore_enterprise,formObj);
						
					}
		})]	,
		listeners : {
			  'beforeclose' : function(panel) {
							 window_update.destroy();			
			  }
		}
	 });
	 window_update.show();
}

function updateEnterprise1(enterpriseData,personData,jStore_enterprise,formObj,legalId,legalHidden) {
	var anchor = '100%';
	var random=rand(1000000);
	var id="update_enterprise"+random;
	var window_update1 = new Ext.Window({
		title : '编辑企业信息',
		layout : 'fit',
		iconCls : 'btn-edit',
		width : (screen.width - 180) * 0.7 + 160,
		constrainHeader : true,
		bodyStyle : 'overflowX:hidden',
		collapsible : true,
		autoScroll : true,
		frame : true,
		height : 460,
		closable : true,
		constrain : true,
		resizable : true,
		modal : true,
		buttonAlign : 'center',
		items : [new enterpriseObj({enterpriseId:enterpriseData.id,enterprise:enterpriseData,person:personData,winId:id,legalHidden:legalHidden,legalId:legalId})],
		tbar : [new Ext.Button({
					text : '更新',
					tooltip : '保存基本信息',
					iconCls : 'btn-refresh',
					hideMode : 'offsets',
					handler : function() {
						var vDates="";
						var panel_add=window_update1.get(0);
					    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
				        vDates=getGridDate(edGrid);
				        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					    gudonginfo_hidden.setValue(vDates);
					    formSavePersonObj(panel_add,window_update1,jStore_enterprise,formObj);
						
					}
		})]	,
		listeners : {
			  'beforeclose' : function(panel) {
							 window_update1.destroy();			
			  }
		}
	 });
	 window_update1.show();
}

var selectLegalperson = function(obj) {
	Ext.getCmp('uplegalpersonName').setValue(obj.name);
	Ext.getCmp('uplegalpersonId').setValue(obj.id);
}
var selectControlperson = function(obj) {
	Ext.getCmp('upcontrolpersonName').setValue(obj.name);
	Ext.getCmp('upcontrolpersonId').setValue(obj.id);
}
var getEnterAreaObjArrayUp = function(objArray) {
	Ext.getCmp('upentermanagecity')
			.setValue(objArray[(objArray.length) - 1].text + "_"
					+ objArray[(objArray.length) - 2].text + "_"
					+ objArray[0].text);
	Ext.getCmp('upentermanagecityid').setValue(objArray[(objArray.length) - 1].id + ","
			
					+ objArray[(objArray.length) - 2].id + "," + objArray[0].id);
}
var getEnterObjArrayUp = function(objArray) {
	Ext.getCmp('upentergslname').setValue(objArray[(objArray.length) - 1].text
			+ "_" + objArray[(objArray.length) - 2].text);
	Ext.getCmp('upentergslnameid').setValue(objArray[(objArray.length) - 1].id
			+ "," + objArray[(objArray.length) - 2].id);
}
var getHangyeAreaObjArrayUp = function(objArray) {
	Ext.getCmp('hangyeTypeValue').setValue(objArray[(objArray.length) - 1].text);
	Ext.getCmp('hangyeTypeId').setValue(objArray[(objArray.length) - 1].id);
}