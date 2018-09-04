/**
 * @author:
 * @class LeaseObjectManagement
 * @extends Ext.Panel
 * @description [LeaseObjectManagement]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
LeaseObjectManagement = Ext.extend(Ext.Panel, {
	isManaged:false,
	isHandled:false,
	isBuyBack:false,
	hiddenChangePlace:false,//转移记录
	hiddenInsurancePay:false,//保险理赔
	hiddenOwnerChange:false,//所有权转移 默认 不隐藏
	hiddenManageObject:false,//所有权转移 默认 不隐藏
	constructor : function(_cfg) {
		if(typeof(_cfg.isManaged)!="undefined"){
			this.isManaged = _cfg.isManaged;
		}
		if(typeof(_cfg.isHandled)!="undefined"){
			this.isHandled = _cfg.isHandled;
		}
		if(typeof(_cfg.isBuyBack)!="undefined"){
			this.isBuyBack = _cfg.isBuyBack;
		}
		if(typeof(_cfg.hiddenOwnerChange)!="undefined"){//所有权转移
			this.hiddenOwnerChange = _cfg.hiddenOwnerChange;
		}
		if(typeof(_cfg.hiddenInsurancePay)!="undefined"){//保险理赔
			this.hiddenInsurancePay = _cfg.hiddenInsurancePay;
		}
		if(typeof(_cfg.hiddenChangePlace)!="undefined"){//转移记录
			this.hiddenChangePlace = _cfg.hiddenChangePlace;
		}
		if(typeof(_cfg.hiddenManageObject)!="undefined"){//租赁物处置
			this.hiddenManageObject = _cfg.hiddenManageObject;
		}
		if(typeof(_cfg.extCpmId)!="undefined"){//防止组件id重复 不能打开另一个 add by gao
			this.extCmpId = _cfg.extCpmId;//另用作 授权管理
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		switch(this.extCmpId){
			case 1:
				this.title = "办理中租赁物管理";
				break;
			case 2:
				this.title = "在租物管理";
				break
			case 3:
				this.title = "已回购租赁物管理";
				break;
			case 4:
				this.title = "已处置租赁物管理";
				break;
		}
		LeaseObjectManagement.superclass.constructor.call(this, {
			id:'LeaseObject'+ this.extCmpId,
			title : this.title,
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
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
			
				items : [ {
					xtype:'hidden',
					name:'isManaged',
					value:this.isManaged
				}, {
					xtype:'hidden',
					name:'isHandled',
					value:this.isHandled
				},{
					xtype:'hidden',
					name:'isBuyBack',
					value:this.isBuyBack
				},{
					xtype : 'textfield',
					fieldLabel : '项目编号',
					anchor : '100%',
					name : 'projectNumber'
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
			},  {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '规格型号',
					anchor : '100%',
					name : 'standardSize'
				}]
			}, {
				columnWidth : 0.22,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '所有权人',
					anchor : '100%',
					name : 'owner'
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
				text : '查看租赁物详情',
				xtype : 'button',
				hidden : isGranted('_seeLeaseObjectInfo_fl'+this.extCmpId)?false:true,
				scope : this,
				handler : this.seeLeaseObjectInfo
			}, {
				iconCls : 'deleteIcon',
				text : '转移记录',
				xtype : 'button',
				hidden : this.hiddenChangePlace?true:(isGranted('_seeLeaseObjectChPlaceInfo_fl'+this.extCmpId)||isGranted('_editLeaseObjectChPlaceInfo_fl'+this.extCmpId))?false:true,
				scope : this,
				handler : this.changePlaceRecord
			}, {
				iconCls : 'deleteIcon',
				text : '保险理赔',
				xtype : 'button',
				hidden : this.hiddenInsurancePay?true:(isGranted('_seeLeaseObjectInsuranceInfo_fl'+this.extCmpId)||isGranted('_editLeaseObjectInsuranceInfo_fl'+this.extCmpId))?false:true,
				scope:this,
				handler : this.leaseObjectInsurancePay
			}, {
				iconCls : 'deleteIcon',
				text : '所有权转移记录',
				xtype : 'button',
				hidden : this.hiddenOwnerChange?true:(isGranted('_seeLeaseObjectChOwnerInfo_fl'+this.extCpmId)||isGranted('_editLeaseObjectChOwnerInfo_fl'+this.extCpmId))?false:true,
				scope:this,
				handler : this.leaseObjectChOwner
			}, {
				iconCls : 'btn-flow-design',
				text : '租赁物处置',
				xtype : 'button',
				scope:this,
				hidden : this.hiddenManageObject?true:this.isHandled?true:this.isBuyBack?true:((isGranted('_seeLeaseObjectHandleInfo_fl'+this.extCmpId)||isGranted('_editLeaseObjectHandleInfo_fl'+this.extCmpId))?false:true),//只有在租物管理才可编辑，而在回购，已处置时都是已办理状态ismanaged = true
				handler : this.leaseObjectHandle
			}/*,'->', {
				xtype : 'label',
				html : '【颜色预警：<font color="purple"> 合同签署未办理</font>&nbsp;&nbsp;<font color="blue">已办理</font>&nbsp;&nbsp;<font color="red">项目完成未解除</font>&nbsp;&nbsp;<font color="green">已解除</font>】'
			}*/ ]
		});
       var  projectName=function(data, cellmeta, record){
       	   
           return '<font color="blue">['+record.get("projectNumber")+']</font>'+record.get("projectName");

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'id',
						type : 'int'
					}, 'suppliorId','name','buyPrice', 
			           'objectCount','originalPrice','standardSize','suppliorName','objectComment',
			           'projectId','useYears','buyDate','owner','isManaged','fileCount','projectNumber','projectName','destPlace','handlePersonName','remnantEvaluatedPrice','remnantActualPrice','handleDate','handleComment']);
       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath +'/creditFlow/leaseFinance/project/listAllViewFlLeaseobjectInfo.do',
			reader : this.reader,	
			autoLoad:true,
			baseParams:{isManaged:this.isManaged,isHandled:this.isHandled,isBuyBack:this.isBuyBack},
			groupField : 'projectId'
		});
		 this.jStore_mortgage.load()
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,

			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			}),
			listeners:{
				scope : this,
				'cellclick':function(grid,row,col,e){
					var fieldName = grid.getColumnModel().getDataIndex(col); //Get field name
					var objId = grid.getStore().getAt(row).get("id");
					var projectId = grid.getStore().getAt(row).get("projectId");
					var fileCountVal = grid.getStore().getAt(row).get("fileCount");
					if(fieldName == 'fileCount'){//重新上传
						if(0==fileCountVal||'0'==fileCountVal){
							return false;
						}else{
							this.seeFileCountInfo(projectId,objId);
						}
					}
				}},
			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : "租赁物名称",
				dataIndex : "name",
				hidden : !isShow
		   }, {
				header : "项目名称",
				dataIndex : "projectId",
				hidden : true,
				renderer : projectName
		   }, {
				header : '规格型号',
				dataIndex : 'standardSize'
			}, {
				header : '价格',
				dataIndex : 'buyPrice',
				renderer: function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}, {
				header : '认购价格',
				dataIndex : 'buyPrice',
				renderer :  function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}, {
				header : '数量',
				dataIndex : 'objectCount'
			}, {
				header : '使用年限',
				dataIndex : 'useYears'
			}, {
				header : '购入日期',
				dataIndex : 'buyDate',
				width:120,
				renderer :function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			},{
				header : '供货单位',
				dataIndex : 'suppliorName'
			}, {
				header : '所有人',
				dataIndex : 'owner'
			}, {
				header : '材料份数',
				dataIndex : 'fileCount',
				renderer : function(val, m, r) {
						return '<a title="预览'+r.get('fileCount')+'" style ="TEXT-DECORATION:underline;cursor:pointer" >'+ val + '</a>';
				}
			},{
				header : '最后存放地点',
				dataIndex : 'destPlace'
			} ,{
				header : '处置日期',
				dataIndex : 'handleDate',
				width:120,
				hidden:!this.isHandled,
				renderer :function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			},{
				header : '处置人',
				hidden:!this.isHandled,
				dataIndex : 'handlePersonName'
			},{
				header : '残值估价',
				hidden:!this.isHandled,
				dataIndex : 'remnantEvaluatedPrice',
				renderer :  function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			},{
				header : '实际价值',
				hidden:!this.isHandled,
				dataIndex : 'remnantActualPrice',
				renderer :  function(value,metaData, record,rowIndex, colIndex,store) {
		            if(value!=null && ""!=value){
		           	    value=Ext.util.Format.number(value,'0,000')+"元";
		            }
					return 	value
				}
			}]
				});


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
	cellClick : function(grid,rowIndex,columnIndex,e){/*
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
	*/},
	
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
	leaseObjectHandle : function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		var standardSize = selected.get('standardSize');
		var destPlace = selected.get('destPlace');
		var name = selected.get('name');
		new LeaseObjectManagementObjectHandle({gridPanel:this.gridPanel,objectId:id,editable:isGranted('_editLeaseObjectHandleInfo_fl'+this.extCmpId)}).show();
		}
	},
	//租赁物理赔记录   ok3
	leaseObjectInsurancePay : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		var standardSize = selected.get('standardSize');
		var destPlace = selected.get('destPlace');
		new LeaseObjectManagementInsurancePay({gridPanel:this.gridPanel,id:id,standardSize:standardSize,destPlace:destPlace,editable:isGranted('_editLeaseObjectInsuranceInfo_fl'+this.extCmpId)}).show();
		}
	},
	//查看租赁物信息  ok3
	seeLeaseObjectInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		seeObjectInfo(id,"leaseFinanceObject",this.isHandled);
		}
	},
	//转移记录处理方法   ok3
	changePlaceRecord:function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		var standardSize = selected.get('standardSize');
		var destPlace = selected.get('destPlace');
		new LeaseObjectManagementChPlace({gridPanel:this.gridPanel,id:id,standardSize:standardSize,destPlace:destPlace,editable:isGranted('_editLeaseObjectInfo_fl'+this.extCmpId)}).show();
		}
	},
	//所有权转移记录  ok3
	leaseObjectChOwner:function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var id = selected.get('id');
		new LeaseObjectManagementChOwner({id:id,editable:isGranted('_editLeaseObjectChOwnerInfo_fl'+this.extCmpId)}).show();
		}
	},
	seeFileCountInfo:function(projectId,objId){
		new AddLeaseObjectWin({projectId:this.projectId,gridPanel:this.gridPanel,objectId:objId,onlyFile:true}).show()//id  test
	}
});
