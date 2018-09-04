var seeContractStore ;
function seeContractListWin(projId){
	var piKey=projId;
//	var isdirectorpromisedic = 1145 ;  var isfanassurematerialdic = 1118 ;
//	var anchor = '100%';
//	Ext.Ajax.request({
//		url : 'isValidUser.do',
//		method : 'POST',
//		success : function(response, request) {
//			obj = Ext.util.JSON.decode(response.responseText);
//			if(obj.mark != true){
//				var funDownload = function(val,m,r){
//					if(val != 0){
//						//下载，删除
//						return '<a style="color: blue" onclick="seeLookContractByHTML('+r.get('id')+')">下载</a>'
//					}else{
//						//上传
//						return '' ;
//					}
//				};		
//			}else{
//				var funDownload = function(val,m,r){
//					if(val != 0){
//						//下载，删除
//							return '<a style="color: blue" onclick="seeLookContractByHTML('+r.get('id')+')">下载</a>  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a style="color: blue" onclick="deleteContractFile_('+r.get('id')+')">删除</a>'
//						}else{
//							return '<a style="color: red" onclick="uploadContractWinSee('+r.get('id')+')">上传</a>' 
//						//上传
//							/*var vup = r.get('contractType') ;
//							var ul = r.get('isUpload') ;
//							if((vup == "" || vup == null) && (ul == false || ul == null)){
//								return '<a style="color: red" onclick="('+r.get('id')+')">新增合同</a>' ;
//							}else
//								return '<a style="color: red" onclick="uploadContractWinSee('+r.get('id')+')">上传</a>' ;*/
//						}
//				};
//			}
//			//
//				var button_see = new CS.button.SButton({
//					text :'查看合同签署信息' ,
//					handler : function() {
//						var selected = seeContractGrid.getSelectionModel().getSelected();
//						if (null == selected) {
//							Ext.ux.Toast.msg('状态', '请选择一条记录!');
//						} else {
//							var id = selected.get('id');
//							var mortgageId = selected.get('mortgageId');
//							if(mortgageId != 0){
//								seeFanContractShow(id);
//							}else{
//								seeContractShow(id);
//							}
//						}
//					}
//				});
//				var button_update = new CS.button.UButton({
//					text : '编辑查看反担保材料',
//					handler : function() {
//						var selected = seeContractGrid.getSelectionModel().getSelected();
//						if (null == selected) {
//							Ext.ux.Toast.msg('状态', '请选择一条反担保合同记录!');
//						} else {
//							var id = selected.get('id');
//							var mortgageId = selected.get('mortgageId');
//							if(mortgageId != 0){
//								Ext.Ajax.request({
//									url : 'isValidUser.do',
//									method : 'POST',
//									success : function(response, request) {
//										obj = Ext.util.JSON.decode(response.responseText);
//										if(obj.mark != true){
//											//不能编辑，只能查看 
//											seefaneditMaterial(mortgageId);
//										}else{
//											//可以编辑
//											faneditMaterial(mortgageId ,projId);
//										}
//									},
//									failure : function(response) {
//										Ext.ux.Toast.msg('状态', '操作失败，请重试');
//									}
//								})
//							}else{
//								Ext.ux.Toast.msg('状态', '请选择一条反担保合同记录!');
//							}
//						}
//					}
//				});
//				
//				seeContractStore = new Ext.data.JsonStore( {
//					url : 'seeContractList.do',
//					totalProperty : 'totalProperty',
//					root : 'topics',
//					fields : [ {name : 'id'},{name : 'url'}, {name : 'contractNumber'}, {name : 'contractName'}, {name : 'contractType'},{name : 'draftDate'}, {name : 'draftName'},
//						{name : 'isLegalCheck'},{name :'legalCheckDate'},{name : 'legalCheckName'},{name : 'issign'},{name :'mortgageId'},{name : 'projId'},{name : 'isUpload'},
//						{name : 'signDate'},{name :'signName'},{name :'isAgreeLetter'},{name : 'agreeLetterDate'},{name :'agreeLetterName'},{name : 'templettype'},
//						{name : 'isSeal'},{name : 'sealDate'},{name : 'sealName'},{name : 'text'},{name : 'supplyClause'},{name : 'ownershipPerson'},{name : 'ownershipPersonType'},{name : 'needmateriallistof'},{name : 'receivemateriallistof'},{name : 'lackmaterialrecord'},
//						{name :'isfanassurematerial'},{name :'isdirectorpromise'},{name :'promisebpdeadline'},{name : 'assureText'}, {name : 'assureTypeText'}, {name : 'reverseAssureText'}
//					],
//					baseParams : {
//						projid : projId
//					}
//				});
//				var seeContractModel = new Ext.grid.ColumnModel([
//					new Ext.grid.RowNumberer(),
//					{
//						header : "合同名称",
//						width : 220,
//						sortable : true,
//						dataIndex : 'contractName'
//					},{
//						header : "操作",
//						width : 130,
//						sortable : true ,
//						dataIndex : 'url' ,
//						renderer : funDownload
//					},{
//						header : "合同编号",
//						width : 160,
//						sortable : true,
//						dataIndex : 'contractNumber'
//					},{
//						header : "合同类型",
//						width : 220,
//						sortable : true,
//						dataIndex : 'text'
//					}, {
//						header : "起草人",
//						width : 80,
//						sortable : true,
//						dataIndex : 'draftName'
//					}, {
//						header : "起草时间",
//						width : 80,
//						sortable : true,
//						dataIndex : 'draftDate'
//					},{
//						header : "确认修改 人",
//						width : 100,
//						sortable : true,
//						dataIndex : 'legalCheckName'
//					},{
//						header : "确认修改时间",
//						width : 100,
//						sortable : true,
//						dataIndex : 'legalCheckDate'
//					},{
//						header : "合同签署人",
//						width : 100,
//						sortable : true,
//						dataIndex : 'signName'
//					},{
//						header : "合同签署时间",
//						width : 100,
//						sortable : true,
//						dataIndex : 'signDate'
//					},{
//						header : "盖章人",
//						width : 100,
//						sortable : true,
//						dataIndex : 'sealName'
//					},{
//						header : "盖章日期",
//						width : 100,
//						sortable : true,
//						dataIndex : 'sealDate'
//					}]
//				);
//				var seeContractGrid = new Ext.grid.GridPanel( {
//					pageSize : 10,
//					store : seeContractStore ,
//					autoWidth : true,
//					autoHeight: false,
//					colModel : seeContractModel,
//					loadMask : new Ext.LoadMask(Ext.getBody(), {
//						msg : "加载数据中······,请稍后······"         
//					}),
//					tbar : [button_see,button_update],
//					listeners : {
//						'rowdblclick' : function(grid,index,e){
//							var id = grid.getSelectionModel().getSelected().get('id');
//							var mortgageId = grid.getSelectionModel().getSelected().get('mortgageId');
//							if(mortgageId != 0){
//								seeFanContractShow(id);
//							}else{
//								seeContractShow(id);
//							}
//						},
//						'rowcontextmenu' :function(g,r,e){
//							e.stopEvent() ;
//							g.getSelectionModel().selectRow(r) ;
//							var id = g.getStore().getAt(r).get('id');
//							var mortgageId = g.getStore().getAt(r).get('mortgageId');
//							if(mortgageId != 0){
//								var menu = new Ext.menu.Menu({
//									items : [{
//										iconCls : 'addIcon',
//										text : '查看编辑担保材料',
//										handler : function() {
//											faneditMaterial(mortgageId ,projId);
//										}
//									}]
//								})
//								menu.showAt(e.getXY());
//							}
//						}
//					}
//				});
//				seeContractStore.load({
//					params : {
//						start : 0,
//						limit : 10
//					}
//				});
//				var seeContractWin = new Ext.Window({
//					title : '合同相关信息',
//					width :(screen.width-180)*0.5 + 150,
//					height : 400,
//					buttonAlign : 'center',
//					layout : 'fit',
//					modal : true,
//					constrainHeader : true ,
//					border : false,
//					collapsible : true, 
//					closable : true,
//					resizable : true,
//					autoScroll : true ,
//					plain : true,
//					items :[seeContractGrid]
//				}).show();
//				var seeContractShow = function(id){
//					Ext.Ajax.request({
//						url : 'getByIdContractAction.do',
//						method : 'POST',
//						success : function(response,request) {
//							var obj = Ext.util.JSON.decode(response.responseText);
//							var conData = obj.data ;
//							var seeConForm = new Ext.form.FormPanel({
//								id :'seeConForm',
//								bodyStyle:'padding:10px',
//								autoScroll : true ,
//								labelAlign : 'right',
//								buttonAlign : 'center',
//								layout:'column',
//								width : 488,
//								height : 308,
//								frame : true ,
//								items: [{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//						            	xtype : 'textfield',
//										fieldLabel : '合同名称',
//										value : conData.contractName == null ? "" : conData.contractName ,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '合同编号',
//										value : conData.contractNumber,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:1,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '合同类型',
//										value : conData.text,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '起草人',
//										value : conData.draftName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '确认修改人 ',
//										value : conData.legalCheckName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '签署人 ',
//										value : conData.signName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '审签人 ',
//										value : conData.agreeLetterName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '盖章人 ',
//										value : conData.sealName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '起草时间',
//										value : conData.draftDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '确认修改时间',
//										value : conData.legalCheckDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '签署日期',
//										value : conData.signDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '审签日期',
//										value : conData.agreeLetterDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '盖章日期',
//										value : conData.sealDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								}]
//							})
//							var window_see = new Ext.Window({
//								title: '查看合同信息',
//								layout : 'fit',
//								closable : true,
//								resizable : true,
//								constrainHeader : true ,
//								collapsible : true, 
//								plain : true,
//								border : false,
//								modal : true,
//								width :(screen.width-180)*0.5 + 100,
//								height : 300,
//								autoScroll : true ,
//								bodyStyle:'overflowX:hidden',
//								buttonAlign : 'right',
//								items :[seeConForm]
//							}).show();			
//						},
//						failure : function(response) {
//								Ext.ux.Toast.msg('状态','操作失败，请重试');	
//						},
//						params: { id: id }
//					});	
//				}
//				var seeFanContractShow = function(id){
//					Ext.Ajax.request({
//						url : 'getByIdContractAction.do',
//						method : 'POST',
//						success : function(response,request) {
//							var obj = Ext.util.JSON.decode(response.responseText);
//							var conData = obj.data ;
//							var seeConForm = new Ext.form.FormPanel({
//								id :'seeConForm',
//								bodyStyle:'padding:10px',
//								autoScroll : true ,
//								labelAlign : 'right',
//								buttonAlign : 'center',
//								layout:'column',
//								width : 488,
//								height : 308,
//								frame : true ,
//								items: [{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//						            	xtype : 'textfield',
//										fieldLabel : '合同名称',
//										value : conData.contractName == null ? "" : conData.contractName ,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '合同编号',
//										value : conData.contractNumber,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:1,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '合同类型',
//										value : conData.text,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '起草人',
//										value : conData.draftName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '确认修改人 ',
//										value : conData.legalCheckName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '签署人 ',
//										value : conData.signName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '审签人 ',
//										value : conData.agreeLetterName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '盖章人 ',
//										value : conData.sealName,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '反担保物名称 ',
//										value : conData.assureText,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '反担保物类型 ',
//										value : conData.reverseAssureText,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '所有权人 ',
//										value : conData.ownershipPerson,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								},{
//									columnWidth:.5,
//							        layout: 'form',
//							        labelWidth : 90,
//									defaults : {anchor : anchor},
//									items :[{
//										xtype : 'textfield',
//										fieldLabel : '起草时间',
//										value : conData.draftDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '确认修改时间',
//										value : conData.legalCheckDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '签署日期',
//										value : conData.signDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '审签日期',
//										value : conData.agreeLetterDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '盖章日期',
//										value : conData.sealDate,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '担保类型',
//										value : conData.assureTypeText,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									},{
//										xtype : 'textfield',
//										fieldLabel : '所有权人类型',
//										value : conData.ownershipPersonType,
//										readOnly  : true,
//										cls : 'readOnlyClass'
//									}]
//								}]
//							})
//							var window_see = new Ext.Window({
//								title: '查看合同信息',
//								layout : 'fit',
//								closable : true,
//								resizable : true,
//								constrainHeader : true ,
//								collapsible : true, 
//								plain : true,
//								border : false,
//								modal : true,
//								width :(screen.width-180)*0.5 + 100,
//								height : 340,
//								autoScroll : true ,
//								bodyStyle:'overflowX:hidden',
//								buttonAlign : 'right',
//								items :[seeConForm]
//							}).show();
//						},
//						failure : function(response) {
//								Ext.ux.Toast.msg('状态','操作失败，请重试');	
//						},
//						params: { id: id }
//					});	
//				}
//				
//			var faneditMaterial = function(id ,piKey){
//				Ext.Ajax.request({   
//			    	url: 'getByIdMortgage.do',   
//			   	 	method:'post',   
//			    	params:{id:id},
//			    	success: function(response, option) {
//			    		var obj = Ext.decode(response.responseText);
//			    		var feneditMaterialWin = new Ext.Window({
//							title : '查看编辑担保材料信息',
//							layout : 'fit',
//							width : 700,
//							height : 480,
//							closable : true,
//							constrainHeader : true ,
//							resizable : true,
//							plain : false,
//							bodyBorder : false,
//							border : false,
//							modal : true,
//							bodyStyle:'overflowX:hidden',
//							buttonAlign : 'right',
//							items :[
//								new Ext.form.FormPanel({
//									id : 'faneditMaterialFrom',
//									url : 'updateMortgage.do',
//									monitorValid : true,
//									labelAlign : 'right',
//									buttonAlign : 'center',
//									layout : 'column',
//									frame : true ,
//									items : [{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 110,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'textfield',
//											fieldLabel : '担保物名称',
//											value : obj.data.mortgagename,
//											readOnly  : true,
//											cls : 'readOnlyClass'
//										}]
//									},{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 110,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'textarea',
//											height : 150,
//											fieldLabel : '要求材料清单',
//											name: 'needDatumList',
//											value : obj.data.needDatumList
//										},{
//											xtype : 'textarea',
//											height : 80,
//											fieldLabel : '缺少材料过程记录',
//											name: 'lessDatumRecord',
//											value : obj.data.lessDatumRecord
//										},{
//											xtype : 'textarea',
//											height : 80,
//											fieldLabel : '收到材料清单',
//											name: 'receiveDatumList',
//											value : obj.data.receiveDatumList
//										}]
//									},{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 120,
//										defaults : {anchor : '100%'},
//										items : [{
//							            	xtype : 'combo',
//											fieldLabel : '反担保材料是否完整',
//											hiddenName : 'isfanassurematerial',
//											mode : 'remote',
//											editable : false,
//											store : new Ext.data.JsonStore({
//												url : 'queryDic.do',
//												root : 'topics',
//												fields : [{
//													name : 'id'
//												}, {
//													name : 'typeId'
//												}, {
//													name : 'sortorder'
//												}, {
//													name : 'value'
//												}],
//												baseParams :{id : isfanassurematerialdic}
//											}),
//											displayField : 'value',
//											valueField : 'id',
//											triggerAction : 'all' ,
//											value : obj.data.isAuditingPass,
//											valueNotFoundText : obj.data.isAuditingPassValue
//										}]
//									},{
//										columnWidth : .5,
//										layout : 'form',
//										labelWidth : 100,
//										defaults : {anchor : '100%'},
//										items :[{
//							            	xtype: 'radiogroup',
//							            	width: 80,
//							                fieldLabel: '业务经理承诺函',
//							                items: [
//							                    {boxLabel: '有', name: 'isdirectorpromise', inputValue: true,checked:obj.data.businessPromise},
//							                    {boxLabel: '无', name: 'isdirectorpromise', inputValue: false, checked:!(obj.data.businessPromise)}
//							                ]
//										}]
//									},{
//										columnWidth : .5,
//										layout : 'form',
//										labelWidth : 100,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'datefield',
//											format:'Y-m-d',
//											fieldLabel : '承诺补齐期限',
//											name: 'replenishTimeLimit',
//											value : obj.data.replenishTimeLimit
//										}]
//									},{
//										xtype : 'hidden',
//										name: 'id',
//										value  :obj.data.id
//									}],
//									buttons : [{
//										text : '保存',
//										iconCls : 'saveIcon',
//										formBind : true,
//										handler : function() {
//											Ext.getCmp('faneditMaterialFrom').getForm().submit({
//												method : 'POST',
//												waitTitle : '连接',
//												waitMsg : '消息发送中...',
//												success : function(form ,action) {
//													Ext.ux.Toast.msg('状态', '编辑担保材料成功!',
//														function(btn, text) {
//															seeContractStore.load({
//																params : {projid :piKey , contractType : 2}
//															});
//															feneditMaterialWin.destroy();
//													});
//												},
//												failure : function(form, action) {
//													Ext.ux.Toast.msg('状态','编辑担保材料失败!');		
//												}
//											});
//										}
//									}]
//								})
//							]
//						}).show();
//			    	},
//			    	failure: function(response, option) {   
//			        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
//			    	} 
//			    })
//			}
//  
//			var seefaneditMaterial = function(id){
//				Ext.Ajax.request({   
//			    	url: 'getByIdMortgage.do',   
//			   	 	method:'post',   
//			    	params:{id:id},
//			    	success: function(response, option) {
//			    		var obj = Ext.decode(response.responseText);
//			    		var feneditMaterialWin = new Ext.Window({
//							title : '查看编辑担保材料信息',
//							layout : 'fit',
//							width : 700,
//							height : 480,
//							closable : true,
//							constrainHeader : true ,
//							resizable : true,
//							plain : false,
//							bodyBorder : false,
//							border : false,
//							modal : true,
//							bodyStyle:'overflowX:hidden',
//							buttonAlign : 'right',
//							items :[
//								new Ext.form.FormPanel({
//									labelAlign : 'right',
//									buttonAlign : 'center',
//									layout : 'column',
//									frame : true ,
//									items : [{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 110,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'textfield',
//											fieldLabel : '担保物名称',
//											value : obj.data.mortgagename,
//											readOnly  : true,
//											cls : 'readOnlyClass'
//										}]
//									},{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 110,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'textarea',
//											height : 150,
//											fieldLabel : '要求材料清单',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											value : obj.data.needDatumList
//										},{
//											xtype : 'textarea',
//											height : 80,
//											fieldLabel : '缺少材料过程记录',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											value : obj.data.lessDatumRecord
//										},{
//											xtype : 'textarea',
//											height : 80,
//											fieldLabel : '收到材料清单',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											value : obj.data.receiveDatumList
//										}]
//									},{
//										columnWidth : 1,
//										layout : 'form',
//										labelWidth : 120,
//										defaults : {anchor : '100%'},
//										items : [{
//							            	xtype : 'textfield',
//											fieldLabel : '反担保材料是否完整',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											valueNotFoundText : obj.data.isAuditingPassValue
//										}]
//									},{
//										columnWidth : .5,
//										layout : 'form',
//										labelWidth : 100,
//										defaults : {anchor : '100%'},
//										items :[{
//							                xtype : 'textfield',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											fieldLabel : '业务经理承诺函',
//											value : (obj.data.businessPromise == true)? "有" : "无" 
//										}]
//									},{
//										columnWidth : .5,
//										layout : 'form',
//										labelWidth : 100,
//										defaults : {anchor : '100%'},
//										items :[{
//											xtype : 'textfield',
//											readOnly  : true,
//											cls : 'readOnlyClass',
//											fieldLabel : '承诺补齐期限',
//											value : obj.data.replenishTimeLimit
//										}]
//									}]
//								})
//							]
//						}).show();
//			    	},
//			    	failure: function(response, option) {   
//			        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
//			    	} 
//			    })
//			}
//
//			
//		},
//		failure : function(response) {
//			Ext.ux.Toast.msg('状态', '操作失败，请重试');
//		}
//	})
	var number = function(v){
	showOrHide=v;
	}
	var isdirectorpromisedic = 1145 ;  var isfanassurematerialdic = 1118 ;
	var anchor = '100%';
	Ext.Ajax.request({
		url : 'isValidUser.do',
		method : 'POST',
		success : function(response, request) {
			obj = Ext.util.JSON.decode(response.responseText);
			if(obj.mark != true){
				var funDeleteRow  = function(val,m,r){
					return '';
				};
				var funDownContract  = function(val,m,r){
					return '';
				};
				var funDoContract = function(val,m,r){
					return '';
				};
				var funAdd = function(val,m,r){
					return '';
				};
				var funDownload = function(val,m,r){
					if(val != 0){
						//下载，删除
						if(showOrHide==15||showOrHide==16||showOrHide==17||showOrHide==18||showOrHide==19||showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11||showOrHide==12||showOrHide==13||showOrHide==14){
							return '<a style="color: blue" onclick="lookContractByHTML('+r.get('contractId')+')">下载查看打印</a>'
						}else if(showOrHide==1000){
							return '<a style="color: blue" onclick="lookContractFileByHTML('+r.get('id')+')">下载查看打印</a>'
						}
					}else{
						//上传
						return '' ;
					}
				};		
			}else{
				var funDeleteRow  = function(val,m,r){
						if(showOrHide==15){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//删除委托担保合同记录行
							}
							
						}else if(showOrHide==16){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//删除委托反担保合同记录行
							}
							
						}else if(showOrHide==17){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//删除股东会决议书记录行
							}
							
						}else if(showOrHide==18){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//客户承诺函
							}
							
						}else if(showOrHide==19){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//对外担保承诺函
							}
							
						}else if(showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+r.get('contractId')+')" />';//反担保合同
							}
							
						}
//						else if(showOrHide==1000){
//							return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractCategoryRecord13('+r.get('id')+')"/>';
//						}else{
//							return '';
//						}
						//return '<img title="点击删除记录行" src="'+basepath()+'images/reset.gif" onclick="deleteContractRecord13('+(conObj1 == '' ? 0 : conObj1.id)+')" />';//删除委托担保合同记录行
				};
				var funDownContract  = function(val,m,r){
						if(showOrHide==15){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ENTRUST_GUARANTEE+'\')" />';//委托担保合同
							}
							
						}else if(showOrHide==16){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\')" />';//委托反担保合同
							}
							
						}else if(showOrHide==17){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+gudongjueyishuVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+COMMITMENT+'\')" />';//股东会决议书
							}
							
						}else if(showOrHide==18){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+acceptanceLetterVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ACCEPTENCE_LETTER_ACCEPTENCE+'\')" />';//客户承诺函
							}
							
						}else if(showOrHide==19){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+assureCommitmentLetterVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ASSURE_COMMITMENT_LETTER+'\')" />';//对外担保承诺函
							}
							
						}else if(showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="下载生成的合同" src="'+basepath()+'images/download-start.png" onclick="downloadContract(\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+REVERSE_GUARANTEE+'\')" />';//反担保合同
							}
							
						}
//						else if(showOrHide==1000){
//							return '<img title="点击下载附件" src="'+basepath()+'images/download-start.gif" onclick="downLoadContractFilesNode13('+r.get('id')+')"/>';
//						}else{
//							return '';
//						}
//						return '<img title="点击下载附件" src="'+basepath()+'images/download-start.gif" onclick="downloadContract(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+(conObj2 == '' ? 0 : conObj2.id)+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\')" />';
				};
				var funDoContract = function(val,m,r){
						if(showOrHide==15){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="addEntrustGuaranteeWin(\''+piKey+'\','+ENTRUST_GUARANTEE+',\''+r.get('contractId')+'\',\''+r.get('id')+'\')" />';//委托担保合同
//							}else{
//								return '';
//							}
							
						}else if(showOrHide==16){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="ajaxUpload13(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\',\''+r.get('id')+'\',\''+r.get('contractId')+'\')" />';//委托反担保合同
//							}else{
//								return '';
//							}
							
						}else if(showOrHide==17){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="ajaxUpload13(\''+gudongjueyishuVal+'\',\''+piKey+'\',\''+taskId+'\',\''+COMMITMENT+'\',\''+r.get('id')+'\',\''+r.get('contractId')+'\')" />';//股东会决议书
//							}else{
//								return '';
//							}
							
						}else if(showOrHide==18){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="ajaxUpload13(\''+acceptanceLetterVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ACCEPTENCE_LETTER_ACCEPTENCE+'\',\''+r.get('id')+'\',\''+r.get('contractId')+'\')" />';//客户承诺函
//							}else{
//								return '';
//							}
							
						}else if(showOrHide==19){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="ajaxUpload13(\''+assureCommitmentLetterVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ASSURE_COMMITMENT_LETTER+'\',\''+r.get('id')+'\',\''+r.get('contractId')+'\')" />';//对外担保承诺函
//							}else{
//								return '';
//							}
							
						}else if(showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11){
//							if(r.get('contractId')==''||r.get('contractId')==null){
								return '<img title="点击生成合同" src="'+basepath()+'images/upload-start.png" onclick="add_AgainstAssureWin(\''+piKey+'\','+REVERSE_GUARANTEE+',\''+r.get('contractId')+'\',\''+r.get('mortgageId')+'\',\''+r.get('id')+'\')" />';
																													  //addAgainstAssureWin(\''+piKey+'\','+REVERSE_GUARANTEE+','+r.get('id')+','+r.get('mortgageId')+')">新增合同</a>';
//							}else{
//								return '';
//							}
							
						}
//						else if(showOrHide==1000){
//							return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="upLoadContractFilesNode13('+r.get('id')+')" />';
//						}else{
//							return '';//
//						}
//						return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\',\''+v+'\')" />';//委托反担保合同
//						return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="addEntrustGuaranteeWin(\''+piKey+'\','+ENTRUST_GUARANTEE+',\''+(conObj1 == '' ? 0 : conObj1.id)+'\',\''+v+'\')" />';//委托担保合同

					
				};
				var funAdd = function(val,m,r){
					if(val != 0){
						if(showOrHide!=1000){
							return '<img title="点击增加文件" src="'+basepath()+'images/add.png" onclick="addContractCategory(\''+r.get('id')+'\',\''+r.get('orderNum')+'\')" />';
						}else{
							return '';
						}
					}else{
						return '' ;
					}
				};
				var funDownload = function(val,m,r){
					if(val != 0){
							//下载，删除
							if(showOrHide==15||showOrHide==16||showOrHide==17||showOrHide==18||showOrHide==19||showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11||showOrHide==12||showOrHide==13||showOrHide==14){
								var funStr = 'reLoadgStoreContractCategory';
								return '<a style="color: blue" onclick="lookContractByHTML('+r.get('contractId')+')">下载查看打印</a>&nbsp;&nbsp;<a style="color: blue" onclick="deleteContractFile('+r.get('contractId')+',\''+funStr+'\')">删除</a>'
							}else if(showOrHide==1000){
								var funStr = 'reLoadgStoreContractCategory';
								return '<a style="color: blue" onclick="lookContractFileByHTML('+r.get('id')+')">下载查看打印</a>&nbsp;&nbsp;<a style="color: blue" onclick="deleteContractFileFile('+r.get('id')+',\''+r.get('contractId')+'\',\''+funStr+'\')">删除</a>'
									
							}
								
					}else{
							if(showOrHide==15||showOrHide==16||showOrHide==17||showOrHide==18||showOrHide==19||showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11||showOrHide==12||showOrHide==13||showOrHide==14){
							var funStr = 'reLoadgStoreContractCategory';
							if(val == false){
								return '<img title="上传确认修改后的合同" src="'+basepath()+'images/upload-start.png" onclick="uploadContractWin('+r.get('contractId')+',\''+funStr+'\')"/>';
							}
							}else if(showOrHide==1000){
								var funStr = 'reLoadgStoreContractCategory';
								if(r.get('contractId')==0){
									return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.png" onclick="upLoadContractFilesNode13(\''+r.get('id')+'\',\''+piKey+'\',\''+r.get('contractCategoryText')+'\',\''+r.get('id')+'\',\''+r.get('mortgageId')+'\')" />';
								}
									
							}
							 
						//上传
							/*var vup = r.get('contractType') ;
							var ul = r.get('isUpload') ;
							if((vup == "" || vup == null) && (ul == false || ul == null)){
								return '<a style="color: red" onclick="('+r.get('id')+')">新增合同</a>' ;
							}else
								return '<a style="color: red" onclick="uploadContractWinSee('+r.get('id')+')">上传</a>' ;*/
						}
				};
			}
			//
				var button_see = new CS.button.SButton({
					text :'查看合同签署信息' ,
					handler : function() {
						var selected = seeContractGrid.getSelectionModel().getSelected();
						if (null == selected) {
							Ext.ux.Toast.msg('状态', '请选择一条记录!');
						} else {
							var id = selected.get('contractId');
							var mortgageId = selected.get('mortgageId');
							if(mortgageId != 0){
								seeFanContractShow(id);
							}else{
								seeContractShow(id);
							}
						}
					}
				});
				var button_update = new CS.button.UButton({
					text : '编辑查看反担保材料',
					handler : function() {
						var selected = seeContractGrid.getSelectionModel().getSelected();
						if (null == selected) {
							Ext.ux.Toast.msg('状态', '请选择一条反担保合同记录!');
						} else {
							var id = selected.get('contractId');
							var mortgageId = selected.get('mortgageId');
							if(mortgageId != 0){
								Ext.Ajax.request({
									url : 'isValidUser.do',
									method : 'POST',
									success : function(response, request) {
										obj = Ext.util.JSON.decode(response.responseText);
										if(obj.mark != true){
											//不能编辑，只能查看 
											seefaneditMaterial(mortgageId);
										}else{
											//可以编辑
											faneditMaterial(mortgageId ,projId);
										}
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									}
								})
							}else{
								Ext.ux.Toast.msg('状态', '请选择一条反担保合同记录!');
							}
						}
					}
				});
				var jReaderSeeContract = new Ext.data.JsonReader({
					totalProperty : 'totalProperty',
					root : 'topics'
				},[{name : 'id'},{name : 'parentId'},{name :'contractName'}, {name : 'contractCategoryTypeText'}, {name : 'number'}, {name : 'projId'}, {name : 'mortgageId'}, {name : 'isOld'}, {name : 'remark'},{name : 'isUpload'},{name : 'issign'},{name : 'isAgreeLetter'},{name : 'isSeal'},{name : 'contractCategoryText'},{name : 'contractId'},{name : 'templateId'},{name : 'localParentId'},{name :'orderNum'}]
				) ;
				gStoreContractCategory = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({url : 'getcontractCategoryTree.do'}) ,
		            reader: jReaderSeeContract,
		            groupField:'contractCategoryTypeText'
		        });
				gStoreContractCategory.load({
					params : {
						projId : projId
					}
				});
				
				var seeContractModel = new Ext.grid.ColumnModel(
				[
				new Ext.grid.RowNumberer( {
					header : '序号',
					width : 35,
					hidden : true
				}),{
					header : "",
					width : 100,
					sortable : true,
					dataIndex : 'number',
					renderer :number,
					hidden : true
				},{
					header : "合同分类类型",
					width : 100,
					sortable : true,
					dataIndex : 'contractCategoryTypeText',
					hidden : true
				}, {
					header : "合同名称",
					width : 180,
					sortable : true,
					dataIndex : 'contractName'
				}, {
					header : "合同分类名称",
					width : 80,
					sortable : true,
					dataIndex : 'contractCategoryText'
				},{
					header : "增加",
					width : 35,
					dataIndex : 'id',
					renderer : funAdd
				},{
					header : "删除新增文件",
					width : 80,
					dataIndex : 'id',
					renderer : function(v){
						//alert("showOrHide删除=="+showOrHide);
						//return '<img title="点击删除增加的文件" src="'+basepath()+'images/failed.gif" onclick="deleteContractCategory('+v+')" />';
						if(showOrHide==1000){
							return '<img title="点击删除增加的文件" src="'+basepath()+'images/failed.gif" onclick="deleteContractCategory('+v+')" />';
						}else{
							return '';
						}
					}
				},{
					header : "生成合同",
					width : 60,
					dataIndex : 'id',
					renderer : funDoContract
				}
//				,{
//					header : "上传",
//					width : 25,
//					dataIndex : 'id',
//					renderer : function(val,m,r){
////						if(showOrHide==15){
//////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="addEntrustGuaranteeWin(\''+piKey+'\','+ENTRUST_GUARANTEE+',\''+(conObj1 == '' ? 0 : conObj1.id)+'\',\''+r.get('id')+'\')" />';//委托担保合同
//////							}else{
//////								return '';
//////							}
////							
////						}else if(showOrHide==16){
////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\',\''+r.get('id')+'\',\''+(conObj2 == '' ? 0 : conObj2.id)+'\')" />';//委托反担保合同
////							}else{
////								return '';
////							}
////							
////						}else if(showOrHide==17){
////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+gudongjueyishuVal+'\',\''+piKey+'\',\''+taskId+'\',\''+COMMITMENT+'\',\''+r.get('id')+'\',\''+(conObj3 == '' ? 0 : conObj3.id)+'\')" />';//股东会决议书
////							}else{
////								return '';
////							}
////							
////						}else if(showOrHide==18){
////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+acceptanceLetterVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ACCEPTENCE_LETTER_ACCEPTENCE+'\',\''+r.get('id')+'\',\''+(conObj4 == '' ? 0 : conObj4.id)+'\')" />';//客户承诺函
////							}else{
////								return '';
////							}
////							
////						}else if(showOrHide==19){
////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+assureCommitmentLetterVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ASSURE_COMMITMENT_LETTER+'\',\''+r.get('id')+'\',\''+(conObj5 == '' ? 0 : conObj5.id)+'\')" />';//对外担保承诺函
////							}else{
////								return '';
////							}
////							
////						}else if(showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11){
////							if(r.get('contractId')==''||r.get('contractId')==null){
////								return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="addAgainstAssureWin(\''+piKey+'\','+REVERSE_GUARANTEE+',\''+r.get('contractId')+'\',\''+mortgage_id+'\',\''+r.get('id')+'\')" />';
////							}else{
////								return '';
////							}
////							
////						}else 
//						if(showOrHide==1000){
//							return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="upLoadContractFilesNode13('+r.get('id')+')" />';
//						}else{
//							return '';//
//						}
////						return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="ajaxUpload13(\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+taskId+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\',\''+v+'\')" />';//委托反担保合同
////						return '<img title="点击上传附件" src="'+basepath()+'images/upload-start.gif" onclick="addEntrustGuaranteeWin(\''+piKey+'\','+ENTRUST_GUARANTEE+',\''+(conObj1 == '' ? 0 : conObj1.id)+'\',\''+v+'\')" />';//委托担保合同
//
//					}
//				}
				,{
					header : "下载",
					width : 45,
					//dataIndex : 'id',
					dataIndex : 'id',
					renderer : funDownContract
				}
				,{
					header : "查看要素",
					width : 80,
					dataIndex : 'id',
					renderer : function(val,m,r){
						if(showOrHide==15){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'委托担保合同要素\',\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ENTRUST_GUARANTEE+'\')" />';//委托担保合同
							}
							
						}else if(showOrHide==16){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'委托反担保合同要素\',\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\')" />';//委托反担保合同
							}
							
						}else if(showOrHide==17){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'股东会决议书要素\',\''+gudongjueyishuVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+COMMITMENT+'\')" />';//股东会决议书
							}
							
						}else if(showOrHide==18){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'客户承诺函要素\',\''+acceptanceLetterVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ACCEPTENCE_LETTER_ACCEPTENCE+'\')" />';//客户承诺函
							}
							
						}else if(showOrHide==19){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'对外担保承诺函要素\',\''+assureCommitmentLetterVal+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+ASSURE_COMMITMENT_LETTER+'\')" />';//对外担保承诺函
							}
							
						}else if(showOrHide==1||showOrHide==2||showOrHide==3||showOrHide==4||showOrHide==5||showOrHide==6||showOrHide==7||showOrHide==8||showOrHide==9||showOrHide==10||showOrHide==11){
							if(r.get('contractId')==''||r.get('contractId')==null){
								return '';
							}else{
								return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'反担保合同要素\',\''+r.get('templateId')+'\',\''+piKey+'\',\''+r.get('contractId')+'\',\''+REVERSE_GUARANTEE+'\')" />';//反担保合同
							}
							
						}else{
							return '';//
						}
//						return '<img title="点击查看合同要素" src="'+basepath()+'images/icon-look.png" onclick="lookElement(\'委托反担保合同要素\',\''+entrustAgainstGuaranteeVal+'\',\''+piKey+'\',\''+(conObj2 == '' ? 0 : conObj2.id)+'\',\''+ENTRUST_REVERSE_GUARANTEE+'\')" />';
					}
				}
//				,{
//					header : "删除上传文件",
//					width : 45,
//					dataIndex : 'id',
//					renderer : function(value){
////						if(showOrHide==15){
////							return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteFile13('+(conObj1 == '' ? 0 : conObj1.id)+')" />';//删除委托担保合同附件
////						}else if(showOrHide==16){
////							return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteFile13('+(conObj2 == '' ? 0 : conObj2.id)+')" />';//删除委托反担保合同附件
////						}else if(showOrHide==17){
////							return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteFile13('+(conObj3 == '' ? 0 : conObj3.id)+')" />';//删除股东会决议书附件
////						}else{
////							return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteFile13('+(conObj1 == '' ? 0 : conObj1.id)+')" />';//删除委托担保合同附件
////						}
//						if(showOrHide!=1000){
//							return '';
//						}else{
//							return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteContractFilesNode13('+value+')" />';
//						}
////						return '<img title="点击删除上传文件" src="'+basepath()+'images/upload-stop.gif" onclick="deleteFile13('+(conObj1 == '' ? 0 : conObj1.id)+')" />';//删除委托担保合同附件
//					}
//				}
				,{
					header : "删除记录行",
					width : 110,
					dataIndex : 'id',
					renderer : funDeleteRow
				},{
					header : "操作",
					width : 120,
					sortable : true,
					dataIndex : 'isUpload',
					renderer : funDownload
				}]/*,checkColumnRECC,checkColumnRECCC,checkColumnRECCCC,checkColumnRECCCCC*/
				);
				var seeContractGrid = new Ext.grid.GridPanel( {
					id : 'gPanelContractCategory',
					pageSize : 10,
					store : gStoreContractCategory ,
					autoWidth : true,
					autoHeight: false,
					colModel : seeContractModel,
					loadMask : new Ext.LoadMask(Ext.getBody(), {
						msg : "加载数据中······,请稍后······"         
					}),
					tbar : [button_see,button_update],
					view: new Ext.grid.GroupingView({
	            	forceFit:true,
	            	groupTextTpl: '{text}'
	       	 		}),
					listeners : {
						'rowdblclick' : function(grid,index,e){
							var id = grid.getSelectionModel().getSelected().get('contractId');
							var mortgageId = grid.getSelectionModel().getSelected().get('mortgageId');
							if(mortgageId != 0){
								seeFanContractShow(id);
							}else{
								seeContractShow(id);
							}
						},
						'rowcontextmenu' :function(g,r,e){
							e.stopEvent() ;
							g.getSelectionModel().selectRow(r) ;
							var id = g.getStore().getAt(r).get('id');
							var mortgageId = g.getStore().getAt(r).get('mortgageId');
							if(mortgageId != 0){
								var menu = new Ext.menu.Menu({
									items : [{
										iconCls : 'addIcon',
										text : '查看编辑担保材料',
										handler : function() {
											faneditMaterial(mortgageId ,projId);
										}
									}]
								})
								menu.showAt(e.getXY());
							}
						}
					}
				});
				
				var seeContractWin = new Ext.Window({
					title : '合同相关信息',
					width :(screen.width-180)*0.5 + 150,
					height : 400,
					buttonAlign : 'center',
					layout : 'fit',
					modal : true,
					constrainHeader : true ,
					border : false,
					collapsible : true, 
					closable : true,
					resizable : true,
					autoScroll : true ,
					plain : true,
					items :[seeContractGrid]
				}).show();
				var seeContractShow = function(id){
					Ext.Ajax.request({
						url : 'getByIdContractAction.do',
						method : 'POST',
						success : function(response,request) {
							var obj = Ext.util.JSON.decode(response.responseText);
							var conData = obj.data ;
							var seeConForm = new Ext.form.FormPanel({
								id :'seeConForm',
								bodyStyle:'padding:10px',
								autoScroll : true ,
								labelAlign : 'right',
								buttonAlign : 'center',
								layout:'column',
								width : 488,
								height : 308,
								frame : true ,
								items: [{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
						            	xtype : 'textfield',
										fieldLabel : '合同名称',
										value : conData.contractName == null ? "" : conData.contractName ,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '合同编号',
										value : conData.contractNumber,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:1,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '合同类型',
										value : conData.text,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '起草人',
										value : conData.draftName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '确认修改人 ',
										value : conData.legalCheckName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '签署人 ',
										value : conData.signName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '审签人 ',
										value : conData.agreeLetterName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '盖章人 ',
										value : conData.sealName,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '起草时间',
										value : conData.draftDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '确认修改时间',
										value : conData.legalCheckDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '签署日期',
										value : conData.signDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '审签日期',
										value : conData.agreeLetterDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '盖章日期',
										value : conData.sealDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								}]
							})
							var window_see = new Ext.Window({
								title: '查看合同信息',
								layout : 'fit',
								closable : true,
								resizable : true,
								constrainHeader : true ,
								collapsible : true, 
								plain : true,
								border : false,
								modal : true,
								width :(screen.width-180)*0.5 + 100,
								height : 300,
								autoScroll : true ,
								bodyStyle:'overflowX:hidden',
								buttonAlign : 'right',
								items :[seeConForm]
							}).show();			
						},
						failure : function(response) {
								Ext.ux.Toast.msg('状态','操作失败，请重试');	
						},
						params: { id: id }
					});	
				}
				var seeFanContractShow = function(id){
					Ext.Ajax.request({
						url : 'getByIdContractAction.do',
						method : 'POST',
						success : function(response,request) {
							var obj = Ext.util.JSON.decode(response.responseText);
							var conData = obj.data ;
							var seeConForm = new Ext.form.FormPanel({
								id :'seeConForm',
								bodyStyle:'padding:10px',
								autoScroll : true ,
								labelAlign : 'right',
								buttonAlign : 'center',
								layout:'column',
								width : 488,
								height : 308,
								frame : true ,
								items: [{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
						            	xtype : 'textfield',
										fieldLabel : '合同名称',
										value : conData.contractName == null ? "" : conData.contractName ,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '合同编号',
										value : conData.contractNumber,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:1,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '合同类型',
										value : conData.text,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '起草人',
										value : conData.draftName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '确认修改人 ',
										value : conData.legalCheckName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '签署人 ',
										value : conData.signName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '审签人 ',
										value : conData.agreeLetterName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '盖章人 ',
										value : conData.sealName,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '反担保物名称 ',
										value : conData.assureText,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '反担保物类型 ',
										value : conData.reverseAssureText,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '所有权人 ',
										value : conData.ownershipPerson,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								},{
									columnWidth:.5,
							        layout: 'form',
							        labelWidth : 90,
									defaults : {anchor : anchor},
									items :[{
										xtype : 'textfield',
										fieldLabel : '起草时间',
										value : conData.draftDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '确认修改时间',
										value : conData.legalCheckDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '签署日期',
										value : conData.signDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '审签日期',
										value : conData.agreeLetterDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '盖章日期',
										value : conData.sealDate,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '担保类型',
										value : conData.assureTypeText,
										readOnly  : true,
										cls : 'readOnlyClass'
									},{
										xtype : 'textfield',
										fieldLabel : '所有权人类型',
										value : conData.ownershipPersonType,
										readOnly  : true,
										cls : 'readOnlyClass'
									}]
								}]
							})
							var window_see = new Ext.Window({
								title: '查看合同信息',
								layout : 'fit',
								closable : true,
								resizable : true,
								constrainHeader : true ,
								collapsible : true, 
								plain : true,
								border : false,
								modal : true,
								width :(screen.width-180)*0.5 + 100,
								height : 340,
								autoScroll : true ,
								bodyStyle:'overflowX:hidden',
								buttonAlign : 'right',
								items :[seeConForm]
							}).show();
						},
						failure : function(response) {
								Ext.ux.Toast.msg('状态','操作失败，请重试');	
						},
						params: { id: id }
					});	
				}
				
			var faneditMaterial = function(id ,piKey){
				Ext.Ajax.request({   
			    	url: 'getByIdMortgage.do',   
			   	 	method:'post',   
			    	params:{id:id},
			    	success: function(response, option) {
			    		var obj = Ext.decode(response.responseText);
			    		var feneditMaterialWin = new Ext.Window({
							title : '查看编辑担保材料信息',
							layout : 'fit',
							width : 700,
							height : 480,
							closable : true,
							constrainHeader : true ,
							resizable : true,
							plain : false,
							bodyBorder : false,
							border : false,
							modal : true,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items :[
								new Ext.form.FormPanel({
									id : 'faneditMaterialFrom',
									url : 'updateMortgage.do',
									monitorValid : true,
									labelAlign : 'right',
									buttonAlign : 'center',
									layout : 'column',
									frame : true ,
									items : [{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 110,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '担保物名称',
											value : obj.data.mortgagename,
											readOnly  : true,
											cls : 'readOnlyClass'
										}]
									},{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 110,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textarea',
											height : 150,
											fieldLabel : '要求材料清单',
											name: 'needDatumList',
											value : obj.data.needDatumList
										},{
											xtype : 'textarea',
											height : 80,
											fieldLabel : '缺少材料过程记录',
											name: 'lessDatumRecord',
											value : obj.data.lessDatumRecord
										},{
											xtype : 'textarea',
											height : 80,
											fieldLabel : '收到材料清单',
											name: 'receiveDatumList',
											value : obj.data.receiveDatumList
										}]
									},{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 120,
										defaults : {anchor : '100%'},
										items : [{
							            	xtype : 'combo',
											fieldLabel : '反担保材料是否完整',
											hiddenName : 'isfanassurematerial',
											mode : 'remote',
											editable : false,
											store : new Ext.data.JsonStore({
												url : 'queryDic.do',
												root : 'topics',
												fields : [{
													name : 'id'
												}, {
													name : 'typeId'
												}, {
													name : 'sortorder'
												}, {
													name : 'value'
												}],
												baseParams :{id : isfanassurematerialdic}
											}),
											displayField : 'value',
											valueField : 'id',
											triggerAction : 'all' ,
											value : obj.data.isAuditingPass,
											valueNotFoundText : obj.data.isAuditingPassValue
										}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
							            	xtype: 'radiogroup',
							            	width: 80,
							                fieldLabel: '业务经理承诺函',
							                items: [
							                    {boxLabel: '有', name: 'isdirectorpromise', inputValue: true,checked:obj.data.businessPromise},
							                    {boxLabel: '无', name: 'isdirectorpromise', inputValue: false, checked:!(obj.data.businessPromise)}
							                ]
										}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'datefield',
											format:'Y-m-d',
											fieldLabel : '承诺补齐期限',
											name: 'replenishTimeLimit',
											value : obj.data.replenishTimeLimit
										}]
									},{
										xtype : 'hidden',
										name: 'id',
										value  :obj.data.id
									}],
									buttons : [{
										text : '保存',
										iconCls : 'saveIcon',
										formBind : true,
										handler : function() {
											Ext.getCmp('faneditMaterialFrom').getForm().submit({
												method : 'POST',
												waitTitle : '连接',
												waitMsg : '消息发送中...',
												success : function(form ,action) {
													Ext.ux.Toast.msg('状态', '编辑担保材料成功!',
														function(btn, text) {
															gStoreContractCategory.load({
																params : {projid :piKey , contractType : 2}
															});
															feneditMaterialWin.destroy();
													});
												},
												failure : function(form, action) {
													Ext.ux.Toast.msg('状态','编辑担保材料失败!');		
												}
											});
										}
									}]
								})
							]
						}).show();
			    	},
			    	failure: function(response, option) {   
			        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
			    	} 
			    })
			}
  
			var seefaneditMaterial = function(id){
				Ext.Ajax.request({   
			    	url: 'getByIdMortgage.do',   
			   	 	method:'post',   
			    	params:{id:id},
			    	success: function(response, option) {
			    		var obj = Ext.decode(response.responseText);
			    		var feneditMaterialWin = new Ext.Window({
							title : '查看编辑担保材料信息',
							layout : 'fit',
							width : 700,
							height : 480,
							closable : true,
							constrainHeader : true ,
							resizable : true,
							plain : false,
							bodyBorder : false,
							border : false,
							modal : true,
							bodyStyle:'overflowX:hidden',
							buttonAlign : 'right',
							items :[
								new Ext.form.FormPanel({
									labelAlign : 'right',
									buttonAlign : 'center',
									layout : 'column',
									frame : true ,
									items : [{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 110,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											fieldLabel : '担保物名称',
											value : obj.data.mortgagename,
											readOnly  : true,
											cls : 'readOnlyClass'
										}]
									},{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 110,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textarea',
											height : 150,
											fieldLabel : '要求材料清单',
											readOnly  : true,
											cls : 'readOnlyClass',
											value : obj.data.needDatumList
										},{
											xtype : 'textarea',
											height : 80,
											fieldLabel : '缺少材料过程记录',
											readOnly  : true,
											cls : 'readOnlyClass',
											value : obj.data.lessDatumRecord
										},{
											xtype : 'textarea',
											height : 80,
											fieldLabel : '收到材料清单',
											readOnly  : true,
											cls : 'readOnlyClass',
											value : obj.data.receiveDatumList
										}]
									},{
										columnWidth : 1,
										layout : 'form',
										labelWidth : 120,
										defaults : {anchor : '100%'},
										items : [{
							            	xtype : 'textfield',
											fieldLabel : '反担保材料是否完整',
											readOnly  : true,
											cls : 'readOnlyClass',
											valueNotFoundText : obj.data.isAuditingPassValue
										}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
							                xtype : 'textfield',
											readOnly  : true,
											cls : 'readOnlyClass',
											fieldLabel : '业务经理承诺函',
											value : (obj.data.businessPromise == true)? "有" : "无" 
										}]
									},{
										columnWidth : .5,
										layout : 'form',
										labelWidth : 100,
										defaults : {anchor : '100%'},
										items :[{
											xtype : 'textfield',
											readOnly  : true,
											cls : 'readOnlyClass',
											fieldLabel : '承诺补齐期限',
											value : obj.data.replenishTimeLimit
										}]
									}]
								})
							]
						}).show();
			    	},
			    	failure: function(response, option) {   
			        	Ext.MessageBox.alert('友情提示',"异步通讯失败，请于管理员联系！");   
			    	} 
			    })
			}

			
		},
		failure : function(response) {
			Ext.ux.Toast.msg('状态', '操作失败，请重试');
		}
	})

}
var uploadContractWinSee= function(id){
	new Ext.Window({
		id : 'uploadContractWin_',
		title : '上传合同',
		layout : 'fit',
		width : (screen.width-180)*0.6,
		height : 130,
		closable : true,
		resizable : true,
		plain : false,
		bodyBorder : false,
		border : false,
		modal : true,
		constrainHeader : true ,
		bodyStyle:'overflowX:hidden',
		buttonAlign : 'right',
		items:[new Ext.form.FormPanel({
			id : 'uploadContractFrom_',
			url : 'uploadContractCall.do',
			monitorValid : true,
			labelAlign : 'right',
			buttonAlign : 'center',
			enctype : 'multipart/form-data',
			fileUpload: true, 
			layout : 'column',
			frame : true ,
			items : [{
				columnWidth : 1,
				layout : 'form',
				labelWidth : 70,
				defaults : {anchor : '95%'},
				items :[{},{
					xtype : 'textfield',
					fieldLabel : '合同文件',
					allowBlank : false,
					blankText : '文件不能为空',
					id : 'fileUpload_',
					name: 'fileUpload',
    				inputType: 'file'
				},{
					xtype : 'hidden',
					name: 'conId',
					value : id
				},{
					id :'fileUploadContentType_',
					xtype : 'hidden',
					name: 'fileUploadContentType'
				}]
			}],
			buttons : [{
				text : '上传',
				iconCls : 'uploadIcon',
				formBind : true,
				handler : function() {
					Ext.getCmp('uploadContractFrom_').getForm().submit({
						method : 'POST',
						waitTitle : '连接',
						waitMsg : '消息发送中...',
						success : function(form ,action) {
							Ext.ux.Toast.msg('状态', '上传成功!',
								function(btn, text) {
									gStoreContractCategory.reload();
									Ext.getCmp('uploadContractWin_').destroy();
							});
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('状态','上传失败!');		
						}
					});
				}
			}]
		})]
	}).show();
}
var deleteContractFile_ = function(id){
	Ext.MessageBox.confirm('确认删除', '是否确认删除合同文件', function(btn) {
		if (btn == 'yes') {
			Ext.Ajax.request({
				url : 'deleteContractCall.do',
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					if(obj.success == true){
						Ext.ux.Toast.msg('状态','删除成功',
						function(btn, text) {
							gStoreContractCategory.reload();
						});
					} else{
						Ext.MessageBox.alert('状态' , '删除失败,请与管理员联系');
					}
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '操作失败，请重试');
				},
				params : {
					conId : id
				}
			})
		}
	});
}
var seeLookContractByHTML = function(id){
	window.location.href="lookUploadContractCall.do?conId="+id
}