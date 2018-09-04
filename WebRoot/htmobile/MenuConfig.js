//menu-team 权限参照这个xml
var menus = [{
				id : 'MyTask',
				cls : 'mySchedule',
				title : '我的待办',
				view : 'htmobile.myTask.MyTask',
				notice : "/htmobile/getNoticeTask.do?processName=" + processNameFlowKey,
				isShowStr : "ActivityTaskView"
					// notice:'/htmobile/getNoticeTask.do?processName='+processNameFlowKey
				}, {
				id : 'DanganConfig',
				cls : 'myTask',
				title : '新建客户',
				view : 'htmobile.customer.DanganConfig',
				isShowStr : "_create_qykh,_create_grkh" // ，个人新建档案,企业新建档案
			}, {
				id : 'XiangmuConfig',
				cls : 'seach',
				title : '申请项目',
				view : 'htmobile.approve.XiangmuConfig' // 个人权限，企业权限
				,
				isShowStr : "CreateNewProjectFrom?operationType=SmallLoan_PersonalCreditLoanBusiness,"
						+ "CreateNewProjectFrom?operationType=SmallLoan_SmallLoanBusiness"
			}, {
				id : 'SlSmallloanProjectList',
				cls : 'person',
				title : '项目管理',
				view : 'htmobile.project.SlSmallloanProjectList',
				isShowStr : "ApproveProjectManager?projectStatus=2&marker=3,ApproveProject_seeAll_3"
			}, {
				id : 'DefineConfig',
				cls : 'mydel',
				title : '贷款计算',
				view : 'htmobile.public.loansCountForm'
			}, {
				id : 'btnPersonList',
				cls : 'entprise',
				title : '消息',
				view : 'htmobile.project.SmallLoanedProjectManager',
				isShowStr : "ApproveProjectManager?projectStatus=2&marker=8,ApproveProject_seeAll_8"
			}, {
				id : 'SlFundIntentView',
				cls : 'addressBook',
				title : '业绩排行榜',
				view : 'htmobile.public.showTable'
			}, {
				id : 'UserSettingManage',
				cls : 'setting',
				title : '我的业绩',
				view : 'htmobile.usermanage.UserSettingManage'
			}, {
				id : 'DatabaseList',
				cls : 'myBulletin',
				title : '用户资料库',
				view : 'htmobile.customer.materialsData.PersonMaterilalist',
				isShowStr : "PersonView,EnterpriseView"
			}];