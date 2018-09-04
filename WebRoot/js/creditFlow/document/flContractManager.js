/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
flContractManager = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		flContractManager.superclass.constructor.call(this, {
			id : 'ContractManager'+this.businessType,
			title : '合同管理'+this.getTitle(),
			region : 'center',
			layout : 'border',
			border : false,
			iconCls :'btn-tree-team31',
			items : [ this.searchPanel, this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var isShow=false; //不显示分公司查询条件
		if(RoleType=="control"){
		    isShow=true; //显示分公司查询条件
		}
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			region : 'north',
			height : 40,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
            scope:this,
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [ {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				hidden:true,
				scope:this,
				items : [ {
					xtype : 'textfield',
					name : "businessType",
					value:this.businessType
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目编号',
					anchor:'100%',
					name : 'projectNum'
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目名称',
					anchor:'95%',
					name : 'projectName'
				}]
			},isShow?{
			    columnWidth : 0.2,
			    layout : 'form',
			    border:false,
			   	labelWidth : 80,
				labelAlign : 'right',
			    items : [
			    {
			      xtype : "combo",
			      anchor : "98%",
			      fieldLabel : '所属分公司',
			      hiddenName : "companyId",
			      displayField : 'companyName',
			      valueField : 'companyId',
			      triggerAction : 'all',
			      store : new Ext.data.SimpleStore({
			       autoLoad : true,
			       url : __ctxPath+'/system/getControlNameOrganization.do',
			       fields : ['companyId', 'companyName']
			      })
			    }
			   ]}:{
			     columnWidth:0.01,
			     border:false
			    }, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					anchor:'100%',
					//width : 60,
					anchor:'100%',
					scope : this,
					iconCls : 'btn-search',
					handler : this.search
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					//width : 60,
					anchor:'100%',
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})
		this.topbar = new Ext.Toolbar({
			items : [{
				xtype : 'button',
				text : '打印借款借据'
			}]
		})
		this.gridPanel = new HT.GridPanel({
			border : false,
			region : 'center',
			hiddenCm:true,
			tbar : this.topbar,
			url : __ctxPath + "/creditFlow/fileUploads/contractListFileForm.do",
			fields : [ {
				name : 'runId',
				type : 'long'
			}, 'projectId', 'projectNumber', 'projectName', 'projectMoney',
					'businessManagerValue', 'activityName', 'createtime', 'endDate', 'contractCount',
					'morContractCount','businessType','companyName','otherFileCount','bzContractCount','repaymentDate','loanStartDate'],
			baseParams:{businessType:this.businessType,
			        projectNum:'',
			        projectName:''
			},
			columns : [ {
				header : 'id',
				dataIndex : 'runId',
				hidden : true
			}, {
				header : '分公司名称',
				hidden : !isShow,
				dataIndex : 'companyName'
			}, {
				header : '项目编号',
				dataIndex : 'projectNumber'
			}, {
				header : '项目名称',
				dataIndex : 'projectName'
			}, {
				header : '金额',
				align:'right',
				dataIndex : 'projectMoney',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return Ext.util.Format.number(value,'0,000.00')+"元"	
				}
			}, {
				header : '业务经理',
				dataIndex : 'businessManagerValue'
			}, {
				header : '项目阶段',
				dataIndex : 'activityName'
			}, {
				header : '贷款开始时间',
				dataIndex : 'loanStartDate'
			}, {
				header : '贷款结束时间',
				dataIndex : 'repaymentDate'
			}, {
				header : '合同(<font color=red>点击查看</font>)',
				dataIndex : 'contractCount',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return "<a href='#' title='点击查看详细' style='color:blue' ><u >"+value+"</u></a>"
				}
			}/*, {
				header : '抵质押担保合同(<font color=red >点击查看</font>)',
				dataIndex : 'morContractCount',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return "<a href='#' title='点击查看详细' style='color:blue' ><u  >"+value+"</u></a>"
				}
			}, {
				header : '保证担保合同(<font color=red >点击查看</font>)',
				dataIndex : 'bzContractCount',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return "<a href='#' title='点击查看详细' style='color:blue' ><u  >"+value+"</u></a>"
				}
			}, {
				header : '其他文件(<font color=red >点击查看</font>)',
				dataIndex : 'otherFileCount',
				renderer : function(value,metaData, record,rowIndex, colIndex,store) {
				
					return "<a href='#' title='点击查看详细' style='color:blue' ><u  >"+value+"</u></a>"
				}
			}*/]
		//end of columns
				});
       this.gridPanel.addListener('cellclick', this.cellClick);
	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	cellClick : function(grid,rowIndex,columnIndex,e){
		var businessType = grid.getStore().getAt(rowIndex).get('businessType');
		var piKey = grid.getStore().getAt(rowIndex).get('projectId');
		var projectName=grid.getStore().getAt(rowIndex).get('projectName');
		if(10==columnIndex){
            if(businessType=="SmallLoan"){
            	var sContractView=new SlContractView({projId:piKey,businessType:businessType,isSignHidden:false,isqsEdit:true,htType:'loanContract',isHiddenAffrim:true,isgdEdit:true,isHiddenAddBtn:false,isHiddenDelBtn:false,isHiddenEdiBtn:false,isHiddenRZZLHT:false})
				var win=new Ext.Window({
					height:200,
					width:940,
					autoScroll:true,
					border : false,
					modal:true,
					title:"【"+projectName+"】合同",
	                items:[sContractView]
				})
			    win.show()
			    win.addListener({
						'close' : function() {
							sContractView.getCmpByName("contractPanel_view").stopEditing();
							grid.getStore().reload();
						}
					});
            }else if(businessType=="Guarantee"){
            	var gContractView=new SlContractView({projId:piKey,businessType:businessType,isSignHidden:false,isqsEdit:true,htType:'guaranteeContract',isHiddenAffrim:true,isgdEdit:true,isHiddenAddBtn:false,isHiddenDelBtn:false,isHiddenEdiBtn:false})
            	var win=new Ext.Window({
					height:200,
					width:800,
					autoScroll:true,
					modal:true,
					title:"【"+projectName+"】合同",
	                items:[gContractView]
				})
			    win.show()
			    win.addListener({
						'close' : function() {
							gContractView.getCmpByName("contractPanel_view").stopEditing();
							grid.getStore().reload();
						}
					});
            }else if(businessType=="Financing"){
            	var fContractView=new SlContractView({projId:piKey,businessType:businessType,isSignHidden:false,isqsEdit:true,'financingContract':null,isHiddenAffrim:true,isgdEdit:true,isHiddenAddBtn:false,isHiddenDelBtn:false,isHiddenEdiBtn:false})
            	var win=new Ext.Window({
					height:200,
					width:800,
					autoScroll:true,
					modal:true,
					title:"【"+projectName+"】合同",
	                items:[fContractView]
				})
			    win.show()
			     win.addListener({
						'close' : function() {
							fContractView.getCmpByName("contractPanel_view").stopEditing();
							grid.getStore().reload();
						}
					});
            }else if(businessType=="Pawn"){//★
            	var flContractView=new SlContractView({projId:piKey,businessType:businessType,isSignHidden:false,isqsEdit:true,htType:'pawnContract',isHiddenAffrim:true,isgdEdit:true,isHiddenAddBtn:false,isHiddenDelBtn:false,isHiddenEdiBtn:false,isHiddenBZ:true,isHiddenDZY:true})
            	var win=new Ext.Window({
					height:200,
					width:800,
					autoScroll:true,
					modal:true,
					title:"【"+projectName+"】合同",
	                items:[flContractView]
				})
			    win.show()
			     win.addListener({
						'close' : function() {
							flContractView.getCmpByName("contractPanel_view").stopEditing();
							grid.getStore().reload();
						}
					});
            }else if(businessType=="LeaseFinance"){//★
            	var flContractView=new SlContractView({projId:piKey,businessType:businessType,isSignHidden:false,isqsEdit:true,htType:'leaseFinanceContract',isHiddenAffrim:true,isgdEdit:true,isHiddenAddBtn:false,isHiddenDelBtn:false,isHiddenEdiBtn:false,isHiddenRZZLHT:false})
            	var win=new Ext.Window({
					height:200,
					width:800,
					autoScroll:true,
					modal:true,
					title:"【"+projectName+"】合同",
	                items:[flContractView]
				})
			    win.show()
			     win.addListener({
						'close' : function() {
							flContractView.getCmpByName("contractPanel_view").stopEditing();
							grid.getStore().reload();
						}
					});
            }
	    }
	},
	getTitle:function(){
				var title="";
				if(this.businessType!=null){
					businessType=this.businessType
					switch (businessType) {
					  case "SmallLoan": title="-贷款业务";
					    break;
					  case "Financing": title="-融资业务";
					    break;
					  case "Guarantee": title="-担保业务";
					    break;
					  case "Pawn": title=" ";
					    break;
					  case "Investment": title=" ";
					    break;
					  case "LeaseFinance": title="-融资租赁业务";
					    break;
					  default: title="&fundType=('principalRepayment')";
					}
				}
				return title
				

			
	}
});
