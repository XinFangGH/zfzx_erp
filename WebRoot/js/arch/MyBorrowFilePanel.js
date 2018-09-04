/**
 * @author:
 * @class MyBorrowFilePanel
 * @extends Ext.Panel
 * @description [RollFile]管理
 * @company 北京互融时代软件有限公司
 * @createtime:
 */
MyBorrowFilePanel = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		MyBorrowFilePanel.superclass.constructor.call(this, {
					buttonAlign : 'center',
					region : 'center',
					layout : 'card',
					activeItem : 0,
					border : false,
					defaults : {
						anchor : '98%,98%'
					},
					items : [this.fileTypePanel, this.fileListPanel],
					listeners : {
						'afterlayout' : function(MyBorrowFilePanel) {

						}
					}
				});
	},// end of constructor
	// 初始化组件
	initUIComponents : function() {
		this.returnButton = new Ext.Button({
					text : '返回',
					iconCls : 'btn-reset',
					scope : this,
					handler : this.activeItem_0
				});

		this.fileListPanel = new MyBorrowFileListPanel({
			borrowNum:this.borrowNum,
			forceLayout : true,// |渲染
			frame : false,
			height : 400,
			border : false,
			defaults : {
				anchor : '96%,96%'
			}
				// ,
				// buttons : [this.returnButton]
			});
		this.fileListPanel.searchPanel.addButton(this.returnButton);

		this.fileTypePanel = new MyBorrowFileTypePanel({
					forceLayout : true,// |渲染
					height : 200,
					border : true,
					defaults : {
						anchor : '96%,96%'
					}
				}).show();

		this.fileTypePanel.store.on('beforeload', function(store) {

					if (this.recordId) {
						store.baseParams = {

							'Q_borrowRecord.recordId_L_EQ' : this.recordId,
							start : 0,
							limit : 25
						}
					} else {
						return false;
					}

				}, this);
		this.fileTypePanel.store.load();

		this.fileTypePanel.rowActions.on('action', this.onRowAction, this);

		// this.fileTypePanel.gridPanel.on('rowdblclick',this.rowClick,this);

	},
	// 显示详细
	rowClick : function(grid, rowindex, e) {
		var id = this.id;

		grid.getSelectionModel().each(function(rec) {

					Ext.getCmp(id).activeItem_1(rec);

				});
	},

	activeItem_1 : function(record) {

		var id = this.id;
		if (record.data.listType == '全宗') {
			this.getLayout().setActiveItem(1);
			this.fileListPanel.reset();

			this.fileListPanel.afNo.readOnly = true;
			this.fileListPanel.rollNo.readOnly = false;

			this.fileListPanel.afNo.setEditable(false), this.fileListPanel.rollNo
					.setEditable(true),

			this.fileListPanel.afNo.setValue(record.data.archFond.archFondId);
			this.fileListPanel.rollNo.getStore().load({
				params : {

					'Q_archFond.archFondId_L_EQ' : record.data.archFond.archFondId
				}
			});
			this.fileListPanel.rollNo.setValue('');
			/** *************案卷树 */
			var panelTree = this.fileListPanel.leftPanel
					.findByType('treepanel')[0];
			panelTree.loader = new Ext.tree.TreeLoader({
						baseParams : {
							'Q_archFondId_L_EQ':record.data.archFond.archFondId//全宗号
						},
						dataUrl : __ctxPath + '/arch/listRollTreeArchFond.do?sno='+ new Date(),
						requestMethod : 'GET'
					});
			
			panelTree.root.reload();
			/** *************案卷树 */

			this.fileListPanel.search();

			Ext.getCmp(id).setTitle('借阅清单>>全宗号:' + record.data.archFond.afNo);
			Ext.getCmp(id).doLayout();

		} else if (record.data.listType == '案卷') {
			this.getLayout().setActiveItem(1);
			this.fileListPanel.reset();

			this.fileListPanel.afNo.readOnly = true;
			this.fileListPanel.rollNo.readOnly = true;

			this.fileListPanel.afNo.setEditable(false), this.fileListPanel.rollNo
					.setEditable(false),

			this.fileListPanel.afNo.setValue(record.data.archFond.archFondId);
			this.fileListPanel.rollNo.getStore().load();
			this.fileListPanel.rollNo.setValue(record.data.archRoll.rollNo);
			/** *************案卷树 */
			var panelTree = this.fileListPanel.leftPanel
					.findByType('treepanel')[0];
			panelTree.loader = new Ext.tree.TreeLoader({
						baseParams : {
							'Q_archFondId_L_EQ':record.data.archFond.archFondId,//全宗号
							'Q_rollNo_S_LK':record.data.archRoll.rollNo//案卷号
						},
						dataUrl : __ctxPath + '/arch/listRollTreeArchFond.do?sno='+new Date(),
						requestMethod : 'POST'
					});
			
			panelTree.root.reload();
			/** *************案卷树 */

			this.fileListPanel.search();

			Ext.getCmp(id).setTitle('借阅清单>>案卷号:' + record.data.archRoll.rollNo);
			Ext.getCmp(id).doLayout();

		} else if (record.data.listType == '文件') {

			new MyBorrowFileViewWindow({
						rollFileId : record.data.rollFile.rollFileId
					}).show();
		}
		this.doLayout(true, true);

	},

	// 显示分类
	activeItem_0 : function() {
		this.getLayout().setActiveItem(0);
		if (this.showFlag) {
			if (this.showFlag == 'check') {
				Ext.getCmp(this.id).setTitle('借阅清单');
			} else if (this.showFlag == 'view') {
				Ext.getCmp(this.id).setTitle('我的借阅>>编号：' + this.borrowNum);
			}
		}
		Ext.getCmp(this.id).doLayout();

	},
	viewFile : function(record) {
		Ext.Ajax.request({
					url : __ctxPath + '/arch/listRollFileList.do',
					method : 'POST',
					// async : false,
					success : function(response, opts) {

						var obj = Ext.decode(response.responseText);
						var viewConfig = [];
						for (i = 0; i < obj.result.length; i++) {
							viewConfig.push({
										fileName : obj.result[i].fileAttach.fileName,
										filePath : obj.result[i].fileAttach.filePath
									});
						}

						new ViewFileWindow({
									viewConfig : viewConfig,
									startIndex : 0
								}).show();
					},

					failure : function(response, opts) {

					},
					params : {

						'Q_rollFile.rollFileId_L_EQ' : record.data.rollFile.rollFileId,
						dir : 'ASC',
						sort : 'sn'
					}
				});

	},
	onRowAction : function(gridPanel, record, action, row, col) {
		switch (action) {

			case 'btn-showDetail' :
				this.activeItem_1(record);
				break;
			case 'btn-readdocument' :
				this.viewFile.call(this, record);
				break;

			default :
				break;
		}
	}
});
