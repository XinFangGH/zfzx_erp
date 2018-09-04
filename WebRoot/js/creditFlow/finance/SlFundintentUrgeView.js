/**
 * @author
 * @class SlFundintentUrgeView
 * @extends Ext.Panel
 * @description [SlFundintentUrge]管理
 * @company 智维软件
 * @createtime:
 */
SlFundintentUrgeView = Ext.extend(Ext.Panel, {
		// 构造函数
		constructor : function(_cfg) {
			Ext.applyIf(this, _cfg);
			// 初始化组件
			this.initUIComponents();
			// 调用父类构造
			SlFundintentUrgeView.superclass.constructor.call(this, {
				id : 'SlFundintentUrgeView'+this.tabflag+this.businessType,
				title : this.tabflagtitle,
				region : 'center',
				layout : 'border',
				items : [this.searchPanel, this.gridPanel]
			});
		},// end of constructor
		
		// 初始化组件
		initUIComponents : function() {
			// 初始化搜索条件Panel
			var tabflag=this.tabflag;
			var onclickflag=this.onclickflag;
			var onclickisInterent=this.onclickisInterent;
			
			var date5=new Date();
			var time=date5.getTime();
			time+=1000*60*60*24*5;
			date5.setTime(time);
			
			var datemonth=new Date();
			var timemonth=datemonth.getTime();
			timemonth+=1000*60*60*24*30;
			datemonth.setTime(timemonth);
				
			var isShow=false;
			if(RoleType=="control"){
			  isShow=true;
			}
			
			this.isGrantedShowAllProjects = isGranted('_seeAllPro_p1');// 是否授权显示所有项目记录
			if(this.businessType=='Pawn'){
				this.isGrantedShowAllProjects = isGranted('_seeAllPawnPro_p1');
			}else if(this.businessType=='Financing'){
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_f1');
			}else if(this.businessType=='LeaseFinance'){
				this.isGrantedShowAllProjects = isGranted('_seeAllPro_fl10');
			}
			
			this.showBtnFlag =(this.businessType=='SmallLoan' || this.businessType=='Pawn'||this.businessType=='LeaseFinance')?false:true;//add by gao
				
			var comboType = new Ext.form.ComboBox({
			 	labelWidth:70,    
				fieldLabel : '款项类型',
				mode : 'local',
				displayField : 'name',
				valueField : 'id',
				editable : false,
				store : new Ext.data.SimpleStore({
					fields : ["name", "id"],
					data : [["本金或利息", "0"], ["本金", "1"], ["利息", "2"]]
				}),
				value :onclickflag ==0?0:onclickisInterent,		
				triggerAction : "all",
				hiddenName : "Q_intentType_SN_EQ",
				name : 'Q_intentType_SN_EQ',
				anchor : '96%'
			});
			
			if("LeaseFinance"==this.businessType){
				comboType = new DicIndepCombo({
					labelWidth:70,    
					fieldLabel : '款项类型',
					editable : true,
					lazyInit : false,
					forceSelection : false,
					anchor : '96%',
					hiddenName : "Q_intentType_SN_EQ",
					name : 'Q_intentType_SN_EQ',
					nodeKey : "leaseFundType"
				})
				comboType.store.reload();
			}
			
			this.searchPanel = new HT.SearchPanel({
				layout : 'column',
				style : 'padding-left:5px;padding-right:5px;padding-top:5px;',
				region : 'north',
				height : 20,
				anchor : '96%',
				keys : [{
					key : Ext.EventObject.ENTER,
					fn : this.search,
					scope : this
				}, {
					key : Ext.EventObject.ESC,
					fn : this.reset,
					scope : this
				}],
				layoutConfig: {
	               align:'middle',
	               padding : '5px'
	            },
				items : [{   
					columnWidth : 0.2,
					layout : 'form',
					border : false,
					labelWidth : 60,
					labelAlign : 'right',
					items : [{
						labelWidth:70,    
						fieldLabel : '项目名称',
						name : 'Q_proj_Name_N_EQ',
						flex : 1,
						editable : false,
						width:105,
						xtype :'textfield',
						anchor : '96%'
					}, {
						labelWidth:70,    
						fieldLabel : '客户名称',
						name : 'Q_oppositeName_N_EQ',
						flex : 1,
						editable : false,
						width:105,
						xtype :'textfield',
						anchor : '96%'
					} ] 
				},{
					columnWidth : 0.2,
					layout : 'form',
					border : false,
					labelWidth : 60,
					labelAlign : 'right',
					items : [{
						labelWidth:70,    
						fieldLabel : '项目编号',
						name : 'Q_projNum_N_EQ',
						flex : 1,
						editable : false,
						width:105,
						xtype :'textfield',
						anchor : '96%'
					}, comboType ] 
				},{   
					columnWidth : 0.21,
					layout : 'form',
					border : false,
					labelWidth : 100,
					labelAlign : 'right',
					items : [{
						labelWidth:70,   
						fieldLabel : '金额范围:从',
						labelSeparator : '',
						width:100,
						xtype : 'textfield',
			          	name : 'Q_incomemoney_S_GE',
			          	anchor : '96%'
					},{
				     	fieldLabel : '计划到账日期:从',
						name : 'Q_intentDate_D_GE',
						labelSeparator : '',
						xtype : 'datefield',
						format : 'Y-m-d',
						anchor : '96%',
						value :(onclickflag ==0 || onclickflag ==4)?null:new Date()
					}] 
				},{
					columnWidth : 0.14,
					layout : 'form',
					border : false,
					labelWidth : 20,
					labelAlign : 'right', 
					items : [{
						fieldLabel : '到',
						name : 'Q_incomemoney_D_LE',
						labelSeparator : '',
						xtype : 'textfield',
						anchor : '96%'
					},{
						fieldLabel : '到',
						name : 'Q_intentDate_D_LE',
						labelSeparator : '',
						xtype : 'datefield',
						format : 'Y-m-d',
						anchor : '96%',
						value :onclickflag ==0?null:(onclickflag ==1?new Date():(onclickflag ==2?date5:(onclickflag ==3?datemonth:new Date)))
					},{
						name : 'isGrantedShowAllProjects',
						xtype : 'hidden',
						value : this.isGrantedShowAllProjects
					}]
				},{
					columnWidth : .06,
					xtype : 'container',
					layout : 'form',
					defaults : {
						xtype : 'button'
					},
					style : 'padding-left:10px;',
					items : [{
						text : '查询',
						iconCls : 'btn-search',
						scope : this,
						handler : this.search
					}, {
						text : '重置',
						scope : this,
						iconCls : 'btn-reset',
						handler : this.reset
					}]
				}]
			});// end of searchPanel
			
	        if(tabflag=="coming"){
				this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-clock',
						text :'到期通知',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
                        handler : function(){
						   this.createAllRs("coming")		
						}	
					},new Ext.Toolbar.Separator({
						hidden :this.showBtnFlag
					}) ,{
						iconCls : 'btn-print',
						text :'打印到期通知',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
						handler : function(){
							var thisPanel = this.gridPanel;
							var selected = this.gridPanel.getSelectionModel().getSelected();
							if (null == selected) {
								Ext.ux.Toast.msg('状态', '请选择一条记录！');
							} else {
								var window = new OperateKxcsContractWindow({
									businessType : selected.get('businessType'),
									piKey : selected.get('projectId'),
									htType : 'DunningLetter',
									thisPanel : thisPanel
								});
								window.show();
								window.addListener({
									'close' : function() {
										thisPanel.getStore().reload();
									}
								});
							}
						}
					},new Ext.Toolbar.Separator({
						hidden :this.showBtnFlag
					}),{
						iconCls : 'btn-xls',
						text :'导出到Excel',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
						handler : function() {
							var projectName=this.getCmpByName("Q_proj_Name_N_EQ").getValue();//项目名称
							var oppositeName=this.getCmpByName("Q_oppositeName_N_EQ").getValue();//客户名称
							var intentType=this.getCmpByName("Q_intentType_SN_EQ").getValue();//款项类型
							var projNum=this.getCmpByName("Q_projNum_N_EQ").getValue();//项目编号
							var incomemoney_S=this.getCmpByName("Q_incomemoney_S_GE").getValue();//从金额开始
							var incomemoney_D=this.getCmpByName("Q_incomemoney_D_LE").getValue();//到金额结束
							var intentDate_S=this.getCmpByName("Q_intentDate_D_GE").getValue();//从日期开始
								intentDate_S=Ext.util.Format.date(intentDate_S, 'Y-m-d')
							var intentDate_D_LE=this.getCmpByName("Q_intentDate_D_LE").getValue();//到日期结束
								intentDate_D_LE=Ext.util.Format.date(intentDate_D_LE, 'Y-m-d')
							window.open( __ctxPath + "/creditFlow/finance/outToExcelSlFundIntent.do?businessType="+this.businessType+"&tabflag="+this.tabflag+"&projectName="+encodeURIComponent(encodeURIComponent(projectName))+"&oppositeName="+encodeURIComponent(encodeURIComponent(oppositeName))+"&intentType="+intentType+"&projNum="+encodeURIComponent(encodeURIComponent(projNum))+"&incomemoney_S="+incomemoney_S+"&incomemoney_D="+incomemoney_D+"&intentDate_S="+intentDate_S+"&intentDate_D_LE="+intentDate_D_LE+"&isGrantedShowAllProjects="+this.isGrantedShowAllProjects,'_blank');
						}
					},'->',{
						xtype:'radio',
						scope : this,
						boxLabel : '今日到期',
						id:"11"+tabflag,
						name : '1',
						checked:onclickflag==1?true:false,
						listeners:{
							scope :this,
						    check :function(){
						    	var flag=Ext.getCmp("11"+tabflag).getValue();
							    if(flag==true){ 
							     	this.getCmpByName("Q_intentDate_D_GE").setValue(new Date);
							     	var now=new Date();
									var time=now.getTime();
									now.setTime(time);
									this.getCmpByName("Q_intentDate_D_LE").setValue(now);
									this.search();
							    }
							 }
						}
					},' ',' ', {
						xtype:'radio',
						boxLabel : '三天内到期',
						name : '1',
						id:"13"+tabflag,
						checked :onclickflag==2?true:false,
						listeners:{
						 	scope :this,
						    check :function(){
							    var flag=Ext.getCmp("13"+tabflag).getValue();
							    if(flag==true){
							     	this.getCmpByName("Q_intentDate_D_GE").setValue(new Date);
									var now=new Date();
									var time=now.getTime();
									time+=1000*60*60*24*3;
									now.setTime(time);
									this.getCmpByName("Q_intentDate_D_LE").setValue(now);
							      	this.search();
							    }
						    }
						}
					},' ',' ',{
						xtype:'radio',
						boxLabel : '五天内到期',
						id:"12"+tabflag,
						name : '1',
						checked :onclickflag==3?true:false,
						listeners:{
							scope :this,
						    check :function(){
							   	var flag=Ext.getCmp("12"+tabflag).getValue();
							    if(flag==true){
							     	this.getCmpByName("Q_intentDate_D_GE").setValue(new Date);
									var now=new Date();
									var time=now.getTime();
									time+=1000*60*60*24*5;//加上5天
									now.setTime(time);
									this.getCmpByName("Q_intentDate_D_LE").setValue(now);
									this.search();
							    }
						    }
						}
					},' ',' ',' ',' ']
				});
			}else{
			   this.topbar = new Ext.Toolbar({
					items : [{
						iconCls : 'btn-clock1',
						text :'单笔款项催收',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
						handler : this.createRs
					},new Ext.Toolbar.Separator({
						hidden :this.showBtnFlag
					}) ,{
						iconCls : 'btn-clock',
						text :'还款催收',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
						handler : function(){
						   this.createAllRs("overdue")		
						}	
					},new Ext.Toolbar.Separator({
						hidden :this.showBtnFlag
					}) ,{
						iconCls : 'btn-print',
						text :'打印催收通知单',
						xtype : 'button',
						scope : this,
						hidden :this.showBtnFlag,
						handler : function(){
							var thisPanel = this.gridPanel;
							var selected = this.gridPanel.getSelectionModel().getSelected();
							if (null == selected) {
								Ext.ux.Toast.msg('状态', '请选择一条记录！');
							} else {
//								var htType = 'DunningLetter';//小贷默认值
								var htType = 'LoanExpirationNotice';//小贷默认值
								if("LeaseFinance" == selected.get('businessType')){
									htType = "flDunningLetter";
								}else if("Pawn" == selected.get("businessType")){
									htType = "plDunningLetter";
								}
								
								var pars={
									'businessType':selected.get('businessType'),
									'projId':selected.get('fundIntentId'),
									'htType':htType
								}
								
								//TODO 1.查询cs_procredit_contract表,条件projid=selected.get('preceptId') and htType=htType
								Ext.Ajax.request({
									url : __ctxPath+'/contract/getContractTreeProcreditContract.do',
									method : 'POST',
									scope : this,
									success : function(response, request) {
										var obj = Ext.util.JSON.decode(response.responseText);
										
										var conId=0;
										var conUrl='';
										if(obj.topics.length>0){
											conId=obj.topics[0].id;
											conUrl=obj.topics[0].url;
										}
										
										//TODO 待修改
										var window = new OperateKxcsContractWindow({
											businessType : selected.get('businessType'),
											fundIntentId : selected.get('fundIntentId'),
											fundProjectId:selected.get('preceptId'),
											htType : htType,
											conId : conId,//合同下载时需要
											conUrl : conUrl//合同下载时需要
										});
										window.show();
										window.addListener({
											'close' : function() {
												thisPanel.getStore().reload();
											}
										});
									},
									params:pars
								});
							}
					}
				},new Ext.Toolbar.Separator({}) ,{
					iconCls : 'btn-xls',
					text :'导出到Excel',
					xtype : 'button',
					scope : this,
					handler : function() {
						var projectName=this.getCmpByName("Q_proj_Name_N_EQ").getValue();//项目名称
						var oppositeName=this.getCmpByName("Q_oppositeName_N_EQ").getValue();//客户名称
						var intentType=this.getCmpByName("Q_intentType_SN_EQ").getValue();//款项类型
						var projNum=this.getCmpByName("Q_projNum_N_EQ").getValue();//项目编号
						var incomemoney_S=this.getCmpByName("Q_incomemoney_S_GE").getValue();//从金额开始
						var incomemoney_D=this.getCmpByName("Q_incomemoney_D_LE").getValue();//到金额结束
						var intentDate_S=this.getCmpByName("Q_intentDate_D_GE").getValue();//从日期开始
						intentDate_S=Ext.util.Format.date(intentDate_S, 'Y-m-d')
						var intentDate_D_LE=this.getCmpByName("Q_intentDate_D_LE").getValue();//到日期结束
						intentDate_D_LE=Ext.util.Format.date(intentDate_D_LE, 'Y-m-d')
						window.open( __ctxPath + "/creditFlow/finance/outToExcelSlFundIntent.do?businessType="+this.businessType+"&tabflag="+this.tabflag+"&projectName="+encodeURIComponent(encodeURIComponent(projectName))+"&oppositeName="+encodeURIComponent(encodeURIComponent(oppositeName))+"&intentType="+intentType+"&projNum="+encodeURIComponent(encodeURIComponent(projNum))+"&incomemoney_S="+incomemoney_S+"&incomemoney_D="+incomemoney_D+"&intentDate_S="+intentDate_S+"&intentDate_D_LE="+intentDate_D_LE+"&isGrantedShowAllProjects="+this.isGrantedShowAllProjects,'_blank');
					}
				},{
					iconCls : 'btn-law',
					text :'提交到法务部处理',
					xtype : 'button',
					hidden:this.tabflag==null?true:(this.tabflag=="overdue"?false:true),
					scope : this,
					handler : function() {
						var thisPanel = this.gridPanel;
						var selected = this.gridPanel.getSelectionModel().getSelected();
						if(selected==null){
							Ext.ux.Toast.msg('状态', '请选择一条记录！');
						}else{
							var fundIntentId = selected.data.fundIntentId;
							Ext.Ajax.request({
								url:__ctxPath + '/creditFlow/finance/getUrgeCustomSlFundintentUrge.do?projectId='+selected.data.projectId+'&businessType='+selected.data.businessType,
								method : 'POST',
								success : function(response, request) {}
							});
						}
					}
				},'->',{
					xtype:'radio',
					scope : this,
					boxLabel : '逾期小于30天',
					id:"11overdue1",
					hidden:this.tabflag==null?true:(this.tabflag=="overdue"?false:true),
					name : '1',
					checked:onclickflag==1?true:false,
					listeners:{
						scope :this,
					    check :function(){
					    	var flag=Ext.getCmp("11overdue1").getValue();
					     	if(flag==true){ 
						     	 this.getCmpByName("Q_intentDate_D_LE").setValue(new Date);
						     	 var now=new Date();
								 var time=now.getTime();
								 time -= 1000*60*60*24*30;
								 now.setTime(time);
								 this.getCmpByName("Q_intentDate_D_GE").setValue(now);
								 this.search();
					    	}
					    }
					}
				},'','',{
					xtype:'radio',
					scope : this,
					boxLabel : '逾期30~60(包含)天',
					id:"13overdue1",
					hidden:this.tabflag==null?true:(this.tabflag=="overdue"?false:true),
					name : '1',
					checked:onclickflag==1?true:false,
					listeners:{
						scope :this,
					    check :function(){
					    	var flag=Ext.getCmp("13overdue1").getValue();
					     	if(flag==true){ 
					     	var now=new Date();
					     	var date1 = new Date();
					     	var date2 = new Date();
					     	var time1 = date1.setDate(date1.getDate()-30);
					     	var time2 = date2.setDate(date2.getDate()-60);
					     	date1.setTime(time1);
					     	date2.setTime(time2);
					     	this.getCmpByName("Q_intentDate_D_GE").setValue(date2);
							this.getCmpByName("Q_intentDate_D_LE").setValue(date1);
							this.search();
					     }
					    }
					}
				},'','',{
					xtype:'radio',
					scope : this,
					boxLabel : '逾期60~100(包含)天',
					id:"14overdue1",
					hidden:this.tabflag==null?true:(this.tabflag=="overdue"?false:true),
					name : '1',
					checked:onclickflag==1?true:false,
					listeners:{
						scope :this,
					    check :function(){
						    var flag=Ext.getCmp("14overdue1").getValue();
						    if(flag==true){ 
					     	var now=new Date();
					     	var date1 = new Date();
					     	var date2 = new Date();
					     	var time1 = date1.setDate(date1.getDate()-60);
					     	var time2 = date2.setDate(date2.getDate()-100);
					     	date1.setTime(time1);
					     	date2.setTime(time2);
					     	this.getCmpByName("Q_intentDate_D_GE").setValue(date2);
							this.getCmpByName("Q_intentDate_D_LE").setValue(date1);
							this.search();
					     }
					    }
					}
				},'','',{
					xtype:'radio',
					scope : this,
					boxLabel : '逾期大于100天',
					id:"15overdue1",
					hidden:this.tabflag==null?true:(this.tabflag=="overdue"?false:true),
					name : '1',
					checked:onclickflag==1?true:false,
					listeners:{
						scope :this,
					    check :function(){
						    var flag=Ext.getCmp("15overdue1").getValue();
						    if(flag==true){ 
						     	var now=new Date();
						     	var date1 = new Date();
						     	var date2 = new Date();
						     	var time1 = date1.setDate(date1.getDate()-100);
						     	date1.setTime(time1);
						     	this.getCmpByName("Q_intentDate_D_LE").setValue(date1);
						     	this.search();
					     	}
					    }
					}
				}]})
			}
		
			var summary = new Ext.ux.grid.GridSummary();
			function totalMoney(v, params, data) {
				return '总计(元)';
			}
				
			this.gridPanel = new HT.GridPanel({
				bodyStyle : "width : 100%",
				region : 'center',
				plugins : [summary],
				tbar : ((this.businessType=='SmallLoan' || this.businessType=='Pawn'||this.businessType=="LeaseFinance") || (this.businessType=='Financing' && tabflag=="coming") ) ?this.topbar:null,
				viewConfig:this.tabflag=="overdue"?{}: {  
		        	forceFit:true,
		        	getRowClass: function(record, index,rp,ds) {
		        	 	var v=record.get('factDate');
		        	 	if(v!=null){
							return ""
		        	 	}else {
		        	 		var intentDate=record.get('intentDate');
		        	 		var nowDays=new Date().format("Y-m-d");//今天日期
							var date1 = new Date(Date.parse(intentDate.replace(/-/g,"/")));
		                    var date2 = new Date(Date.parse(nowDays.replace(/-/g,"/")));
		                    if(date1<date2){
		                    	return 'x-grid-record-red';
		                    }else{
		                    	return ""
		                    }
		        	 	 }
			         }  
		         },
				 // 使用RowActions
				 rowActions : false,
				 id : 'SlFundIntentGrid'+this.tabflag,
				 url :onclickflag==0? (__ctxPath + "/creditFlow/finance/listbyurgeSlFundIntent.do?businessType="+this.businessType+"&tabflag="+this.tabflag+"&isGrantedShowAllProjects="+this.isGrantedShowAllProjects):null,
				 fields : [{
						name : 'fundIntentId',
						type : 'int'
					}, 'projectName','projectNumber', 'incomeMoney','fundTypeName', 'intentDate','preceptId',
					'payMoney', 'payInMoney', 'factDate','fundType','afterMoney', 'notMoney','flatMoney', 'isOverdue',
					'overdueRate', 'accrualMoney', 'status','remark','businessType','projectId','lastslFundintentUrgeTime','oppositeName','opposittelephone','projectStartDate','orgName'],
				 columns : [{
					header : 'fundIntentId',
					align:'center',
					dataIndex : 'fundIntentId',
					hidden : true
				 },{
					header : "所属分公司",
					sortable : true,
					align:'center',
					width : 120,
					hidden:RoleType=="control"?false:true,
					dataIndex : 'orgName'
				 }, {
					header : '项目编号',
					dataIndex : 'projectNumber',
					width : 100
				 }, {
					header : '客户名称',
					align:'center',
					summaryRenderer : totalMoney,
					dataIndex : 'oppositeName',
					width : 100
				 }, {
					header : '联系电话',
					align:'center',
					dataIndex : 'opposittelephone',
					width : 120
				 }, {
					header : '资金类型',
					align:'center',
					dataIndex : 'fundTypeName',
					width : 130
				 }, {
					header :this.businessType=='SmallLoan'?'计划收入金额(元)':'计划支出金额(元)',
					dataIndex : this.businessType=='SmallLoan'?'incomeMoney':'payMoney',
					align : 'right',
					summaryType : 'sum',
					width : 150,
					renderer:function(v){
						return Ext.util.Format.number(v,',000,000,000.00')
             	     }
				 }, {
					header : '未对账金额(元)',
					dataIndex : 'notMoney',
					align : 'right',
					summaryType : 'sum',
					width : 150,
					sortable:true,
					renderer : function(v) {
						return Ext.util.Format.number(v,',000,000,000.00')
					}
				 }, {
					header : '计划到账日',
					width : 100,
					dataIndex : 'intentDate',
					align : 'center'
				 }, {
					header : '实际到账日',
					width : 100,
					align:'center',
					dataIndex : 'factDate'
				 }, {
					header : '最后催收时间',
					dataIndex : 'lastslFundintentUrgeTime',
					width : 150,
					align : 'center'
				 }]
			});
		},
		
		//重置查询表单
		reset : function(){
			this.searchPanel.getForm().reset();
		},
		
		//按条件搜索
		search : function() {
			this.gridPanel.getStore().proxy=new Ext.data.HttpProxy({url: __ctxPath + "/creditFlow/finance/listbyurgeSlFundIntent.do?businessType="+this.businessType+"&tabflag="+this.tabflag});
			$search({
				searchPanel:this.searchPanel,
				gridPanel:this.gridPanel
			});
		},
		
		//创建记录
		createRs : function() {
			var s = this.gridPanel.getSelectionModel().getSelections();
			var tabflag=this.tabflag
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				var	record=s[0];
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/finance/getUrgeCustomSlFundintentUrge.do?projectId='+record.data.projectId+'&businessType='+record.data.businessType,
					method : 'POST',
					success : function(response, request) {
						var tabs = Ext.getCmp('centerTabPanel');
						var gpObj = document.getElementById('SlFundintentUrgeFormWin'+record.data.fundIntentId);
						if (gpObj == null) {
							var obj= Ext.util.JSON.decode(response.responseText);
							var custom = obj.data;
					    	if(obj.oppositeType=="company_customer"){
							  	var custonname=custom.enterprisename;
								var email=custom.email;
								var area=custom.area;
								var telephone=custom.telephone;
								var receiveMail=custom.receiveMail;
								var postcoding=custom.postcoding;
							
								gpObj= new SlFundintentUrgeForm({
								 	tabflag:tabflag,
								 	fundIntentobj:record.data,
								  	fundIntentId : record.data.fundIntentId,
								  	projectId : record.data.projectId,
								  	businessType:record.data.businessType,
								   	custonname:custonname,
								   	email:email,
								   	area:area,
								   	telephone:telephone,
								   	receiveMail:receiveMail,
								   	postcoding:postcoding,
								   	oppositeType:obj.oppositeType,
								   	oppositeID:obj.oppositeID,
								   	enterpriseId:custom.id,
								   	gridPanel:this.gridPanel
								 }).show();
							}else{
								var custonname=custom.name;
								var email=custom.selfemail;
								var area=custom.postaddress;
								var telephone=custom.cellphone;
								var receiveMail=custom.name;
								var postcoding=custom.postcode;
							
								gpObj=  new SlFundintentUrgeForm({
									tabflag:tabflag,
									fundIntentobj:record.data,
								  	fundIntentId : record.data.fundIntentId,
								  	projectId : record.data.projectId,
								 	businessType:record.data.businessType,
								   	custonname:custonname,
								   	email:email,
								   	area:area,
								   	telephone:telephone,
								   	receiveMail:receiveMail,
								   	postcoding:postcoding,
								   	oppositeType:obj.oppositeType,
								   	oppositeID:obj.oppositeID,
								   	personIdValue:custom.id,
								   	gridPanel:this.gridPanel
							    }).show();
						 	}
						 	tabs.add(gpObj);
					  	}
					  	tabs.setActiveTab("SlFundintentUrgeFormWin"+record.data.fundIntentId);
					}
				})
			}
		},
		
		//还款催收
		createAllRs : function(istype){
			var s = this.gridPanel.getSelectionModel().getSelections();
			var tabflag=this.tabflag
			if (s <= 0) {
				Ext.ux.Toast.msg('操作信息','请选中一条记录');
				return false;
			}else if(s.length>1){
				Ext.ux.Toast.msg('操作信息','只能选中一条记录');
				return false;
			}else{	
				var	record=s[0];
				Ext.Ajax.request({
					url:__ctxPath + '/creditFlow/finance/getUrgeCustomSlFundintentUrge.do?projectId='+record.data.projectId+'&businessType='+record.data.businessType,
					method : 'POST',
					success : function(response, request) {
						var tabs = Ext.getCmp('centerTabPanel');
						var gpObj = document.getElementById('SlFundintentUrgeAllFormWin'+record.data.fundIntentId+istype);
						
						if (gpObj == null) {
							var obj= Ext.util.JSON.decode(response.responseText);
							var custom = obj.data;
						    if(obj.oppositeType=="company_customer"){
							  	var custonname=custom.enterprisename;
								var email=custom.email;
								var area=custom.area;
								var telephone=custom.telephone;
								var receiveMail=custom.receiveMail;
								var postcoding=custom.postcoding;
						
						 		gpObj= new SlFundintentUrgeAllForm({
								 	tabflag:tabflag,
								 	fundIntentobj:record.data,
									fundIntentId : record.data.fundIntentId,
									projectId : record.data.projectId,
									businessType:record.data.businessType,
									projectName : record.data.projectName,
									projectNumber : record.data.projectNumber,
								   	custonname:custonname,
								   	email:email,
								   	area:area,
								   	telephone:telephone,
								   	receiveMail:receiveMail,
								  	postcoding:postcoding,
								   	oppositeType:obj.oppositeType,
								   	oppositeID:obj.oppositeID,
								   	enterpriseId:custom.id,
								   	istype:istype,
								   	gridPanel:this.gridPanel
							   }).show();
							}else{
								var custonname=custom.name;
								var email=custom.selfemail;
								var area=custom.postaddress;
								var telephone=custom.cellphone;
								var receiveMail=custom.name;
								var postcoding=custom.postcode;
						
								gpObj=  new SlFundintentUrgeAllForm({
									tabflag:tabflag,
									fundIntentobj:record.data,
								  	fundIntentId : record.data.fundIntentId,
								  	projectId : record.data.projectId,
								  	businessType:record.data.businessType,
								  	projectName : record.data.projectName,
								  	projectNumber : record.data.projectNumber,
								   	custonname:custonname,
								   	email:email,
								   	area:area,
								   	telephone:telephone,
								   	receiveMail:receiveMail,
								   	postcoding:postcoding,
								   	oppositeType:obj.oppositeType,
								   	oppositeID:obj.oppositeID,
								   	personIdValue:custom.id,
								   	istype:istype,
								   	gridPanel:this.gridPanel
							   	}).show();
							}
						 	tabs.add(gpObj);
					  	}
					  	tabs.setActiveTab("SlFundintentUrgeAllFormWin"+record.data.fundIntentId+istype);
					}
				})
			}
		}
});