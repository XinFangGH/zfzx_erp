/**
 * @author csx
 * @createtime 2010年1月10日
 * @class ArchiveTypeTempView
 * @extends Ext.Panel
 * @description 公文分类及模板管理
 * @company 智维软件
 */
ArchTemplateSelector = Ext.extend(Ext.Window, {
	/**
	 * 当前选择的分类ID
	 * @type 
	 */
	curTypeId:null,
	/**
	 * 构造函数
	 * @param {} _cfg
	 */
	constructor:function(_cfg){
		Ext.applyIf(this, _cfg);
		
		this.initUIComponents();
		
		//拷贝父类
		ArchiveTypeTempView.superclass.constructor.call(this,{
			title:'公文模板选择',
			iconCls : 'menu-archive-template',
			layout:'border',
			maximizable : true,
			width:800,
			height:500,
			modal : true,
			closeAction:'hide',
			scope:this,
			items:[
				this.leftTypePanel,
				this.archTemplateView
			],
			buttonAlign:'center',
			buttons : [{
						text : '选择模板',
						iconCls : 'menu-archive-template',
						scope : this,
						handler : function() {
							if (this.callback != null) {
								// 返回模板的名称，路径
								var selectRecords = this.archTemplateView.gridPanel
										.getSelectionModel().getSelections();
								if (selectRecords.length == 0) {
									Ext.ux.Toast.msg("信息", "请选择模板！");
									return;
								}
								this.callback.call(this,selectRecords[0].data.fileId);
								this.close();
							}
						}
					}, {
						text : '取消',
						iconCls:'btn-cancel',
						scope:this,
						handler : function() {
							this.close();
						}
					}
			]
		});//end of the ArchiveTypeTempView.superclass.constructor.call
	},// end of the constructor
	
	initUIComponents : function() {
				this.archTemplateView = new ArchTemplateView({
							allowEdit : false,
							singleSelect:true
						});
				var archTemplateView = this.archTemplateView;
				this.leftTypePanel = new zhiwei.ux.TreePanelEditor({
							region : 'west',
							title : '公文分类',
							collapsible : true,
							split : true,
							width : 200,
							url : __ctxPath+'/system/flowTreeGlobalType.do?catKey=ARC_TEM_TYPE',
							scope : this,
							onclick : function(node) {
								var typeId = node.id;
								if (node.id == 0) {
									archTemplateView.setTitle('所有模板');
									typeId = null;
								} else {
									archTemplateView.setTitle('[' + node.text
											+ ']模板列表');
								}
								archTemplateView.setTypeId(node.id);
								var store = archTemplateView.gridPanel
										.getStore();

								store.url = __ctxPath
										+ '/archive/listArchTemplate.do';
								store.baseParams = {
									'Q_archivesType.proTypeId_L_EQ' : typeId
								};
								store.params = {
									start : 0,
									limit : 25
								};
								store.reload({
											params : {
												start : 0,
												limit : 25
											}
										});
							}

						});
			}
			
});
