var getInvestmentDate = function(grid) {
				if (typeof (grid) == "undefined" || null == grid) {
					return "";
					return false;
				}
				var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
				var vCount = vRecords.length; // 得到记录长度
				var vDatas = '';
				if (vCount > 0) {
					// begin 将记录对象转换为字符串（json格式的字符串）
					var persenct =0;
					for ( var i = 0; i < vCount; i++) {
						persenct =persenct+vRecords[i].data.investRate
						var str = Ext.util.JSON.encode(vRecords[i].data);
						/*var index = str.lastIndexOf(",");
						str = str.substring(0, index) + "}";*/
						vDatas += str + '@';
					}
					vDatas = vDatas.substr(0, vDatas.length - 1);
					vDatas+='&&'+persenct;
				}
				return vDatas;
			}
addInvestment = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		name:"addInvestment_info",
		projectId :null,
		isHidden:false,
		isHiddenAddBtn : false,
		isHiddenDelBtn : false,
		constructor : function(_cfg) {
			if (typeof (_cfg.projectId) != "undefined") {
				this.projectId = _cfg.projectId;
			}
	        if(typeof(_cfg.isHidden) != "undefined"){
	        	this.isHidden=_cfg.isHidden
	        }
	        if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
				this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
			}
			if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
				this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
			}
			if (typeof (_cfg.enableEdit) != "undefined") {
				this.enableEdit = _cfg.enableEdit;
			}
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			addInvestment.superclass.constructor.call(this,
					{
						items : [ this.grid_addInvestment ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计';
			}
			var  panel =this.grid_addInvestment;
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:false,
				allowBlank : false,
				listeners : {
						scope : this,
						'change' : function(nf){
							 var startDate=nf.getValue();
							 var period =this.object.getCmpByName("obObligationProject.payintentPeriod").getValue();
							 //var endDate=this.grid_addInvestment.getSelectionModel().getSelected() ;
							// alert(endDate)
								setIntentDate("monthPay",null,period,startDate,this.grid_addInvestment)
							}
						}
			})
			this.datefield1=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:true,
				allowBlank : false
			})
			
			var setIntentDate=function(payAccrualType,dayOfEveryPeriod,payintentPeriod,startDate,intentDatePanel){
				
				Ext.Ajax.request({
					url : __ctxPath + "/project/getIntentDateSlSmallloanProject.do",
					method : 'POST',
					scope:this,
					params : {
						payAccrualType:payAccrualType,
						dayOfEveryPeriod:dayOfEveryPeriod,
						payintentPeriod:payintentPeriod,
						startDate:startDate
					},
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						intentDatePanel.getSelectionModel().getSelected().data['investEndDate']=obj.intentDate;
						intentDatePanel.getView().refresh();
					},
					failure : function(response,request) {
						Ext.ux.Toast.msg('操作信息', '查询任务完成时限操作失败!');
					}
				});
			}
			var deleteFun = function(url, prame, sucessFun,i,j) {
				Ext.Ajax.request( {
					url : url,
					method : 'POST',
					success : function(response) {
						if (response.responseText.trim() == '{success:true}') {
							if(i==(j-1)){
								Ext.ux.Toast.msg('操作信息', '删除成功!');
							}
							sucessFun();
						} else if (response.responseText.trim() == '{success:false}') {
							Ext.ux.Toast.msg('操作信息', '删除失败!');
						}
					},
					params : prame
				});
			};
			
			this.addInvestmentBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createAddInvestment
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenDelBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteInvestperson
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenseeBtn
					}),new Ext.Toolbar.Separator({
						hidden : this.isHiddenDelBtn
					}), {
						iconCls : 'btn-save',
						text : '保存',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddensaveBtn,
						handler : this.saveRs
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenseeBtn
					}),  {
						iconCls : 'btn-detail',
						text : '查看投资人款项计划',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenseeBtn/*,
						handler : this.deleteCapitalExit*/
					}]
				})
	this.grid_addInvestment = new Ext.grid.EditorGridPanel({
				border : false,
				tbar :this.addInvestmentBar ,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				plugins : [summary],
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
							proxy : new Ext.data.HttpProxy(
									{
										url : __ctxPath+ '/creditFlow/creditAssignment/bank/listInvestObObligationInvestInfo.do?obligationId='+this.obligationId,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'id'
									},{
										name : 'investPersonName'
									},{
										name : 'investQuotient'
									},{
										name : 'investMoney'
									},{
										name : 'investStartDate'
									},{
										name : 'investEndDate'
									},{
										name : 'investRate'
									},{
										name : 'obligationId'
									},{
										name : 'investMentPersonId'
									},{
										name : 'fundIntentStatus'
									},{
										name : 'aviliableMoney'
									}
							]),
							root : 'result'
						})
			}),
			columns : [
						new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
						new Ext.grid.RowNumberer(),
					{
						header : '投资人',
						dataIndex : 'investPersonName',
						align : 'center',
						width : 127,
						summaryRenderer : totalMoney,
						editor :new Ext.form.ComboBox({
							triggerClass : 'x-form-search-trigger',
							resizable : true,
							onTriggerClick : function() {
								selsectSystemAccount(selectInvestPersons)
							},
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							minChars : 1,
							width : 100,
							listWidth : 150,
							readOnly:this.isHidden,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							listeners : {
								'select' : function(combo,record,index) {
									grid.getView().refresh();
								},
								'blur' : function(f) {
									/*if (f.getValue() != null && f.getValue() != '') {
									}*/
								}
							}
						})
					},{
						header : '投资份额（份）',
						dataIndex : 'investQuotient',
						align : 'right',
						width : 127,
						summaryType: 'sum',
						editor : {
								xtype : 'textfield',
								readOnly:this.isHidden,
								listeners : {
									scope:this,
									focus:function(){/*
									var fundIntentStatus= this.grid_addInvestment.getSelectionModel().getSelected().data['fundIntentStatus'] ;
									if(fundIntentStatus==1||fundIntentStatus==2){
										alert("已经对过账的投资人债权信息不允许编辑");
										return;
									}
									*/},
								   	change : function(v){
								   		var fundIntentStatus= this.grid_addInvestment.getSelectionModel().getSelected().data['fundIntentStatus'] ;
									if(fundIntentStatus==1||fundIntentStatus==2){
										alert("已经对过账的投资人债权信息不允许编辑");
										return;
									}
									  if(v.getValue()==""){
									   v.setValue("0.00")
									  }else{
									    var ss=v.getValue()*(this.object.getCmpByName('obObligationProject.minInvestMent').getValue());
									    this.grid_addInvestment.getSelectionModel().getSelected().data['investMoney'] = ss;
									    var rate =(ss/(this.object.getCmpByName('obObligationProject.projectMoney').getValue()))*100;
									    this.grid_addInvestment.getSelectionModel().getSelected().data['investRate'] = rate;
									    
									  }
								   }
								}
						}
					},{
						header : '投资金额（元）',
						dataIndex : 'investMoney',
						align : 'right',
						width : 127,
						summaryType: 'sum',
						editor : {
								xtype : 'numberfield',
								readOnly:true,
								listeners : {
								   blur : function(v){
									  if(v.getValue()==""){
									    v.setValue("0.00")
									  }
								   }
								}
						}
					},{
						header : '投资开始日期',
						dataIndex : 'investStartDate',
						format : 'Y-m-d ',
						//xtype : 'datecolumn',
					    sortable : true,
						width : 120,
						renderer : ZW.ux.dateRenderer(this.datefield),
						editor :this.datefield
						
					},{
						header : '投资结束日期',
						dataIndex : 'investEndDate',
						format : 'Y-m-d ',
						//xtype : 'datecolumn',
					    sortable : true,
						width : 120,
						renderer : ZW.ux.dateRenderer(this.datefield),
						editor :this.datefield
					},{
						header : '投资比例(%)',
						dataIndex : 'investRate',
						align : 'right',
						width : 127,
						summaryType: 'sum',
						editor : {
								xtype : 'numberfield',
								readOnly:true,
								listeners : {
								   blur : function(v){
									  if(v.getValue()==""){
									    v.setValue("0.00")
									  }
								   }
								}
						}
					}],
					listeners : {
						scope : this,
						beforeedit : function(e) {
							if(typeof(e.record.data['fundIntentStatus']) != "undefined") {
								if(e.record.data['fundIntentStatus']==1||e.record.data['fundIntentStatus']==2) {
									e.cancel = true;
								}else if (this.enableEdit == false) {
									e.cancel = true;
								}
							}else if(typeof(e.record.data['fundIntentStatus']) == "undefined") {
								e.cancel = false;
							}
						}
				    }
              });
					var mgrid =this.grid_addInvestment;
				var selectInvestPersons=function(obj){
					if(obj.investPersonId!=0 && obj.investPersonId!=""){
						mgrid.getSelectionModel().getSelected().data.investMentPersonId=obj.investPersonId;
					}if(obj.investPersonName!=0 && obj.investPersonName!=""){
						mgrid.getSelectionModel().getSelected().data.investPersonName=obj.investPersonName;
					}if(obj.availableInvestMoney!=0 && obj.availableInvestMoney!=""){
						mgrid.getSelectionModel().getSelected().data.aviliableMoney=obj.availableInvestMoney;
					}
						mgrid.getView().refresh();
					}
					this.grid_addInvestment.getStore().load();
		
		},
		// 保存数据
		saveRs : function() {
			var data = getInvestmentDate(this.grid_addInvestment);
			var object =this.object;
			var thisValue=this;
			if(data!=""){
				var str =data.split("&&");
				data=str[0];
				var persent =str[1];
				if(persent>100){
					Ext.ux.Toast.msg('操作信息', '购买该债权产品的投资人投资比例不允许超过100%，请检查后在保存');
					return ;
				}else{
					Ext.Ajax.request({
					url : __ctxPath + '/creditFlow/creditAssignment/bank/saveinvestMentListObObligationInvestInfo.do',
					method : 'POST',
					scope : this,
					params : {
						investMentList : data,
						obligationId : this.obligationId
						},
					success : function(response, request) {
						this.grid_addInvestment.getStore().reload();
						this.grid_addInvestment.getView().refresh();
						Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/creditAssignment/project/getObObligationProject.do?id="+ thisValue.obligationId ,
						root : 'data',
						preName : 'obObligationProject',
						scope : this,
						success : function(response, request) {
								var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								object.getCmpByName('mappingMoney').setValue(Ext.util.Format.number(alarm_fields.data.mappingMoney,'0,000.00'));
								object.getCmpByName('unmappingMoney').setValue(Ext.util.Format.number(alarm_fields.data.unmappingMoney,'0,000.00'));
								//object.getCmpByName('minInvestMent').setValue(Ext.util.Format.number(alarm_fields.data.minInvestMent,'0,000.00'));
								object.getCmpByName('obObligationProject.unmappingQuotient').setValue(Ext.util.Format.number(alarm_fields.data.unmappingQuotient,'0,000.00'));
							}
					})
						}
					});
				}
			}else{
				Ext.Ajax.request({
				url : __ctxPath + '/creditFlow/creditAssignment/bank/saveinvestMentListObObligationInvestInfo.do',
				method : 'POST',
				scope : this,
				params : {
					investMentList : data,
					obligationId : this.obligationId
				},
				success : function(response, request) {
					this.grid_addInvestment.getStore().reload();
					this.grid_addInvestment.getView().refresh();
					Ext.Ajax.request({
						url : __ctxPath + "/creditFlow/creditAssignment/project/getObObligationProject.do?id="+ thisValue.obligationId ,
						root : 'data',
						preName : 'obObligationProject',
						scope : this,
						success : function(response, request) {
								var respText = response.responseText;
								var alarm_fields = Ext.util.JSON.decode(respText);
								object.getCmpByName('mappingMoney').setValue(Ext.util.Format.number(alarm_fields.data.mappingMoney,'0,000.00'));
								object.getCmpByName('unmappingMoney').setValue(Ext.util.Format.number(alarm_fields.data.unmappingMoney,'0,000.00'));
								//object.getCmpByName('minInvestMent').setValue(Ext.util.Format.number(alarm_fields.data.minInvestMent,'0,000.00'));
								object.getCmpByName('obObligationProject.unmappingQuotient').setValue(Ext.util.Format.number(alarm_fields.data.unmappingQuotient,'0,000.00'));
							}
					})
					}
				});
			}
			/**/

		}, 	
		createAddInvestment : function() {
			var gridadd = this.grid_addInvestment;
			var storeadd = this.grid_addInvestment.getStore();
			var count =storeadd.getCount();
			var persenct =0;
			if(count!=0){
				for(i=0;i<count;i++){
					persenct+=storeadd.getAt(i).data.investRate;
				}
			}
			if(persenct>=100){
				Ext.ux.Toast.msg('操作信息', '购买该债权产品的投资人投资比例不允许超过100%，不允许增加投资人');
				return ;
			}else{
				var keys = storeadd.fields.keys;
				var p = new Ext.data.Record();
				p.data = {};
				for ( var i = 1; i < keys.length; i++) {
					p.data[keys[2]] = '0';
					p.data[keys[3]] = '0';
					p.data[keys[4]] = '';
					p.data[keys[6]] = '0.0';
					p.data[keys[7]] = this.object.getCmpByName('obObligationProject.id').getValue();
					p.data[keys[9]] = '0';
				}
				var count = storeadd.getCount() + 1;
				gridadd.stopEditing();
				storeadd.addSorted(p);
				gridadd.getView().refresh();
				gridadd.startEditing(0, 1);
			}
			
		},
		deleteInvestperson:function(){
			var griddel = this.grid_addInvestment;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else if(s > 1){
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}else{
				griddel.stopEditing();
				var row = s[0];
				if(row.data.fundIntentStatus==1||row.data.fundIntentStatus==2){
					Ext.ux.Toast.msg('操作信息','该投资人投资债权已经处于回款中了！不能删除');
					return false;
				}else{
					Ext.Msg.confirm("提示!",'确定要删除吗？',
						function(btn) {
							if (btn == "yes") {
								griddel.stopEditing();
									var row = s[0];
									if (row.data.id == null || row.data.id == '') {
										storedel.remove(row);
										griddel.getView().refresh();
									} else {
										deleteFun(
												__ctxPath + '/creditFlow/creditAssignment/bank/delInvestObligationObObligationInvestInfo.do',
												{
													investObligationid :row.data.id,
													investMentPersonId:row.data.investMentPersonId,
													obligationId:row.data.obligationId
													
												},
												function() {
												},0,1)
									}
									storedel.remove(row);
									griddel.getView().refresh();
								}
							}
						)
				}
			}
		
		}
	});
