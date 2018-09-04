var seeOfficeBuildingInfo = function(mortgageid,businessType,mfinancingId){
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
	var seeOfficebuildingInformation = function(MortgageData){
		var panel_seeOfficebuilding = new Ext.form.FormPanel({
			id :'seeOfficebuilding',
			//renderTo : 'seeOfficebuildingDiv',
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
	            title: '查看<商铺写字楼>详细信息',
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
						html : MortgageData.vProjMortOfficeBuilding.houseaddress
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
							html : '<b>产权性质：</b> ' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.propertyRightIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.propertyRightIdValue}else{return ''}}()
						},{
							html : '<b>建筑式样：</b>' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.constructionTypeIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.constructionTypeIdValue}else{return ''}}()
						},{
							html : '<b>建筑结构：</b>' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.constructionFrameIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.constructionFrameIdValue}else{return ''}}()
						},{
							html : '<b>户型结构：</b>' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.houseTypeIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.houseTypeIdValue}else{return ''}}() 
						},{
							html : '<b>地段描述：</b>' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.descriptionIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.descriptionIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortOfficeBuilding.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortOfficeBuilding.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>建成年份：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.buildtime)
						},{
							html : '<b>模型估价：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.modelrangeprice,1)
						},{
							html : '<b>新房交易单价：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangefinalprice,3)
						},{
							html : '<b>同等房产租金1：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.rentone,4)
						},{
							html : '<b>同等房产租金2：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.renttow,4)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>权证编号：</b> '+ MortgageData.vProjMortOfficeBuilding.certificatenumber
						},{
							html : '<b>产权人：</b>' + MortgageData.vProjMortOfficeBuilding.propertyperson
						},{
							html : '<b>共有人：</b>' + MortgageData.vProjMortOfficeBuilding.mutualofperson
						},{
							html : '<b>层高：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.floors)
						},{
							html : '<b>建筑面积：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.buildacreage,2)
						},{
							html : '<b>按揭余额：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.mortgagesbalance,1)
						},{
							html : '<b>剩余年限：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.residualyears,5)
						},{
							html : '<b>租赁模型估算：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.leaseholdrangeprice,1)
						},{
							html : '<b>同等房产单位交易单价1：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepriceone,3)
						},{
							html : '<b>同等房产单位交易单价2：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepricetow,3)
						},{
							html : '<b>同等房产单位交易单价3：</b>' + numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepricethree,3)
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
							html : '<b>同等房产单位交易单价1：</b>'
					},{
						html : numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepriceone)+'元/平方米'
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
						html : numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepricetow)+'元/平方米'
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
						html : numTurnToString(MortgageData.vProjMortOfficeBuilding.exchangepricethree)+'元/平方米'
					}]
	            }*/]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeOfficebuildingInfo').destroy();
				}
			}]*/
		});
		
		var win_seeOfficebuildingInfo = new Ext.Window({
			id : 'win_seeOfficebuildingInfo',
			title: '查看信息>>>商铺写字楼',
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
			items : [panel_seeOfficebuilding]
		});
			win_seeOfficebuildingInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeOfficebuildingForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeOfficebuildingInformation(obj.data) ;
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
