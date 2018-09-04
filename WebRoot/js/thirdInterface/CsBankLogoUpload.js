/**
 * @author
 * @createtime
 * @class P2pFriendlinkForm
 * @extends Ext.Window
 * @description P2pFriendlink表单
 * @company 智维软件
 */
CsBankLogoUpload = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		 
		if (typeof(_cfg.talbeName) != "undefined") {
			this.tablename = _cfg.talbeName; 
		}
		
		if (typeof(_cfg.artileForm) != "undefined") {
			this.artileForm = _cfg.artileForm; 
		}
		 
		
//		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		CsBankLogoUpload.superclass.constructor.call(this, {
					id : 'CsBankLogoUpload',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 50,
					width : 500,   
					maximizable : true,
					title : '文件上传',
					buttonAlign : 'center',
					buttons : [{
								text : '上传',
								iconCls : 'btn-save',
								scope : this,
								handler : this.save
							}]
				});
	},// end of the constructor
	// 初始化组件
	initUIComponents : function() {
		 
		this.formPanel = new Ext.FormPanel({
					layout : 'form',
					bodyStyle : 'padding:10px',
					border : false,
					autoScroll : true,
					 id : 'P2pFriendlinkForm',
					defaults : {
						anchor : '96%,96%'
					},
					enctype : 'multipart/form-data',
					fileUpload: true,
					defaultType : 'textfield',
					items : [{
						xtype : 'textfield',
						fieldLabel : "上传",
						columnWidth : .8,
						allowBlank : false,
						blankText : '不能为空',
						id : 'fileUpload',
						name: 'myUpload',
						fileUpload: true ,
						frame: true,
	    				inputType: 'file'
					}]
				});
//		// 加载表单对应的数据
//		if (this.id != null && this.id != 'undefined') {
//			this.formPanel.loadData({
//						url : __ctxPath + '/p2p/getP2pFriendlink.do?id='
//								+ this.id,
//						root : 'data',
//						preName : 'p2pFriendlink'
//					});
//		}

	},// end of the initcomponents

	/**
	 * 保存记录
	 */
	save : function() {
		 
		 var targetForm =this.targetForm;
		  var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
			   var url = 'file://'+ Ext.get('fileUpload').dom.value;  
               var furl=Ext.get('fileUpload').dom.value;
               var extendname=furl.substring(furl.lastIndexOf("."),furl.length);//获取文件的后缀名
			   
			   if (!img_reg.test(url) && extendname!=".pdf") {  
             	 alert('您选择的文件格式不正确,请重新选择!');
                return false;
              };
                
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath
							+ '/p2p/upLoadFilesArticle.do',
					params:{extendname:extendname,tablename:this.tablename,mark:this.mark,webMaterialsId:this.webMaterialsId,projId:this.projId,businessType:this.businessType},
//				success:function(result,request){  
////                  var object=Ext.util.JSON.decode(result.responseText);
////                  alert(resultObject);
////				  return;  
////				 }
					callback : function(fp, action) {
						
						var object = Ext.util.JSON.decode(action.response.responseText)
						var path=object.webPath;
						 if(targetForm!=null){
						 	var  record =targetForm.getCmpByName("articleImg");
						 	record.setValue(path);
						 	
						 }
//						var object=Ext.util.JSON.decode(fp.responseText);
//						var flag =object.webPath;//flag用来判断是否本金款项是否已经对账：0表示没有对账，1表示已经对账
						 
						var gridPanel = Ext.getCmp('articleImg');
						if (gridPanel != null) {
							gridPanel.setValue();
						}
						this.close();
					}
				});
	}// end of save

});