var seeBusinessInfo = function(mortgageid,businessType,mfinancingId){
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
	var seeBusinessInformation = function(MortgageData){
		var panel_seeBusiness = new Ext.form.FormPanel({
			id :'seeBusiness',
			//renderTo : 'seeBusinessDiv',
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
	            title: '查看<商业用地>详细信息',
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
						html : MortgageData.vProjMortBusiness.address
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
							html : '<b>产权人：</b>' 
					},{
						html : MortgageData.vProjMortBusiness.propertyperson
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
							html : '<b>权证编号：</b> ' + MortgageData.vProjMortBusiness.certificatenumber
						},{
							html : '<b>占地面积：</b>' + numTurnToString(MortgageData.vProjMortBusiness.acreage,2)
						},{
							html : '<b>预规划住宅面积：</b>' + numTurnToString(MortgageData.vProjMortBusiness.anticipateacreage,2)
						},{
							html : '<b>销售模型估值：</b>' + numTurnToString(MortgageData.vProjMortBusiness.venditionrangeprice,1)
						},{
							html : '<b>土地抵质押贷款余额：</b>' + numTurnToString(MortgageData.vProjMortBusiness.mortgagesbalance,1)
						},{
							html : '<b>同等商业房屋价格1：</b>' + numTurnToString(MortgageData.vProjMortBusiness.exchangepriceone,3)
						},{
							html : '<b>同等商业房屋价格2：</b>' + numTurnToString(MortgageData.vProjMortBusiness.exchangepricetow,3)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>地段描述：</b> '+ function(){if(typeof(MortgageData.vProjMortBusiness.descriptionIdValue)!="undefined"){return MortgageData.vProjMortBusiness.descriptionIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortBusiness.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortBusiness.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>租赁模型估值：</b>' + numTurnToString(MortgageData.vProjMortBusiness.tenancyrangeprice,1)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortBusiness.modelrangeprice,1)
						},{
							html : '<b>同等土地单价：</b>' + numTurnToString(MortgageData.vProjMortBusiness.groundexchangeprice,3)
						},{
							html : '<b>同等商业房屋每月出租价格1：</b>' + numTurnToString(MortgageData.vProjMortBusiness.lendpriceformonthone,4)
						},{
							html : '<b>同等商业房屋每月出租价格2：</b>' + numTurnToString(MortgageData.vProjMortBusiness.lendpriceformonthtow,4)
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
							html : '<b>同等商业房屋成交价格1：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusiness.exchangepriceone)+'元/平方米'
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
							html : '<b>同等商业房屋成交价格2：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusiness.exchangepricetow)+'元/平方米'
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
							html : '<b>同等商业房屋每月出租价格1：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusiness.lendpriceformonthone)+'元/月/平方米'
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
							html : '<b>同等商业房屋每月出租价格2：</b>' 
					},{
						html : numTurnToString(MortgageData.vProjMortBusiness.lendpriceformonthtow)+'元/月/平方米'
					}]
	            }*/]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeBusinessInfo').destroy();
				}
			}]*/
		});
		
		var win_seeBusinessInfo = new Ext.Window({
			id : 'win_seeBusinessInfo',
			title: '查看商业用地信息',
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
			items : [panel_seeBusiness]
		});
			win_seeBusinessInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeBusinessForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeBusinessInformation(obj.data) ;
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
