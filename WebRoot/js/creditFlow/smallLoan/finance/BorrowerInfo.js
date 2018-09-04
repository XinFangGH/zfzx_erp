			
	var getBorrowerInfoData = function(grid) {

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
			vDatas += str + '@';
		}

			vDatas = vDatas.substr(0, vDatas.length - 1);
		}
		return vDatas;
	}
BorrowerInfo = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		projectId :null,
		isHidden:false,
		isHiddenAddBtn : true,
		isHiddenDelBtn : true,
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
			BorrowerInfo.superclass.constructor.call(this,
					{
						items : [ this.grid_BorrowerInfo ]
					})
		},
		initUIComponents : function() {
			if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true) {
				this.isHidden = true;
			}
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})
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
			
			this.BorrowerInfoBar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						hidden : this.isHiddenAddBtn,
						handler : this.createBorrowerInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenAddBtn
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenDelBtn,
						handler : this.deleteBorrowerInfo
					},new Ext.Toolbar.Separator({
						hidden : this.isHiddenDelBtn
					}), {
						iconCls : 'btn-readdocument',
						text : '查看',
						xtype : 'button',			
						scope : this,
						hidden : this.isHiddenSeeBtn,
						handler : this.seeBorrowerInfo
					}]
				})

	this.grid_BorrowerInfo = new Ext.grid.EditorGridPanel(
			{
				border : false,
				id : 'grid_BorrowerInfo',
				tbar :this.BorrowerInfoBar,
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
							{
								url : __ctxPath+ '/creditFlow/smallLoan/finance/listBorrowerInfo.do?projectId='+ this.projectId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [
							{
								name : 'borrowerInfoId'
							},
							{
								name : 'type'
							},{
								name : 'customerId'
							},
							{
								name : 'cardNum'
							},
							{
								name : 'relation'
							},
							{
								name : 'address'
							},
							{
								name : 'telPhone'
							},{
								name : 'remarks'
							},{
								name : 'customerName'
							}

					]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),{
					header : '类型',	
					dataIndex : 'type',
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
								data : [["企业", "0"],
										["个人", "1"]]
						}),
						triggerAction : "all",
						listeners : {
							scope : this,
							'select' : function(combox,record,index){
								var grid_BorrowerInfo=this.grid_BorrowerInfo;
								var r=combox.getValue();
								var personCom = new Ext.form.ComboBox({
										triggerClass : 'x-form-search-trigger',
										resizable : true,
										onTriggerClick : function() {
											this.setVisible(false);
											if(r=='0'){
												var selectEnterpriseObj=function(obj){
													grid_BorrowerInfo.getSelectionModel().getSelected().data['customerId']=obj.id;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['customerName']=obj.enterprisename;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['cardNum']=obj.cciaa;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['address']=obj.area;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['telPhone']=obj.telephone;
													grid_BorrowerInfo.getView().refresh();
												}
												selectEnterprise(selectEnterpriseObj);
											}else if(r=='1'){
												var selectPersonObj=function(obj){
													grid_BorrowerInfo.getSelectionModel().getSelected().data['customerId']=obj.id;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['customerName']=obj.name;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['cardNum']=obj.cardnumber;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['address']=obj.postaddress;
													grid_BorrowerInfo.getSelectionModel().getSelected().data['telPhone']=obj.cellphone;
													grid_BorrowerInfo.getView().refresh();
												}
												selectPWName(selectPersonObj);
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
												grid_BorrowerInfo.getView().refresh();
											},
											'blur' : function(f) {
												if (f.getValue() != null && f.getValue() != '') {
												}
											}
										}
									})
									var ComboBox = new Ext.grid.GridEditor(personCom);
									grid_BorrowerInfo.getColumnModel().setEditor(3,ComboBox);
								
							}
						}
					},
					renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						if(value=='0'){
							return '企业'
						}else if(value=='1'){
							return '个人'
						}
					}
				  },{
						header : '名称',
						dataIndex : 'customerName',
						sortable : true

					},{
						header : '证件号码',
						dataIndex : 'cardNum',
						sortable : true,
						align : 'center',
						editor : {
							xtype : 'textfield'	,
							readOnly : this.isReadOnly
						}
					},
					{
						header : '与客户的关系',
						dataIndex : 'relation',
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
					{
						header : '联系电话',
						dataIndex : 'telPhone',
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
					{
						header : '备注',
						dataIndex : 'remarks',
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
					}]
              });
                
				
					this.grid_BorrowerInfo.getStore().load();

		},
		
		createBorrowerInfo : function() {
			
			var gridadd = this.grid_BorrowerInfo;
			var storeadd = this.grid_BorrowerInfo.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = '';
			}

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteBorrowerInfo : function() {
			var griddel = this.grid_BorrowerInfo;
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
							if (row.data.borrowerInfoId == null || row.data.borrowerInfoId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/smallLoan/finance/deleteBorrowerInfo.do',
										{
											borrowerInfoId :row.data.borrowerInfoId
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
		seeBorrowerInfo : function(){
			var s = this.grid_BorrowerInfo.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else if(s.length>1){
					Ext.ux.Toast.msg('操作信息','只能选中一条记录');
					return false;
				}else{	
					var record=s[0]
					if(record.data.type=='0'){
						seeEnterpriseCustomer(record.data.customerId)
			
					}else{
						seePersonCustomer(record.data.customerId)
					}
				}
		}
	});
