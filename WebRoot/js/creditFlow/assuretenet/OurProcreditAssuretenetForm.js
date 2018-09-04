/**
 * @author 
 * @createtime 
 * @class OurProcreditAssuretenetForm
 * @extends Ext.Window
 * @description OurProcreditAssuretenet表单
 * @company 智维软件
 */
OurProcreditAssuretenetForm = Ext.extend(Ext.Window, {
       // businessTypeKey:'SmallLoan',
        operateGrid:null,
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);
			if (typeof (_cfg.businessTypeName) != "undefined") {
				this.businessTypeName = _cfg.businessTypeName;
			}
			if (typeof (_cfg.businessType) != "undefined") {
				this.businessType = _cfg.businessType;
			}
			this.operateGrid=_cfg.operateGrid;
			this.initUIComponents();
			OurProcreditAssuretenetForm.superclass.constructor.call(this, {
				id : 'OurProcreditAssuretenetForm'+this.businessType,
				layout : 'fit',
				items : this.formPanel,
				modal : true,
				height: 150,
				width : 460,
				maximizable : true,
				title : '准入原则名称详细信息',
				buttonAlign : 'center',
				buttons : [{
					text : '保存',
					iconCls : 'btn-save',
					scope : this,
					handler : this.save
				}, {
					text : '重置',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.reset
				}, {
					text : '取消',
					iconCls : 'btn-cancel',
					scope : this,
					handler : this.cancel
				}]
			});
		},//end of the constructor
		//初始化组件
		initUIComponents : function() {
			this.formPanel = new Ext.FormPanel({
			    layout : 'form',
				bodyStyle : 'padding:10px',
				border : false,
				autoScroll:true,
				defaults : {
					anchor : '96%,96%'
				},
				defaultType : 'textfield',
				items : [{
						name : 'ourProcreditAssuretenet.assuretenetId',
						xtype : 'hidden',
						value : this.assuretenetId == null ? '' : this.assuretenetId
					},{
						name : 'ourProcreditAssuretenet.businessTypeKey',
						xtype : 'hidden',
						value : this.businessType
					},{
						name : 'ourProcreditAssuretenet.businessTypeName',
						xtype : 'hidden'
					}/*,{
						xtype : "globalCombo",
						hiddenName : "ourProcreditAssuretenet.businessTypeGlobalId",
						typeName : '业务经营', // xx代表分类名称
						//typeName : this.businessTypeName,
						fieldLabel : "业务类别",
						allowBlank : false,
						editable : false,
						//emptyText : "请选择",
						blankText : "业务类别不能为空，请正确填写!",
						anchor : "100%",
						displayField : 'typeName',
						valueField : 'proTypeId',
						triggerAction : 'all',
						readOnly : true,
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath + '/creditFlow/getBusinessTypeListCreditProject.do',
							fields : ['itemId', 'itemName']
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								Ext.Ajax.request({
			                   		url : __ctxPath + '/system/getTypeInfoGlobalType.do',
			                   		method : 'POST',
			                   		params : {
										nodeKey : this.businessType
									},
			                  		success : function(response,request) {
										var obj = Ext.util.JSON.decode(response.responseText);
										st.on("load", function() {
											if(combox.getValue()==''){
												var idx = st.find("proTypeId", obj.proTypeId);
												var record = st.getAt(idx);
												combox.setValue(st.getAt(idx).data.proTypeId);
												combox.fireEvent("select", combox,record, 0);
											}else{
												var idx = st.find("proTypeId", combox.getValue());
												var record = st.getAt(idx);
												combox.setValue(st.getAt(idx).data.proTypeId);
											}
										
									})}
                             	})
								
							},
							select : function(combox, record, index) {
								var v = record.data.proTypeId;
								var tname = record.data.typeName;
								var arrStore = new Ext.data.ArrayStore({
									url : __ctxPath + '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
									baseParams : {
										parentId : v
									},
									fields : ['proTypeId', 'typeName'],
									autoLoad : true
								});
								var opr_obj = this.getCmpByName('ourProcreditAssuretenet.operationTypeGlobalId');
								opr_obj.clearValue();
								opr_obj.store = arrStore;
								arrStore.load({
									"callback" : test
								});
								function test(r) {
									if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
										opr_obj.view.setStore(arrStore);
									}
									if (typeof(arrStore.getAt(0)) != "undefined") {
										var proTypeId = arrStore.getAt(0).data.proTypeId;
										var typeName = arrStore.getAt(0).data.typeName;
										opr_obj.typeName = typeName;
										opr_obj.setRawValue(typeName);
										opr_obj.setValue(proTypeId);
										var recordN = arrStore.getAt(0);
										opr_obj.fireEvent("select", opr_obj,arrStore.getAt(0), 0);
									}
								}
							}
						}
				    },{
				    	xtype : "globalCombo",
						hiddenName : "ourProcreditAssuretenet.operationTypeGlobalId",
						typeName : this.businessTypeName,
						fieldLabel : "业务品种",
						allowBlank : false,
						editable : false,
						blankText : "业务品种不能为空，请正确填写!",
						anchor : "100%",
						displayField : 'typeName',
						valueField : 'proTypeId',
						triggerAction : 'all',
						store : null,
						listeners : {
							afterrender : function(combox) {
							    var st = combox.getStore();
								st.on("load", function() {
									combox.setValue(combox.getValue());
								})
							}
						}
					}*//*,{
						xtype:'combo',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    anchor:'100%',
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["企业", "company"],
										["个人", "person"]]
						}),
						triggerAction : "all",
						hiddenName : 'ourProcreditAssuretenet.customerType',
						anchor : "100%",
						allowBlank : false,
						fieldLabel : '客户类型',
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								var record = st.getAt(0);
								var v = record.data.id;
								combox.setValue(v);
								combox.fireEvent("select", combox,record, 0);
								combox.clearInvalid();
							}
						}
					}*/,{
						fieldLabel : '准入原则名称',	
						xtype : "textarea",
						name : 'ourProcreditAssuretenet.assuretenet',
						allowBlank : false,
						anchor : '100%',
						maxLength: 255
				    }]
			});
				//加载表单对应的数据	
			if (this.assuretenetId != null && this.assuretenetId != 'undefined') {
				this.formPanel.loadData({
					url : __ctxPath + '/assuretenet/getOurProcreditAssuretenet.do?assuretenetId='+ this.assuretenetId,
					root : 'data',
					success : function(response, options) {
						var result = Ext.decode(response.responseText);
					/*	this.getCmpByName('ourProcreditAssuretenet.businessTypeGlobalId').setValue(result.data.businessTypeGlobalId);
						this.getCmpByName('ourProcreditAssuretenet.operationTypeGlobalId').setValue(result.data.operationTypeGlobalId);*/
					},
					preName : 'ourProcreditAssuretenet'
				});
			}
		},
		/**
		 * 重置
		 * @param {} formPanel
		 */
		reset : function() {
			this.formPanel.getForm().reset();
		},
		/**
		 * 取消
		 * @param {} window
		 */
		cancel : function() {
			this.close();
		},
		/**
		 * 保存记录
		 */
		save : function() {
			//var businessTypeGlobalId = this.getCmpByName('ourProcreditAssuretenet.businessTypeGlobalId').getValue();
			$postForm({
					formPanel:this.formPanel,
					scope:this,
					msg : (this.assuretenetId != null && this.assuretenetId != 'undefined')?'保存成功':'添加成功',
					url:__ctxPath + '/assuretenet/saveOurProcreditAssuretenet.do',
					callback:function(fp,action){
						this.operateGrid.getStore().reload();
						this.close();
					}
				}
			);
		}
	});