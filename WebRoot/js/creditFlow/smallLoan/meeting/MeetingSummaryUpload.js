/**
 * @author:chencc
 * @class MeetingSummaryUpload
 * @extends Ext.Panel
 * @description [MeetingSummaryUpload]管理
 * @company 北京互融时代软件有限公司
 * @createtime
 *  、
 */
MeetingSummaryUpload = Ext.extend(Ext.Panel, {
	isDisabled : false,
	isHidden : false,
	// 构造函数
	constructor : function(_cfg) {
		if (typeof(_cfg.projectId) != "undefined") {
			this.projId = _cfg.projectId;
			this.businessType = typeof(_cfg.businessType) != "undefined"
					? _cfg.businessType
					: null;
		}
		if (typeof(_cfg.isHidden) != 'undefined') {
			this.isHidden = _cfg.isHidden;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MeetingSummaryUpload.superclass.constructor.call(this, {
					region : 'center',
					layout : 'anchor',
					items : [this.grid_MeetingSummaryUploadPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {

		this.render_MeetingSummaryUploadInfo = Ext.data.Record.create([{
					name : 'fileid'
				}, {
					name : 'filename'
				}, {
					name : 'filepath'
				}, {
					name : 'extendname'
				}, {
					name : 'filesize'
				}, {
					name : 'createtime'
				}, {
					name : 'contentType'
				}, {
					name : 'webPath'
				}, {
					name : 'projId'
				}]);
		this.jStore_MeetingSummaryUpload = new Ext.data.JsonStore({
					url : __ctxPath + '/creditFlow/fileUploads/ajaxGetFilesListFileForm.do',
					totalProperty : 'totalProperty',
					root : 'topics',
					fields : this.render_MeetingSummaryUploadInfo

				});
		this.jStore_MeetingSummaryUpload.load({
					scope : this,
					params : {
						mark : "sl_conference_record." + this.projId+"."+this.businessType,
						typeisfile : "typeismeeting"
					}
				});
		// 下载文件
		downFileMeeting = function(fileid) {
			window.open(__ctxPath + '/creditFlow/fileUploads/DownLoadFileForm.do?fileid='
							+ fileid, '_blank');

		};
		// 上传文件
		uploadsFileMeeting = function(mark, uploadsSize, projId, businessType,
				jStore) {
			var reloadjStore_MeetingSummaryUpload = function() {
				jStore.reload()
			};
			var mark = "sl_conference_record." + projId+"."+businessType;

			uploadReportJS('上传/下载会议纪要文件', 'typeismeeting', mark, uploadsSize,
					null, null, reloadjStore_MeetingSummaryUpload, projId,
					businessType, '会议纪要');

		};
		onlineRunNtkOfficePanel = function(projId, businessType, bln) {
			var olymark = "sl_conference_record." + projId+"."+businessType;
			Ext.Ajax.request({
						url : __ctxPath + '/creditFlow/fileUploads/getFileAttachFileForm.do',
						method : 'GET',
						success : function(response, request) {
							var objfile = Ext.util.JSON
									.decode(response.responseText);
							if (objfile.success == true) {
								var file_id = objfile.fileId;
								new OfficeTemplateView(file_id, null, bln, null);
							} else {
								Ext.ux.Toast.msg('提示', '可能还未上传调查报告！');
							}

						},
						failure : function(response) {
							Ext.ux.Toast.msg('提示', '操作失败，请重试');
						},
						params : {
							mark : olymark,
							typeisfile : "typeismeeting",
							projId : projId,
							businessType : businessType
						}
					});
		};

		this.uploadsTopBar = new Ext.Toolbar({
					items : [{
						iconCls : 'slupIcon',
						text : '上传-会议纪要',
						xtype : 'button',
						scope : this,

						handler : function() {
							// 多文件上传
							// uploadsFileMeeting("sl_conference_record."+this.projId,1,this.projId,this.businessType,this.jStore_MeetingSummaryUpload)
							// 单文件上传 未 完成
							this.uploadContractWin(this.projId,this.businessType)
						}
					}]
				});
		this.grid_MeetingSummaryUploadPanel = new HT.EditorGridPanel({
			border : false,
			hiddenCm : true,
			autoWidth : true,
			rowActions : true,
			store : this.jStore_MeetingSummaryUpload,
			autoExpandColumn : 'filename',
			autoScroll : true,
			layout : 'anchor',
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			isShowTbar : this.isHidden ? false : true,
			tbar : this.isHidden ? null : this.uploadsTopBar,
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,

			columns : [{
						header : '',
						dataIndex : 'fileid',
						hidden : true,
						scope : this
					}, {
						header : '文件名称(已上传)',
						width : 220,
						dataIndex : 'filename'
					}, {
						header : '大小',
						width : 47,
						dataIndex : 'filesize',
						renderer : this.transition
					}, {
						header : '类型',
						width : 47,
						dataIndex : 'extendname'
					}, {
						header : '上传时间',
						width : 76,
						dataIndex : 'createtime'
					}, {
						header : '下载',
						width : 47,
						dataIndex : 'fileid',
						scope : this,
						renderer : function(val, m, r) {
							return '<a title="下载会议纪要" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downFileMeeting(\''
									+ r.get('fileid') + '\')" >下载</a>';
						}
					}/*, {
						header : '在线编辑',
						width : 70,
						dataIndex : 'fileid',
						scope : this,
						hidden : this.isHidden,
						renderer : function(val, m, r) {
							return '<a title="在线编辑会议纪要" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="onlineRunNtkOfficePanel(\''
									+ this.projId
									+ '\',\''
									+ this.businessType
									+ '\',' + false + ')" >编辑</a>';

						}
					}*/, new Ext.ux.grid.RowActions({
								header : '重新上传',
								width : 76,
								hidden : this.isHidden,
								actions : [{

											// iconCls : 'slupIcon',
											text : '重新上传',
											qtip : '重新上传',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							}), new Ext.ux.grid.RowActions({
								header : '删除',
								width : 55,
								hidden : this.isHidden,
								actions : [{
											// iconCls : 'btn-del',
											text : '删除',
											qtip : '删除',
											style : 'margin:0 3px 0 3px'
										}],
								listeners : {
									scope : this,
									'action' : this.onRowAction
								}
							})]
		});

		this.grid_MeetingSummaryUploadPanel.addListener('rowdblclick',
				this.rowClick);

	},// end of the initComponents()

	transition : function(v) {
		return ((Math.floor(v / 1024)) > 1024) ? Math.floor((v / 1024 / 1024))
				+ 'M' : Math.floor(v / 1024) + 'K';
	},

	deleteFileMeeting : function(fileid, grid) {
		Ext.Msg.confirm("提示!", '确定要删除吗？', function(btn) {
					if (btn == "yes") {
						var pbar = Ext.MessageBox.wait('数据删除中', '请等待', {
									interval : 200,
									increment : 15
								});
						Ext.Ajax.request({
									url : __ctxPath
											+ '/creditFlow/fileUploads/DeleRsFileForm.do?fileid='
											+ fileid,
									method : 'POST',
									success : function() {
										Ext.ux.Toast.msg('状态', '删除成功!');
										grid.getStore().reload();
										pbar.getDialog().close();
									},
									failure : function(result, action) {
										Ext.ux.Toast.msg('状态', '删除失败!');
										grid.getStore().reload();
										pbar.getDialog().close();
									}
								});
					}
				});

	},
	reUploadMeeting : function(projId, businessType, fileid, grid) {
		var reloadJStoreMeeting = function() {
			grid.getStore().reload()
		};
		var olymark = "sl_conference_record." + projId+"."+businessType;
		reUploadReportJS('上传/下载会议纪要', "typeismeeting", olymark, 2, null, null,
				reloadJStoreMeeting, projId, businessType, fileid);
	},
	rowClick : function(grid, rowindex, e) {
		/*
		 * grid.getSelectionModel().each(function(rec) { new SlMortgageForm({
		 * mortId : rec.data.mortId }).show(); });
		 */
	},

	// 上传文件
	// this.uploadContractWin("sl_conference_record."+this.projId,1,this.projId,this.businessType,this.jStore_MeetingSummaryUpload)
	uploadContractWin : function(projId, businessType,fileidValue) {
		var cpanel = this.grid_MeetingSummaryUploadPanel; 
		var newUrl="";
		var fileid="";
		//判断是否是重新上传
		if(fileidValue){
			newUrl=__ctxPath+'/creditFlow/fileUploads/reUploadReportJSFileForm.do';
			fileid=fileidValue;
		}else{
			newUrl=__ctxPath + '/creditFlow/fileUploads/uploadReportJSFileForm.do';
		}
		new Ext.Window({
			id : 'uploadContractWin',
			title : '上传会议纪要',
			layout : 'fit',
			width : (screen.width - 180) * 0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true,
			bodyStyle : 'overflowX:hidden',
			buttonAlign : 'right',
			items : [new Ext.form.FormPanel({
						id : 'uploadContractFrom',
						url : newUrl,
						monitorValid : true,
						labelAlign : 'right',
						buttonAlign : 'center',
						enctype : 'multipart/form-data',
						fileUpload : true,
						layout : 'column',
						frame : true,
						items : [{
									columnWidth : 1,
									layout : 'form',
									labelWidth : 80,
									defaults : {
										anchor : '95%'
									},
									items : [{
												xtype : 'textfield',
												fieldLabel : '会议纪要文件',
												allowBlank : false,
												blankText : '文件不能为空',
												id : 'fileUpload',
												name : 'myUpload',
												inputType : 'file'
											}]
								}],
						buttons : [{
							text : '上传',
							iconCls : 'uploadIcon',
							formBind : true,
							handler : function() {
								var str = Ext.getCmp("fileUpload").getValue().split('\\').last();
								var size = cpanel.getStore().data.length;
								if(!fileidValue){
									if (size > 0) {
										fileid= cpanel.getStore().getAt(0).data.fileid;
										Ext.Ajax.request({
													url : __ctxPath+ '/creditFlow/fileUploads/DeleRsFileForm.do?fileid='+ fileid,
													method : 'POST'
												});
									}
								}
								
								Ext.getCmp('uploadContractFrom').getForm().submit({
											method : 'POST',
											waitTitle : '连接',
											waitMsg : '消息发送中...',
											params : {
												fileid : fileid,
												myUploadFileName : str,
												mark : "sl_conference_record."+ projId+"."+businessType,
												typeisfile : 'typeismeeting',
												projId : projId,
												businessType : businessType,
												setname : encodeURI('会议纪要',"utf-8")
											},
											success : function(form, action) {
												Ext.ux.Toast.msg('提示','上传成功！',
													Ext.getCmp('uploadContractWin').destroy(),
													function(btn,text) {
												});
												cpanel.getStore().reload();
											},
											failure : function(form, action) {
												Ext.ux.Toast.msg('提示', '上传失败！');
											}
										});
							}
						}]
					})]
		}).show();
	},
	// 行的Action
	onRowAction : function(grid, record, action, row, col) {
		switch (col) {
//			case 9 :
			case 8 :
				this.deleteFileMeeting.call(this, record.data.fileid, grid);
				break;
//			case 8 :
			case 7 :
				/*this.reUploadMeeting.call(this, this.projId, this.businessType,
						record.data.fileid, grid, '会议纪要');*/
			    this.uploadContractWin(this.projId, this.businessType,record.data.fileid);
				break;
			default :
				break;
		}
	}
})