//
//var ErrandsRegisterDetail=function(dateId){
//
//	var detailPanell = new Ext.Panel({
//				autoHeight : true,
//				autoWidth:true,
//				border : false,
//				autoLoad : {
//					url :  + dateId
//				}
//	});
//	var window = new Ext.Window({
//				title:'',
//				iconCls:'',
//				id : '',
//				width : 460,
//				height : 280,
//				modal : true,
//				autoScroll:true,
//				layout : 'form',
//				buttonAlign : 'center',
//				items : [detailPanell],
//				buttons : [{
//							text : '关闭',
//							iconCls : 'btn-cancel',
//							handler : function() {
//								window.close();
//							}
//						}]
//	});
//	
//	window.show();
//};


/**
 * 显示请假的详细信息
 * @param {} dateId
 */
ErrandsRegisterDetail = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.init();
		ErrandsRegisterDetail.superclass.constructor.call(this, {
					title : '请假详细信息',
					id : 'ErrandsRegisterDetail',
					iconCls : 'menu-holiday',
					maximizable : true,
					autoScroll:true,
					layout : 'form',
					modal : true,
					height : 480,
					width : 770,
					border : false,
					defaults : {
						anchor : '98%,98%'
					},
					buttonAlign : 'center',
					buttons :  [ {
									text : '关闭',
									iconCls : 'close',
									handler : this.closePanel,
									scope : this
								}],
					items : [
					         this.detailPanel,
					         this.flowdetailPanel,
					         this.flowImagePanel
					        ]
				});
	},
	/**
	 * 关闭Panel
	 */
	closePanel : function() {
		this.close();
	},
	
	/**
	 * init the components
	 */
	init : function() {
		this.detailPanel = new Ext.Panel({
			border : false,
			autoScroll:true,
			autoLoad:{
				url:__ctxPath+ '/pages/personal/errandsRegisterDetail.jsp?dateId='+ this.dateId
			}
		});
		// 显示流程审批的表单
		this.flowdetailPanel=new Ext.Panel({
			border :false,
			autoHeight:true,
			autoLoad:{
				url:__ctxPath+'/flow/processRunDetail.do?runId='+this.runId,
				nocache: true
			}
		});
		
		this.flowImagePanel=new Ext.Panel({
			
	 		//autoHeight:true,
	 		layout : 'column',
	 		border : false,
	 		//autoScroll:true,
	 		bodyStyle : 'padding:5px;',
	 		//margin:'5 5 5 5',
	 		items : [
	 			new Ext.Panel({
	 				title:'流程示意图',
	 				width : '97%',
	 				html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+this.runId+'"/>'
	 			})
	 		]});
	}// end of init
});