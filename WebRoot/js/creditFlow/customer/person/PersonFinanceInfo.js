var getPersonFinanceInfoData = function(grid) {
		if (typeof (grid) == "undefined" || null == grid) {
			return "";
			return false;
		}
	
		var vRecords = grid.items.get(0).getStore().getRange(0, grid.items.get(0).getStore().getCount()); // 得到修改的数据（记录对象）

		var vCount = vRecords.length; // 得到记录长度

		var vDatas = '';
		if (vCount > 0) {
			// begin 将记录对象转换为字符串（json格式的字符串）
			for ( var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			//var index = str.lastIndexOf(",");
			//str = str.substring(0, index) + "}";
			vDatas += str + '@';
		}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		
		return vDatas;
	}
	
	//房产信息
PersonHouseInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		personId :this.personId,
		isHidden:false,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
		isFlow:false,
		constructor : function(_cfg) {
			
			if (typeof (_cfg.personId) != "undefined") {
				this.personId = _cfg.personId;
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
			if (typeof (_cfg.isFlow) != "undefined") {
				this.isFlow = _cfg.isFlow;
			}
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			PersonHouseInfo.superclass.constructor.call(this,
					{
						items : [ this.grid_PersonHouseInfo ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
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
			
			this.PersonHouseInfoBar = new Ext.Toolbar({
				items : [/*{
						html:'<font style="font-family:Arial,"宋体";font-size:12px;color:#837c71;">【房产信息】</font>',
						hidden:!this.isFlow
					},*/{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createPersonHouseInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletePersonHouseInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenSeeBtn
					}), {
						iconCls : 'btn-readdocument',
						text : '查看',
						xtype : 'button',			
//						scope : this,
						hidden : this.isHiddenSeeBtn,
						handler : this.seePersonHouseInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenEditBtn
					}), {
						iconCls : 'btn-readdocument',
						text : '编辑',
						xtype : 'button',			
//						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.seePersonHouseInfo
					}]
				})
	this.grid_PersonHouseInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				//id : 'grid_PersonHouseInfo',
				personId :this.personId,
				name:"grid_personHouseInfo",
				tbar :this.PersonHouseInfoBar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy(
							{//this.ownerCt.ownerCt.ownerCt.ownerCt.PersonCarView.get(0)
								url : __ctxPath+ '/quickenLoan/listCsPersonHouse.do?personId='+ this.personId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [
							{
								name : 'id'
							},
							{
								name : 'personId',
								value:this.personId
							},{
								name : 'propertyOwner'
							},
							{
								name : 'floorSpace'
							},
							{
								name : 'address'
							},
							{
								name : 'isMortgage'
							},
							{
								name : 'hoseValue'
							},{
								name : 'loanMoney'
							},{
								name : 'houseFactValue'
							},{
								name : 'purchaseTime'
							}
							,{
								name : 'marketValue'
							},{
								name : 'houseType'
							},{
								name : 'purchasePrice'
							},{
								name : 'propertyScale'
							},{
								name : 'loanPeriod'
							},{
								name : 'monthlyPayments'
							},{
								name : 'isDoubleBalloon'
							},{
								name : 'isSchoolDistrict'
							},{
								name : 'isOverstory'
							},{
								name : 'isFitment'
							}

					]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({}),
				new Ext.grid.RowNumberer(),{
						header : '产权人',
						dataIndex : 'propertyOwner',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}

					},{
						header : '房屋类型',
						dataIndex : 'houseType',
						sortable : true,
						editor : {
							xtype:'combo',
							id : 'type',
							mode : 'local',
						    displayField : 'name',
						    valueField : 'value',
						    width : 150,
						    readOnly:this.isHidden,
						    store :new Ext.data.SimpleStore({
									fields : ["name", "value"],
									data : [["按揭商品房", "按揭商品房"],["全款商品房", "全款商品房"],["按揭集体小产全房", "按揭集体小产全房"],["全款小产全集体房", "全款小产全集体房"]]
							}),
							triggerAction : "all"
						}
					},{
//						header : '房屋面积（m²）',
						header : '房产建筑面积（m²）',
						dataIndex : 'floorSpace',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'numberfield'	,
							readOnly : this.isReadOnly
						}
					},
					{
						header : '地址',
						dataIndex : 'address',
						sortable : true,
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},
					/*{
					header : '是否按揭',	
					dataIndex : 'isMortgage',
					sortable : true,
					editor :  {					
						xtype:'combo',
						id : 'isMortgage',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    width : 70,
					    readOnly : this.isReadOnly,
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["否", "0"],
										["是", "1"]]
						}),
						triggerAction : "all"
					},
					renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						if(value=='1'){
							return '是'
						}else {
							return '否'
						}
					}
				  },*//*{
						header : '购房单价(元/m²)',
						dataIndex : 'purchasePrice',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'numberfield'	,
							readOnly : this.isReadOnly
						}
					},*/
					{
						header : '房产总值·万元',
						dataIndex : 'hoseValue',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					},
					{
						header : '贷款余额·万元',
						dataIndex : 'loanMoney',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					}/*,
					{
						header : '房产净价值·万元',
						dataIndex : 'houseFactValue',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly,
							listeners:{
								scope:this,
								'blur' : function(nf) {
									var store =this.grid_PersonHouseInfo.getStore();
							  		this.grid_PersonHouseInfo.stopEditing();
							  		var count =store.getCount();
							  		var totalMoney=0;
							  		if(count>0){
							  			for(var i=0;i<count;i++){
							  				var comparValue=this.grid_PersonHouseInfo.getStore().getAt(i).data.houseFactValue;
							  				if(comparValue!=null && comparValue!=""){
							  					totalMoney=eval(totalMoney)+eval(comparValue);
							  				}
							  			}
							  		}
							  		var personCarInfoStore=this.ownerCt.ownerCt.getCmpByName("personCarInfo").items.get(0).items.get(0).getStore();
							  		var personCarInfoStoreCount=personCarInfoStore.getCount();
							  		if(personCarInfoStoreCount>0){
							  			for(var i=0;i<personCarInfoStoreCount;i++){
							  				var personCarInfo=personCarInfoStore.getAt(i).data.finalCertificationPrice;
							  				if(personCarInfo!=null && personCarInfo!=""){
							  					totalMoney=eval(totalMoney)+eval(personCarInfo);
							  				}
							  			}
							  		}
							  		if(totalMoney!=0){
							  			this.ownerCt.ownerCt.getCmpByName("grossasset").setValue(Ext.util.Format.number(totalMoney, ',000,000,000.00')
										);
							  			this.ownerCt.ownerCt.getCmpByName("person.grossasset").setValue(totalMoney);
							  		}
							  		
								}
							}
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					},{
						header : '购买时间',
						dataIndex : 'purchaseTime',
						format : 'Y-m-d',
						renderer : ZW.ux.dateRenderer(this.datafield),
						sortable : true,
						editor : {
							xtype : 'datefield',
							allowBlank : false,
							readOnly : this.isReadOnly,
							editable:false
						}
					},{
						header : '市场价值',
						dataIndex : 'marketValue',
						summaryType : 'sum',
						align : 'right',
						sortable : true,
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')+ "万元";
						}
					}*/,{
						header : '贷款年限',
						dataIndex : 'loanPeriod',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'numberfield'	,
							readOnly : this.isReadOnly
						}
					},/*{
						header : '产权比例',
						dataIndex : 'propertyScale',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'numberfield'	,
							readOnly : this.isReadOnly
						}
					},*/{
						header : '月供（万元）',
						dataIndex : 'monthlyPayments',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'numberfield'	,
							readOnly : this.isReadOnly
						}
					}]
              });
			this.grid_PersonHouseInfo.getStore().load();
		},
		
		createPersonHouseInfo : function() {
			/*var gridadd = this.grid_PersonHouseInfo;
			var storeadd = this.grid_PersonHouseInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			
			p.data[keys[0]] = null;
			p.data[keys[1]] = this.personId;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = 0;
			p.data[keys[6]] = 0;
			p.data[keys[7]] = 0;
			p.data[keys[8]] = 0;
			p.data[keys[9]] = null;
			p.data[keys[10]] = 0;
			p.data[keys[11]] = null;

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);*/
			var gridPanel = this.grid_PersonHouseInfo;
			var readOnly=false;
			var win_addRelationPerson = new Ext.Window({
				id : 'win_addPersonHouseInfo',
				title : '房产信息',
				layout : 'fit',
				width : 680,
				height : 200,
				constrainHeader : true,
				closable : true,
				resizable : false,
				plain : true,
				border : false,
				modal : true,
				buttonAlign : 'center',
				bodyStyle : 'padding: 0',
				items : [new Ext.form.FormPanel({
					id : 'fPanel_addPersonHouseInfo',
					url : __ctxPath+ '/creditFlow/customer/person/saveCsPersonHouse.do',
					labelAlign : 'right',
					frame : true,
					layout : 'column',
					buttonAlign : 'center',
					monitorValid : true,
					defaults : {
						layout : 'form',
						border : false,
						columnWidth : 1
					},
					items : [{
						columnWidth:.33,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'hidden',
							fieldLabel : '产权人',
							name : 'csPersonHouse.personId',
							value : this.personId
						},{
							xtype : 'textfield',
							fieldLabel : '产权人',
							name : 'csPersonHouse.propertyOwner',
							readOnly  : readOnly,
							width : 120
						}, {
							xtype:'combo',
							fieldLabel : '房屋类型',
							hiddenName : 'csPersonHouse.houseType',
							readOnly  : readOnly,
							width : 120,
							id : 'type',
							mode : 'local',
						    displayField : 'name',
						    valueField : 'value',
						    width : 150,
						    readOnly:this.isHidden,
						    store :new Ext.data.SimpleStore({
									fields : ["name", "value"],
									data : [["按揭商品房", "按揭商品房"],["全款商品房", "全款商品房"],["按揭集体小产全房", "按揭集体小产全房"],["全款小产全集体房", "全款小产全集体房"],["抵债房","抵债房"]]
							}),
							triggerAction : "all"
						},{
							xtype : 'textfield',
							fieldLabel : '房屋面积（m²）',
							name : 'csPersonHouse.floorSpace',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '地址',
							name : 'csPersonHouse.address',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						}]
					}, {
						columnWidth:.33,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '购房单价(元/m²)',
							name : 'csPersonHouse.purchasePrice',
							readOnly  : readOnly,
							width : 120
						}, {
							xtype : 'textfield',
							fieldLabel : '房产总值·万元',
							name : 'csPersonHouse.hoseValue',
							readOnly  : readOnly,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '贷款余额·万元',
							name : 'csPersonHouse.loanMoney',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '房产净价值·万元',
							name : 'csPersonHouse.houseFactValue',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						}]
					},{
						columnWidth:.33,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'datefield',
							fieldLabel : '购买时间',
							format : 'Y-m-d',
							name : 'csPersonHouse.purchaseTime',
							readOnly  : readOnly,
							width : 120
						}, {
							xtype : 'textfield',
							fieldLabel : '市场价值',
							name : 'csPersonHouse.marketValue',
							readOnly  : readOnly,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '贷款年限',
							name : 'csPersonHouse.loanPeriod',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '月供（万元）',
							name : 'csPersonHouse.monthlyPayments',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						}]
					}]
				})],
				buttons : [{
					text : '提交',
					iconCls : 'submitIcon',
					formBind : true,
					scope:this,
					handler : function() {
						Ext.getCmp('fPanel_addPersonHouseInfo').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function() {
								Ext.ux.Toast.msg('状态', '添加成功!');
								gridPanel.getStore().reload();
								win_addRelationPerson.destroy();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '添加失败!');
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'cancelIcon',
					handler : function() {
						win_addRelationPerson.destroy();
					}
				}]
			}).show();
		},
		deletePersonHouseInfo : function() {
			var griddel = this.grid_PersonHouseInfo;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.id == null || row.data.id == '') {
									storedel.remove(row);
								griddel.getView().refresh();
								
							} else {
								deleteFun(
										__ctxPath + '/quickenLoan/deleteCsPersonHouse.do',
										{
											personHouseId :row.data.id
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
							var count =storedel.getCount();
							var totalMoney=0;
							if(count>0){
							  	for(var i=0;i<count;i++){
							  		var comparValue=storedel.getAt(i).data.houseFactValue;
							  		if(comparValue!=null && comparValue!=""){
							  			totalMoney=eval(totalMoney)+eval(comparValue);
							  		}
							  }
							}
							var personCarInfoStore=griddel.ownerCt.ownerCt.ownerCt.getCmpByName("personCarInfo").items.get(0).items.get(0).getStore();
							var personCarInfoStoreCount=personCarInfoStore.getCount();
							if(personCarInfoStoreCount>0){
							  	for(var i=0;i<personCarInfoStoreCount;i++){
							  		var personCarInfo=personCarInfoStore.getAt(i).data.finalCertificationPrice;
							  		if(personCarInfo!=null && personCarInfo!=""){
							  			totalMoney=eval(totalMoney)+eval(personCarInfo);
							  		}
							  	}
							}
							  		
						griddel.ownerCt.ownerCt.ownerCt.getCmpByName("grossasset").setValue(Ext.util.Format.number(totalMoney, ',000,000,000.00'));
						griddel.ownerCt.ownerCt.ownerCt.getCmpByName("person.grossasset").setValue(totalMoney);
							  		
						}
					}
				})
		},
		seePersonHouseInfo : function(){
			var gridPanel = this.getOriginalContainer().grid_PersonHouseInfo;
//			var selected = gridPanel.getSelectionModel().getSelected();
			var selections=this.ownerCt.ownerCt.getSelectionModel().getSelections();
			
			var isEdit=true;
			if(this.text=="编辑"){
				isEdit=false;
			}
			if(null==selections || ""==selections || selections.length>1){
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
				return;
			}else{
//				var id = selected.get('id');
				var id = selections[0].data.id;
				Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/customer/person/getCsPersonHouse.do',
					method : 'POST',
					success : function(response, request) {
						objMes = Ext.util.JSON.decode(response.responseText);
						obj = objMes.data;
						var win_seeRelationPerson = new Ext.Window({
							id : 'win_seePersonHouseInfo',
							title : '房产信息',
							layout : 'fit',
							width : 680,
							height : 150,
							constrainHeader : true,
							closable : true,
							resizable : false,
							plain : true,
							border : false,
							modal : true,
							buttonAlign : 'center',
							bodyStyle : 'padding: 0',
							items : [new Ext.form.FormPanel({
								id : 'fPanel_seePersonHouseInfo',
								url : __ctxPath+ '/creditFlow/customer/person/saveCsPersonHouse.do',
								labelAlign : 'right',
								frame : true,
								layout : 'column',
								buttonAlign : 'center',
								monitorValid : true,
								defaults : {
									layout : 'form',
									border : false,
									columnWidth : 1
								},
								items : [{
									columnWidth:.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'hidden',
										name : 'csPersonHouse.id',
										value:obj.id
									},{
										xtype : 'hidden',
										name : 'csPersonHouse.personId',
										value:obj.personId
									},{
										xtype : 'textfield',
										fieldLabel : '产权人',
										name : 'csPersonHouse.propertyOwner',
										readOnly  : isEdit,
										width : 120,
										value : obj.propertyOwner
									}, {
										xtype : 'textfield',
										fieldLabel : '房屋类型',
										name : 'csPersonHouse.houseType',
										readOnly  : isEdit,
										width : 120,
										value : obj.houseType
									},{
										xtype : 'textfield',
										fieldLabel : '房屋面积（m²）',
										name : 'csPersonHouse.floorSpace',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.floorSpace
									},{
										xtype : 'textfield',
										fieldLabel : '地址',
										name : 'csPersonHouse.address',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.address
									}]
								}, {
									columnWidth:.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '购房单价(元/m²)',
										name : 'csPersonHouse.purchasePrice',
										readOnly  : isEdit,
										width : 120,
										value : obj.purchasePrice
									}, {
										xtype : 'textfield',
										fieldLabel : '房产总值·万元',
										name : 'csPersonHouse.hoseValue',
										readOnly  : isEdit,
										width : 120,
										value : obj.hoseValue
									},{
										xtype : 'textfield',
										fieldLabel : '贷款余额·万元',
										name : 'csPersonHouse.loanMoney',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.loanMoney
									},{
										xtype : 'textfield',
										fieldLabel : '房产净价值·万元',
										name : 'csPersonHouse.houseFactValue',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.houseFactValue
									}]
								},{
									columnWidth:.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'datefield',
										fieldLabel : '购买时间',
										format : 'Y-m-d',
										name : 'csPersonHouse.purchaseTime',
										readOnly  : isEdit,
										width : 120,
										value : obj.purchaseTime
									}, {
										xtype : 'textfield',
										fieldLabel : '市场价值',
										name : 'csPersonHouse.marketValue',
										readOnly  : isEdit,
										width : 120,
										value : obj.marketValue
									},{
										xtype : 'textfield',
										fieldLabel : '贷款年限',
										name : 'csPersonHouse.loanPeriod',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.loanPeriod
									},{
										xtype : 'textfield',
										fieldLabel : '月供（万元）',
										name : 'csPersonHouse.monthlyPayments',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.monthlyPayments
									}]
								}]
							})],
							buttons : [{
								text : '提交',
								iconCls : 'submitIcon',
								formBind : true,
								scope:this,
								hidden:isEdit,
								handler : function() {
									Ext.getCmp('fPanel_seePersonHouseInfo').getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function() {
											Ext.ux.Toast.msg('状态', '添加成功!');
											gridPanel.getStore().reload();
											win_seeRelationPerson.destroy();
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '添加失败!');
										}
									});
								}
							}, {
								text : '取消',
								hidden:isEdit,
								iconCls : 'cancelIcon',
								handler : function() {
									win_seeRelationPerson.destroy();
								}
							}]
						}).show();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						id : id
					}
				});
			}
		}
	});
	
	
//车子信息
PersonCarInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		personId :null,
		isHidden:false,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
		isFlow:false,
		constructor : function(_cfg) {
			if (typeof (_cfg.personId) != "undefined") {
				this.personId = _cfg.personId;
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
			if (typeof (_cfg.isFlow) != "undefined") {
				this.isFlow = _cfg.isFlow;
			}
			Ext.applyIf(this, _cfg);
			this.initUIComponents();
			PersonCarInfo.superclass.constructor.call(this,
					{
						items : [ this.grid_PersonCarInfo ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			/*this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})*/
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
			
			this.PersonCarInfoBar = new Ext.Toolbar({
				items : [/*{
						html:'<font style="font-family:Arial,"宋体";font-size:12px;color:#837c71;">【车辆信息】</font>',
						hidden:!this.isFlow
					},*/{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createPersonCarInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deletePersonCarInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenDelBtn
					}), {
						iconCls : 'btn-readdocument',
						text : '查看',
						xtype : 'button',			
//						scope : this,
						hidden : this.isHiddenSeeBtn,
						handler : this.seePersonCarInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-readdocument',
						text : '编辑',
						xtype : 'button',			
//						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.seePersonCarInfo
					}]
				})

	this.grid_PersonCarInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				name : 'grid_PersonCarInfo',
				tbar :this.PersonCarInfoBar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store({
					proxy : new Ext.data.HttpProxy(
							{//this.ownerCt.ownerCt.ownerCt.ownerCt.PersonHouseView.getCmpByName("personHouseInfo")
//								url : __ctxPath+ '/creditFlow/quickenLoan/listCsPersonCar.do?personId='+ this.personId,
								url : __ctxPath+ '/creditFlow/customer/person/listCsPersonCar.do?personId='+ this.personId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [
							{
								name : 'id'
							},
							{
								name : 'personId'
							},{
								name : 'propertyOwner'
							},
							{
								name : 'carSystemType'
							},
							{
								name : 'carType'
							},
							{
								name : 'carLicenseNumber'
							},
							{
								name : 'isMortgage'
							},
							{
								name : 'newCarValue'
							},{
								name : 'loanMoney'
							},{
								name : 'carFactValue'
							},{
								name : 'yearOfCarUse'
							},{
								name : 'finalCertificationPrice'
							}

					]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({}),
				new Ext.grid.RowNumberer(),{
						header : '所有人',
						dataIndex : 'propertyOwner',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}

					},{
//						header : '车系',
						header : '品牌',
						dataIndex : 'carSystemType',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}

					},{
						header : '车型',
						dataIndex : 'carType',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}

					},{
						header : '车牌号',
						dataIndex : 'carLicenseNumber',
						sortable : true,
						editor : {
							xtype : 'textfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						}
					},{
					header : '是否按揭',	
					dataIndex : 'isMortgage',
					sortable : true,
					editor :  {					
						xtype:'combo',
						id : 'type',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    width : 70,
					    readOnly : this.isReadOnly,
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["否", "0"],
										["是", "1"]]
						}),
						triggerAction : "all",
						listeners : {
							scope : this,
							'select' : function(combox,record,index){}
						}
					},
					renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						if(value=='1'){
							return '是'
						}else {
							return '否'
						}
					}
				  },{
						header : '新车价格·万元',
						dataIndex : 'newCarValue',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					},
					{
						header : '贷款余额·万元',
						dataIndex : 'loanMoney',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					},/*{
						header : '车辆净价值·万元',
						dataIndex : 'carFactValue',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					},*/{
						header : '使用年限',
						dataIndex : 'yearOfCarUse',
						sortable : true,
						editor : {
							xtype : 'textfield',
							readOnly : this.isReadOnly
						}

					},{
//						header : '市场公允价值·万元',
						header : '市场评估价值·万元',
						dataIndex : 'finalCertificationPrice',
						summaryType : 'sum',
						align : 'right',
						editor : {
							xtype : 'numberfield',
							allowBlank : false,
							readOnly : this.isReadOnly,
							listeners:{
								scope:this,
								'blur' : function(nf) {
									var store =this.grid_PersonCarInfo.getStore();
							  		this.grid_PersonCarInfo.stopEditing();
							  		var count =store.getCount();
							  		var totalMoney=0;
							  		if(count>0){
							  			for(var i=0;i<count;i++){
							  				var comparValue=this.grid_PersonCarInfo.getStore().getAt(i).data.finalCertificationPrice;
							  				if(comparValue!=null && comparValue!=""){
							  					totalMoney=eval(totalMoney)+eval(comparValue);
							  				}
							  			}
							  		}
							  		var personHouseInfoStore=this.ownerCt.ownerCt.getCmpByName("personHouseInfo").items.get(0).items.get(0).getStore();
							  		var personHouseInfoStoreCount=personHouseInfoStore.getCount();
							  		if(personHouseInfoStoreCount>0){
							  			for(var i=0;i<personHouseInfoStoreCount;i++){
							  				var personHouseInfo=personHouseInfoStore.getAt(i).data.houseFactValue;
							  				if(personHouseInfo!=null && personHouseInfo!=""){
							  					totalMoney=eval(totalMoney)+eval(personHouseInfo);
							  				}
							  			}
							  		}
							  	
							  		this.ownerCt.ownerCt.getCmpByName("grossasset").setValue(Ext.util.Format.number(totalMoney, ',000,000,000.00')
									);
							  		this.ownerCt.ownerCt.getCmpByName("person.grossasset").setValue(totalMoney);
							  		
							  		
								}
							}
						},
						renderer : function(value, metaData, record, rowIndex,
								colIndex, store) {
							return Ext.util.Format.number(value, ',000,000,000.00')
										+ "万元";
		
						}
					}]
              });
                
				
					this.grid_PersonCarInfo.getStore().load();

		},
		
		createPersonCarInfo : function() {/*
			var gridadd = this.grid_PersonCarInfo;
			var storeadd = this.grid_PersonCarInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			p.data[keys[0]] = null;
			p.data[keys[1]] = this.personId;
			p.data[keys[2]] = null;
			p.data[keys[3]] = null;
			p.data[keys[4]] = null;
			p.data[keys[5]] = null;
			p.data[keys[6]] = 0;			
			p.data[keys[7]] = 0;
			p.data[keys[8]] = 0;
			p.data[keys[9]] = 0;
			p.data[keys[10]] = 0;
			p.data[keys[11]] = 0;

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		*/
			var gridPanel = this.grid_PersonCarInfo;
			var readOnly=false;
			var win_addRelationPerson = new Ext.Window({
				id : 'win_addPersonCarInfo',
				title : '车产信息',
				layout : 'fit',
				width : 700,
				height : 180,
				constrainHeader : true,
				closable : true,
				resizable : false,
				plain : true,
				border : false,
				modal : true,
				buttonAlign : 'center',
				bodyStyle : 'padding: 0',
				items : [new Ext.form.FormPanel({
					id : 'fPanel_addPersonCarInfo',
					url : __ctxPath+ '/creditFlow/customer/person/saveCsPersonCar.do',
					labelAlign : 'right',
					frame : true,
					layout : 'column',
					buttonAlign : 'center',
					monitorValid : true,
					defaults : {
						layout : 'form',
						border : false,
						columnWidth : 1
					},
					items : [{
						columnWidth:.35,
						layout : 'form',
						labelWidth : 110,
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'hidden',
							name : 'csPersonCar.personId',
							value : this.personId
						},{
							xtype : 'textfield',
							fieldLabel : '所有人',
							name : 'csPersonCar.propertyOwner',
							readOnly  : readOnly
						},{
							xtype : 'textfield',
							fieldLabel : '新车价格·万元',
							name : 'csPersonCar.newCarValue',
							readOnly  : readOnly
						}, {
							xtype : 'numberfield',
							fieldLabel : '市场评估价值·万元',
							name : 'csPersonCar.finalCertificationPrice',
							readOnly  : readOnly
						}]
					},{
						columnWidth:.32,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '车牌号',
							name : 'csPersonCar.carLicenseNumber',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						},{
							xtype:'combo',
							fieldLabel : '是否按揭',
							id : 'type',
							mode : 'local',
							hiddenName:'csPersonCar.isMortgage',
						    displayField : 'name',
						    valueField : 'id',
						    width : 70,
						    readOnly :readOnly,
						    store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["否", "0"],
											["是", "1"]]
							}),
							triggerAction : "all"
						}, {
							xtype : 'numberfield',
							fieldLabel : '贷款余额·万元',
							name : 'csPersonCar.loanMoney',
							readOnly  : readOnly,
							width : 120
						}]
					}, {
						columnWidth:.33,
						layout : 'form',
						defaults : {
							anchor : '100%'
						},
						items : [{
							xtype : 'textfield',
							fieldLabel : '品牌',
							name : 'csPersonCar.carSystemType',
							readOnly  : readOnly,
							width : 120
						},{
							xtype : 'textfield',
							fieldLabel : '使用年限',
							name : 'csPersonCar.yearOfCarUse',
							readOnly  : readOnly,
							allowBlank : false,
							width : 120
						},{
							xtype:'textfield',
							fieldLabel : '车型',
							name : 'csPersonCar.carType',
							readOnly  : readOnly,
							width : 120
						}]
					}]
				})],
				buttons : [{
					text : '提交',
					iconCls : 'submitIcon',
					formBind : true,
					scope:this,
					handler : function() {
						Ext.getCmp('fPanel_addPersonCarInfo').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function() {
								Ext.ux.Toast.msg('状态', '添加成功!');
								gridPanel.getStore().reload();
								win_addRelationPerson.destroy();
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '添加失败!');
							}
						});
					}
				}, {
					text : '取消',
					iconCls : 'cancelIcon',
					handler : function() {
						win_addRelationPerson.destroy();
					}
				}]
			}).show();
		},
		deletePersonCarInfo : function() {
			var griddel = this.grid_PersonCarInfo;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
				return false;
			}
			Ext.Msg.confirm("提示!",'确定要删除吗？',
				function(btn) {

					if (btn == "yes") {
						griddel.stopEditing();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.id == null || row.data.id == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/quickenLoan/deleteCsPersonCar.do',
										{
											personCarId :row.data.id
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
							griddel.getView().refresh();
							var count =storedel.getCount();
							var totalMoney=0;
							if(count>0){
							  	for(var i=0;i<count;i++){
							  		var comparValue=storedel.getAt(i).data.finalCertificationPrice;
							  		if(comparValue!=null && comparValue!=""){
							  			totalMoney=eval(totalMoney)+eval(comparValue);
							  		}
							  }
							}
							var personHouseInfoStore=griddel.ownerCt.ownerCt.ownerCt.getCmpByName("personHouseInfo").items.get(0).items.get(0).getStore();
							var personHouseInfoStoreCount=personHouseInfoStore.getCount();
							if(personHouseInfoStoreCount>0){
							  	for(var i=0;i<personHouseInfoStoreCount;i++){
							  		var personHouseInfo=personHouseInfoStore.getAt(i).data.houseFactValue;
							  		if(personHouseInfo!=null && personHouseInfo!=""){
							  			totalMoney=eval(totalMoney)+eval(personHouseInfo);
							  		}
							  	}
							}
							  		
						griddel.ownerCt.ownerCt.ownerCt.getCmpByName("grossasset").setValue(Ext.util.Format.number(totalMoney, ',000,000,000.00'));
						griddel.ownerCt.ownerCt.ownerCt.getCmpByName("person.grossasset").setValue(totalMoney);
						}
					}
				})
		},
		seePersonCarInfo : function(){
//			var selected = this.getOriginalContainer().grid_PersonCarInfo.getSelectionModel().getSelected();
			var gridPanel=this.getOriginalContainer().grid_PersonCarInfo;
			var selections=this.ownerCt.ownerCt.getSelectionModel().getSelections();			
			var isEdit=true;
			if(this.text=="编辑"){
				isEdit=false;
			}
			if(null==selections || ""==selections || selections.length>1){
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
				return;
			}else{
//				var id = selected.get('id');
				var id = selections[0].data.id;
				Ext.Ajax.request({
					url : __ctxPath+ '/creditFlow/customer/person/getCsPersonCar.do',
					method : 'POST',
					success : function(response, request) {
						objMes = Ext.util.JSON.decode(response.responseText);
						obj = objMes.data;
						var win_seeRelationPerson = new Ext.Window({
							id : 'win_seePersonCarInfo',
							title : '车产信息',
							layout : 'fit',
							width : 680,
							height : 160,
							constrainHeader : true,
							closable : true,
							resizable : false,
							plain : true,
							border : false,
							modal : true,
							buttonAlign : 'center',
							bodyStyle : 'padding: 0',
							items : [new Ext.form.FormPanel({
								id : 'fPanel_seePersonCarInfo',
								url : __ctxPath+ '/creditFlow/customer/person/saveCsPersonCar.do',
								labelAlign : 'right',
								frame : true,
								layout : 'column',
								buttonAlign : 'center',
								monitorValid : true,
								defaults : {
									layout : 'form',
									border : false,
									columnWidth : 1
								},
								items : [{
									columnWidth:.35,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									labelWidth : 110,
									items : [{
										xtype : 'hidden',
										name : 'csPersonCar.id',
										value : obj.id
									},{
										xtype : 'hidden',
										name : 'csPersonCar.personId',
										value : this.personId
									},{
										xtype : 'textfield',
										fieldLabel : '所有人',
										name : 'csPersonCar.propertyOwner',
										value : obj.propertyOwner,
										readOnly  : isEdit
									},{
										xtype : 'textfield',
										fieldLabel : '新车价格·万元',
										dataIndex : 'csPersonCar.newCarValue',
										value : obj.newCarValue,
										readOnly  : isEdit
									}, {
										xtype : 'numberfield',
										fieldLabel : '市场评估价值·万元',
										name : 'csPersonCar.finalCertificationPrice',
										readOnly  : isEdit,
										value : obj.finalCertificationPrice
									}]
								},{
									columnWidth:.32,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '车牌号',
										name : 'csPersonCar.carLicenseNumber',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.carLicenseNumber
									},{
										xtype:'combo',
										fieldLabel : '是否按揭',
										id : 'type',
										mode : 'local',
										hiddenName:'csPersonCar.isMortgage',
									    displayField : 'name',
									    valueField : 'id',
									    width : 70,
									    readOnly :isEdit,
										triggerAction : "all",
									    store : new Ext.data.SimpleStore({
												fields : ["name", "id"],
												data : [["否", "0"],
														["是", "1"]]
										}),
										value:1==obj.isMortgage?"是":"否"
									}, {
										xtype : 'numberfield',
										fieldLabel : '贷款余额·万元',
										name : 'csPersonCar.loanMoney',
										readOnly  : isEdit,
										width : 120,
										value : obj.loanMoney
									}]
								}, {
									columnWidth:.33,
									layout : 'form',
									defaults : {
										anchor : '100%'
									},
									items : [{
										xtype : 'textfield',
										fieldLabel : '品牌',
										name : 'csPersonCar.carSystemType',
										readOnly  : isEdit,
										width : 120,
										value : obj.carSystemType
									},{
										xtype : 'textfield',
										fieldLabel : '使用年限',
										name : 'csPersonCar.yearOfCarUse',
										readOnly  : isEdit,
										allowBlank : false,
										width : 120,
										value:obj.yearOfCarUse
									},{
										xtype:'textfield',
										fieldLabel : '车型',
										hiddenName : 'csPersonCar.carType',
										readOnly  : isEdit,
										width : 120,
										value : obj.carType
									}]
								}]
							})],
							buttons : [{
								text : '提交',
								iconCls : 'submitIcon',
								formBind : true,
								scope:this,
								hidden:isEdit,
								handler : function() {
									Ext.getCmp('fPanel_seePersonCarInfo').getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function() {
											Ext.ux.Toast.msg('状态', '添加成功!');
											gridPanel.getStore().reload();
											win_seeRelationPerson.destroy();
										},
										failure : function(form, action) {
											Ext.ux.Toast.msg('状态', '添加失败!');
										}
									});
								}
							}, {
								text : '取消',
								hidden:isEdit,
								iconCls : 'cancelIcon',
								handler : function() {
									win_seeRelationPerson.destroy();
								}
							}]
						}).show();
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '操作失败，请重试');
					},
					params : {
						id : id
					}
				});
			}
		}
	});
