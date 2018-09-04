var seeCompanyInfo = function(mortgageid,businessType,mfinancingId){
		//把数字类型的数据转换成字符串类型,否则html以字符串形式显示,数值类型的值不显示
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
	
	var viewer=function(mortgageId,talbeName,havingFile,title){
			if(havingFile){
				var mark=talbeName+mortgageId;
	         	picViewer(mark,true);
			}else{
				if(title){
					Ext.MessageBox.alert("提示","无"+title+"文件可供预览")
				}else{
					Ext.MessageBox.alert("提示","无相关文件可供预览")
				}
			}
		     
		}
	var seeCompanyInformation = function(MortgageData){
			var transactViewFont = "";
		var unchainViewFont = "";
		if(MortgageData.vProcreditDictionary.havingTransactFile){
			transactViewFont+="<font color=red><u>预览</u></font>";
		}else{
			transactViewFont = "预览";
		}
		if(MortgageData.vProcreditDictionary.havingUnchainFile){
			unchainViewFont+="<font color=red><u>预览</u></font>"
		}else{
			unchainViewFont = "预览";
		}
		var panel_seeCompany = new Ext.form.FormPanel({
			id :'seeCompany',
			//renderTo : 'seeCompanyDiv',
			labelAlign : 'right',
			buttonAlign : 'center',
			bodyStyle : 'overflowX:hidden',
			region:'right', 
			autoScroll : true ,
			frame : true,
			border : false,
			items : [{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<保证担保>基础信息',
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
						html : MortgageData.vProcreditDictionary.projnum
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
						html : MortgageData.vProcreditDictionary.projname
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
							html : '<b>企业名称：</b>'
					},{
						html : MortgageData.vProcreditDictionary.enterprisename
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
							html : '<b>保证人：</b>'
					},{
						html : MortgageData.vProcreditDictionary.assureofnameEnterOrPerson
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
							html : '<b>抵质押物名称：</b>'
					},{
						html : MortgageData.vProcreditDictionary.mortgagename
					}]
	            }*/,{
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
						html : MortgageData.vProcreditDictionary.mortgageRemarks
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
						items : [/*{
							html : '<b>抵质押物类型：</b> ' + MortgageData.vProcreditDictionary.mortgagepersontypeforvalue
						},*/{
							html : '<b>保证人类型：</b>' + MortgageData.vProcreditDictionary.personTypeValue
						}/*,{
							html : '<b>可担保额度：</b>' + numTurnToString(MortgageData.vProcreditDictionary.assuremoney,1)
						}*/,{
							html : '<b>所有权人与借款人的关系：</b>' + MortgageData.vProcreditDictionary.relation
						},{
							html : '<br>'
						},{
							html : '<b>是否落实：</b>' + function(){return (MortgageData.vProcreditDictionary.isTransact)==false?'否':'是' ;}()
						},{
							html : '<b>经办人：</b>' + MortgageData.vProcreditDictionary.transactPerson
						}/*,{
							html : '<b>登记号：</b>' + MortgageData.vProcreditDictionary.pledgenumber
						}*/,{
							html : '<b>落实备注：</b>' + MortgageData.vProcreditDictionary.transactRemarks
						},{
							html : '<br>'
						}/*,{
							html : '<b>是否解除：</b>' + function(){return (MortgageData.vProcreditDictionary.isunchain)==false?'否':'是' ;}()
						},{
							html : '<b>经办人：</b>' + MortgageData.vProcreditDictionary.unchainPerson
						},{
							html : '<b>解除备注：</b>' + MortgageData.vProcreditDictionary.unchainremark
						},{
							html : '<br>'
						}*/,{
							html : '<b>是否归档：</b>' + function(){return (MortgageData.vProcreditDictionary.isArchiveConfirm)==false?'否':'是' ;}()
						},{
							html : '<b>归档备注：</b>' + MortgageData.vProcreditDictionary.remarks
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [/*{
							html : '<b>担保类型：</b> '+ MortgageData.vProcreditDictionary.assuretypeidValue
						},*/{
							html : '<b>保证方式：</b>' + MortgageData.vProcreditDictionary.assuremodeidValue
						}/*,{
							html : '<b>最终估价：</b>' + numTurnToString(MortgageData.vProcreditDictionary.finalprice,1)
						}*/,{
							html : '<br>'
						},{
							html : '<br>'
						},{
							html : '<b>落实时间：</b>' + function(){if(null!=MortgageData.vProcreditDictionary.transactDate){return MortgageData.vProcreditDictionary.transactDate}else{return ""}}()
						},{
							html : '<b>落实文件：</b><a href="#" onclick="viewer('+MortgageData.vProcreditDictionary.id+',\'cs_procredit_mortgage.\','+MortgageData.vProcreditDictionary.havingTransactFile+',\'落实\''+')">'+transactViewFont+'</a>'
						}/*,{
							html : '<b>登记机关：</b>' + MortgageData.vProcreditDictionary.enregisterDepartment
						}*/,{
							html : '<br>'
						},{
							html : '<br>'
						}/*,{
							html : '<b>解除时间：</b>' + function(){if(null!=MortgageData.vProcreditDictionary.unchaindate){return MortgageData.vProcreditDictionary.unchaindate}else{return ""}}()
						},{
							html : '<b>解除文件：</b><a href="#" onclick="viewer('+MortgageData.vProcreditDictionary.id+',\'cs_procredit_mortgage_jc.\')">预览</a>' 
						},{
							html : '<br>'
						}*/,{
							html : '<br>'
						},{
							html : '<br>'
						},{
							html : '<br>'
						}]
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 160
					},
					items : [{
							xtype : 'label',
							html : '<b>要求材料清单：</b><br>'+MortgageData.vProcreditDictionary.needDatumList
					}]
	            }/*,{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 70
					},
					items : [{
							xtype : 'label',
							html : '<b>收到材料清单：</b>'
					},{
						html : MortgageData.vProcreditDictionary.receiveDatumList
					}]
	            },{
	            	layout : 'column',
	            	columnWidth : 1,
					border : false,
					defaults : {
						layout : 'form',
						anchor : '99%',
						height : 70
					},
					items : [{
							xtype : 'label',
							html : '<b>缺少材料过程记录：</b>'
					},{
						html : MortgageData.vProcreditDictionary.lessDatumRecord
					}]
	            }*/]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<无限连带责任-公司>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            //width: 600,
	            anchor : '95%',
	            items : [/*{
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
							html : '<b>公司名称：</b>' 
					},{
						html : MortgageData.vProjMortCompany.enterprisename
					}]
	            },*/{
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
							html : '<b>营业执照号码：</b> ' + MortgageData.vProjMortCompany.licensenumber
						},{
							html : '<b>法人代表：</b> '+ MortgageData.vProjMortCompany.corporate
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>联系电话：</b> '+ MortgageData.vProjMortCompany.telephone
						},{
							html : '<b>法人代表电话：</b>' + MortgageData.vProjMortCompany.corporatetel
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
								html : '<b>公司地址：</b>' 
						},{
							html : MortgageData.vProjMortCompany.registeraddress
						}]
		            },{
		            	layout : 'column',
		            	columnWidth : .5,
		            	border : false,
		            	defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
								xtype : 'label',
								html : '<b>主营业务：</b>' 
						},{
							html : MortgageData.vProjMortCompany.business
						}]
		            },{
		            	layout : 'column',
		            	columnWidth : .5,
		            	border : false,
		            	defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
								xtype : 'label',
								html : '<b>资产价值：</b>' 
						},{
							html : numTurnToString(MortgageData.vProjMortCompany.netassets,1)
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
								html : '<b>月营业额：</b>' 
						},{
							html : MortgageData.vProjMortCompany.monthlyIncome
						}]
		            }]
	            }]
			}]/*,
			tbar : [{
				text : '关闭',
				iconCls : 'cancelIcon',
				handler : function(){
					Ext.getCmp('win_seeCompanyInfo').destroy();
				}
			}]*/
		});
		
		var win_seeCompanyInfo = new Ext.Window({
			id : 'win_seeCompanyInfo',
			title: '查看无限连带责任-公司信息',
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
			items : [panel_seeCompany]
		});
			win_seeCompanyInfo.show();
	}
	
	Ext.Ajax.request({
		url : __ctxPath +'/credit/mortgage/seeCompanyForUpdate.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			//alert(response.responseText);
			if(obj.success==true){
				seeCompanyInformation(obj.data) ;
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
