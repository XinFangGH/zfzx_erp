/**
 * @author
 * @createtime
 * @class SmallLoanAlterAccrualEditView
 * @extends Ext.Window
 * @description 利率变更详情
 * @company 智维软件
 */
SlEarlyrepaymentRecordEditInfoView = Ext.extend(Ext.Panel, {
	// 构造函数
	constructor : function(_cfg) {
		if (_cfg.record) {
			this.record = _cfg.record;
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
		SlEarlyrepaymentRecordEditInfoView.superclass.constructor.call(this, {
			id: 'SlEarlyrepaymentRecordEditInfoView_'+this.flag+'_'+this.record.data.slEarlyRepaymentId,
			title : this.flag=='edit'?'提前还款编辑——'+this.record.data.dateMode:'提前还款查看——'+this.record.data.dateMode,
			autoHeight : true,
			items : [this.outPanel]
		});
	},// end of constructor
	
	// 初始化组件
	initUIComponents : function() {
		this.SlEarlyrepaymentRecordNavigation=new SlEarlyrepaymentRecordNavigation({record:this.record,flag:this.flag,projectStatus:this.projectStatus,fundProjectId:this.fundProjectId,businessType:this.businessType});
		this.SlEarlyrepaymentRecordInfo=""
		if(this.flag=='edit'){
			this.SlEarlyrepaymentRecordInfo=new SlEarlyrepaymentRecordEditInfo({record:this.record,oppositeType:this.oppositeType,projectStatus:this.projectStatus,fundProjectId:this.fundProjectId,businessType:this.businessType,projectId:this.projectId});
		}else{
			this.SlEarlyrepaymentRecordInfo=new SlEarlyrepaymentRecordSeeInfo({record:this.record,oppositeType:this.oppositeType,fundProjectId:this.fundProjectId,businessType:this.businessType,projectId:this.projectId});

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
				items : [this.SlEarlyrepaymentRecordNavigation],
				listeners : {
					scope : this,
					"collapse" : function(panel) {
						this.SlEarlyrepaymentRecordInfo.setHeight(document.body.clientHeight - 225 + 100);//项目信息部分面板最初的高度加上收缩起来的面板的高度
						this.doLayout();
					},
					"expand" : function(panel) { 
						//var nowHeight = this.SlEarlyrepaymentRecordInfo.getHeight();
						this.SlEarlyrepaymentRecordInfo.setHeight(document.body.clientHeight - 200);//项目信息部分面板当前的高度减去收缩起来的面板的高度
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
				items : [this.SlEarlyrepaymentRecordInfo]
			}]
		});
		
	}
});
