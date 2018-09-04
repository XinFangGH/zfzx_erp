/**
 * @author
 * @createtime
 * @class BpProductParameterForm
 * @extends Ext.Window
 * @description BpProductParameter表单
 * @company 智维软件
 */
 var  uploadPhotoBasisMaterial= function(title,mark,displayProfilePhoto,personPId,winID) {
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

P2pAddBasisMaterial = Ext.extend(Ext.Window, {
	// 构造函数
	isAllReadOnly:false,
	productId:null,
	constructor : function(_cfg) {
		if(typeof(_cfg.isAllReadOnly)!="undefined"){
			this.isAllReadOnly=_cfg.isAllReadOnly;
		}
		if(typeof(_cfg.productId)!="undefined"){
			this.productId=_cfg.productId;
		}
		Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pAddBasisMaterial.superclass.constructor.call(this, {
					id : 'P2pAddBasisMaterialWin',
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
		                       name:'p2pLoanBasisMaterial.shilitu1Id',
		                       xtype:'hidden'
		    },{
		                       name:'p2pLoanBasisMaterial.materialId',
		                       xtype:'hidden',
		                       value:this.materialId
		    },{
		                       name:'p2pLoanBasisMaterial.shilitu2Id',
		                       xtype:'hidden'
		    },{
		                       name:'p2pLoanBasisMaterial.operationType',
		                       xtype:'hidden',
		                       value:'person'
		    },{
		    	fieldLabel : '客户类型',
				xtype : 'textfield',
				name:'customerType',
				anchor : '100%',
				allowBlank : false,
				readOnly : true,
				editable : false,
				value:'个人'
		    }/*{
								fieldLabel : '客户类型',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								triggerAction : "all",
								hiddenName:'p2pLoanBasisMaterial.operationType',
								anchor : '100%',
								valueField : 'id',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								editable : false,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["企业", "enterprise"], ["个人", "person"]]
								})
						}*/,{
								fieldLabel : '材料状态',
								xtype : 'combo',
								mode : 'local',
								displayField : 'name',
								allowBlank : false,
								triggerAction : "all",
								hiddenName:'p2pLoanBasisMaterial.materialState',
								anchor : '100%',
								valueField : 'id',
								editable : false,
								readOnly : this.isAllReadOnly,
								store : new Ext.data.SimpleStore({
									fields : ["name", "id"],
									data : [["必备", "1"], ["可选", "2"]]
								})
						},{
								fieldLabel : '贷款材料',
								name : 'p2pLoanBasisMaterial.materialName',
								xtype : 'textfield',
								allowBlank : false,
								readOnly : this.isAllReadOnly,
								anchor : '100%',
								maxLength : 255
							},{
							fieldLabel : '备注',
							name : 'p2pLoanBasisMaterial.remark',
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
										html :this.isAllReadOnly==true?'示例图1':'示例图1&nbsp;&nbsp;&nbsp;<a href="#"  onClick =uploadPhotoBasisMaterial(\'示例图1\',\'p2p_loan_basismaterial\',\'shilitu1\',\'p2pLoanBasisMaterial.shilitu1Id\',\'P2pAddBasisMaterialWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleFtpPhotoFile(\'p2pLoanBasisMaterial.shilitu1Id\',\'shilitu1\',\'P2pAddBasisMaterialWin\')>删除</a>'
									},{
										name:'shilitu1',
										xtype :'label',
										style :'padding-left :10px',
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
										html : this.isAllReadOnly==true?'示例图2':'示例图2&nbsp;&nbsp;&nbsp;<a href="#" onClick =uploadPhotoBasisMaterial(\'示例图2\',\'p2p_loan_basismaterial\',\'shilitu2\',\'p2pLoanBasisMaterial.shilitu2Id\',\'P2pAddBasisMaterialWin\')>上传</a>&nbsp;&nbsp;&nbsp;<a href="#" onClick =deleFtpPhotoFile(\'p2pLoanBasisMaterial.shilitu2Id\',\'shilitu2\',\'P2pAddBasisMaterialWin\')>删除</a>'
									},{
										name:"shilitu2",
										xtype :'label',
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
		if (this.materialId != null && this.materialId != 'undefined') {
			var materialId=this.materialId;
			this.outPanel.loadData({
				url : __ctxPath+ '/p2p/getP2pLoanBasisMaterial.do?materialId='+ this.materialId,
				root : 'data',
				preName : 'p2pLoanBasisMaterial',
				success : function(response, options) {
					var respText = response.responseText;
					var obj = Ext.util.JSON.decode(respText);
				    if(null!=obj.data.shilitu1ExtendName){
			                   	var display =this.getCmpByName('shilitu1');
								if(obj.data.shilitu1ExtendName==".jpg" || obj.data.shilitu1ExtendName==".jpeg"){
								    display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ __fileURL+"/"+obj.data.shilitu1URL+'" ondblclick=showPic("'+obj.data.shilitu1URL+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+__fileURL+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+obj.data.shilitu1Id+',"'+obj.data.shilitu1URL+'") width =140 height=80  /></div>',false);
								}}
					if(null!=obj.data.shilitu2ExtendName){
								var display1 =this.getCmpByName('shilitu2');
								if(obj.data.shilitu2ExtendName==".jpg" || obj.data.shilitu2ExtendName==".jpeg"){
								    display1.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+ __fileURL+"/"+obj.data.shilitu2URL+'" ondblclick=showPic("'+obj.data.shilitu2URL+'") width =140 height=80  /></div>',false);
								}else if(extendname==".pdf"){
									display1.setText('<div style="width:144px; height:84px; margin:10px 0px 0px 10px; padding:1px 1px 1px 1px;"><img src="'+__fileURL+'//images//desktop//pdf_win.jpg" ondblclick=showPdf('+obj.data.shilitu2Id+',"'+obj.data.shilitu2URL+'") width =140 height=80  /></div>',false);
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
					url : __ctxPath+ '/p2p/saveP2pLoanBasisMaterial.do',
					method : 'post',
					waitMsg : '数据正在提交，请稍后...',
					scope: this,
					success : function(fp, action) {
						var gridPanel = Ext.getCmp('P2pLoanBasisMaterialViewGrid');
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