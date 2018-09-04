/**
 * @author
 * @class SysDatabaseBRView
 * @extends Ext.Panel
 * @description [SysDatabaseBR]管理
 * @company 智维软件
 * @createtime:
 */
SysDatabaseBRView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		SysDatabaseBRView.superclass.constructor.call(this, {
			title : '数据备份日志',
			region : 'center',
			id:'sysDatabaseBRView',
			layout : 'border',
			iconCls:"menu-flowManager",
			items : [ this.gridPanel ]
		});
	},
	initUIComponents : function() {

		this.topbar = new Ext.Toolbar( {
			items : [ {
				iconCls:'btn-copyrole',
				text : '手动备份数据库',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('SysDatabaseBRView_backup'),
				handler : this.createRs
			}/*,{
				iconCls :'btn-reseted',
				text : '还原已备份数据库',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('SysDatabaseBRView_restore'),
				handler : this.restoreRs
			}*/,{
				iconCls : 'btn-del',
				text : '删除备份数据库',
				xtype : 'button',
				scope : this,
				hidden:!isGranted('SysDatabaseBRView_del'),
				handler : this.removeSelRs
			} ]
		});
		this.gridPanel = new HT.GridPanel( {
			region : 'center',
			tbar : this.topbar,
			rowActions : true,
			id : 'SysDatabaseBRGrid',
			url : __ctxPath + "/system/listSysDatabaseBR.do",
			fields : [ {
				name : 'id',
				type : 'int'
			}, 'fileName', 'createDate', 'isAutoCreate','fileSize'],
			columns : [ {
				header : 'id',
				dataIndex : 'id',
				hidden : true
			}, {
				header : '文件名称',
				dataIndex : 'fileName'
			}, {
				header : '创建时间',
				align:'center',
				dataIndex : 'createDate'
			},{
				header : '文件大小',
				align:'center',
				dataIndex : 'fileSize',
			    renderer:function(fileS){
				      	 if (fileS < 1024) {
				      		   return  fileS+"B";
				      	 } 
				      	 else if (fileS < 1048576)
				      	 {
					           return Math.round((fileS/1024)*100/100)+ "K";
				      	 } 
				      	 else if (fileS < 1073741824) {
					          
				      		   return Math.round((fileS/1048576)*100/100) + "M";
				      	 } 
				      	 else 
				      	 {
				      		   return Math.round((fileS/1073741824)*100/100) + "G";
					     }
				}
			},{
				header : '创建类型',
				align:'center',
				dataIndex : 'isAutoCreate',
			    renderer:function(v){
				      		if(v==0){
				      			return "自动"
				      		}
				      		else if(v==1){
				      			return "手动"
				      		}
				}
			}, new Ext.ux.grid.RowActions( {
				header : '管理',
				width : 100,
				actions : [ {
					iconCls : 'btn-del',
					qtip : '删除',
					style : !isGranted('SysDatabaseBRView_del')?'margin:0 3px 0 3px;display:none':'margin:0 3px 0 3px'
				},{
					iconCls : 'btn-subordinate',
					qtip : '下载',
					style : !isGranted('SysDatabaseBRView_download')?'margin:0 3px 0 3px;display:none':'margin:0 3px 0 3px'
				}],
				listeners : {
					scope : this,
					'action' : this.onRowAction
				}
			}) ]
		});
		this.gridPanel.addListener('rowdblclick', this.rowClick);
		this.gridPanel.getStore().sort('createDate','DESC');
	},
	reset : function() {
		this.searchPanel.getForm().reset();
	},
	search : function() {
		$search( {
			searchPanel : this.searchPanel,
			gridPanel : this.gridPanel
		});
	},
	rowClick : function(grid, rowindex, e) {
		grid.getSelectionModel().each(function(rec) {
			new SysDatabaseBRForm( {
				id : rec.data.id
			}).show();
		});
	},
	//创建记录
	createRs : function() {
		
	 var  gridPanel=this.gridPanel;
	 var mk = new Ext.LoadMask(Ext.getBody(),{  
			msg: '正在备份数据，请稍候！',  
			removeMask: true //完成后移除  
     });  
	 mk.show(); //显示  
		 Ext.Ajax.request({
								url:__ctxPath + '/system/saveSysDatabaseBR.do',
								method : 'post',
								success : function(response, options) {
									var obj = Ext.decode(response.responseText);
								    gridPanel.getStore().reload();
								    mk.hide();
								    Ext.ux.Toast.msg('操作信息', '数据库备份成功!');
								} 
		});
		
	},
	restoreRs:function(){
		
		var  gsm = this.gridPanel.getSelectionModel();//获取选择列
		var  gridPanel=this.gridPanel;
        var rows = gsm.getSelections();//根据选择列获取到所有的行
           
        if(rows.length<=0){
        	Ext.ux.Toast.msg('操作信息', '请选择要还原的备份文件!');
        	return false;
        }
        else if(rows.length>1){
        	Ext.ux.Toast.msg('操作信息', '只能选择一条备份文件!');
        	return false;
        }
        var id =rows[0].get('id');
        var mk = new Ext.LoadMask(Ext.getBody(),{  
			msg: '正在还原备份数据，请稍候！',  
			removeMask: true //完成后移除  
     	});  
        mk.show(); //显示  
	    Ext.Ajax.request({
								url:__ctxPath + '/system/restoreSysDatabaseBR.do',
								method : 'post',
								params:{id:id},
								success : function(response, options) {
									var obj = Ext.decode(response.responseText);
								    gridPanel.getStore().reload();
								    mk.hide();
								    Ext.ux.Toast.msg('操作信息', '数据库还原成功,请刷新页面!');
								},
							    failure : function() {  
									mk.hide();
                                    Ext.ux.Toast.msg('操作信息', '数据库还原失败,请联系管理员!');
                				}  
		});
		
	},
	//按ID删除记录
	removeRs : function(id) {
		$postDel( {
			url : __ctxPath + '/system/multiDelSysDatabaseBR.do',
			ids : id,
			grid : this.gridPanel
		});
	},
	//把选中ID删除
	removeSelRs : function() {
		$delGridRs( {
			url : __ctxPath + '/system/multiDelSysDatabaseBR.do',
			grid : this.gridPanel,
			idName : 'id'
		});
	},
	//编辑Rs
	download : function(obj) {
		   
		    var id=obj.id;
		    window.open(__ctxPath+'/system/downloadSysDatabaseBR.do?id='+id,'_blank');
	},
	//行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (action) {
		case 'btn-del':
			this.removeRs.call(this, record.data.id);
			break;
		case 'btn-subordinate':
			this.download.call(this, record);
			break;
		default:
			break;
		}
	}
});
