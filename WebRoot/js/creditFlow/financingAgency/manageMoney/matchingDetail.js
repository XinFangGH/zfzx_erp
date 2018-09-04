/**
 * @author
 * @class PlMmOrderChildrenOrView
 * @extends Ext.Panel
 * @description [PlMmOrderChildrenOr]管理
 * @company 智维软件
 * @createtime:
 */
matchingDetail = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		matchingDetail.superclass.constructor.call(this, {
					id : 'matchingDetail',
					title : '实际债权清单',
					region : 'center',
					layout : 'border',
					height : 500,
					width : 1200,
					items : [this.gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		// 初始化搜索条件Panel
//author jiang
		var dl_ttfirst = new  Ext.ToolTip({
			title : '打印须知：',
/*			text :  '<p>0. 请做好备份</p>'+
					'<p>1. 如果（本项目）第一次使用此功能，请先“下载所有担保材料附件”，获得文件架结构【附：您可以保留此压缩包，下次无需再下载】;</p>' +
					'<p>2. 如果您确定有（本项目）担保材料的文件夹结构【即您下载的 zip包】，并且您要上传的附件是最新的【即无需关心以前上传的内容】，请直接上传【无需先下载】;</p>' +
					'<p>3. <u>【切记】上传文件时您要确保：A. 您上传的是.zip压缩文件 ！B. 下载后的压缩文件不能直接上传！ C. 不能改变下载的文件夹结构以及名字！' +
							'D. 标准上传压缩包结构为：“项目编号.zip/项目编号/担保材料”。</u></p>',*/
			text : '如果没选择就按默认打印',
			dismissDelay : 60000,
			width : 400,
			animate: true,
			hideDelay : 1000
		});
		var dl_ttfirst = new  Ext.ToolTip({
			title : '打印须知：',
			text : '如果没选择就按默人的第一起列表打印',
			dismissDelay : 60000,
			width : 400,
			animate: true,
			hideDelay : 1000
		});
			var dl_ttevery = new  Ext.ToolTip({
			title : '打印须知：',
			text : '如果没选择就按本期打印',
			dismissDelay : 60000,
			width : 400,
			animate: true,
			hideDelay : 1000
		});
			var dl_ttend = new  Ext.ToolTip({
			title : '打印须知：',
			text : '如果没选择就按默认的最后一期列表打印',
			dismissDelay : 60000,
			width : 400,
			animate: true,
			hideDelay : 1000
		});
		this.topbar = new Ext.Toolbar({
			items : [/*{
								iconCls : 'btn-print',
								text : '打印第一期',
								xtype : 'button',
								scope : this,
								tooltip : dl_ttfirst,
								handler : this.createRs1
							},*/{
								iconCls : 'btn-print',
								text : '打印',//每期
								xtype : 'button',
								tooltip : dl_ttevery,
								scope : this,
								handler : this.createRs2
							},/*{
								iconCls : 'btn-print',
								text : '打印最后一期',
								xtype : 'button',
								tooltip : dl_ttend,
								scope : this,
								handler : this.createRs3
							},*/{
								iconCls : 'btn-xls1',
								text : '导出',
								xtype : 'button',
								scope : this,
								handler : this.exportExcel
							},"->",	{xtype:'label',html : '【<font style="line-height:20px" color=red>投资人：</font>'+this.investPersonName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp理财产品名称：</font>'+this.mmName},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 购买金额：</font>'+Ext.util.Format.number(this.buyMoney,',000,000,000.00')+"元"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp订单期限：</font>'+this.orderlimit+"天"},
						{xtype:'label',html : '<font color=red>&nbsp;&nbsp&nbsp;&nbsp 承诺总收益：</font>'+Ext.util.Format.number(this.promisIncomeSum,',000,000,000.00')+"元"}
						,{xtype:'label',html : '&nbsp;&nbsp&nbsp;&nbsp<font color=red>当前已实现收益：</font>'+Ext.util.Format.number(this.currentGetedMoney,',000,000,000.00')+'元】'}
							]
		});

		this.gridPanel = new HT.GridPanel({
			region : 'center',
			tbar : this.topbar,
			// 使用RowActions
			id : 'matchingDetailGrid',
			
		//	hiddenCm :true,
			url : __ctxPath
					+ "/creditFlow/financingAgency/listPlMmOrderChildrenOr.do?orderId="+this.orderId,
			fields : [{
						name : 'matchId',
						type : 'int'
					}, 'orderId', 'childrenorId', 'investPersonId',
					'investPersonName', 'mmplanId', 'mmName', 'parentOrBidId',
					'parentOrBidName', 'matchingMoney', 'childrenOrDayRate',
					'matchingStartDate', 'matchingEndDate', 'matchingLimit',
					'matchingEndDateType', 'matchingGetMoney', 'matchingState', 'matchPersonId', 'matchPersonName'],
			columns : [{
						header : 'matchId',
						dataIndex : 'matchId',
						hidden : true
					}, {
						header : '债权的名称',
						dataIndex : 'parentOrBidName'
					}, {
						header : '匹配金额',
						dataIndex : 'matchingMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '债权的日化利率',
						dataIndex : 'childrenOrDayRate',
						renderer:function(v){
								return v+"%";
						}
					}, {
						header : '匹配开始日',
						dataIndex : 'matchingStartDate'
					}, {
						header : '匹配结束日',
						dataIndex : 'matchingEndDate'
					}, {
						header : '匹配期限',
						dataIndex : 'matchingLimit',
						align : 'right',
						renderer:function(v){
								return v+"天";
						}
					}, {
						header : '匹配收益',
						dataIndex : 'matchingGetMoney',
						align : 'right',
						renderer:function(v){
							return Ext.util.Format.number(v,',000,000,000.00')+"元";
						}
					}, {
						header : '匹配人',
						dataIndex : 'matchPersonName',
						align : 'center',
						renderer:function(v){
							if(Ext.isEmpty(v)){
							   return "系统"
							}else{
							  return v;
							}
						}
					}/*, {
						header : 'matchingState',
						dataIndex : 'matchingState'
					}*/]
				// end of columns
		});

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
	// GridPanel行点击处理事件
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
					new PlMmOrderChildrenOrForm({
								matchId : rec.data.matchId
							}).show();
				});
	},
	// 创建记录
	createRs1 : function() {
		var orderIds=this.orderId;
	//	new PlMmOrderChildrenOrForm().show();
	    var matchIds = $getGdSelectedIds(this.gridPanel,'matchId');
	  
	    var matchIds = $getGdSelectedIds(this.gridPanel,'matchId');
	    var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount());
		var vCount = vRecords.length; // 得到记录长度
		var st = "";
	      if (vCount > 0) {
			  var date=vRecords[0].data.matchingStartDate;
			for (var i = 0; i < vCount; i++) {
			if(date== vRecords[i].data.matchingStartDate){
		         st=st+vRecords[i].data.matchId+","
				}
			
				
			}
		 }
		st=st.substring(0,st.length-1);	
		
		var firstst=st;
		Ext.Ajax.request({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/printcommfirstReportTemplate.do?reportKey=obligatoryrightchildrenfirst&orderIds='+orderIds+'&matchIds='+matchIds+'&firstst='+firstst,
							method : 'post',
							success : function(response, opts) {
								var respText = response.responseText;
			                 	var object = Ext.util.JSON.decode(respText);
								window.open(__ctxPath+ '/report/swf.html','blank');
							},
							failure : function(form, action) {
								
							}
						});
						
	},
	// 创建记录
	createRs2 : function() {
			var orderIds=this.orderId;
	    var matchIds = $getGdSelectedIds(this.gridPanel,'matchId');
	  
	    var matchIds = $getGdSelectedIds(this.gridPanel,'matchId');
	    var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount());
		var vCount = vRecords.length; // 得到记录长度
		var st = "";
		
	      if (vCount > 0) {
			  var date="";
			  var a=0;
			for (var i = vCount-1; i >=0; i--) {
			if(Date.parse(vRecords[i].data.matchingStartDate)<= new Date().getTime()){
				a++; 
				if(a==1){
					date=vRecords[i].data.matchingStartDate
				}
				if(date!=vRecords[i].data.matchingStartDate){
				   break;
				}
		         st=st+vRecords[i].data.matchId+","
		         
				}
			
				
			}
		 }
		st=st.substring(0,st.length-1);	
		var everyst=st;
		Ext.Ajax.request({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/printcommReportTemplate.do?reportKey=obligatoryrightchildrenevery&orderIds='+orderIds+'&matchIds='+matchIds+'&everyst='+everyst,
							method : 'post',
							success : function(response, opts) {
								var respText = response.responseText;
			                 	var object = Ext.util.JSON.decode(respText);
								window.open(__ctxPath+ '/report/swf.html','blank');
							},
							failure : function(form, action) {
								
							}
						});
						
	},
	// 创建记录
	createRs3 : function() {
		var orderIds=this.orderId;
	//	new PlMmOrderChildrenOrForm().show();
		var s = this.gridPanel.getSelectionModel().getSelections();
        var matchIds = $getGdSelectedIds(this.gridPanel,'matchId');
	    var vRecords = this.gridPanel.getStore().getRange(0,this.gridPanel.getStore().getCount());
		var vCount = vRecords.length; // 得到记录长度
		var st = "";
	    if (vCount > 0) {
			  var date=vRecords[0].data.matchingStartDate;
			for (var i = 0; i < vCount; i++) {
			if(date== vRecords[i].data.matchingStartDate){
		         st=st+vRecords[i].data.matchId+","
				}
			
				
			}
		 }
		st=st.substring(0,st.length-1);	
		var endst=st;
		Ext.Ajax.request({
							waitMsg : '正在提交查询',
							url : __ctxPath + '/system/printcommendReportTemplate.do?reportKey=obligatoryrightchildrenend&orderIds='+orderIds+'&matchIds='+matchIds+'&endst='+endst,
							method : 'post',
							success : function(response, opts) {
								var respText = response.responseText;
			                 	var object = Ext.util.JSON.decode(respText);
								window.open(__ctxPath+ '/report/swf.html','blank');
							},
							failure : function(form, action) {
								
							}
						});
						
	},
	exportExcel:function(){
		window.open(__ctxPath+ "/creditFlow/financingAgency/generateExcelUrlPlMmOrderChildrenOr.do?orderId="+this.orderId,'_blank');
		/*Ext.Ajax.request({
		   url:  __ctxPath+ "/creditFlow/financingAgency/generateExcelUrlPlMmOrderChildrenOr.do",
		   success: function(response, opts){
		   		var respText = response.responseText;
				var alarm_fields = Ext.util.JSON.decode(respText);
				var url = alarm_fields.url;
				var filepath = basepath()+url;
				window.open(filepath,'Download');
		   },
		   failure: function(response, opts){
		   		Ext.ux.Toast.msg('操作信息', '保存信息失败！');
		   },
		   params: {
		   		orderId:this.orderId
		   }
		});*/
	},
	// 按ID删除记录
	removeRs : function(id) {
		$postDel({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	// 把选中ID删除
	removeSelRs : function() {
		$delGridRs({
			url : __ctxPath
					+ '/creditFlow.financingAgency.manageMoney/multiDelPlMmOrderChildrenOr.do',
			grid : this.gridPanel,
			idName : 'matchId'
		});
	},
	// 编辑Rs
	editRs : function(record) {
		new PlMmOrderChildrenOrForm({
					matchId : record.data.matchId
				}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
			case 'btn-del' :
				this.removeRs.call(this, record.data.matchId);
				break;
			case 'btn-edit' :
				this.editRs.call(this, record);
				break;
			default :
				break;
		}
	}
});
