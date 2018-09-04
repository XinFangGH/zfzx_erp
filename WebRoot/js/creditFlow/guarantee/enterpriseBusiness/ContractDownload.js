
/**
 * @author:chencc
 * @class SlContractQSView
 * @extends Ext.Panel
 * @description [SlContractQSView]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */

ContractDownload = Ext.extend(Ext.Panel, {
	projectId : null,
	// 构造函数
	businessType:6584,
	constructor : function(_cfg) {
		if(typeof(_cfg.projectId)!="undefined")
		{
		      this.projectId=_cfg.projectId;
		}
		if(typeof(_cfg.businessType)!="undefined")
		{
		      this.businessType=_cfg.businessType;
		}

		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ContractDownload.superclass.constructor.call(this, {

					region : 'center',
					layout : 'anchor',
					items : [this.grid_contractQSPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
        
		var projId = this.projectId;
		var piKey = this.projectId;
		var businessType = this.businessType
        
	   DownFiles = function(id,piKey,contractName,contractId,mortgageId){
			var mark = "cs_procredit_contract."+contractId;
			uploadfile('下载附件',mark,0,null,null,null);
		};
		
		
		downloadContract = function(conId){
		var pbar = Ext.MessageBox.wait('数据读取中','请等待',{
			interval:200,
	    	increment:15
		});

		window.open(__ctxPath+"/contract/lookUploadContractProduceHelper.do?conId="+conId,'_blank');
		pbar.getDialog().close();

	};
		
		this.render_contractInfo = Ext.data.Record.create([{name : 'id'},{name : 'parentId'}, {name : 'contractCategoryTypeText'}, {name : 'number'}, {name : 'projId'}, {name : 'mortgageId'}, {name : 'isOld'}, {name : 'remark'},{name : 'isUpload'},{name : 'issign'},{name : 'signDate'},{name : 'isAgreeLetter'},{name : 'isSeal'},{name : 'contractCategoryText'},{name : 'contractId'},{name : 'contractName'},{name : 'contractNumber'},{name : 'text'},{name :'draftName'},{name :'draftDate'},{name : 'localParentId'},{name : 'templateId'},{name : 'isLegalCheck'},{name : 'verifyExplain'},{name :'orderNum'},{name :'fileCount'}]);
		this.jStore_contractCategroyQS = new Ext.data.GroupingStore({
			proxy : new Ext.data.HttpProxy({url : __ctxPath+ '/contract/getContractTreeProcreditContract.do'}) ,
            reader: new Ext.data.JsonReader({
            	fields : this.render_contractInfo,
            	totalProperty : 'totalProperty',
            	root : 'topics'
            }),
            groupField:'contractCategoryTypeText'
        });
		this.jStore_contractCategroyQS.load({
			params : {
				projId : projId,
				businessType:businessType
			}
		});

		this.grid_contractQSPanel = new HT.EditorGridPanel({

			store : this.jStore_contractCategroyQS,
			autoExpandColumn : 'contractCategoryText',
			autoScroll : true,
			autoWidth : true,
			layout : 'anchor',
			clicksToEdit : 1,
			
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			isShowTbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
			
			columns : [{
				header : '合同名称',
				dataIndex : 'contractCategoryText'
			}, {
						header : '合同下载',
						sortable : true,
						renderer : function(val, m, r) {
				          
								if(r.get('contractId')==''||r.get('contractId')==0){
									return '';
								}else{
								
									return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downloadContract(\''+r.get('contractId')+'\')" >下载</a>';//个人贷款合同
								}
							
						}
					}, {
						header : '附件下载',
						dataIndex : 'fileCount',
						sortable : true,
						renderer : function(val,m,r){
							return  '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="DownFiles(\''+r.get('id')+'\',\''+piKey+'\',\''+r.get('contractCategoryText')+'\',\''+r.get('contractId')+'\',\''+r.get('mortgageId')+'\')">'+r.get('fileCount')+'</a>';
						}
					}]
		});

		

	}// end of the initComponents()
	
});
	

