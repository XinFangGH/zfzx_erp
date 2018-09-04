/**
 * @createtime:2010-01-20
 * @author csx
 * @description 公文拟稿发文界面
 * @class ArchivesDetailWin
 * @extends Ext.Panel
 */
ArchivesDetailWin = Ext.extend(Ext.Window, {
	formPanel : null,
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.init();
		ArchivesDetailWin.superclass.constructor.call(this, {
					title : '公文详情',
					id : 'ArchivesDetailWin',
					iconCls : 'btn-archives-detail',
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
				url:__ctxPath+ '/pages/archive/archiveInfo.jsp?archivesId='+ this.archivesId+ '&rand='+Math.random()
			}
		});
		// 显示流程审批的表单
		this.flowdetailPanel=new Ext.Panel({
			border :false,
			autoHeight:true,
			autoLoad:{
				url:__ctxPath+'/flow/processRunDetail.do?runId='+this.runId+ '&rand='+Math.random(),
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
	 				html:'<img src="'+__ctxPath+ '/jbpmImage?runId='+this.runId+ '&rand='+Math.random()+'"/>'
	 			})
	 		]});
	}// end of init
});