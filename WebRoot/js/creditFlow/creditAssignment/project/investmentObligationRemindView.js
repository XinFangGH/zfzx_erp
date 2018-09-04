//investmentObligationRemindView债权到期提醒
investmentObligationRemindView = Ext.extend(Ext.Panel, {
	hiddenInfo:false,
	hiddenMapping:false,
	obligationState:"",//用来区分查找全部的债权产品还是未匹配的债权产品
	// 构造函数
	constructor : function(_cfg) {
		this.titlePrefix="债权匹配情况";
		this.obligationState=1;
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		investmentObligationRemindView.superclass.constructor.call(this, {
			id : 'investmentObligationRemindView',
			title : this.titlePrefix,
			region : 'center',
			layout : 'border',
			iconCls : "btn-tree-team17",
			items : [ this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 70,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
            		xtype : 'hidden',
            		name:"obligationState",
            		value:this.obligationState
            },{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '产品名称',
						name : 'obligationName',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					hidden:this.hiddenMapping,
					border : false,
					items : [{
						fieldLabel : '债权人',
						name : 'investName',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '债权购买日',
						name : 'investStartDate',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'datefield'
					},{
						fieldLabel : '债权到期日',
						name : 'investEndDate',
						anchor : "100%",
						format : 'Y-m-d',
						xtype : 'hidden'
					}]
		     	},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						width : 40,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		},{
	     			
		     		columnWidth :1,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '到期时间',
						//name : 'investEndDate',
						xtype : 'radiogroup',
						items:[{
								boxLabel: '全部', 
								name: '1',  
								id:"checkAll",
								//checked:true,
								inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkAll").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
										      this.search();
									      
									    }
								    }
								}
							},{
							   boxLabel: '今天', 
							   name: '1',
							   id:"checktoday",
							  inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checktoday").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											  /* var time=now.getTime();
											   time-=1000*60*60*24*2;//加上3天
											  now.setTime(time);*/
											 this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									    }
								    }
								}
							},{
							   boxLabel: '三天后', 
							   name: '1', 
							   id:"checkThreeDay",
							   inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkThreeDay").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   
											   var time=now.getTime();
											   time+=1000*60*60*24*2;//加上3天
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									      
									    }
								    }
								}
							},{
							   boxLabel: '一周后', 
							   name: '1', 
							   id:"checkOneWeek",
							  inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkOneWeek").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   var time=now.getTime();
											   time+=1000*60*60*24*6;
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									      
									    }
								    }
								}
							},{
							   boxLabel: '二周后', 
							   name: '1', 
							   id:"checkTwoWeek",
							   inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkTwoWeek").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   var time=now.getTime();
											   time+=1000*60*60*24*13;
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									      
									    }
								    }
								}
							},{
							   boxLabel: '一个月后', 
							   name: '1', 
							   id:"checkOneMonth",
							    inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkOneMonth").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   var time=now.getTime();
											   time+=1000*60*60*24*29;
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search;
									      
									    }
								    }
								}
							},{
							   boxLabel: '二个月后', 
							   name: '1',  
							   id:"checkTwoMonth",
							    inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkTwoMonth").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   var time=now.getTime();
											   time+=1000*60*60*24*59;
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									      
									    }
								    }
								}
							},{
							   boxLabel: '三个月后', 
							   name: '1',  
							   id:"checkThreeMonth",
							    inputValue : false,
								listeners:{
									scope :this,
								    check :function(){
									   	var flag=Ext.getCmp("checkThreeMonth").getValue();
									    if(flag==true){
											  this.getCmpByName("investEndDate").setValue();
											   var now=new Date();
											   var time=now.getTime();
											   time+=1000*60*60*24*89;
											  now.setTime(time);
											  this.getCmpByName("investEndDate").setValue(now);
										      this.search();
									      
									    }
								    }
								}
							}
						]
					}]
		     	
	     		}]
		});
		if(this.managerType=="Info"){
		
		}else if(this.managerType=="Mapping"){
		
		}else if(this.managerType=="closeObligation"){
		
		}else if(this.managerType=="openObligation"){
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '激活债权产品',
					xtype : 'button',
					scope : this/*,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler : this.editObligationProduct*/
				},{
					iconCls : 'btn-detail',
					text : '匹配详情',
					xtype : 'button',
					scope : this,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler :this.seeMappingObligationInfo
				},{
					iconCls : 'btn-detail',
					text : '查看项目信息',
					xtype : 'button',
					scope : this,
					//hidden : isGranted('_seePro_p' + this.projectStatus)? false: true,
					handler : function() {
						//detailPro(this.gridPanel, 'SmallLoanProjectInfo_')
					}
				}]
			});
		}else {
			this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-edit',
					text : '查看收益计划表',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_obligation_detail_1')? false: true,
					handler : this.editObligationProduct
				}]
			});
			
		}
		
			
			
		this.gridPanel = new HT.GridPanel({
			name : 'obligationMappingInfoGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			//plugins : [checkColumn,seniorcheckColumn],
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/bank/listInvestPersonObObligationInvestInfo.do?obligationState="+this.obligationState,
			fields : [{
				name : 'id',
				type : 'int'
			}, 'investMentPersonId','obligationId', 'obligationName', 'obligationAccrual', 'investQuotient','investMoney', 'investStartDate','investEndDate',
					'shopId','creatorId', 'companyId', 'investObligationStatus',
					 'systemInvest','investRate', 'orgName', 'creatorName','investName','cardNumber', 'cellPhone', 'projectName',
					 'projectNumber','obligationNumber', 'projectMoney', 'minInvestMent',
					 'totalQuotient','payintentPeriod'],
			listeners : {
				scope : this,
				afteredit : function(e) {/*
					if (e.originalValue != e.value){
						var args ;
						if(e.field =='rechargeConfirmStatus'){
								args = {
										'rechargeConfirmStatus' : e.value,
										'rechargeLevel':this.rechargeLevel,
										'id' : e.record.data['id']
							}
						}
						if(e.field =='seniorValidationRechargeStatus'){
								args = {
										'seniorValidationRechargeStatus' : e.value,
										'rechargeLevel':this.rechargeLevel,
										'id' : e.record.data['purchaseId']
							}
						}
						if(e.field =='rechargeConfirmStatus' ||e.field =='seniorValidationRechargeStatus'){
							var panel =this;
							Ext.Ajax.request({
										url : __ctxPath + "/obligation/fiance/ConfirmStatusObAccountDealInfo.do",
										method : 'POST',
										scope :this,
										success : function(response, request) {
											e.record.commit();
											panel.getCmpByName("confirmRechargeGrid").getStore().reload();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params: args
									})
						}
					}
				*/}
			},

			columns : [{
				header : '债权人名称',
				width : 100,
				hidden:this.hiddenInfo,
				dataIndex : 'investName'
			}, {
				header : '匹配项目',
				width : 200,
				dataIndex : 'projectName'
			}, {
				header : '项目期限(月)',
				width : 100,
				dataIndex : 'payintentPeriod'
			},{
				header : '投资份额',
				width : 100,
				dataIndex : 'investQuotient',
				renderer : function(value) {
					if (value == "") {
						return "";
					} else {
						return value+ "份";
					}
				}
			}, {
				header : '投资金额（元）',
				width : 100,
				dataIndex : 'investMoney',
				hidden:this.hiddenInfo,
				align : 'right',
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '资金投资日',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenInfo,
				dataIndex : 'investStartDate'
			},{
				header : '债权截止日',
				align : 'right',
				width : 110,
				sortable : true,
				hidden:this.hiddenInfo,
				dataIndex : 'investEndDate'
			}, {
				header : '债权比例（%）',
				width : 100,
				dataIndex : 'investRate',
				hidden:this.hiddenMapping,
				renderer : function(value) {
					if (value == "") {
						return "0.00";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "%";
					}
				}
			}]
		});
		/*this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);*/
	
	},// end of the initComponents()
	
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	// 按条件搜索
	search : function() {
		$search({
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//添加债权产品
	addObligationProduct:function(){},
	//查看收益计划表
	editObligationProduct:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		} else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		} else {
			var window_see = new Ext.Window({
						title : '查看收益计划表',
						layout : 'fit',
						width : (screen.width - 180) * 0.7,
						maximizable : true,
						height : 500,
						closable : true,
						modal : true,
						plain : true,
						border : false,
						autoScroll:true,
						items : [{
							border : false,
							autoWidth : true,
							autoHeight : true,
							autoScroll:true,
							anchor : '100%',
							items : [
								new obligationFundIntentViewVM({
											isHiddenautocreateBtn:true,
											obligationInfoId:s[0].data.id,
											investPersonId:s[0].data.investMentPersonId,
											keyValue:"oneSlFundIntentCreat",
											projectId : s[0].data.obligationId
									})
								]
						}],
						listeners : {
							'beforeclose' : function(panel) {
								window_see.destroy();
							}
						}
					});
			window_see.show();
		}
	
	
	},
	//查看债权产品
	seeObligationProduct:function(){},
	//债权产品匹配投资人
	MappingInvestPerson:function(){},
	//查看债权产品匹配详情
	seeMappingObligationInfo:function(){}
	//obligationInvestInfo
});
