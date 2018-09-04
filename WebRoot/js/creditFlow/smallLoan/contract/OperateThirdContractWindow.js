/**
 * @author
 * @createtime
 * @class OperateContractWindow
 * @extends Ext.Window
 * @description OperateContractWindow
 * @company
 */
OperateThirdContractWindow = Ext.extend(Ext.Window, {
	// 内嵌panelContract
	panelContract : null,
	isHiddenAddBtn : true,
	isHiddenDelBtn : true,
	isHiddenEdiBtn : true,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.title) != "undefined") {
			this.title = _cfg.title;
		}else{
			this.title = '';
		};
		if (typeof(_cfg.categoryId) != "undefined") {
			this.categoryId = _cfg.categoryId;
		};
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
			if(this.businessType == 'SmallLoan'){
				this.HTLX = 'XEDQ';//合同类型（合同模板祖宗ID）,小贷合同
			}else if(this.businessType == 'Guarantee'){
				this.HTLX = 'DBDQ';//企业贷合同
			}
		};
		if (typeof(_cfg.piKey) != "undefined") {
			this.piKey = _cfg.piKey;
		};
		if (typeof(_cfg.contractId) != "undefined") {
			this.contractId = _cfg.contractId;
		};
		if (typeof(_cfg.thirdRalationId) != "undefined") {
			this.thirdRalationId = _cfg.thirdRalationId;
		};
		if (typeof(_cfg.temptId) != "undefined") {
			this.temptId = _cfg.temptId;
		};
		if (typeof(_cfg.thisPanel) != "undefined") {
			this.thisPanel = _cfg.thisPanel;
		};
		if (typeof(_cfg.htType) != "undefined") {
			this.htType = _cfg.htType;
		};
		if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}else{
			this.isHidden = true
		};
		if (typeof(_cfg.isfwEdit) != "undefined") {
			this.isfwEdit = _cfg.isfwEdit;
		}else{
			this.isfwEdit = false
		};
		if (typeof(_cfg.isqsHidden) != "undefined") {
			this.isqsHidden = _cfg.isqsHidden;
		}else{
			this.isqsHidden = true
		};
		if (typeof(_cfg.isqsEdit) != "undefined") {
			this.isqsEdit = _cfg.isqsEdit;
		}else{
			this.isqsEdit = false
		};
		if (typeof(_cfg.isgdHidden) != "undefined") {
			this.isgdHidden = _cfg.isgdHidden;
		}else{
			this.isgdHidden = true
		};
		if (typeof(_cfg.isgdEdit) != "undefined") {
			this.isgdEdit = _cfg.isgdEdit;
		}else{
			this.isgdEdit = false
		};
		if (typeof(_cfg.isHiddenAddBtn) != "undefined") {
			this.isHiddenAddBtn = _cfg.isHiddenAddBtn;
		}
		if (typeof(_cfg.isHiddenDelBtn) != "undefined") {
			this.isHiddenDelBtn = _cfg.isHiddenDelBtn;
		}
		if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
			this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		OperateThirdContractWindow.superclass.constructor.call(this, {
					//id :'OperateThirdContractWindow',
					layout : 'fit',
					iconCls : 'btn-edit',
					title : '查看/编辑'+this.title+'合同信息',
					width : 1000,
					//autoHeight:true,
					minWidth : 899,
					height :220,
					minHeight : 119,
					autoScroll : true,
					items : this.grid_contractPanel,
					maximizable : true,
					modal : true,
					buttonAlign : 'center'
				});
	},// end of the constructor
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
		var projId = this.piKey;
		var piKey = this.piKey;
		var businessType = this.businessType;// 小额贷,企业贷，业务品种
		var contType = this.htType;
		var thirdId = this.thirdRalationId;
		// var projId = this.projId;
		// var piKey = this.projId;
		
		var checkColumn = new Ext.grid.CheckColumn({
			header : '是否法务确认',
			width : 110,
			hidden : this.isHidden,
			editable : this.isfwEdit,
			dataIndex : 'isLegalCheck'
		});
		var qsCheckColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			editable : this.isqsEdit,
			hidden : this.isqsHidden,
			dataIndex : 'issign',
			width : 150
		});
		var affrimCheckColumn = new Ext.grid.CheckColumn({
			header : '是否归档',
			editable :this.isgdEdit,
			hidden : this.isgdHidden,
			dataIndex : 'isRecord',
			width : 100
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
				},{name : 'issign'},{name : 'signDate'},{name :'isRecord'},{name :'recordRemark'}]);
		this.jStore_contractCategroy = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/contract/getThirdContractCategoryTreeProcreditContract.do'
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
						mortgageId :thirdId,
						htType :contType
					}
				});

		this.contractcategroysm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});

		this.contractcategroy = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加合同',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : function(){
					this.addContract(businessType, piKey,thirdId,this.title);
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isHiddenAddBtn
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
				hidden : this.isHiddenDelBtn
			}), {
				iconCls : 'btn-edit',
				text : '编辑合同',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenEdiBtn,
				handler : function() {
					this.operateContract(businessType, piKey,thirdId,this.title)
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isHiddenEdiBtn
			})/*, {
				iconCls : 'btn-edit',
				text : '批量签署',
				xtype : 'button',
				scope : this,
				hidden : !this.isqsEdit,
				handler : function() {
					this.batchQSContract()
				}
			},new Ext.Toolbar.Separator({
				hidden : !this.isqsEdit
			})*/, {
				iconCls : 'btn-readdocument',
				text : '查看合同',
				xtype : 'button',
				scope : this,
				handler : function() {
					this.seeThirdContract(businessType, piKey,thirdId,this.title)
				}
			}]
		}),

		this.grid_contractPanel = new HT.EditorGridPanel({
			border : false,
			//id : 'grid_contractZZPanel',
			store : this.jStore_contractCategroy,
			autoExpandColumn : 'changContent',
			//autoScroll : true,
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
			loadMask : true,
			autoHeight : true,
			isShowTbar : true,
			//tbar : this.contractcategroy,
			sm : this.contractcategroysm,
			listeners : {
				scope : this,
				afteredit : function(e) {
						if (e.originalValue != e.value) {
							var args ;
							if(e.field =='isLegalCheck'){
								args = {
										'procreditContract.isLegalCheck' : e.value,
										'procreditContract.id' : e.record.data['contractId'],
										projId : projId,
										businessType : businessType
									}
							}else if(e.field =='issign'){
								args = {
										'procreditContract.issign':  e.value,
										'procreditContract.id': e.record.data['contractId'],
										projId : projId,
										businessType :businessType
									}
							}else if(e.field == 'signDate'){
								args = {
									'procreditContract.signDate': e.value,
									'procreditContract.id': e.record.data['contractId'],
									projId : projId,
									businessType :businessType
								}
							}else if(e.field == 'isRecord'){
								args = {
									'procreditContract.isRecord': e.value,
									'procreditContract.id': e.record.data['contractId'],
									projId : projId,
									businessType :businessType
								}
							}else if(e.field == 'recordRemark'){
								args = {
									'procreditContract.recordRemark': e.value,
									'procreditContract.id': e.record.data['contractId'],
									projId : projId,
									businessType :businessType
								}
							}
							if(e.field =='isLegalCheck' || e.field =='issign'||e.field == 'signDate'||e.field == 'isRecord'||e.field == 'recordRemark'){
								if(e.record.data['contractId'] == 0){
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
				width : 100,
				sortable : true,
				renderer : function(val, m, r) {
					// if(r.get('number') ==GRDKHT){
					if (r.get('id') == '' || r.get('id') == 0) {
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
				width :100,
				sortable : true,
				renderer : function(val, m, r) {
					if (r.get('id') == '' || r.get('id') == 0) {
						return '';
					} else {
						return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractView.DownFiles(\''
								+ r.get('id')
								+ '\',\''
								+ piKey
								+ '\',\''
								+ r.get('contractCategoryText')
								+ '\',\''
								+ r.get('contractId')
								+ '\',\''
								+ r.get('mortgageId')
								+ '\')">'
								+ r.get('fileCount') + '</a>';
					}

				}
			},checkColumn
			,qsCheckColumn
			,{
				header : '签署时间',
				format : 'Y-m-d',
				width : 100,
				sortable : true,
				editable : this.isqsEdit,
				hidden : this.isqsHidden,
				dataIndex : 'signDate',
				renderer : ZW.ux.dateRenderer(this.datafield),
				editor : new Ext.form.DateField( {
					format : 'Y-m-d',
					allowBlank : false,
					blankText : '签署日期不可为空!'
				})
	
			
			},affrimCheckColumn, {
				header : '归档备注',
				dataIndex : 'recordRemark',
				hidden : this.isgdHidden,
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


	},// end of the initUIComponents
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
	addContract : function(businessType, piKey,thirdRalationId,title){
		var thisPanel = this.grid_contractPanel;
		var window = new OperateContractWindow({
						title : title,
						businessType : businessType,
						piKey : piKey,
						thirdRalationId :thirdRalationId,
						htType :this.htType,
						thisPanel : thisPanel
					});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});
	},
	operateContract : function(businessType, piKey,thirdRalationId,title) {
		var selected = this.grid_contractPanel.getSelectionModel()
				.getSelected();
		var thisPanel = this.grid_contractPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		} else {
			var id = selected.get('id');
			var temptId = selected.get('temptId');
			var contractId = selected.get('contractId');
			var title = selected.get('contractCategoryText');
			var window = new OperateContractWindow({
						title : title,
						businessType : businessType,
						piKey : piKey,
						thirdRalationId :thirdRalationId == null?0:thirdRalationId,
						contractId :contractId,
						categoryId :id,
						temptId :temptId,
						htType :this.htType,
						thisPanel : thisPanel
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
	seeThirdContract : function(businessType, piKey,thirdId,title) {
				var selected = this.grid_contractPanel.getSelectionModel().getSelected();
				var thisPanel = this.grid_contractPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('contractCategoryText');
					var thirdRalationId = thirdId;
					var contractId = selected.get('contractId');
					var categoryId = selected.get('id');
					var temptId = selected.get('temptId');
					var window = new SeeThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :this.htType,
								thisPanel : thisPanel
							});
					window.show();
					window.addListener({
								'close' : function() {
									//thisPanel.getStore().reload();
								}
							});
				}
			}
})
