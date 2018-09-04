MiddleSupervision = Ext.extend(Ext.Panel, {
	layout : 'anchor',
	anchor : '100%',
	projectId : null,
	isHidden:false,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projectId = _cfg.projectId;
		}
        if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		MiddleSupervision.superclass.constructor.call(this, {
					items : [this.grid_MiddleSupervision]
				})
	},
	initUIComponents : function() {
        
		this.datafield=new Ext.form.DateField( {
									format : 'Y-m-d',
									allowBlank : false
		})
		var deleteFun = function(url, prame, sucessFun, i, j) {
			Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response) {
					if (response.responseText.trim() == '{success:true}') {
						if (i == (j - 1)) {
							Ext.ux.Toast.msg('状态', '删除成功!');
						}
						sucessFun();
					} else if (response.responseText.trim() == '{success:false}') {
						Ext.ux.Toast.msg('状态', '删除失败!');
					}
				},
				params : prame
			});
		};

		this.MiddleSupervisionBar = new Ext.Toolbar({
			items : [{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : function() {
							var gridadd = this.grid_MiddleSupervision;
							var storeadd = this.grid_MiddleSupervision.getStore();
							var keys = storeadd.fields.keys;
							var p = new Ext.data.Record();
							p.data = {};
							for (var i = 1; i < keys.length; i++) {
								
								if(i==1){
								  p.data[keys[i]] =new Date();
								}
								else{
								  p.data[keys[i]] = '';
								}
							}
							var count = storeadd.getCount() + 1;
							gridadd.stopEditing();
							storeadd.addSorted(p);
							gridadd.getView().refresh();
							gridadd.startEditing(0, 1);
						}
					}, '-', {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : function() {
							var griddel = this.grid_MiddleSupervision;
							var storedel = griddel.getStore();
							var s = griddel.getSelectionModel().getSelections();
							if (s <= 0) {
								Ext.ux.Toast.msg('状态', '请选中任何一条记录');
								return false;
							}
							Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

								if (btn == "yes") {
									griddel.stopEditing();
									for (var i = 0; i < s.length; i++) {
										var row = s[i];
										if (row.data.supervisionId == null|| row.data.supervisionId == '') {
											storedel.remove(row);
											griddel.getView().refresh();
										} else {
											deleteFun(
													'',//__ctxPath+ '/creditFlow/guarantee/EnterpriseBusiness/multiDelGlProcreditSupervision.do',
													{ids : row.data.supervisionId}, function() {}, i, s.length)
										}

										storedel.remove(row);
										griddel.getView().refresh();
									}
								}
							})
						}
					},'-'/**,{
											iconCls : 'ZWwriteIcon',
											text : '申请展期',
											xtype : 'button',
											scope : this,
											handler : this.applyfor
										}**/]
		})

		this.grid_MiddleSupervision = new Ext.grid.EditorGridPanel({
			border : false,
			isShowTbar : this.isHidden?false:true,
			hiddenCm : this.isHidden?true:false,
			tbar : this.isHidden?null:this.MiddleSupervisionBar,
			autoHeight : true,
			clicksToEdit : 1,
			stripeRows : true,
			viewConfig : {
				forceFit : true
			},
			sm : new Ext.grid.CheckboxSelectionModel({}),
			store : new Ext.data.Store({
				proxy : new Ext.data.HttpProxy({
					url :'',//__ctxPath+ '/creditFlow/guarantee/EnterpriseBusiness/listGlProcreditSupervision.do?projectId='+ this.projectId,
					method : "POST"
				}),
				reader : new Ext.data.JsonReader({
							fields : Ext.data.Record.create([{
										name : 'supervisionId'
									}, {
										name : 'supervisionDate'
									}, {
										name : 'supervisionPlace'
									}, {
										name : 'supervisionPersonId'
									}, {
										name : 'supervisionPersonName'
									}, {
										name : 'supervisionIdea'
									},  {
										name : 'supervisionIdeaName'
									},{
										name : 'remark'
									}

							]),
							root : 'result'
						})
			}),
			columns : [new Ext.grid.CheckboxSelectionModel({}),
					new Ext.grid.RowNumberer(), {
						header : '',
						dataIndex : 'supervisionId',
						hidden :true,
						editor : {
							xtype : 'textfield'
						}
					},{
						header : '监管时间',
						width : 125,
						dataIndex : 'supervisionDate',
						format : 'Y-m-d',
						renderer : function(value,metaData,record,rowIndex,colIndex,store){
							                 if(""==value || null==value){
							                 	 var year=new Date().getFullYear();
							                 	 var month=new Date().getMonth()+1;
							                 	 var day=new Date().getDay();
							                 	 var str=year+"-"+month+"-"+day;
							                 	 var beginDate = new Date(str.replace(/-/g,"/"));
							                 	 return  Ext.util.Format.date(beginDate,"Y-m-d");
							                 }
							                 else if(typeof (value) == "string"){
							                      /*var beginDate = new Date(value.replace(/-/g,"/"));
									         	  return  Ext.util.Format.date(beginDate,"Y-m-d");*/
									         	  return value;
							                 }
							                 var dateTime=Ext.util.Format.date(value, 'Y-m-d');
								             return dateTime;
						},
						editor : this.datafield
					}, {
						header : '监管地点',
						width : 200,
						dataIndex : 'supervisionPlace',
						editor : {
							xtype : 'textfield'
						}
					}, {
						header : '监管人',
						width : 200,
						dataIndex : 'supervisionPersonName',
						sortable : true,
						scope : this,
						editor : {
							name : 'supervisionPersonName',
							xtype : 'textfield',
							allowBlank : true,
							listeners : {
								scope : this,
								focus : function(obj) {
									var mgrid =this.grid_MiddleSupervision;
									new UserDialog({
										single : false,
										callback : function(uId, uname) {
											mgrid.store.getAt(rowEditIndex).data.supervisionPersonId = uId;
											mgrid.store.getAt(rowEditIndex).data.supervisionPersonName = uname;
											mgrid.getView().refresh();
										}
									}).show();
								}
							}

						}
					}, {
						header : '监管意见',
						width : 200,
						dataIndex : 'supervisionIdea',
						sortable : true,
						editor :{
						 	xtype : "dickeycombo",
						    displayField : 'itemName',
						    nodeKey : 'glGuaranteeloan_supervisionIdea'
						 },
						  renderer : function(value, metaData, record, rowIndex,colIndex, store) 
						  {
							 var objcom = this.editor;
							 var objStore = objcom.getStore();
							 var idx = objStore.find("itemId", value);
							 if (idx != "-1") {
								return objStore.getAt(idx).data.itemName;
							} else {
								return record.get("supervisionIdeaName")
							}
						}
					}, {
						header : '备注',
						width : 285,
						align : 'center',
						dataIndex : 'remark',
						sortable : true,
						editor : {
							xtype : 'textfield'
						}
					}]

		});

		this.grid_MiddleSupervision.getStore().load();

		var rowEditIndex;
		this.grid_MiddleSupervision.on({
			scope : this,
			beforeedit : function(e) {
				if (this.grid_MiddleSupervision.store.getAt(e.row).data.verify == true) {
					rowEditIndex = -1;
					e.stopEvent();
				} else {
					rowEditIndex = e.row;
				}
			}
		});
	},
	getGridDate : function() {
		if (typeof(this.grid_MiddleSupervision) == "undefined" || null == this.grid_MiddleSupervision) {
			return "";
			return false;
		}
		var vRecords = this.grid_MiddleSupervision.getStore().getRange(0,this.grid_MiddleSupervision.getStore().getCount()); // 得到修改的数据（记录对象）
		var vCount = vRecords.length; // 得到记录长度
		var vDatas = '';
		if (vCount > 0) {
			for (var i = 0; i < vCount; i++) {
				      var tempDateTime=vRecords[i].data.supervisionDate;
					  if(typeof (tempDateTime) == "string"){ 
					        var beginDate = new Date(tempDateTime.replace(/-/g,"/"));
						    beginDate.format("Y-m-d");
						    vRecords[i].data.supervisionDate=beginDate;
				      }
				vDatas += Ext.util.JSON.encode(vRecords[i].data) + '@';
			}
			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	},
    applyfor:function(){ //申请展期
			 var gridPanel=this.grid_MiddleSupervision;
			 var projectId=this.projectId;
			 var businessType="Guarantee";
			 var slClauseContractGrid=gridPanel.ownerCt.ownerCt.nextSibling().nextSibling().get(0).get(0);
			 var outObjPanel=gridPanel.ownerCt.ownerCt.ownerCt; 
			 var financeInfoFieldset=outObjPanel.getCmpByName('financeInfoFieldset');
			 var fp1 =  new Ext.FormPanel({
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
															name : "reason",
															allowBlank : false,
															fieldLabel : '申请原因',
															anchor : '100%'
														}]
											}, {
												columnWidth : 1, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : "numberfield",
															name : "continuationRate",
															allowBlank : false,
															style: {imeMode:'disabled'},
															fieldClass:'field-align',
															fieldLabel : '展期贷款利率',
															anchor : '50%'
														}]
											}, {
												columnWidth : .5, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : 'datefield',
															fieldLabel : '展期开始时间',
															allowBlank : false,
															name : "startDate",
															anchor : '100%',
															format : 'Y-m-d'
														}]
											}, {
												columnWidth : .5, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : 'datefield',
															fieldLabel : '展期结束时间',
															allowBlank : false,
															name : "endDate",
															anchor : '100%',
															format : 'Y-m-d'
														}]
											}, {
												columnWidth : 1, // 该列在整行中所占的百分比
												layout : "form", // 从上往下的布局
												items : [{
															xtype : "textarea",
															name : "remark",
															fieldLabel : '备注',
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
												isUnLoadData : true,
												isHidden : false,
												businessType : businessType
											})]
								}, {
									title : '修定条款展期合同生成与签订',
									autoHeight : true,
									collapsible : true,
									width : '100%',
									bodyStyle : 'padding-left:8px',
									items : [this.SlClauseContractView =new SlClauseContractView(
												{
													projectId : this.projectId,
													businessType : businessType,
													HTLX : 'DBDZZQ',
													isqsEdit : true,
													isApply : false
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
		               fp1
		            ],
		            buttons:[  
                        {iconCls : 'btn-save',text:"保存",handler:function(){
                                          var startDate=fp1.getCmpByName('startDate').getValue();
                        	              var endDate=fp1.getCmpByName('endDate').getValue();
                        	              var reason=fp1.getCmpByName('reason').getValue();
                        	              var continuationRate=fp1.getCmpByName('continuationRate').getValue();
                        	              var remark=fp1.getCmpByName('remark').getValue();
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
                        	              var money_plan=fp1.get(2).get(0).getGridDate();
                        	              var intent_plan=fp1.get(1).get(0).getGridDate();
                        	              var categoryIds=fp1.get(3).get(0).getGridDate();
                        	              if (fp1.getForm().isValid()) {
			                                          fp1.getForm().submit({
				                                        params:{"projectId":projectId,
				                                                 "glSuperviseRecord.startDate":startDate,
				                                                 "glSuperviseRecord.endDate":endDate,
				                                                 "glSuperviseRecord.reason":reason,
				                                                 "glSuperviseRecord.continuationRate":continuationRate,
				                                                 "glSuperviseRecord.remark":remark,
				                                                 "money_plan":money_plan,    
				                                                 "intent_plan": intent_plan,
				                                                 "flag":0
				                                                 ,"categoryIds":categoryIds
				                                      
				                                      },
					                        	  	  url : __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/askforGLGuaranteeloanProject.do',
									    			  method : 'post',
									    			  success : function(form,action) {
									    			  	    Ext.ux.Toast.msg('操作信息', '申请展期成功!');
									    			  	    gridPanel.ownerCt.ownerCt.nextSibling().setVisible(true);
									    			  	    if(categoryIds !=''){
									    			  	     	gridPanel.ownerCt.ownerCt.nextSibling().nextSibling().setVisible(true);
									    			  	     }
									    			  	     slClauseContractGrid.getStore().reload();
									    			  	    gridPanel.ownerCt.ownerCt.nextSibling().get(0).get(0).getStore().reload();
									    			  	    financeInfoFieldset.get(1).save(); //reload
									    			  	    financeInfoFieldset.get(2).savereload();//reload
								    			      }
			                                    })
                        	              }
                        
                        _window.hide()}},
                        {iconCls : 'btn-close',text:"取消", handler:function(){
                        Ext.Ajax.request({
								url : __ctxPath+ '/contract/deleteByProjectProcreditContract.do',
								method : 'POST',
								params : {
									projId : projectId,
									businessType : businessType,
									htType : 'clauseContract'
								}
							});
                            _window.hide();  
                         }}  
                    ]
		            
		          })
	         _window.addListener({
						'close' : function() {
							Ext.Ajax.request({
								url : __ctxPath+ '/contract/deleteByProjectProcreditContract.do',
								method : 'POST',
								params : {
									projId : projectId,
									businessType : businessType,
									htType : 'clauseContract'
								}
							});
						}
					});
				  _window.show(); 
	
	},
	saveRs : function(grid_MiddleSupervision) {
		var vDates = this.getGridDate();
		if (vDates == '') {
			Ext.ux.Toast.msg("提示", "请先填写保中监管！")
			return;
		}
		Ext.Ajax.request({
			url : '',// __ctxPath+ '/creditFlow/guarantee/EnterpriseBusiness/saveGlProcreditSupervision.do',
			method : 'POST',
			params : {
				glProcreditSupervisionStr : vDates,
				projectId : this.projectId
			},
			success : function(response, request) {
				var obj = response.responseText.toString().trim();
				if(obj== "{success:true}"){
					grid_MiddleSupervision.getStore().reload();
					grid_MiddleSupervision.getView().refresh();
				}else{
					Ext.ux.Toast.msg('操作信息', '保存信息失败，请与管理员联系!');
				}
				
			}
		});

	}
});
