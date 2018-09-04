/**
 * @author
 * @createtime
 * @class ArticleForm
 * @extends Ext.Window
 * @description Article表单
 * @company 智维软件
 */
SystemUploadWin = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		SystemUploadWin.superclass.constructor.call(this, {
					id : 'SystemUploadWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 140,
					width : 500,
					maximizable : true,
					title : '文件上传',
					buttonAlign : 'center',
					buttons : [{
								text : '保存',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}, {
								text : '重置',
								iconCls : 'btn-reset',
								scope : this,
								handler : this.reset
							}, {
								text : '取消',
								iconCls : 'btn-cancel',
								scope : this,
								handler : this.cancel
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		var isPublicationRadioGroup = new Ext.form.RadioGroup({
					fieldLabel : "radioGroup",
					disabled : false,
					items : [{
								boxLabel : 'p2p',
								inputValue : "1",
								name : "isPublication",
								id : "isPublicationY",
								checked : true
							}, {
								boxLabel : 'erp',
								name : "isPublication",
								id : "isPublicationN",
								inputValue : "0"

							}]
				});
 
	 
		this.formPanel = new Ext.FormPanel({
			layout : 'form',
			bodyStyle : 'padding:10px',
			border : false,
			autoScroll : true,
			// id : 'ArticleForm',
			defaults : {
				anchor : '96%,96%'
			},
			defaultType : 'textfield',
				enctype : 'multipart/form-data',
					fileUpload: true,
			items : [{
						name : 'article.id',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					},  {
						fieldLabel : '选择服务器',
						xtype : 'radiogroup',
						anchor : '100%',
						layout : "column",
						items : [isPublicationRadioGroup]
					},  {
						xtype : 'textfield',
						text:'选择文件',
						fieldLabel : '选择文件',
						id : 'SystemUploadWinfileUpload',
						name: 'SystemUploadWinfileUpload',
						width:.5,
						scope : this,
						fileUpload: true ,
						frame: true,
	    				inputType: 'file'
					}]
		});
	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.formPanel.getForm().reset();
	},
	/**
	 * 取消
	 * 
	 * @param {}
	 *            window
	 */
	cancel : function() {
		this.close();
	},
	/**
	 * 保存记录
	 */
	save : function() {
		   var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
		   var url = 'file://'+ Ext.get('SystemUploadWinfileUpload').dom.value;  
	       var furl=Ext.get('SystemUploadWinfileUpload').dom.value;
	       var extendname=furl.substring(furl.lastIndexOf(".")+1,furl.length);//获取文件的后缀名
	     
		   var isPublication = this.formPanel.getForm().getValues()["isPublication"];
		   if(extendname=="rar"){
	             $postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/p2p/upLoadFilesSystemServiceUpdate.do',
					params:{
						isPublication:isPublication,
						extendname:extendname,
						filename:furl
					},
					callback : function(fp, action) {
						this.close();
				}
		   });
		   }else  {
		       Ext.ux.Toast.msg('状态', '请选择压缩文件，如rar');
			   return;
		   }
	}// end of save,
});
