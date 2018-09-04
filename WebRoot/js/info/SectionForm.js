/**
 * @author
 * @createtime
 * @class SectionForm
 * @extends Ext.Window
 * @description Section表单
 * @company 智维软件
 */
SectionForm = Ext.extend(Ext.Window, {
			// 构造函数
			constructor : function(_cfg) {
				Ext.applyIf(this, _cfg);
				// 必须先初始化组件
				this.initUIComponents();
				SectionForm.superclass.constructor.call(this, {
							id : 'SectionFormWin',
							layout : 'fit',
							items : this.formPanel,
							modal : true,
							height : 250,
							width : 500,
							maximizable : true,
							title : '栏目详细信息',
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
				this.formPanel = new Ext.FormPanel({
							layout : 'form',
							bodyStyle : 'padding:10px',
							border : false,
							autoScroll : true,
							// id : 'SectionForm',
							defaults : {
								anchor : '96%,96%'
							},
							defaultType : 'textfield',
							items : [{
								name : 'section.sectionId',
								xtype : 'hidden',
								value : this.sectionId == null
										? ''
										: this.sectionId
							}, {
								fieldLabel : '栏目名称',
								name : 'section.sectionName',
								allowBlank : false,
								maxLength : 256
							}, {
								fieldLabel : '栏目描述',
								name : 'section.sectionDesc',
								xtype : 'textarea',
								maxLength : 1024
							}, {
								fieldLabel : '栏目类型',
								hiddenName : 'section.sectionType',
								allowBlank : false,
								xtype : 'combo',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['1', '一般栏目'], ['2', '桌面新闻'],
										['3', '滚动公告']],
								value : 1
							}, {
								fieldLabel : '状态',
								hiddenName : 'section.status',
								allowBlank : false,
								xtype : 'combo',
								editable : false,
								mode : 'local',
								triggerAction : 'all',
								store : [['0','禁用'],['1','激活']],
								value : 1
							}]
						});
				// 加载表单对应的数据
				if (this.sectionId != null && this.sectionId != 'undefined') {
					this.formPanel.loadData({
								url : __ctxPath
										+ '/info/getSection.do?sectionId='
										+ this.sectionId,
								root : 'data',
								preName : 'section'
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
				$postForm({
							formPanel : this.formPanel,
							scope : this,
							url : __ctxPath + '/info/saveSection.do',
							callback : function(fp, action) {
								var gridPanel = Ext.getCmp('SectionGrid');
								if (gridPanel != null) {
									gridPanel.getStore().reload();
								}
								var sectionView = Ext.getCmp('SectionView');
								var section = action.result.data;
								if(sectionView !=null && section !=null){
									var firstColumn = sectionView.getCmpByName('FirstColumn');
									firstColumn.add(new Portlet({
										title : section.sectionName,
										sectionId : section.sectionId,
										sectionType : section.sectionType
									}));
									sectionView.doLayout();
								}
								this.close();
							}
						});
			}// end of save

		});