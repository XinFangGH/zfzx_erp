var seeBusinessAndLiveInfo = function(mortgageid,businessType,mfinancingId){
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
	var seeBusinessAndLiveInformation = function(MortgageData){
		var panel_seeBusinessAndLive = new Ext.form.FormPanel({
			id :'seeBusinessAndLive',
			//renderTo : 'seeBusinessAndLiveDiv',
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
	            title: '查看<商住用地>详细信息',
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
							html : '<b>土地地点：</b>' 
					},{
						html : MortgageData.vProjMortBusAndLive.address
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
							html : '<b>地段描述：</b> ' + MortgageData.vProjMortBusAndLive.descriptionIdValue
						},{
							html : '<b>登记情况：</b>' + MortgageData.vProjMortBusAndLive.registerInfoIdValue
						},{
							html : '<b>占地面积：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.acreage,2)
						},{
							html : '<b>租赁模型估值：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.tenancyrangeprice,1)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.modelrangeprice,1)
						},{
							html : '<b>同等土地单价：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.exchangepriceground,3)
						},{
							html : '<b>同等商业房屋价格：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.exchangepricebusiness,3)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>权证编号：</b> '+ MortgageData.vProjMortBusAndLive.certificatenumber
						},{
							html : '<b>产权人：</b>' + MortgageData.vProjMortBusAndLive.propertyperson
						},{
							html : '<b>预规划住宅面积：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.anticipateacreage,2)
						},{
							html : '<b>销售模型估值：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.venditionrangeprice,1)
						},{
							html : '<b>同等住宅单价：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.exchangepricehouse,3)
						},{
							html : '<b>土地抵质押贷款余额：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.mortgagesbalance,1)
						},{
							html : '<b>同等商业房屋每月出租价格：</b>' + numTurnToString(MortgageData.vProjMortBusAndLive.rentpriceformonth,4)
						}]
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
							html : '<b>同等商业房屋成交价格：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusAndLive.exchangepricebusiness)+'元/平方米'
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
							html : '<b>同等商业房屋每月出租价格：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusAndLive.rentpriceformonth)+'元/月/平方米'
					}]
	            }*/]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeBusinessAndLiveInfo').destroy();
				}
			}]*/
		});
	
		var win_seeBusinessAndLiveInfo = new Ext.Window({
			id : 'win_seeBusinessAndLiveInfo',
			title: '查看商住用地信息',
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
			items : [panel_seeBusinessAndLive]
		});
			win_seeBusinessAndLiveInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeBusinessAndLiveForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeBusinessAndLiveInformation(obj.data) ;
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
