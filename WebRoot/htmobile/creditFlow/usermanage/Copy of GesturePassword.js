        var R = 26, CW = 300, CH = 300, OffsetX = 26, OffsetY = 26;
     
         function CaculateNinePointLotion(diffX, diffY) {
            var Re = [];
            for (var row = 0; row < 3; row++) {
                for (var col = 0; col < 3; col++) {
                    var Point = {
                        X: (OffsetX + col * diffX + ( col * 2 + 1) * R),
                        Y: (OffsetY + row * diffY + (row * 2 + 1) * R)
                    };
                    Re.push(Point);
                }
            }
            return Re;
        }
          var PointLocationArr1 = [];
          var PointLocationArr = [];
          var   windowonload = function () {
  /*   	   var div = document.createElement("div");
     	  div.id="myCanvasdiv";
     	  div.style="background:red;";
          div.innerHTML = "<canvas id='myCanvas'></canvas>";
          var first=document.body.firstChild;//得到页面的第一个元素 
            document.body.insertBefore(div,first);//在得到的第一个元素之前插入
*/            
            var c = document.getElementById("myCanvas");
            CW = document.body.offsetWidth;
            c.width = CW;
            c.height = CH;
            var cxt = c.getContext("2d");
            //两个圆之间的外距离 就是说两个圆心的距离去除两个半径
            var X = (CW - 2 * OffsetX - R * 2 * 3) / 2;
            var Y = (CH - 2 * OffsetY - R * 2 * 3) / 2;
            PointLocationArr = CaculateNinePointLotion(X, Y);
            InitEvent(c, cxt);
            //CW=2*offsetX+R*2*3+2*X
            Draw(cxt, PointLocationArr, [],null);
        }
        function Draw(cxt, _PointLocationArr, _LinePointArr,touchPoint) {
            if (_LinePointArr.length > 0) {
                cxt.beginPath();
                for (var i = 0; i < _LinePointArr.length; i++) {
                    var pointIndex = _LinePointArr[i];
                    cxt.lineTo(_PointLocationArr[pointIndex].X, _PointLocationArr[pointIndex].Y);
                }
                cxt.lineWidth = 10;
                cxt.strokeStyle = "#ffffff";//选中的连线
                cxt.stroke();
                cxt.closePath();
                if(touchPoint!=null)
                {
                    var lastPointIndex=_LinePointArr[_LinePointArr.length-1];
                    var lastPoint=_PointLocationArr[lastPointIndex];
                    cxt.beginPath();
                    cxt.moveTo(lastPoint.X,lastPoint.Y);
                    cxt.lineTo(touchPoint.X,touchPoint.Y-100);
                    cxt.stroke();
                    cxt.closePath();
                }
            }
            for (var i = 0; i < _PointLocationArr.length; i++) {
                var Point = _PointLocationArr[i];
                cxt.fillStyle = "#ffffff";
                cxt.beginPath();
                cxt.arc(Point.X, Point.Y, R, 0, Math.PI * 2, true);
                cxt.closePath();
                cxt.fill();
                cxt.fillStyle = "#1069a5";
                cxt.beginPath();
                cxt.arc(Point.X, Point.Y, R - 3, 0, Math.PI * 2, true);
                cxt.closePath();
                cxt.fill();
                if(_LinePointArr.indexOf(i)>=0)
                {
                    cxt.fillStyle = "#ffffff";//选中的小圆圈
                    cxt.beginPath();
                    cxt.arc(Point.X, Point.Y, R -16, 0, Math.PI * 2, true);
                    cxt.closePath();
                    cxt.fill();
                }

            }
        }
        
        function IsPointSelect(touches,LinePoint)
        {
            for (var i = 0; i < PointLocationArr.length; i++) {
                var currentPoint = PointLocationArr[i];
                var xdiff = Math.abs(currentPoint.X - touches.pageX);
                var ydiff = Math.abs(currentPoint.Y - touches.pageY+100);
                var dir = Math.pow((xdiff * xdiff + ydiff * ydiff), 0.5);
                if (dir < R ) {
                    if(LinePoint.indexOf(i) < 0){ LinePoint.push(i);}
                    break;
                }
            }
        }
        function InitEvent(canvasContainer, cxt) {
            var LinePoint = [];
            canvasContainer.addEventListener("touchstart", function (e) {
                IsPointSelect(e.touches[0],LinePoint);
            }, false);
            canvasContainer.addEventListener("touchmove", function (e) {
                e.preventDefault();
                var touches = e.touches[0];
                IsPointSelect(touches,LinePoint);
                cxt.clearRect(0,0,CW,CH);
                Draw(cxt,PointLocationArr,LinePoint,{X:touches.pageX,Y:touches.pageY});
            }, false);
            canvasContainer.addEventListener("touchend", function (e) {
                cxt.clearRect(0,0,CW,CH);
                Draw(cxt,PointLocationArr,LinePoint,null);
                
                var my = document.getElementById("myCanvasdiv");
                var  username=localStorage.getItem("userName");
                var isGesturePassword= localStorage.getItem(username+"isGesturePassword");
                //设置手势密码
                if(isGesturePassword=="false"){
                	if(LinePoint.length > 0 && LinePoint.length<4){  
            
	            	  cxt.clearRect(0,0,CW,CH);
	                  Draw(cxt, PointLocationArr, [],null);
	                  LinePoint=[];
	                   
	                  var diverrorMsg = document.getElementById("errorMsg");
	                  diverrorMsg.innerHTML = "至少需要4个链接点！";
                    
                  }else{
                 
	                  var  settinggesturePasswordcount= localStorage.getItem(username+"settinggesturePasswordcount");
	                  if(settinggesturePasswordcount==1){
	                  	   localStorage.setItem(username+"settinggesturePasswordcount",2);
	                  	   localStorage.setItem(username+"gesturePassword",LinePoint.join(""));
	                  	   cxt.clearRect(0,0,CW,CH);
		                   Draw(cxt, PointLocationArr, [],null);
		                   LinePoint=[];
		                   
		                   var diverrorMsg = document.getElementById("errorMsg");
		                   diverrorMsg.innerHTML = "再次绘制解锁图案";
	                  	  
	                  }else{
	                  	  if(localStorage.getItem(username+"gesturePassword")==LinePoint.join("")){
	                  	    Ext.Msg.alert('提示', "设置手势密码成功");
	                  	    gesturePasswordSetting.destroy();
	                  	    localStorage.setItem(username+"isGesturePassword",'true');
				  //      	localStorage.setItem(username+"gesturePassword","036");
				            localStorage.setItem(username+"errorPassword","0");
				            localStorage.setItem(username+"settinggesturePasswordcount",1);
			        		mobileNavi = Ext.create('mobile.View',{username:curUserInfo.fullname,userId:curUserInfo.userId});
			        		mobileView.add(mobileNavi);
	                  	  	return ;
	                  	  }else{
	                  	    var diverrorMsg = document.getElementById("errorMsg");
		                    diverrorMsg.innerHTML = "与上一次绘制的不一致，请重新绘制";
	                  	    localStorage.setItem(username+"settinggesturePasswordcount",1);
	                  	    cxt.clearRect(0,0,CW,CH);
		                    Draw(cxt, PointLocationArr, [],null);
		                    LinePoint=[];
	                  	  	
	                  	  }
	                  	
	                  }
	                 
                 
                 }	
                
                 
                }else{
                
	                var username = localStorage.getItem("userName")!=null?localStorage.getItem("userName"):'';
	                var gesturePassword= localStorage.getItem(username+"gesturePassword")!=null?localStorage.getItem(username+"gesturePassword"):'';
	                if(gesturePassword==LinePoint.join("")){
	                    Ext.Ajax.request({
						 url: __ctxPath+'/htmobile/loginMobile.do',
						params:{
						    username:username,
						    isGesturePassword:"true"
						    
						},
					    success : function(response) {
				        	var obj = Ext.util.JSON.decode(response.responseText);
				        	if(obj.success){
				        	/*	 var my = document.getElementById("myCanvasdiv");
				        		my.parentNode.removeChild(my);*/
				        		mobileView.removeAt(0);
			        	    	gesturePasswordLogin.destroy();
				        		curUserInfo=obj.user;
				        		localStorage.setItem(username+"errorPassword",0);
				        		mobileNavi = Ext.create('mobile.View',{username:obj.user.fullname,userId:obj.user.userId});
				        		mobileView.add(mobileNavi);
				        	   
				        	}else{}
					        }
						});
	                }else{
	                	var errorsum= localStorage.getItem(username+"errorPassword");
	                	if(typeof(errorsum)=="undefind"|| errorsum==null){
	                		errorsum=0;
	                	}
	                	if(parseInt(errorsum)==4){
	                	    localStorage.setItem(username+"errorPassword",parseInt(errorsum)+1);
	                		Ext.Msg.alert('提示', "你已连续5次输错手势，手势解锁已关闭，请重新登录。");
	                		 localStorage.setItem(username+"isGesturePassword","false")
	                		gesturePasswordLogin.destroy();
	                		 mobileLogin = Ext.create('mobile.Login', {fullscreen: true});
	                	}else{
		                	localStorage.setItem(username+"errorPassword",parseInt(errorsum)+1);
		                    var diverrorMsg = document.getElementById("errorMsg");
		                    diverrorMsg.innerHTML = "密码错了，还可以输入"+(5-parseInt(errorsum)-1)+"次";
		                	cxt.clearRect(0,0,CW,CH);
		                    Draw(cxt, PointLocationArr, [],null);
		                    LinePoint=[];
	                	}
	                	
	                }
                }
	                
                
            /*     mobileLogin = Ext.create('mobile.Login', {fullscreen: true});
                LinePoint=[];*/
            }, false);
        }