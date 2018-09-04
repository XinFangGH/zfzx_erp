/**
 * 
 * @class
 * @extends Ext.form.FormPanel
 * 设置参数  id:随机ID
 *           personData:add时null edit时为从数据库查询的对象
 */
seeApplyInfo  = Ext.extend(Ext.Window, {

	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.customerType)!="undefined"){
			this.customerType=_cfg.customerType
		}
		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId;
		}
		if(typeof(_cfg.personType)!="undefined"){
			this.personType=_cfg.personType;
		}
		if(typeof(_cfg.shareequityType)!="undefined"){
			this.shareequityType=_cfg.shareequityType;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		seeApplyInfo .superclass.constructor.call(this, {
			id : 'EnterpriseAll',
			title : '客户基本信息',
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			width :1000,
			height:400,
			modal : true,
			items : [this.tabpanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.tabpanel= new Ext.TabPanel({
			resizeTabs : true, // turn on tab resizing
			enableTabScroll : true,
			Active : 0,
			width:800,
			height:300,
			region : 'center', 
			deferredRender : false,
			activeTab : 0, // first tab initially active
			xtype : 'tabpanel',
			items : [new panel1({type:1,userId:this.userId}),
			         new panel1({type:2,userId:this.userId}),
					 new panel1({type:3,userId:this.userId}),
					 new panel1({type:4,userId:this.userId}),
					 new panel1({type:5,userId:this.userId})
					]
		});
	}
	
	
});
panel1= Ext.extend(Ext.Panel, {
	
	// 构造函数
	constructor : function(_cfg) {
		if(typeof(_cfg.customerType)!="undefined"){
			this.customerType=_cfg.customerType
		}
		if(typeof(_cfg.customerId)!="undefined"){
			this.customerId=_cfg.customerId
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		panel1.superclass.constructor.call(this, {
			id : 'panel1'+this.type,
			title : (this.type==1)?'个人信息':((this.type==2)?'家庭信息':((this.type==3)?'网店信息':((this.type==4)?'工作信息':'资产信息'))),
			region : 'center',
			layout : 'border',
			iconCls :'menu-company',
			items : [this.gridPanel ]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.gridPanel = new HT.GridPanel( {
		region : 'center',
		hiddenCm:true,
		showPaging:false,
		autoScroll:true,
		id:'custMemGrid',
		url : __ctxPath + "/p2p/getCustMemBpCustMember.do?userId="+this.userId,
		fields : [
		          {
		        	  name : 'id',
		        	  type : 'long',
		        	  hidden : true
				},
				'truename',
				'cardcode',
				'birthday',
				'collegeDegree',
				'collegeYear',
				'collegename',
				'nativePlaceProvice',
				'nativePlaceCity',
				'relationAddress',
				'postCode',
				'homePhone',
				'strMarry',
				'strChildren',
				'rel_dir_name',
				'relDirType',
				'relDirPhone',
				'relOtherName',
				'relOtherType',
				'relOtherPhone',
				'relFriendName',
				'relFriendType',
				'relFriendPhone',
				'careerType',
				'webshopName',
				'webshopMonthlyincome',
				'webshopEmail',
				'webshopProvince',
				'webshopCity',
				'webshopAddress',
				'webshopStartyear',
				'webshopPhone',
				'hireCompanyname',
				'hirePosition',
				'hireMonthlyincome',
				'hireEmail',
				'hireProvince',
				'hireCity',
				'hireAddress',
				'hireCompanytype',
				'hireCompanycategory',
				'hireCompanysize',
				'hireStartyear',
				'hireCompanyphone',
				'strHouse',
				'strHouseLoan',
				'strCar',
				'strCarLoan'
		
		
		],
		
		columns : [
		    {
			header : 'id',
			dataIndex : 'id',
			hidden : true
		    },{
				header : '真实姓名',
				dataIndex : 'truename',
				hidden:this.type==1?false:true,
				width:100
			  },{
				header : '身份证号',
				dataIndex : 'cardcode',
				hidden:this.type==1?false:true,
				width:100
			},{
				header:'生日',
				dataIndex : 'birthday',
				hidden:this.type==1?false:true,
				width:60
			},{
				header:'最高学历',
				dataIndex:'collegeDegree',
				hidden:this.type==1?false:true,
				width:60
			},{
				header:'入学年份',
				dataIndex:'collegeYear',
				hidden:this.type==1?false:true,
				width:60
			},{
				header:'毕业学校',
				dataIndex:'collegename',
				hidden:this.type==1?false:true,
				width:60
			},{
				header:'籍贯省',
				dataIndex:'nativePlaceProvice',
				hidden:this.type==1?false:true,
				width:80
			},{
				header:'婚姻状况',
				dataIndex:'strMarry',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'有无子女',
				dataIndex:'strChildren',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'直系亲属姓名',
				dataIndex:'relDirName',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'直系亲属关系',
				dataIndex:'relDirType',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'直系亲属手机号',
				dataIndex:'relDirPhone',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他亲属姓名',
				dataIndex:'relOtherName',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他亲属关系',
				dataIndex:'relOtherType',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他亲属手机号',
				dataIndex:'relOtherPhone',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他联系人姓名',
				dataIndex:'relFriendName',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他联系人关系',
				dataIndex:'relFriendType',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'其他联系人手机号',
				dataIndex:'relFriendPhone',
				hidden:this.type==2?false:true,
				width:80
			},{
				header:'职业状态',
				dataIndex:'careerType',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点名称',
				dataIndex:'webshopName',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点月收入',
				dataIndex:'webshopMonthlyincome',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点邮箱',
				dataIndex:'webshopEmail',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点省',
				dataIndex:'webshopProvince',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点市',
				dataIndex:'hireCity',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点地址',
				dataIndex:'webshopAddress',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'经营年份',
				dataIndex:'webshopStartyear',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'网点电话',
				dataIndex:'webshopPhone',
				hidden:this.type==3?false:true,
				width:80
			},{
				header:'职业状态',
				dataIndex:'careerType',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司名称',
				dataIndex:'hireCompanyname',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司月收入',
				dataIndex:'hireMonthlyincome',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'工作邮箱',
				dataIndex:'hireEmail',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'省份',
				dataIndex:'hireProvince',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'市份',
				dataIndex:'hireCompanyname',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司地址',
				dataIndex:'hireAddress',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司类别',
				dataIndex:'hireCompanytype',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司行业',
				dataIndex:'hireCompanycategory',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司规模',
				dataIndex:'hireCompanysize',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'工作年份',
				dataIndex:'hireStartyear',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'公司电话',
				dataIndex:'hireCompanyphone',
				hidden:this.type==4?false:true,
				width:80
			},{
				header:'是否有房',
				dataIndex:'strHouse',
				hidden:this.type==5?false:true,
				width:80
			},{
				header:'是否有房贷',
				dataIndex:'strHouseLoan',
				hidden:this.type==5?false:true,
				width:80
			},{
				header:'是否有车',
				dataIndex:'strCar',
				hidden:this.type==5?false:true,
				width:80
			},{
				header:'是否有车贷',
				dataIndex:'strCarLoan',
				hidden:this.type==5?false:true,
				width:80
			}
		]	
	})
					
	},   
});


