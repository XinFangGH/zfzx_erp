/**
 * 分类管理
 * @class CreditDocumentManagerZW
 * @extends Ext.Panel
 */
CreditDocumentManager=Ext.extend(Ext.Panel,{
	constructor:function(config){
		if(config.businessType){
			this.businessType=config.businessType
		}
		this.isGranted = function(a){
			var b = "";
			/*if(this.businessType!=null){
				b=this.businessType;
			}*/
			if("LeaseFinance"==this.businessType){//如果全部合同权限都要修改，那么解注上面一句就好
				b="_"+this.businessType;
			}else if(this.businessType == "SmallLoan"){//不打算更改小贷的xml权限配置
				b="";
			}
			return isGranted(a+b);
		}
		this.isGranted("LeaseFinance");
		Ext.applyIf(this,config);
		this.initUIComponents();
		CreditDocumentManager.superclass.constructor.call(this,{
			id : 'CreditDocumentManager'+this.businessType,
			height:450,
			autoScroll:true,
			layout:'border',
			title:'合同模板管理',
			iconCls:"menu-onlinedoc",
			items:[
				this.leftPanel,
				this.centerPanel
			]
		});
	},
	initUIComponents:function(){
		var businessType = this.businessType;
		var uploadTEMPLATEID;
		
		this.panelWidth = 400;
		this.panelHeight = 490;
		this.myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		this.topBar =  new Ext.Toolbar({
			items:[{
				xtype : 'button',
				iconCls : 'btn-add',
				text : '新增模板类型',
				tooltip : '新增模板类型信息',
				hidden : isGranted('_create_cdm')?false:true,
				scope:this,
				handler : function() {
					//if(typeof(this.nodeObj) != "undefined"){
						this.addTemplate(this.nodeObj,this.documentTreePanel,this.businessType);
					/*}else
						Ext.ux.Toast.msg('状态','请选择业务品种或者文档类型');
						return ;*/
				}
			},{
				xtype : 'button',
				text : '修改',
				tooltip : '修改模板类型信息',
				iconCls : 'btn-edit',
				hidden : isGranted('_edit_cdm')?false:true,
				scope : this,
				handler : function(){
					if(typeof(this.nodeObj) != "undefined"){
						if(this.nodeObj.attributes.parentid == 0){
							Ext.ux.Toast.msg('状态','不能修改业务品种');
							return ;
						}else
							this.updateTemplate(this.nodeObj,this.documentTreePanel);
					}else{
						Ext.ux.Toast.msg('状态','请选择模板类型');
						return ;
					}
				}
			},{
				text : '删除',
				tooltip : '删除模板类型',
				iconCls : 'btn-del',
				hidden : this.isGranted('_remove_cdm')?false:true,
				scope : this,
				handler : function(){
					if(typeof(this.nodeObj) != "undefined"){
						if(this.nodeObj.attributes.parentid == 0){
							Ext.ux.Toast.msg('状态','不能删除业务品种');
							return ;
						}else if(this.nodeObj.attributes.isexist){
							Ext.ux.Toast.msg('状态', '请先删除模板');
							return ;
						}else if(this.nodeObj.attributes.onlymark !='' && this.nodeObj.attributes.templettype == 1){
							Ext.ux.Toast.msg('状态', '不能删除系统文档类型');
							return;
						}else
							this.deleteTemplate(this.nodeObj.attributes.id,this.documentTreePanel);
					}else
						Ext.ux.Toast.msg('状态','请选择模板类型');
						return ;
					
				}
			},{
				text : '查看系统要素',
				tooltip : '查看系统所有要素',
				iconCls : 'btn-readdocument',
				hidden : this.isGranted('_read_cdm')?false:true,
				scope : this,
				handler : function(){
					this.seeTempletSystem(businessType);
				}
			},{
				text : '导出系统要素',
				tooltip : '导出系统要素到Excel',
				iconCls : 'btn-xls',
				hidden : this.isGranted('_output_cdm')?false:true,
				scope : this,
				handler : function(){
					window.open(__ctxPath+'/contract/outputExcelProduceHelper.do?businessType='+this.businessType,'_blank');
				}
			}]
		});
		this.documentTreeLoad = new Ext.tree.TreeLoader({
			dataUrl : __ctxPath+'/contract/getByTypeDocumentTemplet.do?businessType='+this.businessType
		})
		this.documentTreePanel= new Ext.ux.tree.TreeGrid({
			tbar : this.topBar,
			 height : Ext.getBody().getViewSize().height-115,
			border : false,
			scope : this,
			loader : this.documentTreeLoad ,
			root : new Ext.tree.AsyncTreeNode({
				text :'跟节点',
				id : '0'
			}),
			columns : [
			{
				width : 220,
				header : '模板类型',
				dataIndex : 'text'
			},{
				width : 170,
				header : '唯一标识KEY',
				dataIndex : 'onlymark'
			}],
			listeners : {
				scope : this,
				click : function(node){
					var grid = this.centerPanel;
					if(node.leaf == 1){
						this.documentTreePanel.getTopToolbar().items.get(0).disable();
						this.documentTreePanel.getTopToolbar().items.get(1).enable();
						this.documentTreePanel.getTopToolbar().items.get(2).enable();
						this.centerPanel.getTopToolbar().enable();
						
					}else{
						this.documentTreePanel.getTopToolbar().items.get(0).enable();
						if(node.attributes.parentid == 0 && node.attributes.isChild == '' ){
							this.documentTreePanel.getTopToolbar().items.get(2).disable();
						}else{
							this.documentTreePanel.getTopToolbar().items.get(2).enable();
						}
						this.centerPanel.getTopToolbar().disable();
						
					}
					if(grid!=null){
	             		if(node.attributes.parentid==0){
	             			grid.setTitle('所有模板文档');
	             		}else{
	             			grid.setTitle(node.text+'-模板文档');
	             		}
	             		grid.getBottomToolbar().moveFirst();
	             		var store= grid.getStore();
	             		store.url = __ctxPath + "/contract/getTempletByParentIdDocumentTemplet.do";
	             		store.baseParams={parentid: node.id,leaf : node.leaf};
						store.reload();
	             	}
					this.nodeObj = node
					/*Ext.getCmp('selectid').value = node.id;
					Ext.getCmp('selectleaf').value = node.leaf;
					parentid = node.id;
					pcid = node.attributes.parentid ;
					selid = node.id;
					Ext.Ajax.request({
						url : __ctxPath+'/credit/document/getByIdDocumentTempletCall.do',
							method : 'POST',
							success : function(response, request){
								obj = Ext.util.JSON.decode(response.responseText);
								Ext.getCmp('selectexist').value = obj.data.isexist;
							},
							failure : function(response, request) {
								Ext.ux.Toast.msg('状态','系统错误');
							},
							params: { id: node.id}
					})*/
				}
			}
		});
		this.documentTreePanel.expandAll();
		this.leftPanel = new Ext.Panel({
			layout : 'anchor',
			region : "west",
			title : '合同模板类型',
			width:410,
			autoHeight:true,
			autoScroll : true,
			collapsible : true,
			split : true,
			border : false,
			loadMask : this.myMask,
			items : [this.documentTreePanel/*,{
				id : 'selectid',
				xtype : 'hidden',
				name : 'selectid',
				value : '0'
			},{
				id : 'selectleaf',
				xtype : 'hidden',
				name : 'selectleaf'
			},{
				id : 'selectexist',
				xtype : 'hidden',
				name : 'selectexist',
				value : false
			}*/,{
				id : 'id_isUploadOk',
				xtype : 'hidden'
			},{
				id : 'id_FileId',
				xtype : 'hidden'
			}]
		});
					
		this.store = new Ext.data.JsonStore({
					//url : __ctxPath + "/credit/document/getTempletIsDocument.do",
					url : __ctxPath + "/contract/getTempletByParentIdDocumentTemplet.do?businessType="+this.businessType+"&path="+__ctxPath,
					root : 'topics',
					totalProperty : 'totalProperty',
					remoteSort : true,
					fields : [{
								name : 'id',
								type : 'int'
							}, 'text', 
							'onlymark', 
							'parentid',
							'leaf',
							'isexist',
							'templettype',
							'fileid',
							'remarks',
							'textoo',
							'businessType',
							'handleFun',
							'checked',
							'orderNum',
							'isChild']
				});
	
		this.store.setDefaultSort('orderNum');
		// 加载数据
		this.store.load({
					scope : this,
					params : {
						start : 0,
						limit : 25
					}
				});

		var tbar=new Ext.Toolbar({
			items:[{
					text:'新增模板文档项',
					iconCls:'btn-add',
					scope:this,
					hidden : isGranted('_create_ddm')?false:true,
					handler:function(){
						if(typeof(this.nodeObj) != "undefined"){
							this.addTemplate(this.nodeObj,this.documentTreePanel,this.businessType);
						}else
							Ext.ux.Toast.msg('状态','请先在左侧列表选择一个模板类型再进行新增操作!');
							return 
					}
				},
				{
					text:'修改',
					iconCls:'btn-edit',
					hidden : isGranted('_edit_ddm')?false:true,
					scope:this,
					handler:function(){
						var rows = this.centerPanel.getSelectionModel().getSelections();
						if(rows.length==0){
							Ext.ux.Toast.msg('操作信息','请选择记录!');
							return;
						}else if(rows.length>1){
							Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
							return;
						}else{
							this.updateTemplate(rows[0].data.id,this.centerPanel);
						}
					}
				},
				{
					text:'删除',
					iconCls:'btn-del',
					hidden : isGranted('_remove_ddm')?false:true,
					scope:this,
					handler:function(){
						var rows = this.centerPanel.getSelectionModel().getSelections();
						if(rows.length==0){
							Ext.ux.Toast.msg('操作信息','请选择记录!');
							return;
						}else if(rows.length>1){
							Ext.ux.Toast.msg('操作信息','只能选择一条记录!');
							return;
						}else{
							this.deleteTemplate(rows[0].data.id,this.centerPanel);
						}
						
					}
				}
			]
		});
		
		this.centerPanel= new HT.EditorGridPanel({
			region:'center',
			title:'模板文档项管理',
			tbar:tbar,
			clicksToEdit: 1,
			//id:'centerPanelGrid',
	        store: this.store,
	        height:450,
	        viewConfig : {
						forceFit : true,
						autoFill : true
					},
			columns : [{
				header : 'ID',
				dataIndex : 'id',
				hidden : true
			}, {
				header : 'parentid',
				dataIndex : 'parentid',
				hidden : true
			},{
				header : 'leaf',
				dataIndex : 'leaf',
				hidden : true
			},{
				header : 'isexist',
				dataIndex : 'isexist',
				hidden : true
			},{
				header : 'fileid',
				dataIndex : 'fileid',
				hidden : true
			},{
				header : 'businessType',
				dataIndex : 'businessType',
				hidden : true
			},{
				header : 'isChild',
				dataIndex : 'isChild',
				hidden : true
			},{
				header : '文档名称',
				dataIndex : 'text',
				editor:new Ext.form.TextField()
			},{
				header:'唯一标识',
				dataIndex:'onlymark',
				editor:new Ext.form.TextField()
			},{
				header:'描述',
				dataIndex:'remarks',
				editor:new Ext.form.TextField()
			}, {
				header : '排序号',
				dataIndex : 'orderNum',
				editor:new Ext.form.TextField()
			},{
				header:'操作',
				dataIndex:'handleFun'
			}],
			listeners :{
				afteredit : function(e) {
					var args;
					var templateId = e.record.data['id'];
					if (e.originalValue != e.value) {
						if(e.field =='text'){
							args = {
								'documentTemplet.id' : templateId,
								'documentTemplet.text' : e.value
							}
						}
						if(e.field =='remarks'){
							args = {
								'documentTemplet.id' : templateId,
								'documentTemplet.remarks' : e.value
							}
						}
						if(e.field =='orderNum'){
							args = {
								'documentTemplet.id' : templateId,
								'documentTemplet.orderNum' : e.value
							}
						}
						Ext.Ajax.request({
								url : __ctxPath+'/contract/updateByIdDocumentTemplet.do',
								method : 'POST',
								success : function(response, request) {
									e.record.commit();
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
		var centerGridPanel = this.centerPanel;
		var leftGridPanel = this.leftPanel;
		
		uploadTemplate = function(id){
			uploadTEMPLATEID = id;
			//@HT_version3.0
			uploadfile('上传合同模板','cs_document_templet.'+id ,1,null ,null,callUploadFun);
		};
		//上传模板会掉修改fileId——
		function callUploadFun(){
			var isUploadOk = Ext.getCmp('id_isUploadOk').value;
			var fileId = Ext.getCmp('id_FileId').value;
			var objPanel = centerGridPanel;
			if(typeof(fileId) != "undefined"){
				var fileIdArr = fileId.split(',');
				fileId = fileIdArr[fileIdArr.length-2];
			}
			if(isUploadOk == "ok"){
				Ext.Ajax.request({
					url : __ctxPath+'/contract/sqlUpdateDocumentTemplet.do',
					method : 'POST',
					success : function(response, request){
						objPanel.getStore().reload();
						Ext.getCmp('id_isUploadOk').value = "" ;
						Ext.getCmp('id_FileId').value = "" ;
					},
					failure : function(response, request) {
						Ext.ux.Toast.msg('状态','系统错误');
					},
					params: { id: uploadTEMPLATEID ,fileId :fileId}
				})
			}else{
				return  ;
			}
		};
		//查看模板
		seeTemplet = function(id){
			var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
				interval:200,
		    	increment:15
			});
			Ext.Ajax.request({
				url : __ctxPath+'/contract/validationFileIsExistProduceHelper.do',
				method : 'POST',
				success : function(response, request){
					obj = Ext.util.JSON.decode(response.responseText);
					if(obj.exsit == false){
						pbar.getDialog().close();
						Ext.ux.Toast.msg('状态',obj.msg);
					}else{
						pbar.getDialog().close();
						window.location.href = __ctxPath+"/contract/seeTemplateOfHTMLContractHelper.do?tempId="+id;
					}
				},
				failure : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					Ext.ux.Toast.msg('状态',obj.msg);
				},
				params: { documentId: id}
			})
		};
		//查看模板要素
		seeTempletElement = function(id){
			var myMask = new Ext.LoadMask(Ext.getBody(), {
				msg : "加载数据中······,请稍后······"
			});
			var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
				interval:200,
		    	increment:15
			});
			Ext.Ajax.request({
				url : __ctxPath+'/contract/validateExistDocumentTemplet.do',
				method : 'POST',
				success : function(response, request){
					var elementCodeStore = new Ext.data.JsonStore( {
						url : __ctxPath+'/contract/findElementTwoProduceHelper.do',
						root : 'topics',
						fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
						listeners : {
							load : function(){
								elementWin.show();
								pbar.getDialog().close();
							}
						}
					});
					elementCodeStore.load({
						params : {
							documentId: id,
							businessType :businessType
						},
						callback :function(r,o,s){
							if(s == false){
								pbar.getDialog().close();
								Ext.ux.Toast.msg('状态','合同模板没有上传，请先上传模板');
								return ;
							}
						}
					});
					var elementCodeModel = new Ext.grid.ColumnModel([
						new Ext.grid.RowNumberer({header:'序'}),
						{
							header : "要素描述",
							width : 180,
							sortable : true,
							dataIndex : 'depict'
						},{
							header : "要素编码",
							width : 290,
							sortable : true,
							dataIndex : 'code'
						}
					])
					var elementCodePanel = new Ext.grid.GridPanel( {
						store : elementCodeStore,
						autoWidth : true,
						loadMask : true ,
						stripeRows : true ,
						loadMask : myMask,
						height:440,
						colModel : elementCodeModel,
						autoExpandColumn : 2,
						listeners : {
							'render' : function(grid){
						    	var store = grid.getStore();  
						    	var view = grid.getView();    
							    grid.tip = new Ext.ToolTip({
							    	dismissDelay : 10000,
							        target: view.mainBody,    
							        delegate: '.x-grid3-row', 
							        trackMouse: true,         
							        renderTo: document.body,  
							        listeners: {
							            beforeshow: function updateTipBody(tip) {
							                var rowIndex = view.findRowIndex(tip.triggerElement);
							                tip.body.dom.innerHTML = store.getAt(rowIndex).get('depict');
							            }
							        }
							    });
							}
						}
					});
					var elementWin = new Ext.Window({
						id : 'elementWin',
						layout : 'fit',
						title : '模板要素',
						width : 530,
						height : 400,
						minimizable : true,
						modal : true,
						items :[elementCodePanel]
					});
					
				},
				failure : function(response, request) {
					Ext.ux.Toast.msg('状态','加载错误');
				},
				params: {id: id}
			})
		};
		deleteTempletFile = function(id){
			Ext.MessageBox.confirm('确认删除', '是否确认删除模板文件？', function(btn) {
				if (btn == 'yes') {
					Ext.Ajax.request({
						url : __ctxPath+'/contract/deleteTempletFileDocumentTemplet.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							if(obj.success == true){
								Ext.ux.Toast.msg('状态','删除成功');
								centerGridPanel.getStore().reload();
							} else{
								Ext.ux.Toast.msg('状态' , '删除失败,请与管理员联系');
							}
						},
						failure : function(response) {
							Ext.ux.Toast.msg('状态', '操作失败，请重试');
						},
						params : {
							id : id
						}
					})
				}
			});
		
		};
	},//end of initUIComponents
	//新增合同类型
	addTemplate : function(node ,documentTreePanel,businessType){
		var thisCenterPanel = this.centerPanel;
		var addDocumentWin = new Ext.Window({
			id : 'addDocumentWin',
			layout : 'fit',
			title : '添加合同模板类型',
			width : 500,
			height : 230,
			minimizable : true,
			modal : true,
			items : [new Ext.form.FormPanel({
				id : 'addDocumentForm',
				labelAlign : 'right',
				buttonAlign : 'center',
				url : __ctxPath + '/contract/addDocumentTemplet.do',
				bodyStyle : 'padding:10px 25px 25px',
			    layout : 'column',
				frame : true,
				waitMsgTarget : true,
				monitorValid : true,
				autoWidth : true,
				autoHight : true ,
				items :[{
					columnWidth : 1,
					layout : 'form',
					id : 'addDocumentColumn',
					labelWidth : 100,
					defaults : {anchor : '100%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : '合同类型名称',
						name : 'documentTemplet.text',
						allowBlank : false,
						blankText : '合同类型名称为必填内容'
					},{
						xtype : 'numberfield',
						fieldLabel : '排序号',
						name : 'documentTemplet.orderNum',
						allowBlank : false,
						blankText : '排序号为必填内容'
					},{
						xtype : 'textfield',
						fieldLabel : '描述',
						name : 'documentTemplet.remarks'
					},{
						xtype : 'hidden',
						name : 'documentTemplet.businessType',
						value : businessType
					},{
						xtype : 'hidden',
						name : 'documentTemplet.parentid',
						value : typeof(node)!='undefined'?node.id:0
					}]
				}],
				buttons : [{
					text : '提交',
					formBind : true,
					handler : function() {
						addDocumentWin.findById('addDocumentForm').getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							success : function(form ,action) {
								obj = Ext.util.JSON.decode(action.response.responseText);
								if(obj.mark == false){
									Ext.ux.Toast.msg('状态',obj.msg);
								}else{
									Ext.ux.Toast.msg('状态', '添加成功!');
									if(documentTreePanel.getXType()=='hteditorgrid'){
										thisCenterPanel.getStore().reload();
									}else{
										documentTreePanel.getRootNode().reload();
										documentTreePanel.expandAll() ;
										thisCenterPanel.getStore().reload();
									}
									addDocumentWin.destroy();
									parentid = 0 ;
								}
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('状态', '添加失败,有重复项!');
							}
						})
					}
				}, {
					text : '取消',
					handler : function() {
						addDocumentWin.destroy();
					}
				}]
			})]
		});
		if(typeof(node)!='undefined'){
			if (node.attributes.parentid == '0') {
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容'
				});
				var hidLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : 0
				});
				var hidLeaf = {
					xtype : "combo",
					fieldLabel : "其下是否允许有子项",
					allowBlank : false,
					editable : false,
					mode : 'local',
					displayField : 'displayText',
					valueField : 'value',
					triggerAction : 'all',
					hiddenName : 'documentTemplet.leaf',
					store : new Ext.data.SimpleStore({
								fields : ['displayText', 'value'],
								data : [['允许', 0], ['不允许', 1]]
							})
				
				}
				var child = new Ext.form.Hidden({
					name : 'documentTemplet.isChild',
					value : 0
				});
				addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
				addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
				addDocumentWin.findById('addDocumentColumn').add(child);
				addDocumentWin.show();
			}
			if(this.nodeObj.attributes.onlymark != '' && this.nodeObj.attributes.leaf =='0' && node.attributes.parentid != '0'){
				var hidLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : 1
				})
				var child = new Ext.form.Hidden({
					name : 'documentTemplet.isChild',
					value : 1
				});
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容'
				});
				addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
				addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
				addDocumentWin.findById('addDocumentColumn').add(child);
				addDocumentWin.show();
			}
			if(this.nodeObj.attributes.onlymark == '' && this.nodeObj.attributes.parentid !='0'){
				var hidLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : 1
				})
				var child = new Ext.form.Hidden({
					name : 'documentTemplet.isChild',
					value : 2
				});
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容'
				});
				addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
				addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
				addDocumentWin.findById('addDocumentColumn').add(child);
				addDocumentWin.show();
			}
			
			if(this.nodeObj.attributes.onlymark != '' && this.nodeObj.attributes.parentid !='0' && this.nodeObj.attributes.leaf =='1'){
				var hidLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : 1
				})
				var child = new Ext.form.Hidden({
					name : 'documentTemplet.isChild',
					value : 2
				});
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容'
				});
				addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
				addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
				addDocumentWin.findById('addDocumentColumn').add(child);
				addDocumentWin.show();
			}
		}else{
			
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					//allowBlank : false,
					blankText : '唯一标识KEY为必填内容',
					value:'',
					hideLabel:true,
					hidden:true
				});
				var hidLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : 1
				});
				var hidLeaf = {
					xtype : "combo",
					fieldLabel : "其下是否允许有子项",
					allowBlank : false,
					editable : false,
					mode : 'local',
					displayField : 'displayText',
					valueField : 'value',
					triggerAction : 'all',
					hiddenName : 'documentTemplet.leaf',
					store : new Ext.data.SimpleStore({
								fields : ['displayText', 'value'],
								data : [['允许', 0], ['不允许', 1]]
							})
				
				}
				/*var child = new Ext.form.Hidden({
					name : 'documentTemplet.isChild',
					value : null
				});*/
				addDocumentWin.findById('addDocumentColumn').add(objFieldOnly);
				addDocumentWin.findById('addDocumentColumn').add(hidLeaf);
				//addDocumentWin.findById('addDocumentColumn').add(child);
				addDocumentWin.show();
		}
	},
	//修改合同类型
	updateTemplate : function(node ,documentTreePanel){
		var thisCenterPanel = this.centerPanel;
		Ext.Ajax.request({
			url : __ctxPath + '/contract/getByIdDocumentTemplet.do',
			method : 'POST',
			success : function(response, request){
				obj = Ext.util.JSON.decode(response.responseText);
				var updateDocumentWin = new Ext.Window({
					id : 'updateDocumentWin',
					layout : 'fit',
					title : '修改合同模板类型',
					width : 500,
					height : 200,
					minimizable : true,
					modal : true,
					items :[
						new Ext.form.FormPanel({
							id : 'updateDocumentForm',
							labelAlign : 'right',
							buttonAlign : 'center',
							url : __ctxPath + '/contract/updateDocumentTemplet.do',
							bodyStyle : 'padding:10px 25px 25px',
						    layout : 'column',
							frame : true,
							waitMsgTarget : true,
							monitorValid : true,
							autoWidth : true,
							autoHight : true ,
							items :[{
								columnWidth : 1,
								layout : 'form',
								id : 'updateDocumentColumn',
								labelWidth : 100,
								defaults : {anchor : '100%'},
								items :[{
									xtype : 'textfield',
									fieldLabel : '合同类型名称',
									name : 'documentTemplet.text',
									allowBlank : false,
									blankText : '合同类型名称为必填内容',
									value : obj.data.text
									},{
			            	 		xtype : 'numberfield',
									fieldLabel : '排序号',
									name : 'documentTemplet.orderNum',
									allowBlank : false,
									blankText : '排序号为必填内容',
									value : obj.data.orderNum
								},{
									xtype : 'textfield',
									fieldLabel : '描述',
									name : 'documentTemplet.remarks',
									value : obj.data.remarks
								},{
									xtype : 'hidden',
									name : 'documentTemplet.templettype',
									value : obj.data.templettype
								},{
									xtype : 'hidden',
									name : 'documentTemplet.businessType',
									value : obj.data.businessType
								},{
									xtype : 'hidden',
									name : 'documentTemplet.id',
									value : obj.data.id
								},{
									xtype : 'hidden',
									name : 'documentTemplet.parentid',
									value : obj.data.parentid
								},{
									xtype : 'hidden',
									name : 'documentTemplet.fileid',
									value : obj.data.fileid
								},{
									xtype : 'hidden',
									name : 'documentTemplet.isexist',
									value : obj.data.isexist
								},{
									xtype : 'hidden',
									name : 'documentTemplet.textoo',
									value : obj.data.textoo
								},{
									xtype : 'hidden',
									name : 'documentTemplet.isChild',
									value : obj.data.isChild
								},{
									xtype : 'hidden',
									name : 'documentTemplet.leaf',
									value : obj.data.leaf
								},{
									xtype : 'hidden',
									name : 'documentTemplet.handleFun',
									value : obj.data.handleFun
								}]
							}],
							buttons : [{
								text : '提交',
								formBind : true,
								handler : function() {
									updateDocumentWin.findById('updateDocumentForm').getForm().submit({
										method : 'POST',
										waitTitle : '连接',
										waitMsg : '消息发送中...',
										success : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
											if(objt.mark == false){
												Ext.ux.Toast.msg('状态',objt.msg)
											}else{
												Ext.ux.Toast.msg('状态', objt.msg);
												if(documentTreePanel.getXType()=='hteditorgrid'){
													thisCenterPanel.getStore().reload();
												}else{
													documentTreePanel.getRootNode().reload();
													documentTreePanel.expandAll() ;
												}
												
												updateDocumentWin.destroy();
												parentid = 0 ;
											}
										},
										failure : function(form ,action) {
											objt = Ext.util.JSON.decode(action.response.responseText);
											Ext.ux.Toast.msg('状态', objt.msg);
										}
									});
								}
	
							}, {
								text : '取消',
								handler : function() {
									updateDocumentWin.destroy();
								}
							}]
						})
					]
				});
				var objFieldOnly = new Ext.form.TextField({
					fieldLabel : '唯一标识KEY',
					name : 'documentTemplet.onlymark',
					allowBlank : false,
					blankText : '唯一标识KEY为必填内容',
					//readOnly : true,
					//cls : 'readOnlyClass',
					value : obj.data.onlymark
				})
				var objFieldLeaf = new Ext.form.Hidden({
					name : 'documentTemplet.leaf',
					value : obj.data.leaf
				})
				updateDocumentWin.findById('updateDocumentColumn').add(objFieldOnly);
				updateDocumentWin.findById('updateDocumentColumn').add(objFieldLeaf);
				updateDocumentWin.show();
					
					
			},
			failure : function(response) {
				Ext.ux.Toast.msg('状态', '操作失败，请重试');
			},
			params : {
				id : typeof(node.id) == 'undefined'?node:node.id
			}
		})
	},
	//删除合同类型
	deleteTemplate : function(node ,documentTreePanel){
		var thisCenterPanel = this.centerPanel;
		Ext.MessageBox.confirm('确认删除', '是否确认删除选中的记录', function(btn) {
			if (btn == 'yes') {
				Ext.Ajax.request({
					url : __ctxPath + '/contract/isHaveChildNodeDocumentTemplet.do',
					method : 'POST',
					success : function(response, request) {
						obj = Ext.util.JSON.decode(response.responseText);
						if(obj.mark==false){
							Ext.MessageBox.confirm('确认删除',obj.msg,function(btno){
								if(btno == 'yes'){
									Ext.Ajax.request({
										url : __ctxPath + '/contract/deleteRsDocumentTemplet.do',
										method : 'POST',
										success : function(response, request){
											obj = Ext.util.JSON.decode(response.responseText);
											Ext.ux.Toast.msg('状态', obj.msg);
											if(documentTreePanel.getXType()=='hteditorgrid'){
												thisCenterPanel.getStore().reload();
											}else{
												documentTreePanel.getRootNode().reload();
												documentTreePanel.expandAll() ;
											}
											
											parentid = 0 ;
										},
										failure : function(response, request) {
											Ext.ux.Toast.msg('状态','删除失败');
										},
										params: { id: node }
									})
								}
							})
						}else if(obj.mark==true){
							Ext.ux.Toast.msg('状态', obj.msg);
							if(documentTreePanel.getXType()=='hteditorgrid'){
								thisCenterPanel.getStore().reload();
							}else{
								documentTreePanel.getRootNode().reload();
								documentTreePanel.expandAll() ;
							}
							parentid = 0 ;
						}
					},
					failure : function(result, action) {
						var msg = Ext.decode(action.response.responseText);
						Ext.ux.Toast.msg('状态',msg);
					},
					params: { id: typeof(node.id)=='undefined'?node:node.id}
				});
			}
		});
	},
	
	//查看系统要素
	seeTempletSystem : function(value){
		var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
			interval:200,
	    	increment:15
		});
		Ext.Ajax.request({
			url : __ctxPath + '/contract/validateParamDocumentTemplet.do',
			method : 'POST',
			success : function(response, request){
				var elementCodeStore = new Ext.data.JsonStore( {
					url : __ctxPath + '/contract/findElementListProduceHelper.do',
					root : 'topics',
					fields : [ {name : 'code'},{name : 'value'}, {name : 'depict'}],
					baseParams :{businessType : value},
					listeners : {
						load : function(){
							elementWin.show();
							pbar.getDialog().close();
							
						}
					}
				});
				elementCodeStore.load();
				var elementCodeModel = new Ext.grid.ColumnModel([
					new Ext.grid.RowNumberer({header:'序'}),
					{
						header : "要素描述",
						width : 180,
						sortable : true,
						dataIndex : 'depict'
					},{
						header : "要素编码",
						width : 250,
						sortable : true,
						dataIndex : 'code'
					},{
						header : "复制",
						width : 40,
						sortable : true,
						dataIndex : 'code',
						renderer : function(value){
							return '<img title="快捷复制要素" src="'+__ctxPath+'/images/copy.png" onclick="doCopy(\''+value+'\')" />';
						}
					}
				])
				var elementCodePanel = new Ext.grid.GridPanel( {
					store : elementCodeStore,
					autoWidth : true,
					loadMask : true ,
					stripeRows : true ,
					height:440,
					colModel : elementCodeModel,
					autoExpandColumn : 2,
					listeners : {
						'rowdblclick' : function(grid,index,e){
							var eleStore = grid.getStore().data.items;
							var eleWin = new Ext.Window({
								id : 'eleWin',
								layout : 'fit',
								title : '要素信息',
								width : 530,
								height : 150,
								minimizable : true,
								modal : true,
								items :[
									new Ext.form.FormPanel({
										labelAlign : 'right',
										bodyStyle : 'padding:10px 25px 25px',
									    layout : 'column',
										frame : true,
										waitMsgTarget : true,
										autoWidth : true,
										autoHight : true ,
										items:[{
											columnWidth : 1,
											layout : 'form',
											labelWidth : 60,
											defaults : {anchor : '100%'},
											items :[{
												xtype : 'label',
												id : 'remarks',
												fieldLabel : '要素描述',
												html : eleStore[index].data.depict
											},{
												xtype : 'label',
												id : 'code',
												fieldLabel : '要素编码',
												html : eleStore[index].data.code
											}]
										}]
									})
								]
							}).show();
						},
						'render' : function(grid){
						    var store = grid.getStore();  
						    var view = grid.getView();    
						    grid.tip = new Ext.ToolTip({
						    	dismissDelay : 10000,
						        target: view.mainBody,    
						        delegate: '.x-grid3-row', 
						        trackMouse: true,         
						        renderTo: document.body,  
						        listeners: {
						            beforeshow: function updateTipBody(tip) {
						                var rowIndex = view.findRowIndex(tip.triggerElement);
						                tip.body.dom.innerHTML = store.getAt(rowIndex).get('depict');
						            }
						        }
						    });
						},
						'cellclick' : function(grid, rowIndex, columnIndex, e){
							var record = grid.getStore().getAt(rowIndex);
							var fieldName = grid.getColumnModel().getDataIndex(columnIndex);
							cellValue = record.get(fieldName);
						}
					}
				});
				var elementWin = new Ext.Window({
					id : 'elementWin',
					layout : 'fit',
					title : '系统要素',
					width : 530,
					height : 400,
					minimizable : true,
					modal : true,
					items :[elementCodePanel]
				});
			},
			failure : function(response, request) {
				Ext.ux.Toast.msg('状态','加载错误');
			},
			params: { businessType : value}
		})
	}
	
});
//复制到剪切板
function doCopy(cellValue){
	if(cellValue == "" || typeof(cellValue) == "undefined"){
		Ext.ux.Toast.msg('状态','请选择一条数据再复制');
	}else{
		if(document.selection.createRange().text!=null && document.selection.createRange().text!=''&&document.selection.createRange().text!='null'){
	  		clipboardData.setData("Text",document.selection.createRange().text);
	  		Ext.ux.Toast.msg('状态','复制成功');
		}else{
			clipboardData.setData("Text",cellValue);
			Ext.ux.Toast.msg('状态','复制成功');
		}
	}
}