/**
 * @author
 * @createtime
 * @class P2pFriendlinkForm
 * @extends Ext.Window
 * @description P2pFriendlink表单
 * @company 智维软件
 */
RefreshUserNumTW = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.plainpassword) != "undefined") {
			this.plainpassword = _cfg.plainpassword; 
		}
		if (typeof(_cfg.cardcode) != "undefined") {
			this.cardcode = _cfg.cardcode; 
		}
		if (typeof(_cfg.truename) != "undefined") {
			this.truename = _cfg.truename; 
		}
		if (typeof(_cfg.telphone) != "undefined") {
			this.telphone = _cfg.telphone; 
		}
		if (typeof(_cfg.email) != "undefined") {
			this.email = _cfg.email; 
		}
		if (typeof(_cfg.loginname) != "undefined") {
			this.loginname = _cfg.loginname; 
		}
		if (typeof(_cfg.id) != "undefined") {
			this.id = _cfg.id; 
		}
		 
//		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		RefreshUserNumTW.superclass.constructor.call(this, {
					id : 'P2pFileUpload',
					region : 'center',
					layout : 'border',
					items :[this.formPanel,this.gridPanel],
					modal : true,
					height : 550,
					width : 850,   
					maximizable : true,
					title : '刷新推荐用户数'
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		 
		this.formPanel = new HT.SearchPanel({
		        	layout : 'column',
					bodyStyle : 'padding:10px',
					region : 'north',
					border : false,
					autoScroll : true,
					layoutConfig: {
			               align:'middle'
			        },
					items:[{
			     		columnWidth :.33,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
					    	fieldLabel : '用户名',
							name : 'Q_loginname_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.loginname,disabled:true
						   },{
							fieldLabel:'身份证号',
							name : 'Q_cardcode_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.cardcode,disabled:true
	     	              }]
			     	},{
			     		columnWidth :.33,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							fieldLabel:'邀请码',
							name : 'Q_truename_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.plainpassword,disabled:true
			     	      },{
							fieldLabel:'手机号',
							name : 'Q_cardcode_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.telphone,disabled:true
	     	              }]
			     	},{
			     		columnWidth :.33,
						layout : 'form',
						labelWidth : 80,
						labelAlign : 'right',
						border : false,
						items : [{
							fieldLabel:'姓名',
							name : 'Q_telphone_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.truename,disabled:true
		                  },{
							fieldLabel:'电子邮箱',
							name : 'Q_cardcode_S_EQ',
							flex:1,
							xtype : 'textfield',
							value:this.email,disabled:true
		     	          }]
			     	}]
						
					});

		
		var summary = new Ext.ux.grid.GridSummary();
		function totalMoney(v, params, data) {
			return '合计(元)';
		}

			this.gridPanel=new HT.GridPanel({
				region:'center',
				layout: 'fit',
				tbar:this.topbar,
				id:'BpCustMemberGrid',
                bbar:false,
				plugins : [summary],
				url : __ctxPath + "/p2p/listRecommandPersonBpCustMember.do?plainpassword="+this.plainpassword,
				fields : [{
								name : 'id',
								type : 'int'
							},'loginname'
				,'truename'
				,'password'
				,'plainpassword'
				,'telphone'
				,'email'
				,'categoryName'
                ,'registrationDate'
                ,'recommandPerson'
                ,'recommandNum'
							,'totalRecharge'
							,'totalInvestMoney'
							,'totalprofitMoney'
							,'totalEnchashment'
							,'allInterest'
						],
				columns:[{
								header : 'id',
								dataIndex : 'id',
								header : '序号',
								hidden:true,
								anchor : "100%"
							},{
								header : '被推荐人的用户名',
								dataIndex : 'loginname',
								summaryType : 'count',
								summaryRenderer : totalMoney,
								anchor : "100%"
							},{
								header : '姓名',	
								dataIndex : 'truename',
								anchor : "100%"
							},{
								header : '累计充值资金',
								dataIndex : 'totalRecharge',
								summaryType : 'sum',
								anchor : "100%"
							},{
								header : '累计投资资金',	
								dataIndex : 'totalInvestMoney',
								summaryType : 'sum',
								anchor : "100%"
							},{
								header : '累计收益',
								dataIndex : 'allInterest',
								summaryType : 'sum',
								anchor : "100%"
							},{
								header : '累计取现金额',	
								dataIndex : 'totalEnchashment',
								summaryType : 'sum',
								anchor : "100%"
							}]
							
							});
	} 
});