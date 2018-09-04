Ext.ns('SlClauseContractView');
/**
 * @author: chencc
 * @class SlClauseContractView
 * @extends Ext.Panel
 * @description [SlClauseContractView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
SlClauseContractView = Ext.extend(Ext.Panel, {
	isLoadData:true,  //是否加载数据 申请展期时不应该加载展期合同
	projId:null,
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.isLoadData) != "undefined") {
			this.isLoadData = _cfg.isLoadData;
		}
		if (typeof(_cfg.HTLX) != "undefined") {
			this.HTLX = _cfg.HTLX;
		}
		if (typeof(_cfg.isqsEdit) != "undefined") {
			this.isqsEdit = _cfg.isqsEdit;
		}else{
			this.isqsEdit = false
		}
		if (typeof(_cfg.isApply) != "undefined") {
			this.isApply = _cfg.isApply;
		}else{
			this.isApply = true
		}
		if (typeof(_cfg.isUpdate) != "undefined") {
			this.isUpdate = _cfg.isUpdate;
		}else{
			this.isUpdate = false
		}
		if (typeof(_cfg.clauseId) != "undefined") {
			this.clauseId = _cfg.clauseId;
		}
		if(_cfg.isShowHtml){
		   this.isShowHtml=false;
		}
		else{
		   this.isShowHtml=true;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造松散
		SlClauseContractView.superclass.constructor.call(this, {
					region : 'center',
					layout : 'anchor',
					items : [
					{xtype:'panel',border:false,hidden:this.isShowHtml,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【修定条款展期合同生成与签订】:</font></B>'},
					this.grid_contractPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var HTLX = this.HTLX;// 合同类型ID
		var projId = this.projId;
		var piKey = this.projId;
		var businessType = this.businessType;// 小额贷,企业贷，业务品种
		var isApply = this.isApply;
		var isUpdate = this.isUpdate;
		var clauseId = this.clauseId;

		var checkColumn = new Ext.grid.CheckColumn({
			header : '是否法务确认',
			width : 93,
			editable : this.isqsEdit,
			dataIndex : 'isLegalCheck'
		});
		var qsCheckColumn = new Ext.grid.CheckColumn({
			header : '是否签署并检验合格',
			width : 130,
			editable : this.isqsEdit,
			dataIndex : 'issign'
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
					name : 'signDate'
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
				}, {
					name : 'isApply'
				}]);
		this.jStore_contractCategroy = new Ext.data.GroupingStore({
					proxy : new Ext.data.HttpProxy({
								url : __ctxPath	+ '/contract/getSlauseContractCategoryTreeProcreditContract.do'
							}),
					baseParams:{
						projId : projId,
						businessType : businessType,
						htType : 'clauseContract',
						isApplyy : isApply,
						isUpdate :isUpdate,
						clauseId : clauseId
					},
					reader : new Ext.data.JsonReader({
								fields : this.render_contractInfo,
								totalProperty : 'totalProperty',
								root : 'topics'
							}),
					groupField : 'contractCategoryTypeText'
				});
	    if(this.isLoadData){
	    	   this.jStore_contractCategroy.load();
	    };
		this.contractcategroysm = new Ext.grid.CheckboxSelectionModel({
					header : '序号'
				});

		this.contractcategroy = new Ext.Toolbar({
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				handler : function(){
					this.addContract(businessType,piKey,clauseId);
				}
			}, '-', {
				iconCls : 'btn-edit',
				text : '编辑合同',
				xtype : 'button',
				hidden : !this.isqsEdit,
				scope : this,
				handler : function() {
					this.operateContract(businessType, piKey,clauseId)
				}
			}, '-', {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				//id : "contractremoveBtn",
				hidden : !this.isqsEdit,
				//disabled : true,
				scope : this,
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

									SlClauseContractView
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
			}]
		}),

		this.grid_contractPanel = new HT.EditorGridPanel({
			border : false,
			//id : 'grid_contractZZPanel',
			hiddenCm :this.isApply,
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
			plugins : [checkColumn,qsCheckColumn],
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			tbar : this.isqsEdit?this.contractcategroy:null,
			isShowTbar : this.isqsEdit?true:false,
			sm : this.contractcategroysm,
			listeners : {
				scope : this,
				afteredit : function(e) {
					var args;
						if (e.originalValue != e.value) {
							if(e.field == 'isLegalCheck'){
								args = {'procreditContract.isLegalCheck': e.value,'procreditContract.id': e.record.data['contractId'],projId : projId,businessType : businessType};
							}
							if(e.field == 'issign'){
								args = {'procreditContract.issign': e.value,'procreditContract.id': e.record.data['contractId'],projId : projId,businessType : businessType};
							}
							if(e.field == 'signDate'){
								args = {'procreditContract.signDate': e.value,'procreditContract.id': e.record.data['contractId'],projId : projId,businessType : businessType};
							}
							if(e.field == 'isLegalCheck'||e.field == 'issign'||e.field == 'signDate'){
								if(e.record.data['contractId'] == 0){
									Ext.ux.Toast.msg('提示', '操作无效，请先生成合同！');
									
									this.grid_contractPanel.getStore().reload();
								}else{
									Ext.Ajax.request({
										url : __ctxPath
												+ '/contract/updateProcreditContractByIdProduceHelper.do',
										method : 'POST',
										success : function(response, request) {
											e.record.commit();
										},
										failure : function(response) {
											Ext.ux.Toast.msg('状态', '操作失败，请重试！');
										},
										params : args
									})
								}
							}
							
						}
				}
			},
			columns : [

			{
				header : '合同类别',
				width : 143,
				dataIndex : 'contractCategoryTypeText'
			}, {
				header : '合同名称',
				dataIndex : 'contractCategoryText',
				width : 300,
				editable : this.isqsEdit
			}, {
				header : '合同下载',
				sortable : true,
				width : 72,
				renderer : function(val, m, r) {
					if (r.get('contractId') == '' || r.get('contractId') == 0) {
						return '';
					} else {
						return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlClauseContractView.downloadContract(\''
								+ r.get('contractId') + '\')" >下载</a>';// 个人贷款合同
					}
				}
			}, {
				header : '附件',
				dataIndex : 'fileCount',
				sortable : true,
				width : 44,
				renderer : function(val, m, r) {
					if (r.get('contractId') == '' || r.get('contractId') == 0) {
						return '';
					} else {
						return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlClauseContractView.DownFiles(\''
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
				width : 77,
				format : 'Y-m-d',
				sortable : true,
				editable : this.isqsEdit,
				dataIndex : 'signDate',
				renderer : ZW.ux.dateRenderer(this.datafield),
				editor : new Ext.form.DateField( {
					format : 'Y-m-d',
					allowBlank : false,
					blankText : '签署日期不可为空!'
				})
			}]
		});

	},// end of the initComponents()
	getGridDate : function(){
		var vRecords = this.grid_contractPanel.getStore().getRange(0,
						this.grid_contractPanel.getStore().getCount()); // 得到修改的数据（记录对象）
				var vCount = vRecords.length; // 得到记录长度
				var vDatas = '';
				if (vCount > 0) {
					for ( var i = 0; i < vCount; i++) {
						vDatas += vRecords[i].data.id+","
					}
				}
		return vDatas;
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
	addContract : function(businessType, piKey,clauseId){
		var thisPanel = this.grid_contractPanel;
		var window = new OperateContractWindow({
						businessType : businessType,
						piKey : piKey,
						isApply : false,
						htType : 'clauseContract',
						thisPanel : thisPanel,
						clauseId :clauseId
					});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});
	},
	operateContract : function(businessType, piKey,clauseId) {
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
						categoryId : id,
						temptId : temptId,
						businessType : businessType,
						piKey : piKey,
						contractId : contractId,
						isApply : false,
						htType : 'clauseContract',
						thisPanel : thisPanel,
						clauseId :clauseId
					});
			window.show();
			window.addListener({
						'close' : function() {
							thisPanel.getStore().reload()
						}
					});
		}
	}

});
SlClauseContractView.deleteFun = function(url, prame, sucessFun) {
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
SlClauseContractView.downloadContract = function(conId) {
	var pbar = Ext.MessageBox.wait('数据读取中', '请等待', {
				interval : 200,
				increment : 15
			});
	window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
					+ conId, '_blank');
	pbar.getDialog().close();
};
// 下载附件
SlClauseContractView.DownFiles = function(id, piKey, contractName, contractId,
		mortgageId) {
	var mark = "cs_procredit_contract." + contractId;
	uploadfile('下载附件', mark, 0, null, null, null);
};