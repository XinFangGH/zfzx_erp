/**
 * @author lisl
 * @class GuaranteeProjectInfoPanel
 * @description 担保项目详细信息页面
 * @extends Ext.Panel
 */
LeaseFinanceProjectInfoPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		if(typeof(_cfg.isHidden)!="undefined"){
			this.isHidden=_cfg.isHidden
		}
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		LeaseFinanceProjectInfoPanel.superclass.constructor.call(this, {
			id : 'LeaseFinanceProjectInfo_' + this.record.data.projectId,// id属性值前缀通过是否包含"Edit"来区分查看和编辑页面
			title : this.isHidden==true?false:"项目详细-" + this.record.data.projectName,
			iconCls : 'btn-detail',
			autoHeight : true,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath + '/js/creditFlow/leaseFinance/project/LeaseFinanceProjectInfo.js'// 项目信息
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);

	},// 初始化组件
	constructPanel : function() {
		// 项目信息导航
		this.projectInfoNavigation = new LeaseFinanceProjectInfoNavigation({
			record : this.record,
			flag : "see" // edit表示编辑页面,see表示查看页面
		});
		// 项目信息
		this.projectInfo = new LeaseFinanceProjectInfo({
			record : this.record
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
				height : 150,
				title : '<font class="x-myZW-fieldset-title">项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.projectInfoNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.projectInfo.setHeight(document.body.clientHeight - 245 + 125);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						var nowHeight = this.projectInfo.getHeight();
						this.projectInfo.setHeight(nowHeight - 125);//项目信息部分面板当前的高度减去收缩起来的面板的高度
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