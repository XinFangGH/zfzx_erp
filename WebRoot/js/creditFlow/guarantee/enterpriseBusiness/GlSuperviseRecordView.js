/**
 * @author
 * @class SlSuperviseRecordView
 * @extends Ext.Panel
 * @description 贷中监管记录管理
 * @company 智维软件
 * @createtime:
 */
GlSuperviseRecordView = Ext.extend(Ext.Panel, {
	projId:null,
	gridPanel:null,
	topbar:null,
	isHidden:false,
	flowType:null,
	constructor : function(_cfg) {
	    if(typeof(_cfg.projectId)!="undefined")
		{
		     this.projId=_cfg.projectId;
		}
		if(typeof(_cfg.flowType)!="undefined")
		{
		     this.flowType=_cfg.flowType;
		}
	    if (_cfg.isHidden) {
			  this.isHidden = _cfg.isHidden;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		GlSuperviseRecordView.superclass.constructor.call(this, {
					border : false,
					layout : 'anchor',
					anchor : '100%',
					modal : true,
					items : [this.gridPanel]
				});
	},
	initUIComponents : function() {
        this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false
		})
		if(!this.isHidden){
		    		this.topbar = new Ext.Toolbar({
								items : [{
											iconCls : 'btn-add',
											text : '编辑',
											xtype : 'button',
											scope : this,
											handler : this.editRs
										},{
											iconCls : 'btn-del',
											text : '删除',
											xtype : 'button',
											scope : this,
											handler : this.removeSelRs
										}]
		                });
		}
		this.gridPanel = new HT.GridPanel({
			isShowTbar : false,
			region : 'center',
			autoHeight:true,
			showPaging:false,
			tbar : this.topbar,
			hiddenCm:this.isHidden,
		    url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/listSuperviseRecordGLGuaranteeloanProject.do',
			baseParams:{
				projectId : this.projId
			},
			fields : [{
						name : 'id',
						type : 'int'
					}, 'remark','reason','startDate','endDate','continuationRate','projectId'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, {
						header : '申请原因',
						dataIndex : 'reason'
					}, {
						header : '展期贷款利率',
						dataIndex : 'continuationRate',
						 renderer :  function(value,metaData,record,rowIndex,colIndex,store){
						 	if(value!=""){
						 	  return value+"%";
						 	}
						 	else{
						 	  return "";
						 	}
						 	
						 }
					},{
						header : '展期开始日期',
						width : 96,
						format : 'Y-m-d',
						dataIndex : 'startDate',
					    renderer :  function(value,metaData,record,rowIndex,colIndex,store){
									         if(typeof (value) == "string"){ 
									         	 /* var beginDate = new Date(value.replace(/-/g,"/"));
									         	  return Ext.util.Format.date(beginDate,"Y-m-d");*/
									         	  return value;
								             } 
								             var dateTime=Ext.util.Format.date(value, 'Y-m-d');
								             return dateTime
						}
					}, {
						header : '展期结束日期',
						width : 96,
						format : 'Y-m-d',
						dataIndex : 'endDate',
					    renderer :  function(value,metaData,record,rowIndex,colIndex,store){
									         if(typeof (value) == "string"){ 
									         	 /* var beginDate = new Date(value.replace(/-/g,"/"));
									         	  return  Ext.util.Format.date(beginDate,"Y-m-d");*/
									         	  return value;
								             } 
								             var dateTime=Ext.util.Format.date(value, 'Y-m-d');
								             return dateTime
						}
					},{
						header : '备注',
						dataIndex : 'remark'
					}]
			});

		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new SlSuperviseRecordForm({
								id : rec.data.id
							}).show();
				});
	},
	createRs : function() {
		new SlSuperviseRecordForm().show();
	},
	removeRs : function(id) {
		$postDel({
					url : __ctxPath + '/supervise/multiDelSlSuperviseRecord.do',
					ids : id,
					grid : this.gridPanel
				});
	},
	removeSelRs : function() {
		       $delGridRs({
					url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/deleteSuperviseRecordGLGuaranteeloanProject.do',
					grid : this.gridPanel,
					error:'确认删除吗,删除记录无法恢复?',
					idName : 'id'
				});
	},
	updateForm:function(record,gridObj){
		            var flowType=this.flowType;
	                 var businessType="Guarantee";
	                var id=record.data.id;
				    var reason=record.data.reason;
				    var startDate=new Date(record.data.startDate.replace(/-/g,"/")).format("Y-m-d");
				    var endDate= new Date(record.data.endDate.replace(/-/g,"/")).format("Y-m-d");
				    var remark=record.data.remark;
				    var projectId=record.data.projectId;
				    var continuationRate=record.data.continuationRate;
				    var outObj=gridObj.ownerCt.ownerCt; //展期记录的fileset; 当前外围对象 很重要  可以通过此对象 获取你要操作的fileSet
				    var outObjPanel=gridPanel.ownerCt.ownerCt.ownerCt; 
			        var financeInfoFieldset=outObjPanel.getCmpByName('financeInfoFieldset');
				  
	                var fp=  new Ext.FormPanel({
								modal : true,
								labelWidth : 100,
								frame:true,
								buttonAlign : 'center',
								layout : 'form',
								border : false,
								autoHeight: true,  
								defaults : {
									anchor : '100%',
									xtype : 'fieldset',
									columnWidth : 1,
									labelAlign : 'right',
									collapsible : true,
									autoHeight : true
								},
								labelAlign : "right",
								items : [{
									title : '申请原因',
									autoHeight : true,
									collapsible : true,
									width : '100%',
									bodyStyle : 'padding-left:8px',
									layout : "column",
									defaults : {
										anchor : '100%',
										columnWidth : 1,
										isFormField : true,
										labelWidth : 90
									},
									items : [{
												columnWidth : 1, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : "textarea",
															name : "glSuperviseRecord.reason",
															allowBlank : false,
															value:reason,
															fieldLabel : '申请原因',
															anchor : '100%'
														}]
											}, {
												columnWidth : 1, 
												layout : "form", 
												items : [{
															xtype : "numberfield",
															name : "glSuperviseRecord.continuationRate",
															allowBlank : false,
															style: {imeMode:'disabled'},
															fieldClass:'field-align',
															value:continuationRate,
															fieldLabel : '展期贷款利率',
															anchor : '50%'
														},{
															xtype : "hidden",
															name : "glSuperviseRecord.id",
															value:id
														},{
															xtype : "hidden",
															name : "glSuperviseRecord.projectId",
															value:projectId
														}]
											}, {
												columnWidth : .5, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : 'datefield',
															fieldLabel : '展期开始时间',
															allowBlank : false,
															name : "glSuperviseRecord.startDate",
															anchor : '100%',
															value:startDate,
															format : 'Y-m-d'
														}]
											}, {
												columnWidth : .5, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : 'datefield',
															fieldLabel : '展期结束时间',
															allowBlank : false,
															name : "glSuperviseRecord.endDate",
															anchor : '100%',
															value:endDate,
															format : 'Y-m-d'
														}]
											}, {
												columnWidth : 1, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : "textarea",
															name : "glSuperviseRecord.remark",
															fieldLabel : '备注',
															value:remark,
															anchor : '100%'
														}]
											}]
								}, {
									title : '款项展期修订',
									autoHeight : true,
									collapsible : true,
									width : '100%',
									bodyStyle : 'padding-left:8px',
								   items : [new GuaranteeSlFundIntentViewVM({
									        	isHiddenOperation : false,
												projectId : this.projectId,
												businessType : businessType,
												isHiddenAddBtn : false,
											    isHiddenDelBtn : false,
											    isHiddenCanBtn : false,
											    isHiddenResBtn : false
											})]
								}, {
									title : '杂项展期费用',
									autoHeight : true,
									collapsible : true,
									width : '100%',
									bodyStyle : 'padding-left:8px',
								  items : [this.slActualToCharge = new SlActualToCharge({
									        	isHiddenOperation : false,
												projId : this.projectId,
											//	isUnLoadData : true,
												isHidden : false,
												businessType : businessType
											})]
								}, {
									title : '修定条款展期合同生成与签订',
									autoHeight : true,
									collapsible : true,
									width : '100%',
									bodyStyle : 'padding-left:8px',
									items : [this.SlClauseContractView = new SlClauseContractView(
											{
												projectId : projectId,
												businessType : businessType,
												HTLX : 'DBDZZQ',
												isqsEdit : true,
												isApply : false,
												isUpdate : true,
												clauseId :id
											})]
								}]
		           })
				  var _window = new Ext.Window({
		            border : false,
		            title: "申请展期", 
		            autoScroll: true, 
		            maximizable :true,
		            height:500,
		            width:"70%",  
		            modal : true,
		            labelWidth:45,  
		            plain:true,  
		            resizable:true,  
		            frame:true,
		            buttonAlign:"center",  
		            closable:true,
		            items:[
		                 fp
		            ],
		            buttons:[  
                        {iconCls : 'btn-save',text:"更新",handler:function(){
                        	
                        	  var categoryIds=fp.get(3).get(0).getGridDate();
                        	  var startDate=fp.getCmpByName('glSuperviseRecord.startDate').getValue();
            	              var endDate=fp.getCmpByName('glSuperviseRecord.endDate').getValue();
            	              if(startDate=="" || startDate==null){
            	                   Ext.ux.Toast.msg('操作信息', '申请展期失败,展期开始日期不能为空!'); 
            	                   return false;
            	              }
            	              if(endDate=="" || endDate==null){
            	                   Ext.ux.Toast.msg('操作信息', '申请展期失败,展期结束日期不能为空!'); 
            	                   return false;
            	              }
            	              startDate=startDate.format("Y-m-d");
            	              endDate=endDate.format("Y-m-d");
            	              var s=daysBetween(startDate,endDate);
            	              if(s>=0){
            	                      Ext.ux.Toast.msg('操作信息', '申请展期失败,展期结束日期必须大于展期开始日期!'); 
            	                      return false;
            	              }
            	               var money_plan=fp.get(2).get(0).getGridDate();
                        	  var intent_plan=fp.get(1).get(0).getGridDate();
            	              if (fp.getForm().isValid()) {
            	              	        var outobj;
            	              	        var slClauseContractGrid=gridObj.ownerCt.ownerCt.nextSibling().get(0).get(0);
            	              	       
            	              	        fp.getForm().submit({
            	              	        	          params:{
            	              	        	          	"projectId":projectId,
            	              	        	            "money_plan":money_plan,    
				                                         "intent_plan": intent_plan,
				                                         "flag":0,
				                                         "categoryIds":categoryIds}, // 传递的参数flag gaomimi加
					                        	  	  url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/askforGLGuaranteeloanProject.do',
									    			  method : 'POST',
									    			  success : function(form,action) {
										    			  	 _window.close();
										    			  	 gridObj.getStore().reload();
										    			  	 financeInfoFieldset.get(1).save(); //reload
									    			  	     financeInfoFieldset.get(2).savereload();//reload
									    			  	     slClauseContractGrid.getStore().reload();
									    			  }
									    })
            	              }
                        }},
                        {iconCls : 'btn-close',text:"取消", handler:function(){
                        	 _window.close()
                        }}  
                    ]
		          })
		          /**
		           * 
		         
		           _window.addListener({
						'close' : function() {
							Ext.Ajax.request({
								url : __ctxPath+ '/creditcontract/deleteContractCategoryByProject.do',
								method : 'POST',
								params : {
									projId : projectId,
									businessType : this.businessType,
									htType : 'clauseContract'
								}
							});
						}
					});
					  */
				  _window.show(); 
	
	},
	editRs : function(record) {
		          var s = this.gridPanel.getSelectionModel().getSelections();
		          if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					this.updateForm(record,this.gridPanel);
				}
		
	}
});
