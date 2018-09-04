var seeEducationInfo = function(mortgageid,businessType,mfinancingId){
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
	var seeEducationInformation = function(MortgageData){
		var panel_seeEducation = new Ext.form.FormPanel({
			id :'seeEducation',
			//renderTo : 'seeEducationDiv',
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
	            title: '查看<教育用地>详细信息',
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
						html : MortgageData.vProjMortEducation.address
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
						html : MortgageData.vProjMortEducation.propertyperson
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
							html : '<b>占地面积：</b> ' + numTurnToString(MortgageData.vProjMortEducation.acreage,2)
						},{
							html : '<b>权证编号：</b>' + MortgageData.vProjMortEducation.certificatenumber
						},{
							html : '<b>购买年份：</b>' + numTurnToString(MortgageData.vProjMortEducation.buytime)
						},{
							html : '<b>剩余年限：</b>' + numTurnToString(MortgageData.vProjMortEducation.residualyears,5)
						},{
							html : '<b>土地租赁模型估值：</b>' + numTurnToString(MortgageData.vProjMortEducation.groundtenancyrangeprice,1)
						},{
							html : '<b>同等土地每月出租价格：</b>' + numTurnToString(MortgageData.vProjMortEducation.groundrentpriceformonth,4)							
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>建筑物面积：</b> '+ numTurnToString(MortgageData.vProjMortEducation.builacreage,2)
						},{
							html : '<b>土地性质：</b>' + MortgageData.vProjMortEducation.groundCharacterIdValue
						},{
							html : '<b>地段描述：</b>' + MortgageData.vProjMortEducation.descriptionIdValue
						},{
							html : '<b>登记情况：</b>' + MortgageData.vProjMortEducation.registerInfoIdValue
						},{
							html : '<b>建筑物租赁模型估值：</b>' + numTurnToString(MortgageData.vProjMortEducation.buildtenancyrangeprice,1)
						},{
							html : '<b>同等建筑物每月出租价格：</b>' + numTurnToString(MortgageData.vProjMortEducation.buildrentpriceformonth,4)
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
								html : '<b>同等土地每月出租价格：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortEducation.groundrentpriceformonth)+'元/月/平方米'
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
								html : '<b>同等建筑物每月出租价格：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortEducation.buildrentpriceformonth)+'元/月/平方米'
						}]
		            }*/]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeEducationInfo').destroy();
				}
			}]*/
		});
	
		var win_seeEducationInfo = new Ext.Window({
			id : 'win_seeEducationInfo',
			title: '查看教育用地信息',
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
			items : [panel_seeEducation]
		});
			win_seeEducationInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/education/seeEducationForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeEducationInformation(obj.data) ;
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
