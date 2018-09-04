
GlActualToChargePanel = Ext.extend(Ext.Panel, {
			//构造函数
	        projId:null,
	        operateObj:null,
	        gridPanel:null,
	        tbar:null,
	        isReadOnly:false,
			constructor : function(_cfg) {
			    if(typeof(_cfg.projectId)!="undefined")
				{
				      this.projId=_cfg.projectId;
				}
				if(typeof(_cfg.operateObj)!="undefined")
				{
				      this.operateObj=_cfg.operateObj;
				}
				if(_cfg.isReadOnly)
				{
				      this.isReadOnly=_cfg.isReadOnly;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				GlActualToChargePanel.superclass.constructor.call(this, {	
							layout : 'fit',
							items : this.gridPanel,
							modal : true,
							autoHieght:true,
							maximizable : true,
							buttonAlign : 'center'
				});
			},
			initUIComponents : function() {
				
				var projectId=this.projId;
		        if(!this.isReadOnly) 
		        {
		            this.toolbar=new Ext.Toolbar({
					items : [
						{
							iconCls : 'btn-add',
							text : '添加计划收费项目',
							xtype : 'button',
							scope : this,
							handler : function() {
							   var objout=this.gridPanel;
				               var _window = new Ext.Window({  
					                title: "添加计划收费项目", 
					                border : false,
					                autoHeight:true,
					                modal : true,
					                width:"30%",  
					                labelWidth:45, 
					                id:'addPlanTocharge',
					                plain:true,  
					                resizable:true,  
					                buttonAlign:"center",  
					                closable:true,  
					                items:[  
					                         new Ext.FormPanel({
												    frame:true,
												    width:"100%",
												    layout:"form",
												    labelWidth:90,
												    items:[{
												              xtype:"textfield",
												              fieldLabel:"收费类型名称",
												              allowBlank:false,
												              blankText:"收费类型名称不能为空，请正确填写",
												              name:"name",
												              anchor:"90%"
												          },{
												              xtype:"combo",
												              fieldLabel:"计费方式",
												              mode : 'local',
												              displayField : 'name',
												              valueField : 'id',
												              editable : false,
												              triggerAction : 'all',
												              hiddenName:'type',
												              allowBlank:false,
												              autoload : false,
												              anchor:"90%",
												              isShowQtip:true,
												              store : new Ext.data.SimpleStore({
														      fields : ["name", "id"],
													          data : [["百分比 (%)", "0"],
																     	["固定金额", "1"]
																     ]})
												          },{
												              xtype : "numberfield",
															  style: {imeMode:'disabled'},
												              fieldLabel:"计划收取费用",
												              name:"planCharges",
												              allowBlank:false,
												              anchor:"90%"
												          },{
												              xtype : "numberfield",
															  style: {imeMode:'disabled'},
												              fieldLabel:"最低收取费用",
												              allowBlank:false,
												              name:"leastCharges",
												              anchor:"90%"
												          }]
												 })
					                ],  
					                buttons:[ {iconCls : 'btn-save',text:"保存",handler:function(){
					                      
					                      var winobj=this.ownerCt.ownerCt;
					                      var formObj=winobj.items.first();
					                      
					                      var name=formObj.getCmpByName('name').getValue();
					                      var type=formObj.getCmpByName('type').getValue();
					                      var planCharges=formObj.getCmpByName('planCharges').getValue();
					                      var leastCharges=formObj.getCmpByName('leastCharges').getValue();
					                      
					                      formObj.getForm().submit({
					                              params:{'gLPlansTocharge.name':name,'gLPlansTocharge.planChargeType':type,'gLPlansTocharge.planCharges':planCharges,
					                              'gLPlansTocharge.leastCharges':leastCharges,
					                              'projId':projectId
					                              },
				                        	  	  url : '',/ __ctxPath + '/creditFlow/guarantee/EnterpriseBusiness/savePlanAndActualGlPlansTocharge.do',
								    			  method : 'POST',
								    			  success : function(form, action) {
								    			         
								    			        _window.hide(); 
								    			        objout.getStore().reload();
								    			  }
					                      })
					                      
					                	
					                }},  
					                 { text:"取消",   iconCls : 'btn-close', handler:function(){_window.hide();}                
	                              } ]  
				                });  
				                 _window.show();  
							}
						},
						'-',
						{
							iconCls : 'btn-del',
							text : '删除实际收费项目',
							xtype : 'button',			
							scope : this,
							handler : function() {
							   
								   var griddel = this.gridPanel;
								   var storedel = griddel.getStore();
								   var s = griddel.getSelectionModel().getSelections();
								   if (s <= 0) {
										Ext.Msg.alert('状态','请选中任何一条记录');
										return false;
								   }
								   Ext.Msg.confirm("提示!",'确定要删除吗？',
										function(btn) {
					
											if (btn == "yes") {
												  griddel.stopEditing();
												  for ( var i = 0; i < s.length; i++) {
													    var row = s[i];
														storedel.remove(row);
														griddel.getView().refresh();
														deleteFun(
															__ctxPath + '/actualCharges/deleteActualToCharge.do',
															{
																id :row.data.actualChargeId
															},
														function() {
															
														},i,s.length)
												   }
											}
									})
							}
						} ]
					})
		        }
		
				this.gridPanel = new HT.EditorGridPanel ({
							border : false,
							tbar:this.toolbar,
						    isShowTbar:false,
					        showPaging:false,
							autoHeight:true,
							clicksToEdit :1,
							stripeRows : true,
							viewConfig : {
								forceFit : true
							},
							url : __ctxPath + "/actualCharges/listByProjectIdActualToCharge.do",
							fields : [
										'actualChargeId',
										'projectId'
										,'planChargeId'
									    ,'actualCharge'
										,'actualChargeRemark'
										,'actualChargeName'
										,'actualChargeType'
										,'actualCharges'
										,'leastCharges'
										,'remark'
									],
							    columns:[
										{
											header : 'actualChargeId',
											dataIndex : 'actualChargeId',
											hidden : true
										},{
											header : '收取类型',	
											dataIndex : 'actualChargeName',
										    editor:{
												xtype : 'textfield',
												readOnly:this.isReadOnly
											}
										 }
										,{
											header : '计费方式',	
											dataIndex : 'actualChargeType',
											editor:new Ext.form.ComboBox({
													   mode : 'local',
													               displayField : 'name',
													              valueField : 'id',
													              editable : false,
													              triggerAction : 'all',
													                 width : 70,
													                 autoload : false,
													                 store : new Ext.data.SimpleStore({
															        fields : ["name", "id"],
														            data : [["百分比 (%)", "0"],
																	     	["固定金额", "1"]
																	     ]
											})}),
											renderer :function(v, metadata, record,rowIndex, columnIndex, store)
					                        {        
											       if(v==0)
											       {
											           var str="计费方式为借款金额乘以百分比";
					                        	       metadata.attr = 'ext:qtip="' + str + '"';
					                        	       return "百分比(%)"
											       }else if(v==1){
											           return "固定金额"
											       }
											}
										},{
											header : '计划收取费用',	
											dataIndex : 'actualCharges',
											editor : new Ext.form.NumberField( {
												allowBlank : false
											}),
											renderer : function(value,metaData, record,rowIndex, colIndex,store){
												  
												  var type=record.get("actualChargeType");
												  if(type==0){
												      return value+"%";
												  }
												  else
												  {
												      return value+"元";
												  }
											}
										},{
											header : '最低收费标准',	
											dataIndex : 'leastCharges',
											qtip:'ddd',
											editor : new Ext.form.NumberField( {
												allowBlank : false
											}),
											renderer : function(value,metaData, record,rowIndex, colIndex,store){
												
												  var type=record.get("actualChargeType");
												  if(type==0){
												      return value+"%";
												  }
												  else
												  {
												      return value+"元";
												  }
											}
										},{
											header : '调整后费用',	
											dataIndex : 'actualCharge',
											renderer : function(value,metaData, record,rowIndex, colIndex,store){
												 
												  if(value==""){
												  	   return '<font color=red>请双击编辑</font>';
												  	   return false;
												  }
												  var type=record.get("actualChargeType");
												  if(type==0){
												      return value+"%";
												  }
												  else
												  {
												      return value+"元";
												  }
											},
											editor:{
												xtype : 'numberfield',
												style: {imeMode:'disabled'},
												readOnly:this.isReadOnly
											}
										},{
											header : '调整费用说明',	
											dataIndex : 'actualChargeRemark',
											editor:{
												xtype : 'textfield',
												readOnly:this.isReadOnly
											}
										},{
											header : '备注',	
											dataIndex : 'remark',
											editor:{
												xtype : 'textfield',
												readOnly:this.isReadOnly
											}
										}
							],
							baseParams:{
								'projectId' : this.projId
							},
						    listeners : {
							   'afteredit' : function(e) {
							         var actualChargeId = e.record.get('actualChargeId'); //收费ID
							         var actualCharge= e.record.get('actualCharge'); //调整后费用
							         var actualChargeRemark= e.record.get('actualChargeRemark'); //调整后费用说明
							         var remark=e.record.get('remark'); //调整后费用说明
							         var actualChargeName=e.record.get('actualChargeName');;
							         var actualChargeType=e.record.get('actualChargeType');;
							         var actualCharges=e.record.get('actualCharges');;
							         var leastCharges=e.record.get('leastCharges');;
							         
							         Ext.Ajax.request({
										url : __ctxPath + '/actualCharges/updateActualToCharge.do',
										method : 'POST',
										success : function(response, request) {
										},
										failure : function(response) {
											Ext.ux.Toast.msg('操作提示', '对不起，数据操作失败！');
										},
										params : {
											'glActualToCharge.actualChargeId' :actualChargeId,
											'glActualToCharge.actualCharge' :actualCharge,
											'glActualToCharge.actualChargeName' :actualChargeName,
											'glActualToCharge.actualChargeType' :actualChargeType,
											'glActualToCharge.actualCharges' :actualCharges,
											'glActualToCharge.leastCharges' :leastCharges,
											'glActualToCharge.actualChargeRemark' :actualChargeRemark,
											'glActualToCharge.remark' :remark
										}
								     })
							    }
					         }
						})
			}
		});