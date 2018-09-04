/**
 * @author
 * @createtime
 * @class P2pAddBasisMaterialProduct
 * @extends Ext.Window
 * @description P2pAddBasisMaterialProduct表单
 * @company 互融软件
 */
 var  uploadPhotoProductMaterial= function(title,mark,displayProfilePhoto,personPId,winID) {
	var  winobj=Ext.getCmp(winID);
	var panelObj=winobj.get(0);
	var uploadWindow=new Ext.Window({
			title : title,
			layout : 'fit',
			width : (screen.width-180)*0.6,
			height : 130,
			closable : true,
			resizable : true,
			plain : false,
			bodyBorder : false,
			border : false,
			modal : true,
			constrainHeader : true ,
			bodyStyle:'overflowX:hidden',
			buttonAlign : 'right',
			items:[new Ext.form.FormPanel({
				url : getRootPath()+'/p2p/uploadPhotoP2pLoanBasisMaterial.do',
				monitorValid : true,
				labelAlign : 'right',
				buttonAlign : 'center',
				enctype : 'multipart/form-data',
			    permitted_extensions:['JPG','jpg','jpeg','JPEG'],  
				fileUpload: true, 
				layout : 'column',
				frame : true ,
				items : [{
					columnWidth : 1,
					layout : 'form',
					labelWidth : 140,
					defaults : {anchor : '95%'},
					items :[{
						xtype : 'textfield',
						fieldLabel : title,
						allowBlank : true,
						blankText : title+'不能为空',
						id : 'fileUpload',
						name: 'fileUpload',
	    				inputType: 'file'
					},{
						xtype : 'hidden',
						name: 'mark',
						value :mark
					}]
				}],
				scope:this,
				buttons : [{
					text : '上传',
					iconCls : 'uploadIcon',
					formBind : true,
					handler : function() {
					  var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/  
					  var url = 'file://'+ Ext.get('fileUpload').dom.value;  
                      var furl=Ext.get('fileUpload').dom.value;
					  var extendname=furl.substring(furl.lastIndexOf("."),furl.length);
					  if (!img_reg.test(url) && extendname!=".pdf") {  
                      	 alert('您选择的文件格式不正确,请重新选择!');
                         return false;
                      };
					
					  var pwindow=this.ownerCt.ownerCt.ownerCt;
					   var collection = this.ownerCt.ownerCt.ownerCt.items; 
					   var formPanel=collection.first();
					   formPanel.getForm().submit({
							method : 'POST',
							waitTitle : '连接',
							waitMsg : '消息发送中...',
							params:{extendname:extendname},
							success : function(form ,action) {
								Ext.ux.Toast.msg('提示',title+'上传成功！',
								pwindow.destroy(),
									function(btn, text) {
								});
								var fileid = action.result.fileid;
								var webPath = action.result.webPath;
								var display =panelObj.getCmpByName(displayProfilePhoto);
								if(extendname==".jpg" || extendname==".jpeg" || extendname==".JPG" || extendname==".JPEG"){
								    display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ __fileURL+"/"+webPath+'" ondblclick=showPic("'+webPath+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+__fileURL+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+fileid+',"'+webPath+'") width =140 height=80  /></div>',false);
								}
								panelObj.getCmpByName(personPId).setValue(fileid);
								
							},
							failure : function(form, action) {
								Ext.ux.Toast.msg('提示',title+'上传失败！');		
							}
						});
					}
				}]
			})]
		});
	    uploadWindow.show();
};
P2pAddBasisMaterialProduct = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	productId:null,
	operateObj:null,
	constructor : function(_cfg) {
		if(typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		if(typeof(_cfg.operateObj)!="undefined"){
			this.operateObj=_cfg.operateObj;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pAddBasisMaterialProduct.superclass.constructor.call(this, {
					id : 'P2pAddBasisMaterialProductssWin',
					layout : 'fit',
					items : this.outPanel,
					modal : true,
					autoScroll:true,
					maximizable : true,
					frame:true,
					height : 380,
			        width :500,
					title : '新增材料',
					buttonAlign : 'center',
					buttons :this.isHideBtns?null: [{
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
		this.outPanel = new Ext.form.FormPanel({
			modal : true,
			labelWidth : 70,
			frame:true,
			monitorValid : true,
			buttonAlign : 'center',
			layout : 'form',
		    items:[{
		                       name:'p2pLoanConditionOrMaterial.productId',
		                       xtype:'hidden',
		                       value:this.productId
		    },{
		                       name:'p2pLoanConditionOrMaterial.conditionType',
		                       xtype:'hidden',
		                       value:this.conditionType
		    },{
		                       name:'p2pLoanConditionOrMaterial.shilitu1Id',
		                       xtype:'hidden',
		                       value:this.productId
		    },{
		                       name:'p2pLoanConditionOrMaterial.shilitu2Id',
		                       xtype:'hidden',
		                       value:this.productId
		    },
		    	{
								fieldLabel : this.conditionType==2?'材料类型':'条件类型',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								readOnly : this.isAllReadOnly,
								allowBlank : false,
								triggerAction : "all",
								hiddenName:'p2pLoanConditionOrMaterial.conditionState',
								anchor : '100%',
								valueField : 'id',
							//	readOnly : this.isAllReadOnly,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["必备", "1"], ["可选", "2"]]
								})
						},{
								fieldLabel :this.conditionType==2?'材料名称':'条件名称' ,
								name : 'p2pLoanConditionOrMaterial.conditionContent',
								xtype : 'textfield',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : '100%',
								maxLength : 255
							},{
							fieldLabel : '备注',
							name : 'p2pLoanConditionOrMaterial.remark',
							xtype : 'textarea',
							maxLength : 500,
							height:80,
							readOnly : this.isAllReadOnly,
							anchor : "100%"
						},{
							columnWidth :1,
							layout : 'column',
							defaults : {
								anchor : '100%'
							},
							scope:this,
							items : [{
								columnWidth :0.2,
								layout : 'form',
								labelWidth : 35,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										scope:this,
										html :''
									}]
							},{
								columnWidth :0.4,
								layout : 'form',
								labelWidth : 35,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :10px',
										scope:this,
										hidden:this.conditionType==2?false:true,
										html :this.isAllReadOnly==true?'示例图1':'示例图1&nbsp;&nbsp;&nbsp;<a href="#"  onClick =uploadPhotoProductMaterial(\'示例图1\',\'p2p_loan_conditionormaterial\',\'pshilitu1\',\'p2pLoanConditionOrMaterial.shilitu1Id\',\'P2pAddBasisMaterialProductssWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleFtpPhotoFile(\'p2pLoanConditionOrMaterial.shilitu1Id\',\'pshilitu1\',\'P2pAddBasisMaterialProductssWin\')>删除</a>'
									},{
										name:'pshilitu1',
										xtype :'label',
										style :'padding-left :10px',
										hidden:this.conditionType==2?false:true,
										html :function(){
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
										}()
									}]
							},{
								columnWidth :0.4,
								layout : 'form',
								labelWidth : 35,
								defaults : {
									anchor : '100%'
								},
								items : [{
										xtype :'label',
										style :'padding-left :20px',
										hidden:this.conditionType==2?false:true,
										html : this.isAllReadOnly==true?'示例图2':'示例图2&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoProductMaterial(\'示例图2\',\'p2p_loan_conditionormaterial\',\'pshilitu2\',\'p2pLoanConditionOrMaterial.shilitu2Id\',\'P2pAddBasisMaterialProductssWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleFtpPhotoFile(\'p2pLoanConditionOrMaterial.shilitu2Id\',\'pshilitu2\',\'P2pAddBasisMaterialProductssWin\')>删除</a>'
									},{
										name:"pshilitu2",
										xtype :'label',
										hidden:this.conditionType==2?false:true,
										html : function(){	
												return '<div style="width:144px; height:84px; margin:10px 0px 0px 20px; padding:1px 1px 1px 1px;"><img src="'
											+ getRootPath()
											+ '/images/nopic.jpg" width =140 height=80/></div>'
										}()
										
									}]
							}]
						}]
		});
					// 加载表单对应的数据
		if (this.conditionId != null && this.conditionId != 'undefined') {
			var conditionId=this.conditionId;
		    var conditionType =this.conditionType;
			this.outPanel.loadData({
				url : __ctxPath+ '/p2p/getP2pLoanConditionOrMaterial.do?conditionId='+ this.conditionId,
				root : 'data',
				preName : 'p2pLoanConditionOrMaterial',
				success : function(response, options) {
					var respText = response.responseText;
					var obj = Ext.util.JSON.decode(respText);
					debugger
					if(conditionType==2){
						debugger
				    if(null!=obj.data.shilitu1ExtendName){
			                   	var display =this.getCmpByName('pshilitu1');
								if(obj.data.shilitu1ExtendName==".jpg" || obj.data.shilitu1ExtendName==".jpeg"){
								    display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ __fileURL+"/"+obj.data.shilitu1URL+'" ondblclick=showPic("'+obj.data.shilitu1URL+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+__fileURL+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+obj.data.shilitu1Id+',"'+obj.data.shilitu1URL+'") width =140 height=80  /></div>',false);
								}}
					if(null!=obj.data.shilitu2ExtendName){
								var display1 =this.getCmpByName('pshilitu2');
								if(obj.data.shilitu2ExtendName==".jpg" || obj.data.shilitu2ExtendName==".jpeg"){
								    display1.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ __fileURL+"/"+obj.data.shilitu2URL+'" ondblclick=showPic("'+obj.data.shilitu2URL+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display1.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+__fileURL+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+obj.data.shilitu2Id+',"'+obj.data.shilitu2URL+'") width =140 height=80  /></div>',false);
								}
								}
				}
				}
			});
		}
		

	},// end of the initcomponents

	/**
	 * 重置
	 * 
	 * @param {}
	 *            formPanel
	 */
	reset : function() {
		this.outPanel.getForm().reset();
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
		if(this.outPanel.getForm().isValid()){
			this.outPanel.getForm().submit({
				    clientValidation: false, 
					url : __ctxPath+ '/p2p/saveP2pLoanConditionOrMaterial.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var gridPanel = this.operateObj;
						if (gridPanel != null) {
							Ext.ux.Toast.msg("操作信息","保存成功");
							gridPanel.getStore().reload();
						}
						this.close();
					}
			});
		}
	}
});