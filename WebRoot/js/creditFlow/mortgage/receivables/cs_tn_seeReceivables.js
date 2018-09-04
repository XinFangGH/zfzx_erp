var seeReceivables = function(mortgageid,businessType,mfinancingId){
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
	var seeReceivablesInfo = function(MortgageData){
		var panel_seeReceivables = new Ext.form.FormPanel({
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
	            title: '查看<应收账款>详细信息',
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
							html : '<b>应收账款企业：</b>'
					},{
						html : MortgageData.csProcreditMortgageReceivables.enterpriseName
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
							html : '<b>应收账款公司：</b> ' + MortgageData.csProcreditMortgageReceivables.accountsPayCompany
						},{
							html : '<b>对应合同编号：</b>' + MortgageData.csProcreditMortgageReceivables.contractNumber
						},{
							html : '<b>发票号码：</b>' + MortgageData.csProcreditMortgageReceivables.billNumber
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>应收账款金额：</b> '+ MortgageData.csProcreditMortgageReceivables.receivableMoney+'元'
						},{
							html : '<b>应收账款到期日：</b>' + MortgageData.csProcreditMortgageReceivables.intentDate
						},{
							html : '<b>发票日期：</b>' + MortgageData.csProcreditMortgageReceivables.billDate
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
		
		var win_seeReceivablesInfo = new Ext.Window({
			id : 'win_seeReceivablesInfo',
			title: '查看信息>>>应收账款',
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
			items : [panel_seeReceivables]
		});
			win_seeReceivablesInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeReceivables.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeReceivablesInfo(obj.data) ;
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
