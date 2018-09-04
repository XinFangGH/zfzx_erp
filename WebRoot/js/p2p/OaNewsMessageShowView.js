//OaNewsMessageShowView
OaNewsMessageShowView = Ext.extend(Ext.Window, {
			//构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				//必须先初始化组件
				this.initUIComponents();
				OaNewsMessageShowView.superclass.constructor.call(this, {
							id : 'OaNewsMessageShowView',
							height : 400,
							width : 1000,
							region : 'center',
							layout : 'border',
							modal : true,
							maximizable : true,
							title : '收到站内信的用户',
							items : [this.searchPanel, this.gridPanel]
				});
			},//end of the constructor
			//初始化组件
			initUIComponents : function() {
				this.searchPanel=new HT.SearchPanel({
							layout : 'column',
							region : 'north',
							items:[{
								columnWidth : .4,
								layout : 'form',
								labelWidth : 100,
								labelAlign : 'right',
								border : false,
								items : [{
											fieldLabel : '收件人',
											name : 'userName',
											anchor : "100%",
											xtype : 'textfield'
										}]
							},{
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 80,
								items : [{
											text : '查询',
											xtype : 'button',
											scope : this,
											style : 'margin-left:30px',
											anchor : "90%",
											iconCls : 'btn-search',
											handler : this.search
										}]
							},{
								columnWidth : .125,
								layout : 'form',
								border : false,
								labelWidth : 80,
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
				});// end of searchPanel

	
				this.gridPanel=new HT.GridPanel({
					region:'center',
//					tbar:this.topbar,
					//使用RowActions
//					rowActions:true,
					id:'OaNewsMessagerinfoGrid',
					url : __ctxPath + "/p2p/listOaNewsMessagerinfo.do?messageId="+this.id,
					fields : [{
									name : 'id',
									type : 'int'
								},'userId','userType','userName','messageId','status','readStatus'
								 ,'readTime','istop','isTopTime','title','content','sendTime','operator',
								 'addresser','typename'
							],
					columns:[{
									header : 'id',
									dataIndex : 'id',
									hidden : true
								},{
									header : '类型名称',	
									dataIndex : 'typename'
								},{
									header : '标题 ',	
									dataIndex : 'title'
								},{
									header : '收收人',	
									dataIndex : 'userName'
								},{
									header : '状态',	
									dataIndex : 'status',
									renderer:function(val){
										if(val==2){
											return "已发送";
										}else if(val==1){
											return "已删除";
										}else{
											return "未发送";
										}
									}
								},{
									header : '阅读状态',	
									dataIndex : 'readStatus',
									renderer:function(val){
										if(val==1){
											return "已阅读";
										}else{
											return "未阅读";
										}
									}
								},{
									header : '阅读时间',	
									dataIndex : 'readTime',
									format:'Y-m-d'
								},{
									header : '是否置顶',	
									dataIndex : 'istop',
									renderer:function(val){
										if(val==1){
											return "已置顶";
										}else{
											return "未置顶";
										}
									}
								},{
									header : '置顶时间',	
									dataIndex : 'isTopTime',
									format:'Y-m-d'
								}]//end of columns
				});
			},// end of the initComponents()
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
			}
		});