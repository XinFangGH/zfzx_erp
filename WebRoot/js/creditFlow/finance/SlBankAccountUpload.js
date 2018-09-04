/**
 * @author
 * @class SlFundQlideView
 * @extends Ext.Panel
 * @description [SlFundQlide]管理
 * @company 智维软件
 * @createtime:
 */
 function basepath(){
	var strFullPath=window.document.location.href;
	var strPath=window.document.location.pathname;
	var pos=strFullPath.indexOf(strPath);
	var prePath=strFullPath.substring(0,pos);
	var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
	return prePath+postPath+"/";
}
SlBankAccountUpload = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 初始化组件
				this.initUIComponents();
				// 调用父类构造
				SlBankAccountUpload.superclass.constructor.call(this, {
							id : 'SlBankAccountUpload',
							title : '上传银行账户',
							border : false,
							layout : 'fit',
							modal : true,
							layout : 'border',
							items : this.formPanel,
							height : 190,
							width : 377,
							listeners : {
								   close : function() {
									this.gridPanel.getStore().reload();
								}
							}
						
								
						});
			},// end of constructor
			// 初始化组件
			initUIComponents : function() {
				// 初始化搜索条件Panel
			this.formPanel = new Ext.FormPanel({
				            layout : 'form',
							bodyStyle : 'padding:10px',
							region:'center',
							//url : '/testupload.jsp',
							//html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="http://localhost:8080/zhiwei/testupload.jsp"> </iframe>'
							html:'<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+basepath()+'testupload.jsp?flag=bankaccountupload"> </iframe>'
							//html:__ctxPath+ '/testupload.jsp'
							//fileUpload:true,
//							 autoLoad:{
//				            url:__ctxPath +'/testupload.jsp',
//							scripts : true
//							},
//							url:__ctxPath + '/finance/getExcelSlFundQlide.do',
							//html:'<input id="excelsql" type="file" value="12131" id="excelsql" name="excelsql">'
//							border : false,
//							 items:[{
//		                    title: '资金流水',
//		                    autoLoad:{
//							url:__ctxPath +'/testupload.jsp',
//							scripts : true
//							} , 
//							
////                   
//                }]
				
			})
				
		
			}	
	
			
			
				

			
});
