/**
 * 导航－金融中介管理
 * 	   ---审批项目查看页面
 * @extends Ext.Panel
 */
ApproveProjectInfoPanel = Ext.extend(Ext.Panel, {
	constructor : function(_cfg) {
		Ext.applyIf(this, _cfg);
		this.initUIComponents();
		ApproveProjectInfoPanel.superclass.constructor.call(this, {
			id : 'ApproveProjectInfoPanel'+ this.flag + this.record.data.projectId,// id属性值前缀,通过是否包含"Edit"来区分查看和编辑页面
			title : (this.isSeeOrEdit()?"项目详细-":"编辑项目") + this.record.data.projectName,
			iconCls : this.isSeeOrEdit()?'btn-detail':'btn-edit',
			autoHeight : true,
			items : []
		});
	},
	initUIComponents : function() {
		var jsArr = [
				__ctxPath
						+ '/js/creditFlow/smallLoan/project/ApproveProjectInfo.js'// 项目信息
		];
		$ImportSimpleJs(jsArr, this.constructPanel, this);

	},// 初始化组件
	constructPanel : function() {
		var readOnly = true
		if(this.flag=="see"){
			readOnly = true
		}else if(this.flag=="edit"){
			readOnly = false
		}
		// 项目信息导航
		this.approveProjectInfoNavigation = new ApproveProjectInfoNavigation({
			record : this.record,
			flag : this.flag,
			readOnly : readOnly
		});
		// 项目信息
		this.projectInfo = new ApproveProjectInfo({
			record : this.record,
			flag : this.flag,
			readOnly : readOnly
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
				title : '<font class="x-myZW-fieldset-title">项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作 按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.approveProjectInfoNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.projectInfo.setHeight(document.body.clientHeight - 200 + 160);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						var nowHeight = this.projectInfo.getHeight();
						this.projectInfo.setHeight(nowHeight - 160);//项目信息部分面板当前的高度减去收缩起来的面板的高度
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
	,
	isSeeOrEdit :function(){ //see  返回true   ,edit 返回false
		if(this.flag=='see'){
			return true;
		}else{
			return false;
		}
	}
});