//LoanerRepaymentRecord
LoanerRepaymentRecord = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				LoanerRepaymentRecord.superclass.constructor.call(this, {
							layout : 'border',
							border : false,
							items : [this.searchPanel ,this.gridPanel],
							modal : true,
							height : 500,
							width : 700,
							maximizable : true,
							title : '保证金偿还记录'
						});
			},// end of the constructor
			// 初始化组件
			initUIComponents : function() {
				this.searchPanel = new HT.SearchPanel({
							layout : 'column',
							style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
							region : 'north',
							height : 20,
							anchor : '96%',
							keys : [{
								key : Ext.EventObject.ENTER,
								fn : this.search,
								scope : this
							}, {
								key : Ext.EventObject.ESC,
								fn : this.reset,
								scope : this
							}],
							layoutConfig: {
				               align:'middle',
				               padding : '5px'
				               
				            },
							items : [{
										columnWidth : .3,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 90,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '招标项目名称',
											name : "bidPlanName",
											xtype : 'textfield',
											labelSeparator : ""
										},{
											fieldLabel : '招标项目编号',
											name : "bidPlanNumber",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
										columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '起始日期',
											name : "startDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										},{
											fieldLabel : '借款人',
											name : "borrowerName",
											xtype : 'textfield',
											labelSeparator : ""
										}]
							         },{
							         	columnWidth : .25,
										labelAlign : 'right',
										xtype : 'container',
										layout : 'form',
										labelWidth : 70,
										defaults : {
											anchor : anchor
										},
										items : [{
											fieldLabel : '截止日期',
											name : "endDate",
											xtype : 'datefield',
											labelSeparator : "",
											format : 'Y-m-d'
										}]
							         },{
										columnWidth : .1,
										xtype : 'container',
										layout : 'form',
										defaults : {
											xtype : 'button'
										},
										style : 'padding-left:10px;',
										items : [{
											text : '查询',
											scope : this,
											iconCls : 'btn-search',
											handler : this.search
										},{
											text : '重置',
											scope : this,
											iconCls : 'reset',
											handler : this.reset
										}]
									}]
						});


				var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
				this.gridPanel = new HT.GridPanel({
					bodyStyle : "width : 100%",
					region : 'center',
					sm : this.projectFundsm,
					plugins : [summary],
					// 使用RowActions
					rowActions : false,
					id : 'LoanerRepaymentRecordGrid',
					url : __ctxPath + "/plateForm/getrepaymentRecordSlRiskGuaranteeMoneyBackRecord.do?punishmentId="+this.punishmentId,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'totalRepaymentMoney','repaymentMoney','repaymentPunishmentMoney',
							'repaymentDate','requestNO', 'repaymentStatus','punishmentId','companyId'],
					columns : [{
								header : 'id',
								dataIndex : 'id',
								hidden : true
							}, {
								header : '第三方支付流水号',
								dataIndex : 'requestNO',
								summaryType : 'sum',
				                summaryRenderer : totalMoney
							},{
								header : '本次偿还总额',
								dataIndex : 'totalRepaymentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							},{
								header : '本次偿还代偿金额',
								dataIndex : 'repaymentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							} ,{
								header : '本次偿还罚息金额',
								dataIndex : 'repaymentPunishmentMoney',
								summaryType : 'sum',
								align : 'right',
								renderer:function(v){
								 return Ext.util.Format.number(v,',000,000,000.00')+"元"
                         	    }
							}, {
								header : '偿还日期',
								dataIndex : 'repaymentDate'
							}, {
								header : '本次偿还状态',
								dataIndex : 'repaymentStatus',
								summaryType : 'sum',
								renderer:function(v){
								 	if(v==1){
								 		return "支付中";
								 	}else if(v==2){
								 		return "支付授权成功";
								 	}else if(v==3){
								 		return "支付成功";
								 	}else{
								 		return "";
								 	}
                         	    }
							}
						]
					});
						
			},
			/**
			 * 取消
			 * @param {}
			 *  window
			 */
			cancel : function() {
				this.close();
			},
			/**
			 * 保存记录
			 */
			save : function() {
			   var openNewPage=this.openNewPage;
			   var accountId=this.accountId;
			   var accountType=this.accountType;
			   var thirdPayName=this.thirdPayName
			   var thirdPayType=this.thirdPayTypeName;
			   var plateFormnumber=this.platFormNumber;
			   var accountName=this.accountName;
			   var accountId=this.accountId;
			   var accountNumber=this.accountNumber;
			   var balanceMoney=this.balanceMoney;
			   var refreshPanel =this.refreshPanel;
			   var rerchargeMoney=this.formPanel.getCmpByName("obAccountDealInfo.payMoney").getValue();
			   /*var bankCode=this.formPanel.getCmpByName("bankcode").getValue();*/
			   if(openNewPage==1){
			   		if(eval(rerchargeMoney)==eval(0)){
			   			Ext.ux.Toast.msg('操作信息','取现金额不能为0');
			   	    	return;
			   		}else{
			   			window.open(
										__ctxPath + '/creditFlow/creditAssignment/bank/openNewPageSaveDealInfoObSystemAccount.do?accountId='+ accountId+'&rerchargeMoney='+rerchargeMoney+'&bankCode='+bankCode,
										'平台普通资金账户取现',
										'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no',
										'_blank');
			   		}
			   }else{
			       this.formPanel.getForm().submit({
							clientValidation: false, 
							scope : this,
							method : 'post',
							waitMsg : '数据正在提交，请稍后...',
							scope: this,
							url : __ctxPath + '/creditFlow/creditAssignment/bank/normalWithDrawSaveDealInfoObSystemAccount.do',
							success : function(fp, action) {
								var object = Ext.util.JSON.decode(action.response.responseText)
								Ext.ux.Toast.msg('操作信息',object.msg);
								refreshPanel.getStore().reload();
								this.close();
								
							},
							failure : function(fp, action) {
								Ext.MessageBox.show({
									title : '操作信息',
									msg : '信息保存出错，请联系管理员！',
									buttons : Ext.MessageBox.OK,
									icon : 'ext-mb-error'
								});
							}
						});
			   }
			}// end of save
		});