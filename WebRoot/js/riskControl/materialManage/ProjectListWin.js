/**
 * @author
 * @class BpArchivesBorrowView
 * @extends Ext.Panel
 * @description 项目信息
 * @company 智维软件
 * @createtime:
 */
ProjectListWin = Ext.extend(Ext.Window, {
	isHidden:true,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if(typeof(this.businessClass)=="undefined" || this.businessClass == "All"){
			this.isHidden = false;
		}
		this.isGrantedShowAllProjects =  isGranted('_seeAllPro_p7');
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		ProjectListWin.superclass.constructor.call(this, {
					id : 'ProjectListWin',
					title : '项目列表',
					region : 'center',
					layout : 'border',
					width:800,
					height:500,
					items : [this.searchPanel, this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var fromPage = this.fromPage;
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel({
			layout : 'form',
			border : false,
			region : 'north',
			height : 43,
			anchor : '70%',
			keys : [{
				key : Ext.EventObject.ENTER,
				fn : this.search,
				scope : this
			}, {
				key : Ext.EventObject.ESC,
				fn : this.reset,
				scope : this
			}],
			items:[{
				border : false,
				layout : 'column',
				style : 'padding-left:5px;padding-right:5px;padding-top:10px;',
				layoutConfig : {
					align : 'middle',
					padding : '5px'
				},
				defaults : {
					xtype : 'label',
					anchor : anchor
				},
				items : [{
					xtype : 'hidden',
					name:'projectStatus'
				},{
					xtype : 'hidden',
					name:'bmStatus'
				},{
					columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					hidden:this.isHidden,
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : "combo",
						fieldLabel : "业务类型",
						anchor : '100%',
						hiddenName : "businessTypeName",
						displayField : 'itemName',
						valueField : 'itemId',
						triggerAction : 'all',
						emptyText : "请选择",
						store : new Ext.data.SimpleStore({
							autoLoad : true,
							url : __ctxPath
									+ '/creditFlow/getBusinessTypeList1CreditProject.do',
							fields : ['itemId', 'itemName']
						}),
						listeners : {
							scope : this,
							afterrender : function(combox) {
								var st = combox.getStore();
								st.on("load", function() {
									
								})
								combox.clearInvalid();
							},
							select : function(combox, record, index) {
								var businessType = combox.getValue();
								combox.ownerCt.ownerCt.getCmpByName("businessType").setValue(businessType);
								var projectStatus = "";
								var bmStatus = "";
								if(fromPage == 'ArchivesBrrow'){//来自于档案借阅的调用
									if (null != businessType && businessType != "" ){
										if("SmallLoan" == businessType){//委贷
											projectStatus = "1,2,5,8";
										}else if("Guarantee" == businessType){//担保
											projectStatus = "1";
											bmStatus = "0,1,2,3";
										}else if("Pawn" == businessType){//典当
											projectStatus = "1,4,5,6";
										}
										combox.ownerCt.ownerCt.getCmpByName("businessType").setValue(businessType);
								    }
								}else if(fromPage == 'GeneralizedFlowsheet'){//来自于通用流程的调用
									
								}
								//把状态放在隐藏域中
								if(projectStatus!=""){
									combox.ownerCt.ownerCt.getCmpByName("projectStatus").setValue(projectStatus);
									combox.ownerCt.ownerCt.getCmpByName("bmStatus").setValue(bmStatus);
									this.search();
								}
							}
						}
					},{
						name:'businessType',
						xtype:'hidden'
					}]
				},{
					columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '项目名称',
						anchor : '100%',
						name : 'projectName'
					}]
				},{
					columnWidth : .25,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 60,
					defaults : {
						anchor : anchor
					},
					items : [{
						xtype : 'textfield',
						fieldLabel : '项目编号',
						anchor : '100%',
						name : 'projectNumber'
					}]
				},{
					columnWidth : .12,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 5,
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '查询',
						fieldLabel : ' ',
						labelSeparator : "",
						scope : this,
						iconCls : 'btn-search',
						handler : this.search,
						anchor:'80%'
					}]
				}, {
					columnWidth : .12,
					labelAlign : 'right',
					xtype : 'container',
					layout : 'form',
					labelWidth : 15,
					defaults : {
						xtype : 'button'
					},
					items : [{
						text : '重置',
						fieldLabel : '',
						labelSeparator : "",
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset,
						anchor:'80%'
					}]
				}]
			}]
		});// end of searchPanel
		this.gridPanel = new HT.GridPanel({
			isShowTbar:false,
			region : 'center',
			hiddenCm:true,
			url : __ctxPath + "/fund/projectListQueryBpFundProject.do?isGrantedShowAllProjects="+this.isGrantedShowAllProjects,
			fields : [{
						name : 'id',
						type : 'long'
					}, 'projectNumber', 'projectName', 'oppositeTypeName','projectId',
					'appUserId', 'appUserName', 'projectStatus', 'businessType'],
			columns : [{
						dataIndex : 'id',
						hidden : true
					},{
						dataIndex : 'projectId',
						hidden : true
					},{
						header : '项目业务类型',
						dataIndex : 'businessType',
						hidden : true
					}, {
						header : '项目编号',
						width:40,
						dataIndex : 'projectNumber'
					}, {
						header : '项目名称',
						width:140,
						dataIndex : 'projectName'
					},{
						header : '客户类型',
						width:20,
						dataIndex : 'oppositeTypeName'
					}, {
						header : '项目经理',
						width:40,
						dataIndex : 'appUserName'
					}],
				listeners : {
					scope : this,
					'rowdblclick':function(grid,index,e){
						//得到记录的值
						var record = grid.store.getAt(index);
						var projectId = record.data.projectId;
						//获得项目编号，项目名称
						//根据列名获取值
//						var projectNumber = record.get("projectNumber");
						var projectName = record.data.projectName;
						var businessType = record.data.businessType;
						if (this.callback){
							this.callback.call(this.scope,projectId, projectName, businessType,record);
						}
						this.close();
					}
				}
				// end of columns
			});
		this.gridPanel.addListener('rowdblclick', this.rowClick);

	},// end of the initComponents()
	// 重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
		//重置的时候会把searchPanel中的businessTypeName字段重置为空
		this.getCmpByName("businessType").setValue(this.businessClass);
	},
	// 按条件搜索
	search : function() {
		$search({
					searchPanel : this.searchPanel,
					gridPanel : this.gridPanel
				});
	},
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new BpArchivesBorrowForm({
								borrowId : rec.data.borrowId
							}).show();
				});
	}
});
