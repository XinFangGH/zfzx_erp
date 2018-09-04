/**
 * @author
 * @createtime
 * @class ViewFileWindow
 * @extends Ext.Window
 * @description ArchBorrow表单
 * @company 智维软件
 */
ViewFileWindow = Ext.extend(Ext.Window, {
//	docSrc:__ctxPath +'/images/filetype/doc.png',
//	excelSrc:__ctxPath +'/images/filetype/excel.png',
//	exeSrc:__ctxPath +'/images/filetype/exe.png',
//	imageSrc:__ctxPath +'/images/filetype/image.png',
//	pdfSrc:__ctxPath +'/images/filetype/pdf.png',
//	rarSrc:__ctxPath +'/images/filetype/rar.png',
//	txtSrc:__ctxPath +'/images/filetype/txt.png',
	
	index:0,
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.index=this.startIndex;
		this.initUIComponents();
		ViewFileWindow.superclass.constructor.call(this, {
			id : 'ViewFileWindowWin',
			region : 'center',
			layout : 'form',
			frame:true,
			border:true,
			modal : true,
			height : 600,
			width : 550,
			maximizable : true,
			title : '附件预览',
			buttonAlign : 'center',
			buttons : [ {
				text : '',
				iconCls : 'btn-top',
				scope : this,
				handler : this.up
			}, {
				text : '',
				iconCls : 'btn-down',
				scope : this,
				handler : this.down
			} ],
			items : [{
				xtype : 'iframepanel',
				id:'ViewFileWindow.iframepanel',
				frame:false,
				border:false,
				style: 'margin:0 auto',
				loadMask : true,
				autoLoad:false,
				listeners : {

					afterrender : function(iframe) {}
				}
			},{

				xtype : 'panel',
				id:'ViewFileWindow.panel',
				height:30,
				frame:false,
				border:false,
				autoLoad:false
//				,
//				html:
//					'<div align="center">'
//					+this.viewConfig[this.index].fileName
//					+'</div>'
//					+'<div align="center">'
//					+'<a href="'+ __ctxPath + '/attachFiles/'+ this.viewConfig[this.index].filePath+'" target="_blank">下载</a>'
//					+'</div>'
				
			}],
			listeners : {
				'afterrender' : function(window) {
					//调整上下高度
					var iframe=Ext.getCmp('ViewFileWindow.iframepanel');
					var panel=Ext.getCmp('ViewFileWindow.panel');
					var wh=window.getInnerHeight();
					var ph=panel.getHeight();
					iframe.setHeight(wh-ph);
					
					//过滤预览文件
					if(window.viewConfig&&window.viewConfig.length>0){
						//iframe
						var typeIndex = window.viewConfig[window.index].fileName.lastIndexOf('.');
						var typeLastdex=window.viewConfig[window.index].fileName.length;
						var fileType  = window.viewConfig[window.index].fileName.substring((typeIndex+1),typeLastdex);
						if(fileType=='png'||fileType=='PNG'
							||fileType=='gif'||fileType=='GIF'
								||fileType=='jpg'||fileType=='JPG'
									||fileType=='bmp'||fileType=='BMP'	
											||fileType=='xml'||fileType=='XML'
												||fileType=='txt'||fileType=='TXT'
													||fileType=='html'||fileType=='HTML'
						){
							iframe.setSrc(
								 __ctxPath + '/attachFiles/'
									+ window.viewConfig[window.index].filePath
							);
						}
						//panel
						panel.body.update(

								'<div align="center">'
								+this.viewConfig[this.index].fileName
								+'</div>'
								+'<div align="center">'
								+'<a href="'+ __ctxPath + '/attachFiles/'+ this.viewConfig[this.index].filePath+'" target="_blank">下载</a>'
								+'</div>'
						);
					
					
					
					}
					
					
					//分页
				
					
			}}
		});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {},
	
	up : function() {
		this.index=this.index-1;
		if(this.index>=0){
			//iframe
			var iframe=Ext.getCmp('ViewFileWindow.iframepanel');
			var typeIndex = this.viewConfig[this.index].fileName.lastIndexOf('.');
			var typeLastdex=this.viewConfig[this.index].fileName.length;
			var fileType  = this.viewConfig[this.index].fileName.substring((typeIndex+1),typeLastdex);
			if(fileType=='png'||fileType=='PNG'
				||fileType=='gif'||fileType=='GIF'
					||fileType=='jpg'||fileType=='JPG'
						||fileType=='bmp'||fileType=='BMP'	
								||fileType=='xml'||fileType=='XML'
									||fileType=='txt'||fileType=='TXT'
										||fileType=='html'||fileType=='HTML'
			){
				iframe.setSrc(
					 __ctxPath + '/attachFiles/'
						+ this.viewConfig[this.index].filePath
				);
			}else{
				iframe.resetFrame();
			}
			
			//panel
			var panel=Ext.getCmp('ViewFileWindow.panel');
			panel.body.update(

					'<div align="center">'
					+this.viewConfig[this.index].fileName
					+'</div>'
					+'<div align="center">'
					+'<a href="'+ __ctxPath + '/attachFiles/'+ this.viewConfig[this.index].filePath+'" target="_blank">下载</a>'
					+'</div>'
			);
			
		}else{
			this.index=0;
			Ext.ux.Toast.msg('操作信息', '已是第一张！');
		}
	},
	down : function() {
		this.index=this.index+1;
		if(this.index<this.viewConfig.length){
			//iframe
			var iframe=Ext.getCmp('ViewFileWindow.iframepanel');
			var typeIndex = this.viewConfig[this.index].fileName.lastIndexOf('.');
			var typeLastdex=this.viewConfig[this.index].fileName.length;
			var fileType  = this.viewConfig[this.index].fileName.substring((typeIndex+1),typeLastdex);
			if(fileType=='png'||fileType=='PNG'
				||fileType=='gif'||fileType=='GIF'
					||fileType=='jpg'||fileType=='JPG'
						||fileType=='bmp'||fileType=='BMP'	
								||fileType=='xml'||fileType=='XML'
									||fileType=='txt'||fileType=='TXT'
										||fileType=='html'||fileType=='HTML'
			){
				iframe.setSrc(
					 __ctxPath + '/attachFiles/'
						+ this.viewConfig[this.index].filePath
				);
			}else{
				iframe.resetFrame();
			}
			//panel
			var panel=Ext.getCmp('ViewFileWindow.panel');
			panel.body.update(

					'<div align="center">'
					+this.viewConfig[this.index].fileName
					+'</div>'
					+'<div align="center">'
					+'<a href="'+ __ctxPath + '/attachFiles/'+ this.viewConfig[this.index].filePath+'" target="_blank">下载</a>'
					+'</div>'
			);
			
			
		}else{
			this.index=this.viewConfig.length-1;
			Ext.ux.Toast.msg('操作信息', '已是最后一张！');
		}
	}

});