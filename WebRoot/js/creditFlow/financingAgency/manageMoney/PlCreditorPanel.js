/**
 * 债权信息
 * @class PlMmOrderInfoPanel
 * @extends Ext.Panel
 */
PlCreditorPanel = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	companyHidden : false,
	constructor : function(_cfg) {
		if (typeof(_cfg.spouseHidden) != "undefined") {
		}
		Ext.applyIf(this, _cfg);

		PlCreditorPanel.superclass.constructor.call(this, {
			layout : 'column',
			name : 'PlCreditorPanel',
			bodyStyle : 'padding:10px',
			autoScroll : true,
			monitorValid : true,
			frame : false,
			
			plain : true,
			labelAlign : "right",
			items : [{
						columnWidth : .6,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						labelAlign : "right",
						items : [{
									xtype : 'textfield',
									fieldLabel : '债权名称',
									name : 'bidProName',
									readOnly : true
								}]
					}, {
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						labelAlign : "right",
						items : [ {
									xtype : 'textfield',
									fieldLabel : '债权编号',
									name : 'bidProNumber',
									readOnly : true
								}]
					}, {
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						labelAlign : "right",
						items : [ {
									xtype : 'textfield',
									fieldLabel : '债权金额',
									name : 'bidMoney',
									readOnly : true
								}]
					},{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						labelAlign : "right",
						items : [ {
									xtype : 'datefield',
									fieldLabel : '债权开始日期',
									format  : 'Y-m-d',
									name : 'loanStarTime',
									readOnly : true
								}]
					},{
						columnWidth : .3,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						labelWidth : 100,
						labelAlign : "right",
						items : [ {
									xtype : 'datefield',
									fieldLabel : '债权到期日期',
									format  : 'Y-m-d',
									name : 'loanEndTime',
									readOnly : true
								}]
					}]
		})
		
		  if (this.projectId != null && this.projectId != 'undefined') {
		  	  var panel = this;
			  this.loadData( {
				  url : __ctxPath + '/project/getSlSmallloanProject.do?projectId=' + this.projectId,
				  root : 'data', 
				  preName : 'slSmallloanProject' ,
				  success : function (response,obj){
				  		var	result = Ext.util.JSON.decode(response.responseText);
				  		panel.getCmpByName('bidProName').setValue(result.data.projectName);
				  		panel.getCmpByName('bidProNumber').setValue(result.data.projectNumber);
				  		panel.getCmpByName('bidMoney').setValue(result.data.projectMoney);
				  		panel.getCmpByName('loanStarTime').setValue(result.data.startDate);
				  		panel.getCmpByName('loanEndTime').setValue(result.data.intentDate);
				  }
			  }); 
		  }
		 
	}

});
