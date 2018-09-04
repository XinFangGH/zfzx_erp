FundsToPromotePanel = Ext.extend(Ext.Panel,{
		layout : 'anchor',
		anchor : '100%',
		constructor : function(_cfg) {
		
			Ext.applyIf(this, _cfg);

			this.initUIComponents();
			FundsToPromotePanel.superclass.constructor.call(this,
					{
						items : [this.gridPanel ]
					})
		},
		initUIComponents : function() {
			 var rowEditIndex=-1;
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
			
			/*this.tbar = new Ext.Toolbar({
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
					}),{
						iconCls : 'btn-add',
						text : '立即推介',
						xtype : 'button',
						scope : this,
						handler : this.editRs
					}]
				})*/

	this.gridPanel = new Ext.grid.GridPanel(//EditorGrid
			{
				border : false,
			    //tbar :this.isHidden ? null:this.tbar,
				autoHeight : true,
				clicksToEdit :1,
				stripeRows : true,
				enableDragDrop : false,
				rowActions : true,
				viewConfig : {
					forceFit : true
				},
				sm : new Ext.grid.CheckboxSelectionModel({}),
				store : new Ext.data.Store(
						{
							proxy : new Ext.data.HttpProxy(
									{
										url :  __ctxPath+ '/creditFlow/finance/listFundsToPromote.do?projectId='+ this.projectId,
										method : "POST"
									}),
							reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [
									{
										name : 'fundsToPromoteId'
									},
									{
										name : 'promoteDate'
									},
									{
										name : 'promotePersonId'
									},
									{
										name : 'promotePersonName'
									},
									{
										name : 'investPersonId'
									},
									{
										name : 'investPersonName'
									},
									{
										name : 'title'
									},{
										name : 'promoteContent'
									},{
										name : 'promoteMonthod'
									},{
										name : 'fileId'
									},{
										name : 'projectId'
									}

							]),
							root : 'result'
						})
			}),
				columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isHidden}),new Ext.grid.RowNumberer(),
				{
					header : '推介时间',
					format : 'Y-m-d',
					dataIndex : 'promoteDate',
					sortable : true,
					editor : this.datefield,
					renderer : ZW.ux.dateRenderer(this.datefield)	
				},{
					header : '推介人',
					dataIndex : 'promotePersonName',
					sortable : true,
					editor :  {
						xtype : 'textfield',
						allowBlank : false,
						listeners : {
							scope : this,
							focus : function(obj) {
								var mgrid =this.gridPanel;
								var projectId=this.projectId
								new UserDialog({
									single : true,
									userIds:mgrid.getSelectionModel().getSelected().data.promotePersonId,
				 					userName:mgrid.getSelectionModel().getSelected().data.promotePersonName,
									callback : function(uId, uname) {
										mgrid.getSelectionModel().getSelected().data.promotePersonId = uId;
										mgrid.getSelectionModel().getSelected().data.promotePersonName = uname;
										mgrid.getView().refresh();
										var args = {
											'fundsToPromote.promotePersonId' : uId,
											'fundsToPromote.promotePersonName' : uname,
											'fundsToPromote.fundsToPromoteId' : mgrid.getSelectionModel().getSelected().data.fundsToPromoteId,
											'fundsToPromote.projectId' : projectId
										}
										Ext.Ajax.request({
											url : __ctxPath+'/creditFlow/finance/saveFundsToPromote.do',
											method : 'POST',
											scope :this,
											success : function(response, request) {
												mgrid.getStore().reload();
												//e.record.commit();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试！');
											},
											params: args
										})
									}
								}).show();
							}
						}
		
								
					},
					renderer : function(data, metadata, record,
		                    rowIndex, columnIndex, store) {
		                metadata.attr = ' ext:qtip="' + data + '"';
		                return data;
		            }
				  },{
						header : '推介方式',
						dataIndex : 'promoteMonthod',
						sortable : true,
						editor : {
						       xtype:'textfield',
						       readOnly:this.isHidden
					       } 

					},
					{
						header : '已推介投资客户',
						dataIndex : 'investPersonName',
						sortable : true,
						editor : {
							xtype : "combo",
							triggerClass : 'x-form-search-trigger',
							listeners : {
								scope : this,
								focus : function(com) {
									var mgrid =this.gridPanel;
									var projectId=this.projectId
									var selectPerson=function(obj){
										mgrid.getSelectionModel().getSelected().data.investPersonName=obj.perName
										mgrid.getSelectionModel().getSelected().data.investPersonId=obj.perId
										mgrid.getView().refresh();
										var args = {
												'fundsToPromote.investPersonId' : obj.perId,
												'fundsToPromote.investPersonName' : obj.perName,
												'fundsToPromote.fundsToPromoteId' : mgrid.getSelectionModel().getSelected().data.fundsToPromoteId,
												'fundsToPromote.projectId' : projectId
											}
											Ext.Ajax.request({
											url : __ctxPath+'/creditFlow/finance/saveFundsToPromote.do',
											method : 'POST',
											scope :this,
											success : function(response, request) {
												mgrid.getStore().reload();
												//e.record.commit();
											},
											failure : function(response) {
												Ext.ux.Toast.msg('状态', '操作失败，请重试！');
											},
											params: args
										})
									}
									selectInvestPerson(selectPerson)
								}
							}
		
								
					},
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }
					},{
						header : '标题',
						dataIndex : 'title',
						sortable : true,
						editor : {
						       xtype:'textfield',
						       readOnly:this.isHidden
					       } ,
						renderer : function(data, metadata, record,
			                    rowIndex, columnIndex, store) {
			                metadata.attr = ' ext:qtip="' + data + '"';
			                return data;
			            }

					},{
						header : '推介内容',
						dataIndex : 'promoteContent',
						sortable : true

					},new Ext.ux.grid.RowActions({
						header : '推介附件',
						dataIndex : 'fujian',
						renderer : function(){
							return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
						}
					})],
					listeners : {
						scope : this,
						'cellclick' : function(grid,rowIndex,columnIndex,e){
							var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
							var markId=grid.getStore().getAt(rowIndex).get("fundsToPromoteId");
							 var talbeName="FundsToPromote.";
							 var mark=talbeName+markId;
							 
						     picViewer(mark,true);
						},
						afteredit : function(e) {
							if (e.originalValue != e.value) {
								var args ;
								if(e.field =='promoteDate'){
									args = {
											'fundsToPromote.promoteDate' : e.value,
											'fundsToPromote.fundsToPromoteId' : e.record.data['fundsToPromoteId'],
											'fundsToPromote.projectId' : this.projectId
										}
								}else if(e.field =='promoteMonthod'){
									args = {
											'fundsToPromote.promoteMonthod' : e.value,
											'fundsToPromote.fundsToPromoteId' : e.record.data['fundsToPromoteId'],
											'fundsToPromote.projectId' : this.projectId
										}
								}else if(e.field =='title'){
									args = {
											'fundsToPromote.title' : e.value,
											'fundsToPromote.fundsToPromoteId' : e.record.data['fundsToPromoteId'],
											'fundsToPromote.projectId' : this.projectId
										}
								}else if(e.field =='promoteContext'){
									args = {
											'fundsToPromote.promoteContext' : e.value,
											'fundsToPromote.fundsToPromoteId' : e.record.data['fundsToPromoteId'],
											'fundsToPromote.projectId' : this.projectId
										}
								}
							}
							if(e.field!='investPersonName'){
								Ext.Ajax.request({
									url : __ctxPath+'/creditFlow/finance/saveFundsToPromote.do',
									method : 'POST',
									scope :this,
									success : function(response, request) {
										this.gridPanel.getStore().reload();
										//e.record.commit();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									},
									params: args
								})
							}
						}
				    }
              });
                
				
					this.gridPanel.getStore().load();

		},
		createRs : function() {
			
			var gridadd = this.gridPanel;
			var storeadd = this.gridPanel.getStore();
			var keys = storeadd.fields.keys;
			var p = new Ext.data.Record();
			p.data = {};
			for ( var i = 0; i < keys.length; i++) {
				p.data[keys[i]] = null;
			}

			var count = storeadd.getCount() + 1;
			gridadd.stopEditing();
			storeadd.addSorted(p);
			gridadd.getView().refresh();
			gridadd.startEditing(0, 1);
		},
		deleteRs : function() {
			var griddel =this.gridPanel;
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
							if (row.data.fundsToPromoteId == null || row.data.fundsToPromoteId == '') {
								storedel.remove(row);
								griddel.getView().refresh();
							} else {
								deleteFun(
										__ctxPath + '/creditFlow/finance/multiDelFundsToPromote.do',
										{
											fundsToPromoteId :row.data.fundsToPromoteId
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
		editRs:function(){
			new FundsToPromoteForm({projectId:this.projectId,gridPanel:this.gridPanel}).show()
		}
	});
