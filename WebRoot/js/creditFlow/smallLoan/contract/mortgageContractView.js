/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MortgageContractView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
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
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MortgageContractView.superclass.constructor.call(this, {
		/*	id:'MortgageContractView',
			title : '抵质押物管理',
			region : 'center',
			layout : 'border',
			iconCls :'menu-property',*/
			items : [this.grid_contractPanel ]
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

		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : function(){
					this.addContract(businessType, piKey);
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
					this.operateContract(businessType, piKey)
				}
			}]
		});
       var  projectName=function(data, cellmeta, record){
       	   
           return '担保物类型：【'+record.get("mortgageTypeValue")+'】，担保物名称：【'+record.get("mortgagename")+"】";

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'id',
						type : 'int'
					}, 'mortgageId','mortgagename','mortgageTypeValue', 
			           'contractCategoryTypeText','contractCategoryText','contractId','fileCount','isLegalCheck',
			           'issign','signDate','isRecord','recordRemark','contractType']);
       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath +'/creditFlow/fileUploads/getMorContractListFileForm.do?projectId='+this.projectId+"&businessType="+this.businessType+"&htType="+this.htType,
			reader : this.reader,
			autoLoad:true,
			sortInfo:{field: 'mortgageId', direction: "ASC"},
			groupField : 'mortgageId'
		});
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
		this.grid_contractPanel = new HT.EditorGridPanel( {
			name:'grid_contractPanel',
			region : 'center',
			tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,
			autoHeight:true,
			showPaging:false,
			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			}),
            plugins : [checkColumn,qsCheckColumn,affrimCheckColumn],
			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : "",
				dataIndex : "mortgageId",
				hidden : true,
				renderer : projectName
		   },{
				header : '合同类别',
				width : 143,
				dataIndex : 'contractCategoryTypeText'
			}, {
				header : '合同名称',
				dataIndex : 'contractCategoryText',
				width : 140
			}, {
				header : '合同下载',
				width : 70,
				sortable : true,
				renderer : function(val, m, r) {

					// if(r.get('number') ==GRDKHT){
					if (r.get('contractId') == '' || r.get('contractId') == 0) {
						return '';
					} else {
						return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="SlContractView.downloadContract(\''
								+ r.get('contractId') + '\')" >下载</a>';// 个人贷款合同
					}
					// }

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
						return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downsFiles('+ r.get('contractId')+','+piKey+',\''+ businessType+ '\','+cfileCount+')">'+ r.get('fileCount') + '</a>';
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
			}],
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
			}
		//end of columns
				});
          // 下载附件
		var tPanel = this.grid_contractPanel;
		downsFiles = function(contractId,piKey,businessType,cfileCount){
			var reloadStore = function(){tPanel.getStore().reload();}
			var mark = "cs_procredit_contract."+contractId;
			uploadfileContract('上传/下载附件',mark,cfileCount,null,null,contractId,reloadStore,piKey,businessType);
		}
		//this.gridPanel.addListener('cellclick', this.cellClick);

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
	addContract : function(businessType, piKey){
		var grid=this.grid_contractPanel;
		if(businessType=="SmallLoan"){
	    		
	    		var sm=new SmallLoanMortgageView({
					projectId : piKey,
					businessType :businessType,
					isReadOnly : false,//是否显示只读的合同
					isHiddenAffrim : true,
					isContractHidden : false,
					isHiddenAddContractBtn : false,
					isHiddenDelContractBtn : false,
					isHiddenEdiContractBtn : true,
					isAfterContract : false,
					isSeeContractHidden : false,
					htType:this.htType
				})
				var win=new Ext.Window({
					height:300,
					width:850,
					autoScroll:true,
					modal:true,
					//title:"【"+projectName+"】担保合同",
	                items:[sm]
				})
			    win.show()
			    win.addListener({
					'close' : function() {
						grid.getStore().reload();
					}
				});
	    	}else if(businessType=="Guarantee"){
	    		var gm=new GuaranteeMortgageView({
					projectId : piKey,
					businessType :businessType,
					isReadOnly : false,//是否显示只读的合同
					isAfterContract : true,
					isHiddenAffrim : true,
					isContractHidden : false,
					isHiddenDelContractBtn : false,
					isHiddenEdiContractBtn : false,
					isHidden : false,
					isHiddenSeeMortgageInfo:true,
					isSeeContractHidden : false
					
					
				});
				var win=new Ext.Window({
					height:300,
					width:850,
					autoScroll:true,
					modal:true,
				//	title:"【"+projectName+"】担保合同",
	                items:[gm]
				})
			    win.show()
			    win.addListener({
					'close' : function() {
						grid.getStore().reload();
					}
				});
	    	}else if(businessType=="Financing"){
	    		var fm=new FinanceMortgageView({
					projectId : piKey,
					businessType : businessType,
					isHiddenRelieve : true,
					isHiddenInArchiveConfirm : false,
					isHiddenAddContractBtn : false,
					isHiddenDelContractBtn : false,
					isHiddenEdiContractBtn : true,
					isfwEdit : false,
					isHiddenSignContractBtn : true,
					isAfterContract : false,
					isHiddenRelieve : true,
					isSeeContractHidden : false,
					isHiddenAffrim : true
				});
				var win=new Ext.Window({
					height:300,
					width:850,
					autoScroll:true,
					modal:true,
					//title:"【"+projectName+"】担保合同",
	                items:[fm]
				})
			    win.show()
			    win.addListener({
					'close' : function() {
						grid.getStore().reload();
					}
				});
	    	}
		
		
			/*	var selected = this.grid_contractPanel.getSelectionModel().getSelected();
				var thisPanel = this.grid_contractPanel;
				var rows = this.grid_contractPanel.getSelectionModel().getSelections();
				if(rows.length >1){
					Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
					return;
				}
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				} else {
					var title = selected.get('mortgagename');
					var thirdRalationId = selected.get('mortgageId');
					//var categoryId = selected.get('contractType');
					var window = new OperateContractWindow({
								title : title,
								businessType : businessType,
								piKey : piKey,
								thirdRalationId :thirdRalationId,
								//categoryId :categoryId == null?0:categoryId,
								contractId :contractId,
								categoryId :categoryId == null?0:categoryId,
								temptId :temptId,
								htType :'thirdContract',
								thisPanel : thisPanel
							});
					window.show();
					window.addListener({
								'close' : function() {
									thisPanel.getStore().reload();
								}
							});
				}*/
			},
	operateContract : function(businessType, piKey) {
		var selected = this.grid_contractPanel.getSelectionModel()
				.getSelected();
		var thisPanel = this.grid_contractPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录！');
		} else {
			var id = selected.get('id');
			var title = selected.get('mortgagename');
			//var temptId = selected.get('temptId');
			var contractId = selected.get('contractId');
			var title = selected.get('contractCategoryText');
			var thirdRalationId = selected.get('mortgageId');
			var window = new OperateContractWindow({
						title : title,
						categoryId : id,
						//temptId : temptId,
						businessType : businessType,
						piKey : piKey,
						contractId : contractId,
						thirdRalationId :thirdRalationId,
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