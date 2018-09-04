var seeIndustry = function(pawnItemId,typeid){
		//把数字，日期类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
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
		viewer=function(mortgageId,talbeName){
		     var mark=talbeName+mortgageId;
	         picViewer(mark,true);
		}
		
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
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<当物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            //style : 'align:right',
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
							html : '<b>项目编号：</b>'
					},{
						html : MortgageData.vPawnItemsList.projectNumber
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
							html : '<b>项目名称：</b>'
					},{
						html : MortgageData.vPawnItemsList.projectName
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
							html : '<b>客户名称：</b>'
					},{
						html : MortgageData.vPawnItemsList.customerName
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
							html : '<b>当物类型：</b> ' + MortgageData.vPawnItemsList.pawnItemTypeName
						},{
							html : '<b>规格和状态：</b>' + MortgageData.vPawnItemsList.specificationsStatus
						},{
							html : '<b>评估价值：</b>' + numTurnToString(MortgageData.vPawnItemsList.assessedValuationValue,8)
						},{
							html : '<b>当物金额：</b>' +numTurnToString(MortgageData.vPawnItemsList.pawnItemMoney,8)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>当物名称：</b> '+ MortgageData.vPawnItemsList.pawnItemName
						},{
							html : '<b>数量：</b>' + MortgageData.vPawnItemsList.counts
						},{
							html : '<b>折当率：</b>' + numTurnToString(MortgageData.vPawnItemsList.discountRate,7)
						},{
							html : '<b>获取时间：</b>' + function(){if(MortgageData.vPawnItemsList.accessTime!=null){return MortgageData.vPawnItemsList.accessTime}else{return ""}}()
						}]
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
							html : '<b>备注：</b>'
					},{
						html : MortgageData.vPawnItemsList.remarks
					}]
	            }]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<工业用地>详细信息',
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
						html : MortgageData.vProjMortIndustry.address
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
							html : '<b>土地性质：</b> ' + function(){if(typeof(MortgageData.vProjMortIndustry.groundCharacterIdValue)!="undefined"){return MortgageData.vProjMortIndustry.groundCharacterIdValue}else{return ''}}()
						},{
							html : '<b>地段描述：</b>' + function(){if(typeof(MortgageData.vProjMortIndustry.descriptionIdValue)!="undefined"){return MortgageData.vProjMortIndustry.descriptionIdValue}else{return ''}}()
						},{
							html : '<b>登记情况：</b>' + function(){if(typeof(MortgageData.vProjMortIndustry.registerInfoIdValue)!="undefined"){return MortgageData.vProjMortIndustry.registerInfoIdValue}else{return ''}}()
						},{
							html : '<b>购买年份：</b>' + numTurnToString(MortgageData.vProjMortIndustry.buytime)
						},{
							html : '<b>市场价格：</b>' + numTurnToString(MortgageData.vProjMortIndustry.exchangeprice,3)
						},{
							html : '<b>销售模型估值：</b>' + numTurnToString(MortgageData.vProjMortIndustry.venditionrangeprice,1)
						},{
							html : '<b>每月出租价格1：</b>' + numTurnToString(MortgageData.vProjMortIndustry.rentpriceformonthone,4)
						},{
							html : '<b>每月出租价格2：</b>' + numTurnToString(MortgageData.vProjMortIndustry.rentpriceformonthtow,4)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>权证编号：</b> '+ MortgageData.vProjMortIndustry.certificatenumber
						},{
							html : '<b>产权人：</b>' + MortgageData.vProjMortIndustry.propertyperson
						},{
							html : '<b>占地面积：</b>' + numTurnToString(MortgageData.vProjMortIndustry.occupyacreage,2)
						},{
							html : '<b>剩余年限：</b>' + numTurnToString(MortgageData.vProjMortIndustry.residualyears,5)
						},{
							html : '<b>租赁模型估值：</b>' + numTurnToString(MortgageData.vProjMortIndustry.tenancyrangeprice,1)
						},{
							html : '<b>土地抵质押贷款余额：</b>' + numTurnToString(MortgageData.vProjMortIndustry.mortgagesbalance,1)
						},{
							html : '<b>同等土地价格1：</b>' + numTurnToString(MortgageData.vProjMortIndustry.exchangepriceone,3)
						},{
							html : '<b>同等土地价格2：</b>' + numTurnToString(MortgageData.vProjMortIndustry.exchangepricetow,3)
						}]
					}]
	            }]
			},{
				xtype:'fieldset',
	            title: '当物材料',
	            collapsible: true,
	            autoHeight:true,
	            anchor : '95%',
			 	items :[new PawnMaterialsView({projId:pawnItemId,businessType:"Pawn",isHidden_materials:true})]
			}]
		});
		
	
		var win_seeCarInfo = new Ext.Window({
			title: '查看工业用地信息',
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
		url : __ctxPath +'/creditFlow/pawn/pawnItems/getInfoPawnItemsList.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			seeCarInformation(obj.data)
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			id : pawnItemId,
			typeid : typeid
		}
	});
}