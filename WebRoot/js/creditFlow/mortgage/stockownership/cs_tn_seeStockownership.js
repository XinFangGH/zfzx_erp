var seeStockownershipInfo = function(mortgageid,businessType,mfinancingId){
//把数字类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
	/*	var numTurnToString = function(object,number){// annotation  by gao 2013-9-30
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
	var seeStockownershipInformation = function(MortgageData){
		/*var test1 = MortgageData.vProjMortStockOwnerShip.test1;
		var test2 = MortgageData.vProjMortStockOwnerShip.test2;
		var test3 = MortgageData.vProjMortStockOwnerShip.test3;
		var test4 = MortgageData.vProjMortStockOwnerShip.test4;*/
		//alert("test1="+test1+"  test2="+test2+"   test3"+test3+"   test4="+test4);
		var panel_seeStockownership = new Ext.form.FormPanel({
			id :'seeStockownership',
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
	            title: '查看<股权>详细信息',
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
							html : '<b>目标公司名称：</b>'
					},{
						html : MortgageData.vProjMortStockOwnerShip.enterprisename
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
							html : '<b>营业执照号码：</b> ' + MortgageData.vProjMortStockOwnerShip.licencenumber
						},{
							html : '<b>注册资本：</b>' + numTurnToString(MortgageData.vProjMortStockOwnerShip.enrolcapital,1)
						},{
							html : '<b>股权：</b>' + numTurnToString(MortgageData.vProjMortStockOwnerShip.stockownershippercent,7)
						},{
							html : '<b>净资产：</b>' + numTurnToString(MortgageData.vProjMortStockOwnerShip.netassets,1)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortStockOwnerShip.modelrangeprice,1)
						},{
							html : '<b>登记情况：</b>' + MortgageData.vProjMortStockOwnerShip.registerInfoIdValue
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>行业类型：</b> '+ MortgageData.vProjMortStockOwnerShip.hangyeTypeValue
						},{
							html : '<b>注册时间：</b> '+ numTurnToString(MortgageData.vProjMortStockOwnerShip.registerdate)
						},{
							html : '<b>法人代表：</b>' + MortgageData.vProjMortStockOwnerShip.legalperson
						},{
							html : '<b>控制权人：</b>' + MortgageData.vProjMortStockOwnerShip.stockownership
						},{
							html : '<b>经营时间：</b>' + numTurnToString(MortgageData.vProjMortStockOwnerShip.managementtime,5)
						},{
							html : '<b>经营状况：</b>' + MortgageData.vProjMortStockOwnerShip.managementStatusIdValue
						}]
					}]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeStockownershipInfo').destroy();
				}
			}]*/
		});
		
		var win_seeStockownershipInfo = new Ext.Window({
			id : 'win_seeStockownershipInfo',
			title: '查看股权信息',
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
			items : [panel_seeStockownership]
		});
			win_seeStockownershipInfo.show();
	}

	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeStockownershipForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeStockownershipInformation(obj.data) ;
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
