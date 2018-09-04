/**
 * @author lisl
 * @class PostponedProjectInfoPanel
 * @description 小贷展期项目详细信息页面
 * @extends Ext.Panel
 */
flPostponedProjectInfoPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		flPostponedProjectInfoPanel.superclass.constructor.call(this, {
			id : 'PostponedProjectInfoPanel_' + this.projectRecord.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : "展期项目详细-" + this.projectRecord.data.projectName,
			iconCls : 'btn-detail',
			autoHeight : true,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath
						+ '/js/creditFlow/leaseFinance/project/flPostponedProjectInfo.js'// 项目信息
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);

	},// 初始化组件
	constructPanel : function() {
		// 项目信息导航
		this.postponedProjectInfoNavigation = new flPostponedProjectInfoNavigation({
			slSuperviseRecordId : this.record.data.id,
			record : this.projectRecord,
			//isActivityComboEdit : this.isActivityComboEdit,
			//enabled : this.enabled,
			flag : "see" // edit表示编辑页面,see表示查看页面
			/*record : this.projectRecord,
			flag : "see" // edit表示编辑页面,see表示查看页面
*/		});
		// 项目信息
		this.projectInfo = new flPostponedProjectInfo({
			record : this.record,
			projectRecord : this.projectRecord
		});
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			frame : true,
			defaults : {
				anchor : '100%',
				columnWidth : 1,
				collapsible : false
			},
			labelAlign : "right",
			items : [{
				xtype : 'panel',
				height : 120,
				title : '<font class="x-myZW-fieldset-title">展期项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作 按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.postponedProjectInfoNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.projectInfo.setHeight(document.body.clientHeight - 280 + 65);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						var nowHeight = this.projectInfo.getHeight();
						this.projectInfo.setHeight(nowHeight - 65);//项目信息部分面板当前的高度减去收缩起来的面板的高度
						this.doLayout();
					}
				}
			}, {
				xtype : 'panel',
				layout : 'form',
				layoutConfig : {
					padding : '5',
					pack : 'center',
					align : 'middle'
				},
				items : [this.projectInfo]
			}]
		});
		this.add(this.outPanel);
		this.doLayout();
	}// 初始化UI结束
});