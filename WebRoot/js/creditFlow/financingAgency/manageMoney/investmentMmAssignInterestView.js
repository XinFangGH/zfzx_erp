/**
 * @author
 * @class PlMmOrderAssignInterestView
 * @extends Ext.Panel
 * @description [PlMmOrderAssignInterest]管理
 * @company 智维软件
 * @createtime:
 */
investmentMmAssignInterestView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		investmentMmAssignInterestView.superclass.constructor.call(this, {
					id : 'investmentMmAssignInterestView'+this.keystr+this.buttonType,
					//title : this.keystr=="mmplan"?'理财计划款项台账':'理财产品款项台账',
					region : 'center',
					layout : 'border',
					iconCls : 'btn-tree-team30',
					items : [this.searchPanel,this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		var keystr=this.keystr;
		this.searchPanel = new HT.SearchPanel({
			layout : 'form',
			border : false,
			region : 'north',
			height : 65,
			anchor : '70%',
			keys : [{
						key : Ext.EventObject.ENTER,
						fn : this.search,
						scope : this
					}, {
						key : Ext.EventObject.ESC,
						fn : this.reset,
						scope : this
					}],
			items : [{
				border : false,
				layout : 'column',
				style : 'padding-left:5px;padding-right:0px;padding-top:5px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor : '95%'

				},
				items : [{
					columnWidth : .15,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : '100%'
					},
					items : [{
						fieldLabel : '投资人',
						xtype : 'textfield',
						name : 'Q_investPersonName_S_LK',

						anchor : '100%'
					}]
				}, {

					columnWidth : .2,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 90,
					defaults : {
						anchor : '100%'
					},
					items : [{
								fieldLabel : keystr=="mmplan"?'理财计划名称':'理财产品名称',
								name : 'Q_mmName_S_LK',
								xtype : 'textfield'

							}]

				},{
									columnWidth : .16,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 90,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : '计划派息日期',
												name : 'Q_intentDate_D_GE',
												xtype : 'datefield',
												format : "Y-m-d"

											}]
								},{
									columnWidth : .1,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 15,
									defaults : {
										anchor : '100%'
									},
									items : [{
												fieldLabel : '到',
												name : 'Q_intentDate_D_LE',
												xtype : 'datefield',
												format : "Y-m-d"

											}]
								},{
									columnWidth : .1,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 40,
									defaults : {
										anchor : '100%'
									},
									items : [ {
										fieldLabel : '期数',
										xtype : 'textfield',
										name : 'Q_periods_N_EQ',
				
										anchor : '100%'
									}]
								},{
									columnWidth : .15,
									labelAlign : 'right',
									xtype : 'container',
									layout : 'form',
									labelWidth : 60,
									defaults : {
										anchor : '100%'
									},
									items : [{
										fieldLabel : '派息状态',
										name : 'loanerRepayMentStatus',
										hiddenName: 'loanerRepayMentStatus',
										xtype:'combo',
										mode : 'local',
									    displayField : 'itemName',
									    valueField : 'itemId',
									    triggerAction : "all",
									    //value:"1",
										store:new Ext.data.SimpleStore({
											fields:["itemName","itemId"],
											data:[["未派息","3"],["已派息，未返款","1"],["已派息完成","2"]]
										})
									}]
								},{
					columnWidth : .07,
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
							}]
				}, {
					columnWidth : .07,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					style : 'padding-left:10px;',
					items : [{
								text : '重置',
								scope : this,
								iconCls : 'btn-reset',
								handler : this.reset
							}]
				}]
			}]
		});// end of searchPanel
		this.topbar = new Ext.Toolbar({
			items : [	  	{
								iconCls : 'btn-user-paixi',
								text : '派息',
								xtype : 'button',
								scope : this,
								hidden:this.buttonType==7 ? true : false,
								handler : keystr=="mmproduce" ? this.assignInterestMoneyPruduct : this.assignInterest
							},{
								iconCls : 'btn-dispaixi',
								text : '解除派息锁定',
								xtype : 'button',
								scope : this,
								hidden:keystr == "mmproduce" || this.buttonType==7 ? true : false,
								handler : this.updateLockType
							},{
								iconCls : 'btn-setting6',
								text : '返款',
								xtype : 'button',
								scope : this,
								hidden:this.buttonType==7 ? false : true,
								handler : this.backMoney
							}
							]
		});
		
		var rightValue =  isGranted('_InterestView_mmproduce_see_All');
		var isShop = isGranted('_InterestView_mmproduce_see_shop');
		var url="";
		if(this.keystr=='mmproduce'){
			url = __ctxPath
					+ "/creditFlow/financingAgency/listByMmproduceAssigninterestPlMmOrderAssignInterest.do?isAll="+rightValue+"&isShop="+isShop
		}else{
			url = __ctxPath
					+ "/creditFlow/financingAgency/listAssigninterestPlMmOrderAssignInterest.do?Q_keystr_S_EQ="+this.keystr
					+"&Q_isValid_SN_EQ=0&Q_isCheck_SN_EQ=0&buttonType="+this.buttonType
		}
         var summary = new Ext.ux.grid.GridSummary();
				function totalMoney(v, params, data) {
					return '总计(元)';
				}
		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			plugins : [summary],
			// 使用RowActions
			id : 'investmentMmAssignInterestGrid'+this.keystr+this.buttonType,
			//isautoLoad:false,
			url : url,
			fields : [{
						name : 'assignInterestId',
						type : 'int'
					}, 'orderId', 'investPersonId', 'investPersonName',
					'mmplanId', 'mmName', 'fundType', 'incomeMoney','afterMoney','factDate',
					'intentDate','periods','payMoney','loanerRepayMentStatus'],
			columns : [{
						header : 'assignInterestId',
						align:'center',
						dataIndex : 'assignInterestId',
						hidden : true
					}, {
						header : '投资人',
						align:'center',
						dataIndex : 'investPersonName'
					}, {
						header :  keystr=="mmplan"?'理财计划名称':'理财产品名称',
						summaryRenderer : totalMoney,
						dataIndex : 'mmName'
					}, {
						header : '期数',
						align:'center',
						dataIndex : 'periods',
						
						renderer:function(v){
							if(v==null){return "";}
							else{
							return "第"+v+"期"
							}
						}
					}, {
						header : '资金类型',
						dataIndex : 'fundType',
					    align:'center',
						renderer:function(v){
							if(v=="loanInterest"){
							   return "利息"
							}else if(v=="riskRate"){
							   return "风险金"
							}else if(v=="liquidatedDamages"){
							   return "提前赎回违约金"
							}else {
							   return "本金";
							}
						}
					}, {
						header : '收入金额(元)',
						dataIndex : 'incomeMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
					}, {
						header : '支出金额(元)',
						summaryType : 'sum',
						dataIndex : 'payMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
					}, {
						header : '计划派息日期',
						align : 'center',
						dataIndex : 'intentDate'
					}, {
						header : '已派金额(元)',
						dataIndex : 'afterMoney',
						align : 'right',
						summaryType : 'sum',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00');
						}
					}, {
						header : '实际派息日期',
						align : 'center',
						dataIndex : 'factDate'
					}, {
						header : '派息状态',
						align : 'center',
						dataIndex : 'loanerRepayMentStatus',
						renderer:function(v){
							if(v=="1"){
								return "已派息，未返款"
							}else if(v=="2"){
								return "已派息完成"
							}else{
								return "未派息"
							}
						}
					}]
				// end of columns
		});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlMmOrderAssignInterestForm({
								assignInterestId : rec.data.assignInterestId
							}).show();
				});
	},
	//理财产品派息
	assignInterestMoneyPruduct : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var keystr=this.keystr;
		var buttonType=this.buttonType;
		if(s.length>1){
			Ext.ux.Toast.msg('操作信息', '只能选择一条记录来进行派息');
			return;
		}else if(s.length<=0){
			Ext.ux.Toast.msg('操作信息', '请选择一条记录来进行派息');
			return;
		}else{
			if(eval(s[0].data.afterMoney)>=eval(s[0].data.fundType=="liquidatedDamages"?s[0].data.payMoney:s[0].data.incomeMoney)){
				Ext.ux.Toast.msg('操作信息', '已经成功派息');
				return;
			}else if(eval(s[0].data.afterMoney)<eval(0)){
				Ext.ux.Toast.msg('操作信息', '账目异常,不能派息');
				return;
			}else{
				Ext.Msg.confirm('信息确认','是否派息',function(btn){
						if(btn=="yes"){
							Ext.Ajax.request({
								url : __ctxPath+ "/creditFlow/financingAgency/assignInterestPlMmOrderAssignInterest.do?keystr="+keystr,
								method : 'post',
								success : function(response, request) {
									Ext.getCmp('investmentMmAssignInterestGrid'+keystr+buttonType).getStore().reload();
									Ext.ux.Toast.msg('操作信息', '派息成功');
								},
								params : {
									assignInterestId : s[0].data.assignInterestId,
									investPersonId : s[0].data.investPersonId
								}
							});
					}
				})
			}
		
		}
		
		
	},
	updateLockType : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
		var keystr=this.keystr;
		 if(s.length<=0){
			Ext.ux.Toast.msg('操作信息', '请选择一条记录');
			return;
		}else{
			
			Ext.Msg.confirm('信息确认','是否解除派息锁定(谨慎操作)',function(btn){
				if(btn=="yes"){
					var ids = Array();
			var count=0;
			for (var i = 0; i < s.length; i++) {
		    	if(null!=eval(s[i].data.assignInterestId) && eval(s[i].data.assignInterestId)!=''){
		    		if(eval(s[i].data.afterMoney)<eval(s[i].data.incomeMoney)){
		    			ids.push(eval(s[i].data.assignInterestId));
		    			count++;
		    		}else{
			    		this.gridPanel.getStore().remove(s[i]);
						this.gridPanel.getView().refresh();
		    		}
		    		
		    	}else{
		    		this.gridPanel.getStore().remove(s[i]);
					this.gridPanel.getView().refresh();
		    	}
		    }
		    
		    
		    	Ext.Ajax.request({
					url : __ctxPath
							+ "/creditFlow/financingAgency/updateLockTypePlMmOrderAssignInterest.do?keystr="+keystr,
					method : 'post',
					success : function(response, request) {
						var obj=Ext.util.JSON.decode(response.responseText)
						Ext.ux.Toast.msg('操作信息', obj.msg);
						//Ext.MessageBox.hide();//解除锁屏
					},
					params : {
						ids : ids
					}
				});
				}
			})
		}
	},
	// 创建记录
	assignInterest : function() {
	
		var s = this.gridPanel.getSelectionModel().getSelections();
		var keystr=this.keystr;
		if(s.length<=0){
			Ext.ux.Toast.msg('操作信息', '请选择一条记录来进行派息');
			return;
		}else{
			var flag2=true;
			var flag=true;
			var mids=Array();
			for (var i = 0; i < s.length; i++) {
		    	if(null!=eval(s[i].data.mmplanId) && eval(s[i].data.mmplanId)!='' && s[i].data.loanerRepayMentStatus !=1){
	    			mids.push(eval(s[i].data.mmplanId));
		    	}else{
		    		flag2 = false;
		    	}
		    }
		    if(!flag2){
		        Ext.Msg.alert('操作信息','选择的台账中，存在已经派过息的，请重新选择！');
			    return;
			} 
			//理财计划验证是否平台授权
		    if(keystr='mmplan'){
		    	Ext.Ajax.request({
	                url :  __ctxPath + '/creditFlow/financingAgency/isAuthorityPlManageMoneyPlan.do',
                	method : 'POST',
	                scope:this,
	                async :  false,
                	success : function(response, request) {
	            		obj = Ext.util.JSON.decode(response.responseText);
	            	 	flag = obj.flag;
	            	 	if(flag=="false"){
	            	 		flag=false;
	            	 	}
					},
					
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					},
					
					params : {
						mids:mids
					}
				});
			}
		 	if(!flag){
		        Ext.Msg.alert('操作信息','选择的台账中，有理财计划未进行授权平台派息，请先进行平台派息授权！');
			    return;
			} 
			var ids = Array();
			var count=0;
			for (var i = 0; i < s.length; i++) {
		    	if(null!=eval(s[i].data.assignInterestId) && eval(s[i].data.assignInterestId)!=''){
		    		if(eval(s[i].data.afterMoney)<eval(s[i].data.incomeMoney)){
		    			ids.push(eval(s[i].data.assignInterestId));
		    			count++;
		    		}else{
			    		this.gridPanel.getStore().remove(s[i]);
						this.gridPanel.getView().refresh();
		    		}
		    	}else{
		    		this.gridPanel.getStore().remove(s[i]);
					this.gridPanel.getView().refresh();
		    	}
		    }
		    if(count==s.length){
		    	var gridPanel=this.gridPanel;
		    	Ext.Msg.confirm('信息确认','是否派息',function(btn){
					if(btn=="yes"){
						Ext.MessageBox.wait('派息进行中','请稍后...');//锁屏
				    	Ext.Ajax.request({
							url : __ctxPath
									+ "/creditFlow/financingAgency/assignInterestMoneyPlanPlMmOrderAssignInterest.do?keystr="+keystr,
							method : 'post',
							success : function(response, request) {
								var obj=Ext.util.JSON.decode(response.responseText)
								Ext.ux.Toast.msg('操作信息', obj.msg);
								gridPanel.getStore().reload();
								Ext.MessageBox.hide();//解除锁屏
							},
							params : {
								ids : ids
							}
						});
					}
		    	})
		    
		    }else{
		    	Ext.ux.Toast.msg('操作信息', '请检查所选进行派息的款项，其中有已经派息完成的记录');
				return;
		    }
		}
	},
	
		// 创建记录
	backMoney : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		var keystr=this.keystr;
		if(s.length<=0){
			Ext.ux.Toast.msg('操作信息', '请选择一条记录来进行派息');
			return;
		}else{
			var ids = Array();
			var count=0;
			for (var i = 0; i < s.length; i++) {
		    	if(null!=eval(s[i].data.assignInterestId) && eval(s[i].data.assignInterestId)!=''){
		    		if(eval(s[i].data.afterMoney)<eval(s[i].data.incomeMoney)){
		    			ids.push(eval(s[i].data.assignInterestId));
		    			count++;
		    		}else{
			    		this.gridPanel.getStore().remove(s[i]);
						this.gridPanel.getView().refresh();
		    		}
		    	}else{
		    		this.gridPanel.getStore().remove(s[i]);
					this.gridPanel.getView().refresh();
		    	}
		    }
		    if(count==s.length){
		    	var gridPanel=this.gridPanel;
		    	Ext.Msg.confirm('信息确认','是否返款',function(btn){
					if(btn=="yes"){
						Ext.MessageBox.wait('返款进行中','请稍后...');//锁屏
				    	Ext.Ajax.request({
							url : __ctxPath
									+ "/creditFlow/financingAgency/backMoneyPlMmOrderAssignInterest.do?keystr="+keystr,
							method : 'post',
							success : function(response, request) {
								var obj=Ext.util.JSON.decode(response.responseText)
								Ext.ux.Toast.msg('操作信息', obj.msg);
								gridPanel.getStore().reload();
								Ext.MessageBox.hide();//解除锁屏
							},
							params : {
								ids : ids
							}
						});
					}
		    	})
		    
		    }else{
		    	Ext.ux.Toast.msg('操作信息', '请检查所选进行派息的款项，其中有不符合派息规则的记录');
				return;
		    }
		}
	},
	
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderAssignInterest.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderAssignInterest.do',
			grid : this.gridPanel,
			idName : 'assignInterestId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmOrderAssignInterestForm({
					assignInterestId : record.data.assignInterestId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.assignInterestId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
