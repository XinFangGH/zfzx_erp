BpFinanceApplyUserTypeView = Ext.extend(Ext.Panel, {
//	 	title:'',
		constructor : function(_cfg) {
			/*if (typeof(_cfg.type) != "undefined") {
				this.type = _cfg.type;
				if(this.type==0){
					this.title="未    提     交";
				}else if(this.type==1){
					this.title="已    提      交";
				}else if(this.type==2){
					this.title="初步受理     ";
				}else if(this.type==3){
					this.title="打回补充     ";
				}else if(this.type==4){
					this.title="通过审核     ";
				}else if(this.type==5){
					this.title="发      标     中";
				}else if(this.type==6){
					this.title="已     发     标";
				}else if(this.type==7){
					this.title="未通过终止";
				}
			}*/
			Ext.applyIf(this, _cfg);
			// 初始化组件
			this.initUIComponents();
			// 调用父类构造
			BpFinanceApplyUserTypeView.superclass.constructor.call(this, {
				id : 'BpFinanceApplyUserTypeView_'+this.type,
//				title : this.title,
				region : 'center',
				layout : 'border',
				height : 550, 
				iconCls : 'btn-tree-team30',
				items : [this.searchPanel,this.gridPanel]
			});
		},
		initUIComponents:function(){
			this.searchPanel=new HT.SearchPanel({
					layout : 'column',
					region : 'north',
					items:[{
						columnWidth : .17,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						border :false,
						items : [{
							fieldLabel:'借款金额',
							name : 'Q_loanMoney_S_EQ',
							anchor : '100%',
							xtype : 'textfield'
						}]
					},{
						columnWidth : .17,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						border :false,
						items : [{
							fieldLabel:'借款期限',
							name : 'Q_loanTimeLen_S_EQ',
							anchor : '100%',
							xtype : 'textfield'
						}]
					},{
						columnWidth : .17,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						border :false,
						items : [{
							fieldLabel:'用户名',
							name : 'loginname',
							anchor : '100%',
							xtype : 'textfield'
						}]
					},{
						columnWidth : .2,
						layout : 'form',
						labelWidth : 60,
						labelAlign : 'right',
						border :false,
						items : [{
							fieldLabel:'真实姓名',
							name : 'truename',
							anchor : '90%',
							xtype : 'textfield'
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border :false,
						items :[{
							xtype : 'button',
							text:'查询',
							scope:this,
							iconCls:'btn-search',
							handler:this.search
						}]
					},{
						columnWidth : .07,
						layout : 'form',
						border :false,
						items :[{
							xtype : 'button',
							text:'重置',
							scope:this,
							iconCls:'btn-reset',
							handler:this.reset
						}]
					}]	
			});
			
			this.topbar = new Ext.Toolbar({
				items :[{
						iconCls:'btn-readdocument',
						text:'查看详细',
						xtype:'button',
						scope:this,
						handler : function() {
							var rows = this.gridPanel.getSelectionModel().getSelections();
							if (rows.length == 0) {
								Ext.ux.Toast.msg('操作信息', '请选择记录!');
								return;
							} else if (rows.length > 1) {
								Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
								return;
							} else {
								Ext.Ajax.request({
									url : __ctxPath + '/p2p/getInfoBpFinanceApplyUser.do?loanId='+rows[0].data.loanId,
									method:'post',
									async:false,
									success: function(resp,opts) {
										var respText = resp.responseText;
										var alarm_fields = Ext.util.JSON.decode(respText);
										var data=alarm_fields.data;
										new P2PBpCustMemberForm({
											memObj:data.bpCustMember,
											appUser:data.bpFinanceApplyUser,
											userId:data.bpCustMember.id,
											userName:data.bpCustMember.loginname
										}).show();
									}
								});
							}
						}
					}
				]
			})
			
			if(this.type==1){
					this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-agree',
							text : '受理',
							scope : this,
							handler : this.updateApplyType
						})
					);
				
				
					this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-pause',
							text : '不受理终止',
							scope : this,
							handler : this.updateApplyType
						})
					);
				
			}else if(this.type==2){
				this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-finish',
							text : '审核通过',
							scope : this,
							handler : this.updateApplyType
						})
					);
				this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-comeback',
							text : '打回补充',
							scope : this,
							handler : this.updateApplyType
						})
					);
				this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-disagree',
							text : '审核未通过',
							scope : this,
							handler : this.updateApplyType
						})
					);
			}else if(this.type==4){
				
			}else if(this.type==7){
				this.topbar.items.add(
						new Ext.Toolbar.Separator({})
					);
					this.topbar.items.add(
						new Ext.Button({
							iconCls : 'btn-save1',
							text : '恢复到初步受理',
							scope : this,
							handler : this.updateApplyType
						})
					);
			}
			var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计（元）';
				}
			this.gridPanel=new HT.GridPanel({
				id:'BpFinanceApplyUserView_'+this.type,
				region:'center',
				tbar:this.topbar,
				 plugins : [summary],
				rowActions:false,
				url : __ctxPath + "/p2p/loanListBpFinanceApplyUser.do?state="+this.type,
				fields : [{
						name : 'loanId',
						type : 'int'
					},'loanTitle','productId','appName','proName','loanMoney','loanTimeLen','loanUseStr','createTime','truename'
				],
				columns:[{
					header : 'loanId',
					align:'center',
					dataIndex : 'loanId',
					hidden : true
				},{
					header : '借款标题 ',
					
					dataIndex : 'loanTitle'
				},{
					header : '用户账号',
					align:'center',
					summaryRenderer : totalMoney,
					dataIndex : 'appName'
				},{
					header : '贷款类别',	
					dataIndex : 'proName'
				},{
					header : '真实姓名',
					align:'center',
					dataIndex : 'truename'
				},{
					header : '借款日期',
					align:'center',
					dataIndex:'createTime'
				},{
					header : '借款金额(元)',
					align:'right',
					summaryType : 'sum',
					dataIndex : 'loanMoney',
					align:'right'
				},{
					header : '借款用途',	
					dataIndex : 'loanUseStr'
				},{
					header : '借款期限',
					align:'center',
					dataIndex : 'loanTimeLen'
				}]
			});
		},
		reset : function(){
			this.searchPanel.getForm().reset();
		},
		search : function() {
			$search({
				searchPanel:this.searchPanel,
				gridPanel:this.gridPanel
			});
		},
		//修改申请状态
		updateApplyType:function(v){
			var panel=this.gridPanel;
			var rows = panel.getSelectionModel().getSelections();
			if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (rows.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				var loanId=rows[0].data.loanId;
				var text=v.text;
				var rowState;
				if(text=="提交"){
					rowState='1';
				}else if(text=="受理" || text=="恢复到初步受理"){
					rowState='2';
				}else if(text=="打回补充"){
					rowState='3';
				}else if(text=="审核通过"){
					rowState='4'
					new BpFinanceApplyUserTypeForm({
						loanId : loanId,
						panel : panel
					}).show();
				}else if(text=="立项发标"){
					rowState='5'
				}else if(text=="审核未通过" || text=="不受理终止"){
					rowState='7'
				}
				if(rowState != 4){
					Ext.Msg.confirm('信息确认', '是否'+text+'该记录', function(btn) {
	   					 if (btn == 'yes') {
							Ext.Ajax.request({
								url:__ctxPath + '/p2p/saveBpFinanceApplyUser.do',
								params:{'loanId':loanId,'state':rowState},
								success: function(resp,opts) {
									Ext.ux.Toast.msg('操作信息', '操作成功!');
									panel.getStore().reload();
								}
							});
	   					 }
					})
				}
			}
		},
		startLoanFlow:function(){
			var rows = this.gridPanel.getSelectionModel().getSelections();
			if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (rows.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				var loanId=rows[0].data.loanId;
				Ext.Msg.confirm('信息确认', '是否启动借款审批流程', function(btn) {
			        if (btn == 'yes') {
						Ext.Ajax.request({
							url:__ctxPath + '/p2p/startLoanApprovalFlow2BpFinanceApplyUser.do',
							params:{'loanId':loanId,'state':'5'},
							success: function(resp,opts) {
								var obj = Ext.util.JSON.decode(resp.responseText);
								if(obj.flag==false){
									Ext.ux.Toast.msg('操作信息', '注册用户和借款用户未建立连接，不能启动借款审批流程!');
									return;
								}else{
									var type=obj.custType;
									if("p_loan"==type){//个人补录
										new CreateNewProjectFrom({
											operationType:'SmallLoan_PersonalCreditLoanBusiness',
											isNameReadOnly:true,
											isAllReadOnly:true,
											isProductReadOnly:true,
											userId:obj.custId,
											userType:type,
											loanId:loanId,
											productId:rows[0].data.productId
										}).show();
									}else if("b_loan"==type){//企业
										new CreateNewProjectFrom({
											operationType:'SmallLoan_SmallLoanBusiness',
											isNameReadOnly:true,
											isAllReadOnly:true,
											isProductReadOnly:true,
											userId:obj.custId,
											userType:type,
											loanId:loanId,
											productId:rows[0].data.productId
										}).show();
									}
								}
							}
						});
			        }
				 })
			}
		},
		startFlowRs : function(){
			var rows = this.gridPanel.getSelectionModel().getSelections();
			if (rows.length == 0) {
				Ext.ux.Toast.msg('操作信息', '请选择记录!');
				return;
			} else if (rows.length > 1) {
				Ext.ux.Toast.msg('操作信息', '只能选择一条记录!');
				return;
			} else {
				var loanId=rows[0].data.loanId;
				Ext.Msg.confirm('信息确认', '是否启动补录流程', function(btn) {
			        if (btn == 'yes') {
						Ext.Ajax.request({
							url:__ctxPath + '/p2p/startLoanApprovalFlow2BpFinanceApplyUser.do',
							params:{'loanId':loanId,'state':'5'},
							success: function(resp,opts) {
								var obj = Ext.util.JSON.decode(resp.responseText);
								if(obj.flag==false){
									Ext.ux.Toast.msg('操作信息', '注册用户和借款用户未建立连接，不能启动补录流程!');
									return;
								}else{
									var type=obj.custType;
									if("p_loan"==type){//个人补录
										new CreateNewProjectFrom({
											operationType:'SmallLoan_PersonalCreditLoanBusiness',
											history:'dir',
											isNameReadOnly:true,
											isAllReadOnly:true,
											isProductReadOnly:true,
											userId:obj.custId,
											userType:type,
											loanId:loanId,
											productId:rows[0].data.productId
										}).show();
									}else if("b_loan"==type){//企业
										new CreateNewProjectFrom({
											operationType:'SmallLoan_SmallLoanBusiness',
											history:'dir',
											isNameReadOnly:true,
											isAllReadOnly:true,
											isProductReadOnly:true,
											userId:obj.custId,
											userType:type,
											loanId:loanId,
											productId:rows[0].data.productId
										}).show();
									}
								}
							}
						});
			        }
				 })
			}
		}
});