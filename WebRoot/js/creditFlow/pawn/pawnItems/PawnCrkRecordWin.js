PawnCrkRecordWin = Ext.extend(Ext.Window,{
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			PawnCrkRecordWin.superclass.constructor.call(this,{	
				layout : 'fit',
				items : this.gridPanel,
				modal : true,
				height : 350,
				width : 1000,
				
				maximizable : true,
				title : '出入库记录',
				buttonAlign : 'center',
				buttons : [{
							text : '保存',
							iconCls : 'btn-save',
							scope : this,
							hidden:this.isHidden,
							handler : this.save
						}, {
							text : '关闭',
							iconCls : 'close',
							scope : this,
							handler :function(){
								this.close()
							}
						}]
				})
		},
		initUIComponents : function() {
			 var winObj = this;
			 var rowEditIndex=-1;
			this.datefield=new Ext.form.DateField({
				format : 'Y-m-d',
				readOnly:this.isHidden,
				allowBlank : false
			})
			this.topbar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : this.createRs
					},new Ext.Toolbar.Separator({
					}), {
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',			
						scope : this,
						handler : this.deleteRs
					},new Ext.Toolbar.Separator({
					})]
				})

	this.gridPanel = new Ext.grid.EditorGridPanel(
			{
				border : false,
				tbar :this.isHidden==true?null:this.topbar,
				autoScroll : true,
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
								url : __ctxPath+ '/creditFlow/pawn/pawnItems/listPawnCrkRecord.do?projectId='+ this.projectId+"&businessType="+this.businessType+"&pawnItemId="+this.pawnItemId,
								method : "POST"
							}),
					reader : new Ext.data.JsonReader({
						fields : Ext.data.Record.create( [
							{
								name : 'crkRecordId'
							},
							{
								name : 'operateType'
							},{
								name : 'personnelIssueId'
							},
							{
								name : 'personnelIssueName'
							},
							{
								name : 'matterDate'
							},
							{
								name : 'storageLocation'
							},
							{
								name : 'remarks'
							},{
								name : 'creatorName'
							},{
								name : 'createTime'
							},{
								name : 'pawnItemId'
							}

					]),
					root : 'result'
				})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),
				new Ext.grid.RowNumberer(),{
					header : '操作类型',	
					dataIndex : 'operateType',
					sortable : true,
					editor :  {					
						xtype:'combo',
						id : 'type',
						mode : 'local',
					    displayField : 'name',
					    valueField : 'id',
					    width : 70,
					    readOnly : this.isHidden,
					    store : new Ext.data.SimpleStore({
								fields : ["name", "id"],
								data : [["入库", "0"],
										["出库", "1"]]
						}),
						triggerAction : "all"
					},
					renderer : function(value,metaData, record,rowIndex, colIndex,store) {
						if(value=='0'){
							return '入库'
						}else if(value=='1'){
							return '出库'
						}
					}
				  },{
						header : '事项人员',
						dataIndex : 'personnelIssueName',
						sortable : true,
						editor : {
						xtype : 'textfield',
						allowBlank : false,
						scope : this,
						 readOnly : this.isHidden,
						listeners : {
							scope : this,
							focus : function(obj) {
								var grid = winObj.get(0);
								new UserDialog({
									single : true,
									userIds : grid.store.getAt(rowEditIndex).data.personnelIssueId,
									userName : grid.store.getAt(rowEditIndex).data.personnelIssueName,
									callback : function(uId, uname) {
										grid.store.getAt(rowEditIndex).data.personnelIssueId = uId;
										grid.store.getAt(rowEditIndex).data.personnelIssueName = uname;
										grid.getView().refresh();
									}
								}).show();
							}
						}
	
					},
					renderer : function(value, metaData, record, rowIndex,
							colIndex, store) {
						return value;
					}
					},{
						header : '事项时间',
						dataIndex : 'matterDate',
						sortable : true,
						editor : this.datefield,
						renderer : function(value){
							return  Ext.util.Format.date(value, 'Y-m-d');
						}
					},
					{
						header : '存放地点',
						dataIndex : 'storageLocation',
						sortable : true,
						editor : {
							xtype : 'textfield',
							 readOnly : this.isHidden
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},
					{
						header : '事项备注',
						dataIndex : 'remarks',
						sortable : true,
						editor : {
							xtype : 'textfield',
							 readOnly : this.isHidden
							
						},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},
					{
						header : '录入人员',
						dataIndex : 'creatorName',
						sortable : true,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},
					{
						header : '录入时间',
						dataIndex : 'createTime',
						sortable : true,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                    	data=Ext.util.Format.date(data, 'Y-m-d')
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					}]
              });
                
				
					this.gridPanel.getStore().load();
					this.gridPanel.on({
						scope : this,
						beforeedit : function(e) {
							if (this.gridPanel.store.getAt(e.row).data.verify == true || this.gridPanel.store.getAt(e.row).data.superviseManageStatus ==1 || this.isHidden == true || this.gridPanel.store.getAt(e.row).data.isProduceTask == true) {
								rowEditIndex = -1;
								e.stopEvent();
							} else {
								rowEditIndex = e.row;
							}
						}
					});

		},
		
		createRs : function() {
			
			var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 1; i < keys.length; i++) {
				p.data[keys[i]] = null;
				p.data[keys[4]] = new Date();
				p.data[keys[7]] = currentUserFullName;
				p.data[keys[8]] = new Date();
			}

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteRs : function() {
			var griddel = this.gridPanel;
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
						 var ids = Array();
						for ( var i = 0; i < s.length; i++) {
							var row = s[i];
							if (row.data.crkRecordId == null || row.data.crkRecordId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								ids.push(row.data.crkRecordId)
							}
							storedel.remove(row);
							griddel.getView().refresh();
						}
						Ext.Ajax.request( {
							url : __ctxPath + '/creditFlow/pawn/pawnItems/multiDelPawnCrkRecord.do',
							method : 'POST',
							success : function(response) {
								Ext.ux.Toast.msg('操作信息', '删除成功!');
									
							},
							params : {ids:ids}
						});
					}
				})
		},
	
		getData : function() {
		var vRecords = this.gridPanel.getStore().getRange(0, this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）

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
	},
	save : function(){
		var recordData=this.getData();
		var win=this;
		Ext.Ajax.request( {
			url : __ctxPath + '/creditFlow/pawn/pawnItems/savePawnCrkRecord.do',
			method : 'POST',
			success : function(response) {
				Ext.ux.Toast.msg('操作信息', '保存成功!');
				win.close()	
			},
			params : {
				recordData : recordData,
				projectId : this.projectId,
				businessType : this.businessType,
				pawnItemId : this.pawnItemId
			}
		});
	}
});
