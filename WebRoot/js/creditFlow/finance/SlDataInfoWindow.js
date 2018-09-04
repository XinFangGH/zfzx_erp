SlDataInfoWindow = Ext.extend(Ext.Window, {
	layout : 'anchor',
	anchor : '100%',
	constructor : function(_cfg) {
		
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SlDataInfoWindow.superclass.constructor.call(this, {
			        width:'50%',
			        buttonAlign:'center',
			        title:'日结日期'+this.dayDate+'同步日期详细',
			        modal:true,
					items : [this.gridPanel]
					
					
				})
	},
	initUIComponents : function() {
		this.topbar= new Ext.Toolbar({
							items : [{
										iconCls : 'btn-del',
										text : '撤销',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_createAccount_b')?false:true,
										handler : this.cancel
									},'-', {
										iconCls : 'btn-add',
										text : '重新生成',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_remove_b')?false:true,
										handler : this.reCreate
									},'-', {
										iconCls : 'btn-edit',
										text : '发送',
										xtype : 'button',
										scope : this,
										hidden : isGranted('_edit_b')?false:true,
										handler : this.send
									}]});
	this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			autoHeight : true,
			showPaging:false,
			hiddenCm:true,
			id:"SlDataInfoGrid",
			url : __ctxPath + "/creditFlow/finance/listSlDataInfo.do?dataId="+this.dataId,
			fields : [ {
				name : 'dataInfoId',
				type : 'long'
			}, 'dataTypeName', 'createTime', 'filePath', 'dataId','dataTypeStatus'],
			columns : [ {
				header : 'id',
				dataIndex : 'dataInfoId',
				hidden : true
			},{
			   	header : '数据类型名称',
			   	width:150,
				dataIndex : 'dataTypeName'
			}, {
				header : '生成时间',
				width:150,
				dataIndex : 'createTime'
			}, {
				header : '生成文件<font color="red">(点击下载)</font>)',
				dataIndex : 'filePath',
				renderer : function(value){
						return '<img title="下载" src="'+ __ctxPath+ '/images/btn/system/xls.png"/>'
					
				}
			}]
		//end of columns
				});
				this.gridPanel.addListener('cellclick', this.cellClick);
		},
		cellClick : function(grid,rowIndex,columnIndex,e){
				if(4==columnIndex){
				  
					var dataInfoId = grid.getStore().getAt(rowIndex).get('dataInfoId');
					
								window.open( __ctxPath + "/creditFlow/finance/downloadSlDataInfo.do?dataInfoId="+dataInfoId,'_blank');
						
							
							
						

				
				
				}
		
	},
	cancel :function(){
		
	var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else{	
					var record=s[0];
					var dayDate=this.dayDate;
					var dataId=this.dataId;
					var dataTypeStatus=record.data.dataTypeStatus;
					 var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在撤销。。。。',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
			   	          Ext.Ajax.request({
								url : __ctxPath + '/webservice/cancDateCancelDataByType.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									var object=Ext.util.JSON.decode(response.responseText);
									  var flag=object.flag;
									  if(flag==0){
									  	var result=object.result;
									     Ext.ux.Toast.msg('状态', result);
									  }else{
									      Ext.ux.Toast.msg('状态', '撤销失败');
									  }
									var gridPanel = Ext.getCmp('SlDataInfoGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									dataTypeStatus:dataTypeStatus
								}
								
								})
				}	
		 
	},
	reCreate :function(){
	var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else{	
					var record=s[0];
					var dayDate=this.dayDate;
					var dataId=this.dataId;
					var dataTypeStatus=record.data.dataTypeStatus;
					 var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在重新生成。。。。',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
					 Ext.ux.Toast.msg('状态', '重新生成需要点时间，请耐心等候');
			   	          Ext.Ajax.request({
			   	          	waitMsg : '正在重新生成',
								url : __ctxPath + '/webservice/createExcelReCreateExcelByType.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '重新生成成功');
									var gridPanel = Ext.getCmp('SlDataInfoGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									dataTypeStatus:dataTypeStatus
								}
								
								})
				}	
		 
			   		
	
	},
	send:function(){
	
		var s = this.gridPanel.getSelectionModel().getSelections();
				if (s <= 0) {
					Ext.ux.Toast.msg('操作信息','请选中任何一条记录');
					return false;
				}else{	
					var record=s[0];
					var dayDate=this.dayDate;
					var dataId=this.dataId;
					var dataTypeStatus=record.data.dataTypeStatus;
					 var myMask = new Ext.LoadMask(Ext.getBody(), {
                        msg: '正在发送……',
                        removeMask: true //完成后移除
                       });
                   myMask.show();
					 Ext.ux.Toast.msg('状态', '发送需要点时间，请耐心等候');
			   	          Ext.Ajax.request({
								url : __ctxPath + '/webservice/getDateFromExcelReSendDataByType.do',
								method : 'POST',
								success : function(response, request) {
									  myMask.hide();
									 var object=Ext.util.JSON.decode(response.responseText);
									var flag=object.flag;
									var result=object.result;
									  if(flag==0){
									  	   Ext.ux.Toast.msg('状态', result);
									  }else{
									      Ext.ux.Toast.msg('状态', '发送失败'+result);
									  }
									var gridPanel = Ext.getCmp('SlDataInfoGrid');
									if (gridPanel != null) {
										gridPanel.getStore().reload();
									}
								},
								failure : function(response) {
									  myMask.hide();
									Ext.ux.Toast.msg('状态', '操作失败，请重试！');
									
								},
								params : {
									dataId : dataId,
									factdate : dayDate,
									dataTypeStatus:dataTypeStatus
								}
								
								})
				}	
	}
	
	
});
