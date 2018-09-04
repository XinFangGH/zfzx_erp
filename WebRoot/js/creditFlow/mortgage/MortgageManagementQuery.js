/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MortgageManagementQuery = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MortgageManagementQuery.superclass.constructor.call(this, {
			id:'MortgageManagementQuery'+this.businessType,
			title : '抵质押物',
			region : 'center',
			layout : 'border',
			iconCls:"btn-tree-team36",
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var data = [['车辆',1],['股权',2],['无限连带责任-公司',3],['无限连带责任-个人',4],['机器设备',5],['存货/商品',6],['住宅',7],['商铺写字楼',8],['住宅用地',9],['商业用地',10],['商住用地',11],['教育用地',12],['工业用地',13],['无形权利',14]];
		var statusdata = [['初步录入',1],['银行已放款未办理',2],['保中已办理',3],['客户已还款未解除',4],['已解除',5]];
		var mortgageStatus="";
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
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
					fieldLabel : '项目编号',
					anchor : '100%',
					name : 'projectNum'
				}]
			}, {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目名称',
					anchor : '100%',
					name : 'projectName'
				}  ]
			},{
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '抵质押物名称',
					anchor : '100%',
					name : 'mortgageName'
				}]
			}, {
				columnWidth : 0.3,
				layout : 'form',
				border : false,
				labelWidth : 70,
				labelAlign : 'right',
				items : [ {
					xtype : 'dickeycombo',
					nodeKey : 'dblx',
					anchor : '100%',
					fieldLabel : '担保类型',
					hiddenName : 'danbaoType'
				}]
			}, {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype:'combo',
					hiddenName : 'mortgageType',
					anchor : '100%',
					fieldLabel:'抵质押物类型',
					mode : 'local',
					forceSelection : true, 
					displayField : 'CompanyKind',
					valueField : 'CompanyKindValue',
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						data : data,
						fields:['CompanyKind','CompanyKindValue']
					})
				}]
			}/*, {
				columnWidth : .22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype:'combo',
					id:'mortgageStatusQuery',
					hiddenName : 'mortgageStatus',
					anchor : '100%',
					 triggerAction : 'all',
		            store : new Ext.data.ArrayStore({
		                autoLoad : true,
		                baseParams : {
		                    nodeKey : nodeKey
		                },
		                url : __ctxPath + '/system/loadIndepItemsDictionaryIndependent.do',
		                fields : ['dicKey', 'itemValue']
		            }),
		            displayField : 'itemValue',
		            valueField : 'dicKey',
					fieldLabel:'抵质押物状态'
					mode : 'local',
					forceSelection : true, 
					displayField : 'statusValue',
					valueField : 'statusId',
					editable : false,
					triggerAction : 'all',
					store : new Ext.data.SimpleStore({
						data : statusdata,
						fields:['statusValue','statusId']
					})
				}]
			}*/, {
				columnWidth : 0.12,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '最终估价',
					anchor : '100%',
					name : 'minMoney'
				}]
			}, {
				columnWidth : 0.02,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [ {
					fieldLabel : '元',
					labelSeparator:'', 
					anchor : "100%"
				}]
			}, {
				columnWidth : 0.06,
				layout : 'form',
				border : false,
				labelWidth :15,
				labelAlign : 'right',
				items : [ {
					xtype : 'numberfield',
					fieldLabel : '~',
					labelSeparator:'', 
					anchor : '100%',
					name : 'maxMoney'
				}]
			}, {
				columnWidth : 0.02,
				layout : 'form',
				border : false,
				labelWidth : 20,
				labelAlign : 'right',
				items : [ {
					fieldLabel : '元',
					labelSeparator:'', 
					anchor : "100%"
					//html:'<pre>万元</pre>'
					//fieldLabel : '任务分配时间',
					//name : 'Q_organizeCode_S_LK'
				}]
			},isShow?{
			    columnWidth : 0.22,
			    layout : 'form',
			    border:false,
			   	labelWidth : 80,
				labelAlign : 'right',
			    items : [
			    {
			      xtype : "combo",
			      anchor : "100%",
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
			    }
			   ]}:{
			     columnWidth:0.001,
			     border:false
			   },{
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					//width : 60,
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

		this.topbar = new Ext.Toolbar( {
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
			}/*,'->', {
				xtype : 'label',
				html : '【颜色预警：<font color="purple"> 合同签署未办理</font>&nbsp;&nbsp;<font color="blue">已办理</font>&nbsp;&nbsp;<font color="red">项目完成未解除</font>&nbsp;&nbsp;<font color="green">已解除</font>】'
			}*/ ]
		});
       var  projectName=function(data, cellmeta, record){
       	   
           return '<font color="blue">['+record.get("projnum")+']</font>'+record.get("projname");

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'id',
						type : 'int'
					}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
			           'assuremoney','finalprice','remarks','assuretypeidValue','personTypeValue',
			           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
			           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
			           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
			           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
			          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
			           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
			           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId',
			           'issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','enregisterDepartment',
			           'projid','mortgageId','mortgageStatus','loanDate','days','companyName','finalCertificationPrice','fundProjectId']);

       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath +'/credit/mortgage/getMortgageList.do?businessType='+this.businessType,
			reader : this.reader,
			autoLoad:true,
			groupField : 'projid'
		});
		
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,
			width:'100%',
			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			}),

			columns : [{
				header : 'id',
				dataIndex : 'id',
				align:'center',
				hidden : true
			}, {
				header : "分公司名称",
				dataIndex : "companyName",
				hidden : !isShow
		   }, {
			header : "项目名称",
			dataIndex : "projid",
			hidden : true,
			renderer : projectName
		   }/*, {
				header : '抵质押物名称',
				dataIndex : 'mortgagename',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
				
			}*/, {
				header : '抵质押物类型',
				dataIndex : 'mortgagepersontypeforvalue',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '担保类型',
				dataIndex : 'assuretypeidValue',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '所有权人',
				dataIndex : 'assureofnameEnterOrPerson',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '评估价值',
				align:'right',
				dataIndex : 'finalprice',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}, {
				header : '公允价值',
				align:'right',
				dataIndex : 'finalCertificationPrice',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}, {
				header : '抵质押率',
				dataIndex : 'assuremoney',
				width:120,
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				    if(value!=null && ""!=value){
		           	    value=value+"%";
		            }
					return 	value	
				}
			}/*, {
				header : '抵质押物状态',
				dataIndex : 'mortgageStatus',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                if(null!=data && data=='yhyfkwbl'){
	                	 metadata.attr = ' ext:qtip="银行已放款未办理"';
	                    return '<font color="red">银行已放款未办理</font>'
	                }else if(null!=data && data=='bzybl'){
	                	metadata.attr = ' ext:qtip="保中已办理"';
	                	return '<font color="blue">保中已办理</font>'
	                }else if(null!=data && data=='khyhkwjc'){
	                	metadata.attr = ' ext:qtip="客户还款未解除"';
	                    return '<font color="purple">客户还款未解除</font>'
	                }else if(null!=data && (data=='guaranteeyjc' || data=='smallyjc')){
	                	metadata.attr = ' ext:qtip="已解除"';
	                    return '<font color="green">已解除</font>'
	                }else if(null!=data && (data=='guaranteecblr' || data=='smallcblr')){
	                	metadata.attr = ' ext:qtip="初步录入"';
	                    return '初步录入'
	                }else if(null!=data && data=='htqswbl'){
	                	metadata.attr = ' ext:qtip="合同签署未办理"';
	                    return '<font color="purple">合同签署未办理</font>'
	                }else if(null!=data && (data=='smallybl' || data=='financingybl')){
	                	metadata.attr = ' ext:qtip="已办理"';
	                    return '<font color="blue">已办理</font>'
	                }else if(null!=data && data=='xmwcwjc'){
	                	metadata.attr = ' ext:qtip="项目完成未解除"';
	                    return '<font color="red">项目完成未解除</font>'
	                }else if(null!=data && data=='financingwbl'){
	                	metadata.attr = ' ext:qtip="未办理"';
	                    return '<font color="red">未办理</font>'
	                }else{
	                   return "";
	                
	                }
	            }
			}*/, {
				header : '合同个数',
				dataIndex : 'contractCount',
				width:120,
				align:'center',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '办理时间',
				align:'center',
				dataIndex : 'transactDate',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}/*, {
				header : '距合同签署天数',
				dataIndex : 'days',
				width:130,
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				    if(value!=null && ""!=value){
		           	    value=value+"天";
		            }
					return 	value	
				}
			}*//*, {
				header : '合同个数',
				dataIndex : 'contractCount',
				renderer : function(v){
						if(v==null||v=='null'||v==0){
							return '<a href="#" title ="查看合同详情" <font color=blue>0</font></a>';							
						}else{
							return '<a href="#" title ="查看合同详情" <font color=blue>'+v+'</font></a>';
							
						}
				}
			}, {
				header : '办理文件',
				dataIndex : 'fileCounts',
				renderer : function(v){
						if(v==null||v=='null'||v==0){
							return '<a href="#" title ="点击可上传" <font color=blue>0</font></a>';							
						}else{
							return '<a href="#" title ="点击可上传或下载" <font color=blue>'+v+'</font></a>';
							
						}
				}
			}, {
				header : '预览',
				dataIndex : 'fileCounts',
				renderer : function(v){
					if(v==null||v=='null'||v==0){
						return '';
					}else{
						return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
					}
				}
			}*/, {
				header : '是否办理',
				align:'center',
				dataIndex : 'isTransact',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	               if(data==true){
	                  return '是';
	               }else{
	               	  return '否';
	               }
	            }
			}, {
				header : '是否解除',
				align:'center',
				dataIndex : 'isunchain',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	               if(data==true){
	                  return '是';
	               }else{
	               	  return '否';
	               }
	            }
			}, {
				header : '是否归档',
				align:'center',
				dataIndex : 'issign',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                if(data==true){
	                  return '是';
	               }else{
	               	  return '否';
	               }
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
		/*var businessType=Ext.getCmp("businessTypeQuery").getValue();
		if(businessType=='SmallLoan'){
			this.gridPanel.getTopToolbar().items.last().setText('<pre>                                    【 颜色预警：<font color="purple"> 合同签署未办理</font>    <font color="blue">已办理</font>   <font color="red">项目完成未解除</font>   <font color="green">已解除</font>】 </pre>',false);
			this.gridPanel.getColumnModel().setColumnHeader(10,'合同签署时间')
			this.gridPanel.getColumnModel().setColumnHeader(12,'距合同签署天数')
		}else if(businessType=='Financing'){
			this.gridPanel.getTopToolbar().items.last().setText('<pre>                                     【颜色预警：<font color="red">未办理</font>   <font color="blue">已办理</font>】   </pre>',false);
			this.gridPanel.getColumnModel().setColumnHeader(10,'合同签署时间')
			this.gridPanel.getColumnModel().setColumnHeader(12,'距合同签署天数')
		}else if(businessType=='Guarantee'){
			this.gridPanel.getTopToolbar().items.last().setText('<pre>                                     【颜色预警：<font color="red"> 银行已放款未办理</font>    <font color="blue">保中已办理</font>   <font color="purple">客户还款未解除</font>   <font color="green">已解除</font>】 </pre>',false);
			this.gridPanel.getColumnModel().setColumnHeader(10,'银行放款时间')
			this.gridPanel.getColumnModel().setColumnHeader(12,'距银行放款天数')
		}*/
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	//GridPanel行点击处理事件
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
				/*if(14==columnIndex){
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
				}*/
	},
	
	
	seeThirdContract : function() {
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
					var title = selected.get('mortgagepersontypeforvalue');
					var thirdRalationId = selected.get('id');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					var businessType = selected.get('businessType');
					var piKey = selected.get('fundProjectId');
					var sprojectId = selected.get('projid');
					
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
								sprojectId:sprojectId,
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
			},
	seeMortgageInfo : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{
				var mortgageId = selected.get('id');
				if(this.businessType=="Financing"){
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
					}else if(typeId==18){
						seeReceivables(mortgageId,businessType);
					}else{
						window.location.href="/error.jsp";
					}
				}
			},
		seeProjectInfo:function(){
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				var projectId = selected.get('projid');
				var businessType = selected.get('businessType');

					var idPrefix = "";
					if (businessType == 'SmallLoan') {
						projectId = selected.get('fundProjectId');
						idPrefix = "SmallLoanProjectInfo_";
					} else if (businessType == 'Financing') {
						idPrefix = "FinancingProjectInfo_";
					} else if (businessType == 'Guarantee') {
						idPrefix = "GuaranteeProjectInfo_";
					}else if (businessType == 'LeaseFinance') {
						idPrefix = "LeaseFinanceProjectInfo_";
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
		}
});
