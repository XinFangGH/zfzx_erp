//investmentEnchashmentListView
/**
 * @author liny
 * @extends Ext.Panel
 * @description 投资人系统账户
 * @company 智维软件
 * @createtime:
 */
investmentEnchashmentListView = Ext.extend(Ext.Panel, {
	
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();

		// 调用父类构造
		investmentEnchashmentListView.superclass.constructor.call(this, {
			id : 'investmentEnchashmentListView',
			title : "取现审核清单",
			region : 'center',
			layout : 'border',
			iconCls : "btn-tree-team17",
			items : [ this.searchPanel, this.gridPanel]
		});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		var anchor = '100%';
		var startDate = {};
		var endDate = {};
		
		this.searchPanel = new Ext.FormPanel({
			layout : 'column',
			region : 'north',
			border : false,
			height : 50,
			anchor : '96%',
			layoutConfig: {
               align:'middle'
            },
             bodyStyle : 'padding:15px 0px 10px 10px',
            items : [/*{
					name : 'projectStatus',
					xtype : 'hidden',
					value : this.projectStatus
				}, {
					name : 'isGrantedShowAllProjects',
					xtype : 'hidden',
					value : this.isGrantedShowAllProjects
				},*/{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '投资人',
						name : 'investPersonName',
						anchor : "90%",
						xtype : 'textfield'
					}]
		     	},{
		     		columnWidth :.25,
					layout : 'form',
					labelWidth : 80,
					labelAlign : 'right',
					border : false,
					items : [{
						fieldLabel : '取现申请时间',
						name : 'transferDate',
						anchor : "90%",
						format : 'Y-m-d',
						xtype : 'datefield'
					}]
		     	},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					labelWidth :80,
					items :[{
						text : '查询',
						xtype : 'button',
						scope : this,
						style :'margin-left:30px',
						anchor : "90%",
						iconCls : 'btn-search',
						handler : this.search
					}]
	     		},{
	     			columnWidth :.125,
					layout : 'form',
					border : false,
					items :[{
						text : '重置',
						style :'margin-left:30px',
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
					iconCls : 'btn-detail',
					text : '打印审核单',
					xtype : 'button',
					scope : this,
					hidden : isGranted('_print_enchashmentList')? false: true,
					handler : function() {
						this.beforeFundRateF();
					}
				}]
			});
		this.gridPanel = new HT.GridPanel({
			name : 'investmentEnchashmentGrid',
			region : 'center',
			tbar : this.topbar,
			notmask : true,
			// 不适用RowActions
			rowActions : false,
			url : __ctxPath + "/creditFlow/creditAssignment/bank/getListInfoObAccountDealInfo.do",
			fields : [{
				name : 'id',
				type : 'int'
			}, 'accountId','incomMoney', 'payMoney', 'transferType', 'shopId', 'shopName',
					'createId', 'dealType', 'createDate','accountName','accountNumber','khname',
					'transferDate', 'rechargeLevel', 'investPersonName', 'createName','bankName', 'bankNum', 'areaName', 'createName'],

			columns : [{
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : 'investmentPersonId',
				dataIndex : 'investmentPersonId',
				hidden : true
			}, {
				header : '投资人姓名',
				//hidden : this.isHiddenBranch?true : false,
				width : 100,
				dataIndex : 'investPersonName'
			}, {
				header : '取现金额',
				width : 130,
				align:"right",
				dataIndex : 'payMoney',
				renderer : function(value) {
					if (value == "") {
						return "0.00元";
					} else {
						return Ext.util.Format.number(value, ',000,000,000.00')
								+ "元";
					}
				}
			}, {
				header : '取现时间',
				width : 110,
				dataIndex : 'transferDate'
			}, {
				header : '系统账户名',
				width : 68,
				//dataIndex : 'currentInvestMoney'
				dataIndex :'accountName'
			}, {
				header : '系统账号',
				width : 110,
				sortable : true,
				//dataIndex : 'totalMoney'
				dataIndex : 'accountNumber'
			}, /*{
				header : '开户行',
				width : 70,
				//dataIndex : 'totalInvestMoney'
				dataIndex :'khname'
			},*/ {
				header : '门店',
				width : 70,
				dataIndex : 'shopName'
			}, {
				header : '操作人',
				width : 70,
				dataIndex : 'createName'
			}]
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
	
	},// end of the initComponents()
	
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
	},
	beforeFundRateF:function(){
		var gridPanel=this.gridPanel;
		var vRecords=gridPanel.getSelectionModel().getSelections();
		if(vRecords.length==0){
			Ext.ux.Toast.msg('操作信息','请选择任意记录');
			return;
		}else{
			var vCount=vRecords.length; //取得记录长度
			var vDatas="";
			for(var i=0;i<vCount;i++){
				var str={
					'id':vRecords[i].data.id
				}
				vDatas+=Ext.util.JSON.encode(str)+'@';
			}
			vDatas=vDatas.substr(0,vDatas.length-1);
		}
		window.open(__ctxPath+"/creditFlow/creditAssignment/bank/p_beforeFundRateHObAccountDealInfo.do?JsonData="+vDatas);
	}
});
