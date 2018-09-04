var seeHouseInfo = function(mortgageid,businessType,mfinancingId){
	var seeHouseInformation = function(MortgageData){
		var panel_seeHouse = new Ext.form.FormPanel({
			id :'seeHouse',
			//renderTo : 'seeHouseDiv',
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
	            title: '查看<住宅>详细信息',
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
							html : '<b>房地产地点：</b>'
					},{
						html : MortgageData.vProjMortHouse.houseaddress
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
							html : '<b>产权性质：</b> ' + function(){if(typeof(MortgageData.vProjMortHouse.propertyRightIdValue)!="undefined"){return MortgageData.vProjMortHouse.propertyRightIdValue}else{return ''}}()
						},{
							html : '<b>建筑式样：</b>' + function(){if(typeof(MortgageData.vProjMortHouse.constructionTypeIdValue)!="undefined"){return MortgageData.vProjMortHouse.constructionTypeIdValue}else{return ''}}()
						},{
							html : '<b>建筑结构：</b>' + function(){if(typeof(MortgageData.vProjMortHouse.constructionFrameIdValue)!="undefined"){return MortgageData.vProjMortHouse.constructionFrameIdValue}else{return ''}}()
						},{
							html : '<b>户型结构：</b>' + function(){if(typeof(MortgageData.vProjMortHouse.houseTypeIdValue)!="undefined"){return MortgageData.vProjMortHouse.houseTypeIdValue}else{return ''}}()
						},{
							html : '<b>地段描述：</b>' + function(){if(typeof(MortgageData.vProjMortHouse.descriptionIdValue)!="undefined"){return MortgageData.vProjMortHouse.descriptionIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortHouse.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortHouse.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>按揭余额：</b>' + numTurnToString(MortgageData.vProjMortHouse.mortgagesbalance,1)
						},{
							html : '<b>同等房产单位单价1：</b>' + numTurnToString(MortgageData.vProjMortHouse.exchangepriceone,3)
						},{
							html : '<b>同等房产单位单价2：</b>' + numTurnToString(MortgageData.vProjMortHouse.exchangepricetow,3)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>权证编号：</b> '+ MortgageData.vProjMortHouse.certificatenumber
						}/*,{
							html : '<b>产权人：</b>' + MortgageData.vProjMortHouse.propertyperson
						}*/,{
							html : '<b>共有人：</b>' + MortgageData.vProjMortHouse.mutualofperson
						},{
							html : '<b>建筑面积：</b>' + numTurnToString(MortgageData.vProjMortHouse.buildacreage,2)
						},{
							html : '<b>建成年份：</b>' + numTurnToString(MortgageData.vProjMortHouse.buildtime)
						},{
							html : '<b>剩余年限：</b>' + numTurnToString(MortgageData.vProjMortHouse.residualyears,5)
						},{
							html : '<b>新房单价：</b>' + numTurnToString(MortgageData.vProjMortHouse.exchangefinalprice,3)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortHouse.modelrangeprice,1)
						},{
							html : '<b>同等房产单位单价3：</b>' + numTurnToString(MortgageData.vProjMortHouse.exchangepricethree,3)
						}]
					}/*,{
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
								html : '<b>同等房产单位交易单价1：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortHouse.exchangepriceone)+'元/平方米'
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
								html : '<b>同等房产单位交易单价2：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortHouse.exchangepricetow)+'元/平方米'
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
								html : '<b>同等房产单位交易单价3：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortHouse.exchangepricethree)+'元/平方米'
						}]
		            }*/]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeHouseInfo').destroy();
				}
			}]*/
		});
		
	  	var win_seeHouseInfo = new Ext.Window({
			id : 'win_seeHouseInfo',
			title: '查看住宅信息',
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
			items : [panel_seeHouse]
		});
			win_seeHouseInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeHouseForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeHouseInformation(obj.data) ;// fixed by gao  old
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
