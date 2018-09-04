var seeObjectInfo = function(id,businessType,isShowHandleMsg){
		//避免数据库为空数据，显示undefined
		var getNotUndefinedData = function(data){
			if(typeof(data)=="undefined"){
				return '';
			}else{
				return data;
			}
		}
		if(typeof(isShowHandleMsg)=='undefined'){
			isShowHandleMsg = false;
		}
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
		var leaseObjectInsuranceInfo = new LeaseObjectInsuranceInfo({projectId:MortgageData.vLeaseFinanceObjectInfo.id,isHidden:true});
		var uploads = new LeaseFinanceUploads({
		    	projectId : MortgageData.vLeaseFinanceObjectInfo.id,
		    	isHidden : false,
		    	businessType :'LeaseFinanceObject',//LeaseFinanceObject租赁标的物的特殊type
		    	isNotOnlyFile : false,
		    	isHiddenColumn : false,
		    	isDisabledButton : false,
		    	hiddenUpBtn:true,//只查看文件详情的话，不允许上传   add  by gao
//		    	setname :'担保责任解除函',
//		    	titleName :'担保责任解除函',
//		    	tableName :'typeisdbzrjchsmj',
		    	uploadsSize :10,
		    	isHiddenOnlineButton :true,
		    	isDisabledOnlineButton :true
		});
		
		var panel_seeCar = new Ext.form.FormPanel({
			id :'seeObjectInfoPanel',
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
	            title: '查看<标的物>基础信息',
	            collapsible: true,
	            autoHeight:true,
	            //style : 'align:right',
	            anchor : '95%',
	            items : [{
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
							html : '<b>标的物名称：</b>'
					},{
						html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.name)
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
							html : '<b>规格型号：</b>'
					},{
						html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.standardSize)
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
							html : '<b>原价格：</b> ' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.originalPrice)+'元'
						},{
							html : '<b>使用年限：</b>' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.useYears)+'年'
						},{
							html : '<b>所有人：</b>' +getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.owner)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>认购价格：</b> ' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.buyPrice)+'元'
						},{
							html : '<b>数量：</b>' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.objectCount)
						},{
							html : '<b>购入日期：</b>' +getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.buyDate)
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
						html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.objectComment)
					}]
	            }]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<供货方>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            //width: 600,
	            anchor : '95%',
	            items : [{
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
							html : '<b>供货方名称：</b>'
					},{
						html : getNotUndefinedData(MortgageData.flObjectSupplior.Name)
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
							html : '<b>法人代表(负责人)名称：</b>'
					},{
						html : getNotUndefinedData(MortgageData.flObjectSupplior.legalPersonName)
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
							html : '<b>公司电话：</b> ' + getNotUndefinedData(MortgageData.flObjectSupplior.companyPhoneNum)
						},{
							html : '<b>联系人电话：</b>' + getNotUndefinedData(MortgageData.flObjectSupplior.connectorPhoneNum)
						},{
							html : '<b>传真电话：</b>' + getNotUndefinedData(MortgageData.flObjectSupplior.companyFax)
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>联系人姓名：</b> '+ getNotUndefinedData(MortgageData.flObjectSupplior.connectorName)
						},{
							html : '<b>联系人职位：</b>' + getNotUndefinedData(numTurnToString(MortgageData.flObjectSupplior.connectorPosition))
						},{
							html : '<b>供货地址：</b>' +  getNotUndefinedData(MortgageData.flObjectSupplior.companyAddress)
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
							html : getNotUndefinedData(MortgageData.flObjectSupplior.companyComment)
						}]
				}]
			},{
				xtype : 'fieldset',
				title : '租赁物保险信息',
				name:'leaseObjectInsurance',
				autoHeight : true,
				hidden:this.onlyFile,
				anchor : '95%',
				items:[leaseObjectInsuranceInfo]
			},{
					xtype : 'fieldset',
					title : '租赁物材料清单',
					collapsible : true,
					autoHeight : true,
					name:'zeren',
					anchor : '95%',
					items : [uploads]
			},{
				layout : 'column',
				xtype:'fieldset',
	            title: '查看<租赁物处置>详细信息',
	            collapsible: true,
	            autoHeight:true,
	            hidden:!isShowHandleMsg,
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
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>认购价格：</b> ' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.remnantEvaluatedPrice)+'元'
						}]
					},{
						columnWidth : .5,
						defaults : {
							layout : 'form',
							anchor : '99%',
							height : 30
						},
						items : [{
							html : '<b>实际价值：</b> ' + getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.remnantActualPrice)+'元'
						}]
					}
					]
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
							html : '<b>处置人：</b>'
					},{
						html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.handlePersonName)
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
							html : '<b>处置时间：</b>'
					},{
						html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.handleDate)
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
								html : '<b>处置备注信息：</b>'
						},{
							html : getNotUndefinedData(MortgageData.vLeaseFinanceObjectInfo.handleComment)
						}]
				}]
			
			}]
			});
		
	
		var win_seeCarInfo = new Ext.Window({
			title: '查看租赁标的物信息',
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
	};
	Ext.Ajax.request({
//		url : __ctxPath +'/creditFlow/pawn/pawnItems/getInfoPawnItemsList.do',
		url : __ctxPath +'/creditFlow/leaseFinance/project/seeObjectInfoFlLeaseobjectInfo.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			seeCarInformation(obj.data)
		},
		failure : function(response) {
			Ext.Msg.alert('状态', '操作失败，请重试');
		},
		params : {
			id : id
		}
	});
}