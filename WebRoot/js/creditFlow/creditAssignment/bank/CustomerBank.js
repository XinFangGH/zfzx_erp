Ext.ns('bank');

/**
 * @author: zcb
 * @class bank
 * @extends Ext.Panel
 * @description [bank]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
CustomerBank = Ext.extend(Ext.Window, {
	personData : null,
	isRead : false,
	enterpriseid : null,
	constructor : function(_cfg) {
		if (_cfg == null) {
			_cfg = {};
		};
	
		Ext.applyIf(this, _cfg);
		this.initUIComponents()
		CustomerBank.superclass.constructor.call(this, {
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height : 450,
			width : 650,
			maximizable : true,
			title : '贷款账户信息',
			buttonAlign : 'center',
			buttons : [{
						text : '保存',
						iconCls : 'btn-save',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.save
					}, {
						text : '重置',
						iconCls : 'btn-reset',
						scope : this,
						disabled : this.isReadOnly,
						handler : this.reset
					}, {
						text : '取消',
						iconCls : 'btn-cancel',
						scope : this,
						handler : this.cancel
					}]
		});
	},
		initUIComponents : function() {
		
		this.formPanel = new Ext.FormPanel({
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true,
			monitorValid : true,
			labelWidth : 110,
			autoScroll : true,
			bodyStyle : 'overflowX:hidden',
			layout : 'form',
			url:this.url,
			border : false,
			items : [{layout : 'column',
						xtype : 'fieldset',
						title : '银行账户信息',
						collapsible : true,
						autoHeight : true,
						anchor : '100%',
						items : [{
							columnWidth : 0.9,
							labelWidth : 90,
							layout : 'column',
							items : [{
								columnWidth : .5,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
												xtype:'combo',
										          mode : 'local',
									               displayField : 'name',
									              valueField : 'id',
									              editable : false,
									              readOnly : this.isRead,
									                 width : 70,
									                 store : new Ext.data.SimpleStore({
											        fields : ["name", "id"],
										            data : [["个人", "0"],
													     	["公司", "1"]]
									              	}),
										            triggerAction : "all",
									                hiddenName:"enterpriseBank.openType",
								                	fieldLabel : '开户类型',	
								                	anchor : '100%',
								                	allowBlank:false,
								                	value : personData == null?null:personData.openType,
										          	name : 'enterpriseBank.openType',
										          	listeners : {
															scope : this,
															select : function(combox, record, index) {
															var v = record.data.id;
															var obj = Ext.getCmp('accountTypeid');
															obj.enable();
															var arrStore = null;
															if(v==0){
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["个人储蓄户", "0"]]
																});
															}else{
																arrStore = new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["基本户", "1"],["一般户", "2"]]
												              	});
															}
															obj.clearValue();
								                            obj.store = arrStore;
								                            obj.view.setStore(arrStore);
								                      //      arrStore.load();
														},
														afterrender : function(combox) {
															var st = combox.getStore();
															st.on("load", function() {
																if(combox.getValue()=='' || combox.getValue()==null){												
																	combox.setValue(st.getAt(0).data.itemId);
																	combox.clearInvalid();
																}else{
																	combox.setValue(combox.getValue());
																	combox.clearInvalid();
																}
															})
														}
													
											}
								 }, {
										fieldLabel : "银行名称",
										xtype : "combo",
										displayField : 'itemName',
										valueField : 'itemId',
										allowBlank:false,
										readOnly : this.isRead,
										triggerAction : 'all',
										store : new Ext.data.ArrayStore({
											url : __ctxPath
															+ '/creditFlow/common/getBankListCsBank.do',
													fields : ['itemId', 'itemName'],
													autoLoad : true
										}),
										mode : 'remote',
										hiddenName : "enterpriseBank.bankid",
										editable : false,
										blankText : "银行名称不能为空，请正确填写!",
										anchor : "100%",
										listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
								},{
											name : 'enterpriseBank.areaId',
											xtype:'hidden'
								},{					
											name : 'enterpriseBank.areaName',
										    hiddenName : 'enterpriseBank.areaName',
											fieldLabel : '开户地区',	
											anchor : '100%',
											readOnly : this.isRead,
											allowBlank:false,
					                      	xtype:'trigger',
											triggerClass :'x-form-search-trigger',
											editable : false,
											scope : this,
											onTriggerClick : function(){
												var com=this
												var selectBankLinkMan = function(array){
													var str="";
													var idStr=""
													for(var i=array.length-1;i>=0;i--){
														str=str+array[i].text+"-"
														idStr=idStr+array[i].id+","
													}
													if(str!=""){
														str=str.substring(0,str.length-1);
													}
													if(idStr!=""){
														idStr=idStr.substring(0,idStr.length-1)
													}
													com.previousSibling().setValue(idStr)
													com.setValue(str);
												};
												selectDictionary('area',selectBankLinkMan);
											}
									},{
									 
										fieldLabel : '开户名称',	
		                              	name : 'enterpriseBank.name',
										xtype:'textfield',
										anchor : '100%',
										readOnly : this.isRead,
										allowBlank:false
								 	}]

								}]
							},{
								columnWidth : .5,
								labelWidth : 90,
								layout : 'column',
								items : [{
									columnWidth : 1,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									scope : this,
									items : [{
											xtype:'combo',
								            mode : 'local',
							                displayField : 'name',
							                valueField : 'id',
							                editable : false,
							                width : 70,
								            triggerAction : "all",
							                hiddenName:"enterpriseBank.accountType",
						                	fieldLabel : '账户类型',	
						                	anchor : '100%',
						                	readOnly : this.isRead,
						                	allowBlank:false,
						                	store:new Ext.data.SimpleStore({
												        	fields : ["name", "id"],
											            data : [["个人储蓄户", "0"],["基本户", "1"],["一般户", "2"]]
													}),
								          	name : 'enterpriseBank.accountType',
								          	listeners : {
											scope : this,
											afterrender : function(combox) {
												var st = combox.getStore();
												st.on("load", function() {
													combox.setValue(combox.getValue());
													
												})
												combox.clearInvalid();
											}
											
										}
							 		},{	
						                	xtype: 'radiogroup',
							                fieldLabel: '银行开户类别',
							                items: [
							                    {boxLabel: '本币开户', name: 'enterpriseBank.openCurrency',  disabled : this.isRead,inputValue: "0",checked:personData == null?true:(personData.openCurrency==0?true:false)},
							                    {boxLabel: '外币开户', name: 'enterpriseBank.openCurrency',  disabled : this.isRead,inputValue: "1",checked:personData == null?false:(personData.openCurrency==1?true:false)}
							                ]
				                	}, {
									
										fieldLabel : "网点名称",
	                                    name : 'enterpriseBank.bankOutletsName',
									    xtype:'textfield',
									
										
										allowBlank:false,
										readOnly : this.isRead,
										
										anchor : "100%"
									
									
									},{
											fieldLabel : '卡号',	
										 	name : 'enterpriseBank.accountnum',
										  	maxLength: 100,
										  	xtype:'textfield',
										  	readOnly : this.isRead,
										  	anchor : '100%',
										  	allowBlank:false
								}]

							}]
							},{
				              	columnWidth : 1,
								layout : 'form',
								labelWidth : 90,
								hidden:!this.isHidden,
								items :[{
									xtype : 'textarea',
									 anchor : '100%',
									fieldLabel : '备注',
									readOnly : this.isRead,
									height : 80,
									name : 'enterpriseBank.remarks'
								}]
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.enterpriseid'
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.id'
				              },{
				              	xtype : 'hidden',
								name : 'enterpriseBank.isEnterprise',
								value : "1"
				              }, {
								xtype : 'hidden',
								name : 'enterpriseBank.isInvest',
								value : "3"//表示这个属于债权转让的客户
							}, {
								xtype : 'hidden',
								name : 'enterpriseBank.isCredit',
								value : "0"
							}]
						}]
					
						}]});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			
			this.formPanel.loadData({
						url : __ctxPath
								+ '/creditFlow/customer/common/getAccountBankEnterpriseBank.do?id='
								+ this.id,
						root : 'data',
						preName : 'enterpriseBank',
						scope : this,
						success : function(resp, options) {
							var result = Ext.decode(resp.responseText);
							if(result!=null&&result.data.areaName!=null){
								this.getCmpByName('enterpriseBank.areaName').setValue(result.data.areaName)
							}
						}
					});
		}

	},
	cancel:function(){
		this.close()
	},
	reset : function(){
		this.formPanel.getForm().reset()
	},
	save : function(){
		$postForm( {
			formPanel : this.formPanel,
			scope : this,
			msg : '保存成功',
			url : __ctxPath + '/creditFlow/customer/common/updateAccountEnterpriseBank.do',
			callback : function(fp, action) {
				var formPanel = Ext.getCmp("getMoneyPanel");
				
				if(this.gridPanel || formPanel){
					if(this.type!=null&&typeof(this.type)!="undefined"&&this.type==2){
						formPanel.loadData({
							url : __ctxPath+ '/creditFlow/creditAssignment/customer/getMoneyInfoCsInvestmentperson.do',
							params : {
								investPersonId : this.id
							},
							root : 'data',
							preName : ['enterpriseBank', 'blance','csInvestmentperson','obSystemAccount'],
							success : function(response, options) {
								var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								
								if (null != alarm_fields.data.obSystemAccount.availableInvestMoney) {
									this.getCmpByName('availableInvestMoney1').setValue(alarm_fields.data.obSystemAccount.availableInvestMoney);
								}
								
							}
						});
					}else{
						this.gridPanel.loadData({
							url : __ctxPath
									+ '/creditFlow/customer/common/getAccountBankEnterpriseBank.do?id='
									+ this.id,
							root : 'data',
							preName : 'enterpriseBank'
						});
					}
				}
				this.close();
			}
		});
	}
});