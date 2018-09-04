/**
 * @author
 * @createtime
 * @class P2pFriendlinkForm
 * @extends Ext.Window
 * @description P2pFriendlink表单
 * @company 智维软件
 */
P2pFileUpload = Ext.extend(Ext.Window, {
	// 构造函数
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		if (typeof(_cfg.mark) != "undefined") {
			this.mark = _cfg.mark;
		}
		if (typeof(_cfg.talbeName) != "undefined") {
			this.tablename = _cfg.talbeName;
		}
		if (typeof(_cfg.webMaterialsId) != "undefined") {
			this.webMaterialsId = _cfg.webMaterialsId;
		}
		if (typeof(_cfg.projId) != "undefined") {
			this.projId = _cfg.projId;
		}
		if (typeof(_cfg.businessType) != "undefined") {
			this.businessType = _cfg.businessType;
		}
		if (typeof(_cfg.grid) != "undefined") {
			this.grid = _cfg.grid;
		}
		// Ext.applyIf(this, _cfg);
		// 必须先初始化组件
		this.initUIComponents();
		P2pFileUpload.superclass.constructor.call(this, {
			id : 'P2pFileUpload',
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
			fileUpload : true,
			defaultType : 'textfield',
			items : [{
				xtype : 'textfield',
				fieldLabel : "上传",
				columnWidth : .8,
				allowBlank : false,
				blankText : '不能为空',
				id : 'fileUpload',
				name : 'myUpload',
				fileUpload : true,
				frame : true,
				inputType : 'file'
			}]
		});
	},// end of the initcomponents

	/**
	 * 保存记录
	 */
	save : function() {
		var img_reg = /\.([jJ][pP][gG]){1}$|\.([jJ][pP][eE][gG]){1}$|\.([gG][iI][fF]){1}$|\.([pP][nN][gG]){1}$|\.([bB][mM][pP]){1}$/
		var url = 'file://' + Ext.get('fileUpload').dom.value;
		var furl = Ext.get('fileUpload').dom.value;
		var extendname = furl.substring(furl.lastIndexOf("."), furl.length);// 获取文件的后缀名
		$postForm({
			formPanel : this.formPanel,
			scope : this,
			url : __ctxPath+ '/p2pMaterials/upLoadFilesPlWebShowMaterials.do',
			params : {
				extendname : extendname,
				tablename : this.tablename,
				mark : this.mark,
				webMaterialsId : this.webMaterialsId,
				projId : this.projId,
				businessType : this.businessType
			},
			callback : function(fp, action) {
				var gridPanel = this.grid;
				if (gridPanel != null) {
					gridPanel.getStore().reload();
				}
				this.close();
			}
		});
	}// end of save
});