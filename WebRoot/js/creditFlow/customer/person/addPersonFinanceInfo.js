//function addPersonFinanceInfo(page)
//addInvestObligation
addPersonFinanceInfo = Ext.extend(Ext.Window, {
	isLook : false,
	isRead : false,
	isflag : false,
	// 构造函数
	investPersonPanel : null,
	constructor : function(_cfg) {
		
		if (_cfg == null) {
			_cfg = {};
		};
		if(typeof(_cfg.isReadOnly) != "undefined")
		{
			this.isRead=_cfg.isReadOnly;
		};
		if(null!=_cfg.personData){
		    this.isflag=true;
		};
		if (typeof(_cfg.isLook) != "undefined") {
			this.isLook = _cfg.isLook;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		addPersonFinanceInfo.superclass.constructor.call(this, {
					id : 'addPersonFinanceInfo',
					layout : 'fit',
					autoScroll:true,
					items : [this.formPanel],
					modal : true,
					height : 600,
					width : 1100,
					maximizable : true,
					title : '家庭经济情况',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								hidden : this.isLook,
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								hidden : this.isLook,
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								hidden : this.isLook,
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		this.PersonHouseView =new PersonHouseInfo({
			personId:this.personId,
			isHiddenAddBtn:this.isLook,
			isHiddenDelBtn:this.isLook,
			isReadOnly:this.isLook,
			isHiddenSeeBtn:false
		})
		
		this.PersonCarView =new PersonCarInfo({
			personId:this.personId,
			isHiddenAddBtn:this.isLook,
			isHiddenDelBtn:this.isLook,
			isReadOnly:this.isLook,
			isHiddenSeeBtn:false
		})
		this.formPanel = new Ext.FormPanel( {
			//url :  __ctxPath + '/credit/customer/person/addSpouse.do',
			monitorValid : true,
			bodyStyle:'padding:10px',
			autoScroll : true ,
			labelAlign : 'right',
			buttonAlign : 'center',
			frame : true ,
			border : false,
			layout : 'form',
			labelWidth:100,
			items : [{
					xtype : 'fieldset',
					title : '资产信息',
					collapsible : true,
					autoHeight : true,
					layout : 'column',
					bodyStyle : 'padding-left: 0px',
					items : [{
						columnWidth:.5,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items:[{
									xtype : "hidden",
									name : "person.id"
								},{
									xtype : "numberfield",
									fieldLabel : '总资产(万元)',
									name : "person.grossasset",
									readOnly : this.isLook,
									anchor : '100%'
								},
								{
									xtype : "numberfield",
									fieldLabel : '家庭财产(万元)',
									name : "person.homeasset",
									readOnly : this.isLook,
									anchor : '100%'
								},
								{
									xtype : "numberfield",
									fieldLabel : '总负债(万元)',
									name : "person.grossdebt",
									readOnly : this.isLook,
									anchor : '100%'
								},
								{
									xtype : "numberfield",
									fieldLabel : '年总支出(万元)',
									name : "person.yeargrossexpend",
									readOnly : this.isLook,
									anchor : '100%'
								}]
					}]
				},{
					xtype : 'fieldset',
					title : '房产信息',
					name:"personHouseInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.PersonHouseView]
				},{
					xtype : 'fieldset',
					title : '车辆信息',
					name:"personCarInfo",
					collapsible : true,
					autoHeight : true,
					bodyStyle : 'padding-left: 0px',
					items : [this.PersonCarView]
				},{
					xtype : 'fieldset',
					title : '工资卡信息',
					collapsible : true,
					autoHeight : true,
					layout : 'column',
					bodyStyle : 'padding-left: 0px',
					items :[{
		    			rowspan :3,
		    			width: 430,
						items :[{
							layout: 'table',
							layoutConfig: {
		        				columns: 2
		    				},
		    				defaults: {
		    					bodyStyle:'padding:1.5px;',
		       	 				width :430
		    				},
		    				items :[{
		    					width: 120,
		    					rowspan :3,
		    					html :'<span style="font-size:10pt">本人工资代发信息</span>'
		    				},{
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'textfield',
									fieldLabel : '工资开户行',
									readOnly : this.isLook,
									name : 'person.wagebank'
									/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
									regexText : '开户行必须为中文',*/
//									value : data.wagebank
		    					}]
		    				},{
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'textfield',
									fieldLabel : '工资开户人',
									name : 'person.wageperson',
									readOnly : this.isLook
//									value : data.wageperson
		    					}]
		    				},{
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'numberfield',
									fieldLabel : '工资开户账号',
									name : 'person.wageaccount',
									readOnly : this.isLook
									/*regex : /^(\d{4}[\s\-]?){4}\d{3}$/g,
									regexText : '账号格式不正确',*/
//									value : data.wageaccount
		    					}]
		    				},{
				    			colspan: 2,
				    			html:'<hr>'
		    				},//工资end
		    				{
		    					width: 120,
		    					rowspan :3,
		    					html :'<span style="font-size:10pt">配偶工资代发信息</span>'
		    				},{
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'textfield',
									fieldLabel : '工资开户行',
									name : 'person.matebank',
									readOnly : this.isLook
									/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
									regexText : '开户行必须为中文',*/
//									value : data.matebank
		    					}]
		    				},{
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'textfield',
									fieldLabel : '工资开户人',
									name : 'person.mateperson',
									readOnly : this.isLook
									/*regex : /^[\u4e00-\u9fa5]{1,10}$/,
									regexText : '开户行必须为中文',*/
//									value : data.mateperson
		    					}]
		    				},{
		    					
		    					width :280,
		    					layout : 'form',
		    					defaults : {anchor:'95%'},
		    					items :[{
		    						xtype : 'numberfield',
									fieldLabel : '工资开户账号',
									name : 'person.mateaccount',
									readOnly : this.isLook
									/*regex : /^(\d{4}[\s\-]?){4}\d{3}$/g,
									regexText : '账号格式不正确',*/
//									value : data.mateaccount
		    					}]
		    				}]
	    				}]
					}]
				}]
		})
		var storepayintentPeriod="[";
		  for (var i = 1; i <31; i++) {
				storepayintentPeriod = storepayintentPeriod + "[" + i
						+ ", " + i + "],";
			}
			
			storepayintentPeriod = storepayintentPeriod.substring(0,storepayintentPeriod.length - 1);
			storepayintentPeriod = storepayintentPeriod + "]";
			var obstorepayintentPeriod = Ext.decode(storepayintentPeriod);
			
			var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel){
				
				Ext.Ajax.request({
					url : __ctxPath + "/project/getIntentDateSlSmallloanProject.do",
					method : 'POST',
					scope:this,
					params : {
						payAccrualType:payAccrualType,
						dayOfEveryPeriod:dayOfEveryPeriod,
						payintentPeriod:payintentPeriod,
						startDate:startDate
					},
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						obj.intentDate;
						intentDatePanel.getCmpByName("obObligationInvestInfo.investEndDate").setValue(obj.intentDate);
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					}
				});
			}
			
		
		
		// this.gridPanel.addListener('rowdblclick', this.rowClick);
		// 加载表单对应的数据
		if (this.personId != null && this.personId != 'undefined') {
			var   panel =this;
			
			this.formPanel.loadData({
						url : __ctxPath + '/creditFlow/customer/person/getByIdPerson.do?personId='+ this.personId,
						root : 'data',
						preName : 'person',
						scorp:this,
						success : function(response, options) {
							var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								panel.getCmpByName('person.grossasset').setValue(Ext.util.Format.number(alarm_fields.data.grossasset,'0,000.00'));
							}
					});
		}

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
		this.getCmpByName('obObligationInvestInfo.investPersonName').setValue(this.investPersonName);
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		var win=this;
			var personId=this.getCmpByName("person.id").getValue();
			var personCarInfos=this.PersonCarView;
			var personcarDate=getPersonFinanceInfoData(personCarInfos);
			var personHouseInfos=this.PersonHouseView;
			var personHouseDate=getPersonFinanceInfoData(personHouseInfos);
				
			$postForm({
						formPanel : this.formPanel,
						scope : this,
						url : __ctxPath + '/creditFlow/customer/person/saveFinancePerson.do',
						params : {
								'personId':personId,
								'personCarInfo':personcarDate,
								'personHouseInfo':personHouseDate
							},
						callback : function(fp, action) {
							win.close();
						}
					});
		}// end of save

});