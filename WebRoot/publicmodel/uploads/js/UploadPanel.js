Date.prototype.getElapsed = function(A) {
	return Math.abs((A || new Date()).getTime() - this.getTime())
};

UploadPanel = Ext.extend(Ext.Panel, {
	fileList : null,
	swfupload : null,
	progressBar : null,
	progressInfo : null,
	uploadInfoPanel : null,
	isHiddenDelete : true,
 	
	constructor : function(config) {
		if(typeof(config.upLimit)!="undefined") {
	        if(null!=config.upLimit && config.upLimit > 0){
	        	this.isHiddenDelete = false;//当为查看附件列表的时候，隐藏掉删除列 add by chencc
	        }
		}
		var listStore = config.listStore;
		this.progressInfo = {
			filesTotal : 0,
			filesUploaded : 0,
			bytesTotal : 0,
			bytesUploaded : 0,
			currentCompleteBytes : 0,
			lastBytes : 0,
			lastElapsed : 1,
			lastUpdate : null,
			timeElapsed : 1
			
		};
		
		Ext.applyIf(this,config);
		
		var sm= new Ext.grid.CheckboxSelectionModel({
			
		});

		this.uploadInfoPanel = new Ext.grid.GridPanel({
			id : 'ContractGrid',
			region : 'north',
			height : 200,
			border : true,
			enableColumnMove : false,
			split : true,
			autoScroll : true,
			selModel : new Ext.grid.RowSelectionModel(),//行选择模式
			enableHdMenu : false,
			sm : sm,
			
			columns : [sm,new Ext.grid.RowNumberer({
				header : '序号',
				dataIndex : 'fileid',
				width : 35
			}),  {
				header : '文件名【已上传】',
				width : 100,
				dataIndex : 'filename',
				sortable : false,
				fixed : true,
				id : 'id_autoExpandColumnId'
			}, {
				header : '大小',
				width : 80,
				dataIndex : 'filesize',
				sortable : false,
				renderer : this.formatFileSize,
				fixed : true,
				align : 'right'
			}, {
				header : '类型',
				width : 60,
				dataIndex : 'extendname',
				sortable : false,
				renderer : this.formatIcon,
				fixed : true,
				align : 'center'
			},{
				header : '上传时间',
				width : 100,
				dataIndex : 'createtime',
				sortable : false,
				fixed : true,
				align : 'center'
			},{
				header : '下载',
				width : 50,
				dataIndex : 'fileid',
				sortable : false,
				fixed : true,
				align : 'center',
				renderer : function(v){
					return '<img src="'+basepath()+'images/download-start.png" onclick="download('+v+')" />';
				}
			},{
				header : '删除',
				width : 50,
				dataIndex : 'fileid',
				sortable : false,
				fixed : true,
				align : 'center',
				hidden : this.isHiddenDelete,
				renderer : function(v){
					//return '<img src="'+basepath()+'images/reset.gif" onclick="deleteFile1('+v+'); " />';
					return '<img src="'+basepath()+'images/reset.png"/>';
					
				}
			}],
			
			/*tbar : [{
				text : '删除文件',
				iconCls : 'btn-delete',
				listeners : {
					scope:this,
				 	'click' : function(grid,rowIndex,columnIndex,e){
				 		var grid = Ext.getCmp("ContractGrid");
	                	var rows = grid.getSelectionModel().getSelections();// 返回值为 Record 数组 
						if(rows.length==0){ 
							Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!'); 
						}else{
							var flag=this.flag;//如果是流程中的合同上传功能,则flag=0
							var ids = Array();
							for (var i = 0; i < rows.length; i++) {
								var fileid = rows[i].get("fileid");
								ids.push(fileid);
							}
							Ext.Ajax.request({
								url : __ctxPath +'/creditFlow/fileUploads/DeleMoreRsFileForm.do',
								params : {
									ids : ids,
									flag:flag
								},
								method : 'post',
								success : function() {
									Ext.Msg.alert('状态', '删除成功!');
									grid.getStore().removeAll();
									grid.getStore().reload();
									Ext.getCmp('id_addFile').enable();
								},
								failure : function(result, action) {
									Ext.Msg.alert('状态','删除失败!');
								}
							});
<<<<<<< .mine
=======
						//ContractView.remove(ids);
					}
                 }
			}
				 handler : function(){ 
				 
            		//var grid = Ext.getCmp("ContractGrid");
	                //var record = grid.getSelectionModel().getSelected();// 返回值为 Record 类型 
				 	var grid = Ext.getCmp("ContractGrid");
	                var rows = grid.getSelectionModel().getSelections();// 返回值为 Record 数组 
					if(rows.length==0){ 
						Ext.MessageBox.alert('警告', '最少选择一条信息，进行删除!'); 
					}else{ 
						var ids = Array();
						for (var i = 0; i < rows.length; i++) {
							var fileid = rows[i].get("fileid");
							//var fileid = grid.rows[i].FindControl("fileid");
							//grid.getStore().getAt(rows).get("fileid");
							//var status = listStore.getAt(dataIndex).get('fileid');
							ids.push(fileid);
>>>>>>> .r123280
						}
<<<<<<< .mine
	                 }
				}
=======
						//alert(listStore.length);
						
						Ext.Ajax.request({
								url : __ctxPath +'/credit/document/DeleMoreFile.do',
								params : {
									ids : ids
								},
								method : 'post',
								success : function() {
									Ext.Msg.alert('状态', '删除成功!');
									grid.getStore().removeAll();
									grid.getStore().reload();
									Ext.getCmp('id_addFile').enable();
								},
								failure : function(result, action) {
									Ext.Msg.alert('状态','删除失败!');
								}
							});
						//ContractView.remove(ids);
					}
				}
				
>>>>>>> .r123280
			}],*/
			
			autoExpandColumn : 'id_autoExpandColumnId',
			store : listStore,
			listeners : {
				scope : this,
				'cellclick' : function(grid,row,col,e){
					var fileid = grid.getStore().getAt(row).get("fileid");
					
					if(col == 7){
						this.deleteFile1(fileid,grid);
					}
				}
			}
		});
		
		this.progressBar = new Ext.ProgressBar({
			text : '等待中 0 %',
			animate : true
		});
		var autoExpandColumnId = Ext.id('fileName');
		this.fileList = new Ext.grid.GridPanel({
			border : false,
			enableColumnMove : false,
//			selModel : new Ext.grid.RowSelectionModel(),//行选择模式
			enableHdMenu : false,
			columns : [new Ext.grid.RowNumberer({
				header : '序号',
				width : 35
			}), {
				header : '文件名',
				width : 100,
				dataIndex : 'fileName',
				sortable : false,
				fixed : true,
				renderer : this.formatFileName,
				id : autoExpandColumnId
			}, {
				header : '大小',
				width : 80,
				dataIndex : 'fileSize',
				sortable : false,
				fixed : true,
				renderer : this.formatFileSize,
				align : 'right'
			}, {
				header : '类型',
				width : 60,
				dataIndex : 'fileType',
				sortable : false,
				fixed : true,
				renderer : this.formatIcon,
				align : 'center'
			}, {
				header : '进度',
				width : 100,
				dataIndex : '',
				sortable : false,
				fixed : true,
				renderer : this.formatProgressBar,
				align : 'center'
			}, {
				header : '&nbsp;',
				width : 28,
				dataIndex : 'fileState',
				renderer : this.formatState,
				sortable : false,
				fixed : true,
				align : 'center'
			}],
			autoExpandColumn : autoExpandColumnId,
			ds : new Ext.data.SimpleStore({
				fields : ['fileId', 'fileName', 'fileSize', 'fileType', 'fileState']
			}),
			bbar : [this.progressBar],
			tbar : [{
				id : 'id_addFile',
				text : '添加文件',
				iconCls : 'db-icn-add'
			}, {
				id : 'id_startUpload',
				text : '开始上传',
				iconCls : 'db-icn-upload_',
				handler : this.startUpload,
				scope : this
			}, {
				text : '停止上传',
				iconCls : 'db-icn-stop',
				handler : this.stopUpload,
				scope : this
			}, {
				text : '取消队列',
				iconCls : 'db-icn-cross',
				handler : this.cancelQueue,
				scope : this
			},{
				text : '清空列表',
				iconCls : 'db-icn-trash',
				handler : this.clearList,
				scope : this
			},'-','->','-','已上传文件↑↑'],
			listeners : {
				cellclick : {
					fn : function(grid, rowIndex, columnIndex, e) {
						if (columnIndex == 5) {
							var record = grid.getSelectionModel().getSelected();
							var fileId = record.data.fileId;
							var file = this.swfupload.getFile(fileId);
							if (file) {
								if (file.filestatus != SWFUpload.FILE_STATUS.CANCELLED) {
									this.swfupload.cancelUpload(fileId);
									if (record.data.fileState != SWFUpload.FILE_STATUS.CANCELLED) {
										record.set('fileState', SWFUpload.FILE_STATUS.CANCELLED);
										record.commit();
										this.onCancelQueue(fileId);
									}
								}
							}
						}
					},
					scope : this
				},
				render : {
					scope : this,
					fn : function() {
						var isDeleteHidden = this.isHiddenDelete;
						var thisPanel = this.uploadInfoPanel;
						global_myUp_fileNumLimit = global_up_fileNumLimit-thisPanel.getStore().getCount();
						if(global_myUp_fileNumLimit <= 0){
							if(isDeleteHidden == false){//当为查看的时候，不弹出提示框 add by chencc
								Ext.Msg.alert('提醒','您不能再上传附件了,此项只准上传'+global_up_fileNumLimit+'个附件。若想继续上传，请删除原有文件。');
							}
							Ext.getCmp('id_addFile').disable();
						}
						var grid = this.get(1).get(0);
						var em = grid.getTopToolbar().get(0).el.child('em');
						var placeHolderId = Ext.id();
						em.setStyle({
							position : 'relative',
							display : 'block'
						});
						em.createChild({
							tag : 'div',
							id : placeHolderId
						});
						var settings = {
							upload_url : this.uploadUrl,
							post_params : Ext.isEmpty(this.postParams) ? {} : this.postParams,
							flash_url : Ext.isEmpty(this.flashUrl)
									? 'http://www.swfupload.org/swfupload.swf'
									: this.flashUrl,
							file_post_name : Ext.isEmpty(this.filePostName) ? 'myUpload' : this.filePostName,
							file_size_limit : Ext.isEmpty(this.fileSize) ? '100 MB' : this.fileSize,
							file_types : Ext.isEmpty(this.fileTypes) ? '*.*' : this.fileTypes,
							file_types_description : this.fileTypesDescription,
							use_query_string : true,
							debug : false,
							button_width : '73',
							button_height : '20',
							button_placeholder_id : placeHolderId,
							button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
							button_cursor : SWFUpload.CURSOR.HAND,
							custom_settings : {
								scope_handler : this
							},
							file_queued_handler : this.onFileQueued,
							file_queue_error_handler : this.onFileQueueError,
							file_dialog_complete_handler : this.onFileDialogComplete,
							upload_start_handler : this.onUploadStart,
							upload_progress_handler : this.onUploadProgress,
							upload_error_handler : this.onUploadError,
							upload_success_handler : this.onUploadSuccess,
							upload_complete_handler : this.onUploadComplete
						};
						this.swfupload = new SWFUpload(settings);
						this.swfupload.uploadStopped = false;
						Ext.get(this.swfupload.movieName).setStyle({
							position : 'absolute',
							top : 0,
							left : -2
						});
						this.resizeProgressBar();
						this.on('resize', this.resizeProgressBar, this);
					},
					scope : this,
					delay : 100
				}
			}

		});
		UploadPanel.superclass.constructor.call(this, Ext.applyIf(config || {}, {
			layout : 'border',
			width : 500,
			height : 500,
			minWidth : 450,
			minHeight : 500,
			split : true,
			items : [this.uploadInfoPanel,
			{
				region : 'center', 
				layout : 'fit',
				margins : '0 -1 -1 -1',
				items : [this.fileList]
			}]
		}));
	},
	resizeProgressBar : function() {
		this.progressBar.setWidth(this.el.getWidth() - 5);
	},
	startUpload : function() {
		debugger
		if (this.swfupload) {
			this.swfupload.uploadStopped = false;
			var post_params = this.swfupload.settings.post_params;
			this.swfupload.startUpload();
			
		}
	},
	stopUpload : function() {
		if (this.swfupload) {
			this.swfupload.uploadStopped = true;
			this.swfupload.stopUpload();
		}
	},
	cancelQueue : function() {
		if (this.swfupload) {
			this.swfupload.stopUpload();
			var stats = this.swfupload.getStats();
			while (stats.files_queued > 0) {
				this.swfupload.cancelUpload();
				stats = this.swfupload.getStats();
			}
			this.fileList.getStore().each(function(record) {
				switch (record.data.fileState) {
					case SWFUpload.FILE_STATUS.QUEUED :
					case SWFUpload.FILE_STATUS.IN_PROGRESS :
						record.set('fileState', SWFUpload.FILE_STATUS.CANCELLED);
						record.commit();
						this.onCancelQueue(record.data.fileId);
						break;
					default :
						break;
				}
			}, this);
		}
	},
	clearList : function() {
		var store = this.fileList.getStore();
		store.each(function(record) {
			if (record.data.fileState != SWFUpload.FILE_STATUS.QUEUED
					&& record.data.fileState != SWFUpload.FILE_STATUS.IN_PROGRESS) {
				store.remove(record);
			}
		});
	},
//	getProgressTemplate : function() {
//		var tpl = new Ext.Template('<table class="upload-progress-table"><tbody>',
//				'<tr><td class="upload-progress-label"><nobr>已上传数:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{filesUploaded} / {filesTotal}</nobr></td>',
//				'<td class="upload-progress-label"><nobr>上传状态:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{bytesUploaded} / {bytesTotal}</nobr></td></tr>',
//				'<tr><td class="upload-progress-label"><nobr>已用时间:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{timeElapsed}</nobr></td>',
//				'<td class="upload-progress-label"><nobr>剩余时间:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{timeLeft}</nobr></td></tr>',
//				'<tr><td class="upload-progress-label"><nobr>当前速度:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{speedLast}</nobr></td>',
//				'<td class="upload-progress-label"><nobr>平均速度:</nobr></td>',
//				'<td class="upload-progress-value"><nobr>{speedAverage}</nobr></td></tr>', '</tbody></table>');
//		//tpl.compile();
//		return tpl;
//	},
	updateProgressInfo : function() {
		//this.getProgressTemplate().overwrite(this.uploadInfoPanel.body, this.formatProgress(this.progressInfo));
		this.formatProgress(this.progressInfo);
	},
	formatProgress : function(info) {
		var r = {};
		r.filesUploaded = String.leftPad(info.filesUploaded, 3, '&nbsp;');
		r.filesTotal = info.filesTotal;
		r.bytesUploaded = String.leftPad(Ext.util.Format.fileSize(info.bytesUploaded), 6, '&#160;');
		r.bytesTotal = Ext.util.Format.fileSize(info.bytesTotal);
		r.timeElapsed = this.formatTime(info.timeElapsed);
		r.speedAverage = Ext.util.Format.fileSize(Math.ceil(1000 * info.bytesUploaded / info.timeElapsed)) + '/s';
		r.timeLeft = this.formatTime((info.bytesUploaded === 0) ? 0 : info.timeElapsed
				* (info.bytesTotal - info.bytesUploaded) / info.bytesUploaded);
		var caleSpeed = 1000 * info.lastBytes / info.lastElapsed;
		r.speedLast = Ext.util.Format.fileSize(caleSpeed < 0 ? 0 : caleSpeed) + '/s';
		var p = info.bytesUploaded / info.bytesTotal;
		p = p || 0;
		this.progressBar.updateProgress(p, "上传中 " + Math.ceil(p * 100) + " %");
		return r;
	},
	formatTime : function(milliseconds) {
		var seconds = parseInt(milliseconds / 1000, 10);
		var s = 0;
		var m = 0;
		var h = 0;
		if (3599 < seconds) {
			h = parseInt(seconds / 3600, 10);
			seconds -= h * 3600;
		}
		if (59 < seconds) {
			m = parseInt(seconds / 60, 10);
			seconds -= m * 60;
		}
		m = String.leftPad(m, 2, '0');
		h = String.leftPad(h, 2, '0');
		s = String.leftPad(seconds, 2, '0');
		return h + ':' + m + ':' + s;
	},
	formatFileSize : function(_v, celmeta, record) {
		return '<div id="fileSize_' + record.data.fileId + '">' + Ext.util.Format.fileSize(_v) + '</div>';
	},
	formatFileName : function(_v, cellmeta, record) {
		return '<div id="fileName_' + record.data.fileId + '">' + _v + '</div>';
	},
	formatIcon : function(_v, cellmeta, record) {
		var returnValue = '';
		var extensionName = _v.substring(1);
		var fileId = record.data.fileId;
		if (_v) {
			var css = '.db-ft-' + extensionName.toLowerCase() + '-small';
			if (Ext.isEmpty(Ext.util.CSS.getRule(css, true))) { // 判断样式是否存在
				returnValue = '<div id="fileType_' + fileId
						+ '" class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;">'
						+ '&nbsp;&nbsp;&nbsp;&nbsp;' + extensionName.toUpperCase() + '</div>';
			} else {
				returnValue = '<div id="fileType_' + fileId + '" class="db-ft-' + extensionName.toLowerCase()
						+ '-small" style="height: 16px;background-repeat: no-repeat;"/>&nbsp;&nbsp;&nbsp;&nbsp;'
						+ extensionName.toUpperCase();
				+'</div>';
			}
			return returnValue;
		}
		return '<div id="fileType_'
				+ fileId
				+ '" class="db-ft-unknown-small" style="height: 16px;background-repeat: no-repeat;"/>&nbsp;&nbsp;&nbsp;&nbsp;'
				+ extensionName.toUpperCase() + '</div>';
	},
	formatProgressBar : function(_v, cellmeta, record) {
		var returnValue = '';
		switch (record.data.fileState) {
			case SWFUpload.FILE_STATUS.COMPLETE :
				if (Ext.isIE) {
					returnValue = '<div class="x-progress-wrap" style="height: 18px">'
							+ '<div class="x-progress-inner">'
							+ '<div style="width: 100%;" class="x-progress-bar x-progress-text">' + '100 %'
					'</div>' + '</div>' + '</div>';
				} else {
					returnValue = '<div class="x-progress-wrap" style="height: 18px">'
							+ '<div class="x-progress-inner">' + '<div id="progressBar_' + record.data.fileId
							+ '" style="width: 100%;" class="x-progress-bar">' + '</div>' + '<div id="progressText_'
							+ record.data.fileId
							+ '" style="width: 100%;" class="x-progress-text x-progress-text-back" />100 %</div>'
					'</div>' + '</div>';
				}
				break;
			default :
				returnValue = '<div class="x-progress-wrap" style="height: 18px">' + '<div class="x-progress-inner">'
						+ '<div id="progressBar_' + record.data.fileId + '" style="width: 0%;" class="x-progress-bar">'
						+ '</div>' + '<div id="progressText_' + record.data.fileId
						+ '" style="width: 100%;" class="x-progress-text x-progress-text-back" />0 %</div>'
				'</div>' + '</div>';
				break;
		}
		return returnValue;
	},
	formatState : function(_v, cellmeta, record) {
		var returnValue = '';
		switch (_v) {
			case SWFUpload.FILE_STATUS.QUEUED :
				returnValue = '<span id="' + record.id + '"><div id="fileId_' + record.data.fileId
						+ '" class="ux-cell-icon-delete"/></span>';
				break;
			case SWFUpload.FILE_STATUS.CANCELLED :
				returnValue = '<span id="' + record.id + '"><div id="fileId_' + record.data.fileId
						+ '" class="ux-cell-icon-clear"/></span>';
				break;
			case SWFUpload.FILE_STATUS.COMPLETE :
				returnValue = '<span id="' + record.id + '"><div id="fileId_' + record.data.fileId
						+ '" class="ux-cell-icon-completed"/></span>';
				break;
			default :
				alert('没有设置图表状态');
				break;
		}
		return returnValue;
	},
	onClose : function() {
		this.close();
	},
	onCancelQueue : function(fileId) {
		Ext.getDom('fileName_' + fileId).className = 'ux-cell-color-gray';// 设置文字颜色为灰色
		Ext.getDom('fileSize_' + fileId).className = 'ux-cell-color-gray';
		Ext.DomHelper.applyStyles('fileType_' + fileId, 'font-style:italic;text-decoration: line-through;color:gray');
	},
	onFileQueued : function(file) {
		
		var thiz = this.customSettings.scope_handler;
		
//		alert('1');
//		alert(global_up_fileNumLimit);
		
		if(thiz.progressInfo.filesTotal+1 == global_myUp_fileNumLimit){
			Ext.Msg.alert('提醒','您已上传'+global_up_fileNumLimit+'个附件，此项最多允许上传'+global_up_fileNumLimit+'个附件，请勿再添加附件！');
			Ext.getCmp('id_addFile').disable();
//			Ext.getCmp('id_addFile').destroy();
			
			thiz.fileList.getStore().add(new UploadPanel.FileRecord({
				fileId : file.id,
				fileName : file.name,
				fileSize : file.size,
				fileType : file.type,
				fileState : file.filestatus
			}));
			thiz.progressInfo.filesTotal += 1;
			thiz.progressInfo.bytesTotal += file.size;
			thiz.updateProgressInfo();
		}else if(thiz.progressInfo.filesTotal+1 > global_myUp_fileNumLimit){
			Ext.Msg.alert('错误','对不起，您将上传超过了'+global_up_fileNumLimit+'个附件，请不要在【添加文件】按钮变灰后继续点击。此功能已暂停使用，您需要刷新页面重新使用！');
			if(!Ext.getCmp('id_addFile')){
				Ext.getCmp('id_addFile').destroy();
			}
			if(!Ext.getCmp('id_startUpload')){
				Ext.getCmp('id_startUpload').destroy();
			}
		}else{
			thiz.fileList.getStore().add(new UploadPanel.FileRecord({
				fileId : file.id,
				fileName : file.name,
				fileSize : file.size,
				fileType : file.type,
				fileState : file.filestatus
			}));
			thiz.progressInfo.filesTotal += 1;
			thiz.progressInfo.bytesTotal += file.size;
			thiz.updateProgressInfo();
		}
	},
	onQueueError : function(file, errorCode, message) {
		var thiz = this.customSettings.scope_handler;
		try {
			if (errorCode != SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
				thiz.progressInfo.filesTotal -= 1;
				thiz.progressInfo.bytesTotal -= file.size;
			}
			thiz.progressInfo.bytesUploaded -= fpg.getBytesCompleted();
			thiz.updateProgressInfo();
		} catch (ex) {
			this.debug(ex);
		}
	},
	onFileDialogComplete : function(selectedFilesCount, queuedFilesCount) {
		// alert("selectedFilesCount:" + selectedFilesCount + "
		// queuedFilesCount:" + queuedFilesCount );
	},
	onUploadStart : function(file) {
//		alert('s');
	},
	onUploadProgress : function(file, completeBytes, bytesTotal) {
		var percent = Math.ceil((completeBytes / bytesTotal) * 100);
		Ext.getDom('progressBar_' + file.id).style.width = percent + "%";
		Ext.getDom('progressText_' + file.id).innerHTML = percent + " %";
		
		var thiz = this.customSettings.scope_handler;
		var bytes_added = completeBytes - thiz.progressInfo.currentCompleteBytes;
		thiz.progressInfo.bytesUploaded += Math.abs(bytes_added < 0 ? 0 : bytes_added);
		thiz.progressInfo.currentCompleteBytes = completeBytes;
		if (thiz.progressInfo.lastUpdate) {
			thiz.progressInfo.lastElapsed = thiz.progressInfo.lastUpdate.getElapsed();
			thiz.progressInfo.timeElapsed += thiz.progressInfo.lastElapsed;
		}
		thiz.progressInfo.lastBytes = bytes_added;
		thiz.progressInfo.lastUpdate = new Date();
		thiz.updateProgressInfo();
	},
	onUploadError : function(file, errorCode, message) {
		var thiz = this.customSettings.scope_handler;
		switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED :
				thiz.progressInfo.filesTotal -= 1;
				thiz.progressInfo.bytesTotal -= file.size;
				thiz.updateProgressInfo();
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED :
		}
	},
	onUploadSuccess : function(file, serverData) {
//		alert('2');
		var thiz = this.customSettings.scope_handler;
		if (Ext.util.JSON.decode(serverData).success) {
			var record = thiz.fileList.getStore().getById(Ext.getDom('fileId_' + file.id).parentNode.id);
			record.set('fileState', file.filestatus);
			record.commit();
			//Ext.getCmp('loadPanelid').getStore().reload();
			thiz.uploadInfoPanel.getStore().reload();
		}
		thiz.progressInfo.filesUploaded += 1;
        thiz.updateProgressInfo(); //显示上传信息
        
        
        /*update by jiang*/
        
        //设置上传按钮为 “对号”
        var btn_upload = Ext.getCmp('id_btn_upload');
        if(!Ext.isEmpty(btn_upload)){
        	btn_upload.setIconClass('okIcon');
        }
        
        //标识 上传成功
        var cpn_isUploadOk = Ext.getCmp('id_isUploadOk');
        if(!Ext.isEmpty(cpn_isUploadOk)){
        	cpn_isUploadOk.setValue('ok');
        }
        
        
	        
//        //返回 插入数据的主键
        var cpn_fileValue = Ext.getCmp('id_FileId');
        if(!Ext.isEmpty(cpn_fileValue)){
        	var fileValue=cpn_fileValue.getValue();
        	cpn_fileValue.setValue(fileValue+Ext.util.JSON.decode(serverData).id_file+',');
        }
        
		thiz.progressInfo.filesUploaded += 1;
		thiz.updateProgressInfo();
	},
	onUploadComplete : function(file) {
		if (this.getStats().files_queued > 0 && this.uploadStopped == false) {
			this.startUpload();
		}
	},
	/*删除文件*/
	deleteFile1 : function(id,grid){
		var flag=this.flag;//如果是流程中的合同上传功能,则flag=0
		Ext.Ajax.request({
			url : __ctxPath +'/creditFlow/fileUploads/DeleRsFileForm.do?fileid=' + id+"&flag="+flag,
			method : 'POST',
			success : function() {
				global_myUp_fileNumLimit += 1;
				Ext.Msg.alert('状态', '删除成功!');
				grid.getStore().removeAll();
				grid.getStore().reload();
				Ext.getCmp('id_addFile').enable();
			},
			failure : function(result, action) {
				Ext.Msg.alert('状态','删除失败!');
			}
		});
	}
});


function download(v){
    window.open(__ctxPath+"/creditFlow/fileUploads/DownLoadFileForm.do?fileid="+v,'_blank');
};

UploadPanel.FileRecord = Ext.data.Record.create([{
	name : 'fileId'
}, {
	name : 'fileName'
}, {
	name : 'fileSize'
}, {
	name : 'fileType'
}, {
	name : 'fileState'
}]);


Ext.reg('uploadpanel', UploadPanel);
