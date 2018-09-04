RiskMaterialManager = Ext.extend(Ext.Panel, {
	constructor : function(conf) {
		Ext.applyIf(this, conf);
		this.initUIComponents();
		RiskMaterialManager.superclass.constructor.call(this, {
					id:'RiskMaterialManager',
					title : '贷款材料管理',
					iconCls:"btn-tree-team36",
					layout : 'border',
					items : [this.searchPanel, this.gridPanel]
				});
	},
	initUIComponents : function() {
		var isHidden =false;			
		this.searchPanel = new HT.SearchPanel({
						layout : 'column',
						style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
						region : 'north',
						height : 20,
						anchor : '96%',
						layoutConfig: {
			               align:'middle',
			               padding : '5px'
			               
			            },
						items : [{   
								columnWidth : 0.4,
								layout : 'form',
								border : false,
								labelWidth : 100,
								labelAlign : 'right',
								items : [ {
								xtype : 'combo',
								anchor : '100%',
								triggerClass : 'x-form-search-trigger',
								fieldLabel : "项目名称",
								name : "projectName",
								editable:false,
								scope : this,
								onTriggerClick : function() {
									var obj=this;
									var op=this.ownerCt.ownerCt;
									op.getCmpByName('projId').setValue("");
									op.getCmpByName('businessType').setValue("");
									new ProjectListWin({
										fromPage:'GeneralizedFlowsheet',
										businessClass:"AllProject",
										callback : function(projectId, projectName,businessType, record) {
											obj.setValue(projectName);
											obj.nextSibling().setValue(projectId);
											obj.nextSibling().nextSibling().setValue(businessType);
										
										}
									}).show();
																			
								},
								resizable : true,
								mode : 'romote',
								lazyInit : false,
								typeAhead : true,
								minChars : 1,
						
								triggerAction : 'all'
							
							},{
								xtype : 'hidden',
								name : 'projId'
							},{
								xtype : 'hidden',
								name : 'businessType'
							},{
								xtype : 'hidden',
								name : 'show',
								value:true
							}] 
								      
							},{
							columnWidth : .08,
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
						},{
							columnWidth : .08,
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
	
					});
					
		this.Employee = Ext.data.Record.create([
             {name: 'materialsType', mapping: 'parentName'},
             {name: 'materialsName', mapping: 'materialsName'},
             {name: 'datumNums', mapping: 'datumNums'},
             {name: 'datumNumsOfLine', mapping: 'datumNumsOfLine'},
             {name: 'remark', mapping: 'remark'},
             {name: 'proMaterialsId', mapping: 'proMaterialsId'},
             {name: 'isReceive', mapping: 'isReceive'},
             {name: 'ruleExplain', mapping: 'ruleExplain'},
             {name: 'isArchiveConfirm', mapping: 'isArchiveConfirm'},
             {name: 'confirmTime', mapping: 'confirmTime'},
             {name: 'isPigeonhole', mapping: 'isPigeonhole'}
		]);
		this.reader = new Ext.data.JsonReader({totalProperty : 3,root : 'result'},this.Employee);
		this.store = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({url :__ctxPath + '/materials/listEnterpriseSlProcreditMaterials.do'}) ,
	                reader: this.reader,
	                groupField:'materialsType'
				});
		this.store.load();
		//有多少份材料清单
		var iteamCountRender=function(v){
				if(v&&v>0){
					return v + "份" ;
				}else{
					return "0份";
				}
		};
		
		//是否已收到的render
		var receiveRender = function(v){
		    if(v){
		       return "是"
		    } else {
		        return "否"
		     }
		}
		//备注的render
		var remarkRender=function(v){
		     if(v&&v!=""){
				 return v;
			}else {
				 return '<font color=red>未填写</font>';
			}
		};
		this.topbar = new Ext.Toolbar({
						items : []
				});
		var sm = new Ext.grid.CheckboxSelectionModel({singleSelect:false});
		var cm = new Ext.grid.ColumnModel([
				            new Ext.grid.RowNumberer( {header : '序号',align:'center',	width : 35}),
				            {header : "材料类型",width : 110,sortable : true,dataIndex : 'materialsType',hidden : true},
				            {header : 'proMaterialsId',align:'center',dataIndex : 'proMaterialsId',hidden : true},
				            {header: "材料名称",dataIndex: 'materialsName'},
				            {header: "上传材料份数",align:'center',fixed: true,width:93,dataIndex: 'datumNums',renderer :iteamCountRender},
				            {header: "线下材料份数",align:'center',fixed: true,width:93,dataIndex: 'datumNumsOfLine',renderer : fenRenderer},
				            {header: "是否收到",fixed: true,align:'center',width:67,dataIndex: 'isReceive',renderer : receiveRender},
				            {header: "是否归档",fixed: true,align:'center',width:67,dataIndex: 'isArchiveConfirm',renderer : receiveRender},
				            //是否已提交  add by gao
				            {header: "是否提交",fixed: true,align:'center',width:67,dataIndex: 'isPigeonhole',renderer : receiveRender},
				            //提交时间  add by gao
				            {header : '提交时间',format : 'Y-m-d',align:'center',dataIndex : 'confirmTime',sortable : true,width : 82,renderer : ZW.ux.dateRenderer(this.confirmDatefield)},
				            {header: "备注",fixed: true,width:153,dataIndex: 'remark',renderer : remarkRender,hidden:this.isp2pEdit,editor:isHidden && this.isarchives?null:{xtype:'textfield',readOnly:this.isHidden}},
				            {header: "上传或下载",fixed: true,width:86,align:'center',dataIndex: 'uploadOrDown',hidden:this.isp2pEdit,renderer:function(){
				                return "<img src='"+__ctxPath+"/images/download-start.png'/>";
				            }},
				            {header: "预览",fixed: true,width:40,dataIndex: 'viewPic',renderer:this.imageView}
				        ]);
		 var tpl=new Ext.XTemplate(
            '<p><b>材料类型:</b>{materialsType}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>材料名称:</b>{materialsName}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>线下材料份数:</b>{datumNumsOfLine}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
            '<p><b>上传材料份数:</b>{datumNums}&nbsp;&nbsp;&nbsp;&nbsp;</p>',
	        '<p><b>是否已收到:</b>&nbsp;&nbsp;&nbsp;&nbsp;<tpl if="isReceive==true">已收到</tpl><tpl if="isReceive==false">未收到</tpl></p>',
	        '<p><b>合规说明:</b>{ruleExplain}</p>',
	        '<p><b>备注:</b>{remark}</p>'
          )
 var expander = new Ext.grid.RowExpander({
 	    enableCaching : true
 });
		this.gridPanel = new Ext.grid.GridPanel({
					id:'riskMaterialGrid',
					region : 'center',
					store : this.store,
					shim : true,
					trackMouseOver : true,
					loadMask : true,
					cm : cm,
					tbar : this.topbar,
					selModel : new Ext.grid.RowSelectionModel(),
				    view: new Ext.grid.GroupingView({
                                 forceFit:true,
                                 groupTextTpl: '{text}'
                    }),
                    stripeRows : true,
				    width: '80%',
				    autoHeight : this.isAutoHeight,
				    border : false,
				    //plugins: expander,
				    animCollapse: false,
				    iconCls:'icon-grid',
					//bbar : new HT.PagingBar({store : this.store}),
					listeners : {
                		   'cellclick' : function(grid,rowIndex,columnIndex,e){
                		   	
                		   	         var record = grid.getStore().getAt(rowIndex);   //Get the Record
                                     var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name
									if("uploadOrDown"==fieldName){
										 var loadData=null;
										 var uploadFileCounts=0;
										 var markId=grid.getStore().getAt(rowIndex).get("proMaterialsId");
										 var talbeName="sl_procredit_materials.";
										 var mark=talbeName+markId;
										 var title = "下载材料";
										 loadData = function(){};//不需要执行该方法
										 uploadFileCounts = 0;//不能上传附件
										 uploadfile(title,mark,uploadFileCounts,null,null,loadData);
									}
									if("viewPic"==fieldName){   
										 var markId=grid.getStore().getAt(rowIndex).get("proMaterialsId");
										 var talbeName="sl_procredit_materials.";
										 var mark=talbeName+markId;
									     picViewer(mark,this.isHiddenEdit);
									}
								 }
                        }
				});
		
		
			
	},//end of initUIComponents
		// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},

	// 查询条件
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	refresh : function() {
		this.store.reload();
	},
	//导出到Excel
	toExcel:function(){
		var userId=this.searchPanel.getCmpByName('userId').getValue();
		var taskName=this.searchPanel.getCmpByName('taskName').getValue();
		var projectName=this.searchPanel.getCmpByName('projectName').getValue();
		
		var status=this.searchPanel.getCmpByName('status').getValue();
		var taskstatus=this.searchPanel.getCmpByName('taskstatus').getValue();
		var stateDate=this.searchPanel.getCmpByName('stateDate').getValue();
		var endDate=this.searchPanel.getCmpByName('endDate').getValue();
		
		var finishStartDate=this.searchPanel.getCmpByName('finishStartDate').getValue();
		var finishEndDate=this.searchPanel.getCmpByName('finishEndDate').getValue();
		
		window.open(__ctxPath + '/flow/allExportExcelTask.do?taskName='+taskName+"&userId="+userId+"&projectName="+projectName+"&taskstatus="+taskstatus+"&stateDate="+stateDate+"&endDate="+endDate+"&finishStartDate="+finishStartDate+"&finishEndDate="+finishEndDate+"&status="+status,'_blank');
		
	},
	imageView :  function(){
			         return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
			}

});
