Ext.ns('fundVoucher');
fundVoucher.jStore;
GlFundVoucher = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		GlFundVoucher.superclass.constructor.call(this, {
					id : 'GlFundVoucherId',
					region : 'center',
					layout : 'anchor',
					items : [this.fundVoucher_gridPanel]
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		 RunNtkOfficePanel = function(contract_Id){/*
				Ext.Ajax.request({
					url : __ctxPath+'/credit/document/getFileAttachContract.do',
					method : 'GET',
					success : function(response, request) {
						var objfile = Ext.util.JSON.decode(response.responseText);
						if(objfile.success == true){
							var file_id =objfile.fileId;
							new OfficeTemplateView(file_id,null,false,null);
						}else{
							Ext.ux.Toast.msg('状态', '合同未生成或上传成功，请先生成或上传合同！');
						}
						
					},
					failure : function(response) {
						Ext.ux.Toast.msg('状态', '合同未上传，请先上传合同');
					},
					params : {
						contractId :contract_Id
					}
				})
			
		*/};
		seeFile = function(fileid,extendname,src){
			
			if(extendname==".doc" || extendname==".docx" || extendname==".xls" || extendname==".xlsx"){
				Ext.Ajax.request({
					url : __ctxPath+'/creditFlow/fileUploads/getfileAttachBycsfileidFileForm.do',
					method : 'POST',
					success : function(response, request) {
						var fileAttach = Ext.util.JSON.decode(response.responseText);
						new OfficeTemplateView(fileAttach.fileId,null,false,null);
					},
					failure : function(result, action) {
						Ext.Msg.alert('状态','预览失败!');
					},
					params : {
						fileid : fileid
					}
				});
				
			}else if(extendname==".jpg"||extendname==".gif"||extendname==".bmp"||extendname==".png"){
				var pic_panel;
				var pic_win;
				
				//下载图片
				downloadPic = function(fileid){
					window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
				};
				
				pic_panel = new Ext.Panel({
					
					//id:'id_pic_panel',
			        frame:true,
			        autoHeight : true,
			        autoWidth: true,
			        layout:'fit',
			        items:[{
			        	html: '<div align="center"><img src="'+src+'" ondblclick="downloadPic('+fileid+'); " alt ="双击下载此文件"/></div>'
			        }]
					
				});
				
				pic_win = new Ext.Window({
					//id : 'id_pic_win',
					title: '文件预览',
					autoScroll : true,
					layout : 'fit',
					width : 550,
					height :405,
		//			animCollapse : true,
					//animateTarget : win.getEl(),
		//			closeAction : 'hide',
					maximizable : true,
					closable : true,
					resizable : true,
					plain : true,
					border : false,
					modal : true,
					buttonAlign: 'center',
					bodyStyle : 'padding: 0',
					items : [pic_panel]
				});
				
				pic_win.show();
			
			}else{
				window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+fileid,'_blank');
			}
				
				
		};
			
		this.fundVoucher_Record = Ext.data.Record.create([{name : 'fileid'},{name : 'filename'},{name : 'remark'},{name : 'businessType'}, {name : 'mark'},{name : 'filepath'}, {name : 'isArchiveConfirm'}, {name : 'archiveConfirmRemark'}, {name : 'createtime'}, {name : 'contentType'}, {name:'setname'},{name : 'webPath'},{name : 'projId'},{name : 'extendname'}]);
		fundVoucher.jStore = new Ext.data.JsonStore({
			url : __ctxPath+ '/creditFlow/fileUploads/getArchiveListByBusinessTypeFileForm.do',
			totalProperty : 'totalProperty',
            root : 'topics',
            fields : this.fundVoucher_Record
            
        });
		fundVoucher.jStore.load({
			scope : this,
			params : {
				projId : this.projectId,
				businessType : this.businessType,
				//"from FileForm AS f where f.businessType=? and f.projId=? and f.mark in("+f.mark+")";
				remark : "'sl_fund_intent_GuaranteeToCharge."+this.projectId+"','sl_fund_intent_customGuarantMoney."+this.projectId+"','sl_fund_intent_backCustomGuarantMoney."+this.projectId+"','gl_Bank_guaranteemoney."+this.projectId+"','back_gl_bank_guaranteemoney."+this.projectId+"'"
			}
		});
		this.fundVoucher_gridPanel = new HT.EditorGridPanel({
			id : 'fundVoucher_gridPanel_id',
			autoWidth : true,
			store : fundVoucher.jStore,
			autoExpandColumn : 'filename',
			autoScroll : true,
			hiddenCm:true,
			layout : 'anchor',
			isShowTbar : false,
			clicksToEdit : 1,
			viewConfig : {
				forceFit : true
			},
			bbar : false,
			showPaging : false,
			stripeRows : true,
			plain : true,
			loadMask : true,
			autoHeight : true,
						
			columns : [{
				header : '',
				dataIndex : 'fileid',
				hidden :true,
				scope : this
			},{
				header : '类型',
				dataIndex : 'setname'
			},{
				header : '文件名称',
				dataIndex : 'filename'
			}, {
				header : '大小',
				width : 60,
				dataIndex : 'filesize',
				renderer : this.transition
				/*renderer : function(v) {
					switch (v) {
						case 0 :
						return '--';
						break;
					}
			   }*/
			}, {
				header : '扩展名',
				width : 50,
				dataIndex : 'extendname'
			}, {
				header : '上传时间',
				width : 76,
				dataIndex : 'createtime',
				renderer : function(v){
					v=v.trim();
					return v;
				}
			}, {
				header : '下载打印',
				dataIndex : 'fileid',
				scope : this,
				width : 66,
				renderer : function(val, m, r) {
					return '<a title="下载附件" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="downLoadFile(\''+val+'\')" ><font color=blue>下载</font></a>';
				}
			},{
				header : '在线预览',
				width : 66,
				dataIndex : 'fileid',
				renderer : function(val, m, r) {
					if(r.get('mark')!=''){
					    return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="seeFile('+r.get('fileid')+',\''+r.get('extendname')+'\',\''+r.get('webPath')+'\')" >查看</a>';
					}else{
						return '<a title="在线查看" style ="TEXT-DECORATION:underline;cursor:pointer" onclick="RunNtkOfficePanel('+r.get('fileid')+')" >查看</a>';
					}
					
				}
			}],
			listeners : {
			}
		});
	},// end of the initComponents()
	isArchiveConfirm : function(v){
		return v == true?'<font color=green>是</font>' : '<font color=red>否</font>';
	},
	savereload:function(){
		this.fundVoucher_gridPanel.getStore().reload();
	},
	transition : function(v){
		var size = "";
		if(v==0||v==''||v==null){
			size = '--';
		}else{
			if(Math.floor(v/1024)>1024){
				size = Math.floor((v/1024/1024))+'M';
			}else if(Math.floor(v)<1024){
				size = Math.floor(v)+'B';
			}else if(Math.floor(v/1024)<1024){
				size = Math.floor(v/1024)+'K'
			}else{
				size = '未知';
			}
		}
		return size;
	}
});

downLoadFile = function(v){
	window.open(__ctxPath+'/creditFlow/fileUploads/DownLoadFileForm.do?fileid='+v);
		//uploadfile('下载附件',v,0,null,null,null);
};