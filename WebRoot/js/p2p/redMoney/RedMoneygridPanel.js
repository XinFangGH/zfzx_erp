/**
 * @author
 * @class BpCustRedEnvelopeView
 * @extends Ext.Panel
 * @description [BpCustRedEnvelope]管理
 * @company 智维软件
 * @createtime:
 */
RedMoneygridPanel = Ext.extend(Ext.Panel, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				RedMoneygridPanel.superclass.constructor.call(this, {
							id : 'RedMoneygridPanel'+this.type,
							title : this.type=='ed'?"已派发":"未派发",
							region : 'center',
							layout : 'border',
							items : [ this.gridPanel]
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
							              
          this.sharteequitybar = new Ext.Toolbar({
				items : [
					{
						iconCls : 'btn-add',
						text : '增加',
						xtype : 'button',
						scope : this,
						handler : this.createShareequity,
						hidden:this.isreadOnly
							
					},new Ext.Toolbar.Separator({
						hidden : this.isreadOnly
					}),{
						iconCls : 'btn-del',
						text : '删除',
						xtype : 'button',
						scope : this,
						handler : this.deleteShareequity,
						hidden:this.isreadOnly
					},{
						iconCls : 'btn-distribute',
						text : '派发',
						xtype : 'button',
						scope : this,
						handler : this.distributemany,
						hidden:this.type=='ed'?true:false
							
					}]
				})
				
				var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '总计(元)';
		}
				this.gridPanel = new HT.EditorGridPanel({
			rowActions : false,
			region : 'center',
			bodyStyle : "width : 100%",
			id : 'SlFundQlideGridcheck'+this.type,
			isautoLoad:false,
			tbar :this.sharteequitybar,
			plugins : [summary],
			bbar : false,
		//	tbar : this.topbar,
			url : __ctxPath
					+ "/customer/listbyredIdBpCustRedMember.do?redId="+this.redId+"&type="+this.type,
			fields : [{
						name : 'redTopersonId',
						type : 'int'
					}, 'redId', 'bpCustMemberId','redMoney','loginname', 'distributeTime','email','telphone','truename','edredMoney','thirdPayFlag0'
				],
			columns : [{
						header : 'redTopersonId',
						dataIndex : 'redTopersonId',
						hidden : true
					},{
						header : 'bpCustMemberId',
						dataIndex : 'bpCustMemberId',
						hidden : true
					}, {
						header : '登陆名',
						dataIndex : 'loginname',
						width : 130,
						summaryType : 'count',
						summaryRenderer : totalMoney,
						editor: new Ext.form.ComboBox({
						    triggerClass : 'x-form-search-trigger',
							resizable : true,
							mode : 'remote',
							editable : false,
							lazyInit : false,
							allowBlank : false,
							typeAhead : true,
							readOnly:this.isreadOnly,
							minChars : 1,
							width : 100,
							listWidth : 150,
							store : new Ext.data.JsonStore({}),
							triggerAction : 'all',
							onTriggerClick : function() {
							
							var selectPersonObj =function(obj) {
	                            var grid = Ext.getCmp("SlFundQlideGridcheck");
	                             grid.getSelectionModel().getSelected().data['bpCustMemberId'] = obj.id;
	                            grid.getSelectionModel().getSelected().data['loginname'] = obj.loginname;
									grid.getSelectionModel().getSelected().data['truename'] = obj.truename;
	                    			grid.getSelectionModel().getSelected().data['telphone'] = obj.telphone;
	                    			grid.getSelectionModel().getSelected().data['email'] = obj.email;
	                    			grid.getSelectionModel().getSelected().data['thirdPayFlag0'] = obj.thirdPayFlag0;
							        grid.getView().refresh();
									
							
								}
								selectCustMember(selectPersonObj);
												
							},
						
					listeners : {
						'select' : function(combo,record,index) {
							grid.getView().refresh();
						},
						'blur' : function(f) {
							if (f.getValue() != null && f.getValue() != '') {
							}
						}
					}
						})

						

					}
			, {
						header : '真实姓名',	
						dataIndex : 'truename',
						width : 85
					}
					, {
						header : '第三方账号',	
						dataIndex : 'thirdPayFlag0',
						width : 85
					
					}, {
						header : '电话',
						dataIndex : 'telphone',
						width : 85
					
					}, {
						header : '邮箱',
						dataIndex : 'email'
						
					},

					
					{
						header : '金额',
						align : 'right',
						dataIndex : 'redMoney',
						summaryType : 'sum',
							editor : {
							xtype : 'numberfield',
							//readOnly:this.isreadOnly,
							readOnly:false,
							allowBlank : false
						},
						renderer:function(v){
						return Ext.util.Format.number(v,
											',000,000,000.00')
											+ "元"
						
						}
					}, {
						header : '派发时间',
						dataIndex : 'distributeTime',
						hidden:this.type=="not"?true:false
						
					}]
				// end of columns
			});
			if(this.redId!=""){
				this.gridPanel.getStore().reload();
			}},// end of the initComponents()
			//重置查询表单
			reset : function(){
				this.searchPanel.getForm().reset();
			},
			//按条件搜索
			search : function() {
				$search({
					searchPanel:this.searchPanel,
					gridPanel:this.gridPanel
				});
			},
	distributemany :function() {
					
	  var gp=this.gp;		
		var redId=this.redId;
		var s = this.gridPanel.getSelectionModel().getSelections();
		var this1=this.obj;
		  if (s <= 0) {
			Ext.ux.Toast.msg('操作信息','请选中一条记录');
			return false;
		}
		var qlidePanel = this.gridPanel;
		  var ids = $getGdSelectedIds(qlidePanel,'redTopersonId');
					Ext.MessageBox.confirm('确认', '确定派发吗', function(btn) {
					if (btn == 'yes') {
			       Ext.Ajax.request( {
									url : __ctxPath + '/customer/distributeBpCustRedEnvelope.do',
									method : 'POST',
									scope : this,
									params : {
										ids : ids,
										redId:redId
									},
									success : function(response, request) {
										this.infoObj = Ext.util.JSON.decode(response.responseText);
										Ext.ux.Toast.msg('操作信息', infoObj.msg);
										var gridPanel = gp;
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
						             	var gridPanel1 = Ext.getCmp('IngRedEnvelopeGrid'
												 );
										var gridPanel2 = Ext
												.getCmp('EdRedEnvelopeGrid');
		
										if (gridPanel1 != null) {
											gridPanel1.getStore().reload();
										}
										if (gridPanel2 != null) {
											gridPanel2.getStore().reload();
										}
							                   this1.close();
							                   
										

									},
									checkfail:function(response, request) {
										Ext.Msg.alert('状态', "派发失败");
										var gridPanel = parentGrid||Ext.getCmp('SlActualToChargeGrid');//update by gao
                                       var gridPanel = gp;
                                        if (gridPanel != null) {
								         gridPanel.getStore().reload();
						             	}
							                   this1.close();
										

										

									}
								});
				
					}
			
				})	
				
		},
			//GridPanel行点击处理事件
			rowClick:function(grid,rowindex, e) {
				grid.getSelectionModel().each(function(rec) {
					new BpCustRedEnvelopeForm({redId:rec.data.redId}).show();
				});
			},
			//创建记录
			createRs : function() {
				new BpCustRedEnvelopeForm().show();
			},
			//按ID删除记录
			removeRs : function(id) {
				$postDel({
					url:__ctxPath+ '/p2p.redMoney/multiDelBpCustRedEnvelope.do',
					ids:id,
					grid:this.gridPanel
				});
			},
			//把选中ID删除
			removeSelRs : function() {
				$delGridRs({
					url:__ctxPath + '/p2p.redMoney/multiDelBpCustRedEnvelope.do',
					grid:this.gridPanel,
					idName:'redId'
				});
			},
			//编辑Rs
			editRs : function(record) {
				new BpCustRedEnvelopeForm({
					redId : record.data.redId
				}).show();
			},
			//行的Action
			onRowAction : function(grid, record, action, row, col) {
				switch (action) {
					case 'btn-del' :
						this.removeRs.call(this,record.data.redId);
						break;
					case 'btn-edit' :
						this.editRs.call(this,record);
						break;
					default :
						break;
				}
			}
});
