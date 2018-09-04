/**
 * @author
 * @class SlFundIntentView
 * @extends Ext.Panel
 * @description [SlFundIntent]管理
 * @company 智维软件
 * @createtime:
 */
VPunishDetail = Ext.extend(Ext.Panel, {
		// 构造函数
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);
			// 初始化组件
			this.initUIComponents();
			// 调用父类构造
			VPunishDetail.superclass.constructor.call(this, {
				id : 'VPunishDetail'+this.businessType,
				title : '逾期罚息对账日志',
				region : 'center',
				layout : 'border',
				iconCls :'btn-tree-team2',
				items : [this.searchPanel, this.gridPanel]
			});
		},// end of constructor
		
		// 初始化组件
		initUIComponents : function() {
			var isShow=false;
			if(RoleType=="control"){
			  isShow=true;
			}
			// 初始化搜索条件Panel
			this.searchPanel = new HT.SearchPanel({
				layout : 'column',
				style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
				region : 'north',
				height : 20,
				anchor : '100%',
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
					columnWidth : 0.2,
					layout : 'form',
					border : false,
					labelWidth : 80,
					labelAlign : 'right',
					items : [{
					    fieldLabel : '操作日期：从',
						name : 'Q_intentDate_D_GE',
						labelSeparator : '',
						xtype : 'datefield',
						format : 'Y-m-d',
						anchor : '100%'
					},{
						labelWidth:70,    
						fieldLabel : '项目名称',
						name : 'Q_proj_Name_N_EQ',
						flex : 1,
						editable : false,
						width:105,
						xtype :'textfield',
						anchor : '100%'
					}] 
				},{
					columnWidth : 0.2,
					layout : 'form',
					border : false,
					labelWidth : 60,
					labelAlign : 'right', 
					items : [{
						fieldLabel : '到',
						name : 'Q_intentDate_D_LE',
						labelSeparator : '',
						xtype : 'datefield',
						format : 'Y-m-d',
						anchor : '96%'
					},{
					    labelWidth:70,    
						fieldLabel : '项目编号',
						name : 'Q_projNum_N_EQ',
						flex : 1,
						editable : false,
						width:105,
						xtype :'textfield',
						anchor : '96%'
					}]
				},{ 
					columnWidth : 0.2,
					layout : 'form',
					border : false,
					labelWidth : 60,
					labelAlign : 'right',
					items : [{
						fieldLabel : '资金类型',
						xtype : 'combo',
						mode : 'local',
						displayField : 'name',
						valueField : 'id',
						editable : false,
						store : new Ext.data.SimpleStore({
							fields : ["name", "id"],
							data : [["罚息", "0"]
								  ]
						}),
						triggerAction : "all",
						hiddenName:'Q_type_S_EQ',
						anchor : '96%'
				},{
						labelWidth:70,    
						fieldLabel : '交易摘要',
						name : 'Q_transactionType_S_LK',
						flex : 1,
						width:105,
						xtype :'textfield',
						anchor : '96%'
					}] 
				}/*,{
					columnWidth : .2,
					layout : 'form',
					border : false,
					labelWidth : 80,
					labelAlign : 'right',
					items : [this.businessType=='SmallLoan'?{
						xtype : 'lovcombo',
						fieldLabel : '项目属性',
						anchor : '96%',
						hiddenName : 'projectProperties',
						triggerAction : 'all',
						editable :false,
						readOnly : false,
			            store :new Ext.data.ArrayStore({
			                autoLoad : true,
			                baseParams : {
			                    nodeKey : 'projectProperties'
			                },
			                url : __ctxPath + '/system/loadIndepItemsDictionaryIndependent.do',
			                fields : ['dicKey', 'itemValue']
			            }),
			            displayField : 'itemValue',
			            valueField : 'dicKey',
			            listeners :{
							afterrender : function(combox) {
							var st = combox.getStore();
						}
						}
					}:{border :false},isShow?{
						xtype : "combo",
						anchor : "96%",
						fieldLabel : '所属分公司',
						hiddenName : "companyId",
						displayField : 'companyName',
						valueField : 'companyId',
						triggerAction : 'all',
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath+'/system/getControlNameOrganization.do',
							fields : ['companyId', 'companyName']
						})
					}:{border : false}]
				}*/,{
					columnWidth : .08,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						scope : this,
						iconCls : 'btn-search',
						handler : this.search
					},{
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			});// end of searchPanel

			this.topbar = new Ext.Toolbar({
				items : [{
		        	iconCls : 'btn-detailsa',
		        	text : '撤销对账',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_punish_back_'+this.businessType)?false:true,
					handler : this.cancelAccount
				}]
			});
			
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计';
			}
			
			this.gridPanel = new HT.GridPanel({
				bodyStyle : "width : 100%",
				region : 'center',
				tbar : this.topbar,
				plugins : [summary],
				rowActions : false,
				id : 'VFundDetailGrid',
				viewConfig: {  
	            	forceFit:false  
	            },
            	isautoLoad:false,
            	loadMask : true,
				url : __ctxPath + "/creditFlow/finance/listVPunishDetail.do?businessType="+this.businessType,
				fields : [{
						name : 'punishDetailId',
						type : 'int'
					}, 'projectName','projectNumber', 'intentincomeMoney','itemValue', 'intentDate',
					'intentpayMoney', 'operTime', 'factDate','afterMoney', 'myAccount',
					'transactionType', 'qlideincomeMoney','qlidepayMoney','punishInterestId',
					'checkuser','iscancel','cancelremark','orgName','type'],
				columns : [{
						header : 'punishDetailId',
						dataIndex : 'punishDetailId',
						hidden : true
					},{
						header : "所属分公司",
						sortable : true,
						width : 120,
						hidden:RoleType=="control"?false:true,
						dataIndex : 'orgName'
					}, {
						header : '项目名称',
						dataIndex : 'projectName',
						width : 150
					}, {
						header : '项目编号',
						dataIndex : 'projectNumber',
						width : 120
					}, {
						header : '资金类型',
						dataIndex : 'type',
						summaryType : 'count',
						summaryRenderer : totalMoney,
						width : 130,
						renderer : function(v) {//用于区分是罚息(0)记录还是逾期(1)记录
							if(v == 0){
								return "罚息";
							}else if(v == 1){
						     	return "逾期利息";
							}
                 	    }
					}, {
						header : '计划收入金额',
						dataIndex : 'intentincomeMoney',
						align : 'right',
						width : 150,
						summaryType: 'sum',
						renderer : function(v, metadata, record,rowIndex, columnIndex, store) {
		                   	metadata.attr = ' ext:qtip="' + Ext.util.Format.number(v,',000,000,000.00')+"元" + '"';
							if(v ==null){
								return "";
							}else{
						     	return Ext.util.Format.number(v,',000,000,000.00')+"元"
							}
                 	     }
					}, {
						header : '实际到帐日',
						dataIndex : 'factDate',
						width : 100,
						sortable:true
					}, {
						header : '我方账户',
						width : 150,
						dataIndex : 'myAccount',
						renderer : function(v, metadata, record,rowIndex, columnIndex, store) {
		                    metadata.attr = ' ext:qtip="' + v + '"';
							if(v=="1111"){
								return "";
							}else{
							  return v;
							}
                 		}
					}, {
						header : '收入金额',
						width : 150,
						dataIndex : 'qlideincomeMoney',
						align : 'right',
						sortable:true,
						summaryType: 'sum',
						renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
		                    metadata.attr = ' ext:qtip="' + Ext.util.Format.number(value,',000,000,000.00')+"元" + '"';
							if(value ==null){
								return "";
							}else{
								if(record.data.myAccount =="1111"){
									return "";
								} else{ 
						        	return Ext.util.Format.number(value,',000,000,000.00')+"元"
						        }
							}
                 	     }
					}, {
						header : '划入本款项金额',
						width : 150,
						dataIndex : 'afterMoney',
						sortable:true,
						summaryType: 'sum',
						renderer : function(value, metadata, record, rowIndex, columnIndex, store) {
		                    metadata.attr = ' ext:qtip="' + "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"	+ '"';
							if(record.data.myAccount =="1111") {
								return "现金"+Ext.util.Format.number(value,',000,000,000.00')+"元"	
							}else {
								return Ext.util.Format.number(value,',000,000,000.00')+"元"	
							}
						}
					}, {
						header : '操作人',
						align :'center',
						width : 150,
						dataIndex : 'checkuser'
					}, {
						header : '操作时间',
						width : 160,
						align :'center',
						dataIndex : 'operTime'
					}, {
						header : '交易摘要',
						align :'center',
						width : 150,
						dataIndex : 'transactionType'
					}, {
						header : '备注',
						dataIndex : 'cancelremark',
						align : 'center',
						width :300,
						renderer : function(v, metadata, record,rowIndex, columnIndex, store) {
		                	metadata.attr = ' ext:qtip="' + v + '"';
		                	if(v !=null){
		                		return '<font color="red">'+v+'</font>';
		                	}else{
		                 		return "";
		                		}
		                }
					}]
				});
			},// end of the initComponents()
			// 重置查询表单
			reset : function() {
				this.searchPanel.getForm().reset();
				$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
			},
			// 按条件搜索
			search : function() {
				$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
			},
			//撤销
			cancelAccount:function(){
				var gridPanel = this.gridPanel;
				var punishInterestId = $getGdSelectedIds(gridPanel,'punishInterestId');//罚息记录id
			    var ids = $getGdSelectedIds(gridPanel,'punishDetailId');//罚息对账明细id
			 	var s = gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					if(s[0].data.iscancel==1){
						Ext.ux.Toast.msg('操作信息','此对账记录已被撤销，无须再撤！');
				     	return false;
					}
					Ext.MessageBox.confirm('确认撤销吗', '撤销就不能恢复了', function(btn) {
						if (btn == 'yes') {
				           Ext.Ajax.request( {
								url : __ctxPath + '/creditFlow/finance/cancelPunishAccountSlFundIntent.do',
								method : 'POST',
								scope : this,
								params : {
									punishInterestId : punishInterestId,
									detailIds:ids
								},
								success : function(response, request) {
									Ext.ux.Toast.msg('操作信息', '撤销成功！');
							         gridPanel.getStore().reload();
								},
								checkfail:function(response, request) {
									Ext.Msg.alert('状态', "撤销失败");
								}
							});
						}
					});
				}
			}
		});