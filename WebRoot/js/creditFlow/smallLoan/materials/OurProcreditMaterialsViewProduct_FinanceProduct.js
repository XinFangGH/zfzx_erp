/**
 * @author
 * @class OurProcreditMaterialsView
 * @extends Ext.Panel
 * @description [OurProcreditMaterials]管理
 * @company 智维软件
 * @createtime:
 */
OurProcreditMaterialsViewProduct_FinanceProduct = Ext.extend(Ext.Panel, {
			productId:null,
			isAllReadOnly:false,
			constructor : function(_cfg) {
				if(typeof(_cfg.productId)!="undefined"){
					this.productId=_cfg.productId;
				}
				if(typeof(_cfg.isAllReadOnly)!="undefined"){
					this.isAllReadOnly=_cfg.isAllReadOnly;
				}
				Ext.applyIf(this, _cfg);
				this.initUIComponents();
				OurProcreditMaterialsViewProduct_FinanceProduct.superclass.constructor.call(this, {
					region : 'center',
					layout : 'fit',
					items : [this.gridPanel]
				});
			},
			initUIComponents : function() {
				 
				var jsArr = [__ctxPath+'/js/creditFlow/smallLoan/materials/addProductOwnMaterialForm.js'];
				$ImportSimpleJs(jsArr, null);
				var	url = __ctxPath+ "/materials/listByProjectAndBusinessTypeOurProcreditMaterialsEnterprise.do?projectId="+this.projectId+"&businessType=FinanceProduct"                   
				var isHidden=this.isHidden_materials;
				this.topbar = new Ext.Toolbar({
					items : [{
							iconCls : 'btn-add',
							text : '选择产品融资材料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_create_dc')?false:true,
							hidden:this.hiddenAdd,
							handler : this.createRs
						}, '-',{
							iconCls : 'btn-edit',
							text : '新增产品融资材料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_remove_dc')?false:true,
							hidden:this.hiddenedit,
							handler : this.creditNewRs
						},'-',{
							iconCls : 'btn-del',
							text : '删除融资资料',
							xtype : 'button',
							scope : this,
//							hidden : isGranted('_remove_dc')?false:true,
							hidden:this.hiddenDel,
							handler : this.removeSelRs
						}]
				});

				this.gridPanel = new Ext.grid.EditorGridPanel({
					tbar :this.isAllReadOnly?null:this.topbar,
					autoHeight : true,
					clicksToEdit :1,
					stripeRows : true,
					enableDragDrop : false,
					viewConfig : {
						forceFit : true
					},
					sm : new Ext.grid.CheckboxSelectionModel({}),
					autoExpandColumn:'materialsName',
					store : new Ext.data.Store({
						proxy : new Ext.data.HttpProxy({
							url :url,
							method : "POST"
						}),
						reader : new Ext.data.JsonReader({
								fields : Ext.data.Record.create( [{
									name : 'materialsId'
								},{
									name : 'materialsName'
								},{
									name : 'productId'
								},{
									name : 'projectId'
								},{
									name : 'parentId'
								},{name:'xxnums'},{name:'datumNums'},{name:'remarks'},{name:'isfile'},{name:'gdremark'}
							]),
							root : 'result'
						})
					}),
					columns : [new Ext.grid.CheckboxSelectionModel({}),
						{
							header : 'materialsId',
							dataIndex : 'materialsId',
							hidden : true
						},{
							header : 'productId',
							dataIndex : 'productId',
							hidden : true
						},{
							header : 'projectId',
							dataIndex : 'projectId',
							hidden : true
						},{
							header : 'parentId',
							dataIndex : 'parentId',
							hidden : true
						}, {
							header : '融资资料名称',
							id:'materialsName',
							dataIndex : 'materialsName',
							editor : {
								xtype : 'textfield',
								readOnly : this.isReadOnly
							}
						},{
							header : '线上份数',	
							width : 60,
							dataIndex : 'datumNums'
						},{
							header : '上传',
							width : 44,
							dataIndex:'uploadOrDown',
							//hidden : this.isHidden_materials,
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
								/* if(this.ownerCt.isHidden_materials){
								 	return false;
								 }*/
								 var markId=grid.getStore().getAt(rowIndex).get("materialsId");
								 var talbeName="our_procredit_materials_mmproduct.";
								 var mark=talbeName+markId;
								 uploadfile("上传产品融资材料",mark,null,null,null,loadData,1);//uploadfile 方法多一个属性 1（可以写任意值）： 主要是为控制title是否显示 。不添加这个属性会正常显示title，添加这个属性则会让title显示
							}
							if("viewPic"==fieldName){  
								 var markId=grid.getStore().getAt(rowIndex).get("materialsId");
								 var talbeName="our_procredit_materials_mmproduct.";
								 var mark=talbeName+markId;
							     picViewer(mark,this.ownerCt.isHiddenEdit);
							}
						},
						afteredit : function(e) {/*
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
						*/}
					}
				});
				this.gridPanel.getStore().load();
//				this.gridPanel.addListener('cellclick', this.cellClick);
			},
			cellClick : function(grid,rowIndex,columnIndex,e){
				var materialsId = grid.getStore().getAt(rowIndex).get('materialsId');
				if(materialsId){
					
				}
			},
			createRs : function() {
				new OurProcreditMaterialsForm({
					gridPanel:this.gridPanel,
					productId:this.productId,
					operationTypeKey : "FinanceProduct"
				}).show();
				/*var newRecord = this.gridPanel.getStore().recordType;
				var newData = new newRecord({
					materialsId : '',
					materialsName : ''
				});
				this.gridPanel.stopEditing();
				this.gridPanel.getStore().insert(this.gridPanel.getStore().getCount(),newData);
				this.gridPanel.getView().refresh();
				this.gridPanel.getSelectionModel().selectRow((this.gridPanel.getStore().getCount() - 1));
				this.gridPanel.startEditing(0,1);*/
			},
			//新增产品特有的
			creditNewRs:function(){
				new addProductOwnMaterialForm({
					gridPanel:this.gridPanel,
					productId:this.productId
				}).show();
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
									"materialsName" : vRecords[i].data.materialsName,
									"parentId":vRecords[i].data.parentId==""?null:parseInt(vRecords[i].data.parentId),
									"projectId":vRecords[i].data.projectId==""?(vRecords[i].data.parentId==""?null:parseInt(vRecords[i].data.parentId)):parseInt(vRecords[i].data.projectId),
									"datumNums":vRecords[i].data.datumNums==""?null:parseInt(vRecords[i].data.datumNums)
								};
							} else {
								st = {
									"materialsId" : null,
									"materialsName" : vRecords[i].data.materialsName,
									"parentId":vRecords[i].data.parentId==""?null:parseInt(vRecords[i].data.parentId),
									"projectId":vRecords[i].data.projectId==""?null:parseInt(vRecords[i].data.projectId),
									"datumNums":vRecords[i].data.datumNums==""?null:parseInt(vRecords[i].data.datumNums)
								};
							}
							vDatas += Ext.util.JSON.encode(st) + '@';
					}
					vDatas = vDatas.substr(0, vDatas.length - 1);
				}
				return vDatas;
			}
		});
