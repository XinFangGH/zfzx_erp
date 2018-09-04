//confirmRechargeView
EarlyOutList = Ext.extend(Ext.Panel, {
	rechargeConfirmStatus : 1,
	dealRecordStatus:2,
	titlePrefix : "",
	seniorHidden:false,
	Confirmhidden:false,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		
		// 初始化组件
		this.initUIComponents();
		
		// 调用父类构造
		EarlyOutList.superclass.constructor.call(this, {
					id : 'EarlyOutList'+this.keystr+this.Q_checkStatus_SN_EQ+this.buttonType,
					layout : 'border',
					items : [this.searchPanel ,this.gridPanel],
					modal : true,
					height : 550, 
					autoWidth : true,
					boder:0,
					maximizable : true,
					iconCls : 'btn-tree-team30',
					//title : this.titlePrefix ,
					buttonAlign : 'center'

				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		
		this.searchPanel = new Ext.FormPanel({
//			rechargeLevel:this.rechargeLevel,
			layout : 'column',
			region : 'north',
			id:'EarlyOutListsearchPanel'+this.keystr+this.Q_checkStatus_SN_EQ+this.buttonType,
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
            items : [{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '申请提前支取的日期',
						name : 'Q_createDate_D_EQ',
						format:"Y-m-d",
						anchor : "100%",
						xtype : 'datefield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 120,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '理财计划名称',
						name : 'Q_plManageMoneyPlanBuyinfo.mmName_S_LK',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '投资人',
						name : 'Q_plManageMoneyPlanBuyinfo.investPersonName_S_EQ',
						anchor : "100%",
						xtype : 'textfield'
					}]
		     	},/*{
						xtype : 'hidden',
						name : 'Q_keystr_S_EQ',
						value:this.keystr
		     	},*/{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :50,
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
					labelWidth :50,
					items :[{
						text : '重置',
						style :'margin-left:30px',
						xtype : 'button',
						scope : this,
						//width : 40,
						anchor : "90%",
						iconCls : 'btn-reset',
						handler : this.reset
					}]
	     		}]
		});
		
		this.topbar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-detail',
						text : '审核',
						xtype : 'button',
						scope : this,
						id:"checkEarlyOutbtn"+this.keystr+this.Q_checkStatus_SN_EQ+this.buttonType,
						handler : this.checks,
//						hidden : this.Q_checkStatus_SN_EQ == 0 ? this.buttonType ==1 ? true : false : true
						hidden : (this.buttonType ==1 || this.buttonType==2|| this.buttonType==3)? true : false
					},{
						iconCls : 'btn-setting6',
						text : '返款',
						xtype : 'button',
						scope : this,
						handler : this.backMoney,
						hidden : this.buttonType == 1 ? false : true
					}]
		});	

	  /* var params1={
	       Q_checkStatus_SN_EQ:this.Q_checkStatus_SN_EQ,
	       Q_keystr_S_EQ:this.keystr
       };*/
		var buttonType = this.buttonType;
		var url = __ctxPath
					+ "/creditFlow/financingAgency/listbystatePlEarlyRedemption.do?Q_checkStatus_SN_EQ="+this.Q_checkStatus_SN_EQ
					+"&Q_keystr_S_EQ="+this.keystr;
		if(buttonType == 1){
			url = __ctxPath
					+ "/creditFlow/financingAgency/listbystatePlEarlyRedemption.do?Q_checkStatus_SN_EQ="+this.Q_checkStatus_SN_EQ
					+"&Q_keystr_S_EQ="+this.keystr+"&Q_loanerRepayMentStatus_N_EQ=1";
		}else if(buttonType == 0){
			url = __ctxPath
					+ "/creditFlow/financingAgency/listbystatePlEarlyRedemption.do?Q_checkStatus_SN_EQ="+this.Q_checkStatus_SN_EQ
					+"&Q_keystr_S_EQ="+this.keystr+"&Q_loanerRepayMentStatus_N_LT=1";
		}
		var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			name : 'EarlyOutListGrid',
			id:"EarlyOutListGrid"+this.keystr+this.Q_checkStatus_SN_EQ+this.buttonType,
			region : 'center',
			plugins : [summary],
			tbar : this.topbar,
			notmask : true,
			//baseParams :params1,
			// 不适用RowActions
		//	plugins : [checkColumn1,checkColumn2],
			rowActions : false,
				url : url,
			fields : [{
						name : 'earlyRedemptionId',
						type : 'int'
					}, 'plManageMoneyPlanBuyinfo.mmplanId', 'plManageMoneyPlanBuyinfo.buyDatetime', 'plManageMoneyPlanBuyinfo.investPersonId',
					'plManageMoneyPlanBuyinfo.investPersonName', 'plManageMoneyPlanBuyinfo.buyMoney', 'plManageMoneyPlanBuyinfo.startinInterestTime',
					'plManageMoneyPlanBuyinfo.endinInterestTime', 'plManageMoneyPlanBuyinfo.orderlimit', 'plManageMoneyPlanBuyinfo.promisDayRate',
					'plManageMoneyPlanBuyinfo.promisIncomeSum', 'plManageMoneyPlanBuyinfo.currentMatchingMoney',
					'earlyDate','createDate','checkStatus',
					'plManageMoneyPlanBuyinfo.currentGetedMoney', 'plManageMoneyPlanBuyinfo.optimalDayRate', 'plManageMoneyPlanBuyinfo.keystr', 'plManageMoneyPlanBuyinfo.mmName','plManageMoneyPlanBuyinfo.isAtuoMatch','plManageMoneyPlanBuyinfo.firstProjectIdcount','plManageMoneyPlanBuyinfo.state'],
			columns : [ {
						header : '投资人',
						width : 110,
						dataIndex : 'plManageMoneyPlanBuyinfo.investPersonName'
					}, {
						header : '理财计划名称',
						summaryRenderer : totalMoney,
						dataIndex : 'plManageMoneyPlanBuyinfo.mmName',
						width : 130
					}, {
						header : '购买金额（元）',
						dataIndex : 'plManageMoneyPlanBuyinfo.buyMoney',
						width : 110,
						summaryType : 'sum',
						align : 'right',
						renderer : function(v) {
							return Ext.util.Format.number(v, ',000,000,000.00');
						}
					}, {
						header : '计息起日',
						width : 110,
						align:'center',
						dataIndex : 'plManageMoneyPlanBuyinfo.startinInterestTime'
					}, {
						header : '计息止日',
						width : 110,
						align:'center',
						dataIndex : 'plManageMoneyPlanBuyinfo.endinInterestTime'
					}, {
						header : '申请提前支取的日期',
						width : 110,
						align:'center',
						dataIndex : 'createDate'
					}, {
						header : '审核状态',
						width : 110,
						align:'center',
						dataIndex : 'checkStatus',
						renderer:function(v){
						   if(v==0){
						     return "待审核";
						   }else if(v==1){
						   	return "通过";
						   }else if(v==3){
						    return "驳回";
						   }
							
						}
					}]/*,
			listeners : {
				scope : this,
				afteredit : function(e) {
					if (e.originalValue != e.value){
						var args ;
						if(e.field =='state1'){
								args = {
										'state' : 8,
										'checkStatus':1,
										 'earlyRedemptionId':e.record.data['earlyRedemptionId']
							}           
							var panel =this;
							var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
								msg : '正处理中······,请稍候······',
								removeMask : true
									// 完成后移除
									});
							  loadMask1.show(); // 显示
							Ext.Ajax.request({
										url : __ctxPath + "/creditFlow/financingAgency/updateStatePlEarlyRedemption.do",
										method : 'POST',
										scope :this,
										success : function(response, request) {
											loadMask1.hide();
											e.record.commit();
											panel.getCmpByName("EarlyOutListGrid").getStore().reload();
											var record = Ext.util.JSON.decode(response.responseText);
											Ext.ux.Toast.msg('信息提示', Ext.isEmpty(record.msg)?"操作成功":record.msg);
										},
										failure : function(response) {
											loadMask1.hide();
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params: args
									})
						}
						if(e.field =='state2'){
								args = {
										'state' : 2,
										'checkStatus':3,
										 'earlyRedemptionId':e.record.data['earlyRedemptionId']
							}           
							var panel =this;
							var loadMask1 = new Ext.LoadMask(Ext.getBody(), {
							msg : '正处理中·······,请稍候······',
							removeMask : true
								// 完成后移除
								});
						  loadMask1.show(); // 显示
							Ext.Ajax.request({
										url : __ctxPath + "/creditFlow/financingAgency/updateStatePlEarlyRedemption.do",
										method : 'POST',
										scope :this,
										success : function(response, request) {
											loadMask1.hide();
											e.record.commit();
											panel.getCmpByName("EarlyOutListGrid").getStore().reload();
											var record = Ext.util.JSON.decode(response.responseText);
											Ext.ux.Toast.msg('信息提示', Ext.isEmpty(record.msg)?"操作成功":record.msg);
										},
										failure : function(response) {
											loadMask1.hide();
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params: args
									})
					}
				}
			}
			}*/
		});
		this.gridPanel.addListener('afterrender', function() {
			this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
				msg : '正在加载数据中······,请稍候······',
				store : this.gridPanel.store,
				removeMask : true
					// 完成后移除
					});
			this.loadMask1.show(); // 显示

		}, this);
	
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
	checks:function(){
		var gp = this.gridPanel;
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		if (selectRs.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
				} else if (selectRs.length > 1) {
					Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
					return;
				} else {
				var record = selectRs[0];
				var url =__ctxPath
						+ "/creditFlow/financingAgency/gcalculateEarlyOutPlEarlyRedemption.do"
				Ext.Ajax.request({
							url : url,
							method : "post",
							scope : this,
							success : function(response, opts) {
								var obj=Ext.util.JSON.decode(response.responseText);
                    		    new EarlyOutDetail({
                        		    liquidatedDamagesMoney:obj.liquidatedDamagesMoney,
                        		    loanInterestMoney:obj.loanInterestMoney,
                        		    principalRepaymenMoney:obj.principalRepaymenMoney,
                        		    sumMoney:obj.sumMoney,
                        		    earlyRedemptionId:record.data.earlyRedemptionId,
                        		    investPersonName:record.get("plManageMoneyPlanBuyinfo.investPersonName"),
                        		    checkStatus:this.Q_checkStatus_SN_EQ,
                        		    keystr : this.keystr,
                        		    gp : gp
                    		    }).show();										
							},
							params : {
								earlyRedemptionId : record.data.earlyRedemptionId
							}
						})
				   
				}
	  
	 
	},
	
	backMoney : function(){
		var selectRs = this.gridPanel.getSelectionModel().getSelections();
		var gridPanel=this.gridPanel;
		if(selectRs.length == 0) {
			Ext.ux.Toast.msg('操作信息', '请选择记录!');
			return;
		}else if(selectRs.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
			return;
		}else{
			var record = selectRs[0];
			Ext.Msg.confirm('信息确认','是否返款',function(btn){
				if(btn=="yes"){
					Ext.MessageBox.wait('返款进行中','请稍后...');//锁屏
			    	Ext.Ajax.request({
						url : __ctxPath
								+ "/creditFlow/financingAgency/earlyBackMoneyPlEarlyRedemption.do",
						method : 'post',
						success : function(response, request) {
							var obj=Ext.util.JSON.decode(response.responseText)
							Ext.ux.Toast.msg('操作信息', obj.msg);
							gridPanel.getStore().reload();
							Ext.MessageBox.hide();//解除锁屏
						},
						params : {
							earlyRedemptionId : record.data.earlyRedemptionId
						}
					});
				}
    		})
		}
	}
	
});
