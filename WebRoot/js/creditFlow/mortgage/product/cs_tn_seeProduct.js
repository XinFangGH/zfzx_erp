var seeProductInfo = function(mortgageid,businessType,mfinancingId){
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
	var seeProductInformation = function(MortgageData){
		var panel_seeProduct = new Ext.form.FormPanel({
			id :'seeProduct',
			//renderTo : 'seeProductDiv',
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
	            title: '查看<存货/商品>详细信息',
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
							html : '<b>商品名称：</b>'
					},{
						html : MortgageData.vProjMortProduct.name
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
							html : '<b>存放地点：</b>'
					},{
						html : MortgageData.vProjMortProduct.depositary
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
							html : '<b>品牌：</b> ' + MortgageData.vProjMortProduct.brand
						},{
							html : '<b>型号：</b>' + MortgageData.vProjMortProduct.type
						},{
							html : '<b>市场单价1：</b>' + numTurnToString(MortgageData.vProjMortProduct.priceone,8)
						},{
							html : '<b>市场单价2：</b>' + numTurnToString(MortgageData.vProjMortProduct.pricetow,8)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortProduct.modelrangeprice,8)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>控制权人：</b> '+ MortgageData.vProjMortProduct.controller
						},{
							html : '<b>数量：</b>' + numTurnToString(MortgageData.vProjMortProduct.amount)
						},{
							html : '<b>控制权方式：</b>' + function(){if(typeof(MortgageData.vProjMortProduct.controllerTypeIdValue)!="undefined"){return MortgageData.vProjMortProduct.controllerTypeIdValue}else{return ''}}()
						},{
							html : '<b>通用程度：</b>' + function(){if(typeof(MortgageData.vProjMortProduct.commonGradeIdValue)!="undefined"){return MortgageData.vProjMortProduct.commonGradeIdValue}else{return ''}}()
						},{
							html : '<b>变现能力：</b>' + function(){if(typeof(MortgageData.vProjMortProduct.cashabilityIdValue)!='undefined'){return MortgageData.vProjMortProduct.cashabilityIdValue}else{return ''}}()
						}]
					}]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeProductInfo').destroy();
				}
			}]*/
		});
		
		var win_seeProductInfo = new Ext.Window({
			id : 'win_seeProductInfo',
			title: '查看信息>>>存货/商品',
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
			items : [panel_seeProduct]
		});
			win_seeProductInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeProductForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeProductInformation(obj.data) ;
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
