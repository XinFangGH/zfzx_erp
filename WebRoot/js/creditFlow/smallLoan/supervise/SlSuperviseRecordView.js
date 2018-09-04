/**
 * @author
 * @class SlSuperviseRecordView
 * @extends Ext.Panel
 * @description 贷中监管记录管理
 * @company 智维软件
 * @createtime:
 */
SlSuperviseRecordView = Ext.extend(Ext.Panel, {
	projId:null,
	gridPanel:null,
	topbar:null,
	isHidden:false,
	flowType:null,
	isEdit:false,
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
		if (_cfg.isEdit) {
			  this.isEdit = _cfg.isEdit;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlSuperviseRecordView.superclass.constructor.call(this, {
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
		//if(!this.isHidden){
		    		this.topbar = new Ext.Toolbar({
								items : [{
											iconCls : 'btn-add',
											text : '编辑',
											xtype : 'button',
											scope : this,
											hidden:this.isHidden,
											handler : this.editRs
										},new Ext.Toolbar.Separator({
										    hidden:this.isHidden
										}),{
											iconCls : 'btn-del',
											text : '删除',
											xtype : 'button',
											scope : this,
											hidden:this.isHidden,
											handler : this.removeSelRs
										},new Ext.Toolbar.Separator({
										    hidden:this.isHidden
										}),{
											iconCls : 'btn-readdocument',
											text : '查看展期记录详情',
											xtype : 'button',
											scope:this,
											handler : this.viewRs
										}]
		                });
		//}
		this.gridPanel = new HT.GridPanel({
			border : false,
			isShowTbar : true,
			region : 'center',
			autoHeight:true,
			showPaging:false,
			tbar : this.topbar,
			singleSelect:true,
			//hiddenCm:this.isHidden,
			url : __ctxPath + "/supervise/listSlSuperviseRecord.do",
			baseParams:{
				projectId : this.projId
			},
			fields : [{
						name : 'id',
						type : 'int'
					}, 'remark','reason','startDate','endDate','continuationMoney','continuationRate','projectId','accrualtype','payaccrualType','accrualtypeName','payaccrualTypeName','managementConsultingOfRate','financeServiceOfRate','checkStatus','isPreposePayAccrualsupervise'],
			columns : [{
						header : 'id',
						dataIndex : 'id',
						hidden : true
					}, /*{
						header : '申请原因',
						dataIndex : 'reason'
					},*/ {
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
					   header : '是否前置付息',
					   dataIndex : 'isPreposePayAccrualsupervise',
					   renderer :  function(value){
					     if(value==1){
					     	return "是";
					     }else{
					        return "否";
					     }
					     
					     }
					},{
					   header : '计息方式',
					   dataIndex : 'accrualtypeName'
					},{
					   header : '付息方式',
					   dataIndex : 'payaccrualTypeName'
					},{
					   header : '咨询服务费率',
					   dataIndex : 'managementConsultingOfRate',
					   renderer :  function(value,metaData,record,rowIndex,colIndex,store){
						 	if(value!=""){
						 	  return value+"%";
						 	}
						 	else{
						 	  return "";
						 	}
						 }
					},/*{
					   header : '财务服务费率',
					   dataIndex : 'financeServiceOfRate',
						renderer :  function(value,metaData,record,rowIndex,colIndex,store){
						 	if(value!=""){
						 	  return value+"%";
						 	}
						 	else{
						 	  return "";
						 	}
						 	
						 }
					},*/{
						header : '展期开始日期',
						width : 96,
						format : 'Y-m-d',
						dataIndex : 'startDate',
					    renderer :  function(value,metaData,record,rowIndex,colIndex,store){
									         if(typeof (value) == "string"){ 
									         	 /* var beginDate = new Date(value.replace(/-/g,"/"));
									         	  return  Ext.util.Format.date(beginDate,"Y-m-d");*/
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
									         	/*  var beginDate = new Date(value.replace(/-/g,"/"));
									         	  return  Ext.util.Format.date(beginDate,"Y-m-d");*/
									         	  return value;
								             } 
								             var dateTime=Ext.util.Format.date(value, 'Y-m-d');
								             return dateTime
						}
					},{
						header : '展期状态',
						dataIndex : 'checkStatus',   
						renderer :  function(value,metaData,record,rowIndex,colIndex,store){
						      var cs= record.get("checkStatus");
						      var statusStr="";
						      if(cs==0){
						             statusStr="未审批";
						      }else if(cs==1){
						             statusStr="<font color='red'>未提交审批</font>";
						      }else if(cs==5){
						             statusStr="审批通过";
						      }else  if(cs==6){
						             statusStr="审批未通过";
						      }
						      return statusStr;
						}}/*,{
						header : '备注',
						dataIndex : 'remark'
					}*/]
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
			   var FundIntentobj=this.gridPanel.ownerCt.ownerCt.ownerCt.getCmpByName('financeInfoFieldset');
		        var gridObj1=FundIntentobj.get(1);
		        var gridObj2=FundIntentobj.get(2);
		        var  gridPanel= this.gridPanel;      
		       var sm = this.gridPanel.getSelectionModel(); 
		       var rs = sm.getSelections(); 
		       var ids = Array();
			for (var i = 0; i < rs.length; i++) {
			    ids.push(eval('rs[i].data.id' ));
			}
			
			
		  var projectId=rs[0].data.projectId;
		       if(rs[0].data.checkStatus!=1){
		              Ext.MessageBox.show({
							title : '操作信息',
							msg : '已提交申请展期审查的展期记录不能删除！',
							buttons : Ext.MessageBox.OK,
							icon : 'ext-mb-error'
					 });
		       	     return false;
		       };
			
	    Ext.Msg.confirm('信息确认', '确认删除吗,删除记录无法恢复?', function(btn) {
        if (btn == 'yes') {
            Ext.Ajax.request({
                url : __ctxPath + '/supervise/multiDelSlSuperviseRecord.do?businessType=SmallLoan',
                params : {
                    ids : ids
                },
                method : 'POST',
                success : function(response, options) {
                    Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
                       gridPanel.getStore().reload();
			        	gridObj1.fillDatas(projectId,'SmallLoan');
			        	gridObj1.save();
			        	gridObj2.savereload();
                },
                failure : function(response, options) {
                    Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
                }
            });
        }
        });

	},
	updateForm:function(record,gridObj,isAllReadOnly){
		            
		            var flowType=this.flowType;
		            var isEdit=this.isEdit;
	                var businessType="SmallLoan";
	                if(!isAllReadOnly && record.data.checkStatus!=1 && !isEdit){
			              Ext.MessageBox.show({
								title : '操作信息',
								msg : '已提交申请展期审查的展期记录不能编辑！',
								buttons : Ext.MessageBox.OK,
								icon : 'ext-mb-error'
						 });
			       	     return false;
		      		};
	                var id=record.data.id;
				    var reason=record.data.reason;
				    var startDate=new Date(record.data.startDate.replace(/-/g,"/")).format("Y-m-d");
				    var endDate= new Date(record.data.endDate.replace(/-/g,"/")).format("Y-m-d");
				    var remark=record.data.remark;
				    var projectId=record.data.projectId;
				    var continuationRate=record.data.continuationRate;
				    var DeferApplyInfoPanel =new ExtUD.Ext.DeferApplyInfoPanel({"record":record,"isAllReadOnly":isAllReadOnly})
				  /*  var DeferApplyInfoPanel =new FinanceExtensionlPanel({
	 		//continuationMoney:(this.projectMoney - this.payProjectMoney),
	 		 isAllReadOnly:true,
			 projectId :projectId,//使用展期表的Id
			 idDefinition:'enstion'
		})*/
				    this.superviseSlFundIntent=new superviseSlFundIntent({
												projId : projectId,
												object : DeferApplyInfoPanel,
												titleText : '款项信息',
												isHidden : isAllReadOnly,
												businessType : businessType,
											    slSuperviseRecordId : id,
												isUnLoadData :false,
												isThisSuperviseRecord :'yes'
											})
	                var fp=  new Ext.FormPanel({
								modal : true,
								labelWidth : 100,
								frame:true,
								buttonAlign : 'center',
								layout : 'form',
								border : false,
								autoHeight: true, 
								scope :this,
								defaults : {
									anchor : '100%',
									xtype : 'fieldset',
									columnWidth : 1,
									labelAlign : 'right',
									collapsible : true,
									autoHeight : true
								},
								labelAlign : "right",
								items : [{title:"申请展期信息",
										items:[DeferApplyInfoPanel]
								    },
									{
									title : '历史财务信息',
									autoHeight : true,
									collapsible : true,
									name:"historyfinance",
									width : '100%',
									bodyStyle : 'padding-left:8px',
									items : [new SlFundIntentViewVM({
									        	isHiddenOperation : false,
												projectId : projectId,
												businessType : businessType,
												isHiddenAddBtn : true,
											    isHiddenDelBtn : true,
											    isHiddenCanBtn : true,
											    isHiddenResBtn : true,
											    isHidden1:true,
											     slSuperviseRecordId : id,
											    isUnLoadData :false,
												isThisSuperviseRecord :'no'
											})/*,this.slActualToCharge = new SlActualToChargeVM({
									        	isHiddenAddBtn : isAllReadOnly,
												isHiddenDelBtn : isAllReadOnly,
												projId : projectId,
												isHidden:isAllReadOnly,
												businessType :businessType,
												slSuperviseRecordId : id,
											    isUnLoadData :false,
												isThisSuperviseRecord :'no'
											})*/]
								}, {
									title : '展期增加财务信息【注：历史财务信息<font color=orange>+</font>展期增加财务信息<font color=orange>=</font><font color=red>整个项目财务信息</font>】',
									autoHeight : true,
									collapsible : true,
									name:"supervisefinance",
									width : '100%',
									bodyStyle : 'padding-left:8px',
									items : [this.superviseSlFundIntent/*,
									this.slActualToCharge = new SlActualToCharge({
									        	isHiddenOperation : isAllReadOnly,
												projId : projectId,
												isUnLoadData : true,
												isHidden : isAllReadOnly,
												businessType : businessType,
												slSuperviseRecordId : id,
												isUnLoadData :false,
												isThisSuperviseRecord :'yes'
											})*/]
								}, {
									title : '修定条款展期合同生成与签订',
									autoHeight : true,
									collapsible : true,
									name:'contract',
									width : '100%',
									bodyStyle : 'padding-left:8px',
									items : [this.SlClauseContractView = new SlClauseContractView(
											{
												projectId : projectId,
												businessType : businessType,
												HTLX : 'XEDZZQ',
												isqsEdit : isAllReadOnly?false:true,
												isApply : false,
												isUpdate : true,
												clauseId :id
											})]
								}  ,  {
															xtype : "hidden",
															name : "slSuperviseRecord.id",
															value:id
									},
									{
															xtype : "hidden",
															name : "slSuperviseRecord.projectId",
															value:projectId
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
		            buttons:!isAllReadOnly?[  
                        {iconCls : 'btn-save',text:"更新展期信息",handler:function(){
                        	  var checkStatus=1; //保存
                        	  var categoryIds=fp.getCmpByName('contract').get(0).getGridDate();
                        	  var startDate=fp.getCmpByName('slSuperviseRecord.startDate').getValue();
            	              var endDate=fp.getCmpByName('slSuperviseRecord.endDate').getValue();
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
            	              if (fp.getForm().isValid()) {
            	              	        var FundIntentobj=gridObj.ownerCt.ownerCt.ownerCt.getCmpByName('financeInfoFieldset');
            	              	        var gridObj1=FundIntentobj.get(1);
            	              	        var gridObj2=FundIntentobj.get(2);
            	              	        var slSuperviseRecordGrid=FundIntentobj.get(1).get(1);
            	              	        var slActualToChargeGrid=FundIntentobj.get(2).get(1);
            	              	      
                        	            var money_plan=fp.getCmpByName('historyfinance').get(1).getGridDate();
                    	              var intent_plan=fp.getCmpByName('historyfinance').get(0).getGridDate();
                    	              var intent_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(0).getGridDate();
                    	              var money_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(1).getGridDate();
            	              	        var slClauseContractGrid=gridObj.ownerCt.ownerCt.nextSibling().get(0).get(0);
            	              	        fp.getForm().submit({
            	              	        	          params:{"projectId":projectId,"money_plan":money_plan,"intent_plan":intent_plan,"flag":0,"categoryIds":categoryIds,'intent_plan_SuperviseRecord':intent_plan_SuperviseRecord,'money_plan_SuperviseRecord': money_plan_SuperviseRecord,'slSuperviseRecord.checkStatus':checkStatus}, // 传递的参数flag gaomimi加
					                        	  	  url :  __ctxPath + '/project/askForSlSmallloanProject.do',
									    			  method : 'POST',
									    			  success : function(form,action) {
										    			  	slSuperviseRecordGrid.getStore().reload();
										    			  	//slClauseContractGrid.getStore().reload();
										    			  	gridObj.getStore().reload();
										    			  	gridObj1.fillDatas(projectId,businessType);
										    			  	gridObj1.save();
										    			  	gridObj2.savereload();
										    			  	_window.close();
									    			  }
									    })
            	              }
                        }},
                        {iconCls : 'btn-save',text:"更新并申请审批展期",handler:function(){
                          	      var checkStatus=0; //保存并申请
                          	 	  var errorMsg="您确定要提交吗？信息一经提交，不能修改，请认真检查填写的内容”选择“是”提交，选择“否”返回";
					              Ext.Msg.confirm('信息确认', errorMsg, function(btn) {
					              if (btn == 'yes') {
		                          	   var categoryIds=fp.getCmpByName('contract').get(0).getGridDate();
		                        	  var startDate=fp.getCmpByName('slSuperviseRecord.startDate').getValue();
		            	              var endDate=fp.getCmpByName('slSuperviseRecord.endDate').getValue();
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
		            	              if (fp.getForm().isValid()) {
		            	              	        var FundIntentobj=gridObj.ownerCt.ownerCt.ownerCt.getCmpByName('financeInfoFieldset');
		            	              	        var gridObj1=FundIntentobj.get(1);
		            	              	        var gridObj2=FundIntentobj.get(2);
		            	              	        var slSuperviseRecordGrid=FundIntentobj.get(1).get(1);
		            	              	        var slActualToChargeGrid=FundIntentobj.get(2).get(1);
		            	              	      
		                        	              var money_plan=fp.getCmpByName('historyfinance').get(1).getGridDate();
		                        	              var intent_plan=fp.getCmpByName('historyfinance').get(0).getGridDate();
		                        	              var intent_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(0).getGridDate();
		                        	              var money_plan_SuperviseRecord=fp.getCmpByName('supervisefinance').get(1).getGridDate();
		            	              	        var slClauseContractGrid=gridObj.ownerCt.ownerCt.nextSibling().get(0).get(0);
		            	              	        fp.getForm().submit({
		            	              	        	          params:{"projectId":projectId,"money_plan":money_plan,"intent_plan":intent_plan,"flag":0,"categoryIds":categoryIds,'intent_plan_SuperviseRecord':intent_plan_SuperviseRecord,'money_plan_SuperviseRecord': money_plan_SuperviseRecord,'slSuperviseRecord.checkStatus':checkStatus}, // 传递的参数flag gaomimi加
							                        	  	  url :  __ctxPath + '/project/askForSlSmallloanProject.do',
											    			  method : 'POST',
											    			  success : function(form,action) {
												    			    gridObj.ownerCt.ownerCt.previousSibling().get(2).setValue(4);
												    			  	slSuperviseRecordGrid.getStore().reload();
												    			  	//slClauseContractGrid.getStore().reload();
												    			  	gridObj.getStore().reload();
												    			  	gridObj1.fillDatas(projectId,businessType);
												    			  	gridObj1.save();
												    			  	gridObj2.savereload();
												    			  	_window.close();
											    			  }
											    })
		            	              }
					           }})
                        }},
                        {iconCls : 'btn-close',text:"取消", handler:function(){
                        	 _window.close()
                        }}  
                    ]:null
		          })
		           _window.addListener({
						'close' : function() {
							Ext.Ajax.request({
								url : __ctxPath+ '/contract/deleteByProjectProcreditContract.do',
								method : 'POST',
								params : {
									projId : projectId,
									businessType : this.businessType,
									htType : 'clauseContract'
								}
							});
						}
					});
				  _window.show(); 
	
	},
	editRs : function(record) {
		        var s = this.gridPanel.getSelectionModel().getSelections();
		        var gridPanel=this.gridPanel;
		          if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					this.updateForm(record,gridPanel,false);
				}
		
	},
	viewRs:function(record){
	
	            var s = this.gridPanel.getSelectionModel().getSelections();
		        var gridPanel=this.gridPanel;
		          if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					this.updateForm(record,gridPanel,true);
				}
	}
});
