ApprovalZM = Ext.extend(Ext.Panel, {
	layout : "form",
	autoHeight : true,
	autoWidth:true,
	header:false,
	title:false,
	projectId:null,
	border:false,
	constructor : function(_cfg) {
	
		if (_cfg == null) {
			_cfg = {};
		}
		if(typeof(_cfg.projectId)!="undifined"){
			this.projectId=_cfg.projectId
		}
		Ext.applyIf(this, _cfg);
		//this.BankGuaranteeMoney=new BankGuaranteeMoney({projectId:this.projectId})
		//this.BankLoanProgram=new BankLoanProgram({projectId:this.projectId})
	//	this.CustomerRepaymentPlan=new CustomerRepaymentPlan({projectId:this.projectId});
				this.gridPanel = new gt_FundIntent_formulate_editGrid({
			projId : this.projectId,
			businessType : 'Guarantee'
			
		});
		ApprovalZM.superclass.constructor.call(this, {
			
			items:[{
				xtype:'fieldset',
				collapsible : true,

				title:'客户银行借款合同',
				items:[new uploads({
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					hidden : false,
					titleName :'文件',
					isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
					setname :'客户借款合同',
					tableName :'gl_borrow_guarantee',
					typeisfile :'typeisglborrowguarantee',
					businessType:'Guarantee',
					uploadsSize : 15
				})]
			},{
				xtype:'fieldset',
				collapsible : true,
				title:'银行担保合同',
				items:[new uploads({
					hidden : false,
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					titleName :'文件',
					isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
					setname :'担保合同',
					tableName :'gl_db_guarantee',
					typeisfile :'typeisgldbguarantee',
					businessType:'Guarantee',
					uploadsSize : 15
					
				})]
			},{
				xtype:'fieldset',
				collapsible : true,
				title:'放款通知单扫描件',
				name : 'fangk',
				items:[new uploads({
					hidden : false,
					projectId : this.projectId,
					isHiddenColumn : false,
					isDisabledButton : false,
					isHiddenOnlineButton : true,
					titleName :'文件',
					isJKDB : true,//标识是否客户借款合同及担保合同，true为uploads方法中上传的文件类型为setname，否则为titleName
					setname :'放款通知单扫描件',
					tableName :'gl_db_guarantee',
					typeisfile :'tongzhidan',
					businessType:'Guarantee'				
				})]
			
			}/*,{
				xtype:'fieldset',
				collapsible : true,
				title:'银行放款计划表',
				items:[this.BankLoanProgram]
			}*/,{
				xtype:'fieldset',
				collapsible : true,
				hidden : false,
				title:'银行放款收息表',
				items:[this.gridPanel]
			}
			/*,{
				xtype:'fieldset',
				collapsible : true,
				title:'银行保证金',
				items:[this.BankGuaranteeMoney]
			}*//*,{
				name : 'BankLoanProgram',
				xtype : 'hidden'
			}*/,{
				name : 'CustomerRepaymentplan',
				xtype : 'hidden'
			},{
				name : 'projectId1',
				xtype : 'hidden'
			}]
		})
	}
		
})
		