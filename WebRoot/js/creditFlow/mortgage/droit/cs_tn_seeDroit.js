var seeDroitUpdateInfo = function(mortgageid,businessType,mfinancingId){
		/*//把数字类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
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
	var seeDroitInformation = function(MortgageData){
		var panel_seeDroit = new Ext.form.FormPanel({
			id :'seeDroit',
			//renderTo : 'seeDroitDiv',
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
	            title: '查看<无形权利>详细信息',
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
							html : '<b>权利名称：</b>'
					},{
						html : MortgageData.vProjMortDroit.droitname
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
							html : '<b>流通性：</b> ' + function(){if(typeof(MortgageData.vProjMortDroit.negotiabilityIdValue)!="undefined"){return MortgageData.vProjMortDroit.negotiabilityIdValue}else{return ''}}()
						},{
							html : '<b>经营状况：</b>' +function(){if(typeof(MortgageData.vProjMortDroit.dealStatusIdValue)!="undefined"){return MortgageData.vProjMortDroit.dealStatusIdValue}else{return ''}}()
						},{
							html : '<b>权利质量：</b>' + function(){if(typeof(MortgageData.vProjMortDroit.droitMassIdValue)!="undefined"){return MortgageData.vProjMortDroit.droitMassIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortDroit.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortDroit.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortDroit.modelrangeprice,1)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>享有权利比重：</b> '+ numTurnToString(MortgageData.vProjMortDroit.droitpercent,7)
						},{
							html : '<b>已经经营权利时间：</b>' + numTurnToString(MortgageData.vProjMortDroit.dealdroittime,5)
						},{
							html : '<b>享有权利剩余时间：</b>' + numTurnToString(MortgageData.vProjMortDroit.residualdroittime,5)
						},{
							html : '<b>最近一年权利净收益：</b>' + numTurnToString(MortgageData.vProjMortDroit.droitlucre,1)
						}]
					}]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeDroitInfo').destroy();
				}
			}]*/
		});
	
		var win_seeDroitInfo = new Ext.Window({
			id : 'win_seeDroitInfo',
			title: '查看无形权利信息',
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
			items : [panel_seeDroit]
		});
			win_seeDroitInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeDroitForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeDroitInformation(obj.data) ;
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
