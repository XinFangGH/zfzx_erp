
function addEnterpriseWin(jStore_enterprise,enterId) {//enterId在添加关联企业是用
	var random=rand(1000000);
	var id="add_enterprise"+random;
	var enterId = enterId;
	var window_add = new Ext.Window({
		title : '新增企业信息',
		height : 460,
		constrainHeader : true,
		collapsible : true,
		frame : true,
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
		items : [new enterpriseObj({winId:id,enterId:enterId})],
		tbar : [new Ext.Button({
					text : '保存',
					tooltip : '保存基本信息',
					iconCls : 'submitIcon',
					hideMode : 'offsets',
					handler : function() {
						var vDates="";
						var panel_add=window_add.get(0);
					    /*var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
				        vDates=getGridDate(edGrid);
				        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					    gudonginfo_hidden.setValue(vDates);*/
						formSavePersonObj(panel_add,window_add,jStore_enterprise);
					}
				})]	,
		listeners : {
			  'beforeclose' : function(panel) {
							 window_add.destroy();			
			  }
		}
	});
	window_add.show();
}

function addEnterpriseWin1(jStore_enterprise,legalHidden,legalId,gridPanel) {
	var random=rand(1000000);
	var id="add_enterprise"+random;
	var window_add1 = new Ext.Window({
		title : '新增企业信息',
		height : 460,
		constrainHeader : true,
		collapsible : true,
		frame : true,
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
		items : [new enterpriseObj({winId:id,legalHidden:legalHidden,legalId:legalId})],
		tbar : [new Ext.Button({
					text : '保存',
					tooltip : '保存基本信息',
					iconCls : 'submitIcon',
					hideMode : 'offsets',
					handler : function() {
						 
						gridPanel.store.reload();
						var vDates="";
						var panel_add=window_add1.get(0);
					    var edGrid=panel_add.getCmpByName('gudong_store').get(0).get(1);
				        vDates=getGridDate(edGrid);
				        var gudonginfo_hidden=panel_add.getCmpByName('gudongInfo');
					    gudonginfo_hidden.setValue(vDates);
						formSavePersonObj(panel_add,window_add1,jStore_enterprise);
					}
				})]	,
		listeners : {
			  'beforeclose' : function(panel) {
							 window_add1.destroy();			
			  }
		}
	});
	window_add1.show();
}

var amselectControlperson = function(obj) {
	Ext.getCmp('amcontrolpersonName').setValue(obj.name);
	Ext.getCmp('amcontrolpersonId').setValue(obj.id);
}
var getEnterObjArray = function(objArray) {
	Ext.getCmp('entergslname').setValue(objArray[(objArray.length) - 1].text+ "_" + objArray[(objArray.length) - 2].text);
	Ext.getCmp('entergslnameid').setValue(objArray[(objArray.length) - 1].id+ "," + objArray[(objArray.length) - 2].id);
}
