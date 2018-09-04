var MyPlanTaskView=function(){
	Ext.QuickTips.init(); 	
	return my_calendar();
};

function updateStoreincalendar(){
	var viewx = prueba.currentView; 
	if (viewx=='month'){ 	
		var mynewmonth= parseInt(prueba.currentdate.format('m')); 
		var mymonthday=prueba.currentdate.format('m/d/Y');
		calendarStore.baseParams={action:'month',displaymonth:mynewmonth,monthday:mymonthday};
		calendarStore.reload(); 
	}else if (viewx=='day'){ 
		var mynewdate= prueba.currentdate.format('m/d/Y'); 
		calendarStore.baseParams={action:'day',day:mynewdate};
		calendarStore.reload(); 
	} else if (viewx=='week'){ 
		var mynewdate= parseInt(prueba.currentdate.format('W'));
		var myrange = prueba.getDateRangeOfWeek(mynewdate); 
		calendarStore.baseParams={
					action:'week',
					weeknumber:mynewdate,
					startweek:myrange[0].format('m/d/Y'),
					endweek:myrange[1].format('m/d/Y')
					};
		calendarStore.reload(); 
	} else if (viewx=='schedule'){
		// e2cs.schedviews.subView ={ Day:  0,  Week: 1,  Month: 2, TwoMonths: 3,  Quarter: 4};
		if (prueba.viewscheduler.listbody.periodType==0){  // day 
			var mynewdate= prueba.currentdate.format('m/d/Y'); 
			calendarStore.baseParams={action:'day',day:mynewdate};
			calendarStore.reload();
		} else if (prueba.viewscheduler.listbody.periodType==1){ //week 
			var mynewdate= parseInt(prueba.currentdate.format('W'));
			var myrange = prueba.getDateRangeOfWeek(mynewdate); 
			calendarStore.baseParams={
						action:'week',
						weeknumber:mynewdate,
						startweek:myrange[0].format('m/d/Y'),
						endweek:myrange[1].format('m/d/Y')
						};
			calendarStore.reload();
		} else if (prueba.viewscheduler.listbody.periodType==2){ //month
			var mynewmonth= parseInt(prueba.currentdate.format('m')); 
			var mymonthday=prueba.currentdate.format('m/d/Y');
			calendarStore.baseParams={action:'month',displaymonth:mynewmonth,monthday:mymonthday};
			calendarStore.reload(); 
		} else if (prueba.viewscheduler.listbody.periodType==3){ // two months 
			var myrange = prueba.viewscheduler.getDatesforBimonth(prueba.currentdate); 
			calendarStore.baseParams={
				action:'period',
				start:myrange[0].format('m/d/Y'),
				ends:myrange[1].format('m/d/Y')
				};
			calendarStore.reload(); 
		} else if (prueba.viewscheduler.listbody.periodType==4){ // Quarter 
			var myrange = prueba.viewscheduler.getDatesforBimonth(prueba.currentdate); 
			calendarStore.baseParams={
				action:'period',
				start:myrange[0].format('m/d/Y'),
				ends:myrange[1].format('m/d/Y')
				};
			calendarStore.reload(); 		
		} 		
	}	
} 
function my_calendar(){
	var buttonx1= new Ext.menu.Item({ id: 'buttonx1_task', iconCls:'x-calendar-month-btnmv_task',	text: "Custom menu test 1" });
	var buttonx2= new Ext.menu.Item({ id: 'buttonx2_task',iconCls:'x-calendar-month-btnmv_task',	text: "Custom menu test 2" });
	var buttonz1= new Ext.menu.Item({ id: 'buttonz1_task', iconCls:'x-calendar-month-btnmv_task',	text: "Custom action 1" });
	var buttonz2= new Ext.menu.Item({ id: 'buttonz2_task',iconCls:'x-calendar-month-btnmv_task',	text: "Custom action 2" });
	var boton_daytimertask  = new Ext.menu.Item({ id: 'btnTimerTask', iconCls:'task_time', text: "Set Task Alarm...."  });
	var boton_daytimertaskb = new Ext.menu.Item({ id: 'btnTimerOff' , iconCls:'task_time_off', text: "Delete Task's Alarm...."  });		
	var button_sched_1= new Ext.menu.Item({ id: 'buttonx1_task',iconCls:'x-calendar-month-btnmv_task',text: "Custom menu  on sched test 1" });
	var button_sched_2= new Ext.menu.Item({ id: 'buttonx2_task',iconCls:'x-calendar-month-btnmv_task',text: "Custom menu  on sched test 2" });
	
	var curDate=new Date();
	
	calendarStore=new Ext.data.Store({
        url:__ctxPath+'/task/myCalendarPlan.do',
		baseParams:{ action:'all',action:'month',displaymonth:12,monthday:curDate.format('m/d/Y')},
        reader: new Ext.data.JsonReader({
	        root:'records',id: 'id',totalProperty: 'totalCount'
           	   },
			   [{name:'recid', 		 mapping:'id', 			type: 'string'},{name:'subject', mapping:'subject', 	type: 'string'},
				{name:'description', mapping:'description', type: 'string'},{name:'startdate', mapping:'startdate', 	type: 'string'},
				{name:'enddate', 	 mapping:'enddate', 	type: 'string'},{name:'color', 	mapping:'color', 		type: 'string'},
				{name:'parent',		 mapping:'parent',		type: 'int'},{name:'priority',	mapping:'priority',	type: 'int'}
				]
			   )
    });		
	prueba = new Ext.ECalendar({
		id:'MyPlanTaskView', 
		name:'MyPlanTaskView',
		title:'日程管理',
		mytitle:'',	
		height:500, 
		fieldsRefer:{ //0.0.11 
			id:'recid',
			subject:'subject',
			description:'description', 
			color:'color',
			startdate:'startdate',
			enddate:'enddate',
			priority:'priority',
			parent:'parent',
			html:'' //0.0.13  new property
		},
		storeOrderby:'priority', 	//0.0.11 
		storeOrder:'DESC',		//0.0.11 
		showCal_tbar: true, 
		showRefreshbtn:true,
		refreshAction:'view', //0.0.11 
		currentView: 'month',
		currentdate: curDate,//new Date(),//TEST is for data new Date()
		dateSelector: true,
		dateSelectorIcon: __ctxPath+'/ext3/ux/caltask/images/date.png',
		dateSelectorIconCls: 'x-cmscalendar-icon-date-selector',
		dateformat :'d/m/Y',
		header: true,
		title: '我的日程管理',
		mytitle: ' ',//001
		iconCls: 'x-cmscalendar-icon-main',
		dateSelector:true,
		store:	calendarStore, 
		monitorBrowserResize:true, 
		widgetsBind: {bindMonth:null,bindDay:null,binWeek:null},
		tplTaskZoom: new Ext.XTemplate( 
		'<tpl for=".">',
			'<div class="ecal-show-basetasktpl-div"><b>主题:</b>{subject}<br>',
			'<b>开始1:</b>{startdate}<br><b>结束1:</b>{enddate}',
			'<br><b>明细:</b><hr>{description}</div>',
		'</tpl>'
		),
		
		iconToday:__ctxPath+'/ext3/ux/caltask/images/cms_calendar.png',
		iconMonthView:__ctxPath+'/ext3/ux/caltask/images/calendar_view_month.png',
		iconWeekView:__ctxPath+'/ext3/ux/caltask/images/calendar_view_week.png',
		iconDayView:__ctxPath+'/ext3/ux/caltask/images/calendar_view_day.png',
		iconSchedView:__ctxPath+'/ext3/ux/caltask/images/calendar_view_schedule.png', //0.0.10  // NEW :) 
		iconRefresh:__ctxPath+'/ext3/ux/caltask/images/calendar_view_refresh.png',
		iconAddTask:__ctxPath+'/ext3/ux/caltask/images/calendar_view_addTask.png',
		loadMask:true, //0.0.12 
		customMaskText:'系统信息<br>请稍等!<br>正在处理日程的信息', //0.0.12 
		//-------- NEW on 0.0.10 -------------------
		sview:{
				header: true, headerFormat:'Y-M', 
				headerButtons: true,
				headerAction:'event',  //gotoview
				periodselector:false,
				blankzonebg:'#6C90B4',
				//sched_addevent_id
				blankHTML:'<div id="{calx}-test-img" class="custom_image_addNewEvent_scheduler" style=" width:100%; background-color:#6C90B4"><div align="center" id="{sched_addevent_id}"><img src="'+__ctxPath+'/ext3/ux/caltask/images/no_events_default.jpg" width="174" height="143"></div><div class="custom_text_addNewEvent_scheduler">点击图片加日程任务</div></div>',
				listItems: { 
					headerTitle:"日程任务", 		
					periodFormats:{ 
						Day:		'星期l - d - F - Y', 	
						DayScheduler_format: 'd', 	
						hourFormat: 'h:i a', 				 
						startTime:  '7:00:00 am',		
						endTime:    '10:00:00 pm',
						WeekTPL:  	'<tpl for=".">Week No.{numweek} Starting on {datestart} Ending on {dateend}</tpl>',  
						WeekFormat:	'W',	
						DatesFormat:'d/m/Y', 
						Month:'M-Y', 
						TwoMonthsTPL:'<tpl for=".">Period No.{numperiod} Starting on {datestart} Ending on {dateend}</tpl>',
						QuarterTPL:  '<tpl for=".">Period No.{numperiod} Starting on {datestart} Ending on {dateend}</tpl>'
					},	
					useStoreColor:false, 	
					descriptionWidth:246,
					parentLists:false, //to expand collapse Parent Items if false all tasks shown as parent
					launchEventOn:'click',
					editableEvents:true, // If true a context menu will appear 
					ShowMenuItems:[1,1,1,1,1,1,1,1], // ADD, EDIT, DELETE, GO NEXT PERIOD , GO PREV PERIOD, Chg Month, Chg Week, CHG Day
					taskdd_ShowMenuItems:[1,1,1],    // ADD, EDIT, DELETE
					moreMenuItems:[button_sched_1,button_sched_2],
					taskdd_BaseColor:'#6C90B4',
					taskdd_clsOver:'test_taskovercss_sched',
					taskdd_showqtip:true,
					taskdd_shownames:true
				},
				listbody:{
					//e2cs.schedviews.subView ={ Day:  0,  Week: 1,  Month: 2, TwoMonths: 3,  Quarter: 4};
					//e2cs.schedviews.Units   ={ Hours:0,  Days: 1,  Weeks: 2};
					periodType:e2cs.schedviews.subView.Month,
					headerUnit:e2cs.schedviews.Units.Days,
					headerUnitWidth:25
				}
		}, 
		//-------------------------------------------
		mview:{
			header: true,
			headerFormat:'Y-F',
			headerButtons: true,
			dayAction:'viewday',    //dayAction: //viewday , event, window
			moreMenuItems:[buttonx1,buttonx2],
			showTaskcount: false,
			startDay:0,
			taskStyle:'margin-top:2px;', //Css style for text in day(if it has tasks and showtaskcount:true)
			showTaskList: true,
			showNumTasks:10,
			TaskList_launchEventOn:'click', //0.0.11 
			//TaskList_tplqTip: new Ext.XTemplate( '<tpl for=".">{starxl}{startval}<br>{endxl}{endval}<hr color=\'#003366\' noshade>{details}</tpl>' ), //0.0.11 
			ShowMenuItems:[1,1,1,1,1,1],  //0.0.11  - ADD, nextmonth, prevmonth, chg Week , CHG Day, chg Sched,	
			TaskList_moreMenuItems:[buttonz1,buttonz2], 	  //0.0.11
			TaskList_ShowMenuItems:[1,1,1]//0.0.11 	- Add, DELETE, EDIT 	
		},
		wview:{
			headerlabel:'周 #',
			headerButtons: true,
			dayformatLabel:'D j', 
			moreMenuItems:[buttonx1,buttonx2],
			style: 'google',
			alldayTaksMore:'window', 
			alldayTasksMaxView:6,
			store: null, 
			task_width:25, 
			tasksOffset:40,
			headerDayClick:'viewday',
			ShowMenuItems:[1,1,1,1,1,1],	//0.0.11  add, go next w , go prev w , chg month , chg day, chg sched 
			task_ShowMenuItems:[1,1,1,1,1], //0.0.11  add, delete, edit, go next w , go prev w
			task_eventLaunch:'click',		//0.0.11
			startDay:0, //sundays   0.0.14 
			task_clsOver:'test_taskovercss',
			forceTaskFit:true // 0.0.14 
		},
		dview:{
			header:true,
			headerFormat:'星期l - d - F  - Y',
			headerButtons: true,
			moreMenuItems:[],
			// day specific 
			hourFormat: 'h',
			startTime: '00:00:00 am',
			endTime:   '11:59:59 pm',
			// task settings 
			store: null,
			taskBaseColor: '#ffffff', 
			task_width:60,
			taskAdd_dblclick: true,				//added on 0.0.7
			taskAdd_timer_dblclick:true,		//0.0.11
			useMultiColorTasks: false, 
			multiColorTasks:[], 
			tasks:[],
			moreMenuItems:[buttonx1,buttonx2],
			//moreMenuItems:[	boton_daytimertask,	boton_daytimertaskb	],
			task_clsOver:'test_taskovercss',	
			ShowMenuItems:[1,1,1,1,1,1],		//0.0.11 ADD, next day, prev day , chg Month , CHG Week, chg Sched, (for daybody menu) 
			task_DDeditable:true, 			    //0.0.11   
			task_eventLaunch:'click',  		    //0.0.11 'click, dblclick, if set to '' then no action is taken
			task_ShowMenuItems:[1,1,1,1,1],		//0.0.11 ADD, delete, edit, Next day , Prev Day  (for Taks/events) 
			customHTMLinpos:'before',			//0.0.13  Feature request 
			forceTaskFit: true					//0.0.14	
		}
	});
	
	prueba.viewscheduler.on({//scheduler only event on this object  
		'headerClick':{
				fn: function(refunit,datex, mviewx, calx) { 
				},
				scope:this
		},
		'beforePeriodChange':{
				fn:function(refperiod,datexold,datexnew){
					if (refperiod==1){ //week 
						//do your stuff here 
					} else { 
						//do your stuff here 
					}
					return true; 
				},
				scope:this
		}, 
		'afterPeriodChange':{
				fn:function(refperiod,datexnew){
					if (refperiod==1){ 
						//alert ("Changed date from " + datexnew[0] + " to " + datexnew[1]);					
					} else { 
						//alert ("Changed date to " + datexnew);					
					}
					updateStoreincalendar();
				}
		}
	});
	prueba.viewmonth.on({//dayClick only event on this object		
		'dayClick':{
				fn: function(datex, mviewx, calx) { 
					//alert ("dayclick event for " + datex);
				},
				scope:this
		},
		'beforeMonthChange':{
				fn: function(currentdate,newdate) { 
					//alert ("gonna change month to " + newdate.format('m/Y') + ' Actual date=' + currentdate.format('m/Y') );
					prueba.currentdate=newdate;
					return true; 
				},
				scope:this
		},
		'afterMonthChange':{
				fn: function(newdate) { 
					//alert ("Month changed to " + newdate.format('m/Y') ) ;
					//alert('ok');
					updateStoreincalendar();  // refresh the data for that selected month 
				},
				scope:this
		}
	}); 
	prueba.viewweek.on({
		'dblClickTaskAllDay':{
				fn: function(task,dxview,calendar) { 

					new CalendarPlanDetailView(task[1]);
				},
				scope:this
		},
		'beforeWeekChange':{
			fn: function (currentDate, newDate){
					return true;	
			}
		}, 
		'afterWeekChange':{
			fn: function(newdate){
				prueba.currentdate=newdate;
				updateStoreincalendar();
				return false; 
			}
		}
	});  
	prueba.viewday.on({//'beforeDayChange' and 'afterDayChange' unique events on day view 	
		'beforeDayChange':{
				fn: function(currentdate, newdate) { 
					//alert ("gonna change to " + newdate.format('m/d/Y') + ' Actual date=' + currentdate.format('m/d/Y') );
					return true; 
				},
				scope:this
		},		
		'afterDayChange':{
				fn: function(newdate) { 
					prueba.currentdate=newdate;
					updateStoreincalendar();
				},
				scope:this
		}	
	}); 
	prueba.on({
		'onReload':{
			fn:function(){
				prueba.store.reload();
			}
		},
		'beforeContextMenuTask': {
			fn: function(refview,datatask,showItems,myactions) { 
				return false; 
			} 
		},
		'beforeChangeDate': {
			fn: function( newdate , calobj){
				return true; 
			} 		
		},
		'afterChangeDate':{
			fn: function( newdate , calobj){
				//alert ("Date changed to:" + newdate.format('d-m-Y'));
				updateStoreincalendar();  // refresh the data for that selected date and view 
			}
		},
		'onChangeView':{
			fn: function(newView, oldView, calobj){ 	
				//Ext.get("samplebox_cview").update("<b>Current View:</b> " + newView); 
				updateStoreincalendar(); // refresh the data for that selected date and view 
			},scope: this
		},
		'beforeChangeView':{
				fn: function (newView,OldView,calendarOBJ){
					if (newView==OldView){ 	return true; } 
					return true;
				},scope:this
		},
		'taskAdd':{
				fn: function( datex ) { 
					//添加任务
					new CalendarPlanForm(null,function(){
							updateStoreincalendar();
						}
					);			
				}
		},
		'taskDblClick':{
				fn: function (task,dxview,calendar,refviewname){
					new CalendarPlanDetailView(task[1]);
				},
				scope:this 
		},
		'beforeTaskDelete': {
				fn: function (datatask,dxview) { 
					return false; 
					// do your stuff to check if the event/task could be deleted 
				}, scope:this
		},
		'onTaskDelete':{
			fn:function(datatask){
				var r=confirm("Delete event " + datatask[1] + " " + datatask[2] + "...? YES/NO" );
				return r; 
				// do your stuf for deletion and return the value 
			},scope:this
		},
	   'afterTaskDelete':{
	   		fn: function(datatask,action){
				action ? alert("Event: " + datatask[1] + " " + datatask[2] + " Deleted"): alert("Event Delete was canceled..!"); 
				// perform any action after deleting the event/task
				// do your stuff and then send the data to the php file and 
				updateStoreincalendar();						
			},scope:this
 	    },
		'beforeTaskEdit': {
				fn: function (datatask,dxview) { 
					return false; 							
				}, scope:this
		},	
	   'onTaskEdit':{
			fn:function(datatask){
				//var r=confirm("Edit event " + datatask[1] + " " + datatask[2] + "...? YES/NO" );
				return true; 
				// do your stuff for editing and return the value				
			},scope:this
	    },
	    'afterTaskEdit':{
	   		fn: function(datatask,action){// perform any action after deleting the event/task
				if (action){ 
					//alert("Event: " + datatask[1] + " " + datatask[2] + " Edited");
					// do your stuff and then send the data to the php file and 
					// updateStoreincalendar();						
				} else { 
					alert("Event Edit was canceled..!"); 
				} 
				return false; 		
			},scope:this
	    },
		'beforeTaskMove':{
				fn: function (datatask,Taskobj,dxview,TaskEl) { // return "true" to cancel or "false" to go on 
					return false; 	
				}, scope:this
		},
		'TaskMoved':{
				fn: function (newDataTask,Taskobj,dxview,TaskEl) {   // do some stuff 
					var test=21;  // use breakpoint in firefox here 
					task = newDataTask; 
//					datatest ='Task id:'  + task[0] + ' ' + task[2] + '<br>';
//					datatest+='recid:'    + task[1] + '<br>';
//					datatest+='starts:'    + task[3] + '<br>';
//					datatest+='ends:'   + task[4] + '<br>';
//					datatest+='contents:' + task[5] + '<br>';	
//					datatest+='index:'    + task[6] + '<br>';	
//					Ext.Msg.alert('Information Modified task', datatest);	
					new CalendarPlanDetailView(task[0]);
					// do your stuff and then send the data to the php file and 
					// updateStoreincalendar();	
				}, scope:this
		},
		'customMenuAction':{
				fn: function (MenuId, Currentview,datatask,objEl,dxview){
					var datatest = ''; 
					if (Currentview=='month'){ 
//						task = datatask; 
//						datatest ='Element ID :'  + task[0] + '<br>';
//						datatest+='Task ID :'  + task[1] + '<br>';
//						datatest+='Menu ID :'  + MenuId + '<br>';
//						Ext.Msg.alert('(Month) Information- ' + Currentview, datatest);	

					} else if (Currentview=='day'){ 
						task = datatask; 
						datatest ='Task id:'  + task[0] + ' ' + task[1] + '<br>';
						datatest+='starts:'    + task[2] + '<br>';
 						datatest+='Ends:'   + task[3] + '<br>';
 						datatest+='contents:' + task[4] + '<br>';	
						datatest+='index:'    + task[5] + '<br>';	
						datatest+='Test Menu:'  + MenuId + '<br>';	
						Ext.Msg.alert('(Day) Task information' + Currentview, datatest);			
					} else if (Currentview=='week'){
						//misssing sample 
					} else if (Currentview=='scheduler'){
						task = datatask; 
						datatest ='Task id:'  + task[0] + ':' + task[2] + '<br>';
						datatest+='starts:'    + task[3] + '<br>';
 						datatest+='Ends:'   + task[4] + '<br>';
 						datatest+='contents:' + task[5] + '<br>';	
						datatest+='index:'    + task[6] + '<br>';	
						datatest+='Test Menu:'  + MenuId + '<br>';	
						Ext.Msg.alert('(Scheduler) Task information' + Currentview, datatest);			
					} 
				},scope:this		
		}

	});
	//prueba.render('calendar');

	prueba.on({
		'render':{
			fn:function(prueba){
				calendarStore.load();	
				myMasktest = new Ext.LoadMask( prueba.id, {msg:e2cs.cal_locale.loadmaskText});
				calendarStore.on({
						'beforeload':{fn:function(){ prueba.calendarMask.show(); }
						},	
						'load':{
							fn:function(success,dataxx,purebax){
								if(success==false){
									alert ("加载日程任务数据出错！");
								} else { 
								 	prueba.refreshCalendarView(true); 
								} 
								prueba.calendarMask.hide();
							}
						} 
				});		
				Ext.EventManager.onWindowResize( function(){ 
					prueba.refreshCalendarView(); 
				});
			}
		}
	});

	return prueba;
}

 