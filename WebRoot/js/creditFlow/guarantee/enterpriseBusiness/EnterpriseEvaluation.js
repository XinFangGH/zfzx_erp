EnterpriseEvaluationGuarantee = Ext.extend(Ext.Panel, {
            projectId:null,
            isHidden:false,
            customerObj:null,
			constructor : function(_cfg) {
                 if(typeof(_cfg.projectId)!="undefined"){
                	 this.projectId=_cfg.projectId
                 }
                 if(typeof(_cfg.isHidden)!="undefined"){
                	 this.isHidden=_cfg.isHidden
                 }
	             Ext.Ajax.request({
	                   url: __ctxPath+'/creditFlow/common/getEnterpriseShareequity.do',
	                   method : 'POST',
	                   params : {
								projectId : this.projectId
							},
	                  success : function(response,request) {
							this.customerObj = Ext.util.JSON.decode(response.responseText);
		
                      }
                     });  
	                var customerObj=this.customerObj              
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				EnterpriseEvaluationGuarantee.superclass.constructor.call(this, {
							border:false,
							items : []
						})
			},
			initUIComponents : function() {
				var jsArr = [
                 __ctxPath+'/js/creditFlow/guarantee/enterpriseBusiness/creditRatingB.js'
				];
				$ImportSimpleJs(jsArr, this.constructPanel,this);
			
			
			},
			constructPanel:function(){

                var jStore_creditRating = new Ext.data.JsonStore(getCreditRatingStoreCfg(this.projectId,null,null));
		
				jStore_creditRating.addListener('load',function(){
					//Ext.getCmp('gPanel_creditRating').getSelectionModel().selectFirstRow() ;
					gPanel_creditRating.getSelectionModel().selectFirstRow() ;
				});
				jStore_creditRating.load()
				var cModel_creditRating = new Ext.grid.ColumnModel(creditRatingModelCfg);
				
				var button_addCreditRating = new Ext.Button({
					text : '新建',
					tooltip : '新建一条新的资信评估',
					iconCls : 'btn-add',
					hidden:this.isHidden,
					scope : this,
					handler : function() {
						addCreditRatingEB(this.projectId, customerObj, jStore_creditRating, (typeof(customerObj.data.enterprisename)!='undefined')?'企业':'个人');
					}
				});
				
				var button_see = new Ext.Button({
					text : '查看',
					tooltip : '查看资信评估数据',
					iconCls : 'btn-detail',
					scope : this,
					handler : function() {
						var selected = gPanel_creditRating.getSelectionModel().getSelected();
						if (null == selected) {
							Ext.ux.Toast.msg('操作信息', '请选择一条记录!');
						}else{
							var id = selected.get('id');
					
							getCreditRatingResult(selected);
						}
					}
				});
				
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
				
				var button_deleteCreditRating = new Ext.Button({
					text : '删除',
					tooltip : '删除此条资信评估',
					iconCls : 'btn-del',
					hidden:this.isHidden,
					scope : this,
					handler : function() {
						
						var selected =this.gPanel_creditRating.getSelectionModel().getSelections();

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
										url : __ctxPath+'/credit/creditmanagement/deleteCreditRating.do',
										method : 'POST',
										success : function(response) {
											if (response.responseText == '{success:true}') {
									
												Ext.ux.Toast.msg('操作信息', '删除成功!');
												jStore_creditRating.removeAll();
												jStore_creditRating.reload();
											} else if(response.responseText == '{success:false}') {
												Ext.ux.Toast.msg('操作信息', '删除失败!');
						
											} else {
												Ext.ux.Toast.msg('操作信息', '删除失败!');
											
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
				
		}
				});
				this.tbar=new Ext.Toolbar({
					items:[
						button_addCreditRating,
						new Ext.Toolbar.Separator({
							hidden : this.isHidden
						}),
						button_deleteCreditRating,
						new Ext.Toolbar.Separator({
							hidden : this.isHidden
						}),
						button_see  
					]
				}) 
				var gPanel_creditRating = new Ext.grid.GridPanel({
					//id : 'gPanel_creditRating',
					store : jStore_creditRating,
					border:false,
				 	autoHeight:true,
					colModel : cModel_creditRating,
					selModel : new Ext.grid.RowSelectionModel(),
					stripeRows : true,
					plugins : expanderCreditRating,
					viewConfig : {forceFit : true},
					tbar:this.tbar,
					listeners : {}
				});
			    this.add(gPanel_creditRating);
				this.doLayout();
			}
})			