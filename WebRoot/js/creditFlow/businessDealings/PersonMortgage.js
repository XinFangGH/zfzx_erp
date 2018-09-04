/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PersonMortgage = Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.personType)!="undefined"){
			this.personType=_cfg.personType
		}
		if(typeof(_cfg.assureofname)!="undefined"){
			this.assureofname=_cfg.assureofname
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PersonMortgage.superclass.constructor.call(this, {
			id : 'PersonMortgage',
			title : '作为第三方保证',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.topbar = new Ext.Toolbar({
			items : [ {
						iconCls : 'btn-detail',
						text : '查看抵质押物详细',
						xtype : 'button',
						scope : this,
						hidden : isGranted('_loanAccept_flow')? false: true,
						handler : this.seeMortgageInfo
					}]
		});

		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			hiddenCm:true,
			tbar:this.topbar,
			showPaging:false,
			url :  __ctxPath +"/credit/mortgage/getMorByPersonType.do?personType="+this.personType+"&assureofname="+this.assureofname,
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'businessType',
			'remarks',
			'assuretypeid',
			'mortgagepersontypeforvalue',
			'mortgagename',
			'finalprice',
			'transactDate',
			'needDatumList',
			'lessDatumRecord',
			'mortgagenametypeid'
			],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '项目名称',
				dataIndex : 'remarks',
				width:300
			}, {
				header : '担保类型',
				dataIndex : 'lessDatumRecord'
			}, {
				header : '抵质押物类型',
				dataIndex : 'mortgagepersontypeforvalue'
			}, {
				header : '抵质押物名称',
				dataIndex : 'mortgagename'
			
				
			}, {
				header : '最终估价',
				dataIndex : 'finalprice',
				align:'right',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			}, {
				header : '登记时间',
				align:'center',
				dataIndex : 'transactDate'
			}, {
				header : '状态',
				dataIndex : 'needDatumList',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
					if(value=="已结束"){
						return '<font color="red">已结束</font>'
					}else if(value=="进行中"){
						return '<font color="green">进行中</font>'
					}else{
						return ''
					}
				}
			}]
		//end of columns
				});
	},// end of the initComponents()
	seeMortgageInfo : function() {
		var selected = this.gridPanel.getSelectionModel().getSelected();
		if (null == selected) {
			Ext.ux.Toast.msg('状态', '请选择一条记录!');
		}else{
		var mortgageId = selected.get('id');
		var businessType = selected.get('businessType');
		var typeId = selected.get('mortgagenametypeid');
			if(typeId==1){
				seeCarInfo(mortgageId,businessType);
			}else if(typeId==2){
				seeStockownershipInfo(mortgageId,businessType);
			}else if(typeId==3){
				seeCompanyInfo(mortgageId,businessType);
			}else if(typeId==4){
				seeDutyPersonInfo(mortgageId,businessType);
			}else if(typeId==5){
				seeMachineInfo(mortgageId,businessType);
			}else if(typeId==6){
				seeProductInfo(mortgageId,businessType);
			}else if(typeId==7){
				seeHouseInfo(mortgageId,businessType);
			}else if(typeId==8){
				seeOfficeBuildingInfo(mortgageId,businessType);
			}else if(typeId==9){
				seeHouseGroundInfo(mortgageId,businessType);
			}else if(typeId==10){
				seeBusinessInfo(mortgageId,businessType);
			}else if(typeId==11){
				seeBusinessAndLiveInfo(mortgageId,businessType);
			}else if(typeId==12){
				seeEducationInfo(mortgageId,businessType);
			}else if(typeId==13){
				seeIndustryInfo(mortgageId,businessType);
			}else if(typeId==14){
				seeDroitUpdateInfo(mortgageId,businessType);
			}else{
				window.location.href="/error.jsp";
			}
		}
	}
});
