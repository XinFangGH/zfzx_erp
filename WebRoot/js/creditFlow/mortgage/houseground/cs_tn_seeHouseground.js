var seeHouseGroundInfo = function(mortgageid,businessType,mfinancingId){
		//把数字类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
	/*var numTurnToString = function(object,number){
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
	var seeHousegroundInformation = function(MortgageData){
		var panel_seeHouseGround = new Ext.form.FormPanel({
			id :'seeHouseGround',
			//renderTo : 'seeHouseGroundDiv',
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
	            title: '查看<住宅用地>详细信息',
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
						html : MortgageData.vProjMortHouseGround.address
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
							html : '<b>权证编号：</b> ' + MortgageData.vProjMortHouseGround.certificatenumber
						},{
							html : '<b>产权人：</b>' + MortgageData.vProjMortHouseGround.propertyperson
						},{
							html : '<b>占地面积：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.acreage,2)
						},{
							html : '<b>模型估值1：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.modelrangepriceone,1)
						},{
							html : '<b>土地抵质押贷款余额：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.mortgagesbalance,1)
						},{
							html : '<b>同等土地单价2：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.exchangepriceone,3)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>地段描述：</b> '+ function(){if(typeof(MortgageData.vProjMortHouseGround.descriptionIdValue)!="undefined"){return MortgageData.vProjMortHouseGround.descriptionIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortHouseGround.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortHouseGround.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>预规划住宅面积：	</b>' + numTurnToString(MortgageData.vProjMortHouseGround.anticipateacreage,2)
						},{
							html : '<b>模型估值2：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.modelrangepricetow,1)
						},{
							html : '<b>同等土地单价1：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.exchangeprice,3)
						},{
							html : '<b>同等土地单价3：</b>' + numTurnToString(MortgageData.vProjMortHouseGround.exchangepricetow,3)
						}]
					}]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeHouseGroundInfo').destroy();
				}
			}]*/
		});
		
		var win_seeHouseGroundInfo = new Ext.Window({
			id : 'win_seeHouseGroundInfo',
			title: '查看住宅用地信息',
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
			items : [panel_seeHouseGround]
		});
			win_seeHouseGroundInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeHousegroundForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeHousegroundInformation(obj.data) ;
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
