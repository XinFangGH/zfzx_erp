/**
 * @author 
 * @createtime 
 * @class OurProcreditMaterialsForm
 * @extends Ext.Window
 * @description OurProcreditMaterials表单
 * @company 智维软件
 */
OurArchivesMaterialsForm = Ext.extend(Ext.Window, {
	operateGrid:null,
	constructor : function(_cfg) {
		if (typeof (_cfg.businessTypeName) != "undefined") {
			this.businessTypeName = _cfg.businessTypeName;
		}
		if (typeof (_cfg.operateGrid) != "undefined") {
			this.operateGrid = _cfg.operateGrid;
		}
		if (typeof (_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		OurArchivesMaterialsForm.superclass.constructor.call(this, {
			id : 'OurArchivesMaterialsForm'+this.businessType,
			layout : 'fit',
			items : this.formPanel,
			modal : true,
			height: 150,
			width : 420,
			maximizable : true,
			title : '归档材料详细信息',
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
			autoScroll : true,
			defaultType : 'textfield',
			labelAlign:'right',
			labelWidth:70,
			items : [{
				name : 'ourArchivesMaterials.materialsId',
				xtype : 'hidden',
				value : this.materialsId == null? '': this.materialsId
			},/* {
				xtype : "globalCombo",
				hiddenName : "ourArchivesMaterials.businessTypeGlobalId",
				typeName : '业务经营', // xx代表分类名称
				//typeName : this.businessTypeName,
				fieldLabel : "业务类别",
				allowBlank : false,
				editable : false,
				//emptyText : "请选择",
				blankText : "业务类别不能为空，请正确填写!",
				anchor : "93%",
				displayField : 'typeName',
				valueField : 'proTypeId',
				triggerAction : 'all',
				readOnly : true,
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
						var st = combox.getStore();
						st.on("load", function() {
							combox.setValue(combox.getValue());
						})
					},
					select : function(combox, record, index) {
						var v = record.data.proTypeId;
						var arrStore = new Ext.data.ArrayStore({
							url : __ctxPath + '/creditFlow/getBusinessTypeListByParentIdCreditProject.do',
							baseParams : {
								parentId : v
							},
							fields : ['proTypeId', 'typeName'],
							autoLoad : true
						});
						var opr_obj = this.getCmpByName('ourArchivesMaterials.operationTypeGlobalId');
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
				hiddenName : "ourArchivesMaterials.operationTypeGlobalId",
				typeName : this.businessTypeName,
				fieldLabel : "业务品种",
				allowBlank : false,
				editable : false,
				blankText : "业务品种不能为空，请正确填写!",
				anchor : "93%",
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
			},*/ {
				xtype:'textarea',
				fieldLabel : '材料名称',
				name : 'ourArchivesMaterials.materialsName',
				allowBlank : false,
				anchor : "93%"
			},{
				xtype : 'hidden',
				name : 'ourArchivesMaterials.isPublic',
				value :1
			},{
				xtype : 'hidden',
				name : 'ourArchivesMaterials.businessTypeKey',
				value :this.businessType
			}/*, {
				xtype:'combo',
				mode : 'local',
			    displayField : 'name',
			    valueField : 'id',
			    value:"1",
			    store : new Ext.data.SimpleStore({
					fields : ["name", "id"],
					data : [["必备", "1"],["可选", "2"]]
				}),
				triggerAction : "all",
				hiddenName:"ourArchivesMaterials.isPublic",
				fieldLabel : '是否必备',
		        anchor : '93%',
				allowBlank : false,
				readOnly : this.isReadOnlyPerson
			}*/]
		});
		//加载表单对应的数据	
		if (this.materialsId != null && this.materialsId != 'undefined') {
			this.formPanel.loadData({
				url : __ctxPath + "/creditFlow/archives/getOurArchivesMaterials.do?materialsId="+ this.materialsId,
				root : 'data',
				success : function(response, options) {
					var result = Ext.decode(response.responseText);
					/*this.getCmpByName('ourArchivesMaterials.businessTypeGlobalId').setValue(result.data.businessTypeGlobalId);
					this.getCmpByName('ourArchivesMaterials.operationTypeGlobalId').setValue(result.data.operationTypeGlobalId);*/
				},
				preName : 'ourArchivesMaterials'
			});
		}

	},//end of the initcomponents

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
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath + "/creditFlow/archives/saveOurArchivesMaterials.do",
			callback : function(fp, action) {
				if (this.operateGrid!= null) {
					this.operateGrid.getStore().reload();
				}
				this.close();
			}
		});
	}//end of save
});