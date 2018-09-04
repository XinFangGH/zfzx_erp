
CreditRatingManage= Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"gudong_info",
		sharteequitysm : null,
		sharteequitybar : null,
		bussinessType:null,
		grid_sharteequity : null,
		projectId : null,
		enId : null,
		isHidden : false,
		constructor : function(_cfg) {
            if(typeof(_cfg.type)!="undefind"){
            	this.type=_cfg.type
            }
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			CreditRatingManage.superclass.constructor.call(this,{
				id:'CreditRatingManage'+this.type,
				title:'客户资信评估',
				iconCls:"btn-tree-team36",
				items : [ 
					this.fPanel_search,
					this.gPanel_creditRating
				]
			})
		},
		initUIComponents : function() {
		    var start = 0 ;
		    var pageSize = 25;
		    var bodyWidth = Ext.getBody().getWidth();
			var bodyHeight = Ext.getBody().getHeight();
			var innerPanelWidth = bodyWidth-6 ;/** 暂时未用到，调整窗体大小使用*/
			var defaultLabelWidth = 120 ;//默认标签的宽度
			var defaultTextfieldWidth = 150 ;//默认文本输入域宽度
			var fieldWidth = 150 ;
			var root = 'topics' ;
			var totalProperty = 'totalProperty' ;

			
			Ext.form.Field.prototype.msgTarget = 'side';
			var windowGroup = new Ext.WindowGroup();
			
			var widthFun = function(bodyWidth){
				return ((bodyWidth-6)<500) ? 500 : (bodyWidth-6);
			}
			var heightFun = function(bodyHeight){
				return ((bodyHeight<400) ? 400 : (bodyHeight));	
			}
			this.jStore_creditRating = new Ext.data.JsonStore( {
				url : __ctxPath+'/creditFlow/creditmanagement/crListCreditRating.do?isAll='+isGranted('_zx_see_customer'),
				totalProperty : totalProperty,
				root : 'topics',
				fields : [ {
					name : 'id'
				},{
					name : 'customerName'
				}, {
					name : 'customerType'
				}, {
					name : 'creditTemplate'
				},{
					name : 'ratingScore'
				},{
					name : 'templateScore'
				},{
					name : 'creditRegister'
				},{
					name : 'ratingMan'
				},{
					name : 'ratingTime'
				},{
					name:'advise_sb'
				},{
				    name:'pgtime'
				}]
			});
			
			this.jStore_creditRating.load({
				params : {
					start : start,
					limit : pageSize
				}
			});
	

			
			
		/*增加，查看，删除按钮end------------------------------------------------------------------------*/		
			var R_templateScore = function (data) {
				return data;
			}
			var R_ratingScore = function (data) {
				return data;
			}
			
		/*	this.cModel_creditRating = new Ext.grid.ColumnModel(
					[
							new Ext.grid.RowNumberer( {
								header : '序号',
								width : 50
							}),
							{
								header : "客户名称",
								width : 200,
								sortable : true,
								dataIndex : 'customerName'
							}, {
								header : "客户类型",
								width : 100,
								sortable : true,
								dataIndex : 'customerType'
								},
							{
								header : "所用评估模板",
								width : 100,
								sortable : true,
								dataIndex : 'creditTemplate'
							}, {
								header : "评估百分制总分",
								width : 100,
								sortable : true,
								dataIndex : 'ratingScore',
								renderer : R_ratingScore
							}, {
								header : "资信评级",
								width : 100,
								sortable : true,
								dataIndex : 'creditRegister'
							}, {
								header : "评级含义",
								width : 100,
								sortable : true,
								dataIndex : 'advise_sb',
								renderer : function(data, metadata, record,
					                    rowIndex, columnIndex, store) {
					                metadata.attr = ' ext:qtip="' + data + '"';
					                return data;
					            }
								
							}, {
								header : "评估人",
								width : 100,
								sortable : true,
								dataIndex : 'ratingMan'
							}, {
								header : "评估日期",
								width : 100,
								sortable : true,
								dataIndex : 'ratingTime'
							}, {
								header : "耗时",
								width : 100,
								sortable : true,
								dataIndex : 'pgtime'
							} ]);
		
			this.pagingBar = new Ext.PagingToolbar( {
				pageSize : pageSize,
				store : this.jStore_creditRating,
				autoWidth : true,
				hideBorders : true,
				displayInfo : true,
				displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
				emptyMsg : "没有符合条件的记录······"
			});*/

			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
		    this.tbar_creditRating=new Ext.Toolbar({
		       items:[{
		          	text : '查看',
				    tooltip : '查看资信评估数据',
				    iconCls : 'btn-detail',
					scope : this,
					hidden : isGranted('_creditRating_see_'+this.type)?false:true,
					handler : this.seeCreditRating
				    
		       },new Ext.Toolbar.Separator({
					hidden : isGranted('_creditRating_see_'+this.type) ? false : true
			   }),{
		            text : '新建',
					tooltip : '新建一条新的资信评估',
					iconCls : 'btn-add',
					scope : this,
					hidden : isGranted('_creditRating_add_'+this.type)?false:true,
					handler : function() {
					  addCreditRatingEB(this.jStore_creditRating);
					}
		       },new Ext.Toolbar.Separator({
					hidden : (isGranted('_creditRating_add_'+this.type) ? false : true)||(isGranted('_creditRating_delete')?false:true)
			   }),{
		            text : '删除',
					tooltip : '删除此条资信评估',
					iconCls : 'btn-del',
					scope : this,
					hidden : isGranted('_creditRating_delete_'+this.type)?false:true,
					handler:this.deleteCreditRating
		       
		       }]
		    })
			this.gPanel_creditRating = new HT.GridPanel( {
				
				store : this.jStore_creditRating,
				anchor:'100%',
				height : Ext.getBody().getViewSize().height-153,
				//colModel : this.cModel_creditRating,
				selModel : new Ext.grid.RowSelectionModel(),
				stripeRows : true,
				loadMask : myMask,
				tbar : this.tbar_creditRating,
				columns:[
							{
								header : "客户名称",
								width : 200,
								sortable : true,
								dataIndex : 'customerName'
							}, {
								header : "客户类型",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'customerType'
								},
							{
								header : "所用评估模板",
								width : 100,
								sortable : true,
								dataIndex : 'creditTemplate'
							}, {
								header : "评估百分制总分",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'ratingScore',
								renderer : R_ratingScore
							}, {
								header : "资信评级",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'creditRegister'
							}, {
								header : "评级含义",
								width : 100,
								sortable : true,
								dataIndex : 'advise_sb',
								renderer : function(data, metadata, record,
					                    rowIndex, columnIndex, store) {
					                metadata.attr = ' ext:qtip="' + data + '"';
					                return data;
					            }
								
							}, {
								header : "评估人",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'ratingMan'
							}, {
								header : "评估日期",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'ratingTime'
							}, {
								header : "耗时",
								width : 100,
								align:'center',
								sortable : true,
								dataIndex : 'pgtime'
							} 
				],
				listeners : {
					scope:this,
					'rowdblclick' : this.seeCreditRating
				}
			});
			
			this.fPanel_search = new Ext.FormPanel( {
				labelAlign : 'right',
				buttonAlign : 'center',
				bodyStyle : 'padding:5px;',
				height : 60,
				anchor:'100%',
				//width : (((Ext.getBody().getWidth()-6)<800) ? 800 : (Ext.getBody().getWidth()-6)),
				labelWidth : 80,
				layout:'column',
				border : false,
				items : [ {
				   columnWidth:0.21,
				   labelWidth : 60,
				   layout:'form',
				   border : false,
				   items:[{
				        xtype : 'combo',
						anchor:'95%',
						editable : false,
						mode : 'local',
						fieldLabel : '客户类型',
						name : 'customerType',
						store : new Ext.data.SimpleStore({
										data : [['企业', '企业'], ['个人', '个人']],
										fields : ['text', 'value']
									}),
						displayField : 'text',
						valueField : 'value',
						triggerAction : 'all'
				 
				      },{
				         xtype:'hidden',
				         name:'username'
				      },{
				         fieldLabel : "评估人",
							xtype : "combo",
							editable : false,
							triggerClass : 'x-form-search-trigger',
							itemVale : creditkindDicId, // xx代表分类名称
							readOnly : this.isAllReadOnly,
						    anchor:'95%',
						    onTriggerClick : function(cc) {
							     var obj = this;
							     var appuerIdObj=obj.previousSibling();
								 new UserDialog({
								 	userIds:appuerIdObj.getValue(),
								 	userName:obj.getValue(),
									single : true,
									title:"评估人",
									callback : function(uId, uname) {
										obj.setRawValue(uname);
										appuerIdObj.setValue(uId);
									}
								}).show();
						    }
				      }]
				},{
				      columnWidth:0.25,
				      layout:'form',
				      labelWidth:100,
				      border : false,
				      items:[{				          
							xtype : 'textfield',
							fieldLabel : '客户名称',
							name : 'customerName',
							anchor:'95%'
				      },{
				            xtype : 'combo',
							fieldLabel : '评估模板类型 ',
							
							name : 'creditTemplate',
							mode : 'romote',
							anchor:'95%',
							store : new Ext.data.JsonStore({
								url : __ctxPath+'/creditFlow/creditmanagement/rtListRatingTemplate.do',
								root : 'topics',
								fields : [{
											name : 'id'
										}, {
											name : 'templateName'
										}]
							}),
							displayField : 'templateName',
							valueField : 'templateName',
							triggerAction : 'all'
				      }]
				   },{
				      columnWidth:0.25,
				      layout:'form',
				      labelWidth:100,
				      border : false,
				      items:[{
							xtype : 'combo',
							fieldLabel : '信用评级标准 ',
							hiddenName: 'ratingTemplate.classId',
							displayField:'className',
							valueField:'classId',
							 store : new Ext.data.SimpleStore({
							 	autoLoad : true,
							 	url: __ctxPath+'/creditFlow/creditmanagement/getAllCLassTypeScoreGradeOfClass.do',
								fields : ['className','classId']
							}),
							triggerAction : "all",
							blankText : '必填信息',
		                	anchor:'95%',
		                
		                	listeners:{
		                		scope:this,
		                		'select':function(combox,record,index){
		                			this.ownerCt.getCmpByName("grandname"+this.type).store.baseParams["classId"]=combox.getValue()
		                			this.ownerCt.getCmpByName("grandname"+this.type).store.load()
		                		}
		                	}
					
					    }]
				   },{
				      columnWidth:0.22,
				      layout:'form',
				      border : false,
				      items:[{
				            xtype : 'combo',
							fieldLabel : '信用评级 ',
						    name:'grandname'+this.type,
							mode : 'romote',
							anchor : '95%',
							store : new Ext.data.JsonStore({
								url : __ctxPath+'/creditFlow/creditmanagement/getScoreGradeListScoreGradeOfClass.do',
								root : 'topics',
								fields : [{
											name : 'grandId'
										}, {
											name : 'grandname'
										}]
							}),
							displayField : 'grandname',
							valueField : 'grandname',
							triggerAction : 'all'
				      }]
				   },{
				      columnWidth:0.15,
				      layout:'form',
				      labelWidth:100,
				      border : false,
					   	items:[{
					   	     xtype:'textfield',
							 fieldLabel:'评估得分',
							 anchor:'100%',
							 name:'startScore'
							 
					   	}]
				         
				   },{
				      columnWidth:0.02,
				      layout:'form',
				      border : false,
				      items:[{
				          xtype:'label',
				          text:'~'
				      }]
				   },{
				     columnWidth:0.1,
				     layout:'form',
				     border : false,
				     items:[{
				         xtype:'textfield',
				         hideLabel:true,
				         anchor:'70%',
				         name:'endScore'
				     }]
				   },{
				      columnWidth:0.07,
				      layout:'form',
				      border : false,
				      items:[{
				      	    xtype:'button',
				            text : '查询',
							tooltip : '根据查询条件过滤',
							iconCls : 'btn-search',
							anchor:'100%',
							scope : this,
							handler :this.searchByCondition
				      }]
				   },{
				      columnWidth:0.1,
				      layout:'form',
				      border : false,
				      items:[{
				      	xtype:'button',
				            text : '重置',
							tooltip : '重置查询条件',
							iconCls : 'btn-reset',
							style :'margin-left:30px',
							scope : this,
							anchor:'100%',
							handler : function() {
								this.fPanel_search.getForm().reset();
							}
				      }]
				   }]
			});

			
			 
			
			
			
			window.onresize = function(){
				bodyWidth = Ext.getBody().getWidth();
				bodyHeight = Ext.getBody().getHeight() ;
				w = widthFun(bodyWidth);
				//panel_enterprise.doLayout();
				//vPort_enterprise.doLayout();
			}
			var widthFun = function(bodyWidth){
				return ((bodyWidth-6)<800) ? 800 : (bodyWidth-6);
			}


		},
		seeCreditRating:function(){
			 var getCreditRatingResult = function(selected) {
				var fPanel_search = new Ext.form.FormPanel( {
					labelAlign : 'right',
					buttonAlign : 'center',
					bodyStyle : 'padding:10px;',
					height : 80,
					width : (((Ext.getBody().getWidth()-6)<800) ? 800 : (Ext.getBody().getWidth()-6)),
					frame : true,
					labelWidth : 80,
					monitorValid : true,
					items : [ {
						layout : 'column',
						border : false,
						labelSeparator : ':',
						defaults : {
							layout : 'form',
							border : false,
							columnWidth : .25
						},
						items : [ {
							items : [{
								id : 'creditRating.arr_id',
								xtype : 'hidden',
								name : 'creditRating.arr_score'
							},{
								id : 'creditRating.arr_score',
								xtype : 'hidden',
								name : 'creditRating.arr_score'
							}, {
								
								html : "<b>客户类型：</b>"+selected.get('customerType')
							},{
							    html:'<br>'
							},{
								
								html : "<b>评估人：</b>"+selected.get('ratingMan')
							}]
						}, {
							items : [ {
								
								html : "<b>客户名称：</b>"+selected.get('customerName')
							},{
							    html:'<br>'
							},{	
								
								html : "<b>评估时间：</b>"+selected.get('pgtime')
							} ]
						},{
							items : [ {
							
								html : "<b>资信评估模板：</b>"+selected.get('creditTemplate')
							},{
							    html:'<br>'
							},{
								
								html : "<b>资信评级：</b>"+selected.get('creditRegister')
							}]
						},{
						   items:[{
								html : "<b>评级含义：</b>"+selected.get('advise_sb')
						   },{
							    html:'<br>'
							},{
								
								html : "<b>百分制分数：</b>"+selected.get('ratingScore')
							}]
						}]// items
					} ]
				});
			var sreader = new Ext.data.JsonReader({
			    totalProperty : 'totalCounts',
				root : 'result'
			}, [{
					name : 'indicatorId'
				},{
					name : 'indicatorName'
				},{
					name : 'optionId'
				}, {
					name : 'optionName'
				}, {
					name : 'score'
				},{
				    name:'xuhao'
				}]
	       );
	        jStore_enterprise = new Ext.data.GroupingStore({
				url : __ctxPath+'/creditFlow/creditmanagement/resultCreditRating.do',
				reader : sreader,
				baseParams : {
					id : selected.get('id')
				},
				groupField : 'indicatorId'
			});
			jStore_enterprise.load();
				
			var incName=function(data, cellmeta, record){

              return record.get("indicatorName");
            }
			var cModel_enterprise = new Ext.grid.ColumnModel([
				 {
					header : '',
					width : 10,
					dataIndex : 'xuhao'
				},
				{
					header : "",
					width : 200,
					dataIndex : 'indicatorId',
					hidden:true,
					renderer:incName
				},{
					header : "指标选项",
					width : 300,
					dataIndex : 'optionName'
				},
				{
					header : "评估结果",
					width : 200,
					dataIndex : 'score'
				}]);

				var myMask = new Ext.LoadMask(Ext.getBody(), {
					msg : "加载数据中······,请稍后······"
				});
			
				var gPanel_enterprise = new Ext.grid.GridPanel( {
					id : 'gPanel_enterprise',
					store : jStore_enterprise,
					width : Ext.getBody().getWidth() - 15,
					height : Ext.getBody().getHeight()-180,
					view : new Ext.grid.GroupingView({
					forceFit : true,
					groupTextTpl : '{text}'
				
				    }),
					autoScroll : true,
					colModel : cModel_enterprise,
					selModel : new Ext.grid.RowSelectionModel(),
					stripeRows : true,
					loadMask : myMask,
					
					//tbar : [button_add,button_see,button_update,button_delete],
					listeners : {
						'rowdblclick' : function(grid,index,e){
							var id = grid.getSelectionModel().getSelected().get('id');
							seeEnterprise(id);
						}
					}
				});
				
				var panel_enterprise = new Ext.Panel( {
					autoHeight : true,
					items : [fPanel_search,gPanel_enterprise]
				});
				
				var win = new Ext.Window({
					layout : 'fit',
					width : Ext.getBody().getWidth(),
					height : Ext.getBody().getHeight()-50,
					closable : true,
					buttonAlign : 'center',
					plain : true,
					border : false,
					modal : true,
					items : [panel_enterprise],
					title : '资信评估结果',
					collapsible : true
				});
				win.show();
			}
			var selected = this.gPanel_creditRating.getSelectionModel().getSelected();
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var id = selected.get('id');
				getCreditRatingResult(selected);
			}
		   
		},
		deleteCreditRating:function(){
		
			var selected =this.gPanel_creditRating.getSelectionModel().getSelections();
			var gPanel_creditRating=this.gPanel_creditRating
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				//var id = selected.get('id');
				Ext.MessageBox.confirm('确认删除', '是否确认执行删除 ', function(btn) {
					if (btn == 'yes') {
						var arr=new Array();
						for ( var i = 0; i < selected.length; i++) {
							var row = selected[i];
							arr.push(row.data.id);
						
						}

						Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/creditmanagement/deleteCreditRating.do',
							method : 'POST',
							success : function(response) {
								if (response.responseText == '{success:true}') {
									Ext.ux.Toast.msg('状态', '删除成功!');
									gPanel_creditRating.getStore().removeAll();
									gPanel_creditRating.getStore().reload();
								} else if(response.responseText == '{success:false}') {
									Ext.ux.Toast.msg('状态','删除失败!');
								} else {
									Ext.ux.Toast.msg('状态','删除失败!');
								}
							},
							failure : function(result, action) {
								Ext.ux.Toast.msg('状态','服务器未响应，删除失败!');
							},
							params: { arr: arr }
						});
					}
				});
			
			}
				
		},
		searchByCondition :function() {
				this.jStore_creditRating.baseParams.customerType = this.fPanel_search.getForm().findField('customerType').getValue();
				this.jStore_creditRating.baseParams.customerName = this.fPanel_search.getForm().findField('customerName').getValue();
				this.jStore_creditRating.baseParams.creditTemplate = this.fPanel_search.getForm().findField('creditTemplate').getValue();
				this.jStore_creditRating.baseParams.userId = this.fPanel_search.getForm().findField('username').getValue();
				this.jStore_creditRating.baseParams.startScore = this.fPanel_search.getForm().findField('startScore').getValue();
				this.jStore_creditRating.baseParams.endScore = this.fPanel_search.getForm().findField('endScore').getValue();
				this.jStore_creditRating.baseParams.grandName = this.fPanel_search.getForm().findField('grandname'+this.type).getValue();
				this.jStore_creditRating.load();
		}
});
