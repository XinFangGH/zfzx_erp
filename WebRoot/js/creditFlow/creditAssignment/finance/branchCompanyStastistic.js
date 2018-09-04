/**
 * @author:
 * @class branchCompanyStastistic
 * @extends Ext.Panel
 * @description [branchCompanyStastistic]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
branchCompanyStastistic = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		branchCompanyStastistic.superclass.constructor.call(this, {
			id:'branchCompanyStastistic',
			title : '分公司在保金额统计',
			region : 'center',
			layout : 'border',
			iconCls :'menu-property',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
	/*	var data = [['车辆',1],['股权',2],['无限连带责任-公司',3],['无限连带责任-个人',4],['机器设备',5],['存货/商品',6],['住宅',7],['商铺写字楼',8],['住宅用地',9],['商业用地',10],['商住用地',11],['教育用地',12],['工业用地',13],['无形权利',14]];
		var statusdata = [['初步录入',1],['银行已放款未办理',2],['保中已办理',3],['客户已还款未解除',4],['已解除',5]];
		var mortgageStatus="";
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}*/
		var firstDate =new Date();
		firstDate.setDate(1);
		var endDate =new Date(firstDate);
		endDate.setMonth(firstDate.getMonth()+1);
		endDate.setDate(0);
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
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
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
			
				items : [ {
					xtype : 'textfield',
					fieldLabel : '门店名称',
					anchor : '100%',
					name : 'shopName'
				}]
			}, {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '开始时间',
					anchor : '100%',
					value:firstDate,
					format : 'Y-m-d',
					name : 'startDate'
				}  ]
			}, {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'datefield',
					fieldLabel : '结束时间',
					anchor : '100%',
					format : 'Y-m-d',
					value:endDate,
					name : 'endDate'
				}]
			},{
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					anchor:'100%',
					scope : this,
					style:'margin-left:22',
					iconCls : 'btn-search',
					handler : this.search
				} ]
			} , {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					//width : 60,
					anchor:'100%',
					scope : this,
					style:'margin-left:23',
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {/*
			items : [ {
				iconCls : 'btn-readdocument',
				text : '查看抵质押物详情',
				xtype : 'button',
				hidden : isGranted('_seeMortgageInfo')?false:true,
				scope : this,
				handler : this.seeMortgageInfo
			}, {
				iconCls : 'btn-readdocument',
				text : '查看合同详情',
				xtype : 'button',
				hidden : isGranted('_seeThirdContract')?false:true,
				scope : this,
				handler : this.seeThirdContract
			}, {
				iconCls : 'btn-readdocument',
				text : '查看项目详情',
				xtype : 'button',
				hidden : isGranted('_seeProjectInfo')?false:true,
				scope:this,
				handler : this.seeProjectInfo
			},'->', {
				xtype : 'label',
				html : '【颜色预警：<font color="purple"> 合同签署未办理</font>&nbsp;&nbsp;<font color="blue">已办理</font>&nbsp;&nbsp;<font color="red">项目完成未解除</font>&nbsp;&nbsp;<font color="green">已解除</font>】'
			} ]
		*/});
       var  projectName=function(data, cellmeta, record){
       	   
           return '门店名称：<font color="red">'
							+ record.get("shopName")
							+ '</font>,门店收益：<font color="red">'
							+ record.get("companyTotalIntent")
							+ '元</font>';

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'shopIntentId',
						type : 'int'
					}, 'shopId','shopName','investpersonId', 
			           'investName','systemAccountId','systenAccountNumber','dealInfoId','dealMoney',
			           'shopRate','shopIntent','createDate',
			           'creatorId','companyId','creatorName','companyTotalIntent']);

       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath +'/creditFlow/creditAssignment/bank/listshopIntentStatisticObShopintentStatistic.do',
			reader : this.reader,
			autoLoad:true,
			baseParams:{firstDate:firstDate,endDate:endDate},
			groupField : 'shopId'
		});
		
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			//tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,
			width:'100%',
			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			}),

			columns : [{
				header : 'shopIntentId',
				dataIndex : 'shopIntentId',
				hidden : true
			},{
				header : "",
				dataIndex : "shopId",
				hidden : true,
				renderer : projectName
		   },{
				header : "投资人姓名",
				dataIndex : "investName"/*,
				hidden : !isShow*/
		   }, {
				header : "充值金额",
				dataIndex : "dealMoney",
				hidden : true,
				renderer : projectName
		   }, {
				header : '投资人系统账号',
				dataIndex : 'systenAccountNumber',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '当日在保利率',
				dataIndex : 'shopRate',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data+"%";
	            }
			}, {
				header : '在保收益',
				dataIndex : 'shopIntent',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data+"元";
	            }
			},{
				header : '计息日期',
				dataIndex : 'createDate',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}]
		//end of columns
				});

		this.gridPanel.addListener('cellclick', this.cellClick);

	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	}
	/*//GridPanel行点击处理事件
	cellClick : function(grid,rowIndex,columnIndex,e){
		
				var reloadGrid=function(size){    
				     grid.getStore().reload();
				}
				if(13==columnIndex){
				   
					var title = grid.getStore().getAt(rowIndex).get('mortgagename');
					var thirdRalationId = grid.getStore().getAt(rowIndex).get('id');
					var contractId = grid.getStore().getAt(rowIndex).get('contractId');
					var categoryId = grid.getStore().getAt(rowIndex).get('categoryId');
					var temptId = grid.getStore().getAt(rowIndex).get('temptId');
					var businessType = grid.getStore().getAt(rowIndex).get('businessType');
					var piKey = grid.getStore().getAt(rowIndex).get('projid');
					var window = new OperateThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :'thirdContract',
								thisPanel : grid,
								isHidden : false,
								isqsHidden : false,
								isgdHidden :false
							});
					window.show();
					window.addListener({
								'close' : function() {
									//thisPanel.getStore().reload();
								}
							});
				
				}
				if(14==columnIndex){
					 var markId=grid.getStore().getAt(rowIndex).get("id");
					 var talbeName="cs_procredit_mortgage.";
					 var mark=talbeName+markId;
					 uploadfile("上传/下载抵质押物文件",mark,15,null,null,reloadGrid);
					
				}
				if(15==columnIndex){   
					 var markId=grid.getStore().getAt(rowIndex).get("id");
					 var talbeName="cs_procredit_mortgage.";
					 var mark=talbeName+markId;
				     picViewer(mark,true);
				}
	},*/
	
	
	/*seeThirdContract : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				var rows = this.gridPanel.getSelectionModel().getSelections();
				if(rows.length >1){
					Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
					return;
				}
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('mortgagename');
					var thirdRalationId = selected.get('id');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					var businessType = selected.get('businessType');
					var piKey = selected.get('projid');
					var window = new OperateThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :'thirdContract',
								thisPanel : thisPanel,
								isHidden : false,
								isqsHidden : false,
								isgdHidden :false
							});
					window.show();
					window.addListener({
								'close' : function() {
									//thisPanel.getStore().reload();
								}
							});
				}
			},*/
	/*seeMortgageInfo : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
				var mortgageId = selected.get('id');
				if(Ext.getCmp("businessTypeQuery").getValue()=="Financing"){
			        mortgageId=selected.get('mortgageId')
		        }
				var typeId = selected.get('typeid');
				var businessType = selected.get('businessType');
					if(typeId==1){
						seeCarInfo(mortgageId,businessType);
					}else if(typeId==2){
						seeStockownershipInfo(mortgageId,businessType);
					}else if(typeId==3){
						seeCompanyInfo(mortgageId,businessType);
					}else if(typeId==4){
						seeDutyPersonInfo(mortgageId,businessType);
					}else if(typeId==5){
						seeMachineInfo(mortgageId,businessType);
					}else if(typeId==6){
						seeProductInfo(mortgageId,businessType);
					}else if(typeId==7){
						seeHouseInfo(mortgageId,businessType);
					}else if(typeId==8){
						seeOfficeBuildingInfo(mortgageId,businessType);
					}else if(typeId==9){
						seeHouseGroundInfo(mortgageId,businessType);
					}else if(typeId==10){
						seeBusinessInfo(mortgageId,businessType);
					}else if(typeId==11){
						seeBusinessAndLiveInfo(mortgageId,businessType);
					}else if(typeId==12){
						seeEducationInfo(mortgageId,businessType);
					}else if(typeId==13){
						seeIndustryInfo(mortgageId,businessType);
					}else if(typeId==14){
						seeDroitUpdateInfo(mortgageId,businessType);
					}else{
						window.location.href="/error.jsp";
					}
				}
			},*/
		/*seeProjectInfo:function(){
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				var projectId = selected.get('projid');
				var businessType = selected.get('businessType');

					var idPrefix = "";
					if (businessType == 'SmallLoan') {
						idPrefix = "SmallLoanProjectInfo_";
					} else if (businessType == 'Financing') {
						idPrefix = "FinancingProjectInfo_";
					} else if (businessType == 'Guarantee') {
						idPrefix = "GuaranteeProjectInfo_";
					}
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
				
			}
		}*/
});
