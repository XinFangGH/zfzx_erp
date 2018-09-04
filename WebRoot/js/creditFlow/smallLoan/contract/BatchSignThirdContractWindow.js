/**
 * @author
 * @createtime
 * @class OperateContractWindow
 * @extends Ext.Window
 * @description OperateContractWindow
 * @company
 */
BatchSignThirdContractWindow = Ext.extend(Ext.Window, {
	// 内嵌panelContract
	// 构造函数
	
	constructor : function(_cfg) {
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
			if(this.businessType == 'SmallLoan'){
				this.HTLX = 'XEDQ';//合同类型（合同模板祖宗ID）,小贷合同
				this.mortgagenameVar = '担保物';
			}else if(this.businessType == 'Guarantee'){
				this.HTLX = 'DBDQ';//企业贷合同
				this.mortgagenameVar = '反担保物';
			}else if(this.businessType == 'Financing'){
				this.HTLX = 'ZJRZ';//融资合同
				this.mortgagenameVar = '抵质押物';
			}
		};
		if (typeof(_cfg.piKey) != "undefined") {
			this.piKey = _cfg.piKey;
			this.projectId = this.piKey;
		};
		if (typeof(_cfg.thisPanel) != "undefined") {
			this.thisPanel = _cfg.thisPanel;
		};
		if (typeof(_cfg.htType) != "undefined") {
			this.htType = _cfg.htType;
		};
		if (typeof(_cfg.isfwEdit) != "undefined") {
			this.isfwEdit = _cfg.isfwEdit;
		}else{
			this.isfwEdit=false
		}
		if (typeof(_cfg.isqsEdit) != "undefined") {
			this.isqsEdit = _cfg.isqsEdit;
		}else{
			this.isqsEdit=false
		}
		if (typeof(_cfg.isgdEdit) != "undefined") {
			this.isgdEdit = _cfg.isgdEdit;
		}else{
			this.isgdEdit=false
		}
		if (typeof(_cfg.isgdHidden) != "undefined") {
			this.isgdHidden = _cfg.isgdHidden;
		}else{
			this.isgdHidden= true
		}if (typeof(_cfg.isHidden) != "undefined") {
			this.isHidden = _cfg.isHidden;
		}else{
			this.isHidden= true
		}
		this.operType = this.isfwEdit == true?'fw':'qs';
		if(this.businessType == 'Financing'){
				this.isqsEdit = true
		}
		if(typeof(_cfg.isFw) != "undefined"){
			this.isFw = _cfg.isFw
		}else{
			this.isFw = false
		}
		if(typeof(_cfg.isqsHidden) != "undefined"){
			this.isqsHidden = _cfg.isqsHidden
		}else{
			this.isqsHidden = false
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		BatchSignThirdContractWindow.superclass.constructor.call(this, {
					layout : 'fit',
					iconCls : 'btn-edit',
					title : (this.operType =='qs'?'签署':'编辑')+'全部'+this.mortgagenameVar+'合同信息',
					width : 1000,
					//autoHeight:true,
					minWidth : 999,
					height :320,
					minHeight : 219,
					autoScroll : true,
					items : this.grid_contractPanel,
					maximizable : true,
					modal : true,
					buttonAlign : 'center'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var HTLX = this.HTLX;// 合同类型ID
		var templateId = 0;// 模板ID
		var projId = this.piKey;
		var piKey = this.piKey;
		var businessType = this.businessType;// 小额贷,企业贷，业务品种
		var contType = this.htType;
		var thirdId = this.thirdRalationId;
		var operaType = this.operType;
		var checkColumn = new Ext.grid.CheckColumn({
			header : '是否法务确认',
			width : 90,
			hidden : this.isHidden,
			editable :this.isfwEdit,
			dataIndex : 'isLegalCheck'
		});
		var qsCheckColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			dataIndex : 'issign',
			width : 128,
			hidden : this.isqsHidden,
			editable :this.isqsEdit
		});
		var affrimCheckColumn = new Ext.grid.CheckColumn({
			header : '是否归档',
			editable :this.isgdEdit,
			hidden : this.isgdHidden,
			dataIndex : 'isRecord',
			width : 70
		});

		this.contractcategroysm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});

		this.contractcategroy = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-readdocument',
				text : '查看合同',
				xtype : 'button',
				scope : this,
				handler : function() {
					this.seeThirdContract(businessType, piKey)
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isFw == true?this.isFw:!this.isfwEdit
			}),{
				iconCls : 'btn-edit',
				text : '编辑合同',
				xtype : 'button',
				scope:this,
				hidden : this.isFw == true?this.isFw:!this.isfwEdit,
				handler : function(){
					this.operateContract(this.businessType, this.projectId)
				}
			},new Ext.Toolbar.Separator({
				hidden : this.isFw == true?this.isFw:!this.isfwEdit
			}), {
				iconCls : 'btn-del',
				text : '删除合同',
				xtype : 'button',
				scope : this,
				hidden :this.isFw == true?this.isFw:!this.isfwEdit,
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
			}]}),

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
				},{name : 'issign'},{name : 'signDate'},{name :'isRecord'},{name :'recordRemark'},{name :'mortgagename'},{name :'mortgageTypeValue'}]);
		this.jStore_contractCategroy = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath
										+ '/contract/getAllThirdContractByProjectIdProcreditContract.do'
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
						htType :contType,
						operType :operaType
					}
				});
		this.grid_contractPanel = new HT.EditorGridPanel({
			border : false,
			//hiddenCm :true,
			//id : 'grid_contractZZPanel',
			store : this.jStore_contractCategroy,
			autoExpandColumn : 'contractCategoryTypeText',
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
			tbar : this.contractcategroy,
			sm : this.contractcategroysm,
			listeners : {
				scope : this,
				afteredit : function(e) {
						if (e.originalValue != e.value) {
							var args ;
							if(e.field =='issign'){
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
							}else if(e.field =='isLegalCheck'){
								args = {
									'procreditContract.isLegalCheck': e.value,
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
							if(e.field =='issign'||e.field == 'signDate' || e.field =='isLegalCheck'||e.field == 'isRecord'||e.field == 'recordRemark'){
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
			columns : [
			{
				header : this.mortgagenameVar+'名称',
				width : 120,
				dataIndex : 'mortgagename'
			},
			{
				header : this.mortgagenameVar+'类型',
				width : 100,
				dataIndex : 'mortgageTypeValue'
			},
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
				dataIndex : 'contractNumber',
				width : 140
			}, {
				header : '合同下载',
				width : 70,
				sortable : true,
				renderer : function(val, m, r) {
					if (r.get('contractId') == '' || r.get('contractId') == 0) {
						return '';
					} else {
						return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractView.downloadContract(\''
								+ r.get('contractId') + '\')" >下载</a>';// 个人贷款合同
					}
				}
			}, {
				header : '附件下载',
				dataIndex : 'fileCount',
				width : 70,
				sortable : true,
				renderer : function(val, m, r) {
					if (r.get('contractId') == '' || r.get('contractId') == 0) {
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
				width : 77,
				sortable : true,
				dataIndex : 'signDate',
				hidden : this.isqsHidden,
				editable :this.isqsEdit,
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
				editor : new Ext.form.TextField({
					selectOnFocus: true
				})
			}]
		});


	},// end of the initUIComponents
	operateContract : function(businessType, piKey) {
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
			var mortgageId = selected.get('mortgageId');
			var mortgagename = selected.get('mortgagename');
			var window = new OperateContractWindow({
						title : mortgagename+'-'+title,
						businessType : businessType,
						piKey : piKey,
						thirdRalationId :mortgageId == null?0:mortgageId,
						contractId :contractId,
						categoryId :id,
						temptId :temptId,
						htType :this.htType,
						thisPanel : thisPanel
					});
			window.show();
			window.addListener({
						'close' : function() {
							thisPanel.getStore().reload();
						}
					});
		}
	},
	seeThirdContract : function(businessType, piKey) {
				var selected = this.grid_contractPanel.getSelectionModel().getSelected();
				var thisPanel = this.grid_contractPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('contractCategoryText');
					var contractId = selected.get('contractId');
					var categoryId = selected.get('id');
					var temptId = selected.get('temptId');
					var window = new SeeThirdContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
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
