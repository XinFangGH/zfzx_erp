Ext.ns('PawnContractView');
/**
 * @author: chencc
 * @class SlContractView
 * @extends Ext.Panel
 * @description [SlContractView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PawnContractView = Ext.extend(Ext.Panel, {
	isHiddenTbar : false,
	islcEdit : false,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	isHiddenEdiBtn : true,
	constructor : function(_cfg) {
		var jsCtArr = [		
				__ctxPath + '/js/creditFlow/smallLoan/contract/SlGenerationContract.js',
   				__ctxPath + '/js/creditFlow/smallLoan/contract/ContractMake.js',
  				__ctxPath + '/js/creditFlow/smallLoan/contract/ContractMakeWay.js'
  		];
		if (typeof(_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.HTLX) != "undefined") {
			this.HTLX = _cfg.HTLX;
		}
		if(typeof(_cfg.isqsEdit)!="undefined")
		{
		      this.isqsEdit=_cfg.isqsEdit;
		}else{
			this.isqsEdit = false
		}
		if(typeof(_cfg.isSignHidden)!="undefined")
		{
		      this.isSignHidden=_cfg.isSignHidden;
		}else{
			this.isSignHidden = true
		}
		if(typeof(_cfg.isHiddenAffrim)!="undefined")
		{
		      this.isHiddenAffrim=_cfg.isHiddenAffrim;
		}else{
			this.isHiddenAffrim = true
		}
		if(typeof(_cfg.isgdEdit)!="undefined")
		{
		      this.isgdEdit=_cfg.isgdEdit;
		}else{
			this.isgdEdit = false
		}
		if(_cfg.isHiddenTbar){
			this.isHiddenTbar=_cfg.isHiddenTbar;
		}
		if(_cfg.islcEdit){
			this.islcEdit=_cfg.islcEdit;
		}
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}else{
			this.isHidden = false
		}
		Ext.applyIf(this, _cfg);
		$ImportSimpleJs(jsCtArr, this.initUIComponents, this);

		// 调用父类构造
		PawnContractView.superclass.constructor.call(this, {
					// id : 'SlContractView',
					// title : '合同确认',
					region : 'center',
					border : false,
					layout : 'anchor'
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		var PERSON_FINANCE_CONTRACT = 0;// 个人贷款合同,暂时没用
		var rows = 0;
		var cols = 0;
		var srows = 0;
		var selectids = Array();
		var htlxid = 0;// 合同类型id
		var htlxname = null;
		var HTLX = this.HTLX;// 合同类型ID
		// var GRDKHT ="91";//个人贷款合同
		var templateId = 0;// 模板ID
		var projId = this.projId;
		var piKey = this.projId;
		var businessType = this.businessType;// 小额贷,企业贷，业务品种
		var cfileCount = this.isHiddenAddBtn==false?15:0;
		// var projId = this.projId;
		// var piKey = this.projId;
		if(this.isHiddenDelBtn == true && this.isHiddenAddBtn == true && this.isHiddenEdiBtn == true) {
			this.isHiddenTbar = true;
		}
		if(this.isqsEdit ==true){
			this.isHiddenTbar = false;
		}
		var checkColumn = new Ext.grid.CheckColumn({
			header : '是否法务确认',
			width : 90,
			hidden : this.isHidden,
			editable : !this.islcEdit,
			dataIndex : 'isLegalCheck'
		});
		var qsCheckColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			editable : this.isqsEdit,
			hidden : this.isSignHidden,
			dataIndex : 'issign',
			width : 128
		});
		var affrimCheckColumn = new Ext.grid.CheckColumn({
			hidden : this.isHiddenAffrim,
			header : '是否归档',
			editable :this.isgdEdit,
			dataIndex : 'isRecord',
			width : 70
		});
		
		this.render_contractInfo = Ext.data.Record.create([{
					name : 'id'
				}, {
					name : 'parentId'
				}, {
					name : 'contractCategoryTypeText'
				}, {
					name : 'number'
				}, {
					name : 'projId'
				}, {
					name : 'mortgageId'
				}, {
					name : 'isOld'
				}, {
					name : 'remark'
				}, {
					name : 'isUpload'
				}, {
					name : 'issign'
				}, {
					name : 'isAgreeLetter'
				}, {
					name : 'isSeal'
				}, {
					name : 'contractCategoryText'
				}, {
					name : 'contractId'
				}, {
					name : 'contractName'
				}, {
					name : 'contractNumber'
				}, {
					name : 'text'
				}, {
					name : 'draftName'
				}, {
					name : 'draftDate'
				}, {
					name : 'localParentId'
				}, {
					name : 'templateId'
				}, {
					name : 'isLegalCheck'
				}, {
					name : 'verifyExplain'
				}, {
					name : 'orderNum'
				}, {
					name : 'fileCount'
				}, {
					name : 'temptId'
				},{
					name : 'issign'
				},{
					name : 'signDate'
				},{
				    name : 'isRecord'
				},{
				    name : 'recordRemark'
				},{
				    name : 'searchRemark'
				},{
					name : 'htType'
				},{
					name:'dwId'
				}]);
		this.jStore_contractCategroy = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/contract/getContractTreeProcreditContract.do'
							}),
					reader : new Ext.data.JsonReader({
								fields : this.render_contractInfo,
								totalProperty : 'totalProperty',
								root : 'topics'
							}),
					groupField : 'contractCategoryTypeText'
				});
		this.jStore_contractCategroy.load({
					params : {
						projId : projId,
						businessType : businessType,
						htType : this.htType
					}
				});

		this.contractcategroysm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});

		this.contractcategroy = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : function(){
					this.addContract(businessType, piKey,projId);
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isHiddenDelBtn
			}), {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				//id : "contractremoveBtn",
				scope : this,
				hidden : this.isHiddenDelBtn,
				handler : function(panel) {
					var selRs = this.grid_contractPanel.getSelectionModel().getSelections();
					var thisPanel = this.grid_contractPanel;
					if(selRs.length==0){
					   Ext.ux.Toast.msg('操作信息','请选择一条记录！');
					   return;
					}
					Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {

						if (btn == "yes") {

							panel.ownerCt.ownerCt.stopEditing();
							var s = panel.ownerCt.ownerCt.getSelectionModel()
									.getSelections();
							for (var i = 0; i < s.length; i++) {
								var row = s[i];
								if (row.data.id == null || row.data.id == '') {
									thisPanel.getStore().remove(row);
									//jStore_contractCategroy.remove(row);
								} else {

									SlContractView
											.deleteFun(
													__ctxPath
															+ '/contract/deleteContractCategoryRecordProcreditContract.do',
													{
														categoryId : row.data.id
													}, function() {
														thisPanel.getStore().remove(row);
														thisPanel.getStore().reload();
													});
								}
							}

						}
					})

				}
			},new Ext.Toolbar.Separator({
				hidden : this.isHiddenEdiBtn
			}), {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenEdiBtn,
				handler : function() {
//					this.operateContract(businessType, piKey)
					this.editContract(businessType, piKey,projId);
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isHiddenEdiBtn
			}),{
				iconCls : 'btn-edit',
				text : '合同制作',
				xtype : 'button',
				scope : this,
				handler : function() {
					this.makeHTFiles(businessType, piKey)
				}
			},{
				iconCls : 'btn-edit',
				text : '附件上传',
				xtype : 'button',
				scope : this,
				handler : function() {
					this.downsandUpdateFiles(businessType, piKey)
				}
			}, {
				iconCls : 'selectIcon',
				text : '批量签署',
				xtype : 'button',
				scope : this,
				hidden : !this.isqsEdit,
				handler : function() {
					this.batchQSContract()
				}
			}]
		}),

		this.grid_contractPanel = new HT.EditorGridPanel({
			name:'contractPanel_view',
			border : false,
			//id : 'grid_contractZZPanel',
			store : this.jStore_contractCategroy,
			autoExpandColumn : 'changContent',
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			// anchor : fullanchor,
			viewConfig : {
				forceFit : true
			},
			plugins : [checkColumn,qsCheckColumn,affrimCheckColumn],
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			isShowTbar : this.isHiddenTbar?false : true,
			tbar : this.isHiddenTbar?null : this.contractcategroy,
			hiddenCm : this.isHiddenTbar?true : false,
			sm : this.contractcategroysm,
			listeners : {
				scope : this,
				afteredit : function(e) {
						if (e.originalValue != e.value) {
							var args ;
							if(e.field =='isLegalCheck'){
								args = {
										'procreditContract.isLegalCheck' : e.value,
										'procreditContract.id' : e.record.data['id']/*,
										projId : projId,
										businessType : businessType*/
									}
							}else if(e.field =='issign'){
								args = {
										'procreditContract.issign':  e.value,
										'procreditContract.id': e.record.data['id']/*,
										projId : projId,
										businessType :businessType*/
									}
							}else if(e.field == 'signDate'){
								args = {
									'procreditContract.signDate': e.value,
									'procreditContract.id': e.record.data['id']/*,
									projId : projId,
									businessType :businessType*/
								}
							}else if(e.field == 'isRecord'){
								args = {
									'procreditContract.isRecord': e.value,
									'procreditContract.id': e.record.data['id']/*,
									projId : projId,
									businessType :businessType*/
								}
							}else if(e.field == 'recordRemark'){
								args = {
									'procreditContract.recordRemark': e.value,
									'procreditContract.id': e.record.data['id']/*,
									projId : projId,
									businessType :businessType*/
								}
							}
							if(e.field =='isLegalCheck' || e.field =='issign'||e.field == 'signDate'||e.field == 'isRecord'||e.field == 'recordRemark'){
								if(e.record.data['id'] == 0){
									Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
									this.grid_contractPanel.getStore().reload();
								}else{
									Ext.Ajax.request({
										url : __ctxPath+'/contract/updateProcreditContractByIdProduceHelper.do',
										method : 'POST',
										scope :this,
										success : function(response, request) {
											//this.grid_contractPanel.getStore().reload();
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
				}
			},
			columns : [/* new Ext.grid.RowNumberer({header:'序号'}), */

			{
				header : '合同类别',
				width : 143,
				dataIndex : 'contractCategoryTypeText'
			}, {
				header : '合同名称',
				dataIndex : 'contractCategoryText',
				width : 140
			}, {
				header : '合同编号',
				dataIndex : 'contractNumber'
			}, {
				header : '合同下载',
				width : 70,
				sortable : true,
				renderer : function(val, m, r) {

					// if(r.get('number') ==GRDKHT){
					if (r.get('contractName') == null || r.get('contractName') == '') {
						return '';
					} else {
						return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractView.downloadContract(\''
								+ r.get('id') + '\')" >下载</a>';// 个人贷款合同
					}
					// }

				}
			}, {
				header : '附件下载',
				dataIndex : 'fileCount',
				width : 70,
				sortable : true,
				renderer : function(val, m, r) {
					if (r.get('contractName') == null || r.get('contractName') =='') {
						return '';
					} else {
						return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downsFiles('+ r.get('id')+','+piKey+',\''+ businessType+ '\','+cfileCount+')">'+ r.get('fileCount') + '</a>';
						/*return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downsFiles(\''
								+ r.get('id')
								+ '\',\''
								+ piKey
								+ '\',\''
								+ r.get('contractCategoryText')
								+ '\',\''
								+ r.get('contractId')
								+ '\',\''
								+ r.get('mortgageId')
								+ '\','+cfileCount+')">'
								+ r.get('fileCount') + '</a>';*/
					}

				}
			},{
				header : '备注',
				dataIndex : 'searchRemark',
				width : 100
			},checkColumn
			,qsCheckColumn
			,{
				header : '签署时间',
				format : 'Y-m-d',
				width : 77,
				sortable : true,
				editable : this.isqsEdit,
				hidden : this.isSignHidden,
				dataIndex : 'signDate',
				renderer : ZW.ux.dateRenderer(this.datafield),
				editor : new Ext.form.DateField( {
					format : 'Y-m-d',
					allowBlank : false,
					blankText : '签署日期不可为空!'/*,
					listeners :{
						scope : this,
						select : function(d){
							var signdate = d.value;
							
							var s = this.grid_contractPanel.getSelectionModel().getSelected();
							Ext.Ajax.request({
								url : __ctxPath+'/credit/document/updateProcreditContractById.do',
								method : 'POST',
								success : function(response, request) {
									//SlContractQSView.jStore_contractCategroyQS.reload();
								},
								failure : function(response) {
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
								},
								params: {
									'procreditContract.signDate': signdate,
									'procreditContract.id': s.data.contractId,
									projId : projId,
									businessType :businessType
								}
							})
						}
					}*/
				})
	
			
			},affrimCheckColumn, {
				header : '归档备注',
				dataIndex : 'recordRemark',
				hidden : this.isHiddenAffrim,
				editable :this.isgdEdit,
				align : "center",
				width : 165,
				/*renderer : function(v){
					if(v == "" || v == null) {
						return "<font color=red>点击编辑备注</font>";
					}else{
						return v;
					}
				},*/
				editor : new Ext.form.TextField({
					selectOnFocus: true
				})
			},{
				hidden : true,
				dataIndex : 'htType'
			},{
				hidden:true,
				dataIndex:'dwId'
			}
			/*, {
				header : '是否法务确认',
				sortable : true,
				editor : new Ext.form.ComboBox({
							mode : 'local',
							editable : false,
							store : new Ext.data.SimpleStore({
										data : [['是', true], ['否', false]],
										fields : ['text', 'value']
									}),
							displayField : 'text',
							valueField : 'value',
							triggerAction : 'all'
						}),
				dataIndex : 'isLegalCheck',
				renderer : this.contractRenderer
			}*/]
		});
		// 下载附件
		var tPanel = this.grid_contractPanel;
		downsFiles = function(contractId,piKey,businessType,cfileCount){
			var reloadStore = function(){tPanel.getStore().reload();}
			var mark = "cs_procredit_contract."+contractId;
			uploadfileContract('上传/下载附件',mark,cfileCount,null,null,contractId,reloadStore,piKey,businessType);
	}
		/*this.grid_contractPanel.getSelectionModel().on('selectionchange',
				function(sm) {
					Ext.getCmp('contractremoveBtn')
							.setDisabled(sm.getCount() < 1 ? 1 : 0);
					this.ownerCt.ownerCt.getTopToolBar.items[0].get(2).setDisabled(sm.getCount() < 1 ? 1 : 0);
				});*/
		this.add(this.grid_contractPanel)
		this.doLayout();
	},// end of the initComponents()

	getGridDate : function(){
		var vDatas ='';
		var selects = this.grid_contractPanel.getSelectionModel().getSelections(); // 得到修改的数据（记录对象）
		if(selects.length == 0){
			Ext.ux.Toast.msg('状态', '请至少选择一条您需要批量签署的记录！');
			return false;
		}else{
			for (var i = 0; i < selects.length; i++) {
				vDatas += selects[i].data.id+",";
			}
		}
		return vDatas;
	},
	batchQSContract : function(){
		var categoryIds = this.getGridDate();
		if(categoryIds != false){
			Ext.Ajax.request({
				url : __ctxPath+'/contract/batchQSContractProcreditContract.do',
				method : 'GET',
				scope : this,
				success : function(response, request) {
					Ext.ux.Toast.msg('状态', '批量签署成功！');
					this.grid_contractPanel.getStore().reload();
				},
				failure : function(response) {
					Ext.ux.Toast.msg('状态', '签署失败，请重试！');
				},
				params : {
					categoryIds :categoryIds
				}
			})
		}
		
	},
	contractRenderer : function(v) {
		if (v == '' || v == null) {
			return '<font color=red>否</font>';
		} else if (v == true) {
			return '<font color=green>是</font>';
		} else if (v == false) {
			return '<font color=red>否</font>';
		} else {
			return v;
		}
	},
	/*addContract : function(businessType, piKey,projId){
		var thisPanel = this.grid_contractPanel;
		var window = new PawnOperateContractWindow({
						businessType : businessType,
						piKey : piKey,
						htType : this.htType,
						thisPanel : thisPanel,
						isApply:this.isApply,
						clauseId:this.clauseId,
						projectId : projId
					});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});
	},*/
	//添加合同
	addContract : function(businessType, piKey,projId){
		var thisPanel = this.grid_contractPanel;
		var CustomerName="";
	   //新制作
		var window = new SlGenerationContract({
			businessType : businessType,
			piKey : piKey,
			htType : this.htType,
			thisPanel : thisPanel,
			projectId : projId,
			isHiddenBZ:true,
			isHiddenDZY:true,
			isHiddenDW:false
		});
		window.show();
		window.addListener({
			'close' : function() {
				thisPanel.getStore().reload();
			}
		});
	},
	//编辑合同
	editContract : function(businessType, piKey,projId){
		var thisPanel = this.grid_contractPanel;
		var selected = thisPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		} else {
			var id = selected.get('id');
			var temptId = selected.get('temptId');
			var contractId = selected.get('id');
			var title = selected.get('contractCategoryText');
			var htType = selected.get('htType');
			var mortgagename = selected.get('mortgagename');
			var mortgageTypeValue = selected.get('mortgageTypeValue');
			var thirdRalationId = selected.get('mortgageId');
//			var searchRemark = selected.get('searchRemark');
			var remark = selected.get('remark');
			var contractCategoryTypeText=selected.get('contractCategoryTypeText');
			var contractCategoryText=selected.get('contractCategoryText');
			var leaseObjectId=selected.get('leaseObjectId');
			var dwId=selected.get('dwId');
			
			var CustomerName="";
		    //新制作
			var window = new SlGenerationContract({
				businessType : businessType,
				piKey : piKey,
				htType : htType,
				thisPanel : thisPanel,
				projectId : projId,
				title : title,
				categoryId : id,
				contractId : id,
				thirdRalationId : thirdRalationId,
				remark : remark,
				dwId:dwId,
				contractCategoryTypeText :contractCategoryTypeText,
				contractCategoryText : contractCategoryText,
				leaseObjectId:leaseObjectId,
				mortgageTypeValue :mortgageTypeValue,
				mortgagename : mortgagename,
				isHiddenBZ:true,
				isHiddenDZY:true,
				isHiddenDW:false
			});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});
		}
	},
	//合同制作MakeContract
	makeHTFiles : function(businessType, piKey){
		var selected = this.grid_contractPanel.getSelectionModel().getSelected();
		var thisPanel = this.grid_contractPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		} else {
			var categoryId = selected.get('id');
			var temptId = selected.get('temptId');
			var contractId = selected.get('id');
			var title = selected.get('contractCategoryText');
			var htType = selected.get('htType');
			var mortgagename = selected.get('mortgagename');
			var mortgageTypeValue = selected.get('mortgageTypeValue');
			var thirdRalationId = selected.get('mortgageId');
//			var contractNumber = selected.get('contractNumber');
			var searchRemark = selected.get('remark');
			var dwId = selected.get('dwId');
//			var searchRemark = selected.get('searchRemark');
			var contractCategoryTypeText=selected.get('contractCategoryTypeText');
			var contractCategoryText=selected.get('contractCategoryText');
			var window = new ContractMake({
						title : title,
						categoryId : categoryId,
						temptId : temptId,
						businessType : businessType,
						piKey : piKey,
						contractId : contractId,
						thisPanel : thisPanel,
						htType : htType,
//						contractNumber : contractNumber,
						searchRemark : searchRemark,
						
						mortgageTypeValue :mortgageTypeValue,
						mortgagename : mortgagename,
						thirdRalationId : thirdRalationId,
						projectId : this.projId,
						dwId : dwId,
						contractCategoryTypeText:contractCategoryTypeText,
						contractCategoryText:contractCategoryText
					});
			window.show();
			window.addListener({
				'close' : function() {
					//jStore_contractCategroy.reload()\
					thisPanel.getStore().reload();
				}
			});
		}
	},
	downsandUpdateFiles : function(businessType, piKey){
		var selected = this.grid_contractPanel.getSelectionModel().getSelected();
		var thisPanel = this.grid_contractPanel;
		var contractId = selected.get('id');
		var cfileCount = selected.get('cfileCount');
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		}else{
			var reloadStore = function(){thisPanel.getStore().reload();}
			var mark = "cs_procredit_contract."+contractId;
			uploadfileContract('上传/下载附件',mark,cfileCount,null,null,contractId,reloadStore,piKey,businessType);
		}
	},
	operateContract : function(businessType, piKey) {
		var selected = this.grid_contractPanel.getSelectionModel().getSelected();
		var thisPanel = this.grid_contractPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		} else {
			var id = selected.get('id');
			var temptId = selected.get('temptId');
			var contractId = selected.get('id');
			var title = selected.get('contractCategoryText');
			var htType = selected.get('htType');
			var searchRemark = selected.get('searchRemark');
			var window = new PawnOperateContractWindow({
						title : title,
						categoryId : id,
						temptId : temptId,
						businessType : businessType,
						piKey : piKey,
						contractId : contractId,
						thisPanel : thisPanel,
						htType : htType,
						searchRemark : searchRemark,
						projectId : this.projId
					});
			window.show();
			window.addListener({
						'close' : function() {
							//jStore_contractCategroy.reload()\
							thisPanel.getStore().reload();
						}
					});
		}
	}

});
SlContractView.deleteFun = function(url, prame, sucessFun) {
	Ext.Ajax.request({
				url : url,
				method : 'POST',
				success : function(response, request) {
					obj = Ext.util.JSON.decode(response.responseText);
					if (obj.data) {
						Ext.ux.Toast.msg('状态', '删除成功！');
						sucessFun();
					} else {
						Ext.ux.Toast.msg('状态', '删除失败！');
					}

				},
				params : prame
			});
};
// 下载合同
SlContractView.downloadContract = function(conId) {
	var pbar = Ext.MessageBox.wait('数据读取中', '请等待', {
				interval : 200,
				increment : 15
			});
	window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
					+ conId, '_blank');
	pbar.getDialog().close();
};
// 下载附件
SlContractView.DownFiles = function(id, piKey, contractName, contractId,
		mortgageId) {
	var mark = "cs_procredit_contract." + contractId;
	uploadfile('下载附件', mark, 0, null, null, null);
};