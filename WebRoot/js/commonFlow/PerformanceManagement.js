/**
 * @author:
 * @class SlCompanyMainView
 * @extends Ext.Panel
 * @description [业绩管理]
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
PerformanceManagement = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		PerformanceManagement.superclass.constructor.call(this, {
			title : '业绩管理',
			region : 'center',
			layout : 'border',
			items : [ this.searchPanel,this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
		this.searchPanel = new Ext.FormPanel( {
			layout : 'column',
			region : 'north',
			height : 90,
			anchor : '96%',
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
			
				items : [ {
					xtype : "combo",
					anchor : "100%",
					hiddenName:"businessType",
					displayField : 'itemName',
				    valueField : 'itemId',
				    triggerAction : 'all',
				    allowBlank:false,
				    store : new Ext.data.SimpleStore({
						autoLoad : false,
						baseParams : {
							parentId : slbusinesstype
						},
						url : __ctxPath+ '/system/getByParentIdDicAreaDynam.do',
						fields : ['itemId', 'itemName']
					}),
					fieldLabel : "业务类别",
					blankText : "业务类别不能为空，请正确填写!",
					emptyText : "请选择",
					editable : false,
					listeners : {
						afterrender : function(combox) {
							combox.clearInvalid();
						},
						select:function(combox, record, index)
						{
						      	var v = record.data.itemId;
							    var arrStore = new Ext.data.ArrayStore({
								url : __ctxPath	+ '/system/getByParentIdDicAreaDynam.do',
								fields : ['itemId', 'itemName'],
								baseParams : {
									parentId : v
								}
							    });
							    var opr_obj=this.ownerCt.ownerCt.getCmpByName('operationType')
							    opr_obj.clearValue();
							    opr_obj.store = arrStore;
							    arrStore.load();
							    if (opr_obj.view) { // 刷新视图,避免视图值与实际值不相符
								         opr_obj.view.setStore(arrStore);
							    }
						}
					}
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {

					fieldLabel : "业务品种",
					xtype : "combo",
					emptyText : "请选择",
					displayField : 'itemName',
					valueField : 'itemId',
					triggerAction : 'all',
					allowBlank:false,
					mode : 'local',
					store : new Ext.data.SimpleStore({
						fields : ['displayText', 'value']
					}),
					hiddenName : "operationType",
					editable : false,
					blankText : "业务品种不能为空，请正确填写!",
					anchor : "100%"
				
				} ]
			}, {
				columnWidth : 0.17,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'datefield',
					format:'Y-m-d',
					fieldLabel : '时间',
					name:'startTime',
					allowBlank : false,
					width:110

				} ]
			}, {
				columnWidth : 0.02,
				layout : 'form',
				border : false,
				labelWidth : 80,
				labelAlign : 'right',
				items : [ {
					xtype : 'label',
					text:'--'
				} ]
			} ,{
	    		columnWidth:0.12,
	    		layout:'form',
	    		border:false,
	    		width:10,
	    
	    		items:[{
	    			xtype:'datefield',
	    			fieldClass:'field-align',
                    format:'Y-m-d',
                    name:'endTime',
                    allowBlank : false,
	    			hideLabel:true
	    		}]
	    	}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype : "combo",
					triggerClass : 'x-form-search-trigger',
					hiddenName : "department",
					fieldLabel : "部门",
					readOnly : this.isAllReadOnly,
					anchor : "100%",
					onTriggerClick : function(cc) {
						var obj = this;
						new department({
							callback : function(orgId, orgName) {
							obj.setValue(orgId);
							obj.setRawValue(orgName);
						}}).show();

					}
				
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {
					xtype:'combo',
					mode : 'local',
				    displayField : 'itemName',
				    valueField : 'itemId',
				    emptyText:'请选择',
					store:new Ext.data.SimpleStore({
						fields:["itemName","itemId"],
						data:[["贷前项目","0"],
						      ["贷中项目","1"],
						      ["已完成项目","2"],
						      ["所有状态","3"]]
					}),
				    triggerAction : "all",
				    fieldLabel:'项目状态',
				    name:'projectStatus'
				
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,
				labelWidth : 60,
				labelAlign : 'right',
				items : [ {

				     xtype:'textfield',
				     fieldLabel:'姓名',
				     name:'userName'
				
				} ]
			}, {
				columnWidth : 0.07,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '查询',
					width : 60,
					scope : this,
					iconCls : 'btn-search',
					handler : function(){
					   this.gridPanel.getStore().baseParams["businessType"]=this.searchPanel.getCmpByName('businessType').getValue();
					   this.gridPanel.getStore().baseParams["operationType"]=this.searchPanel.getCmpByName('operationType').getValue();
					   this.gridPanel.getStore().baseParams["startTime"]=this.searchPanel.getCmpByName('startTime').getValue();
					   this.gridPanel.getStore().baseParams["endTime"]=this.searchPanel.getCmpByName('endTime').getValue();
					   this.gridPanel.getStore().baseParams["departmentId"]=this.searchPanel.getCmpByName('department').getValue();
					   this.gridPanel.getStore().baseParams["projectStatus"]=this.searchPanel.getCmpByName('projectStatus').getValue();
					   this.gridPanel.getStore().baseParams["userName"]=this.searchPanel.getCmpByName('userName').getValue();
					   this.gridPanel.getStore().baseParams["state"]=0;
					   this.gridPanel.getStore().load();
				}
				} ]
			}, {
				columnWidth : 0.2,
				layout : 'form',
				border : false,

				labelAlign : 'right',
				items : [ {
					xtype : 'button',
					text : '重置',
					width : 60,
					scope : this,
					iconCls : 'btn-reset',
					handler : this.reset
				} ]
			},{
				columnWidth:1,
				layout :'form',
				border:false,
				labelAlign:'right',
				items:[{
					columnWidth:1,
					layout :'column',
					border:false,
					labelAlign:'right',
					 
					items:[{
						columnWidth:0.25,
						layout :'form',
						border:false,
						labelAlign:'right',
						labelWidth:60,
						items:[{
							xtype:'combo',
							mode : 'local',
						    displayField : 'itemName',
						    valueField : 'itemId',
						    emptyText:'请选择',
							store:new Ext.data.SimpleStore({
								fields:["itemName","itemId"],
								data:[["项目数量","0"],
								      ["项目总额","1"],
								      ["利息总额","2"],
								      ["利润率","3"],
								      ["未还资金总额","4"]]
							}),
						    triggerAction : "all",
						    fieldLabel:'统计表'
						}]
					},{
						columnWidth:0.2,
						layout :'form',
						border:false,
						labelAlign:'right',
						items:[{
							xtype:'button',
							text:'生成统计表'
						}]
					}]
					
				}]
			}  ]

		})
       

		this.gridPanel = new HT.GridPanel( {
			
			region : 'center',
			url : __ctxPath + "/creditFlow/ourmain/listSlCompanyMain.do",
			fields : [ {
				name : 'companyMainId',
				type : 'int'
			}, 'corName', 'simpleName', 'lawName', 'organizeCode',
					'businessCode', 'haveCharcter', 'tax', 'tel', 'mail',
					'messageAddress' ],
			columns : [ {
				header : 'id',
				dataIndex : 'companyMainId',
				hidden : true
			}, {
				header : '姓名',
				dataIndex : 'corName'
			}

			, {
				header : '部门',
				dataIndex : 'organizeCode'
			}, {
				header : '岗位',
				dataIndex : 'businessCode'
			}, {
				header : '业务品种',
				dataIndex : 'tel'
			}, {
				header : '项目个数',
				dataIndex : 'haveCharcter'
			}, {
				header : '项目总金额',
				dataIndex : 'lawName'
			}, {
				header : '利息总额',
				dataIndex : 'lawName'
			}, {
				header : '利润',
				dataIndex : 'lawName'
			}, {
				header : '利润率',
				dataIndex : 'lawName'
			}, {
				header : '已还本金',
				dataIndex : 'lawName'
			}, {
				header : '已还利息',
				dataIndex : 'lawName'
			}, {
				header : '未还金额',
				dataIndex : 'lawName'
			}, {
				header : '操作',
				renderer:function(){
				    return '<a>查看明细</a>'
			    }
				
			}]
		//end of columns
				});


	},// end of the initComponents()
	//重置查询表单
	reset : function() {
		this.searchPanel.getForm().reset();
	}
	
});
