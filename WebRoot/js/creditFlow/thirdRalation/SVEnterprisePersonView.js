/**
 * @author:
 * @class SVEnterprisePersonView
 * @extends Ext.Panel
 * @description [SVEnterprisePerson1]管理
 * @company北京互融时代软件有限公司
 * @createtime:
 */
function selectCus(obj)
{
    var op=Ext.getCmp("addThPerson_win").get(0).get(0);
	if(obj.id!=0 && obj.id!="")	
	op.getCmpByName('person.id').setValue(obj.id);
	if(obj.name!=0 && obj.name!="")	
	op.getCmpByName('person.name').setValue(obj.name);
	if(obj.sex!=0 && obj.sex!="")	
	op.getCmpByName('person.sex').setValue(obj.sex);
	if(obj.cardtype!=0 && obj.cardtype!="")	
	op.getCmpByName('person.cardtype').setValue(obj.cardtype);
	if(obj.cardnumber!=0 && obj.cardnumber!="")	
	op.getCmpByName('person.cardnumber').setValue(obj.cardnumber);
	if(obj.marry!=0 && obj.marry!="")	
	op.getCmpByName('person.marry').setValue(obj.marry);
	if(obj.telphone!=0 && obj.telphone!="")	
	op.getCmpByName('person.telphone').setValue(obj.telphone);
	if(obj.selfemail!=0 && obj.selfemail!="")	
	op.getCmpByName('person.selfemail').setValue(obj.selfemail);
	if(obj.postcode!=0 && obj.postcode!="")	
	op.getCmpByName('person.postcode').setValue(obj.postcode);
	if(obj.postaddress!=0 && obj.postaddress!="")	
	op.getCmpByName('person.postaddress').setValue(obj.postaddress);
}
function setEnterpriseNameStockUpdate(obj) {

	   var op=Ext.getCmp("addThridCompany_win").get(0).get(0);
	   
	   if(obj.enterprisename!=0 && obj.enterprisename!="")	
	   op.getCmpByName('enterprise.enterprisename').setValue(obj.enterprisename);
	   if(obj.id!=0 && obj.id!="")	
	   op.getCmpByName('enterprise.id').setValue(obj.id);
	   if(obj.shortname!=0 && obj.shortname!="")	
	   op.getCmpByName('enterprise.shortname').setValue(obj.shortname);
	   if(obj.address!=0 && obj.address!="")	
	   op.getCmpByName('enterprise.address').setValue(obj.address);
	   if(obj.cciaa!=0 && obj.cciaa!="")	
	   op.getCmpByName('enterprise.cciaa').setValue(obj.cciaa);
       if(obj.organizecode!=0 && obj.organizecode!="")	
	   op.getCmpByName('enterprise.organizecode').setValue(obj.organizecode);
	   if(obj.telephone!=0 && obj.telephone!="")
	   op.getCmpByName('enterprise.telephone').setValue(obj.telephone);
	   if(obj.postcoding!=0 && obj.postcoding!="")
	   op.getCmpByName('enterprise.postcoding').setValue(obj.postcoding);
	   if(obj.hangyetype!=0 && obj.hangyetype!="")
	   op.getCmpByName('enterprise.hangyeType').setValue(obj.hangyetype);
	   if(obj.hangyetypevalue!=0 && obj.hangyetypevalue!="")
	   op.getCmpByName('enterprise.hangyeName').setRawValue(obj.hangyetypevalue);
	   Ext.Ajax.request({
							url : __ctxPath+'/creditFlow/customer/person/seeInfoPerson.do',
							method : "post",
							params : {
								 id : obj.legalpersonid 
							},
							success : function(d) {
								   
	                                 var c = Ext.util.JSON.decode(d.responseText);
	                                 var id=c.data.id;
	                                 var name=c.data.name;
	                                 var sex=c.data.sex;
	                                 var cardtype=c.data.cardtype;
	                                 var cardnumber=c.data.cardnumber;
	                                 var telphone=c.data.telphone;
	                                 var selfemail=c.data.selfemail;
	                                if(id!=0 && id!="")
									op.getCmpByName('person.id').setValue(id);
									if(name!=0 && name!="")
									op.getCmpByName('person.name').setValue(name);
									if(sex!=0 && sex!="")
									op.getCmpByName('person.sex').setValue(sex);
									if(cardtype!=0 && cardtype!="")
									op.getCmpByName('person.cardtype').setValue(cardtype);
									if(cardnumber!=0 && cardnumber!="")
									op.getCmpByName('person.cardnumber').setValue(cardnumber);
									if(telphone!=0 && telphone!="")
									op.getCmpByName('person.telphone').setValue(telphone);
									if(selfemail!=0 && selfemail!="")
									op.getCmpByName('person.selfemail').setValue(selfemail);
							}
	
	   })
	   //grid_sharteequity
	   	if(typeof(Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1))!="undefined")
		{
		      var store=Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1).getStore();
		      var url =__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+obj.id;
		      store.proxy.conn.url = url;
		      store.load();
		}

}
SVEnterprisePersonView = Ext.extend(Ext.Panel, {
			layout:'anchor',
			anchor : '100%',
	        formPanel : null,
	        isLoadThirdRalation : false,
	        isEdit:false,
	        projectId:null,
	        jStore_third:null,
	        gridPanel:null,
			constructor : function(_cfg) {
	        if (_cfg.isLoadThirdRalation) {
			    this.isLoadThirdRalation = _cfg.isLoadThirdRalation;
		    }
	        if (_cfg.isEdit) {
			    this.isEdit = _cfg.isEdit;
		    }
            if(typeof(_cfg.projectId)!="undefined")
            {
                  this.projectId=_cfg.projectId;
            }
			if(typeof(_cfg.businessType)!="undefined")
            {
                  this.businessType=_cfg.businessType;
            }
            if(typeof(_cfg.isHidden)!="undefined")
            {
                  this.isHidden=_cfg.isHidden;
            }else{
            	 this.isHidden=true;
            }
            if(typeof(_cfg.isContractHidden)!="undefined")
            {
                  this.isContractHidden=_cfg.isContractHidden;
            }else{
            	 this.isContractHidden=true;
            }
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			SVEnterprisePersonView.superclass.constructor.call(this, {
						items : [this.gridPanel]
					});
			},
			initUIComponents : function() {
				
				var checkColumn =new Ext.grid.CheckColumn({
					header : "是否法务确认",
					width : 60,
					dataIndex : 'isLegalCheck',
					editable : true,
					hidden : this.isContractHidden,
					scope : this
					
				});
				var render_third = Ext.data.Record.create([{
					name : 'key'
				},{
				name : 'thirdRalationId'
			}, {
				name : 'name'
			}, {
				name : 'code'
			}, {
				name : 'tel'
			}, {
				name : 'type'
			},{
				name : 'contractCategoryTypeText'
			},{
				name : 'contractCategoryText'
			},{
				name : 'isLegalCheck'
			},{
				name : 'id'
			},{
				name : 'contractId'
			},{
				name : 'categoryId'
			},{
				name : 'temptId'
			}]);

	        jStore_third = new Ext.data.Store({
				         proxy : new Ext.data.HttpProxy({
							url : __ctxPath + "/creditFlow/thirdRalation/listSVEnterprisePerson.do",
							method : "POST"
						 }),
						 baseParams:{projectId:this.projectId,businessType:this.businessType},
						 reader : new Ext.data.JsonReader({
									fields : render_third,
									root : 'result',
									id : 'key'
								})
					    });

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '添加企业第三方保证',
									xtype : 'button',
									scope : this,
									hidden : this.isHidden,
									handler : this.createCompany
								},new Ext.Toolbar.Separator({
									hidden : this.isHidden
								}),{
									iconCls : 'btn-add',
									text : '添加个人第三方保证',
									xtype : 'button',
									scope:this,
									hidden : this.isHidden,
									handler : this.createPerson
								},new Ext.Toolbar.Separator({
									hidden : this.isHidden
								}),{
									iconCls : 'btn-del',
									text : '删除第三方保证',
									xtype : 'button',
									scope:this,
									hidden : this.isHidden,
									handler : this.removeRs
								},new Ext.Toolbar.Separator({
									hidden : this.isHidden
								}),{
									iconCls : this.isHidden?'btn-readdocument' : 'btn-edit',
									text : this.isHidden?'查看第三方保证' : '编辑第三方保证',
									xtype : 'button',
									scope:this,
									handler : this.rowClick
								},new Ext.Toolbar.Separator({
									hidden : this.isContractHidden
								}),{
									iconCls : 'btn-edit',
									text : '编辑合同',
									xtype : 'button',
									scope:this,
									hidden : this.isContractHidden,
									handler : function(){
										this.operateThirdContract(this.businessType, this.projectId,jStore_third)
									}
								},new Ext.Toolbar.Separator({
									hidden : this.isContractHidden
								}), {
									iconCls : 'btn-del',
									text : '删除合同',
									xtype : 'button',
									//id : "thirdcontractremoveBtn",
									hidden : this.isContractHidden,
									//disabled : true,
									scope : this,
									handler : function(panel) {
										var selRs = this.gridPanel.getSelectionModel().getSelections();
										if(selRs.length==0){
										   Ext.ux.Toast.msg('操作信息','请选择一条记录！');
										   return;
										}
										Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
					
											if (btn == "yes") {
					
												panel.ownerCt.ownerCt.stopEditing();
												var s = panel.ownerCt.ownerCt.getSelectionModel()
														.getSelections();
												var storethird = panel.ownerCt.ownerCt.getStore();
												for (var i = 0; i < s.length; i++) {
													var row = s[i];
													if (row.data.id == null || row.data.id == '') {
					
														//jStore_contractCategroy.remove(row);
													} else {
					
														SlContractView.deleteFun(
																		__ctxPath
																				+ '/contract/deleteContractCategoryRecordProcreditContract.do',
																		{
																			categoryId : row.data.categoryId
																		}, function() {
																			storethird.reload();
																		});
													}
												}
					
											}
										})
					
									}
								}]
				});
	          	
	            if (this.isLoadThirdRalation == true) {
		                  jStore_third.load();
	            }
				this.gridPanel=new HT.EditorGridPanel({
					//id : 'disanfanggridPanel',
					border : false,
					region : 'center',
					tbar : this.topbar,
					isShowTbar : this.isEdit?false : true,
				    bbar : false,
				    autoHeight : true,
					store : jStore_third,
					plugins : [checkColumn],
					listeners :{
						afteredit : function(e){
							if(e.record.data['contractId']== null||e.record.data['contractId']== ''){
								Ext.ux.Toast.msg('操作信息','请先生成第三方保证合同！');
								jStore_third.reload();
							}else{
								if(e.field == 'isLegalCheck'){//是否法务确认
									Ext.Ajax.request({
										url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
										method : 'POST',
										scope :this,
										success : function(response, request) {
											//jStore_third.reload();
											e.record.commit();
										},
										failure : function(response) {
											Ext.Msg.alert('状态', '操作失败，请重试');
										},
										params: {
											'procreditContract.isLegalCheck':  e.value,
											'procreditContract.id': e.record.data['contractId'],
											projId : this.projectId,
											businessType :this.businessType
										}
									})
								
								}
							}
							
						}
					},
					columns : [{
									header : 'key',
									dataIndex : 'key',
									hidden : true
								},
								{
									header : 'thirdRalationId',
									dataIndex : 'thirdRalationId',
									hidden : true
								}
								,{
									header : '类型',	
									width : 30,
									dataIndex : 'type',
									renderer : function(v) {
												switch (v) {
													case 'company_third' :
													return '企业';
													break;
													case 'person_third' :
													return '个人';
													break;
												}
									}
								}
								,{
									header : '名称',	
									dataIndex : 'name'
								}
								,{
									header : '证件号码',	
									dataIndex : 'code'
								}
								,{
									header : '联系电话',	
									width : 60,
									dataIndex : 'tel'
								},{
									header : '合同类型',	
									hidden : this.isContractHidden,
									dataIndex : 'contractCategoryTypeText'
								},{
									header : '合同名称',	
									width : 140,
									hidden : this.isContractHidden,
									dataIndex : 'contractCategoryText'
								},checkColumn
								
							
					]//end of columns
				});
              
				   this.gridPanel.addListener('rowdblclick',this.rowClick);
				   /*this.gridPanel.getSelectionModel().on('selectionchange',
					function(sm) {
						Ext.getCmp('thirdcontractremoveBtn')
								.setDisabled(sm.getCount() < 1 ? 1 : 0);
					});*/
			},// end of the initComponents()
			
		
			//GridPanel行点击处理事件     
			rowClick:function() {

			var projId=this.projectId;
			var busi=this.businessType;
	        var grid=this.gridPanel;
		    var edit=this.isHidden;
			  var store=grid.getStore();
				
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
			var rec=s[0]
				if(rec.data.type=='company_third'){
				var Company= new Ext.FormPanel({
			       frame:true,
				   items:[new ExtUD.Ext.PeerMainInfoPanel({isAllReadOnly : edit,isEnterprisenameReadonly : edit,
						isEnterpriseShortNameReadonly : edit})]
			
			   })
			
			if (rec.data.id != null && rec.data.id != 'undefined') {
				Company.loadData({
							url : __ctxPath + '/creditFlow/thirdRalation/getSVEnterprisePerson.do?type=company_third&id='+ rec.data.id,
							root : 'data',
							preName : 'enterprise,person'
						});
			}

		    var _window = new Ext.Window({  
                title: "查看企业第三方保证", 
                border : false,
                autoHeight:true,
                width:"60%",  
                labelWidth:45,  
                id:"addThridCompany_win",
                plain:true,  
                resizable:true,  
                buttonAlign:"center",  
                closable:true,  
                items:[  
                     	Company
                ],  
                buttons:[  
                        {iconCls : 'btn-save',text:"确定",hidden:edit,handler:function(){
                                  
                        	      var vDates = getGridDate(Company.getCmpByName('gudong_store').get(0).get(1));
                        	      Company.getForm().submit({
                        	      params:{gudong:vDates,
                        	    	      id:rec.data.id,
                        	    	      projectId:projId,
                        	    	      businessType : busi}, // 传递的参数
                        	  	  url :  __ctxPath + '/creditFlow/thirdRalation/updateComapnyInfoSVEnterprisePerson.do',
				    			  method : 'POST',
				    			  success : function(form, action) {
                        	    	    		var xx=action.response.responseText.toString().trim();
                	                        	  
    				                            if(xx=="{success:true,msg:false}"){
    				                            	Ext.ux.Toast.msg('操作信息','该企业第三方保证已存在，请重新添加');
    				                            	
    				                            	 	   var op=Ext.getCmp("addThridCompany_win").get(0).get(0);
    													   op.getCmpByName('enterprise.enterprisename').setValue('');
    													   op.getCmpByName('enterprise.id').setValue('');
    													   op.getCmpByName('enterprise.shortname').setValue('');
    													   op.getCmpByName('enterprise.address').setValue('');
    													   op.getCmpByName('enterprise.cciaa').setValue('');
    													   op.getCmpByName('enterprise.organizecode').setValue('');
    													   op.getCmpByName('enterprise.telephone').setValue('');
    													   op.getCmpByName('enterprise.postcoding').setValue('');
    													   op.getCmpByName('enterprise.hangyeType').setValue('');

    						                                 op.getCmpByName('person.id').setValue('');
    													     op.getCmpByName('person.name').setValue('');
    													     op.getCmpByName('person.sex').setValue('');
    													     op.getCmpByName('person.cardtype').setValue('');
    													     op.getCmpByName('person.cardnumber').setValue('');
    													     op.getCmpByName('person.telphone').setValue('');
    													     op.getCmpByName('person.selfemail').setValue('');
    													    var sgrid=Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1);
    													    sgrid.getStore().removeAll();
    													    sgrid.getView().refresh();

    				                            }else if(xx=="{success:true,msg:null}"){
    				                            	Ext.ux.Toast.msg('操作信息','企业第三方保证不能添加自己，请重新添加');
    				                            	
    			                            	 	   var op=Ext.getCmp("addThridCompany_win").get(0).get(0);
    												   op.getCmpByName('enterprise.enterprisename').setValue('');
    												   op.getCmpByName('enterprise.id').setValue('');
    												   op.getCmpByName('enterprise.shortname').setValue('');
    												   op.getCmpByName('enterprise.address').setValue('');
    												   op.getCmpByName('enterprise.cciaa').setValue('');
    												   op.getCmpByName('enterprise.organizecode').setValue('');
    												   op.getCmpByName('enterprise.telephone').setValue('');
    												   op.getCmpByName('enterprise.postcoding').setValue('');
    												   op.getCmpByName('enterprise.hangyeType').setValue('');

    					                                 op.getCmpByName('person.id').setValue('');
    												     op.getCmpByName('person.name').setValue('');
    												     op.getCmpByName('person.sex').setValue('');
    												     op.getCmpByName('person.cardtype').setValue('');
    												     op.getCmpByName('person.cardnumber').setValue('');
    												     op.getCmpByName('person.telphone').setValue('');
    												     op.getCmpByName('person.selfemail').setValue('');
    												      var sgrid=Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1);
    												     sgrid.getStore().removeAll();
    												     sgrid.getView().refresh();
    												    
    				                            }else{
    				                            	 
    				                            	 Ext.ux.Toast.msg('操作信息', '修改企业第三方保证成功!');
    				                            	 grid.getStore().reload();
    				                            	 _window.close();
    				                            }
    				                             
				    			 }
				             })
                        }},  
                        {  
                            text:"取消", 
                            iconCls : 'btn-close',
                            hidden:edit,
                            handler:function(){  
                            	     _window.close();
                                }  
                        }, {
                        	text : "关闭", 
                        	iconCls : 'close',
                            hidden : !edit,
                            handler : function(){  
                            	  _window.close();                         
                            }  
                        } 
                    ]  
            });  
					if(typeof(Company.getCmpByName('gudong_store').get(0).get(1))!="undefined"){
				
					      var store=Company.getCmpByName('gudong_store').get(0).get(1).getStore();
					      var url =__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+rec.data.id;
					      store.proxy.conn.url = url;
					      store.load();
				     }
			            _window.show();  
				    
				}else{
						     var person= new Ext.FormPanel
					    ({
					    	  frame:true,
							  items:[new ExtUD.Ext.PeerPersonMainInfoPanel({isAllReadOnly : edit})]
						})
						if (rec.data.id != null && rec.data.id != 'undefined') {
						person.loadData({
									url : __ctxPath + '/creditFlow/thirdRalation/getSVEnterprisePerson.do?type=person_third&id='+ rec.data.id,
									root : 'data',
									preName : 'enterprise,person'
								});
					}
		            var _window = new Ext.Window({
		            border : false,
		            title: "查看个人第三方保证", 
		            autoHeight:true,
		            id:"addThPerson_win",
		            width:"60%",  
		            labelWidth:45,  
		            plain:true,  
		            resizable:true,  
		            buttonAlign:"center",  
		            closable:true,  
		            items:[person],
		                 buttons:[  
		                    {iconCls : 'btn-save',text:"确定",hidden:edit,handler:function(){
		                        	 person.getForm().submit({
		                        	      params:{
		                        	    	      id:rec.data.id,
		                        	    	      projectId:projId,
		                        	    	      businessType : busi
		                        	      }, // 传递的参数
		                        	  	  url :  __ctxPath + '/creditFlow/thirdRalation/updatePersonInfoSVEnterprisePerson.do',
						    			  method : 'POST',
						    			  success : function(form, action) {
		                        	    	 	var xx=action.response.responseText.toString().trim();
					                            if(xx=="{success:true,msg:false}"){
					                            	Ext.ux.Toast.msg('操作信息','该个人第三方保证已存在，请重新添加');
					                            	  
					                            	  var op=Ext.getCmp("addThPerson_win").get(0).get(0);
												      op.getCmpByName('person.id').setValue('');
												      op.getCmpByName('person.name').setValue('');
												      op.getCmpByName('person.sex').setValue('');
												      op.getCmpByName('person.cardtype').setValue('');
												      op.getCmpByName('person.cardnumber').setValue('');
												      op.getCmpByName('person.marry').setValue('');
												      op.getCmpByName('person.telphone').setValue('');
												      op.getCmpByName('person.selfemail').setValue('');
												      op.getCmpByName('person.postcode').setValue('');
												      op.getCmpByName('person.postaddress').setValue('');
					                            }else if(xx=="{success:true,msg:null}"){
					                            	Ext.ux.Toast.msg('操作信息','个人第三方保证不能添加自己，请重新添加');
					                            	
					                            	  var op=Ext.getCmp("addThPerson_win").get(0).get(0);
												      op.getCmpByName('person.id').setValue('');
												      op.getCmpByName('person.name').setValue('');
												      op.getCmpByName('person.sex').setValue('');
												      op.getCmpByName('person.cardtype').setValue('');
												      op.getCmpByName('person.cardnumber').setValue('');
												      op.getCmpByName('person.marry').setValue('');
												      op.getCmpByName('person.telphone').setValue('');
												      op.getCmpByName('person.selfemail').setValue('');
												      op.getCmpByName('person.postcode').setValue('');
												      op.getCmpByName('person.postaddress').setValue('');
					                            }else{
					                            	 Ext.ux.Toast.msg('操作信息', '修改个人第三方保证成功!');
					                            	 store.reload();
						    			             _window.close(); 
					                            }
						    			 }
						             })
		                    	  
		                    	
		                    }},  
		                    {  
		                        text:"取消", 
		                        iconCls : 'btn-close',
		                        hidden:edit,
		                        handler:function(){  
		                                _window.hide();                           
		                            }  
		                    }, {  
		                        text : "关闭",  
		                        iconCls : 'close',
		                        hidden :!edit,
		                        handler : function(){
		                        	_window.close(); 
		                        }  
		                    } 
		                ]
		              })
						_window.show();
						}
					
		
				}
		},

			//添加企业第三方保证
			createCompany:function(){
				
				 var fp= new Ext.FormPanel({
				       frame:true,
					   items:[new ExtUD.Ext.PeerMainInfoPanel({})]
				
				})
				var projectId = this.projectId;
				 var businessType=this.businessType;
			    var _window = new Ext.Window({  
	                title: "添加企业第三方保证", 
	                border : false,
	                autoHeight:true,
	                modal : true,
	                width:"60%",  
	                labelWidth:45,  
	                id:"addThridCompany_win",
	                plain:true,  
	                resizable:true,  
	                buttonAlign:"center",  
	                closable:true,  
	                items:[  
	                     	fp
	                ],  
	                buttons:[  
	                        {iconCls : 'btn-save',text:"保存",handler:function(){
	                                  
	                        	      var vDates = getGridDate(Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1));
	                        	      fp.getForm().submit({
	                        	      params:{"gudongInfo":vDates,"projectId":projectId,businessType:businessType}, // 传递的参数
	                        	  	  url :  __ctxPath + '/project/saveComapnySlSmallloanProject.do',
					    			  method : 'POST',
					    			  success : function(form, action) {
	                        	    	  	var xx=action.response.responseText.toString().trim();
	                        	  
				                            if(xx=="{success:true,msg:false}"){
				                            	Ext.ux.Toast.msg('操作信息','该企业第三方保证已存在，请重新添加');
				                            	
				                            	 	   var op=Ext.getCmp("addThridCompany_win").get(0).get(0);
													   op.getCmpByName('enterprise.enterprisename').setValue('');
													   op.getCmpByName('enterprise.id').setValue('');
													   op.getCmpByName('enterprise.shortname').setValue('');
													   op.getCmpByName('enterprise.address').setValue('');
													   op.getCmpByName('enterprise.cciaa').setValue('');
													   op.getCmpByName('enterprise.organizecode').setValue('');
													   op.getCmpByName('enterprise.telephone').setValue('');
													   op.getCmpByName('enterprise.postcoding').setValue('');
													   op.getCmpByName('enterprise.hangyeType').setValue('');

						                                 op.getCmpByName('person.id').setValue('');
													     op.getCmpByName('person.name').setValue('');
													     op.getCmpByName('person.sex').setValue('');
													     op.getCmpByName('person.cardtype').setValue('');
													     op.getCmpByName('person.cardnumber').setValue('');
													     op.getCmpByName('person.telphone').setValue('');
													     op.getCmpByName('person.selfemail').setValue('');
													    var sgrid=Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1);
													    sgrid.getStore().removeAll();
													    sgrid.getView().refresh();

				                            }else if(xx=="{success:true,msg:null}"){
				                            	Ext.ux.Toast.msg('操作信息','企业第三方保证不能添加自己，请重新添加');
				                            	
			                            	 	   var op=Ext.getCmp("addThridCompany_win").get(0).get(0);
												   op.getCmpByName('enterprise.enterprisename').setValue('');
												   op.getCmpByName('enterprise.id').setValue('');
												   op.getCmpByName('enterprise.shortname').setValue('');
												   op.getCmpByName('enterprise.address').setValue('');
												   op.getCmpByName('enterprise.cciaa').setValue('');
												   op.getCmpByName('enterprise.organizecode').setValue('');
												   op.getCmpByName('enterprise.telephone').setValue('');
												   op.getCmpByName('enterprise.postcoding').setValue('');
												   op.getCmpByName('enterprise.hangyeType').setValue('');

					                                 op.getCmpByName('person.id').setValue('');
												     op.getCmpByName('person.name').setValue('');
												     op.getCmpByName('person.sex').setValue('');
												     op.getCmpByName('person.cardtype').setValue('');
												     op.getCmpByName('person.cardnumber').setValue('');
												     op.getCmpByName('person.telphone').setValue('');
												     op.getCmpByName('person.selfemail').setValue('');
												    var sgrid=Ext.getCmp("addThridCompany_win").getCmpByName('gudong_store').get(0).get(1);
												    sgrid.getStore().removeAll();
												    sgrid.getView().refresh();
				                            }else{
				                            	 
				                            	 Ext.ux.Toast.msg('操作信息', '添加企业第三方保证成功!');
				                            	 this.jStore_third.reload();
					    			             _window.close(); 
				                            }
					    			    
					    			       
					    			 }
					             })
	                        }},  
	                        {  
	                            text:"取消",  
	                            iconCls : 'btn-close',
	                            handler:function(){  
	                                   _window.close();                
	                                }  
	                        }  
	                    ]  
                });  
                _window.show();  
			},
			//添加个人第三方保证
			createPerson:function(){
				
			    var fp= new Ext.FormPanel
			    ({
			    	  frame:true,
					  items:[new ExtUD.Ext.PeerPersonMainInfoPanel()]
				})
				var projectId = this.projectId;
			    var businessType=this.businessType;
                var _window = new Ext.Window({  
                title: "添加个人第三方保证", 
                modal : true,
                autoHeight:true,
                border : false,
                id:"addThPerson_win",
                width:"60%",  
                labelWidth:45,  
                plain:true,  
                resizable:true,  
                buttonAlign:"center",  
                closable:true,  
                items:[fp],
                     buttons:[  
                        {iconCls : 'btn-save',text:"保存",handler:function(){
                            	 fp.getForm().submit({
	                        	      params:{"projectId":projectId,businessType:businessType}, // 传递的参数
	                        	  	  url :  __ctxPath + '/project/savePersonSlSmallloanProject.do',
					    			  method : 'POST',
					    			  success : function(form, action) {
	                        	    	  	var xx=action.response.responseText.toString().trim();
				                            if(xx=="{success:true,msg:false}"){
				                            	Ext.ux.Toast.msg('操作信息','该个人第三方保证已存在，请重新添加');
				                            	  
				                            	  var op=Ext.getCmp("addThPerson_win").get(0).get(0);
											      op.getCmpByName('person.id').setValue('');
											      op.getCmpByName('person.name').setValue('');
											      op.getCmpByName('person.sex').setValue('');
											      op.getCmpByName('person.cardtype').setValue('');
											      op.getCmpByName('person.cardnumber').setValue('');
											      op.getCmpByName('person.marry').setValue('');
											      op.getCmpByName('person.telphone').setValue('');
											      op.getCmpByName('person.selfemail').setValue('');
											      op.getCmpByName('person.postcode').setValue('');
											      op.getCmpByName('person.postaddress').setValue('');
				                            }else if(xx=="{success:true,msg:null}"){
				                            	Ext.ux.Toast.msg('操作信息','个人第三方保证不能添加自己，请重新添加');
				                            	
				                            	  var op=Ext.getCmp("addThPerson_win").get(0).get(0);
											      op.getCmpByName('person.id').setValue('');
											      op.getCmpByName('person.name').setValue('');
											      op.getCmpByName('person.sex').setValue('');
											      op.getCmpByName('person.cardtype').setValue('');
											      op.getCmpByName('person.cardnumber').setValue('');
											      op.getCmpByName('person.marry').setValue('');
											      op.getCmpByName('person.telphone').setValue('');
											      op.getCmpByName('person.selfemail').setValue('');
											      op.getCmpByName('person.postcode').setValue('');
											      op.getCmpByName('person.postaddress').setValue('');
				                            }else{
				                            	 Ext.ux.Toast.msg('操作信息', '添加个人第三方保证成功!');
				                            	 this.jStore_third.reload();
					    			             _window.close(); 
				                            }
					    		   
					    			 }
					             })
                        	  
                        	
                        }},  
                        {  
                            text:"取消", 
                            iconCls : 'btn-close',
                            handler:function(){  
                                    _window.hide();                           
                                }  
                        }  
                    ]  })
					_window.show();
			},
			//按ID删除记录
			removeRs : function() {

				  var proId=this.projectId;
				  var bus=this.businessType;
				  var store=this.gridPanel.getStore();
	
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.Msg.alert('状态','请选中任何一条记录');
					return false;
				}
				Ext.Msg.confirm("提示!",'确定要删除吗？',
					function(btn) {

						if (btn == "yes") {
							var string="";
					
							for ( var i = 0; i < s.length; i++) {
								var row = s[i];
								var str="{"+row.data.id+","+row.data.type+"}";
								if(row.data.categoryId!=''){
									Ext.Msg.alert('状态','请先删除相应的第三方保证对应的合同');
									return;
								}
								string=string+str+"@";
						    }
						
							string=string.substring(0,string.length-1)
						
							Ext.Ajax.request({
								url :__ctxPath+ '/creditFlow/thirdRalation/multiDelSVEnterprisePerson.do',
								params : {
									string : string,
									projectId:proId,
									businessType:bus
								},
								method : 'POST',
								success : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '成功删除该记录！');
								
									store.reload();
								},
								failure : function(response, options) {
									Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
								}
							});
						}
					})
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/creditFlow/thirdRalation/multiDelSVEnterprisePerson.do',
					grid:this.gridPanel,
					idName:'id'
				});
			},
			//编辑Rs
			editRs : function(record) {

			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			},
			operateThirdContract : function(businessType, piKey,jStore) {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var thisPanel = this.gridPanel;
				if (null == selected) {
					Ext.MessageBox.alert('状态', '请选择一条记录!');
				} else {
					var title = selected.get('name');
					var thirdRalationId = selected.get('thirdRalationId');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('categoryId');
					var temptId = selected.get('temptId');
					var window = new OperateContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null||categoryId ==''?0:categoryId,
								temptId :temptId,
								htType :'thirdRalationContract',
								thisPanel : thisPanel
							});
					window.show();
					window.addListener({
								'close' : function() {
									jStore.reload();
								}
							});
				}
			}
});
