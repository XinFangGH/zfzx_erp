/**
 * @author
 * @createtime
 * @class ArticleForm
 * @extends Ext.Window
 * @description Article表单
 * @company 智维软件
 */
var ccc;
ArticleForm = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		 ccc = this.name;
		// 必须先初始化组件
		this.initUIComponents();
		ArticleForm.superclass.constructor.call(this, {
					id : 'ArticleFormWin',
					layout : 'fit',
					items : this.formPanel,
					modal : true,
					height : 600,
					width : 700,
					maximizable : true,
					title : '文章详细信息',
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
								boxLabel : '是',
								inputValue : "1",
								name : "isPublication",
								id : "isPublicationY",
								checked : true
							}, {
								boxLabel : '否',
								name : "isPublication",
								id : "isPublicationN",
								inputValue : "0"

							}]
				});
		var isRecommendRadioGroup = new Ext.form.RadioGroup({
					fieldLabel : "radioGroup",
					disabled : false,
					items : [{
								boxLabel : '是',
								inputValue : "1",
								name : "isRecommend",
								id : "isRecommendY"

							}, {
								boxLabel : '否',
								name : "isRecommend",
								id : "isRecommendN",
								inputValue : "0",
								checked : true
							}]
				});
		var isTopRadioGroup = new Ext.form.RadioGroup({
					fieldLabel : "radioGroup",
					disabled : false,
					items : [{
								boxLabel : '是',
								inputValue : "1",
								name : "isTop",
								id : "isTopY"

							}, {
								boxLabel : '否',
								name : "isTop",
								id : "isTopN",
								inputValue : "0",
								checked : true

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
			items : [{
						name : 'article.id',
						xtype : 'hidden',
						value : this.id == null ? '' : this.id
					},{
								xtype : "combo",
								id:'parentId',
								anchor : "96%",
								hiddenName : "parentId",
								displayField : 'itemName',
								valueField : 'itemId',
								triggerAction : 'all',
								disable : true,
								store : new Ext.data.SimpleStore({
									autoLoad : true,
									url : __ctxPath
											+ '/p2p/getListArticlecategory.do?type=1',
									fields : ['itemId', 'itemName','itemKey']
								}),
								fieldLabel : "上级类别",
								blankText : "上级类别不能为空，请正确填写!",
								listeners : {
									scope:this,
									afterrender : function(combox) {
										var st = combox.getStore();
										st.on("load", function() {
											for (var i = 0; i< st.data.length;i++){
                                                var record =   st.getAt(i)
                                                var v = record.data.itemId;
                                                if(record.data.itemName == ccc ){
                                                    combox.setValue(v);
                                                    combox.fireEvent("select", combox, record, 0);
                                                    return;
												}
											}
												})
										combox.clearInvalid();
									}
								}
							}, {
						fieldLabel : '文章标题',
						name : 'article.title',
						id:'title',
						allowBlank : false,
						maxLength : 255
					}, {
						fieldLabel : '栏目简写(英文字母)',
						name : 'article.col',
						id:'col',
						allowBlank : this.single==0?false:true,
						maxLength : 255
					}, {
						fieldLabel : '文章作者',
						name : 'article.author',
						id:'author',
						maxLength : 255
					}, {
						fieldLabel : '是否发布',
						xtype : 'radiogroup',
						anchor : '100%',
						layout : "column",
						items : [isPublicationRadioGroup]
					}, {
						fieldLabel : '是否推荐',
						xtype : 'radiogroup',
						anchor : '100%',
						layout : "column",
						items : [isRecommendRadioGroup]
					}, {
						fieldLabel : '是否置顶',
						xtype : 'radiogroup',
						anchor : '100%',
						layout : "column",
						items : [isTopRadioGroup]
					}, {
						xtype : 'button',
						text:'上传图片',
						fieldLabel : '上传图片',
						value:'上传图片',
						width:.5,
						scope : this,
						handler : this.upload
					}, {
						fieldLabel : '文章图片',
						 
						name:'articleImg',
						maxLength : 255
					},  {
						fieldLabel : '文章内容',
						name : 'article.content',
						id:'content',
						allowBlank : false,
						xtype : 'fckeditor',
						maxLength : 6553500
					}, {
						fieldLabel : '页面关键词',
						name : 'article.metaKeywords',
						id:'metaKeywords',
						xtype : 'textarea',
						maxLength : 65535
					}, {
						fieldLabel : '页面描述',
						name : 'article.metaDescription',
						id:'metaDescription',
						xtype : 'textarea',
						maxLength : 65535
					}]
		});
		// 加载表单对应的数据
		if (this.id != null && this.id != 'undefined') {
			this.formPanel.getForm().load({ 
				deferredRender : false,
						url : __ctxPath + '/p2p/getArticle.do?id=' + this.id,
						//root : 'data',
						//preName : 'article',
						waitMsg : '正在载入数据...',
						success : function(form, action) { 
							
							var  isPublication = action.result.data.isPublication;
							var isTop = action.result.data.isTop;
							var isRecommend = action.result.data.isRecommend;
						/*	var id = action.result.data.articlecategory.name;
							Ext.getCmp('parentId').setValue(id) ;	*/
							//是否置顶
			 				if(isTop == 1){
								Ext.getCmp('isTopY').setValue(true) ;	
							}else if(isTop == 0){
								Ext.getCmp('isTopN').setValue(true) ;	
							}
							
							//是否发布
							if(isPublication == 1){
								Ext.getCmp('isPublicationY').setValue(true) ;	
							}else if(isPublication == 0){
								Ext.getCmp('isPublicationN').setValue(true) ;	
							}
						
							//是否推荐
							if(isRecommend == 1){  
								Ext.getCmp('isRecommendY').setValue(true) ;	
							}else if(isRecommend == 0){
								Ext.getCmp('isRecommendN').setValue(true) ;	
							}
						},
						failure : function(form, action) {
							Ext.ux.Toast.msg('编辑', '载入失败');
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
		var isPublication = this.formPanel.getForm().getValues()["isPublication"];
		var parentId = this.formPanel.getForm().getValues()["parentId"];
		
		var isRecommend = this.formPanel.getForm().getValues()["isRecommend"];
		var isTop = this.formPanel.getForm().getValues()["isTop"];
		 
		var  themeUrl = this.formPanel.getCmpByName('articleImg').getValue();
		
		
		
	 
		$postForm({
					formPanel : this.formPanel,
					scope : this,
					url : __ctxPath + '/p2p/saveArticle.do',
					params : {
						parentId:parentId,
						isPublication : isPublication,
						isRecommend : isRecommend,
						isTop : isTop,
						themeUrl:themeUrl
					},
					callback : function(fp, action) {
						var gridPanel = Ext.getCmp('ArticleGrid_'+this.type);
						if (gridPanel != null) {
							gridPanel.getStore().reload();
						}
						this.close();
					}
				});
	}// end of save,
	,upload : function() {
		 
	      new ArticleUpLoadFile({
				 talbeName: "p2p_article",
				 targetForm:this.formPanel
				 
			}).show();}// end of save

});
