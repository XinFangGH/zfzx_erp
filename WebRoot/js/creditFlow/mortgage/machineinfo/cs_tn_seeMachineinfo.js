var seeMachineInfo = function(mortgageid,businessType,mfinancingId){
/*		//把数字类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
	var numTurnToString = function(object,number){
			var obj = "";
			if(object == null || object == 0){
				obj = "";
			}else{
				if(number ==1){
					obj = object.toString()+'万元';
				}else if(number == 2){
					obj = object.toString()+'平方米';
				}else if(number == 3){
					obj = object.toString()+'元/平方米';
				}else if(number == 4){
					obj = object.toString()+'元/月/平方米';
				}else if(number == 5){
					obj = object.toString()+'年';
				}else if(number == 6){
					obj = object.toString()+'公里';
				}else if(number == 7){
					obj = object.toString()+'%';
				}else if(number == 8){
					obj = object.toString()+'元';
				}else{
					obj = object.toString()+"";
				}
			}
			return obj;
		}
	viewer=function(mortgageId,talbeName){
		     var mark=talbeName+mortgageId;
	         picViewer(mark,true);
		}*/
	var seeMachineInformation = function(MortgageData){
		var panel_seeMachineinfo = new Ext.form.FormPanel({
			id :'seeMachineinfo',
			//renderTo : 'seeMachineInfoDiv',
			labelAlign : 'right',
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			region:'right', 
			autoScroll : true ,
			frame : true,
			border : false,
			items : [baseMortgageInfo(MortgageData),{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<机器设备>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            //width: 600,
	            anchor : '95%',
	            items : [{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 30
					},
					items : [{
							xtype : 'label',
							html : '<b>设备名称：</b>'
					},{
						html : MortgageData.vProjMortMachineInfo.machinename
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 30
					},
					items : [{
							xtype : 'label',
							html : '<b>控制权人：</b>'
					},{
						html : MortgageData.vProjMortMachineInfo.controller
					}]
	            },{

	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					items : [{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>控制权方式：</b> ' + MortgageData.vProjMortMachineInfo.controllerTypeIdValue
						},{
							html : '<b>设备型号：</b>' + MortgageData.vProjMortMachineInfo.machinetype
						},{
							html : '<b>新货价格：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.newcarprice,1)
						},{
							html : '<b>使用时间：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.havedusedtime,5)
						},{
							html : '<b>二手价值1：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.secondaryvalueone,1)
						},{
							html : '<b>二手价值2：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.secondaryvaluetow,1)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>通用程度：</b> '+ function(){if(typeof(MortgageData.vProjMortMachineInfo.commonGradeIdValue)!="undefined"){return MortgageData.vProjMortMachineInfo.commonGradeIdValue}else{return ''}}()
						},{
							html : '<b>性能状况：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.capabilityStatusIdValue)
						},{
							html : '<b>变现能力：</b>' + function(){if(typeof(MortgageData.vProjMortMachineInfo.cashabilityIdValue)!="undefined"){return MortgageData.vProjMortMachineInfo.cashabilityIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortMachineInfo.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortMachineInfo.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>出厂日期：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.leavefactorydate)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortMachineInfo.modelrangeprice,1)
						}]
					}]
	            
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeMachineInfo').destroy();
				}
			}]*/
		});
		
		var win_seeMachineInfo = new Ext.Window({
			id : 'win_seeMachineInfo',
			title: '查看机器设备信息',
			layout : 'fit',
			iconCls : 'btn-readdocument',
			//width:660,
			width : (screen.width-180)*0.6,
			height : 450,
			closable : true,
			collapsible : true,
			resizable : true,
			plain : true,
			border : false,
			autoScroll : true ,
			modal : true,
			buttonAlign: 'right',
			//minHeight: 350,       
			//minWidth: 560,
			bodyStyle:'overflowX:hidden',
			items : [panel_seeMachineinfo]
		});
			win_seeMachineInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeMachineinfoForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeMachineInformation(obj.data) ;
			}else{
				Ext.Msg.alert('状态', obj.msg);
			}
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			id : mortgageid,
			businessType : businessType,
			mfinancingId : mfinancingId
		}
	});
}
