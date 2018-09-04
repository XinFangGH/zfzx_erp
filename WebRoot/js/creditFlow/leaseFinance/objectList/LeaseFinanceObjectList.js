LeaseFinanceObjectList = Ext.extend(Ext.Panel, {
	// 构造函数
	isHandleHidden : true,
	isHandleEdit : true,
	constructor : function(_cfg) {
		/*if(typeof(_cfg.isHiddenSeeBtn)!="undefined"){
			this.isHiddenSeeBtn=isHiddenSeeBtn
		}*/
		if(typeof(_cfg.isHiddenRelieve)!="undefined")
		    {
		          this.isHiddenRelieve=_cfg.isHiddenRelieve;
		    }
			if(typeof(_cfg.projectId)!="undefined")
            {
                  this.projectId=_cfg.projectId;
            }
			if(typeof(_cfg.businessType)!="undefined")
            {
                  this.businessType=_cfg.businessType;
            }
			if (_cfg.isHidden) {
			   this.isHidden = _cfg.isHidden;
		    }
		    if(typeof(_cfg.isHiddenAffrim != "undefined")) {
		    	this.isHiddenAffrim = _cfg.isHiddenAffrim;
		    }
		    if(typeof(_cfg.isContractHidden)!="undefined")
            {
                  this.isContractHidden=_cfg.isContractHidden;
            }else{
            	 this.isContractHidden=true
            }
            if(typeof(_cfg.isgdEdit)!="undefined"){
            	this.isgdEdit=_cfg.isgdEdit
            }else{
            	this.isgdEdit=false;
            }
            if(typeof(_cfg.isRemarksEdit)!="undefined"){
            	this.isRemarksEdit=_cfg.isRemarksEdit
            }
            if(typeof(_cfg.isfwEdit)!="undefined")
            {
                  this.isfwEdit=_cfg.isfwEdit;
            }else{
            	 this.isfwEdit=false
            }
            if(typeof(_cfg.isqsEdit)!="undefined")
            {
                  this.isqsEdit=_cfg.isqsEdit;
            }else{
            	 this.isqsEdit=false
            }
            if(typeof(_cfg.isSignHidden)!="undefined")
            {
                  this.isSignHidden=_cfg.isSignHidden;
            }else{
            	 this.isSignHidden=true
            }
            if(typeof(_cfg.isAfterContract)!="undefined")
            {
                  this.isAfterContract=_cfg.isAfterContract;
            }else{
            	 this.isAfterContract=false
            }
            if(typeof(_cfg.isSeeContractHidden)!="undefined")
            {
                  this.isSeeContractHidden=_cfg.isSeeContractHidden;
            }else{
            	 this.isSeeContractHidden=true
            }
            if (_cfg.isHiddenInArchiveConfirm) {
			   this.isHiddenInArchiveConfirm = _cfg.isHiddenInArchiveConfirm;
		    }
		    if (typeof(_cfg.isHiddenTransact)!="undefined") {
			   this.isHiddenTransact = false;
		    }else {
		    	this.isHiddenTransact = true;
		    }
		 
			if (typeof(_cfg.isHiddenEdiBtn) != "undefined") {
				this.isHiddenEdiBtn = _cfg.isHiddenEdiBtn;
			}
			if (typeof(_cfg.isHiddenQSBtn) != "undefined") {
				this.isHiddenQSBtn = _cfg.isHiddenQSBtn;
			}else{
				this.isHiddenQSBtn = true
			
			}
			if (typeof(_cfg.isHiddenGDBtn) != "undefined") {
				this.isHiddenGDBtn = _cfg.isHiddenGDBtn;
			}else{
				this.isHiddenGDBtn = true
			
			}
			if (typeof(_cfg.isHiddenDelContractBtn) != "undefined") {
				this.isHiddenDelContractBtn = _cfg.isHiddenDelContractBtn;
			}
			if (typeof(_cfg.isHiddenEdiContractBtn) != "undefined") {
				this.isHiddenEdiContractBtn = _cfg.isHiddenEdiContractBtn;
			}
			if (typeof(_cfg.isReadOnly) != "undefined") {
				this.isReadOnly = _cfg.isReadOnly;
			}else{
				this.isReadOnly = true
			}
			if (typeof(_cfg.isdjHidden) != "undefined") {
				this.isdjHidden = _cfg.isdjHidden;
			}
			if (typeof(_cfg.isdjEdit) != "undefined") {
				this.isdjEdit = _cfg.isdjEdit;
			}
			if (typeof(_cfg.isblHidden) != "undefined") {
				this.isblHidden = _cfg.isblHidden;
			}else{
				this.isblHidden = true
			}
			if (typeof(_cfg.isblEdit) != "undefined") {
				this.isblEdit = _cfg.isblEdit;
			}
			if (typeof(_cfg.isgdHidden) != "undefined") {
				this.isgdHidden = _cfg.isgdHidden;
			}
		/*	if (typeof(_cfg.isgdEdit) != "undefined") {
				this.isgdEdit = _cfg.isgdEdit;
			}*/
			if(typeof(_cfg.isHiddenAddContractBtn)!="undefined"){
				this.isHiddenAddContractBtn=_cfg.isHiddenAddContractBtn
			}
			if(typeof(_cfg.isHiddenSave)!="undefined"){
				this.isHiddenSave=_cfg.isHiddenSave;
			}else{
				this.isHiddenSav=false
			}
			if(typeof(_cfg.isRelieveEdit) != "undefined") {
				this.isRelieveEdit = _cfg.isRelieveEdit;
			}else{
				this.isRelieveEdit = false;
			}
			if(typeof(_cfg.isKS) != "undefined") {//isKS为true时，为快速流程，快速流程中隐藏掉“签署合同”和“签署全部合同”按钮
				this.isKS = _cfg.isKS;
			}else{
				this.isKS = false;
			}
			if(typeof(_cfg.isHandleHidden) != "undefined") {
				this.isHandleHidden = _cfg.isHandleHidden;
			}
			if(typeof(_cfg.isHandleEdit) != "undefined") {
				this.isHandleEdit = _cfg.isHandleEdit;
			}
		Ext.applyIf(this, _cfg);
		// 初始化组件

		this.initUIComponents();
		// 调用父类构造
		LeaseFinanceObjectList.superclass.constructor.call(this, {
			layout : 'anchor',
			anchor : "100",
			autoWidth:true,
			items : [ 
					{xtype:'panel',border:false,bodyStyle:'margin-bottom:5px',html : '<br/><B><font class="x-myZW-fieldset-title">【'+this.titleText+'】:</font></B>'},
					this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {


		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-add',
				text : '增加',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenAddBtn,
				handler : this.createMortgage
			}, {
				iconCls : 'btn-del',
				text : '删除',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenDelBtn,
				handler : this.removeSelRs
			}, {
				iconCls : 'btn-edit',
				text : '编辑',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenEdiBtn,
				handler : this.editMortgage
			},{
				iconCls : 'btn-readdocument',
				text : '查看',
				xtype : 'button',
				scope : this,
				hidden : this.isHiddenSeeBtn,
				handler : this.seeObjectInfo
			}]
		});
		this.expanderFlow = new Ext.ux.grid.RowExpander({
			tpl : new Ext.Template('<table>{contractContent}</table>'),
//			tpl : new Ext.Template(
//			 '<tpl if="{iafeLevel} >2">','<p><b>意见与说明:</b>','12321','<l>'
//			)  
			lazyRender : false, 
			enableCaching : false,
			hidden : this.isSeeContractHidden
			/*getRowClass : function(record, rowIndex, p, ds){
		        p.cols = p.cols-1;
		        var content = this.bodyContent[record.id];
		        if(!content ){//&& !this.lazyRender){
		            content = this.getBodyContent(record, rowIndex);
		        }
		        if(content){
		            p.body = content;
		        }
		        //alert(record.id+"|"+content);
		        return this.state[record.id] ? 'x-grid3-row-expanded' : 'x-grid3-row-expanded';
		    }*/
			
		});		
		
		this.confirmDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
					readOnly:!this.isgdEdit,
					allowBlank : false
				})
				this.recieveDatefield=new Ext.form.DateField({
					format : 'Y-m-d',					
					readOnly:!this.isRecieveEdit,
					allowBlank : false
				})
				var checkColumn = new Ext.grid.CheckColumn({
					hidden : this.isgdHidden,
					header : '是否已提交',
					dataIndex : 'isArchiveConfirm',
					editable : this.isgdEdit,
					width : 70
				});
				var recieveCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isRecieveHidden,
					header : '是否已收到',
					dataIndex : 'isRecieve',
					editable : this.isRecieveEdit,
					width : 70
				});
				var relieveCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHiddenRelieve,
					editable : this.isRelieveEdit,
					header : '是否解除',
					dataIndex : 'isunchain',
					width : 70
				});
				var contractCheckColumn =new Ext.grid.CheckColumn({
					header : "是否法务确认",
					width : 90,
					dataIndex : 'isLegalCheck',
					editable : this.isfwEdit,
					hidden : this.isContractHidden,
					scope : this
				});
				var issignContractcheckColumn = new Ext.grid.CheckColumn({
					header : '是否签署并检验合格',
					width : 128,
					editable : this.isqsEdit,
					hidden : this.isSignHidden,
					dataIndex : 'issign'
				});
				var transactCheckColumn =new Ext.grid.CheckColumn({
					header : "是否办理",
					width : 65,
					dataIndex : 'isTransact',
					hidden : this.isblHidden,
					editable : this.isblEdit
				});
				this.handleCheckColumn = new Ext.grid.CheckColumn({
					hidden : this.isHandleHidden,
					header : '是否处理',
					dataIndex : 'isHandle',
					editable : this.isHandleEdit
				});
		this.gridPanel = new HT.EditorGridPanel({
			border : false,
			isShowTbar : true,
			tbar : this.topbar,
			clicksToEdit : 1,
			showPaging:false,
			autoHeight:true,
			viewConfig : {
				forceFit : true
			},
			rowActions : false,
			plugins : [this.expanderFlow,relieveCheckColumn,checkColumn,contractCheckColumn,issignContractcheckColumn,transactCheckColumn,this.handleCheckColumn,recieveCheckColumn],
			url : __ctxPath +'/credit/mortgage/getMortgageOfDY.do?projectId='+this.projectId+'&businessType='+this.businessType+'&isReadOnly='+this.isReadOnly,
			fields : [{
						name : 'id',
						type : 'int'
					}, 'mortgagename','mortgagepersontypeforvalue','assureofname', 
			           'assuremoney','finalprice','finalCertificationPrice','remarks','assuretypeidValue','personTypeValue',
			           'mortgagepersontypeidValue','assuremodeidValue','enregisterDatumList',
			           'pledgenumber','bargainNum','isAuditingPassValue','planCompleteDate',
			           'statusidValue','enregisterperson','unchainofperson','enregisterdate','isunchain',
			           'unchaindate','typeid','others', 'assureofnameEnterOrPerson','relation','needDatumList',
			          'receiveDatumList','lessDatumRecord','businessPromise','replenishTimeLimit',
			           'isReplenish','replenishDate','personTypeId','projnum','projname','enterprisename','isArchiveConfirm','businessType',
			           'contractCategoryTypeText','contractCategoryText','isLegalCheck','thirdRalationId','contractId','categoryId','temptId',
			           'issign','signDate','fileCount','isTransact','transactDate','fileCounts','contractContent','contractCount','enregisterDepartment','mortgageRemarks',
			           'isHandle','isRecieve','recieveDate','recieveRemarks'],
			columns : [this.expanderFlow, {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}/*, {
				header : '抵质押名称',
				dataIndex : 'mortgagename'
			}*/, {
				header : '抵质押类型',
				dataIndex : 'mortgagepersontypeforvalue'
			}, {
				header : '担保类型',
				dataIndex : 'assuretypeidValue'
			}, {
				header : '所有权人',
				dataIndex : 'assureofnameEnterOrPerson'
			}, {
				header : '评估价值',
				dataIndex :/* 'finalprice'*/'finalCertificationPrice',
				align:'right',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return Ext.util.Format.number(v,'0,000.00')+"元"	
					}
				}
			}, {
				header : '公允价值',
				dataIndex : 'finalCertificationPrice',
				align:'right',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return Ext.util.Format.number(v,'0,000.00')+"元"	
					}
				}
			}, {
				header : '抵押率',
				dataIndex : 'assuremoney',
				align:'right',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return Ext.util.Format.number(v,'0,000.00')+"%"	
					}
				}
			}, /*{
				header : '可担保额度',
				dataIndex : 'assuremoney',
				align:'right',
				renderer : function(v){
					if(v==''||v=='null'||v==null){
						return '';
					}else{
						return  Ext.util.Format.number(v,'0,000.00')+"元";
					}
				}
			}, */{
				header : '所有权人类型',
				dataIndex : 'personTypeValue'
			},{
				header : "",
				sortable : true,
				dataIndex : 'typeid',
				hidden:true
			}
			,{
				header : '合同个数',					
				hidden : this.isSeeContractHidden,
				dataIndex : 'contractCount'
			}
			,
			transactCheckColumn
			,relieveCheckColumn,this.handleCheckColumn,checkColumn,
			{
				header : '提交时间',
				dataIndex : 'unchaindate',
				align : "center",
				hidden : this.isgdHidden,
				editor : this.confirmDatefield,
				renderer : ZW.ux.dateRenderer(this.confirmDatefield)
			},{
				header : '提交备注',
				dataIndex : 'remarks',
				align : "center",
				hidden : this.isgdHidden,
				editable : this.isgdEdit,
				editor : new Ext.form.TextField({readOnly:this.isRemarksEdit})
			},{
				header : '收到备注',
				dataIndex : 'recieveRemarks',
				align : "center",
				hidden : this.isRecieveHidden,
				editor :{
					xtype : 'textfield'
				}
			}],
						// end of columns
			listeners : {
				scope : this,
				afteredit : function(e) {
					var value = e.value;
					var id = e.record.data['id'];
					var businessType=e.record.data['businessType'];
					if (e.originalValue != e.value) {//编辑行数据发生改变
						if(e.field == 'isunchain'){//是否解除
							Ext.Ajax.request({
									url : __ctxPath+'/credit/mortgage/updateMortgage.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										//this.gridPanel.getStore().reload();
										e.record.commit();
									},
									failure : function(response) {
										Ext.ux.Toast.msg('状态', '操作失败，请重试');
									},
									params: {
										'procreditMortgage.isunchain': e.value,
										mortgageid: e.record.data['id']
									}
								})
							return false;
						}
						if(e.field == 'isArchiveConfirm') {
							args = {'procreditMortgage.isArchiveConfirm': value,'procreditMortgage.id': id}
						}
						if(e.field == 'remarks') {
							args = {'procreditMortgage.remarks': value,'procreditMortgage.id': id}
						}
						if(e.field == 'pledgenumber') {//修改列为"反担保物登记号"列
							args = {'procreditMortgage.pledgenumber': value,'procreditMortgage.id': id};
						}
						if(e.field == 'enregisterDepartment') {//修改列为"反担保物登记机关"列
							args = {'procreditMortgage.enregisterDepartment': value,'procreditMortgage.id': id};
						}
						if(e.field == 'isTransact'){//是否办理
							args={
								'procreditMortgage.isTransact': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
					/*	if(e.field == 'transactDate') {//修改列为"办理时间"列
							args= {
								'procreditMortgage.transactDate': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}*/
						if(e.field == 'isHandle') {//修改列为"是否处理"列
							args= {
								'procreditMortgage.isHandle': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
						if(e.field == 'unchaindate') {
							args= {
								'procreditMortgage.unchaindate': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
						if(e.field == 'isRecieve') {
							args= {
								'procreditMortgage.isRecieve': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
						if(e.field == 'recieveDate') {
							args= {
								'procreditMortgage.recieveDate': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
						if(e.field == 'recieveRemarks') {
							args= {
								'procreditMortgage.recieveRemarks': e.value,
								'procreditMortgage.id': id,
								'procreditMortgage.businessType': businessType
							}
						}
						Ext.Ajax.request({
							url : __ctxPath+'/credit/mortgage/ajaxArchiveConfirm.do',
							method : 'POST',
							scope : this,
							success : function(response, request) {
								//this.gridPanel.getStore().reload();
								e.record.commit();
							},
							failure : function(response) {
								Ext.ux.Toast.msg('状态', '操作失败，请重试');
							},
							params : args
						})
					}
				},
				rowdblclick : function(grid, rowIndex, e) {
					//this.seeMortgageInfo();
				}
			} 
		//end of columns
		});
        
		downloadFiles = function(contractId,count,piKey,businessType) {
			var thisPanel = cpanel;
			var reloadStore = function(){
				thisPanel.getStore().reload();
			}
			if(contractId == null || contractId == 'undefined' || contractId == ''){
				Ext.ux.Toast.msg('提示','操作失败，请先生成合同！');
			}else{
				var mark = "cs_procredit_contract."+contractId;
				uploadfileContract('上传/下载附件',mark,count,null,null,contractId,reloadStore,piKey,businessType);
			}
		}
		//单个合同的下载
		downloadContract = function(contractId){
			window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
			+ contractId, '_blank');
		};
		//单个合同的重新生成
		var cpanel = this.gridPanel;
		reMakeContract = function(businessType, piKey,mortgagename,id,contractId,categoryId,temptId) {
			var thisPanel = cpanel;
			var window = new OperateContractWindow({
				title : mortgagename,
				businessType : businessType,
				piKey : piKey,
				thirdRalationId :id,
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
		};
		//单个合同的删除
		deleteContract = function(categoryId){
			var thisPanel = cpanel;
			Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
				if (btn == "yes") {
					Ext.Ajax.request({
						url : __ctxPath+ '/contract/deleteContractCategoryRecordProcreditContract.do',
						method : 'POST',
						success : function(response, request) {
							obj = Ext.util.JSON.decode(response.responseText);
							if (obj.data) {
								Ext.ux.Toast.msg('状态', '删除成功！');
								thisPanel.getStore().reload();
							} else {
								Ext.ux.Toast.msg('状态', '删除失败！');
							}
		
						},
						params : {
							categoryId : categoryId
						}
					});	
				}
			})
			
		};
	},
	//创建记录
	createMortgage : function() {
		if(this.businessType=="SmallLoan"){
			if(this.formPanel.getCmpByName('slSmallloanProject.projectMoney')!=null){
				var projectMoney=this.formPanel.getCmpByName('slSmallloanProject.projectMoney').getValue()
				new AddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,projectMoney:projectMoney}).show()
			}else{
				var projectMoney=this.formPanel.getCmpByName('slSuperviseRecord.continuationMoney').getValue()
				new AddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,projectMoney:projectMoney}).show()
			}
		}else if(this.businessType=="Guarantee"){//担保业务  具体业务逻辑可以在这里添加
			new AddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel}).show()
		}else if(this.businessType=="Financing"){//融资业务具体逻辑可以在这里添加
			new AddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel}).show()
		}else if(this.businessType=="LeaseFinance"){
			new LeaseFinanceAddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel}).show()
		}else {
			new AddDzyMortgageWin({projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel}).show()
		}
		
	},

	//把选中ID删除
	removeSelRs : function() {
		
				var selected = this.gridPanel.getSelectionModel().getSelected();
				var grid=this.gridPanel;
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择记录!');
				}else{
					var mortgageIds = "";
					//var typeIds = "";
					var rows = this.gridPanel.getSelectionModel().getSelections();
					for(var i=0;i<rows.length;i++){
						mortgageIds = mortgageIds+rows[i].get('id');
						
						if(i!=rows.length-1){
							mortgageIds = mortgageIds+',';
						}
					}
					Ext.MessageBox.confirm('确认删除', '该记录在附表同时存在相应记录,您确认要一并删除? ', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								url : __ctxPath +'/credit/mortgage/deleteMortgage.do',
								method : 'POST',
								success : function() {
									Ext.ux.Toast.msg('操作信息', '删除成功!');
									grid.getStore().remove(rows);
									grid.getStore().reload();
									
								},
								failure : function(result, action) {
									Ext.ux.Toast.msg('操作信息', '删除失败!');
								},
								params: { 
									mortgageIds: mortgageIds
								}
							});
						}
					});
				}
			
	},
	//编辑Rs
	editMortgage : function(record) {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
			return false;
		}else if(s.length>1){
			Ext.ux.Toast.msg('操作信息','只能选中一条记录');
			return false;
		}else{
			if(this.businessType=="SmallLoan"){
				if(this.formPanel.getCmpByName('slSmallloanProject.projectMoney')!=null){
					var projectMoney=this.formPanel.getCmpByName('slSmallloanProject.projectMoney').getValue()
					record=s[0]
					new UpdateDzyMortgageWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid,projectMoney:projectMoney}).show()
				}else{
					var projectMoney=this.formPanel.getCmpByName('slSuperviseRecord.continuationMoney').getValue()
					record=s[0]
					new UpdateDzyMortgageWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid,projectMoney:projectMoney}).show()
				}
			}else if(this.businessType=="Guarantee"){
				record=s[0]
				new UpdateDzyMortgageWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid}).show()
			}else if(this.businessType=="Financing"){
				record=s[0]
				new UpdateDzyMortgageWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid}).show()
			}else if(this.businessType=="LeaseFinance"){
				record=s[0]
				new UpdateLeaseFinanceMaterialsWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid}).show()
			}else {
				record=s[0]
				new UpdateDzyMortgageWin({id:record.data.id,projectId:this.projectId,businessType:this.businessType,gridPanel:this.gridPanel,type:record.data.typeid}).show()
			}
			
		}	
	},
	seeObjectInfo :function(record) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var objectId = selected.get('id');
			seeCarInfo(mortgageId,businessType);
		}
	},
	addThirdContract : function(businessType, piKey) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var thisPanel = this.gridPanel;
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if(rows.length >1){
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		}
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var title = selected.get('mortgagepersontypeforvalue');
			var thirdRalationId = selected.get('id');
			var contractId = selected.get('contractId');
			var categoryId = selected.get('categoryId');
			var temptId = selected.get('temptId');
			var window = new OperateContractWindow({
						title : title,
						businessType : businessType,
						piKey : piKey,
						thirdRalationId :thirdRalationId,
						/*contractId :contractId,
						categoryId :categoryId == null?0:categoryId,
						temptId :temptId,*/
						htType :'thirdContract',
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
	operateThirdContract : function(businessType, piKey) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var thisPanel = this.gridPanel;
		var isQSHidden = this.isSignHidden;
		var isqsEditVar = this.isqsEdit;
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if(rows.length >1){
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		}
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var title = '抵质押担保<<font color=red>'+selected.get('mortgagepersontypeforvalue')+'</font>>';
			var thirdRalationId = selected.get('id');
			var contractId = selected.get('contractId');
			var categoryId = selected.get('categoryId');
			var temptId = selected.get('temptId');
			var window = new OperateThirdContractWindow({
						title : title,
						businessType : businessType,
						piKey : piKey,
						thirdRalationId :thirdRalationId,
						contractId :contractId,
						categoryId :categoryId == null?0:categoryId,
						temptId :temptId,
						htType :'thirdContract',
						thisPanel : thisPanel,
						isHidden : false,
						isfwEdit : true,
						isHiddenAddBtn : false,
						isHiddenDelBtn : false,
						isHiddenEdiBtn : false,
						isgdHidden : this.isgdHidden,
						isgdEdit: this.isgdEdit,
						isqsHidden :isQSHidden,
						isqsEdit :isqsEditVar
					});
			window.show();
			window.addListener({
						'close' : function() {
							window.grid_contractPanel.stopEditing();
							thisPanel.getStore().reload();
						}
					});
		}
	},
	batchBJThirdContract : function(businessType,piKey){
		var thisPanel = this.gridPanel;
		var isfwEdit = this.isfwEdit;
		var isqsEdit = this.isqsEdit;
		var window = new BatchSignThirdContractWindow({
			businessType : businessType,
			piKey : piKey,
			htType :'thirdContract',
			thisPanel : thisPanel,
			isgdHidden : this.isgdHidden,
			isgdEdit: this.isgdEdit,
			isfwEdit:isfwEdit,
			isqsHidden:this.isqsHidden,
			isqsEdit:isqsEdit,
			isHidden:false
		});
		window.show();
		window.addListener({
			'close' : function() {
				window.grid_contractPanel.stopEditing();
				thisPanel.getStore().reload();
			}
		});			
	},
	qsThirdContract : function(businessType, piKey) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
		if(rows.length >1){
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		}
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var title = selected.get('mortgagename');
			var thirdRalationId = selected.get('id');
			var contractId = selected.get('contractId');
			var categoryId = selected.get('categoryId');
			var temptId = selected.get('temptId');
			var window = new OperateThirdContractWindow({
				title : title,
				businessType : businessType,
				piKey : piKey,
				thirdRalationId :thirdRalationId,
				contractId :contractId,
				categoryId :categoryId == null?0:categoryId,
				temptId :temptId,
				htType :'thirdContract',
				thisPanel : thisPanel,
				isHidden : false,
				isfwEdit : false,
				isHiddenAddBtn : true,
				isHiddenDelBtn : true,
				isHiddenEdiBtn : true,
				isqsHidden : false,
				isqsEdit : true
			});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});
		}
	},
	batchQSThirdContract : function(businessType,piKey){
		var thisPanel = this.gridPanel;
		var isfwEdit = this.isfwEdit;
		var isqsEdit = this.isqsEdit;
		var window = new BatchSignThirdContractWindow({
				businessType : businessType,
				piKey : piKey,
				htType :'thirdContract',
				thisPanel : thisPanel,
				isHidden:false,
				isfwEdit:isfwEdit,
				isqsEdit:isqsEdit
			});
			window.show();
			window.addListener({
				'close' : function() {
					thisPanel.getStore().reload();
				}
			});			
	},
	gdThirdContract : function(businessType, piKey) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var rows = this.gridPanel.getSelectionModel().getSelections();
		var thisPanel = this.gridPanel;
		if(rows.length >1){
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		}
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var title = selected.get('mortgagepersontypeforvalue');
			var thirdRalationId = selected.get('id');
			var contractId = selected.get('contractId');
			var categoryId = selected.get('categoryId');
			var temptId = selected.get('temptId');
			var window = new OperateThirdContractWindow({
					title : title,
					businessType : businessType,
					piKey : piKey,
					thirdRalationId :thirdRalationId,
					contractId :contractId,
					categoryId :categoryId == null?0:categoryId,
					temptId :temptId,
					htType :'thirdContract',
					thisPanel : thisPanel,
					isHidden : false,
					isfwEdit : false,
					isHiddenAddBtn : true,
					isHiddenDelBtn : true,
					isHiddenEdiBtn : true,
					isqsHidden : false,
					isgdHidden :false,
					isgdEdit : true
				});
			window.show();
			window.addListener({
				'close' : function() {
					window.grid_contractPanel.stopEditing();
					thisPanel.getStore().reload();
				}
			});
		}
	},
	batchGDThirdContract : function(businessType,piKey){
		var thisPanel = this.gridPanel;
		var isfwEdit = this.isfwEdit;
		var isqsEdit = this.isqsEdit;
		var window = new BatchSignThirdContractWindow({
				businessType : businessType,
				piKey : piKey,
				htType :'thirdContract',
				thisPanel : thisPanel,
				isgdHidden : this.isgdHidden,
				isgdEdit: this.isgdEdit,
				isfwEdit:isfwEdit,
				isqsHidden:false,
				isqsEdit : false,
				isHidden : this.isHidden,
				isFw : this.isFw
			});
			window.show();
			window.addListener({
				'close' : function() {
					window.grid_contractPanel.stopEditing();
					thisPanel.getStore().reload();
				}
			});			
	},
	seeThirdContract : function(businessType, piKey,isSignHidden,isgdHidden,isHidden) {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var thisPanel = this.gridPanel;
		var rows = this.gridPanel.getSelectionModel().getSelections();
		if(rows.length >1){
			Ext.ux.Toast.msg('状态', '请选择唯一一条记录!');
			return;
		}
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		} else {
			var title = selected.get('mortgagepersontypeforvalue');
			var thirdRalationId = selected.get('id');
			var contractId = selected.get('contractId');
			var categoryId = selected.get('categoryId');
			var temptId = selected.get('temptId');
			var window = new OperateThirdContractWindow({
				title : title,
				businessType : businessType,
				piKey : piKey,
				thirdRalationId :thirdRalationId,
				contractId :contractId,
				categoryId :categoryId == null?0:categoryId,
				temptId :temptId,
				htType :'thirdContract',
				thisPanel : thisPanel,
				isHidden : isHidden,
				isqsHidden : isSignHidden,
				isgdHidden :isgdHidden
			});
			window.show();
			window.addListener({
				'close' : function() {
					//thisPanel.getStore().reload();
				}
			});
		}
	},
	banliMortgage : function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
		var grid=this.gridPanel;
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
			var mortgageId = selected.get('id');
			var businessType=selected.get('businessType');
			new BanliMortgageWindow({
			    mortgageId:mortgageId,
			    businessType:businessType,
			    type:'DY',
			    gridPanel:grid,
			    isAllReadOnly:this.isAllReadOnly
		    }).show()
			
		}
	
	},
	relieveMortgage:function(){
		var selected = this.gridPanel.getSelectionModel().getSelected();
			var grid=this.gridPanel;
			if (null == selected) {
				Ext.ux.Toast.msg('状态', '请选择一条记录!');
			}else{
				var mortgageId = selected.get('id');
				 var businessType=selected.get('businessType');
			   
			    new RelieveMortgageWindow({
				    mortgageId:mortgageId,
				    businessType:businessType,
				    gridPanel:grid
			    }).show()
			}
	}
	
});
