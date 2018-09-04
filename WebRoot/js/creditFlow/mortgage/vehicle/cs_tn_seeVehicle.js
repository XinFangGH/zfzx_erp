var seeCarInfo = function(mortgageid,businessType,mfinancingId){
	/*	//把数字，日期类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
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
		}*/
		
		//处理特殊字符小于号
		var formatValue = function(obj){
			if(obj.indexOf("<")){
				//var firstElement = obj.indexOf("<");
				//alert("第一次出现<的位置："+firstElement);
				var lastValue = obj.substring(obj.indexOf("<")+1);//获取从'<'第次一出现的位置后第一个元素至末位的值
		        var startValue = obj.substring(0,obj.indexOf("<"));//获取从0开始至第一次出现'<'位置之间的值
		        //alert("获取从0开始至第一次出现'<'位置之间的值==="+start+"       获取从'<'开始至末位的值==="+len);
		        return startValue+'&lt'+lastValue;
			}else{
				return obj;
			}
		}
/*		viewer=function(mortgageId,talbeName){// 提取到DZYMortgageView.js by gao 2013-3-30
		     var mark=talbeName+mortgageId;
	         picViewer(mark,true);
		}*/
		
	var seeCarInformation = function(MortgageData){
		var panel_seeCar = new Ext.form.FormPanel({
			id :'seeVehicle',
			//labelAlign : 'left',
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			autoScroll : true ,
			//cls : 'align:right',
			frame : true,
			border : false,
			items : [baseMortgageInfo(MortgageData),{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<车辆>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
	            items : [{
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
							html : '<b>制造商：</b>' + MortgageData.vProjMortCar.carManufacturer
						},{
							html : '<b>车型编号：</b>' + MortgageData.vProjMortCar.carNumber
						},{
							html : '<b>发动机号：</b>' + MortgageData.vProjMortCar.engineNo
						},{
							html : '<b>车架号：</b>' + MortgageData.vProjMortCar.vin
						},{
							html : '<b>新车价格：</b>' + numTurnToString(MortgageData.vProjMortCar.carprice,1)
						},{
							html : '<b>使用时间：</b>' + numTurnToString(MortgageData.vProjMortCar.haveusedtime,5)
						},{
							html : '<b>里程数：</b>' + numTurnToString(MortgageData.vProjMortCar.totalkilometres,6)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortCar.modelrangeprice,1)
						},{
							html : '<b>市场交易价格1：</b>' + numTurnToString(MortgageData.vProjMortCar.exchangepriceone,1)
						},{
							html : '<b>市场交易价格2：</b>' + numTurnToString(MortgageData.vProjMortCar.exchangepricetow,1)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>车系：</b>' + MortgageData.vProjMortCar.carStyle
						},{
							html : '<b>车型：</b>' + MortgageData.vProjMortCar.carModel
						},{
							html : '<b>排量：</b>' + formatValue(MortgageData.vProjMortCar.displacementValue)
						},{
							html : '<b>配置：</b>' + MortgageData.vProjMortCar.configuration
						},{
							html : '<b>座位数：</b>' + MortgageData.vProjMortCar.seatingValue
						},{
							html : '<b>登记情况：</b> '+ function(){if (typeof(MortgageData.vProjMortCar.enregisterInfoIdValue)!='undefined'){return MortgageData.vProjMortCar.enregisterInfoIdValue}else{return ''}}()
						},{
							html : '<b>出厂日期：</b>' + numTurnToString(MortgageData.vProjMortCar.leavefactorydate)
						},{
							html : '<b>事故次数：</b>' + numTurnToString(MortgageData.vProjMortCar.accidenttimes)
						},{
							html : '<b>车况：</b>' + function(){if (typeof(MortgageData.vProjMortCar.carInfoIdValue)!='undefined'){return MortgageData.vProjMortCar.carInfoIdValue}else{return ''}}()
						}]
					}]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeCarInfo').destroy();
				}
			}]*/
		});
		
	
		var win_seeCarInfo = new Ext.Window({
			id : 'win_seeCarInfo',
			title: '查看车辆信息',
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
			minHeight: 350,       
			minWidth: 560,
			bodyStyle:'overflowX:hidden',
			items : [panel_seeCar]
		});
			win_seeCarInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeVehicleForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.success==true){
				seeCarInformation(obj.data) ;
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