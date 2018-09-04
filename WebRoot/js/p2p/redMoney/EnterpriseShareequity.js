var getGridDate = function(grid) {

	if (typeof (grid) == "undefined" || null == grid) {
		return "";
		return false;
	}
	var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）

	var vCount = vRecords.length; // 得到记录长度

	var vDatas = '';
	if (vCount > 0) {
		// begin 将记录对象转换为字符串（json格式的字符串）
		for ( var i = 0; i < vCount; i++) {
			var str = Ext.util.JSON.encode(vRecords[i].data);
			var index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			index = str.lastIndexOf(",");
			str = str.substring(0, index) + "}";
			vDatas += str + '@';
		}

		vDatas = vDatas.substr(0, vDatas.length - 1);
	}
	return vDatas;
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
EnterpriseShareequity = Ext.extend(Ext.Panel,{
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
		isTitle : true,
		constructor : function(_cfg) {
			if (typeof (_cfg.projectId) != "undefined") {
				this.projectId = _cfg.projectId;
			}
			if (typeof (_cfg.enId) != "undefined") {
				this.enId = _cfg.enId;
			}
			if (typeof (_cfg.bussinessType) != "undefined") {
				this.bussinessType = _cfg.bussinessType;
			}
			if (_cfg.isHidden) {
				this.isHidden = _cfg.isHidden;
			}
			if (typeof (_cfg.isTitle) != "undefined") {
				this.isTitle = _cfg.isTitle;
			}
			
			Ext.applyIf(this, _cfg);
			
			this.initUIComponents();
			EnterpriseShareequity.superclass.constructor.call(this,{
						items : [ 
							{xtype:'panel',
							border:false,
							hidden : this.isTitle==true?false:true,
							bodyStyle:'margin-bottom:5px',
							html : this.enId!=null?"":'<B><font class="x-myZW-fieldset-title">【股东(投资人)信息】：</font></B>'
							},
							this.grid_sharteequity 
						]
					})
		},
		initUIComponents : function() {
			this.sharteequitybar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.createShareequity
							
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						hidden:this.isHidden,
						scope : this,
						handler : this.deleteShareequity
					},new Ext.Toolbar.Separator({
						hidden : this.isHidden
					}),{
						iconCls : 'btn-readdocument',
						text : '查看',
						xtype : 'button',			
						scope : this,
						handler:this.seeShareequity
					} ]
				})
	var url="";
	if(this.projectId==null && this.bussinessType==null && this.enId!=null){
	       url=__ctxPath+ '/creditFlow/common/getShareequity.do?enterpriseId='+ this.enId;
	}		
	else{
	
	       url=__ctxPath+ '/creditFlow/common/getShareequity.do?projectId='+ this.projectId+ "&enterpriseId="+ this.enId+"&bussinessType="+this.bussinessType;
	}
	this.grid_sharteequity = new HT.EditorGridPanel(
			{
				border : false,
				region:'center',
				showPaging:false,
				tbar : this.sharteequitybar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store(
						{
							proxy : new Ext.data.HttpProxy(
									{
										url:url,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'id'
									},
									{
										name : 'shareholdertype'
									}/*,
									{
										name : 'shareholdercode'
									}*/,
									{
										name : 'capital'
									},
									{
										name : 'capitaltype'
									},
									{
										name : 'share'
									},
									{
										name : 'shareholder'
									},
									{
										name : 'remarks'
									},
									{
										name : 'personid'
									},{
									
									    name : 'createTime'
									},
									{
										name : 'personName'
									},
									{
										name : 'shareholdertypeName'
									},
									{
										name : 'capitaltypeName'
									}

							]),
							root : 'result'
						})
			}),
				columns : [
					{
					header : '股东(投资人)类别',
					dataIndex : 'shareholdertype',
					sortable : true,
					width : 150,
					editor : new DicIndepCombo({

						allowBlank : false,
						maxLength : 128,
						editable : true,
						lazyInit : false,
						nodeKey : 'shareequityType',
                        readOnly:this.isHidden, 
						listeners : {
							scope : this,
							'select' : function(combox,record,index) {
								var grid = this.grid_sharteequity;

								var r = combox.getValue();
								grid.getSelectionModel().getSelected().data['shareholdertype'] =r;
								grid.getSelectionModel().getSelected().data['personid'] = "";
					            grid.getSelectionModel().getSelected().data['personName'] = "";
					           // grid.getSelectionModel().getSelected().data['shareholdercode'] = "";
					            grid.getView().refresh();
									var personCom = new Ext.form.ComboBox(
											{
												triggerClass : 'x-form-search-trigger',
												resizable : true,
												onTriggerClick : function() {
												if(grid.getSelectionModel().getSelected().data['shareholdertype'].trim()=="person_shareequity"){
												var selectPersonObj =function(obj) {
													var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
		                                            var vCount = vRecords.length;
		                                            var flag=false;
													if (vCount > 0) {
														for ( var i = 0; i < vCount; i++) {
															if(vRecords[i].data.personid==obj.id && vRecords[i].data.shareholdertype=='person_shareequity'){
																Ext.ux.Toast.msg('操作信息','该股东(投资人)名称已存在，请重新输入');
																flag=true;
																return;
															}
	
														}
												
													}
													if(flag==false){
														grid.getSelectionModel().getSelected().data['personid'] = obj.id;
				                            			grid.getSelectionModel().getSelected().data['personName'] = obj.name;
				                            		//	grid.getSelectionModel().getSelected().data['shareholdercode'] = obj.cardnumber;
												        grid.getView().refresh();
													}
														
												
													}
													selectPWName(selectPersonObj);
												}else if(grid.getSelectionModel().getSelected().data['shareholdertype'].trim()=="company_shareequity"){
													
											var selectPersonObj = function(obj) {
												var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
	                                            var vCount = vRecords.length;
	                                            var flag=false;
												if (vCount > 0) {
													for ( var i = 0; i < vCount; i++) {

														if(vRecords[i].data.personid==obj.id && vRecords[i].data.shareholdertype=='company_shareequity'){
															Ext.ux.Toast.msg('操作信息','该股东(投资人)名称已存在，请重新输入');
															flag=true;
															return;
														}

													}
											
												}
												if(flag==false){
														grid.getSelectionModel().getSelected().data['personid'] = obj.id;
											            grid.getSelectionModel().getSelected().data['personName'] = obj.enterprisename;
													//	grid.getSelectionModel().getSelected().data['shareholdercode'] = obj.organizecode;
						                            	grid.getView().refresh();
												}
											}
											selectEnterprise(selectPersonObj);
										
												}
												},
										mode : 'remote',
										editable : false,
										lazyInit : false,
										allowBlank : false,
										typeAhead : true,
										minChars : 1,
										width : 100,
										listWidth : 150,
										store : new Ext.data.JsonStore({}),
										triggerAction : 'all',
										listeners : {
											'select' : function(combo,record,index) {
												grid.getView().refresh();
											},
											'blur' : function(f) {
												if (f.getValue() != null && f.getValue() != '') {
												}
											}
										}
											})
									var ComboBox = new Ext.grid.GridEditor(personCom);
									grid.getColumnModel().setEditor(3,ComboBox);
							}
						}
					}),
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							
							var objcom = this.editor;
							var objStore = objcom.getStore();
							var idx = objStore.find("dicKey", value);
							if (idx != "-1") {
								return objStore.getAt(idx).data.itemValue;
							} else {
								return record.get("shareholdertypeName")
							}
						}
					},
					{
						header : '股东(投资人)名称',
						dataIndex : 'personName',
						sortable : true,
						width : 200
					}/*,{
						header : '证件号码',
						dataIndex : 'shareholdercode',
						sortable : true,
						//width : 150,
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						}
					}*/,{
						header : '出资额',
						dataIndex : 'capital',						
						sortable : true,
						align : 'right',
						editor : {
							xtype : 'numberfield',
							readOnly:this.isHidden,
							allowBlank:false,
							style: {imeMode:'disabled'}
							
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						
							return Ext.util.Format.number(value,'0,000.00')+"元"	
						}

					},
					{
						header : '出资方式',
						dataIndex : 'capitaltype',
						sortable : true,
						align:'center',
						width : 100,
						editor : new DicKeyCombo(
								{
									allowBlank : false,
									maxLength : 128,
									editable : true,
									nodeKey : 'czfs',//原来为capitaltype。两个key下的子项一样。使用czfs。
									lazyInit : true,
									lazyRender : true,
									readOnly:this.isHidden
									//width : 200
								}),
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						
							var objcom = this.editor;
							var objStore = objcom.getStore();
							var idx = objStore.find("itemId", value);
							if (idx != "-1") {
								return objStore.getAt(idx).data.itemName;
							} else {
								return record.get("capitaltypeName")
							}
						}
					}, {
						header : '持股比例',
						width : 100,
						dataIndex : 'share',
						sortable : true,
						editor : {
							xtype : 'numberfield',
							allowBlank:false,
							readOnly:this.isHidden,
							style: {imeMode:'disabled'},
							listeners : {
								scope : this,
								'blur' : function(com){
									var grid = this.grid_sharteequity;
									var vRecords = grid.getStore().getRange(0, grid.getStore().getCount()); // 得到修改的数据（记录对象）
	                                var vCount = vRecords.length;
	                                //判断单条记录持股比例是否超过100%
	                                if(com.getValue()>100){
										Ext.ux.Toast.msg('操作信息','股东持股比例不能超过100%');
										com.setValue('')
									}
									//判断所有记录持股比例之和是否超过100%
	                                if(vCount>0){
	                                	var flag=0 ;
	                                	for( var i = 0; i < vCount; i++){
	                                		flag =flag+vRecords[i].data.share
	                                	}
	                                	 if((flag+com.getValue())>100){
	                                	 	Ext.ux.Toast.msg('操作信息','所有股东持股比例之和不能超过100%');
	                                	 	com.setValue('')
	                                	 }
	                                }
	                               
									
								}
							}
						},
						renderer : function(value,metaData, record,rowIndex, colIndex,store) {
							return value+"%"
							
						}
					}, {
						header : '备注',
						dataIndex : 'remarks',
						sortable : true,
						align : "center",
						editor : {
							xtype : 'textfield',
							readOnly:this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},{
						header : '时间',
						dataIndex : 'createTime',
						hidden:true
					} ]

              });
                
				if (null != this.projectId) {
					this.grid_sharteequity.getStore().load();
				}
				else if(null!=this.enId){
				    this.grid_sharteequity.getStore().load();
				}
     },
     createShareequity : function(){
    	 var gridadd = this.grid_sharteequity;
			var storeadd = this.grid_sharteequity.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = '';
				p.data[keys[2]] = 0;
				p.data[keys[4]] = 0;
				p.data[keys[8]]=new Date()
			}
			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
     },
     deleteShareequity : function(){
      
			var griddel = this.grid_sharteequity;
			var storedel = griddel.getStore();
			var s = griddel.getSelectionModel().getSelections();
			if (s.length <= 0) {
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
										__ctxPath + '/creditFlow/common/deleteShareequity.do',
										{
											id :row.data.id
										},
										function() {
											
										},i,s.length)
							}
							
							storedel.remove(row);
							griddel.getView().refresh();
						}
					}
				})
		
     },
     seeShareequity : function(){

			var s = this.grid_sharteequity.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					if(record.data.shareholdertype=='company_shareequity'){
						seeEnterpriseCustomer(record.data.personid)
			
					}else{
						seePersonCustomer(record.data.personid)
					}
				}
		    
     }
});
