Ext.ns('SysLogView');
/**
 * @author:
 * @class AppRoleView
 * @extends Ext.Panel
 * @description 用户角色列表
 * @company 北京互融时代软件有限公司
 * @createtime:2010-04-12
 */
SysLogView = Ext.extend(Ext.Panel, {
	// 条件搜索Panel
	searchPanel : null,
	// 数据展示Panel
	gridPanel : null,
	// GridPanel的数据Store
	store : null,
	// 头部工具栏
	topbar : null,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SysLogView.superclass.constructor.call(this, {
					id : 'SysLogView',
					title : '用户登录日志',
					iconCls:"menu-flowManager",
					region : 'center',
					layout : 'border',
					items : [this.gridPanel]
				});
	},// end of constructor

	// 初始化组件
	initUIComponents : function() {
		var start = 0;
		var limit =20;
		
		/**↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		/**	查询段					*/
		/**↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓*/
		
		//关键字查询
		var keyword = new Ext.form.TextField({
			hiddenName : 's_keyword',
			width : 100,
			emptyText : '账户 | 姓名',
			fieldLabel : '关键字',
			hideLabel : true,
			listeners : {
				'specialkey' : function(t, e){
					 if (e.getKey() == e.ENTER) {
						 	Js_logData.load();
		             }//if	
				}
			}
		});
		
		//是否成功
		var cm_isSuccess = new Ext.form.ComboBox({
	//		fieldLabel : '是否成功',
			hiddenName : 's_isSuccess',
			//emptyText : '请选择',
			mode : 'local',
			width : 60,
			editable : false,
			store : new Ext.data.SimpleStore({
						data : [['成功', 1], ['失败', 0],['全部',100]],
						fields : ['text', 'value']
					}),
			displayField : 'text',
			valueField : 'value',
			triggerAction : 'all'
	//		listeners : {
	//			'select' : function(combo, record, index) {
	//				
	//			}
	//		}
		});
		
		var beginDate = new Ext.form.DateField({
			name : 's_beginDate',
			width : 100
		});
		
		var endDate = new Ext.form.DateField({
			name : 's_endDate',
			width : 100
		});
		
		//查找按钮
		var searchBtn = new Ext.Button({
			text : '查找',
			iconCls : 'searchIcon',
			scope : this,
			handler : function() {
				Js_logData.load();
			}//end of function
		});
		
		var resetBtn = new Ext.Button({
			text : '重置',
			iconCls : 'resetIcon',
			scope : this,
			handler : function() {
				
				keyword.reset();
				cm_isSuccess.clearValue();
				beginDate.reset();
				endDate.reset();
				
				Js_logData.load();
				
			}//end of function
		});
		
		
		/**↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		/**↑	查询段			   ↑*/
		/**↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑*/
		
		
		/**###########################          数据源                  ###########################*/
		var Js_logData = new Ext.data.JsonStore( {
			id : 'id_Js_logData',
			url : __ctxPath + '/creditFlow/log/getLoginDataListUserloginlogs.do',
			totalProperty : 'totalProperty',
			root : 'topics',
			autoLoad : true,
			fields : [{name : 'id'},
						{name : 'userLoginName'}, 
						{name : 'loginTime'},
						{name : 'loginIp'},
						{name : 'loginMac'},
						{name : 'isSuccess'},
						{name : 'userName'},
						{name : 'loginAddr'},
						{name : 'type'},
						{name : 'companyId'}
					],
			baseParams : {
				start : start,
				limit : limit
			},
			listeners : {
				beforeload : function(){
					 Ext.apply(this.baseParams, {
			           s_keyWord: keyword.getValue(),
			           s_isSuccess: cm_isSuccess.getValue(),
			           s_beginTime: beginDate.getValue(),
			           s_endTime: endDate.getValue()
		        	}); 
				}
			}
		});
		
	
		/**#########################         加载数据       ##################################*/
	
		var cm_log = new Ext.grid.ColumnModel(
				[
						new Ext.grid.RowNumberer( {
							header : '序号',
							align:'center',
							width :80
						}),
						{
							header : "账号",
							align:'center',
							width : 220,
							sortable : true,
							dataIndex : 'userLoginName'
						},{
							header : "姓名",
							width : 160,
							sortable : true,
							dataIndex : 'userName'
						}, {
							header : "IP地址",
							align:'center',
							width : 160,
							sortable : true,
							dataIndex : 'loginIp'
						},{
							header : "登录时间",
							width : 150,
							align:'center',
							sortable : true,
							dataIndex : 'loginTime'
						},{
							header : "登录地址",
							width : 300,
							sortable : true,
							hidden : true,
							dataIndex : 'loginAddr'
						},/*{
							header : '系统类型',
							width : 150,
							sortable : true,
							dataIndex : 'type',
							renderer : function(v){
								if(v == 'p2p'){
									return 'P2P';
								}else{
									return 'ERP';
								}
							}
						},*/{
							id : 'id_autoExpandColumn',
							header : '是否成功',
							width : 100,
							align:'center',
							sortable : true,
							dataIndex : 'isSuccess',
							renderer : function(v){
								if(v == true){
									return '<font color="green">成功</font>';
								}else{
									return '<font color="red">失败</font>';
								}
							}
						}]);
	
		var pagingBar = new Ext.PagingToolbar( {
			pageSize : limit,
			store : Js_logData,
			autoWidth : true,
			hideBorders : true,
			displayInfo : true,
			displayMsg : '当前第{0} - {1}条，共 {2} 条记录',
			emptyMsg : "没有符合条件的记录······"
		});
		var myMask = new Ext.LoadMask(Ext.getBody(), {
			msg : "加载数据中······,请稍后······"
		});
		
		this.gridPanel = new Ext.grid.GridPanel({
			id : 'gp_log',
			region:'center',
			title : '用户登录记录',
			store : Js_logData,
	//		width : (((Ext.getBody().getWidth()-6)<500) ? 500 : (Ext.getBody().getWidth()-6)),
	//		height : ((Ext.getBody().getHeight()-6)<485 ? 485 : (Ext.getBody().getWidth()-6)),
			autoScroll : true ,
			colModel : cm_log,
			autoExpandColumn : 'id_autoExpandColumn',
			selModel : new Ext.grid.RowSelectionModel(),
			stripeRows : true,
			loadMask : myMask,
			bbar : pagingBar,
			tbar : ['->','【','关键字：',keyword,'-','是否成功：',cm_isSuccess,'-','起止日期：',beginDate,' - ',endDate,'】',searchBtn,'-',resetBtn],
			listeners : {
				'cellclick' : function(grid,rowIndex,colIndex,e){
					
					if(colIndex == 5){//点在第5列上【登录地址列】
						
						var tip = new Ext.ToolTip({
							title : '此地址从其他网站IP库查询，请酌情参考。',
	//						text : showIp,//不知道为什么 text不行。
							border : true,
	//						height : 100,
							autoWidth : true,
	//						width : 200,
	//						html :'222',//这个好用
							animate: true
						});
						
						tip.showAt([e.getPageX(),e.getPageY()]);
						tip.show();
					}
					
				}
			}
		});

	}// end of the initUIComponents

});