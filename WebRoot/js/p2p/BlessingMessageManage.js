/**
 * OaNewsMessageType.js
 */
/**
 * @author
 * @class BlessingMessageManage
 * @extends Ext.Panel
 * @description 祝福短信管理
 * @createtime:20180807
 */
BlessingMessageManage = Ext.extend(Ext.Panel, {
	       title:"节日短信管理",
	       iconCls:"menu-finance",
			// 构造函数
			constructor : function(_cfg) {
				if (_cfg == null) {
					_cfg = {};
				}
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
                BlessingMessageManage.superclass.constructor.call(this, {
							id : 'BlessingMessageManage',
							title : this.title,
							region : 'center',
							layout : 'border',
							items : [this.searchPanel,this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
                this.searchPanel = new Ext.FormPanel({
                    autoWhith : true,
                    layout : 'column',
                    region : 'north',
                    border : false,
                    height : 50,
                    anchor : '100%',
                    layoutConfig : {
                        align : 'middle'
                    },
                    bodyStyle : 'padding:15px 0px 0px 0px',
                    items : [{
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80,
                        labelAlign : 'right',
                        border : false,
                        items : [{
                            fieldLabel : '节日名称',
                            name : 'Q_holidayName_S_LK',
                            anchor : "100%",
                            xtype : 'textfield'
                        }]
                    },{
                        columnWidth : .25,
                        layout : 'form',
                        labelWidth : 80,
                        labelAlign : 'right',
                        editable : false,
                        border : false,
                        items : [{
                            fieldLabel:'类型',
                            hiddenName : 'Q_type_N_EQ',
                            anchor : "100%",
                            xtype : 'combo',
                            displayField : 'itemName',
                            valueField : 'itemValue',
                            triggerAction : 'all',
                            anchor : '100%',
                            mode : 'local',
                            store : new Ext.data.ArrayStore({
                                fields : ['itemValue', 'itemName'],
                                data : [['1', '节日短信'],
                                    ['2', '生日短信']]
                            })
                        }]
                    }, {
                        columnWidth : .125,
                        layout : 'form',
                        border : false,
                        labelWidth : 60,
                        items : [{
                            text : '查询',
                            xtype : 'button',
                            scope : this,
                            style : 'margin-left:30px',
                            anchor : "90%",
                            iconCls : 'btn-search',
                            handler : this.search
                        }]
                    }, {
                        columnWidth : .125,
                        layout : 'form',
                        border : false,
                        labelWidth : 60,
                        items : [{
                            text : '重置',
                            style : 'margin-left:30px',
                            xtype : 'button',
                            scope : this,
                            anchor : "90%",
                            iconCls : 'btn-reset',
                            handler : this.reset
                        }]
                    }]
                });

				this.topbar = new Ext.Toolbar({
						items : [{
									iconCls : 'btn-add',
									text : '新增短信模板',
									xtype : 'button',
									scope : this,
									handler : this.createRs
								},{
									iconCls : 'btn-edit',
									text : '编辑短信模板',
									xtype : 'button',
									scope : this,
									handler : this.editRs
								},{
									iconCls : 'btn-del',
									text : '删除短信模板',
									xtype : 'button',
									scope : this,
									handler : this.removeRs
								}]
				});

				this.gridPanel=new HT.GridPanel({
					region:'center',
					tbar:this.topbar,
					id : 'BlessingMessageManagePanel',
					url : __ctxPath + "/p2p/listOaHolidayMessage.do",
					fields : [{
									name : 'id',
									type : 'int'
								}
									,'holidayName'
									,'message'
									,'state'
									,'type'
									,'holidayDate'
							],
					columns:[{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '节假日',
									align:'center',
									dataIndex : 'holidayName',
									width:80,
								},{
									header : '日期',
									align:'center',
									dataIndex : 'holidayDate',
                        			width:80,
								},{
									header : '状态',
									align:'center',
									dataIndex : 'state',
                        			width:80,
									renderer : function(val){
										if(val =="0"){
											return "关闭";
										}else if(val =="1"){
											return "激活";
										}
										return val;
									}
								},{
									header : '类型',
									align:'center',
									dataIndex : 'type',
                        			width:80,
									renderer : function(val){
										if(val =="1"){
											return "节日短信";
										}else if(val =="2"){
											return "生日短信";
										}
										return val;
									}
								},{
									header : '信息内容 ',
									align:'center',
                        			width:280,
									dataIndex : 'message'
								}]
				});
				
				this.gridPanel.addListener('rowdblclick',this.rowClick);
					
			},// end of the initComponents()

			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BlessingMessageManageForm({id:rec.data.id}).show();
				});
			},
			//创建记录
			createRs : function() {
				var panel=this.gridPanel
				new BlessingMessageManageForm({
					gridPanel:panel
				}).show();
			},
			//按选中记录删除记录
			removeRs : function() {
				var gridPanel = this.gridPanel;
				var selectRecords = gridPanel.getSelectionModel().getSelections();
				if (selectRecords.length == 0) {
					Ext.ux.Toast.msg("信息", "请选择要删除的记录！");
					return;
				}
				var ids = Array();
				for (var i = 0; i < selectRecords.length; i++) {
					ids.push(selectRecords[i].data.id);
				}
				Ext.Msg.confirm('信息确认', '您确认要删除所选记录吗？', function(btn) {
					if (btn == 'yes') {
						Ext.Ajax.request({
									url : __ctxPath + '/p2p/multiDelOaHolidayMessage.do',
									params : {
										ids : ids
									},
									method : 'POST',
									success : function(response, options) {
										Ext.ux.Toast.msg('操作信息','删除成功！');
										gridPanel.getStore().reload();
									},
									failure : function(response, options) {
										Ext.ux.Toast.msg('操作信息', '操作出错，请联系管理员！');
									}
								});
					}
				});
					
			},
			editRs:function(){
				var grid = this.gridPanel;
				var store = grid.getStore();
				var s = this.gridPanel.getSelectionModel().getSelections();
				if (s.length <= 0) {
					Ext.Msg.alert('状态', '请选中任何一条记录');
					return false;
				} else if (s.length > 1) {
					Ext.Msg.alert('状态', '只能选中一条记录');
					return false;
				} else {
					var record=s[0];
					new BlessingMessageManageForm({
						    gridPanel:grid,
							id  : record.data.id
						}).show();
				}
			},

    // 重置查询表单
    reset : function() {
        this.searchPanel.getForm().reset();
    },
    // 按条件搜索
    search : function() {
        $search({
            searchPanel : this.searchPanel,
            gridPanel : this.gridPanel
        });
    }

			
});
