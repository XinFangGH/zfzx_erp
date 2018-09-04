/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditMaterialsView = Ext.extend(Ext.Panel, {
			productId:null,
			projectId:null,
			isAllReadOnly:false,
			isReadOnly:false,
			isFlow:true,
			isHiddenGd:true,
			constructor : function(_cfg) {
				if(typeof(_cfg.productId)!="undefined"){
					this.productId=_cfg.productId;
				}
				if(typeof(_cfg.isAllReadOnly)!="undefined"){
					this.isAllReadOnly=_cfg.isAllReadOnly;
				}
				if(typeof(_cfg.projectId)!="undefined"){
					this.projectId=_cfg.projectId;
				}
				if (typeof (_cfg.isFlow) != "undefined") {
					this.isFlow = _cfg.isFlow;
				}
				if (typeof (_cfg.isReadOnly) != "undefined") {
					this.isReadOnly = _cfg.isReadOnly;
				}
				if(typeof(_cfg.isHiddenGd)!="undefined"){
					this.isHiddenGd=_cfg.isHiddenGd;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				OurProcreditMaterialsView.superclass.constructor.call(this, {
					region : 'center',
					layout : 'fit',
					items : [this.gridPanel]
				});
			},
			initUIComponents : function() {
				var tempUrl=null;
				if(this.projectId){
					tempUrl=__ctxPath + "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?projectId="+this.projectId
				}else{
					tempUrl=__ctxPath + "/materials/listByProductIdOurProcreditMaterialsEnterprise.do?productId="+this.productId
				}
				var isHidden=this.isHidden_materials;
				this.topbar =new Ext.Toolbar({
					items : [{
							iconCls : 'btn-add',
							text : '添加',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_create_dc')?false:true,
							hidden:this.hiddenAdd,
							handler : this.createRs
						}, new Ext.Toolbar.Separator({
							hidden : this.hiddenAdd
						}),{
							iconCls : 'btn-del',
							text : '删除',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_remove_dc')?false:true,
							hidden:this.hiddenDel,
							handler : this.removeSelRs
						}]
				});
				
				var checkColumn = new Ext.grid.CheckColumn({
					hidden:this.isHiddenGd,
					header : '是否已归档',
					editable:true,
					dataIndex : 'isfile',
					width : 70
				});
				
				this.gridPanel = new Ext.grid.EditorGridPanel({
					tbar :this.topbar,
					autoHeight : true,
					clicksToEdit :1,
					stripeRows : true,
					enableDragDrop : false,
					plugins : [checkColumn],
					viewConfig : {
						forceFit : true
					},
					sm : this.isAllReadOnly?null:new Ext.grid.CheckboxSelectionModel({}),
//					autoExpandColumn:'remarks',
					store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
							url : tempUrl,
							method : "POST"
						}),
						reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [{
									name : 'materialsId'
								},{
									name : 'materialsName'
								},{name:'xxnums'},{name:'datumNums'},{name:'remarks'},{name:'isfile'},{name:'gdremark'}
							]),
							root : 'result'
						})
					}),
					columns : [new Ext.grid.CheckboxSelectionModel({hidden:this.isAllReadOnly}),new Ext.grid.RowNumberer(),
						{
							header : 'materialsId',
							dataIndex : 'materialsId',
							hidden : true
						}, {
							header : '贷款资料名称',
							dataIndex : 'materialsName',
							editor : {
								xtype : 'textfield',
//								readOnly : this.isHidden_materials? true:false
								readOnly:this.isReadOnly
							}
						},{
							header : '线下份数',	
							width : 60,
							dataIndex : 'xxnums',
							editor: {
							   xtype:'numberfield',
							   readOnly : this.isHidden_materials? true:false
							}
						},{
							header : '线上份数',	
							width : 60,
							dataIndex : 'datumNums'
						},{
							header : '上传',
							width : 44,
							dataIndex:'uploadOrDown',
							hidden : this.isHidden_materials,
							renderer:function(){
				                if(isHidden){
				                      return "<img src='"+__ctxPath+"/images/download-start.png'/>";
				                }else{
				                      return "<img src='"+__ctxPath+"/images/upload-start.png'/>";
				                }
		           			 }
						},{
							header : '预览',
							width : 60,
							dataIndex:'viewPic',
							renderer:function(){
				                return "<img src='"+__ctxPath+"/images/btn/read_document.png'>";
		           			 }
						},{
							header : '备注',	
							id:'remarks',
							dataIndex : 'remarks',
							align : "center",
							width : this.isHiddenAffrim?400:100,
							editor :this.isHidden_materials?null:new Ext.form.TextField({
								selectOnFocus: true
							})
						},checkColumn,{
							header : '归档备注',	
							align : "center",
							dataIndex : 'gdremark',
							hidden : this.isHiddenGd,
							editor : new Ext.form.TextField({})
						}
					],
					listeners : {
						'cellclick' : function(grid,rowIndex,columnIndex,e){
							 var record = grid.getStore().getAt(rowIndex);   //Get the Record
                             var fieldName = grid.getColumnModel().getDataIndex(columnIndex); //Get field name
                             var loadData=function(size){     
								 var oldSize=grid.getStore().getAt(rowIndex).get("datumNums");
								 grid.getStore().getAt(rowIndex).set("datumNums",size);
								 var e1={record:grid.getStore().getAt(rowIndex),field:'datumNums','value':size,'originalValue':oldSize}
								 grid.fireEvent("afteredit",e1);
								 return false;
							}
							if("uploadOrDown"==fieldName){
								 if(this.ownerCt.isHidden_materials){
								 	return false;
								 }
								 var markId=grid.getStore().getAt(rowIndex).get("materialsId");
								 var talbeName="our_procredit_materials_enterprise.";
								 var mark=talbeName+markId;
								 uploadfile("上传贷款资料",mark,null,null,null,loadData,1);//uploadfile 方法多一个属性 1（可以写任意值）： 主要是为控制title是否显示 。不添加这个属性会正常显示title，添加这个属性则会让title显示
							}
							if("viewPic"==fieldName){  
								 var markId=grid.getStore().getAt(rowIndex).get("materialsId");
								 var talbeName="our_procredit_materials_enterprise.";
								 var mark=talbeName+markId;
							     picViewer(mark,this.ownerCt.isHiddenEdit);
							}
						},
						afteredit : function(e) {
							var  gridObj=this;
							var args="";
							var value = e.value;
							var id = e.record.data['materialsId'];
							if (e.originalValue != e.value) {//编辑行数据发生改变
								if(e.field == 'remarks') {
									args = {
										'ourProcreditMaterialsEnterprise.remarks': value,
										'ourProcreditMaterialsEnterprise.materialsId': id,
										'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
									};
								}
							}
							if(e.field == 'datumNums') {
								args = {
									'ourProcreditMaterialsEnterprise.datumNums': value,
									'ourProcreditMaterialsEnterprise.materialsId': id,
									'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
								};
							}
							if(e.field == 'xxnums') {
								args = {
									'ourProcreditMaterialsEnterprise.xxnums': value,
									'ourProcreditMaterialsEnterprise.materialsId': id,
									'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
								};
							}
							if(e.field == 'gdremark') {
								args = {
									'ourProcreditMaterialsEnterprise.gdremark': value,
									'ourProcreditMaterialsEnterprise.materialsId': id,
									'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
								};
							}
							if(e.field == 'isfile') {
								args = {
									'ourProcreditMaterialsEnterprise.isfile': value,
									'ourProcreditMaterialsEnterprise.materialsId': id,
									'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
								};
							}
							if(e.field == 'materialsName') {
								if(null==id && ""==id){
									args = {
										'ourProcreditMaterialsEnterprise.materialsName': value,
										'ourProcreditMaterialsEnterprise.materialsId':null,
										'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId
									};
								}else{
									args = {
										'ourProcreditMaterialsEnterprise.materialsName': value,
										'ourProcreditMaterialsEnterprise.materialsId': id,
										'ourProcreditMaterialsEnterprise.projectId':this.ownerCt.projectId	
									};
								}
							}
							Ext.Ajax.request({
								url : __ctxPath+'/materials/save2OurProcreditMaterialsEnterprise.do',
								method : 'POST',
								scope : this,
								success : function(response, request) {
									 e.record.commit();
									 gridObj.store.reload();
								},
								failure : function(response) {
									Ext.Msg.alert('状态', '操作失败，请重试');
								},
								params : args
							})
						}
					}
				});
				this.gridPanel.getStore().load();
			},
			cellClick : function(grid,rowIndex,columnIndex,e){
				var materialsId = grid.getStore().getAt(rowIndex).get('materialsId');
				if(materialsId){
					
				}
			},
			createRs : function() {
				var newRecord = this.gridPanel.getStore().recordType;
				var newData = new newRecord({
					materialsId : '',
					materialsName : ''
				});
				this.gridPanel.stopEditing();
				this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
				this.gridPanel.getView().refresh();
				this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
				this.gridPanel.startEditing(0,1);
			},
			removeRs : function(id) {
				$postDel({
					url : __ctxPath+ '/materials/multiDelOurProcreditMaterialsEnterprise.do',
					ids : id,
					grid : this.gridPanel
				});
			},
			removeSelRs : function() {
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录');
				} else {
					if(selected.data.materialsId){
						 $delGridRs({
							url : __ctxPath+ '/materials/multiDelOurProcreditMaterialsEnterprise.do',
							grid : this.gridPanel,
							idName : 'materialsId'
						});
					}else{
						grid.store.remove(selected);
					}
				}
			   
			},
			getGridDate : function() {
				var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount()); // 得到修改的数据（记录对象）
				var vCount = vRecords.length; // 得到记录长度
				var vDatas = '';
				if (vCount > 0) {
					// begin 将记录对象转换为字符串（json格式的字符串）
					for (var i = 0; i < vCount; i++) {
							if (vRecords[i].data.materialsId != null && vRecords[i].data.materialsId != "") {
								st = {
									"materialsId" : vRecords[i].data.materialsId,
									"materialsName" : vRecords[i].data.materialsName
								};
							} else {
								st = {
									"materialsId" : null,
									"materialsName" : vRecords[i].data.materialsName
								};
							}
							vDatas += Ext.util.JSON.encode(st) + '@';
					}
					vDatas = vDatas.substr(0, vDatas.length - 1);
				}
				return vDatas;
			},
			validate:function(){
				var bool = true;
				this.gridPanel.getStore().each(function(record){
					if(record.data.datumNums==null||typeof(record.data.datumNums)=="undefined"||""==record.data.datumNums){
						bool = false;
					}
				},this);
				return bool;
			}
		});
