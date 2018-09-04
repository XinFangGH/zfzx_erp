/**
 * MemberGradeSet.js
 */
 
MemberGradeSet= Ext.extend(Ext.Panel, {

	// 构造函数
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MemberGradeSet.superclass.constructor.call(this, {
					id : 'MemberGradeSet' ,
					title : "用户积分等级设置",
					region : 'center',
					layout : 'border',
					 iconCls:"menu-finance",
//					items : [this.searchPanel, this.gridPanel],
					items : [ this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};


		this.topbar = new Ext.Toolbar({
				items : [{
					iconCls : 'btn-add',
					text : '添加用户等级',
					xtype : 'button',
					scope : this,
					handler : this.addNewRule
				},new Ext.Toolbar.Separator({
							
					}),{
					iconCls : 'btn-edit',
					text : '修改用户等级',
					xtype : 'button',
					scope : this,
					handler : this.editNewRule
				},new Ext.Toolbar.Separator({
							
					}),{
					iconCls : 'btn-detail',
					text : '查看用户等级',
					xtype : 'button',
					scope : this,
					handler : this.seeNewRule
					
				},new Ext.Toolbar.Separator({
							
					}),{
					iconCls : 'btn-del',
					text : '删除用户等级',
					xtype : 'button',
					scope : this,
					handler : this.addRecoerd
				},new Ext.Toolbar.Separator({
							
				})]
		});
		
		this.gridPanel = new HT.GridPanel({
					name : 'MemberGradeSetGrid',
					region : 'center',
					tbar : this.topbar,
					notmask : true,
					// 不适用RowActions
					rowActions : false,
					url : __ctxPath
							+ "/bonusSystem/listMemberGradeSet.do",
					fields : [{
								name : 'levelId',
								type : 'int'
							}, 'levelName', 'levelMinBonus',
							'recordMsg', 'levelImageUrl','levelMark'
							],

					columns : [{
								header : 'levelId',
								dataIndex : 'levelId',
								hidden : true
							},  {
								header : '用户等级',
								width : 60,
								align:'center',
								sortable : true,
								dataIndex : 'levelName'
							},{
								header : '升级所需积分',
								align : 'right',
								width : 60,
								sortable : true,
								dataIndex : 'levelMinBonus'
							},{
								header : '会员等级描述',
								align : 'left',
								width : 170,
								sortable : true,
								dataIndex : 'levelMark'
							}
//							,{
//								header : '等级图标',
//								align : 'right',
//								width : 80,
//								sortable : true,
//								dataIndex : 'levelImageUrl'
//							}
							]
				});
		this.gridPanel.addListener('afterrender', function() {
					this.loadMask1 = new Ext.LoadMask(this.gridPanel.getEl(), {
						msg : '正在加载数据中······,请稍候······',
						store : this.gridPanel.store,
						removeMask : true
							// 完成后移除
						});
					this.loadMask1.show(); // 显示

				}, this);
		
	},

	// 添加奖励规则
	addNewRule : function() {
		new MemberGradeSetForm ({
			title:"添加用户等级",
			flashTargat:this.gridPanel
		}).show();
	},
	// 查看奖励规则
	seeNewRule : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else {
			new MemberGradeSetForm ({
				title:"查看用户等级",
				levelId:s[0].data.levelId,
				isReadOnly:true,
				isKeyReadOnly:true,
				isLook:true,
				flashTargat:this.gridPanel
			}).show();
		}
	
	},
	// 编辑奖励规则
	editNewRule : function() {
		var s = this.gridPanel.getSelectionModel().getSelections();
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}else if (s.length > 1) {
			Ext.ux.Toast.msg('操作信息', '只能选中一条记录');
			return false;
		}else {
		
			new MemberGradeSetForm ({
				title:"编辑用户等级",
				levelId:s[0].data.levelId,
				isReadOnly:false,
				isKeyReadOnly:false,
				isLook:true,
				flashTargat:this.gridPanel
			}).show();
		}
	
	},
	
	//删除、批量删除会员等级的方法
	addRecoerd:function(){
		var s = this.gridPanel.getSelectionModel().getSelections();
		
		if (s <= 0) {
			Ext.ux.Toast.msg('操作信息', '请选中任何一条记录');
			return false;
		}
		
		//拼接主键字符串
		var ids = Array();
		for (var i = 0; i < s.length; i++) {
			ids.push(s[i].data.levelId);
		}
		
		//向后台发送删除命令以及参数
		Ext.Ajax.request({
						url : __ctxPath + "/bonusSystem/multiDelMemberGradeSet.do",
						method : 'POST',
						params : {
							ids :ids
						},
						scope:this,
						//数据删除成功后的操作
						success :function(response, request){
							this.gridPanel.getStore().reload();//表格数据源刷新
							Ext.ux.Toast.msg('操作信息', '用户等级数据删除成功！');//成功删除提示
//							var object=Ext.util.JSON.decode(response.responseText);
//							var flag =object.flag;//flag用来判断是否本金款项是否已经对账：0表示没有对账，1表示已经对账
							
						}
			       })
	}
});