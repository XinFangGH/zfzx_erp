/**
 * @author 
 * @createtime 
 * @class SlFundintentUrgeForm
 * @extends Ext.Window
 * @description SlFundintentUrge表单
 * @company 智维软件
 */
MatchFundsForm = Ext.extend(Ext.Panel, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				MatchFundsForm.superclass.constructor.call(this, {
							id : 'MatchFundsForm'+this.fundIntentId,
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 600,
							width : 800,
							maximizable : true,
							title : '催收详细信息',
							buttonAlign : 'center',
							tbar : this.toolbar,
							buttons : [
										{
											text : '提交',
											iconCls : 'btn-save',
											scope : this,
											handler : this.save
										}, {
											text : '关闭',
											iconCls : 'close',
											scope : this,
											handler : function(){
											
											 var tabs = Ext.getCmp('centerTabPanel');
							               tabs.remove('SlFundintentUrgeFormWin'+this.fundIntentId);
											}
										}
							         ]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
              var oppositeID=this.oppositeID;
              var oppositeType=this.oppositeType;
             var businessType =this.businessType;
	 		var projectId =this.projectId;
	 		var fundIntentobj=this.fundIntentobj;
	 		this.toolbar=new Ext.Toolbar({
	 		    items:[
	 		    '->',{ 	iconCls : 'btn-detail',
					text : '项目详细',
					xtype : 'button',
					scope : this,
					handler : function() {
						var idPrefix = "";
						if(businessType=="SmallLoan"){idPrefix = "SmallLoanProjectInfo_";}
						
						Ext.Ajax.request({
							url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
							params : {
								businessType : businessType,
								projectId : projectId
							},
							method : 'post',
							success : function(resp, op) {
								var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
								showProjectInfoTab(record, idPrefix)
							},
							failure : function() {
								Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
							}
						})
					} },{
					iconCls : 'btn-customer',
						text : '客户详细',
						xtype : 'button',
						scope : this,
						handler : function() {
							if (oppositeType == 'person_customer') {
								seePersonCustomer(oppositeID);
							} else if (oppositeType == 'company_customer') {
								seeEnterpriseCustomer(oppositeID);
							}
							
							
							}
						
					},{
					iconCls : 'btn-print',
					text : '打印款项催收通知单',
					xtype : 'button',
					scope : this,
					handler : function() {
					} 
				}
	 		    
	 		    
	 		    ]
	 		})
	 		
	 		
				this.formPanel = new Ext.Panel({
				 layout:'column',
							bodyStyle : 'padding:10px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						    plain : true,
						   labelAlign : "right",
							// id : 'SlFundQlideForm',
							defaults : {
								anchor : '96%',
								labelWidth : 85,
								columnWidth : 1,
							    layout : 'column'
							},
							items : [  {
								     columnWidth : 0.3,
								     layout : 'form',
									 items :[
									 	{html:"客户名称："+(typeof(this.custonname)=="undefined"?"":this.custonname) },
									 	{style:'margin-top:5px',html:"电子邮箱：" +(typeof(this.email)=="undefined"?"": this.email)},
									 	{style:'margin-top:5px',html:"通信地址："+(typeof(this.area)=="undefined"?"":this.area) }
									]}
							  , {
								     columnWidth : 0.3,
								     layout : 'form',
									 items :[
									 	{html:"电话:"+(typeof(this.telephone)=="undefined"?"":this.telephone) },
									 	{style:'margin-top:5px',html:"收件人：" +(typeof(this.receiveMail)=="undefined"?"":this.receiveMail) },
									 	{style:'margin-top:5px',html:"邮政编码：" +(typeof(this.postcoding)=="undefined"?"": this.postcoding)}
									]}, {
								     columnWidth : 0.4,
								     layout : 'form',
									 items :[
									 	{html:"" },
									     {html:"" },
									 	{html:"" }
									]}, {
								     columnWidth : 1,
								     layout : 'form',
									 items :[{
									  html:'&nbsp;&nbsp;&nbsp;<br><b>联系人：</b><br>&nbsp;&nbsp;&nbsp;'
									  }
									 	
									]} , {
								     columnWidth : 1,
								     layout : 'form',
									 items :[
									 
									   this.relationPerson()
									 
									 	
									]}, {
								     columnWidth : 1,
								     layout : 'form',
									 items :[
									  {
									  html:'&nbsp;&nbsp;&nbsp;<br><b>催收款项：</b><br>&nbsp;&nbsp;&nbsp;'
									  }
									 	
									]} , {
								     columnWidth : 1,
								     layout : 'form',
									 items :[
									   this.slfundintentbyid()
									 
									 	
									]}, {
								     columnWidth : 1,
								     layout : 'form',
								     title : '',
									 items :[
									 {
									 	html:'&nbsp;&nbsp;&nbsp;<br><b>催收记录：</b><br>&nbsp;&nbsp;&nbsp;'
									 }
									]
									}, {
								     columnWidth : 1,
								     layout : 'form',
									 items :[
									 	this.urgegrid()
									]
									}, {
								     columnWidth : 1,
								     layout : 'form',
								     title : '',
									 items :[
									 {
									 	html:'&nbsp;&nbsp;&nbsp;<br><b>立即催收：</b><br>&nbsp;&nbsp;&nbsp;'
									 }
									]
									}, {
								     columnWidth : 1,
								     layout : 'form',
									 items :[
										this.urgeformpanel()
									]
									}
							 
						 ]})
				//加载表单对应的数据	
				
				
			},//end of the initcomponents

			/**
			 * 重置
			 * @param {} formPanel
			 */
			reset : function() {
				this.formPanel.getForm().reset();
			},
			/**
			 * 取消
			 * @param {} window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
			$postForm({
						formPanel:Ext.getCmp("urgeformpanel"),
						scope:this,
						url:__ctxPath + '/creditFlow/finance/saveSlFundintentUrge.do',
						callback:function(fp,action){
						 var tabs = Ext.getCmp('centerTabPanel');
							  tabs.remove('SlFundintentUrgeFormWin'+this.fundIntentId);
							   Ext.getCmp('SlFundIntentGrid'+this.tabflag).getStore().reload();
						}
					}
				);
			},//end of save
			slfundintentbyid:function(){
			
				var gridPanel1 = new HT.GridPanel({
					hiddenCm:true,
					//height:50,
					isShowTbar : false,
					showPaging :false,
					viewConfig: {  
		            	forceFit:true  
		            },
		            autoHeight:true,
					// 使用RowActions
					rowActions : false,
					id : 'SlFundIntentGrid',
					url : __ctxPath + "/creditFlow/finance/listbyIdSlFundIntent.do?Q_fundIntentId_SN_EQ="+this.fundIntentId,
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'incomeMoney','fundTypeName', 'intentDate','notMoney','remark'
							
							
						],
					columns : [ {
								header : '资金类型',
								dataIndex : 'fundTypeName',
								width : 130
							}, {
								header : '计划收入金额',
								dataIndex : 'incomeMoney',
								align : 'right',
								width : 150,
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							
							}, {
								header : '计划到账日',
								width : 100,
								dataIndex : 'intentDate',
								align : 'center'
						//		sortable:true
							}, {
								header : '未核销金额',
								dataIndex : 'notMoney',
								align : 'right',
								width : 150,
								sortable:true,
								renderer:function(v){
								return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	     }
							}, {
								header : '备注',
								dataIndex : 'remark',
								width : 520,
								align : 'center'
						//		sortable:true
							}
							
							
						]

						// end of columns
					});


				
				return gridPanel1;
				
				
			},
         urgegrid: function(){
         
           			var gridPanel2 = new HT.GridPanel({
				//	tbar : this.topbar,
           			isShowTbar : false,
					hiddenCm:true,
					//height:200,
					showPaging :false,
					viewConfig: {  
		            	forceFit:true  
		            },
		            autoHeight:true,
					// 使用RowActions
					rowActions : false,
					id : 'SlFundIntentGrid',
					url : __ctxPath + "/creditFlow/finance/listByfundIntentIdSlFundintentUrge.do?fundIntentId="+this.fundIntentId,
					fields : [{
								name : 'fundIntentId',
								type : 'int'
							}, 'urgeTitle','urgeType', 'urgeTitle','urgeContext', 'urgePerson',
							'urgeTypeName', 'urgePersonName','urgeTime'],
					columns : [ {
								header : '催收时间',
								align : 'center',
								dataIndex : 'urgeTime',
								width : 130
							}, {
								header : '催收人',
								dataIndex : 'urgePersonName',
								align : 'center',
								width : 150
							
							}, {
								header : '催收方式',
								width : 100,
								dataIndex : 'urgeTypeName',
								align : 'center'
						//		sortable:true
							}, {
								header : '标题',
								dataIndex : 'urgeTitle',
								align : 'center',
								width : 150,
								sortable:true
								
							}, {
								header : '催收内容',
								dataIndex : 'urgeContext',
								width : 520,
								align : 'center'
								
							
							}
							
							
						]

						// end of columns
					});
         
         return gridPanel2;
         },
         urgeformpanel :function(){
         var fundIntentId=this.fundIntentId;
         var fundIntentobj=this.fundIntentobj;
         var intentDatearray=fundIntentobj.intentDate.split("-");
          var projectStartDatearray=fundIntentobj.projectStartDate.split("-");
          var payInMoney=this.convertCurrency(fundIntentobj.payInMoney);
         var urgeformpanel = new Ext.FormPanel({
                            layout:'column',
							bodyStyle : 'padding:1px',
							autoScroll : true,
							monitorValid : true,
							frame : true,
						     plain : true,
						    labelAlign : "right",
							id : 'urgeformpanel',
							defaults : {
								anchor : '96%',
								labelWidth : 80,
								columnWidth : 1,
							    layout : 'column'
							},
							//defaultType : 'textfield',
							items : [
							
								 	{
									 columnWidth : 0.1,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									  fieldLabel : '催收方式',	
								 	  name : 'urgeType',
								      xtype:'label',
								      anchor : '98%'
										 },{
								 	  name : 'slFundintentUrge.urgeType',
								      xtype:'hidden',
								      value :1
										 },{
								 	  name : 'slFundintentUrge.fundIntentId',
								      xtype:'hidden',
								      value :fundIntentId
										 }]
								 },{ columnWidth : 0.3,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									xtype:'radio',
									boxLabel : '发送邮件【系统】',
									name : '1',
									id:"11"+this.fundIntentId,
									inputValue : true,
									checked :true,
									listeners:{
											scope :this,
										    check :function(obj,checked ){
										      var flag=Ext.getCmp("11"+this.fundIntentId).getValue();
							                 if(flag==true){
											      this.getCmpByName('slFundintentUrge.urgeType').setValue(1);
											       this.getCmpByName('slFundintentUrge.urgeContext').setValue("根据       字第　　 号借款合同，你单位(个人)于 "+projectStartDatearray[0]+"年"+projectStartDatearray[1]+"月"+projectStartDatearray[2]+"向我公司所借 贷款（大写）"+payInMoney+"，现尚余"+ fundIntentobj.notMoney+"元将于"+intentDatearray[0]+"年"+intentDatearray[1]+"月"+intentDatearray[2]+"日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。 ");
											      }
										    }
									}
								}]},{ columnWidth : 0.3,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									xtype:'radio',
									boxLabel : '发送短信【系统】',
									name : '1',
									id:"12"+this.fundIntentId,
									inputValue : false,
									listeners:{
										scope :this,
										    check :function(obj,checked ){
										    	 var flag=Ext.getCmp("12"+this.fundIntentId).getValue();
							                 if(flag==true){
											     this.getCmpByName('slFundintentUrge.urgeType').setValue(2);
											    this.getCmpByName('slFundintentUrge.urgeContext').setValue("现尚余"+ fundIntentobj.notMoney+"元将于"+intentDatearray[0]+"年"+intentDatearray[1]+"月"+intentDatearray[2]+"日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。 ");
											    }
										    }
									}
								}]},{ columnWidth : 0.3,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									xtype:'radio',
									boxLabel : '电话催收',
									name : '1',
									id:"13"+this.fundIntentId,
									inputValue : false,
									listeners:{
										scope :this,
										    check :function(obj,checked ){
										    	 var flag=Ext.getCmp("13"+this.fundIntentId).getValue();
							                 if(flag==true){
										      this.getCmpByName('slFundintentUrge.urgeType').setValue(3);
										      this.getCmpByName('slFundintentUrge.urgeContext').setValue("");
										     }
										   }
									}
								}]},{ 
									 columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									  fieldLabel : '催收标题',	
								 	  name : 'slFundintentUrge.urgeTitle',
								      xtype:'textfield',
								      anchor : '98%'
										 }]
								 },{
									 columnWidth : 1,
									  labelWidth : 73,
								     layout : 'form',
								      items :[{
									  fieldLabel : '催收内容',	
								 	  name : 'slFundintentUrge.urgeContext',
								 	  value:"根据       字第　　 号借款合同，你单位(个人)于"+projectStartDatearray[0]+"年"+projectStartDatearray[1]+"月"+projectStartDatearray[2]+"向我公司所借 贷款（大写）"+payInMoney+"，现尚余"+ fundIntentobj.notMoney+"元将于"+ fundIntentobj.notMoney+"元将于"+intentDatearray[0]+"年"+intentDatearray[1]+"月"+intentDatearray[2]+"日到期，请抓紧筹措资金按期归还借款，如到期未还，我公司即依照借款合同有关规定予以处理。",
								      xtype:'textarea',
								      anchor : '98%'
										 }]
								 }
							
							]
							
						});
   
         return urgeformpanel;
         
         },
     relationPersonListenterprise:function(){
     	var enterpriseId=this.enterpriseId;
       var cModel_relationPerson = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer(),
							{
								header : "联系人姓名",
								width : 130,
								sortable : true,
								dataIndex : 'relationName'
							}, {
								header : "职务",
								width : 150,
								sortable : true,
								dataIndex : 'relationJob'
							}, {
								header : "固定电话",
								width : 100,
								sortable : true,
								dataIndex : 'relationFixedPhone'
							}, {
								header : "移动电话",
								width : 150,
								sortable : true,
								dataIndex : 'relationMovePhone'
							},{
								header : "是否主联系人",
								width : 100,
								sortable : true,
								dataIndex : 'mark',
								renderer : function(r){
									if (r == true) {
										return '是';
									} else if (r == false) {
										return '否';
									} 
								}
							},{
								header : "家庭住址",
								sortable : true,
								dataIndex : 'relationFamilyAddress'
							}]);
							var jStore_relationPerson = new Ext.data.JsonStore( {
							url : __ctxPath + '/credit/customer/enterprise/queryListRelationPersonAction.do',
							totalProperty : 'totalProperty',
							root : 'topics',
							fields : [{name : 'id'},{name : 'relationName'},{name : 'relationJob'},{name : 'relationFixedPhone'},{name : 'relationMovePhone'},{name : 'relationFamilyAddress'},{name : 'bossName'},{name : 'bossPhone'},{name : 'remarks'},{name : 'mark'}],
							baseParams : {
								eid : enterpriseId
							},
							listeners : {
								'load':function(){
									gPanel_relationPerson.getSelectionModel().selectFirstRow() ;
								},
								'loadexception' : function(){
									Ext.ux.Toast.msg('提示','数据加载失败，请保证网络畅通！');			
								}
							}
						});
						jStore_relationPerson.load({
							params : {
								start : 0,
								limit : 1000
							}
						});
						var gPanel_relationPerson = new Ext.grid.GridPanel( {
							pageSize : 100,
							store : jStore_relationPerson,
							autoWidth : true,
							border : false ,
							autoHeight:true,
							colModel : cModel_relationPerson
							
						});
			         
           return gPanel_relationPerson
         
         },
         relationPersonListPerson:function(){
         var personIdValue=this.personIdValue;
         var jStore_PanelReliation = new Ext.data.JsonStore( {
				url : __ctxPath+'/credit/customer/person/ajaxQueryAllRelationPersonList.do',
				totalProperty : 'totalProperty',
				root : 'topics',
				fields : [ {
					name : 'id' 
				},{
					name : 'relationName'
				},{
					name : 'relationShip'
				}, {
					name : 'relationPhone'
				},{
					name : 'relationCellPhone'
				}, {
					name : 'relationShipValue'
				}, {
					name : 'personId'
				}]
			});
			jStore_PanelReliation.load({
				params : {
					personId : personIdValue
				}
			});
         var cModel_PanelReliation = new Ext.grid.ColumnModel(
					[
						new Ext.grid.RowNumberer(),
						{
							header : "姓名",
							width : 130,
							sortable : true,
						    dataIndex : 'relationName'
						}, {
							header : "关系",
							width : 150,
							sortable : true,
							dataIndex : 'relationShipValue'
						},{
							header : "电话",
							width : 100,
							sortable : true,
							dataIndex : 'relationPhone'
						},{
							id : 'id_autoExpandColumn',
							header : "手机",
							sortable : true,
							width : 150,
							dataIndex : 'relationCellPhone'
						}
						]);
         var gPanelReliation = new Ext.grid.GridPanel( {
				store : jStore_PanelReliation,
				colModel : cModel_PanelReliation,
				border : false ,
				viewConfig: {  
		            	forceFit:true  
		            },
				autoHeight:true
				
			});
			return gPanelReliation;
         },
          relationPerson :function(){
          
             if (this.oppositeType == 'person_customer') {
					     return	this.relationPersonListPerson()  
				} else if (this.oppositeType == 'company_customer') {
			          return	this.relationPersonListenterprise()
				}
          
          
          },
  convertCurrency:function (currencyDigits) {
// Constants:
 var MAXIMUM_NUMBER = 99999999999.99;
 // Predefine the radix characters and currency symbols for output:
 var CN_ZERO = "零";
 var CN_ONE = "壹";
 var CN_TWO = "贰";
 var CN_THREE = "叁";
 var CN_FOUR = "肆";
 var CN_FIVE = "伍";
 var CN_SIX = "陆";
 var CN_SEVEN = "柒";
 var CN_EIGHT = "捌";
 var CN_NINE = "玖";
 var CN_TEN = "拾";
 var CN_HUNDRED = "佰";
 var CN_THOUSAND = "仟";
 var CN_TEN_THOUSAND = "万";
 var CN_HUNDRED_MILLION = "亿";
 var CN_SYMBOL = "人民币";
 var CN_DOLLAR = "元";
 var CN_TEN_CENT = "角";
 var CN_CENT = "分";
 var CN_INTEGER = "整";
 
// Variables:
 var integral; // Represent integral part of digit number.
 var decimal; // Represent decimal part of digit number.
 var outputCharacters; // The output result.
 var parts;
 var digits, radices, bigRadices, decimals;
 var zeroCount;
 var i, p, d;
 var quotient, modulus;
 
// Validate input string:
 currencyDigits = currencyDigits.toString();
 if (currencyDigits == "") {
  alert("Empty input!");
  return "";
 }
 if (currencyDigits.match(/[^,.\d]/) != null) {
  alert("Invalid characters in the input string!");
  return "";
 }
 if ((currencyDigits).match(/^((\d{1,3}(,\d{3})*(.((\d{3},)*\d{1,3}))?)|(\d+(.\d+)?))$/) == null) {
  alert("Illegal format of digit number!");
  return "";
 }
 
// Normalize the format of input digits:
 currencyDigits = currencyDigits.replace(/,/g, ""); // Remove comma delimiters.
 currencyDigits = currencyDigits.replace(/^0+/, ""); // Trim zeros at the beginning.
 // Assert the number is not greater than the maximum number.
 if (Number(currencyDigits) > MAXIMUM_NUMBER) {
  alert("Too large a number to convert!");
  return "";
 }
 
// Process the coversion from currency digits to characters:
 // Separate integral and decimal parts before processing coversion:
 parts = currencyDigits.split(".");
 if (parts.length > 1) {
  integral = parts[0];
  decimal = parts[1];
  // Cut down redundant decimal digits that are after the second.
  decimal = decimal.substr(0, 2);
 }
 else {
  integral = parts[0];
  decimal = "";
 }
 // Prepare the characters corresponding to the digits:
 digits = new Array(CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE);
 radices = new Array("", CN_TEN, CN_HUNDRED, CN_THOUSAND);
 bigRadices = new Array("", CN_TEN_THOUSAND, CN_HUNDRED_MILLION);
 decimals = new Array(CN_TEN_CENT, CN_CENT);
 // Start processing:
 outputCharacters = "";
 // Process integral part if it is larger than 0:
 if (Number(integral) > 0) {
  zeroCount = 0;
  for (i = 0; i < integral.length; i++) {
   p = integral.length - i - 1;
   d = integral.substr(i, 1);
   quotient = p / 4;
   modulus = p % 4;
   if (d == "0") {
    zeroCount++;
   }
   else {
    if (zeroCount > 0)
    {
     outputCharacters += digits[0];
    }
    zeroCount = 0;
    outputCharacters += digits[Number(d)] + radices[modulus];
   }
   if (modulus == 0 && zeroCount < 4) {
    outputCharacters += bigRadices[quotient];
   }
  }
  outputCharacters += CN_DOLLAR;
 }
 // Process decimal part if there is:
 if (decimal != "") {
  for (i = 0; i < decimal.length; i++) {
   d = decimal.substr(i, 1);
   if (d != "0") {
    outputCharacters += digits[Number(d)] + decimals[i];
   }
  }
 }
 // Confirm and return the final output string:
 if (outputCharacters == "") {
  outputCharacters = CN_ZERO + CN_DOLLAR;
 }
 if (decimal == "") {
  outputCharacters += CN_INTEGER;
 }
 outputCharacters = CN_SYMBOL + outputCharacters;
 return outputCharacters;
}
		});