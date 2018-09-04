/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnItemsListManagment = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PawnItemsListManagment.superclass.constructor.call(this, {
			id:'PawnItemsListManagment'+this.type,
			title : this.type=='underway'?'在当物品管理':(this.type=='finish'?'赎当物品管理':'绝当物品管理'),
			region : 'center',
			layout : 'border',
			iconCls :'btn-team32',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			region : 'north',
			border : false,
			height : 40,
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
				iconCls : 'btn-add',
				text : '添加出入库记录',
				xtype : 'button',
				hidden : (this.type=='underway' && isGranted('_add_crkrecord_'+this.type))?false:true,
				scope : this,
				handler : this.addCrkRecord
			}, {
				iconCls : 'btn-readdocument',
				text : '查看出入库记录',
				xtype : 'button',
				hidden : isGranted('_see_crkrecord_'+this.type)?false:true,
				scope : this,
				handler : this.seeCrkRecord
			}, {
				iconCls : 'btn-add',
				text : '添加检查记录',
				xtype : 'button',
				hidden : (this.type=='underway' && isGranted('_add_jcrecord_'+this.type))?false:true,
				scope:this,
				handler : this.addInspection
			}, {
				iconCls : 'btn-readdocument',
				text : '查看检查记录',
				xtype : 'button',
				scope:this,
				hidden : isGranted('_see_jcrecord_'+this.type)?false:true,
				handler : this.seeInspection
			}, {
				iconCls : 'btn-readdocument',
				text : '查看典当物详细信息',
				xtype : 'button',
				hidden : isGranted('_see_pawnItemsList_'+this.type)?false:true,
				scope:this,
				handler : this.seePawnItemInfo
			} ]
		});
       var  projectName=function(data, cellmeta, record){
       	   
           return '<font color="blue">['+record.get("projectNumber")+']</font>'+record.get("projectName");

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'pawnItemId',
						type : 'long'
					}, 'pawnItemName','pawnItemType','pawnItemTypeName', 
			           'projectId','pawnItemMoney','accessTime','assessedValuationValue','businessType',
			           'counts','discountRate','remarks',
			           'specificationsStatus','projectName','projectNumber','customerName',
			           'fileCount','pawnItemStatus','companyName','crkstatus','storageLocation']);

       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath +'/creditFlow/pawn/pawnItems/getAllListPawnItemsList.do?pawnItemStatus='+this.type,
			reader : this.reader,	
			autoLoad:true,
			groupField : 'projectId'
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,

			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			}),

			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : "分公司名称",
				dataIndex : "companyName",
				hidden : !isShow
		   }, {
				header : "项目名称",
				dataIndex : "projectId",
				hidden : true,
				renderer : projectName
		   }, {
				header : '当物名称',
				dataIndex : 'pawnItemName',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '规格和状态',
				dataIndex : 'specificationsStatus',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '评估价值',
				dataIndex : 'assessedValuationValue',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                if(data!=null && ""!=data){
	                	data=Ext.util.Format.number(data,'0,000')
	                }
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '折当率',
				dataIndex : 'discountRate',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"%";
		            }
					return 	value
				}
			}, {
				header : '当物金额',
				dataIndex : 'pawnItemMoney',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}, {
				header : '材料分数',
				dataIndex : 'fileCount'
				
			}, {
				header : '备注',
				dataIndex : 'remarks',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '开始日期',
				dataIndex : 'startDate',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '到期日期',
				dataIndex : 'intentDate',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '状态',
				dataIndex : 'crkstatus',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
                	if(null!=data && data!=''){
                		if(data==0){
                			data='在库'
                		}else if(data==1){
                			data='出库'
                		}
                	}
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '存放地点',
				dataIndex : 'storageLocation',
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
	
	relieveMortgage : function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			if(Ext.getCmp("businessType").getValue()=="Financing"){
			    mortgageId=selected.get('mortgageId')
		    }
		    var businessType=selected.get('businessType');
		    new RelieveMortgageWindow({
			    mortgageId:mortgageId,
			    businessType:businessType,
			    gridPanel:grid
		    }).show()
			
		}
	},
	banliMortgage : function(){
	
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			if(Ext.getCmp("businessType").getValue()=="Financing"){
			    mortgageId=selected.get('mortgageId')
		    }
		     var businessType=selected.get('businessType');
		     new BanliMortgageWindow({
			    mortgageId:mortgageId,
			    businessType:businessType,
			    gridPanel:grid
		    }).show()
			
		}
	
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
			},
	seePawnItemInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var pawnItemId = selected.get('pawnItemId');
		var typeId = selected.get('pawnItemType');
			if(typeId==1){
				seeVehicleInfo(pawnItemId,typeId);
			}else if(typeId==2){
				seeStockownership(pawnItemId,typeId);
			}else if(typeId==5){
				seeMachine(pawnItemId,typeId);
			}else if(typeId==6){
				seeProduct(pawnItemId,typeId);
			}else if(typeId==7 || typeId==15 || typeId==16 || typeId==17){
				seeHouse(pawnItemId,typeId);
			}else if(typeId==8){
				seeOfficeBuilding(pawnItemId,typeId);
			}else if(typeId==9){
				seeHouseGround(pawnItemId,typeId);
			}else if(typeId==10){
				seeBusiness(pawnItemId,typeId);
			}else if(typeId==11){
				seeBusinessAndLive(pawnItemId,typeId);
			}else if(typeId==12){
				seeEducation(pawnItemId,typeId);
			}else if(typeId==13){
				seeIndustry(pawnItemId,typeId);
			}else if(typeId==14){
				seeDroitUpdate(pawnItemId,typeId);
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
		},
		addCrkRecord : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				record=s[0]
				new PawnCrkRecordWin({projectId : record.data.projectId,businessType : record.data.businessType,pawnItemId:record.data.pawnItemId}).show()
			}
		},
		seeCrkRecord : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				record=s[0]
				new PawnCrkRecordWin({projectId : record.data.projectId,businessType : record.data.businessType,pawnItemId:record.data.pawnItemId,isHidden:true}).show()
			}
		},
		addInspection : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				record=s[0]
				new PawnInspectionRecordWin({projectId : record.data.projectId,businessType : record.data.businessType,pawnItemId:record.data.pawnItemId}).show()
			}
		},
		seeInspection : function(){
			var s = this.gridPanel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				record=s[0]
				new PawnInspectionRecordWin({projectId : record.data.projectId,businessType : record.data.businessType,pawnItemId:record.data.pawnItemId,isHidden:true}).show()
			}
		}
});
