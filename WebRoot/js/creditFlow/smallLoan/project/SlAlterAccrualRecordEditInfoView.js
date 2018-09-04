/**
 * @author
 * @createtime
 * @class SmallLoanAlterAccrualEditView
 * @extends Ext.Window
 * @description 利率变更详情
 * @company 智维软件
 */
SlAlterAccrualRecordEditInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
		}
		if (_cfg.oppositeType) {
			this.oppositeType = _cfg.oppositeType;
		}
		if (_cfg.flag) {
			this.flag = _cfg.flag;
		}
		if (_cfg.projectStatus) {
			this.projectStatus = _cfg.projectStatus;
		}
		Ext.applyIf(this, _cfg);
		// 初始化组件
		this.initUIComponents();
		// 调用父类构造
		SlAlterAccrualRecordEditInfoView.superclass.constructor.call(this, {
			id: 'SlAlterAccrualRecordEditInfoView_'+this.flag+'_'+this.record.data.slAlteraccrualRecordId,
			title : this.flag=='edit'?'利率变更编辑——'+this.record.data.dateMode:'利率变更查看——'+this.record.data.dateMode,
			autoHeight : true,
			items : [this.outPanel]
		});
	},// end of constructor
	
	// 初始化组件
	initUIComponents : function() {
		this.SlAlterAccrualRecordNavigation=new SlAlterAccrualRecordNavigation({record:this.record,flag:this.flag,projectStatus:this.projectStatus,projectId:this.projectId});
		this.SlAlterAccrualRecordInfo=""
		if(this.flag=='edit'){
			this.SlAlterAccrualRecordInfo=new SlAlterAccrualRecordEditInfo({record:this.record,oppositeType:this.oppositeType,projectStatus:this.projectStatus,projectId:this.projectId});
		}else{
			this.SlAlterAccrualRecordInfo=new SlAlterAccrualRecordSeeInfo({record:this.record,oppositeType:this.oppositeType,projectId:this.projectId});

		}
		this.outPanel = new Ext.Panel({
			modal : true,
			buttonAlign : 'center',
			layout : 'form',
			frame : true,
			autoHeight :true,
			defaults : {
				anchor : '100%',
				columnWidth : 1,
				collapsible : false
			},
			labelAlign : "right",
			items : [{
				xtype : 'panel',
				height : 100,
				title : '<font class="x-myZW-fieldset-title">项目总览(</font><font color="red">注：带图标按钮为弹窗式操作按钮，其余均为项目信息部分快速定位操作按钮</font><font class="x-myZW-fieldset-title">)</font>',
				collapsible : true,
				items : [this.SlAlterAccrualRecordNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.SlAlterAccrualRecordInfo.setHeight(document.body.clientHeight - 225 + 100);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						this.SlAlterAccrualRecordInfo.setHeight(document.body.clientHeight - 200);//项目信息部分面板当前的高度减去收缩起来的面板的高度
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
				items : [this.SlAlterAccrualRecordInfo]
			}]
		});
		
	}
});
