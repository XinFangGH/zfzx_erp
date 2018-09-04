/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [SlCompanyMain]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
ContractQuery = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ContractQuery.superclass.constructor.call(this, {
			id:'ContractQuery',
			title : '合同查询',
			region : 'center',
			layout : 'border',
			iconCls :'btn-tree-team2',
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
			border : false,
			height : 40,
			anchor : '100%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:10px 10px 10px 10px',
			items : [ {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				hidden:true,
				items : [ {
					xtype : "combo",
					hiddenName : "businessType",
					id:'businessTypeQuery',
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					readOnly:true,
					store : new Ext.data.SimpleStore({
						autoLoad : true,
						url : __ctxPath
								+ '/creditFlow/getBusinessTypeList1CreditProject.do',
						fields : ['itemId', 'itemName']
					}),
					fieldLabel : "业务类型",
					anchor : '100%',
					listeners : {
						scope : this,
						'afterrender' : function(com){
							var st=com.getStore();
							st.on('load',function(){
								com.setValue(st.getAt(0).data.itemId)
							})
						}
					}
				}]
			},{
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
			
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目编号',
					anchor : '100%',
					name : 'projectNum'
				}]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '项目名称',
					anchor : '100%',
					name : 'projectName'
				}  ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '合同编号',
					anchor : '100%',
					name : 'contractNum'
				}]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : 'textfield',
					fieldLabel : '备注',
					anchor : '100%',
					name : 'searchRemark'
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
			   	  columnWidth:0.001,
			      border:false
			     },{
				columnWidth : 0.1,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					//width : 60,
					anchor:'100%',
					scope : this,
					style:'margin-left:22',
					iconCls : 'btn-search',
					handler : this.search
				} ]
			} , {
				columnWidth : 0.1,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					//width : 60,
					anchor:'100%',
					scope : this,
					style:'margin-left:23',
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			} ]

		})

		this.topbar = new Ext.Toolbar( {
			items : [{
				iconCls : 'btn-readdocument',
				text : '查看项目详情',
				xtype : 'button',
				hidden : isGranted('_seeProjectInfo')?false:true,
				scope:this,
				handler : this.seeProjectInfo
			}]
		});
       var  projectName=function(data, cellmeta, record){
       	   
           return '<font color="blue">['+record.get("projectNumber")+']</font>'+record.get("projectName");

       }
       
       this.reader = new Ext.data.JsonReader({
			root : "result",
			totalProperty : "totalCounts"
			}, [{
						name : 'id',
						type : 'int'
					}, 'contractCategoryTypeText','contractCategoryText','contractId', 
			           'contractNumber','contractName','issign','signDate','isLegalCheck',
			           'projId','businessType','htType','projectName','projectNumber','companyName','fileCount','searchRemark']);

       this.jStore_mortgage = new Ext.data.GroupingStore({
			url : __ctxPath + "/creditFlow/fileUploads/queryContractFileForm.do",
			reader : this.reader,
			autoLoad:true,
			baseParams:{businessType:'SmallLoan'},
			groupField : 'projId'
		});
		
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			store:this.jStore_mortgage,
			hiddenCm:true,
			width:'100%',
			view : new Ext.grid.GroupingView({
				forceFit : true,
				groupTextTpl : '{text}'
			
			}),

			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : "分公司名称",
				dataIndex : "companyName",
				hidden : !isShow
		   }, {
				header : "项目名称",
				dataIndex : "projId",
				hidden:true,
				renderer : projectName
		   }, {
				header : "合同编号",
				dataIndex : "contractNumber"
		   }, {
				header : '合同类型',
				dataIndex : 'contractCategoryTypeText',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
				
			}, {
				header : '合同名称',
				dataIndex : 'contractName',
				width:120,
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	                metadata.attr = ' ext:qtip="' + data + '"';
	                return data;
	            }
			}, {
				header : '合同下载',
				align:'center',
				dataIndex : 'assuretypeidValue',
				renderer : function(val, m, r) {
					if (r.get('contractName') == null || r.get('contractName') == '') {
						return '';
					} else {
						return '<a title="下载合同" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="ContractQuery.downloadContract(\''
								+ r.get('id') + '\')" >下载</a>';// 个人贷款合同
					}

				}
			}, {
				header : '附件下载',
				align:'center',
				dataIndex : 'fileCount',
				renderer : function(val, m, r) {
					if (r.get('contractName') == null || r.get('contractName') == '') {
						return '';
					} else {
						return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downsFiles('+ r.get('id')+','+r.get('projId')+',\''+ businessType+ '\',0)">'+ r.get('fileCount') + '</a>';
					}
				}
			}, {
				header : '是否法务确认',
				align:'center',
				dataIndex : 'isLegalCheck',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	               if(data==true){
	                  return '是';
	               }else{
	               	  return '否';
	               }
	            }
			}, {
				header : '是否签署',
				align:'center',
				dataIndex : 'issign',
				renderer : function(data, metadata, record,
	                    rowIndex, columnIndex, store) {
	               if(data==true){
	                  return '是';
	               }else{
	               	  return '否';
	               }
	            }
			}, {
				header : '签署时间',
				align:'center',
				dataIndex : 'signDate'
			}/*,{
				header : '备注',
				dataIndex : 'searchRemark'
			}*/]
		//end of columns
				});

		this.gridPanel.addListener('cellclick', this.cellClick);
		ContractQuery.downloadContract = function(conId) {
			var pbar = Ext.MessageBox.wait('数据读取中', '请等待', {
						interval : 200,
						increment : 15
					});
			window.open(__ctxPath + "/contract/lookUploadContractProduceHelper.do?conId="
							+ conId, '_blank');
			pbar.getDialog().close();
		};
		downsFiles = function(contractId,piKey,businessType,cfileCount){
			var reloadStore = function(){tPanel.getStore().reload();}
			var mark = "cs_procredit_contract."+contractId;
			uploadfileContract('上传/下载附件',mark,cfileCount,null,null,contractId,reloadStore,piKey,businessType);
		}
	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
		this.searchPanel.getCmpByName('businessType').setValue('SmallLoan')
	},
	//按条件搜索
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
		seeProjectInfo:function(){
			var selected = this.gridPanel.getSelectionModel().getSelected();
				if (null == selected) {
					Ext.ux.Toast.msg('状态', '请选择一条记录!');
				}else{

				var projectId = selected.get('projId');
				var businessType = selected.get('businessType');

					var idPrefix = "";
					if (businessType == 'SmallLoan') {
						idPrefix = "SmallLoanProjectInfo_";
					} else if (businessType == 'Financing') {
						idPrefix = "FinancingProjectInfo_";
					} else if (businessType == 'Guarantee') {
						idPrefix = "GuaranteeProjectInfo_";
					}
					Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/getProjectViewObjectCreditProject.do',
						params : {
							businessType : businessType,
							projectId : projectId
						},
						method : 'post',
						success : function(resp, op) {
							var record = Ext.util.JSON.decode(resp.responseText);//JSON对象，root为data,通过record对象取数据必须符合"record.data.name"格式
							showProjectInfoTab(record, idPrefix)
						},
						failure : function() {
							Ext.ux.Toast.msg('信息提示', '出错，请联系管理员！');
						}
					})
				
			}
		}
});
